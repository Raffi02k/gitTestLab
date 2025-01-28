package com.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Aktiverar Mockito för testet

class BookingSystemTest {

    @Mock
    private TimeProvider timeProvider; // Mock för TimeProvider

    @Mock
    private RoomRepository roomRepository; // Mock för RoomRepository

    @Mock
    private NotificationService notificationService; // Mock för NotificationService

    @InjectMocks
    private BookingSystem bookingSystem; // Skapar en instans av BookingSystem med mockade beroenden

    // implementera egna interface. Göra ett låtsats klass som implementerar
    @Test
    @DisplayName("Book time is not null or before")
    void bookTimeIsNotNullOrBefore() {
        // Mocka aktuell tid
        LocalDateTime currentTime = LocalDateTime.of(2025, 1, 24, 10, 0);
        when(timeProvider.getCurrentTime()).thenReturn(currentTime);

        // Testfall 1: startTime är null
        assertThatThrownBy(() -> bookingSystem.bookRoom("room1", null, currentTime.plusHours(1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Bokning kräver giltiga start- och sluttider samt rum-id");

        // Testfall 2: startTime är i dåtiden
        LocalDateTime pastTime = currentTime.minusHours(1);
        assertThatThrownBy(() -> bookingSystem.bookRoom("room1", pastTime, currentTime.plusHours(1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Kan inte boka tid i dåtid");
    }

    @Test
    @DisplayName("Book end time is not before start time")
    void BookEndTimeIsNotBeforeStartTime() {
        // Mocka aktuell tid
        LocalDateTime currentTime = LocalDateTime.of(2025, 1, 24, 10, 0);
        when(timeProvider.getCurrentTime()).thenReturn(currentTime);
        // Korrekt starttid (i framtiden)
        LocalDateTime validStartTime = currentTime.plusHours(1);
        // Ogiltig sluttid (före starttid)
        LocalDateTime invalidEndTime = validStartTime.minusHours(2);

        assertThatThrownBy(() -> bookingSystem.bookRoom("room1", validStartTime, invalidEndTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Sluttid måste vara efter starttid"); // Korrekt meddelande
    }

    @Test
    @DisplayName("Kastar undantag när rummet inte existerar")
    void bookRoom_ThrowsExceptionWhenRoomDoesNotExist() {
        // Mocka aktuell tid
        LocalDateTime currentTime = LocalDateTime.of(2025, 1, 24, 10, 0);
        when(timeProvider.getCurrentTime()).thenReturn(currentTime);

        // Ogiltigt rum-ID
        String invalidRoomId = "nonExistentRoom";
        // Konfigurera mocken att returnera tomt Optional
        when(roomRepository.findById(invalidRoomId)).thenReturn(Optional.empty());

        // Giltiga tider för testet
        LocalDateTime startTime = currentTime.plusHours(1);
        LocalDateTime endTime = currentTime.plusHours(2);

        // Verifiera att rätt undantag kastas
        assertThatThrownBy(() -> bookingSystem.bookRoom(invalidRoomId, startTime, endTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Rummet existerar inte");
    }

    @Test
    @DisplayName("Returns false if room is not available")
    void bookRoom_WhenRoomIsNotAvailable_ReturnsFalse() {
        LocalDateTime currentTime = LocalDateTime.of(2025, 1, 24, 10, 0);
        when(timeProvider.getCurrentTime()).thenReturn(currentTime);

        Room room = mock(Room.class);
        when(roomRepository.findById("room1")).thenReturn(Optional.of(room));
        when(room.isAvailable(currentTime.plusHours(1), currentTime.plusHours(2))).thenReturn(false);

        LocalDateTime startTime = currentTime.plusHours(1);
        LocalDateTime endTime = currentTime.plusHours(2);

        boolean result = bookingSystem.bookRoom("room1", startTime, endTime);
        assertThat(result).isFalse();
    }








    @Test
    @DisplayName("Booking process with notification success")
    void bookRoom_SuccessfulBookingAndNotification() throws NotificationException {
        LocalDateTime currentTime = LocalDateTime.of(2025, 1, 24, 10, 0);
        when(timeProvider.getCurrentTime()).thenReturn(currentTime);

        Room room = new Room("room1", "room name");
        when(roomRepository.findById("room1")).thenReturn(Optional.of(room));
//        when(room.isAvailable(currentTime.plusHours(1), currentTime.plusHours(2))).thenReturn(true);

        LocalDateTime startTime = currentTime.plusHours(1);
        LocalDateTime endTime = currentTime.plusHours(2);

        Booking booking = new Booking(UUID.randomUUID().toString(), "room1", startTime, endTime);

        // Testa om notifiering skickas
        bookingSystem.bookRoom("room1", startTime, endTime);

        verify(notificationService).sendBookingConfirmation(Mockito.any());
        verify(roomRepository).save(room);

    }

    @Test
    @DisplayName("Booking process with notification failure handled")
    void bookRoom_FailsToSendNotification_HandlesException() throws NotificationException {
        LocalDateTime currentTime = LocalDateTime.of(2025, 1, 24, 10, 0);
        when(timeProvider.getCurrentTime()).thenReturn(currentTime);

        Room room = new Room("room1", "room name");
        when(roomRepository.findById("room1")).thenReturn(Optional.of(room));
//        when(room.isAvailable(currentTime.plusHours(1), currentTime.plusHours(2))).thenReturn(true);

        LocalDateTime startTime = currentTime.plusHours(1);
        LocalDateTime endTime = currentTime.plusHours(2);

        Booking booking = new Booking(UUID.randomUUID().toString(), "room1", startTime, endTime);

        // Simulera att notifiering misslyckas genom att kasta NotificationException
        doThrow(NotificationException.class).when(notificationService).sendBookingConfirmation(Mockito.any());

        // Kontrollera att bokningen inte stoppas av notifieringsfelet
        boolean result = bookingSystem.bookRoom("room1", startTime, endTime);
        assertThat(result).isTrue();

        // Verifiera att notifieringen anropas, trots att ett undantag kastades
        verify(notificationService).sendBookingConfirmation(Mockito.any());
        verify(roomRepository).save(room);
    }

}



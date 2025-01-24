package com.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

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
        LocalDateTime currentTime = LocalDateTime.of(2024, 5, 20, 10, 0);
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

}

package com.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

class ShoppingCartTest {
    ShoppingCart shoppingCart = new ShoppingCart();

    @Test
    void CreatShoppingCartList() {
        ShoppingCart shoppingCart = new ShoppingCart();
        assertThat(shoppingCart.getItemCount()).isEqualTo(0);
    }

    @Test
    void newShoppingCartIsEmpty() {
        assertThat(shoppingCart.getItemCount()).isEqualTo(0);
    }

    @Test
    void addItemToShoppingCart() {
        shoppingCart.addItem("Item1", 1, 10.0);
        assertThat(shoppingCart.getItemCount()).isEqualTo(1);
    }

    @DisplayName("Remove item from shoppingCart")
    @Test
    void testRemoveItem() {
        shoppingCart.addItem("Item1", 1, 10.0);
        shoppingCart.removeItem("Item1");
        assertThat(shoppingCart.getItemCount()).isEqualTo(0);
    }

    @DisplayName("Update Quantity of item")
    @Test
    void UpdateQuantity() {
        fail("Not yet implemented");
    }

    @DisplayName("Calculate total price of items")
    @Test
    void CalculateTotalPrice() {
        fail("Not yet implemented");
    }

    @DisplayName("Apply discount to an item")
    @Test
    void ApplyDiscount() {
        fail("Not yet implemented");
    }

    @DisplayName("Remove non-existing item from shopping cart")
    @Test
    void RemoveNonExistingItem() {
        shoppingCart.removeItem("NonExistingItem");
        assertThat(shoppingCart.getItemCount()).isEqualTo(0);
    }

    @Test
    void getReturnsAddedString() {
        shoppingCart.addItem("Hello", 1, 10.0);
        assertThat(shoppingCart.get()).isEqualTo("Hello");
    }

    @Test
    void getReturnsAnotherAddedString() {
        shoppingCart.addItem("World", 1, 10.0);
        assertThat(shoppingCart.get()).isEqualTo("World");
    }

    @Test
    void addTwoStringsAndReturnFirstUsingIndex() {
        shoppingCart.addItem("Hello", 1, 10.0);
        shoppingCart.addItem("World", 1, 10.0);
        assertThat(shoppingCart.get(0)).isEqualTo("Hello");
        assertThat(shoppingCart.get(1)).isEqualTo("World");
    }
}

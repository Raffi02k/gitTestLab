package com.example;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ShoppingCartTest {

    //Remove
    ShoppingCart shoppingCart = new ShoppingCart();


    //Creat new ShoppingCartList
    @Test
    void CreatShoppingCartList() {
        ShoppingCart shoppingCart = new ShoppingCart();

    }

    //Check Size
    @Test
    void newShoppingCartIsEmpty() {
        assertThat(shoppingCart.size()).isEqualTo(0);
    }

    //Add a String
    @Test
    void addItemToShoppingCart() {
        shoppingCart.add("Item1");
        assertThat(shoppingCart.size()).isEqualTo(1);
    }

    //Remove
    @DisplayName("Remove item from shoppingCart")
    @Test
    void testRemoveItem() {
        fail("Not yet implemented");
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

    @DisplayName("Apply discount to a item")
    @Test
    void ApplyDiscount() {
        fail("Not yet implemented");
    }

    @DisplayName("Remove non existing item from shoppingcart")
    @Test
    void RemoveNonExistingItem() {
        fail("Not yet implemented");
    }


    //Get
    @Test
    void getReturnsAddedString() {
        shoppingCart.add("Hello");
        assertThat(shoppingCart.get()).isEqualTo("Hello");

    }

    @Test
    void getReturnsAnotherAddedString() {
        shoppingCart.add("World");
        assertThat(shoppingCart.get()).isEqualTo("World");

    }

    @Test
    void addTwoStringsAndReturnFirstUsingIndex() {
        shoppingCart.add("Hello");
        shoppingCart.add("World");
        assertThat(shoppingCart.get(0)).isEqualTo("Hello");
        assertThat(shoppingCart.get(1)).isEqualTo("World");

    }

}

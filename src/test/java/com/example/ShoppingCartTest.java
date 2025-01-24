package com.example;


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
    void addStringMakesSizeReturnOne() {
        shoppingCart.add("Item1");
        assertThat(shoppingCart.size()).isEqualTo(1);
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
        shoppingCart.add("World");
        shoppingCart.add("Hello");
        assertThat(shoppingCart.get()).isEqualTo("Hello");
        assertThat(shoppingCart.get()).isEqualTo("World");

    }

}

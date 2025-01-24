package com.example;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ShoppingCartTest {

        //Add a String
        //Remove
        //Get

    //Creat new ShoppingCartList
    @Test
    void CreatShoppingCartList(){
        ShoppingCart shoppingCart = new ShoppingCart();

    }

    //Check Size
    @Test
    void newShoppingCartIsEmpty() {
        ShoppingCart shoppingCart = new ShoppingCart();
        assertThat(shoppingCart.size()).isEqualTo(0);
    }

    @Test
    void addStringMakesSizeReturnOne() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.add("");
        assertThat(shoppingCart.size()).isEqualTo(1);
    }

}

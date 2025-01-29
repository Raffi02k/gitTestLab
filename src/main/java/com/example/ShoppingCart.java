package com.example;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<CartItem> items;

    public ShoppingCart() {
        items = new ArrayList<>();
    }

    public void addItem(String item, int quantity, double price) {
        for (CartItem cartItem : items) {
            if (cartItem.getName().equals(item)) {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                return;
            }
        }
        items.add(new CartItem(item, quantity, price));
    }

    public int getItemCount() {
        return items.size();
    }

    public void removeItem(String item) {
        items.removeIf(cartItem -> cartItem.getName().equals(item));
    }


    public String get(int index) {
        if (index < 0 || index >= items.size()) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        return items.get(index).getName();
    }

    public void updateQuantity(String item, int newQuantity) {
        for (CartItem cartItem : items) {
            if (cartItem.getName().equals(item)) {
                cartItem.setQuantity(cartItem.getQuantity() - newQuantity);
                return;
            }
        }
    }

    public String get() {
        if (items.isEmpty()) {
            throw new IllegalStateException("Shopping cart is empty!");
        }
        return items.get(items.size() - 1).getName();
    }



}

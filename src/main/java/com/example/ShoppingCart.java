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
        throw new IllegalArgumentException("Item not found: " + item);
    }

    public double calculateTotalPrice() {
        return items.stream()
                .mapToDouble(item -> item.getQuantity() * item.getPrice())
                .sum();
    }

    public void applyDiscount(String item, double discountPercentage) {
        for (CartItem cartItem : items) {
            if (cartItem.getName().equals(item)) {
                double discountedPrice = cartItem.getPrice() * (1 - discountPercentage / 100);
                cartItem.setPrice(discountedPrice);
                return;
            }
        }
        throw new IllegalArgumentException("Item not found: " + item);
    }

    public int getQuantity(String item) {
        for (CartItem cartItem : items) {
            if (cartItem.getName().equals(item)) {
                return cartItem.getQuantity();
            }
        }
        return 0;
    }
}




package com.example;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<String> items = new ArrayList<>();

    public int size() {
        return items.size();
    }

    public void add(String s) {
        items.add(s);
    }

    public String get() {
        if (items.isEmpty()) {
            throw new IllegalStateException("Shopping cart is empty!");
        }
        return items.get(items.size() - 1); // Returnerar senaste tillagda objektet
    }

    public String get(int index) {
        if (index < 0 || index >= items.size()) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        return items.get(index); // Returnerar objekt på angivet index
    }
}

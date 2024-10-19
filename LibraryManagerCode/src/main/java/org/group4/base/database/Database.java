package org.group4.base.database;

import java.util.ArrayList;
import java.util.List;

public abstract class Database<T> {
    protected final List<T> items = new ArrayList<>();

    public List<T> getItems() {
        return items;
    }

    public void addItem(T item) {
        items.add(item);
    }

    public void removeItem(T item) {
        items.remove(item);
    }
}
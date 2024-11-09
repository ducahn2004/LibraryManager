package org.group4.database;

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

    public boolean removeItem(T item) {
        items.remove(item);
        return false;
    }

    public boolean updateItem(T item) {
        for (T i : items) {
            if (i.equals(item)) {
                items.remove(i);
                items.add(item);
                break;
            }
        }
        return false;
    }
}
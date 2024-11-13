package org.group4.dao;

import java.util.List;

public interface GenericDAO<T> {
    void add(T item);
    void remove(T item);
    void update(T item);
    T get(T item);
    List<T> getAll();
}

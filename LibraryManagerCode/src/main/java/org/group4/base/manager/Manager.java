package org.group4.base.manager;

public interface Manager<T> {
  boolean add(T item);
  boolean remove(T item);
  boolean update(T item);
}

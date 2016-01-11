package org.team10424102.whisky.components.auth;

import java.util.Iterator;

public interface LazySetAdapter<T> {

    Iterator<T> getIterator();

    boolean addItem(T item);

    boolean removeItem(T item);

    boolean contains(T item);

    boolean removeAll();

}

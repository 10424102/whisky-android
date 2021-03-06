package org.team10424102.whisky.models;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import rx.Observable;

public class LazyList<T> implements List<T> {

    private Observable<T> observable;
    private int size;

    public LazyList(int count) {
        size = count;
    }

    public int size() {
        return size;
    }


    /////////////


    @Override
    public void add(int location, T object) {

    }

    @Override
    public boolean add(T object) {
        return false;
    }

    @Override
    public boolean addAll(int location, Collection<? extends T> collection) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean contains(Object object) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return false;
    }

    @Override
    public T get(int location) {
        return null;
    }

    @Override
    public int indexOf(Object object) {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @NonNull
    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public int lastIndexOf(Object object) {
        return 0;
    }

    @Override
    public ListIterator<T> listIterator() {
        return null;
    }

    @NonNull
    @Override
    public ListIterator<T> listIterator(int location) {
        return null;
    }

    @Override
    public T remove(int location) {
        return null;
    }

    @Override
    public boolean remove(Object object) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return false;
    }

    @Override
    public T set(int location, T object) {
        return null;
    }

    @NonNull
    @Override
    public List<T> subList(int start, int end) {
        return null;
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @NonNull
    @Override
    public <T1> T1[] toArray(T1[] array) {
        return null;
    }
}

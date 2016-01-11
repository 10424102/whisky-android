package org.team10424102.whisky.components.auth;

import android.support.annotation.NonNull;

import org.team10424102.whisky.models.LazyImage;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class LazySet<E> implements Set<E> {

    private LazySetAdapter<E> mAdapter;
    private int mCount;
    private int mCacheSize;
    private Object[] mCache;
    private int index;

    public LazySet(int count) {
        this(count, 256);
    }

    public LazySet(int count, int cacheSize) {
        mCount = count;
        mCacheSize = cacheSize < count ? cacheSize : count;
        mCache = new Object[mCacheSize];
    }

    @Override
    public boolean add(E object) {
        return mAdapter.addItem(object);
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends E> collection) {
        boolean modified = false;
        for (E e : collection) {
            if (add(e)) modified = true;
        }
        return modified;
    }

    @Override
    public void clear() {
        mAdapter.removeAll();
    }

    @Override
    public boolean contains(@NonNull Object object) {
        return mAdapter.contains((E) object);
    }

    @Override
    public boolean containsAll(@NonNull Collection<?> collection) {
        return false;
    }

    @Override
    public boolean isEmpty() {
        if (mCount == 0) return true;
        return false;
    }

    @NonNull
    @Override
    public Iterator<E> iterator() {
        return mAdapter.getIterator();
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
    public int size() {
        return mCount;
    }

    @NonNull
    @Override
    public Object[] toArray() {

        return new Object[0];
    }

    @NonNull
    @Override
    public <T> T[] toArray(T[] array) {
        return null;
    }
}

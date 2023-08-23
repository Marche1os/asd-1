package org.example;

import java.lang.reflect.Array;

public class ImmutableStack<T extends Comparable<T>> {
    private final T[] data;
    private int lastIndexPointer;
    private Class<T> clazz;

    private final int INIT_CAPACITY = 0;

    public ImmutableStack(Class<T> clazz) {
        this.clazz = clazz;
        data = (T[]) Array.newInstance(clazz, INIT_CAPACITY);
    }

    public ImmutableStack(final T[] array, Class<T> clazz) {
        this.data = array;
        this.clazz = clazz;
    }

    public ImmutableStack<T> push(final T el) {
        final T[] copy = (T[]) Array.newInstance(clazz, data.length + 1);
        System.arraycopy(data, 0, copy, 0, copy.length - 1);
        copy[data.length] = el;

        return new ImmutableStack<>(copy, clazz);
    }

    public ImmutableStack<T> pop() throws NullPointerException {
        return null;
    }

    public boolean isEmpty() {
        return data.length == 0;
    }

    public int getCount() {
        return data.length;
    }

    public T[] getData() {
        return data;
    }
}

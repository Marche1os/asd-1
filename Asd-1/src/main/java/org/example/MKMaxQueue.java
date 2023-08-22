package org.example;

import java.util.Deque;
import java.util.LinkedList;

public class MKMaxQueue<T extends Comparable<T>> {
    private final Deque<T> data;
    private final Deque<T> maxValues;

    public MKMaxQueue() {
        data = new LinkedList<>();
        maxValues = new LinkedList<>();
    }

    public void enqueue(final T value) {
        data.addLast(value);

        while (!maxValues.isEmpty() && value.compareTo(maxValues.peekLast()) > 0) {
            maxValues.removeFirst();
        }

        maxValues.addLast(value);
    }

    public T dequeue() {
        if (data.isEmpty()) {
            return null;
        }

        final T value = data.removeFirst();
        if (value == maxValues.peekFirst()) {
            maxValues.removeFirst();
        }

        return value;
    }

    public T getMax() {
        if (maxValues.isEmpty()) {
            return null;
        }

        return maxValues.peekFirst();
    }
}

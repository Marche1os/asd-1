package org.example;

import tools.VisibleForTestOnly;

import java.util.Deque;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class ImmutableQueue<T> {
    private Deque<T> data;

    public ImmutableQueue() {
        data = new LinkedList<>();
    }

    public ImmutableQueue(LinkedList<T> elements) {
        this.data = elements;
    }

    public ImmutableQueue<T> enqueue(final T element) {
        final LinkedList<T> copy = new LinkedList<>(data);
        copy.add(element);
        return new ImmutableQueue<>(copy);
    }

    public ImmutableQueue<T> dequeue() {
        if (data.isEmpty()) {
            throw new NoSuchElementException();
        }

        final LinkedList<T> copy = new LinkedList<>(data);
        copy.removeFirst();
        return new ImmutableQueue<>(copy);
    }

    @VisibleForTestOnly
    public Deque<T> getData() {
        return data;
    }
}

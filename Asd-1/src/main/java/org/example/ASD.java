package org.example;

import tools.VisibleForTestOnly;

import java.lang.reflect.Array;
import java.util.Deque;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class ASD {
    /*
    1. Стек с максимальным значением
     */
    public class MKStack<T extends Comparable<T>> {
        private final T[] data;
        private final T[] maxValuesStack; //contains current max value.
        private final Class<T> clazz;
        private int lastIndexPointer;
        private int count;

        private static final int DEFAULT_CAPACITY = 12;

        public MKStack(Class<T> clazz) {
            this(clazz, DEFAULT_CAPACITY);
        }

        public MKStack(Class<T> clazz, int size) {
            data = (T[]) Array.newInstance(clazz, size);
            maxValuesStack = (T[]) Array.newInstance(clazz, size);
            lastIndexPointer = 0;
            count = 0;
            this.clazz = clazz;
        }

        public void push(final T el) {
            if (count == 0) {
                pushOnEmptyStack(el);
                return;
            }

            data[lastIndexPointer] = el;
            final T currentMaxValue = maxValuesStack[lastIndexPointer - 1];
            if (currentMaxValue.compareTo(el) > 0) {
                maxValuesStack[lastIndexPointer] = currentMaxValue;
            } else {
                maxValuesStack[lastIndexPointer] = el;
            }

            lastIndexPointer++;
            count++;
        }

        private void pushOnEmptyStack(final T el) {
            data[lastIndexPointer] = el;
            maxValuesStack[lastIndexPointer] = el;
            lastIndexPointer++;
            count++;
        }

        public T pop() throws NullPointerException {
            if (lastIndexPointer == 0)
                throw new NullPointerException("Stack is empty");

            maxValuesStack[lastIndexPointer] = null;
            data[lastIndexPointer] = null;

            lastIndexPointer--;
            return data[lastIndexPointer];
        }

        public boolean isEmpty() {
            return false;
        }

        public int getCount() {
            return 1;
        }

        @VisibleForTestOnly
        public T[] getMaxValuesStack() {
            return maxValuesStack;
        }


        public T[] getData() {
            return data;
        }
    }


    /**
     * 2. Очередь с максимальным значением
     */
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

    /**
     * 3. Иммутабельный стек
     */

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

        public org.example.ImmutableStack<T> push(final T el) {
            final T[] copy = (T[]) Array.newInstance(clazz, data.length + 1);
            System.arraycopy(data, 0, copy, 0, copy.length - 1);
            copy[data.length] = el;

            return new org.example.ImmutableStack<>(copy, clazz);
        }

        public org.example.ImmutableStack<T> pop() throws NullPointerException {
            final T[] copy = (T[]) Array.newInstance(clazz, data.length - 1);
            System.arraycopy(data, 0, copy, 0, copy.length);

            return new org.example.ImmutableStack<>(copy, clazz);
        }

        public boolean isEmpty() {
            return data.length == 0;
        }

        public int getCount() {
            return data.length;
        }

        @VisibleForTestOnly
        public T[] getData() {
            return data;
        }
    }

    /**
     * 4. иммутабельная очередь
     */

    public class ImmutableQueue<T> {
        private Deque<T> data;

        public ImmutableQueue() {
            data = new LinkedList<>();
        }

        public ImmutableQueue(LinkedList<T> elements) {
            this.data = elements;
        }

        public org.example.ImmutableQueue<T> enqueue(final T element) {
            final LinkedList<T> copy = new LinkedList<>(data);
            copy.add(element);
            return new org.example.ImmutableQueue<>(copy);
        }

        public org.example.ImmutableQueue<T> dequeue() {
            if (data.isEmpty()) {
                throw new NoSuchElementException();
            }

            final LinkedList<T> copy = new LinkedList<>(data);
            copy.removeFirst();
            return new org.example.ImmutableQueue<>(copy);
        }

        @VisibleForTestOnly
        public Deque<T> getData() {
            return data;
        }
    }
}

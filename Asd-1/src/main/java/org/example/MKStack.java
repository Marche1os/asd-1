package org.example;

import tools.VisibleForTestOnly;

import java.lang.reflect.Array;

/**
 * Динамически расширяемый Стек.
 * Особенность: извлечение максимального значения за O(1)
 * <p>
 * Принцип работы: при добавлении смотрим текущий элемент вспомогательного стека:
 * - если в стеке элемент больший, то добавляем еще раз это значение в вспомогательный стек
 * - иначе - добавляем новое значение в стек
 */
@SuppressWarnings("unchecked")
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

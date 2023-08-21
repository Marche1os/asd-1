package org.example;

public class MKQueue<T extends Comparable<T>> {
    private Node<T> head;
    private Node<T> tail;


    private int count;

    public MKQueue() {
        head = null;
        tail = null;
        count = 0;
    }

    public void addToHead(final Node value) {
        head = value;
        if (head.next == null) {
            tail = head;
            head.next = tail;
        }

        count++;
    }

    public void addToTail(final Node value) {

    }

    public void addAfter(final Node before, final Node after) {

    }

    public Node<T> getHead() {
        return head;
    }

    public Node<T> getTail() {
        return tail;
    }

    public int getCount() {
        return count;
    }

    public static class Node<T> {
        public T value;
        public Node prev;
        public Node next;

        public Node(T value) {
            this.value = value;
            prev = null;
            next = null;
        }
    }
}

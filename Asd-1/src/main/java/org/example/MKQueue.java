package org.example;

@SuppressWarnings("unchecked")
public class MKQueue<T extends Comparable<T>> {
    private Node head;
    private Node<T> tail;


    private int count;

    public MKQueue() {
        head = null;
        tail = null;
        count = 0;
    }

    public void addToHead(final Node value) {
        if (head == null) {
            head = value;
            tail = head;
            head.next = tail;
            count++;
            return;
        }

        value.next = head;
        head.prev = value;
        head.next = head.next.next;
        head = value;

        count++;
    }

    public void addToTail(final Node value) {
        if (tail == null) {
            addToHead(value);
            return;
        }

        value.prev = tail;
        value.next = null;
        tail.next = value;
        tail = value;

        count++;
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

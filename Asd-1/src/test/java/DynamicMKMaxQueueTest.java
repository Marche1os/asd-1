import org.example.MKQueue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DynamicMKMaxQueueTest {

    @Test
    void emptyAddToHeadTest() {
        MKQueue<Integer> queue = new MKQueue<>();
        MKQueue.Node<Integer> node = new MKQueue.Node<>(2);

        queue.addToHead(node);

        assertEquals(2, queue.getHead().value);
        assertEquals(2, queue.getTail().value);

        assertNotNull(queue.getHead().next);
        assertNotNull(queue.getTail());
        assertEquals(queue.getHead().next, queue.getTail());
    }

    @Test
    void addToHeadTest() {
        MKQueue<Integer> queue = new MKQueue<>();
        MKQueue.Node<Integer> firstValue = new MKQueue.Node<>(10);
        MKQueue.Node<Integer> secondValue = new MKQueue.Node<>(15);
        MKQueue.Node<Integer> thirdValue = new MKQueue.Node<>(20);

        queue.addToHead(firstValue);
        queue.addToHead(secondValue);
        queue.addToHead(thirdValue);

        assertEquals(20, queue.getHead().value);
        assertEquals(10, queue.getTail().value);
        assertEquals(15, queue.getHead().next.value);
        assertEquals(15, queue.getTail().prev.value);

        assertSame(queue.getHead(), queue.getHead().next.prev);
        assertSame(queue.getTail(), queue.getTail().prev.next);
    }

    @Test
    void addToTailTest() {
        MKQueue<Integer> queue = new MKQueue<>();
        MKQueue.Node<Integer> firstValue = new MKQueue.Node<>(10);
        MKQueue.Node<Integer> secondValue = new MKQueue.Node<>(15);
        MKQueue.Node<Integer> thirdValue = new MKQueue.Node<>(20);

        queue.addToTail(firstValue);

        assertSame(queue.getHead(), queue.getTail());
        assertEquals(10, queue.getTail().value);

        queue.addToTail(secondValue);

        assertEquals(15, queue.getTail().value);
        assertEquals(10, queue.getTail().prev.value);
        assertSame(queue.getHead(), queue.getTail().prev);

        queue.addToTail(thirdValue);

        assertEquals(10, queue.getHead().value);
        assertEquals(15, queue.getHead().next.value);
        assertEquals(20, queue.getHead().next.next.value);

        assertEquals(20, queue.getTail().value);
        assertEquals(15, queue.getTail().prev.value);
        assertEquals(10, queue.getTail().prev.prev.value);
    }
}

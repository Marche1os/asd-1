import org.example.MKQueue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DynamicMaxQueueTest {

    @Test
    void addToHeadTest() {
        MKQueue<Integer> queue = new MKQueue<>();
        MKQueue.Node<Integer> node = new MKQueue.Node<>(2);

        queue.addToHead(node);

        assertEquals(2, queue.getHead().value);
        assertEquals(2, queue.getTail().value);

        assertNotNull(queue.getHead().next);
        assertNotNull(queue.getTail());
        assertEquals(queue.getHead().next, queue.getTail());
    }
}

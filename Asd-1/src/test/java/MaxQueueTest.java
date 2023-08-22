import org.example.MKMaxQueue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaxQueueTest {

    @Test
    void enqueueTest() {
        final MKMaxQueue<Integer> queue = new MKMaxQueue<>();

        queue.enqueue(10);
        queue.enqueue(15);
        queue.enqueue(13);

        assertEquals(15, queue.getMax());
        assertEquals(10, queue.dequeue());

        assertEquals(15, queue.getMax());
        assertEquals(15, queue.dequeue());

        assertEquals(13, queue.getMax());
    }
}

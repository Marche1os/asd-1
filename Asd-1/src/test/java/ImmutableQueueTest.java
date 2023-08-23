import org.example.ImmutableQueue;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ImmutableQueueTest {

    @Test
    void pushTest() {
        final ImmutableQueue<Integer> emptyQueue = new ImmutableQueue<>();

        final ImmutableQueue<Integer> twoElementsQueue = emptyQueue.enqueue(10).enqueue(20);

        assertEquals(0, emptyQueue.getData().size());
        assertEquals(2, twoElementsQueue.getData().size());

        assertEquals(10, twoElementsQueue.getData().peekFirst());
        assertEquals(20, twoElementsQueue.getData().peekLast());

        final ImmutableQueue<Integer> singleElementQueue = twoElementsQueue.dequeue();

        assertEquals(1, singleElementQueue.getData().size());

        final ImmutableQueue<Integer> removedFromSingle = singleElementQueue.dequeue();
        assertThrows(NoSuchElementException.class, removedFromSingle::dequeue);
    }
}

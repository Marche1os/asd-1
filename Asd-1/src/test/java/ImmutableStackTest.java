import org.example.ImmutableStack;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ImmutableStackTest {

    @Test
    void pushTest() {
        final ImmutableStack<Integer> emptyStack = new ImmutableStack<>(Integer.class);
        final ImmutableStack<Integer> notEmptyStack = emptyStack.push(10);

        assertEquals(1, notEmptyStack.getCount());
        assertEquals(0, emptyStack.getCount());

        assertEquals(10, notEmptyStack.getData()[0]);
        assertNotSame(emptyStack, notEmptyStack);
    }

    @Test
    void popTest() {
        final ImmutableStack<Integer> emptyStack = new ImmutableStack<>(Integer.class);
        final ImmutableStack<Integer> twoElementsStack = emptyStack.push(10).push(20);

        assertEquals(2, twoElementsStack.getCount());
        assertEquals(0, emptyStack.getCount());
        assertTrue(emptyStack.isEmpty());
        assertEquals(20, twoElementsStack.getData()[1]);

        final ImmutableStack<Integer> singleElementStack = twoElementsStack.pop();

        assertEquals(2, twoElementsStack.getCount());
        assertEquals(20, twoElementsStack.getData()[1]);
        assertEquals(10, singleElementStack.getData()[0]);
        assertEquals(1, singleElementStack.getCount());

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            Integer datum = singleElementStack.getData()[1];
        });
    }
}

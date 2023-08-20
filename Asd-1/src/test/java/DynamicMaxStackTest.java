import org.example.MKStack;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DynamicMaxStackTest {

    @Test
    @DisplayName("Проверка корректности поведения вспомогательного стека при операциях CRUD")
    void crudOperationsTest() {
        MKStack<Integer> stack = new MKStack<>(Integer.class);
        stack.push(10);

        Integer[] maxValues = stack.getMaxValuesStack();
        Integer[] data = stack.getData();

        assertEquals(10, maxValues[0]);
        assertEquals(10, data[0]);

        stack.push(9);

        assertNotNull(maxValues[0]);
        assertEquals(10, maxValues[0]);

        assertEquals(10, maxValues[1]);
        assertEquals(9, data[1]);

        stack.push(11);

        assertEquals(10, maxValues[1]);
        assertEquals(11, data[2]);
    }
}

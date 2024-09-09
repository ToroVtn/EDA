import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IndexWithDuplicatesTest {
    IndexService<Integer> myIndex = new IndexGeneric<>(Integer.class);
    @BeforeEach
    void initialize() {
        myIndex.initialize(new Integer[] {100, 50, 30, 50, 80, 100, 100, 30});
        myIndex.sortedPrint();
        assertArrayEquals(new Integer[]{30, 30, 50, 50, 80, 100, 100, 100}, myIndex.getArray());
    }

    @Test
    void testPPT() {
        Integer[] rta;
        assertAll(()-> assertArrayEquals(new Integer[] {80}, myIndex.range(50, 100,
                false, false)),
                ()->assertArrayEquals(new Integer[]{30, 30}, myIndex.range(30, 50,
                        true, false)),
                ()->assertArrayEquals(new Integer[]{50, 50, 80}, myIndex.range(45, 100,
                        false, false)),
                ()->assertArrayEquals(new Integer[]{50, 50, 80}, myIndex.range(45, 100,
                        true, false)),
                ()->assertArrayEquals(new Integer[]{30, 30}, myIndex.range(10, 50,
                        false, false)),
                ()->assertArrayEquals(new Integer[]{}, myIndex.range(10, 20,
                        false, false)));
    }
}
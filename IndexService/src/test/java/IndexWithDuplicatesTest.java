import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IndexWithDuplicatesTest {
    IndexWithDuplicates myIndex = new IndexWithDuplicates();
    @BeforeEach
    void initialize() {
        myIndex.initialize(new int[] {100, 50, 30, 50, 80, 100, 100, 30});
        myIndex.sortedPrint();
        assertArrayEquals(new int[]{30, 30, 50, 50, 80, 100, 100, 100}, myIndex.getArray());
    }

    @Test
    void testPPT() {
        int[] rta;
        assertAll(()-> assertArrayEquals(new int[] {80}, myIndex.range(50, 100,
                false, false)),
                ()->assertArrayEquals(new int[]{30, 30}, myIndex.range(30, 50,
                        true, false)),
                ()->assertArrayEquals(new int[]{50, 50, 80}, myIndex.range(45, 100,
                        false, false)),
                ()->assertArrayEquals(new int[]{50, 50, 80}, myIndex.range(45, 100,
                        true, false)),
                ()->assertArrayEquals(new int[]{30, 30}, myIndex.range(10, 50,
                        false, false)),
                ()->assertArrayEquals(new int[]{}, myIndex.range(10, 20,
                        false, false)));
    }
}
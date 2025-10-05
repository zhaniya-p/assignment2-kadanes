package algorithms;

import metrics.PerformanceTracker;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class KadaneAlgorithmTest {

    @Test
    public void testEmptyArray() {
        int[] a = new int[] {};
        KadaneAlgorithm.Result r = KadaneAlgorithm.kadane(a, new PerformanceTracker());
        assertEquals(0, r.maxSum);
        assertEquals(-1, r.start);
        assertEquals(-1, r.end);
    }

    @Test
    public void testSinglePositive() {
        int[] a = new int[] {5};
        KadaneAlgorithm.Result r = KadaneAlgorithm.kadane(a, new PerformanceTracker());
        assertEquals(5, r.maxSum);
        assertEquals(0, r.start);
        assertEquals(0, r.end);
    }

    @Test
    public void testAllNegative() {
        int[] a = new int[] {-3, -1, -7, -2};
        KadaneAlgorithm.Result r = KadaneAlgorithm.kadane(a, new PerformanceTracker());

        assertEquals(-1, r.maxSum);
    }

    @Test
    public void testMixed() {
        int[] a = new int[] { -2, 1, -3, 4, -1, 2, 1, -5, 4 };
        KadaneAlgorithm.Result r = KadaneAlgorithm.kadane(a, new PerformanceTracker());
        assertEquals(6, r.maxSum); 
        assertEquals(3, r.start);
        assertEquals(6, r.end);
    }
}

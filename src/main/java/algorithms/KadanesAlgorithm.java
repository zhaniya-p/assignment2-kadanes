package algorithms;

import metrics.PerformanceTracker;
import java.util.Objects;

/**
 * Kadane's Algorithm: maximum subarray sum with position tracking.
 * Returns a Result containing maxSum, start index, end index (inclusive).
 */
public class KadanesAlgorithm {

    public static class Result {
        public final long maxSum;
        public final int start;
        public final int end;

        public Result(long maxSum, int start, int end) {
            this.maxSum = maxSum;
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            return String.format("Result(maxSum=%d, start=%d, end=%d)", maxSum, start, end);
        }
    }

    /**
     * Runs Kadane's algorithm on the provided array.
     * Tracks basic metrics via PerformanceTracker (comparisons, assignments, accesses).
     *
     * @param arr the input array (must not be null)
     * @param tracker PerformanceTracker instance (may be null)
     * @return Result with maximum subarray sum and indices (start..end inclusive). If arr is empty, returns (0,-1,-1).
     */
    public static Result kadane(int[] arr, PerformanceTracker tracker) {
        Objects.requireNonNull(arr, "Input array cannot be null");
        if (tracker == null) {
            tracker = new PerformanceTracker(); // no-op tracker if not provided
        }

        tracker.incrementArrayAccesses(); // read length (conceptual)
        final int n = arr.length;
        if (n == 0) {
            return new Result(0, -1, -1);
        }

        long maxSoFar = Long.MIN_VALUE;
        long maxEndingHere = 0;
        int start = 0;
        int s = 0;
        int end = 0;

        tracker.incrementAssignments(4); // approximate assigns above

        for (int i = 0; i < n; i++) {
            tracker.incrementComparisons(); // loop comparison i < n
            tracker.incrementArrayAccesses(); // access arr[i]
            int value = arr[i];
            tracker.incrementAssignments();

            // maxEndingHere = max(value, maxEndingHere + value)
            tracker.incrementComparisons();
            if (maxEndingHere + value < value) {
                tracker.incrementAssignments();
                maxEndingHere = value;
                s = i;
                tracker.incrementAssignments();
            } else {
                tracker.incrementAssignments();
                maxEndingHere = maxEndingHere + value;
                tracker.incrementAssignments();
            }

            // update maxSoFar
            tracker.incrementComparisons();
            if (maxSoFar < maxEndingHere) {
                tracker.incrementAssignments();
                maxSoFar = maxEndingHere;
                start = s;
                end = i;
                tracker.incrementAssignments(2);
            }
        }
        // final loop comparison failure counted
        tracker.incrementComparisons();

        return new Result(maxSoFar, start, end);
    }
}

package algorithms;

import metrics.PerformanceTracker;
import java.util.Objects;

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

    public static Result kadane(int[] arr, PerformanceTracker tracker) {
        Objects.requireNonNull(arr, "Input array cannot be null");
        if (tracker == null) {
            tracker = new PerformanceTracker(); 
        }

        tracker.incrementArrayAccesses(); 
        final int n = arr.length;
        if (n == 0) {
            return new Result(0, -1, -1);
        }

        long maxSoFar = Long.MIN_VALUE;
        long maxEndingHere = 0;
        int start = 0;
        int s = 0;
        int end = 0;

        tracker.incrementAssignments(4); 

        for (int i = 0; i < n; i++) {
            tracker.incrementComparisons(); 
            tracker.incrementArrayAccesses(); 
            int value = arr[i];
            tracker.incrementAssignments();

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

            tracker.incrementComparisons();
            if (maxSoFar < maxEndingHere) {
                tracker.incrementAssignments();
                maxSoFar = maxEndingHere;
                start = s;
                end = i;
                tracker.incrementAssignments(2);
            }
        }

        tracker.incrementComparisons();

        return new Result(maxSoFar, start, end);
    }
}

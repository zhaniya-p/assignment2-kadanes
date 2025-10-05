package metrics;

/**
 * Simple performance tracker to count comparisons, assignments, array accesses.
 * This is intentionally lightweight and serializable to CSV-friendly lines.
 */
public class PerformanceTracker {
    private long comparisons = 0;
    private long assignments = 0;
    private long arrayAccesses = 0;

    public void incrementComparisons() { comparisons++; }
    public void incrementComparisons(long n) { comparisons += n; }
    public void incrementAssignments() { assignments++; }
    public void incrementAssignments(long n) { assignments += n; }
    public void incrementArrayAccesses() { arrayAccesses++; }
    public void incrementArrayAccesses(long n) { arrayAccesses += n; }

    public long getComparisons() { return comparisons; }
    public long getAssignments() { return assignments; }
    public long getArrayAccesses() { return arrayAccesses; }

    public void reset() { comparisons = 0; assignments = 0; arrayAccesses = 0; }

    @Override
    public String toString() {
        return String.format("comparisons=%d, assignments=%d, arrayAccesses=%d",
                comparisons, assignments, arrayAccesses);
    }

    public String toCsvLine() {
        return String.format("%d,%d,%d", comparisons, assignments, arrayAccesses);
    }
}

package cli;

import algorithms.KadanesAlgorithm;
import algorithms.KadanesAlgorithm.Result;
import metrics.PerformanceTracker;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

/**
 * CLI benchmark runner for Kadane's algorithm.
 * Generates arrays of various sizes and distributions and measures time and metrics.
 *
 * Usage: java -cp target/yourjar.jar cli.BenchmarkRunner
 *
 * This class prints CSV lines to stdout and also writes results to docs/benchmarks.csv
 */
public class BenchmarkRunner {

    private static int[] randomArray(int n, int bound, Random rnd) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = rnd.nextInt(bound * 2 + 1) - bound;
        return a;
    }

    private static int[] sortedArray(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = i;
        return a;
    }

    private static int[] reverseSortedArray(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = n - i;
        return a;
    }

    private static int[] nearlySortedArray(int n, Random rnd) {
        int[] a = sortedArray(n);
        // introduce few swaps
        int swaps = Math.max(1, n/100); // 1% shuffled
        for (int i = 0; i < swaps; i++) {
            int x = rnd.nextInt(n);
            int y = rnd.nextInt(n);
            int tmp = a[x]; a[x] = a[y]; a[y] = tmp;
        }
        return a;
    }

    public static void main(String[] args) throws IOException {
        int[] sizes = new int[] {100, 1000, 10000, 100000};
        Random rnd = new Random(42);
        String outPath = "docs/performance_plots.csv";
        try (FileWriter fw = new FileWriter(outPath)) {
            fw.write("size,distribution,time_ns,comparisons,assignments,arrayAccesses,subarrayStart,subarrayEnd,maxSum\n");
            for (int size : sizes) {
                int[][] datasets = new int[][] {
                        randomArray(size, Math.max(10, size/10), rnd),
                        sortedArray(size),
                        reverseSortedArray(size),
                        nearlySortedArray(size, rnd)
                };
                String[] names = new String[] {"random","sorted","reverse","nearly-sorted"};
                for (int di = 0; di < datasets.length; di++) {
                    int[] data = datasets[di];
                    // run a few trials and take median
                    long[] times = new long[5];
                    long[] comps = new long[5];
                    long[] assigns = new long[5];
                    long[] accesses = new long[5];
                    int trialCount = times.length;
                    for (int t = 0; t < trialCount; t++) {
                        PerformanceTracker tracker = new PerformanceTracker();
                        int[] copy = Arrays.copyOf(data, data.length);
                        long start = System.nanoTime();
                        Result r = KadanesAlgorithm.kadane(copy, tracker);
                        long end = System.nanoTime();
                        times[t] = end - start;
                        comps[t] = tracker.getComparisons();
                        assigns[t] = tracker.getAssignments();
                        accesses[t] = tracker.getArrayAccesses();
                        // write each trial as a CSV line
                        fw.write(String.format("%d,%s,%d,%d,%d,%d,%d,%d,%d\n",
                                size, names[di], times[t], comps[t], assigns[t], accesses[t],
                                r.start, r.end, r.maxSum));
                        fw.flush();
                    }
                    System.out.println("Completed size=" + size + " dist=" + names[di]);
                }
            }
            System.out.println("Benchmark results written to " + outPath);
        }
    }
}

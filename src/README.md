## Kadane’s Algorithm Benchmark (Assignment 2 – Pair 3)

## Overview
This project implements and benchmarks **Kadane’s Algorithm** for the *Maximum Subarray Sum Problem* as part of the Design and Analysis of Algorithms course (Assignment 2, Pair 3).

The implementation was completed by **Student B**, with **Student A** analyzing the algorithm’s performance and complexity.

The benchmark compares execution time across multiple input distributions and sizes to validate the expected **O(n)** linear runtime.

---

## Algorithms
### Kadane’s Algorithm (Implemented)
Kadane’s Algorithm finds the contiguous subarray with the largest sum using dynamic programming.
It maintains two variables:
- `maxEndingHere = max(A[i], maxEndingHere + A[i])`
- `maxSoFar = max(maxSoFar, maxEndingHere)`

This allows detection of the maximum subarray sum in **linear time O(n)** using only **constant memory O(1)**.

### Boyer–Moore Majority Vote (Partner’s Algorithm)
Used for comparative analysis.
It identifies the majority element (> n/2 occurrences) in a single pass using candidate counting.
Both Kadane’s and Boyer–Moore achieve O(n) performance but solve different optimization problems.

---

## Benchmark Description
The benchmarking framework tests the algorithm on various **input distributions**:
- Random
- Sorted
- Reverse-sorted
- Nearly-sorted

Each test runs for multiple input sizes:  
**100, 1 000, 10 000, and 100 000 elements.**

Metrics collected include:
- Execution time (ns)
- Number of comparisons
- Number of assignments
- Array accesses


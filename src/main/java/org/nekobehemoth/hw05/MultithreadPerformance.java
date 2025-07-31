package org.nekobehemoth.hw05;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public class MultithreadPerformance {
    private static final int arraySize = 100_000_000;
    private static final short[] values = new short[arraySize];
    private static final int[] threadCount = {1, 10, 100, 1000};
    private final static String parallelStreamImpl = "Parallel Stream";
    private final static String parallelThreadImpl = "Parallel Thread";
    private static final Map<Integer, Map<String, Double>> testResults = new TreeMap<>();

    public static void main(String[] args) throws InterruptedException, IOException {
        Random rand = new Random(10);
        long sumToCheck = 0;
        for (int i = 0; i < values.length; i++) {
            values[i] = (short) (rand.nextInt(2 * 32768) - 32768);
            sumToCheck += values[i];
        }

        for (int i = 0; i < threadCount.length; i++) {
            int finalI = i;
            double parallelStreamTimeExecution = execTime(() -> sumWithParallelStream(threadCount[finalI]));
            double parallelThreadTimeExecution = execTime(() -> sumWithParallelThreads(threadCount[finalI]));
            testResults.put(threadCount[finalI],
                    Map.of(parallelStreamImpl, parallelStreamTimeExecution,
                           parallelThreadImpl, parallelThreadTimeExecution));
        }

        writeTestResultToFile(testResults);
    }

    static double execTime(Runnable runnable) {
        long statTime = System.nanoTime();
        runnable.run();
        long endTime = System.nanoTime();
        return (endTime - statTime) / 1_000_000_000.0;
    }


    static long sumWithParallelStream(int threadsCount) {
        IntStream intStream = IntStream.range(0, values.length).map(i -> values[i]);
        ForkJoinPool pool = new ForkJoinPool(threadsCount);

        return (long) pool.submit(() -> intStream.parallel().sum()).join();
    }

    static long sumWithParallelThreads(int threadsCount) {
        int batchSize = values.length / threadsCount;
        List<short[]> batches = batchArray(values, batchSize);
        AtomicLong resultSum = new AtomicLong();
        List<Thread> threads = new ArrayList<>();
        for (short[] batch : batches) {
            Thread thread =
            new Thread(() -> {
               resultSum.addAndGet(sumElements(batch));
            });
            thread.start();
            threads.add(thread);
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return resultSum.get();
    }

    private static long sumElements(short[] array) {
        long sum = 0;
        for (short value : array) {
            sum += value;
        }
        return sum;
    }

    private static List<short[]> batchArray(short[] array, int batchSize) {
        List<short[]> batches = new ArrayList<>();
        for (int i = 0; i < array.length; i+=batchSize) {
            int end = Math.min(i + batchSize, array.length);
            short[] batch = Arrays.copyOfRange(array, i, end);
            batches.add(batch);
        }
        return batches;
    }


    public static void writeTestResultToFile(Map<Integer, Map<String, Double>> testResults) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("TestResult.txt"))) {
            for (Map.Entry<Integer, Map<String, Double>> entry : testResults.entrySet()) {
                Integer threadCount = entry.getKey();
                Map<String, Double> result = entry.getValue();
                writer.append(String.format("Number of threads: %d\n", threadCount));
                for(Map.Entry<String, Double> implResult : result.entrySet()){
                    String implName = implResult.getKey();
                    Double duration = implResult.getValue();
                    writer.append(String.format("Implementation: \"%s\", duration: %.2f\n", implName, duration));
                }
            }
        }
    }
}

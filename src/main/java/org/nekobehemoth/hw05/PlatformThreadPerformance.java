package org.nekobehemoth.hw05;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class PlatformThreadPerformance {

    private static int threadAmount = 8000;

    static Callable<Integer> sleepTask = () -> {
        try {
            Thread.sleep(200);
            return 0;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    };

    @SneakyThrows
    public static void main(String[] args) {
        performanceTest(PlatformThreadPerformance::createManyVirtualThread, "VirtualThreads");
        performanceTest(PlatformThreadPerformance::createManyPlatformThread, "PlatformThreads");
    }

    private static void createManyPlatformThread()  {
        try (ExecutorService executorService = Executors.newFixedThreadPool(threadAmount)) {
            executeTasks(executorService);
        }
    }

    private static void createManyVirtualThread() {
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            executeTasks(executorService);
        }
    }

    @SneakyThrows
    private static void executeTasks(ExecutorService executorService) {
        List<Callable<Integer>> tasks = new ArrayList<>();
        for (int i = 0; i < threadAmount; i++) {
            tasks.add(sleepTask);
        }
        executorService.invokeAll(tasks);
    }

    public static void performanceTest(Runnable function, String implementationName) {
        Runtime runtime = Runtime.getRuntime();
        Long startTime = System.currentTimeMillis();
        Long memoryBefore = runtime.totalMemory() - runtime.freeMemory();
        function.run();
        Long endTime = System.currentTimeMillis();
        Long memoryAfter = runtime.totalMemory() - runtime.freeMemory();
        double duration = endTime - startTime;
        double memoryUsed = ((double) memoryAfter - memoryBefore) / (1024 * 1024);
        System.out.printf("%s worked %.2f ms, memory used: %.2f MB\n", implementationName, duration, memoryUsed);
    }
}

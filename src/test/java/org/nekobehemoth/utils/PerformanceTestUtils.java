package org.nekobehemoth.utils;

public class PerformanceTestUtils {

    public static void performanceTest(Runnable function, String implementationName) {
        Runtime runtime = Runtime.getRuntime();
        Long startTime = System.currentTimeMillis();
        Long memoryBefore = runtime.totalMemory() - runtime.freeMemory();
        function.run();
        Long endTime = System.currentTimeMillis();
        Long memoryAfter = runtime.totalMemory() - runtime.freeMemory();
        double duration = endTime - startTime;
        double memoryUsed = ((double) memoryAfter - memoryBefore) / (1024 * 1024);
        System.out.printf("%s implementation worked %f ms, memory used: %.2f MB\n", implementationName, duration, memoryUsed);
    }
}

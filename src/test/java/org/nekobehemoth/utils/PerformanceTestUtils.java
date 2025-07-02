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
        long memoryUsed = (memoryAfter - memoryBefore);
        System.out.printf("%s implementation worked %f ms, memory used: %d\n", implementationName, duration, memoryUsed);
    }
}

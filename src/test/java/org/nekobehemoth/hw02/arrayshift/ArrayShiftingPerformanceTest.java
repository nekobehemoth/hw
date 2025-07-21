package org.nekobehemoth.hw02.arrayshift;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.nekobehemoth.hw02.fibonacci.FibonacciAlgorithms;
import org.nekobehemoth.hw02.shifting.ArrayOperations;
import org.nekobehemoth.utils.PerformanceTestUtils;

public class ArrayShiftingPerformanceTest {
    private static final int[] positions = {10, 100, 1000, 10000, };
    private static final int[] arraySizes = {10000, 100000, 1000000, 10000000};

    @Test
    void testArrayShiftingPerformance(){
        System.out.println("-------------Comparative test-------------");
        for (int position : positions) {
            for (int size : arraySizes) {
                System.out.printf("-------------Array size: %d; Shift position: %d-------------\n",  size, position);
                PerformanceTestUtils.performanceTest(() -> ArrayOperations.shiftLeftSystemCopy(new int[size], position), "System");
                PerformanceTestUtils.performanceTest( () -> ArrayOperations.shiftLeftManualLoop(new int[size], position), "Manual");
                System.out.println("--------------------------------------------------------------");
            }
            System.out.println("--------------------------------------------------------------");
        }

    }
    @Test
    void testSystemCopyArrayShiftingPerformance() {
        System.out.println("System Copy array shifting with changing array's size with fixed position");
        System.out.println("--------------------------------------------------------------");
        for (int i = 10000000; i <= 1000000000; i += 10000000) {
            int finalI = i;
            PerformanceTestUtils.performanceTest(() -> ArrayOperations.shiftLeftSystemCopy(new int[finalI], 1), "System");
        }
        System.out.println("--------------------------------------------------------------");
    }

    @Test
    void testManualArrayShiftingPerformance() {
        System.out.println("Manual Shifting with fixed array size and changing position");
        System.out.println("--------------------------------------------------------------");
        int arraySize = 100000;
        for (int i = 10000; i <= arraySize; i += 10000) {
            int finalI = i;
            PerformanceTestUtils.performanceTest(() -> ArrayOperations.shiftLeftManualLoop(new int[arraySize], finalI), "System");
        }
        System.out.println("--------------------------------------------------------------");
    }


    @AfterAll
    static void afterAll() {
        System.out.println("""
                System copy implementation is not depend on position, only on array size,\s
                because we just copy elements, but not physically shifting it. Time complexity is O(n).\s
                Especially you can see it in the separate test."""
        );
        System.out.println("--------------------------------------------------------------");
        System.out.println("Manual implementation highly depend on the position and on array size \n" +
                "time complexity is O(position x n)");
        System.out.println("--------------------------------------------------------------");
    }
}

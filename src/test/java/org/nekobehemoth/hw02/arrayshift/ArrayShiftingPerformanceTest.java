package org.nekobehemoth.hw02.arrayshift;

import org.junit.jupiter.api.Test;
import org.nekobehemoth.hw02.fibonacci.FibonacciAlgorithms;
import org.nekobehemoth.hw02.shifting.ArrayOperations;
import org.nekobehemoth.utils.PerformanceTestUtils;

public class ArrayShiftingPerformanceTest {
    private static final int[] positions = {1, 10, 100, 1000};
    private static final int[] arraySizes = {1000, 10000, 100000, 1000000};

    @Test
    void testFibonacciRealizationPerformance(){

        for (int position : positions) {
            for (int size : arraySizes) {
                System.out.printf("-------------Array size: %d; Shift position: %d-------------\n",  size, position);
                PerformanceTestUtils.performanceTest(() -> ArrayOperations.shiftLeftSystemCopy(new int[size], position), "System");
                PerformanceTestUtils.performanceTest( () -> ArrayOperations.shiftLeftManualLoop(new int[size], position), "Manual");
                System.out.println("--------------------------------------------------------------");
            }
        }
    }
}

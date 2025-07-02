package org.nekobehemoth.hw02.fibonacci;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.nekobehemoth.gehtsofttraining.TestTiming;
import org.nekobehemoth.utils.PerformanceTestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@ExtendWith(TestTiming.class)
public class FibonacciPerformanceTest {

    //private static final int[] input = {10, 20, 30, 35, 45, 60, 70};
    private static final int[] input = {100, 1000, 10000, 100000, 1000000};
    FibonacciAlgorithms fib = new FibonacciAlgorithms();

    @Test
    void testFibonacciRealizationPerformance(){
        for (int fibNum : input) {
            System.out.printf("-------------------------Fibonacci %s------------------------\n",  fibNum);
            PerformanceTestUtils.performanceTest(() -> fib.fibonacciMemoized(fibNum), "Memorized");
            PerformanceTestUtils.performanceTest( () -> fib.fibonacciIterative(fibNum), "Iterative");
            System.out.println("--------------------------------------------------------------");
        }
    }
}

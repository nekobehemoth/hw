package org.nekobehemoth.hw02.fibonacci;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.nekobehemoth.gehtsofttraining.TestTiming;
import org.nekobehemoth.utils.PerformanceTestUtils;


@ExtendWith(TestTiming.class)
public class FibonacciPerformanceTest {

    //private static final int[] input = {10, 20, 30, 35, 45, 60, 70};
    private static final int[] input = {100, 1000, 10000, 100000, 1000000, 10000000};
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

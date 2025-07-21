package org.nekobehemoth.hw02.fibonacci;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.nekobehemoth.gehtsofttraining.TestTiming;
import org.nekobehemoth.utils.PerformanceTestUtils;

import java.util.List;
import java.util.Map;


@ExtendWith(TestTiming.class)
public class FibonacciPerformanceTest {

    //private static final int[] input = {10, 20, 30, 35, 45, 60, 70};
    private static final int[] input = {100, 1000, 10000, 100000, 1000000, 10000000};
    static FibonacciAlgorithms fib = new FibonacciAlgorithms();

    @Test
    void testFibonacciRealizationPerformance(){
        for (int fibNum : input) {
            System.out.printf("-------------------------Fibonacci %s------------------------\n",  fibNum);
            PerformanceTestUtils.performanceTest(() -> fib.fibonacciMemoized(fibNum), "Memorized");
            PerformanceTestUtils.performanceTest( () -> fib.fibonacciIterative(fibNum), "Iterative");
            System.out.println("--------------------------------------------------------------");
        }
        System.out.println("It looks like Iterative implementation is logarithm complexity - O(log n)");
        System.out.println("But if you check with bigger values you will see the linear complexity O(n)");
        System.out.println("""
                Memorized implementation is something  between O(n) and O(n log n). Should be O(n),\s
                because we make one unique calculation and repeated take from cache,\s
                but because of array copy it broke ideal complexity""");
        System.out.println("------------------------------------------------------");
    }

    @Test
    void testIterativeFibonacciWithBiggerValues() {
        for (int i = 10000000; i<= 100000000; i += 10000000) {
            int finalI = i;
            PerformanceTestUtils.performanceTest( () -> fib.fibonacciIterative(finalI), "Iterative");
        }
        System.out.println("Here we are, you can see the linear complexity O(n)");
        System.out.println("------------------------------------------------------");
    }
}

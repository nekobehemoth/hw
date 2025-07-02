package org.nekobehemoth.hw02.fibonacci;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.nekobehemoth.gehtsofttraining.TestTiming;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FibonacciAlgorithmsTest {

    FibonacciAlgorithms fib = new FibonacciAlgorithms();

    @ParameterizedTest
    @CsvFileSource(resources = "/fibonacciTestCases.csv", numLinesToSkip = 1)
    void testRecursiveFibonacci(int argument, int expected) {
        int result = fib.fibonacciRecursive(argument);
        assertEquals(expected, result);
    }


    @ParameterizedTest
    @CsvFileSource(resources = "/fibonacciTestCases.csv", numLinesToSkip = 1)
    void testMemorizedFibonacci(int argument, int expected) {
        int result = fib.fibonacciMemoized(argument);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/fibonacciTestCases.csv", numLinesToSkip = 1)
    void testIterativeFibonacci(int argument, int expected) {
        int result = fib.fibonacciIterative(argument);
        assertEquals(expected, result);
    }
}
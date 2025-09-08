package org.nekobehemoth.hw05;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class MultithreadSumCorrectnessTest {
    private static MultithreadSum multithreadSum;
    private static final int arraySize = 100_000_000;
    private static final short[] values = new short[arraySize];
    static long sumToCheck;

    @BeforeAll
    static void setUp() {
        multithreadSum = new MultithreadSum();
        Random rand = new Random(10);
        sumToCheck = 0;
        for (int i = 0; i < values.length; i++) {
            values[i] = (short) (rand.nextInt(2 * 32768) - 32768);
            sumToCheck += values[i];
        }
    }


    @Test
    void multithreadSumParallelCorrectness() {
        long result = multithreadSum.sumWithParallelStream(values, 10);
        assertEquals(sumToCheck, result);
    }

    @Test
    void multithreadSumThreadCorrectness() {
        long result = multithreadSum.sumWithParallelThreads(values, 10);
        assertEquals(sumToCheck, result);
    }
}

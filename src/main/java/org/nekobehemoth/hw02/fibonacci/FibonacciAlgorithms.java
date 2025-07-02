package org.nekobehemoth.hw02.fibonacci;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class FibonacciAlgorithms {
    
    private Integer[] cache = new Integer[10];
    
    public int fibonacciRecursive(int n) {
        if (n == 0) return 0;
        return n <= 2 ? 1 : fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
    }


    public int fibonacciMemoized(int n) {
        if (n == 0) return 0;
        if (n <= 2) return 1;
        if (n >= cache.length) {
            cache = Arrays.copyOf(cache, Math.max(n + 1, cache.length * 2));
        }
        if (cache[n] != null) return cache[n];
        int result = fibonacciMemoized(n - 1) + fibonacciMemoized(n - 2);
        cache[n] = result;
        return result;

    }

    public int fibonacciIterative(int n) {
        int result = 1;
        int prev = 0;
        int prevTwoTimes = 0;
        if (n == 0) return 0;
        for (int i = 1; i<n; i++) {
            prevTwoTimes = prev;
            prev = result;
            result = prevTwoTimes + prev;
        }
        return result;
    }
}

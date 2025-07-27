package org.nekobehemoth.hw04;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.nekobehemoth.hw01.PerformanceTest;
import org.nekobehemoth.hw01.TestTiming;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;


@ExtendWith(TestTiming.class)
public class CustomHashMapPerformanceTest {

    static HashMap<Integer, Integer> natiiveHashMap;
    static CustomHashMap<Integer, Integer> customHashMap;
    static final int testDataSize = 10000000;
    @BeforeAll
    static void setUp() {
        natiiveHashMap = new HashMap<>();
        customHashMap = new CustomHashMap<>();
        for (int i = 0; i <= testDataSize; i++) {
            natiiveHashMap.put(i, i);
            customHashMap.put(i, i);
        }
    }


    private static Stream<Arguments> hashMapImplementation() {
        return Stream.of(
                Arguments.of("NativeHashMap", new HashMap<Integer, Integer>()),
                Arguments.of("CustomHashMap", new CustomHashMap<Integer, Integer>())
        );
    }

    private static Stream<Arguments> hashMapImplementationWithValue() {
        return Stream.of(
                Arguments.of("NativeHashMap", natiiveHashMap),
                Arguments.of("CustomHashMap", customHashMap)
        );
    }

    @ParameterizedTest(name = "{0} add performance")
    @MethodSource("hashMapImplementation")
    void addPerformanceTest(String implName, Map<Integer, Integer> hashMapImpl) {
        for (int i = 0; i <= testDataSize; i++) {
            hashMapImpl.put(i, i);
        }
    }

    @ParameterizedTest(name = "{0} remove performance")
    @MethodSource("hashMapImplementationWithValue")
    void addAndRemovePerformanceTest(String implName, Map<Integer, Integer> hashMapImpl) {
        for (int i = 0; i <= testDataSize; i++) {
            hashMapImpl.remove(i, i);
        }
    }


    @ParameterizedTest(name = "{0} entrySet performance")
    @MethodSource("hashMapImplementationWithValue")
    void entrySetPerformanceTest(String implName, Map<Integer, Integer> hashMapImpl) {
        hashMapImpl.entrySet();
    }

    @ParameterizedTest(name = "{0} entrySet performance")
    @MethodSource("hashMapImplementationWithValue")
    void valuesPerformanceTest(String implName, Map<Integer, Integer> hashMapImpl) {
        hashMapImpl.values();
    }

    @ParameterizedTest(name = "{0} containsKey performance")
    @MethodSource("hashMapImplementationWithValue")
    void containsKeyPerformanceTest(String implName, Map<Integer, Integer> hashMapImpl) {
        hashMapImpl.containsKey(100000);
    }

    @ParameterizedTest(name = "{0} containsValue performance")
    @MethodSource("hashMapImplementationWithValue")
    void containsValuePerformanceTest(String implName, Map<Integer, Integer> hashMapImpl) {
        hashMapImpl.containsValue(10000000);
    }

    @ParameterizedTest(name = "{0} containsValue performance")
    @MethodSource("hashMapImplementationWithValue")
    void getPerformanceTest(String implName, Map<Integer, Integer> hashMapImpl) {
        hashMapImpl.get(10000000);
    }

    @ParameterizedTest(name = "{0} containsValue performance")
    @MethodSource("hashMapImplementationWithValue")
    void clearPerformanceTest(String implName, Map<Integer, Integer> hashMapImpl) {
        hashMapImpl.clear();
    }


}

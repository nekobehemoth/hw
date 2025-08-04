package org.nekobehemoth.hw05.decorators;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.nekobehemoth.hw01.CustomList;
import org.nekobehemoth.hw01.TestTiming;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
@ExtendWith(TestTiming.class)
public class PerformanceTest {

    private static Stream<Arguments> listRealisation() {
        return Stream.of(
                Arguments.of("CustomList", new CustomList<String>()),
                Arguments.of("SynchronizedCustomList", new SynchronizedCustomListDecorator<>(new CustomList<>())),
                Arguments.of("LockedCustomList", new SynchronizedCustomListDecorator<>(new CustomList<>()))
        );
    }

    @ParameterizedTest(name = "{0} exec time and memory usage for adding million records")
    @MethodSource("listRealisation")
    void testAddingMillionElements(String arrayName, List<Integer> list){
        for (int i = 0; i <= 1000000; i++) {
            list.add(i);
        }
    }


    @ParameterizedTest(name = "{0} exec time and memory usage for adding 10000 records and deleting them from the first element")
    @MethodSource("listRealisation")
    void testAddingAndDeleting(String arrayName, List<Integer> list){
        for (int i = 0; i < 10000; i++) {
            list.add(i);
        }
        for (int i = 0; i < 10000; i++) {
            list.remove(0);
        }

    }


    @ParameterizedTest(name = "{0} exec time and memory usage for adding million records")
    @MethodSource("listRealisation")
    void testAddingMillionElementsAndCheckIfContains(String arrayName, List<Integer> list){
        for (int i = 0; i < 1000000; i++) {
            list.add(i);
        }

        assertTrue(list.contains(999999));
    }


    @AfterAll
    public static void afterAll() throws Exception {
        TestTiming.getReport().forEach((testMethod, implementation) -> {
            System.out.println();
            System.out.println("Test results for method: " + testMethod);
            implementation.forEach((implementationName, metric) -> {
                double execTime = metric.get(0);
                double usedMemory = metric.get(1);
                System.out.printf("%s: average time: %.3f ms, memory usage: %.3f MB%n", implementationName, execTime, usedMemory);
            });
        } );
    }
}

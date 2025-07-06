package org.nekobehemoth.hw01;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(TestTiming.class)
public class PerformanceTest {

    private static Stream<Arguments> listRealisation() {
        return Stream.of(
                Arguments.of("CustomList", new CustomList<Integer>()),
                Arguments.of("ArrayList", new ArrayList<Integer>()),
                Arguments.of("LinkedList", new LinkedList<Integer>())
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
}

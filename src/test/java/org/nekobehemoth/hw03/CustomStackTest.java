package org.nekobehemoth.hw03;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomStackTest {


    private static Stream<Arguments> stackImplementation() {
        return Stream.of(
                Arguments.of("Stack", new Stack<String>()),
                Arguments.of("CustomStack", new CustomStack<String>())
        );    }


    @ParameterizedTest
    @MethodSource("stackImplementation")
    void testAddFirst(String implName, Stack<String> list) {
        String band_1 = "The Beatles";
        String band_2 = "Behemoth";
        list.push(band_1);
        list.push(band_2);
        assertEquals(band_2, list.pop());
        assertEquals(band_1, list.pop());
    }
}

package org.nekobehemoth.hw03;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class CustomStackTest {
    Stack<String> nativeStack;
    CustomStack<String> customStack;

    @BeforeEach
    void setUp() {
        nativeStack = new Stack<>();
        customStack = new CustomStack<>();
    }

    @Test
    void testAdd() {
        String band_1 = "The Beatles";
        String band_2 = "Behemoth";
        String band_3 = "Deep Purple";
        nativeStack.add(band_1);
        customStack.add(band_1);
        nativeStack.add(band_2);
        customStack.add(band_2);
        nativeStack.add(band_3);
        customStack.add(band_3);
        assertEquals(nativeStack.toString(), customStack.toString());
    }

    @Test
    void testPeek() {
        String band_1 = "The Beatles";
        String band_2 = "Behemoth";
        String band_3 = "Deep Purple";
        nativeStack.add(band_1);
        customStack.add(band_1);
        nativeStack.add(band_2);
        customStack.add(band_2);
        nativeStack.add(band_3);
        customStack.add(band_3);
        //Should be Deep Purple. In different with LinkedList, the Stack's peek get the last element.
        assertEquals(nativeStack.peek(), customStack.peek());
    }

    @Test
    void testPeekFromEmpty() {
        //Should throws EmptyStackException if trying to peek from empty stack
        assertThrows(EmptyStackException.class, () -> nativeStack.peek());
        assertThrows(EmptyStackException.class, () -> customStack.peek());
    }

    @Test
    void testContains() {
        String band_1 = "The Beatles";
        String band_2 = "Behemoth";
        String band_3 = "Deep Purple";
        nativeStack.add(band_1);
        customStack.add(band_1);
        nativeStack.add(band_2);
        customStack.add(band_2);
        nativeStack.add(band_3);
        customStack.add(band_3);

        assertTrue(nativeStack.contains(band_2));
        assertTrue(customStack.contains(band_2));
    }

    @Test
    void testContainsOfEmptyStack() {
        String band_1 = "The Beatles";
        assertFalse(nativeStack.contains(band_1));
        assertFalse(customStack.contains(band_1));
    }

    @Test
    void testPop() {
        String band_1 = "The Beatles";
        String band_2 = "Behemoth";
        String band_3 = "Deep Purple";
        nativeStack.add(band_1);
        customStack.add(band_1);
        nativeStack.add(band_2);
        customStack.add(band_2);
        nativeStack.add(band_3);
        customStack.add(band_3);
        //In compare of LinkedList, pop remove the Last added Element not the first
        assertEquals(band_3, nativeStack.pop());
        assertEquals(band_3, customStack.pop());
        //Pop deletes element from the Stack
        assertFalse(nativeStack.contains(band_3));
        assertFalse(customStack.contains(band_3));
    }

    @Test
    void testPopFromEmptyStack() {
        assertThrows(EmptyStackException.class, () -> nativeStack.pop());
        assertThrows(EmptyStackException.class, () -> customStack.pop());
    }

    @Test
    void testPush() {
        String band_1 = "The Beatles";
        String band_2 = "Behemoth";
        String band_3 = "Deep Purple";
        nativeStack.push(band_1);
        customStack.push(band_1);
        nativeStack.push(band_2);
        customStack.push(band_2);
        assertEquals(band_3,  nativeStack.push(band_3));
        assertEquals(band_3,  customStack.push(band_3));
        assertEquals(nativeStack.toString(), customStack.toString());
    }

    @Test
    void testSearch() {
        String band_1 = "The Beatles";
        String band_2 = "Behemoth";
        String band_3 = "Deep Purple";
        nativeStack.push(band_1);
        customStack.push(band_1);
        nativeStack.push(band_2);
        customStack.push(band_2);
        nativeStack.push(band_3);
        customStack.push(band_3);
        //In stack's search the first element is 1 not 0 as usual, so the second element will return 2
        assertEquals(3, nativeStack.search(band_1));
        assertEquals(3, customStack.search(band_1));
        assertEquals(nativeStack.search(band_2), customStack.search(band_2));
    }



}

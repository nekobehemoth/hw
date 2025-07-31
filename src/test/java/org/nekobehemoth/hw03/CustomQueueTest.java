package org.nekobehemoth.hw03;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class CustomQueueTest {

    Queue<String> customQueue;

    @BeforeEach
    void setUp() {
        customQueue = new CustomQueue<>();
    }

    @Test
    void testAddAndPeek() {
        String band_1 = "The Beatles";
        String band_2 = "Behemoth";
        customQueue.add(band_1);
        assertEquals(1, customQueue.size());
        customQueue.add(band_2);
        assertEquals(2, customQueue.size());
        assertEquals(band_1, customQueue.peek());
    }

    @Test
    void remove() {
    }

    @Test
    void testRemove() {
        String band_1 = "The Beatles";
        String band_2 = "Behemoth";
        String band_3 = "Deep Purple";
        customQueue.add(band_1);
        customQueue.add(band_2);
        customQueue.add(band_3);
        System.out.println(customQueue);
        assertTrue(customQueue.remove(band_1));
        assertEquals(band_2, customQueue.peek());
        assertEquals(2, customQueue.size());
    }


    //the same as add method
    @Test
    void testOffer() {
        String band_1 = "The Beatles";
        String band_2 = "Behemoth";
        customQueue.offer(band_1);
        assertEquals(1, customQueue.size());
        customQueue.offer(band_2);
        assertEquals(2, customQueue.size());
        assertEquals(band_1, customQueue.peek());
    }


    @Test
    void testPoll() {
        String band_1 = "The Beatles";
        String band_2 = "Behemoth";
        String band_3 = "Deep Purple";
        customQueue.add(band_1);
        customQueue.add(band_2);
        customQueue.add(band_3);
        assertEquals(band_1, customQueue.poll());
        assertEquals(2, customQueue.size());
    }

    @Test
    void testElement() {
        String band_1 = "The Beatles";
        String band_2 = "Behemoth";
        customQueue.add(band_1);
        assertEquals(1, customQueue.size());
        customQueue.add(band_2);
        assertEquals(2, customQueue.size());
        assertEquals(band_1, customQueue.element());
    }
    @Test
    void testElementFromEmptyQueue() {
        assertThrows(NoSuchElementException.class, () ->  customQueue.element());
    }
}

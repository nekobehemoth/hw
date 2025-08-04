package org.nekobehemoth.hw03;



import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class CustomLinkedListTest {

    private static Stream<Arguments> linkedListImplementationList() {
        return Stream.of(
                Arguments.of("LinkedList", new LinkedList<String>()),
                Arguments.of("CustomLinkedList", new CustomLinkedList<String>())
        );
    }

    @ParameterizedTest
    @MethodSource("linkedListImplementationList")
    void testAddFirst(String implName, List<String> list) {
        String band_1 = "The Beatles";
        String band_2 = "Behemoth";
        list.add(band_1);
        list.add(band_2);
        assertEquals(band_1, list.get(0));
        assertEquals(band_2, list.get(1));
    }

    @ParameterizedTest(name = "{0} should increase size after entry added")
    @MethodSource("linkedListImplementationList")
    void testAddAndSize(String arrayName, List<String> list) {
        String band_1 = "The Beatles";
        String band_2 = "Behemoth";
        list.add(band_1);
        assertEquals(1, list.size());
        list.add(band_2);
        assertEquals(2, list.size());
        assertEquals(band_1, list.get(0));
        assertEquals(band_2, list.get(1));
    }

    @ParameterizedTest(name = "{0} should return false after entry added")
    @MethodSource("linkedListImplementationList")
    void testNotEmpty(String arrayName, List<String> list) {
        String band_1 = "The Beatles";
        list.add(band_1);
        assertFalse(list.isEmpty());
    }

    @ParameterizedTest(name = "{0} should found added null entry if it added")
    @MethodSource("linkedListImplementationList")
    void testAddNull(String arrayName, List<String> list) {
        list.add(null);
        assertEquals(1, list.size());
        assertEquals(null, list.get(0));
    }


    @ParameterizedTest(name = "{0} should return true if new array")
    @MethodSource("linkedListImplementationList")
    void testEmpty(String arrayName, List<String> list) {
        assertTrue(list.isEmpty());
    }

    @ParameterizedTest(name = "{0} should return true if new array")
    @MethodSource("linkedListImplementationList")
    void testEmptyAfterRemoving(String arrayName, List<String> list) {
        String band_1 = "The Beatles";
        String band_2 = "Behemoth";
        list.add(band_1);
        assertEquals(1, list.size());
        list.add(band_2);
        list.remove(band_1);
        list.remove(band_2);
        assertTrue(list.isEmpty());
    }

    @ParameterizedTest(name = "{0} must be found the element that was added and not found which was not added")
    @MethodSource("linkedListImplementationList")
    void testContains(String arrayName, List<String> list) {
        String band_1 = "The Beatles";
        String band_2 = "Behemoth";
        String band_3 = "Deep Purple";
        list.add(band_1);
        list.add(band_3);

        assertTrue(list.contains(band_1));
        assertFalse(list.contains(band_2));
    }

    @ParameterizedTest(name = "{0} must be found the null element if added")
    @MethodSource("linkedListImplementationList")
    void testContainsNull(String arrayName, List<String> list) {
        list.add(null);
        assertTrue(list.contains(null));
    }

    @ParameterizedTest(name = "{0} should rebuild indexes after removing")
    @MethodSource("linkedListImplementationList")
    void testRemoveByObjectFromMiddle(String arrayName, List<String> list) {
        String band_1 = "The Beatles";
        String band_2 = "Behemoth";
        String band_3 = "Deep Purple";
        list.add(band_1);
        list.add(band_2);
        list.add(band_3);

        assertTrue(list.contains(band_2));
        assertTrue(list.remove(band_2));
        assertFalse(list.contains(band_2));
        assertEquals(2, list.size());
        System.out.println(list.size());
        assertEquals(band_1, list.get(0));
        assertEquals(band_3, list.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(2));
    }

    @ParameterizedTest(name = "{0} should rebuild indexes after removing")
    @MethodSource("linkedListImplementationList")
    void testGetFromEmptyList(String arrayName, List<String> list) {
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
    }

    @ParameterizedTest(name = "{0} should return false if remove from empty list")
    @MethodSource("linkedListImplementationList")
    void testRemoveEntryFromEmptyList(String arrayName, List<String> list) {
        String band_1 = "The Beatles";
        assertFalse(list.remove(band_1));
    }

    @ParameterizedTest(name = "{0} should delete entry exactly with particular entry, after deletion all elements list should be empty")
    @MethodSource("linkedListImplementationList")
    void testRemoveEntryByIndex(String arrayName, List<String> list) {
        String band_1 = "The Beatles";
        String band_2 = "Behemoth";
        String band_3 = "Deep Purple";
        list.add(band_1);
        list.add(band_2);
        list.add(band_3);
        assertEquals(3, list.size());
        list.remove(1);
        assertFalse(list.contains(band_2));
        assertEquals(2, list.size());
        list.remove(1);
        list.remove(0);
        assertEquals(0, list.size());
    }

    @ParameterizedTest(name = "{0} should thrown error if delete with negative index")
    @MethodSource("linkedListImplementationList")
    void testRemoveEntryByIndexFromWithNegativeIndex(String arrayName, List<String> list) {
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
    }

    @ParameterizedTest(name = "{0} should thrown error if delete by index from empty list")
    @MethodSource("linkedListImplementationList")
    void testRemoveEntryByIndexFromEmptyList(String arrayName, List<String> list) {
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(1));
    }


    @ParameterizedTest(name = "{0} should be completely empty after clear")
    @MethodSource("linkedListImplementationList")
    void testClear(String arrayName, List<String> list) {
        String band_1 = "The Beatles";
        String band_2 = "Behemoth";
        String band_3 = "Deep Purple";
        list.add(band_1);
        list.add(band_2);
        list.add(band_3);

        list.clear();
        assertEquals(0, list.size(), "size should be 0");
        assertFalse(list.contains(null), "it shouldn't contains 0");
        assertFalse(list.contains(band_1), "it shouldn't contains any previous number");
    }


    @ParameterizedTest(name = "{0} new set value should be placed to pointed index")
    @MethodSource("linkedListImplementationList")
    void testSetWithValue(String arrayName, List<String> list) {
        String band_1 = "The Beatles";
        String band_2 = "Behemoth";
        String band_3 = "Deep Purple";
        list.add(band_1);
        list.add(band_2);
        list.set(0, band_3);
        assertEquals(list.get(0), band_3);
    }

    @ParameterizedTest(name = "{0} should allow set null value")
    @MethodSource("linkedListImplementationList")
    void testSetWithNull(String arrayName, List<String> list) {
        String band_1 = "The Beatles";
        String band_2 = "Behemoth";
        String band_3 = "Deep Purple";
        list.add(band_1);
        list.add(band_2);
        list.set(0, null);
        assertNull(list.get(0));
    }

    @ParameterizedTest(name = "{0} should throw the IndexOutOfBoundsException when set with negative index")
    @MethodSource("linkedListImplementationList")
    void testSetWithNegativeIndex(String arrayName, List<String> list) {
        assertThrows(IndexOutOfBoundsException.class, () -> list.set(-1, null));
    }


    @ParameterizedTest(name = "{0} must add element to pointed index")
    @MethodSource("linkedListImplementationList")
    void testAddByIndex(String arrayName, List<String> list) {
        String band_1 = "The Beatles";
        String band_2 = "Behemoth";
        String band_3 = "Deep Purple";
        list.add( band_1);
        list.add(band_3);
        list.add(1, band_2);
        assertTrue(list.contains(band_2));
        assertEquals(band_2, list.get(1));
    }

    @ParameterizedTest(name = "{0} must throw error if add to index more than or equal size")
    @MethodSource("linkedListImplementationList")
    void testAddByIndexThrowError(String arrayName, List<String> list) {
        String band_1 = "The Beatles";
        String band_2 = "Behemoth";
        String band_3 = "Deep Purple";
        list.add( band_1);
        list.add(band_3);
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(3, band_2));
    }
}

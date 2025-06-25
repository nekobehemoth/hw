package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CustomListTest {

    private static Stream<Arguments> listRealisation() {
        return Stream.of(
                Arguments.of("CustomList", new CustomList<String>()),
                Arguments.of("ArrayList", new ArrayList<String>())
        );
    }


    @ParameterizedTest(name = "{0} should increase size after entry added")
    @MethodSource("listRealisation")
    void testEmptySize(String arrayName, List<String> list) {
        assertEquals(0, list.size());
    }


    @ParameterizedTest(name = "{0} should increase size after entry added")
    @MethodSource("listRealisation")
    void testAddAndGet(String arrayName, List<String> list) {
        String band_1 = "The Beatles";
        String band_2 = "Behemoth";
        list.add(band_1);
        list.add(band_2);
        assertEquals(band_1, list.get(0));
        assertEquals(band_2, list.get(1));
    }

    @ParameterizedTest(name = "{0} should increase size after entry added")
    @MethodSource("listRealisation")
    void testNegativeGet(String arrayName, List<String> list) {
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
    }

    @ParameterizedTest(name = "{0} should increase size after entry added")
    @MethodSource("listRealisation")
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
    @MethodSource("listRealisation")
    void testNotEmpty(String arrayName, List<String> list) {
        String band_1 = "The Beatles";
        list.add(band_1);
        assertFalse(list.isEmpty());
    }

    @ParameterizedTest(name = "{0} should found added null entry if it added")
    @MethodSource("listRealisation")
    void testAddNull(String arrayName, List<String> list) {
        list.add(null);
        assertEquals(1, list.size());
        assertEquals(null, list.get(0));
    }


    @ParameterizedTest(name = "{0} should return false after entry added")
    @MethodSource("listRealisation")
    void testEmpty(String arrayName, List<String> list) {
        assertTrue(list.isEmpty());
    }

    @ParameterizedTest(name = "{0} should found added entry and not found not added")
    @MethodSource("listRealisation")
    void testContains(String arrayName, List<String> list) {
        String band_1 = "The Beatles";
        String band_2 = "Behemoth";
        String band_3 = "Deep Purple";
        list.add(band_1);
        list.add(band_3);

        assertTrue(list.contains(band_1));
        assertFalse(list.contains(band_2));
    }

    @ParameterizedTest(name = "{0} should found added entry and not found not added")
    @MethodSource("listRealisation")
    void testContainsNull(String arrayName, List<String> list) {
        list.add(null);
        assertTrue(list.contains(null));
    }


    @ParameterizedTest(name = "{0} should rebuild indexes after removing")
    @MethodSource("listRealisation")
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
        assertEquals(band_1, list.get(0));
        assertEquals(band_3, list.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(2));
    }

    @ParameterizedTest(name = "{0} should return false if delete from empty list")
    @MethodSource("listRealisation")
    void testRemoveEntryFromEmptyList(String arrayName, List<String> list) {
        String band_1 = "The Beatles";
        assertFalse(list.remove(band_1));
    }

    @ParameterizedTest(name = "{0} should null if delete from empty list")
    @MethodSource("listRealisation")
    void testRemoveEntryByIndexFromEmptyList(String arrayName, List<String> list) {
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(1));
    }


    @ParameterizedTest(name = "{0} should be completely empty after clear")
    @MethodSource("listRealisation")
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



}
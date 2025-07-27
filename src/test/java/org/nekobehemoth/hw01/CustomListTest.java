package org.nekobehemoth.hw01;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
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


    private static Stream<Arguments> listRealisationWithCapacity() {
        return Stream.of(
                Arguments.of("CustomList", new CustomList<Integer>(10)),
                Arguments.of("ArrayList", new ArrayList<Integer>(10))
        );
    }

    private static Stream<Arguments> listRealisationConstructor() {
        return Stream.of(
                Arguments.of("CustomList", new CustomList<Integer>(Arrays.asList(1, 2, 3, 4, 5))),
                Arguments.of("ArrayList", new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5)))
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


    @ParameterizedTest(name = "{0} should return true if new array")
    @MethodSource("listRealisation")
    void testEmpty(String arrayName, List<String> list) {
        assertTrue(list.isEmpty());
    }

    @ParameterizedTest(name = "{0} should return true if new array")
    @MethodSource("listRealisation")
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

    @ParameterizedTest(name = "{0} must be found the null element if added")
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

    @ParameterizedTest(name = "{0} should return false if remove from empty list")
    @MethodSource("listRealisation")
    void testRemoveEntryFromEmptyList(String arrayName, List<String> list) {
        String band_1 = "The Beatles";
        assertFalse(list.remove(band_1));
    }

    @ParameterizedTest(name = "{0} should delete entry exactly with particular entry, after deletion all elements list should be empty")
    @MethodSource("listRealisation")
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
    @MethodSource("listRealisation")
    void testRemoveEntryByIndexFromWithNegativeIndex(String arrayName, List<String> list) {
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
    }

    @ParameterizedTest(name = "{0} should thrown error if delete by index from empty list")
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


    @ParameterizedTest(name = "{0} new set value should be placed to pointed index")
    @MethodSource("listRealisation")
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
    @MethodSource("listRealisation")
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
    @MethodSource("listRealisation")
    void testSetWithNegativeIndex(String arrayName, List<String> list) {
        assertThrows(IndexOutOfBoundsException.class, () -> list.set(-1, null));
    }

    @ParameterizedTest(name = "{0} the size of list should be increase after initial capacity without an an issue ")
    @MethodSource("listRealisationWithCapacity")
    //Initial capacity is 10. But we can put more items then initial capacity, because list automatically extended.
    void testCapacityExtension(String arrayName, List<Integer> list) {
        for(int i = 0; i < 20; i++) {
            list.add(i);
        }
        assertEquals(20, list.size());
        assertEquals(0, list.get(0));
        assertEquals(5, list.get(5));
        assertEquals(10, list.get(10));
        assertEquals(19, list.get(19));
    }

    @ParameterizedTest(name = "{0} should have the same size as amount of initialized items and items should be on their own places")
    @MethodSource("listRealisationConstructor")
    void testConstructorWithInitialization(String arrayName, List<Integer> list) {
        int arraySize = list.toArray().length;
        Object firstElement = list.toArray()[0];
        assertEquals(arraySize, list.size());
        assertEquals(firstElement, list.get(0));
    }
}
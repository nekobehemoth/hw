package org.nekobehemoth.hw04;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

public class CustomHashMapTest {


    static HashMap<Integer, Integer> natiiveHashMap;
    static CustomHashMap<Integer, Integer> customHashMap;
    static final int testDataSize = 100;

    private static final String BAND_1 = "The Beatles";
    private static final String BAND_2 = "Behemoth";
    private static final String BAND_3 = "Black Sabbath";

    @BeforeEach
    void beforeEach() {
        natiiveHashMap = new HashMap<>();
        customHashMap = new CustomHashMap<>();
        for (int i = 0; i < testDataSize; i++) {
            natiiveHashMap.put(i, i);
            customHashMap.put(i, i);
        }
    }


    private static Stream<Arguments> hashMapImplementation() {
        return Stream.of(
                Arguments.of("NativeHashMap", new HashMap<Integer, String>()),
                Arguments.of("CustomHashMap", new CustomHashMap<Integer, String>())
        );
    }

    private static Stream<Arguments> hashMapImplementationWithValue() {
        return Stream.of(
                Arguments.of("NativeHashMap", natiiveHashMap),
                Arguments.of("CustomHashMap", customHashMap)
        );
    }

    @ParameterizedTest(name = "{0} should have size 100 after add 100 elements added")
    @MethodSource("hashMapImplementationWithValue")
    void sizeTest(String implName, Map<Integer, Integer> hashMapImpl) {
        assertEquals(testDataSize, hashMapImpl.size());
    }

    @ParameterizedTest(name = "{0} should return true when it is empty and false when not")
    @MethodSource("hashMapImplementation")
    void isEmptyTest(String implName, Map<Integer, String> hashMapImpl) {
        assertTrue(hashMapImpl.isEmpty());
        hashMapImpl.put(1, BAND_1);
        assertFalse(hashMapImpl.isEmpty());
    }

    @ParameterizedTest(name = "{0} should contain added values and size should be changed")
    @MethodSource("hashMapImplementation")
    void putTest(String implName, Map<Integer, String> hashMapImpl) {
        assertTrue(hashMapImpl.isEmpty());
        hashMapImpl.put(1, BAND_1);
        hashMapImpl.put(2, BAND_2);
        assertEquals(2, hashMapImpl.size());
    }

    @ParameterizedTest(name = "{0} should return null if entry added for the first time")
    @MethodSource("hashMapImplementation")
    void putCheckIfInsertedFirstTest(String implName, Map<Integer, String> hashMapImpl) {
        assertNull(hashMapImpl.put(1, BAND_1));
        assertNull(hashMapImpl.put(2, BAND_2));
    }

    @ParameterizedTest(name = "{0} should return prev value if entry with the same key added for the second time")
    @MethodSource("hashMapImplementation")
    void putCheckIfInsertedSecondTimeTest(String implName, Map<Integer, String> hashMapImpl) {
        assertNull(hashMapImpl.put(1, BAND_1));
        assertEquals(BAND_1, hashMapImpl.put(1, BAND_2));
        assertEquals(1, hashMapImpl.size());
    }

    @ParameterizedTest(name = "{0} should return value of the entry")
    @MethodSource("hashMapImplementation")
    void getTest(String implName, Map<Integer, String> hashMapImpl) {
        hashMapImpl.put(1, BAND_1);
        hashMapImpl.put(2, BAND_2);
        hashMapImpl.put(3, BAND_3);
        assertEquals(BAND_2, hashMapImpl.get(2));
    }

    @ParameterizedTest(name = "{0} should return null if key is not exists")
    @MethodSource("hashMapImplementation")
    void getKeyWhichNotExistsTest(String implName, Map<Integer, String> hashMapImpl) {
        hashMapImpl.put(1, BAND_1);
        hashMapImpl.put(2, BAND_2);
        hashMapImpl.put(3, BAND_3);
        assertNull(hashMapImpl.get(4));
    }

    @ParameterizedTest(name = "{0} should return null if key is negative")
    @MethodSource("hashMapImplementation")
    void getNegativeKeyTest(String implName, Map<Integer, String> hashMapImpl) {
        hashMapImpl.put(1, BAND_1);
        hashMapImpl.put(2, BAND_2);
        hashMapImpl.put(3, BAND_3);
        assertNull(hashMapImpl.get(-1));
    }

    @ParameterizedTest(name = "{0} return last added value for the key which added few times")
    @MethodSource("hashMapImplementation")
    void putOverWriteValueTest(String implName, Map<Integer, String> hashMapImpl) {
        hashMapImpl.put(1, BAND_1);
        hashMapImpl.put(1, BAND_2);
        hashMapImpl.put(1, BAND_3);
        assertEquals(BAND_3, hashMapImpl.get(1));
    }

    @ParameterizedTest(name = "{0} should decrement size and return null if try to search removed items")
    @MethodSource("hashMapImplementation")
    void removeTest(String implName, Map<Integer, String> hashMapImpl) {
        hashMapImpl.put(1, BAND_1);
        hashMapImpl.put(2, BAND_2);
        hashMapImpl.put(3, BAND_3);
        assertEquals(BAND_2, hashMapImpl.get(2));
        assertEquals(3, hashMapImpl.size());
        hashMapImpl.remove(2);
        assertEquals(2, hashMapImpl.size());
        assertNull(hashMapImpl.get(2));
    }

    @ParameterizedTest(name = "{0} should return true if key exists and false if not")
    @MethodSource("hashMapImplementation")
    void containsKeyTest(String implName, Map<Integer, String> hashMapImpl) {
        hashMapImpl.put(1, BAND_1);
        hashMapImpl.put(2, BAND_2);
        hashMapImpl.put(3, BAND_3);
        assertTrue(hashMapImpl.containsKey(1));
        assertFalse(hashMapImpl.containsKey(4));
    }

    @ParameterizedTest(name = "{0} should return true if Value exists and false if not")
    @MethodSource("hashMapImplementation")
    void containsValueTest(String implName, Map<Integer, String> hashMapImpl) {
        hashMapImpl.put(1, BAND_1);
        hashMapImpl.put(2, BAND_2);
        hashMapImpl.put(3, BAND_3);
        assertTrue(hashMapImpl.containsValue(BAND_2));
        assertFalse(hashMapImpl.containsValue("Buddy Holly"));
    }

    @ParameterizedTest(name = "{0} should have size 0 after clear and be empty")
    @MethodSource("hashMapImplementationWithValue")
    void clearTest(String implName, Map<Integer, String> hashMapImpl) {
        hashMapImpl.clear();
        assertEquals(0, hashMapImpl.size());
    }

    @ParameterizedTest(name = "{0} should return set of keys")
    @MethodSource("hashMapImplementation")
    void keySetTest(String implName, Map<Integer, String> hashMapImpl) {
        hashMapImpl.put(1, BAND_1);
        hashMapImpl.put(2, BAND_2);
        hashMapImpl.put(3, BAND_3);
        Set<Integer> keySet = hashMapImpl.keySet();

        assertTrue(keySet.contains(1));
        assertTrue(keySet.contains(2));
        assertTrue(keySet.contains(3));
        assertEquals(3, keySet.size());
    }

    @ParameterizedTest(name = "{0} should return collection of values")
    @MethodSource("hashMapImplementation")
    void valuesTest(String implName, Map<Integer, String> hashMapImpl) {
        hashMapImpl.put(1, BAND_1);
        hashMapImpl.put(2, BAND_2);
        hashMapImpl.put(3, BAND_3);
        Collection<String> values = hashMapImpl.values();
        assertTrue(values.contains(BAND_1));
        assertTrue(values.contains(BAND_2));
        assertTrue(values.contains(BAND_3));
        hashMapImpl.remove(3);
        values = hashMapImpl.values();
        assertFalse(values.contains(BAND_3));
        assertEquals(2, values.size());
    }


    @ParameterizedTest(name = "{0} should return set of Entries")
    @MethodSource("hashMapImplementation")
    void entrySetTest(String implName, Map<Integer, String> hashMapImpl) {
        hashMapImpl.put(1, BAND_1);
        hashMapImpl.put(2, BAND_2);
        hashMapImpl.put(3, BAND_3);
        Set<Map.Entry<Integer,String>> entrySet = hashMapImpl.entrySet();
        assertEquals(3, entrySet.size());
        List<Integer> keyList = entrySet.stream().map(Map.Entry::getKey).toList();
        List<String> valueList = entrySet.stream().map(Map.Entry::getValue).toList();
        assertEquals(3, keyList.size());
        assertEquals(3, valueList.size());
        assertTrue(keyList.contains(1));
        assertTrue(keyList.contains(2));
        assertTrue(keyList.contains(3));
        assertTrue(valueList.contains(BAND_1));
        assertTrue(valueList.contains(BAND_2));
        assertTrue(valueList.contains(BAND_3));

    }
}

package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CustomListTest {

    public CustomList myList;


    @BeforeEach
    void beforeEach() {
        myList = new CustomList();
    }

    @Test
    void testSize() {
        assertEquals(0, myList.size());
    }

    @Test
    void testAddAndSize() {
        Integer intg = 1;
        myList.add(intg);
        assertEquals(1, myList.size());
        myList.add(intg);
        assertEquals(2, myList.size());
    }

    @Test
    void testEmpty() {
        assertTrue(myList.isEmpty());
    }

    @Test
    void testNotEmpty() {
        Integer intg = 1;
        myList.add(intg);
        assertFalse(myList.isEmpty());
    }

    @Test
    void testContains() {
        String band_1 = "The Beatles";
        String band_2 = "Behemoth";
        String band_3 = "Deep Purple";
        myList.add(band_1);
        myList.add(band_3);

        assertTrue(myList.contains(band_1));
        assertFalse(myList.contains(band_2));
    }

    @Test
    void testRemove() {
        String band_1 = "The Beatles";
        String band_2 = "Behemoth";
        String band_3 = "Deep Purple";
        myList.add(band_1);
        myList.add(band_2);
        myList.add(band_3);

        assertTrue(myList.contains(band_2));
        assertTrue(myList.remove(band_2));
        assertFalse(myList.contains(band_2));
        assertEquals(2, myList.size());
    }
}
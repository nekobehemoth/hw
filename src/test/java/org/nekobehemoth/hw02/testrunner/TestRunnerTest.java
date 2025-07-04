package org.nekobehemoth.hw02.testrunner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nekobehemoth.gehtsofttraining.CustomList;
import static org.junit.jupiter.api.Assertions.*;

public class TestRunnerTest {

    CustomList<String> myList = new CustomList<>();

    @BeforeEach
    void beforeEach() {
        System.out.println("Before Each");
    }

    @AfterEach
    void anotherBeforeEach() {
        System.out.println("After Each" );
    }

    @Test
    void addCustomListTestWithError() {
        String band = "The Beatles";
        myList.add(band);
        assertEquals("Wronm", myList.get(0));
    }

    @Test
    void addCustomListTest() {
        String band = "The Beatles";
        myList.add(band);
        assertEquals(band, myList.get(0));
    }

    @Test
    void throwOtherError() {
        String band = "The Beatles";
        myList.add(band);
        throw new RuntimeException("Some error in code");
    }
}

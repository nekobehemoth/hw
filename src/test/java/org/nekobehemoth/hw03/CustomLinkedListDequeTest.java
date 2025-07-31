package org.nekobehemoth.hw03;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Deque;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class CustomLinkedListDequeTest {

    private static Stream<Arguments> linkedListImplementationDeque() {
        return Stream.of(
                Arguments.of("LinkedList", new LinkedList<String>()),
                Arguments.of("CustomLinkedList", new CustomLinkedList<String>())
        );
    }


    @ParameterizedTest(name = "{0}  When add element as first it should be at very beginning")
    @MethodSource("linkedListImplementationDeque")
    void addFirstTest(String implName, Deque<String> listImpl) {
        String band_1 = "The Beatles";
        String band_2 = "Behemoth";
        listImpl.addFirst(band_1);
        assertEquals(band_1, listImpl.getFirst());
        listImpl.addFirst(band_2);
        assertEquals(band_2, listImpl.getFirst());
        assertEquals(band_1, listImpl.getLast());
    }

    @ParameterizedTest(name = "{0}  When add element as last it should be in the end")
    @MethodSource("linkedListImplementationDeque")
    void addLastTest(String implName, Deque<String> listImpl) {
        String band_1 = "The Beatles";
        String band_2 = "Behemoth";
        listImpl.addLast(band_1);
        assertEquals(band_1, listImpl.getLast());
        listImpl.addLast(band_2);
        assertEquals(band_2, listImpl.getLast());
        assertEquals(band_1, listImpl.getFirst());
    }

    @ParameterizedTest(name = "{0}  When add element as first it should be at very beginning")
    @MethodSource("linkedListImplementationDeque")
    void offerFirstTest(String implName, Deque<String> listImpl) {
        String band_1 = "The Beatles";
        String band_2 = "Behemoth";
        listImpl.offerFirst(band_1);
        assertEquals(band_1, listImpl.getFirst());
        listImpl.offerFirst(band_2);
        assertEquals(band_2, listImpl.getFirst());
        assertEquals(band_1, listImpl.getLast());
    }

    @ParameterizedTest(name = "{0}  When add element as last it should be in the end")
    @MethodSource("linkedListImplementationDeque")
    void offerLastTest(String implName, Deque<String> listImpl) {
        String band_1 = "The Beatles";
        String band_2 = "Behemoth";
        listImpl.offerLast(band_1);
        assertEquals(band_1, listImpl.getLast());
        listImpl.offerLast(band_2);
        assertEquals(band_2, listImpl.getLast());
        assertEquals(band_1, listImpl.getFirst());
    }


    @ParameterizedTest(name = "{0}  After removing first element it shouldn't be present in list")
    @MethodSource("linkedListImplementationDeque")
    void removeFirstTest(String implName, Deque<String> listImpl) {
        String band_1 = "The Beatles";
        String band_2 = "Behemoth";
        listImpl.offerFirst(band_1);
        assertEquals(band_1, listImpl.getFirst());
        listImpl.offerFirst(band_2);
        listImpl.removeFirst();
        assertEquals(band_1, listImpl.getFirst());
        //Because we added only two element, after the removing first, the second will be the first and last element.
        assertEquals(band_1, listImpl.getLast());
        //Check if the removed item (band 2) is not present in the list
        assertFalse(listImpl.contains(band_2));
    }

    @ParameterizedTest(name = "{0}  After removing last element it shouldn't be present in list")
    @MethodSource("linkedListImplementationDeque")
    void removeLastTest(String implName, Deque<String> listImpl) {
        String band_1 = "The Beatles";
        String band_2 = "Behemoth";
        listImpl.offerFirst(band_1);
        assertEquals(band_1, listImpl.getFirst());
        listImpl.offerFirst(band_2);
        listImpl.removeLast();
        assertEquals(band_2, listImpl.getFirst());
        //Because we added only two element ( and added into beginning), after the removing last element,
        //the second band will be remained and will be the first and last element.
        assertEquals(band_2, listImpl.getLast());
        //Check if the removed interface is not present in the list
        assertFalse(listImpl.contains(band_1));
    }

    @ParameterizedTest(name = "{0}  When we pollFirst item should be returned and it shouldn't be present in list in the end")
    @MethodSource("linkedListImplementationDeque")
    void pollFirstTest(String implName, Deque<String> listImpl) {
        String band_1 = "The Beatles";
        String band_2 = "Behemoth";
        listImpl.offerFirst(band_1);
        assertEquals(band_1, listImpl.getFirst());
        listImpl.offerFirst(band_2);
        //We added band_2 to begin. pollFirst should retrieve the first item and then delete it.
        assertEquals(band_2, listImpl.pollFirst());
        assertEquals(band_1, listImpl.getFirst());
        //Because we added only two element, after the removing first, the second will be the first and last element.
        assertEquals(band_1, listImpl.getLast());
        //Check if the removed item (band 2) is not present in the list
        assertFalse(listImpl.contains(band_2));
    }

    @ParameterizedTest(name = "{0}  After removing last element it shouldn't be present in list")
    @MethodSource("linkedListImplementationDeque")
    void pollLastTest(String implName, Deque<String> listImpl) {
        String band_1 = "The Beatles";
        String band_2 = "Behemoth";
        listImpl.offerFirst(band_1);
        assertEquals(band_1, listImpl.getFirst());
        listImpl.offerFirst(band_2);
        assertEquals(band_2, listImpl.getFirst());
        //We added band_1 first then after adding second band it will be last.
        //pollLast should retrieve the last item and then delete it.
        assertEquals(band_1, listImpl.pollLast());
        //Because we added only two element ( and added into beginning), after the removing last element,
        //the second band will be remained and will be the first and last element.
        assertEquals(band_2, listImpl.getLast());
        //Check if the removed interface is not present in the list
        assertFalse(listImpl.contains(band_1));
    }

    @ParameterizedTest(name = "{0}  When we getFirst item should be returned but not deleted")
    @MethodSource("linkedListImplementationDeque")
    void getFirstTest(String implName, Deque<String> listImpl) {
        String band_1 = "The Beatles";
        String band_2 = "Behemoth";
        listImpl.offerFirst(band_1);
        assertEquals(band_1, listImpl.getFirst());
        listImpl.offerFirst(band_2);
        //We added band_2 to begin. getFirst should retrieve the first item but doesn't delete it.
        assertEquals(band_2, listImpl.getFirst());
        //getFirst don't remove retrieved item so band_2 should present in list
        assertTrue(listImpl.contains(band_2));
    }

    @ParameterizedTest(name = "{0}  When we getFirst from empty list the error should be thrown")
    @MethodSource("linkedListImplementationDeque")
    void getFirstFromEmptyArrayTest(String implName, Deque<String> listImpl) {
        assertThrows(NoSuchElementException.class, listImpl::getFirst);
    }

    @ParameterizedTest(name = "{0}  When we getLast item should be returned but not deleted")
    @MethodSource("linkedListImplementationDeque")
    void getLastTest(String implName, Deque<String> listImpl) {
        String band_1 = "The Beatles";
        String band_2 = "Behemoth";
        listImpl.offerFirst(band_1);
        listImpl.offerFirst(band_2);
        //We added band_2 to begin. getLast should retrieve the last item but doesn't delete it.
        assertEquals(band_1, listImpl.getLast());
        //getLast don't remove item so band_1 should present
        assertTrue(listImpl.contains(band_1));
    }

    @ParameterizedTest(name = "{0}  When we getLast from empty list the error should be thrown")
    @MethodSource("linkedListImplementationDeque")
    void getLastFromEmptyArrayTest(String implName, Deque<String> listImpl) {
        assertThrows(NoSuchElementException.class, listImpl::getLast);
    }

    @ParameterizedTest(name = "{0}  When we getFirst item should be returned but not deleted")
    @MethodSource("linkedListImplementationDeque")
    void peekFirstTest(String implName, Deque<String> listImpl) {
        String band_1 = "The Beatles";
        String band_2 = "Behemoth";
        listImpl.offerFirst(band_1);
        assertEquals(band_1, listImpl.getFirst());
        listImpl.offerFirst(band_2);
        //We added band_2 to begin. peekLast should retrieve the first item but doesn't delete it.
        assertEquals(band_1, listImpl.peekLast());
        //getFirst don't remove retrieved item so band_2 should present in list
        assertTrue(listImpl.contains(band_1));
    }

    @ParameterizedTest(name = "{0}  {0}  When we getFirst from empty list no error should be thrown")
    @MethodSource("linkedListImplementationDeque")
    void peekFirstFromEmptyArrayTest(String implName, Deque<String> listImpl) {
        assertDoesNotThrow(listImpl::peekFirst);
    }

    @ParameterizedTest(name = "{0}  {0}  When we getLast item should be returned but not deleted")
    @MethodSource("linkedListImplementationDeque")
    void peekLastTest(String implName, Deque<String> listImpl) {
        String band_1 = "The Beatles";
        String band_2 = "Behemoth";
        listImpl.offerFirst(band_1);
        listImpl.offerFirst(band_2);
        //We added band_2 to begin. peekLast should retrieve the last item but doesn't delete it.
        assertEquals(band_1, listImpl.peekLast());
        //getLast don't remove item so band_1 should present
        assertTrue(listImpl.contains(band_1));
    }

    @ParameterizedTest(name = "{0}  {0}  When we peekLast from empty list no error should be thrown")
    @MethodSource("linkedListImplementationDeque")
    void peekLastFromEmptyArrayTest(String implName, Deque<String> listImpl) {
        assertDoesNotThrow(listImpl::peekLast);
    }


    @ParameterizedTest(name = "{0}  {0}  When add element as first it should be at very beginning")
    @MethodSource("linkedListImplementationDeque")
    void removeFirstOccurrenceTest(String implName, Deque<String> listImpl) {
        String band_1 = "The Beatles";
        String band_2 = "Behemoth";
        listImpl.addFirst(band_1);
        listImpl.addLast(band_2);
        listImpl.addLast(band_1);
        assertEquals(3, listImpl.size());
        assertEquals(band_1, listImpl.getFirst());
        assertEquals(band_1, listImpl.getLast());
        //After adding the bands, we have a hamburger like "The Beatles" "Behemoth" "The Beatles"
        //After deleting the first occurrence of The Beatles, Behemoth will be first
        assertTrue(listImpl.removeFirstOccurrence(band_1));
        assertEquals(band_2, listImpl.getFirst());
        //Only the last The Beatles occurrence will remain
        assertEquals(band_1, listImpl.getLast());
        assertEquals(2, listImpl.size());
    }

    @ParameterizedTest(name = "{0}  {0}  When add element as first it should be at very beginning")
    @MethodSource("linkedListImplementationDeque")
    void removeLastOccurrenceTest(String implName, Deque<String> listImpl) {
        String band_1 = "The Beatles";
        String band_2 = "Behemoth";
        listImpl.addFirst(band_1);
        listImpl.addLast(band_2);
        listImpl.addLast(band_1);
        assertEquals(3, listImpl.size());
        assertEquals(band_1, listImpl.getFirst());
        assertEquals(band_1, listImpl.getLast());
        //After adding the bands, we have a hamburger like "The Beatles" "Behemoth" "The Beatles"
        //After deleting the last occurrence of The Beatles, The Beatles remain the first
        assertTrue(listImpl.removeLastOccurrence(band_1));
        assertEquals(band_1, listImpl.getFirst());
        //The last band will be Behemoth
        assertEquals(band_2, listImpl.getLast());
        assertEquals(2, listImpl.size());
    }


    @ParameterizedTest(name = "{0}  {0}  Remove it almost the same as removeFirstOccurrence")
    @MethodSource("linkedListImplementationDeque")
    void removeTest(String implName, Deque<String> listImpl) {
        String band_1 = "The Beatles";
        String band_2 = "Behemoth";
        listImpl.addFirst(band_1);
        listImpl.addLast(band_2);
        listImpl.addLast(band_1);
        assertEquals(3, listImpl.size());
        assertEquals(band_1, listImpl.getFirst());
        assertEquals(band_1, listImpl.getLast());
        //After adding the bands, we have a hamburger like "The Beatles" "Behemoth" "The Beatles"
        //After deleting the first occurrence of The Beatles, Behemoth will be first
        assertTrue(listImpl.remove(band_1));
        assertEquals(band_2, listImpl.getFirst());
        //Only the last The Beatles occurrence will remain
        assertEquals(band_1, listImpl.getLast());
        assertEquals(2, listImpl.size());
    }

    @ParameterizedTest(name = "{0}  {0}  Removing from empty list or non existing element should return false")
    @MethodSource("linkedListImplementationDeque")
    void removeFromEmptyListTest(String implName, Deque<String> listImpl) {
        String band_1 = "The Beatles";
        assertFalse(listImpl.remove(band_1));
    }


    @ParameterizedTest(name = "{0} when peek element, returns first one")
    @MethodSource("linkedListImplementationDeque")
    void testPeek(String implName, Deque<String> listImpl) {
        String band_1 = "The Beatles";
        String band_2 = "Behemoth";
        listImpl.addLast(band_1);
        listImpl.addLast(band_2);
        assertEquals(band_1, listImpl.peek());
        assertEquals(listImpl.getFirst(), listImpl.peek());
    }

}

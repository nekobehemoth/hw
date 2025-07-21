package org.nekobehemoth.hw03;

import java.util.EmptyStackException;

public class CustomStack<E> {
    CustomLinkedList<E> customLinkedList;


    public CustomStack(){
        this.customLinkedList = new CustomLinkedList<>();
    }

    public E push(E item) {
        customLinkedList.addLast(item);
        return item;
    }

    public boolean add(E item) {
        return customLinkedList.add(item);
    }

    public E pop() {
        if (customLinkedList.isEmpty()) throw new EmptyStackException();
        return  customLinkedList.removeLast();
    }

    public E peek() {
        if (customLinkedList.isEmpty()) throw new EmptyStackException();
        return customLinkedList.getLast();
    }

    public boolean empty(){
        return customLinkedList.isEmpty();
    }

    public int search(Object o) {
        int index = customLinkedList.lastIndexOf(o);
        if (index >= 0) return customLinkedList.size() - index;
        return -1;
    }

    public boolean contains(Object o) {
        return customLinkedList.contains(o);
    }

    @Override
    public String toString() {
        return customLinkedList.toString();
    }


}

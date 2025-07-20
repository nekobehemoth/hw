package org.nekobehemoth.hw03;

public class CustomStack<E> {
    CustomLinkedList<E> customLinkedList;


    public CustomStack(){
        this.customLinkedList = new CustomLinkedList<>();
    }

    public E push(E item) {
        customLinkedList.addFirst(item);
        return item;
    }

    public E pop() {
        return  customLinkedList.pop();
    }

    public E peep() {
        return customLinkedList.peek();
    }

    public boolean empty(){
        return customLinkedList.isEmpty();
    }

    public int search(Object o) {
        return customLinkedList.lastIndexOf(o);
    }
}

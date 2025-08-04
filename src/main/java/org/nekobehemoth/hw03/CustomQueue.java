package org.nekobehemoth.hw03;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

public class CustomQueue<E> implements Queue<E> {

    //Queue represent First In - First Out behaviour


    CustomLinkedList<E> linkedList;

    public CustomQueue() {
        linkedList = new CustomLinkedList<>();
    }

    @Override
    public int size() {
        return linkedList.size();
    }

    @Override
    public boolean isEmpty() {
        return linkedList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return linkedList.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return linkedList.iterator();
    }

    @Override
    public Object[] toArray() {
        return linkedList.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return linkedList.toArray(a);
    }


    //Elements added to the end of queue
    @Override
    public boolean add(E e) {
        linkedList.add(e);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return linkedList.removeLastOccurrence(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        linkedList.clear();
    }

    @Override
    public boolean offer(E e) {
        return linkedList.offer(e);
    }

    @Override
    public E remove() {
        return linkedList.remove();
    }

    @Override
    public E poll() {
        return linkedList.pollFirst();
    }

    @Override
    public E element() {
        return linkedList.element();
    }

    @Override
    public E peek() {
        return linkedList.peek();
    }

    @Override
    public String toString() {return linkedList.toString();}
}

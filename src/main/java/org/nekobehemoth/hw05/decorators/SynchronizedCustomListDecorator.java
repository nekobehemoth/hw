package org.nekobehemoth.hw05.decorators;

import org.nekobehemoth.hw01.CustomList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class SynchronizedCustomListDecorator<E> extends CustomListDecorator<E>{

    public SynchronizedCustomListDecorator(List<E> customList) {
        super(customList);
    }

    @Override
    public synchronized boolean add(E o) {
        return super.add(o);
    }

    @Override
    public synchronized boolean remove(Object o) {
        return super.remove(o);
    }

    @Override
    public synchronized void clear() {
        super.clear();
    }

    @Override
    public synchronized E get(int index) {
        return super.get(index);
    }

    @Override
    public synchronized E set(int index, E element) {
        return super.set(index, element);
    }

    @Override
    public synchronized int size() {
        return super.size();
    }

    @Override
    public synchronized boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    public synchronized boolean contains(Object o) {
        return super.contains(o);
    }

    @Override
    public synchronized Iterator<E> iterator() {
        return super.iterator();
    }

    @Override
    public synchronized Object[] toArray() {
        return super.toArray();
    }

    @Override
    public synchronized <T> T[] toArray(T[] a) {
        return super.toArray(a);
    }

    @Override
    public synchronized boolean containsAll(Collection<?> c) {
        return super.containsAll(c);
    }

    @Override
    public synchronized boolean addAll(Collection<? extends E> c) {
        return super.addAll(c);
    }

    @Override
    public synchronized boolean addAll(int index, Collection<? extends E> c) {
        return super.addAll(index, c);
    }

    @Override
    public synchronized boolean removeAll(Collection<?> c) {
        return super.removeAll(c);
    }

    @Override
    public synchronized boolean retainAll(Collection<?> c) {
        return super.retainAll(c);
    }

    @Override
    public synchronized void add(int index, E element) {
        super.add(index, element);
    }

    @Override
    public synchronized E remove(int index) {
        return super.remove(index);
    }

    @Override
    public synchronized int indexOf(Object o) {
        return super.indexOf(o);
    }

    @Override
    public synchronized int lastIndexOf(Object o) {
        return super.lastIndexOf(o);
    }

    @Override
    public synchronized ListIterator<E> listIterator() {
        return super.listIterator();
    }

    @Override
    public synchronized ListIterator<E> listIterator(int index) {
        return super.listIterator(index);
    }

    @Override
    public synchronized List<E> subList(int fromIndex, int toIndex) {
        return super.subList(fromIndex, toIndex);
    }
}

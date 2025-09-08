package org.nekobehemoth.hw05.decorators;

import org.nekobehemoth.hw01.CustomList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CustomListDecorator<E> implements List<E> {

    private final List<E> customList;

    public CustomListDecorator(List<E> customList){
        this.customList = customList;
    }

    @Override
    public int size() {
        return customList.size();
    }

    @Override
    public boolean isEmpty() {
        return customList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return customList.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return customList.iterator();
    }

    @Override
    public Object[] toArray() {
        return customList.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return customList.toArray(a);
    }

    @Override
    public boolean add(E e) {
        return customList.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return customList.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return customList.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return customList.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return customList.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return customList.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return customList.retainAll(c);
    }

    @Override
    public void clear() {
        customList.clear();
    }

    @Override
    public E get(int index) {
        return customList.get(index);
    }

    @Override
    public E set(int index, E element) {
        return customList.set(index, element);
    }

    @Override
    public void add(int index, E element) {
        customList.add(index, element);
    }

    @Override
    public E remove(int index) {
        return customList.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return customList.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return customList.lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        return customList.listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return customList.listIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return customList.subList(fromIndex, toIndex);
    }
}

package org.example;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CustomList implements List {


    private int size;
    Object[] array;


    private final static int DEFAULT_CAPACITY = 10;
    private final static Object[] DEFAULT_EMPTY_LIST = {};

    public CustomList() {
        this.array = DEFAULT_EMPTY_LIST;
    }

    public CustomList(int initCapacity) {
        if (initCapacity > 0) this.array = new Object[size];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (Object currObject : this.array) {
            if (currObject.equals(0)) return true;
        }
        return false;
    }

    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public boolean add(Object o) {
        if (this.size == this.array.length) this.array = increaseCapacity();
        this.array[size] = o;
        this.size += 1;
        return true;
    }

    private Object[] increaseCapacity() {
        int oldCapacity = this.array.length;
        int minCapacity = size + 1;
        if (oldCapacity > 0) {
            int newCapacity = oldCapacity + Math.max(minCapacity, oldCapacity >> 1);
            return new Object[newCapacity];
        } else {
            return new Object[DEFAULT_CAPACITY];
        }
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean addAll(Collection c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public Object get(int index) {
        return null;
    }

    @Override
    public Object set(int index, Object element) {
        return null;
    }

    @Override
    public void add(int index, Object element) {

    }

    @Override
    public Object remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator listIterator() {
        return null;
    }

    @Override
    public ListIterator listIterator(int index) {
        return null;
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        return List.of();
    }

    @Override
    public boolean retainAll(Collection c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection c) {
        return false;
    }

    @Override
    public boolean containsAll(Collection c) {
        return false;
    }

    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }
}

package org.example;

import java.io.ObjectStreamException;
import java.util.*;

public class CustomList<E> implements List<E> {

    private int size;
    private Object[] array;

    private final static int DEFAULT_CAPACITY = 10;
    private final static Object[] DEFAULT_EMPTY_LIST = {};

    public CustomList() {
        this.array = DEFAULT_EMPTY_LIST;
    }

    public CustomList(int initCapacity) {
        if (initCapacity > 0) this.array = new Object[initCapacity];
    }

    public CustomList(Collection<? extends E> c) {
        Object[] newArray = c.toArray();
        size = newArray.length;
        if (newArray.length != 0) {
            if (c.getClass() == CustomList.class) {
                array = newArray;
            } else {
                array = Arrays.copyOf(newArray, size, Object[].class);
            }
        } else {
            array = DEFAULT_EMPTY_LIST;
        }
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
        if (o == null) {
            for (int i = 0; i < size; i++){
                if (array[i] == null) return true;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (o.equals(array[i])) return true;
            }
        }
        return false;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array, size);
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
        for (int i = 0; i < size; i++) {
            if (o.equals(array[i])) {
                helpRemove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) {
        Objects.checkIndex(index, size);
        return (E) array[index];
    }

    @Override
    @SuppressWarnings("unchecked")
    public E set(int index, E element) {
        E oldValue = (E) array[index];
        array[index] = element;
        return (E) oldValue;
    }

    @Override
    public void add(int index, Object element) {
        int size = this.size;
        int newSize = size + 1;
        Object[] result = new Object[newSize];
        System.arraycopy(array, index - 1, result, index, newSize - index);
        System.arraycopy(array, 0, result, 0, index);
        result[index] = element;
        array = result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E remove(int index) {
        Objects.checkIndex(index, size);
        E deleted;
        deleted =  (E) array[index];
        helpRemove(index);
        return deleted;
    }


    private void helpRemove(int index) {
        int newSize = size - 1;
        Object[] result = new Object[newSize];
        System.arraycopy(array, index + 1, result, index, newSize - index);
        System.arraycopy(array, 0, result, 0, index);
        array = result;
        size = newSize;
    }



    //Features for future implementation

    @Override
    public boolean addAll(Collection c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection c) {
        return false;
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
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        //if (a.length < size) return (T[]) Arrays.copyOf(array, size, a.getClass());

        return (T[]) Arrays.copyOf(array, size, a.getClass());
    }

    @Override
    public Iterator iterator() {
        return null;
    }
}

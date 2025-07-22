package org.nekobehemoth.hw03;

import java.lang.reflect.Array;
import java.util.*;

public class CustomLinkedList<E> implements List<E>, Deque<E> {

    private Node<E> first;
    private Node<E> last;
    private int size;

    public void addFirst(E item) {
        final Node<E> f = first;
        final Node<E> newNode = new Node<>(null, item, f);
        first = newNode;
        //If it first node in LinkedArray (there is no others yet), then it last node as well
        if (f == null) last = newNode;
        //if there are some nodes already exists then just update the link for prev item
        else f.prev = newNode;
        size++;
    }

    @Override
    public void addLast(E item) {
        linkLast(item);
    }

    @Override
    public boolean add(E item) {
        linkLast(item);
        return true;
    }

    private void linkLast(E item) {
        final Node<E> l = last;
        final Node<E> newNode = new Node<>(l, item,null);
        last = newNode;
        if (l == null) first = newNode;
        else l.next = newNode;
        size++;
    }

    private E unlinkLast(Node<E> l) {
        final E deletedItem = l.item;
        final Node<E> prev = l.prev;
        //if last don't have prev, that mean the last is only item in list, then first also null now
        last = prev;
        if (prev == null) first = null;
        else prev.next = null;
        l.item = null;
        l.prev = null;
        size--;
        return deletedItem;
    }

    private E unlinkFirst(Node<E> f) {
        final E deletedItem = f.item;
        final Node<E> next = f.next;
        first = next;
        if (next == null) last = null;
        else next.prev = null;
        f.item = null;
        f.next = null;
        f = null;
        size--;
        return deletedItem;
    }

    private E unlink(Node<E> delNode) {
        final E deletedItem = delNode.item;
        final Node<E> next = delNode.next;
        final Node<E> prev = delNode.prev;

        //If prev item is null then it was first element and next element will first after deletion;
        if (prev == null) first = next;
        else {
            //If prev is not null then we need to update the link and next element of deleted item will be
            //next element of prev item.
            prev.next = delNode.next;
            //Set prev Node to null, garbage collector then throw it.
            delNode.prev = null;
        }
        //If next item is null then it was last item, so prev item will last after deletion of current item
        if (next == null) last = prev;
        else {
            //If not then update next's item prev link
            next.prev = prev;
            //Will be green and will help to collect garbage
            delNode.next = null;
        }
        delNode.item = null;
        size--;
        return deletedItem;
    }

    @Override
    public E getLast() {
        final Node<E> l = last;
        if (l == null) throw new NoSuchElementException();
        return l.item;
    }

    @Override
    public E removeFirst() {
        final Node<E> f = first;
        if (f == null) throw new NoSuchElementException();
        return unlinkFirst(f);
    }

    @Override
    public E removeLast() {
        final Node<E> l = last;
        if (l == null) throw new NoSuchElementException();
        return unlinkLast(l);
    }

    @Override
    public E getFirst() {
        final Node<E> f = first;
        if (f == null) throw new NoSuchElementException();
        return f.item;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) {
            for (Node<E> i = first; i != null; i = i.next) {
                if (i.item == null) return true;
            }
        } else {
            for (Node<E> i = first; i != null; i = i.next) {
                if (i.item.equals(o)) return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iter();
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        int index = 0;
        for (Node<E> i = first; i != null; i = i.next) {
            result[index] = i.item;
            index++;
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (size > a.length) {
            a = (T[]) Array.newInstance(a.getClass().getComponentType(), size);
        }
        Object[] result = a;
        int j = 0;
        for (Node<E> i = first; i != null; i = i.next) {
            result[j++] = i.item;
        }
        if (size > a.length) a[size] = null;
        return a;
    }

    public CustomLinkedList<E> reversed() {
        CustomLinkedList<E> rev = new CustomLinkedList<>();
        final  Node<E> l = last;
        for (Node<E> i = l; i!= null; i = i.prev) {
            rev.addFirst(i.item);
        }
        return rev;
    }


    @Override
    public boolean offerFirst(E e) {
        addFirst(e);
        return true;
    }

    @Override
    public boolean offerLast(E e) {
        addLast(e);
        return true;
    }

    @Override
    public E pollFirst() {
        final Node<E> f = first;
        return f == null ? null : unlinkFirst(f);
    }

    @Override
    public E pollLast() {
        final Node<E> l = last;
        return l == null ? null : unlinkLast(l);
    }

    @Override
    public E peekFirst() {
        final Node<E> f = first;
        return f == null ? null : f.item;
    }

    @Override
    public E peekLast() {
        final Node<E> l = last;
        return l == null ? null : l.item;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        int index = indexOf(o);
        if (index < 0) return false;
        remove(index);
        return true;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        int index = lastIndexOf(o);
        if (index < 0) return false;
        remove(index);
        return true;
    }

    @Override
    public boolean offer(E e) {
        return offerLast(e);
    }

    @Override
    public E remove() {
        return removeFirst();
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E peek() {
        return peekFirst();
    }

    @Override
    public void push(E e) {
        addFirst(e);
    }

    @Override
    public E pop() {
        return removeFirst();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<E> descendingIterator() {
        return null;
    }


    @Override
    public boolean remove(Object o) {
        if (o == null) {
            for (Node<E> i = first; i != null; i = i.next) {
                if (i.item == null) {
                    unlink(i);
                    return true;
                };
            }
        } else {
            for (Node<E> i = first; i != null; i = i.next) {
                if (i.item.equals(o)) {
                    unlink(i);
                    return true;
                };
            }
        }
        return false;
    }

    @Override
    public void clear() {
        for (Node<E> i = first; i != null;) {
            Node<E> next = i.next;
            i.prev = null;
            i.item = null;
            i.next = null;
            i = next;
        }
        first = null;
        last = null;
        size = 0;
    }

    @Override
    public E get(int index) {
        Node<E> found = getElementByIndex(index);
        return found.item;
    }

    @Override
    public E set(int index, E element) {
        Node<E> found = getElementByIndex(index);
        found.item = element;
        return found.item;
    }

    @Override
    public void add(int index, E element) {
        if (index == size) {
            addLast(element);
            return;
        }
        if (index == 0) {
            addFirst(element);
            return;
        };
        final Node<E> found = getElementByIndex(index);
        Node<E> prev = found.prev;
        prev.next = new Node<>(prev, element, found);
        found.prev = prev.next;
        size++;
    }

    @Override
    public E remove(int index) {
        Node<E> found = getElementByIndex(index);
        return unlink(found);
    }

    private Node<E> getElementByIndex(int index) {
        checkIndex(index);
        Node<E> found;
        if (index < (size / 2)) {
            found = first;
            for (int i = 0; i < index; i++) {
                found = found.next;
            }
        } else {
            found = last;
            for(int i = size - 1; i > index; i--) {
                found = found.prev;
            }
        }
        return found;
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        if (o == null) {
            for (Node<E> i = first; i != null; i = i.next) {
                if (i.item == null) return index;
                index++;
            }
        } else {
            for (Node<E> i = first; i != null; i = i.next) {
                if (i.item.equals(o)) return index;
                index++;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = size - 1;
        if (o == null) {
            for (Node<E> i = last; i != null; i = i.prev) {
                if (i.item == null) return index;
                index--;
            }
        } else {
            for (Node<E> i = last; i != null; i = i.prev) {
                if (i.item.equals(o)) return index;
                index--;
            }
        }
        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new LstIter(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new LstIter(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return List.of();
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        Iterator<E> it = new Iter();
        buffer.append("[");
        while (it.hasNext()) {
            buffer.append(it.next());
            if (!it.hasNext()) continue;
            buffer.append(", ");
        }
        buffer.append("]");
        return buffer.toString();
    }


    private class LstIter extends Iter implements ListIterator<E> {

        private int nextIndex;
        private Node<E> lastReturned;
        private Node<E> next;


        LstIter(int index) {
            if (index == size) next = null;
            else next = getElementByIndex(index);
            nextIndex = index;
        }

        @Override
        public boolean hasPrevious() {
            return nextIndex > 0;
        }

        @Override
        public E previous() {
            if (!hasPrevious()) throw new NoSuchElementException();
            if (next ==  null) next = last;
            else next = next.prev;
            lastReturned = next;
            nextIndex--;
            return lastReturned.item;
        }

        @Override
        public int nextIndex() {
            return nextIndex;
        }

        @Override
        public int previousIndex() {
            return nextIndex - 1;
        }

        @Override
        public void remove() {

        }

        @Override
        public void set(E e) {

        }

        @Override
        public void add(E e) {

        }
    }

    private class Iter implements Iterator<E> {

        private int nextIndex;

        @Override
        public boolean hasNext() {
            return nextIndex < size;
        }

        @Override
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            Node<E> next = getElementByIndex(nextIndex);
            Node<E> lastReturned = next;
            next = next.next;
            nextIndex++;
            return lastReturned.item;
        }
    }

    private void checkIndex(int index) {
        if (index + 1 > size || index < 0)
            throw new IndexOutOfBoundsException(String.format("Index: %d, Size: %d", index, size));
    }

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E item, Node<E> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    //Not implemented

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
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
}

package org.nekobehemoth.hw05.decorators;

import org.nekobehemoth.hw01.CustomList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockedCustomListDecorator<E> extends CustomListDecorator<E>{


    private ReentrantReadWriteLock lock;

    public LockedCustomListDecorator(List<E> customList) {
        super(customList);
        lock = new ReentrantReadWriteLock();
    }

    @Override
    public int size() {
        return readLockWrapper(super::size);
    }

    @Override
    public boolean isEmpty() {
        return readLockWrapper(super::isEmpty);
    }

    @Override
    public boolean contains(Object o) {
        return readLockWrapper(() -> super.contains(o));
    }

    @Override
    public Iterator<E> iterator() {
        return readLockWrapper(super::iterator);
    }

    @Override
    public Object[] toArray() {
        return readLockWrapper(() -> super.toArray());
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return readLockWrapper(() -> super.toArray(a));
    }

    @Override
    public boolean add(E e) {
        return writeLockWrapper(() -> super.add(e));
    }

    @Override
    public boolean remove(Object o) {
        return writeLockWrapper(() -> super.remove(o));
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return readLockWrapper(() -> super.containsAll(c));
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return writeLockWrapper(() -> super.addAll(c));
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return writeLockWrapper(() -> super.addAll(index, c));
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return writeLockWrapper(() -> super.removeAll(c));
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return writeLockWrapper(() -> super.retainAll(c));
    }

    @Override
    public void clear() {
        writeLockWrapper(super::clear);
    }

    @Override
    public E get(int index) {
        return readLockWrapper(() -> super.get(index));
    }

    @Override
    public E set(int index, E element) {
        return writeLockWrapper(() -> super.set(index, element));
    }

    @Override
    public void add(int index, E element) {
        writeLockWrapper(() -> super.add(index, element));
    }

    @Override
    public E remove(int index) {
        return writeLockWrapper(() -> super.remove(index));
    }

    @Override
    public int indexOf(Object o) {
        return readLockWrapper(() -> super.indexOf(o));
    }

    @Override
    public int lastIndexOf(Object o) {
        return readLockWrapper(() -> super.lastIndexOf(o));
    }

    @Override
    public ListIterator<E> listIterator() {
        return readLockWrapper(() -> super.listIterator());
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return readLockWrapper(() -> super.listIterator(index));
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return readLockWrapper(() -> super.subList(fromIndex, toIndex));
    }

    private <V> V readLockWrapper(Callable<V> task) {
        lock.readLock().lock();
        try {
            V result = task.call();
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.readLock().unlock();
        }
    }

    private void readLockWrapper(Runnable task) {
        lock.readLock().lock();
        try {
            task.run();
        } catch (Exception e) {
            System.out.println(e. getMessage());
        } finally {
            lock.readLock().unlock();
        }
    }


    private <V> V writeLockWrapper(Callable<V> task) {
        lock.writeLock().lock();
        try {
            V result = task.call();
            return result;
        } catch (Exception e) {
            System.out.println(e. getMessage());
        } finally {
            lock.writeLock().unlock();
        }
        return null;
    }

    private void writeLockWrapper(Runnable task) {
        lock.writeLock().lock();
        try {
            task.run();
        } catch (Exception e) {
            System.out.println(e. getMessage());
        } finally {
            lock.writeLock().unlock();
        }
    }
}

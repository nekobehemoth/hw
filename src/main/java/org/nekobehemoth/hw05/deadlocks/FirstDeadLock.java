package org.nekobehemoth.hw05.deadlocks;

import lombok.SneakyThrows;

import java.util.concurrent.locks.ReentrantLock;

public class FirstDeadLock {
    private final ReentrantLock lock1 = new ReentrantLock();
    private final ReentrantLock lock2 = new ReentrantLock();

    @SneakyThrows
    public static void main(String[] args) {

        FirstDeadLock deadLock = new FirstDeadLock();

        new Thread(deadLock::firstProcess).start();
        new Thread(deadLock::secondProcess).start();

    }

    private void firstProcess()  {
        lock1.lock();
        System.out.println("firstProcess - First lock");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        lock2.lock();
        System.out.println("firstProcess - Second lock");
        lock2.unlock();
        System.out.println("firstProcess - Second lock - unlocked");
        lock1.unlock();
        System.out.println("firstProcess - First lock - unlocked");

    }

    private void secondProcess()  {
        lock2.lock();
        System.out.println("secondProcess - Second lock");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        lock1.lock();
        System.out.println("secondProcess - First lock");
        lock1.unlock();
        System.out.println("secondProcess - First lock - unlocked");
        lock2.unlock();
        System.out.println("secondProcess - Second lock - unlocked");
    }
}

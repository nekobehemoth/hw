package org.nekobehemoth.hw05.deadlocks;

import lombok.SneakyThrows;

import java.util.concurrent.locks.ReentrantLock;

public class SecondDeadLock {
    private final ReentrantLock lock1 = new ReentrantLock();
    private final ReentrantLock lock2 = new ReentrantLock();
    private final ReentrantLock lock3 = new ReentrantLock();

    @SneakyThrows
    public static void main(String[] args) {

        SecondDeadLock deadLock = new SecondDeadLock();

        new Thread(deadLock::firstProcess).start();
        new Thread(deadLock::secondProcess).start();
        new Thread(deadLock::thirdProcess).start();

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
        lock1.unlock();
        System.out.println("firstProcess - First lock - unlocked");
        lock2.unlock();
        System.out.println("firstProcess - Second lock - unlocked");
    }

    private void secondProcess()  {
        lock2.lock();
        System.out.println("secondProcess - Second lock");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        lock3.lock();
        System.out.println("secondProcess - Third lock");
        lock2.unlock();
        System.out.println("secondProcess - Second lock - unlocked");
        lock3.unlock();
        System.out.println("secondProcess - Third lock - unlocked");
    }

    private void thirdProcess()  {
        lock3.lock();
        System.out.println("thirdProcess - Third lock");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        lock1.lock();
        System.out.println("thirdProcess - First lock");
        lock3.unlock();
        System.out.println("thirdProcess - Third lock - unlocked");
        lock1.unlock();
        System.out.println("thirdProcess - First lock - unlocked");
    }
}

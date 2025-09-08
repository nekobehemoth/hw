package org.nekobehemoth.hw05.deadlocks;

import lombok.SneakyThrows;

import java.util.concurrent.locks.ReentrantLock;

public class ThirdDeadLock {

    static Thread t1;
    static Thread t2;

    @SneakyThrows
    public static void main(String[] args) {

        t1 = new Thread(() -> {
            try {
                System.out.println("Start thread 1, wait for t2 to join.");
                t2.join();
                System.out.println("Start thread 2 is joined.");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        t2 = new Thread(() -> {

            try {
                System.out.println("Start thread 2, wait for t1 to join.");
                t1.join();
                System.out.println("Start thread 1 is joined.");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        t1.start();
        t2.start();
    }

}

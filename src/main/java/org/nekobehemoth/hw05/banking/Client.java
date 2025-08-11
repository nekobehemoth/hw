package org.nekobehemoth.hw05.banking;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Client {

    public static void main(String[] args) throws Exception {
       ;
        Bank beNekoBank = new SynchronizedBank(200, 0L, 1000L);
        BigInteger beforeTransfer = beNekoBank.getSumOfAllAccounts();

        try (ExecutorService transferService = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<?>> futures = new ArrayList<>();
            for (int i = 0; i < 1000; i++) {
                futures.add(transferService.submit(() -> {
                    int sender = beNekoBank.pickRandomAccountId();
                    int receiver = beNekoBank.pickRandomAccountId();
                    Random amount = new Random();
                    try {
                        beNekoBank.transfer(sender, receiver, amount.nextLong(beNekoBank.getAccountBalance(sender)));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }));
            }

            for (Future<?> future : futures) {
                try {
                    future.get();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        BigInteger afterTransfer = beNekoBank.getSumOfAllAccounts();
        System.out.println("Before money transfer: " + beforeTransfer);
        System.out.println("After money transfer: " + afterTransfer);
        System.out.println("Lose: " + (beforeTransfer.subtract(afterTransfer)));
    }

    public boolean bankClearing(Bank bank) {
        BigInteger beforeTransfer = bank.getSumOfAllAccounts();

        try (ExecutorService transferService = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<?>> futures = new ArrayList<>();
            for (int i = 0; i < 1000; i++) {
                futures.add(transferService.submit(() -> {
                    int sender = bank.pickRandomAccountId();
                    int receiver = bank.pickRandomAccountId();
                    Random amount = new Random();
                    try {
                        bank.transfer(sender, receiver, amount.nextLong(bank.getAccountBalance(sender)));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }));
            }

            for (Future<?> future : futures) {
                try {
                    future.get();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        BigInteger afterTransfer = bank.getSumOfAllAccounts();
        return beforeTransfer.equals(afterTransfer);
    }
}

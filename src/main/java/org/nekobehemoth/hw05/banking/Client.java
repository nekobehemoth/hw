package org.nekobehemoth.hw05.banking;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Client {
    public static void main(String[] args) throws Exception {
       ;
        Bank beNekoBank = new AtomicBank(200, 0L, 1000L);
        System.out.println(beNekoBank.getSumOfAllAccounts());

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

        System.out.println(beNekoBank.getSumOfAllAccounts());
    }
}

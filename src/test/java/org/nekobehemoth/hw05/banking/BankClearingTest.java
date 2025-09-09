package org.nekobehemoth.hw05.banking;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.concurrent.ThreadFactory;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class BankClearingTest {


    private static int numberOfAccounts = 200;
    private static long minBalance =  0L;
    private static long maxBalance = 1000L;



    private static Stream<Arguments> bankImplementation() {
        return Stream.of(
                Arguments.of("SynchronizedBank", new SynchronizedBank(numberOfAccounts, minBalance, maxBalance)),
                Arguments.of("LockedBank", new LockedBank(numberOfAccounts, minBalance, maxBalance)),
                Arguments.of("AtomicBank", new AtomicBank(numberOfAccounts, minBalance, maxBalance))
        );
    }

//Unstable test, it can return true times to times
//    @Test
//    void testUnsafeBank() {
//        Bank unsafeBank = new UnsafeBank(numberOfAccounts, minBalance, maxBalance);
//        Client client = new Client();
//        assertFalse(client.bankClearing(unsafeBank));
//    }

    @ParameterizedTest(name = "Client for {0} bank should return true when transfer money with threads")
    @MethodSource("bankImplementation")
    void testThreadSafeBank(String implName, Bank bankImpl) {
        Client client = new Client();
        assertTrue(client.bankClearing(bankImpl));
    }

    @ParameterizedTest(name = "Client for {0} bank should return true when transfer money with threads")
    @MethodSource("bankImplementation")
    void testSimultaneousWithdrawal(String implName, Bank bankImpl) throws Exception {
        ThreadFactory threadFactory = Thread.ofVirtual().factory();
        long balance_before = bankImpl.getAccountBalance(1);
        Thread t1 = threadFactory.newThread(() -> {
            try {
                bankImpl.withdraw(1, 10);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        Thread t2 =  threadFactory.newThread(() -> {
            try {
                bankImpl.withdraw(1, 10);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        long balance_after = bankImpl.getAccountBalance(1);


        assertEquals(20, balance_before - balance_after);
    }

    @ParameterizedTest(name = "Client for {0} bank should return true when transfer money with threads")
    @MethodSource("bankImplementation")
    void testSimultaneousDeposit(String implName, Bank bankImpl) throws Exception {
        ThreadFactory threadFactory = Thread.ofVirtual().factory();
        long balance_before = bankImpl.getAccountBalance(1);
        Thread t1 = threadFactory.newThread(() -> {
            try {
                bankImpl.deposit(1, 10);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        Thread t2 =  threadFactory.newThread(() -> {
            try {
                bankImpl.deposit(1, 10);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        long balance_after = bankImpl.getAccountBalance(1);


        assertEquals(20, balance_after - balance_before);
    }




}

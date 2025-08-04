package org.nekobehemoth.hw05.banking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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


}

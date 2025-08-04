package org.nekobehemoth.hw05.banking;

import java.math.BigInteger;

public class SynchronizedBank implements Bank {

    UnsafeBank unsafeBank;

    public SynchronizedBank(int numberOfAccounts, long minBalance, long maxBalance) {
        this.unsafeBank = new UnsafeBank(numberOfAccounts, minBalance, maxBalance);
    }


    @Override
    public int pickRandomAccountId() {
        return unsafeBank.pickRandomAccountId();
    }

    @Override
    public long getAccountBalance(int accountId) throws Exception {
        return unsafeBank.getAccountBalance(accountId);
    }

    @Override
    public void setAccountBalance(int accountId, long newBalance) throws Exception {
        unsafeBank.setAccountBalance(accountId, newBalance);
    }

    @Override
    public BigInteger getSumOfAllAccounts() {
        return unsafeBank.getSumOfAllAccounts();
    }

    @Override
    public long deposit(int accountId, long amount) throws Exception {
        return unsafeBank.deposit(accountId, amount);
    }

    @Override
    public long withdraw(int accountId, long amount) throws Exception {
        return unsafeBank.withdraw(accountId, amount);
    }

    @Override
    public synchronized long transfer(int senderAccountId, int receiverAccountId, long amount) throws Exception {
        return unsafeBank.transfer(senderAccountId, receiverAccountId, amount);
    }
}

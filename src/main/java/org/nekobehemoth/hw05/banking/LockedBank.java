package org.nekobehemoth.hw05.banking;

import java.math.BigInteger;
import java.util.concurrent.locks.ReentrantLock;

public class LockedBank implements Bank {

    UnsafeBank unsafeBank;
    ReentrantLock lock;

    public LockedBank(int numberOfAccounts, long minBalance, long maxBalance) {
        lock = new ReentrantLock();
        this.unsafeBank = new UnsafeBank(numberOfAccounts, minBalance, maxBalance);
    }


    @Override
    public int pickRandomAccountId() {
        return unsafeBank.pickRandomAccountId();
    }

    @Override
    public long getAccountBalance(int accountId) throws Exception {
        lock.lock();
        long balance = unsafeBank.getAccountBalance(accountId);
        lock.unlock();
        return balance;
    }

    @Override
    public void setAccountBalance(int accountId, long newBalance) throws Exception {
        lock.lock();
        unsafeBank.setAccountBalance(accountId, newBalance);
        lock.unlock();
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

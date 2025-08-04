package org.nekobehemoth.hw05.banking;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class AtomicBank implements Bank {

    final AtomicAccount[] accounts;
    private final int numberOfAccounts;


    public AtomicBank(int numberOfAccounts, long minBalance, long maxBalance) {
        this.numberOfAccounts = numberOfAccounts;
        this.accounts = new AtomicAccount[numberOfAccounts];
        Random balance = new Random();
        for (int i = 0; i < accounts.length; i++) {
            AtomicLong atomicBalance = new AtomicLong(balance.nextLong(maxBalance - minBalance) + minBalance);
            accounts[i] = new AtomicAccount(i,atomicBalance);
        }
    }

    @Override
    public int pickRandomAccountId() {
        Random id = new Random();
        return accounts[id.nextInt(numberOfAccounts)].getId();
    }

    @Override
    public long getAccountBalance(int accountId) throws Exception {
        AtomicAccount account = accounts[accountId];
        checkAccountId(accountId);
        return account.getBalance().get();
    }

    @Override
    public void setAccountBalance(int accountId, long newBalance) throws Exception {
        AtomicAccount account = accounts[accountId];
        checkAccountId(accountId);
        account.getBalance().getAndSet(newBalance);
    }

    @Override
    public BigInteger getSumOfAllAccounts() {
        return Arrays.stream(accounts)
                .map(account -> BigInteger.valueOf(account.getBalance().get()))
                .reduce(BigInteger.ZERO, BigInteger::add);
    }

    @Override
    public long deposit(int accountId, long amount) throws Exception {
        checkAccountId(accountId);
        AtomicLong balance = accounts[accountId].getBalance();
        while (true) {
            long oldBalance = getAccountBalance(accountId);
            long newBalance = oldBalance + amount;
            if (balance.compareAndSet(oldBalance, newBalance)) {
                return amount;
            }
        }
    }

    @Override
    public long withdraw(int accountId, long amount) throws Exception {
        checkAccountId(accountId);
        AtomicLong balance = accounts[accountId].getBalance();
        while (true) {
            long oldBalance = getAccountBalance(accountId);
            long newBalance = oldBalance - amount;
            if (newBalance < 0) throw new Exception("Insufficient balance on: " + accountId + " account");
            if (balance.compareAndSet(oldBalance, newBalance)) {
                return amount;
            }
        }
    }

    @Override
    public long transfer(int senderAccountId, int receiverAccountId, long amount) throws Exception {
        checkAccountId(senderAccountId);
        checkAccountId(receiverAccountId);
        withdraw(senderAccountId, amount);
        deposit(receiverAccountId, amount);
        return amount;
    }

    private void checkAccountId(int accountId) throws Exception {
        if (accountId < 0 || accountId >= numberOfAccounts) throw new Exception("No such account id: " + accountId);
    }
}

package org.nekobehemoth.hw05.banking;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

public class UnsafeBank implements Bank {

    final Account[] accounts;
    private final int numberOfAccounts;


    public UnsafeBank(int numberOfAccounts, long minBalance, long maxBalance) {
        this.numberOfAccounts = numberOfAccounts;
        this.accounts = new Account[numberOfAccounts];
        Random balance = new Random();
        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = new Account(i,
                    balance.nextLong(maxBalance - minBalance) + minBalance);
        }
    }

    @Override
    public int pickRandomAccountId() {
        Random id = new Random();
        return accounts[id.nextInt(numberOfAccounts)].getId();
    }

    @Override
    public long getAccountBalance(int accountId) throws Exception {
        Account account = accounts[accountId];
        checkAccountId(accountId);
        return account.getBalance();
    }

    @Override
    public void setAccountBalance(int accountId, long newBalance) throws Exception {
        Account account = accounts[accountId];
        checkAccountId(accountId);
        account.setBalance(newBalance);
    }

    @Override
    public BigInteger getSumOfAllAccounts() {
        return Arrays.stream(accounts)
                .map(account -> BigInteger.valueOf(account.getBalance()))
                .reduce(BigInteger.ZERO, BigInteger::add);
    }

    @Override
    public long deposit(int accountId, long amount) throws Exception {
        checkAccountId(accountId);
        setAccountBalance(accountId, getAccountBalance(accountId) + amount);
        return amount;
    }

    @Override
    public long withdraw(int accountId, long amount) throws Exception {
        checkAccountId(accountId);
        long newBalance = getAccountBalance(accountId) - amount;
        if (newBalance < 0) throw new Exception("Insufficient balance on: " + accountId + " account");
        setAccountBalance(accountId, newBalance);
        return amount;
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

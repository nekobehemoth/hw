package org.nekobehemoth.hw05.banking;

import java.math.BigInteger;

public interface Bank {
    public int pickRandomAccountId();
    public long getAccountBalance(int accountId) throws Exception;
    public void setAccountBalance(int accountId, long newBalance) throws Exception;
    public BigInteger getSumOfAllAccounts();
    public long deposit(int accountId, long amount) throws Exception;
    public long withdraw(int accountId, long amount) throws Exception;
    public long transfer(int senderAccountId, int receiverAccountId, long amount) throws Exception;
}

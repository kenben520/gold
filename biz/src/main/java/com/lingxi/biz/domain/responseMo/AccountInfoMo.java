package com.lingxi.biz.domain.responseMo;

import java.io.Serializable;

/**
 * Created by zhangwei on 2018/5/18.
 * 个人账户信息mo
 */

public class AccountInfoMo implements Serializable {

    /**
     * account : 20001
     * accountName : test 87
     * balance : 37397.23
     * cashAvailable : 0
     * equity : 39561.740000000005
     * floatingProfit : 2164.510000000002
     * leverage : 125
     * marginAvailable : 31470.660000000003
     * marginUsed : 8091.080000000001
     * marginUtilisation : 488.9549973551121
     * previousBalance : 37397.23
     */

    private String account;
    private String accountName;
    private double balance;
    private int cashAvailable;
    private double equity;
    private double floatingProfit;
    private int leverage;
    private double marginAvailable;
    private double credit;
    private double marginUsed;
    private double marginUtilisation;
    private double previousBalance;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getCashAvailable() {
        return cashAvailable;
    }

    public void setCashAvailable(int cashAvailable) {
        this.cashAvailable = cashAvailable;
    }

    public double getEquity() {
        return equity;
    }

    public void setEquity(double equity) {
        this.equity = equity;
    }

    public double getFloatingProfit() {
        return floatingProfit;
    }

    public void setFloatingProfit(double floatingProfit) {
        this.floatingProfit = floatingProfit;
    }

    public int getLeverage() {
        return leverage;
    }

    public void setLeverage(int leverage) {
        this.leverage = leverage;
    }

    public double getMarginAvailable() {
        return marginAvailable;
    }

    public void setMarginAvailable(double marginAvailable) {
        this.marginAvailable = marginAvailable;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public double getMarginUsed() {
        return marginUsed;
    }

    public void setMarginUsed(double marginUsed) {
        this.marginUsed = marginUsed;
    }

    public double getMarginUtilisation() {
        return marginUtilisation;
    }

    public void setMarginUtilisation(double marginUtilisation) {
        this.marginUtilisation = marginUtilisation;
    }

    public double getPreviousBalance() {
        return previousBalance;
    }

    public void setPreviousBalance(double previousBalance) {
        this.previousBalance = previousBalance;
    }
}

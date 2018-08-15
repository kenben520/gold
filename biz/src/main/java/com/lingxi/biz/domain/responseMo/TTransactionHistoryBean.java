package com.lingxi.biz.domain.responseMo;

public class TTransactionHistoryBean extends BaseMo {

    /**
     * account : 2109732023
     * amount : 1.02
     * comment :
     * fundingType : Deposit
     * time : 2018-05-17T03:12:55Z
     */

    private String account;
    private double amount;
    private String comment;
    private String fundingType;
    private String time;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFundingType() {
        return fundingType;
    }

    public void setFundingType(String fundingType) {
        this.fundingType = fundingType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

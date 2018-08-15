package com.lingxi.biz.domain.responseMo;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class TradeAllPositionBean extends BaseMo implements Serializable{

    /**
     * account : 2109732023
     * closePrice : 0
     * closeTime :
     * comment :
     * commission : 0
     * commissionAgent : 0
     * dateTime : 2018-05-23T11:43:36Z
     * initialMargin : 0
     * maintenanceMargin : 0
     * marginRate : 0.68944
     * openPrice : 1.17007
     * openTime : 2018-05-23T11:43:36Z
     * orderDirection : Buy
     * pnl : -83
     * positionId : 152644491
     * positionStatus : Open
     * stopLoss : 0
     * swap : 1139.87
     * symbol : EURUSD
     * takeProfit : 0
     * tax : 0
     * tradeReason : TRADE_BY_DEALER
     * volume : 1
     */
    private String account;
    private double closePrice;
    private String closeTime;
    private String comment;
    private double commission;
    private double commissionAgent;
    private String dateTime;
    private double initialMargin;
    private double maintenanceMargin;
    private double marginRate;
    private double openPrice;
    private String openTime;
    private String orderDirection;
    private double pnl;
    private int positionId;
    private String positionStatus;
    private double stopLoss;
    private double swap;
    private String symbol;
    private double takeProfit;
    private double tax;
    private String tradeReason;
    private double volume;
    private int cmd;

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(double closePrice) {
        this.closePrice = closePrice;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public double getCommissionAgent() {
        return commissionAgent;
    }

    public void setCommissionAgent(double commissionAgent) {
        this.commissionAgent = commissionAgent;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public double getInitialMargin() {
        return initialMargin;
    }

    public void setInitialMargin(double initialMargin) {
        this.initialMargin = initialMargin;
    }

    public double getMaintenanceMargin() {
        return maintenanceMargin;
    }

    public void setMaintenanceMargin(double maintenanceMargin) {
        this.maintenanceMargin = maintenanceMargin;
    }

    public double getMarginRate() {
        return marginRate;
    }

    public void setMarginRate(double marginRate) {
        this.marginRate = marginRate;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
    }

    public double getPnl() {
        return pnl;
    }

    public void setPnl(double pnl) {
        this.pnl = pnl;
    }

    public String getPositionStatus() {
        return positionStatus;
    }

    public void setPositionStatus(String positionStatus) {
        this.positionStatus = positionStatus;
    }

    public double getStopLoss() {
        return stopLoss;
    }

    public void setStopLoss(double stopLoss) {
        this.stopLoss = stopLoss;
    }

    public double getSwap() {
        return swap;
    }

    public void setSwap(double swap) {
        this.swap = swap;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getTakeProfit() {
        return takeProfit;
    }

    public void setTakeProfit(double takeProfit) {
        this.takeProfit = takeProfit;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public String getTradeReason() {
        return tradeReason;
    }

    public void setTradeReason(String tradeReason) {
        this.tradeReason = tradeReason;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }
}

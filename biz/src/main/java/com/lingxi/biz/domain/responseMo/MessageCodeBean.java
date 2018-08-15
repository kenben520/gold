package com.lingxi.biz.domain.responseMo;

import java.io.Serializable;

public class MessageCodeBean extends BaseMo implements Serializable {

    /**
     * account : 2109732023
     * dateTime : 2018-05-24T07:42:28Z
     * orderDirection : Buy
     * orderDuration : GoodTillDate
     * orderId : 152644782
     * orderPrice : 100
     * orderType : Limit
     * orderStatus : Placed
     * symbol : GOLD
     * volume : 0.1
     * comment :
     * expirationDate : 2018-05-25T12:34:55Z
     * stopLoss : 0
     * takeProfit : 0
     * extraData : {"cmd":"2","digits":"4","state":"0"}
     */

    private String account;
    private String dateTime;
    private String orderDirection;
    private String orderDuration;
    private int orderId;
    private double orderPrice;
    private String orderType;
    private String orderStatus;
    private String symbol;
    private double volume;
    private String comment;
    private String expirationDate;
    private double stopLoss;
    private double takeProfit;
    private ExtraDataBean extraData;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
    }

    public String getOrderDuration() {
        return orderDuration;
    }

    public void setOrderDuration(String orderDuration) {
        this.orderDuration = orderDuration;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public double getStopLoss() {
        return stopLoss;
    }

    public void setStopLoss(double stopLoss) {
        this.stopLoss = stopLoss;
    }

    public double getTakeProfit() {
        return takeProfit;
    }

    public void setTakeProfit(double takeProfit) {
        this.takeProfit = takeProfit;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }


    public ExtraDataBean getExtraData() {
        return extraData;
    }

    public void setExtraData(ExtraDataBean extraData) {
        this.extraData = extraData;
    }

    public static class ExtraDataBean implements Serializable{
        /**
         * cmd : 2
         * digits : 4
         * state : 0
         */

        private String cmd;
        private String digits;
        private String state;

        public String getCmd() {
            return cmd;
        }

        public void setCmd(String cmd) {
            this.cmd = cmd;
        }

        public String getDigits() {
            return digits;
        }

        public void setDigits(String digits) {
            this.digits = digits;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}

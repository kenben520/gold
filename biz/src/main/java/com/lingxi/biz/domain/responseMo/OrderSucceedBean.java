package com.lingxi.biz.domain.responseMo;

import java.io.Serializable;

public class OrderSucceedBean extends BaseMo implements Serializable{

    /**
     * orderId : 152649179
     * orderPrice : 1.15275
     * stopLoss : 0
     * takeProfit : 0
     */

    private int orderId;
    private double orderPrice;
    private double stopLoss;
    private double takeProfit;

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
}

package com.lingxi.biz.domain.responseMo;

/**
 * Created by qian on 2018/5/31.
 */

public class ClearOrderHistoryBean {


    /**
     * account : 2109732023
     * closePrice : 1.17061
     * closeTime : 2018-05-25T06:40:00Z
     * comment :
     * commission : 0
     * commissionAgent : 0
     * dateTime : 2018-05-23T11:46:26Z
     * extraData : {"cmd":"0","digits":"5","state":"3"}
     * initialMargin : 0
     * maintenanceMargin : 0
     * marginRate : 0.68975
     * openPrice : 1.17015
     * openTime : 2018-05-23T11:46:26Z
     * orderDirection : Buy
     * pnl : 4.6
     * positionId : 152644494
     * positionStatus : Closed
     * stopLoss : 0
     * swap : 152.14
     * symbol : EURUSD
     * takeProfit : 0
     * tax : 0
     * tradeReason : TRADE_BY_DEALER
     * volume : 0.1
     */

    private String account;
    private double closePrice;
    private String closeTime;
    private String comment;
    private int commission;
    private int commissionAgent;
    private String dateTime;
    private ExtraDataBean extraData;
    private int initialMargin;
    private int maintenanceMargin;
    private double marginRate;
    private double openPrice;
    private String openTime;
    private String orderDirection;
    private double pnl;
    private String positionId;
    private String positionStatus;
    private int stopLoss;
    private double swap;
    private String symbol;
    private int takeProfit;
    private int tax;
    private String tradeReason;
    private double volume;

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

    public int getCommission() {
        return commission;
    }

    public void setCommission(int commission) {
        this.commission = commission;
    }

    public int getCommissionAgent() {
        return commissionAgent;
    }

    public void setCommissionAgent(int commissionAgent) {
        this.commissionAgent = commissionAgent;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public ExtraDataBean getExtraData() {
        return extraData;
    }

    public void setExtraData(ExtraDataBean extraData) {
        this.extraData = extraData;
    }

    public int getInitialMargin() {
        return initialMargin;
    }

    public void setInitialMargin(int initialMargin) {
        this.initialMargin = initialMargin;
    }

    public int getMaintenanceMargin() {
        return maintenanceMargin;
    }

    public void setMaintenanceMargin(int maintenanceMargin) {
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

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getPositionStatus() {
        return positionStatus;
    }

    public void setPositionStatus(String positionStatus) {
        this.positionStatus = positionStatus;
    }

    public int getStopLoss() {
        return stopLoss;
    }

    public void setStopLoss(int stopLoss) {
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

    public int getTakeProfit() {
        return takeProfit;
    }

    public void setTakeProfit(int takeProfit) {
        this.takeProfit = takeProfit;
    }

    public int getTax() {
        return tax;
    }

    public void setTax(int tax) {
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

    public static class ExtraDataBean {
        /**
         * cmd : 0
         * digits : 5
         * state : 3
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

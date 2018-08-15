package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.TradeSubOrderRequest;

public class TradeSubOrderRequestMo {
    private TradeSubOrderRequest request;

    public TradeSubOrderRequestMo(String account_id,String order) {
        request = new TradeSubOrderRequest();
        request.account = account_id;
        request.order = order;
    }

    public TradeSubOrderRequest getRequest() {
        return request;
    }
}

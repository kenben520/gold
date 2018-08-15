package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.TradeAllPositionRequest;

public class TradeAllPositionRequestMo {
    private TradeAllPositionRequest request;

    public TradeAllPositionRequestMo(String account_id) {
        request = new TradeAllPositionRequest();
        request.account = account_id;
    }

    public TradeAllPositionRequest getRequest() {
        return request;
    }
}

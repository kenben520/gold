package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.TMoneyHistoryRequest;

public class TMoneyHistoryRequestMo {
    private TMoneyHistoryRequest request;

    public TMoneyHistoryRequestMo(String account_id,String startTime,String endTime) {
        request = new TMoneyHistoryRequest();
        request.account = account_id;
        request.from = startTime;
        request.to = endTime;
    }

    public TMoneyHistoryRequest getRequest() {
        return request;
    }
}

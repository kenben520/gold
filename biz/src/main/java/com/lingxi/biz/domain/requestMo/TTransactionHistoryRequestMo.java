package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.TTransactionHistoryRequest;

public class TTransactionHistoryRequestMo {

    private TTransactionHistoryRequest request;

    public TTransactionHistoryRequestMo(String account_id,String startTime,String endTime, String order) {
        request = new TTransactionHistoryRequest();
        request.account = account_id;
        request.from = startTime;
        request.to = endTime;
        request.sort = order;
    }

    public TTransactionHistoryRequest getRequest() {
        return request;
    }

}

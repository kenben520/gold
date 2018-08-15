package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.MarketRequest;

public class KMarketRequestMo {

    private MarketRequest request;

    public KMarketRequestMo() {
        request = new MarketRequest();
//        request.start_id = size;
    }

    public MarketRequest getRequest() {
        return request;
    }

}

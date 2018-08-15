package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.NewTradeRequest;

public class NewTradeRequestMo {

    private NewTradeRequest request;

    public NewTradeRequestMo(int page,int pageSize) {
        request = new NewTradeRequest();
        request.page = page;
        request.page_size = pageSize;
    }

    public NewTradeRequest getRequest() {
        return request;
    }

}

package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.IndustryRequest;

public class IndustryRequestMo {

    private IndustryRequest request;

    public IndustryRequestMo(int size,int page_size) {
        request = new IndustryRequest();
        request.page = size;
        request.page_size = page_size;
    }

    public IndustryRequest getRequest() {
        return request;
    }

}

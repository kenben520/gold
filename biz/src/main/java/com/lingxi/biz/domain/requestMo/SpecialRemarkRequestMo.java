package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.SpecialRemarkRequest;

public class SpecialRemarkRequestMo {

    private SpecialRemarkRequest request;

    public SpecialRemarkRequestMo(int page,int page_size) {
        request = new SpecialRemarkRequest();
        request.page = page;
        request.page_size = page_size;
    }

    public SpecialRemarkRequest getRequest() {
        return request;
    }

}

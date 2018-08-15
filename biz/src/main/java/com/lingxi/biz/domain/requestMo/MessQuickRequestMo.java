package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.MessQuickRequest;

public class MessQuickRequestMo {

    private MessQuickRequest request;

    public MessQuickRequestMo(int page_size,int page) {
        request = new MessQuickRequest();
        request.page_size = page_size;
        request.page = page;
    }

    public MessQuickRequest getRequest() {
        return request;
    }

}

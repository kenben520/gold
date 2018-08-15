package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.MessImportantRequest;

public class MessImportantRequestMo {

    private MessImportantRequest request;

    public MessImportantRequestMo(int count,int page) {
        request = new MessImportantRequest();
        request.page = page;
        request.count = count;
    }

    public MessImportantRequest getRequest() {
        return request;
    }

}

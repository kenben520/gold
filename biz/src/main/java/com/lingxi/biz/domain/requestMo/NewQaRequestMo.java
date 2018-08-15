package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.NewQARequest;

public class NewQaRequestMo {

    private NewQARequest request;

    public NewQaRequestMo(int page,int pageSize) {
        request = new NewQARequest();
        request.page = page;
        request.page_size = pageSize;
    }

    public NewQARequest getRequest() {
        return request;
    }

}

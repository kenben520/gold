package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.NewNoticeRequest;

public class NewNoticeRequestMo {

    private NewNoticeRequest request;

    public NewNoticeRequestMo(int size,int page,int type) {
        request = new NewNoticeRequest();
        request.page_size = size;
        request.page = page;
        request.type = type;
    }

    public NewNoticeRequest getRequest() {
        return request;
    }

}

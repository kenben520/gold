package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.NewKlineRequest;

public class NewKlineRequestMo {

    private NewKlineRequest request;

    public NewKlineRequestMo(int page,int pageSize) {
        request = new NewKlineRequest();
        request.page = page;
        request.page_size = pageSize;
    }

    public NewKlineRequest getRequest() {
        return request;
    }

}

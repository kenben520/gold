package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.NewKlineRequest;
import com.lingxi.biz.domain.request.NewRecomRequest;

public class NewsRecomRequestMo {

    private NewRecomRequest request;

    public NewsRecomRequestMo(int page_size, int size) {
        request = new NewRecomRequest();
        request.page_size = page_size;
        request.page = size;
    }

    public NewRecomRequest getRequest() {
        return request;
    }

}

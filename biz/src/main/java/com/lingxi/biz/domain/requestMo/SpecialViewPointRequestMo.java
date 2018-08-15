package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.MessQuickRequest;
import com.lingxi.biz.domain.request.SpecialViewPointRequest;

public class SpecialViewPointRequestMo {

    private SpecialViewPointRequest request;

    public SpecialViewPointRequestMo(int page,int pageSize) {
        request = new SpecialViewPointRequest();
        request.page = page;
        request.page_size = pageSize;
    }

    public SpecialViewPointRequest getRequest() {
        return request;
    }

}

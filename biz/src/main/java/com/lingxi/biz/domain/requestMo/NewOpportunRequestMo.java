package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.NewKlineRequest;
import com.lingxi.biz.domain.request.NewOpportunityRequest;

public class NewOpportunRequestMo {

    private NewOpportunityRequest request;

    public NewOpportunRequestMo(int size,int page_size) {
        request = new NewOpportunityRequest();
        request.page = size;
        request.page_size = page_size;
    }

    public NewOpportunityRequest getRequest() {
        return request;
    }

}

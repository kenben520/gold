package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.NewFundamentalAnalysisRequest;
import com.lingxi.biz.domain.request.NewQARequest;

public class NewFundAnalysisRequestMo {

    private NewFundamentalAnalysisRequest request;

    public NewFundAnalysisRequestMo(int page,int pageSize) {
        request = new NewFundamentalAnalysisRequest();
        request.page = page;
        request.page_size = pageSize;
    }

    public NewFundamentalAnalysisRequest getRequest() {
        return request;
    }

}

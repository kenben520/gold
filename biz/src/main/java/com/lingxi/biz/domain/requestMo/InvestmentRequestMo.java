package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.InvestmentRequest;
import com.lingxi.biz.domain.request.KnowledgeRequest;

/**
 * Created by zhangwei on 2018/4/4.
 */

public class InvestmentRequestMo {

    private InvestmentRequest request;

    public InvestmentRequestMo(int size,int page_size) {
        request = new InvestmentRequest();
        request.page = size;
        request.page_size = page_size;
    }

    public InvestmentRequest getRequest() {
        return request;
    }

}

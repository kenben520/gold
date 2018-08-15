package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.GetAgreementRequest;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class GetAgreementRequestMo {
    private GetAgreementRequest request;

    public GetAgreementRequestMo(String article_id) {
        request = new GetAgreementRequest();
        request.article_id = article_id;
    }

    public GetAgreementRequest getRequest() {
        return request;
    }
}

package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.GetLivePeopleRequest;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class GetLivePeopleCountRequestMo {
    private GetLivePeopleRequest request;

    public GetLivePeopleCountRequestMo() {
        request = new GetLivePeopleRequest();
    }

    public GetLivePeopleRequest getRequest() {
        return request;
    }
}

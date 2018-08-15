package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.GetAdRequest;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class GetAdRequestMo {
    private GetAdRequest request;

    public GetAdRequestMo(int type) {
        request = new GetAdRequest();
        request.type = type;
    }

    public GetAdRequest getRequest() {
        return request;
    }
}

package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.LiveStatusRequest;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class LiveStatusRequestMo {
    private LiveStatusRequest request;

    public LiveStatusRequestMo() {
        request = new LiveStatusRequest();
    }

    public LiveStatusRequest getRequest() {
        return request;
    }
}

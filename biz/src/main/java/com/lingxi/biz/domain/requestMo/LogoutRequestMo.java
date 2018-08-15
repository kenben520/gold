package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.LogoutRequest;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class LogoutRequestMo {
    private LogoutRequest request;

    public LogoutRequestMo(String user_id) {
        request = new LogoutRequest();
        request.user_id = user_id;
    }

    public LogoutRequest getRequest() {
        return request;
    }
}

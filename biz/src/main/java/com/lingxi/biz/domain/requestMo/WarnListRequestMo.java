package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.WarnListRequest;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class WarnListRequestMo {
    private WarnListRequest request;

    public WarnListRequestMo(String user_id) {
        request = new WarnListRequest();
        request.user_id = user_id;
    }

    public WarnListRequest getRequest() {
        return request;
    }
}

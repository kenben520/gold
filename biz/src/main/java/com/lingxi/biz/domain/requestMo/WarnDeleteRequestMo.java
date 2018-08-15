package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.WarnDeleteRequest;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class WarnDeleteRequestMo {
    private WarnDeleteRequest request;

    public WarnDeleteRequestMo(String user_id, String inc_id) {
        request = new WarnDeleteRequest();
        request.user_id = user_id;
        request.inc_id = inc_id;
    }

    public WarnDeleteRequest getRequest() {
        return request;
    }
}

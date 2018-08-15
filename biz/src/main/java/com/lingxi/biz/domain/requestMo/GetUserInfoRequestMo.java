package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.GetUserInfoRequest;

/**
 * Created by zhangwei on 2018/4/17.
 * 个人资料信息
 */

public class GetUserInfoRequestMo {
    private GetUserInfoRequest request;

    public GetUserInfoRequestMo(String user_id) {
        request = new GetUserInfoRequest();
        request.user_id = user_id;
    }

    public GetUserInfoRequest getRequest() {
        return request;
    }
}

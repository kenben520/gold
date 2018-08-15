package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.GetAccountInfoRequest;

/**
 * Created by zhangwei on 2018/5/18.
 * 账户资金信息
 */

public class GetAccountInfoRequestMo {
    private GetAccountInfoRequest request;

    public GetAccountInfoRequestMo(String account_id) {
        request = new GetAccountInfoRequest();
        request.account = account_id;
    }

    public GetAccountInfoRequest getRequest() {
        return request;
    }
}

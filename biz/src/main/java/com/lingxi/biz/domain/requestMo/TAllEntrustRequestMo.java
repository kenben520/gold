package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.TAllEntrustRequest;

public class TAllEntrustRequestMo {
    private TAllEntrustRequest request;

    public TAllEntrustRequestMo(String account_id) {
        request = new TAllEntrustRequest();
        request.account = account_id;
    }

    public TAllEntrustRequest getRequest() {
        return request;
    }

}

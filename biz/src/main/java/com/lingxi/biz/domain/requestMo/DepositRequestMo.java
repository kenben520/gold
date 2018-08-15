package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.DepositRequest;

/**
 * Created by zhangwei on 2018/5/20.
 */

public class DepositRequestMo {
    private DepositRequest request;

    public DepositRequestMo(String account_id, double amount, String comment) {
        request = new DepositRequest();
        request.account = account_id;
        request.amount = amount;
//        request.comment = comment;
    }

    public DepositRequest getRequest() {
        return request;
    }
}

package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.WithdrawRequest;

/**
 * Created by zhangwei on 2018/5/20.
 */

public class WithdrawRequestMo {
    private WithdrawRequest request;

    public WithdrawRequestMo(String account_id, double amount, String comment) {
        request = new WithdrawRequest();
        request.account = account_id;
        request.amount = amount;
//        request.comment = comment;
    }

    public WithdrawRequest getRequest() {
        return request;
    }
}

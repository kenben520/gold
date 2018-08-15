package com.lingxi.biz.domain.request;

import com.google.gson.annotations.SerializedName;
import com.lingxi.biz.base.BaseRequest_1;

/**
 * Created by zhangwei on 2018/5/20.
 * 账户入金
 */

public class DepositRequest extends BaseRequest_1 {
    public String account;
    public double amount;
    public transient String url = "v2/account/deposit";

}

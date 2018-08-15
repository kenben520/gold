package com.lingxi.biz.domain.request;

import com.lingxi.biz.base.BaseRequest;

public class TMoneyHistoryRequest extends BaseRequest {
    public String account;
    public String from;
    public String to;
    //    public String comment;
    public transient String url = "v2/account/funding/history";
}

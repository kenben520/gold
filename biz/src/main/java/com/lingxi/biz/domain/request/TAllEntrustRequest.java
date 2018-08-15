package com.lingxi.biz.domain.request;

import com.lingxi.biz.base.BaseRequest;

public class TAllEntrustRequest extends BaseRequest {
    public String account;
    public transient String url = "v2/trade/portfolio/orders";
}

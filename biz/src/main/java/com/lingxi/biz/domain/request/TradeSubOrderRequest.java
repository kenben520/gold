package com.lingxi.biz.domain.request;

import com.lingxi.biz.base.BaseRequest;

public class TradeSubOrderRequest extends BaseRequest {

    public String account;
    public String order;
    //    public String comment;
    public transient String url = "v2/trade/orders";

}

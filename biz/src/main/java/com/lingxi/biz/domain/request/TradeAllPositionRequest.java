package com.lingxi.biz.domain.request;

import com.lingxi.biz.base.BaseRequest;

public class TradeAllPositionRequest extends BaseRequest {
    public String account;
    //    public String comment;
    public transient String url = "v2/trade/portfolio/positions";
}

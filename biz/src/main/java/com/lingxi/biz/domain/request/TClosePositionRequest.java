package com.lingxi.biz.domain.request;

import com.lingxi.biz.base.BaseRequest;

public class TClosePositionRequest extends BaseRequest {
    public String account;
    public String positionId;
    public double volume;
    //    public String comment;
    public transient String url = "v2/trade/portfolio/positions";
}

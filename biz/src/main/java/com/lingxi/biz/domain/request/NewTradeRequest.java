package com.lingxi.biz.domain.request;

import com.lingxi.biz.base.BaseRequest;

public class NewTradeRequest extends BaseRequest {
    //模块名称
    public String app = "article";
    //动作名称
    public String act = "trade_app";

    public int  page;
    public int  page_size;
}

package com.lingxi.biz.domain.request;

import com.lingxi.biz.base.BaseRequest;

public class TTransactionHistoryRequest extends BaseRequest {
    public String account;
    public String from;
    public String to;
    public String sort;

    public transient String url = "v2/trade/portfolio/history/trades";
}

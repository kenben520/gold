package com.lingxi.biz.domain.request;

import com.lingxi.biz.base.BaseRequest;

public class KlineChartRequest extends BaseRequest {
    //模块名称
    public String app = "market";
    //动作名称
    public String act = "get_market_chart_app";

    public String  prod_code;
    public int candle_period;
    public int data_count;

}

package com.lingxi.biz.domain.request;

import com.lingxi.biz.base.BaseRequest;

public class MessFinanceRequest extends BaseRequest {
    //模块名称
    public String app = "market";
    //动作名称
    public String act = "get_finance_app";

    public String  country;
    public String  calendar_type;//日历类型 VN 假期 FE 事件 FD 数据
    public int  subscribe_status;//是否已公布 0已公布 1 “未公布”
    public int  stars;
    public long  cur_date;
    public int page_size;
    public int page;
}

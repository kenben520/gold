package com.lingxi.biz.domain.request;

import com.lingxi.biz.base.BaseRequest;

public class NewOpportunityRequest extends BaseRequest {
    //模块名称
    public String app = "article";
    //动作名称
    public String act = "opportunity_app";

    public int  page_size;
    public int  page;
}

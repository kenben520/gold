package com.lingxi.biz.domain.request;

import com.lingxi.biz.base.BaseRequest;

public class MessExpertRequest extends BaseRequest {
    //模块名称
    public String app = "article";
    //动作名称
    public String act = "expert_app";

    public int  start_id;
}

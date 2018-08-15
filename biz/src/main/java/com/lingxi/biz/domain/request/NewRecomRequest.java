package com.lingxi.biz.domain.request;

import com.lingxi.biz.base.BaseRequest;

public class NewRecomRequest extends BaseRequest {
    //模块名称
    public String app = "news";
    //动作名称
    public String act = "index_app";

    public int  page;
    public int page_size;
}

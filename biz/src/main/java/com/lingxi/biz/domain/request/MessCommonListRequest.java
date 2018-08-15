package com.lingxi.biz.domain.request;

import com.lingxi.biz.base.BaseRequest;

public class MessCommonListRequest extends BaseRequest {
    //模块名称
    public String app = "news";
    //动作名称
    public String act = "list_app";

    public int  page_size;
    public int  page;
    public int cate_id;

}

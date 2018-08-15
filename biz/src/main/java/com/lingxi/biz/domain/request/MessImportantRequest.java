package com.lingxi.biz.domain.request;

import com.lingxi.biz.base.BaseRequest;

public class MessImportantRequest extends BaseRequest {
    //模块名称
    public String app = "grap";
    //动作名称
    public String act = "app_important_list";

    public int  count;
    public int  page;
}

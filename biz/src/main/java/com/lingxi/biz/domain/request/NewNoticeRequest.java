package com.lingxi.biz.domain.request;

import com.lingxi.biz.base.BaseRequest;

public class NewNoticeRequest extends BaseRequest {
    //模块名称
    public String app = "notice";
    //动作名称
    public String act = "app_notice_list";

    public int  page;
    public int  page_size;
    public int type;
}

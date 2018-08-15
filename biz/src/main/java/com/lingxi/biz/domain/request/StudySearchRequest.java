package com.lingxi.biz.domain.request;

import com.lingxi.biz.base.BaseRequest;

public class StudySearchRequest extends BaseRequest {
    //模块名称
    public String app = "article";
    //动作名称
    public String act = "search_app";

    public String type;
    public String keywords;
}

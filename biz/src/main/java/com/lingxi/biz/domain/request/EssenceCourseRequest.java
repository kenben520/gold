package com.lingxi.biz.domain.request;

import com.lingxi.biz.base.BaseRequest;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class EssenceCourseRequest extends BaseRequest {
    //模块名称
    public String app = "online";
    //动作名称
    public String act = "course_app";

    public int page_size;
    public int page;
}

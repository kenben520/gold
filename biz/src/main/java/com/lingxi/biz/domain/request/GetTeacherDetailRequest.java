package com.lingxi.biz.domain.request;

import com.lingxi.biz.base.BaseRequest;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class GetTeacherDetailRequest extends BaseRequest {
    //模块名称
    public String app = "teacher";
    //动作名称
    public String act = "view_app";
    public String teacher_id;
}

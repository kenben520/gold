package com.lingxi.biz.domain.request;

import com.lingxi.biz.base.BaseRequest;

public class GetTeacherOpinionsVideoRequest extends BaseRequest {
    //模块名称
    public String app = "teacher";
    //动作名称
    public String act = "viewpoint_list_app";

    public String teacher_id;
    public int page_size;
    public int page;
}

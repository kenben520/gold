package com.lingxi.biz.domain.request;

import com.lingxi.biz.base.BaseRequest;

public class GetTeacherVideosVideoRequest extends BaseRequest {
    //模块名称
    public String app = "teacher";
    //动作名称
    public String act = "video_list_app";

    public String teacher_id;
    public int page_size;
    public int page;
}

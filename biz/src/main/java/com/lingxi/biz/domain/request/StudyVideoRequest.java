package com.lingxi.biz.domain.request;

import com.lingxi.biz.base.BaseRequest;

public class StudyVideoRequest extends BaseRequest {
    //模块名称
    public String app = "teachervideo";
    //动作名称
    public String act = "teacher_video_list_app";

    public int type;
    public int page_size;
    public int  page;
}

package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.GetTeacherVideosVideoRequest;

public class TeacherVideosRequestMo {

    private GetTeacherVideosVideoRequest request;

    public TeacherVideosRequestMo(String teacher_id, int page, int page_size) {
        request = new GetTeacherVideosVideoRequest();
        request.page = page;
        request.teacher_id = teacher_id;
        request.page_size = page_size;
    }

    public GetTeacherVideosVideoRequest getRequest() {
        return request;
    }

}

package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.GetTeacherOpinionsVideoRequest;

public class TeacherOpinionsRequestMo {

    private GetTeacherOpinionsVideoRequest request;

    public TeacherOpinionsRequestMo(String teacher_id, int page, int page_size) {
        request = new GetTeacherOpinionsVideoRequest();
        request.page = page;
        request.teacher_id = teacher_id;
        request.page_size = page_size;
    }

    public GetTeacherOpinionsVideoRequest getRequest() {
        return request;
    }

}

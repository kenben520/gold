package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.GetTeacherDetailRequest;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class GetTeacherDetailRequestMo {
    private GetTeacherDetailRequest request;

    public GetTeacherDetailRequestMo(String teacherId) {
        request = new GetTeacherDetailRequest();
        request.teacher_id = teacherId;
    }

    public GetTeacherDetailRequest getRequest() {
        return request;
    }
}

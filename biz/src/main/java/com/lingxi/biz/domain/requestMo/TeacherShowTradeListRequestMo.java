package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.TeacherShowTradeListRequest;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class TeacherShowTradeListRequestMo {
    private TeacherShowTradeListRequest request;

    public TeacherShowTradeListRequestMo(String teacher_id,int page, int count) {
        request = new TeacherShowTradeListRequest();
        request.page = page;
        request.page_size = count;
        request.teacher_id = teacher_id;
    }

    public TeacherShowTradeListRequest getRequest() {
        return request;
    }
}

package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.EssenceCourseRequest;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class EssenceCourseRequestMo {
    private EssenceCourseRequest request;

    public EssenceCourseRequestMo(int page, int count) {
        request = new EssenceCourseRequest();
        request.page = page;
        request.page_size = count;
    }

    public EssenceCourseRequest getRequest() {
        return request;
    }
}

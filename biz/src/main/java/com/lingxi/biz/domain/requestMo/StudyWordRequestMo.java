package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.StudyWordsRequest;

public class StudyWordRequestMo {

    private StudyWordsRequest request;

    public StudyWordRequestMo(int page,int pageSize) {
        request = new StudyWordsRequest();
        request.page = page;
        request.page_size = pageSize;
    }

    public StudyWordsRequest getRequest() {
        return request;
    }

}

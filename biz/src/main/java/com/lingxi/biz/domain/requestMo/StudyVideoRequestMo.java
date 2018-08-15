package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.StudyVideoRequest;
import com.lingxi.biz.domain.request.StudyWordsRequest;

public class StudyVideoRequestMo {

    private StudyVideoRequest request;

    public StudyVideoRequestMo(int type,int page_size, int page) {
        request = new StudyVideoRequest();
        request.page = page;
        request.type = type;
        request.page_size = page_size;
    }

    public StudyVideoRequest getRequest() {
        return request;
    }

}

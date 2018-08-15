package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.StudySearchRequest;

public class StudySearchRequestMo {

    private StudySearchRequest request;

    public StudySearchRequestMo(String type, String keywords) {
        request = new StudySearchRequest();
        request.type = type;
        request.keywords = keywords;
    }

    public StudySearchRequest getRequest() {
        return request;
    }

}

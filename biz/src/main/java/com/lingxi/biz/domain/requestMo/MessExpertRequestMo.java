package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.MessExpertRequest;

public class MessExpertRequestMo {

    private MessExpertRequest request;

    public MessExpertRequestMo(int size) {
        request = new MessExpertRequest();
        request.start_id = size;
    }

    public MessExpertRequest getRequest() {
        return request;
    }

}

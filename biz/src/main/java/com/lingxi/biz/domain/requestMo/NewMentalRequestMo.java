package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.NewMentalityRequest;

public class NewMentalRequestMo {

    private NewMentalityRequest request;

    public NewMentalRequestMo(int size,int pagesize) {
        request = new NewMentalityRequest();
        request.page = size;
        request.page_size = pagesize;
    }

    public NewMentalityRequest getRequest() {
        return request;
    }

}

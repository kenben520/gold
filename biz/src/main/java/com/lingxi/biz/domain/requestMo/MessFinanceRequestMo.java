package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.MessFinanceRequest;

public class MessFinanceRequestMo {

    private MessFinanceRequest request;

    public MessFinanceRequestMo(String  country,String  calendar_type,int  subscribe_status,int  stars,long  cur_date,int page_size,int page) {
        request = new MessFinanceRequest();
        request.country = country;
        request.calendar_type = calendar_type;
        request.subscribe_status = subscribe_status;
        request.stars = stars;
        request.cur_date = cur_date;
        request.page = page;
        request.page_size = page_size;
    }

    public MessFinanceRequest getRequest() {
        return request;
    }

}

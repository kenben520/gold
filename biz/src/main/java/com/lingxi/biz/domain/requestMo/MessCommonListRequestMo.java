package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.MessCommonListRequest;
import com.lingxi.biz.domain.request.MessQuickRequest;

public class MessCommonListRequestMo {

    private MessCommonListRequest request;

    public MessCommonListRequestMo(int cate_id, int page_size, int page) {
        request = new MessCommonListRequest();
        request.page_size = page_size;
        request.page = page;
        request.cate_id = cate_id;
    }

    public MessCommonListRequest getRequest() {
        return request;
    }

}

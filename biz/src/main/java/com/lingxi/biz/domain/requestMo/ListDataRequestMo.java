package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.ListDataRequest;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class ListDataRequestMo {
    private ListDataRequest request;

    public ListDataRequestMo(int page, int count) {
        request = new ListDataRequest();
        request.page = page;
        request.count = count;
    }

    public ListDataRequest getRequest() {
        return request;
    }
}

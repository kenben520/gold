package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.ListDataRequest2;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class ListDataRequestMo2 {
    private ListDataRequest2 request;

    public ListDataRequestMo2(int start_id) {
        request = new ListDataRequest2();
        request.start_id = start_id;
    }

    public ListDataRequest2 getRequest() {
        return request;
    }
}

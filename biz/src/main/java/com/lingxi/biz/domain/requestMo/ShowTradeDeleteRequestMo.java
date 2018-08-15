package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.ShowTradeDeleteRequest;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class ShowTradeDeleteRequestMo {
    private ShowTradeDeleteRequest request;

    public ShowTradeDeleteRequestMo(String user_id, String sheets_id) {
        request = new ShowTradeDeleteRequest();
        request.user_id = user_id;
        request.sheets_id = sheets_id;
    }

    public ShowTradeDeleteRequest getRequest() {
        return request;
    }
}

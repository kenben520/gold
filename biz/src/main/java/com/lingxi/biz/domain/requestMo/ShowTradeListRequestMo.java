package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.ShowTradeListRequest;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class ShowTradeListRequestMo {
    private ShowTradeListRequest request;

    public ShowTradeListRequestMo(int page, int count, String user_id,int type) {
        request = new ShowTradeListRequest();
        request.page = page;
        request.page_size = count;
        request.she_user_id = user_id;
        request.type = type;
    }

    public ShowTradeListRequestMo(int page, int count,int type) {
        request = new ShowTradeListRequest();
        request.page = page;
        request.page_size = count;
        request.type = type;
    }

    public ShowTradeListRequest getRequest() {
        return request;
    }
}

package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.ShowTradeRequest;

/**
 * Created by zhangwei on 2018/5/20.
 */

public class ShowTradeRequestMo {
    private ShowTradeRequest request;

    public ShowTradeRequestMo(String user_id, String url, String she_heart) {
        request = new ShowTradeRequest();
        request.she_heart = she_heart;
        request.she_image = url;
        request.she_user_id = user_id;
    }

    public ShowTradeRequest getRequest() {
        return request;
    }
}

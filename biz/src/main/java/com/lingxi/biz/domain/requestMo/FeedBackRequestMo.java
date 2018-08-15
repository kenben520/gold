package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.FeedBackRequest;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class FeedBackRequestMo {
    private FeedBackRequest request;

    public FeedBackRequestMo(String user_mobile, String content, String pic) {
        request = new FeedBackRequest();
        request.user_mobile = user_mobile;
        request.content = content;
        request.image_url = pic;
    }

    public FeedBackRequest getRequest() {
        return request;
    }
}

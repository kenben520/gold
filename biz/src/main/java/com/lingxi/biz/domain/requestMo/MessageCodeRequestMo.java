package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.MessageCodeRequest;

public class MessageCodeRequestMo {

    private MessageCodeRequest request;

    public MessageCodeRequestMo(String mobile, String scene) {
        request = new MessageCodeRequest();
        request.mobile = mobile;
        request.scene = scene;
    }

    public MessageCodeRequest getRequest() {
        return request;
    }

}

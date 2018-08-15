package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.UploadPicRequest;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class UploadPicRequestMo {
    private UploadPicRequest request;

    public UploadPicRequestMo(String pictures) {
        request = new UploadPicRequest();
        request.pictures = pictures;
    }

    public UploadPicRequest getRequest() {
        return request;
    }
}

package com.lingxi.biz.service;

import com.lingxi.biz.base.BizNetDefaultListener;
import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.UploadPicRequestMo;
import com.lingxi.biz.domain.response.UploadPicResponse;
import com.lingxi.biz.domain.responseMo.UploadPicResultMo;
import com.lingxi.net.NetWorker;
import com.lingxi.net.basenet.MethodEnum;
import com.lingxi.net.biznet.BizNetRequest;

/**
 * Created by zhangwei on 2018/4/2.
 */

public class UploadService {
    public static void uploadPic(UploadPicRequestMo uploadPicRequestMo, BizResultListener<UploadPicResultMo> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(uploadPicRequestMo.getRequest(), UploadPicResponse.class, 1, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        netWorker.asyncRequest(bizNetRequest);
    }
}

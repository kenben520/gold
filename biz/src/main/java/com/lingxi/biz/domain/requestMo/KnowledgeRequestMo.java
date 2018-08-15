package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.KnowledgeRequest;
import com.lingxi.biz.domain.request.LoginRequest;
import com.lingxi.net.basenet.util.SecurityUtils;

/**
 * Created by zhangwei on 2018/4/4.
 */

public class KnowledgeRequestMo {

    private KnowledgeRequest request;

    public KnowledgeRequestMo() {
        request = new KnowledgeRequest();
    }

    public KnowledgeRequest getRequest() {
        return request;
    }

}

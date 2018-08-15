package com.lingxi.biz.domain.request;

import com.lingxi.biz.base.BaseRequest;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class GetAgreementRequest extends BaseRequest {
    //模块名称
    public String app = "adv";
    //动作名称
    public String act = "myAgreement_app";

    public String article_id;
}

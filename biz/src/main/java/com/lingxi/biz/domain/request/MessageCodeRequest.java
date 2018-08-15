package com.lingxi.biz.domain.request;

import com.lingxi.biz.base.BaseRequest;
import com.lingxi.biz.base.BaseRequest_1;

/**
 * Created by zhangwei on 2018/5/20.
 * 账户入金
 */

public class MessageCodeRequest extends BaseRequest {
    //模块名称
    public String app = "sms";
    //动作名称
    public String act = "send";

    public String  mobile;
    public String  scene;
    public String captcha;
    public int from = 1;
}

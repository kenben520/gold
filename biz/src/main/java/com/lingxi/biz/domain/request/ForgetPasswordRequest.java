package com.lingxi.biz.domain.request;

import com.lingxi.biz.base.BaseRequest;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class ForgetPasswordRequest extends BaseRequest {
    //模块名称
    public String app = "api";
    //动作名称
    public String act = "forget_password_app";
    public String phone_mob;
    public String new_password;
    public String id_card;
    public String code;
}

package com.lingxi.biz.domain.request;

import com.lingxi.biz.base.BaseRequest;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class RegisterRequest extends BaseRequest {
    //模块名称
    public String app = "api";
    //动作名称
    public String act = "user_register_app";

    public String phone_mob;

    public String code;
    public String id_card;
    public String user_name;
}

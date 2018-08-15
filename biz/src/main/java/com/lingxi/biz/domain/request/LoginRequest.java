package com.lingxi.biz.domain.request;

import com.lingxi.biz.base.BaseRequest;

/**
 * Created by zhangwei on 2018/4/3.
 */

public class LoginRequest extends BaseRequest {
    //模块名称
    public String app = "api";
    //动作名称
    public String act = "user_login_app";

    public String phone_mob;

    public String password;
}

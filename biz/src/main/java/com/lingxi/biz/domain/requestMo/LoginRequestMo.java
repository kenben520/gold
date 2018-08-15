package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.LoginRequest;
import com.lingxi.net.basenet.util.SecurityUtils;

/**
 * Created by zhangwei on 2018/4/4.
 */

public class LoginRequestMo {

    private LoginRequest request;

    public LoginRequestMo(String phone_mob, String password) {
        request = new LoginRequest();
        request.phone_mob = phone_mob;
        request.password = SecurityUtils.getMd5(password + "lx");
    }

    public LoginRequest getRequest() {
        return request;
    }
}

package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.LoginRequest;
import com.lingxi.biz.domain.request.RegisterRequest;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class RegisterRequestMo {
    private RegisterRequest request;

    public RegisterRequestMo(String phone_mob, String code,String id_card, String user_name) {
        request = new RegisterRequest();
        request.phone_mob=phone_mob;
        request.code=code;
        request.id_card=id_card;
        request.user_name=user_name;
    }

    public RegisterRequest getRequest() {
        return request;
    }
}

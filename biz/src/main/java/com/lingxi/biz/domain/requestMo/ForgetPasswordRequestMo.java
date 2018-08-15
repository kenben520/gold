package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.ForgetPasswordRequest;
import com.lingxi.net.basenet.util.SecurityUtils;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class ForgetPasswordRequestMo {
    private ForgetPasswordRequest request;

    public ForgetPasswordRequestMo(String phone_mob, String code, String id_card, String new_password) {
        request = new ForgetPasswordRequest();
        request.phone_mob = phone_mob;
        request.code = code;
        request.new_password = SecurityUtils.getMd5(new_password + "lx");
        request.id_card = id_card;
    }

    public ForgetPasswordRequest getRequest() {
        return request;
    }
}

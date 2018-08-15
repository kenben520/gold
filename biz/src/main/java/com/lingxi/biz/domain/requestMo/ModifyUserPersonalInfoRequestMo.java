package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.ModifyUserPersonalInfoRequest;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class ModifyUserPersonalInfoRequestMo {
    private ModifyUserPersonalInfoRequest request;

    public ModifyUserPersonalInfoRequestMo(String userId, String avatar, String nick_name, String email) {
        request = new ModifyUserPersonalInfoRequest();
        request.user_id = userId;
        request.avatars = avatar;
        request.nick_name = nick_name;
        request.email = email;
    }

    public ModifyUserPersonalInfoRequest getRequest() {
        return request;
    }
}

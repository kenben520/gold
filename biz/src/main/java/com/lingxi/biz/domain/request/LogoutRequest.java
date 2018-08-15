package com.lingxi.biz.domain.request;

import com.lingxi.biz.base.BaseRequest;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class LogoutRequest extends BaseRequest {
    //模块名称
    public String app = "api";
    //动作名称
    public String act = "logout_app";

    public String user_id;
}

package com.lingxi.biz.domain.request;

import com.lingxi.biz.base.BaseRequest;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class FeedBackRequest extends BaseRequest {
    //模块名称
    public String app = "feedback";
    //动作名称
    public String act = "add_app";

    public String user_mobile;
    public String content;
    public String image_url;
}

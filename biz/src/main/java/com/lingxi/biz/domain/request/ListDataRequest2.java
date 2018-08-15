package com.lingxi.biz.domain.request;

import com.lingxi.biz.base.BaseRequest;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class ListDataRequest2 extends BaseRequest {

    //模块名称
    public String app = "article";
    //动作名称
    public String act = "common_app";
    public int start_id;
}

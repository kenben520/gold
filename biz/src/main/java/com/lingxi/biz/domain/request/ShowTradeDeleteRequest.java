package com.lingxi.biz.domain.request;

import com.lingxi.biz.base.BaseRequest;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class ShowTradeDeleteRequest extends BaseRequest {
    //模块名称
    public String app = "online";
    //动作名称
    public String act = "sheets_delete_app";
    public String user_id;
    public String sheets_id;
}

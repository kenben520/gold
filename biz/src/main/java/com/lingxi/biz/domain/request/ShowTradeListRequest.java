package com.lingxi.biz.domain.request;

import com.lingxi.biz.base.BaseRequest;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class ShowTradeListRequest extends BaseRequest {
    //模块名称
    public String app = "online";
    //动作名称
    public String act = "sheets_list_app";

    public int page_size;
    public int page;
    public int type;
    public String she_user_id;//如果查询 我的晒单 则为必传
}

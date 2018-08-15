package com.lingxi.biz.domain.request;

import com.lingxi.biz.base.BaseRequest;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class WarnListRequest extends BaseRequest {
    //模块名称
    public String app = "warning";
    //动作名称
    public String act = "get_user_warning_list";
    public String user_id;//如果查询 我的晒单 则为必传
}

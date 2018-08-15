package com.lingxi.biz.domain.request;

import com.lingxi.biz.base.BaseRequest;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class WarnDeleteRequest extends BaseRequest {
    //模块名称
    public String app = "warning";
    //动作名称
    public String act = "del_user_warning";
    public String user_id;
    public String inc_id;
}

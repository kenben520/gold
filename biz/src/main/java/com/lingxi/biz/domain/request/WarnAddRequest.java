package com.lingxi.biz.domain.request;

import com.lingxi.biz.base.BaseRequest;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class WarnAddRequest extends BaseRequest {
    //模块名称
    public String app = "warning";
    //动作名称
    public String act = "add_user_warning";
    public String user_id;
    public String prod_code;
    public int pro_type;
    public int pro_direction;
    public String pro_warn_price;
    public int pro_h_exp;
}

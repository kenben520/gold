package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.WarnAddRequest;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class WarnAddRequestMo {
    private WarnAddRequest request;

    public WarnAddRequestMo(String user_id, String prod_code, int pro_type, int pro_direction, String pro_warn_price, int pro_h_exp) {
        request = new WarnAddRequest();
        request.user_id = user_id;
        request.prod_code = prod_code;
        request.pro_type = pro_type;
        request.pro_direction = pro_direction;
        request.pro_warn_price = pro_warn_price;
        request.pro_h_exp = pro_h_exp;
    }

    public WarnAddRequest getRequest() {
        return request;
    }
}

package com.lingxi.biz.domain.responseMo;

import com.lingxi.biz.util.ConstantUtil;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class WarningsItemMo extends BasePushImMo {
    public String prod_code;
    public String user_id;
    public int pro_type;
    public int pro_direction;
    public String pro_warn_price;
    public long pro_h_exp;
    public long add_time;
    public long pro_trigger_time;
    public String inc_id;

    @Override
    public String getContent() {
        String content = null;
        if (ConstantUtil.XAUUSD.equals(prod_code)) {
            content = "伦敦金";
        } else if (ConstantUtil.XAGUSD.equals(prod_code)) {
            content = "伦敦银";
        } else if (ConstantUtil.USDOLLARINDEX.equals(prod_code)){
            content = "美元指数";
        }

        if (pro_type == 1) {
            content += "买入价格";
        } else if (pro_type == 2) {
            content +="买出价格";
        }

        if (pro_direction == 1) {
            content += "≥";
        } else if (pro_direction == 2) {
            content +="≤";
        }

        content += pro_warn_price;
        return content;
    }
}

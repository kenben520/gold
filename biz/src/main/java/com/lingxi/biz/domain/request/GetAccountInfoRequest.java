package com.lingxi.biz.domain.request;

import com.lingxi.biz.base.BaseRequest_1;

/**
 * Created by zhangwei on 2018/5/18.
 */

public class GetAccountInfoRequest extends BaseRequest_1 {
    public String account;
    public String url = "v2/trade/portfolio/balance";
}

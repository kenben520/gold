package com.lingxi.biz.domain.request;

import com.lingxi.biz.base.BaseRequest;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class GetSignParamsRequest extends BaseRequest {
    //模块名称
    public String app = "default";
    //动作名称
    public String act = "get_weather_app";
}

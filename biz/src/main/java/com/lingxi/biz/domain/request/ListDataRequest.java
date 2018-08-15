package com.lingxi.biz.domain.request;

import com.lingxi.biz.base.BaseRequest;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class ListDataRequest extends BaseRequest {

    //模块名称
    public String app = "notice";
    //动作名称
    public String act = "app_notice_list";
    //    count条数（默认5条）
//    page页数（默认第一页）
    public int count;
    public int page;
}

package com.lingxi.biz.domain.request;

import com.lingxi.biz.base.BaseRequest;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class ShowTradeRequest extends BaseRequest {
    //模块名称
    public String app = "online";
    //动作名称
    public String act = "sheets_upload_app";

    public String she_heart;//晒单心得
    public String she_image;//上传图片文件后的url
    public String she_user_id;//晒单会员 user_id
}

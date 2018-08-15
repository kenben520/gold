package com.lingxi.biz.domain.request;

import com.lingxi.biz.base.BaseRequest;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class SubmitUserInfoRequest extends BaseRequest {
    //模块名称
    public String app = "api";
    //动作名称
    public String act = "submit_personal_app";
    public String user_id;
    public String user_name;
    public String id_card;
    public String id_card_front;
    public String id_card_behind;
    public String bank_name;
    public String bank_account;
    public String belong_to_branch;
    public String bank_card_front;
}

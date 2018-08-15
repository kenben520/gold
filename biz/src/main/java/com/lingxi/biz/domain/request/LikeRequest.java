package com.lingxi.biz.domain.request;

import com.lingxi.biz.base.BaseRequest;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class LikeRequest extends BaseRequest {
    //模块名称
    public String app = "online";
    //动作名称
    public String act = "click_thumbsup_app";

    public String teacher_id;//直播老师表id
    public String user_id;//点赞者用户id
    public String sheets_id;//晒单者 id
    public String click_user_id;//点赞者用户id
    public int op_status;//行为操作 1点赞 2取消点赞 默认 1
}

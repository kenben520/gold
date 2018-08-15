package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.LikeRequest;

/**
 * Created by zhangwei on 2018/5/20.
 */

public class LikeRequestMo {
    private LikeRequest request;

    public LikeRequestMo(String teacher_id, String user_id, String sheets_id, String click_user_id,int op_status) {
        request = new LikeRequest();
        request.teacher_id = teacher_id;
        request.user_id = user_id;
        request.sheets_id = sheets_id;
        request.click_user_id = click_user_id;
        request.op_status = op_status;
    }
    public LikeRequest getRequest() {
        return request;
    }
}

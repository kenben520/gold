package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.SubmitUserInfoRequest;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class SubmitUserInfoRequestMo {
    private SubmitUserInfoRequest request;

    public SubmitUserInfoRequestMo(String user_id, String user_name, String id_card, String id_card_front, String id_card_behind, String bank_name,
                                   String bank_account, String belong_to_branch, String bank_card_front) {
        request = new SubmitUserInfoRequest();
        request.user_id = user_id;
        request.user_name = user_name;
        request.id_card = id_card;
        request.id_card_front = id_card_front;
        request.id_card_behind = id_card_behind;
        request.bank_name = bank_name;
        request.bank_account = bank_account;
        request.belong_to_branch = belong_to_branch;
        request.bank_card_front = bank_card_front;
    }

    public SubmitUserInfoRequest getRequest() {
        return request;
    }
}

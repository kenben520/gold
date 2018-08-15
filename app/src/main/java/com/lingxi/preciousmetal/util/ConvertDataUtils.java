package com.lingxi.preciousmetal.util;

import com.lingxi.biz.domain.responseMo.LoginResultMo;
import com.lingxi.biz.domain.responseMo.UserInfoResultMo;
import com.lingxi.preciousmetal.domain.UserInfoBean;

/**
 * Created by zhangwei on 2018/4/23.
 */

public class ConvertDataUtils
{
    public static UserInfoBean convertMoToBean(UserInfoResultMo mo) {
        UserInfoBean userInfoBean = new UserInfoBean();
        userInfoBean.id_card = mo.id_card;
        userInfoBean.user_name = mo.user_name;
        userInfoBean.nick_name = mo.nick_name;
        userInfoBean.email = mo.email;
        userInfoBean.avatars = mo.avatars;
        userInfoBean.bank_account = mo.bank_account;
        userInfoBean.bank_account_hide = mo.bank_account_hide;
        userInfoBean.bank_card_front = mo.bank_card_front;
        userInfoBean.bank_name = mo.bank_name;
        userInfoBean.belong_to_branch = mo.belong_to_branch;
        userInfoBean.id_card_behind = mo.id_card_behind;
        userInfoBean.id_card_front = mo.id_card_front;
        userInfoBean.is_certified = mo.is_certified;
        userInfoBean.fail_reason = mo.fail_reason;
        userInfoBean.phone_mob = mo.phone_mob;
        userInfoBean.user_id = mo.user_id;
        userInfoBean.user_token = mo.user_token;
        userInfoBean.user_token_time = mo.user_token_time;
        userInfoBean.balance = mo.balance;
        userInfoBean.id_card_hide = mo.id_card_hide;
        return userInfoBean;
    }

    public static UserInfoBean convertMoToBean(LoginResultMo mo) {
        UserInfoBean userInfoBean = new UserInfoBean();
        userInfoBean.id_card = mo.id_card;
        userInfoBean.user_name = mo.user_name;
        userInfoBean.email = mo.email;
        userInfoBean.nick_name = mo.nick_name;
        userInfoBean.avatars = mo.avatars;
        userInfoBean.bank_account = mo.bank_account;
        userInfoBean.bank_account_hide = mo.bank_account_hide;
        userInfoBean.bank_card_front = mo.bank_card_front;
        userInfoBean.bank_name = mo.bank_name;
        userInfoBean.belong_to_branch = mo.belong_to_branch;
        userInfoBean.id_card_behind = mo.id_card_behind;
        userInfoBean.id_card_front = mo.id_card_front;
        userInfoBean.is_certified = mo.is_certified;
        userInfoBean.fail_reason = mo.fail_reason;
        userInfoBean.phone_mob = mo.phone_mob;
        userInfoBean.user_id = mo.user_id;
        userInfoBean.user_token = mo.user_token;
        userInfoBean.user_token_time = mo.user_token_time;
        userInfoBean.balance = mo.balance;
        userInfoBean.id_card_hide = mo.id_card_hide;
        return userInfoBean;
    }
}

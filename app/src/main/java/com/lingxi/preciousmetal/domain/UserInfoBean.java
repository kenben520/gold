package com.lingxi.preciousmetal.domain;

import java.io.Serializable;

/**
 * 用户信息资料
 * Created by zhangwei on 2018/4/19.
 */

//public class UserInfoBean extends BaseBean<UserInfoResultMo> implements Serializable {
public class UserInfoBean implements Serializable {
//    public UserInfoBean(UserInfoResultMo mo) {
//        super(mo);
//    }
//
//    public String getAvatars() {
//        return mo.avatars;
//    }
//
//    public void setAvatars(String avatars) {
//        this.avatars = avatars;
//    }
//
//    public String getPhone_mob() {
//        return mo.phone_mob;
//    }
//
//    public void setPhone_mob(String phone_mob) {
//        this.phone_mob = phone_mob;
//    }
//
//    public String getUser_token() {
//        return mo.user_token;
//    }
//
//    public void setUser_token(String user_token) {
//        this.user_token = user_token;
//    }
//
//    public String getUser_token_time() {
//        return mo.user_token_time;
//    }
//
//    public void setUser_token_time(String user_token_time) {
//        this.user_token_time = user_token_time;
//    }
//
//    public String getIs_certified() {
//        return mo.is_certified;
//    }
//
//    public void setIs_certified(String is_certified) {
//        this.is_certified = is_certified;
//    }
//
//    public String getUser_id() {
//        return mo.user_id;
//    }
//
//    public void setUser_id(String user_id) {
//        this.user_id = user_id;
//    }
//
//    public String getUser_name() {
//        return mo.user_name;
//    }
//
//    public void setUser_name(String user_name) {
//        this.user_name = user_name;
//    }
//
//    public String getId_card() {
//        return mo.id_card;
//    }
//
//    public void setId_card(String id_card) {
//        this.id_card = id_card;
//    }
//
//    public String getId_card_front() {
//        return mo.id_card_front;
//    }
//
//    public void setId_card_front(String id_card_front) {
//        this.id_card_front = id_card_front;
//    }
//
//    public String getId_card_behind() {
//        return mo.id_card_behind;
//    }
//
//    public void setId_card_behind(String id_card_behind) {
//        this.id_card_behind = id_card_behind;
//    }
//
//    public String getBank_name() {
//        return mo.bank_name;
//    }
//
//    public void setBank_name(String bank_name) {
//        this.bank_name = bank_name;
//    }
//
//    public String getBank_account() {
//        return mo.bank_account;
//    }
//
//    public void setBank_account(String bank_account) {
//        this.bank_account = bank_account;
//    }
//
//    public String getBelong_to_branch() {
//        return mo.belong_to_branch;
//    }
//
//    public void setBelong_to_branch(String belong_to_branch) {
//        this.belong_to_branch = belong_to_branch;
//    }
//
//    public String getBank_card_front() {
//        return mo.bank_card_front;
//    }
//
//    public void setBank_card_front(String bank_card_front) {
//        this.bank_card_front = bank_card_front;
//    }

    public String avatars;
    public String phone_mob;
    public String user_token;
    public String user_token_time;
    //是否认证过，默认是0未认证，1是已认证 2认证中 3认证失败
    public String is_certified;
    public String fail_reason;

    public String user_id;
    public String user_name;
    public String nick_name;
    public String email;
    public String id_card;
    public String id_card_hide;
    public String id_card_front;
    public String id_card_behind;
    public String bank_name;
    public String bank_account;
    public String bank_account_hide;
    public String belong_to_branch;
    public String bank_card_front;
    public double balance;
}

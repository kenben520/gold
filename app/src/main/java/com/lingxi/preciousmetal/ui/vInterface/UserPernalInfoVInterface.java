package com.lingxi.preciousmetal.ui.vInterface;

/**
 * Created by zhangwei on 2018/4/18.
 */
public interface UserPernalInfoVInterface extends UploadPicVInterface {
    public void modifyUserInfoSuccess(String avatar,String email);

    public void modifyUserInfoFail(String errorMsg);
}

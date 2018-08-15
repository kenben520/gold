package com.lingxi.preciousmetal.ui.vInterface;

import com.lingxi.preciousmetal.domain.UserInfoBean;

/**
 * Created by zhangwei on 2018/4/18.
 */
public interface UserInfoVInterface {
    public void auditSuccess(UserInfoBean baseMo);

    public void auditFail(UserInfoBean baseMo);

    public void auditing(UserInfoBean baseMo);

    public void getUserInfoFail(String errorMsg);

    void showLoadingDialog(String message);

    void cancelLoadingDialog();
}

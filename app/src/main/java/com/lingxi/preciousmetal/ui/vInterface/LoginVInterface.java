package com.lingxi.preciousmetal.ui.vInterface;

import com.lingxi.biz.domain.responseMo.LoginResultMo;

/**
 * Created by zhangwei on 2018/4/18.
 */
public interface LoginVInterface {

    public void loginSuccess(LoginResultMo accountVo);

    public void loginFail(String errorMsg);

    void showLoadingDialog(String message);

    void cancelLoadingDialog();
}

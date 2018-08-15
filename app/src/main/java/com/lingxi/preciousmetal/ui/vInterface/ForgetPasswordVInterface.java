package com.lingxi.preciousmetal.ui.vInterface;

import com.lingxi.biz.domain.responseMo.LoginResultMo;

/**
 * Created by zhangwei on 2018/4/18.
 */
public interface ForgetPasswordVInterface {

    public void forgetSuccess(LoginResultMo accountVo);

    public void forgetFail(String errorMsg);

    void showLoadingDialog(String message);

    void cancelLoadingDialog();
}

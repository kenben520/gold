package com.lingxi.preciousmetal.ui.vInterface;

import com.lingxi.biz.domain.responseMo.BaseMo;
import com.lingxi.biz.domain.responseMo.LoginResultMo;

/**
 * Created by zhangwei on 2018/4/18.
 */
public interface UpUserInfoVInterface {
    public void uploadFrontIDPicSuccess(String url);

    public void uploadFrontIDPicFail(String errorMsg);

    public void uploadBackIDPicSuccess(String url);

    public void uploadBackIDPicFail(String errorMsg);

    public void uploadBankPicSuccess(String url);

    public void uploadBankPicFail(String errorMsg);

    public void submitUserInfoSuccess(BaseMo baseMo);

    public void submitUserInfoFail(String errorMsg);

    void goBackToMain();

    void justBack();

    void showLoadingDialog(String message);

    void cancelLoadingDialog();

}

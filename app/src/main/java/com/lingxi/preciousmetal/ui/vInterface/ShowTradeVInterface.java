package com.lingxi.preciousmetal.ui.vInterface;

import com.lingxi.biz.domain.responseMo.BaseMo;

/**
 * Created by zhangwei on 2018/4/18.
 */
public interface ShowTradeVInterface extends UploadPicVInterface {
    void submitSuccess(BaseMo baseMo);

    void submitFail(String errorMsg);

    void showLoadingDialog(String message);

    void cancelLoadingDialog();
}

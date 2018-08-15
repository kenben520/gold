package com.lingxi.preciousmetal.presenter.pIml;

import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.ShowTradeRequestMo;
import com.lingxi.biz.domain.requestMo.UploadPicRequestMo;
import com.lingxi.biz.domain.responseMo.BaseMo;
import com.lingxi.biz.domain.responseMo.UploadPicResultMo;
import com.lingxi.biz.service.AnalyseTradeService;
import com.lingxi.biz.service.UploadService;
import com.lingxi.common.util.StringUtil;
import com.lingxi.preciousmetal.presenter.BasePresenter;
import com.lingxi.preciousmetal.ui.vInterface.ShowTradeVInterface;

/**
 * Created by zhangwei on 2018/4/19.
 */

public class ShowTradePresenter extends BasePresenter<ShowTradeVInterface> {
    private BizResultListener bizResultListener;
    private BizResultListener uploadBankPicListener;

    public ShowTradePresenter(ShowTradeVInterface view) {
        super(view);
        initListener();
    }

    private void initListener() {
        bizResultListener = new BizResultListener<BaseMo>() {
            @Override
            public void onPreExecute() {
                if (isViewAttached()) {
                    getView().showLoadingDialog(null);
                }
            }

            @Override
            public void hitCache(boolean hit, BaseMo baseMo) {

            }

            @Override
            public void onSuccess(BaseMo baseMo) {
                if (isViewAttached()) {
                    getView().submitSuccess(baseMo);
                    getView().cancelLoadingDialog();
                }
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                if (isViewAttached()) {
                    getView().submitFail(bizMessage);
                    getView().cancelLoadingDialog();
                }
            }
        };

        uploadBankPicListener = new BizResultListener<UploadPicResultMo>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, UploadPicResultMo loginResultMo) {

            }

            @Override
            public void onSuccess(UploadPicResultMo uploadPicResultMo) {
                if (isViewAttached() && uploadPicResultMo != null && !StringUtil.isEmpty(uploadPicResultMo.pictures)) {
                    getView().uploadUserHeadPicSuccess(uploadPicResultMo.pictures);
                } else {
                    onFail(-1, -1, "上传失败，请重新上传");
                }
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                if (isViewAttached()) {
                    getView().uploadUserHeadPicFail(bizMessage);
                }
            }
        };
    }

    public void uploadPic(String localPicPath) {
        UploadPicRequestMo uploadPicRequestMo = new UploadPicRequestMo(localPicPath);
        UploadService.uploadPic(uploadPicRequestMo, uploadBankPicListener);
    }

    public void submit(String user_id, String text, String picUrl) {
        ShowTradeRequestMo liveListRequestMo = new ShowTradeRequestMo(user_id, picUrl, text);
        AnalyseTradeService.showTrade(liveListRequestMo, bizResultListener);
    }

}

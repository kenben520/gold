package com.lingxi.preciousmetal.presenter.pIml;

import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.GetUserInfoRequestMo;
import com.lingxi.biz.domain.requestMo.SubmitUserInfoRequestMo;
import com.lingxi.biz.domain.requestMo.UploadPicRequestMo;
import com.lingxi.biz.domain.responseMo.BaseMo;
import com.lingxi.biz.domain.responseMo.UploadPicResultMo;
import com.lingxi.biz.domain.responseMo.UserInfoResultMo;
import com.lingxi.biz.service.UploadService;
import com.lingxi.biz.service.UserService;
import com.lingxi.common.util.StringUtil;
import com.lingxi.preciousmetal.common.EventBusKeyDefine;
import com.lingxi.preciousmetal.domain.UserInfoBean;
import com.lingxi.preciousmetal.login.LoginHelper;
import com.lingxi.preciousmetal.presenter.BasePresenter;
import com.lingxi.preciousmetal.ui.vInterface.UpUserInfoVInterface;
import com.lingxi.preciousmetal.util.ConvertDataUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zhangwei on 2018/4/19.
 */

public class UpUserInfoPresenter extends BasePresenter<UpUserInfoVInterface> {
    private BizResultListener bizResultListener;
    private BizResultListener uploadFrontIdPicListener;
    private BizResultListener uploadBackIdPicListener;
    private BizResultListener uploadBankPicListener;
    private boolean submitSuccess = false;
    private BizResultListener getUserInfoListener;

    public UpUserInfoPresenter(UpUserInfoVInterface view) {
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
                    submitSuccess = true;
                    getView().submitUserInfoSuccess(baseMo);
                    getView().cancelLoadingDialog();
                }
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                if (isViewAttached()) {
                    getView().submitUserInfoFail(bizMessage);
                    getView().cancelLoadingDialog();
                }
            }
        };
        uploadFrontIdPicListener = new BizResultListener<UploadPicResultMo>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, UploadPicResultMo loginResultMo) {

            }

            @Override
            public void onSuccess(UploadPicResultMo uploadPicResultMo) {
                if (isViewAttached() && uploadPicResultMo != null && !StringUtil.isEmpty(uploadPicResultMo.pictures)) {
                    getView().uploadFrontIDPicSuccess(uploadPicResultMo.pictures);
                } else {
                    onFail(-1, -1, "上传失败，请重新上传");
                }
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                if (isViewAttached()) {
                    getView().uploadFrontIDPicFail(bizMessage);
                }
            }
        };
        uploadBackIdPicListener = new BizResultListener<UploadPicResultMo>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, UploadPicResultMo loginResultMo) {

            }

            @Override
            public void onSuccess(UploadPicResultMo uploadPicResultMo) {
                if (isViewAttached() && uploadPicResultMo != null && !StringUtil.isEmpty(uploadPicResultMo.pictures)) {
                    getView().uploadBackIDPicSuccess(uploadPicResultMo.pictures);
                } else {
                    onFail(-1, -1, "上传失败，请重新上传");
                }
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                if (isViewAttached()) {
                    getView().uploadFrontIDPicFail(bizMessage);
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
                    getView().uploadBankPicSuccess(uploadPicResultMo.pictures);
                } else {
                    onFail(-1, -1, "上传失败，请重新上传");
                }
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                if (isViewAttached()) {
                    getView().uploadBankPicFail(bizMessage);
                }
            }
        };
        getUserInfoListener = new BizResultListener<UserInfoResultMo>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, UserInfoResultMo mo) {

            }

            @Override
            public void onSuccess(UserInfoResultMo mo) {
                if (isViewAttached()) {
                    UserInfoBean userInfoBean = ConvertDataUtils.convertMoToBean(mo);
                    if (userInfoBean != null) {
                        LoginHelper.getInstance().updateLoginUserInfo(userInfoBean);
                        EventBus.getDefault().post(new EventBusKeyDefine.EventBusMsgData<String>(EventBusKeyDefine.EVENTBUS_USER_INFO_UPDATE, ""));
                    }
                }
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                if (isViewAttached()) {
                }
            }
        };
    }

    public void uploadFrontIdPic(String localPicPath) {
        UploadPicRequestMo uploadPicRequestMo = new UploadPicRequestMo(localPicPath);
        UploadService.uploadPic(uploadPicRequestMo, uploadFrontIdPicListener);
    }

    public void uploadBackIdPic(String localPicPath) {
        UploadPicRequestMo uploadPicRequestMo = new UploadPicRequestMo(localPicPath);
        UploadService.uploadPic(uploadPicRequestMo, uploadBackIdPicListener);
    }

    public void uploadBankPic(String localPicPath) {
        UploadPicRequestMo uploadPicRequestMo = new UploadPicRequestMo(localPicPath);
        UploadService.uploadPic(uploadPicRequestMo, uploadBankPicListener);
    }

    public void submitUserInfo(UserInfoBean mUserInfoBean) {
        SubmitUserInfoRequestMo submitUserInfoRequestMo = new SubmitUserInfoRequestMo(mUserInfoBean.user_id, mUserInfoBean.user_name, mUserInfoBean.id_card, mUserInfoBean.id_card_front, mUserInfoBean.id_card_behind, mUserInfoBean.bank_name,
                mUserInfoBean.bank_account, mUserInfoBean.belong_to_branch, mUserInfoBean.bank_card_front);
        UserService.submitUserInfo(submitUserInfoRequestMo, bizResultListener);
    }

    public void getUserInfo(String userId) {
        GetUserInfoRequestMo getUserInfoRequestMo = new GetUserInfoRequestMo(userId);
        UserService.getUserInfo(getUserInfoRequestMo, getUserInfoListener);
    }

    public void back() {
        if (isViewAttached()) {
            if (submitSuccess) {
                getView().goBackToMain();
            } else {
                getView().justBack();
            }
        }
    }
}

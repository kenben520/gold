package com.lingxi.preciousmetal.presenter.pIml;

import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.ForgetPasswordRequestMo;
import com.lingxi.biz.domain.requestMo.LoginRequestMo;
import com.lingxi.biz.domain.responseMo.LoginResultMo;
import com.lingxi.biz.service.UserService;
import com.lingxi.preciousmetal.common.EventBusKeyDefine;
import com.lingxi.preciousmetal.login.LoginHelper;
import com.lingxi.preciousmetal.presenter.BasePresenter;
import com.lingxi.preciousmetal.ui.vInterface.ForgetPasswordVInterface;
import com.lingxi.preciousmetal.util.ConvertDataUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zhangwei on 2018/4/19.
 */

public class ForgetPasswordPresenter extends BasePresenter<ForgetPasswordVInterface> {
    private BizResultListener bizResultListener,bizResultListener1;
    public ForgetPasswordPresenter(ForgetPasswordVInterface view) {
        super(view);
        initListener();
    }

    private void initListener() {
        bizResultListener = new BizResultListener<LoginResultMo>() {
            @Override
            public void onPreExecute() {
                if (isViewAttached()) {
                    getView().showLoadingDialog(null);
                }
            }

            @Override
            public void hitCache(boolean hit, LoginResultMo loginResultMo) {

            }

            @Override
            public void onSuccess(LoginResultMo loginResultMo) {
                if (isViewAttached()) {
                    autoLogin();
                }
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                if (isViewAttached()) {
                    getView().cancelLoadingDialog();
                    getView().forgetFail(bizMessage);
                }
            }
        };
        bizResultListener1 = new BizResultListener<LoginResultMo>() {
            @Override
            public void onPreExecute() {
            }

            @Override
            public void hitCache(boolean hit, LoginResultMo loginResultMo) {

            }

            @Override
            public void onSuccess(LoginResultMo loginResultMo) {
                if (isViewAttached()) {
                    LoginHelper.getInstance().updateLoginUserInfo(ConvertDataUtils.convertMoToBean(loginResultMo));
                    EventBus.getDefault().post(new EventBusKeyDefine.EventBusMsgData<String>(EventBusKeyDefine.EVENTBUS_LOGIN_SUCCESS, ""));
                    getView().forgetSuccess(loginResultMo);
                    getView().cancelLoadingDialog();
                }
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                if (isViewAttached()) {
                    getView().forgetFail(bizMessage);
                    getView().cancelLoadingDialog();
                }
            }
        };
    }

    public void forgetPassword(String phone, String code, String id_card, String newPassword) {
        this.phone=phone;
        this.newPassword=newPassword;
        ForgetPasswordRequestMo forgetPasswordRequestMo = new ForgetPasswordRequestMo(phone, code, id_card, newPassword);
        UserService.forgetPassword(forgetPasswordRequestMo, bizResultListener);
    }
    private String newPassword,phone;

    public void autoLogin() {
        LoginRequestMo loginRequestMo = new LoginRequestMo(phone, newPassword);
        UserService.login(loginRequestMo, bizResultListener1);
    }

}

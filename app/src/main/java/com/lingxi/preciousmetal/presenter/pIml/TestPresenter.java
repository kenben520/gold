package com.lingxi.preciousmetal.presenter.pIml;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.ForgetPasswordRequestMo;
import com.lingxi.biz.domain.responseMo.LoginResultMo;
import com.lingxi.biz.service.UserService;
import com.lingxi.preciousmetal.presenter.pInterface.ITestPresenter;
import com.lingxi.preciousmetal.ui.vInterface.ITestView;

/**
 * Created by zhangwei on 2018/3/26.
 */
public class TestPresenter implements ITestPresenter {

    private final Activity activity;
    private final ITestView mainView;
    private BizResultListener bizResultListener;

    public TestPresenter(@NonNull Activity activity, @NonNull ITestView mainView) {
        this.activity = activity;
        this.mainView = mainView;
        initListener();
        initView();
    }

    private void initListener() {
        bizResultListener = new BizResultListener<LoginResultMo>() {
            @Override
            public void onPreExecute() {
                mainView.initTab();
            }

            @Override
            public void hitCache(boolean hit, LoginResultMo wxTopicMos) {


            }

            @Override
            public void onSuccess(LoginResultMo wxTopicMos) {
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {

            }
        };
    }

    private void initView() {

    }

    @Override
    public void switchTab() {
//        LoginRequestMo loginRequestMo = new LoginRequestMo("15626475591", "12345678");
//        UserService.login(loginRequestMo, bizResultListener);

//        RegisterRequestMo registerRequestMo = new RegisterRequestMo("15626475591", "12345","441304185401010255","zwtest");
//        UserService.register(registerRequestMo, bizResultListener);
//
//        ForgetPasswordRequestMo forgetPasswordRequestMo = new ForgetPasswordRequestMo("15626475591", "652800197805071235","12345678");
//        UserService.forgetPassword(forgetPasswordRequestMo, bizResultListener);
    }
}

package com.lingxi.preciousmetal.presenter.pIml;

import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.LoginRequestMo;
import com.lingxi.biz.domain.responseMo.LoginResultMo;
import com.lingxi.biz.service.UserService;
import com.lingxi.preciousmetal.common.EventBusKeyDefine;
import com.lingxi.preciousmetal.login.LoginHelper;
import com.lingxi.preciousmetal.presenter.BasePresenter;
import com.lingxi.preciousmetal.presenter.pInterface.ILoginPresenter;
import com.lingxi.preciousmetal.ui.vInterface.LoginVInterface;
import com.lingxi.preciousmetal.util.ConvertDataUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zhangwei on 2018/4/18.
 */

public class LoginPresenter extends BasePresenter<LoginVInterface> implements ILoginPresenter {

    private BizResultListener bizResultListener;

    public LoginPresenter(LoginVInterface view) {
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
                    LoginHelper.getInstance().updateLoginUserInfo(ConvertDataUtils.convertMoToBean(loginResultMo));
                    EventBus.getDefault().post(new EventBusKeyDefine.EventBusMsgData<String>(EventBusKeyDefine.EVENTBUS_LOGIN_SUCCESS, ""));
                    getView().loginSuccess(loginResultMo);
                    getView().cancelLoadingDialog();
                }
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                if (isViewAttached()) {
                    getView().loginFail(bizMessage);
                    getView().cancelLoadingDialog();
                }
            }
        };
    }

    @Override
    public void login(String phone, String password) {
        LoginRequestMo loginRequestMo = new LoginRequestMo(phone, password);
        UserService.login(loginRequestMo, bizResultListener);
    }
}

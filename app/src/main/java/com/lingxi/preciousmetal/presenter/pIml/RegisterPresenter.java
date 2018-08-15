package com.lingxi.preciousmetal.presenter.pIml;

import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.RegisterRequestMo;
import com.lingxi.biz.domain.responseMo.LoginResultMo;
import com.lingxi.biz.service.UserService;
import com.lingxi.preciousmetal.common.EventBusKeyDefine;
import com.lingxi.preciousmetal.login.LoginHelper;
import com.lingxi.preciousmetal.presenter.BasePresenter;
import com.lingxi.preciousmetal.ui.vInterface.RegisterVInterface;
import com.lingxi.preciousmetal.util.ConvertDataUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zhangwei on 2018/4/19.
 */

public class RegisterPresenter extends BasePresenter<RegisterVInterface> {
    private BizResultListener bizResultListener;

    public RegisterPresenter(RegisterVInterface view) {
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
                    EventBus.getDefault().post(new EventBusKeyDefine.EventBusMsgData<String>(EventBusKeyDefine.EVENTBUS_REGISTER_SUCCESS, ""));
                    getView().registerSuccess(loginResultMo);
                    getView().cancelLoadingDialog();
                }
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                if (isViewAttached()) {
                    getView().cancelLoadingDialog();
                    getView().registerFail(bizMessage);
                }
            }
        };
    }

    public void register(String phone, String code, String id_card, String user_name) {
        RegisterRequestMo loginRequestMo = new RegisterRequestMo(phone, code, id_card, user_name);
        UserService.register(loginRequestMo, bizResultListener);
    }
}

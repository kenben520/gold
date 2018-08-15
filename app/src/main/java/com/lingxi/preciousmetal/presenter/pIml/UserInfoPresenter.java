package com.lingxi.preciousmetal.presenter.pIml;

import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.GetUserInfoRequestMo;
import com.lingxi.biz.domain.responseMo.UserInfoResultMo;
import com.lingxi.biz.service.UserService;
import com.lingxi.preciousmetal.common.EventBusKeyDefine;
import com.lingxi.preciousmetal.domain.UserInfoBean;
import com.lingxi.preciousmetal.login.LoginHelper;
import com.lingxi.preciousmetal.presenter.BasePresenter;
import com.lingxi.preciousmetal.ui.vInterface.UserInfoVInterface;
import com.lingxi.preciousmetal.util.ConvertDataUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zhangwei on 2018/4/19.
 */

public class UserInfoPresenter extends BasePresenter<UserInfoVInterface> {
    private BizResultListener getUserInfoListener;

    public UserInfoPresenter(UserInfoVInterface view) {
        super(view);
        initListener();
    }

    private void initListener() {
        getUserInfoListener = new BizResultListener<UserInfoResultMo>() {
            @Override
            public void onPreExecute() {
                if (isViewAttached()) {
                    getView().showLoadingDialog(null);
                }
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
                        switch (mo.is_certified) {
                            case "0":
//                            getView().auditSuccess(convertMoToBean(mo));
                                break;
                            case "1":
                                getView().auditSuccess(userInfoBean);
                                break;
                            case "2":
                                getView().auditing(userInfoBean);
                                break;
                            case "3":
                                getView().auditFail(userInfoBean);
                                break;
                        }
                    }
                    getView().cancelLoadingDialog();
                }
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                if (isViewAttached()) {
                    getView().getUserInfoFail(bizMessage);
                    getView().cancelLoadingDialog();
                }
            }
        };
    }


    public void getUserInfo(String userId) {
        GetUserInfoRequestMo getUserInfoRequestMo = new GetUserInfoRequestMo(userId);
        UserService.getUserInfo(getUserInfoRequestMo, getUserInfoListener);
    }
}

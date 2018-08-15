package com.lingxi.preciousmetal.login;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.GetAccountInfoRequestMo;
import com.lingxi.biz.domain.requestMo.GetUserInfoRequestMo;
import com.lingxi.biz.domain.responseMo.AccountInfoMo;
import com.lingxi.biz.domain.responseMo.UserInfoResultMo;
import com.lingxi.biz.service.TradeService;
import com.lingxi.biz.service.UserService;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.MyApplication;
import com.lingxi.preciousmetal.common.EventBusKeyDefine;
import com.lingxi.preciousmetal.domain.UserInfoBean;
import com.lingxi.preciousmetal.ui.widget.dialog.DialogManager;
import com.lingxi.preciousmetal.util.ConvertDataUtils;
import com.lingxi.preciousmetal.util.utilCode.ObjectUtils;
import com.lingxi.preciousmetal.util.utilCode.SPUtils;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import static com.lingxi.preciousmetal.ui.activity.PropertyDetailActivity.ACCOUNT_ID;

/**
 * Created by zhangwei on 2018/4/20.
 */

public class LoginHelper {
    private static final String TAG = LoginHelper.class.getSimpleName();

    public static final String CURRENT_USER = "currentUser";
    public static final String CURRENT_ACCOUNT = "currentAccount";
    private static LoginHelper instance;

    private UserInfoBean currentLoginUser;
    private AccountInfoMo currentAccountInfo;

    private Context context = null;


    private LoginHelper() {
        context = MyApplication.getInstance();
    }

    public synchronized static LoginHelper getInstance() {
        if (instance == null) {
            instance = new LoginHelper();
        }
        return instance;
    }


    public UserInfoBean getLoginUserInfo() {
        UserInfoBean info = null;
        if (currentLoginUser != null) {
            info = currentLoginUser;
        } else {
            String accountStr = SPUtils.getInstance().getString(CURRENT_USER);
            if (!TextUtils.isEmpty(accountStr)) {
                try {
                    info = JSON.parseObject(accountStr, UserInfoBean.class);
                } catch (Exception e) {
                    Log.e(TAG, "failed to load local user info ", e);
                }
            }
            if (info == null || TextUtils.isEmpty(info.user_id)) {
                info = null;
            }
            if (info != null) {
                currentLoginUser = info;
            }
        }
        return info;
    }


    public AccountInfoMo getAccountInfo() {
        AccountInfoMo info = null;
        if (currentAccountInfo != null) {
            info = currentAccountInfo;
        } else {
            String accountStr = SPUtils.getInstance().getString(CURRENT_ACCOUNT);
            if (!TextUtils.isEmpty(accountStr)) {
                try {
                    info = JSON.parseObject(accountStr, AccountInfoMo.class);
                } catch (Exception e) {
                    Log.e(TAG, "failed to load local user info ", e);
                }
            }
            if (info == null || TextUtils.isEmpty(info.getAccount())) {
                info = null;
            }
            if (info != null) {
                currentAccountInfo = info;
            }
        }
        return info;
    }

    public void updateAccountInfoMo(AccountInfoMo account) {
        currentAccountInfo = account;
        String accoutStr = "";
        if (account != null) {
            accoutStr = JSON.toJSONString(account);

        }
        SPUtils.getInstance().put(CURRENT_ACCOUNT, accoutStr);
    }

    public void updateLoginUserInfo(UserInfoBean account) {
        currentLoginUser = account;
        String accoutStr = "";
        if (account != null) {
            accoutStr = JSON.toJSONString(account);

        }
        SPUtils.getInstance().put(CURRENT_USER, accoutStr);
    }

    public void updateUserHeadUrl(String url) {
        if (TextUtils.isEmpty(url)) return;
        UserInfoBean userInfoBean = getLoginUserInfo();
        if (userInfoBean != null) {
            userInfoBean.avatars = url;
        }
        updateLoginUserInfo(userInfoBean);
    }

    public boolean isLogin() {
        UserInfoBean userInfo = LoginHelper.getInstance().getLoginUserInfo();
        if (userInfo != null && !TextUtils.isEmpty(userInfo.user_id)) {
            return true;
        }
        return false;
    }

    public void httpGetUserInfo() {
        if (isLogin()) {
            GetUserInfoRequestMo getUserInfoRequestMo = new GetUserInfoRequestMo(currentLoginUser.user_id);
            UserService.getUserInfo(getUserInfoRequestMo, new BizResultListener<UserInfoResultMo>() {
                @Override
                public void onPreExecute() {

                }

                @Override
                public void hitCache(boolean hit, UserInfoResultMo userInfoResultMo) {

                }

                @Override
                public void onSuccess(UserInfoResultMo userInfoResultMo) {
                    UserInfoBean userInfoBean = ConvertDataUtils.convertMoToBean(userInfoResultMo);
                    if (userInfoBean != null) {
                        LoginHelper.getInstance().updateLoginUserInfo(userInfoBean);
                        EventBus.getDefault().post(new EventBusKeyDefine.EventBusMsgData<String>(EventBusKeyDefine.EVENTBUS_USER_INFO_UPDATE, ""));
                    }
                }

                @Override
                public void onFail(int resultCode, int bizCode, String bizMessage) {

                }
            });
        }
    }

    public void httpGetAccountInfo() {
        if (isLogin()) {
            GetAccountInfoRequestMo loginRequestMo = new GetAccountInfoRequestMo(ACCOUNT_ID);
            TradeService.getAccountInfo(loginRequestMo, new BizResultListener<AccountInfoMo>() {
                @Override
                public void onPreExecute() {
                }

                @Override
                public void hitCache(boolean hit, AccountInfoMo accountInfoMo) {

                }

                @Override
                public void onSuccess(AccountInfoMo accountInfoMo) {
                    LoginHelper.getInstance().updateAccountInfoMo(accountInfoMo);
                    EventBus.getDefault().post(new EventBusKeyDefine.EventBusMsgData<String>(EventBusKeyDefine.EVENTBUS_USER_INFO_UPDATE, ""));
                }

                @Override
                public void onFail(int resultCode, int bizCode, String bizMessage) {
                }
            });

            GetUserInfoRequestMo getUserInfoRequestMo = new GetUserInfoRequestMo(currentLoginUser.user_id);
            UserService.getUserInfo(getUserInfoRequestMo, new BizResultListener<UserInfoResultMo>() {
                @Override
                public void onPreExecute() {

                }

                @Override
                public void hitCache(boolean hit, UserInfoResultMo userInfoResultMo) {

                }

                @Override
                public void onSuccess(UserInfoResultMo userInfoResultMo) {
                    UserInfoBean userInfoBean = ConvertDataUtils.convertMoToBean(userInfoResultMo);
                    if (userInfoBean != null) {
                        LoginHelper.getInstance().updateLoginUserInfo(userInfoBean);
                        EventBus.getDefault().post(new EventBusKeyDefine.EventBusMsgData<String>(EventBusKeyDefine.EVENTBUS_USER_INFO_UPDATE, ""));
                    }
                }

                @Override
                public void onFail(int resultCode, int bizCode, String bizMessage) {

                }
            });
        }
    }
}

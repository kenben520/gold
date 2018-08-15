package com.lingxi.preciousmetal.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lingxi.biz.domain.responseMo.AccountInfoMo;
import com.lingxi.common.base.BaseFragment;
import com.lingxi.common.util.StringUtil;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.common.EventBusKeyDefine;
import com.lingxi.preciousmetal.domain.UserInfoBean;
import com.lingxi.preciousmetal.login.LoginHelper;
import com.lingxi.preciousmetal.ui.activity.ContactUsActivity;
import com.lingxi.preciousmetal.ui.activity.DepositMoneyActivity;
import com.lingxi.preciousmetal.ui.activity.DepositWithdrawMoneyActivity;
import com.lingxi.preciousmetal.ui.activity.FeedBackActivity;
import com.lingxi.preciousmetal.ui.activity.FrequentlyAskedQuestionsActivity;
import com.lingxi.preciousmetal.ui.activity.LoginActivity;
import com.lingxi.preciousmetal.ui.activity.MineWarnListActivity;
import com.lingxi.preciousmetal.ui.activity.NewNoticeActivity;
import com.lingxi.preciousmetal.ui.activity.PropertyDetailActivity;
import com.lingxi.preciousmetal.ui.activity.RegisterActivity;
import com.lingxi.preciousmetal.ui.activity.SettingActivity1;
import com.lingxi.preciousmetal.ui.activity.UploadUserInfoActivity;
import com.lingxi.preciousmetal.ui.activity.UserAccountInfoDisplayActivity;
import com.lingxi.preciousmetal.ui.activity.UserPersonalInfoActivity;
import com.lingxi.preciousmetal.ui.activity.kefu.KefuLoginActivity;
import com.lingxi.preciousmetal.ui.widget.CommonItemBean;
import com.lingxi.preciousmetal.ui.widget.CommonItemView;
import com.lingxi.preciousmetal.util.glideutils.GlideUtils;
import com.lingxi.preciousmetal.util.utilCode.AppUtils;
import com.lingxi.preciousmetal.util.utilCode.SPUtils;
import com.lingxi.preciousmetal.util.utilCode.SharedPreferencesHelper;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

//import com.lingxi.preciousmetal.ui.activity.kefu.KefuLoginActivity;

/**
 * 首页我的Tab
 * Created by zhangwei on 2018/4/18.
 */

public class MineFragment extends BaseFragment {
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    Unbinder unbinder;
    @BindView(R.id.btn_deposit)
    Button mBtnRecharge;
    @BindView(R.id.layout_personal_info)
    RelativeLayout layoutPersonalInfo;
    @BindView(R.id.layout_property_detail)
    RelativeLayout layoutPropertyDetail;
    @BindView(R.id.layout_money_access)
    RelativeLayout layoutMoneyAccess;
    @BindView(R.id.layout_audit_bar)
    RelativeLayout layoutAuditBar;
    @BindView(R.id.iv_user_icon)
    ImageView ivUserIcon;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.layout_user_icon)
    LinearLayout layoutUserIcon;
    @BindView(R.id.layout_unlogin_head)
    LinearLayout layoutUnloginHead;
    @BindView(R.id.layout_login_head)
    LinearLayout layoutLoginHead;
    @BindView(R.id.tv_total_property)
    TextView tvTotalProperty;
    @BindView(R.id.tv_account)
    TextView tvAccount;
    @BindView(R.id.iv_hide_property)
    ImageView ivHideProperty;
    @BindView(R.id.tv_tag1)
    TextView tvTag1;
    @BindView(R.id.btn_online_kefu)
    RelativeLayout btnOnlineKefu;
    @BindView(R.id.layout_hide_property)
    LinearLayout layoutHideProperty;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    private View mContentView;
    @BindView(R.id.item_1)
    CommonItemView mItemNews;
    @BindView(R.id.item_2)
    CommonItemView mItemHelpCenter;
    @BindView(R.id.item_3)
    CommonItemView mItemContactUs;
    @BindView(R.id.item_4)
    CommonItemView mItemSysSetting;
    @BindView(R.id.item_6)
    CommonItemView mItemAdvise;
    @BindView(R.id.item_5)
    CommonItemView mItemWarns;
    @BindView(R.id.btn_register)
    Button mBtnRegister;
    private SharedPreferencesHelper sharedPreferencesHelper;

    public static MineFragment newInstance() {
        MineFragment mineFragment = new MineFragment();
        return mineFragment;
    }

    @Override
    public void onViewCreated(@NonNull final View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
        registorEventBus();
        initItemTab();
        refreshLoginStateView();
        sharedPreferencesHelper = new SharedPreferencesHelper(mContext, "newNotice");
        showRedView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mContentView == null) {
            mContentView = inflater.inflate(R.layout.fragment_mine, container, false);
        }
        unbinder = ButterKnife.bind(this, mContentView);
        return mContentView;
    }

    private void showRedView() {
        if (sharedPreferencesHelper == null) {
            return;
        }
        int type1Show = (int) sharedPreferencesHelper.getSharedPreference("type1Show", 0);
        int type2Show = (int) sharedPreferencesHelper.getSharedPreference("type2Show", 0);
        int type3Show = (int) sharedPreferencesHelper.getSharedPreference("type3Show", 0);
        if (type1Show == 1 && type2Show == 1 && type3Show == 1) {
            mItemNews.new_notice_imgView.setVisibility(View.GONE);
        } else {
            mItemNews.new_notice_imgView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && getActivity() != null) {
            LoginHelper.getInstance().httpGetAccountInfo();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        showRedView();
    }

    public void initItemTab() {
        CommonItemBean userCenterSetBean1 = new CommonItemBean();
        userCenterSetBean1.imgResId = R.drawable.ic_announcement;
        userCenterSetBean1.title = "最新公告";
        userCenterSetBean1.isShowTopDivider = true;
        mItemNews.initData(userCenterSetBean1);
        CommonItemBean userCenterSetBean2 = new CommonItemBean();
        userCenterSetBean2.imgResId = R.drawable.ic_help_center;
        userCenterSetBean2.title = "常见问题";
        mItemHelpCenter.initData(userCenterSetBean2);
        CommonItemBean userCenterSetBean5 = new CommonItemBean();
        userCenterSetBean5.imgResId = R.drawable.lx_profile_warning;
        userCenterSetBean5.title = "我的预警";
        mItemWarns.initData(userCenterSetBean5);
        CommonItemBean userCenterSetBean3 = new CommonItemBean();
        userCenterSetBean3.imgResId = R.drawable.ic_contact_us;
        userCenterSetBean3.title = "联系我们";
        userCenterSetBean3.isShowTopDivider = true;
        mItemContactUs.initData(userCenterSetBean3);
        CommonItemBean userCenterSetBean4 = new CommonItemBean();
        userCenterSetBean4.imgResId = R.drawable.ic_sys_setting;
        userCenterSetBean4.title = "系统设置";
        mItemSysSetting.initData(userCenterSetBean4);

        CommonItemBean userCenterSetBean6 = new CommonItemBean();
        userCenterSetBean6.imgResId = R.drawable.ic_feedback;
        userCenterSetBean6.title = "意见反馈";
        mItemAdvise.initData(userCenterSetBean6);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unregistorEventBus();
        unbinder.unbind();
    }


    @OnClick({R.id.btn_login, R.id.btn_register, R.id.btn_deposit, R.id.item_1, R.id.item_2, R.id.item_5, R.id.item_3, R.id.item_4, R.id.item_6,
            R.id.layout_personal_info, R.id.layout_reigister, R.id.layout_property_detail, R.id.layout_money_access, R.id.layout_user_icon, R.id.layout_hide_property, R.id.btn_online_kefu, R.id.btn_online_kefu1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                LoginActivity.actionStart(getActivity());
                break;
            case R.id.btn_register:
            case R.id.layout_reigister:
                RegisterActivity.actionStart(getActivity());
                break;
            case R.id.btn_deposit:
                boolean isLogin3 = LoginHelper.getInstance().isLogin();
                if (!isLogin3) {
                    LoginActivity.actionStart(getActivity());
                } else {
                    UserInfoBean userInfo = LoginHelper.getInstance().getLoginUserInfo();
                    switch (userInfo.is_certified) {
                        case "0":
                            UploadUserInfoActivity.actionStart(getActivity(), userInfo);
                            break;
                        case "1"://success
                            DepositMoneyActivity.actionStart(getActivity());
                            break;
                        case "2":
                        case "3":
                            UserAccountInfoDisplayActivity.actionStart(getActivity(), userInfo.user_id);
                            break;
                    }
                }
                break;
            case R.id.layout_personal_info:
                boolean isLogin = LoginHelper.getInstance().isLogin();
                if (!isLogin) {
                    LoginActivity.actionStart(getActivity());
                } else {
                    UserInfoBean userInfo = LoginHelper.getInstance().getLoginUserInfo();
                    switch (userInfo.is_certified) {
                        case "0":
                            UploadUserInfoActivity.actionStart(getActivity(), userInfo);
                            break;
                        case "1"://success
                        case "2":
                        case "3":
                            UserAccountInfoDisplayActivity.actionStart(getActivity(), userInfo.user_id);
                            break;
                    }
                }
                break;
            case R.id.layout_property_detail:
                boolean isLogin1 = LoginHelper.getInstance().isLogin();
                if (!isLogin1) {
                    LoginActivity.actionStart(getActivity());
                } else {
                    UserInfoBean userInfo = LoginHelper.getInstance().getLoginUserInfo();
                    switch (userInfo.is_certified) {
                        case "0":
                            UploadUserInfoActivity.actionStart(getActivity(), userInfo);
                            break;
                        case "1"://success
                            PropertyDetailActivity.actionStart(getActivity());
                            break;
                        case "2":
                        case "3":
                            UserAccountInfoDisplayActivity.actionStart(getActivity(), userInfo.user_id);
                            break;
                    }
                }
                break;
            case R.id.layout_money_access:
                boolean isLogin2 = LoginHelper.getInstance().isLogin();
                if (!isLogin2) {
                    LoginActivity.actionStart(getActivity());
                } else {
                    UserInfoBean userInfo = LoginHelper.getInstance().getLoginUserInfo();
                    switch (userInfo.is_certified) {
                        case "0":
                            UploadUserInfoActivity.actionStart(getActivity(), userInfo);
                            break;
                        case "1"://success
                            DepositWithdrawMoneyActivity.actionStart(getActivity());
                            break;
                        case "2":
                        case "3":
                            UserAccountInfoDisplayActivity.actionStart(getActivity(), userInfo.user_id);
                            break;
                    }
                }
                break;
            case R.id.item_1:
                NewNoticeActivity.startMyActivity(getActivity());
                break;
            case R.id.item_2:
//                HelpCenterActivity.actionStart();
                FrequentlyAskedQuestionsActivity.actionStart(getActivity());
                break;
            case R.id.item_5:
                boolean isLogin5 = LoginHelper.getInstance().isLogin();
                if (!isLogin5) {
                    LoginActivity.actionStart(getActivity());
                } else {
                    MineWarnListActivity.actionStart(getActivity());
                }
                break;
            case R.id.item_3:
                ContactUsActivity.actionStart(getActivity());
                break;
            case R.id.item_4:
                SettingActivity1.actionStart(getActivity());
                break;
            case R.id.item_6:
                FeedBackActivity.actionStart(getActivity());
                break;
            case R.id.layout_user_icon:
                UserPersonalInfoActivity.actionStart(getActivity());
                break;
            case R.id.btn_online_kefu:
            case R.id.btn_online_kefu1:
                KefuLoginActivity.actionStart(getActivity());
                break;
            case R.id.layout_hide_property:
                boolean isSelected = !ivHideProperty.isSelected();
                ivHideProperty.setSelected(isSelected);
                SPUtils.getInstance().put("hideTotalProperty", isSelected);
                if (isSelected) {
                    tvTotalProperty.setText("*********");
                    tvTag1.setVisibility(View.GONE);
                    ivHideProperty.setImageDrawable(getResources().getDrawable(R.drawable.ic_property_hide));
                } else {
                    tvTotalProperty.setText(totalProperty);
                    tvTag1.setVisibility(View.VISIBLE);
                    ivHideProperty.setImageDrawable(getResources().getDrawable(R.drawable.ic_property_show));

                }
                break;
        }
    }

    @OnClick(R.id.layout_audit_bar)
    public void onViewClicked() {
        UserInfoBean userInfo = LoginHelper.getInstance().getLoginUserInfo();
        UploadUserInfoActivity.actionStart(getActivity(), userInfo);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThrea(EventBusKeyDefine.EventBusMsgData<String> data) {
        int type = data.getType();
        try {
            if (EventBusKeyDefine.EVENTBUS_LOGIN_SUCCESS == type || EventBusKeyDefine.EVENTBUS_LOGOUT == type || EventBusKeyDefine.EVENTBUS_USER_INFO_UPDATE == type || EventBusKeyDefine.EVENTBUS_REGISTER_SUCCESS == type) {
                if (EventBusKeyDefine.EVENTBUS_LOGIN_SUCCESS == type || EventBusKeyDefine.EVENTBUS_LOGOUT == type) {
                    scrollView.scrollTo(0, 0);
                }
                refreshLoginStateView();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101 && resultCode == 101) {

        }
    }

    private String totalProperty = "";

    private void refreshLoginStateView() {
        UserInfoBean userInfoBean = LoginHelper.getInstance().getLoginUserInfo();
        if (userInfoBean != null) {
            if ("0".equals(userInfoBean.is_certified)) {
                layoutAuditBar.setVisibility(View.VISIBLE);
            } else {
                layoutAuditBar.setVisibility(View.GONE);
            }
            layoutUserIcon.setVisibility(View.VISIBLE);
            layoutLoginHead.setVisibility(View.VISIBLE);
            layoutUnloginHead.setVisibility(View.GONE);
            String name = StringUtil.isEmpty(userInfoBean.nick_name) ? userInfoBean.user_name : userInfoBean.nick_name;
            tvUserName.setText(name);
            GlideUtils.loadImageViewCrop(getContext(), R.drawable.main_default_header, userInfoBean.avatars, ivUserIcon);
            AccountInfoMo accountInfoMo = LoginHelper.getInstance().getAccountInfo();
            if (accountInfoMo != null) {
                tvAccount.setText("真实帐号：" + accountInfoMo.getAccount());
                DecimalFormat nf = new DecimalFormat("#,###,##0.00");
                String str = nf.format(accountInfoMo.getBalance());
                totalProperty = str;
                boolean hide = SPUtils.getInstance().getBoolean("hideTotalProperty");//+accountInfoMo.getAccount()
                if (hide) {
                    tvTotalProperty.setText("*********");
                    tvTag1.setVisibility(View.GONE);
                    ivHideProperty.setImageDrawable(getResources().getDrawable(R.drawable.ic_property_hide));
                } else {
                    tvTotalProperty.setText(totalProperty);
                    tvTag1.setVisibility(View.VISIBLE);
                    ivHideProperty.setImageDrawable(getResources().getDrawable(R.drawable.ic_property_show));
                }
            }
            AppUtils.setCustomFont(getContext(), tvTotalProperty);
        } else {
            layoutAuditBar.setVisibility(View.GONE);
            layoutUserIcon.setVisibility(View.GONE);
            layoutLoginHead.setVisibility(View.GONE);
            layoutUnloginHead.setVisibility(View.VISIBLE);
        }
    }


}

package com.lingxi.preciousmetal.ui.fragment.trade;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lingxi.common.base.BaseApplication;
import com.lingxi.common.base.BaseFragment;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.common.EventBusKeyDefine;
import com.lingxi.preciousmetal.login.LoginHelper;
import com.lingxi.preciousmetal.ui.activity.LoginActivity;
import com.lingxi.preciousmetal.ui.activity.RegisterActivity;
import com.lingxi.preciousmetal.ui.widget.TopBarView;
import com.lingxi.preciousmetal.util.DisplayUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by zhangwei on 2018/4/26.
 */

public class TradeFragment extends BaseFragment {

    @BindView(R.id.bg_unlogin_banner)
    ImageView bgUnloginBanner;
    @BindView(R.id.btn_register)
    TextView btnRegister;
    @BindView(R.id.btn_login)
    TextView btnLogin;
    Unbinder unbinder;
    @BindView(R.id.layout_unlogin)
    LinearLayout layoutUnlogin;
    @BindView(R.id.layout_trade_login)
    LinearLayout layoutTradeLogin;
    @BindView(R.id.topbar_view)
    TopBarView mTopbarView;
    @BindView(R.id.textView)
    TextView textView;

    public static TradeFragment newInstance() {
        TradeFragment tradeFragment = new TradeFragment();
        return tradeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trade, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
        registorEventBus();
        initView();
    }

    private void initView() {
        int screenWidth = DisplayUtil.getScreenWidth(BaseApplication.getInstance());
        ViewGroup.LayoutParams params = bgUnloginBanner.getLayoutParams();
        params.height = screenWidth * 758 / 750;
        mTopbarView.setMode(TopBarView.MODE_DEFAULT);
        mTopbarView.setTitle("交易");
        refreshLoginStateView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unregistorEventBus();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_register, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                RegisterActivity.actionStart(getActivity());
                break;
            case R.id.btn_login:
                LoginActivity.actionStart(getActivity());
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThrea(EventBusKeyDefine.EventBusMsgData<String> data) {
        int type = data.getType();
        try {
            if (EventBusKeyDefine.EVENTBUS_LOGIN_SUCCESS == type || EventBusKeyDefine.EVENTBUS_LOGOUT == type || EventBusKeyDefine.EVENTBUS_USER_INFO_UPDATE == type || EventBusKeyDefine.EVENTBUS_REGISTER_SUCCESS == type) {
                refreshLoginStateView();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshLoginStateView() {
        if (LoginHelper.getInstance().isLogin()) {
            layoutUnlogin.setVisibility(View.GONE);
            layoutTradeLogin.setVisibility(View.VISIBLE);
        } else {
            layoutUnlogin.setVisibility(View.VISIBLE);
            layoutTradeLogin.setVisibility(View.GONE);
        }
    }
}

package com.lingxi.preciousmetal.ui.fragment.analyzeplate;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lingxi.biz.domain.responseMo.EssenceCourseMo;
import com.lingxi.common.base.BaseFragment;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.common.EventBusKeyDefine;
import com.lingxi.preciousmetal.ui.adapter.NewsFrPagerAdapter;
import com.lingxi.preciousmetal.ui.adapter.ViewPagerSlide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhangwei on 2018/5/22.
 * 解盘tab
 */

public class AnalyzePlateFragment extends BaseFragment {

    @BindView(R.id.news_main_tabs)
    TabLayout toolbar_tab;
    @BindView(R.id.news_main_pager1)
    ViewPagerSlide pager;
    Unbinder unbinder;
    String[] tab_title = {"直播", "晒单"};

    public static AnalyzePlateFragment newInstance() {
        AnalyzePlateFragment tradeFragment = new AnalyzePlateFragment();
        return tradeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_analyze_plate, container, false);
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
        refreshLoginStateView();
        ArrayList<String> titles = new ArrayList<>();
        titles.add("直播");
        titles.add("晒单");
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(AnalyzePlateLiveFragment.newInstance());
        fragments.add(AnalyzePlateShowTradeFragment.newInstance());
        toolbar_tab.addTab(toolbar_tab.newTab().setText(tab_title[0]));
        toolbar_tab.addTab(toolbar_tab.newTab().setText(tab_title[1]));
//        pager.setPagingEnabled(false);
        pager.setOffscreenPageLimit(2);
        pager.setAdapter(new NewsFrPagerAdapter(getChildFragmentManager(), titles, fragments));
        toolbar_tab.setupWithViewPager(pager);
        toolbar_tab.setTabMode(TabLayout.MODE_FIXED);
        int fType = 0;
        if (fType == 0) {
            pager.setCurrentItem(0);
        } else if (fType == 1)
            pager.setCurrentItem(1);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unregistorEventBus();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private boolean isLoadData = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser &&  getActivity() != null) {
            EventBus.getDefault().post(new EventBusKeyDefine.EventBusMsgData<String>(EventBusKeyDefine.EVENTBUS_REFRESH_LIVE_TEACHER, ""));
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
//        if (LoginHelper.getInstance().isLogin()) {
//            layoutUnlogin.setVisibility(View.GONE);
//            layoutTradeLogin.setVisibility(View.VISIBLE);
//        } else {
//            layoutUnlogin.setVisibility(View.VISIBLE);
//            layoutTradeLogin.setVisibility(View.GONE);
//        }
    }
}

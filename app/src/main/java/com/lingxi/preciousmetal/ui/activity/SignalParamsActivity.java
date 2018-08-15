package com.lingxi.preciousmetal.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.widget.LinearLayout;

import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.GetSignalParamsRequestMo;
import com.lingxi.biz.domain.responseMo.SignalParamsMo;
import com.lingxi.biz.service.TradeService;
import com.lingxi.biz.util.ConstantUtil;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;
import com.lingxi.preciousmetal.ui.adapter.NewsFrPagerAdapter;
import com.lingxi.preciousmetal.ui.adapter.ViewPagerSlide;
import com.lingxi.preciousmetal.ui.fragment.SignalParamsFragment;
import com.lingxi.preciousmetal.ui.widget.TopBarView;
import com.lingxi.preciousmetal.ui.widget.dialog.DialogManager;
import com.lingxi.preciousmetal.util.ApplicationUtils;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangwei on 2018/4/19.
 */

public class SignalParamsActivity extends TranslucentStatusBarActivity {
    public static final int GOLD_TYPE = 1;
    public static final int SILVER_TYPE = 2;
    int type = GOLD_TYPE;
    @BindView(R.id.topbar_view)
    TopBarView mTopbarView;
    @BindView(R.id.tab)
    TabLayout toolbar_tab;
    @BindView(R.id.viewpager)
    ViewPagerSlide pager;
    @BindView(R.id.content)
    LinearLayout content;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private SignalParamsFragment mondayF, tuesdayF;

    public static void actionStart(Context context, int type) {
        Intent intent = new Intent(context, SignalParamsActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signal_params);
        ButterKnife.bind(this);
        initTopBar();
        type = getIntent().getIntExtra("type", GOLD_TYPE);
        initView();
        loadData(true);
    }

    private void initView() {
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadData(true);
            }
        });
        ArrayList<String> titles = new ArrayList<>();
        titles.add("伦敦金");
        titles.add("伦敦银");
        toolbar_tab.addTab(toolbar_tab.newTab().setText(titles.get(0)));
        toolbar_tab.addTab(toolbar_tab.newTab().setText(titles.get(1)));
        ArrayList<Fragment> fragments = new ArrayList<>();
        mondayF = SignalParamsFragment.newInstance(ConstantUtil.XAUUSD);
        tuesdayF = SignalParamsFragment.newInstance(ConstantUtil.XAGUSD);
        fragments.add(mondayF);
        fragments.add(tuesdayF);
        pager.setOffscreenPageLimit(2);
        pager.setPagingEnabled(true);
        pager.setAdapter(new NewsFrPagerAdapter(getSupportFragmentManager(), titles, fragments));
        toolbar_tab.setupWithViewPager(pager);
        toolbar_tab.setTabMode(TabLayout.MODE_FIXED);
        switch (type) {
            case GOLD_TYPE:
                pager.setCurrentItem(0);
                break;
            case SILVER_TYPE:
                pager.setCurrentItem(1);
                break;
        }
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setEnableRefresh(false);
        toolbar_tab.post(new Runnable() {
            @Override
            public void run() {
                ApplicationUtils.setIndicator(toolbar_tab,60,60);
            }
        });
    }

    private void initTopBar() {
        mTopbarView.setMode(TopBarView.MODE_BACK);
        mTopbarView.setTitle("信号气象标");
    }

    private void updateInfo(SignalParamsMo signalParamsMo) {
//        this.accountInfoMo = accountInfoMo;
        mondayF.onDataChange(signalParamsMo);
        tuesdayF.onDataChange(signalParamsMo);
    }

    private void loadData(final boolean hasUI) {
        if (hasUI) {
            DialogManager.getInstance().showLoadingDialog(this, "", false);
        }
        GetSignalParamsRequestMo liveListRequestMo = new GetSignalParamsRequestMo();
        TradeService.getSignParams(liveListRequestMo, new BizResultListener<SignalParamsMo>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, SignalParamsMo signalParamsMo) {

            }

            @Override
            public void onSuccess(SignalParamsMo signalParamsMo) {
                updateInfo(signalParamsMo);
                refreshLayout.finishRefresh();
                DialogManager.getInstance().cancellLoadingDialog();
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                if (hasUI) {
                    refreshLayout.finishRefresh();
                    ToastUtils.showShort(bizMessage);
                    DialogManager.getInstance().cancellLoadingDialog();
                }
            }
        });
    }


}

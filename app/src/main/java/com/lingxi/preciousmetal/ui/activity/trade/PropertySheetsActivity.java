package com.lingxi.preciousmetal.ui.activity.trade;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;
import com.lingxi.preciousmetal.common.IntentParam;
import com.lingxi.preciousmetal.ui.activity.PropertyDetailActivity;
import com.lingxi.preciousmetal.ui.activity.TradeHistoryFilterActivity;
import com.lingxi.preciousmetal.ui.activity.TradeWaterBillsFilterActivity;
import com.lingxi.preciousmetal.ui.activity.kefu.KefuLoginActivity;
import com.lingxi.preciousmetal.ui.adapter.NewsFrPagerAdapter;
import com.lingxi.preciousmetal.ui.adapter.ViewPagerSlide;
import com.lingxi.preciousmetal.ui.fragment.trade.TradeInfoCommonFragment;
import com.lingxi.preciousmetal.ui.widget.TopBarView;
import com.lingxi.preciousmetal.util.ApplicationUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PropertySheetsActivity extends TranslucentStatusBarActivity {
    public static final int TYPE_1 = 1;//trade history filter
    public static final int TYPE_2 = 2;//trade water bills filter
    int type = TYPE_1;
    @BindView(R.id.topbar_view)
    TopBarView mTopbarView;
    @BindView(R.id.tab)
    TabLayout toolbar_tab;
    @BindView(R.id.viewpager)
    ViewPagerSlide pager;
    @BindView(R.id.content)
    LinearLayout content;
    @BindView(R.id.btn_filter)
    ImageView btnFilter;
    private TradeInfoCommonFragment tradeHistory, tradeWaterBills;

    public static void actionStart(Context context, int type) {
        Intent intent = new Intent(context, PropertySheetsActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_sheets);
        ButterKnife.bind(this);
        initTopBar();
        type = getIntent().getIntExtra("type", TYPE_1);
        initView();
    }

    private void initView() {
        ArrayList<String> titles = new ArrayList<>();
        titles.add("交易历史");
        titles.add("资金流水");
        toolbar_tab.addTab(toolbar_tab.newTab().setText(titles.get(0)));
        toolbar_tab.addTab(toolbar_tab.newTab().setText(titles.get(1)));
        ArrayList<Fragment> fragments = new ArrayList<>();
        tradeHistory = TradeInfoCommonFragment.newInstance("3");
        tradeWaterBills = TradeInfoCommonFragment.newInstance("4");
        fragments.add(tradeHistory);
        fragments.add(tradeWaterBills);
        pager.setOffscreenPageLimit(2);
        pager.setPagingEnabled(true);
        pager.setAdapter(new NewsFrPagerAdapter(getSupportFragmentManager(), titles, fragments));
        toolbar_tab.setupWithViewPager(pager);
        toolbar_tab.setTabMode(TabLayout.MODE_FIXED);
        switch (type) {
            case TYPE_1:
                pager.setCurrentItem(0);
                break;
            case TYPE_2:
                pager.setCurrentItem(1);
                break;
        }
        toolbar_tab.post(new Runnable() {
            @Override
            public void run() {
                ApplicationUtils.setIndicator(toolbar_tab, 60, 60);
            }
        });
    }

    private void initTopBar() {
        mTopbarView.setMode(TopBarView.MODE_BACK);
        mTopbarView.setTitle("资产报表");
        mTopbarView.setActionButton(R.drawable.ic_custom_service_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KefuLoginActivity.actionStart(PropertySheetsActivity.this);
            }
        });
    }

    @OnClick(R.id.btn_filter)
    public void onViewClicked() {
        if (type == 2) {
            TradeWaterBillsFilterActivity.actionStart(this);
        } else if (type == 1) {
            TradeHistoryFilterActivity.actionStart(this);
        } else {
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (TradeHistoryFilterActivity.TIME_FILTER_TRADE_HISTORY_REQUEST_CODE == requestCode && TradeHistoryFilterActivity.TIME_FILTER_TRADE_HISTORY_RESULT_CODE == resultCode) {
            String filterStartTime = data.getStringExtra(IntentParam.START_TIME);
            String filterEndTime = data.getStringExtra(IntentParam.END_TIME);
            tradeHistory.upOrderHistoryDataView(filterStartTime, filterEndTime);
        }else if (TradeWaterBillsFilterActivity.TIME_FILTER_TRADE_WATER_BILLS_REQUEST_CODE == requestCode && TradeWaterBillsFilterActivity.TIME_FILTER_TRADE_WATER_BILLS_RESULT_CODE == resultCode) {
            String filterStartTime = data.getStringExtra(IntentParam.START_TIME);
            String filterEndTime = data.getStringExtra(IntentParam.END_TIME);
            tradeWaterBills.upMoneyDataView(filterStartTime, filterEndTime);
        }
    }
}

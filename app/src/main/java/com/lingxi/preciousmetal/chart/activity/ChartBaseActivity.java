package com.lingxi.preciousmetal.chart.activity;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.zhouwei.library.CustomPopWindow;
import com.github.tifezh.kchartlib.chart.KChartView;
import com.github.tifezh.kchartlib.chart.KMinuteChartView;
import com.github.tifezh.kchartlib.chart.entity.KMinuteLineEntity;
import com.github.tifezh.kchartlib.utils.ViewUtil;
import com.gxz.PagerSlidingTabStrip;
import com.lingxi.biz.domain.responseMo.KLineChartResultBean;
import com.lingxi.biz.domain.responseMo.KMarketResultBean;
import com.lingxi.biz.util.ConstantUtil;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.MyApplication;
import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;
import com.lingxi.preciousmetal.chart.adapter.KChartAdapter;
import com.lingxi.preciousmetal.chart.entity.KLineEntity;
import com.lingxi.preciousmetal.domain.CalendarItem;
import com.lingxi.preciousmetal.domain.FilterSection;
import com.lingxi.preciousmetal.domain.KOperationNoteBean;
import com.lingxi.preciousmetal.domain.SignalParamValues;
import com.lingxi.preciousmetal.domain.TimeTitleBean;
import com.lingxi.preciousmetal.login.LoginHelper;
import com.lingxi.preciousmetal.ui.activity.LoginActivity;
import com.lingxi.preciousmetal.ui.activity.trade.StartBuyGoldActivity;
import com.lingxi.preciousmetal.ui.adapter.IndicatorSectionAdapter;
import com.lingxi.preciousmetal.ui.adapter.KTimeTitleLandAdapter;
import com.lingxi.preciousmetal.ui.adapter.TimeTitleFrPagerAdapter;
import com.lingxi.preciousmetal.ui.widget.dialog.CommonDialog;
import com.lingxi.preciousmetal.util.utilCode.AppViewUtils;
import com.lingxi.preciousmetal.util.utilCode.ScreenUtils;
import com.lingxi.preciousmetal.util.utilCode.SharedPreferencesHelper;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  抽出部分代码
 */

public class ChartBaseActivity extends TranslucentStatusBarActivity implements View.OnClickListener{
    protected KChartView mKChartView;
    protected ImageView leftBtn, right_more_btn;
    protected TextView kMaxText, kLowText, kTodayOpenText, kYesterdayCloseText;
    protected View k_time_title_land_layout,k_land_buy_sell_layout;
    protected TextView kTitleTextView, goldNowTextView, gold_now_percent_textView,k_time_textView;
    protected TextView buy_gold_value_View, sell_gold_value_View, price_spread_view;
    protected ImageView changeScreenBtn;
    protected KMinuteChartView kMinuteChartView;
    protected View k_title_land_view,left_window_view;
    protected View buy_gold_View,sell_gold_View,k_land_buy_layout,k_land_sell_layout;;
    protected ListView k_time_title_listView;
    protected View k_title_port_view,k_buy_sell_port_layout;
    protected PagerSlidingTabStrip tabs;
    protected ViewPager pager;
    protected KChartAdapter mAdapter;
    protected List<KLineChartResultBean.ChartBean> chartList;
    protected String goods, goodsName;
    protected boolean isVertical,flagThread = true;
    protected int queryIndex,currentTabItem,querySize;
    protected SharedPreferencesHelper sharedPreferencesHelper;
    protected boolean flagMinute,flagTime = true;
    protected List<KMinuteLineEntity> minuteListData = null;
    protected float openPrice, minPrice, maxPrice = 0.0f;
    protected int minuteSize;
    protected boolean flagSolid;
    protected int kShowType = 0;
    protected KOperationNoteBean operationNoteBean;
    protected List<KMarketResultBean.MarketBean> marketList;
    protected KTimeTitleLandAdapter timeTitleAdapter;
    protected TimeTitleFrPagerAdapter titleFrPagerAdapter;
    protected String priceDecimal = "%.2f";
    protected TextView k_land_title_textView,k_land_time_textView;
    protected TextView k_land_open_textView,k_land_close_textView;
    protected TextView k_land_high_textView,k_land_low_textView;
    protected TextView k_land_price_textView,k_land_percent_textView;
    protected TextView k_land_buy_px_textView,k_land_sell_px_textView,k_land_price_spread_view;

    protected ArrayList<Fragment> fragments;
    protected List<TimeTitleBean> kTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected  List<KLineEntity> kchartData;
    protected IndicatorSectionAdapter sectionAdapter;
    protected List<FilterSection> filterData;
    protected  CustomPopWindow filterPopWindow, switchNameWindow,kTypeWindow,moreWindow;
    protected TextView k_solid_textView;
    protected float spread = 0.5f;
    protected KMarketResultBean.MarketBean marketGdBean,marketSdBean,marketUsBean = null;

    protected void initSwitchNameWindow(View view) {
        View contentView = LayoutInflater.from(this).inflate(R.layout.window_k_switch_name, null);
        if (isVertical){
            switchNameWindow = new CustomPopWindow.PopupWindowBuilder(this)
                    .setView(contentView).size(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
                    .create();
            switchNameWindow.showAsDropDown(view);
          //  switchNameWindow.showAsDropDown(view, -switchNameWindow.getWidth()/2,0);
        } else {
            switchNameWindow = new CustomPopWindow.PopupWindowBuilder(this)
                    .setView(contentView).size(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
                    .create();
            switchNameWindow.showAsDropDown(view);
        }
        TextView switch_gold_textView = contentView.findViewById(R.id.switch_gold_textView);
        TextView switch_silver_textView = contentView.findViewById(R.id.switch_silver_textView);
        TextView switch_us_textView = contentView.findViewById(R.id.switch_us_textView);
        Drawable drawable = ContextCompat.getDrawable(mContext,R.drawable.lx_info_arrow_sel);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchNameWindow.dissmiss();
            }
        });
        if (ConstantUtil.XAUUSD.equals(goods)) {
            switch_gold_textView.setBackground(null);
            switch_gold_textView.setTextColor(ContextCompat.getColor(mContext, R.color.FFD8883B));
            switch_gold_textView.setCompoundDrawables(drawable,null,null,null);
        } else if (ConstantUtil.XAGUSD.equals(goods)) {
            switch_silver_textView.setBackground(null);
            switch_silver_textView.setTextColor(ContextCompat.getColor(mContext, R.color.FFD8883B));
            switch_silver_textView.setCompoundDrawables(drawable,null,null,null);
        } else if (ConstantUtil.USDOLLARINDEX.equals(goods)) {
            switch_us_textView.setBackground(null);
            switch_us_textView.setTextColor(ContextCompat.getColor(mContext, R.color.FFD8883B));
            switch_us_textView.setCompoundDrawables(drawable,null,null,null);
        }
        switch_gold_textView.setOnClickListener(this);
        switch_silver_textView.setOnClickListener(this);
        switch_us_textView.setOnClickListener(this);
    }

    protected void initkMoreWindow() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.window_k_more, null);
        //创建并显示popWindow
        moreWindow = new CustomPopWindow.PopupWindowBuilder(this).setView(contentView)
                .create().showAsDropDown(right_more_btn,0,0);
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moreWindow.dissmiss();
            }
        });
        View k_type_textView = contentView.findViewById(R.id.k_type_textView);
        k_type_textView.setOnClickListener(this);
        contentView.findViewById(R.id.k_warning_textView).setOnClickListener(this);
        contentView.findViewById(R.id.k_rule_textView).setOnClickListener(this);
        k_solid_textView = contentView.findViewById(R.id.k_solid_textView);
      //  k_solid_textView.setOnClickListener(this);
        View k_solid_layout = contentView.findViewById(R.id.k_solid_layout);
        k_solid_layout.setOnClickListener(this);

        setkSolidImage();
        if (flagMinute){
            k_type_textView.setVisibility(View.GONE);
            k_solid_layout.setVisibility(View.GONE);
        }
    }

    protected void setkSolidImage(){
        Drawable rightDrawable;
        if (flagSolid){
            rightDrawable = mContext.getResources().getDrawable(R.drawable.k_more_solid_check);
        } else  {
            rightDrawable = mContext.getResources().getDrawable(R.drawable.k_more_solid);
        }
        rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
        k_solid_textView.setCompoundDrawables(rightDrawable, null, null, null);
    }


    protected void kTypeWindow() {
        if (kTypeWindow != null && kTypeWindow.getPopupWindow().isShowing()) {
            return;
        }
        View contentView = LayoutInflater.from(this).inflate(R.layout.window_k_type, null);
        //创建并显示popWindow
        kTypeWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView).size(ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight())
                .create().showAsDropDown(kTitleTextView);
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kTypeWindow.dissmiss();
            }
        });
        RadioButton type_kCandle_radio = contentView.findViewById(R.id.type_kCandle_radio);
        RadioButton  type_kRod_radio = contentView.findViewById(R.id.type_kRod_radio);
        RadioButton  type_kline_radio = contentView.findViewById(R.id.type_kline_radio);
        if (kShowType==0){
            type_kCandle_radio.setChecked(true);
        } else if (kShowType==1){
            type_kRod_radio.setChecked(true);
        } else if (kShowType==2){
            type_kline_radio.setChecked(true);
        }
        RadioGroup radioGroup = contentView.findViewById(R.id.radioGroup_kType_layout);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.type_kCandle_radio){
                    kShowType = 0;
                } else if (checkedId==R.id.type_kRod_radio){
                    kShowType = 1;
                } else if (checkedId==R.id.type_kline_radio){
                    kShowType = 2;
                }
                operationNoteBean.setkCurrentType(kShowType);
                mKChartView.setShowType(kShowType);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    protected void firstColorWindow(){
        mKChartView.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean isFirst = (boolean) sharedPreferencesHelper.getSharedPreference("kFirst", false);
                if (!isFirst) {
                    sharedPreferencesHelper.put("kFirst", true);
                    CommonDialog commonDialog = new CommonDialog(mContext, " ",  "美盛达贵金属服务全球市场，默认采用绿涨红跌，是否需要调整？\n", new CommonDialog.CommitClickListener() {
                        @Override
                        public void click(int whichDialog, Object object) {
                           KColorSettingActivity.startMyActivity2(mContext);
                        }
                    }, 4, "不用了", "去设置");
                    commonDialog.show();
                }
            }
        }, 1000);
    }

    protected void initFirstWindow() {
        if (filterPopWindow != null && filterPopWindow.getPopupWindow().isShowing()) {
            return;
        }
        sharedPreferencesHelper.put("kFirst", true);
        View contentView = LayoutInflater.from(this).inflate(R.layout.window_k_first_enter, null);
        //创建并显示popWindow
        filterPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView).size(ScreenUtils.getScreenWidth(),ScreenUtils.getScreenHeight())
                .create().showAsDropDown(kTitleTextView);
        TextView first_title_textView = contentView.findViewById(R.id.first_title_textView);
        first_title_textView.setText(goodsName);
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterPopWindow.dissmiss();
            }
        });
    }

    protected void initWindowFilter() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.window_indicators_filter, null);
        //创建并显示popWindow
        int w = ScreenUtils.getScreenWidth();

        filterPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView).size(w, ScreenUtils.getScreenHeight())
                .create().showAsDropDown(kTitleTextView);
        RecyclerView mRecyclerView = contentView.findViewById(R.id.indicator_filter_list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        filterData = operationNoteBean.getIndicatorList();

        contentView.findViewById(R.id.indicatiors_close_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterPopWindow.dissmiss();
            }
        });
        // mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        sectionAdapter = new IndicatorSectionAdapter(R.layout.item_section_content, R.layout.def_section_head,filterData);
        int gone = operationNoteBean.getChildDisplayOrGone();
        if (gone==0){
            sectionAdapter.setDisplayGone(true);
        } else {
            sectionAdapter.setDisplayGone(false);
        }
        sectionAdapter.setIndicatorOnclickListener(new IndicatorSectionAdapter.IndicatorOnclickListener() {
            @Override
            public void itemClick(boolean flag) {
                int index;
                if (flag){
                    mKChartView.upShowTypeView(0);
                    index = 0;
                } else {
                    index = 2;
                    mKChartView.upShowTypeView(2);
                }
                operationNoteBean.setChildDisplayOrGone(index);
            }
        });
        sectionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                FilterSection section = filterData.get(position);
                if (section.isHeader) {
                    return;
                }
                CalendarItem bean = section.t;
                for (FilterSection item : filterData) {
                    if (item.isHeader) {
                        continue;
                    }
                    CalendarItem cItem = item.t;
                    if (cItem.getType() == bean.getType()) {
                        cItem.setUserCheck(false);
                    }
                }
                bean.setUserCheck(true);
                int titleIndex = bean.getType();
                int index = Integer.parseInt(bean.getTypeId());
                if (titleIndex == 1) {//主图指标
                    mKChartView.kIndicatorsMainBtn.setText(bean.getName());
                    mKChartView.upMainIndicatorDraw(index);
                    operationNoteBean.setMainIndex(index);
                    operationNoteBean.setMainName(bean.getName());
                } else {//幅图指标
                    mKChartView.kIndicatorsChildBtn.setText(bean.getName());
                    mKChartView.upChildIndicatorDraw(index);
                    operationNoteBean.setChildIndex(index);
                    operationNoteBean.setChildName(bean.getName());
                }
                adapter.notifyDataSetChanged();
            }
        });
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                //   outRect.set(24, 24, 0, 4);//设置itemView中内容相对边框左，上，右，下距离
            }
        });

        sectionAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        mRecyclerView.setAdapter(sectionAdapter);
    }

    protected void landOrPort(){
        if (isVertical) {
            k_buy_sell_port_layout.setVisibility(View.VISIBLE);
            k_title_port_view.setVisibility(View.VISIBLE);
            k_title_land_view.setVisibility(View.GONE);
            k_time_title_land_layout.setVisibility(View.GONE);
        } else {
            k_title_land_view.setVisibility(View.VISIBLE);
            k_time_title_land_layout.setVisibility(View.VISIBLE);
            k_title_port_view.setVisibility(View.GONE);
            k_buy_sell_port_layout.setVisibility(View.GONE);
        }
    }

    protected void updateIndexParams(){
        SignalParamValues values = MyApplication.getSignalParamValues();
        Map<String, int[]> map = new HashMap<>();
        map.put("ARBR", new int[]{values.arbr.value1});
        map.put("ATR", new int[]{values.atr.value1});
        map.put("BBI", new int[]{values.bbi.value1, values.bbi.value2, values.bbi.value3, values.bbi.value4});
        map.put("BIAS", new int[]{values.bias.value1, values.bias.value2, values.bias.value3});
        map.put("KD", new int[]{values.kd.value1, values.kd.value2, values.kd.value3});
        map.put("KDJ", new int[]{values.kdj.value1, values.kdj.value2, values.kdj.value3});
        map.put("LWR", new int[]{values.lwr.value1, values.lwr.value2, values.lwr.value3});
        map.put("MACD", new int[]{values.macd.value1, values.macd.value2, values.macd.value3});
        map.put("MA", new int[]{values.ma.value1, values.ma.value2, values.ma.value3, values.ma.value4});
        map.put("MIKE", new int[]{values.mike.value1});
        map.put("RSI", new int[]{values.rsi.value1, values.rsi.value2, values.rsi.value3});
        map.put("WR", new int[]{values.wr.value1});
        map.put("BOLL", new int[]{values.boll.value1, values.boll.value2});
        map.put("CCI", new int[]{values.cci.value1});
        mKChartView.setIndexParams(map);
    }

    protected void initLandView() {
        mKChartView = findViewById(R.id.kchart_view);
        leftBtn = findViewById(R.id.left_btn);
        leftBtn.setOnClickListener(this);
        right_more_btn = findViewById(R.id.right_more_btn);
        right_more_btn.setOnClickListener(this);

        kMaxText = findViewById(R.id.k_max_text);
        kTodayOpenText = findViewById(R.id.k_today_open_text);
        kLowText = findViewById(R.id.k_low_text);
        kYesterdayCloseText = findViewById(R.id.k_yesterday_close_text);
//        k_deal_layout = findViewById(R.id.k_deal_buy_layout);
        kTitleTextView = findViewById(R.id.k_title_textView);
        goldNowTextView = findViewById(R.id.gold_now_textView);
        changeScreenBtn = findViewById(R.id.change_screen_btn);
        changeScreenBtn.setOnClickListener(this);
        buy_gold_View = findViewById(R.id.buy_gold_View);
        buy_gold_View.setOnClickListener(this);
        sell_gold_View = findViewById(R.id.sell_gold_View);
        sell_gold_View.setOnClickListener(this);

        buy_gold_value_View = findViewById(R.id.buy_gold_value_View);
        sell_gold_value_View = findViewById(R.id.sell_gold_value_View);
        price_spread_view = findViewById(R.id.price_spread_view);
        kMinuteChartView = findViewById(R.id.kMinuteChartView);
        View k_switch_kind_layout = findViewById(R.id.k_switch_kind_layout);
        k_switch_kind_layout.setOnClickListener(this);
        k_time_textView =  findViewById(R.id.k_time_textView);
        gold_now_percent_textView = findViewById(R.id.gold_now_percent_textView);
        k_title_port_view = findViewById(R.id.k_title_port_view);
        k_title_land_view = findViewById(R.id.k_title_land_view);
        left_window_view = findViewById(R.id.left_window_view);
        k_buy_sell_port_layout = findViewById(R.id.k_buy_sell_port_layout);
        k_time_title_land_layout = findViewById(R.id.k_time_title_land_layout);
        k_time_title_listView = findViewById(R.id.k_time_title_listView);
        pager =  findViewById(R.id.k_main_pager);
        tabs = findViewById(R.id.k_tools_tabs);
        initViewPort();
    }

    protected void initViewPort() {
        k_land_title_textView = findViewById(R.id.k_land_title_textView);
        k_land_title_textView.setOnClickListener(this);
        k_land_time_textView = findViewById(R.id.k_land_time_textView);
        k_land_high_textView = findViewById(R.id.k_land_high_textView);
        k_land_low_textView = findViewById(R.id.k_land_low_textView);
        k_land_open_textView = findViewById(R.id.k_land_open_textView);
        k_land_close_textView = findViewById(R.id.k_land_close_textView);
        k_land_price_textView = findViewById(R.id.k_land_price_textView);
        k_land_percent_textView = findViewById(R.id.k_land_percent_textView);
        k_land_buy_px_textView = findViewById(R.id.k_land_buy_px_textView);
        k_land_sell_px_textView = findViewById(R.id.k_land_sell_px_textView);
        k_land_price_spread_view = findViewById(R.id.k_land_price_spread_view);
        k_land_buy_sell_layout = findViewById(R.id.k_land_buy_sell_layout);
        k_land_buy_layout = findViewById(R.id.k_land_buy_layout);
        k_land_buy_layout.setOnClickListener(this);
        k_land_sell_layout = findViewById(R.id.k_land_sell_layout);
        k_land_sell_layout.setOnClickListener(this);
    }

    protected void clearData() {
        chartList.clear();
        kchartData.clear();
        minuteListData.clear();
        mAdapter = new KChartAdapter();
        mKChartView.setAdapter(mAdapter);
    }

    protected void showLogin() {
        if (!LoginHelper.getInstance().isLogin()) {
            ToastUtils.showShort("你暂时未登陆");
            LoginActivity.actionStart(mContext);
        } else {
            StartBuyGoldActivity.startMyActivity(mContext,0,goodsName,goods);
        }
    }

    @Override
    public void onClick(View v) {

    }
}

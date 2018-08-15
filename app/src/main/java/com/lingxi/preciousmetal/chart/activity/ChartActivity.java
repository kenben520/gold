package com.lingxi.preciousmetal.chart.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.TextView;

import com.github.tifezh.kchartlib.chart.BaseKChartView;
import com.github.tifezh.kchartlib.chart.KChartView;
import com.github.tifezh.kchartlib.chart.draw.MainDraw;
import com.github.tifezh.kchartlib.chart.entity.KMinuteLineEntity;
import com.github.tifezh.kchartlib.chart.formatter.DayTimeFormatter;
import com.github.tifezh.kchartlib.chart.formatter.MinuteTimeFormatter;
import com.github.tifezh.kchartlib.chart.formatter.SilverValueFormatter;
import com.github.tifezh.kchartlib.chart.formatter.UsValueFormatter;
import com.github.tifezh.kchartlib.chart.formatter.ValueFormatter;
import com.github.tifezh.kchartlib.utils.ViewUtil;
import com.gxz.PagerSlidingTabStrip;
import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.KlineChartRequestMo;
import com.lingxi.biz.domain.responseMo.KLineChartResultBean;
import com.lingxi.biz.domain.responseMo.KMarketResultBean;
import com.lingxi.biz.service.KMarketService;
import com.lingxi.biz.util.ConstantUtil;
import com.lingxi.net.basenet.NetConstant;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.MyApplication;
import com.lingxi.preciousmetal.chart.adapter.KChartAdapter;
import com.lingxi.preciousmetal.chart.data.DataHelper;
import com.lingxi.preciousmetal.chart.entity.KLineEntity;
import com.lingxi.preciousmetal.domain.DataServer;
import com.lingxi.preciousmetal.domain.TimeTitleBean;
import com.lingxi.preciousmetal.login.LoginHelper;
import com.lingxi.preciousmetal.ui.activity.LoginActivity;
import com.lingxi.preciousmetal.ui.activity.SignalSettingActivity;
import com.lingxi.preciousmetal.ui.activity.SignalSortSettingActivity;
import com.lingxi.preciousmetal.ui.activity.WarnAddActivity;
import com.lingxi.preciousmetal.ui.activity.WebViewActivity;
import com.lingxi.preciousmetal.ui.adapter.KTimeTitleLandAdapter;
import com.lingxi.preciousmetal.ui.adapter.TimeTitleFrPagerAdapter;
import com.lingxi.preciousmetal.ui.widget.dialog.DialogManager;
import com.lingxi.preciousmetal.util.utilCode.AppUtils;
import com.lingxi.preciousmetal.util.utilCode.AppViewUtils;
import com.lingxi.preciousmetal.util.utilCode.LogUtils;
import com.lingxi.preciousmetal.util.utilCode.SharedPreferencesHelper;
import com.lingxi.preciousmetal.util.utilCode.TimeUtils;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;
import java.util.ArrayList;
import java.util.List;

public class ChartActivity extends ChartBaseActivity implements KChartView.KChartRefreshListener{

    public static void startMyActivity(Context context, String goods, int name) {
        Intent intent = new Intent(context, ChartActivity.class);
        intent.putExtra("goods", goods);
        intent.putExtra("name", name);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAllView();
    }

    private void initAllView() {
        setContentView(R.layout.activity_chart);
        isVertical = (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
        initLandView();
        initView();
        initData();
        updateIndexParams();
        loadData(queryIndex);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        isVertical = (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        showTitleView();
        if (isVertical) {
            pager.setAdapter(titleFrPagerAdapter);
            tabs.setViewPager(pager);
            pager.setCurrentItem(currentTabItem,true);
        } else {
            if (flagMinute){
                queryIndex=0;
            }
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            timeTitleAdapter.setSelectIndex(queryIndex);
            k_time_title_listView.setSelection(currentTabItem);
            mKChartView.notifyChanged();
        }
        landOrPort();
    }

    private void loadMinuteData() {
        KlineChartRequestMo requestMo = new KlineChartRequestMo(goods, 1, 1);
        KMarketService.marketChart(requestMo, new BizResultListener<KLineChartResultBean>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, KLineChartResultBean resultBean) {

            }

            @Override
            public void onSuccess(KLineChartResultBean resultBean) {
                if (resultBean != null && resultBean.getChart()!=null && resultBean.getChart().size() > 0) {
                    KLineChartResultBean.ChartBean bean = resultBean.getChart().get(0);
                    float closePx = bean.getClose_px();
                    float px_change = closePx - openPrice;
                    float px_change_rate = px_change/openPrice*100;

                    KMarketResultBean.MarketBean marketBean = new KMarketResultBean.MarketBean();
                    marketBean.px_change = px_change;
                    marketBean.px_change_rate = px_change_rate;
                    marketBean.last_px = closePx;
                    AppViewUtils.setNowPrice2(mContext, goldNowTextView, gold_now_percent_textView, marketBean,priceDecimal);
                    AppViewUtils.setNowPrice2(mContext, k_land_price_textView, k_land_percent_textView, marketBean,priceDecimal);

                    KMinuteLineEntity minEntity = new KMinuteLineEntity();
                    minEntity.kTime = bean.getTime_stamp() * 1000;
                    minEntity.price = closePx;
                    if (flagMinute){
                        kMinuteChartView.addMinutePrice(minEntity);
                    }
                    float buyPx = closePx - spread;
                    String buyPxStr = String.format(priceDecimal,buyPx);
                    String sellPx = String.format(priceDecimal,closePx);
                    KMarketResultBean.MarketBean tempMarketBean = null;
                    if (ConstantUtil.XAUUSD.equals(goods)) {
                        tempMarketBean = marketGdBean;
                    } else if (ConstantUtil.XAGUSD.equals(goods)) {
                        tempMarketBean = marketSdBean;
                    } else if (ConstantUtil.USDOLLARINDEX.equals(goods)) {
                        tempMarketBean = marketUsBean;
                    }
                    if (isVertical){
                        // 最高最低价格判断
                        AppViewUtils.kPriceVerStyle(mContext,tempMarketBean,marketBean,priceDecimal,kMaxText,kLowText,kTodayOpenText,kYesterdayCloseText);

                        buy_gold_value_View.setText(buyPxStr);
                        sell_gold_value_View.setText(sellPx);
                        String nowTime = TimeUtils.millis2String(minEntity.kTime,TimeUtils.DEFAULT_FORMAT);
                        k_time_textView.setText(nowTime);
                        AppUtils.setTextSize(sellPx,buy_gold_value_View);
                        AppUtils.setTextSize(buyPxStr,sell_gold_value_View);
                    } else {
                        AppViewUtils.kPriceLandStyle(mContext, tempMarketBean,marketBean,priceDecimal,k_land_high_textView,k_land_low_textView,k_land_open_textView,k_land_close_textView);

                        String nowTime = TimeUtils.millis2String(minEntity.kTime*1000,TimeUtils.FORMATMDHMS);
                        k_land_time_textView.setText(nowTime);
                        k_land_buy_px_textView.setText(sellPx);
                        k_land_sell_px_textView.setText(buyPxStr);
                        AppUtils.setTextSize(sellPx,k_land_buy_px_textView);
                        AppUtils.setTextSize(buyPxStr,k_land_sell_px_textView);
                    }
                    int size = kchartData.size();
                    if (size>0){
                        //更新最后一根k线
                        KLineEntity lastEntity =  kchartData.get(size-1);
                        if (lastEntity.kDate!=bean.getTime_stamp() * 1000){
                            kchartData.addAll(AppViewUtils.getKData(resultBean.getChart().subList(0,1)));
                            DataHelper.calculate(kchartData);
                            mAdapter.addHeaderData(lastEntity);
                        } else {
                            if (closePx>lastEntity.High){
                                lastEntity.High = closePx;
                            } else if (closePx<lastEntity.Low){
                                lastEntity.Low = closePx;
                            }
                            lastEntity.Close = closePx;
                            mAdapter.changeItem(size-1,lastEntity);
                        }
                    }
                }
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                DialogManager.getInstance().cancellLoadingDialog();
            }
        });
    }

    private void loadData(int type) {
        if (flagMinute) {
            querySize = minuteSize;
        } else {
            querySize = ConstantUtil.KCHARTSIZE;
        }
        DialogManager.getInstance().showLoadingDialog(this, "", false);
        KlineChartRequestMo requestMo = new KlineChartRequestMo(goods, type,querySize);
        KMarketService.marketChart(requestMo, new BizResultListener<KLineChartResultBean>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, KLineChartResultBean resultBean) {

            }

            @Override
            public void onSuccess(KLineChartResultBean resultBean) {
                //clearData();
                chartList.clear();
                if (resultBean.getChart() != null && resultBean.getChart().size() > 0) {
                    chartList.addAll(resultBean.getChart());
                }
                showChartView();
                DialogManager.getInstance().cancellLoadingDialog();
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                DialogManager.getInstance().cancellLoadingDialog();
                LogUtils.i("k数据请求失败==");
            }
        });
    }

    private void showTitleView() {
        priceDecimal = AppUtils.getGoodsDecimal(goods);
        String buySpread = "50";
        String titleName = "";
        if (ConstantUtil.XAUUSD.equals(goods)) {
            titleName = "伦敦金";
            if (marketList != null && marketList.size() >= 3) {
                marketGdBean = marketList.get(0);
                showPriceData(marketGdBean);
            }
            buySpread = "50";
            spread = 0.5f;
            mKChartView.setValueFormatter(new ValueFormatter());
        } else if (ConstantUtil.XAGUSD.equals(goods)) {
            titleName = "伦敦银";
            if (marketList != null && marketList.size() >= 3) {
                marketSdBean = marketList.get(1);
                showPriceData(marketSdBean);
            }
            spread = 0.04f;
            buySpread = "4";
            mKChartView.setValueFormatter(new SilverValueFormatter());
        } else if (ConstantUtil.USDOLLARINDEX.equals(goods)) {
            if (marketList != null && marketList.size() >= 3) {
                marketUsBean = marketList.get(2);
                showPriceData(marketUsBean);
            }
            titleName = "美元指数";
            mKChartView.setValueFormatter(new UsValueFormatter());
        }
        goodsName = titleName;
        kTitleTextView.setText(titleName);
        k_land_title_textView.setText(titleName);

        if (!ConstantUtil.USDOLLARINDEX.equals(goods)) {
            if (isVertical){
                price_spread_view.setText(String.valueOf(buySpread));
                k_buy_sell_port_layout.setVisibility(View.VISIBLE);
                k_land_buy_sell_layout.setVisibility(View.GONE);
            } else {
                k_land_price_spread_view.setText(String.valueOf(buySpread));
                k_land_buy_sell_layout.setVisibility(View.VISIBLE);
                k_buy_sell_port_layout.setVisibility(View.GONE);
            }
        } else {
            k_land_buy_sell_layout.setVisibility(View.GONE);
            k_buy_sell_port_layout.setVisibility(View.GONE);
        }
    }

    private void loadKMinuteData() {
        if (flagMinute) {
            kMinuteChartView.setPriceDecimalPoint(priceDecimal);
            kMinuteChartView.setBasicsPrice(openPrice, maxPrice, minPrice);
            kMinuteChartView.setMinutePrice(minuteListData);
        } else {
            mKChartView.upShowTypeView(operationNoteBean.getChildDisplayOrGone());
            mKChartView.kIndicatorsMainBtn.setText(operationNoteBean.getMainName());
            mKChartView.upMainIndicatorDraw(operationNoteBean.getMainIndex());
            mKChartView.kIndicatorsChildBtn.setText(operationNoteBean.getChildName());
            mKChartView.upChildIndicatorDraw(operationNoteBean.getChildIndex());
            //updateIndexParams();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    List<KLineEntity> listEntity = AppViewUtils.getKData(chartList);
                    DataHelper.calculate(listEntity);
                    kchartData.addAll(listEntity);
                    //第一次加载时开始动画
                    mAdapter.addFooterData(listEntity);
                    if (kchartData.size() > 0) {
                        mKChartView.refreshComplete();
                    } else {
                        mKChartView.refreshEnd();
                    }
                }
            });
        }
    }

    private void initData() {
        operationNoteBean = MyApplication.operationNoteBean;
        kShowType = operationNoteBean.getkCurrentType();
        mKChartView.setShowType(kShowType);
        kTitles =  DataServer.getTimeTitleData();
        fragments = new ArrayList<>();
        for (TimeTitleBean title : kTitles){
            fragments.add(null);
        }

        titleFrPagerAdapter = new TimeTitleFrPagerAdapter(getSupportFragmentManager(), kTitles, fragments);
        pager.setAdapter(titleFrPagerAdapter);
        tabs.setViewPager(pager);
        tabs.setOnPagerTitleItemClickListener(new PagerSlidingTabStrip.OnPagerTitleItemClickListener() {
            @Override
            public void onSingleClickItem(int position) {
                refreshLoadData(position,1);
                if(position!=0){
                    ViewGroup tabContent = (ViewGroup) tabs.getChildAt(0);
                    ViewGroup view = (ViewGroup)tabContent.getChildAt(0);
                    TextView upperText = (TextView) view.getChildAt(1);
                    upperText.setAlpha(0.0f);
                }
            }

            @Override
            public void onDoubleClickItem(int position) {

            }
        });

        timeTitleAdapter =  new KTimeTitleLandAdapter(mContext,kTitles,R.layout.adapter_k_time_title_layout);
        k_time_title_listView.setAdapter(timeTitleAdapter);
        k_time_title_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            refreshLoadData(position,2);
            }
        });
        sharedPreferencesHelper = new SharedPreferencesHelper(mContext, "kFirstEnter");
        flagSolid = (boolean)sharedPreferencesHelper.getSharedPreference("kSolid",true);
        mKChartView.setCandleSolid(flagSolid);
        firstColorWindow();
        marketList = MyApplication.marketBeanList;
        minuteSize = TimeUtils.getMinute();
        queryIndex = 1;
        flagMinute = true;
        querySize = ConstantUtil.KCHARTSIZE;
        kchartData = new ArrayList<>();
        chartList = new ArrayList<>();
        minuteListData = new ArrayList<>();
        goods = getIntent().getStringExtra("goods");
        priceDecimal = AppUtils.getGoodsDecimal(goods);
        showTitleView();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (flagThread) {
                    try {
                        loadMinuteData();
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
//        }).start();
        }).start();
    }

    private void refreshLoadData(int position,int from){
        TimeTitleBean bean =  kTitles.get(position);
        queryIndex =  bean.getQueryType();
        if (queryIndex==0){
            queryIndex = 1;
            flagMinute = true;
        } else if (queryIndex==-1){
            SignalSortSettingActivity.actionStart(mContext);
            tabs.setViewPager(pager);
            pager.setCurrentItem(currentTabItem);
            return;
        } else {
            flagMinute = false;
        }
        if (from==2){
//            k_time_title_listView.setSelection(position);
            timeTitleAdapter.setSelectIndex(bean.getQueryType());
//            k_time_title_listView.smoothScrollToPosition(position+2);
            k_time_title_listView.post(new Runnable() {
                @Override
                public void run() {
                    k_time_title_listView.smoothScrollToPosition(3);
                }
            });
        }
        if (queryIndex==8 || queryIndex==10 || queryIndex==11){
            flagTime = false;
        } else {
            flagTime = true;
        }
        currentTabItem = position;
        if (flagMinute) {
            kMinuteChartView.setVisibility(View.VISIBLE);
            mKChartView.setVisibility(View.GONE);
        } else {
            kMinuteChartView.setVisibility(View.GONE);
            mKChartView.setVisibility(View.VISIBLE);
            querySize = ConstantUtil.KCHARTSIZE;
        }
        clearData();
        loadData(queryIndex);
    }

    private void initView() {
//        AppUtils.setCustomFont(mContext, kMaxText);
//        AppUtils.setCustomFont(mContext, kLowText);
//        AppUtils.setCustomFont(mContext, kTodayOpenText);
//        AppUtils.setCustomFont(mContext, kYesterdayCloseText);
        AppUtils.setCustomFont(this, goldNowTextView);
        mKChartView.setSelectorBackgroundColor(ContextCompat.getColor(mContext,R.color.FF262626));
        mKChartView.setSelectorTextSize(ViewUtil.Dp2Px(mContext,12));
        mKChartView.setSelectorTextColor(ContextCompat.getColor(mContext,R.color.whiteColor));
        mKChartView.setCandleSolid(false);//实心
        mAdapter = new KChartAdapter();
//        mKChartView.currentType = 1;
        mKChartView.currentType = 0;
        mKChartView.showLoading();
        mKChartView.setRefreshListener(this);
        onLoadMoreBegin(mKChartView);
        mKChartView.setAdapter(mAdapter);
        mKChartView.setGridRows(4);
        mKChartView.setGridColumns(4);
        mKChartView.setGridLineColor(ContextCompat.getColor(this, R.color.warmGrey30));
        mKChartView.setOnSelectedChangedListener(new BaseKChartView.OnSelectedChangedListener() {
            @Override
            public void onSelectedChanged(BaseKChartView view, Object point, int index) {
                KLineEntity data = (KLineEntity) point;
                Log.i("onSelectedChanged", "index:" + index + " closePrice:" + data.getClosePrice());
            }
        });
        mKChartView.setOnSelectedIndicatorsListener(new BaseKChartView.OnSelectedIndicatorsListener() {
            @Override
            public void onSelectedChanged(View.OnClickListener view, int index) {
                initWindowFilter();
            }
        });

        mKChartView.setUpIndicatorsNameListener(new BaseKChartView.OnUpInDicatorsNameListener() {
            @Override
            public void OnUpInDicatorsNameListener(String name, int index) {
                String name2 = DataServer.getIndicatorsName(index);
                operationNoteBean.setChildIndex(index);
                operationNoteBean.setChildName(name2);
                mKChartView.kIndicatorsChildBtn.setText(name2);
            }
        });
        mKChartView.setOnChangedIndicatorsListener(new MainDraw.OnChangedIndicatorsListener() {
            @Override
            public void onIndicatorsChanged(float Open, float max, float low, float close) {
                //  showPriceData(max,low,Open,close);
            }
        });
        mKChartView.k_main_set_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            SignalSettingActivity.actionStart(mContext,1);
            }
        });
        mKChartView.kChild_set_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            SignalSettingActivity.actionStart(mContext,2);
            }
        });
    }

    private void showPriceData(KMarketResultBean.MarketBean bean) {
        openPrice = bean.preclose_px;
        if (buy_gold_value_View != null) {
            float price = bean.last_px;
            float value = price - spread;
            if (isVertical){
                buy_gold_value_View.setText(String.format(priceDecimal,value));
                sell_gold_value_View.setText(String.format(priceDecimal,price));
                AppViewUtils.setDrawableRadius(buy_gold_View,5,ViewUtil.getKUpColor(mContext));
                AppViewUtils.setDrawableRadius(sell_gold_View,5,ViewUtil.getKLossColor(mContext));
            } else {
                k_land_buy_px_textView.setText(String.format(priceDecimal,value));
                k_land_sell_px_textView.setText(String.format(priceDecimal,price));
                AppViewUtils.setDrawableRadius(k_land_buy_layout,5,ViewUtil.getKUpColor(mContext));
                AppViewUtils.setDrawableRadius(k_land_sell_layout,5,ViewUtil.getKLossColor(mContext));
            }
        }
        if (isVertical) {
            AppViewUtils.kPriceVerStyle(mContext, bean,null,priceDecimal,kMaxText,kLowText,kTodayOpenText,kYesterdayCloseText);
        } else {
            AppViewUtils.kPriceLandStyle(mContext, bean,null,priceDecimal,k_land_high_textView,k_land_low_textView,k_land_open_textView,k_land_close_textView);
        }
    }

    private void showChartView() {
        maxPrice = 0.0f;
        minPrice = Float.MAX_VALUE;
        querySize = querySize + ConstantUtil.KCHARTSIZE;
        if (flagMinute) {
           for (KLineChartResultBean.ChartBean chartBean : chartList) {
                float tempPrice = chartBean.getClose_px();
                maxPrice = Math.max(maxPrice, tempPrice);
                minPrice = Math.min(minPrice, tempPrice);
                KMinuteLineEntity entity2 = new KMinuteLineEntity();
                entity2.price = chartBean.getClose_px();
                entity2.kTime = chartBean.getTime_stamp() * 1000;
                minuteListData.add(entity2);
            }
        }
        if (flagTime) {
            mKChartView.setDateTimeFormatter(new MinuteTimeFormatter());
        } else {
            mKChartView.setDateTimeFormatter(new DayTimeFormatter());
        }
        loadKMinuteData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_btn:
                finish();
                break;
            case R.id.right_more_btn:
                initkMoreWindow();
                break;
            case R.id.change_screen_btn:
                boolean isVertical = (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
                if (isVertical) {
                    this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } else {
                    this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
                }
                break;
            case R.id.k_solid_layout:
                if (moreWindow!=null){
                    moreWindow.dissmiss();
                }
                flagSolid = !flagSolid;
                mKChartView.setCandleSolid(flagSolid);
                sharedPreferencesHelper.put("kSolid",flagSolid);
                setkSolidImage();
               // flagSolid = (boolean)sharedPreferencesHelper.getSharedPreference("kSolid",false);
                break;
            case R.id.buy_gold_View:
                showLogin();
                break;
            case R.id.sell_gold_View:
                showLogin();
                break;
            case R.id.k_land_sell_layout:
                showLogin();
                break;
            case R.id.k_land_buy_layout:
                showLogin();
                break;
            case R.id.k_switch_kind_layout:
                initSwitchNameWindow(kTitleTextView);
                break;
            case R.id.k_land_title_textView:
                initSwitchNameWindow(left_window_view);
                break;
            case R.id.switch_gold_textView:
                goods = ConstantUtil.XAUUSD;
                showTitleView();
                switchNameWindow.dissmiss();
                clearData();
                loadData(queryIndex);
                break;
            case R.id.switch_silver_textView:
                goods = ConstantUtil.XAGUSD;
                showTitleView();
                clearData();
                loadData(queryIndex);
                switchNameWindow.dissmiss();
                break;
            case R.id.switch_us_textView:
                goods = ConstantUtil.USDOLLARINDEX;
                showTitleView();
                switchNameWindow.dissmiss();
                clearData();
                loadData(queryIndex);
                break;
            case R.id.k_type_textView:
                if (moreWindow!=null){
                    moreWindow.dissmiss();
                }
                kTypeWindow();
                break;
            case R.id.k_warning_textView:
                if (moreWindow!=null){
                    moreWindow.dissmiss();
                }
                if (!LoginHelper.getInstance().isLogin()) {
                    ToastUtils.showShort("你暂时未登陆");
                    LoginActivity.actionStart(mContext);
                } else {
                    if (ConstantUtil.XAUUSD.equals(goods)) {
                        WarnAddActivity.actionStart(mContext, 1);
                    } else {
                        WarnAddActivity.actionStart(mContext, 2);
                    }
                }
                break;
            case R.id.k_rule_textView:
                if (moreWindow!=null){
                    moreWindow.dissmiss();
                }
                WebViewActivity.actionStart(ChartActivity.this, NetConstant.Companion.getBASE_URL() + "/index.php?app=market&act=contract_view_app", "交易规则", false);
                break;
        }
    }

    @Override
    public void onLoadMoreBegin(KChartView chart) {
        mKChartView.hideLoading();
        loadData(queryIndex);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mKChartView.resultKPaintColor();
        kMinuteChartView.resultKPaintColor();
        if (requestCode == 100 && resultCode == 100) {
            kTitles.clear();
            fragments.clear();
            kTitles =  DataServer.getTimeTitleData();
            pager.setAdapter(titleFrPagerAdapter);
            tabs.setViewPager(pager);

            kTitles =  DataServer.getTimeTitleData();
            for(int i=0;i<kTitles.size();i++){
                fragments.add(null);
            }
            titleFrPagerAdapter = new TimeTitleFrPagerAdapter(getSupportFragmentManager(), kTitles, fragments);
            pager.setAdapter(titleFrPagerAdapter);
            tabs.setViewPager(pager);
            kMinuteChartView.setVisibility(View.VISIBLE);
            mKChartView.setVisibility(View.GONE);
            pager.setCurrentItem(0,true);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ViewGroup tabContent = (ViewGroup) tabs.getChildAt(0);
                    int count = tabContent.getChildCount();
                    for(int i=0; i<count; i++){
                        ViewGroup view = (ViewGroup)tabContent.getChildAt(i);
                        TextView lowwerText = (TextView) view.getChildAt(0);
                        TextView upperText = (TextView) view.getChildAt(1);
                        if(i==0){
                            upperText.setAlpha(1.0f);
                        } else {
                            upperText.setAlpha(0.0f);
                            lowwerText.setAlpha(1.0f);
                        }
                    }
                }
            }, 100);

            timeTitleAdapter =  new KTimeTitleLandAdapter(mContext,kTitles,R.layout.adapter_k_time_title_layout);
            k_time_title_listView.setAdapter(timeTitleAdapter);
//
//            refreshLoadData(0,1);
        } else if (requestCode == 101 && resultCode == 101) {
            //指标
            updateIndexParams();
            DataHelper.calculate(kchartData);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        flagThread = false;
    }

}

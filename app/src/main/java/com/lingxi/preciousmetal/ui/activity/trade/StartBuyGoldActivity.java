package com.lingxi.preciousmetal.ui.activity.trade;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.tifezh.kchartlib.chart.BaseKChartView;
import com.github.tifezh.kchartlib.chart.KChartView;
import com.github.tifezh.kchartlib.chart.KMinuteChartView;
import com.github.tifezh.kchartlib.chart.draw.MainDraw;
import com.github.tifezh.kchartlib.chart.entity.KMinuteLineEntity;
import com.github.tifezh.kchartlib.chart.formatter.DateFormatter;
import com.github.tifezh.kchartlib.chart.formatter.DayTimeFormatter;
import com.github.tifezh.kchartlib.chart.formatter.MinuteTimeFormatter;
import com.github.tifezh.kchartlib.chart.formatter.SilverValueFormatter;
import com.github.tifezh.kchartlib.chart.formatter.ValueFormatter;
import com.gxz.PagerSlidingTabStrip;
import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.KlineChartRequestMo;
import com.lingxi.biz.domain.responseMo.KLineChartResultBean;
import com.lingxi.biz.domain.responseMo.KMarketResultBean;
import com.lingxi.biz.domain.responseMo.TradeAllEntrustBean;
import com.lingxi.biz.domain.responseMo.TradeAllPositionBean;
import com.lingxi.biz.service.KMarketService;
import com.lingxi.biz.util.ConstantUtil;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.MyApplication;
import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;
import com.lingxi.preciousmetal.chart.adapter.KChartAdapter;
import com.lingxi.preciousmetal.chart.data.DataHelper;
import com.lingxi.preciousmetal.chart.entity.KLineEntity;
import com.lingxi.preciousmetal.common.EventBusKeyDefine;
import com.lingxi.preciousmetal.domain.DataServer;
import com.lingxi.preciousmetal.domain.KOperationNoteBean;
import com.lingxi.preciousmetal.domain.TimeTitleBean;
import com.lingxi.preciousmetal.ui.activity.SignalSortSettingActivity;
import com.lingxi.preciousmetal.ui.adapter.TimeTitleFrPagerAdapter;
import com.lingxi.preciousmetal.ui.fragment.trade.EntryOrdersFragment;
import com.lingxi.preciousmetal.ui.fragment.trade.OrderDetailFragment;
import com.lingxi.preciousmetal.ui.fragment.trade.TradingInTimeFragment;
import com.lingxi.preciousmetal.ui.widget.dialog.DialogManager;
import com.lingxi.preciousmetal.util.utilCode.AppViewUtils;
import com.lingxi.preciousmetal.util.utilCode.LogUtils;
import com.lingxi.preciousmetal.util.utilCode.SharedPreferencesHelper;
import com.lingxi.preciousmetal.util.utilCode.TimeUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartBuyGoldActivity extends TranslucentStatusBarActivity implements KChartView.KChartRefreshListener{
    @BindView(R.id.change_good_btn)
    LinearLayout changeGoodBtn;
    @BindView(R.id.buy_change_goods_tView)
    TextView buyChangeGoodsTView;
    @BindView(R.id.order_buy_sell_view)
    TextView orderBuySellView;
    @BindView(R.id.order_type_view)
    TextView orderTypeView;
    @BindView(R.id.order_name_view)
    TextView orderNameView;
    @BindView(R.id.order_detail_title_view)
    LinearLayout orderDetailTitleView;
    @BindView(R.id.fl_content)
    LinearLayout flContent;
    @BindView(R.id.trade_title_RadioGroup)
    RadioGroup tradeTitleRadioGroup;
    @BindView(R.id.order_market_layout)
    View orderMarketLayout;
    @BindView(R.id.trade_in_time_radio)
    RadioButton tradeInTimeRadio;
    @BindView(R.id.trade_enter_order_radio)
    RadioButton tradeEnterOrderRadio;
    @BindView(R.id.back_btn)
    ImageView backBtn;
    @BindView(R.id.add_order_title_view)
    LinearLayout addOrderTitleView;

    @BindView(R.id.kchart_view)
    KChartView mKChartView;
    @BindView(R.id.minuteChartView)
    KMinuteChartView minuteChartView;
    @BindView(R.id.trade_now_price_textView)
    TextView tradeNowPriceTextView;
    @BindView(R.id.trade_now_price_percent_textView)
    TextView tradeNowPricePercentTextView;
    @BindView(R.id.k_trade_tools_tabs)
    PagerSlidingTabStrip kTradeToolsTabs;
    @BindView(R.id.k_trade_main_pager)
    ViewPager kTradeMainPager;

    private List<KLineEntity> kchartData;
    private int queryIndex;
    private KChartAdapter mAdapter;
    private List<KLineChartResultBean.ChartBean> chartList;
    private boolean flagMinute = false,flagTime = true,flagSolid;
    private SharedPreferencesHelper sharedPreferencesHelper;
    private int minuteSize;
    private List<KMinuteLineEntity> minuteListData = null;
    private String goodsName,goodsCode;
    private TradingInTimeFragment inTimeFragment;
    private EntryOrdersFragment ordersFragment;
    private List<TimeTitleBean> kTitles;
    private float openPrice, minPrice, maxPrice = 0.0f;
    private String priceDecimal = "%.2f";
    private boolean flagFrist = true;

    public static void startMyActivity(Context context, int type) {
        Intent intent = new Intent(context, StartBuyGoldActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    public static void startMyActivity(Context context, int type,String goodsName,String goodsCode) {
        Intent intent = new Intent(context, StartBuyGoldActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("goodsName", goodsName);
        intent.putExtra("goodsCode", goodsCode);
        context.startActivity(intent);
    }

    public static void startMyActivity(Context context, int type, TradeAllPositionBean bean) {
        Intent intent = new Intent(context, StartBuyGoldActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("positionBean", bean);
        context.startActivity(intent);
    }

    public static void startMyActivity(Context context, int type, TradeAllEntrustBean bean) {
        Intent intent = new Intent(context, StartBuyGoldActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("positionBean", bean);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_buy_gold);
        ButterKnife.bind(this);
        RadioGroup radioGroup = findViewById(R.id.trade_title_RadioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (inTimeFragment==null||ordersFragment==null)
                return;
                if (checkedId == R.id.trade_enter_order_radio) {
                    getSupportFragmentManager().beginTransaction().hide(inTimeFragment).commit();
                    getSupportFragmentManager().beginTransaction().show(ordersFragment).commit();
                } else {
                    getSupportFragmentManager().beginTransaction().show(inTimeFragment).commit();
                    getSupportFragmentManager().beginTransaction().hide(ordersFragment).commit();
                }
            }
        });

        Intent intent = getIntent();
        int type = intent.getIntExtra("type", 0);
        goodsCode = intent.getStringExtra("goodsCode");
        goodsName = intent.getStringExtra("goodsName");
        if (TextUtils.isEmpty(goodsCode)){
            goodsCode = ConstantUtil.XAUUSD;
            buyChangeGoodsTView.setText("伦敦金(LLG)");
        } else {
            buyChangeGoodsTView.setText(goodsName);
        }

        if (type == 0) {
            tradeInTimeRadio.setChecked(true);
            //新建订单
            tradeTitleRadioGroup.setVisibility(View.VISIBLE);
            orderDetailTitleView.setVisibility(View.GONE);
            ordersFragment = EntryOrdersFragment.newInstance(goodsCode);
            inTimeFragment = TradingInTimeFragment.newInstance(goodsCode);
            getSupportFragmentManager().beginTransaction().add(R.id.fl_content, inTimeFragment).commit();
            getSupportFragmentManager().beginTransaction().add(R.id.fl_content, ordersFragment).commit();
        } else if (type == 1) {
            tradeEnterOrderRadio.setChecked(true);
            //挂单交易
            tradeTitleRadioGroup.setVisibility(View.VISIBLE);
            orderDetailTitleView.setVisibility(View.GONE);
            ordersFragment = EntryOrdersFragment.newInstance(goodsCode);
            inTimeFragment = TradingInTimeFragment.newInstance(goodsCode);
            getSupportFragmentManager().beginTransaction().add(R.id.fl_content, ordersFragment).commit();
            getSupportFragmentManager().beginTransaction().add(R.id.fl_content, inTimeFragment).commit();
        } else if (type == 2) {
            //持仓管理
            TradeAllPositionBean bean = (TradeAllPositionBean) intent.getSerializableExtra("positionBean");
            AppViewUtils.setBuyOrSell(getApplicationContext(), bean.getOrderDirection(), orderBuySellView);
            orderMarketLayout.setVisibility(View.GONE);
            orderTypeView.setVisibility(View.GONE);
            tradeTitleRadioGroup.setVisibility(View.GONE);
            orderDetailTitleView.setVisibility(View.VISIBLE);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, OrderDetailFragment.newInstance(1, bean)).commit();
        } else if (type == 3) {
            // 挂单管理
            TradeAllEntrustBean bean = (TradeAllEntrustBean) intent.getSerializableExtra("positionBean");
            orderMarketLayout.setVisibility(View.GONE);
            tradeTitleRadioGroup.setVisibility(View.GONE);
            orderDetailTitleView.setVisibility(View.VISIBLE);
            AppViewUtils.setBuyOrSell(getApplicationContext(), bean.getOrderDirection(), orderBuySellView);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, OrderDetailFragment.newInstance(2, bean)).commit();
        }
        initKData();
    }

    @OnClick({R.id.back_btn, R.id.change_good_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                finish();
                break;
            case R.id.change_good_btn:
                if (ConstantUtil.XAGUSD.equals(goodsCode)){
                    goodsCode = ConstantUtil.XAUUSD;
                    buyChangeGoodsTView.setText("伦敦金(LLG)");
                    mKChartView.setValueFormatter(new ValueFormatter());
                } else if (ConstantUtil.XAUUSD.equals(goodsCode)){
                    mKChartView.setValueFormatter(new SilverValueFormatter());
                    goodsCode = ConstantUtil.XAGUSD;
                    buyChangeGoodsTView.setText("伦敦银(LLS)");
                }
                clearData();
                getOpenPrice();
                loadKData(queryIndex);
                EventBus.getDefault().post(new EventBusKeyDefine.EventBusMsgData<String>(EventBusKeyDefine.EVENTBUS_SWITCH_GOODS_NAME, goodsCode));
                break;
        }
    }

    @Override
    public void onLoadMoreBegin(KChartView chart) {
        mKChartView.hideLoading();
        loadKData(queryIndex);
    }

    private void loadMinuteData() {
        KlineChartRequestMo requestMo = new KlineChartRequestMo(goodsCode, 1, 1);
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
                    KMinuteLineEntity entity = new KMinuteLineEntity();
                    entity.kTime = bean.getTime_stamp() * 1000;
                    entity.price = closePx;
                    if (flagMinute){
                        minuteChartView.addMinutePrice(entity);
                    }
                    float px_change = closePx - openPrice;
                    float px_change_rate = px_change/openPrice*100;
                    KMarketResultBean.MarketBean marketBean = new KMarketResultBean.MarketBean();
                    marketBean.px_change = px_change;
                    marketBean.px_change_rate = px_change_rate;
                    marketBean.last_px = closePx;
                    marketBean.p_code = goodsCode;
                    AppViewUtils.setNowPrice(mContext,tradeNowPriceTextView, tradeNowPricePercentTextView, marketBean);
                    if (inTimeFragment!=null){
                        inTimeFragment.setNowPrice(marketBean.last_px,goodsCode);
                    }
                    int size = kchartData.size();
                    if (size>0){
                        //更新最后一根k线
                        KLineEntity lastEntity =  kchartData.get(size-1);
                        if (lastEntity.kDate!=bean.getTime_stamp() * 1000){
                            kchartData.addAll(AppViewUtils.getKData(resultBean.getChart()));
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
                    if (flagMinute){
                        mKChartView.setVisibility(View.GONE);
                        minuteChartView.addMinutePrice(entity);
                    } else {
                        minuteChartView.setVisibility(View.GONE);
                        mKChartView.setVisibility(View.VISIBLE);
                    }

                }
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                DialogManager.getInstance().cancellLoadingDialog();
            }
        });
    }

    private void getOpenPrice(){
        List<KMarketResultBean.MarketBean> marketList =  MyApplication.marketBeanList;
        KMarketResultBean.MarketBean marketBean = null;
        if (ConstantUtil.XAUUSD.equals(goodsCode)) {
            if (marketList != null && marketList.size() >= 3) {
                marketBean = marketList.get(0);
                openPrice = marketBean.preclose_px;
            }
            priceDecimal = "%.2f";
        } else if (ConstantUtil.XAGUSD.equals(goodsCode)) {
            if (marketList != null && marketList.size() >= 3) {
                marketBean = marketList.get(1);
                openPrice = marketBean.preclose_px;
            }
            priceDecimal = "%.3f";
        }
    }

    private void initKData() {
        //初始化数据
        minuteSize = TimeUtils.getMinute();
        kchartData = new ArrayList<>();
        chartList = new ArrayList<>();
        minuteListData = new ArrayList<>();
        queryIndex = 1;
        mAdapter = new KChartAdapter();
        mKChartView.currentType = 1;
        flagMinute = true;

        //设置是否空心
        sharedPreferencesHelper = new SharedPreferencesHelper(mContext, "kFirstEnter");
        flagSolid = (boolean)sharedPreferencesHelper.getSharedPreference("kSolid",false);
        mKChartView.setCandleSolid(flagSolid);
        KOperationNoteBean operationNoteBean = MyApplication.operationNoteBean;
        int kShowType = operationNoteBean.getkCurrentType();
        mKChartView.setShowType(kShowType);
        mKChartView.setCurrentType(1);
        mKChartView.showLoading();
        onLoadMoreBegin(mKChartView);
        mKChartView.setRefreshListener(this);
        mKChartView.setAdapter(mAdapter);
        mKChartView.setDateTimeFormatter(new DateFormatter());
        mKChartView.setGridRows(4);
        mKChartView.setGridColumns(4);
        mKChartView.setGridLineColor(ContextCompat.getColor(this, R.color.brownishGreyTwo));
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
            }
        });
        mKChartView.setOnChangedIndicatorsListener(new MainDraw.OnChangedIndicatorsListener() {
            @Override
            public void onIndicatorsChanged(float Open, float max, float low, float close) {
                //  showPriceData(max,low,Open,close);
            }
        });

        getOpenPrice();
        kTitles = DataServer.getTimeTitleData();
        kTitles.remove(kTitles.size()-1);
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (TimeTitleBean title : kTitles) {
            fragments.add(null);
        }
        TimeTitleFrPagerAdapter titleFrPagerAdapter = new TimeTitleFrPagerAdapter(getSupportFragmentManager(), kTitles, fragments);
        kTradeMainPager.setAdapter(titleFrPagerAdapter);
        kTradeToolsTabs.setViewPager(kTradeMainPager);
        kTradeToolsTabs.setOnPagerTitleItemClickListener(new PagerSlidingTabStrip.OnPagerTitleItemClickListener() {
            @Override
            public void onSingleClickItem(int position) {
                refreshLoadData(position);
            }

            @Override
            public void onDoubleClickItem(int position) {
            }
        });
//        loadKData(queryIndex);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        loadMinuteData();
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void refreshLoadData(int position){
        if (kTitles == null || kTitles.size()<position){
            return;
        }
        TimeTitleBean bean =  kTitles.get(position);
        queryIndex =  bean.getQueryType();
        if (queryIndex==0){
            queryIndex = 1;
            flagMinute = true;
        } else {
            flagMinute = false;
        }
        if (queryIndex==8 || queryIndex==10 || queryIndex==11){
            flagTime = false;
        } else {
            flagTime = true;
        }
        clearData();
        loadKData(queryIndex);
    }


    private void loadKData(int type) {
        int querySize;
        DialogManager.getInstance().showLoadingDialog(this, "", false);
        if (flagMinute) {
            querySize = minuteSize;
        } else {
            querySize = ConstantUtil.KCHARTSIZE;
        }
        KlineChartRequestMo requestMo = new KlineChartRequestMo(goodsCode, type, querySize);
        KMarketService.marketChart(requestMo, new BizResultListener<KLineChartResultBean>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, KLineChartResultBean resultBean) {

            }

            @Override
            public void onSuccess(KLineChartResultBean resultBean) {
                chartList.clear();
                if (resultBean.getChart() != null && resultBean.getChart().size() > 0) {
                    chartList.addAll(resultBean.getChart());
                }
                showChartView();
                DialogManager.getInstance().cancellLoadingDialog();
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                LogUtils.i("k数据请求失败==");
                DialogManager.getInstance().cancellLoadingDialog();
            }
        });
    }


    private void clearData() {
        chartList.clear();
        kchartData.clear();
        minuteListData.clear();
        mAdapter = new KChartAdapter();
        mKChartView.setAdapter(mAdapter);
    }

    private void loadKMinuteData() {
        if (flagMinute) {
            minuteChartView.setVisibility(View.VISIBLE);
            mKChartView.setVisibility(View.GONE);
            minuteChartView.setBasicsPrice(openPrice, maxPrice, minPrice);
            minuteChartView.setPriceDecimalPoint(priceDecimal);
            minuteChartView.setMinutePrice(minuteListData);
        }
        if (flagFrist){
            //第一次全部隐藏
            minuteChartView.setVisibility(View.VISIBLE);
            mKChartView.setVisibility(View.GONE);
            flagFrist = false;
        }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mKChartView.upChildIndicatorDraw(2);
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

    private void showChartView() {
        maxPrice = 0.0f;
        minPrice = Float.MAX_VALUE;
        if (flagMinute){
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
}

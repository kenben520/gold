package com.lingxi.preciousmetal.ui.activity.trade;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhouwei.library.CustomPopWindow;
import com.gxz.PagerSlidingTabStrip;
import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.GetAccountInfoRequestMo;
import com.lingxi.biz.domain.responseMo.AccountInfoMo;
import com.lingxi.biz.service.TradeService;
import com.lingxi.biz.util.ConstantUtil;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;
import com.lingxi.preciousmetal.ui.adapter.NewsFrPagerAdapter;
import com.lingxi.preciousmetal.ui.fragment.trade.TradeInfoCommonFragment;
import com.lingxi.preciousmetal.ui.widget.dialog.DialogManager;
import com.lingxi.preciousmetal.util.utilCode.AppUtils;
import com.lingxi.preciousmetal.util.utilCode.ScreenUtils;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.util.ConvertUtils;
@Deprecated
public class TradeUserInfoActivity extends TranslucentStatusBarActivity implements View.OnClickListener{

    @BindView(R.id.my_profit_textView)
    TextView myProfitTextView;
    @BindView(R.id.my_margin_tView)
    TextView myMarginTView;
    @BindView(R.id.my_margin_ratio_tView)
    TextView myMarginRatioTView;
    @BindView(R.id.my_balance_tView)
    TextView myBalanceTView;
    @BindView(R.id.trade_main_tabs)
    PagerSlidingTabStrip tradeMainTabs;
    @BindView(R.id.trade_main_pager)
    ViewPager tradeMainPager;
    @BindView(R.id.add_trade_btn)
    ImageView addTradeBtn;
    Unbinder unbinder;
    private int currentIndex;

    public static void startMyActivity(Context context){
        Intent intent = new Intent(context, TradeUserInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_user_info);
        unbinder = ButterKnife.bind(this);
        initView();
        loadData();
    }

    private void initTradeHistoryFilter(){
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.window_trade_history, null);
        windowTrade =  new CustomPopWindow.PopupWindowBuilder(mContext)
                .setView(contentView).size(ScreenUtils.getScreenWidth(),ScreenUtils.getScreenHeight())
                .create().showAsDropDown(myProfitTextView);
        TextView w_close_view = contentView.findViewById(R.id.w_close_view);
        w_close_view.setOnClickListener(this);
        TextView w_sure_view = contentView.findViewById(R.id.w_sure_view);
        w_sure_view.setOnClickListener(this);
        TextView w_start_time_view = contentView.findViewById(R.id.w_start_time_view);
        w_start_time_view.setOnClickListener(this);
        TextView w_end_time_view = contentView.findViewById(R.id.w_end_time_view);
        w_end_time_view.setOnClickListener(this);
    }

    private CustomPopWindow windowMoney,windowTrade;

    public void initMoneyFilter(){
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.window_money_filter, null);
        windowMoney =  new CustomPopWindow.PopupWindowBuilder(mContext)
                .setView(contentView).size(ScreenUtils.getScreenWidth(),ScreenUtils.getScreenHeight())
                .create().showAsDropDown(myProfitTextView);
        TextView w_close_view = contentView.findViewById(R.id.w_close_view);
        w_close_view.setOnClickListener(this);
        TextView w_sure_view = contentView.findViewById(R.id.w_sure_view);
        w_sure_view.setOnClickListener(this);
        TextView w_start_time_view = contentView.findViewById(R.id.w_start_time_view);
        w_start_time_view.setOnClickListener(this);
        TextView w_end_time_view = contentView.findViewById(R.id.w_end_time_view);
        w_end_time_view.setOnClickListener(this);
    }

    public void onYearMonthDayPicker(final View view){
        Calendar calendar = Calendar.getInstance();
        final DatePicker picker = new DatePicker(this);
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setTopPadding(ConvertUtils.toPx(mContext, 10));
        picker.setRangeEnd(2111, 1, 11);
        picker.setRangeStart(2016, 8, 29);
        picker.setSelectedItem(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH));
        picker.setResetWhileWheel(false);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                //  ToastUtils.showShort (year + "-" + month + "-" + day);
                TextView textView = (TextView) view;
                textView.setText(year + "-" + month + "-" + day);
            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
    }

    private void loadData() {
        DialogManager.getInstance().showLoadingDialog(this, "", false);
        GetAccountInfoRequestMo loginRequestMo = new GetAccountInfoRequestMo(ConstantUtil.ACCOUNT_ID);
        TradeService.getAccountInfo(loginRequestMo, new BizResultListener<AccountInfoMo>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, AccountInfoMo accountInfoMo) {

            }

            @Override
            public void onSuccess(AccountInfoMo accountInfoMo) {
                DialogManager.getInstance().cancellLoadingDialog();
                double profit = accountInfoMo.getFloatingProfit();
                AppUtils.setCustomFont(mContext,myProfitTextView);
                String value =  String.format("%.2f",profit);
                if (profit>0){
                    myProfitTextView.setTextColor(ContextCompat.getColor(mContext,R.color.green));
                } else {
                    myProfitTextView.setTextColor(ContextCompat.getColor(mContext,R.color.chart_red));
                }
                myProfitTextView.setText(value);
                value =  String.format("%.2f",accountInfoMo.getMarginAvailable());
                AppUtils.setCustomFont(getApplicationContext(),myMarginTView);
                myMarginTView.setText(value);

                value =  String.format("%.2f",accountInfoMo.getMarginUtilisation());
                AppUtils.setCustomFont(getApplicationContext(),myMarginRatioTView);
                myMarginRatioTView.setText(value);

                value =  String.format("%.2f",accountInfoMo.getEquity());
                AppUtils.setCustomFont(getApplicationContext(),myBalanceTView);
                myBalanceTView.setText(value);
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                ToastUtils.showShort(bizMessage);
                DialogManager.getInstance().cancellLoadingDialog();
            }
        });
    }


    @OnClick(R.id.add_trade_btn)
    public void onViewClicked() {
        if (currentIndex==3){
            initMoneyFilter();
        } else if (currentIndex==2){
            initTradeHistoryFilter();
        } else {
            StartBuyGoldActivity.startMyActivity(mContext,currentIndex);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id==R.id.w_end_time_view){
            onYearMonthDayPicker(v);
        } else if (id==R.id.w_start_time_view){
            onYearMonthDayPicker(v);
        } else if (id==R.id.w_close_view){
            if (currentIndex==3){
                windowMoney.dissmiss();
            } else {
                windowTrade.dissmiss();
            }
        } else if (id==R.id.w_sure_view){
            if (currentIndex==3){
                windowMoney.dissmiss();
            } else {
                windowTrade.dissmiss();
            }
        }
    }

    private void initView() {
        tradeMainPager = findViewById(R.id.trade_main_pager);
        tradeMainTabs = findViewById(R.id.trade_main_tabs);
        ArrayList<String> titles = new ArrayList<>();
        titles.add("持仓");
        titles.add("挂单");
        titles.add("交易历史");
        titles.add("资金流水");
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(TradeInfoCommonFragment.newInstance("1"));
        fragments.add(TradeInfoCommonFragment.newInstance("2"));
        fragments.add(TradeInfoCommonFragment.newInstance("3"));
        fragments.add(TradeInfoCommonFragment.newInstance("4"));
        currentIndex = 0;
        tradeMainPager.setAdapter(new NewsFrPagerAdapter(getSupportFragmentManager(), titles, fragments));
        tradeMainTabs.setViewPager(tradeMainPager);
        tradeMainPager.setCurrentItem(0);
        tradeMainTabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currentIndex = position;
                if (position==2 || position==3){
                    addTradeBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.lx_exchange_history_filter));
                } else {
                    addTradeBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.lx_exchange_home_add_neworder));
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}

package com.lingxi.preciousmetal.ui.fragment.trade;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.zhouwei.library.CustomPopWindow;
import com.github.tifezh.kchartlib.utils.ViewUtil;
import com.gxz.PagerSlidingTabStrip;
import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.GetAccountInfoRequestMo;
import com.lingxi.biz.domain.responseMo.AccountInfoMo;
import com.lingxi.biz.service.TradeService;
import com.lingxi.biz.util.ConstantUtil;
import com.lingxi.common.base.BaseFragment;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.common.EventBusKeyDefine;
import com.lingxi.preciousmetal.common.IntentParam;
import com.lingxi.preciousmetal.login.LoginHelper;
import com.lingxi.preciousmetal.ui.activity.LoginActivity;
import com.lingxi.preciousmetal.ui.activity.RegisterActivity;
import com.lingxi.preciousmetal.ui.activity.TradeHistoryFilterActivity;
import com.lingxi.preciousmetal.ui.activity.TradeWaterBillsFilterActivity;
import com.lingxi.preciousmetal.ui.activity.trade.StartBuyGoldActivity;
import com.lingxi.preciousmetal.ui.adapter.NewsFrPagerAdapter;
import com.lingxi.preciousmetal.util.utilCode.AppUtils;
import com.lingxi.preciousmetal.util.utilCode.ScreenUtils;
import com.lingxi.preciousmetal.util.utilCode.TimeUtils;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.util.ConvertUtils;

public class TradeUserInfoFragment extends BaseFragment implements View.OnClickListener {
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
    @BindView(R.id.btn_filter)
    ImageView btnFilter;
    Unbinder unbinder;
    @BindView(R.id.btn_add_trade)
    View btnAddTrade;
    private int currentIndex;
    private View trade_un_login, trade_login;
    private DatePicker picker = null;
    private boolean isFirstLoad;

    public static TradeUserInfoFragment newInstance() {
        TradeUserInfoFragment tradeFragment = new TradeUserInfoFragment();
        return tradeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trade_main, container, false);
        trade_un_login = view.findViewById(R.id.trade_un_login);
        trade_login = view.findViewById(R.id.trade_login);
        btnFilter = view.findViewById(R.id.btn_filter);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
        registorEventBus();
        initView(root);
        initData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            if (isFirstLoad) {
                initData();
            }
        }
    }

    private void initData() {
        refreshLoginStateView();
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
            trade_un_login.setVisibility(View.GONE);
            trade_login.setVisibility(View.VISIBLE);
            loadData();
        } else {
            trade_un_login.setVisibility(View.VISIBLE);
            trade_login.setVisibility(View.GONE);
        }
    }

    private void initTradeHistoryFilter() {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.window_trade_history, null);
        windowTrade = new CustomPopWindow.PopupWindowBuilder(mContext)
                .setView(contentView).size(ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight())
                .create().showAsDropDown(myProfitTextView);
        publicTimeView(contentView);
    }

    private CustomPopWindow windowMoney, windowTrade;
    //    private String moneyStartTime,moneyEndTime,orderStartTime,orderEndTime;
    private String filterStartTime, filterEndTime;

    public void initMoneyFilter() {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.window_money_filter, null);
        windowMoney = new CustomPopWindow.PopupWindowBuilder(mContext)
                .setView(contentView).size(ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight())
                .create().showAsDropDown(myProfitTextView);
        publicTimeView(contentView);
    }

    private Calendar selectCalendar, startCalendar, endCalendar;
    private TextView startTimeTextView, endTimeTextView;

    private void publicTimeView(View contentView) {

        Calendar calendar = Calendar.getInstance();
        endCalendar = calendar;
        startCalendar = calendar;

        TextView w_close_view = contentView.findViewById(R.id.w_close_view);
        w_close_view.setOnClickListener(this);
        TextView w_sure_view = contentView.findViewById(R.id.w_sure_view);
        w_sure_view.setOnClickListener(this);

        startTimeTextView = contentView.findViewById(R.id.w_start_time_view);
        startTimeTextView.setOnClickListener(this);
        endTimeTextView = contentView.findViewById(R.id.w_end_time_view);
        endTimeTextView.setOnClickListener(this);

        filterStartTime = startCalendar.get(Calendar.YEAR) + "-" + (startCalendar.get(Calendar.MONTH)) + "-" + startCalendar.get(Calendar.DAY_OF_MONTH);
        filterEndTime = endCalendar.get(Calendar.YEAR) + "-" + (endCalendar.get(Calendar.MONTH) + 1) + "-" + (endCalendar.get(Calendar.DAY_OF_MONTH));
        //点击事件
        startTimeTextView.setTag(1);
        endTimeTextView.setTag(2);
        startTimeTextView.setText(filterStartTime);
        endTimeTextView.setText(filterEndTime);

        RadioGroup group = contentView.findViewById(R.id.time_RadioGroup);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Calendar calendar = Calendar.getInstance();
                endCalendar = Calendar.getInstance();
                if (checkedId == R.id.time_today_radio) {
                    filterEndTime = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + (calendar.get(Calendar.DAY_OF_MONTH));
                    filterStartTime = filterEndTime;
                } else if (checkedId == R.id.time_week_radio) {
                    filterEndTime = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + (calendar.get(Calendar.DAY_OF_MONTH));
                    calendar.add(Calendar.DAY_OF_WEEK, -7);
                    filterStartTime = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + ((calendar.get(Calendar.DAY_OF_MONTH)));
                } else if (checkedId == R.id.time_month_radio) {
                    filterStartTime = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH)) + "-" + (calendar.get(Calendar.DAY_OF_MONTH));
                    filterEndTime = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + (calendar.get(Calendar.DAY_OF_MONTH));
                    calendar.add(Calendar.MONTH, -1);
                } else if (checkedId == R.id.time_year_radio) {
                    filterEndTime = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + (calendar.get(Calendar.DAY_OF_MONTH));
                    calendar.add(Calendar.YEAR, -1);
                    filterStartTime = (calendar.get(Calendar.YEAR)) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + (calendar.get(Calendar.DAY_OF_MONTH));
                }
                startCalendar = calendar;
                startTimeTextView.setText(filterStartTime);
                endTimeTextView.setText(filterEndTime);
            }
        });
    }

//    private DatePicker initDatePicker(){
//        if (picker==null)
//        picker = new DatePicker(mContext);
//        Calendar calendar = Calendar.getInstance();
//        picker.setCanceledOnTouchOutside(true);
//        picker.setUseWeight(true);
//        picker.setTopPadding(ConvertUtils.toPx(mContext, 10));
//        picker.setRangeEnd(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH));
//        picker.setRangeStart(2000, 8, 29);
//        picker.setSelectedItem(selectCalendar.get(Calendar.YEAR),selectCalendar.get(Calendar.MONTH)+1, selectCalendar.get(Calendar.DAY_OF_MONTH));
//        picker.setResetWhileWheel(false);
//        return picker;
//    }

    public void onYearMonthDayPicker(final TextView textView) {
        picker = new DatePicker(mContext);
        Calendar calendar = Calendar.getInstance();
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setTopPadding(ConvertUtils.toPx(mContext, 10));
        picker.setRangeEnd(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        picker.setRangeStart(2000, 8, 29);
        picker.setSelectedItem(selectCalendar.get(Calendar.YEAR), selectCalendar.get(Calendar.MONTH) + 1, selectCalendar.get(Calendar.DAY_OF_MONTH));
        picker.setResetWhileWheel(false);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                int tag = (int) textView.getTag();
                String time = year + "-" + month + "-" + day;
                textView.setText(time);
                if (tag == 1) {
                    filterStartTime = time;
                } else if (tag == 2) {
                    filterEndTime = time;
                }
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
        //  DialogManager.getInstance().showLoadingDialog(mContext, "", false);
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
                isFirstLoad = true;
                LoginHelper.getInstance().updateAccountInfoMo(accountInfoMo);
                //  DialogManager.getInstance().cancellLoadingDialog();
                double profit = accountInfoMo.getFloatingProfit();
                String value = String.format("%.2f", profit);
                AppUtils.setCustomFont(mContext, myProfitTextView);
                myProfitTextView.setText(value);
                if (profit >= 0) {
                    myProfitTextView.setTextColor(ViewUtil.getKUpColor(mContext));
                } else {
                    myProfitTextView.setTextColor(ViewUtil.getKLossColor(mContext));
                }

                value = String.format("%.2f", accountInfoMo.getMarginAvailable());
                AppUtils.setCustomFont(mContext, myMarginTView);
                myMarginTView.setText(value);

                value = String.format("%.2f", accountInfoMo.getMarginUtilisation());
                AppUtils.setCustomFont(mContext, myMarginRatioTView);
                myMarginRatioTView.setText(value + "%");

                value = String.format("%.2f", accountInfoMo.getEquity());
                AppUtils.setCustomFont(mContext, myBalanceTView);
                myBalanceTView.setText(value);
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
//                ToastUtils.showShort(bizMessage);
                //  DialogManager.getInstance().cancellLoadingDialog();
            }
        });
    }

    @OnClick({R.id.btn_register, R.id.btn_login, R.id.btn_filter, R.id.btn_add_trade})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                RegisterActivity.actionStart(getActivity());
                break;
            case R.id.btn_login:
                LoginActivity.actionStart(getActivity());
                break;
            case R.id.btn_add_trade:
                StartBuyGoldActivity.startMyActivity(mContext, currentIndex);
                break;
            case R.id.btn_filter:
                if (currentIndex == 3) {
                    TradeWaterBillsFilterActivity.actionStart(this);
                } else if (currentIndex == 2) {
                    TradeHistoryFilterActivity.actionStart(this);
                } else {
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.w_end_time_view) {
            selectCalendar = endCalendar;
            onYearMonthDayPicker((TextView) v);
        } else if (id == R.id.w_start_time_view) {
            selectCalendar = startCalendar;
            onYearMonthDayPicker((TextView) v);
        } else if (id == R.id.w_close_view) {
            if (currentIndex == 3) {
                windowMoney.dissmiss();
            } else {
                windowTrade.dissmiss();
            }
        } else if (id == R.id.w_sure_view) {
            Date startTime = TimeUtils.string2Date(filterStartTime + " 00:00:00", TimeUtils.DEFAULT_FORMAT);
            filterStartTime = TimeUtils.date2String(startTime, TimeUtils.DEFAULT_FORMATGMC);

            Date endTime = TimeUtils.string2Date(filterEndTime + " 00:00:00", TimeUtils.DEFAULT_FORMAT);
            filterEndTime = TimeUtils.date2String(endTime, TimeUtils.DEFAULT_FORMATGMC);

            if (currentIndex == 3) {
                moneyFragment.upMoneyDataView(filterStartTime, filterEndTime);
                windowMoney.dissmiss();
            } else {
                orderFragment.upOrderHistoryDataView(filterStartTime, filterEndTime);
                windowTrade.dissmiss();
            }
        }
    }

    TradeInfoCommonFragment orderFragment, moneyFragment;

    private void initView(View view) {
        tradeMainPager = view.findViewById(R.id.trade_main_pager);
        tradeMainTabs = view.findViewById(R.id.trade_main_tabs);
        ArrayList<String> titles = new ArrayList<>();
        titles.add("持仓");
        titles.add("挂单");
        titles.add("交易历史");
        titles.add("资金流水");
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(TradeInfoCommonFragment.newInstance("1"));
//        fragments.add(FragmentVideoFlash.newInstance("1"));
        fragments.add(TradeInfoCommonFragment.newInstance("2"));
        orderFragment = TradeInfoCommonFragment.newInstance("3");
        fragments.add(orderFragment);
        moneyFragment = TradeInfoCommonFragment.newInstance("4");
        fragments.add(moneyFragment);
        currentIndex = 0;
        tradeMainPager.setOffscreenPageLimit(4);
        tradeMainPager.setAdapter(new NewsFrPagerAdapter(getChildFragmentManager(), titles, fragments));
        tradeMainTabs.setViewPager(tradeMainPager);
        tradeMainPager.setCurrentItem(0);
        tradeMainTabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currentIndex = position;
                if (position == 2 || position == 3) {
                    btnFilter.setVisibility(View.VISIBLE);
                    btnFilter.setBackground(ContextCompat.getDrawable(mContext, R.drawable.lx_exchange_history_filter));
                } else {
                    btnFilter.setVisibility(View.GONE);
//                    btnFilter.setBackground(ContextCompat.getDrawable(mContext,R.drawable.lx_exchange_home_add_neworder));
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unregistorEventBus();
        unbinder.unbind();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (TradeHistoryFilterActivity.TIME_FILTER_TRADE_HISTORY_REQUEST_CODE == requestCode && TradeHistoryFilterActivity.TIME_FILTER_TRADE_HISTORY_RESULT_CODE == resultCode) {
            String filterStartTime = data.getStringExtra(IntentParam.START_TIME);
            String filterEndTime = data.getStringExtra(IntentParam.END_TIME);
            orderFragment.upOrderHistoryDataView(filterStartTime, filterEndTime);
        }else if (TradeWaterBillsFilterActivity.TIME_FILTER_TRADE_WATER_BILLS_REQUEST_CODE == requestCode && TradeWaterBillsFilterActivity.TIME_FILTER_TRADE_WATER_BILLS_RESULT_CODE == resultCode) {
            String filterStartTime = data.getStringExtra(IntentParam.START_TIME);
            String filterEndTime = data.getStringExtra(IntentParam.END_TIME);
            moneyFragment.upMoneyDataView(filterStartTime, filterEndTime);
        }
    }
}

package com.lingxi.preciousmetal.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.contrarywind.view.WheelView;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;
import com.lingxi.preciousmetal.common.IntentParam;
import com.lingxi.preciousmetal.ui.widget.TopBarView;
import com.lingxi.preciousmetal.util.utilCode.TimeUtils;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhangwei on 2018/4/23.
 */

public class TradeHistoryFilterActivity extends TranslucentStatusBarActivity {
    @BindView(R.id.topbar_view)
    protected TopBarView mTopbarView;
    @BindView(R.id.fragmen_fragment)
    protected FrameLayout mFrameLayout;
    @BindView(R.id.w_start_time_view)
    protected TextView startTimeTextView;
    @BindView(R.id.w_end_time_view)
    protected TextView endTimeTextView;
    @BindView(R.id.line_start_time)
    protected View lineStartTime;
    @BindView(R.id.line_end_time)
    protected View lineEndTime;
    protected TimePickerView pvTime;
    public static final int TIME_FILTER_TRADE_HISTORY_REQUEST_CODE = 1001;
    public static final int TIME_FILTER_TRADE_HISTORY_RESULT_CODE = 1002;
    @BindView(R.id.time_today_radio)
    Button timeTodayRadio;
    @BindView(R.id.time_week_radio)
    Button timeWeekRadio;
    @BindView(R.id.time_month_radio)
    Button timeMonthRadio;
    @BindView(R.id.time_year_radio)
    Button timeYearRadio;
    @BindView(R.id.time_RadioGroup)
    LinearLayout timeRadioGroup;

    public static void actionStart(Fragment context) {
        Intent intent = new Intent(context.getContext(), TradeHistoryFilterActivity.class);
        context.startActivityForResult(intent, TIME_FILTER_TRADE_HISTORY_REQUEST_CODE);
    }

    public static void actionStart(Activity context) {
        Intent intent = new Intent(context, TradeHistoryFilterActivity.class);
        context.startActivityForResult(intent, TIME_FILTER_TRADE_HISTORY_REQUEST_CODE);
    }

    protected int getLayoutId() {
        return R.layout.activity_trade_history_filter;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initTopBar();
        initTimePicker();
    }

    protected void initTopBar() {
        mTopbarView.setMode(TopBarView.MODE_BACK);
        mTopbarView.setTitle("请选择时间范围");
        mTopbarView.setLeftTitle("取消");
        mTopbarView.setActionTitle("完成", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startCalendar == null) {
                    Calendar rangStartDate = Calendar.getInstance();
                    rangStartDate.set(2000, 8, 9);
                    startCalendar = rangStartDate;
                }
                String filterStartTime = startCalendar.get(Calendar.YEAR) + "-" + (startCalendar.get(Calendar.MONTH) + 1) + "-" + startCalendar.get(Calendar.DAY_OF_MONTH);
                String filterEndTime = endCalendar.get(Calendar.YEAR) + "-" + (endCalendar.get(Calendar.MONTH) + 1) + "-" + (endCalendar.get(Calendar.DAY_OF_MONTH));
                Date startTime = TimeUtils.string2Date(filterStartTime + " 00:00:00", TimeUtils.DEFAULT_FORMAT);
                filterStartTime = TimeUtils.date2String(startTime, TimeUtils.DEFAULT_FORMATGMC);

                Date endTime = TimeUtils.string2Date(filterEndTime + " 00:00:00", TimeUtils.DEFAULT_FORMAT);
                filterEndTime = TimeUtils.date2String(endTime, TimeUtils.DEFAULT_FORMATGMC);
                if (startTime.before(endTime)) {
                    onSubmit(filterStartTime, filterEndTime);
                } else {
                    onSubmit(filterEndTime, filterStartTime);
                }
            }
        });
    }

    protected void onSubmit(String startTime, String endTime) {
        Intent intent = new Intent();
        intent.putExtra(IntentParam.START_TIME, startTime);
        intent.putExtra(IntentParam.END_TIME, endTime);
        TradeHistoryFilterActivity.this.setResult(TIME_FILTER_TRADE_HISTORY_RESULT_CODE, intent);
        TradeHistoryFilterActivity.this.finish();
    }

    protected Calendar selectCalendar, startCalendar, endCalendar;
    private String filterStartTime, filterEndTime;

    private void publicTimeView() {

//        filterStartTime = startCalendar.get(Calendar.YEAR) + "-" + (startCalendar.get(Calendar.MONTH)) + "-" + startCalendar.get(Calendar.DAY_OF_MONTH);
        filterEndTime = endCalendar.get(Calendar.YEAR) + "-" + (endCalendar.get(Calendar.MONTH) + 1) + "-" + (endCalendar.get(Calendar.DAY_OF_MONTH));
        //点击事件
//        startTimeTextView.setText(filterStartTime);
        startTimeTextView.setText("起始时间");
        endTimeTextView.setText(filterEndTime);
        endTimeTextView.setSelected(true);
        startTimeTextView.setSelected(false);
        lineStartTime.setSelected(false);
        lineEndTime.setSelected(true);
    }

    private void initTimePicker() {


        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar rangEndDate = Calendar.getInstance();
        endCalendar = rangEndDate;
        selectCalendar = endCalendar;
//        startCalendar = rangEndDate;
        publicTimeView();

        Calendar rangStartDate = Calendar.getInstance();
        rangStartDate.set(2000, 8, 9);

//        Calendar endDate = Calendar.getInstance();
//        endDate.set(2019, 11, 28);
        //时间选择器
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime(date);
//                String time = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH)) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
//                if(startTimeTextView.isSelected()){
//                    startTimeTextView.setText(time);
//                    filterStartTime = time;
//                }else if(endTimeTextView.isSelected()){
//                    endTimeTextView.setText(time);
//                    filterEndTime = time;
//                }
            }
        })
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        setHookItemVisible((WheelView) v.findViewById(R.id.year));
                        setHookItemVisible((WheelView) v.findViewById(R.id.month));
                        setHookItemVisible((WheelView) v.findViewById(R.id.day));
                    }

                    private void setHookItemVisible(WheelView year) {
                        try {
                            Class<?> clazz = year.getClass();// 获取到对象对应的class对象
                            Field typeField = clazz.getDeclaredField("itemsVisible");
                            typeField.setAccessible(true);
                            typeField.set(year, 7);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        String time = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
                        if (startTimeTextView.isSelected()) {
                            startTimeTextView.setText(time);
                            filterStartTime = time;
                            startCalendar = calendar;
                        } else if (endTimeTextView.isSelected()) {
                            endTimeTextView.setText(time);
                            filterEndTime = time;
                            endCalendar = calendar;
                        }
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})
//                .setLabel("", "", "", "", "", "") //设置空字符串以隐藏单位提示   hide label
                .setDividerColor(Color.DKGRAY)
                .setContentTextSize(20)
                .setDate(rangEndDate)
                .setRangDate(rangStartDate, rangEndDate)
                .setDecorView(mFrameLayout)//非dialog模式下,设置ViewGroup, pickerView将会添加到这个ViewGroup中
                .setBackgroundId(0x00000000)
                .setOutSideCancelable(false)
                .build();

        pvTime.setKeyBackCancelable(false);//系统返回键监听屏蔽掉
        pvTime.show(false);
    }

    @OnClick({R.id.w_start_time_view, R.id.line_start_time, R.id.w_end_time_view, R.id.line_end_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.w_start_time_view:
            case R.id.line_start_time:
                if (startCalendar == null) {
                    startCalendar = endCalendar;
                    String filterStartTime = startCalendar.get(Calendar.YEAR) + "-" + (startCalendar.get(Calendar.MONTH) + 1) + "-" + startCalendar.get(Calendar.DAY_OF_MONTH);
                    startTimeTextView.setText(filterStartTime);
                } else {
                    selectCalendar = startCalendar;
                }
                startTimeTextView.setSelected(true);
                lineStartTime.setSelected(true);
                endTimeTextView.setSelected(false);
                lineEndTime.setSelected(false);
                pvTime.setDate(selectCalendar);
                break;
            case R.id.w_end_time_view:
            case R.id.line_end_time:
                selectCalendar = endCalendar;
                endTimeTextView.setSelected(true);
                lineEndTime.setSelected(true);
                startTimeTextView.setSelected(false);
                lineStartTime.setSelected(false);
                pvTime.setDate(selectCalendar);
                break;
        }
    }

    private void initStartTimeView() {
        startCalendar = null;
        startTimeTextView.setSelected(false);
        startTimeTextView.setText("起始时间");
    }

    @OnClick({R.id.time_today_radio, R.id.time_week_radio, R.id.time_month_radio, R.id.time_year_radio})
    public void onViewClicked1(View view) {
        Calendar calendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();
        switch (view.getId()) {
            case R.id.time_today_radio:
                if (timeTodayRadio.isSelected()) {
                    timeTodayRadio.setSelected(false);
                    initStartTimeView();
                } else {
                    timeTodayRadio.setSelected(true);
                    timeWeekRadio.setSelected(false);
                    timeMonthRadio.setSelected(false);
                    timeYearRadio.setSelected(false);
                    filterEndTime = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + (calendar.get(Calendar.DAY_OF_MONTH));
                    filterStartTime = filterEndTime;
                    startCalendar = calendar;
                    startTimeTextView.setText(filterStartTime);
                    endTimeTextView.setText(filterEndTime);
                    if (startTimeTextView.isSelected()) {
                        pvTime.setDate(startCalendar);
                    }
                }
                break;
            case R.id.time_week_radio:
                if (timeWeekRadio.isSelected()) {
                    timeWeekRadio.setSelected(false);
                    initStartTimeView();
                } else {
                    timeTodayRadio.setSelected(false);
                    timeWeekRadio.setSelected(true);
                    timeMonthRadio.setSelected(false);
                    timeYearRadio.setSelected(false);filterEndTime = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + (calendar.get(Calendar.DAY_OF_MONTH));
                    calendar.add(Calendar.DAY_OF_WEEK, -7);
                    filterStartTime = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + ((calendar.get(Calendar.DAY_OF_MONTH)));
                    startCalendar = calendar;
                    startTimeTextView.setText(filterStartTime);
                    endTimeTextView.setText(filterEndTime);
                    if (startTimeTextView.isSelected()) {
                        pvTime.setDate(startCalendar);
                    }
                }

                break;
            case R.id.time_month_radio:
                if (timeMonthRadio.isSelected()) {
                    timeMonthRadio.setSelected(false);
                    initStartTimeView();
                } else {
                    timeTodayRadio.setSelected(false);
                    timeWeekRadio.setSelected(false);
                    timeMonthRadio.setSelected(true);
                    timeYearRadio.setSelected(false); filterStartTime = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH)) + "-" + (calendar.get(Calendar.DAY_OF_MONTH));
                    filterEndTime = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + (calendar.get(Calendar.DAY_OF_MONTH));
                    calendar.add(Calendar.MONTH, -1);
                    startCalendar = calendar;
                    startTimeTextView.setText(filterStartTime);
                    endTimeTextView.setText(filterEndTime);
                    if (startTimeTextView.isSelected()) {
                        pvTime.setDate(startCalendar);
                    }
                }

                break;
            case R.id.time_year_radio:
                if (timeYearRadio.isSelected()) {
                    timeYearRadio.setSelected(false);
                    initStartTimeView();
                } else {
                    timeTodayRadio.setSelected(false);
                    timeWeekRadio.setSelected(false);
                    timeMonthRadio.setSelected(false);
                    timeYearRadio.setSelected(true);
                    filterEndTime = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + (calendar.get(Calendar.DAY_OF_MONTH));
                    calendar.add(Calendar.YEAR, -1);
                    filterStartTime = (calendar.get(Calendar.YEAR)) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + (calendar.get(Calendar.DAY_OF_MONTH));
                    startCalendar = calendar;
                    startTimeTextView.setText(filterStartTime);
                    endTimeTextView.setText(filterEndTime);
                    if (startTimeTextView.isSelected()) {
                        pvTime.setDate(startCalendar);
                    }
                }
                break;
        }
    }
}

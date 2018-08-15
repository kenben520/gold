package com.lingxi.preciousmetal.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;

import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.common.IntentParam;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhangwei on 2018/4/23.
 */

public class TradeWaterBillsFilterActivity extends TradeHistoryFilterActivity {
    public static final int TIME_FILTER_TRADE_WATER_BILLS_REQUEST_CODE = 1003;
    public static final int TIME_FILTER_TRADE_WATER_BILLS_RESULT_CODE = 1004;
    @BindView(R.id.money_type1_btn)
    Button moneyType1Btn;
    @BindView(R.id.money_type2_btn)
    Button moneyType2Btn;
    @BindView(R.id.money_type3_btn)
    Button moneyType3Btn;
    @BindView(R.id.money_type5_btn)
    Button moneyType5Btn;

    public static void actionStart(Fragment context) {
        Intent intent = new Intent(context.getContext(), TradeWaterBillsFilterActivity.class);
        context.startActivityForResult(intent, TIME_FILTER_TRADE_WATER_BILLS_REQUEST_CODE);
    }

    public static void actionStart(Activity context) {
        Intent intent = new Intent(context, TradeWaterBillsFilterActivity.class);
        context.startActivityForResult(intent, TIME_FILTER_TRADE_WATER_BILLS_REQUEST_CODE);
    }

    protected int getLayoutId() {
        return R.layout.trade_property_water_bills_filter;
    }

    public void initTopBar() {
        super.initTopBar();
        mTopbarView.setTitle("筛选结果");
    }

    @Override
    protected void onSubmit(String startTime, String endTime) {
        Intent intent = new Intent();
        intent.putExtra(IntentParam.START_TIME, startTime);
        intent.putExtra(IntentParam.END_TIME, endTime);
        TradeWaterBillsFilterActivity.this.setResult(TIME_FILTER_TRADE_WATER_BILLS_RESULT_CODE, intent);
        TradeWaterBillsFilterActivity.this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.money_type1_btn, R.id.money_type2_btn, R.id.money_type3_btn, R.id.money_type5_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.money_type1_btn:
                if (moneyType1Btn.isSelected()) {
                    moneyType1Btn.setSelected(false);
                } else {
                    moneyType1Btn.setSelected(true);
                    moneyType2Btn.setSelected(false);
                    moneyType3Btn.setSelected(false);
                    moneyType5Btn.setSelected(false);
                }
                break;
            case R.id.money_type2_btn:
                if (moneyType2Btn.isSelected()) {
                    moneyType2Btn.setSelected(false);
                } else {
                    moneyType1Btn.setSelected(false);
                    moneyType2Btn.setSelected(true);
                    moneyType3Btn.setSelected(false);
                    moneyType5Btn.setSelected(false);
                }
                break;
            case R.id.money_type3_btn:
                if (moneyType3Btn.isSelected()) {
                    moneyType3Btn.setSelected(false);
                } else {
                    moneyType1Btn.setSelected(false);
                    moneyType2Btn.setSelected(false);
                    moneyType3Btn.setSelected(true);
                    moneyType5Btn.setSelected(false);
                }
                break;
            case R.id.money_type5_btn:
                if (moneyType5Btn.isSelected()) {
                    moneyType5Btn.setSelected(false);
                } else {
                    moneyType1Btn.setSelected(false);
                    moneyType2Btn.setSelected(false);
                    moneyType3Btn.setSelected(false);
                    moneyType5Btn.setSelected(true);
                }
                break;
        }
    }
}

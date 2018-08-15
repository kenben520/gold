package com.lingxi.preciousmetal.chart.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.github.tifezh.kchartlib.chart.MinuteChartView;
import com.github.tifezh.kchartlib.utils.DateUtil;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.BaseActivity;
import com.lingxi.preciousmetal.chart.data.DataRequest;
import com.lingxi.preciousmetal.chart.entity.MinuteLineEntity;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tifezh on 2017/7/20.
 */

public class MinuteChartActivity extends BaseActivity {

    @BindView(R.id.minuteChartView)
    MinuteChartView mMinuteChartView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minute_chart);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {

    }

    private void initData() {
        try {
            //整体开始时间
            Date startTime = DateUtil.shortTimeFormat.parse("09:30");
            //整体的结束时间
            Date endTime = DateUtil.shortTimeFormat.parse("13:00");
            //休息开始时间
//            Date firstEndTime = DateUtil.shortTimeFormat.parse("13:00");
//            //休息结束时间
//            Date secondStartTime = DateUtil.shortTimeFormat.parse("15:00");
            //获取随机生成的数据
            List<MinuteLineEntity> minuteData =
                    DataRequest.getMinuteData(startTime,
                            endTime,
                            null,
                            null);
            mMinuteChartView.initData(minuteData,
                    startTime,
                    endTime,
                    null,
                    null,
                    (float) (minuteData.get(0).price - 0.5 + Math.random()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

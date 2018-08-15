package com.github.tifezh.kchartlib.chart.entity;

/**
 * 均幅指标（ATR）是取一定时间周期内的股价波动幅度的移动平均值，主要用于研判买卖时机。
 * Created by jilucheng on 2018/5/3.
 */

public interface IATR {
    float getTR();
    float getATR();
}

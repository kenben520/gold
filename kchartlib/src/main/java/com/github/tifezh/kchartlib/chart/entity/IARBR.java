package com.github.tifezh.kchartlib.chart.entity;

/**
 * ARBR
 情绪指标（ARBR）也称为人气意愿指标，其英文缩写亦可表示为BRAR。由人气指标(AR)和意愿指标(BR)两个指标构成。AR指标和BR指标都是以分析历史股价为手段的技术指标。

 AR计算公式
 AR=((H - O)26天之和/(O - L)26天之和) * 100
 H：当天之最高价
 L：当天之最低价
 O：当天之开盘价


 BR计算公式
 BR=((H - PC)26天之和/(PC - L)26天之和) * 100
 H：当天之最高价；
 L：当天之最低价；
 PC：昨天之收盘价；
 * Created by jilucheng on 2018/5/3.
 */

public interface IARBR {
    float getAR();
    float getBR();
}

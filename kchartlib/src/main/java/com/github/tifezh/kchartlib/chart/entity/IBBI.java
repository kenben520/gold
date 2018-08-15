package com.github.tifezh.kchartlib.chart.entity;

/**
 * BBI又叫多空指数，是通过将几条不同天数移动平均线用加权平均方法计算出的
 * 一条移动平均线的综合指标BBI=(3日均价+6日均价+12日均价+24日均价)÷4
 * Created by jilucheng on 2018/4/19.
 */

public interface IBBI {
    //
    float getBBI();
}

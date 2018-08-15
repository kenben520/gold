package com.github.tifezh.kchartlib.chart.entity;

/**
 * 麦克支撑压力（MIKE）指标是一种股价波动幅度大小而变动的压力支撑指标，设有初级、中级、强力三种不同级别的支撑和压力，用图标方式直接显示压力、支撑的位置。

 1.初始价（TYP）=（当日最高价+当日最低价+当日收盘价）/3
 2.HH=N日内最高价的最高值
 LL=N日内最低价的最低值
 3.压力线
 初级压力线（WEKR）=TYP+(TYP-LL)
 中级压力线（MIDR）=TYP+(HH-LL)
 强力压力线（STOR）=2*HH-LL
 4.支撑线
 初级支撑线（WEKS）=TYP-(HH-TYP)
 中级支撑线（MIDS）=TYP-(HH-LL)
 强力支撑线（STOS）=2*LL-HH
 * Created by jilucheng on 2018/5/2.
 */

public interface IMIKE {
    /**
     * 初级压力线
     * @return
     */
    float getMikeWR();

    /**
     * 中级压力线
     * @return
     */
    float getMikeMR();

    /**
     * 强力压力线
     * @return
     */
    float getMikeSR();

    /**
     * 初级支撑线
     * @return
     */
    float getMikeWS();

    /**
     * 中级支撑线
     * @return
     */
    float getMikeMS();

    /**
     * 强力支撑线
     * @return
     */
    float getMikeSS();
}
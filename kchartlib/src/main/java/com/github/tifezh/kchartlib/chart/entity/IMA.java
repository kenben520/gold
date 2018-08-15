package com.github.tifezh.kchartlib.chart.entity;

/**
 * 移动平均线数据接口
 * Created by jilucheng on 2018/4/18.
 */

public interface IMA {
  /**
   * 五(月，日，时，分，5分等)均价
   */
  float getMA1Price();

  float getMA2Price();
  /**
   * 五(月，日，时，分，5分等)均价
   */
  float getMA3Price();

  /**
   * 五(月，日，时，分，5分等)均价
   */
  float getMA4Price();
}

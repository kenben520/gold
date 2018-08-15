package com.github.tifezh.kchartlib.chart.base;

/**
 * 主绘制视图
 * Created by jilucheng on 2018/4/18.
 */

public interface IMainChartDraw<T> extends IChartDraw<T> {
  /**
   * 添加一个子绘制视图
   * @param chartDraw
   */
  void setChildDraw(IChartDraw chartDraw);


}

package com.github.tifezh.kchartlib.chart.entity;

import java.util.Date;

/**
 * Created by jilucheng on 2018/4/18.
 */

public class KMinuteLineEntity implements IMinuteLine {
  /**
   * time : 09:30
   * price : 3.53
   * avg : 3.5206
   * vol : 9251
   */

  public Date time;
  public float price;
  public float avg;
  public float volume;
  public long kTime;

  public Date getTime() {
    return time;
  }

  public void setTime(Date time) {
    this.time = time;
  }

  @Override
  public float getAvgPrice() {
    return avg;
  }

  @Override
  public float getPrice() {
    return price;
  }

  @Override
  public Date getDate() {
    return time;
  }

  @Override
  public float getVolume() {
    return volume;
  }
}

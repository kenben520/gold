package com.lingxi.preciousmetal.chart.entity;

import com.github.tifezh.kchartlib.chart.entity.IKLine;

/**
 * Created by jilucheng on 2018/4/18.
 */

public class KLineEntity implements IKLine {
//  public String getDatetime() {
//    return DateStr;
//  }

  @Override
  public float getOpenPrice() {
    return Open;
  }

  @Override
  public float getHighPrice() {
    return High;
  }

  @Override
  public float getLowPrice() {
    return Low;
  }

  @Override
  public float getClosePrice() {
    return Close;
  }

  @Override
  public float getMA1Price() {
    return MA1Price;
  }

  @Override
  public float getMA2Price() {
    return MA2Price;
  }

  @Override
  public float getMA3Price() {
    return MA3Price;
  }

  @Override
  public float getMA4Price() {
    return MA4Price;
  }

  @Override
  public float getDea() {
    return dea;
  }

  @Override
  public float getDif() {
    return dif;
  }

  @Override
  public float getMacd() {
    return macd;
  }

  @Override
  public float getK() {
    return k;
  }

  @Override
  public float getD() {
    return d;
  }

  @Override
  public float getJ() {
    return j;
  }

  @Override
  public float getRsi1() {
    return rsi1;
  }

  @Override
  public float getRsi2() {
    return rsi2;
  }

  @Override
  public float getRsi3() {
    return rsi3;
  }

  @Override
  public float getUp() {
    return up;
  }

  @Override
  public float getMb() {
    return mb;
  }

  @Override
  public float getDn() {
    return dn;
  }

  @Override
  public float getVolume() {
    return Volume;
  }

  @Override
  public float getMA5Volume() {
    return MA5Volume;
  }

  @Override
  public float getMA10Volume() {
    return MA10Volume;
  }

  @Override
  public float getBBI() {
    return bbi;
  }

  @Override
  public float[] getPBX() {
    return pbx;
  }

  @Override
  public float getMikeWR() {
    return mike_wr;
  }

  @Override
  public float getMikeMR() {
    return mike_mr;
  }

  @Override
  public float getMikeSR() {
    return mike_sr;
  }

  @Override
  public float getMikeWS() {
    return mike_ws;
  }

  @Override
  public float getMikeMS() {
    return mike_ms;
  }

  @Override
  public float getMikeSS() {
    return mike_ss;
  }

  @Override
  public float getAR() {
    return ar;
  }

  @Override
  public float getBR() {
    return br;
  }

  @Override
  public float getTR() {
    return tr;
  }

  @Override
  public float getATR() {
    return atr;
  }

  @Override
  public float getBias1() {
    return bias1;
  }

  @Override
  public float getBias2() {
    return bias2;
  }

  @Override
  public float getBias3() {
    return bias3;
  }

  @Override
  public float getWr() {
    return wr;
  }

  @Override
  public float getLwr1() {
    return lwr1;
  }

  @Override
  public float getLwr2() {
    return lwr2;
  }

  @Override
  public float getCci() {
    return cci;
  }

  @Override
  public float getBuy() {
    return buy;
  }

  @Override
  public float getSell() {
    return sell;
  }

  @Override
  public float getEne1() {
    return ene1;
  }

  @Override
  public float getEne2() {
    return ene2;
  }

  public long kDate;
//  public String DateStr;
  public float Open;
  public float High;
  public float Low;
  public float Close;
  public float Volume;

  public float MA1Price;
  public float MA2Price;
  public float MA3Price;
  public float MA4Price;

  public float dea;
  public float dif;
  public float macd;

  public float k;
  public float d;
  public float j;

  public float rsi1;
  public float rsi2;
  public float rsi3;

  public float up;
  public float mb;
  public float dn;

  public float MA5Volume;
  public float MA10Volume;

  public float bbi;

  public float[] pbx = new float[4];

  public float mike_wr;
  public float mike_mr;
  public float mike_sr;
  public float mike_ws;
  public float mike_ms;
  public float mike_ss;

  public float ar;
  public float br;

  public float tr;
  public float atr;

  public float bias1;
  public float bias2;
  public float bias3;

  public float wr;

  public float lwr1;
  public float lwr2;

  public float cci;

  public float sell;
  public float buy;
  public float ene1;
  public float ene2;
}

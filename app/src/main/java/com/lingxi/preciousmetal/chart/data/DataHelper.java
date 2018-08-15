package com.lingxi.preciousmetal.chart.data;

import com.lingxi.preciousmetal.base.MyApplication;
import com.lingxi.preciousmetal.chart.entity.KLineEntity;
import com.lingxi.preciousmetal.util.utilCode.ObjectUtils;

import java.util.List;

/**
 * Created by jilucheng on 2018/4/18.
 */

public class DataHelper {
  /**
   * 计算RSI
   *
   * @param datas
   */
  static void calculateRSI(List<KLineEntity> datas) {
    int n1=MyApplication.getSignalParamValues().rsi.value1;
    int n2=MyApplication.getSignalParamValues().rsi.value2;
    int n3=MyApplication.getSignalParamValues().rsi.value3;
    float rsi1 = 0;
    float rsi2 = 0;
    float rsi3 = 0;
    float rsi1ABSEma = 0;
    float rsi2ABSEma = 0;
    float rsi3ABSEma = 0;
    float rsi1MaxEma = 0;
    float rsi2MaxEma = 0;
    float rsi3MaxEma = 0;
    for (int i = 0; i < datas.size(); i++) {
      KLineEntity point = datas.get(i);
      final float closePrice = point.getClosePrice();
      if (i == 0) {
        rsi1 = 0;
        rsi2 = 0;
        rsi3 = 0;
        rsi1ABSEma = 0;
        rsi2ABSEma = 0;
        rsi3ABSEma = 0;
        rsi1MaxEma = 0;
        rsi2MaxEma = 0;
        rsi3MaxEma = 0;
      } else {
        float Rmax = Math.max(0, closePrice - datas.get(i - 1).getClosePrice());
        float RAbs = Math.abs(closePrice - datas.get(i - 1).getClosePrice());
        rsi1MaxEma = (Rmax + (n1 - 1) * rsi1MaxEma) / n1;
        rsi1ABSEma = (RAbs + (n1 - 1) * rsi1ABSEma) / n1;

        rsi2MaxEma = (Rmax + (n2 - 1) * rsi2MaxEma) / n2;
        rsi2ABSEma = (RAbs + (n2 - 1) * rsi2ABSEma) / n2;

        rsi3MaxEma = (Rmax + (n3 - 1) * rsi3MaxEma) / n3;
        rsi3ABSEma = (RAbs + (n3 - 1) * rsi3ABSEma) / n3;

        rsi1 = (rsi1MaxEma / rsi1ABSEma) * 100;
        rsi2 = (rsi2MaxEma / rsi2ABSEma) * 100;
        rsi3 = (rsi3MaxEma / rsi3ABSEma) * 100;
      }
      point.rsi1 = rsi1;
      point.rsi2 = rsi2;
      point.rsi3 = rsi3;
    }
  }

  /**
   * 计算kdj
   *
   * @param datas
   */
  static void calculateKDJ(List<KLineEntity> datas) {
    int n =  MyApplication.getSignalParamValues().kdj.value1;
    float m1 = MyApplication.getSignalParamValues().kdj.value2;
    float m2 = MyApplication.getSignalParamValues().kdj.value3;
    float k = 0;
    float d = 0;

    for (int i = 0; i < datas.size(); i++) {
      KLineEntity point = datas.get(i);
      final float closePrice = point.getClosePrice();
      int startIndex = i - n - 1;
      if (startIndex < 0) {
        startIndex = 0;
      }
      float max9 = Float.MIN_VALUE;
      float min9 = Float.MAX_VALUE;
      for (int index = startIndex; index <= i; index++) {
        max9 = Math.max(max9, datas.get(index).getHighPrice());
        min9 = Math.min(min9, datas.get(index).getLowPrice());
      }
      float rsv = 100f * (closePrice - min9) / (max9 - min9);
      if (i == 0) {
        k = rsv;
        d = rsv;
      } else {
        k = (rsv +(m1-1) * k) / m1;
        d = (k + (m2-1) * d) / m2;
      }
      point.k = k;
      point.d = d;
      point.j = 3f * k - 2 * d;
    }

  }

  /**
   * 计算macd
   *
   * @param datas
   */
  static void calculateMACD(List<KLineEntity> datas) {
    int shortParams = MyApplication.getSignalParamValues().macd.value2;
    int longParams = MyApplication.getSignalParamValues().macd.value1;
    int m =MyApplication.getSignalParamValues().macd.value3;
    float[] funcEMAShort=getEMAs(datas,shortParams);
    float[] funcEMALong=getEMAs(datas,longParams);
    float[] funcDiff=getDifs(funcEMAShort,funcEMALong);
    float[] funcDea=getEMAs(funcDiff,m);
    for (int i = 0; i < datas.size(); i++) {
      KLineEntity point = datas.get(i);
      point.dif = funcDiff[i];
      point.dea = funcDea[i];
      point.macd = 2 * (point.dif - point.dea);
    }
  }

  /**
   * 计算 BOLL 需要在计算ma之后进行
   *
   * @param datas
   */
  static void calculateBOLL(List<KLineEntity> datas) {
    int n = MyApplication.getSignalParamValues().boll.value1;
    int p = MyApplication.getSignalParamValues().boll.value2;
    float[] maN=getEMAs(datas,n);
    for (int i = 0; i < datas.size(); i++) {
      KLineEntity point = datas.get(i);
      final float closePrice = point.getClosePrice();
      if (i == 0) {
        point.mb = closePrice;
        point.up = Float.NaN;
        point.dn = Float.NaN;
      } else {
        if (i < n) {
          n = i + 1;
        }
        float md = 0;
        for (int j = i - n + 1; j <= i; j++) {
          float c = datas.get(j).getClosePrice();
          float m = maN[i];
          float value = c - m;
          md += value * value;
        }
        md = md / (n - 1);
        md = (float) Math.sqrt(md);
        point.mb = maN[i];
        point.up = point.mb + p * md;
        point.dn = point.mb - p * md;
      }
    }
  }

  /**
   * 计算ma
   *
   * @param datas
   */
  static void calculateMA(List<KLineEntity> datas) {
    int l1=MyApplication.getSignalParamValues().ma.value1;
    int l2=MyApplication.getSignalParamValues().ma.value2;
    int l3=MyApplication.getSignalParamValues().ma.value3;
    int l4=MyApplication.getSignalParamValues().ma.value4;
    float maSum1 = 0;
    float maSum2 = 0;
    float maSum3 = 0;
    float maSum4 = 0;

    for (int i = 0; i < datas.size(); i++) {
      KLineEntity point = datas.get(i);
      final float closePrice = point.getClosePrice();
      maSum1 += closePrice;
      maSum2 += closePrice;
      maSum3 += closePrice;
      maSum4 += closePrice;

      if (i >= l1) {
        maSum1 -= datas.get(i - l1).getClosePrice();
        point.MA1Price = maSum1 / l1;
      } else {
        point.MA1Price = maSum1 / (i + 1f);
      }

      if (i >= l2) {
        maSum2 -= datas.get(i - l2).getClosePrice();
        point.MA2Price = maSum2 / l2;
      } else {
        point.MA2Price = maSum2 / (i + 1f);
      }

      if (i >= l3) {
        maSum3 -= datas.get(i - l3).getClosePrice();
        point.MA3Price = maSum3 / l3;
      } else {
        point.MA3Price = maSum3 / (i + 1f);
      }

      if (i >= l4) {
        maSum4 -= datas.get(i - l4).getClosePrice();
        point.MA4Price = maSum4 / l4;
      } else {
        point.MA4Price = maSum4 / (i + 1f);
      }
    }
  }

  /**
   * 计算MA BOLL RSI KDJ MACD
   *
   * @param datas
   */
  public static void calculate(List<KLineEntity> datas) {
    calculateMA(datas);
    calculateMACD(datas);
    calculateBOLL(datas);
    calculateRSI(datas);
    calculateKDJ(datas);
    calculateVolumeMA(datas);
    calculateBBI(datas);
    calculateMIKE(datas);
    calculatePBX(datas);
    calculateARBR(datas);
    calculateART(datas);
    calculateBIAS(datas);
    calculateWR(datas);
    calculateLWR(datas);
    calculateCCI(datas);
    calculateDKBY(datas);
  }

  private static void calculateBBI(List<KLineEntity> datas) {
    int l1=MyApplication.getSignalParamValues().bbi.value2;
    int l2=MyApplication.getSignalParamValues().bbi.value2;
    int l3=MyApplication.getSignalParamValues().bbi.value3;
    int l4=MyApplication.getSignalParamValues().bbi.value4;
    float maSum1 = 0;
    float maSum2 = 0;
    float maSum3 = 0;
    float maSum4 = 0;

    float ma1, ma2, ma3, ma4;
    for (int i = 0; i < datas.size(); i++) {
      KLineEntity entry = datas.get(i);
      final float closePrice = entry.getClosePrice();
      maSum1 += closePrice;
      maSum2 += closePrice;
      maSum3 += closePrice;
      maSum4 += closePrice;

      if (i >= l1) {
        maSum1 -= datas.get(i - l1).getClosePrice();
        ma1 = maSum1 / l1;
      } else {
        ma1 = maSum1 / (i + 1f);
      }

      if (i >= l2) {
        maSum2 -= datas.get(i - l2).getClosePrice();
        ma2 = maSum2 / l2;
      } else {
        ma2 = maSum2 / (i + 1f);
      }

      if (i >= l3) {
        maSum3 -= datas.get(i - l3).getClosePrice();
        ma3 = maSum3 / l3;
      } else {
        ma3 = maSum3 / (i + 1f);
      }

      if (i >= l4) {
        maSum4 -= datas.get(i - l4).getClosePrice();
        ma4 = maSum4 / l4;
      } else {
        ma4 = maSum4 / (i + 1f);
      }

      entry.bbi = (ma1+ma2+ma3+ma4)* .25f;
    }
  }

  /**
   * 乘离率 取6，12，24日乘离率
   * @param entries
   */
  private static void calculateBIAS(List<KLineEntity> entries) {
    int l1 = MyApplication.getSignalParamValues().bias.value1;
    int l2 = MyApplication.getSignalParamValues().bias.value2;
    int l3 =MyApplication.getSignalParamValues().bias.value3;

    float[] l1s=getMAs(entries,l1);
    float[] l2s=getMAs(entries,l2);
    float[] l3s=getMAs(entries,l3);
    for (int i = 0; i < entries.size(); i++) {
      KLineEntity entry = entries.get(i);
      entry.bias1=(entry.getClosePrice()-l1s[i])/l1s[i]*100f;
      entry.bias2=(entry.getClosePrice()-l2s[i])/l2s[i]*100f;
      entry.bias3=(entry.getClosePrice()-l3s[i])/l3s[i]*100f;
    }
  }

  /**
   * wr威廉指标 n取14日
   * @param datas
   */
  private static void calculateWR(List<KLineEntity> datas) {
    int n=MyApplication.getSignalParamValues().wr.value1;
    for (int i = 0; i < datas.size(); i++) {
      KLineEntity point = datas.get(i);
      final float closePrice = point.getClosePrice();
      int startIndex = i - (n-1);// i - (14-1) n取14日
      if (startIndex < 0) {
        startIndex = 0;
      }
      float max14 = Float.MIN_VALUE;
      float min14 = Float.MAX_VALUE;
      for (int index = startIndex; index <= i; index++) {
        max14 = Math.max(max14, datas.get(index).getHighPrice());
        min14 = Math.min(min14, datas.get(index).getLowPrice());
      }
      point.wr=(100f*(max14-closePrice))/(max14-min14);
    }
  }

  //-- LLV(X, N): 求X在N个周期内的最小值
  static void LLV(List<KLineEntity> datas,int i,int n)
  {

  }
  /**
   * 取值9,3,3
   * @param datas
   */
  static void calculateLWR(List<KLineEntity> datas) {
    int n=MyApplication.getSignalParamValues().lwr.value1;
    int m1=MyApplication.getSignalParamValues().lwr.value2;
    int m2=MyApplication.getSignalParamValues().lwr.value3;

    if(ObjectUtils.isEmpty(datas)) return;
    float [] rsvs=new float[datas.size()];
    for (int i = 0; i < datas.size(); i++) {
      KLineEntity point = datas.get(i);
      final float closePrice = point.getClosePrice();
      int startIndex = i - (n-1);
      if (startIndex < 0) {
        startIndex = 0;
      }
      float max9 = Float.MIN_VALUE;
      float min9 = Float.MAX_VALUE;
      for (int index = startIndex; index <= i; index++) {
        max9 = Math.max(max9, datas.get(index).getHighPrice());
        min9 = Math.min(min9, datas.get(index).getLowPrice());
      }
      float rsv = 100f * (max9 - closePrice) / (max9 - min9);
      rsvs[i]=rsv;
    }
    float [] lwr1s=new float[datas.size()];
    for (int i = 0; i < datas.size(); i++) {
      KLineEntity point = datas.get(i);
      float rsv=rsvs[i];
      if(i==0)
      {
        lwr1s[i]=rsv;
      }else{
        int count=m1;//count==M1  coe=1   LWR1:SMA(RSV,M1,1);
        float price=rsvs[i];
        int coe=1;
        lwr1s[i]=(coe * price + (count - coe) * lwr1s[i - 1]) / count;
      }
      point.lwr1=lwr1s[i];
    }

    float [] lwr2s=new float[datas.size()];
    for (int i = 0; i < datas.size(); i++) {
      KLineEntity point = datas.get(i);
      float lwr1=lwr1s[i];
      if(i==0)
      {
        lwr2s[i]=lwr1;
      }else{
        int count=m2;//count==M1  coe=1   LWR1:SMA(RSV,M1,1);
        float price=lwr1s[i];
        int coe=1;
        lwr2s[i]=(coe * price + (count - coe) * lwr2s[i - 1]) / count;
      }
      point.lwr2=lwr2s[i];
    }
  }

  /**
   * 取值14
   * @param datas
   */
  static void calculateCCI(List<KLineEntity> datas) {
    int n = MyApplication.getSignalParamValues().cci.value1;
    if(ObjectUtils.isEmpty(datas)) return;
    float [] types=new float[datas.size()];
    for (int i = 0; i < datas.size(); i++) {
      KLineEntity point = datas.get(i);
      types[i]=(point.getHighPrice()+point.getLowPrice()+point.getClosePrice())/3;
    }
    float [] maTypes=getMAs(types,14);

    float sumAvedevTyp = 0;
    float avedevTyp = 0;
    for (int i = 0; i < datas.size(); i++) {
      KLineEntity point = datas.get(i);
      float typ = types[i];
      float ma = maTypes[i];
      sumAvedevTyp = sumAvedevTyp + Math.abs(typ - ma);
      if(i>=n) {
        sumAvedevTyp -=Math.abs(types[i-n]-maTypes[i-n]);
        avedevTyp = sumAvedevTyp / n;
      }
        else{
        avedevTyp = sumAvedevTyp /(i + 1f);
      }
      point.cci = (typ - ma) / avedevTyp / 0.015f;
    }
  }

  /**
   * N取值21
   * @param datas
   */
  static void calculateDKBY(List<KLineEntity> datas) {
    if(ObjectUtils.isEmpty(datas)) return;
    float [] var1s=new float[datas.size()];
    float [] var2s=new float[datas.size()];
    for (int i = 0; i < datas.size(); i++) {
      KLineEntity point = datas.get(i);
      final float close = point.getClosePrice();
      int startIndex = i - 20;//N-1
      if (startIndex < 0) {
        startIndex = 0;
      }
      float max21 = Float.MIN_VALUE;
      float min21 = Float.MAX_VALUE;
      for (int index = startIndex; index <= i; index++) {
        max21 = Math.max(max21, datas.get(index).getHighPrice());
        min21 = Math.min(min21, datas.get(index).getLowPrice());
      }
      var1s[i] = (max21-close) / (max21-min21)*100-10;
      var2s[i] = (close-min21) / (max21-min21)*100;
    }

    float [] var3s=new float[datas.size()];
    for (int i = 0; i < datas.size(); i++) {
      float var2=var2s[i];
      if(i==0)
      {
        var3s[i]=var2;
      }else{
        int count=13;//count==M1  coe=8   LWR1:SMA(RSV,M1,1);
        float price=var2s[i];
        int coe=8;
        var3s[i]=(coe * price + (count - coe) * var3s[i - 1]) / count;
      }
    }

    float [] funcSmaVar3=new float[datas.size()];//多方
    for (int i = 0; i < datas.size(); i++) {
      float var3=var3s[i];
      if(i==0)
      {
        funcSmaVar3[i]=var3;
      }else{
        int count=13;//count==M1  coe=1   LWR1:SMA(RSV,M1,1);
        float price=var3s[i];
        int coe=8;
        funcSmaVar3[i]=(coe * price + (count - coe) * funcSmaVar3[i - 1]) / count;
      }
    }

    float [] funcSmaVar1=new float[datas.size()];//空方
    for (int i = 0; i < datas.size(); i++) {
      float var1=var1s[i];
      if(i==0)
      {
        funcSmaVar1[i]=var1;
      }else{
        int count=21;//count==M1  coe=1   LWR1:SMA(RSV,M1,1);
        float price=var1s[i];
        int coe=8;
        funcSmaVar1[i]=(coe * price + (count - coe) * funcSmaVar1[i - 1]) / count;
      }
    }

    for (int i = 0; i < datas.size(); i++) {
      KLineEntity point = datas.get(i);
      point.sell=90f;
      point.buy=0f;
      point.ene1=funcSmaVar3[i];
      point.ene2=funcSmaVar1[i];
    }
  }

  /**
   * 计算n日内平均值 ，包括当前的值
   * 比如今天5号  n=3  则计算 3、4、5的平均值
   * @param datas
   */
  static float [] getMAs(float [] datas,int n) {
    float [] maNs=new float[datas.length];
    float sumN = 0;
    for (int i = 0; i < datas.length; i++) {
      float price = datas[i];
      sumN += price;

      if (i >= n) {
        sumN -= datas[i - n];
        maNs[i] = sumN / n;
      } else {
        maNs[i] = sumN / (i + 1f);
      }
    }
    return maNs;
  }


  static float [] getMAs(List<KLineEntity> datas,int n) {
    float [] maNs=new float[datas.size()];
    float sumN = 0;
    for (int i = 0; i < datas.size(); i++) {
      KLineEntity entry = datas.get(i);
      float price = entry.getClosePrice();
      sumN += price;
      if (i >= n) {
        sumN -= datas.get(i - n).getClosePrice();
        maNs[i] = sumN / n;
      } else {
        maNs[i] = sumN / (i + 1f);
      }
    }
    return maNs;
  }

  /**
   * EMA(X, N): 求X在N日移动均匀值
   * 比如今天5号  n=3  则计算 3、4、5的平均值
   * @param datas
   */
  static float [] getEMAs(List<KLineEntity> datas,int n) {
    float [] emaNs=new float[datas.size()];
    for (int i = 0; i < datas.size(); i++) {
      KLineEntity entry = datas.get(i);
      float price = entry.getClosePrice();
      if(i==0)
      {
        emaNs[i] = price;
      }else{
        int count=n+1;
        emaNs[i] = (2 * price + (n - 1) * emaNs[i - 1]) / count;
      }
    }
    return emaNs;
  }


  /**
   * EMA(X, N): 求X在N日移动均匀值
   * 比如今天5号  n=3  则计算 3、4、5的平均值
   * @param datas
   */
  static float [] getEMAs(float [] datas,int n) {
    float [] emaNs=new float[datas.length];
    float sumN = 0;
    for (int i = 0; i < datas.length; i++) {
      float price = datas[i];
      if(i==0)
      {
        emaNs[i] = price;
      }else{
        int count=n+1;
        emaNs[i] = (2 * price + (n - 1) * emaNs[i - 1]) / count;
      }
    }
    return emaNs;
  }


  /**
   * 计算dif
   */
  static float [] getDifs(float [] datas1,float [] datas2) {
    float [] difNs=new float[datas1.length];
    for (int i = 0; i < datas1.length; i++) {
      difNs[i] = datas1[i]-datas2[i];
    }
    return difNs;
  }


  private static void calculateVolumeMA(List<KLineEntity> entries) {
    float volumeMa5 = 0;
    float volumeMa10 = 0;

    for (int i = 0; i < entries.size(); i++) {
      KLineEntity entry = entries.get(i);

      volumeMa5 += entry.getVolume();
      volumeMa10 += entry.getVolume();

      if (i >= 5) {

        volumeMa5 -= entries.get(i - 5).getVolume();
        entry.MA5Volume = (volumeMa5 / 5f);
      } else {

        entry.MA5Volume = (volumeMa5 / (i + 1f));
      }

      if (i >= 10) {
        volumeMa10 -= entries.get(i - 10).getVolume();
        entry.MA10Volume = (volumeMa10 / 5f);
      } else {
        entry.MA10Volume = (volumeMa10 / (i + 1f));
      }
    }
  }

  private static void calculatePBX(List<KLineEntity> entries) {
    int size = entries.size();
    final int [] nValue = {4,6,9,13};
    float maSum1, maSum2, ema, a, ma1, ma2;
    int md2, md4;

    for(int i=0; i<nValue.length; i++){
      int m = nValue[i];
      md2 = m*2;
      md4 = m*4;
      ema = 0;
      maSum1 = 0;
      maSum2 = 0;
      a = 2f/(m+1f);
      for (int j = 0; j < size; j++) {
        KLineEntity entry = entries.get(j);
        maSum1 += entry.getClosePrice();
        maSum2 += entry.getClosePrice();
        if (j >= md2) {
          maSum1 -= entries.get(j - md2).getClosePrice();
          ma1 = maSum1 / md2;
        } else {
          ma1 = maSum1 / (j + 1f);
        }

        if (j >= md4) {
          maSum2 -= entries.get(j - md4).getClosePrice();
          ma2 = maSum2 / md4;
        } else {
          ma2 = maSum2 / (j + 1f);
        }

        if (j == 0) {
          ema = entry.getClosePrice();
        } else {
          ema = ema * (1-a) + entry.getClosePrice() * a;
        }
        entry.pbx[i] = (ema+ma1+ma2)/3;
      }
    }
  }

  private static void calculateMIKE(List<KLineEntity> entries) {
    final int n = MyApplication.getSignalParamValues().mike.value1;

    int size = entries.size();
    float typ, hh, ll;
    for(int i=0; i<size; i++){
      KLineEntity entry = entries.get(i);
      typ = (entry.getHighPrice()+entry.getLowPrice()+entry.getClosePrice())/3;
      hh = 0;
      ll = Float.MAX_VALUE;
      for(int j=Math.max(i-n, 0); j<=i; j++){
        KLineEntity en = entries.get(j);
        float hPrice = en.getHighPrice();
        float lPrice = en.getLowPrice();
        if(hPrice>hh){
          hh = hPrice;
        }
        if(lPrice<ll){
          ll = lPrice;
        }
      }
      entry.mike_wr = typ + (typ - ll);
      entry.mike_mr = typ + (hh - ll);
      entry.mike_sr = hh * 2 - ll;
      entry.mike_ws = typ - (hh - typ);
      entry.mike_ms = typ - (hh-ll);
      entry.mike_ss = ll*2 - hh;
    }
  }

  private static void calculateARBR(List<KLineEntity> entries) {
    final int n = MyApplication.getSignalParamValues().arbr.value1;
    float hMinusO = 0,oMinusL = 0, hMinusPC = 0, pcMinusL = 0;
    for(int i=0; i<entries.size(); i++) {
      KLineEntity entity = entries.get(i);
      hMinusO += entity.getHighPrice() - entity.getOpenPrice();
      oMinusL += entity.getOpenPrice() - entity.getLowPrice();
      if(i>0){
        float preClose = entries.get(i-1).getClosePrice();
        hMinusPC += entity.getHighPrice() - preClose;
        pcMinusL += preClose - entity.getLowPrice();
      } else {
        hMinusPC += entity.getHighPrice() - entity.getOpenPrice();
        pcMinusL += entity.getOpenPrice() - entity.getLowPrice();
      }
      if(i>=n){
        KLineEntity pre26Entity = entries.get(i-n);
        hMinusO -= pre26Entity.getHighPrice() - pre26Entity.getOpenPrice();
        oMinusL -= pre26Entity.getOpenPrice() - pre26Entity.getLowPrice();
        float pre27Close = i==n ? pre26Entity.getOpenPrice() : entries.get(i-n-1).getClosePrice();
        hMinusPC -= pre26Entity.getHighPrice() - pre27Close;
        pcMinusL -= pre27Close - pre26Entity.getLowPrice();
      }
      entity.ar = hMinusO / oMinusL * 100f;
      entity.br = hMinusPC / pcMinusL * 100f;
    }
  }

  private static void calculateART(List<KLineEntity> entries) {
    final int n = MyApplication.getSignalParamValues().atr.value1;
    float atr = 0, tr, trSum = 0;
    float[] trAry  = new float[entries.size()];
    KLineEntity preEntity = null;
    for(int i=0; i<entries.size(); i++) {
      KLineEntity entity = entries.get(i);
      if(preEntity!=null){
        tr = Math.max(Math.max(entity.getHighPrice()-entity.getLowPrice(),
                Math.abs(preEntity.getClosePrice()-entity.getHighPrice())),
                Math.abs(preEntity.getClosePrice()-entity.getLowPrice()));
      } else {
        tr = entity.getHighPrice()-entity.getLowPrice();
      }
      trAry[i] = tr;
      trSum += tr;
      if(i>=n) {
        trSum -= trAry[i-n];
        entity.atr = trSum / n;
      } else {
        entity.atr = trSum / (i+1f);
      }
      entity.tr = tr;

      preEntity = entity;
    }
  }
}

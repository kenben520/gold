package com.lingxi.preciousmetal.util.utilCode;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.tifezh.kchartlib.chart.entity.KMinuteLineEntity;
import com.github.tifezh.kchartlib.utils.ViewUtil;
import com.lingxi.biz.domain.responseMo.HomeAllResultBean;
import com.lingxi.biz.domain.responseMo.KLineChartResultBean;
import com.lingxi.biz.domain.responseMo.KMarketResultBean;
import com.lingxi.biz.util.ConstantUtil;
import com.lingxi.common.base.BaseApplication;
import com.lingxi.common.util.AppUtil;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.MyApplication;
import com.lingxi.preciousmetal.chart.entity.KLineEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qian on 2018/5/30.
 */

public class AppViewUtils {

    public static void setHomeTextViewColor(Context context, String str,TextView textView){
        SpannableStringBuilder sbuilder = new SpannableStringBuilder(str);
        if (TextUtils.isEmpty(str)){
            return;
        }
        int color = 0;
        if (str.contains("看多")){
            color = ViewUtil.getKUpColor(context);
        } else  if (str.contains("看空")){
            color = ViewUtil.getKLossColor(context);
        } else {
            color = ContextCompat.getColor(context,R.color.black);
        }
        ForegroundColorSpan yellowSpan = new ForegroundColorSpan(color);
        int start = str.indexOf(" ");
        sbuilder.setSpan(yellowSpan, start,str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(sbuilder);
    }

    public static void setDrawableRadius(View view,int radius,int color){
        view.setBackground(null);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(radius);
        drawable.setColor(color);
        view.setBackground(drawable);
    }

    public static void setUpAnimator(Context context, View view,String color){
        if (TextUtils.isEmpty(color)){
            return;
        }
        int chart_white = 0xffffffff;
        view.clearAnimation();
        ValueAnimator colorAnim = ObjectAnimator.ofInt(view,"backgroundColor", Color.parseColor(color),chart_white);
        colorAnim.setDuration(600);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(0);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();
    }

    public static void setUpDownIvAnimator(Context context, ImageView view, String color){
        if (TextUtils.isEmpty(color)){
            return;
        }
        int chart_white = 0xffffffff;
        view.clearAnimation();
        ValueAnimator colorAnim = ObjectAnimator.ofInt(view,"backgroundColor", Color.parseColor(color),chart_white);
        colorAnim.setDuration(600);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(0);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();
    }



    public static void kPriceLandStyle(Context mContext, KMarketResultBean.MarketBean bean,KMarketResultBean.MarketBean nowBean,String priceDecimal
            ,TextView kMaxText,TextView kLowText,TextView kTodayOpenText,TextView kYesterdayCloseText){
        if (bean==null){
            return;
        }
        float max = bean.high_px;
        float low = bean.low_px;
        float open = bean.open_px;
        float close = bean.preclose_px;
        if (nowBean!=null){
            //如果超过最高最低才更新
            boolean flag = false;
            float last_px = nowBean.last_px;
            if (last_px>max){
                max = last_px;
                flag = true;
            }
            if (last_px<low){
                low = last_px;
                flag = true;
            }
            if (!flag){
                return;
            }
        }
        String maxStr = String.format(priceDecimal,max);
        String lowStr = String.format(priceDecimal,low);
        String openStr = String.format(priceDecimal,open);
        String closeStr = String.format(priceDecimal,close);

        if (open>close){
            AppUtils.setTextViewColor("今开 " + openStr, 0, 2, kTodayOpenText,ViewUtil.getKUpColor(mContext,2));
        } else {
            AppUtils.setTextViewColor("今开 " + openStr, 0, 2, kTodayOpenText,ViewUtil.getKLossColor(mContext,2));
        }
        if (max>close){
            AppUtils.setTextViewColor("最高 " + maxStr, 0, 2, kMaxText,ViewUtil.getKUpColor(mContext,2));
        } else {
            AppUtils.setTextViewColor("最高 " + maxStr, 0, 2, kMaxText,ViewUtil.getKLossColor(mContext,2));
        }
        if (low>close){
            AppUtils.setTextViewColor("最低 " + lowStr, 0, 2, kLowText, ContextCompat.getColor(mContext, R.color.whiteTwo33));
        } else {
            AppUtils.setTextViewColor("最低 " + lowStr, 0, 2, kLowText, ViewUtil.getKLossColor(mContext,2));
        }
        AppUtils.setTextViewColor("昨收 " + closeStr, 0, 2, kYesterdayCloseText,ContextCompat.getColor(mContext,R.color.whiteColor));
    }

    public static void kPriceVerStyle(Context mContext, KMarketResultBean.MarketBean bean,KMarketResultBean.MarketBean nowBean,String priceDecimal
        ,TextView kMaxText,TextView kLowText,TextView kTodayOpenText,TextView kYesterdayCloseText){
        if (bean==null){
            return;
        }
        float max = bean.high_px;
        float low = bean.low_px;
        float open = bean.open_px;
        float close = bean.preclose_px;

        if (nowBean!=null){
            //如果超过最高最低才更新
            boolean flag = false;
            float last_px = nowBean.last_px;
            if (last_px>max){
                max = last_px;
                flag = true;
            }
            if (last_px<low){
                low = last_px;
                flag = true;
            }
//            if (!flag){
                 // 去掉优化
//                return;
//            }
        }

        String maxStr = String.format(priceDecimal,max);
        String lowStr = String.format(priceDecimal,low);
        String openStr = String.format(priceDecimal,open);
        String closeStr = String.format(priceDecimal,close);
        kMaxText.setText(maxStr);
        kLowText.setText(lowStr);
        kTodayOpenText.setText(openStr);
        kYesterdayCloseText.setText(closeStr);

        if (open>close){
            kTodayOpenText.setTextColor(ViewUtil.getKUpColor(mContext,2));
        } else if (open==close){
            kTodayOpenText.setTextColor(ContextCompat.getColor(mContext,R.color.whiteColor));
        } else {
            kTodayOpenText.setTextColor(ViewUtil.getKLossColor(mContext,2));
        }
        if (max>close){
            kMaxText.setTextColor(ViewUtil.getKUpColor(mContext,2));
        } else if (max==close){
            kMaxText.setTextColor(ContextCompat.getColor(mContext,R.color.whiteColor));
        }  else {
            kMaxText.setTextColor(ViewUtil.getKLossColor(mContext,2));
        }
        if (low>close){
            kLowText.setTextColor(ViewUtil.getKUpColor(mContext,2));
        } else if (low==close){
            kLowText.setTextColor(ContextCompat.getColor(mContext,R.color.whiteColor));
        }  else {
            kLowText.setTextColor(ViewUtil.getKLossColor(mContext,2));
        }
    }

    public static void setBuyOrSell(Context context, String type, TextView textView){
        if ("buy".equals(type)){
            textView.setText(context.getString(R.string.kBuy));
            textView.setBackgroundColor(ViewUtil.getKUpColor(context));
        } else {
            textView.setBackgroundColor(ViewUtil.getKLossColor(context));
            textView.setText(context.getString(R.string.kSell));
        }
    }

    public static String getBuyOrSellStr(String type){
        if ("buy".equals(type)){
            return  "买入";
        } else {
            return  "买出";
        }
    }

    public static void setNowPrice2(Context context, TextView nameText,TextView percentText,KMarketResultBean.MarketBean marketBean,String dian){
        String value = dian;
        float changeDb = marketBean.px_change;
        if (nameText!=null){
            String rate = String.format(value,marketBean.last_px);
            nameText.setText(rate);
            AppUtils.setCustomFont(context,nameText);
        }
        String rate = String.format("%.2f",marketBean.px_change_rate);
        String change = String.format(value,changeDb);
        Drawable rightDrawable = null;
        String  percent = null;
        if (changeDb>0){
            // 显示+
            percent = "+" + change+"  +"+rate+"%";
            rightDrawable = ViewUtil.getKUpImage(context,1);
            nameText.setTextColor(ViewUtil.getKUpColor(context,2));
            percentText.setTextColor(ViewUtil.getKUpColor(context,2));
        } else {
            percent = change+"  "+rate+"%";
            rightDrawable = ViewUtil.getKLossImage(context,1);
            nameText.setTextColor(ViewUtil.getKLossColor(context,2));
            percentText.setTextColor(ViewUtil.getKLossColor(context,2));
        }
        rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
        nameText.setCompoundDrawables(null, null, rightDrawable, null);
        percentText.setText(percent);
    }

    public static void setNowPrice(Context context, TextView nameText,TextView percentText,KMarketResultBean.MarketBean marketBean){
        String value = "";
        String code = marketBean.p_code;
        if (ConstantUtil.USDOLLARINDEX.equals(code)){
            value = "%.4f";
        } else if (ConstantUtil.XAGUSD.equals(code)){
            value = "%.3f";
        } else if (ConstantUtil.XAUUSD.equals(code)){
            value = "%.2f";
        } else {
            value = "%.2f";
        }
        double changeDb = marketBean.px_change;
        if (nameText!=null){
            String rate = String.format(value,marketBean.last_px);
            nameText.setText(rate);
            AppUtils.setCustomFont(context,nameText);
        }

        String rate = String.format("%.2f",marketBean.px_change_rate);
        String change = String.format(value,changeDb);
        Drawable rightDrawable = null;
        String  percent = null;
        if (changeDb>0){
            // 显示+
            percent = "+" + change+"\n+"+rate+"%";
            percentText.setTextColor(ViewUtil.getKUpColor(context));
            nameText.setTextColor(ViewUtil.getKUpColor(context));
            rightDrawable = ViewUtil.getKUpImage(context,2);
        } else {
            percent = change+"\n"+rate+"%";
            percentText.setTextColor(ViewUtil.getKLossColor(context));
            nameText.setTextColor(ViewUtil.getKLossColor(context));
            rightDrawable = ViewUtil.getKLossImage(context,2);
        }
        rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
        nameText.setCompoundDrawables(null, null, rightDrawable, null);
        percentText.setText(percent);
    }

    public static void setMarketValue(Context context, TextView nameText,TextView priceText,TextView percentText, HomeAllResultBean.MarketBean marketBean){
        String value = "%.2f";
        // 黄金小数点2位，白银小数点3位，美元小数点4位
        String code = marketBean.getP_code();
        if (ConstantUtil.USDOLLARINDEX.equals(code)){
            value = "%.4f";
        } else if (ConstantUtil.XAGUSD.equals(code)){
            value = "%.3f";
        } else if (ConstantUtil.XAUUSD.equals(code)){
            value = "%.2f";
        } else {
            value = "%.2f";
        }
        if (nameText!=null){
            nameText.setText(marketBean.getProd_name());
        }
        float nowPx = marketBean.getLast_px();
        if (priceText!=null){
            priceText.setText(String.format(value,nowPx));
            if (marketBean.flagKUp==1){
                priceText.setTextColor(ViewUtil.getKUpColor(context));
            } else if (marketBean.flagKUp==2){
                priceText.setTextColor(ViewUtil.getKLossColor(context));
            } else {
              //  priceText.setTextColor(ViewUtil.getKNormalColor(context,1));
            }
            AppUtils.setCustomFont(context,priceText);
        }
        float close =  marketBean.getPreclose_px();
        float changeDb = nowPx- close;
        float ratePx = changeDb/close*100;
        if (percentText!=null){
            String rate = String.format("%.2f",ratePx);
            String change = String.format(value,changeDb);

            String  percent = null;
            if (changeDb>0){
                // 显示+
                percent = "+" + change+"   +"+rate+"%";
                percentText.setTextColor(ViewUtil.getKUpColor(context));
            } else {
                percent = change+"   "+rate+"%";
                percentText.setTextColor(ViewUtil.getKLossColor(context));
            }
            percentText.setText(percent);
        }
    }

    public static List<KLineEntity> getKData(List<KLineChartResultBean.ChartBean> chartList){
        List<KLineEntity> kchartData = new ArrayList<>();
        for (KLineChartResultBean.ChartBean chartBean : chartList) {
            KLineEntity entity = new KLineEntity();
            entity.Close = chartBean.getClose_px();
            entity.Open = chartBean.getOpen_px();
            entity.High = chartBean.getHigh_px();
            entity.Low = chartBean.getLow_px();
            entity.kDate = chartBean.getTime_stamp() * 1000;
            entity.Volume = 5928000;
            kchartData.add(entity);
        }
        return kchartData;
    }

    public static void setAlphaAnimator(Context context, View view){
        view.clearAnimation();
        AlphaAnimation colorAnim = new AlphaAnimation(1.0f,0.0f);
        colorAnim.setDuration(1200);
        colorAnim.setRepeatCount(0);
        colorAnim.setFillAfter(true);//动画结束后保持状态
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        view.startAnimation(colorAnim);
    }


    public static Drawable getKUpArrowImage(Context context){
        int kIndexColor = BaseApplication.kIndexColor;
        if (kIndexColor==0){
            return ContextCompat.getDrawable(context, R.drawable.k_up_green);
        } else {
            return  ContextCompat.getDrawable(context, R.drawable.k_up_red);
        }
    }

    public static Drawable getKLossArrowImage(Context context){
        int kIndexColor = BaseApplication.kIndexColor;
        if (kIndexColor==0){
            return ContextCompat.getDrawable(context,R.drawable.k_down_red);
        } else {
            return  ContextCompat.getDrawable(context, R.drawable.k_down_green);
        }
    }
}

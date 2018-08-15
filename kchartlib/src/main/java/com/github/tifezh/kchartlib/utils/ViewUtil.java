package com.github.tifezh.kchartlib.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.github.tifezh.kchartlib.R;
import com.lingxi.common.base.BaseApplication;

import java.math.BigDecimal;

/**
 * Created by tian on 2016/4/11.
 */
public class ViewUtil {

    public static String getKUpBgColor(Context context){
        if (BaseApplication.kIndexColor==0){
            return "#300DA206";
        } else {
            return "#30FB3B3B";
        }
    }

    public static String getKLossBgColor(Context context){
        if (BaseApplication.kIndexColor==0){
            return "#30FB3B3B";
        } else {
            return "#300DA206";
        }
    }

    public static int getKUpColor(Context context){
        if (BaseApplication.kIndexColor==0){
            return ContextCompat.getColor(context, R.color.kWhiteGreen);
        } else {
            return  ContextCompat.getColor(context,R.color.kWhiteRed);
        }
    }

    /**
     * 1白色底 2 黑色底
     * @param context
     * @param type
     * @return
     */
    public static int getKNormalColor(Context context,int type){
        if (type==1){
            return ContextCompat.getColor(context,R.color.kWhiteNormal);
        } else {
            return  ContextCompat.getColor(context,R.color.kBlackNormal);
        }
    }

    /**
     * @param context
     * @param type 1白底 2黑底
     * @return
     */
    public static int getKUpColor(Context context,int type){
        int kIndexColor = BaseApplication.kIndexColor;
        if (type==1){
            if (kIndexColor==0){
                return ContextCompat.getColor(context, R.color.kWhiteGreen);
            } else {
                return  ContextCompat.getColor(context,R.color.kWhiteRed);
            }
        } else if(type==2){
            if (kIndexColor==0){
                return ContextCompat.getColor(context, R.color.kBlackGreen);
            } else {
                return ContextCompat.getColor(context,R.color.kBlackRed);
            }
        }
      return ContextCompat.getColor(context,R.color.kBlackRed);
    }

    /**
     * @param context
     * @param type 图片大小 1大的 2小的
     * @return
     */
    public static Drawable getKUpImage(Context context,int type){
        int kIndexColor = BaseApplication.kIndexColor;
        if (type==1){
            if (kIndexColor==0){
                return ContextCompat.getDrawable(context, R.drawable.k_up_green_big);
            } else {
                return  ContextCompat.getDrawable(context, R.drawable.k_up_red_big);
            }
        } else if (type==2) {
            if (kIndexColor == 0) {
                return ContextCompat.getDrawable(context, R.drawable.k_up_green_small);
            } else {
                return ContextCompat.getDrawable(context, R.drawable.k_up_red_small);
            }
        } else {
            return null;
        }
    }

    /**
     * @param context
     * @param type 图片大小 1大的 2小的
     * @return
     */
    public static Drawable getKLossImage(Context context,int type){
        int kIndexColor = BaseApplication.kIndexColor;
        if (type==1){
            if (kIndexColor==0){
                return ContextCompat.getDrawable(context, R.drawable.k_loss_red_big);
            } else {
                return ContextCompat.getDrawable(context, R.drawable.k_loss_green_big);
            }
        } else if (type==2) {
            if (kIndexColor == 0) {
                return ContextCompat.getDrawable(context, R.drawable.k_loss_red_small);
            } else {
                return ContextCompat.getDrawable(context, R.drawable.k_loss_green_small);
            }
        } else {
            return null;
        }
    }

    public static int getKLossColor(Context context){
        if (BaseApplication.kIndexColor==0){
            return ContextCompat.getColor(context,R.color.kWhiteRed);
        } else {
            return  ContextCompat.getColor(context,R.color.kWhiteGreen);
        }
    }

    public static int getKLossColor(Context context,int type){
        if (type==1){
            if (BaseApplication.kIndexColor==0){
                return ContextCompat.getColor(context,R.color.kWhiteRed);
            } else {
                return  ContextCompat.getColor(context,R.color.kWhiteGreen);
            }
        } else if (type==2){
            if (BaseApplication.kIndexColor==0){
                return ContextCompat.getColor(context,R.color.kBlackRed);
            } else {
                return  ContextCompat.getColor(context,R.color.kBlackGreen);
            }
        }
        return ContextCompat.getColor(context,R.color.kBlackGreen);
    }

    static public int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    static public int Px2Dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static int getNumberDecimalDigits(float balance) {
        int dcimalDigits = 0;
        String balanceStr = new BigDecimal(balance).toString();
        int indexOf = balanceStr.indexOf(".");
        if(indexOf > 0){
            dcimalDigits = balanceStr.length() - 1 - indexOf;
        }
        return dcimalDigits;
    }

}

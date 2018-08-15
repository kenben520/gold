package com.lingxi.preciousmetal.util;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangwei on 2018/6/13.
 */

public class MoneyInputFilterSilver implements InputFilter {
    Pattern mPattern;

    //输入的最大金额
    private float MAX_VALUE = Integer.MAX_VALUE;
    //小数点后的位数
    private static final int POINTER_LENGTH = 3;
    //小数点前的位数
    private static final int POINTER_PRE_LENGTH = 3;//必须大于0

    private static final String POINTER = ".";

    private static final String ZERO = "0";

    public MoneyInputFilterSilver() {
        mPattern = Pattern.compile("([0-9]|\\.)*");
    }

    /**
     * @param source 新输入的字符串
     * @param start  新输入的字符串起始下标，一般为0
     * @param end    新输入的字符串终点下标，一般为source长度-1
     * @param dest   输入之前文本框内容
     * @param dstart 原内容起始坐标，一般为0
     * @param dend   原内容终点坐标，一般为dest长度-1
     * @return 输入内容
     */
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        String sourceText = source.toString();//新输入数字字符串   1299.413     3
        String destText = dest.toString();//原来数字字符串 1299.41

        //验证删除等按键
        if (TextUtils.isEmpty(sourceText)) {
            return "";
        }

        Matcher matcher = mPattern.matcher(source);
        //已经输入小数点的情况下，只能输入数字
        if (destText.contains(POINTER)) {
            if (!matcher.matches()) {
                return "";
            } else {
                if (POINTER.equals(source.toString())) {  //只能输入一个小数点
                    return "";
                }
            }

            //验证小数点精度，保证小数点后只能输入两位
            int index = destText.indexOf(POINTER);
            int length = dend - index;
            if (length > POINTER_LENGTH) {
                CharSequence subSeq = dest.subSequence(dstart, dend);
                return subSeq;
            }
            if (index > (POINTER_PRE_LENGTH - 1) && dstart < index) {//小数点前大于5位 则裁剪
                return dest.subSequence(dstart, dend);
            }
        } else {
            int desLength = destText.length();
            if (desLength > POINTER_PRE_LENGTH) {
                return "";
            }
            /**
             * 没有输入小数点的情况下，只能输入小数点和数字
             * 1. 首位不能输入小数点
             * 2. 如果首位输入0，则接下来只能输入小数点了
             */
            if (!matcher.matches()) {
                return "";
            } else {
                if ((POINTER.equals(source.toString())) && TextUtils.isEmpty(destText)) {  //首位不能输入小数点
                    return "";
                } else if (!POINTER.equals(source.toString()) && ZERO.equals(destText)) { //如果首位输入0，接下来只能输入小数点
                    return "";
                }
            }
        }

        //验证输入金额的大小
        double sumText = Double.parseDouble(destText + sourceText);
        if (sumText > MAX_VALUE) {
            return dest.subSequence(dstart, dend);
        }

        return dest.subSequence(dstart, dend) + sourceText;
    }

    public void setMaxValue(float maxValue) {
        MAX_VALUE = maxValue;
    }
}
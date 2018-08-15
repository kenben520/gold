package com.lingxi.preciousmetal.util;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 验证类
 * Created by zhangwei on 2018/4/18.
 */

public class VerifyUtil {
    private static final String PHONE_NUMBER_PATTERN = "^1\\d{10}$";
    private static final Pattern sPhotoNumberPattern = Pattern.compile(PHONE_NUMBER_PATTERN);

    /**
     * 手机号格式验证
     *
     * @param number
     * @return
     */
    public static boolean isValidPhoneNumber(String number) {
        return sPhotoNumberPattern.matcher(number).matches();
    }

    /**
     * 验证码格式验证
     *
     * @param verifyCode
     * @return
     */
    public static boolean isValidVerifyCode(String verifyCode) {
        return !TextUtils.isEmpty(verifyCode) && verifyCode.length() >= 4;
    }

    /**
     * 姓名格式验证
     *
     * @param name
     * @return
     */
    public static boolean isValidName(String name) {
        return !TextUtils.isEmpty(name) && name.length() >= 1 && name.length() <= 12;
    }

    private static final String PASSWORD_PATTERN = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,15}$";

    /**
     * 验证密码
     *
     * @param str
     * @return
     */
    public static boolean isValidNamePassword(String str) {
        return Pattern.compile(PASSWORD_PATTERN).matcher(str).matches();
    }

    /**
     * 校验过程：
     * 1、从卡号最后一位数字开始，逆向将奇数位(1、3、5等等)相加。
     * 2、从卡号最后一位数字开始，逆向将偶数位数字，先乘以2（如果乘积为两位数，将个位十位数字相加，即将其减去9），再求和。
     * 3、将奇数位总和加上偶数位总和，结果应该可以被10整除。
     * 校验银行卡卡号
     */
    public static boolean checkBankCard(String bankCard) {
        if (TextUtils.isEmpty(bankCard) || bankCard.length() < 15 || bankCard.length() > 19) {
            return false;
        }
        char bit = getBankCardCheckCode(bankCard.substring(0, bankCard.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return bankCard.charAt(bankCard.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhn 校验算法获得校验位
     */
    public static char getBankCardCheckCode(String nonCheckCodeBankCard) {
        if (nonCheckCodeBankCard == null || nonCheckCodeBankCard.trim().length() == 0
                || !nonCheckCodeBankCard.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeBankCard.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

    /**
     * 验证身份证是否合法
     *
     * @param idCardNum
     * @return
     */
    public static boolean isValidIdCard(String idCardNum) {
        if (TextUtils.isEmpty(idCardNum)) return false;
        String errorMsg = IdCardCheckUtil.IDCardValidate(idCardNum);
        if (!TextUtils.isEmpty(errorMsg)) return false;
        return true;
    }

    public static String stringFilter(String str) throws
            PatternSyntaxException {
        // 只允许字母、数字和汉字
        String regEx =
                "[^a-zA-Z0-9\u4E00-\u9FA5]";//正则表达式
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public static String stringFilter1(String str) throws
            PatternSyntaxException {
        // 只允许汉字
        String regEx =
                "[^\u4E00-\u9FA5]";//正则表达式
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * 判断是否为数字包括整数 小数等所有数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    /**
     * 检测是否邮箱
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        Pattern pattern = Pattern
                .compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}

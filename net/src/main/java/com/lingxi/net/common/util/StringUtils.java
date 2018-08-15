package com.lingxi.net.common.util;


/**
 * Created by zhangwei on 2018/3/29.
 */
public class StringUtils {
    /**
     * 判断字符串是否为空（包括null和长度为0）
     *
     * @param str 需要处理的字符串
     * @return 字符串是否为空
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * @param str
     * @return
     */
    public static boolean isNotBlank(String str) {
        return !StringUtils.isBlank(str);
    }

    /**
     * Checks if a String is whitespace, empty ("") or null.</p>
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 拼接字符串，用"$"连接
     *
     * @param key1
     * @param key2
     * @return
     */
    public static String concatStr(String key1, String key2) {

        if (StringUtils.isBlank(key1) || StringUtils.isBlank(key2)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(key1.trim());
        sb.append(SymbolExpUtil.SYMBOL_DOLLAR);
        sb.append(key2.trim());

        return sb.toString();
    }

    /**
     * 拼接字符串，用"$"连接，并且字符串转成小写形式
     *
     * @param key1
     * @param key2
     * @return
     */
    public static String concatStr2LowerCase(String key1, String key2) {

        if (StringUtils.isBlank(key1) || StringUtils.isBlank(key2)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(key1.trim());
        sb.append(SymbolExpUtil.SYMBOL_DOLLAR);
        sb.append(key2.trim());

        return sb.toString().toLowerCase();
    }


    /**
     * 拼接字符串，用"$"连接，并且字符串转成小写形式
     *
     * @param key1
     * @param keys
     * @return
     */
    public static String concatStr2LowerCase(String key1, String... keys) {

        if (StringUtils.isBlank(key1) || null == keys) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(key1.trim());
        if (keys.length > 0) {
            for (String key : keys) {
                if (StringUtils.isNotBlank(key)) {
                    sb.append(SymbolExpUtil.SYMBOL_DOLLAR);
                    sb.append(key.trim());
                }
            }
        }
        return sb.toString().toLowerCase();
    }

    /**
     * @param source
     * @param seperator
     * @return
     */
    public static String[] splitString(String source, String seperator) {
        if (null == source) {
            return null;
        }

        if (StringUtils.isBlank(seperator)) {
            return new String[]{seperator};
        }

        String[] items = source.split(seperator);
        return items;
    }
}


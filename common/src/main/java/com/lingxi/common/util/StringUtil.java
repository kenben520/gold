package com.lingxi.common.util;

import com.lingxi.net.common.util.SymbolExpUtil;

/**
 * Created by zhangwei on 2018/3/26.
 */
public final class StringUtil {
    private StringUtil() {
        throw new UnsupportedOperationException("Utility class should not be inistantiated");
    }

    public static boolean isAbsolutelyNotEmpty(CharSequence target) {
        return (null != target) && (!"".equals(target.toString().trim()));
    }

    public static boolean isAbsolutelyEmpty(CharSequence target) {
        return (null == target) || ("".equals(target));
    }

    public static boolean isNotEmpty(CharSequence target) {
        return (null != target) && (!"".equals(target));
    }

    public static boolean isEmpty(CharSequence target) {
        return (null == target) || ("".equals(target.toString().trim()));
    }

    public static boolean equals(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equals(str2);
    }

    public static boolean match(String value, String keyword) {
        if (value == null || keyword == null)
            return false;
        if (keyword.length() > value.length())
            return false;

        int i = 0, j = 0;
        do {
            if (keyword.charAt(j) == value.charAt(i)) {
                i++;
                j++;
            } else if (value.charAt(i) > 90 && value.charAt(i) - 32 == keyword.charAt(j)) {
                i++;
                j++;
            } else if (j > 0)
                break;
            else
                i++;
        } while (i < value.length() && j < keyword.length());

        return (j == keyword.length()) ? true : false;
    }

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
        return !StringUtil.isBlank(str);
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
     * @param source
     * @param seperator
     * @return
     */
    public static String[] splitString(String source, String seperator) {
        if (null == source) {
            return null;
        }

        if (StringUtil.isBlank(seperator)) {
            return new String[]{seperator};
        }

        String[] items = source.split(seperator);
        return items;
    }

    /**
     * 拼接字符串，用"$"连接
     *
     * @param key1
     * @param key2
     * @return
     */
    public static String concatStr(String key1, String key2) {

        if (StringUtil.isBlank(key1) || StringUtil.isBlank(key2)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(key1.trim());
        sb.append(SymbolExpUtil.SYMBOL_DOLLAR);
        sb.append(key2.trim());

        return sb.toString();
    }
}


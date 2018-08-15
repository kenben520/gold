package com.lingxi.net.basenet.util;

import com.lingxi.net.common.util.NetSdkLog;
import com.lingxi.net.common.util.StringUtils;
import com.lingxi.net.common.util.SymbolExpUtil;

import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
/**
 * Created by zhangwei on 2018/4/16.
 */
public class SecurityUtils {
    private static final String TAG = "netsdk.SecurityUtils";
//    private static final String SPLIT_STR = "&";


    /**
     * null转""
     *
     * @param param
     * @return
     */
    private static String convertNull2Default(String param) {
        return (null == param) ? "" : param;
    }

    /**
     * 对参数进行md5加签
     * @param originMap
     * @param appSecretKey
     * @return
     */
    public static String getMd5Sign(Map<String, String> originMap, String appSecretKey) {
        // 根据key升序对参数进行排序
        TreeMap<String, String> tmpMap = new TreeMap<>(originMap);
        Iterator<String> iterator = tmpMap.keySet().iterator();
        StringBuffer tmpSb = new StringBuffer();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = tmpMap.get(key);
            tmpSb.append(key).append(convertNull2Default(value));
        }
        //添加加密秘钥
        tmpSb.append(appSecretKey);
        return getMd5(tmpSb.toString());
    }




    /**
     * @param source
     * @return
     */
    public static String getMd5(String source) {
        if (StringUtils.isBlank(source)) {
            return source;
        }
        String dest = null;
        try {
            // Create MD5 Hash
            MessageDigest digester = MessageDigest.getInstance("MD5");
            digester.update(source.getBytes(SymbolExpUtil.CHARSET_UTF8));
            byte[] digest = digester.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                String h = Integer.toHexString(0xFF & digest[i]);
                while (h.length() < 2) {
                    h = "0" + h;
                }
                hexString.append(h);
            }
            return hexString.toString();
        } catch (Exception e) {
            NetSdkLog.e(TAG, "[getMd5] compute md5 value failed for source str=" + source, e);
        }
        return dest;
    }
}

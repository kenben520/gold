package com.lingxi.net.basenet;

import java.util.HashMap;


/**
 * Created by zhangwei on 2018/3/29.
 * ErrorConstant类定义了API错误的返回值
 */

public class ErrorConstant {


    public final static int INT_UNKNOWN_ERROR = -1000;

    public final static int INT_ERRCODE_SUCCESS = -1001;
    /**
     * 系统异常,整数值从-2000开始递减
     */
    private final static int INT_SYSTEM_ERROR = -2000;
    /**
     * SDK retcode,整数值从-2500开始递减
     */
    public static final int INT_ANDROID_SYS_ERROR = -2500;

    /**
     * API调用成功
     */
    public static final String ERRCODE_SUCCESS = "0";

    private static final String FAIL_SYS_PREFIX = "FAIL_SYS_";
    /**
     *  服务端错误
     */
    /**
     * 缺少必填的参数  ERRMSG_FAIL_SYS_PARAM_MISSING="缺少必填的参数";
     */
    private static final String ERRCODE_FAIL_SYS_PARAM_MISSING = "30005";

    /**
     * 参数格式不正确  ERRMSG_FAIL_SYS_PARAM_FORMAT_ERROR="参数格式不正确";
     */
    private static final String ERRCODE_FAIL_SYS_PARAM_FORMAT_ERROR = "30006";


    /**
     * wapSession读取tair失败，在调用需要登录的接口时返回  ERRMSG_FAIL_SYS_SYSTEM_BUSY_ERROR ="抱歉,服务系统繁忙";
     */
    private static final String ERRCODE_FAIL_SYS_SYSTEM_BUSY_ERROR = "10001";
    private static final int INT_FAIL_SYS_SYSTEM_BUSY_ERROR = INT_SYSTEM_ERROR - 2;//wapSession读取tair失败，在调用需要登录的接口时返回



    /**
     * 无法获取session ERRMSG_FAIL_SYS_SESSION_EXPIRED="Session过期";
     */
    public static final String ERRCODE_FAIL_SYS_SESSION_EXPIRED = "20001";
    private final static int INT_FAIL_SYS_SESSION_EXPIRED = INT_SYSTEM_ERROR - 5;

    public static final int INT_FAIL_SYS_PARAM_MISSING = INT_SYSTEM_ERROR - 8;//未传递必填的参数

    private static final int INT_FAIL_SYS_PARAM_FORMAT_ERROR = INT_SYSTEM_ERROR - 9;//传递的参数与API定义的格式不一致

    /**
     * 时间戳t检测失败  ERRMSG_FAIL_SYS_REQUEST_EXPIRED ="请求失效";
     */
    private static final String ERRCODE_FAIL_SYS_REQUEST_EXPIRED = "30004";
    private static final int INT_FAIL_SYS_REQUEST_EXPIRED = INT_SYSTEM_ERROR - 16;

    /**
     * Hsf不存在  ERRMSG_FAIL_SYS_SERVICE_NOT_EXIST="请求服务不存在";
     */
    private static final String ERRCODE_FAIL_SYS_SERVICE_NOT_EXIST = "30001";
    private static final int INT_FAIL_SYS_SERVICE_NOT_EXIST = INT_SYSTEM_ERROR - 19;


    public static final String ERRCODE_NETWORK_ERROR = "ANDROID_SYS_NETWORK_ERROR";
    public static final String ERRMSG_NETWORK_ERROR = "网络错误";
    private static final int INT_ERRCODE_NETWORK_ERROR = INT_ANDROID_SYS_ERROR - 1;
//
    public static final String ERRCODE_JSONDATA_BLANK = "ANDROID_SYS_JSONDATA_BLANK";
    public static final String ERRMSG_JSONDATA_BLANK = "返回JSONDATA为空";
    private static final int INT_ERRCODE_JSONDATA_BLANK = INT_ANDROID_SYS_ERROR - 2;
//
    public static final String ERRCODE_JSONDATA_PARSE_ERROR = "ANDROID_SYS_JSONDATA_PARSE_ERROR";
    public static final String ERRMSG_JSONDATA_PARSE_ERROR = "解析JSONDATA错误";
    private static final int INT_ERRCODE_JSONDATA_PARSE_ERROR = INT_ANDROID_SYS_ERROR - 3;

    public static final String ERRCODE_NETPROXYBASE_INIT_ERROR = "ANDROID_SYS_NETPROXYBASE_INIT_ERROR";
    public static final String ERRMSG_NETPROXYBASE_INIT_ERROR = "NETProxyBase初始化失败";
    private static final int INT_ERRCODE_NETPROXYBASE_INIT_ERROR = INT_ANDROID_SYS_ERROR - 5;
//


    static HashMap<String, Integer> errCodeMap = new HashMap<String, Integer>();
    static HashMap<String, Integer> sdkErrcodeMap = new HashMap<String, Integer>();
    static HashMap<String, Integer> systemErrCodeMap = new HashMap<String, Integer>();

    static {
        //系统异常情况str errcode 转 int errcode
        systemErrCodeMap.put(ERRCODE_FAIL_SYS_PARAM_MISSING, INT_FAIL_SYS_PARAM_MISSING);
        systemErrCodeMap.put(ERRCODE_FAIL_SYS_PARAM_FORMAT_ERROR, INT_FAIL_SYS_PARAM_FORMAT_ERROR);
        systemErrCodeMap.put(ERRCODE_FAIL_SYS_SYSTEM_BUSY_ERROR, INT_FAIL_SYS_SYSTEM_BUSY_ERROR);

        systemErrCodeMap.put(ERRCODE_FAIL_SYS_SERVICE_NOT_EXIST, INT_FAIL_SYS_SERVICE_NOT_EXIST);

        //NET-SDK错误
        sdkErrcodeMap.put(ERRCODE_NETWORK_ERROR, INT_ERRCODE_NETWORK_ERROR);
        sdkErrcodeMap.put(ERRCODE_JSONDATA_BLANK, INT_ERRCODE_JSONDATA_BLANK);
        sdkErrcodeMap.put(ERRCODE_JSONDATA_PARSE_ERROR, INT_ERRCODE_JSONDATA_PARSE_ERROR);
        sdkErrcodeMap.put(ERRCODE_NETPROXYBASE_INIT_ERROR, INT_ERRCODE_NETPROXYBASE_INIT_ERROR);

        //api4规范化错误码，非系统错误
        errCodeMap.put(ERRCODE_FAIL_SYS_REQUEST_EXPIRED, INT_FAIL_SYS_REQUEST_EXPIRED);
        errCodeMap.put(ERRCODE_FAIL_SYS_SESSION_EXPIRED, INT_FAIL_SYS_SESSION_EXPIRED);

        // 全体异常情况
        errCodeMap.putAll(systemErrCodeMap);
        errCodeMap.putAll(sdkErrcodeMap);
        errCodeMap.put(ERRCODE_SUCCESS, INT_ERRCODE_SUCCESS);
    }

    /**
     * 从返回的String形式的Error，获得ErrorCode
     *
     * @param key 错误的字符串描述
     * @return 返回错误码，见类的常量定义
     */
    public static Integer getIntErrCodeByStrErrorCode(String key) {
        Integer errcode = errCodeMap.get(key);
        if (errcode == null) {
            errcode = INT_UNKNOWN_ERROR;
        }
        return errcode;
    }


    /**
     * 从返回的String形式的Error，获得ErrorCode
     *
     * @param key
     * @return
     */
    public static Integer getNetSdkIntErrCode(String key) {
        Integer errcode = sdkErrcodeMap.get(key);
        if (errcode == null) {
            errcode = INT_UNKNOWN_ERROR;
        }
        return errcode;
    }


    /**
     * 判断系统错误
     *
     * @param key
     * @return
     */
    public static boolean isSystemError(String key) {
        return systemErrCodeMap.containsKey(key);
    }

    /**
     * 判断网络错误
     *
     * @param key
     * @return
     */
    public static boolean isNetworkError(String key) {
        int errCode = ErrorConstant.getNetSdkIntErrCode(key);
        return (ErrorConstant.INT_ERRCODE_NETWORK_ERROR == errCode /*|| ErrorConstant.INT_ERRCODE_NO_NETWORK == errCode*/);
    }



    /**
     * 判断会话是否失效
     *
     * @param key
     * @return
     */
    public static boolean isSessionInvalid(String key) {
        return ErrorConstant.INT_FAIL_SYS_SESSION_EXPIRED == ErrorConstant.getIntErrCodeByStrErrorCode(key);
    }

    /**
     * 判断是否调用成功，retCode返回SUCCESS
     *
     * @param key
     * @return
     */
    public static boolean isSuccess(String key) {
        return ErrorConstant.INT_ERRCODE_SUCCESS == ErrorConstant.getIntErrCodeByStrErrorCode(key);
    }


    /**
     * 判断是否是过期访问，即t过期,服务端返回ErrorConstant.ERRCODE_FAIL_SYS_REQUEST_EXPIRED
     *
     * @param key
     * @return
     */
    public static boolean isExpiredRequest(String key) {
        return ErrorConstant.INT_FAIL_SYS_REQUEST_EXPIRED == ErrorConstant.getIntErrCodeByStrErrorCode(key);
    }

    /**
     * 判断是否是输出系统错误 retCode前缀"FAIL_SYS_"
     *
     * @param key
     * @return
     */
    public static boolean isNetServerError(String key) {
        if (null != key && key.startsWith(FAIL_SYS_PREFIX)) {
            return true;
        }
        return false;
    }
}

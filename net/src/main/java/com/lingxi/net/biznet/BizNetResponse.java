package com.lingxi.net.biznet;

import com.lingxi.net.R;
import com.lingxi.net.NetSDK;
import com.lingxi.net.basenet.NetResponse;

/**
 * 任务请求返回结果模型
 * Created by zhangwei on 2018/3/29.
 */
public class BizNetResponse<T> {

    //需要保证code和服务端兼容，成功的code和服务端一致，其它code不冲突
    public static final int RESULT_CODE_SUC = 0;
    public static final int RESULT_CODE_FAIL = 0x01;
    public static final int RESULT_CODE_NETWORK = 0x02;
    public static final int RESULT_CODE_SESSION_EXPIRED = 0x03;

    public static final int RESULT_CODE_NO_CACHE = 0x10;
    public static final int RESULT_CODE_CACHED = 0x20;
    public static final int RESULT_CODE_CACHE_EXPIRED = 0x30;
    public static final int RESULT_CODE_CACHE_ERROR = 0x40;

    public static String getDefaultFailMessage() {
        int key = NetSDK.getContext().getResources().getIdentifier("system_error", "string", NetSDK.getContext().getPackageName());
        if (NetSDK.getContext() != null && key != 0x0) {
            return NetSDK.getContext().getString(R.string.system_error);
        }
        return "系统错误，请稍后再试";
    }

    public static String getNetWorkFailMessage() {
        int key = NetSDK.getContext().getResources().getIdentifier("network_error", "string", NetSDK.getContext().getPackageName());
        if (NetSDK.getContext() != null && key != 0x0) {
            return NetSDK.getContext().getString(R.string.network_error);
        }
        return "网络错误";
    }

    /**
     * 返回码
     */
    public int resultCode = RESULT_CODE_FAIL;

    /**
     * 服务端返回的错误码
     */
    public int returnCode = -1;


    /**
     * 服务端返回的错误信息
     */
    public String returnMessage = getDefaultFailMessage();

    /**
     * 返回的业务模型
     */
    public T model;

    /**
     * 返回的模型，一般业务端不需要使用
     */
    public NetResponse response;

    public BizNetResponse() {
    }

}

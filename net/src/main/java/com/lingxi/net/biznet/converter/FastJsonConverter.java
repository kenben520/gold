package com.lingxi.net.biznet.converter;

import com.alibaba.fastjson.JSON;
import com.lingxi.net.common.util.NetSdkLog;

/**
 * Created by zhangwei on 2018/3/29.
 */
public class FastJsonConverter implements JsonConverter {

    private static final String TAG = "NetWorker." + FastJsonConverter.class.getSimpleName();
    private static FastJsonConverter instance;

    private FastJsonConverter() {

    }

    public static FastJsonConverter getInstance() {
        if (instance == null)
            instance = new FastJsonConverter();
        return instance;
    }

    @Override
    public <T> T parseJson(String json, Class<T> clz) {
        try {
            return JSON.parseObject(json, clz);
        } catch (Exception e) {
            NetSdkLog.e(TAG, "parsejsonerror:",e);
            return null;
        }
    }

    @Override
    public String toJson(Object object) {
        return JSON.toJSONString(object);
    }
}

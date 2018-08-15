package com.lingxi.net.cache;

import android.text.TextUtils;

import com.lingxi.net.basenet.NetResponse;
import com.lingxi.net.biznet.BizNetRequest;
import com.lingxi.net.biznet.BizNetResponse;
import com.lingxi.net.common.util.NetSdkLog;


public class NetCacheWrapper {

    private static final String TAG = NetCacheWrapper.class.getSimpleName();
    private static NetCacheWrapper sInstance;
    private NetCache netCache;

    private NetCacheWrapper(NetCache cache) {
        netCache = cache;
    }

    public static NetCacheWrapper getInstance(NetCache netCache) {
        if (sInstance == null) {
            sInstance = new NetCacheWrapper(netCache);
        }
        return sInstance;
    }

    /**
     * 保存结果到缓存
     *
     * @param request
     * @param response
     */
    public void saveCache(BizNetRequest request, BizNetResponse response) {
        if (response.response == null || response.response.getBytedata() == null) {
            return;
        }
        if (request == null || TextUtils.isEmpty(request.cacheKey)) {
            return;
        }

        try {
            byte[] data = response.response.getBytedata();
            String cacheValue = new String(data);
            NetSdkLog.d(TAG, "save key:" + request.cacheKey + ", value:" + cacheValue);
            netCache.put(request.cacheKey, cacheValue, false);
        } catch (Exception e) {
            NetSdkLog.e(TAG, e.getMessage());
        }
    }

    /**
     * 获取缓存
     *
     * @param request
     * @return
     */
    public BizNetResponse getCache(BizNetRequest request) {
        BizNetResponse response = new BizNetResponse();
        response.resultCode = BizNetResponse.RESULT_CODE_NO_CACHE;

        if (request == null || TextUtils.isEmpty(request.cacheKey)) {
            return response;
        }

        try {
            String cacheValue = netCache.get(request.cacheKey, false);
            NetSdkLog.d(TAG, "get key:" + request.cacheKey + ", value:" + cacheValue);
            if (TextUtils.isEmpty(cacheValue)) {
                return response;
            }
            NetResponse netResponse = new NetResponse();
            netResponse.setBytedata(cacheValue.getBytes());
            netResponse.parseJsonByte();
            response.response = netResponse;
            response.resultCode = BizNetResponse.RESULT_CODE_CACHED;
        } catch (Exception e) {
            NetSdkLog.e(TAG, e.getMessage());
            response.resultCode = BizNetResponse.RESULT_CODE_CACHE_ERROR;
        }
        return response;
    }
}

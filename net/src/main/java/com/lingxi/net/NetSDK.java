package com.lingxi.net;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lingxi.net.biznet.converter.FastJsonConverter;
import com.lingxi.net.biznet.converter.JsonConverter;
import com.lingxi.net.cache.NetCache;
import com.lingxi.net.cache.NetCacheWrapper;
import com.lingxi.net.cache.NetDefaultCache;

/**
 * Created by zhangwei on 2018/3/29.
 */
public class NetSDK {
    private static Context context;
    private static JsonConverter jsonConverter;
    private static NetCache cache;
    private static NetCacheWrapper cacheWrapper;

    public static void init(Context ctx, JsonConverter jc) {
        if (ctx == null)
            throw new RuntimeException("param can not be null");
        context = ctx;
        jsonConverter = jc;
        if (jsonConverter == null)
            jsonConverter = FastJsonConverter.getInstance();
        if (cache == null)
            cache = NetDefaultCache.getNetDefaultCache(context);
        cacheWrapper = NetCacheWrapper.getInstance(NetSDK.getCache());
    }

    public static Context getContext() {
        return context;
    }

    public static JsonConverter getJsonConverter() {
        if (jsonConverter == null)
            throw new RuntimeException("NetSDK not init.");
        return jsonConverter;
    }

    public static NetCacheWrapper getNetCacheWrapper() {
        return cacheWrapper;
    }

    public static NetCache getCache() {
        if (cache == null)
            throw new RuntimeException("NetSDK not init.");
        return cache;
    }
}

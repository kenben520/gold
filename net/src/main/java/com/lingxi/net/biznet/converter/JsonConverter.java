package com.lingxi.net.biznet.converter;

/**
 * Created by zhangwei on 2018/3/29.
 */
public interface JsonConverter {

    <T> T parseJson(String json, Class<T> clz);
    String toJson(Object object);
}

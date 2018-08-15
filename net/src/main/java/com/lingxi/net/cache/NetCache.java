package com.lingxi.net.cache;

public interface NetCache {

    String get(String key, boolean needEncrypt);

    String put(String key, String value, boolean needEncrypt);

    String remove(String key);

    long getCachedTime(String key);

    void updateCachedTime(String key, long value);

}

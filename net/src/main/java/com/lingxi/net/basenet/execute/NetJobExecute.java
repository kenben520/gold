package com.lingxi.net.basenet.execute;

import com.lingxi.net.basenet.NetBuilder;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by zhangwei on 2018/3/29.
 */
public interface NetJobExecute {
    /**
     * 异步网络调用
     *
     * @param proxy
     * @param urlParams
     * @return
     */
    void asyncExecute(NetBuilder proxy, Map<String, String> urlParams);

    void uploadImage(NetBuilder proxy, Map<String, String> urlParams);
}

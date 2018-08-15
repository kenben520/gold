package com.lingxi.net;

import com.lingxi.net.biznet.BizNetAsyncTask;
import com.lingxi.net.biznet.BizNetRequest;
import com.lingxi.net.cache.NetCacheWrapper;

/**
 * Created by zhangwei on 2018/3/29.
 *  * 功能：
 * 1，发送异步任务，取消之前类型相同的任务
 * 2，发送同步任务
 */
public class NetWorker {

    private static final String TAG = NetWorker.class.getSimpleName();
    private NetCacheWrapper cacheWrapper;
    public NetWorker() {
    }



    /**
     * 发起异步请求，主线程调用
     */
    public void asyncRequest(BizNetRequest request) {
        BizNetAsyncTask task = new BizNetAsyncTask(request);
        task.asyncRequest();
    }

    /**
     * 发起同步请求
     */
    public void syncRequest(BizNetRequest request) {
    }

}

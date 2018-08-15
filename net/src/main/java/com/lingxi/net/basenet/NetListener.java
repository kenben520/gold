package com.lingxi.net.basenet;

/**
 * Created by zhangwei on 2018/3/29.
 */
public interface NetListener {
    /**
     * 异步任务结果成功返回
     */
    void onResponse(NetResponse netResponse);

    /**
     * 异步任务结果失败返回
     */
    void onFail(NetResponse netResponse);
}

package com.lingxi.net.biznet;

/**
 * 异步任务回调接口
 * Created by zhangwei on 2018/3/29.
 */
public interface BizNetListener<T> {

    /**
     * 任务执行前回调
     */
    void onPreExecute();

    /**
     * 缓存是否命中回调，缓存请求会回调
     *
     * @param hit
     * @param response
     */
    void hitCache(boolean hit, BizNetResponse<T> response);

    /**
     * 异步任务结果成功返回
     *
     * @param response
     */
    void onSuccess(BizNetResponse<T> response);

    /**
     * 异步任务结果失败返回
     *
     * @param response
     */
    void onFail(BizNetResponse<T> response);
}

package com.lingxi.biz.base;

/**
 * Created by zhangwei on 2018/4/4.
 */
public interface BizResultListener<Model> {

    /**
     * 任务执行前回调
     */
    void onPreExecute();

    /**
     * 缓存是否命中回调，缓存请求会回调
     *
     * @param hit
     * @param model
     */
    void hitCache(boolean hit, Model model);

    /**
     * 异步任务结果成功返回
     *
     * @param model
     */
    void onSuccess(Model model);

    /**
     * 异步任务结果失败返回
     */
    void onFail(int resultCode, int bizCode, String bizMessage);
}
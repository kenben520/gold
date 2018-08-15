package com.lingxi.net.biznet;

import com.lingxi.net.basenet.INetDataObject;
import com.lingxi.net.basenet.MethodEnum;
import com.lingxi.net.basenet.PlatformEnum;

/**
 * Created by zhangwei on 2018/3/29.
 */
public class BizNetRequest<T> {

    /**
     * 请求类型，用于区分是否是同一种请求，做下一次请求是需要取消之前同类型的请求任务
     */
    public int type;

    /**
     * 服务端业务请求模型，NetRequest 或 INetDataObject 类型
     */
    public Object request;

    /**
     * 是否走https
     */
    public boolean needHttps = false;


    /**
     * 服务端返回结果模型
     */
    public Class<T> clz;

    /**
     * 异步任务回调
     */
    public BizNetListener<T> listener;


    /**
     * Method类型(GET、POST)
     */
    public MethodEnum method = MethodEnum.GET;

    /**
     * 请求的服务器平台
     */
    public PlatformEnum platform =PlatformEnum.SELF_SERVER;

    public boolean needCache;
    public String cacheKey;

    public BizNetRequest(INetDataObject request, Class<T> clz, int type, BizNetListener<T> listener) {
        this.request = request;
        this.clz = clz;
        this.listener = listener;
        this.type = type;
    }

    public BizNetRequest(INetDataObject request, Class<T> clz, BizNetListener<T> listener) {
        this.request = request;
        this.clz = clz;
        this.listener = listener;
    }


    public BizNetRequest() {
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setRequest(INetDataObject request) {
        this.request = request;
    }

    public void setClz(Class<T> clz) {
        this.clz = clz;
    }


    public void setListener(BizNetListener<T> listener) {
        this.listener = listener;
    }

}

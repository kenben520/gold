package com.lingxi.biz.base;

import com.lingxi.net.basenet.INetDataObject;

/**
 * Created by zhangwei on 2018/4/3.
 */
public class BaseResponse<T> implements INetDataObject {
    // 返回码
    public int code;
    // 返回说明
    public String message;
    // 返回结果集
    public T data;

    public BaseResponse(T t) {
        this.data = t;
    }

    public BaseResponse() {
    }
}

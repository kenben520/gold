package com.lingxi.biz.base;

import com.lingxi.net.biznet.BizNetListener;
import com.lingxi.net.biznet.BizNetResponse;
/**
 * Created by zhangwei on 2018/4/4.
 */
public class BizNetDefaultListener<T extends BaseResponse<V>, V> implements BizNetListener<T> {
    private BizResultListener<V> callbackToPresenter;

    public BizNetDefaultListener(final BizResultListener<V> callbackToPresenter) {
        this.callbackToPresenter = callbackToPresenter;
    }

    @Override
    public void onPreExecute() {
        if (callbackToPresenter != null) {
            callbackToPresenter.onPreExecute();
        }
    }

    @Override
    public void hitCache(boolean hit, BizNetResponse<T> response) {
        if (callbackToPresenter != null && response != null && response.model != null && response.model.data != null) {
            callbackToPresenter.hitCache(hit, response.model.data);
        }
    }

    @Override
    public void onSuccess(BizNetResponse<T> response) {
        if (callbackToPresenter != null) {
            callbackToPresenter.onSuccess(response.model.data);
        }
    }

    @Override
    public void onFail(BizNetResponse<T> response) {
        if (callbackToPresenter != null) {
            callbackToPresenter.onFail(response.resultCode, response.returnCode, response
                    .returnMessage);
        }
    }
}

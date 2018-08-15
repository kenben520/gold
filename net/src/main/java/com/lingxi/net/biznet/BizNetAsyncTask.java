package com.lingxi.net.biznet;

import com.lingxi.net.NetSDK;
import com.lingxi.net.basenet.INetDataObject;
import com.lingxi.net.basenet.NetBuilder;
import com.lingxi.net.basenet.NetListener;
import com.lingxi.net.basenet.NetRequest;
import com.lingxi.net.basenet.NetResponse;
import com.lingxi.net.basenet.ProtocolEnum;
import com.lingxi.net.biznet.converter.JsonConverter;
import com.lingxi.net.cache.NetCacheWrapper;
import com.lingxi.net.common.util.NetSdkLog;
import com.lingxi.net.common.util.NetworkUtil;

/**
 * Created by zhangwei on 2018/3/29.
 */
public class BizNetAsyncTask implements NetListener {

    private static final String TAG = BizNetAsyncTask.class.getSimpleName();
    private static final String KEY_RESULT_CODE = "code";
    private static final String KEY_RESULT_MSG = "message";

    private static final String KEY_RESULT_RESULT = "result";

    protected JsonConverter jsonConverter;
    protected BizNetRequest request;
    protected NetCacheWrapper cacheWrapper;

    public BizNetAsyncTask(BizNetRequest request) {
        this.jsonConverter = NetSDK.getJsonConverter();
        this.cacheWrapper = NetSDK.getNetCacheWrapper();
        this.request = request;
    }

    public void asyncRequest() {
        if (request.listener != null)
            request.listener.onPreExecute();
        BizNetResponse result = new BizNetResponse();
//        check cache
        if (request.needCache) {
            NetSdkLog.d(TAG, getType() + ", " + this + " check cache");
            BizNetResponse cacheResult = cacheWrapper.getCache(request);
            boolean hit = cacheResult.resultCode == BizNetResponse.RESULT_CODE_CACHED;
            if (hit) {
                cacheResult.model = jsonConverter.parseJson(cacheResult.response.getDataJsonObject().toString(), request.clz);
                if (null == cacheResult.model) {
                    hit = false;
                }
            }
            if (hit) {
                NetSdkLog.d(TAG, getType() + ", " + this + " hit return" + getType());
                onPostExecute(cacheResult);
            }
        }

        //check network
        if (!NetworkUtil.networkStatusOK(NetSDK.getContext())) {
            result.resultCode = BizNetResponse.RESULT_CODE_NETWORK;
            result.returnMessage = BizNetResponse.getNetWorkFailMessage();
            onPostExecute(result);
            return;
        }
        //network
        NetSdkLog.d(TAG, getType() + ", " + this + " network");
        NetBuilder builder;
        if (request.request instanceof NetRequest)
            builder = new NetBuilder((NetRequest) request.request);
        else
            builder = new NetBuilder((INetDataObject) request.request);//一般走这里
        NetSdkLog.d(TAG, getType() + ", " + this + ",apiName=" + builder.request.getApiUniqueKey());
        if (request.needHttps) builder.protocol(ProtocolEnum.HTTPSECURE);
        if (request.method != null) builder.reqMethod(request.method);
        builder.setPlatform(request.platform);
        // timeOut
        builder.setSocketTimeoutMilliSecond(20000);
        builder.addListener(this);
        builder.asyncRequest();
    }

    @Override
    public void onResponse(NetResponse netResponse) {
        onPostExecute(getBizNetResponse(netResponse));
    }

    @Override
    public void onFail(NetResponse netResponse) {
        onPostExecute(getBizNetResponse(netResponse));
    }

    private BizNetResponse getBizNetResponse(NetResponse netResponse) {
        BizNetResponse result = new BizNetResponse();
        result.response = netResponse;
        result.resultCode = BizNetResponse.RESULT_CODE_FAIL;
        if (result.response.getDataJsonObject() == null) {

        } else {
            int defaultCode = BizNetResponse.RESULT_CODE_FAIL;
            // 不同服务器 错误信息判断
            switch (netResponse.getPlatform()) {
                case SELF_SERVER:
                    result.resultCode = result.returnCode = result.response.getDataJsonObject().optInt(KEY_RESULT_CODE, defaultCode);
                    result.returnMessage = result.response.getDataJsonObject().optString(KEY_RESULT_MSG, BizNetResponse.getDefaultFailMessage());
                    break;
                case LEAN_WORK_SERVER:
                    boolean resultBool = result.response.getDataJsonObject().optBoolean(KEY_RESULT_RESULT, false);
                    result.resultCode = result.returnCode = resultBool ? BizNetResponse.RESULT_CODE_SUC : BizNetResponse.RESULT_CODE_FAIL;
                    result.returnMessage = resultBool ? "" : result.response.getDataJsonObject().optString("msg", BizNetResponse.getDefaultFailMessage());
                    break;
            }
            NetSdkLog.d(TAG, getType() + ", " + this + " result.resultCode=" + result.resultCode);
            NetSdkLog.d(TAG, getType() + ", " + this + " result.returnMessage=" + result.returnMessage);
            if (request.clz != null) {
                if (result.resultCode == BizNetResponse.RESULT_CODE_SUC) {
                    result.model = jsonConverter.parseJson(result.response.getDataJsonObject().toString(), request.clz);
                }
                long time = System.currentTimeMillis();
                if (null == result.model) {
                    result.resultCode = BizNetResponse.RESULT_CODE_FAIL;
                }
                NetSdkLog.d(TAG, "valid cost time: " + (System.currentTimeMillis() - time));
            }
        }

        if (request.needCache && result.resultCode == BizNetResponse.RESULT_CODE_SUC) {
            NetSdkLog.d(TAG, getType() + ", " + this + " syncRequest saveCache");
            cacheWrapper.saveCache(request, result);
        }

        return result;
    }

    public int getType() {
        return request.type;
    }

    public void publishProgress(BizNetResponse... values) {
        NetSdkLog.d(TAG, getType() + ", " + this + " onProgressUpdate");
        if (request.listener != null) {
            boolean hit = values[0].resultCode == BizNetResponse.RESULT_CODE_CACHED;
            request.listener.hitCache(hit, values[0]);
        }
    }

    protected void onPostExecute(BizNetResponse response) {
        NetSdkLog.d(TAG, getType() + ", " + this + " onPostExecute");
        if (request.listener != null) {
            if (response.resultCode == BizNetResponse.RESULT_CODE_SUC) {
                request.listener.onSuccess(response);
            } else if (response.resultCode == BizNetResponse.RESULT_CODE_FAIL) {
                request.listener.onFail(response);
            } else if (response.resultCode == BizNetResponse.RESULT_CODE_CACHED) {
                request.listener.hitCache(true, response);
            } else if (response.resultCode == BizNetResponse.RESULT_CODE_SESSION_EXPIRED) {
                request.listener.onFail(response);
            } else {
                request.listener.onFail(response);
            }
        }
    }
}

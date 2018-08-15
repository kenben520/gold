package com.lingxi.net.basenet;

import com.lingxi.net.common.util.NetSdkLog;
import com.lingxi.net.basenet.NetResponse;
import com.lingxi.net.basenet.ErrorConstant;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by zhangwei on 2018/3/29.
 */
public class NetworkResultParser {

    private static final String TAG = "basenet.NetworkResultParser";

    public static class ParseParameter {
        public int responseCode;
        public Map<String, List<String>> header;
        public byte[] bytedata;

        public ParseParameter(int responseCode, Map<String, List<String>> header, byte[] bytedata) {
            this.responseCode = responseCode;
            this.header = header;
            this.bytedata = bytedata;
        }
    }

    /**
     * <!--SDK内部使用-->
     * 将networkRlt转换成NetResponse
     * @param response
     * @param paramter
     * @return
     */
    public static NetResponse parseNetworkRlt(NetResponse response, ParseParameter paramter) {
        if (response == null) {
            response = new NetResponse();
        }
        if (paramter == null) {
            response.setRetCode(ErrorConstant.ERRCODE_NETWORK_ERROR);
            response.setRetMsg(ErrorConstant.ERRMSG_NETWORK_ERROR);
            return response;
        }

        //处理Response Code和Response header
        int responseCode = paramter.responseCode;
        Map<String, List<String>> header = paramter.header;
        response.setResponseCode(responseCode);
        response.setHeaderFields(header);
        response.setBytedata(paramter.bytedata);

        //判断statusCode
        if (responseCode < 0) {
            response.setRetCode(ErrorConstant.ERRCODE_NETWORK_ERROR);
            response.setRetMsg(ErrorConstant.ERRMSG_NETWORK_ERROR);
            return response;
        }


        //handle bytedata为空的场景
        if (null == response.getBytedata()) {
            response.setRetCode(ErrorConstant.ERRCODE_JSONDATA_BLANK);
            response.setRetMsg(ErrorConstant.ERRMSG_JSONDATA_BLANK);
            return response;
        }

        response.parseJsonByte();
        return response;
    }

    /**
     * NetJobExecute syncTransform 方法调用
     * @param result
     * @return
     */
    public static NetResponse parseNetworkRlt(final Response<ResponseBody> result) {
        if (result == null || result.body() == null) {
            NetResponse response = new NetResponse(ErrorConstant.ERRCODE_NETWORK_ERROR, ErrorConstant.ERRMSG_NETWORK_ERROR);
            return response;
        }

        byte[] bytes = null;
        try {
            bytes = result.body().bytes();
        } catch (IOException e) {
            NetSdkLog.d(TAG, "test");
        }

        return parseNetworkRlt(null,
                new ParseParameter(result.code(), result.headers().toMultimap(), bytes));
    }
}

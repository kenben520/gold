package com.lingxi.net.basenet;


import com.lingxi.net.common.util.NetSdkLog;
import com.lingxi.net.common.util.StringUtils;
import com.lingxi.net.common.util.NetSdkLog.LogEnable;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangwei on 2018/3/29.
 */
public class NetResponse implements Serializable {

    private static final long serialVersionUID = 1566423746968673499L;

    private static final String TAG = "basenet.NetResponse";

    private volatile boolean bParsed = false;

    /**
     * API 返回码
     */
    private String retCode;

    /**
     * API 返回信息
     */
    private String retMsg;


    /**
     * api 名字
     */
    private String api;

    /**
     * api 版本
     */
    private String v;

    /**
     * api data
     * JSONObject
     */
    private JSONObject dataJsonObject;

    /**
     * 网络层返回的原始byte[]
     */
    private byte[] bytedata;

    /**
     * 请求的服务器平台
     */
    private PlatformEnum platform=PlatformEnum.SELF_SERVER;

    /**
     * 响应头信息字段
     */
    private Map<String, List<String>> headerFields;

    /**
     * http 响应码
     */
    private int responseCode;

    private ResponseSource responseSource = ResponseSource.NETWORK_REQUEST;

    public NetResponse() {
        super();
    }

    /**
     * 判断会话是否失效
     *
     * @return
     */
    public boolean isSessionInvalid() {
        return ErrorConstant.isSessionInvalid(getRetCode());
    }

    public NetResponse(String retCode, String retMsg) {
        super();
        this.retCode = retCode;
        this.retMsg = retMsg;
    }


    public NetResponse(String api, String v, String retCode, String retMsg) {
        super();
        this.api = api;
        this.v = v;
        this.retCode = retCode;
        this.retMsg = retMsg;
    }

    /**
     * @return the retCode
     */
    public String getRetCode() {
        return retCode;
    }

    /**
     * @param retCode the retCode to set
     */
    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    /**
     * @return the retMsg
     */
    public String getRetMsg() {
        if (retMsg == null && !bParsed) {
            parseJsonByte();
        }
        return retMsg;
    }

    /**
     * @param retMsg the retMsg to set
     */
    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    /**
     * @return the api
     */
    public String getApi() {
        if (api == null && !bParsed) {
            parseJsonByte();
        }
        return api;
    }

    /**
     * @param api the api to set
     */
    public void setApi(String api) {
        this.api = api;
    }

    /**
     * @return the v
     */
    public String getV() {
        if (v == null && !bParsed) {
            parseJsonByte();
        }
        return v;
    }

    /**
     * @param v the v to set
     */
    public void setV(String v) {
        this.v = v;
    }

    public JSONObject getDataJsonObject() {
        if (dataJsonObject == null && !bParsed) {
            parseJsonByte();
        }
        return dataJsonObject;
    }

    public void setDataJsonObject(JSONObject dataJsonObject) {
        this.dataJsonObject = dataJsonObject;
    }

    /**
     * @return the bytedata
     */
    public byte[] getBytedata() {
        return bytedata;
    }

    /**
     * @param bytedata the bytedata to set
     */
    public void setBytedata(byte[] bytedata) {
        this.bytedata = bytedata;
    }

    /**
     * @return the headerFields
     */
    public Map<String, List<String>> getHeaderFields() {
        return headerFields;
    }

    /**
     * @param headerFields the headerFields to set
     */
    public void setHeaderFields(Map<String, List<String>> headerFields) {
        this.headerFields = headerFields;
    }


    public int getResponseCode() {
        return responseCode;
    }


    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public void parseJsonByte() {
        if (bParsed) {
            return;
        }

        synchronized (this) {
            if (bParsed) {
                return;
            }
            if (bytedata == null || bytedata.length == 0) {
                if (NetSdkLog.isLogEnable(LogEnable.WarnEnable)) {
                    NetSdkLog.w(TAG, "[parseJsonByte]bytedata is blank ---api=" + api + ",v=" + v);
                }
                retCode = ErrorConstant.ERRCODE_JSONDATA_BLANK;
                retMsg = ErrorConstant.ERRMSG_JSONDATA_BLANK;
                bParsed = true;
                return;
            }

            try {
                String jsonStr = new String(bytedata);
                if (NetSdkLog.isLogEnable(LogEnable.DebugEnable)) {
                    NetSdkLog.d(TAG, "[parseJsonByte]response : " + jsonStr);
                }

                dataJsonObject = new JSONObject(jsonStr);
            } catch (Throwable e) {
                retCode = ErrorConstant.ERRCODE_JSONDATA_PARSE_ERROR;
                retMsg = ErrorConstant.ERRMSG_JSONDATA_PARSE_ERROR;
            } finally {
                bParsed = true;
            }
        }
    }

    public enum ResponseSource {
        FRESH_CACHE,
        EXPIRED_CACHE,
        NETWORK_REQUEST
    }

    public void setSource(ResponseSource source) {
        this.responseSource = source;
    }

    public ResponseSource getSource() {
        return responseSource;
    }


    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("NetResponse");
        try {
            str.append("[retCode=").append(retCode);
            str.append(",retMsg=").append(retMsg);
//            str.append(",ret=").append(Arrays.toString(ret));
            str.append(",dataJsonObject=").append(dataJsonObject);
            str.append(",bytedata=").append((bytedata == null) ? null : new String(bytedata));
            str.append("]");
            return str.toString();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return super.toString();
    }

    public String getFullKey() {
        if (StringUtils.isBlank(api) || StringUtils.isBlank(v)) {
            return null;
        }
        return StringUtils.concatStr2LowerCase(api, v);
    }

    /**
     * 网络请求成功的前提下，API调用的返回值是否成功
     */
    public boolean isApiSuccess() {
        return ErrorConstant.isSuccess(getRetCode()) && null != getBytedata();
    }


    /**
     * 判断请求失效
     *
     * @return
     */
    public boolean isExpiredRequest() {
        return ErrorConstant.isExpiredRequest(getRetCode());
    }


    /**
     * 判断系统错误
     *
     * @return
     */
    public boolean isSystemError() {
        return ErrorConstant.isSystemError(getRetCode());
    }

    /**
     * 判断网络错误，包括网络错误和无网络
     *
     * @return
     */
    public boolean isNetworkError() {
        return ErrorConstant.isNetworkError(getRetCode());
    }

    /**
     * 判断是否是Server输出的系统错误 retCode前缀"FAIL_SYS_"
     *
     * @return
     */
    public boolean isNetServerError() {
        return ErrorConstant.isNetServerError(getRetCode());
    }

    public PlatformEnum getPlatform() {
        return platform;
    }

    public void setPlatform(PlatformEnum platform) {
        this.platform = platform;
    }
}

package com.lingxi.net.basenet;


import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.lingxi.net.NetSDK;
import com.lingxi.net.basenet.execute.NetJobExecute;
import com.lingxi.net.basenet.execute.NetJobExecuteImpl;
import com.lingxi.net.basenet.util.RequestConverter;
import com.lingxi.net.basenet.util.SPUtil;
import com.lingxi.net.basenet.util.SecurityUtils;
import com.lingxi.net.common.util.NetSdkLog;
import com.lingxi.net.common.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by zhangwei on 2018/3/29.
 */
public class NetBuilder {

    private static final String TAG = "netsdk.NetBuilder";

    /**
     * 请求对象
     */

    public NetRequest request;

    /**
     * 请求上下文
     */
    public Object requestContext = null;

    /**
     * 请求参数属性对象
     */
    public NetworkExtraProp networkExtraProp = new NetworkExtraProp();


    /**
     * 网络层请求transform
     */
    private NetJobExecute transformer = new NetJobExecuteImpl();


    /**
     * listener
     */
    public NetListener listener = null;

    /**
     * 完整的URL
     */
    private String fullBaseUrl = null;

    /**
     * 自定义 HOST DOMAIN
     */
    private String customDomain = null;

    /**
     * <默认构造函数>
     *
     * @param //请求业务数据传输对象
     */
    public NetBuilder(INetDataObject netData) {
        super();
        this.request = RequestConverter.convertBizRequestToNetRequest(netData);
    }


    /**
     * <默认构造函数>
     *
     * @param request 请求业务数据传输对象
     */
    public NetBuilder(NetRequest request) {
        super();
        this.request = request;
    }

    /**
     * 自定义请求上下文
     */
    public NetBuilder reqContext(Object requestContext) {
        this.requestContext = requestContext;
        return this;
    }

    /**
     * 获取自定义请求上下文
     */
    public Object getReqContext() {
        return this.requestContext;
    }

    /**
     * 设置请求头
     */
    public NetBuilder headers(Map<String, String> requestHeaders) {
        if ((requestHeaders != null) && (!requestHeaders.isEmpty())) {
            if (this.networkExtraProp.getRequestHeaders() != null) {
                this.networkExtraProp.getRequestHeaders().putAll(requestHeaders);
            } else {
                this.networkExtraProp.setRequestHeaders(requestHeaders);
            }
        }
        return this;
    }

    /**
     * 设置请求头：cache-control=no-cache
     * 注意：发送GET请求时可以调用此方法设置请求头；发送POST请求时不建议调用此方法设置请求头；
     */
//    public NetBuilder setCacheControlNoCache() {
//        Map<String, String> requestHeaders = this.networkExtraProp.getRequestHeaders();
//        if (requestHeaders == null) {
//            requestHeaders = new HashMap<String, String>();
//        }
//        requestHeaders.put(HttpHeaderConstant.CACHE_CONTROL, HttpHeaderConstant.NO_CACHE);
//        this.networkExtraProp.setRequestHeaders(requestHeaders);
//        return this;
//    }

    /**
     * 设置请求协议
     */
    public NetBuilder protocol(ProtocolEnum protocol) {
        if (protocol != null) {
            this.networkExtraProp.setProtocol(protocol);
        }
        return this;
    }

    /**
     * 设置请求完整的URL
     */
    public NetBuilder fullBaseUrl(String fullBaseUrl) {
        if (fullBaseUrl != null) {
            this.fullBaseUrl = fullBaseUrl;
        }
        return this;
    }

    /**
     * 设置请求服务器平台domain
     */
    public NetBuilder setPlatform(PlatformEnum platform) {
        if (platform != null) {
            this.networkExtraProp.setPlatform(platform);
        }
        return this;
    }

    /**
     * 获取平台api domain
     */
    public PlatformEnum getPlatform() {
        return this.networkExtraProp.getPlatform();
    }

    public String getFullBaseUrl() {
        return request.getBaseUrl();
    }

    public void setFullBaseUrl(String url) {
        request.setBaseUrl(url);
    }


    public String getUrl() {
        return request.getUrl();
    }

    public void setUrl(String url) {
        request.setUrl(url);
    }

    /**
     * 设置请求定制域名
     *
     * @param customDomain
     * @return
     */
    public NetBuilder setCustomDomain(String customDomain) {
        if (customDomain != null) {
            this.customDomain = customDomain;
        }
        return this;
    }

    /**
     * 设置listener
     *
     * @param listener
     */
    public NetBuilder addListener(NetListener listener) {
        this.listener = listener;
        return this;
    }

    public NetworkExtraProp getProperty() {
        return networkExtraProp;
    }

    /**
     * 请求URL的QueryStr添加键值对
     *
     * @param key
     * @param value
     * @return
     */
    public NetBuilder addHttpQueryParameter(String key, String value) {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(value)) {
            if (NetSdkLog.isLogEnable(NetSdkLog.LogEnable.DebugEnable)) {
                NetSdkLog.d(TAG, "[addHttpQueryParameter]add HttpQueryParameter error,key=" + key + ",value=" + value);
            }
            return this;
        }

        if (this.networkExtraProp.queryParameterMap == null) {
            this.networkExtraProp.queryParameterMap = new HashMap<String, String>();
        }
        this.networkExtraProp.queryParameterMap.put(key, value);
        return this;
    }

    /**
     * 设置请求发送的方法，目前暂时支持GET或POST方法。
     */
    public NetBuilder reqMethod(MethodEnum method) {
        if (null != method) {
            this.networkExtraProp.setMethod(method);
        }
        return this;
    }

    /**
     * 设置连接超时时间，单位millisecond
     *
     * @param connTimeout
     * @return
     */
    public NetBuilder setConnectionTimeoutMilliSecond(int connTimeout) {
        if (connTimeout > 0) {
            this.networkExtraProp.connTimeout = connTimeout;
        }

        return this;
    }

    /**
     * 设置读超时时间，单位millisecond
     *
     * @param socketTimeout
     * @return
     */
    public NetBuilder setSocketTimeoutMilliSecond(int socketTimeout) {
        if (socketTimeout > 0) {
            this.networkExtraProp.socketTimeout = socketTimeout;
        }
        return this;
    }

    /**
     * 发起异步请求。
     */
    public void asyncRequest() {
        //protocol param builder
        Map<String, String> paramReaders = buildParams();
        //send network request
        if (!paramReaders.containsKey("upload")) {
            transformer.asyncExecute(this, paramReaders);
        } else {
            paramReaders.remove("upload");
            transformer.uploadImage(this, paramReaders);
        }
    }

    public Map<String, String> buildParams() {
        Map<String, String> params = new HashMap<>();
        PlatformEnum platform = getPlatform();
        Map<String, String> dataParams = request.dataParams;
        for (String key : dataParams.keySet()) {
            params.put(key, dataParams.get(key));
        }
        switch (platform) {
            case SELF_SERVER:
                params.putAll(buildGlobalParams());
                setFullBaseUrl(NetConstant.Companion.getBASE_URL());
                setUrl("index.php");
                if (request.dataParams == null) {
                    throw new IllegalArgumentException("lack of params");
                }
                if (!request.dataParams.containsKey("app") || !request.dataParams.containsKey("act")) {
                    throw new IllegalArgumentException("lack of params app or act");
                }
                String app = request.dataParams.get("app");
                String act = request.dataParams.get("act");
                String apiToken = getApiToken(app, act);
                params.put("api_token", apiToken);
                Map<String, String> nullHeaderMap = new HashMap<String, String>();
                this.networkExtraProp.setRequestHeaders(nullHeaderMap);
                break;
            case LEAN_WORK_SERVER:
                // TODO: 2018/5/17 替换回自身服务器后需删除掉
                params.put("vendor", "mt4");//交易平台名称
                setFullBaseUrl(NetConstant.Companion.getBASE_URL_LEAN_WORK());
                params.remove("url");
                dataParams.remove("url");
                Map<String, String> headerParams = buildHeaderParams();
                headers(headerParams);
                request.dataParams = null;
                break;
            default:
                new IllegalArgumentException("lack of platform");
                break;
        }
        return params;
    }

    /**
     * leanwork header params
     *
     * @return
     */
    private Map<String, String> buildHeaderParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("Content-Type", "application/json");
        params.put("x-api-token", "6Couxh6yvjH4g1UL");
        params.put("x-api-serverId", "80");
        params.put("x-api-tenantId", "T001273");
        return params;
    }


    /**
     * 组装固定的全局协议参数
     *
     * @return
     */
    private Map<String, String> buildGlobalParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("version", NetConstant.Companion.getVERSION());
        params.put("client_id", getDeviceID());
        return params;
    }


    public String getApiToken(String moduleName, String actionName) {
        String client_secret = "4ca2aabd20ae9a5dd774etye0fgkgk71fgf";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = df.format(new Date());
        String apiToken = SecurityUtils.getMd5(moduleName + actionName + dateStr + client_secret);
        return apiToken;
    }

    /**
     * 获取设备ID
     * 设备ID目前生成策略：以ANDROID_ID为基础，在获取失败时以TelephonyManager.getDeviceId()为备选方法，如果再失败，使用UUID的生成策略
     */
    protected static UUID uuid;

    public String getDeviceID() {
        String deviceID = "";
        SharedPreferences preferences = SPUtil.getCommonParamsPreferences();
        final String id = preferences.getString("device_id", null);
        if (id != null) {
            // Use the ids previously computed and stored in the prefs file
            uuid = UUID.fromString(id);
        } else {
            final String androidId = Settings.Secure.getString(NetSDK.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
            // Use the Android ID unless it's broken, in which case fallback on deviceId,
            // unless it's not available, then fallback on a random number which we store
            // to a prefs file
            try {
                if (!"9774d56d682e549c".equals(androidId)) {
                    uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
                } else {
                    final String deviceId = ((TelephonyManager) NetSDK.getContext().getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                    uuid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")) : UUID.randomUUID();
                }
            } catch (UnsupportedEncodingException e) {
//                throw new RuntimeException(e);
                uuid = null;
            }
            // Write the value out to the prefs file
            if (uuid != null) preferences.edit().putString("device_id", uuid.toString()).commit();
        }
        if (uuid != null) deviceID = uuid.toString();
        return deviceID;
    }

}

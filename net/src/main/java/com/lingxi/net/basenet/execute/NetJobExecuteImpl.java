package com.lingxi.net.basenet.execute;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.lingxi.net.basenet.MethodEnum;
import com.lingxi.net.basenet.NetBuilder;
import com.lingxi.net.basenet.NetResponse;
import com.lingxi.net.basenet.NetworkExtraProp;
import com.lingxi.net.basenet.NetworkResultParser;
import com.lingxi.net.basenet.service.NetService;
import com.lingxi.net.common.util.NetSdkLog;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhangwei on 2018/3/29.
 */
public class NetJobExecuteImpl implements NetJobExecute {
    private static final String TAG = "basenet.NetJobExecuteImpl";

    public NetJobExecuteImpl() {

    }

    private static Retrofit retrofit;

    static {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.addNetworkInterceptor(new StethoInterceptor());
        OkHttpClient client = builder.build();
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://www.baidu.com/")
                .client(client)
                .build();
    }

    @Override
    public void asyncExecute(final NetBuilder netBuilder, Map<String, String> urlQueryParams) {
        if (urlQueryParams == null) {
            urlQueryParams = new HashMap<>();
        }
        final NetworkExtraProp prop = netBuilder.getProperty();
        Response<ResponseBody> response = null;
        // 设置 okhttpclient 属性
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(prop.socketTimeout, TimeUnit.SECONDS);//30s 链接超时
        builder.addNetworkInterceptor(new StethoInterceptor());
        OkHttpClient client = builder.build();
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(netBuilder.getFullBaseUrl())
                    .client(client)
                    .build();

            // Create an instance of our NetService API interface.
            NetService netService = retrofit.create(NetService.class);
            MethodEnum method = prop.getMethod();

            //默认采用GET方式
            Call<ResponseBody> call = netService.getRequest(netBuilder.getUrl(), urlQueryParams, netBuilder.networkExtraProp.getRequestHeaders());
            if (MethodEnum.POST == method) {
                Map<String, Object> map = new HashMap<>();
                if (netBuilder.request != null && netBuilder.request.dataParams != null) {
                    Map<String, String> dataParams = netBuilder.request.dataParams;
                    for (String key : dataParams.keySet()) {
                        if ("app".equals(key)) continue;
                        if ("act".equals(key)) continue;
                        urlQueryParams.remove(key);
                        map.put(key, dataParams.get(key));
                    }
                    call = netService.postRequest(netBuilder.getUrl(), map, urlQueryParams, netBuilder.networkExtraProp.getRequestHeaders());
                } else if (netBuilder.request != null && netBuilder.request.getData() != null) {
                    RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), netBuilder.request.getData());
                    call = netService.postRequestWithJsonBody(netBuilder.getUrl(), body, urlQueryParams, netBuilder.networkExtraProp.getRequestHeaders());
                }
            }

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    NetResponse netResponse = NetworkResultParser.parseNetworkRlt(response);
                    netResponse.setPlatform(prop.getPlatform());
                    if (netBuilder != null && netBuilder.listener != null)
                        netBuilder.listener.onResponse(netResponse);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    NetResponse netResponse = NetworkResultParser.parseNetworkRlt(null);
                    netResponse.setPlatform(prop.getPlatform());
                    if (netBuilder != null && netBuilder.listener != null)
                        netBuilder.listener.onFail(netResponse);
                }
            });
        } catch (Exception e) {
            NetResponse netResponse = NetworkResultParser.parseNetworkRlt(null);
            netResponse.setPlatform(prop.getPlatform());
            if (netBuilder != null && netBuilder.listener != null)
                netBuilder.listener.onFail(netResponse);
            NetSdkLog.e(TAG, "Exception: ", e);
        }
    }

    public static MultipartBody.Part prepareFilePart(String partName, String path) {
        File file = new File(path);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("image/png"), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    @Override
    public void uploadImage(final NetBuilder netBuilder, Map<String, String> urlParams) {
        String path = urlParams.get("pictures");
        urlParams.remove("pictures");
        MultipartBody.Part body = prepareFilePart("pictures", path);

//        RequestBody rb =
//                RequestBody.create(MediaType.parse("multipart/form-data"), new File(path));
//        RequestBody requestBody = ProgressHelper.withProgress(rb, new ProgressUIListener() {
//
//            //if you don't need this method, don't override this methd. It isn't an abstract method, just an empty method.
//            @Override
//            public void onUIProgressStart(long totalBytes) {
//                super.onUIProgressStart(totalBytes);
//                NetSdkLog.e(TAG, "onUIProgressStart:" + totalBytes);
////                Toast.makeText(getApplicationContext(), "开始上传：" + totalBytes, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onUIProgressChanged(long numBytes, long totalBytes, float percent, float speed) {
//                NetSdkLog.e(TAG, "=============start===============");
//                NetSdkLog.e(TAG, "numBytes:" + numBytes);
//                NetSdkLog.e(TAG, "totalBytes:" + totalBytes);
//                NetSdkLog.e(TAG, "percent:" + percent);
//                NetSdkLog.e(TAG, "speed:" + speed);
//                NetSdkLog.e(TAG, "============= end ===============");
////                uploadProgress.setProgress((int) (100 * percent));
////                uploadInfo.setText("numBytes:" + numBytes + " bytes" + "\ntotalBytes:" + totalBytes + " bytes" + "\npercent:" + percent * 100 + " %" + "\nspeed:" + speed * 1000 / 1024 / 1024 + "  MB/秒");
//            }
//
//            //if you don't need this method, don't override this methd. It isn't an abstract method, just an empty method.
//            @Override
//            public void onUIProgressFinish() {
//                super.onUIProgressFinish();
//                NetSdkLog.e(TAG, "onUIProgressFinish:");
////                Toast.makeText(getApplicationContext(), "结束上传", Toast.LENGTH_SHORT).show();
//            }
//        });
        // 设置 okhttpclient 属性
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(20000, TimeUnit.SECONDS);//30s 链接超时
        builder.addNetworkInterceptor(new StethoInterceptor());
        OkHttpClient client = builder.build();
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(netBuilder.getFullBaseUrl())
                    .client(client)
                    .build();
            NetService netService = retrofit.create(NetService.class);
            Call<ResponseBody> call = netService.uploadImage(netBuilder.getUrl(), urlParams, body);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    NetResponse netResponse = NetworkResultParser.parseNetworkRlt(response);
                    if (netBuilder != null && netBuilder.listener != null)
                        netBuilder.listener.onResponse(netResponse);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    NetResponse netResponse = NetworkResultParser.parseNetworkRlt(null);
                    if (netBuilder != null && netBuilder.listener != null)
                        netBuilder.listener.onFail(netResponse);
                }
            });
        } catch (Exception e) {
            NetSdkLog.e(TAG, "Exception: ", e);
        }
    }
}

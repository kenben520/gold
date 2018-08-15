package com.lingxi.net.basenet.service;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by zhangwei on 2018/3/29.
 */
public interface NetService {
//    @Headers({ "Content-Type: application/json", "x-api-token: 6Couxh6yvjH4g1UL", "x-api-serverId: 80", "x-api-tenantId: T001273"})
    @GET()
    Call<ResponseBody> getRequest(@Url String url, @QueryMap(encoded = true) Map<String, String> queryMap, @HeaderMap() Map<String, String> headerMap);

    @FormUrlEncoded
    @POST()
    Call<ResponseBody> postRequest(@Url String url, @FieldMap Map<String, Object> fieldMap, @QueryMap(encoded = true) Map<String, String> queryMap, @HeaderMap() Map<String, String> headerMap);

    @POST()
    Call<ResponseBody> postRequestWithJsonBody(@Url String url, @Body RequestBody body, @QueryMap(encoded = true) Map<String, String> queryMap, @HeaderMap() Map<String, String> headerMap);

    @Multipart
    @POST()
    Call<ResponseBody> uploadImage(@Url String url, @QueryMap(encoded = true) Map<String, String> queryMap,
                             @Part MultipartBody.Part imgs);
}

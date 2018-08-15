package com.lingxi.preciousmetal.util.net;

import android.app.Activity;
import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.lingxi.biz.util.ConstantUtil;
import com.lingxi.preciousmetal.util.utilCode.LogUtils;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by qian on 2018/5/28.
 */

public class RequestGet {

    private static final String URL = "https://bridge.api.lwork.com/";
    // 平仓接口
    public static final String closePosition  = URL+"v2/trade/positions/%d?account=2109732023&vendor=mt4&volume=%f";
    // 新建订单
    public static final String addTrade = URL+"v2/trade/orders?account=2109732023&vendor=mt4";
    //更新止盈止损
    public static final String profitLoss = URL + "v2/trade/positions/%d?account=2109732023&vendor=mt4";
    // 持仓历史
  //  public static final String orderHistory = URL + "v2/vendor=mt4&login=2109732023&from=%d&to=%d&offset=%d&top=%d&symbol=%s";
    public static final String orderHistory = URL + "v2/trade/portfolio/history/trades?account=2109732023&vendor=mt4&from=%s&to=%s&sort=closeTime,DESC";
    //取消委托单
    public static final String closeEntrustOrder = URL + "v2/trade/orders/%d?account=2109732023&vendor=mt4";

    public static void requestHttpData(final Context context,String type, String url,Map<String,Object> requestParameter,final HttpResultListener resultListener){
        OkHttpClient.Builder mBuilder = new OkHttpClient.Builder();
        mBuilder.connectTimeout(10, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(10, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(10, TimeUnit.SECONDS);//设置写入超时时间

//        NetStop bean = new NetStop();
//        bean.setStopLoss(105.068000);
//        bean.setTakeProfit(102.068000);
//        String json =  com.alibaba.fastjson.JSONObject.toJSONString(bean);
//        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
//                , json);

//        Map<String,Object> map = new HashMap<>();
//        map.put("takeProfit",1.05500);
//        map.put("stopLoss",1.05300);
//        map.put("orderPrice",1.05400);
//        map.put("orderType","Limit");
//        map.put("orderDirection","Buy");
//        map.put("volume",1);
//        map.put("symbol","EURUSD");
//        Map<String,Object> mapBean = new HashMap<>();
//        mapBean.put("order",map);

        RequestBody requestBody = null;
        if (requestParameter!=null){
            String postJson = com.alibaba.fastjson.JSONObject.toJSONString(requestParameter);
            requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8") , postJson);
        } else {
            requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"),"");
        }
        Request.Builder builder = new Request.Builder()
                    .addHeader("Content-Type","application/json")
                    .addHeader("x-api-token","6Couxh6yvjH4g1UL")
                    .addHeader("X-Api-Serverid","80")
                    .addHeader("x-api-tenantId","T001273").url(url);
        Request request = null;
        if (ConstantUtil.PUT.equals(type)){
            request = builder.put(requestBody).build();
        } else if (ConstantUtil.POST.equals(type)){
            request = builder.post(requestBody).build();
        } else if (ConstantUtil.DELETE.equals(type)){
            request = builder.delete().build();
        } else if (ConstantUtil.GET.equals(type)){
            request = builder.get().build();
        }

        OkHttpClient client = mBuilder.build();
        Call call = client.newCall(request);
        //异步调用并设置回调函数
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Activity a =  (Activity)context;
                a.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        resultListener.onFailure("请求错误");
                    }
                });
                System.out.println("--------------onFailure--------------" + e.toString());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String responseStr = response.body().string();
                LogUtils.i(""+responseStr);
                final com.alibaba.fastjson.JSONObject object = JSON.parseObject(responseStr);
                final boolean result = object.getBoolean("result");
                Activity a = (Activity)context;
                a.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                    if (result){
                        if (object.get("data")==null){
                            resultListener.onSuccess("成功");
                        } else {
                            resultListener.onSuccess(object.get("data").toString());
                        }
                    } else {
                        String message = object.getString("message");
                        resultListener.onFailure(message);
                        ToastUtils.showShort(message);
                    }
                        }
                });
            }
        });
    }

    public static void requestHttpGet(Context context, String url, final HttpResultListener httpResponse){
        OkHttpClient.Builder mBuilder = new OkHttpClient.Builder();
        mBuilder.connectTimeout(10, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(10, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(10, TimeUnit.SECONDS);//设置写入超时时间
//        mBuilder.sslSocketFactory(createSSLSocketFactory());
//        mBuilder.hostnameVerifier(new TrustAllHostnameVerifier());
        //构造Request对象
        //采用建造者模式，链式调用指明进行Get请求,传入Get的请求地址
        String urlStr = String.format("https://bridge.api.lwork.com/v2/trade/portfolio/balance?account=%s&vendor%s","2109732023","mt4");
        Request request = new Request.Builder()
                .addHeader("Content-Type","application/json")
                .addHeader("x-api-token","6Couxh6yvjH4g1UL")
                .addHeader("X-Api-Serverid","80")
                .addHeader("x-api-tenantId","T001273")
                .get().url(urlStr).build();
        OkHttpClient client = mBuilder.build();
        Call call = client.newCall(request);

        //异步调用并设置回调函数
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("--------------onFailure--------------" + e.toString());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String responseStr = response.body().string();
                com.alibaba.fastjson.JSONObject object = JSON.parseObject(responseStr);
                boolean result = object.getBoolean("result");
                if (result){
                    httpResponse.onSuccess(object.get("data").toString());
                //    AccountInfoMo mo =  FastJsonTools.getPerson(responseStr, AccountInfoMo.class);
                  //  LogUtils.i(responseStr+ "kkkk==="+ mo.getAccountName());
                } else {
                    String message = object.getString("message");
                    httpResponse.onFailure(message);
                }
            }
        });
    }

}

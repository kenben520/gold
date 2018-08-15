package com.lingxi.preciousmetal.base;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import android.util.Log;

import com.easefun.polyvsdk.PolyvDevMountInfo;
import com.easefun.polyvsdk.PolyvSDKClient;
import com.easefun.polyvsdk.live.PolyvLiveSDKClient;
import com.easefun.polyvsdk.live.chat.linkmic.module.PolyvLinkMicManager;
import com.lingxi.biz.domain.responseMo.HomeAllResultBean;
import com.lingxi.biz.domain.responseMo.KMarketResultBean;
import com.lingxi.common.base.BaseApplication;
import com.lingxi.common.util.StringUtil;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.domain.DataServer;
import com.lingxi.preciousmetal.domain.KOperationNoteBean;
import com.lingxi.preciousmetal.domain.SignalParamValues;
import com.lingxi.preciousmetal.ui.activity.kefu.KefuHelper;
import com.lingxi.preciousmetal.ui.jpush.MyPolyvChatManager;
import com.lingxi.preciousmetal.ui.widget.CommonHeader;
import com.lingxi.preciousmetal.util.DynamicTimeFormat;
import com.lingxi.preciousmetal.util.utilCode.ObjectUtils;
import com.lingxi.preciousmetal.util.utilCode.SPUtils;
import com.lingxi.preciousmetal.util.utilCode.SharedPreferencesHelper;
import com.lingxi.preciousmetal.util.utilCode.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

import static com.lingxi.preciousmetal.common.CommonParam.SIGNAL_SORT_SET;
import static com.lingxi.preciousmetal.util.ApplicationUtils.isActivityEnabled;

/**
 * Created by zhangwei on 2018/4/2.
 */
public class MyApplication extends BaseApplication {

    public static KOperationNoteBean operationNoteBean = new KOperationNoteBean();
    private static SignalParamValues mSignalParamValues;//指标参数
    private static List<String> mSortSignalList;//指标排序
    public static final String TAG = MyApplication.class.getSimpleName();
    /**
     * 登录聊天室/ppt直播/获取回放列表所需，请填写自己的appId和appSecret，否则无法登陆。
     * appId和appSecret可以在直播系统管理后台的用户信息页的API设置中用获取。
     */
    public static final String appId = "f1564rnw21";
    public static final String appSecret = "db5320f8519e40a7a710e7a49c99da6c";

    public static List<HomeAllResultBean.MarketBean> marketList;
    public static List<KMarketResultBean.MarketBean> marketBeanList;//行情数据
    public static File imageDirectory;
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);//启用矢量图兼容
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                layout.setPrimaryColorsId();//全局设置主题颜色
                return new CommonHeader(context);//.setTimeFormat(new DynamicTimeFormat("松手更新 %s"));
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                ClassicsFooter.REFRESH_FOOTER_NOTHING = "已全部加载";
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    public static SignalParamValues getSignalParamValues() {
        if (mSignalParamValues == null)
            mSignalParamValues = SignalParamValues.create();
        return mSignalParamValues;
    }

    public static void setSignalParamValues(SignalParamValues mSignalParamValues) {
        MyApplication.mSignalParamValues = mSignalParamValues;
    }

    public static List<String> getSortSignalList() {
        if (mSortSignalList == null) {
            String sortSigalListStr = SPUtils.getInstance().getString(SIGNAL_SORT_SET);
            List<String> sortSignalList = new ArrayList<>();
            if (!StringUtil.isEmpty(sortSigalListStr)) {
                List<String> oldSignalList = BaseApplication.getInstance().mCustomJsonConverter.parseJson(sortSigalListStr, List.class);
                if (ObjectUtils.isEmpty(oldSignalList)) {
                    oldSignalList = new ArrayList<>();
                }
                sortSignalList.addAll(oldSignalList);
            } else {
                sortSignalList.add("1");
                sortSignalList.add("2");
                sortSignalList.add("3");
                sortSignalList.add("4");
                sortSignalList.add("5");
                sortSignalList.add("7");
                sortSignalList.add("8");
                sortSignalList.add("10");
                sortSignalList.add("11");
                SPUtils.getInstance().put(SIGNAL_SORT_SET, BaseApplication.getInstance().mCustomJsonConverter.toJson(sortSignalList));
            }
            mSortSignalList = sortSignalList;
        }
        return mSortSignalList;
    }

    @Override
    public void onCreate() {
        setInstance(this);
        Utils.init(this);
        super.onCreate();
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(getBaseContext(), "kColorSet");
        BaseApplication.kIndexColor = (int) sharedPreferencesHelper.getSharedPreference("kType", 0);
        operationNoteBean.setChildName("MACD");
        operationNoteBean.setMainName("MA");
        operationNoteBean.setChildIndex(8);
        operationNoteBean.setMainIndex(0);
        operationNoteBean.setIndicatorList(DataServer.getIndicatorsData());
        // 创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);
        initFile();
        initPolyvCilent();
        initPolyvLiveClient();
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush

        // 初始化友盟统计
        UMConfigure.init(this, "5b2b9b05a40fa351b800012f", "default",
                UMConfigure.DEVICE_TYPE_PHONE, null);
//        UMConfigure.setLogEnabled(true);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
//        MobclickAgent.setSecret(this, "s10bacedtyz"); TODO设置加密密钥，企业认证通过后设置
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        //豆瓣RENREN平台目前只能在服务器端配置
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
        PlatformConfig.setYixin("yxc0614e80c9304c11b0391514d09f13bf");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setTwitter("3aIN7fuF685MuZ7jtXkQxalyi", "MK6FEYG63eWcpDFgRYw4w9puJhzDl0tyuqWjZ3M7XJuuG7mMbO");
        PlatformConfig.setAlipay("2015111700822536");
        PlatformConfig.setLaiwang("laiwangd497e70d4", "d497e70d4c3e4efeab1381476bac4c5e");
        PlatformConfig.setPinterest("1439206");
        PlatformConfig.setKakao("e4f60e065048eb031e235c806b31c70f");
        PlatformConfig.setDing("dingoalmlnohc0wggfedpk");
        PlatformConfig.setVKontakte("5764965", "5My6SNliAaLxEm3Lyd9J");
        PlatformConfig.setDropbox("oz8v5apet3arcdy", "h7p2pjbzkkxt02a");

        KefuHelper.getInstance().init(this);
    }

    /**
     * 初始化直播的配置
     */
    public void initPolyvLiveClient() {
        // 初始化实例
        PolyvLiveSDKClient.getInstance();
        // 初始化聊天室配置
        MyPolyvChatManager.initConfig(appId, appSecret);
        // 初始化连麦配置
        PolyvLinkMicManager.init(this);
    }

    //加密秘钥和加密向量，在后台->设置->API接口中获取，用于解密SDK加密串
    //值修改请参考https://github.com/easefun/polyv-android-sdk-demo/wiki/10.%E5%85%B3%E4%BA%8E-SDK%E5%8A%A0%E5%AF%86%E4%B8%B2-%E4%B8%8E-%E7%94%A8%E6%88%B7%E9%85%8D%E7%BD%AE%E4%BF%A1%E6%81%AF%E5%8A%A0%E5%AF%86%E4%BC%A0%E8%BE%93
    /**
     * 加密秘钥
     */
    private String aeskey = "VXtlHmwfS2oYm0CZ";
    /**
     * 加密向量
     */
    private String iv = "2u9gDPKdX6GyQJKU";
    /**
     * 加密串
     */
//    private String encryptedStr = "CMWht3MlpVkgpFzrLNAebYi4RdQDY/Nhvk3Kc+qWcck6chwHYKfl9o2aOVBvXVTRZD/14XFzVP7U5un43caq1FXwl0cYmTfimjTmNUYa1sZC1pkHE8gEsRpwpweQtEIiTGVEWrYVNo4/o5jI2/efzA==";

    private String encryptedStr = "UeqN0PpCVe1+bSb80KYkRcT7j8ziJhJJuPOCGwb+n2ySSQmgGzjnTlvNkrAQc3L0nG+P2nDqE+uqezmH3I3bT4GyEBYJB1nQ46RuAS+bYycrl7YvhZHLIFXV7Fflwj2Axaby58iQsvMql9H3LnhwgQ==";

    // TODO: 2018/6/20 zhangwei 记得更改参数
    public void initPolyvCilent() {
        //网络方式取得SDK加密串，（推荐）
        //网络获取到的SDK加密串可以保存在本地SharedPreference中，下次先从本地获取
//		new LoadConfigTask().execute();
        PolyvSDKClient client = PolyvSDKClient.getInstance();
        //使用SDK加密串来配置
        client.setConfig(encryptedStr, aeskey, iv, getApplicationContext());
        //初始化SDK设置
        client.initSetting(getApplicationContext());
        //启动Bugly
        client.initCrashReport(getApplicationContext());
        //启动Bugly后，在学员登录时设置学员id
//		client.crashReportSetUserId(userId);
        //获取SD卡信息
        PolyvDevMountInfo.getInstance().init(this, new PolyvDevMountInfo.OnLoadCallback() {

            @Override
            public void callback() {
                //是否有可移除的存储介质（例如 SD 卡）或内部（不可移除）存储可供使用。
                if (!PolyvDevMountInfo.getInstance().isSDCardAvaiable()) {
                    // TODO 没有可用的存储设备,后续不能使用视频缓存功能
                    Log.e(TAG, "没有可用的存储设备,后续不能使用视频缓存功能");
                    return;
                }

                //可移除的存储介质（例如 SD 卡），需要写入特定目录/storage/sdcard1/Android/data/包名/。
                String externalSDCardPath = PolyvDevMountInfo.getInstance().getExternalSDCardPath();
                if (!TextUtils.isEmpty(externalSDCardPath)) {
                    StringBuilder dirPath = new StringBuilder();
                    dirPath.append(externalSDCardPath).append(File.separator).append("polyvdownload");
                    File saveDir = new File(dirPath.toString());
                    if (!saveDir.exists()) {
                        getExternalFilesDir(null); // 生成包名目录
                        saveDir.mkdirs();//创建下载目录
                    }

                    //设置下载存储目录
                    PolyvSDKClient.getInstance().setDownloadDir(saveDir);
                    return;
                }

                //如果没有可移除的存储介质（例如 SD 卡），那么一定有内部（不可移除）存储介质可用，都不可用的情况在前面判断过了。
                File saveDir = new File(PolyvDevMountInfo.getInstance().getInternalSDCardPath() + File.separator + "polyvdownload");
                if (!saveDir.exists()) {
                    saveDir.mkdirs();//创建下载目录
                }

                //设置下载存储目录
                PolyvSDKClient.getInstance().setDownloadDir(saveDir);
            }
        }, true);

        // 设置下载队列总数，多少个视频能同时下载。(默认是1，设置负数和0是没有限制)
//        PolyvDownloaderManager.setDownloadQueueCount(1);
    }

    private static WeakReference<Activity> mTopActivity;

    private static boolean activityVisible;

    /**
     * 判断app是否在前台运行
     *
     * @return
     */
    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityPaused() {
        activityVisible = false;
    }


    public static void activityResumed(Activity activity) {
        mTopActivity = new WeakReference<>(activity);
        activityVisible = true;
    }

    //设置顶层ACTIVITY,一般应用到窗口基类
    public static void setTopActivity(Activity cur) {
        mTopActivity = new WeakReference<Activity>(cur);
    }

    public static Activity getTopActivity() {
        return mTopActivity == null ? null : mTopActivity.get();
    }

    //关闭顶层窗口
    public void closeTopActivity() {
        if (isActivityEnabled(mTopActivity.get())) {
            mTopActivity.get().finish();
            mTopActivity = null;
        }
    }


    public static String getDiskCacheDir(Context context){
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            if(context.getExternalCacheDir()!=null) {
                cachePath = context.getExternalCacheDir().getPath();
            }else{
                cachePath = context.getCacheDir().getPath();
            }
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }



    protected void initFile() {
        String cache = getDiskCacheDir(getApplicationContext());
        File cacheDirectory = new File(cache);
        File rootDirectory = cacheDirectory.getParentFile();

        imageDirectory = new File(rootDirectory.getAbsolutePath() + "/images");
        imageDirectory.mkdirs();
    }

}

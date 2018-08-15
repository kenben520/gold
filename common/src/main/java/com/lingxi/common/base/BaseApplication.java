package com.lingxi.common.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.inspector.domstorage.SharedPreferencesHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lingxi.common.util.AppUtil;
import com.lingxi.common.util.StringUtil;
import com.lingxi.net.NetSDK;
import com.lingxi.net.biznet.converter.FastJsonConverter;
import com.lingxi.net.biznet.converter.JsonConverter;
import com.lingxi.net.common.util.NetSdkLog;

import java.util.List;

/**
 * Created by zhangwei on 2018/3/26.
 */
public class BaseApplication extends Application {
    public static int kIndexColor = 0;//0默认 绿长红跌 1 红跌绿长
    private static final String TAG = BaseApplication.class.getSimpleName();
    private static BaseApplication sInstance;
    private boolean hasInit = false;
    public static Context context;

    public boolean getHasInit() {
        return hasInit;
    }

    public static BaseApplication getInstance() {
        return sInstance;
    }

    protected void setInstance(BaseApplication instance) {
        sInstance = instance;
    }

    @Override
    public void onCreate() {
        if (sInstance == null) {
            throw new IllegalStateException("wrong state: sInstance: " + sInstance);
        }
        super.onCreate();
        context = getApplicationContext();
        try {
            String processName = getCurProcessName(this);
            if (StringUtil.equals(getPackageName(), processName)) {
                //主进程
                initMain();
            } else {

            }
        } catch (Exception e) {

        }
    }

    public CustomJsonConverter mCustomJsonConverter;

    /**
     * 主进程初始化工作
     */
    public void initMain() {
        hasInit = true;
        if (AppUtil.isApkDebugable(this)) {
            Stetho.initializeWithDefaults(this);
            NetSdkLog.setPrintLog(true);
        }
        mCustomJsonConverter = new CustomJsonConverter();
        NetSDK.init(this, mCustomJsonConverter);
    }

    public class CustomJsonConverter implements JsonConverter {

        public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        private Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT).create();

        @Override
        public <T> T parseJson(String s, Class<T> clz) {
            T result = null;
            try {
                result = gson.fromJson(s, clz);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (result != null)
                return result;
            else
                return FastJsonConverter.getInstance().parseJson(s, clz);
        }

        @Override
        public String toJson(Object object) {
            return gson.toJson(object);
        }
    }

    private String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    //版本名
    public static String getVersionName() {
        return getPackageInfo().versionName;
    }

    //版本号
    public static int getVersionCode() {
        return getPackageInfo().versionCode;
    }
    private static PackageInfo getPackageInfo() {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }
}

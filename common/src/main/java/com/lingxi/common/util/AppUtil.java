package com.lingxi.common.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;

import com.lingxi.common.R;
import com.lingxi.common.base.BaseApplication;

/**
 * Created by zhangwei on 2018/3/26.
 */
public class AppUtil {

    private static final String TAG = AppUtil.class.getSimpleName();

    /**
     * 获取app版本号
     *
     * @return
     */
    public static String getAppVersion() {

        Context context = BaseApplication.getInstance();
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
//            XLog.e(TAG, e);
        }
        return info != null && info.versionName != null ? info.versionName : "";
    }

    public static boolean isApkDebugable(Context context) {

        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {

        }
        return false;
    }
}




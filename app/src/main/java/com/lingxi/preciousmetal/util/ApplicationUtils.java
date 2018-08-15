package com.lingxi.preciousmetal.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lingxi.preciousmetal.base.MyApplication;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by zhangwei on 2018/4/18.
 */
public class ApplicationUtils {
    //判断父窗口是否可用
    public static boolean isActivityEnabled(Context context) {
        if (context == null) return false;
        if (!((Activity) context).isFinishing() && ((Activity) context).getWindow() != null && ((Activity) context).getWindow().getDecorView() != null)
            return true;
        else
            return false;
    }

    public static String getTagName(String tag) {
        switch (tag) {
            case "1":
                return "1分钟";
            case "2":
                return "5分钟";
            case "3":
                return "15分钟";
            case "4":
                return "30分钟";
            case "5":
                return "1小时";
            case "7":
                return "4小时";
            case "8":
                return "日线";
            case "10":
                return "周线";
            case "11":
                return "月";
        }
        return "";
    }

    /**
     * 判断软键盘是否弹出
     */
    public static boolean isShowKeyboard(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
        if (imm.hideSoftInputFromWindow(v.getWindowToken(), 0)) {
            imm.showSoftInput(v, 0);
            return true;
            //软键盘已弹出
        } else {
            return false;
            //软键盘未弹出
        }
    }

    public static boolean isRunningForeground() {
        String packageName = getPackageName(MyApplication.getInstance());
        String topActivityClassName = getTopActivityName(MyApplication.getInstance());
        System.out.println("packageName=" + packageName + ",topActivityClassName=" + topActivityClassName);
        if (packageName != null && topActivityClassName != null && topActivityClassName.startsWith(packageName)) {
            System.out.println("---> ForeGround");
            return true;
        } else {
            System.out.println("---> BackGround");
            return false;
        }
    }

    /**
     * 判断顶部窗口名称
     */
    public static String getTopActivityName(Context context) {
        String topActivityClassName = null;
        ActivityManager activityManager =
                (ActivityManager) (context.getSystemService(android.content.Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(1);
        if (runningTaskInfos != null) {
            ComponentName f = runningTaskInfos.get(0).topActivity;
            topActivityClassName = f.getClassName();
        }
        return topActivityClassName;
    }

    /**
     * 获取包名
     */
    public static String getPackageName(Context context) {
        String packageName = context.getPackageName();
        return packageName;
    }

    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }
}
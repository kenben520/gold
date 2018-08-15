package com.lingxi.net.basenet.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.lingxi.net.NetSDK;

/**
 * Created by zhangwei on 2018/5/9.
 */

public class SPUtil {
    public static SharedPreferences getCommonParamsPreferences() {
        return getSharedPreferences(NetSDK.getContext(), "common_params");
    }

    public static SharedPreferences getSharedPreferences(Context ctx, String name) {
        return ctx.getSharedPreferences(name, Context.MODE_PRIVATE);
    }
}

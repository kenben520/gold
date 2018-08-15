package com.lingxi.preciousmetal.ui.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.widget.Toast;

import com.lingxi.preciousmetal.common.EventBusKeyDefine;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * 网络监听广播类
 */
public class NetStateReceiver extends BroadcastReceiver {

    private static final String TAG = NetStateReceiver.class.getSimpleName();

    public static final int NETSTATE_NOT_CONNECTED = 0;
    public static final int NETSTATE_WIFI = 1;//wifi流量状态
    public static final int NETSTATE_MOBILE = 2;//手机流量状态

    public static final String mobileNoticeStr = "当前为非wifi环境，是否使用流量观看视频?";

    @Override
    public void onReceive(Context context, Intent intent) {

        State wifiState = null;
        State mobileState = null;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        NetworkInfo mobileNetworkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetworkInfo != null) {
            mobileState = mobileNetworkInfo.getState();
        }

        if (wifiState != null && mobileState != null && State.CONNECTED != wifiState && State.CONNECTED == mobileState) {
//            LogUtil.d(TAG, "移动数据连接成功");
            //传递弱网提示消息
            sendNetState(NETSTATE_MOBILE);
//            ToastUtils.showLong("手机流量状态");
        } else if (wifiState != null && mobileState != null
                && State.CONNECTED != wifiState
                && State.CONNECTED != mobileState) {
            // 手机没有任何的网络
            sendNetState(NETSTATE_NOT_CONNECTED);
//            ToastUtils.showLong("手机没有任何的网络");
        } else if (wifiState != null && State.CONNECTED == wifiState) {
            // 无线网络连接成功
            sendNetState(NETSTATE_WIFI);
//            ToastUtils.showLong("wifi流量状态");
        }
    }

    //传递弱网提示消息
    private static void sendNetState(int sState) {
        EventBus.getDefault().post(new EventBusKeyDefine.EventBusMsgData<Integer>(EventBusKeyDefine.EVENTBUS_LIVE_NETSTATE, sState));
    }

    public static boolean isNetOK(int netState) {
        return NetStateReceiver.NETSTATE_MOBILE==netState || NetStateReceiver.NETSTATE_WIFI==netState;
    }

    /**
     * 如果当前是流量状态则发送一个${NETSTATE_MOBILE}的
     *
     * @param context
     */
    public static boolean isMobileNet(Context context) {
        State wifiState = null;
        State mobileState = null;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        NetworkInfo mobileNetworkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetworkInfo != null) {
            mobileState = mobileNetworkInfo.getState();
        }

        return wifiState != null && mobileState != null && State.CONNECTED != wifiState && State.CONNECTED == mobileState;
    }

    public static boolean isMobileNet(int netState) {
        return NetStateReceiver.NETSTATE_MOBILE==netState;
    }

    public static boolean isWifiNet(Context context) {
        State wifiState = null;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();

        return wifiState != null && State.CONNECTED == wifiState;
    }

}

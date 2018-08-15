package com.lingxi.preciousmetal.ui.jpush;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;

import com.lingxi.biz.domain.requestMo.ActivityInviteMo;
import com.lingxi.biz.domain.responseMo.WarningsItemMo;
import com.lingxi.common.util.StringUtil;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.MyApplication;
import com.lingxi.preciousmetal.common.IntentParam;
import com.lingxi.preciousmetal.ui.activity.WebViewActivity;
import com.lingxi.preciousmetal.ui.widget.dialog.WarningTriaggerDialog;
import com.lingxi.preciousmetal.util.ApplicationUtils;

import org.json.JSONObject;

/**
 * 消息处理器
 */

public class MyRcImHandler {
    private SharedPreferences sp;
    private static MyRcImHandler myRcImHandler;
    private static final String TAG = "MyRcImHandler";
    private WarningTriaggerDialog commonDialog;

    private MyRcImHandler() {
    }

    public static MyRcImHandler getInstance() {
        if (myRcImHandler == null) {
            myRcImHandler = new MyRcImHandler();
        }
        return myRcImHandler;
    }

    //获取类型
    public static String getType(String content) {
        try {
            JSONObject jsonObject = new JSONObject(content);
            return jsonObject.getString("action");
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 尝试过滤通知消息
     * @param content
     * @param notificationId
     * @return
     */
    public void process(String msg, String content, int notificationId) {
        if (StringUtil.isEmpty(content)) return;
        String type = getType(content);
        if (StringUtil.isEmpty(type)) return;
        Activity topActivity = MyApplication.getTopActivity();

        Context context = MyApplication.context;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        switch (type) {
            case "price_alert":
                WarningsItemMo warningsItemMo = MyApplication.getInstance().mCustomJsonConverter.parseJson(content, WarningsItemMo.class);
                if (warningsItemMo != null) {
                    if (topActivity == null) {
                        int i = 0;
//                        //在后台就发送启动首页的通知，然后在首页打开直播页
//                        createNotificationSplashActivity(topActivity, warningsItemMo.getTitle(), warningsItemMo.getContent(), imMessageData);
                        return;
                    }
                    // 如果收到JPush已经自动添加，则移除
                    if(notificationId !=0){
                        notificationManager.cancel(notificationId);
                    }

                    if (ApplicationUtils.isRunningForeground()) {
                        int i = 0;
                        int a=i+1;
                        showPopupwindow(topActivity, warningsItemMo);
                    } else {
                        int i = 0;
                        createNotificationActivity(topActivity, warningsItemMo.getTitle(), warningsItemMo.getContent());
                        showPopupwindow(topActivity, warningsItemMo);
                    }
                }
                break;
            case "activity_invite":

                ActivityInviteMo inviteMo = MyApplication.getInstance().mCustomJsonConverter
                        .parseJson(content, ActivityInviteMo.class);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra(IntentParam.URL, inviteMo.url);

                PendingIntent pendingIntent = PendingIntent.getActivity(context,0,
                        intent, PendingIntent.FLAG_UPDATE_CURRENT);

                builder.setSmallIcon(R.mipmap.app_logo)
                        .setContentText(msg)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent);

                Notification notification = builder.build();

                notificationManager.notify(notificationId, notification);
                break;
        }
    }

    public void test() {
        showPopupwindow(MyApplication.getTopActivity(), null);
    }

    private void showPopupwindow(final Context context, WarningsItemMo warningsItemMo) {
        if (!ApplicationUtils.isActivityEnabled(context)) return;
        commonDialog = new WarningTriaggerDialog(context, "温馨提示", "确定取消?", commitClickListener, 1, "取消", "确定", warningsItemMo);
        commonDialog.show();
    }


    private void createNotificationActivity(Activity context, String title, String content) {
        Intent intent = getCurrentActivityIntent(context);
        createNotification(intent, title, content);
    }

    private Intent getCurrentActivityIntent(Activity activity) {
        return activity.getIntent();
    }


    public static int getMiddleInt(int min, int max) {
        return (int) (Math.random() * (max - min)) + min;
    }

    private void createNotification(Intent liveintent, String title, String content) {
        int id = getMiddleInt(1, 9999888);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                MyApplication.getInstance(), id, liveintent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                MyApplication.getInstance())
                .setSmallIcon(R.mipmap.app_logo)
                .setContentTitle(title)
                .setContentText(content)
                .setDefaults(Notification.DEFAULT_SOUND
                        | Notification.DEFAULT_VIBRATE)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManager mNotifyMgr = (NotificationManager) MyApplication.getInstance()
                .getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyMgr.notify(id, mBuilder.build());
    }


    private WarningTriaggerDialog.CommitClickListener commitClickListener = new WarningTriaggerDialog.CommitClickListener() {
        @Override
        public void Click(int whichDialog) {
            switch (whichDialog) {
                case 1:
                    break;
            }

        }
    };
}

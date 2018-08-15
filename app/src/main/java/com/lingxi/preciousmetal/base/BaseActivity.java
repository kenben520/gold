package com.lingxi.preciousmetal.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.jaeger.library.StatusBarUtil;
import com.lingxi.common.util.MPermissionUtils;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class BaseActivity extends AppCompatActivity {
    protected Context mContext;
    private MyApplication mApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mApp = (MyApplication) this.getApplicationContext();
        mApp.setTopActivity(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        MyApplication.activityResumed(this);
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MyApplication.activityPaused();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        hideSoftInput();
        MPermissionUtils.destroy();
        removeActivity();
        super.onDestroy();
    }

    private void removeActivity() {
        Activity currActivity = mApp.getTopActivity();
        if (this.equals(currActivity))
            mApp.setTopActivity(null);
    }

    /**
     * 输入框隐藏
     */
    public void hideSoftInput() {
        if (mContext != null) {
            InputMethodManager imm = (InputMethodManager) ((Activity) mContext)
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            View view = ((Activity) mContext).getCurrentFocus();
            if (view != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    protected void registorEventBus() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    protected void unregistorEventBus() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}

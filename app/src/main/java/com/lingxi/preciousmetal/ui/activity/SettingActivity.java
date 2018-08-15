package com.lingxi.preciousmetal.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.LogoutRequestMo;
import com.lingxi.biz.domain.responseMo.BaseMo;
import com.lingxi.biz.service.UserService;
import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.common.EventBusKeyDefine;
import com.lingxi.preciousmetal.domain.UserInfoBean;
import com.lingxi.preciousmetal.login.LoginHelper;
import com.lingxi.preciousmetal.ui.widget.CommonItemBean;
import com.lingxi.preciousmetal.ui.widget.CommonItemView;
import com.lingxi.preciousmetal.ui.widget.TopBarView;
import com.lingxi.preciousmetal.ui.widget.dialog.DialogManager;
import com.lingxi.preciousmetal.util.CropUtils;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class SettingActivity extends TranslucentStatusBarActivity {
    @BindView(R.id.topbar_view)
    TopBarView mTopbarView;
    @BindView(R.id.item_1)
    CommonItemView mItem1;
    @BindView(R.id.item_2)
    CommonItemView mItem2;

    CleanCacheTask mCleanCacheTask;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_setting);
        ButterKnife.bind(this);
        initView();
    //    startActivity(new Intent(getApplicationContext(),GoldMainActivity.class));
    }


    private void initView() {
        initTopBar();
        initSystemSettingItemTab();
    }

    private void initTopBar() {
        mTopbarView.setMode(TopBarView.MODE_BACK);
        mTopbarView.setTitle("系统设置");
    }

    public void initSystemSettingItemTab() {
        CommonItemBean commonItemBean1 = new CommonItemBean();
        commonItemBean1.imgResId = 0;
        commonItemBean1.title = "清除缓存";
        mItem1.initData(commonItemBean1);
        if (LoginHelper.getInstance().isLogin()) {
            CommonItemBean commonItemBean2 = new CommonItemBean();
            commonItemBean2.imgResId = 0;
            commonItemBean2.title = "退出登陆";
            mItem2.initData(commonItemBean2);
        } else {
            mItem2.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.item_1)
    public void onClickItem1(View v) {
        mCleanCacheTask = new CleanCacheTask(this);
        mCleanCacheTask.execute(this.getCacheDir(), new File(CropUtils.getCropPicDir()));
    }

    @OnClick(R.id.item_2)
    public void onClickItem2(View v) {
        UserInfoBean userInfoBean = LoginHelper.getInstance().getLoginUserInfo();
        LogoutRequestMo logoutRequestMo = new LogoutRequestMo(userInfoBean.user_id);
        UserService.logout(logoutRequestMo, new BizResultListener<BaseMo>() {
            @Override
            public void onPreExecute() {
                DialogManager.getInstance().showLoadingDialog(SettingActivity.this, "", false);
            }

            @Override
            public void hitCache(boolean hit, BaseMo baseMo) {

            }

            @Override
            public void onSuccess(BaseMo baseMo) {
                LoginHelper.getInstance().updateLoginUserInfo(null);
                EventBus.getDefault().post(new EventBusKeyDefine.EventBusMsgData<String>(EventBusKeyDefine.EVENTBUS_LOGOUT, ""));
                DialogManager.getInstance().cancellLoadingDialog();
                startActivity(new Intent(SettingActivity.this, GoldMainActivity.class));
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                DialogManager.getInstance().cancellLoadingDialog();
                ToastUtils.showShort(bizMessage);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private class CleanCacheTask extends AsyncTask<File, Void, Void> {
        private Activity context;

        public CleanCacheTask(Activity context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            DialogManager.getInstance().showLoadingDialog(SettingActivity.this, "", false);
        }

        @Override
        protected Void doInBackground(File... params) {
            for (File file : params) {
                deleteFilesByDirectory(file);
            }
            try {
                Thread.currentThread().sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            DialogManager.getInstance().cancellLoadingDialog();
            ToastUtils.showShort("清理完成");
        }

        private void deleteFilesByDirectory(File directory) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File item : files) {
                    if (item.isDirectory()) {
                        deleteFilesByDirectory(item);
                    } else {
                        item.delete();
                    }
                }
            }
            directory.delete();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCleanCacheTask != null
                && mCleanCacheTask.getStatus() != AsyncTask.Status.FINISHED) {
            mCleanCacheTask.cancel(true);
        }
    }
}

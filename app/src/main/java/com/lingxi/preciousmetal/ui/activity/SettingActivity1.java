package com.lingxi.preciousmetal.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.LogoutRequestMo;
import com.lingxi.biz.domain.responseMo.BaseMo;
import com.lingxi.biz.service.UserService;
import com.lingxi.common.base.BaseApplication;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.MyApplication;
import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;
import com.lingxi.preciousmetal.chart.activity.KColorSettingActivity;
import com.lingxi.preciousmetal.common.EventBusKeyDefine;
import com.lingxi.preciousmetal.domain.UserInfoBean;
import com.lingxi.preciousmetal.login.LoginHelper;
import com.lingxi.preciousmetal.ui.widget.dialog.DialogManager;
import com.lingxi.preciousmetal.util.CropUtils;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class SettingActivity1 extends TranslucentStatusBarActivity {
    @BindView(R.id.item_1)
    View mItem1;
    @BindView(R.id.item_2)
    View mItem2;
    CleanCacheTask mCleanCacheTask;
    @BindView(R.id.btn_logout)
    TextView btnLogout;
    @BindView(R.id.set_textview_title)
    TextView setTextviewTitle1;
    @BindView(R.id.set_textview_right_title)
    TextView setTextviewRightTitle1;
    @BindView(R.id.set_textview_title2)
    TextView setTextviewTitle2;
    @BindView(R.id.set_textview_right_title2)
    TextView setTextviewRightTitle2;
    @BindView(R.id.tv_app_version)
    TextView tvAppVersion;
    @BindView(R.id.set_textview_title_3)
    TextView setTextviewTitle3;
    @BindView(R.id.set_textview_right_title_3)
    TextView setTextviewRightTitle3;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SettingActivity1.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initView();
        //    startActivity(new Intent(getApplicationContext(),GoldMainActivity.class));
    }


    private void initView() {
        initSystemSettingItemTab();
        tvAppVersion.setText("V" + MyApplication.getVersionName());
    }

    public void initSystemSettingItemTab() {
        setTextviewTitle1.setText("涨跌显示设置");
        setTextviewTitle2.setText("清理缓存");
        setTextviewTitle3.setText("重置密码");
        setTextviewRightTitle2.setText(getCacheTotalSize());
        if (LoginHelper.getInstance().isLogin()) {
            btnLogout.setVisibility(View.VISIBLE);
        } else {
            btnLogout.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.item_2)
    public void onClickItem2(View v) {
        mCleanCacheTask = new CleanCacheTask(this);
        mCleanCacheTask.execute(this.getCacheDir(), new File(CropUtils.getCropPicDir()));
    }

    @OnClick(R.id.item_1)
    public void onClickItem1(View v) {
        KColorSettingActivity.startMyActivity(this);
    }

    private String getCacheTotalSize() {
        File cache1 = new File(CropUtils.getCropPicDir());
        long totalSize = getFolderSize(cache1) + getFolderSize(this.getCacheDir());
        return getText(totalSize);
    }

    public static long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    private static NumberFormat numberFormat;

    static {
        numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(2);
    }


    /**
     * 将内存空间大小转换为单位大小（KB，MB,GB）
     *
     * @param size
     * @return
     */
    public static String getText(long size) {
        String str;
        if (size > (1024 * 1024 * 1024)) {
            double s = size / (1024 * 1024 * 1024f);
            str = numberFormat.format(s) + "GB";
        } else if (size > (1024 * 1024)) {
            double s = size / (1024 * 1024f);
            str = numberFormat.format(s) + "MB";
        } else if (size > 1024) {
            double s = size / 1024f;
            str = numberFormat.format(s) + "KB";
        } else {
            str = size + "B";
        }
        return str;
    }

    @OnClick(R.id.btn_logout)
    public void onClickItem3(View v) {
        UserInfoBean userInfoBean = LoginHelper.getInstance().getLoginUserInfo();
        LogoutRequestMo logoutRequestMo = new LogoutRequestMo(userInfoBean.user_id);
        UserService.logout(logoutRequestMo, new BizResultListener<BaseMo>() {
            @Override
            public void onPreExecute() {
                DialogManager.getInstance().showLoadingDialog(SettingActivity1.this, "", false);
            }

            @Override
            public void hitCache(boolean hit, BaseMo baseMo) {

            }

            @Override
            public void onSuccess(BaseMo baseMo) {
                LoginHelper.getInstance().updateLoginUserInfo(null);
                EventBus.getDefault().post(new EventBusKeyDefine.EventBusMsgData<String>(EventBusKeyDefine.EVENTBUS_LOGOUT, ""));
                DialogManager.getInstance().cancellLoadingDialog();
                startActivity(new Intent(SettingActivity1.this, GoldMainActivity.class));
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
        setTextviewRightTitle1.setText(BaseApplication.kIndexColor == 0 ? "绿涨红跌" : "红涨绿跌");
        super.onResume();
    }

    @OnClick(R.id.layout_left_btn)
    public void onViewClicked() {
        hideSoftInput();
        finish();
    }

    @OnClick(R.id.item_3)
    public void onViewClicked3() {
        ForgetPasswordActivity.actionStart(SettingActivity1.this);
    }

    private class CleanCacheTask extends AsyncTask<File, Void, Void> {
        private Activity context;

        public CleanCacheTask(Activity context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            DialogManager.getInstance().showLoadingDialog(SettingActivity1.this, "", false);
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
            setTextviewRightTitle2.setText(getCacheTotalSize());
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

package com.lingxi.preciousmetal.ui.activity;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.ui.activity.kefu.KefuLoginActivity;
import com.lingxi.preciousmetal.ui.widget.CommonItemBean;
import com.lingxi.preciousmetal.ui.widget.CommonItemView;
import com.lingxi.preciousmetal.ui.widget.TopBarView;
import com.lingxi.preciousmetal.ui.widget.dialog.CommonDialog;
import com.lingxi.preciousmetal.util.AndroidUtils;
import com.lingxi.preciousmetal.util.utilCode.PhoneUtils;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhangwei on 2018/4/25.
 */

public class ContactUsActivity extends TranslucentStatusBarActivity {
    @BindView(R.id.topbar_view)
    TopBarView mTopbarView;
    @BindView(R.id.item_1)
    CommonItemView mItem1;
    @BindView(R.id.item_2)
    CommonItemView mItem2;
    @BindView(R.id.item_3)
    CommonItemView mItem3;
    @BindView(R.id.item_4)
    CommonItemView mItem4;
    @BindView(R.id.item_5)
    CommonItemView mItem5;
    private CommonDialog commonDialog;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ContactUsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_setting);
        ButterKnife.bind(this);
        initView();
    }


    private void initView() {
        initTopBar();
        initSystemSettingItemTab();
    }

    private void initTopBar() {
        mTopbarView.setMode(TopBarView.MODE_BACK);
        mTopbarView.setTitle("联系我们");
    }

    CommonItemBean commonItemBean1, commonItemBean2, commonItemBean3, commonItemBean4, commonItemBean5;

    public void initSystemSettingItemTab() {
        commonItemBean1 = new CommonItemBean();
        commonItemBean1.imgResId = 0;
        commonItemBean1.title = "客服热线";
        commonItemBean1.rightTitle = "+852-66582228";
        commonItemBean1.isShowRightArrow = true;
        mItem1.initData(commonItemBean1);
        commonItemBean2 = new CommonItemBean();
        commonItemBean2.imgResId = 0;
        commonItemBean2.title = "客服邮箱";
        commonItemBean2.rightTitle = "gjs@msd24.com";
        commonItemBean2.isShowRightArrow = true;
        mItem2.initData(commonItemBean2);
        commonItemBean3 = new CommonItemBean();
        commonItemBean3.imgResId = 0;
        commonItemBean3.title = "客服微信";
        commonItemBean3.rightTitle = "tianyu400820";
        commonItemBean3.isShowRightArrow = true;
        mItem3.initData(commonItemBean3);
        mItem3.setVisibility(View.VISIBLE);
        commonItemBean4 = new CommonItemBean();
        commonItemBean4.imgResId = 0;
        commonItemBean4.title = "客服QQ";
        commonItemBean4.rightTitle = "800018358";
        commonItemBean4.isShowRightArrow = true;
        mItem4.initData(commonItemBean4);
        mItem4.setVisibility(View.VISIBLE);
        commonItemBean5 = new CommonItemBean();
        commonItemBean5.imgResId = 0;
        commonItemBean5.title = "在线咨询";
        commonItemBean5.rightTitle = "立即咨询";
        commonItemBean5.isShowRightArrow = true;
        mItem5.initData(commonItemBean5);
        mItem5.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.item_1)
    public void onClickItem1(View v) {
        if (!TextUtils.isEmpty(commonItemBean1.rightTitle)) {
            commonDialog = new CommonDialog(ContactUsActivity.this, "提示", "是否拨打客服热线", commitClickListener, 4, "取消", "拨号");
            commonDialog.show();
        }
    }

    @OnClick(R.id.item_2)
    public void onClickItem2(View v) {
        commonDialog = new CommonDialog(ContactUsActivity.this, "提示", "已复制客服邮箱到剪贴板", commitClickListener, 1, "", "确定");
        commonDialog.show();
    }

    @OnClick(R.id.item_3)
    public void onClickItem3(View v) {
        commonDialog = new CommonDialog(ContactUsActivity.this, "提示", "已复制客服微信到剪贴板", commitClickListener, 2, "", "确定");
        commonDialog.show();
    }

    @OnClick(R.id.item_4)
    public void onClickItem4(View v) {
//        if (checkApkExist(ContactUsActivity.this, "com.tencent.mobileqq")){
//            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=800018358")));
//        }else{
////            ToastUtils.showShort("本机未安装QQ应用");
            commonDialog = new CommonDialog(ContactUsActivity.this, "提示", "已复制客服QQ到剪贴板", commitClickListener, 3, "", "确定");
            commonDialog.show();
//        }
//
    }

    @OnClick(R.id.item_5)
    public void onClickItem5(View v) {
        KefuLoginActivity.actionStart(this);
    }

    private CommonDialog.CommitClickListener commitClickListener = new CommonDialog.CommitClickListener() {
        @Override
        public void click(int whichDialog,Object object) {
            switch (whichDialog) {
                case 1:
                    AndroidUtils.getInstance().copyTextToClipboard(commonItemBean2.rightTitle,ContactUsActivity.this);
                    break;
                case 2:
                    AndroidUtils.getInstance().copyTextToClipboard(commonItemBean3.rightTitle,ContactUsActivity.this);
                    break;
                case 3:
//                    if (checkApkExist(ContactUsActivity.this, "com.tencent.mobileqq")){
//                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin="+800018358+"&version=1")));
//                    }else{
//                        ToastUtils.showShort("本机未安装QQ应用");
//                    }
//                    break;
                    AndroidUtils.getInstance().copyTextToClipboard(commonItemBean4.rightTitle,ContactUsActivity.this);
                    break;
                case 4:
                    PhoneUtils.dial(commonItemBean1.rightTitle);
                    break;
            }

        }
    };

    public boolean checkApkExist(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (commonDialog != null) {
            if (commonDialog.isShowing()) commonDialog.dismiss();
        }
        super.onDestroy();
    }
}

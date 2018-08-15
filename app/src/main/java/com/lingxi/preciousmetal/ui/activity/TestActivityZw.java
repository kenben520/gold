package com.lingxi.preciousmetal.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.presenter.pIml.TestPresenter;
import com.lingxi.preciousmetal.presenter.pInterface.ITestPresenter;
import com.lingxi.preciousmetal.ui.vInterface.ITestView;
import com.lingxi.preciousmetal.ui.widget.CommonItemBean;
import com.lingxi.preciousmetal.ui.widget.CommonItemView;
import com.lingxi.preciousmetal.ui.widget.TopBarView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by zhangwei on 2018/3/26.
 */
public class TestActivityZw extends AppCompatActivity implements ITestView {

    @BindView(R.id.item_1)
    CommonItemView mItemNews;
    @BindView(R.id.item_2)
    CommonItemView mItemHelpCenter;
//    @BindView(R.id.item_3)
//    UserCenterSetView mItemContactUs;
//    @BindView(R.id.item_4)
//    UserCenterSetView mItemSysSetting;
//    @BindView(R.id.btn_open_account)
//    Button mBtnOpenAccount;

    @BindView(R.id.topbar_view)
    TopBarView mTopbarView;
    private ITestPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_setting);
        ButterKnife.bind(this);
        mainPresenter = new TestPresenter(this, this);
        initTopBar();
        initSystemSettingItemTab();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onSwitchTabOk() {

    }

    @Override
    public void initTab() {

    }

    private void initTopBar() {
        mTopbarView.setMode(TopBarView.MODE_BACK);
        mTopbarView.setTitle("系统设置");
    }

    public void initItemTab() {
//        CommonItemBean userCenterSetBean1 = new CommonItemBean();
//        userCenterSetBean1.imgResId = R.drawable.ic_announcement;
//        userCenterSetBean1.title = "最新公告";
//        userCenterSetBean1.isShowTopDivider = true;
//        mItemNews.initData(userCenterSetBean1);
//        CommonItemBean userCenterSetBean2 = new CommonItemBean();
//        userCenterSetBean2.imgResId = R.drawable.ic_help_center;
//        userCenterSetBean2.title = "帮助中心";
//        mItemHelpCenter.initData(userCenterSetBean2);
//        CommonItemBean userCenterSetBean3 = new CommonItemBean();
//        userCenterSetBean3.imgResId = R.drawable.ic_contact_us;
//        userCenterSetBean3.title = "联系我们";
//        userCenterSetBean3.isShowTopDivider = true;
//        mItemContactUs.initData(userCenterSetBean3);
//        CommonItemBean userCenterSetBean4 = new CommonItemBean();
//        userCenterSetBean4.imgResId = R.drawable.ic_sys_setting;
//        userCenterSetBean4.title = "系统设置";
//        mItemSysSetting.initData(userCenterSetBean4);
    }

    public void initSystemSettingItemTab() {
        CommonItemBean commonItemBean1 = new CommonItemBean();
        commonItemBean1.imgResId = 0;
        commonItemBean1.title = "清除缓存";
        mItemNews.initData(commonItemBean1);
        CommonItemBean commonItemBean2 = new CommonItemBean();
        commonItemBean2.imgResId = 0;
        commonItemBean2.title = "退出登陆";
        mItemHelpCenter.initData(commonItemBean2);
    }

    @OnClick(R.id.item_1)
    public void onClickItem1(View v){
        mainPresenter.switchTab();
    }
}

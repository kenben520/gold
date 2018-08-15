package com.lingxi.preciousmetal.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.ui.activity.study.KnowledgeCateActivity;
import com.lingxi.preciousmetal.ui.widget.CommonItemBean;
import com.lingxi.preciousmetal.ui.widget.CommonItemView;
import com.lingxi.preciousmetal.ui.widget.TopBarView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhangwei on 2018/4/25.
 */

public class HelpCenterActivity extends TranslucentStatusBarActivity {
    @BindView(R.id.topbar_view)
    TopBarView mTopbarView;
    @BindView(R.id.item_1)
    CommonItemView mItem1;
    @BindView(R.id.item_2)
    CommonItemView mItem2;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, HelpCenterActivity.class);
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
        mTopbarView.setTitle("帮助中心");
    }

    public void initSystemSettingItemTab() {
        CommonItemBean commonItemBean1 = new CommonItemBean();
        commonItemBean1.imgResId = 0;
        commonItemBean1.title = "投资课堂";
        mItem1.initData(commonItemBean1);
        CommonItemBean commonItemBean2 = new CommonItemBean();
        commonItemBean2.imgResId = 0;
        commonItemBean2.title = "常见问题";
        mItem2.initData(commonItemBean2);
    }

    @OnClick(R.id.item_1)
    public void onClickItem1(View v) {
        startActivity(new Intent(this, KnowledgeCateActivity.class));
    }

    @OnClick(R.id.item_2)
    public void onClickItem2(View v) {
        FrequentlyAskedQuestionsActivity.actionStart(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

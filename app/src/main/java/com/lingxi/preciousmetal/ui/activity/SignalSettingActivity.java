package com.lingxi.preciousmetal.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.MyApplication;
import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;
import com.lingxi.preciousmetal.domain.KOperationNoteBean;
import com.lingxi.preciousmetal.ui.activity.kefu.KefuLoginActivity;
import com.lingxi.preciousmetal.ui.fragment.trade.SignalSettingFragment;
import com.lingxi.preciousmetal.ui.widget.TopBarView;
import com.lingxi.preciousmetal.ui.widget.verticalTabView.TabView;
import com.lingxi.preciousmetal.ui.widget.verticalTabView.TabViewChild;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 指标参数设置
 * Created by zhangwei on 2018/4/23.
 */

public class SignalSettingActivity extends TranslucentStatusBarActivity {
    @BindView(R.id.topbar_view)
    TopBarView mTopbarView;
    @BindView(R.id.tabView)
    TabView tabView;

    public static void actionStart(Context context,int type) {
        Intent intent = new Intent(context, SignalSettingActivity.class);
        intent.putExtra("type",type);
//        context.startActivity(intent);
        Activity activity = (Activity)context;
        activity.startActivityForResult(intent, 101);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signal_setting);
        ButterKnife.bind(this);
        initTopBar();
        initView();
    }

    private void initView() {
        tabView = (TabView) findViewById(R.id.tabView);
        //start add data
        List<TabViewChild> tabViewChildList = new ArrayList<>();
        TabViewChild tabViewChild01 = new TabViewChild("BBI", SignalSettingFragment.newInstance("BBI"));
        TabViewChild tabViewChild02 = new TabViewChild("BOLL", SignalSettingFragment.newInstance("BOLL"));
        TabViewChild tabViewChild03 = new TabViewChild("MA", SignalSettingFragment.newInstance("MA"));
        TabViewChild tabViewChild04 = new TabViewChild("MIKE", SignalSettingFragment.newInstance("MIKE"));
        TabViewChild tabViewChild05 = new TabViewChild("PBX", SignalSettingFragment.newInstance("PBX"));
        TabViewChild tabViewChild06 = new TabViewChild("ARBR", SignalSettingFragment.newInstance("ARBR"));
        TabViewChild tabViewChild07 = new TabViewChild("ATR", SignalSettingFragment.newInstance("ATR"));
        TabViewChild tabViewChild08 = new TabViewChild("BIAS", SignalSettingFragment.newInstance("BIAS"));
        TabViewChild tabViewChild09 = new TabViewChild("CCI", SignalSettingFragment.newInstance("CCI"));
        TabViewChild tabViewChild10 = new TabViewChild("DKBY", SignalSettingFragment.newInstance("DKBY"));
        TabViewChild tabViewChild11 = new TabViewChild("KD", SignalSettingFragment.newInstance("KD"));
        TabViewChild tabViewChild12 = new TabViewChild("KDJ", SignalSettingFragment.newInstance("KDJ"));
        TabViewChild tabViewChild13 = new TabViewChild("LW&R", SignalSettingFragment.newInstance("LW&R"));
        TabViewChild tabViewChild14 = new TabViewChild("MACD", SignalSettingFragment.newInstance("MACD"));
        TabViewChild tabViewChild15 = new TabViewChild("QHLSR", SignalSettingFragment.newInstance("QHLSR"));
        TabViewChild tabViewChild16 = new TabViewChild("RSI", SignalSettingFragment.newInstance("RSI"));
        TabViewChild tabViewChild17 = new TabViewChild("WR", SignalSettingFragment.newInstance("WR"));

        tabViewChildList.add(tabViewChild01);
        tabViewChildList.add(tabViewChild02);
        tabViewChildList.add(tabViewChild03);
        tabViewChildList.add(tabViewChild04);
        tabViewChildList.add(tabViewChild05);
        tabViewChildList.add(tabViewChild06);
        tabViewChildList.add(tabViewChild07);
        tabViewChildList.add(tabViewChild08);
        tabViewChildList.add(tabViewChild09);
        tabViewChildList.add(tabViewChild10);
        tabViewChildList.add(tabViewChild11);
        tabViewChildList.add(tabViewChild12);
        tabViewChildList.add(tabViewChild13);
        tabViewChildList.add(tabViewChild14);
        tabViewChildList.add(tabViewChild15);
        tabViewChildList.add(tabViewChild16);
        tabViewChildList.add(tabViewChild17);

        KOperationNoteBean operationNoteBean = MyApplication.operationNoteBean;
        String setName;
        int type = getIntent().getIntExtra("type",0);
        if (type==1){
            setName = operationNoteBean.getMainName();
        } else {
            setName = operationNoteBean.getChildName();
        }
        for (int i=0;i<tabViewChildList.size();i++){
            TabViewChild tabViewChild = tabViewChildList.get(i);
            String text = tabViewChild.getTextViewText();
            if (text.equals(setName)){
                tabView.setTabViewDefaultPosition(i);
            }
        }
        tabView.setTabViewChild(tabViewChildList, getSupportFragmentManager());
        tabView.setOnTabChildClickListener(new TabView.OnTabChildClickListener() {
            @Override
            public void onTabChildClick(int position, ImageView currentImageIcon, TextView currentTextView) {
                // Toast.makeText(getApplicationContext(),"position:"+position,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initTopBar() {
        mTopbarView.setMode(TopBarView.MODE_BACK);
        mTopbarView.setTitle("指标设置");
        mTopbarView.setActionButton(R.drawable.ic_custom_service_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KefuLoginActivity.actionStart(SignalSettingActivity.this);
            }
        });
        mTopbarView.setBackButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignalSettingActivity.this.setResult(101,null);
                // 结束SelectCityActivity
                SignalSettingActivity.this.finish();
            }
        });
    }

    public void onBackPressed() {
        // super.onBackPressed();
        SignalSettingActivity.this.setResult(101,null);
        // 结束SelectCityActivity
        SignalSettingActivity.this.finish();
    }

}

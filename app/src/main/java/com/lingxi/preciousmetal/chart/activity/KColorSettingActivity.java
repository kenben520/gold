package com.lingxi.preciousmetal.chart.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lingxi.common.base.BaseApplication;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;
import com.lingxi.preciousmetal.ui.activity.SignalSettingActivity;
import com.lingxi.preciousmetal.ui.widget.TopBarView;
import com.lingxi.preciousmetal.util.utilCode.SharedPreferencesHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class KColorSettingActivity extends TranslucentStatusBarActivity {

    @BindView(R.id.set_red_up_layout)
    RelativeLayout setRedUpLayout;
    @BindView(R.id.set_green_up_layout)
    RelativeLayout setGreenUpLayout;
    @BindView(R.id.set_red_up_imageView)
    ImageView setRedUpImageView;
    @BindView(R.id.set_green_up_imageView)
    ImageView setGreenUpImageView;
    private SharedPreferencesHelper sharedPreferencesHelper;
    private int colorIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kcolor_setting);
        ButterKnife.bind(this);
        initTopBar();
        sharedPreferencesHelper = new SharedPreferencesHelper(mContext, "kColorSet");
        colorIndex = (int) sharedPreferencesHelper.getSharedPreference("kType", 0);
        if (colorIndex==0){
            setGreenUpImageView.setVisibility(View.GONE);
            setRedUpImageView.setVisibility(View.VISIBLE);
        } else {
            setGreenUpImageView.setVisibility(View.VISIBLE);
            setRedUpImageView.setVisibility(View.GONE);
        }
    }

    public static void startMyActivity(Context context) {
        Intent intent = new Intent(context, KColorSettingActivity.class);
        Activity activity = (Activity)context;
        activity.startActivity(intent);
    }

    public static void startMyActivity2(Context activity1) {
        Intent intent = new Intent(activity1, KColorSettingActivity.class);
        Activity activity = (Activity)activity1;
        activity.startActivityForResult(intent, 102);
    }

    private void initTopBar() {
        TopBarView mTopBarView = findViewById(R.id.topbar_view);
        mTopBarView.setMode(TopBarView.MODE_BACK);
        mTopBarView.setTitle("涨跌颜色设置");
        mTopBarView.setBackButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KColorSettingActivity.this.setResult(102,null);
                finish();
            }
        });
    }

    @OnClick({R.id.set_red_up_layout, R.id.set_green_up_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.set_red_up_layout:
                BaseApplication.kIndexColor = 0;
                sharedPreferencesHelper.put("kType",0);
                setGreenUpImageView.setVisibility(View.GONE);
                setRedUpImageView.setVisibility(View.VISIBLE);
                break;
            case R.id.set_green_up_layout:
                BaseApplication.kIndexColor = 1;
                sharedPreferencesHelper.put("kType",1);
                setGreenUpImageView.setVisibility(View.VISIBLE);
                setRedUpImageView.setVisibility(View.GONE);
                break;
        }
    }
}

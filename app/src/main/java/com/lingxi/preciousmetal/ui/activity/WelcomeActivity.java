package com.lingxi.preciousmetal.ui.activity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.BaseActivity;
import com.lingxi.preciousmetal.common.CommonParam;
import com.lingxi.preciousmetal.util.DisplayUtil;
import com.lingxi.preciousmetal.util.glideutils.GlideUtils;
import com.lingxi.preciousmetal.util.utilCode.SPUtils;
import com.lingxi.preciousmetal.util.utilCode.StringUtils;

import butterknife.BindView;

public class WelcomeActivity extends BaseActivity {
    @BindView(R.id.iv_welcome_page)
    ImageView ivWelcomePage;
    @BindView(R.id.iv_ad_page)
    ImageView ivAdPage;
    private Point point;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_welcome);
        point = DisplayUtil.getScreenSize(this);
        initView();
        initListener();
//        getAdvertisement();
        showAdvertisementImage();
    }

    private void initView() {
    }

    private void initListener() {
    }

    public void getAdvertisement() {
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void showAdvertisementImage() {
        String imageUrl = SPUtils.getInstance().getString(CommonParam.ADVERTISEMENT_IMAGE_URL);
        if (!StringUtils.isEmpty(imageUrl)) {
            GlideUtils.loadImageViewSize(this,imageUrl,point.x, point.y,ivAdPage);
            ivWelcomePage.setVisibility(View.INVISIBLE);
            ivAdPage.setVisibility(View.VISIBLE);
        } else {
            gotoNextActivity();
        }
    }

    private void gotoNextActivity() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

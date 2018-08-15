package com.lingxi.preciousmetal.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.GetAdRequestMo;
import com.lingxi.biz.domain.responseMo.AdMo;
import com.lingxi.biz.service.CommonService;
import com.lingxi.common.base.BaseApplication;
import com.lingxi.common.util.StringUtil;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.ui.widget.RoundView;
import com.lingxi.preciousmetal.util.DisplayUtil;
import com.lingxi.preciousmetal.util.glideutils.GlideUtils;
import com.lingxi.preciousmetal.util.utilCode.ObjectUtils;
import com.lingxi.preciousmetal.util.utilCode.SPUtils;
import com.lingxi.preciousmetal.util.utilCode.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.lingxi.preciousmetal.common.CommonParam.ADVERTISEMENT_SPLASH;
import static com.lingxi.preciousmetal.common.CommonParam.FIRST_USE;

/**
 * 启动界面
 * Created by zhangwei on 2018/5/7.
 */
public class SplashActivity extends Activity {
    @BindView(R.id.iv_ad)
    ImageView ivAd;
    @BindView(R.id.layout_ad)
    LinearLayout layoutAd;
    @BindView(R.id.round_view)
    RoundView roundView;
    @BindView(R.id.layout_splash)
    LinearLayout layoutSplash;
    private long START_TIME = 2000;
    private Handler mHandler = new Handler();
    boolean firstUse;
    private Point point;
    private int isClickPass = 0;
    private int isClickAD = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StatusBarUtil.setTransparent(this);
        setContentView(R.layout.activity_launcher);
        ButterKnife.bind(this);
        point = DisplayUtil.getScreenSize(this);
        firstUse = SPUtils.getInstance().getBoolean(FIRST_USE, true);
        initData();
    }

    private void initData() {
        GetAdRequestMo liveListRequestMo = new GetAdRequestMo(2);
        CommonService.getAd(liveListRequestMo, new BizResultListener<AdMo>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, AdMo adMo) {

            }

            @Override
            public void onSuccess(AdMo adMo) {
                if (!isFinishing()) {
                    if (ObjectUtils.isEmpty(adMo)||StringUtils.isEmpty(adMo.getImage())) {
                        SPUtils.getInstance().put(ADVERTISEMENT_SPLASH, "");
                    } else {
                        String imageUrl = adMo.getImage();
                        SPUtils.getInstance().put(ADVERTISEMENT_SPLASH, BaseApplication.getInstance().mCustomJsonConverter.toJson(adMo));
                        GlideUtils.preload(BaseApplication.getInstance(), imageUrl, point.x, point.y);
                    }
                }
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        start();
    }

    /**
     * 启动应用
     */
    private void start() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!firstUse) {
                    showAdvertisementImage();
                } else {
                    jumpToGuide();
                }
            }
        }, START_TIME);
    }

    boolean needShowAd = true;
    AdMo adMo;
    private void showAdvertisementImage() {
        String adStr = SPUtils.getInstance().getString(ADVERTISEMENT_SPLASH);
        if (!StringUtil.isEmpty(adStr) && needShowAd) {
            adMo = BaseApplication.getInstance().mCustomJsonConverter.parseJson(adStr, AdMo.class);
            if (!ObjectUtils.isEmpty(adMo) && !ObjectUtils.isEmpty(adMo.getImage())) {
                String imageUrl = adMo.getImage();
//                GlideUtils.loadImageViewSizeCenterInside(this,R.drawable.bg_splash, imageUrl, point.x, point.y, ivAd);
                GlideUtils.loadImageViewLodingSize(this,imageUrl, point.x, point.y, ivAd,R.drawable.bg_splash,R.drawable.bg_splash);
                layoutSplash.setVisibility(View.INVISIBLE);
                layoutAd.setVisibility(View.VISIBLE);
                roundView.setVisibility(View.VISIBLE);
                roundView.setCountDownTimerListener(new RoundView.CountDownTimerListener() {
                    @Override
                    public void onStartCount() {// 开始
                    }

                    @Override
                    public void onFinishCount() {// 结束
                        if (isClickAD == 0) {
                            jumpToHome();
                        }
                    }
                });
                ivAd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!ObjectUtils.isEmpty(adMo)&&!StringUtils.isEmpty(adMo.getLink())&&!isFinishing()){
                            isClickAD = 1;
                            jumpToHome();
                            WebViewActivity.actionStart(SplashActivity.this,adMo.getLink());
                            finish();
                        }
                    }
                });
                roundView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isClickPass == 0) {
                            isClickPass = 1;
                            jumpToHome();
                        }
                    }
                });
                roundView.start();
                return;
            }
        }
        jumpToHome();
    }

    private void jumpToHome() {
        startActivity(new Intent(SplashActivity.this, GoldMainActivity.class));
        finish();
    }

    private void jumpToGuide() {
        startActivity(new Intent(SplashActivity.this, AppGuideActivity.class));
        finish();
    }


    @Override
    protected void onDestroy() {
        roundView.closeTimer();
        super.onDestroy();
    }

}

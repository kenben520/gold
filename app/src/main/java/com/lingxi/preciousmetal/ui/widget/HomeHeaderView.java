package com.lingxi.preciousmetal.ui.widget;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lingxi.biz.domain.responseMo.HomeAllResultBean;
import com.lingxi.biz.domain.responseMo.NewRecomBean;
import com.lingxi.biz.util.ConstantUtil;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.MyApplication;
import com.lingxi.preciousmetal.chart.activity.ChartActivity;
import com.lingxi.preciousmetal.ui.activity.PolyvLivePlayerActivity;
import com.lingxi.preciousmetal.ui.activity.RegisterActivity;
import com.lingxi.preciousmetal.ui.activity.SignalParamsActivity;
import com.lingxi.preciousmetal.ui.activity.WebViewActivity;
import com.lingxi.preciousmetal.ui.activity.specialist.SpecialListMainActivity;
import com.lingxi.preciousmetal.ui.activity.study.KnowledgeCateActivity;
import com.lingxi.preciousmetal.ui.fragment.news.NewsMainActivity;
import com.lingxi.preciousmetal.util.glideutils.GlideUtils;
import com.lingxi.preciousmetal.util.utilCode.AppViewUtils;
import com.lingxi.preciousmetal.util.utilCode.TimeUtils;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.lingxi.preciousmetal.ui.activity.SignalParamsActivity.GOLD_TYPE;
import static com.lingxi.preciousmetal.ui.activity.SignalParamsActivity.SILVER_TYPE;

public class HomeHeaderView extends RelativeLayout {

    @BindView(R.id.advertising_banner_view)
    Banner advertisingBannerView;
    @BindView(R.id.market_gold_name_text)
    TextView marketGoldNameText;
    @BindView(R.id.market_gold_price_text)
    TextView marketGoldPriceText;
    @BindView(R.id.market_gold_percent_text)
    TextView marketGoldPercentText;
    @BindView(R.id.market_gold_layout)
    LinearLayout marketGoldLayout;
    @BindView(R.id.market_silver_name_text)
    TextView marketSilverNameText;
    @BindView(R.id.market_silver_price_text)
    TextView marketSilverPriceText;
    @BindView(R.id.market_silver_percent_text)
    TextView marketSilverPercentText;
    @BindView(R.id.market_silver_layout)
    LinearLayout marketSilverLayout;
    @BindView(R.id.market_us_name_text)
    TextView marketUsNameText;
    @BindView(R.id.market_us_price_text)
    TextView marketUsPriceText;
    @BindView(R.id.market_us_percent_text)
    TextView marketUsPercentText;
    @BindView(R.id.market_us_layout)
    LinearLayout marketUsLayout;
    @BindView(R.id.btn_home_news_message)
    TextView btnHomeNewsMessage;
    @BindView(R.id.btn_home_sole_strategy)
    TextView btnHomeSoleStrategy;
    @BindView(R.id.btn_home_register)
    TextView btnHomeRegister;
    @BindView(R.id.btn_home_study)
    TextView btnHomeStudy;
    @BindView(R.id.btn_money_calendar)
    TextView btnMoneyCalendar;
    @BindView(R.id.online_teacher_imageView)
    ImageView onlineTeacherImageView;
    @BindView(R.id.online_name_view)
    TextView onlineNameView;
    @BindView(R.id.online_time_view)
    TextView onlineTimeView;
    @BindView(R.id.video_play_layout)
    RelativeLayout videoPlayLayout;
    @BindView(R.id.home_news_type_text)
    TextView homeNewsTypeText;
    @BindView(R.id.home_news_text)
    TextView homeNewsText;
    @BindView(R.id.layout_home_news_comment)
    LinearLayout layoutHomeNewsComment;
    @BindView(R.id.signal_gold_imageView)
    ImageView signalGoldImageView;
    @BindView(R.id.signal_gold_textView)
    TextView signalGoldTextView;
    @BindView(R.id.layout_signal_gold)
    RelativeLayout layoutSignalGold;
    @BindView(R.id.signal_silver_imageView)
    ImageView signalSilverImageView;
    @BindView(R.id.signal_silver_textView)
    TextView signalSilverTextView;
    @BindView(R.id.layout_signal_silver)
    RelativeLayout layoutSignalSilver;
    @BindView(R.id.myScrollingContent)
    NestedScrollView myScrollingContent;
    private Context mContext;

    private List<String> bannerList;
    private List<HomeAllResultBean.BannelBean> bannelListBean;
    private String expertWebUrl;

    public HomeHeaderView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public HomeHeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public HomeHeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        initView();
        initData();
    }

    private void initData() {
        bannelListBean = new ArrayList<>();
        bannerList = new ArrayList<>();
        advertisingBannerView.setBannerAnimation(null);
        //设置标题集合（当banner样式有显示title时）
//        banner_layout.setBannerTitles(titles);
        //设置自动轮播，默认为true
        advertisingBannerView.isAutoPlay(true);
        //设置轮播时间
        advertisingBannerView.setDelayTime(2500);
        //设置banner样式
//        banner_layout.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
//        banner_layout.setImageLoader(new GlideImageLoader());
//        //设置图片集合
//        banner_layout.setImages(bannerList);
        //设置指示器位置（当banner模式中有指示器时）
        advertisingBannerView.setIndicatorGravity(BannerConfig.CENTER);
//        banner_layout.start();
        advertisingBannerView.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                HomeAllResultBean.BannelBean bean =  bannelListBean.get(position);
                WebViewActivity.actionStart(mContext,bean.getB_jump_url());
            }
        });
    }

    public void showHomeData(HomeAllResultBean bean) {
        if (bean == null) {
            ToastUtils.showShort("数据有误");
            return;
        }
        List<HomeAllResultBean.MarketBean> marketList = bean.getMarket();
        if (marketList != null && marketList.size() >= 3) {
            MyApplication.marketList = marketList;
            AppViewUtils.setMarketValue(mContext, marketGoldNameText, marketGoldPriceText, marketGoldPercentText, marketList.get(0));
            AppViewUtils.setMarketValue(mContext, marketSilverNameText, marketSilverPriceText, marketSilverPercentText, marketList.get(1));
            AppViewUtils.setMarketValue(mContext, marketUsNameText, marketUsPriceText, marketUsPercentText, marketList.get(2));
        }
        HomeAllResultBean.ExpertBean expertBean = bean.getExpert();
        if (bean.getExpert() != null) {
            if (expertBean.getMorning() == 1) {
                homeNewsTypeText.setText("今日早评：");
            } else {
                homeNewsTypeText.setText("今日晚评：");
            }
            expertWebUrl = expertBean.getLink();
            homeNewsText.setText(expertBean.getTitle());
        }

        HomeAllResultBean.OnlineBean teachBean = bean.getOnline();
        if (teachBean != null) {
            String teacherBannel = teachBean.getTeacher_bannel();
            if (!TextUtils.isEmpty(teacherBannel)) {
                GlideUtils.loadImageView(mContext, teacherBannel, onlineTeacherImageView);
            }
            int isOnline = teachBean.getIs_online();
            String startTime = TimeUtils.millis2String(teachBean.getStart_time() * 1000, TimeUtils.FORMAT_HM);
            String endTime = TimeUtils.millis2String(teachBean.getEnd_time() * 1000, TimeUtils.FORMAT_HM);
            onlineNameView.setVisibility(View.VISIBLE);
            if (isOnline==1){
                String content =  "《"+teachBean.getTitle() + "》  " + teachBean.getTea_name()  + " " + startTime + "-" + endTime;
                onlineNameView.setText(content);
                onlineTimeView.setText(" . 正在直播");
            } else if (isOnline == 2) {
                onlineTimeView.setText("   暂无直播 ");
                String content =  "下节课：《"+teachBean.getTitle() + "》  " + teachBean.getTea_name() +  " "  + startTime + "-" + endTime;
                onlineNameView.setText(content);
            } else {
                onlineNameView.setVisibility(View.GONE);
                onlineTimeView.setText("  暂无直播  ");
            }
        }
        bannelListBean = bean.getBannel();
        if (bannelListBean != null) {
            if (bannerList != null && bannerList.size() <= 0) {
                for (HomeAllResultBean.BannelBean banner : bannelListBean) {
                    bannerList.add(banner.getB_image_url());
                }
                advertisingBannerView.setImages(bannerList);
                advertisingBannerView.setImageLoader(new GlideImageLoader());
                advertisingBannerView.start();
            }
        }
        /*   1 多云天气  2 多云转晴  3 晴朗天气 */
        HomeAllResultBean.XagusdBean xagusdBean = bean.getXagusd();
        if (xagusdBean != null) {
            signalGoldTextView.setText(xagusdBean.getWeather());
            int value = xagusdBean.getPos_xagusd_res();
            if (value==1){
                signalGoldImageView.setBackground(ContextCompat.getDrawable(mContext,R.drawable.lx_home_signal_cloudy));
            } else if (value==2){
                signalGoldImageView.setBackground(ContextCompat.getDrawable(mContext,R.drawable.lx_home_cloudy_sunny));
            } else if (value==3){
                signalGoldImageView.setBackground(ContextCompat.getDrawable(mContext,R.drawable.lx_home_sunny));
            }
        }
        HomeAllResultBean.XauusdBean xauusdBean = bean.getXauusd();
        if (xauusdBean != null) {
            int value = xauusdBean.getPos_xauusd_res();
            signalSilverTextView.setText(xauusdBean.getWeather());
            if (value==1){
                signalSilverImageView.setBackground(ContextCompat.getDrawable(mContext,R.drawable.lx_home_signal_cloudy));
            } else if (value==2){
                signalSilverImageView.setBackground(ContextCompat.getDrawable(mContext,R.drawable.lx_home_cloudy_sunny));
            } else if (value==3){
                signalSilverImageView.setBackground(ContextCompat.getDrawable(mContext,R.drawable.lx_home_sunny));
            }
        }
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.fragment_main_home, this);
    }

    @OnClick({R.id.btn_home_news_message, R.id.btn_home_sole_strategy, R.id.btn_home_register, R.id.btn_home_study,
            R.id.btn_money_calendar, R.id.layout_home_news_comment, R.id.video_play_layout,
            R.id.market_us_layout, R.id.market_gold_layout, R.id.market_silver_layout, R.id.layout_signal_gold, R.id.layout_signal_silver})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_home_news_message:
                NewsMainActivity.startMyActivity(mContext, 1);
                break;
            case R.id.btn_home_sole_strategy:
                SpecialListMainActivity.startMyActivity(mContext);
                break;
            case R.id.btn_home_register:
                RegisterActivity.actionStart(mContext);
                break;
            case R.id.btn_home_study:
                mContext.startActivity(new Intent(mContext, KnowledgeCateActivity.class));
                break;
            case R.id.btn_money_calendar:
                WebViewActivity.actionStart(mContext, ConstantUtil.URLCALENDAR);
                break;
            case R.id.layout_home_news_comment:
                //早晚评
                WebViewActivity.actionStart(mContext, expertWebUrl);
                break;
            case R.id.video_play_layout:
                PolyvLivePlayerActivity.actionStart(mContext);
                break;
            case R.id.market_us_layout:
                ChartActivity.startMyActivity(mContext, ConstantUtil.USDOLLARINDEX, 2);
                break;
            case R.id.market_gold_layout:
                ChartActivity.startMyActivity(mContext, ConstantUtil.XAUUSD, 0);
                break;
            case R.id.market_silver_layout:
                ChartActivity.startMyActivity(mContext, ConstantUtil.XAGUSD, 1);
                break;
            case R.id.layout_signal_gold:
                SignalParamsActivity.actionStart(mContext, GOLD_TYPE);
                break;
            case R.id.layout_signal_silver:
                SignalParamsActivity.actionStart(mContext, SILVER_TYPE);
                break;
        }
    }


}

package com.lingxi.preciousmetal.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.github.tifezh.kchartlib.utils.ViewUtil;
import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.GetAdRequestMo;
import com.lingxi.biz.domain.requestMo.HomeAllRequestMo;
import com.lingxi.biz.domain.requestMo.KMarketRequestMo;
import com.lingxi.biz.domain.responseMo.AdMo;
import com.lingxi.biz.domain.responseMo.HomeAllResultBean;
import com.lingxi.biz.domain.responseMo.KMarketResultBean;
import com.lingxi.biz.service.CommonService;
import com.lingxi.biz.service.KMarketService;
import com.lingxi.biz.service.UserService;
import com.lingxi.biz.util.ConstantUtil;
import com.lingxi.common.base.BaseApplication;
import com.lingxi.common.base.BaseFragment;
import com.lingxi.common.util.StringUtil;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.MyApplication;
import com.lingxi.preciousmetal.chart.activity.ChartActivity;
import com.lingxi.preciousmetal.login.LoginHelper;
import com.lingxi.preciousmetal.ui.activity.GoldMainActivity;
import com.lingxi.preciousmetal.ui.activity.PolyvLivePlayerActivity;
import com.lingxi.preciousmetal.ui.activity.RegisterActivity;
import com.lingxi.preciousmetal.ui.activity.SignalParamsActivity;
import com.lingxi.preciousmetal.ui.activity.WebViewActivity;
import com.lingxi.preciousmetal.ui.activity.kefu.KefuLoginActivity;
import com.lingxi.preciousmetal.ui.activity.specialist.SpecialListMainActivity;
import com.lingxi.preciousmetal.ui.activity.study.KnowledgeCateActivity;
import com.lingxi.preciousmetal.ui.activity.trade.PositionAnalyseActivity;
import com.lingxi.preciousmetal.ui.fragment.news.NewsMainActivity;
import com.lingxi.preciousmetal.ui.widget.Banner;
import com.lingxi.preciousmetal.ui.widget.GlideImageLoader;
import com.lingxi.preciousmetal.ui.widget.dialog.HomeAdDialog;
import com.lingxi.preciousmetal.util.glideutils.GlideUtils;
import com.lingxi.preciousmetal.util.utilCode.ActivityUtils;
import com.lingxi.preciousmetal.util.utilCode.AppUtils;
import com.lingxi.preciousmetal.util.utilCode.AppViewUtils;
import com.lingxi.preciousmetal.util.utilCode.LogUtils;
import com.lingxi.preciousmetal.util.utilCode.ObjectUtils;
import com.lingxi.preciousmetal.util.utilCode.SPUtils;
import com.lingxi.preciousmetal.util.utilCode.StringUtils;
import com.lingxi.preciousmetal.util.utilCode.TimeUtils;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.lingxi.preciousmetal.common.CommonParam.ADVERTISEMENT_HOME;
import static com.lingxi.preciousmetal.ui.activity.SignalParamsActivity.GOLD_TYPE;
import static com.lingxi.preciousmetal.ui.activity.SignalParamsActivity.SILVER_TYPE;

public class HomeFragment3 extends BaseFragment implements AdapterView.OnItemClickListener,View.OnClickListener{

    private  Banner advertisingBannerView;
    private  TextView marketGoldNameText,marketGoldPriceText,marketGoldPercentText,marketSilverNameText,marketSilverPriceText,marketSilverPercentText
            ,marketUsNameText,marketUsPriceText,marketUsPercentText,homeNewsText,signalSilverTextView,signalGoldTextView;
    private TextView online_title_view,onlineNameView;
    private TextView lx_user_textView,lx_order_textView,lx_time_textView,lx_feature_textView;
    private RoundTextView onlineTimeView;
    private List<String> bannerList;
    private NestedScrollView home_scrollView;
    private List<HomeAllResultBean.BannelBean> bannelListBean;
    private String expertWebUrl;
    private ImageView onlineTeacherImageView,signalGoldImageView,signalSilverImageView;
    private ImageView home_news_type_imageView;
    private boolean isFirstLoad;
    private View market_gold_layout,market_us_layout,market_silver_layout;
    private RefreshLayout refreshLayout;
    private HomeAdDialog homeAdDialog;
    private HomeAllResultBean.H5Bean h5Bean;

    public static HomeFragment3 newInstance(String text) {
        HomeFragment3 fragmentCommon = new HomeFragment3();
        Bundle bundle = new Bundle();
        bundle.putString("text", text);
        fragmentCommon.setArguments(bundle);
        return fragmentCommon;
    }

    private void showPriceView(List<HomeAllResultBean.MarketBean> marketList){
        if (marketList != null && marketList.size() >= 3) {
            MyApplication.marketList = marketList;
            AppViewUtils.setMarketValue(getActivity(), marketGoldNameText, marketGoldPriceText, marketGoldPercentText, marketList.get(0));
            AppViewUtils.setMarketValue(getActivity(), marketSilverNameText, marketSilverPriceText, marketSilverPercentText, marketList.get(1));
            AppViewUtils.setMarketValue(getActivity(), marketUsNameText, marketUsPriceText, marketUsPercentText, marketList.get(2));
        }
    }

    private void showHomeData(HomeAllResultBean bean) {
        if (bean == null) {
            ToastUtils.showShort("数据有误");
            return;
        }
        h5Bean = bean.getH5();
        showPriceView(bean.getMarket());
        HomeAllResultBean.ExpertBean expertBean = bean.getExpert();
        if (expertBean != null) {
            if (expertBean.getMorning() == 1) {
                home_news_type_imageView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.home_morning_icon));
            } else {
                home_news_type_imageView.setBackground(ContextCompat.getDrawable(mContext,R.drawable.home_night_icon));
            }
            expertWebUrl = expertBean.getLink();
            homeNewsText.setText(expertBean.getTitle());
        }
        HomeAllResultBean.AccumulativeBean accumulativeBean = bean.getAccumulative();
        if (accumulativeBean!=null){
           // private TextView lx_user_textView,lx_order_textView,lx_time_textView,lx_feature_textView;
            lx_user_textView.setText(accumulativeBean.getCustomer());
            lx_order_textView.setText(accumulativeBean.getOrder());
            lx_time_textView.setText(accumulativeBean.getRealtime());
            lx_feature_textView.setText(accumulativeBean.getCharacteristic());
        }

        HomeAllResultBean.OnlineBean teachBean = bean.getOnline();
        if (teachBean != null) {
            String teacherBannel = teachBean.getTeacher_bannel();
            if (!TextUtils.isEmpty(teacherBannel)) {
                GlideUtils.loadImageViewCrop(mContext, R.drawable.background_gray4, teacherBannel, onlineTeacherImageView);
            }
            int isOnline = teachBean.getIs_online();
            String startTime = TimeUtils.millis2String(teachBean.getStart_time() * 1000, TimeUtils.FORMAT_HM);
            String endTime = TimeUtils.millis2String(teachBean.getEnd_time() * 1000, TimeUtils.FORMAT_HM);

            String title = teachBean.getTitle();
            if (!TextUtils.isEmpty(title) && title.length()>7){
                title = title.substring(0,7)+"...";
            }
            String tea_name = teachBean.getTea_name();
            if (!TextUtils.isEmpty(tea_name) && tea_name.length()>4){
                tea_name = tea_name.substring(0,4)+"...";
            }
            String name = tea_name  + "(" + startTime + "-" + endTime+")";
            if (isOnline==1){
                onlineTimeView.setText("正在直播");
                Drawable drawable = ContextCompat.getDrawable(mContext,R.drawable.home_play_icon);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                online_title_view.setCompoundDrawables(null,null,drawable,null);
            } else if (isOnline == 3) {
                title = "预告-"+ title;
                online_title_view.setCompoundDrawables(null,null,null,null);
                onlineTimeView.setText("暂无直播");
            } else {
                online_title_view.setCompoundDrawables(null,null,null,null);
                title = "暂无课程安排";
                name = "----   ----";
                onlineTimeView.setText("暂无直播");
            }
            onlineNameView.setText(name);
            online_title_view.setText(title);
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
        HomeAllResultBean.XagusdBean xauusdBean = bean.getXagusd();
        if (xauusdBean != null) {
            int value = xauusdBean.getPos_xagusd_res();
            AppViewUtils.setHomeTextViewColor(mContext,xauusdBean.getWeather(),signalSilverTextView);
            if (value==1){
                signalSilverImageView.setBackground(ContextCompat.getDrawable(mContext,R.drawable.lx_home_signal_cloudy));
            } else if (value==2){
                signalSilverImageView.setBackground(ContextCompat.getDrawable(mContext,R.drawable.lx_home_cloudy_sunny));
            } else if (value==3){
                signalSilverImageView.setBackground(ContextCompat.getDrawable(mContext,R.drawable.lx_home_sunny));
            }
        }
        HomeAllResultBean.XauusdBean xagusdBean = bean.getXauusd();
        if (xauusdBean != null) {
            AppViewUtils.setHomeTextViewColor(mContext,xagusdBean.getWeather(),signalGoldTextView);
            int value = xagusdBean.getPos_xauusd_res();
            if (value==1){
                signalGoldImageView.setBackground(ContextCompat.getDrawable(mContext,R.drawable.lx_home_signal_cloudy));
            } else if (value==2){
                signalGoldImageView.setBackground(ContextCompat.getDrawable(mContext,R.drawable.lx_home_cloudy_sunny));
            } else if (value==3){
                signalGoldImageView.setBackground(ContextCompat.getDrawable(mContext,R.drawable.lx_home_sunny));
            }
        }

    }
    public  boolean fragmentIsVisible=false;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        fragmentIsVisible=isVisibleToUser;
        if (getUserVisibleHint()) {
            if (isFirstLoad) {
                loadData();
            }
        }
    }

    private void loadData() {
        HomeAllRequestMo investmentRequestMo = new HomeAllRequestMo();
        UserService.homeAllApp(investmentRequestMo, new BizResultListener<HomeAllResultBean>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, HomeAllResultBean bean) {
                isFirstLoad = true;
                showHomeData(bean);
//                refreshLayout.finishLoadMoreWithNoMoreData();
                refreshLayout.finishLoadMore(false);
                refreshLayout.setEnableLoadMore(false);
            }

            @Override
            public void onSuccess(HomeAllResultBean bean) {
                isFirstLoad = true;
                showHomeData(bean);
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                 refreshLayout.finishLoadMore(false);
                refreshLayout.setEnableLoadMore(false);
                LogUtils.i("首页请求失败");
            }
        });
    }

    private int mScrollY = 0;

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshLayout =  view.findViewById(R.id.refreshLayout);
        initView(view);
        initData();
        showAdvertisementImage();
        getAd();
        if (!isFirstLoad){
            //模拟刷新数据
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            refreshData();
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }

    String usColor="",gdColor="",sdColor = "";

    private void refreshData() {
        KMarketRequestMo requestMo =  new KMarketRequestMo();
        KMarketService.kMarketApp(requestMo,new BizResultListener<KMarketResultBean>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, KMarketResultBean resultBean) {

            }

            @Override
            public void onSuccess(KMarketResultBean resultBean) {
                int index = 0;
                if (resultBean==null){
                    return;
                }
                List<KMarketResultBean.MarketBean> tempList = resultBean.market;
                if (MyApplication.marketList==null || MyApplication.marketList.size()<=0){
                   return;
                }
                for (int i=0;i<tempList.size();i++){
                    KMarketResultBean.MarketBean nowBean = tempList.get(i);
                    HomeAllResultBean.MarketBean oldBean = MyApplication.marketList.get(i);

                    float nowPx = nowBean.last_px;
                    float oldPx = oldBean.getLast_px();

                    int flagIndex = 0;
                    String color = "";
                    if (nowPx > oldPx) {
                        flagIndex = 1;
                        color = ViewUtil.getKUpBgColor(mContext);
                    } else if (nowPx < oldPx) {
                        flagIndex = 2;
                        color = ViewUtil.getKLossBgColor(mContext);
                    } else {
                        flagIndex = 0;
                    }
                    if (index==0){
                        gdColor = color;
                    }
                    if (index==1){
                        sdColor = color;
                    }
                    if (index==2){
                        usColor = color;
                    }
                    index++;
                    oldBean.flagKUp = flagIndex;
                    oldBean.setLast_px(nowPx);
                }
                onlineTimeView.post(new Runnable() {
                    @Override
                    public void run() {
                        AppViewUtils.setUpAnimator(mContext, market_us_layout,usColor);
                        AppViewUtils.setUpAnimator(mContext, market_silver_layout,sdColor);
                        AppViewUtils.setUpAnimator(mContext, market_gold_layout,gdColor);
                        showPriceView(MyApplication.marketList);
                    }
                });
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
            }
        });
    }

    @SuppressLint("CutPasteId")
    private void initView(View view){
        advertisingBannerView = view.findViewById(R.id.advertising_banner_view);
        marketGoldNameText =  view.findViewById(R.id.market_gold_name_text);
        marketGoldPriceText =  view.findViewById(R.id.market_gold_price_text);
        marketGoldPercentText =  view.findViewById(R.id.market_gold_percent_text);

        marketSilverNameText =  view.findViewById(R.id.market_silver_name_text);
        marketSilverPriceText =  view.findViewById(R.id.market_silver_price_text);
        marketSilverPercentText =  view.findViewById(R.id.market_silver_percent_text);

        marketUsNameText =  view.findViewById(R.id.market_us_name_text);
        marketUsPriceText =  view.findViewById(R.id.market_us_price_text);
        marketUsPercentText =  view.findViewById(R.id.market_us_percent_text);

        onlineTeacherImageView =  view.findViewById(R.id.online_teacher_imageView);
        onlineNameView =  view.findViewById(R.id.online_name_view);
        onlineTimeView =  view.findViewById(R.id.online_time_view);
        homeNewsText =  view.findViewById(R.id.home_news_text);
        online_title_view =  view.findViewById(R.id.online_title_view);
//        onlineTimeView.setAlpha(0.8f);
        home_news_type_imageView =  view.findViewById(R.id.home_news_type_imageView);
        signalGoldImageView =  view.findViewById(R.id.signal_gold_imageView);
        signalGoldTextView =  view.findViewById(R.id.signal_gold_textView);
        signalSilverImageView =  view.findViewById(R.id.signal_silver_imageView);
        signalSilverTextView =  view.findViewById(R.id.signal_silver_textView);

        view.findViewById(R.id.btn_home_news_message).setOnClickListener(this);
        view.findViewById(R.id.btn_home_sole_strategy).setOnClickListener(this);
        view.findViewById(R.id.btn_home_register).setOnClickListener(this);
        view.findViewById(R.id.btn_home_study).setOnClickListener(this);
        view.findViewById(R.id.btn_money_calendar).setOnClickListener(this);
        view.findViewById(R.id.layout_home_news_comment).setOnClickListener(this);
        view.findViewById(R.id.video_play_layout).setOnClickListener(this);
        market_gold_layout = view.findViewById(R.id.market_gold_layout);
        market_gold_layout.setOnClickListener(this);
        market_us_layout = view.findViewById(R.id.market_us_layout);
        market_us_layout.setOnClickListener(this);
        market_silver_layout = view.findViewById(R.id.market_silver_layout);
        market_silver_layout.setOnClickListener(this);

        view.findViewById(R.id.layout_signal_gold).setOnClickListener(this);
        view.findViewById(R.id.layout_signal_silver).setOnClickListener(this);
        view.findViewById(R.id.position_analysis_layout).setOnClickListener(this);
        view.findViewById(R.id.home_data_layout).setOnClickListener(this);
        view.findViewById(R.id.home_platform_layout).setOnClickListener(this);
        view.findViewById(R.id.home_advantage_layout).setOnClickListener(this);
        view.findViewById(R.id.home_online_qq_view).setOnClickListener(this);

        lx_user_textView = view.findViewById(R.id.lx_user_textView);
        lx_order_textView = view.findViewById(R.id.lx_order_textView);
        lx_time_textView = view.findViewById(R.id.lx_time_textView);
        lx_feature_textView = view.findViewById(R.id.lx_feature_textView);
        home_scrollView = view.findViewById(R.id.home_scrollView);
        lx_toolbar = view.findViewById(R.id.lx_toolbar);
        lx_title = view.findViewById(R.id.lx_title);
        AppUtils.setCustomFont(mContext,lx_user_textView);
        AppUtils.setCustomFont(mContext,lx_order_textView);
        AppUtils.setCustomFont(mContext,lx_time_textView);
        AppUtils.setCustomFont(mContext,lx_feature_textView);
    }

    RelativeLayout lx_toolbar;
    TextView lx_title;

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
        advertisingBannerView.setIndicatorGravity(BannerConfig.CENTER);
//        banner_layout.start();
        advertisingBannerView.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                HomeAllResultBean.BannelBean bean =  bannelListBean.get(position);
                WebViewActivity.actionStart(getActivity(),bean.getB_jump_url());
            }
        });

        refreshLayout.setEnableAutoLoadMore(true);//开启自动加载功能（非必须）
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().post(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefresh();
                        refreshLayout.setNoMoreData(false);
                        loadData();
                    }
                });
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().post(new Runnable() {
                    @Override
                    public void run() {
                        loadData();
                    }
                });
            }
        });
        lx_title.setAlpha(0);
        refreshLayout.autoRefresh();
        home_scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            private int lastScrollY = 0;
            private int h = (int)DensityUtil.dp2px(40);
           // private int color = ContextCompat.getColor(mContext, R.color.colorPrimary)&0x00ffffff;

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (lastScrollY < h) {
                    scrollY = Math.min(h, scrollY);
                    mScrollY = scrollY > h ? h : scrollY;
                    lx_title.setAlpha(1f * mScrollY / h);
                   // lx_toolbar.setAlpha(1f * mScrollY / h);
                  //  lx_toolbar.setBackgroundColor(((255 * mScrollY / h) << 24) | color);
//                    parallax.setTranslationY(mOffset - mScrollY);
                }
                lastScrollY = scrollY;
            }
        });
    }

    private void getAd() {
        GetAdRequestMo liveListRequestMo = new GetAdRequestMo(11);
        CommonService.getAd(liveListRequestMo, new BizResultListener<AdMo>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, AdMo adMo) {

            }

            @Override
            public void onSuccess(AdMo adMo) {
                String adStr = SPUtils.getInstance().getString(ADVERTISEMENT_HOME);
                AdMo adMoOld=null;
                if (!StringUtil.isEmpty(adStr)){
                    adMoOld = BaseApplication.getInstance().mCustomJsonConverter.parseJson(adStr, AdMo.class);
                }
                if (ObjectUtils.isEmpty(adMo)||StringUtils.isEmpty(adMo.getImage())) {
                        SPUtils.getInstance().put(ADVERTISEMENT_HOME, "");
                } else {
                        if(!ObjectUtils.isEmpty(adMoOld)&&!StringUtils.isEmpty(adMoOld.getId())&&adMoOld.getId().equals(adMo.getId())){
                            adMo.setShowTime(adMoOld.getShowTime());
                        }
                        SPUtils.getInstance().put(ADVERTISEMENT_HOME, BaseApplication.getInstance().mCustomJsonConverter.toJson(adMo));
                }
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {

            }
        });
    }

    AdMo adMo;
    boolean needShowAd = true;
    private static boolean hasShowAd=false;
    private void showAdvertisementImage() {
        String adStr = SPUtils.getInstance().getString(ADVERTISEMENT_HOME);
        if (!StringUtil.isEmpty(adStr)&& needShowAd) {
            adMo = BaseApplication.getInstance().mCustomJsonConverter.parseJson(adStr, AdMo.class);
            if (!ObjectUtils.isEmpty(adMo) && !ObjectUtils.isEmpty(adMo.getImage())&&!hasShowAd&&fragmentIsVisible) {
                String imageUrl = adMo.getImage();
                if(!LoginHelper.getInstance().isLogin())//用户在未登录的情况下打开APP，则每次都出现广告弹窗；
                {
                    hasShowAd=true;
                    homeAdDialog = new HomeAdDialog(getContext(), imageUrl,adMo.getLink());
                    homeAdDialog.show();
//                    adMo.setShowTime(System.currentTimeMillis());
                    SPUtils.getInstance().put(ADVERTISEMENT_HOME, BaseApplication.getInstance().mCustomJsonConverter.toJson(adMo));
                }else {//用户在登录的情况下打开APP，同一个弹窗广告24小时内只弹出一次，不同弹窗广告24小时不受互相限制；
                    long lastTime=adMo.getShowTime();
                    if((System.currentTimeMillis()-lastTime)>24*60*60*1000){
                        hasShowAd=true;
                        homeAdDialog = new HomeAdDialog(getContext(), imageUrl,adMo.getLink());
                        homeAdDialog.show();
                        adMo.setShowTime(System.currentTimeMillis());
                        SPUtils.getInstance().put(ADVERTISEMENT_HOME, BaseApplication.getInstance().mCustomJsonConverter.toJson(adMo));
                    }
                }
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_hometest, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onDestroyView() {
        if (homeAdDialog != null) {
            if (homeAdDialog.isShowing()) homeAdDialog.dismiss();
        }
        super.onDestroyView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_home_news_message:
                NewsMainActivity.startMyActivity(getActivity(), 1);
                break;
            case R.id.btn_home_sole_strategy:
                SpecialListMainActivity.startMyActivity(mContext);
                break;
            case R.id.btn_home_register:
                RegisterActivity.actionStart(mContext);
                break;
            case R.id.btn_home_study:
                startActivity(new Intent(getActivity(), KnowledgeCateActivity.class));
                break;
            case R.id.btn_money_calendar:
                WebViewActivity.actionStart(getActivity(), ConstantUtil.URLCALENDAR);
                break;
            case R.id.layout_home_news_comment:
                //早晚评
                WebViewActivity.actionStart(getActivity(), expertWebUrl);
                break;
            case R.id.video_play_layout:
                PolyvLivePlayerActivity.actionStart(mContext);
                break;
            case R.id.market_us_layout:
                ChartActivity.startMyActivity(getActivity(), ConstantUtil.USDOLLARINDEX, 2);
                break;
            case R.id.market_gold_layout:
//                KColorSettingActivity.startMyActivity(mContext);
                ChartActivity.startMyActivity(getActivity(), ConstantUtil.XAUUSD, 0);
                break;
            case R.id.market_silver_layout:
                ChartActivity.startMyActivity(getActivity(), ConstantUtil.XAGUSD, 1);
                break;
            case R.id.layout_signal_gold:
                SignalParamsActivity.actionStart(getActivity(), GOLD_TYPE);
                break;
            case R.id.layout_signal_silver:
                SignalParamsActivity.actionStart(getActivity(), SILVER_TYPE);
                break;
            case R.id.position_analysis_layout:
                PositionAnalyseActivity.startMyActivity(getActivity());
                break;
            case R.id.home_advantage_layout:
                WebViewActivity.actionStart(getActivity(), h5Bean.getAdvantage());
                break;
            case R.id.home_platform_layout:
                WebViewActivity.actionStart(getActivity(), h5Bean.getPlatform());
                break;
            case R.id.home_data_layout:
                WebViewActivity.actionStart(getActivity(), h5Bean.getHistory());
                break;
            case R.id.home_online_qq_view:
                KefuLoginActivity.actionStart(mContext);
                break;

        }
    }
}

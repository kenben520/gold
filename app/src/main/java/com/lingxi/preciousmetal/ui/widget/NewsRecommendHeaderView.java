package com.lingxi.preciousmetal.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lingxi.biz.domain.responseMo.NewRecomBean;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.ui.activity.WebViewActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class NewsRecommendHeaderView extends RelativeLayout {
    private final static String TAG = NewsRecommendHeaderView.class.getName();
    @BindView(R.id.news_banner_view)
    Banner newsBannerView;
    @BindView(R.id.layout_header_view)
    RelativeLayout layoutHeaderView;
    @BindView(R.id.title)
    TextView title;
    private Context mContext;
    private List<String> bannerList, bannerTitleList;
    private List<NewRecomBean.BannelsBean> bannelsBeans;

    public NewsRecommendHeaderView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public NewsRecommendHeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public NewsRecommendHeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        initView();
        initData();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.new_recommend_header_view, this);
        newsBannerView = findViewById(R.id.news_banner_view);
        title = findViewById(R.id.title);
    }

    private void initData() {
        bannelsBeans = new ArrayList<>();
        bannerList = new ArrayList<>();
        bannerTitleList = new ArrayList<>();
        newsBannerView.setBannerAnimation(null);
        newsBannerView.isAutoPlay(true);
        //设置轮播时间
        newsBannerView.setDelayTime(2500);
        newsBannerView.setIndicatorGravity(BannerConfig.CENTER);
//        banner_layout.start();
        newsBannerView.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (position <= bannelsBeans.size()) {
                    NewRecomBean.BannelsBean bean = bannelsBeans.get(position);
                    WebViewActivity.actionStart(getContext(), bean.getLink(),bean.getTitle(),true);
                }
            }
        });
        newsBannerView.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position <= bannelsBeans.size()) {
                    NewRecomBean.BannelsBean bean = bannelsBeans.get(position);
                    if(title!=null)
                    {
                        title.setText(bean.getTitle());
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void loadData() {

    }

    public void refreshData(List<NewRecomBean.BannelsBean> tembBannelsBeans) {
        if (tembBannelsBeans != null && bannelsBeans.size() <= 0) {
            bannelsBeans.clear();
            bannerList.clear();
            bannerTitleList.clear();
            bannelsBeans = tembBannelsBeans;
            for (NewRecomBean.BannelsBean banner : bannelsBeans) {
                bannerList.add(banner.getImage());
                bannerTitleList.add(banner.getTitle());
            }
            newsBannerView.setImages(bannerList);
            newsBannerView.setImageLoader(new GlideImageLoader());
            newsBannerView.start();
            newsBannerView.titleViewVISIBLE();
            NewRecomBean.BannelsBean bean = bannelsBeans.get(0);
            title.setText(bean.getTitle());
        }
    }

}

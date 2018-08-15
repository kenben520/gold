package com.lingxi.preciousmetal.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.tifezh.kchartlib.utils.ViewUtil;
import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.KMarketRequestMo;
import com.lingxi.biz.domain.requestMo.MessQuickRequestMo;
import com.lingxi.biz.domain.responseMo.HomeAllResultBean;
import com.lingxi.biz.domain.responseMo.KMarketResultBean;
import com.lingxi.biz.domain.responseMo.MessQuickResultBean;
import com.lingxi.biz.service.KMarketService;
import com.lingxi.biz.service.NewsService;
import com.lingxi.common.base.BaseFragment;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.MyApplication;
import com.lingxi.preciousmetal.ui.adapter.FlashNewsAdapter;
import com.lingxi.preciousmetal.ui.adapter.base.CommonAdapter;
import com.lingxi.preciousmetal.util.utilCode.AppViewUtils;
import com.lingxi.preciousmetal.util.utilCode.LogUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FragmentVideoFlash extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.market_gold_name_text)
    TextView marketGoldNameText;
    @BindView(R.id.market_gold_price_text)
    TextView marketGoldPriceText;
    @BindView(R.id.market_silver_name_text)
    TextView marketSilverNameText;
    @BindView(R.id.market_silver_price_text)
    TextView marketSilverPriceText;
    @BindView(R.id.market_us_name_text)
    TextView marketUsNameText;
    @BindView(R.id.market_us_price_text)
    TextView marketUsPriceText;
    @BindView(R.id.video_flash_listView)
    ListView videoFlashListView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
    @BindView(R.id.market_gold_layout)
    LinearLayout marketGoldLayout;
    @BindView(R.id.market_silver_layout)
    LinearLayout marketSilverLayout;
    @BindView(R.id.market_us_layout)
    LinearLayout marketUsLayout;
    private View mContentView;

    private List<MessQuickResultBean.ItemsBean> flashList;
    private int pageSize, page;
    private CommonAdapter mAdapter;

    public static FragmentVideoFlash newInstance(String text) {
        FragmentVideoFlash fragmentCommon = new FragmentVideoFlash();
        Bundle bundle = new Bundle();
        bundle.putString("text", text);
        fragmentCommon.setArguments(bundle);
        return fragmentCommon;
    }

    @Override
    public void onViewCreated(@NonNull final View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mContentView == null) {
            mContentView = inflater.inflate(R.layout.fragment_video_flash, container, false);
        }
        unbinder = ButterKnife.bind(this, mContentView);
        return mContentView;
    }

    String usColor="",gdColor="",sdColor = "";

    private void initData() {
        pageSize = 20;
        showPriceView(MyApplication.marketList);

        flashList = new ArrayList<>();
        mAdapter = new FlashNewsAdapter(getActivity(), flashList, R.layout.adapter_flash_news);
        videoFlashListView.setDividerHeight(0);
        videoFlashListView.setAdapter(mAdapter);

        refreshLayout.setEnableAutoLoadMore(true);//开启自动加载功能（非必须）
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().post(new Runnable() {
                    @Override
                    public void run() {
                        flashList.clear();
                        mAdapter.notifyDataSetChanged();
                        page = 1;
                        pageSize = 20;
                        refreshLayout.finishRefresh();
                        refreshLayout.setNoMoreData(false);
                        requestData();
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
                        requestData();
                    }
                });
            }
        });
        refreshLayout.autoRefresh();
        //模拟刷新数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (isVisibleToUser){
                            refreshData();
                            Thread.sleep(3000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

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
                videoFlashListView.post(new Runnable() {
                    @Override
                    public void run() {
                        AppViewUtils.setUpAnimator(mContext, marketUsLayout,usColor);
                        AppViewUtils.setUpAnimator(mContext, marketSilverLayout,sdColor);
                        AppViewUtils.setUpAnimator(mContext, marketGoldLayout,gdColor);
                        showPriceView(MyApplication.marketList);
                    }
                });
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
            }
        });
    }

    private void showPriceView(List<HomeAllResultBean.MarketBean> marketList){
        if (marketList != null && marketList.size() >= 3) {
            AppViewUtils.setMarketValue(getActivity(), marketGoldNameText, marketGoldPriceText, null, marketList.get(0));
            AppViewUtils.setMarketValue(getActivity(), marketSilverNameText, marketSilverPriceText, null, marketList.get(1));
            AppViewUtils.setMarketValue(getActivity(), marketUsNameText, marketUsPriceText, null, marketList.get(2));
        }
    }

    private void requestData() {
        MessQuickRequestMo requestMo = new MessQuickRequestMo(pageSize, page);
        NewsService.newsFlashList(requestMo, new BizResultListener<MessQuickResultBean>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, MessQuickResultBean resultMo) {

            }

            @Override
            public void onSuccess(MessQuickResultBean resultMo) {
                if (isAdded() && !isHidden()) {
                    List<MessQuickResultBean.ItemsBean> list = resultMo.getItems();
                    if (resultMo.getCountpage() >= page) {
                        page++;
                        flashList.addAll(list);
                        mAdapter.notifyDataSetChanged();
                        refreshLayout.finishLoadMore();
                    } else {
                        refreshLayout.finishLoadMoreWithNoMoreData();
                    }
                }
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                LogUtils.i("要闻请求失败");
            }
        });
    }

    private boolean isVisibleToUser;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
    }

    @Override
    public void onStop() {
        super.onStop();
        isVisibleToUser = false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isVisibleToUser = false;
        unbinder.unbind();
    }
}

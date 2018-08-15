package com.lingxi.preciousmetal.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.GetAdRequestMo;
import com.lingxi.biz.domain.requestMo.KMarketRequestMo;
import com.lingxi.biz.domain.responseMo.AdMo;
import com.lingxi.biz.domain.responseMo.KMarketResultBean;
import com.lingxi.biz.service.CommonService;
import com.lingxi.biz.service.KMarketService;
import com.lingxi.common.base.BaseFragment;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.MyApplication;
import com.lingxi.preciousmetal.chart.activity.ChartActivity;
import com.lingxi.preciousmetal.login.LoginHelper;
import com.lingxi.preciousmetal.ui.activity.RegisterActivity;
import com.lingxi.preciousmetal.ui.activity.kefu.KefuLoginActivity;
import com.lingxi.preciousmetal.ui.adapter.KMarketAdapter;
import com.lingxi.preciousmetal.ui.adapter.base.CommonAdapter;
import com.lingxi.preciousmetal.ui.widget.TopBarView;
import com.lingxi.preciousmetal.util.glideutils.GlideUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/*专家点评
 */
public class KMarketFragment extends BaseFragment {

    public static KMarketFragment newInstance(String text){
        KMarketFragment fragmentCommon=new KMarketFragment();
        Bundle bundle=new Bundle();
        bundle.putString("text",text);
        fragmentCommon.setArguments(bundle);
        return fragmentCommon;
    }

    private RefreshLayout refreshLayout;
    private CommonAdapter mAdapter;
    private List<KMarketResultBean.MarketBean> kMarketList;
    private ListView kmarketListview;
    private ImageView market_ad_imageView;
    private boolean flagRest = true;

    private void initTopBar(View view) {
        TopBarView mTopBarView =  view.findViewById(R.id.topbar_view);
        mTopBarView.resetTopBarState();
        mTopBarView.setTitle("行情");
        mTopBarView.setActionButton(R.drawable.home_onlie_qq, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KefuLoginActivity.actionStart(mContext);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        kmarketListview = view.findViewById(R.id.kmarket_listview);
        kmarketListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KMarketResultBean.MarketBean bean =  kMarketList.get(position);
                ChartActivity.startMyActivity(getActivity(),bean.p_code,position);
            }
        });
        kMarketList = new ArrayList<>();
        mAdapter = new KMarketAdapter(getActivity(), kMarketList, R.layout.adapter_kmarket);
        kmarketListview.setAdapter(mAdapter);
        market_ad_imageView =  view.findViewById(R.id.market_ad_imageView);
        market_ad_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             RegisterActivity.actionStart(mContext);
            }
        });
        refreshLayout =  view.findViewById(R.id.refreshLayout);
        refreshLayout.setEnableAutoLoadMore(true);//开启自动加载功能（非必须）
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().post(new Runnable() {
                    @Override
                    public void run() {
                        flagRest = true;
                        refreshLayout.finishRefresh();
                        refreshLayout.setNoMoreData(false);
                        initData();
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
                        flagRest = false;
                        initData();
                    }
                });
            }
        });
        initTopBar(view);
        initData();
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
//        }).start();
        }).start();
    }

    boolean isVisibleToUser;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser) {
            if (isFirstLoad) {
                if (!LoginHelper.getInstance().isLogin()) {
                    getAd();
                } else {
                    market_ad_imageView.setVisibility(View.GONE);
                }
                initData();
            }
        }
    }

    private boolean isFirstLoad;

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kmarket,container,false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void getAd() {
        GetAdRequestMo liveListRequestMo = new GetAdRequestMo(16);
        CommonService.getAd(liveListRequestMo, new BizResultListener<AdMo>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, AdMo adMo) {

            }

            @Override
            public void onSuccess(AdMo adMo) {
                if (adMo==null){
                    return;
                }
                if (TextUtils.isEmpty(adMo.getImage())){
                    market_ad_imageView.setVisibility(View.GONE);
                } else {
                    market_ad_imageView.setVisibility(View.VISIBLE);
                    GlideUtils.loadImageViewCrop(mContext, R.drawable.background_gray4, adMo.getImage(), market_ad_imageView);
                }
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {

            }
        });
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
                List<KMarketResultBean.MarketBean> tempList =  resultBean.market;
                for (int i=0;i<tempList.size();i++){
                    KMarketResultBean.MarketBean nowBean = tempList.get(i);
                    KMarketResultBean.MarketBean oldBean = kMarketList.get(i);
                    float nowPx = nowBean.last_px;
                    float oldPx = oldBean.last_px;
                    if (nowPx>oldPx){
                        nowBean.flagKUp = 1;
                    } else if (nowPx<oldPx){
                        nowBean.flagKUp = 2;
                    } else {
                        // nowBean.flagKUp = 1;
                    }
                }
                kMarketList.clear();
                kMarketList.addAll(tempList);
                kmarketListview.post(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
            }
        });
    }

    private void initData() {
        KMarketRequestMo requestMo =  new KMarketRequestMo();
        KMarketService.kMarketApp(requestMo,new BizResultListener<KMarketResultBean>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, KMarketResultBean resultBean) {
                showData(resultBean);
            }

            @Override
            public void onSuccess(KMarketResultBean resultBean) {
                showData(resultBean);
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
            }
        });
    }

    private void showData(KMarketResultBean resultBean){
        isFirstLoad = true;
        if (resultBean!=null && resultBean.market!=null){
            if (flagRest){
                kMarketList.clear();
            }
            kMarketList.addAll(resultBean.market);
            MyApplication.marketBeanList = kMarketList;
            mAdapter.notifyDataSetChanged();
        }
        refreshLayout.finishLoadMore(false);
        refreshLayout.setEnableLoadMore(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (LoginHelper.getInstance().isLogin()) {
            market_ad_imageView.setVisibility(View.GONE);
        }
    }
}

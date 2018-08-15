package com.lingxi.preciousmetal.ui.fragment.news;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.NewsRecomRequestMo;
import com.lingxi.biz.domain.responseMo.HomeAllResultBean;
import com.lingxi.biz.domain.responseMo.NewRecomBean;
import com.lingxi.biz.service.NewsService;
import com.lingxi.common.base.BaseFragment;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.ui.activity.WebViewActivity;
import com.lingxi.preciousmetal.ui.adapter.NewRecommendAdapter;
import com.lingxi.preciousmetal.ui.widget.EssenceCourseHeaderView;
import com.lingxi.preciousmetal.ui.widget.GlideImageLoader;
import com.lingxi.preciousmetal.ui.widget.NewsRecommendHeaderView;
import com.lingxi.preciousmetal.util.utilCode.LogUtils;
import com.lingxi.preciousmetal.util.utilCode.ObjectUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.view.View.VISIBLE;

public class NewsRecommendFragment extends BaseFragment {
    @BindView(R.id.recommend_listView)
    ListView recommendListView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private Unbinder unbinder;
    private int size = 10;
    private int page = 1;
    private NewRecommendAdapter adapter;
    private List<NewRecomBean.ItemsBean> listData;
    private NewsRecommendHeaderView headerView;

    private void loadData() {
        NewsRecomRequestMo requestMo = new NewsRecomRequestMo(size, page);
        NewsService.newsRecommend(requestMo, new BizResultListener<NewRecomBean>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, NewRecomBean knowledgeResultMo) {

            }

            @Override
            public void onSuccess(NewRecomBean resultMo) {
                if (resultMo == null) {
                    return;
                }
                if (headerView != null && page== 1) {
                    if (!ObjectUtils.isEmpty(resultMo.getBannels())) {
                        headerView.setVisibility(VISIBLE);
                        headerView.refreshData(resultMo.getBannels());
                    } else {
                        headerView.setVisibility(View.GONE);
                    }
                }
                List<NewRecomBean.ItemsBean> list = resultMo.getItems();
                if (resultMo.getCountpage() >= page) {
                    page++;
                    listData.addAll(list);
                    adapter.notifyDataSetChanged();
                    refreshLayout.finishLoadMore();
                } else {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                LogUtils.i("investmentRequestMo请求失败");
            }
        });
    }

    private void initData() {
        headerView = new NewsRecommendHeaderView(getContext());
        headerView.setVisibility(View.GONE);
        listData = new ArrayList<>();
        adapter = new NewRecommendAdapter(getActivity(), listData, R.layout.adapter_new_recommond);
        recommendListView.setAdapter(adapter);
        recommendListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewRecomBean.ItemsBean bean = listData.get(position-1);
                WebViewActivity.actionStart(getActivity(), bean.getLink(), bean.getTitle(), true);
            }
        });
        recommendListView.addHeaderView(headerView);
    }

    @Override
    public void onViewCreated(@NonNull final View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
        initData();
        page = 1;
        refreshLayout.setEnableAutoLoadMore(true);//开启自动加载功能（非必须）
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().post(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;
                        listData.clear();
                        loadData();
                        refreshLayout.finishRefresh();
                        refreshLayout.setNoMoreData(false);
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
        refreshLayout.autoRefresh();
//        loadData();
    }

    public static NewsRecommendFragment newInstance(String text) {
        NewsRecommendFragment fragmentCommon = new NewsRecommendFragment();
        Bundle bundle = new Bundle();
        bundle.putString("text", text);
        fragmentCommon.setArguments(bundle);
        return fragmentCommon;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommentd, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

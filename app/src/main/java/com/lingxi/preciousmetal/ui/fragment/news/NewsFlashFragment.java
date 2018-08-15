package com.lingxi.preciousmetal.ui.fragment.news;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.MessCommonListRequestMo;
import com.lingxi.biz.domain.requestMo.MessQuickRequestMo;
import com.lingxi.biz.domain.responseMo.MessQuickResultBean;
import com.lingxi.biz.domain.responseMo.NewRecomBean;
import com.lingxi.biz.service.NewsService;
import com.lingxi.common.base.BaseFragment;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.ui.adapter.FlashNewsAdapter;
import com.lingxi.preciousmetal.ui.adapter.NewRecommendAdapter;
import com.lingxi.preciousmetal.util.utilCode.LogUtils;
import com.lingxi.preciousmetal.util.utilCode.TimeUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class NewsFlashFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private RefreshLayout refreshLayout;
    private int page;
    private int pageSize;
    private List<MessQuickResultBean.ItemsBean> listData;
    private FlashNewsAdapter commentAdapter;
    private TextView flash_time_textView;

    public static NewsFlashFragment newInstance(String text){
        NewsFlashFragment fragmentCommon=new NewsFlashFragment();
        Bundle bundle=new Bundle();
        bundle.putString("text",text);
        fragmentCommon.setArguments(bundle);
        return fragmentCommon;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        flash_time_textView = view.findViewById(R.id.flash_time_textView);
        ListView newsListView = view.findViewById(R.id.news_flash_listView);
        newsListView.setOnItemClickListener(this);

        listData = new ArrayList<>();
        commentAdapter = new FlashNewsAdapter(getActivity(), listData, R.layout.adapter_flash_news);
        newsListView.setAdapter(commentAdapter);
        page = 1;
        pageSize = 20;

        refreshLayout =  view.findViewById(R.id.refreshLayout);
        refreshLayout.setEnableAutoLoadMore(true);//开启自动加载功能（非必须）
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().post(new Runnable() {
                    @Override
                    public void run() {
                        clearListData();
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
    }

    private void clearListData(){
        page = 1;
        pageSize = 10;
        listData.clear();
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_flash,container,false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void loadData(){
        MessQuickRequestMo requestMo =  new MessQuickRequestMo(pageSize,page);
        NewsService.newsFlashList(requestMo,new BizResultListener<MessQuickResultBean>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit,MessQuickResultBean resultMo) {

            }

            @Override
            public void onSuccess(MessQuickResultBean resultMo) {
                List<MessQuickResultBean.ItemsBean> list = resultMo.getItems();
                if (resultMo.getCountpage()>=page){
                    page++;
                    listData.addAll(list);
                    commentAdapter.notifyDataSetChanged();
                    refreshLayout.finishLoadMore();
                } else {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                LogUtils.i("要闻请求失败");
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}

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
import com.lingxi.biz.domain.requestMo.MessCommonListRequestMo;
import com.lingxi.biz.domain.responseMo.NewRecomBean;
import com.lingxi.biz.service.NewsService;
import com.lingxi.common.base.BaseFragment;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.ui.activity.WebViewActivity;
import com.lingxi.preciousmetal.ui.adapter.NewRecommendAdapter;
import com.lingxi.preciousmetal.util.utilCode.LogUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class NewsCommonFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private String type;
    private RefreshLayout refreshLayout;
    private int page;
    private int pageSize;
    private List<NewRecomBean.ItemsBean> listData;
    private NewRecommendAdapter commentAdapter;

    public static NewsCommonFragment newInstance(String text){
        NewsCommonFragment fragmentCommon=new NewsCommonFragment();
        Bundle bundle=new Bundle();
        bundle.putString("text",text);
        fragmentCommon.setArguments(bundle);
        return fragmentCommon;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView newsListView = view.findViewById(R.id.public_listView);
        newsListView.setOnItemClickListener(this);
        type =  getArguments().getString("text");
        listData = new ArrayList<>();
        commentAdapter = new NewRecommendAdapter(getActivity(), listData, R.layout.adapter_new_recommond);
        newsListView.setAdapter(commentAdapter);
        page = 1;
        pageSize = 10;

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
        View view = inflater.inflate(R.layout.public_listview_layout,container,false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void loadData(){
        int cateId = Integer.parseInt(type);
        final MessCommonListRequestMo requestMo =  new MessCommonListRequestMo(cateId,pageSize,page);
        NewsService.newsCommontList(requestMo,new BizResultListener<NewRecomBean>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit,NewRecomBean knowledgeResultMo) {

            }

            @Override
            public void onSuccess(NewRecomBean resultMo) {
                List<NewRecomBean.ItemsBean> list = resultMo.getItems();
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
        NewRecomBean.ItemsBean bean =  listData.get(position);
        WebViewActivity.actionStart(getActivity(), bean.getLink(),bean.getTitle(),true);

//        if ("2".equals(type)){
//            MessExpertResultMo.ArticlesMBean bean = newsList.get(position);
//            WebViewActivity.actionStart(getActivity(),bean.link);
////            MessExpertResultMo.ArticlesMBean bean = newsList.get(position);
////            WebViewActivity.actionStart(getActivity(),bean.);
//        } else if ("3".equals(type)){
//
//        } else if ("4".equals(type)){
//            MessImportResultBean.ItemsBean bean =  importList.get(position);
//            ArticleDetailActivity.actionStart(getActivity(),bean);
//        }
    }
}

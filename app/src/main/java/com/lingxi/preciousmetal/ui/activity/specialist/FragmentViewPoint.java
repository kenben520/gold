package com.lingxi.preciousmetal.ui.activity.specialist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.SpecialRemarkRequestMo;
import com.lingxi.biz.domain.requestMo.SpecialViewPointRequestMo;
import com.lingxi.biz.domain.responseMo.SpecialRemarkBean;
import com.lingxi.biz.domain.responseMo.SpecialViewPointBean;
import com.lingxi.biz.service.NewsService;
import com.lingxi.biz.util.ConstantUtil;
import com.lingxi.common.base.BaseFragment;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.ui.activity.WebViewActivity;
import com.lingxi.preciousmetal.ui.adapter.GroupTestAdapter;
import com.lingxi.preciousmetal.ui.adapter.SpecialRemarkAdapter;
import com.lingxi.preciousmetal.ui.adapter.SpecialViewPointAdapter;
import com.lingxi.preciousmetal.util.utilCode.LogUtils;
import com.lingxi.preciousmetal.util.utilCode.SizeUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
//专家观点
public class FragmentViewPoint extends BaseFragment{
    private RefreshLayout refreshLayout;
    private int page,pageSize = 10;
    private List<SpecialViewPointBean.ArticlesBean> listData;
    private List<SpecialRemarkBean.ArticlesBean> remarkListData;
    private SpecialViewPointAdapter commentAdapter;
    private SpecialRemarkAdapter remarkAdapter;
    private int fType = 0;

    public static FragmentViewPoint newInstance(int fType){
        FragmentViewPoint fragmentCommon = new FragmentViewPoint();
        Bundle bundle=new Bundle();
        bundle.putInt("fType",fType);
        fragmentCommon.setArguments(bundle);
        return fragmentCommon;
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommentd,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView newsListView = view.findViewById(R.id.recommend_listView);
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (fType==1){
                    SpecialRemarkBean.ArticlesBean bean =  remarkListData.get(position);
                    WebViewActivity.actionStart(getActivity(), bean.getLink(),bean.getTitle(),true);
                } else if (fType==2){
                    SpecialViewPointBean.ArticlesBean bean =  listData.get(position);
                    WebViewActivity.actionStart(getActivity(),bean.getLink(),bean.getTitle(),true);
                }
            }
        });
        fType =  getArguments().getInt("fType");
        page = 1;
        if (fType==1){
            remarkListData = new ArrayList<>();
            remarkAdapter = new SpecialRemarkAdapter(getActivity(), remarkListData, R.layout.adapter_special_remark);
            newsListView.setAdapter(remarkAdapter);
            newsListView.setDividerHeight(SizeUtils.dp2px(0));
        } else if (fType==2){
            listData = new ArrayList<>();
            commentAdapter = new SpecialViewPointAdapter(getActivity(), listData, R.layout.adapter_special_view_point);
            newsListView.setAdapter(commentAdapter);
        }
        refreshLayout =  view.findViewById(R.id.refreshLayout);
        refreshLayout.setEnableAutoLoadMore(true);//开启自动加载功能（非必须）
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().post(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;
                        if (fType==1){
                            remarkListData.clear();
                            remarkAdapter.notifyDataSetChanged();
                        } else if (fType==2){
                            listData.clear();
                            commentAdapter.notifyDataSetChanged();
                        }
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
        refreshLayout.autoRefresh();
    }

    private void  loadData(){
        if (fType==1){
            SpecialRemarkRequestMo requestMo  = new SpecialRemarkRequestMo(page,pageSize);
            NewsService.specialRemarkData(requestMo,new BizResultListener<SpecialRemarkBean>() {
                @Override
                public void onPreExecute() {

                }

                @Override
                public void hitCache(boolean hit, SpecialRemarkBean resultMo) {

                }

                @Override
                public void onSuccess(SpecialRemarkBean  resultMo) {
                    if (resultMo!=null && resultMo.getArticles()!=null){
                        page++;
                        remarkListData.addAll(resultMo.getArticles());
                        remarkAdapter.notifyDataSetChanged();
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
        } else if (fType==2){
            SpecialViewPointRequestMo requestMo  = new SpecialViewPointRequestMo(page,pageSize);
            NewsService.specialViewPoint(requestMo,new BizResultListener<SpecialViewPointBean>() {
                @Override
                public void onPreExecute() {

                }

                @Override
                public void hitCache(boolean hit, SpecialViewPointBean resultMo) {

                }

                @Override
                public void onSuccess(SpecialViewPointBean  resultMo) {
                    if (resultMo!=null && resultMo.getArticles()!=null){
                        page++;
                        listData.addAll(resultMo.getArticles());
                        commentAdapter.notifyDataSetChanged();
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
    }

}

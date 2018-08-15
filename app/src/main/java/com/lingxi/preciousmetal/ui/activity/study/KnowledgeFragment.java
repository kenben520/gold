package com.lingxi.preciousmetal.ui.activity.study;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.IndustryRequestMo;
import com.lingxi.biz.domain.requestMo.InvestmentRequestMo;
import com.lingxi.biz.domain.requestMo.NewKlineRequestMo;
import com.lingxi.biz.domain.requestMo.NewMentalRequestMo;
import com.lingxi.biz.domain.requestMo.NewOpportunRequestMo;
import com.lingxi.biz.domain.requestMo.NewTradeRequestMo;
import com.lingxi.biz.domain.responseMo.KnowledgeResultMo;
import com.lingxi.biz.service.UserService;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.ui.activity.WebViewActivity;
import com.lingxi.preciousmetal.ui.adapter.KnowledgeCateAdapter;
import com.lingxi.preciousmetal.ui.adapter.base.CommonAdapter;
import com.lingxi.preciousmetal.util.utilCode.LogUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/*专家点评
 */
public class KnowledgeFragment extends Fragment {

    private String type;
    private int mode;

    public static KnowledgeFragment newInstance(int mode,String text){
        KnowledgeFragment fragmentCommon=new KnowledgeFragment();
        Bundle bundle=new Bundle();
        bundle.putString("text",text);
        bundle.putInt("mode",mode);
        fragmentCommon.setArguments(bundle);
        return fragmentCommon;
    }

    CommonAdapter mAdapter;
    List<KnowledgeResultMo.ArticlesBean> newsList;
    ListView news_main_listview;
    private int page, pageSize;
    RefreshLayout refreshLayout;

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        type =  getArguments().getString("text");
        mode = getArguments().getInt("mode");
        news_main_listview = view.findViewById(R.id.public_listView);
        newsList = new ArrayList<>();
        pageSize = 20;
        page = 1;
        mAdapter = new KnowledgeCateAdapter(getContext(), newsList, R.layout.adapter_title_item);
        news_main_listview.setAdapter(mAdapter);
        news_main_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            KnowledgeResultMo.ArticlesBean bean = newsList.get(position);
            WebViewActivity.actionStart(getContext(),bean.link,bean.title,true);
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
                        newsList.clear();
                        page = 1;
                        initData();
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
                        initData();
                    }
                });
            }
        });
        //触发自动刷新
        refreshLayout.autoRefresh();
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

    private void initData() {
        if (mode==1)  {
            if ("1".equals(type)){
                //投资入门
                InvestmentRequestMo investmentRequestMo = new InvestmentRequestMo(page, pageSize);
                UserService.investment_app(investmentRequestMo,new BizResultListener<KnowledgeResultMo>() {
                    @Override
                    public void onPreExecute() {

                    }

                    @Override
                    public void hitCache(boolean hit, KnowledgeResultMo knowledgeResultMo) {
                    }

                    @Override
                    public void onSuccess(KnowledgeResultMo loginResultMo) {
                        showResult(loginResultMo);
                    }

                    @Override
                    public void onFail(int resultCode, int bizCode, String bizMessage) {
                        LogUtils.i("investmentRequestMo请求失败");
                    }
                });
            } else if ("2".equals(type)){
                //行业知识
                IndustryRequestMo investmentRequestMo = new IndustryRequestMo(page, pageSize);
                UserService.industryApp(investmentRequestMo,new BizResultListener<KnowledgeResultMo>() {
                    @Override
                    public void onPreExecute() {
                    }

                    @Override
                    public void hitCache(boolean hit, KnowledgeResultMo knowledgeResultMo) {
                    }

                    @Override
                    public void onSuccess(KnowledgeResultMo loginResultMo) {
                        showResult(loginResultMo);
                    }

                    @Override
                    public void onFail(int resultCode, int bizCode, String bizMessage) {
                        LogUtils.i("investmentRequestMo请求失败");
                    }
                });
            } else if ("3".equals(type)){
                NewTradeRequestMo investmentRequestMo = new NewTradeRequestMo(page, pageSize);
                UserService.newTradeApp(investmentRequestMo,new BizResultListener<KnowledgeResultMo>() {
                    @Override
                    public void onPreExecute() {

                    }

                    @Override
                    public void hitCache(boolean hit, KnowledgeResultMo knowledgeResultMo) {

                    }

                    @Override
                    public void onSuccess(KnowledgeResultMo loginResultMo) {
                        showResult(loginResultMo);
                    }

                    @Override
                    public void onFail(int resultCode, int bizCode, String bizMessage) {
                        LogUtils.i("investmentRequestMo请求失败");
                    }
                });
            }
        } else if (mode==2){
            if ("1".equals(type)){
                NewKlineRequestMo  investmentRequestMo = new NewKlineRequestMo(page,pageSize);
                UserService.newKlineApp(investmentRequestMo,new BizResultListener<KnowledgeResultMo>() {
                    @Override
                    public void onPreExecute() {

                    }

                    @Override
                    public void hitCache(boolean hit, KnowledgeResultMo knowledgeResultMo) {

                    }

                    @Override
                    public void onSuccess(KnowledgeResultMo loginResultMo) {
                        showResult(loginResultMo);
                    }

                    @Override
                    public void onFail(int resultCode, int bizCode, String bizMessage) {
                    }
                });
            } else if ("2".equals(type)){
                NewMentalRequestMo investmentRequestMo = new NewMentalRequestMo(page,pageSize);
                UserService.newMentalApp(investmentRequestMo,new BizResultListener<KnowledgeResultMo>() {
                    @Override
                    public void onPreExecute() {

                    }

                    @Override
                    public void hitCache(boolean hit, KnowledgeResultMo knowledgeResultMo) {

                    }

                    @Override
                    public void onSuccess(KnowledgeResultMo loginResultMo) {
                        showResult(loginResultMo);
                    }

                    @Override
                    public void onFail(int resultCode, int bizCode, String bizMessage) {
                    }
                });
            } else if("3".equals(type)){
                NewOpportunRequestMo investmentRequestMo = new NewOpportunRequestMo(page,pageSize);
                UserService.newOpportunitylApp(investmentRequestMo,new BizResultListener<KnowledgeResultMo>() {
                    @Override
                    public void onPreExecute() {

                    }

                    @Override
                    public void hitCache(boolean hit, KnowledgeResultMo knowledgeResultMo) {

                    }

                    @Override
                    public void onSuccess(KnowledgeResultMo loginResultMo) {
                        showResult(loginResultMo);
                    }

                    @Override
                    public void onFail(int resultCode, int bizCode, String bizMessage) {
                    }
                });
            }
        }
    }

    private void showResult(KnowledgeResultMo resultMo){
        if (resultMo!=null && resultMo.articles!=null){
            newsList.addAll(resultMo.articles);
            page++;
            refreshLayout.finishLoadMore();
            mAdapter.notifyDataSetChanged();
        } else {
//            refreshLayout.finishRefresh();
            refreshLayout.finishLoadMoreWithNoMoreData();
//            refreshLayout.setNoMoreData(false);
        }
        LogUtils.i("investmentRequestMo请求成功");
    }
}

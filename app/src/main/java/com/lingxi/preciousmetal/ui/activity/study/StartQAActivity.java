package com.lingxi.preciousmetal.ui.activity.study;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.NewFundAnalysisRequestMo;
import com.lingxi.biz.domain.requestMo.NewQaRequestMo;
import com.lingxi.biz.domain.requestMo.StudyWordRequestMo;
import com.lingxi.biz.domain.responseMo.KnowledgeResultMo;
import com.lingxi.biz.service.InvestStudyService;
import com.lingxi.biz.util.ConstantUtil;
import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.ui.activity.WebViewActivity;
import com.lingxi.preciousmetal.ui.adapter.KnowledgeCateAdapter;
import com.lingxi.preciousmetal.ui.adapter.base.CommonAdapter;
import com.lingxi.preciousmetal.ui.widget.TopBarView;
import com.lingxi.preciousmetal.util.utilCode.LogUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 交易问答
 */
public class StartQAActivity extends TranslucentStatusBarActivity {

    private CommonAdapter mAdapter;
    private List<KnowledgeResultMo.ArticlesBean> newsList;
    private  RefreshLayout refreshLayout;
    private ListView qaListView;
    private  String type,searchType;
    private int page = 1,pageSize = 20;
    private TopBarView mTopBarView;

    public static void startMyActivity(Context activity, String fType,String searchType){
        Intent intent = new Intent(activity,StartQAActivity.class);
        intent.putExtra("fType",fType);
        intent.putExtra("searchType",searchType);
        activity.startActivity(intent);
    }

    private void initTopBar() {
        mTopBarView =  findViewById(R.id.topbar_view);
        mTopBarView.setTitle(type);
        mTopBarView.search_close_btn.setVisibility(View.VISIBLE);

        mTopBarView =  findViewById(R.id.topbar_view);
        mTopBarView.setMode(TopBarView.MODE_BACK);
        mTopBarView.search_close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudySearchActivity.actionStart(mContext,searchType);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_qa);
        qaListView =  findViewById(R.id.qa_listview);
        refreshLayout = findViewById(R.id.refreshLayout);
        initData();
        initTopBar();

        refreshLayout.setEnableAutoLoadMore(true);//开启自动加载功能（非必须）
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().post(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;
                        newsList.clear();
                        mAdapter.notifyDataSetChanged();
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
        //触发自动刷新
        refreshLayout.autoRefresh();
    }

    private void initData(){
        type = getIntent().getStringExtra("fType");
        searchType = getIntent().getStringExtra("searchType");

        newsList = new ArrayList<>();
        mAdapter = new KnowledgeCateAdapter(this, newsList, R.layout.adapter_title_item);
        qaListView.setAdapter(mAdapter);
        qaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KnowledgeResultMo.ArticlesBean bean = newsList.get(position);
                WebViewActivity.actionStart(mContext,bean.link,bean.title,true);
            }
        });
    }

    private void loadData() {
        if (ConstantUtil.fundamentalAnalysis.equals(type)){
            NewFundAnalysisRequestMo  requestMo = new NewFundAnalysisRequestMo(page,pageSize);
            InvestStudyService.newFundAnalysis(requestMo,new BizResultListener<KnowledgeResultMo>() {
                @Override
                public void onPreExecute() {

                }

                @Override
                public void hitCache(boolean hit, KnowledgeResultMo knowledgeResultMo) {

                }

                @Override
                public void onSuccess(KnowledgeResultMo resultMo) {
                    showData(resultMo);
                }

                @Override
                public void onFail(int resultCode, int bizCode, String bizMessage) {
                    LogUtils.i("investmentRequestMo请求失败");
                }
            });
        } else if (ConstantUtil.TRADEWORD.equals(type)){
            StudyWordRequestMo requestMo = new StudyWordRequestMo(page,pageSize);
            InvestStudyService.studyWord(requestMo,new BizResultListener<KnowledgeResultMo>() {
                @Override
                public void onPreExecute() {

                }

                @Override
                public void hitCache(boolean hit, KnowledgeResultMo knowledgeResultMo) {

                }

                @Override
                public void onSuccess(KnowledgeResultMo resultMo) {
                    showData(resultMo);
                }

                @Override
                public void onFail(int resultCode, int bizCode, String bizMessage) {
                    LogUtils.i("investmentRequestMo请求失败");
                }
            });
        } else  if (ConstantUtil.TRADEQA.equals(type)){
            NewQaRequestMo requestMo = new NewQaRequestMo(page,pageSize);
            InvestStudyService.newQaApp(requestMo,new BizResultListener<KnowledgeResultMo>() {
                @Override
                public void onPreExecute() {

                }

                @Override
                public void hitCache(boolean hit, KnowledgeResultMo knowledgeResultMo) {

                }

                @Override
                public void onSuccess(KnowledgeResultMo  resultMo) {
                    showData(resultMo);
                }

                @Override
                public void onFail(int resultCode, int bizCode, String bizMessage) {
                    LogUtils.i("investmentRequestMo请求失败");
                }
            });
        }
    }

    private void showData(KnowledgeResultMo resultMo){
        if (resultMo!=null && resultMo.articles!=null){
            page ++;
            newsList.addAll(resultMo.articles);
            mAdapter.notifyDataSetChanged();
            refreshLayout.finishLoadMore();
        } else {
            refreshLayout.finishLoadMoreWithNoMoreData();
        }
    }

}

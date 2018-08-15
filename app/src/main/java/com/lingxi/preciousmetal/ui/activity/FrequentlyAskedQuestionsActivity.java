package com.lingxi.preciousmetal.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lingxi.biz.domain.responseMo.KnowledgeResultMo;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;
import com.lingxi.preciousmetal.presenter.pIml.CommonListPresenter2;
import com.lingxi.preciousmetal.ui.activity.kefu.KefuLoginActivity;
import com.lingxi.preciousmetal.ui.activity.trade.PropertySheetsActivity;
import com.lingxi.preciousmetal.ui.vInterface.CommonListVInterface2;
import com.lingxi.preciousmetal.ui.widget.TopBarView;
import com.lingxi.preciousmetal.util.utilCode.ObjectUtils;
import com.lingxi.preciousmetal.util.utilCode.TimeUtils;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangwei on 2018/4/25.
 */

public class FrequentlyAskedQuestionsActivity extends TranslucentStatusBarActivity implements CommonListVInterface2 {
    @BindView(R.id.topbar_view)
    TopBarView topbarView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.ProgressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.empty_img)
    ImageView emptyImg;
    @BindView(R.id.empty_tips)
    TextView emptyTips;
    @BindView(R.id.empty_layout)
    LinearLayout mEmptyLayout;
    @BindView(R.id.layout_no_content)
    RelativeLayout layoutNoContent;
    private QuickAdapter mAdapter;
    private static boolean isFirstEnter = true;
    private CommonListPresenter2 mCommonListPresenter;
    private static final int LIMIT = 10;
    private int pageNum = 0;
    private boolean isLoading = false;
    private List<KnowledgeResultMo.ArticlesBean> list = new ArrayList<>();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, FrequentlyAskedQuestionsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_list);
        mCommonListPresenter = new CommonListPresenter2(this);
        ButterKnife.bind(this);
        initView();
        bindData();
    }

    private void bindData() {
        mProgressBar.setVisibility(View.VISIBLE);
        loadData();
    }

    private void loadData() {
        mCommonListPresenter.getListData2(list.size());
    }


    private void initView() {
        initTopBar();
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (!isLoading) {
                    loadMoreData();
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (!isLoading) {
                    pageNum = 0;
                    refreshLayout.setNoMoreData(false);
                    loadData();
                    isLoading = true;
                }
            }
        });
        mAdapter = new QuickAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
    }


    private void loadMoreData() {
        isLoading = true;
        loadData();
    }

    public class QuickAdapter extends BaseQuickAdapter<KnowledgeResultMo.ArticlesBean, BaseViewHolder> {
        public QuickAdapter() {
            super(R.layout.adapter_important_news);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, final KnowledgeResultMo.ArticlesBean item) {
            viewHolder.setText(R.id.important_title_textVIew, item.title).setText(R.id.important_time_textVIew, TimeUtils.millis2String(item.add_time * 1000));
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(item.link)) return;
                    WebViewActivity.actionStart(FrequentlyAskedQuestionsActivity.this, item.link);
                }
            });
        }
    }

    private void initTopBar() {
        topbarView.setMode(TopBarView.MODE_BACK);
        topbarView.setTitle("常见问题");
        topbarView.setActionButton(R.drawable.ic_custom_service_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KefuLoginActivity.actionStart(FrequentlyAskedQuestionsActivity.this);
            }
        });
    }

    @Override
    public void showList(List<KnowledgeResultMo.ArticlesBean> feedModelList) {
        mProgressBar.setVisibility(View.GONE);
        if (pageNum <= 0) {
            list.clear();
        }
        pageNum++;
        int srcPosition = list.size();
        int addLen = feedModelList.size();
        list.addAll(feedModelList);
        if (list.size() == 0) {
            mEmptyLayout.setVisibility(View.VISIBLE);
            layoutNoContent.setVisibility(View.VISIBLE);
            emptyImg.setImageResource(R.drawable.icon_empty_content);
            emptyTips.setText(getString(R.string.empty_content_tips));
            LoadMoreComplete();
        } else {
            mEmptyLayout.setVisibility(View.GONE);
            layoutNoContent.setVisibility(View.GONE);
            LoadMoreComplete();
        }
        if (pageNum > 1) {
            if (addLen > 0) {
                mAdapter.addData(srcPosition, feedModelList);
            }
        } else {
            List<KnowledgeResultMo.ArticlesBean> list1 = new ArrayList<>();
            list1.addAll(list);
            mAdapter.setNewData(list1);
        }
        if (feedModelList.size() < 1 || list.size() < LIMIT) {
            mRefreshLayout.finishLoadMoreWithNoMoreData();
        }
        isLoading = false;
    }

    private void LoadMoreComplete() {
        isLoading = false;
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMore();
    }

    @Override
    public void showFail(String errorMsg) {
        mProgressBar.setVisibility(View.GONE);
        ToastUtils.showShort(errorMsg);
        LoadMoreComplete();
        if (ObjectUtils.isEmpty(list)) {
            mEmptyLayout.setVisibility(View.VISIBLE);
            layoutNoContent.setVisibility(View.VISIBLE);
            emptyImg.setImageResource(R.drawable.icon_no_network);
            emptyTips.setText(getString(R.string.no_network_tips));
        } else {
            mEmptyLayout.setVisibility(View.GONE);
            layoutNoContent.setVisibility(View.GONE);
        }
    }
}


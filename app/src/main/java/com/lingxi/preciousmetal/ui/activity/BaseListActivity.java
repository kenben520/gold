package com.lingxi.preciousmetal.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;
import com.lingxi.preciousmetal.ui.widget.TopBarView;
import com.lingxi.preciousmetal.util.utilCode.ObjectUtils;
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

public abstract class BaseListActivity<T> extends TranslucentStatusBarActivity {
    @BindView(R.id.topbar_view)
    public
    TopBarView topbarView;
    @BindView(R.id.recyclerView)
    public
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
    @BindView(R.id.menu)
    TextView menu;
    private BaseQuickAdapter mAdapter;
    private static boolean isFirstEnter = true;
    //    private CommonListPresenter mCommonListPresenter;
    protected static final int LIMIT = 10;
    protected int pageNum = 0;
    private boolean isLoading = false;
    private List<T> list = new ArrayList<>();
    protected String noDataTips;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, BaseListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
//        mCommonListPresenter = new CommonListPresenter(this);
        ButterKnife.bind(this);
        noDataTips = getString(R.string.empty_content_tips);
    }

    public int getLayoutId() {
        return R.layout.activity_common_list1;
    }

    public void bindData() {
        mProgressBar.setVisibility(View.VISIBLE);
        loadData();
    }

//    private void loadData() {
//        mCommonListPresenter.getListData(pageNum + 1, LIMIT);
//    }

    protected abstract void loadData();


    protected void initView(BaseQuickAdapter baseQuickAdapter) {
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
        mAdapter = baseQuickAdapter;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
    }


    private void loadMoreData() {
        isLoading = true;
        loadData();
    }


    protected void initTopBar() {
    }

    public void showList(List<T> feedModelList) {
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
            emptyTips.setText(noDataTips);
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
            List<T> list1 = new ArrayList<>();
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

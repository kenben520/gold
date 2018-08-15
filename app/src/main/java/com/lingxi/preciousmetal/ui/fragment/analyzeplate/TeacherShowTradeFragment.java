package com.lingxi.preciousmetal.ui.fragment.analyzeplate;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.TeacherShowTradeListRequestMo;
import com.lingxi.biz.domain.responseMo.TeacherShowTradeListMo;
import com.lingxi.biz.service.AnalyseTradeService;
import com.lingxi.common.base.BaseFragment;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.MyApplication;
import com.lingxi.preciousmetal.common.EventBusKeyDefine;
import com.lingxi.preciousmetal.ui.activity.ViewPagerActivity;
import com.lingxi.preciousmetal.ui.widget.TopBarView;
import com.lingxi.preciousmetal.util.DisplayUtil;
import com.lingxi.preciousmetal.util.glideutils.GlideUtils;
import com.lingxi.preciousmetal.util.utilCode.ObjectUtils;
import com.lingxi.preciousmetal.util.utilCode.TimeUtils;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhangwei on 2018/4/25.
 */

public class TeacherShowTradeFragment extends BaseFragment {
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
    //    private CommonListPresenter mCommonListPresenter;
    private static final int LIMIT = 10;
    private int pageNum = 0;
    private boolean isLoading = false;
    private List<TeacherShowTradeListMo.SheetsBean> list = new ArrayList<>();
    private View mContentView;
    Unbinder unbinder;
    String teacherId;

    public static TeacherShowTradeFragment newInstance(String teacherId) {
        TeacherShowTradeFragment fragmentCommon = new TeacherShowTradeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("teacherId", teacherId);
        fragmentCommon.setArguments(bundle);
        return fragmentCommon;
    }

    private int visibleHeight=0;
    public void setVisibleHeight(int height)
    {
        visibleHeight=height;
    }

    @Override
    public void onViewCreated(@NonNull final View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
//        mCommonListPresenter = new CommonListPresenter(this);
        teacherId = getArguments().getString("teacherId");
        registorEventBus();
        initView();
        bindData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mContentView == null) {
            mContentView = inflater.inflate(R.layout.activity_common_list1, container, false);
        }
        unbinder = ButterKnife.bind(this, mContentView);
        return mContentView;
    }

    private void bindData() {
        mProgressBar.setVisibility(View.VISIBLE);
        loadData();
    }

    //    private void loadData() {
//        mCommonListPresenter.getListData(pageNum + 1, LIMIT);
//    }
    private void loadData() {

        TeacherShowTradeListRequestMo liveListRequestMo = new TeacherShowTradeListRequestMo(teacherId, pageNum + 1, LIMIT);
        AnalyseTradeService.getTeacherShowTradeList(liveListRequestMo, new BizResultListener<TeacherShowTradeListMo>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, TeacherShowTradeListMo teacherShowTradeListMo) {

            }

            @Override
            public void onSuccess(TeacherShowTradeListMo teacherShowTradeListMo) {
                if (teacherShowTradeListMo != null) {
                    showList(teacherShowTradeListMo.getSheets());
                }

            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                showFail(bizMessage);
            }
        });
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
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

//        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setEnableRefresh(false);
    }

    public boolean canScrollVertically(int direction) {
        return recyclerView != null && recyclerView.canScrollVertically(direction);
    }

    public void onFlingOver(int y, long duration) {
        if (recyclerView != null) {
            recyclerView.smoothScrollBy(0, y);
        }
    }

    private void loadMoreData() {
        isLoading = true;
        loadData();
    }
    int width = DisplayUtil.getScreenWidth(MyApplication.getInstance())-DisplayUtil.dip2px(MyApplication.getInstance(),32);
    int height = width * 9 / 16;

    public class QuickAdapter extends BaseQuickAdapter<TeacherShowTradeListMo.SheetsBean, BaseViewHolder> {
        public QuickAdapter() {
            super(R.layout.item_show_trade_teacher_detail);
        }

        @Override
        protected void convert(final BaseViewHolder viewHolder, final TeacherShowTradeListMo.SheetsBean item) {
            ViewGroup.LayoutParams lp = viewHolder.getView(R.id.iv_image).getLayoutParams();
            lp.width = width;
            lp.height = height;
            if (!item.isBlankData()) {
                viewHolder.getView(R.id.content).setVisibility(View.VISIBLE);
                viewHolder.setText(R.id.title1, item.getTea_name()).setText(R.id.title2, item.getProfit_time()).setText(R.id.win_money, "获利：" + item.getProfit() + "美元");
                GlideUtils.loadImageViewCrop(getContext(), R.drawable.background_gray4, item.getThumb(), (ImageView) viewHolder.getView(R.id.iv_image));
                GlideUtils.loadRoundImage(getContext(), R.drawable.default_header1, 40, item.getTea_img_url(), (ImageView) viewHolder.getView(R.id.iv_user_icon));
                viewHolder.getView(R.id.iv_image).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<String> list = new ArrayList<>();
                        list.add(item.getThumb());
                        ViewPagerActivity.actionStart(getContext(), list, viewHolder.getAdapterPosition());
                    }
                });
            } else {
                viewHolder.getView(R.id.content).setVisibility(View.INVISIBLE);
            }
        }
    }

    private void initTopBar() {
        topbarView.setVisibility(View.GONE);
    }

    public void showList(List<TeacherShowTradeListMo.SheetsBean> feedModelList) {
        if(ObjectUtils.isEmpty(feedModelList)){
            feedModelList=new ArrayList<>();
        }
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
            List<TeacherShowTradeListMo.SheetsBean> list1 = new ArrayList<>();
            list1.addAll(list);
            mAdapter.setNewData(list1);
        }
        if (feedModelList.size() < 1 || list.size() < LIMIT) {
//            mRefreshLayout.setNoMoreData(true);//将不会再次触发加载更多事件
            if (list.size() < LIMIT) {
                int addSize = 2 - list.size();
                if (addSize > 0) {
                    List<TeacherShowTradeListMo.SheetsBean> list1 = new ArrayList<>();
                    list1.addAll(list);
                    for (int i = 0; i < addSize; i++) {
                        TeacherShowTradeListMo.SheetsBean videoListBean = new TeacherShowTradeListMo.SheetsBean();
                        videoListBean.setBlankData(true);
                        list1.add(videoListBean);
                    }
                    mAdapter.setNewData(list1);
                }
            }
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThrea(EventBusKeyDefine.EventBusMsgData<String> data) {
        int type = data.getType();
        try {
            if (EventBusKeyDefine.EVENTBUS_LOGIN_SUCCESS == type || EventBusKeyDefine.EVENTBUS_LOGOUT == type || EventBusKeyDefine.EVENTBUS_USER_INFO_UPDATE == type || EventBusKeyDefine.EVENTBUS_REGISTER_SUCCESS == type) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unregistorEventBus();
        unbinder.unbind();
    }
}

package com.lingxi.preciousmetal.ui.fragment.analyzeplate;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lingxi.biz.domain.responseMo.LiveProgramListMo;
import com.lingxi.common.base.BaseFragment;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.common.EventBusKeyDefine;
import com.lingxi.preciousmetal.presenter.pIml.CommonListPresenter;
import com.lingxi.preciousmetal.ui.activity.PolyvLivePlayerActivity;
import com.lingxi.preciousmetal.ui.listener.DataChangeListener;
import com.lingxi.preciousmetal.ui.vInterface.CommonListVInterface;
import com.lingxi.preciousmetal.ui.widget.TopBarView;
import com.lingxi.preciousmetal.util.DisplayUtil;
import com.lingxi.preciousmetal.util.utilCode.ObjectUtils;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhangwei on 2018/4/25.
 */

public class AnalyzePlateProgramFragment extends BaseFragment implements CommonListVInterface<LiveProgramListMo.LiveProgramBean>, DataChangeListener<List<LiveProgramListMo.LiveProgramBean>> {
    @BindView(R.id.topbar_view)
    TopBarView topbarView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.ProgressBar)
    android.widget.ProgressBar mProgressBar;
    @BindView(R.id.empty_img)
    ImageView emptyImg;
    @BindView(R.id.empty_tips)
    TextView emptyTips;
    @BindView(R.id.empty_layout)
    LinearLayout mEmptyLayout;
    private QuickAdapter mAdapter;
    private static boolean isFirstEnter = true;
    private CommonListPresenter mCommonListPresenter;
    private static final int LIMIT = 10;
    private int pageNum = 0;
    private boolean isLoading = false;
    private List<LiveProgramListMo.LiveProgramBean> list = new ArrayList<>();
    private View mContentView;
    Unbinder unbinder;
    private String whatDay;

    public static AnalyzePlateProgramFragment newInstance(String whatDay) {
        AnalyzePlateProgramFragment fragmentCommon = new AnalyzePlateProgramFragment();
        Bundle bundle = new Bundle();
        bundle.putString("whatDay", whatDay);
        fragmentCommon.setArguments(bundle);
        return fragmentCommon;
    }


    @Override
    public void onViewCreated(@NonNull final View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
        registorEventBus();
        mCommonListPresenter = new CommonListPresenter(this);
        Bundle bundle = getArguments();
        whatDay = bundle.getString("whatDay");
//        list = (List<LiveProgramListMo.LiveProgramBean>) bundle.getSerializable("list");
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
//        mProgressBar.setVisibility(View.VISIBLE);
//        loadData();
    }

    private void loadData() {
        mCommonListPresenter.getListData(pageNum + 1, LIMIT);
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
        recyclerView.post(new Runnable() {

            @Override
            public void run() {
                int height = recyclerView.getHeight(); // 获取高度
                int maxheight = DisplayUtil.dip2px(getContext(), 50);
                int itemHeight = height / 6;
                mAdapter.setItemHeight(itemHeight < maxheight ? itemHeight : maxheight);
            }
        });
        mAdapter = new QuickAdapter();
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);
        mAdapter.setNewData(list);
//        mAdapter.notifyDataSetChanged();
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setEnableRefresh(false);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(mEmptyLayout.getLayoutParams());
        lp.setMargins(0, 0, 0, 0);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        mEmptyLayout.setLayoutParams(lp);
    }


    private void loadMoreData() {
        isLoading = true;
        loadData();
    }

    public class QuickAdapter extends BaseQuickAdapter<LiveProgramListMo.LiveProgramBean, BaseViewHolder> {
        public int itemHeight;

        public QuickAdapter() {
            super(R.layout.item_program);
        }

        public void setItemHeight(int itemHeight) {
            this.itemHeight = itemHeight;
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, final LiveProgramListMo.LiveProgramBean item) {
            String time = item.getStart_time() + "-" + item.getEnd_time();
            if (item.isState()) {
                viewHolder.getView(R.id.content).setSelected(true);
                viewHolder.setTextColor(R.id.tv_time, getResources().getColor(R.color.mind)).setTextColor(R.id.tv_teacher_name, getResources().getColor(R.color.mind))
                        .setTextColor(R.id.tv_title, getResources().getColor(R.color.mind)).setText(R.id.tv_time, time).setText(R.id.tv_teacher_name, item.getTeacher()).setText(R.id.tv_title, item.getTitle()).setVisible(R.id.tv_live_status, true).setVisible(R.id.line, false);
            } else {
                viewHolder.getView(R.id.content).setSelected(false);
                viewHolder.setTextColor(R.id.tv_time, getResources().getColor(R.color.black1)).setTextColor(R.id.tv_teacher_name, getResources().getColor(R.color.black1))
                        .setTextColor(R.id.tv_title, getResources().getColor(R.color.black1)).setText(R.id.tv_time, time).setText(R.id.tv_teacher_name, item.getTeacher()).setText(R.id.tv_title, item.getTitle()).setVisible(R.id.tv_live_status, false).setVisible(R.id.line, true);
            }
//            viewHolder.getView(R.id.content).getLayoutParams().height = itemHeight - DisplayUtil.dip2px(getContext(), 0.5f);
//            viewHolder.itemView.getLayoutParams().height = itemHeight;
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(item.isState()){
                        PolyvLivePlayerActivity.actionStart(getActivity());
                    }
                }
            });
        }
    }

    private void initTopBar() {
        topbarView.setVisibility(View.GONE);
    }

    @Override
    public void showList(List<LiveProgramListMo.LiveProgramBean> list) {
        if (list.size() == 0) {
            mEmptyLayout.setVisibility(View.VISIBLE);
            emptyImg.setImageResource(R.drawable.icon_empty_content);
            emptyTips.setText("暂时没有课程安排");
            LoadMoreComplete();
        } else {
            mEmptyLayout.setVisibility(View.GONE);
            LoadMoreComplete();
        }
        mAdapter.setNewData(list);

//        if (pageNum > 1) {
//            if (addLen > 0) {
//                mAdapter.addData(srcPosition, feedModelList);
//            }
//        } else {
//            List<LiveProgramListMo.LiveProgramBean> list1 = new ArrayList<>();
//            list1.addAll(list);
//            mAdapter.setNewData(list1);
//        }
//        if (feedModelList.size() < 1 || list.size() < LIMIT) {
//            mRefreshLayout.finishLoadMoreWithNoMoreData();
//        }
//        isLoading = false;
    }

    private void refreshData(List<LiveProgramListMo.LiveProgramBean> feedModelList) {
        List<LiveProgramListMo.LiveProgramBean> list1 = new ArrayList<>();
        if (!ObjectUtils.isEmpty(feedModelList)) {
            list1.addAll(feedModelList);
        }
        if (mAdapter != null) {
            showList(list1);
        } else {
            list = list1;
        }
    }

    private void LoadMoreComplete() {
        isLoading = false;
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMore();
    }

    @Override
    public void onDataChange(List<LiveProgramListMo.LiveProgramBean> liveProgramBeans) {
        refreshData(liveProgramBeans);
    }

    @Override
    public void showFail(String errorMsg) {
        mProgressBar.setVisibility(View.GONE);
        ToastUtils.showShort(errorMsg);
        LoadMoreComplete();
        if (ObjectUtils.isEmpty(list)) {
            mEmptyLayout.setVisibility(View.VISIBLE);
            emptyImg.setImageResource(R.drawable.icon_no_network);
            emptyTips.setText(getString(R.string.no_network_tips));
        } else {
            mEmptyLayout.setVisibility(View.GONE);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThrea1(EventBusKeyDefine.EventBusMsgData<HashMap<String, List<LiveProgramListMo.LiveProgramBean>>> data) {
        int type = data.getType();
        try {
            if (EventBusKeyDefine.EVENTBUS_PROGRAM_LIST == type) {
                if (!ObjectUtils.isEmpty(data) && !ObjectUtils.isEmpty(data.getData())) {
                    for (String key : data.getData().keySet()) {
                        String weekDay = key;
                        if (whatDay.equals(weekDay)) {
                            List<LiveProgramListMo.LiveProgramBean> value = data.getData().get(key);
                            refreshData(value);
                        }
                    }
                }
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

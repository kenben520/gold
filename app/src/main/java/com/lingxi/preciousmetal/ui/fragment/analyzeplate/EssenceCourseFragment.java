package com.lingxi.preciousmetal.ui.fragment.analyzeplate;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.EssenceCourseRequestMo;
import com.lingxi.biz.domain.responseMo.EssenceCourseMo;
import com.lingxi.biz.service.AnalyseTradeService;
import com.lingxi.common.base.BaseApplication;
import com.lingxi.common.base.BaseFragment;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.common.EventBusKeyDefine;
import com.lingxi.preciousmetal.ui.activity.ViewPagerActivity;
import com.lingxi.preciousmetal.ui.widget.EssenceCourseHeaderView;
import com.lingxi.preciousmetal.ui.widget.ExpandableTextView;
import com.lingxi.preciousmetal.ui.widget.GridLayoutManagerWrapper;
import com.lingxi.preciousmetal.ui.widget.TopBarView;
import com.lingxi.preciousmetal.util.DisplayUtil;
import com.lingxi.preciousmetal.util.glideutils.GlideUtils;
import com.lingxi.preciousmetal.util.utilCode.ObjectUtils;
import com.lingxi.preciousmetal.util.utilCode.TimeUtils;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.view.View.VISIBLE;
import static com.lingxi.preciousmetal.util.utilCode.TimeUtils.FYMDHM;

/**
 * Created by zhangwei on 2018/4/25.
 */

public class EssenceCourseFragment extends BaseFragment {
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
    private List<EssenceCourseMo.CourseBean> list = new ArrayList<>();
    private View mContentView;
    Unbinder unbinder;
    private EssenceCourseHeaderView headerView;

    public static EssenceCourseFragment newInstance() {
        EssenceCourseFragment fragmentCommon = new EssenceCourseFragment();
        Bundle bundle = new Bundle();
        fragmentCommon.setArguments(bundle);
        return fragmentCommon;
    }


    @Override
    public void onViewCreated(@NonNull final View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
//        mCommonListPresenter = new CommonListPresenter(this);
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
        mProgressBar.setVisibility(VISIBLE);
        loadHeaderViewData();
        loadData();
    }

    private void loadHeaderViewData() {
        if (null != headerView) {
            headerView.loadData();
        }
    }

//    private void loadData() {
//        mCommonListPresenter.getListData(pageNum + 1, LIMIT);
//    }


    private void loadData() {
        EssenceCourseRequestMo liveListRequestMo = new EssenceCourseRequestMo(pageNum + 1, LIMIT);
        AnalyseTradeService.getEssenceCourse(liveListRequestMo, new BizResultListener<EssenceCourseMo>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, EssenceCourseMo showTradeListMo) {

            }

            @Override
            public void onSuccess(EssenceCourseMo showTradeListMo) {
                if (isAdded() && !isHidden()) {
                    if (showTradeListMo != null && showTradeListMo.getCourse() != null) {
                        showList(showTradeListMo.getCourse());
                    }
                    if (showTradeListMo != null && showTradeListMo.getTeacher() != null) {
                        refreshTeacherInfo(showTradeListMo.getTeacher());
                    }
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
                    loadHeaderViewData();
                    loadData();
                    isLoading = true;
                }
            }
        });
        headerView = new EssenceCourseHeaderView(getContext());
        headerView.setVisibility(View.GONE);
        mAdapter = new QuickAdapter();
        mAdapter.addHeaderView(headerView);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);
//        FrameLayout.LayoutParams lp1 = new FrameLayout.LayoutParams(recyclerView.getLayoutParams());
//        lp1.setMargins(0, DisplayUtil.dip2px(getContext(), 11), 0, 0);
//        recyclerView.setLayoutParams(lp1);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(layoutNoContent.getLayoutParams());
        lp.setMargins(0, DisplayUtil.dip2px(getContext(), 50), 0, 0);
        layoutNoContent.setLayoutParams(lp);
    }

    private void refreshTeacherInfo(EssenceCourseMo.TeacherBean teacher) {
        if (headerView != null) {
            if (teacher.getIs_online() == 1 || teacher.getIs_online() == 2 || teacher.getIs_online() == 3) {
                headerView.setVisibility(VISIBLE);
                headerView.refreshData(teacher);
            } else {
                headerView.setVisibility(View.GONE);
            }
        }
        EventBus.getDefault().post(new EventBusKeyDefine.EventBusMsgData<EssenceCourseMo.TeacherBean>(EventBusKeyDefine.EVENTBUS_LIVE_TEACHER, teacher));
    }

    private void loadMoreData() {
        isLoading = true;
        loadData();
    }

    public class QuickAdapter extends BaseQuickAdapter<EssenceCourseMo.CourseBean, BaseViewHolder> {
        public QuickAdapter() {
            super(R.layout.item_trade_strategy);
        }

        @Override
        protected void convert(final BaseViewHolder viewHolder, final EssenceCourseMo.CourseBean item) {
            viewHolder.setText(R.id.title1, item.getCou_tea_title()).setText(R.id.time, TimeUtils.getDataStr(item.getCou_release_time(), FYMDHM)+"  "+item.getTea_name());
            ExpandableTextView expandTextVie = viewHolder.getView(R.id.tv_content1);
            expandTextVie.setText(item.getCou_tea_comment());
            final RecyclerView recyclerView = viewHolder.getView(R.id.recyclerView_pic);
            recyclerView.setFocusable(false);
            recyclerView.setFocusableInTouchMode(false);
            QuickAdapter1 commonAdapter = new QuickAdapter1();
            recyclerView.setLayoutManager(new GridLayoutManagerWrapper(recyclerView.getContext(), 3));
            recyclerView.setAdapter(commonAdapter);
            List<String> list1 = new ArrayList<>();
            if (!ObjectUtils.isEmpty(item.getCou_tea_image())) {
                String[] pics = item.getCou_tea_image().split(",");
                if (!ObjectUtils.isEmpty(pics)) {
                    list1.addAll(Arrays.asList(pics));
                }
            }
            commonAdapter.setNewData(list1);
//            final LinearLayout layoutExpand = viewHolder.getView(R.id.layout_expand);
//            final LinearLayout layoutCollapse = viewHolder.getView(R.id.layout_collapse);
//            layoutExpand.setVisibility(VISIBLE);
//            layoutExpand.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    layoutExpand.setVisibility(View.GONE);
//                    recyclerView.setVisibility(View.VISIBLE);
//                    layoutCollapse.setVisibility(View.VISIBLE);
//                }
//            });
//            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    layoutExpand.setVisibility(View.GONE);
//                    recyclerView.setVisibility(VISIBLE);
//                    layoutCollapse.setVisibility(VISIBLE);
//                }
//            });
//            viewHolder.getView(R.id.layout_collapse).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    layoutExpand.setVisibility(VISIBLE);
//                    recyclerView.setVisibility(View.GONE);
//                    layoutCollapse.setVisibility(View.GONE);
//                }
//            });
        }
    }

    int recycleViewWidth = DisplayUtil.getScreenWidth(BaseApplication.getInstance()) - DisplayUtil.dip2px(BaseApplication.getInstance(), 50);
    int picWidth = recycleViewWidth / 3;

    public class QuickAdapter1 extends BaseQuickAdapter<String, BaseViewHolder> {
        public QuickAdapter1() {
            super(R.layout.item_trade_strategy_pic);
        }

        @Override
        protected void convert(final BaseViewHolder viewHolder, final String item) {
            ViewGroup.LayoutParams lp = viewHolder.itemView.getLayoutParams();
            lp.width = picWidth;
            lp.height = picWidth;
            ImageView iv_pic = viewHolder.getView(R.id.iv_pic);
            GlideUtils.loadImageViewCrop(getContext(), R.drawable.background_gray4, item, iv_pic);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<String> datas = (ArrayList) getData();
                    ViewPagerActivity.actionStart(getContext(), datas, viewHolder.getAdapterPosition());
                }
            });
        }
    }


    private void initTopBar() {
        topbarView.setVisibility(View.GONE);
    }

    public void showList(List<EssenceCourseMo.CourseBean> feedModelList) {
        mProgressBar.setVisibility(View.GONE);
        if (pageNum <= 0) {
            list.clear();
        }
        pageNum++;
        int srcPosition = list.size();
        int addLen = feedModelList.size();
        list.addAll(feedModelList);
        if (list.size() == 0) {
            mEmptyLayout.setVisibility(VISIBLE);
            layoutNoContent.setVisibility(VISIBLE);
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
            List<EssenceCourseMo.CourseBean> list1 = new ArrayList<>();
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
            mEmptyLayout.setVisibility(VISIBLE);
            layoutNoContent.setVisibility(VISIBLE);
            emptyImg.setImageResource(R.drawable.icon_no_network);
            emptyTips.setText(getString(R.string.no_network_tips));
            if (headerView != null) {
                headerView.setVisibility(View.GONE);
            }
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThrea1(EventBusKeyDefine.EventBusMsgData<Integer> data) {
        int type = data.getType();
        try {
            if (EventBusKeyDefine.EVENTBUS_LIVE_PEOPLE_COUNT == type) {
                if (!ObjectUtils.isEmpty(data) && !ObjectUtils.isEmpty(data.getData())) {
                    int peopleCount = data.getData();
                    headerView.livePeopleCountChange();
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
        headerView.release();
        unbinder.unbind();
    }
}

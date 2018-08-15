package com.lingxi.preciousmetal.ui.fragment.analyzeplate;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.lingxi.biz.domain.requestMo.TeacherVideosRequestMo;
import com.lingxi.biz.domain.responseMo.StudyVideoBean;
import com.lingxi.biz.domain.responseMo.TeacherVideosMo;
import com.lingxi.biz.service.AnalyseTradeService;
import com.lingxi.common.base.BaseApplication;
import com.lingxi.common.base.BaseFragment;
import com.lingxi.common.util.StringUtil;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.MyApplication;
import com.lingxi.preciousmetal.common.EventBusKeyDefine;
import com.lingxi.preciousmetal.ui.activity.PolyvPlayerActivity;
import com.lingxi.preciousmetal.ui.widget.GridLayoutManagerWrapper;
import com.lingxi.preciousmetal.ui.widget.TopBarView;
import com.lingxi.preciousmetal.util.DisplayUtil;
import com.lingxi.preciousmetal.util.glideutils.GlideUtils;
import com.lingxi.preciousmetal.util.utilCode.ObjectUtils;
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

public class TeacherVideoFragment extends BaseFragment {
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
    private List<StudyVideoBean.VideoListBean> list = new ArrayList<>();
    private View mContentView;
    Unbinder unbinder;
    String teacherId,teacherName;

    public static TeacherVideoFragment newInstance(String teacherId,String teacherName) {
        TeacherVideoFragment fragmentCommon = new TeacherVideoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("teacherId", teacherId);
        bundle.putString("teacherName", teacherName);
        fragmentCommon.setArguments(bundle);
        return fragmentCommon;
    }

    @Override
    public void onViewCreated(@NonNull final View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
//        mCommonListPresenter = new CommonListPresenter(this);
        teacherId = getArguments().getString("teacherId");
        teacherName = getArguments().getString("teacherName");
        registorEventBus();
        initView();
        bindData();
    }

    private int visibleHeight=0;
    private int itemEmptyCount=0;
    public void setVisibleHeight(int height)
    {
        visibleHeight=height;
        itemEmptyCount=(visibleHeight/itemHeight+1)*2;
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

    private void loadData() {
        TeacherVideosRequestMo liveListRequestMo = new TeacherVideosRequestMo(teacherId, pageNum + 1, LIMIT);
        AnalyseTradeService.getTeacherVideoList(liveListRequestMo, new BizResultListener<TeacherVideosMo>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, TeacherVideosMo teacherVideosMo) {

            }

            @Override
            public void onSuccess(TeacherVideosMo teacherVideosMo) {
                if (teacherVideosMo != null) {
                    showList(teacherVideosMo.getVideos());
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
        recyclerView.setLayoutManager(new GridLayoutManagerWrapper(recyclerView.getContext(), 2));
        recyclerView.setAdapter(mAdapter);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(recyclerView.getLayoutParams());
        lp.setMargins(0, DisplayUtil.dip2px(getContext(), 15), 0, 0);
        recyclerView.setLayoutParams(lp);

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
    int itemHeight =DisplayUtil.dip2px(BaseApplication.getInstance(), 148);

    public class QuickAdapter extends BaseQuickAdapter<StudyVideoBean.VideoListBean, BaseViewHolder> {
        public QuickAdapter() {
            super(R.layout.adapter_teacher_live_video);
        }

        @Override
        protected void convert(final BaseViewHolder viewHolder, final StudyVideoBean.VideoListBean item) {
            if (!item.isBlankData()) {
                viewHolder.getView(R.id.content).setVisibility(View.VISIBLE);
                viewHolder.setText(R.id.study_video_name_view, "《" + item.getTitle() + "》 ");
                GlideUtils.loadImageViewCrop(getContext(), R.drawable.background_gray4, item.getImage(), (ImageView) viewHolder.getView(R.id.study_video_image_view));
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!StringUtil.isEmpty(item.getVideo_id())) {
                            ArrayList<StudyVideoBean.VideoListBean> list = new ArrayList<>();
                            List<StudyVideoBean.VideoListBean> videoListBeans= getData();
                            for (int i=0;i<videoListBeans.size();i++)
                            {
                                StudyVideoBean.VideoListBean videoListBean=videoListBeans.get(i);
                                if (!videoListBean.isBlankData()) {
                                    videoListBean.setTea_name(teacherName);
                                    list.add(videoListBean);
                                }
                            }
                            PolyvPlayerActivity.intentTo(getContext(), item, list);
                        }
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

    public void showList(List<StudyVideoBean.VideoListBean> feedModelList) {
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
            List<StudyVideoBean.VideoListBean> list1 = new ArrayList<>();
            list1.addAll(list);
            mAdapter.setNewData(list1);
        }
        if (feedModelList.size() < 1 || list.size() < LIMIT) {
//            mRefreshLayout.setNoMoreData(true);//将不会再次触发加载更多事件
            if (list.size() < LIMIT) {
                // TODO: 2018/6/27 process the bug GridLayout下拉滚动异常
                int addSize = 8 - list.size();
                if (addSize > 0) {
                    List<StudyVideoBean.VideoListBean> list1 = new ArrayList<>();
                    list1.addAll(list);
                    for (int i = 0; i < addSize; i++) {
                        StudyVideoBean.VideoListBean videoListBean = new StudyVideoBean.VideoListBean();
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

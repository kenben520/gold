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
import com.lingxi.biz.domain.requestMo.LikeRequestMo;
import com.lingxi.biz.domain.requestMo.ShowTradeListRequestMo;
import com.lingxi.biz.domain.responseMo.LikeResultMo;
import com.lingxi.biz.domain.responseMo.ShowTradeListMo;
import com.lingxi.biz.service.AnalyseTradeService;
import com.lingxi.common.base.BaseApplication;
import com.lingxi.common.base.BaseFragment;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.common.EventBusKeyDefine;
import com.lingxi.preciousmetal.domain.UserInfoBean;
import com.lingxi.preciousmetal.login.LoginHelper;
import com.lingxi.preciousmetal.ui.activity.LoginActivity;
import com.lingxi.preciousmetal.ui.activity.MineTradeListActivity;
import com.lingxi.preciousmetal.ui.activity.ShowTradeActivity;
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
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by zhangwei on 2018/4/25.
 */

public class AnalyzePlateShowTradeFragment extends BaseFragment {
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
    @BindView(R.id.iv_show_trade)
    ImageView ivShowTrade;
    @BindView(R.id.iv_mine_trade_pic)
    ImageView ivMineTradePic;
    private QuickAdapter mAdapter;
    private static boolean isFirstEnter = true;
    //    private CommonListPresenter mCommonListPresenter;
    private static final int LIMIT = 10;
    private int pageNum = 0;
    private boolean isLoading = false;
    private List<ShowTradeListMo.SheetsListBean> list = new ArrayList<>();
    private View mContentView;
    Unbinder unbinder;
    UserInfoBean mUserInfoBean;

    public static AnalyzePlateShowTradeFragment newInstance() {
        AnalyzePlateShowTradeFragment fragmentCommon = new AnalyzePlateShowTradeFragment();
        Bundle bundle = new Bundle();
        fragmentCommon.setArguments(bundle);
        return fragmentCommon;
    }


    @Override
    public void onViewCreated(@NonNull final View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
//        mCommonListPresenter = new CommonListPresenter(this);
        registorEventBus();
        mUserInfoBean = LoginHelper.getInstance().getLoginUserInfo();
        initView();
        bindData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mContentView == null) {
            mContentView = inflater.inflate(R.layout.fragment_show_trade, container, false);
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
        ShowTradeListRequestMo liveListRequestMo;
        if (!ObjectUtils.isEmpty(mUserInfoBean) && !ObjectUtils.isEmpty(mUserInfoBean.user_id)) {
            liveListRequestMo = new ShowTradeListRequestMo(pageNum + 1, LIMIT, mUserInfoBean.user_id, 1);
        } else {
            liveListRequestMo = new ShowTradeListRequestMo(pageNum + 1, LIMIT, 1);
        }
        AnalyseTradeService.getShowTradeList(liveListRequestMo, new BizResultListener<ShowTradeListMo>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, ShowTradeListMo showTradeListMo) {

            }

            @Override
            public void onSuccess(ShowTradeListMo showTradeListMo) {
                if (showTradeListMo != null && showTradeListMo.getSheets_list() != null) {
                    showList(showTradeListMo.getSheets_list());
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
    }


    private void loadMoreData() {
        isLoading = true;
        loadData();
    }

    @OnClick({R.id.iv_show_trade, R.id.iv_mine_trade_pic})
    public void onViewClicked(View view) {
        boolean isLogin2 = LoginHelper.getInstance().isLogin();
        if (!isLogin2) {
            LoginActivity.actionStart(getActivity());
            return;
        }
        switch (view.getId()) {
            case R.id.iv_show_trade:
                ShowTradeActivity.actionStart(getContext());
                break;
            case R.id.iv_mine_trade_pic:
                MineTradeListActivity.actionStart(getContext());
                break;
        }
    }

//    int height = DisplayUtil.dip2px(BaseApplication.getInstance(), 290);
//    int width = height * 9 / 16;


    public class QuickAdapter extends BaseQuickAdapter<ShowTradeListMo.SheetsListBean, BaseViewHolder> {
        public QuickAdapter() {
            super(R.layout.item_show_trade);
        }

        @Override
        protected void convert(final BaseViewHolder viewHolder, final ShowTradeListMo.SheetsListBean item) {
//            ViewGroup.LayoutParams lp = viewHolder.getView(R.id.iv_image).getLayoutParams();
//            lp.width = width;
//            lp.height = height;
            viewHolder.setText(R.id.title1, item.getUser_name()).setText(R.id.tv_like_num, item.getShe_click_num() + "").setText(R.id.title3, item.getShe_heart()).setText(R.id.title2, TimeUtils.millis2String(item.getShe_add_time() * 1000));
            GlideUtils.loadImageViewCrop(getContext(), R.drawable.background_gray4, item.getShe_image(), (ImageView) viewHolder.getView(R.id.iv_image));

            GlideUtils.loadRoundImage(getContext(), R.drawable.default_header1, 40, item.getAvatars(), (ImageView) viewHolder.getView(R.id.iv_user_icon));
            if (item.isYourself_into()) {
                viewHolder.setTextColor(R.id.tv_like_num, getResources().getColor(R.color.redThree)).setImageResource(R.id.iv_like, R.drawable.ic_like6);
            } else {
                viewHolder.setTextColor(R.id.tv_like_num, getResources().getColor(R.color.warmGreyThree)).setImageResource(R.id.iv_like, R.drawable.ic_like5);
            }
            refreshLikeList(item, viewHolder);
            if (!item.isYourself_into()) {
                viewHolder.setTextColor(R.id.tv_like_num, getResources().getColor(R.color.warmGreyThree)).setImageResource(R.id.iv_like, R.drawable.ic_like5);
            } else {
                viewHolder.setTextColor(R.id.tv_like_num, getResources().getColor(R.color.redThree)).setImageResource(R.id.iv_like, R.drawable.ic_like6);
            }
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isLogin2 = LoginHelper.getInstance().isLogin();
                    if (isLogin2) {
                        UserInfoBean userInfoBean = LoginHelper.getInstance().getLoginUserInfo();
                        if (item.isYourself_into()) {
                            item.getThumbs_up_people().remove(userInfoBean.nick_name);
                            refreshLikeList(item, viewHolder);
                            like(item, userInfoBean.user_id);
                            viewHolder.setTextColor(R.id.tv_like_num, getResources().getColor(R.color.warmGreyThree)).setImageResource(R.id.iv_like, R.drawable.ic_like5);
                            int clickNum = Integer.parseInt(((TextView) (viewHolder.getView(R.id.tv_like_num))).getText().toString());
                            viewHolder.setText(R.id.tv_like_num, (--clickNum) + "");
                        } else {
                            item.getThumbs_up_people().add(0,userInfoBean.nick_name);
                            refreshLikeList(item, viewHolder);
                            like(item, userInfoBean.user_id);
                            viewHolder.setTextColor(R.id.tv_like_num, getResources().getColor(R.color.redThree)).setImageResource(R.id.iv_like, R.drawable.ic_like6);
                            int clickNum = Integer.parseInt(((TextView) (viewHolder.getView(R.id.tv_like_num))).getText().toString());
                            viewHolder.setText(R.id.tv_like_num, (++clickNum) + "");
                        }
                    }else{
                        ToastUtils.showLong("登陆后才可以点赞喔");
                    }
                }
            };
            viewHolder.getView(R.id.layout_like).setOnClickListener(listener);
            viewHolder.getView(R.id.iv_image).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<ShowTradeListMo.SheetsListBean> datas = getData();
                    ArrayList<String> list = new ArrayList<>();
                    for (int i = 0; i < datas.size(); i++) {
                        list.add(datas.get(i).getShe_image());
                    }
                    ViewPagerActivity.actionStart(getContext(), list, viewHolder.getAdapterPosition());
                }
            });
        }

        private void refreshLikeList(ShowTradeListMo.SheetsListBean item, BaseViewHolder viewHolder) {
            if (!ObjectUtils.isEmpty(item.getThumbs_up_people())) {
                StringBuilder sb = new StringBuilder();
                List<String> likePeoples = item.getThumbs_up_people();
                for (int i = 0; i < likePeoples.size(); i++) {
                    sb.append(likePeoples.get(i));

                    if (i < likePeoples.size() - 1) {
                        sb.append(",");
                    }
                }
                viewHolder.setText(R.id.tv_like_people, sb.toString());
                viewHolder.getView(R.id.layout_like_people).setVisibility(View.VISIBLE);
            } else {
                viewHolder.getView(R.id.layout_like_people).setVisibility(View.GONE);
            }
        }
    }

    public void like(final ShowTradeListMo.SheetsListBean item, String click_user_id) {
        int op_status;
        if (item.isYourself_into()) {
            op_status = 2;
            item.setYourself_into(false);
        } else {
            op_status = 1;
            item.setYourself_into(true);
        }
        LikeRequestMo liveListRequestMo = new LikeRequestMo(null, null, item.getId(), click_user_id, op_status);
        AnalyseTradeService.likeCourse(liveListRequestMo, new BizResultListener<LikeResultMo>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, LikeResultMo likeResultMo) {

            }

            @Override
            public void onSuccess(LikeResultMo likeResultMo) {
//                if (likeResultMo.is_click == 1) {//点赞成功
//                    item.setYourself_into(true);
//                    ToastUtils.showShort("点赞成功");
//                } else if (likeResultMo.is_click == 2) {//已经点赞过了
//                    ToastUtils.showShort("今日您已经进行过点赞");
//                    item.setYourself_into(true);
//                } else if (likeResultMo.is_click == 3) {//取消点赞成功
//                    item.setYourself_into(false);
//                    ToastUtils.showShort("取消点赞成功");
//                } else if (likeResultMo.is_click == 4) {//该会员还没点赞无法取消
//                    item.setYourself_into(false);
//                }
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
//                ToastUtils.showShort(bizMessage);
//                item.setYourself_into(false);
            }
        });
    }

    private void initTopBar() {
        topbarView.setVisibility(View.GONE);
    }

    public void showList(List<ShowTradeListMo.SheetsListBean> feedModelList) {
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
            List<ShowTradeListMo.SheetsListBean> list1 = new ArrayList<>();
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

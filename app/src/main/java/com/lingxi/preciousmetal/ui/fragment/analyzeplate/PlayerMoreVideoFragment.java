package com.lingxi.preciousmetal.ui.fragment.analyzeplate;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.easefun.polyvsdk.PolyvBitRate;
import com.lingxi.biz.domain.responseMo.StudyVideoBean;
import com.lingxi.biz.domain.responseMo.TeacherVideosMo;
import com.lingxi.common.base.BaseFragment;
import com.lingxi.common.util.StringUtil;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.common.EventBusKeyDefine;
import com.lingxi.preciousmetal.ui.activity.PolyvPlayerActivity;
import com.lingxi.preciousmetal.ui.widget.GridLayoutManagerWrapper;
import com.lingxi.preciousmetal.util.DisplayUtil;
import com.lingxi.preciousmetal.util.glideutils.GlideUtils;
import com.lingxi.preciousmetal.util.utilCode.TimeUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhangwei on 2018/4/25.
 */

public class PlayerMoreVideoFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ProgressBar)
    android.widget.ProgressBar mProgressBar;
    @BindView(R.id.empty_img)
    ImageView emptyImg;
    @BindView(R.id.empty_tips)
    TextView emptyTips;
    @BindView(R.id.empty_layout)
    LinearLayout mEmptyLayout;
    @BindView(R.id.layout_no_content)
    RelativeLayout layoutNoContent;
    private QuickAdapter mAdapter;
    private List<StudyVideoBean.VideoListBean> list = new ArrayList<>();
    private View mContentView;
    Unbinder unbinder;

    public static PlayerMoreVideoFragment newInstance(ArrayList<StudyVideoBean.VideoListBean> list) {
        PlayerMoreVideoFragment fragmentCommon = new PlayerMoreVideoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("list", (Serializable) list);
        fragmentCommon.setArguments(bundle);
        return fragmentCommon;
    }

    @Override
    public void onViewCreated(@NonNull final View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
        list = (List<StudyVideoBean.VideoListBean>) getArguments().getSerializable("list");
        if (list == null) list = new ArrayList<>();
        registorEventBus();
        initView();
        bindData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mContentView == null) {
            mContentView = inflater.inflate(R.layout.fragment_player_more_video, container, false);
        }
        unbinder = ButterKnife.bind(this, mContentView);
        return mContentView;
    }

    private void bindData() {
        showList(list);
    }

    private void initView() {
        mAdapter = new QuickAdapter();
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);
    }


    public class QuickAdapter extends BaseQuickAdapter<StudyVideoBean.VideoListBean, BaseViewHolder> {

        public QuickAdapter() {
            super(R.layout.item_more_teacher_video);
        }

        @Override
        protected void convert(final BaseViewHolder viewHolder, final StudyVideoBean.VideoListBean item) {
            viewHolder.setText(R.id.recommend_title_view, item.getTitle() );
            viewHolder.setText(R.id.recommend_time_view,  item.getVideo_time() );
            TextView recommend_context_view = viewHolder.getView(R.id.recommend_context_view);
            TextView recommend_date_view = viewHolder.getView(R.id.recommend_date_view);
            TextView recommend_time_view = viewHolder.getView(R.id.recommend_time_view);

            recommend_time_view.setText(item.getVideo_time());
            viewHolder.setText(R.id.recommend_date_view, item.getSub_title());
            String teacherName = item.getTea_name();
            if (TextUtils.isEmpty(teacherName)){
                recommend_date_view.setVisibility(View.GONE);
                recommend_context_view.setText(item.getSub_title());
            } else {
                recommend_context_view.setText(teacherName);
                recommend_date_view.setText(TimeUtils.millis2String(item.getAdd_time_stamp()*1000,TimeUtils.FORMAT_MD));
                recommend_date_view.setVisibility(View.VISIBLE);
            }
            GlideUtils.loadImageViewCrop(getContext(), R.drawable.background_gray4, item.getImage(), (ImageView) viewHolder.getView(R.id.recommend_imageView));
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!StringUtil.isEmpty(item.getVideo_id())) {
                        ((PolyvPlayerActivity) getActivity()).play(item, PolyvBitRate.ziDong.getNum(), true, false);
                    }
                }
            });
        }
    }

    public void showList(List<StudyVideoBean.VideoListBean> feedModelList) {
        if (list.size() == 0) {
            mEmptyLayout.setVisibility(View.VISIBLE);
            layoutNoContent.setVisibility(View.VISIBLE);
            emptyImg.setImageResource(R.drawable.icon_empty_content);
            emptyTips.setText("暂无更多视频");
        } else {
            mEmptyLayout.setVisibility(View.GONE);
            layoutNoContent.setVisibility(View.GONE);
        }
        List<StudyVideoBean.VideoListBean> list1 = new ArrayList<>();
        list1.addAll(list);
        mAdapter.setNewData(list1);
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

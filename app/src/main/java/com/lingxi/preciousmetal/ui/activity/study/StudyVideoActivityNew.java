package com.lingxi.preciousmetal.ui.activity.study;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.StudyVideoRequestMo;
import com.lingxi.biz.domain.responseMo.StudyVideoBean;
import com.lingxi.biz.service.InvestStudyService;
import com.lingxi.common.util.StringUtil;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.domain.UserInfoBean;
import com.lingxi.preciousmetal.login.LoginHelper;
import com.lingxi.preciousmetal.ui.activity.BaseListActivity;
import com.lingxi.preciousmetal.ui.activity.PolyvPlayerActivity;
import com.lingxi.preciousmetal.ui.widget.GridLayoutManagerWrapper;
import com.lingxi.preciousmetal.ui.widget.TopBarView;
import com.lingxi.preciousmetal.util.DisplayUtil;
import com.lingxi.preciousmetal.util.glideutils.GlideUtils;

public class StudyVideoActivityNew extends BaseListActivity<StudyVideoBean.VideoListBean> {

    private QuickAdapter mAdapter;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, StudyVideoActivityNew.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        bindData();
    }

    public void loadData() {
        StudyVideoRequestMo requestMo = new StudyVideoRequestMo(1, LIMIT, pageNum + 1);
        InvestStudyService.studyVideo(requestMo, new BizResultListener<StudyVideoBean>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, StudyVideoBean resultMo) {

            }

            @Override
            public void onSuccess(StudyVideoBean resultMo) {
                if (resultMo != null && resultMo.getVideo_list() != null) {
                    showList(resultMo.getVideo_list());
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
        mAdapter = new QuickAdapter();
        initView(mAdapter);
        recyclerView.setLayoutManager(new GridLayoutManagerWrapper(recyclerView.getContext(), 2));
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(recyclerView.getLayoutParams());
        lp.setMargins(DisplayUtil.dip2px(this, 5), DisplayUtil.dip2px(this, 15), DisplayUtil.dip2px(this, 5), 0);
        recyclerView.setLayoutParams(lp);
    }

    public class QuickAdapter extends BaseQuickAdapter<StudyVideoBean.VideoListBean, BaseViewHolder> {

        public QuickAdapter() {
            super(R.layout.item_study_video);
        }

        @Override
        protected void convert(final BaseViewHolder viewHolder, final StudyVideoBean.VideoListBean item) {
            viewHolder.setText(R.id.study_video_title_view, item.getTitle());
            viewHolder.setText(R.id.study_video_df_view, item.getSub_title());
            viewHolder.setText(R.id.study_video_time_view,  item.getVideo_time());
            GlideUtils.loadImageViewCrop(StudyVideoActivityNew.this, R.drawable.background_gray4, item.getImage(), (ImageView) viewHolder.getView(R.id.study_video_image_view));
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!StringUtil.isEmpty(item.getVideo_id())) {
                        PolyvPlayerActivity.intentTo(StudyVideoActivityNew.this, item, getData());
                    }
                }
            });
        }
    }

    public void initTopBar() {
        topbarView.setMode(TopBarView.MODE_BACK);
        topbarView.setTitle("教学视频");
    }

}

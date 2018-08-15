package com.lingxi.preciousmetal.ui.activity.specialist;

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
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.StudyVideoRequestMo;
import com.lingxi.biz.domain.responseMo.StudyVideoBean;
import com.lingxi.biz.service.InvestStudyService;
import com.lingxi.common.base.BaseFragment;
import com.lingxi.common.util.StringUtil;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.ui.activity.PolyvPlayerActivity;
import com.lingxi.preciousmetal.ui.widget.GridLayoutManagerWrapper;
import com.lingxi.preciousmetal.util.DisplayUtil;
import com.lingxi.preciousmetal.util.glideutils.GlideUtils;
import com.lingxi.preciousmetal.util.utilCode.LogUtils;
import com.lingxi.preciousmetal.util.utilCode.TimeUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentTeacherVideo extends BaseFragment {
    private int size = 0;
    private RecyclerView recyclerView;
    private List<StudyVideoBean.VideoListBean> studyVideoBeanList;
    private QuickAdapter mAdapter;
    private RefreshLayout refreshLayout;

    public static FragmentTeacherVideo newInstance(){
        FragmentTeacherVideo fragmentCommon=new FragmentTeacherVideo();
        Bundle bundle=new Bundle();
        fragmentCommon.setArguments(bundle);
        return fragmentCommon;
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.public_listview_layout2,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManagerWrapper(recyclerView.getContext(), 2));

//        studyVideoGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                StudyVideoBean.VideoListBean item =  studyVideoBeanList.get(position);
//                if(!StringUtil.isEmpty(item.getVideo_id())){
//                    PolyvPlayerActivity.intentTo(getContext(), item, studyVideoBeanList);
//                }
//            }
//        });
        size = 1;
        studyVideoBeanList = new ArrayList<>();
        mAdapter = new QuickAdapter();
        recyclerView.setAdapter(mAdapter);

        refreshLayout =  view.findViewById(R.id.refreshLayout);
        refreshLayout.setEnableAutoLoadMore(true);//开启自动加载功能（非必须）
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().post(new Runnable() {
                    @Override
                    public void run() {
                        size = 1;
                        studyVideoBeanList.clear();
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
        refreshLayout.autoRefresh();
   }

    private void loadData(){
        StudyVideoRequestMo requestMo  = new StudyVideoRequestMo(2,10,size);
        InvestStudyService.studyVideo(requestMo,new BizResultListener<StudyVideoBean>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, StudyVideoBean resultMo) {

            }

            @Override
            public void onSuccess(StudyVideoBean  resultMo) {
                if (resultMo!=null){
                    List temList = resultMo.getVideo_list();
                    if (temList!=null && temList.size()>0) {
                        size++;
                        studyVideoBeanList.addAll(temList);
                        mAdapter.setNewData(studyVideoBeanList);
                        refreshLayout.finishLoadMore();
                    } else {
                        refreshLayout.finishLoadMoreWithNoMoreData();
                    }
                }
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                LogUtils.i("investmentRequestMo请求失败");
            }
        });
    }

    public class QuickAdapter extends BaseQuickAdapter<StudyVideoBean.VideoListBean, BaseViewHolder> {
        public QuickAdapter() {
            super(R.layout.adapter_teacher_video);
        }

        @Override
        protected void convert(final BaseViewHolder viewHolder, final StudyVideoBean.VideoListBean item) {
            ImageView teacher_video_image_view = viewHolder.getView(R.id.teach_video_image_view);
            GlideUtils.loadImageViewCrop(mContext, R.drawable.background_gray4,item.getImage(),teacher_video_image_view);

            TextView teacher_video_title_view = viewHolder.getView(R.id.teach_video_title_view);
            teacher_video_title_view.setText(item.getTitle());

           // long time = TimeUtils.string2Millis(item.getAdd_time(),TimeUtils.DEFAULT_FORMAT2);
            String groupTime = TimeUtils.millis2String(item.getAdd_time_stamp()*1000,TimeUtils.FORMAT_MD);

            TextView teacher_video_name_view = viewHolder.getView(R.id.teach_video_name_view);
            teacher_video_name_view.setText(item.getTea_name());

            TextView teach_video_add_time_view = viewHolder.getView(R.id.teach_video_add_time_view);
            teach_video_add_time_view.setText(groupTime);

            TextView teacher_video_time_view = viewHolder.getView(R.id.teach_video_time_view);
            teacher_video_time_view.setText(item.getVideo_time());

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!StringUtil.isEmpty(item.getVideo_id())) {
                        PolyvPlayerActivity.intentTo(mContext, item, getData());
                    }
                }
            });
        }
    }
  }

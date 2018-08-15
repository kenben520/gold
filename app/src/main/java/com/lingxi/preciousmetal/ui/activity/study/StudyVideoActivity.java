package com.lingxi.preciousmetal.ui.activity.study;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.StudyVideoRequestMo;
import com.lingxi.biz.domain.responseMo.StudyVideoBean;
import com.lingxi.biz.service.InvestStudyService;
import com.lingxi.biz.util.ConstantUtil;
import com.lingxi.common.util.StringUtil;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.BaseActivity;
import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;
import com.lingxi.preciousmetal.ui.activity.PolyvPlayerActivity;
import com.lingxi.preciousmetal.ui.adapter.StudyVideoAdapter;
import com.lingxi.preciousmetal.ui.widget.TopBarView;
import com.lingxi.preciousmetal.util.utilCode.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class StudyVideoActivity extends TranslucentStatusBarActivity {

    public static void startMyActivity(Context activity, String fType){
        Intent intent = new Intent(activity,StudyVideoActivity.class);
        intent.putExtra("fType",fType);
        activity.startActivity(intent);
    }

    String frType;
    int size = 0;
    GridView studyVideoGridView;
    List<StudyVideoBean.VideoListBean> studyVideoBeanList;
    StudyVideoAdapter videoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_video);
        studyVideoGridView = findViewById(R.id.studyVideoGridView);
        studyVideoGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StudyVideoBean.VideoListBean item =  studyVideoBeanList.get(position);
                if(!StringUtil.isEmpty(item.getVideo_id())){
                    PolyvPlayerActivity.intentTo(mContext, item, studyVideoBeanList);
                }
            }
        });

        TopBarView mTopBarView =  findViewById(R.id.topbar_view);
        mTopBarView.setMode(TopBarView.MODE_BACK);
        frType = getIntent().getStringExtra("fType");
        mTopBarView.setTitle(frType);
        size = 1;
        studyVideoBeanList = new ArrayList<>();

        if (ConstantUtil.STUDYVIDEO.equals(frType)){
            StudyVideoRequestMo requestMo  = new StudyVideoRequestMo(1,10,size);
            InvestStudyService.studyVideo(requestMo,new BizResultListener<StudyVideoBean>() {
                @Override
                public void onPreExecute() {

                }

                @Override
                public void hitCache(boolean hit, StudyVideoBean resultMo) {

                }

                @Override
                public void onSuccess(StudyVideoBean  resultMo) {
                    studyVideoBeanList = resultMo.getVideo_list();
//                    videoAdapter = new StudyVideoAdapter(mContext,studyVideoBeanList,R.layout.adapter_study_video);
//                    studyVideoGridView.setAdapter(videoAdapter);
                    videoAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFail(int resultCode, int bizCode, String bizMessage) {
                    LogUtils.i("investmentRequestMo请求失败");
                }
            });
        }
    }
}

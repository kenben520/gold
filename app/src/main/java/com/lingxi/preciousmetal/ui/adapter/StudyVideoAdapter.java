package com.lingxi.preciousmetal.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lingxi.biz.domain.responseMo.KMarketResultBean;
import com.lingxi.biz.domain.responseMo.StudyVideoBean;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.ui.adapter.base.CommonAdapter;
import com.lingxi.preciousmetal.ui.adapter.base.ViewHolder;
import com.lingxi.preciousmetal.util.glideutils.GlideUtils;
import com.lingxi.preciousmetal.util.utilCode.AppUtils;

import java.util.List;


public class StudyVideoAdapter extends CommonAdapter<StudyVideoBean.VideoListBean> {

    public StudyVideoAdapter(Context context, List<StudyVideoBean.VideoListBean> datas, int itemLayoutResId) {
        super(context, datas, itemLayoutResId);
    }

    @Override
    public void convert(ViewHolder viewHolder, StudyVideoBean.VideoListBean item) {
        ImageView study_video_image_view = viewHolder.getView(R.id.study_video_image_view);
        TextView study_video_name_view = viewHolder.getView(R.id.study_video_name_view);
        study_video_name_view.setText("《"+item.getTitle() +"》 ");
        GlideUtils.loadImageViewCache(context,item.getImage(),study_video_image_view);
    }

}

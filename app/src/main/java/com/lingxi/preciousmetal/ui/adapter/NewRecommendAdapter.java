package com.lingxi.preciousmetal.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lingxi.biz.domain.responseMo.NewRecomBean;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.ui.adapter.base.CommonAdapter;
import com.lingxi.preciousmetal.ui.adapter.base.ViewHolder;
import com.lingxi.preciousmetal.util.glideutils.GlideUtils;
import java.util.List;

public class NewRecommendAdapter extends CommonAdapter<NewRecomBean.ItemsBean> {

    public NewRecommendAdapter(Context context, List<NewRecomBean.ItemsBean> datas, int itemLayoutResId) {
        super(context, datas, itemLayoutResId);
    }

    @Override
    public void convert(ViewHolder viewHolder, NewRecomBean.ItemsBean item) {
        ImageView recommend_imageView = viewHolder.getView(R.id.recommend_imageView);
        String imageUrl = item.getThumbnail();
        if (!TextUtils.isEmpty(imageUrl)){
            recommend_imageView.setVisibility(View.VISIBLE);
            GlideUtils.loadImageViewCrop(context, R.drawable.background_gray4, imageUrl,recommend_imageView);
        } else {
            recommend_imageView.setVisibility(View.GONE);
        }
        TextView recommend_title_view = viewHolder.getView(R.id.recommend_title_view);
        recommend_title_view.setText(item.getTitle());
        TextView recommend_time_view = viewHolder.getView(R.id.recommend_time_view);
        recommend_time_view.setText(item.getTime());
    }

}

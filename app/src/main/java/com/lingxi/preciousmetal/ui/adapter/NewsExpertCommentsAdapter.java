package com.lingxi.preciousmetal.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lingxi.biz.domain.responseMo.MessExpertResultMo;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.ui.adapter.base.CommonAdapter;
import com.lingxi.preciousmetal.ui.adapter.base.ViewHolder;
import com.lingxi.preciousmetal.util.utilCode.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NewsExpertCommentsAdapter extends CommonAdapter<MessExpertResultMo.ArticlesMBean> {

    public NewsExpertCommentsAdapter(Context context, List<MessExpertResultMo.ArticlesMBean> datas, int itemLayoutResId) {
        super(context, datas, itemLayoutResId);
    }

    @Override
    public void convert(ViewHolder viewHolder, MessExpertResultMo.ArticlesMBean bean) {
        TextView expert_title_textView = viewHolder.getView(R.id.expert_title_textView);
        expert_title_textView.setText(bean.title);

        TextView expert_content_textView = viewHolder.getView(R.id.expert_content_textView);
        expert_content_textView.setText(bean.content);

        TextView morning_time_textView = viewHolder.getView(R.id.expert_time_textView);
        morning_time_textView.setText(TimeUtils.getYMDToLong(bean.add_time));

        ImageView expert_comment_imageview = viewHolder.getView(R.id.expert_comment_imageview);
        if (bean.morning==1){
            expert_comment_imageview.setBackgroundResource(R.drawable.morning_comment_icon);
        } else {
            expert_comment_imageview.setBackgroundResource(R.drawable.night_comment_icon);
        }

    }
}

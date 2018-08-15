package com.lingxi.preciousmetal.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lingxi.biz.domain.responseMo.MessQuickResultBean;
import com.lingxi.biz.domain.responseMo.SpecialViewPointBean;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.ui.adapter.base.CommonAdapter;
import com.lingxi.preciousmetal.ui.adapter.base.ViewHolder;
import com.lingxi.preciousmetal.util.glideutils.GlideUtils;
import com.lingxi.preciousmetal.util.utilCode.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.List;


public class SpecialViewPointAdapter extends CommonAdapter<SpecialViewPointBean.ArticlesBean> {

	public SpecialViewPointAdapter(Context context, List<SpecialViewPointBean.ArticlesBean> datas, int itemLayoutResId) {
		super(context, datas, itemLayoutResId);
	}

	@Override
	public void convert(ViewHolder viewHolder, SpecialViewPointBean.ArticlesBean bean) {
		ImageView view_point_imageView = viewHolder.getView(R.id.view_point_imageView);
		String imageUrl = bean.getImage();
		if (!TextUtils.isEmpty(imageUrl)){
			GlideUtils.loadImageViewCrop(context, R.drawable.background_gray4, imageUrl,view_point_imageView);
		} else {
			view_point_imageView.setVisibility(View.GONE);
		}
		TextView view_point_title_view = viewHolder.getView(R.id.view_point_title_view);
		view_point_title_view.setText(bean.getTitle());

		TextView view_point_name_view = viewHolder.getView(R.id.view_point_name_view);
		view_point_name_view.setText(bean.getTea_name());

		TextView view_point_time_view = viewHolder.getView(R.id.view_point_time_view);
		view_point_time_view.setText(TimeUtils.millis2String(bean.getAdd_time()*1000));
	}
}

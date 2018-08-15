package com.lingxi.preciousmetal.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lingxi.biz.domain.responseMo.SpecialRemarkBean;
import com.lingxi.biz.domain.responseMo.SpecialViewPointBean;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.ui.adapter.base.CommonAdapter;
import com.lingxi.preciousmetal.ui.adapter.base.ViewHolder;
import com.lingxi.preciousmetal.util.glideutils.GlideUtils;
import com.lingxi.preciousmetal.util.utilCode.TimeUtils;

import java.util.List;


public class SpecialRemarkAdapter extends CommonAdapter<SpecialRemarkBean.ArticlesBean> {

	public SpecialRemarkAdapter(Context context, List<SpecialRemarkBean.ArticlesBean> datas, int itemLayoutResId) {
		super(context, datas, itemLayoutResId);
	}

	private String topTime;

	@Override
	public void convert(ViewHolder viewHolder, SpecialRemarkBean.ArticlesBean bean) {
	  View special_remark_time_layout = viewHolder.getView(R.id.special_remark_time_layout);
	  View special_remark_line_view = viewHolder.getView(R.id.special_remark_line_view);

      TextView special_remark_content_view = viewHolder.getView(R.id.special_remark_content_view);
      TextView special_remark_title_view = viewHolder.getView(R.id.special_remark_title_view);
      TextView special_remark_time_view = viewHolder.getView(R.id.special_remark_time_view);
	  ImageView special_remark_type_imageView = viewHolder.getView(R.id.special_remark_type_imageView);

	  special_remark_content_view.setText(bean.getContent());
	  special_remark_title_view.setText(bean.getTitle());
	  String time = TimeUtils.getYMDToLong(bean.getAdd_time());

      if (!time.equals(topTime)){
		  topTime = time;
		  special_remark_time_layout.setVisibility(View.VISIBLE);
		  special_remark_time_view.setText(time);

		  special_remark_line_view.setVisibility(View.GONE);
	  } else {
		  special_remark_line_view.setVisibility(View.VISIBLE);

		  special_remark_time_layout.setVisibility(View.GONE);
	  }

	  int morning = bean.getMorning();
	  if (morning==1){
		  special_remark_type_imageView.setBackground(ContextCompat.getDrawable(context,R.drawable.lx_strategy_morning));
	  } else {
		  special_remark_type_imageView.setBackground(ContextCompat.getDrawable(context,R.drawable.lx_strategy_night));
	  }

	}
}

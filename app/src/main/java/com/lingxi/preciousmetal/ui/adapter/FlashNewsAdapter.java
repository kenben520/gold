package com.lingxi.preciousmetal.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.tifezh.kchartlib.utils.ViewUtil;
import com.lingxi.biz.domain.responseMo.MessQuickResultBean;
import com.lingxi.common.base.BaseApplication;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.ui.adapter.base.CommonAdapter;
import com.lingxi.preciousmetal.ui.adapter.base.ViewHolder;
import com.lingxi.preciousmetal.util.utilCode.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;


public class FlashNewsAdapter extends CommonAdapter<MessQuickResultBean.ItemsBean> {

	public FlashNewsAdapter(Context context, List<MessQuickResultBean.ItemsBean> datas, int itemLayoutResId) {
		super(context, datas, itemLayoutResId);
	}

	@Override
	public void convert(ViewHolder viewHolder, MessQuickResultBean.ItemsBean bean) {

		TextView quick_new_time_textView = viewHolder.getView(R.id.quick_new_time_textView);
		quick_new_time_textView.setText(TimeUtils.millis2String(bean.getDisplay_time()*1000,new SimpleDateFormat("HH:mm:ss")));

		TextView quick_new_title_textView = viewHolder.getView(R.id.quick_new_title_textView);
		quick_new_title_textView.setText(bean.getContent_text());
		int red = bean.getRed();
		if (red==1){
			quick_new_title_textView.setTextColor(ContextCompat.getColor(context,R.color.chart_red));
		} else {
			quick_new_title_textView.setTextColor(ContextCompat.getColor(context,R.color.ff33));
		}

		LinearLayout flash_market_layout = viewHolder.getView(R.id.flash_market_layout);
		LinearLayout flash_value_layout = viewHolder.getView(R.id.flash_value_layout);
		int special = bean.getSpecial();
		if (special==1){
			flash_market_layout.setVisibility(View.VISIBLE);
			flash_value_layout.setVisibility(View.VISIBLE);

			TextView flash_bad_news_textView = viewHolder.getView(R.id.flash_bad_news_textView);
			TextView flash_good_news_textView = viewHolder.getView(R.id.flash_good_news_textView);
			TextView flash_front_value = viewHolder.getView(R.id.flash_front_value);
			TextView flash_expect_value = viewHolder.getView(R.id.flash_expect_value);
			TextView flash_actual_value = viewHolder.getView(R.id.flash_actual_value);

			flash_good_news_textView.setText("利多："+bean.getRed_get());
			flash_bad_news_textView.setText("利空："+bean.getGreen_get());

			flash_front_value.setText("前值 : "+bean.getFront());
			flash_expect_value.setText("预期 : "+bean.getExpect());
			flash_actual_value.setText("实际值 : "+bean.getActual());

			if (bean.getOrange_get()==1){
				flash_good_news_textView.setTextColor(ContextCompat.getColor(context,R.color.FFCA731F));
				flash_good_news_textView.setBackground(ContextCompat.getDrawable(context,R.drawable.shape_white_bg_yellow_border_circle));
				flash_good_news_textView.setText("中性");
				flash_good_news_textView.setVisibility(View.VISIBLE);
				flash_bad_news_textView.setVisibility(View.GONE);
			} else {
				flash_good_news_textView.setBackground(ContextCompat.getDrawable(context,R.drawable.shape_white_bg_red_border_circle));

				String redStr = bean.getRed_get();
				if (TextUtils.isEmpty(redStr)){
					flash_good_news_textView.setVisibility(View.GONE);
				} else {
					flash_good_news_textView.setVisibility(View.VISIBLE);
					flash_good_news_textView.setText("利多："+redStr);
				}
				String green = bean.getGreen_get();
				if (TextUtils.isEmpty(green)){
					flash_bad_news_textView.setVisibility(View.GONE);
				} else {
					flash_bad_news_textView.setVisibility(View.VISIBLE);
					flash_bad_news_textView.setText("利空："+green);
				}
			}
			if (BaseApplication.kIndexColor==0){
				flash_good_news_textView.setTextColor(ViewUtil.getKUpColor(context));
				flash_bad_news_textView.setTextColor(ViewUtil.getKLossColor(context));
				flash_good_news_textView.setBackground(ContextCompat.getDrawable(context,R.drawable.shape_white_bg_green_border_circle));
				flash_bad_news_textView.setBackground(ContextCompat.getDrawable(context,R.drawable.shape_white_bg_red_border_circle));
			} else {
				flash_good_news_textView.setTextColor(ViewUtil.getKUpColor(context));
				flash_bad_news_textView.setTextColor(ViewUtil.getKLossColor(context));
				flash_bad_news_textView.setBackground(ContextCompat.getDrawable(context,R.drawable.shape_white_bg_green_border_circle));
				flash_good_news_textView.setBackground(ContextCompat.getDrawable(context,R.drawable.shape_white_bg_red_border_circle));
			}
		} else {
			flash_market_layout.setVisibility(View.GONE);
			flash_value_layout.setVisibility(View.GONE);
		}
	}
}

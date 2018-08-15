package com.lingxi.preciousmetal.ui.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lingxi.biz.domain.responseMo.CalendarResultBean;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.ui.adapter.base.CommonAdapter;
import com.lingxi.preciousmetal.ui.adapter.base.ViewHolder;
import com.lingxi.preciousmetal.ui.widget.ImageRatingView;
import com.lingxi.preciousmetal.util.glideutils.GlideUtils;
import com.lingxi.preciousmetal.util.utilCode.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.List;

public class NewsCalendarAdapter extends CommonAdapter<CalendarResultBean.CalendarBean> {

	public NewsCalendarAdapter(Context context, List<CalendarResultBean.CalendarBean> datas, int itemLayoutResId) {
		super(context, datas, itemLayoutResId);
	}

	@Override
	public void convert(ViewHolder viewHolder,CalendarResultBean.CalendarBean item) {
		TextView calendar_time_txt = viewHolder.getView(R.id.calendar_time_txt);
		calendar_time_txt.setText(TimeUtils.millis2String(item.getTimestamp()*1000,new SimpleDateFormat("HH:mm")));

		ImageView calendar_country_image = viewHolder.getView(R.id.calendar_country_image);
		GlideUtils.loadImageViewCache(context,item.getFlagURL(),calendar_country_image);

		ImageRatingView calendar_ratingBar = viewHolder.getView(R.id.calendar_ratingBar);
		calendar_ratingBar.setRating(item.getStars());

	    TextView calendar_title_tex = viewHolder.getView(R.id.calendar_title_tex);
		calendar_title_tex.setText(item.getTitle());

		TextView calendar_actual_text = viewHolder.getView(R.id.calendar_actual_text);
		calendar_actual_text.setText("公布值："+item.getActual());

		TextView calendar_forecast_text = viewHolder.getView(R.id.calendar_forecast_text);
		calendar_forecast_text.setText("预测值："+item.getForecast());

		TextView calendar_previous_text = viewHolder.getView(R.id.calendar_previous_text);
		calendar_previous_text.setText("前值："+item.getPrevious());
	}
 
}

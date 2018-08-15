package com.lingxi.preciousmetal.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lingxi.biz.domain.responseMo.HomeAllResultBean;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.ui.adapter.base.CommonAdapter;
import com.lingxi.preciousmetal.ui.adapter.base.ViewHolder;
import com.lingxi.preciousmetal.ui.widget.ImageRatingView;

import java.util.List;
import java.util.Map;


public class NewsDealAdapter extends CommonAdapter<Object> {

	public NewsDealAdapter(Context context, List<Object> datas, int itemLayoutResId) {
		super(context, datas, itemLayoutResId);
	}

	@Override
	public void convert(ViewHolder viewHolder,Object obj) {
//		LinearLayout calendar_layout = viewHolder.getView(R.id.calendar_layout);
//		TextView detail_textview = viewHolder.getView(R.id.detail_textview);
//		ImageView news_type_imageview = viewHolder.getView(R.id.news_type_imageview);
//		TextView news_type_name = viewHolder.getView(R.id.news_type_name);
//		TextView news_title_textView = viewHolder.getView(R.id.news_title_textView);
//
//		if (obj instanceof HomeAllResultBean.FinanceBean) {
//			ImageRatingView market_ratingBar = viewHolder.getView(R.id.market_ratingBar);
//            TextView finance_value_textView = viewHolder.getView(R.id.finance_value_textView);
//            HomeAllResultBean.FinanceBean bean = (HomeAllResultBean.FinanceBean)obj;
//			market_ratingBar.setRating(bean.getStars());
//
//            news_title_textView.setText(bean.getTitle());
//            String value = "前值："+bean.getPrevious()+"    预测值："+bean.getForecast();
//            finance_value_textView.setText(value);
//            news_type_name.setText("日历");
//            news_type_imageview.setBackgroundResource(R.mipmap.home_calendar_icon);
//            calendar_layout.setVisibility(View.VISIBLE);
//            detail_textview.setVisibility(View.GONE);
//		} else if(obj instanceof HomeAllResultBean.QuickBean){
//            HomeAllResultBean.QuickBean bean = (HomeAllResultBean.QuickBean)obj;
//            news_type_name.setText("快讯");
//            news_title_textView.setText(bean.getContent_text());
//            news_type_imageview.setBackgroundResource(R.mipmap.home_message_icon);
//            calendar_layout.setVisibility(View.GONE);
//            detail_textview.setVisibility(View.GONE);
//		} else if(obj instanceof HomeAllResultBean.ImportantBean){
//            HomeAllResultBean.ImportantBean bean = (HomeAllResultBean.ImportantBean)obj;
//			news_type_name.setText("要闻");
//            news_title_textView.setText(bean.getTitle());
//            detail_textview.setText(bean.getShort_content());
//			news_type_imageview.setBackgroundResource(R.mipmap.home_message_icon);
//			calendar_layout.setVisibility(View.GONE);
//			detail_textview.setVisibility(View.VISIBLE);
//		}

//		if (item.getImportant()!=null){
//			news_title_textView.setText(item.getImportant().getTitle());
//			news_type_name.setText("要闻");
//			news_type_imageview.setBackgroundResource(R.mipmap.home_message_icon);
//			calendar_layout.setVisibility(View.GONE);
//			detail_textview.setVisibility(View.GONE);
//		} else if (item.getQuick()!=null){
//			news_type_name.setText("资讯");
//			news_type_imageview.setBackgroundResource(R.mipmap.home_message_icon);
//			calendar_layout.setVisibility(View.GONE);
//			detail_textview.setVisibility(View.VISIBLE);
//		}  else if (item.getQuick()!=null){
//			news_type_name.setText("日历");
//			news_type_imageview.setBackgroundResource(R.mipmap.home_calendar_icon);
//			calendar_layout.setVisibility(View.VISIBLE);
//			detail_textview.setVisibility(View.GONE);
//		}
//		news_title_textView.setText(item.get("titleName"));
//		String type = item.get("type");
//		if ("1".equals(type)){
//			news_type_name.setText("日历");
//			news_type_imageview.setBackgroundResource(R.mipmap.home_calendar_icon);
//			calendar_layout.setVisibility(View.VISIBLE);
//			detail_textview.setVisibility(View.GONE);
//		} else if("3".equals(type)){
//			news_type_name.setText("资讯");
//			news_type_imageview.setBackgroundResource(R.mipmap.home_message_icon);
//			calendar_layout.setVisibility(View.GONE);
//			detail_textview.setVisibility(View.VISIBLE);
//		} else if("2".equals(type)){
//			news_type_name.setText("要闻");
//			news_type_imageview.setBackgroundResource(R.mipmap.home_message_icon);
//			calendar_layout.setVisibility(View.GONE);
//			detail_textview.setVisibility(View.GONE);
//		}
 
	}
 
}

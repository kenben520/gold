package com.lingxi.preciousmetal.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lingxi.biz.domain.responseMo.MessQuickResultBean;
import com.lingxi.biz.domain.responseMo.TradeMoneyHistoryBean;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.ui.adapter.base.CommonAdapter;
import com.lingxi.preciousmetal.ui.adapter.base.ViewHolder;
import com.lingxi.preciousmetal.util.utilCode.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.List;

public class TMoneyHistoryAdapter extends CommonAdapter<TradeMoneyHistoryBean> {

	public TMoneyHistoryAdapter(Context context, List<TradeMoneyHistoryBean> datas, int itemLayoutResId) {
		super(context, datas, itemLayoutResId);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = ViewHolder.getViewHolder(context, itemLayoutResId, position, convertView, parent);
		convert(viewHolder, getItem(position));
		View money_layout = viewHolder.getView(R.id.money_layout);
		if (position%2==0){
			money_layout.setBackgroundColor(ContextCompat.getColor(context, R.color.whiteTwo));
		} else {
			money_layout.setBackgroundColor(ContextCompat.getColor(context, R.color.whiteFive));
		}
		return viewHolder.getConvertView();
	}

	@Override
	public void convert(ViewHolder viewHolder, TradeMoneyHistoryBean bean) {
		TextView money_type_view = viewHolder.getView(R.id.money_type_view);
		String type = bean.getFundingType();
		if ("Deposit".equals(type)){
			money_type_view.setText("入金");
		} else {
			money_type_view.setText("出金");
		}
		TextView money_money_view = viewHolder.getView(R.id.money_money_view);
		money_money_view.setText("$"+bean.getAmount());

		TextView money_time_view = viewHolder.getView(R.id.money_time_view);
		money_time_view.setText(TimeUtils.stringToMDTZ(bean.getTime(),TimeUtils.FYMDHMS));
	}
 
}

package com.lingxi.preciousmetal.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.tifezh.kchartlib.utils.ViewUtil;
import com.lingxi.biz.domain.responseMo.ClearOrderHistoryBean;
import com.lingxi.biz.util.ConstantUtil;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.ui.adapter.base.CommonAdapter;
import com.lingxi.preciousmetal.ui.adapter.base.ViewHolder;
import com.lingxi.preciousmetal.util.utilCode.AppUtils;
import com.lingxi.preciousmetal.util.utilCode.AppViewUtils;
import com.lingxi.preciousmetal.util.utilCode.TimeUtils;

import java.util.List;


public class TClearOrderHistoryAdapter extends CommonAdapter<ClearOrderHistoryBean>{

	public TClearOrderHistoryAdapter(Context context, List<ClearOrderHistoryBean> datas, int itemLayoutResId) {
		super(context, datas, itemLayoutResId);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = ViewHolder.getViewHolder(context, itemLayoutResId, position, convertView, parent);
		convert(viewHolder, getItem(position));
		View layoutView = viewHolder.getView(R.id.clear_order_layout);
		if (position%2==0){
			layoutView.setBackgroundColor(ContextCompat.getColor(context, R.color.whiteTwo));
		} else {
			layoutView.setBackgroundColor(ContextCompat.getColor(context, R.color.whiteFive));
		}
		return viewHolder.getConvertView();
	}

	@Override
	public void convert(ViewHolder viewHolder, ClearOrderHistoryBean bean) {
		TextView position_type_view = viewHolder.getView(R.id.clear_order_type_view);
		if (ConstantUtil.XAUUSD.equals(bean.getSymbol())) {
			position_type_view.setText("伦敦金");
		} else if (ConstantUtil.XAGUSD.equals(bean.getSymbol())) {
			position_type_view.setText("伦敦银");
		}else {
			position_type_view.setText(bean.getSymbol());
		}
		TextView clear_order_direction_view = viewHolder.getView(R.id.clear_order_direction_view);
		AppViewUtils.setBuyOrSell(context,bean.getOrderDirection(),clear_order_direction_view);

		TextView position_volume_view = viewHolder.getView(R.id.clear_order_volume_view);
		position_volume_view.setText(bean.getVolume()+"");
		AppUtils.setCustomFont(context,position_volume_view);

		TextView position_id_view = viewHolder.getView(R.id.clear_order_id_view);
		position_id_view.setText("#"+bean.getPositionId());

		TextView clear_order_price_view = viewHolder.getView(R.id.clear_order_price_view);
		String openPrice = String.format("%.2f",bean.getOpenPrice());
		clear_order_price_view.setText(String.valueOf(openPrice));
		AppUtils.setCustomFont(context,clear_order_price_view);

		TextView clear_order_close_view = viewHolder.getView(R.id.clear_order_close_view);
		String closePrice = String.format("%.2f",bean.getClosePrice());
		clear_order_close_view.setText(String.valueOf(closePrice));
		AppUtils.setCustomFont(context,clear_order_close_view);

		TextView position_pnl_view = viewHolder.getView(R.id.clear_order_profit_view);
		double pnl = bean.getPnl();
		if (pnl>0){
			position_pnl_view.setTextColor(ViewUtil.getKUpColor(context));
			position_pnl_view.setText("+"+pnl);
		} else {
			position_pnl_view.setTextColor(ViewUtil.getKLossColor(context));
			position_pnl_view.setText(""+pnl);
		}
		AppUtils.setCustomFont(context,position_pnl_view);

		TextView clear_order_time_view = viewHolder.getView(R.id.clear_order_time_view);
		clear_order_time_view.setText(TimeUtils.stringToMDTZ(bean.getDateTime(),TimeUtils.FYMDHMS2));
	}

}

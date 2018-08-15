package com.lingxi.preciousmetal.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.tifezh.kchartlib.utils.ViewUtil;
import com.lingxi.biz.domain.responseMo.TradeAllEntrustBean;
import com.lingxi.biz.domain.responseMo.TradeMoneyHistoryBean;
import com.lingxi.biz.util.ConstantUtil;
import com.lingxi.common.util.AppUtil;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.ui.adapter.base.CommonAdapter;
import com.lingxi.preciousmetal.ui.adapter.base.ViewHolder;
import com.lingxi.preciousmetal.util.utilCode.AppUtils;
import com.lingxi.preciousmetal.util.utilCode.AppViewUtils;
import com.lingxi.preciousmetal.util.utilCode.TimeUtils;

import java.util.List;

public class TEntrustAdapter extends CommonAdapter<TradeAllEntrustBean> {

	public TEntrustAdapter(Context context, List<TradeAllEntrustBean> datas, int itemLayoutResId) {
		super(context, datas, itemLayoutResId);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = ViewHolder.getViewHolder(context, itemLayoutResId, position, convertView, parent);
		convert(viewHolder, getItem(position));
		View layoutView = viewHolder.getView(R.id.entrust_order_layout);
		View vl1 = viewHolder.getView(R.id.vl1);
		View vl2 = viewHolder.getView(R.id.vl2);
		if (position%2==0){
			layoutView.setBackgroundColor(ContextCompat.getColor(context, R.color.whiteTwo));
			vl1.setBackgroundColor(ContextCompat.getColor(context, R.color.whiteFive));
			vl2.setBackgroundColor(ContextCompat.getColor(context, R.color.whiteFive));
		} else {
			layoutView.setBackgroundColor(ContextCompat.getColor(context, R.color.whiteFive));
			vl1.setBackgroundColor(ContextCompat.getColor(context, R.color.whiteSix));
			vl2.setBackgroundColor(ContextCompat.getColor(context, R.color.whiteSix));
		}
		return viewHolder.getConvertView();
	}

	@Override
	public void convert(ViewHolder viewHolder, TradeAllEntrustBean bean) {
		TextView entrust_type_view = viewHolder.getView(R.id.entrust_type_view);
		if (ConstantUtil.XAUUSD.equals(bean.getSymbol())) {
			entrust_type_view.setText("伦敦金");
		} else if (ConstantUtil.XAGUSD.equals(bean.getSymbol())) {
			entrust_type_view.setText("伦敦银");
		}else {
			entrust_type_view.setText(bean.getSymbol());
		}
//		String orderDirection = bean.getOrderDirection();
//		TextView entrust_direction_view = viewHolder.getView(R.id.entrust_direction_view);
//		if ("buy".equals(orderDirection)){
//			entrust_direction_view.setText(context.getString(R.string.kBuy));
//			entrust_direction_view.setBackgroundColor(ViewUtil.getKUpColor(context));
//		} else {
//			entrust_direction_view.setBackgroundColor(ViewUtil.getKLossColor(context));
//			entrust_direction_view.setText(context.getString(R.string.kSell));
//		}
		String orderDirection = bean.getOrderDirection();
//		TextView entrust_direction_view = viewHolder.getView(R.id.entrust_direction_view);
//		if ("buy".equals(orderDirection)){
//			entrust_direction_view.setText("买涨");
//			entrust_direction_view.setBackgroundColor(ViewUtil.getKUpColor(context));
//		} else {
//			entrust_direction_view.setBackgroundColor(ViewUtil.getKLossColor(context));
//			entrust_direction_view.setText("买跌");
//		}

		TextView entrust_volume_view = viewHolder.getView(R.id.entrust_volume_view);
		entrust_volume_view.setText(AppViewUtils.getBuyOrSellStr(orderDirection)+"/"+bean.getVolume()+"手");

		TextView position_time_view = viewHolder.getView(R.id.position_time_view);
		position_time_view.setText(TimeUtils.stringToMDTZ(bean.getDateTime(),TimeUtils.FMDHMS));

		TextView entrust_price_view = viewHolder.getView(R.id.entrust_price_view);
		String openPrice = String.format("%.2f",bean.getOrderPrice());
		entrust_price_view.setText(openPrice);
		AppUtils.setCustomFont(context,entrust_price_view);

		TextView entrust_now_price_view = viewHolder.getView(R.id.entrust_now_price_view);

//		ImageView iv_k_up_down = viewHolder.getView(R.id.iv_k_up_down);

//		entrust_now_price_view.setText("--");
		double pnl = 0.01;// TODO: 2018/8/3 需要修改
		if (pnl>0){
			entrust_now_price_view.setTextColor(ViewUtil.getKUpColor(context));
			entrust_now_price_view.setText(pnl+"");
//			iv_k_up_down.setBackground(AppViewUtils.getKUpArrowImage(context));
		} else {
			entrust_now_price_view.setTextColor(ViewUtil.getKLossColor(context));
			entrust_now_price_view.setText(pnl+"");
//			iv_k_up_down.setBackground(AppViewUtils.getKLossArrowImage(context));
		}
		AppUtils.setCustomFont(context,entrust_now_price_view);

		TextView entrust_validity_view = viewHolder.getView(R.id.entrust_validity_view);
		String orderDuration = bean.getOrderDuration();
		if ("GoodTillCancel".equals(orderDuration)){
			entrust_validity_view.setText("长期有效");
		} else if ("GoodTillDate".equals(orderDuration)){
			entrust_validity_view.setText("限期有效");
		} else if ("FillOrKill".equals(orderDuration)){
			entrust_validity_view.setText("即时全部");
		} else if ("DayOrder".equals(orderDuration)){
			entrust_validity_view.setText("当日有效");
		}
		AppUtils.setCustomFont(context,entrust_validity_view);
	}
}

package com.lingxi.preciousmetal.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.tifezh.kchartlib.utils.ViewUtil;
import com.lingxi.biz.domain.responseMo.KMarketResultBean;
import com.lingxi.biz.domain.responseMo.TradeAllPositionBean;
import com.lingxi.biz.domain.responseMo.TradeMoneyHistoryBean;
import com.lingxi.biz.util.ConstantUtil;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.MyApplication;
import com.lingxi.preciousmetal.ui.activity.trade.StartBuyGoldActivity;
import com.lingxi.preciousmetal.ui.adapter.base.CommonAdapter;
import com.lingxi.preciousmetal.ui.adapter.base.ViewHolder;
import com.lingxi.preciousmetal.util.utilCode.AppUtils;
import com.lingxi.preciousmetal.util.utilCode.AppViewUtils;

import java.util.List;


public class TPositionHistoryAdapter extends CommonAdapter<TradeAllPositionBean> implements View.OnClickListener{

	private InnerItemOnclickListener mListener;


	public interface InnerItemOnclickListener {
		void itemClick(View v);
	}

	public void setOnInnerItemOnClickListener(InnerItemOnclickListener listener){
		this.mListener=listener;
	}

	public TPositionHistoryAdapter(Context context, List<TradeAllPositionBean> datas, int itemLayoutResId) {
		super(context, datas, itemLayoutResId);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = ViewHolder.getViewHolder(context, itemLayoutResId, position, convertView, parent);
		convert(viewHolder, getItem(position));
		View layoutView = viewHolder.getView(R.id.trade_position_layout);
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
	public void convert(ViewHolder viewHolder, TradeAllPositionBean bean) {
		String spread = "0.00";//點差
		String format= "%.2f";
		TextView position_type_view = viewHolder.getView(R.id.position_type_view);
		if (ConstantUtil.XAUUSD.equals(bean.getSymbol())) {
			position_type_view.setText("伦敦金");
			spread = "0.50";
			format = "%.2f";
		} else if (ConstantUtil.XAGUSD.equals(bean.getSymbol())) {
			position_type_view.setText("伦敦银");
			spread = "0.04";
			format = "%.3f";
		}else {
			position_type_view.setText(bean.getSymbol());
		}
		String orderDirection = bean.getOrderDirection();
		TextView position_direction_view = viewHolder.getView(R.id.position_direction_view);
		AppViewUtils.setBuyOrSell(context,orderDirection,position_direction_view);

		TextView position_volume_view = viewHolder.getView(R.id.position_volume_view);
		position_volume_view.setText(AppViewUtils.getBuyOrSellStr(orderDirection)+"/"+bean.getVolume()+"手");

		ImageView iv_k_up_down = viewHolder.getView(R.id.iv_k_up_down);

		TextView position_id_view = viewHolder.getView(R.id.position_id_view);
		position_id_view.setText("#"+bean.getPositionId());

		TextView position_price_view = viewHolder.getView(R.id.position_price_view);
		position_price_view.setText(String.format(format,bean.getOpenPrice()));

		TextView position_now_price_view = viewHolder.getView(R.id.position_now_price_view);
//		position_now_price_view.setText(String.format(format,bean.getClosePrice()));
		position_now_price_view.setText(String.format(format,getNowPrice(bean.getSymbol())));

		TextView position_pnl_view = viewHolder.getView(R.id.position_pnl_view);
		double pnl = bean.getPnl();
		if (pnl>0){
			position_pnl_view.setTextColor(ViewUtil.getKUpColor(context));
			position_pnl_view.setText("+"+pnl);
			iv_k_up_down.setBackground(AppViewUtils.getKUpArrowImage(context));
		} else {
			position_pnl_view.setTextColor(ViewUtil.getKLossColor(context));
			position_pnl_view.setText(""+pnl);
			iv_k_up_down.setBackground(AppViewUtils.getKLossArrowImage(context));
		}
		AppViewUtils.setAlphaAnimator(context,iv_k_up_down);
		AppUtils.setCustomFont(context,position_pnl_view);

		TextView position_close_view = viewHolder.getView(R.id.position_close_view);
		position_close_view.setTag(bean);
		position_close_view.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
//		StartBuyGoldActivity.startMyActivity(context,3);
		mListener.itemClick(v);
	}

	private double getNowPrice(String goodsName) {
		List<KMarketResultBean.MarketBean> marketList = MyApplication.marketBeanList;
		KMarketResultBean.MarketBean marketBean = null;
		if (ConstantUtil.XAUUSD.equals(goodsName)) {
			if (marketList != null && marketList.size() >= 3) {
				marketBean = marketList.get(0);
			}
		} else if (ConstantUtil.XAGUSD.equals(goodsName)) {
			if (marketList != null && marketList.size() >= 3) {
				marketBean = marketList.get(1);
			}
		}
		if (marketBean != null) {
			return marketBean.last_px;
		} else {
			return 0;
		}
	}

}

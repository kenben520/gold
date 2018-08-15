package com.lingxi.preciousmetal.ui.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.tifezh.kchartlib.utils.ViewUtil;
import com.lingxi.biz.domain.responseMo.KMarketResultBean;
import com.lingxi.biz.domain.responseMo.KnowledgeResultMo;
import com.lingxi.biz.util.ConstantUtil;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.chart.activity.ChartActivity;
import com.lingxi.preciousmetal.login.LoginHelper;
import com.lingxi.preciousmetal.ui.activity.LoginActivity;
import com.lingxi.preciousmetal.ui.activity.trade.StartBuyGoldActivity;
import com.lingxi.preciousmetal.ui.adapter.base.CommonAdapter;
import com.lingxi.preciousmetal.ui.adapter.base.ViewHolder;
import com.lingxi.preciousmetal.util.utilCode.AppUtils;
import com.lingxi.preciousmetal.util.utilCode.AppViewUtils;

import java.util.List;


public class KMarketAdapter extends CommonAdapter<KMarketResultBean.MarketBean> implements View.OnClickListener{


    public KMarketAdapter(Context context, List<KMarketResultBean.MarketBean> datas, int itemLayoutResId) {
        super(context, datas, itemLayoutResId);
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder viewHolder = ViewHolder.getViewHolder(context, itemLayoutResId, position, convertView, parent);
//        convert(viewHolder, getItem(position));
//        LinearLayout kMarket_adapter_layout = viewHolder.getView(R.id.kMarket_adapter_layout);
//        if (position==1){
//            kMarket_adapter_layout.setBackgroundColor(ContextCompat.getColor(context, R.color.whiteTwo));
//        } else {
//            kMarket_adapter_layout.setBackgroundColor(ContextCompat.getColor(context, R.color.whiteFive));
//        }
//        return viewHolder.getConvertView();
//    }

    @Override
    public void convert(ViewHolder viewHolder, KMarketResultBean.MarketBean item) {
        String code = item.p_short_code;
        TextView goods_name_textView = viewHolder.getView(R.id.goods_name_textView);
        goods_name_textView.setText(item.prod_name+"("+code+")");
        TextView goods_spread_textView = viewHolder.getView(R.id.goods_spread_textView);
        TextView price_percent_textView = viewHolder.getView(R.id.price_percent_textView);

        TextView goods_sell_textView = viewHolder.getView(R.id.goods_sell_textView);
        TextView goods_buy_textView = viewHolder.getView(R.id.goods_buy_textView);
        ImageView goods_sell_direction_imageView = viewHolder.getView(R.id.goods_sell_direction_imageView);
        ImageView goods_buy_direction_imageView = viewHolder.getView(R.id.goods_buy_direction_imageView);
        LinearLayout goods_sell_layoutView = viewHolder.getView(R.id.goods_sell_layoutView);
        LinearLayout goods_buy_layoutView = viewHolder.getView(R.id.goods_buy_layoutView);
        goods_buy_layoutView.setOnClickListener(this);
        goods_sell_layoutView.setOnClickListener(this);
        goods_buy_layoutView.setTag(item);
        goods_sell_layoutView.setTag(item);

        TextView goods_sell_str_textView = viewHolder.getView(R.id.goods_sell_str_textView);
        TextView goods_buy_str_textView = viewHolder.getView(R.id.goods_buy_str_textView);
        goods_buy_str_textView.setVisibility(View.VISIBLE);
        goods_sell_str_textView.setVisibility(View.VISIBLE);

        String p_code = item.p_code;
        String valueStr = "%.2f";
        String spread = "0.00";
        float buyPrice = item.last_px;
        int kColor;

        goods_buy_layoutView.setBackground(null);
        goods_sell_layoutView.setBackground(null);

        if (ConstantUtil.USDOLLARINDEX.equals(p_code)){
            goods_buy_str_textView.setVisibility(View.GONE);
            goods_sell_str_textView.setVisibility(View.GONE);
            valueStr = ConstantUtil.DECIMAUS;
            buyPrice = item.last_px;
            spread = "0.00";
        } else if (ConstantUtil.XAGUSD.equals(p_code)){
            valueStr = ConstantUtil.DECIMALSSILVER;
            buyPrice =  item.last_px+0.04f;
            spread = "0.04";
        } else if (ConstantUtil.XAUUSD.equals(p_code)){
            buyPrice = item.last_px+0.5f;
            spread = "0.50";
            valueStr = ConstantUtil.DECIMALSGOLD;
        }
        goods_spread_textView.setText("点差:"+spread);

        Drawable signalImageView = null;
        if (item.flagKUp==1){
            signalImageView = ContextCompat.getDrawable(context,R.drawable.lx_mark_signal_top);
            kColor = ViewUtil.getKUpColor(context);
        } else if (item.flagKUp==2){
            signalImageView = ContextCompat.getDrawable(context,R.drawable.lx_mark_signal_up);
            kColor = ViewUtil.getKLossColor(context);
        } else {
            kColor = ViewUtil.getKNormalColor(context,1);
        }
        goods_buy_direction_imageView.setBackground(signalImageView);
        goods_sell_direction_imageView.setBackground(signalImageView);

        AppViewUtils.setDrawableRadius(goods_sell_layoutView,5,kColor);
        AppViewUtils.setDrawableRadius(goods_buy_layoutView,5,kColor);

        String buyPx = String.format(valueStr,item.last_px);
        String sellPx = String.format(valueStr,buyPrice);
        goods_sell_textView.setText(sellPx);
        goods_buy_textView.setText(buyPx);

        AppUtils.setTextSize(sellPx,goods_buy_textView);
        AppUtils.setTextSize(buyPx,goods_sell_textView);
        AppUtils.setCustomFont(context,goods_buy_textView);
        AppUtils.setCustomFont(context,goods_sell_textView);

        float change = item.px_change;
        float px_change_rate = item.px_change_rate;
        String changeStr = "";

        if (change>0){
            changeStr = "+"+String.format("%.2f",change) + "%  " +String.format(valueStr,px_change_rate);
            price_percent_textView.setTextColor(ViewUtil.getKUpColor(context));
        } else {
            changeStr = String.format("%.2f",change) + "%  " +String.format(valueStr,px_change_rate);
            price_percent_textView.setTextColor(ViewUtil.getKLossColor(context));
        }
        price_percent_textView.setText(changeStr);

        String low_px = String.format(valueStr,item.low_px);
        String high_px = String.format(valueStr,item.high_px);
        String open_px = String.format(valueStr,item.open_px);
        String preclose_px = String.format(valueStr,item.preclose_px);

        TextView goods_min_textView = viewHolder.getView(R.id.goods_min_textView);
        goods_min_textView.setText("低"+low_px);

        TextView goods_max_textView = viewHolder.getView(R.id.goods_max_textView);
        goods_max_textView.setText("高"+high_px);

        TextView goods_open_textView = viewHolder.getView(R.id.goods_open_textView);
        goods_open_textView.setText("开"+open_px);

        TextView goods_close_textView = viewHolder.getView(R.id.goods_close_textView);
        goods_close_textView.setText("收："+preclose_px);
    }

    @Override
    public void onClick(View v) {
        KMarketResultBean.MarketBean bean = (KMarketResultBean.MarketBean) v.getTag();
        String name =  bean.prod_name+"("+bean.p_short_code+")";
        String code =  bean.p_code;
        if (ConstantUtil.USDOLLARINDEX.equals(code)){
            ChartActivity.startMyActivity(context,code,0);
            return;
        }
        if (!LoginHelper.getInstance().isLogin()) {
            LoginActivity.actionStart(context);
            return;
        }
        StartBuyGoldActivity.startMyActivity(context,0,name,code);
    }
}

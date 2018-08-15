package com.lingxi.preciousmetal.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.tifezh.kchartlib.utils.ViewUtil;
import com.lingxi.biz.domain.responseMo.KMarketResultBean;
import com.lingxi.biz.domain.responseMo.PositionStatisticsBean;
import com.lingxi.biz.util.ConstantUtil;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.ui.activity.trade.StartBuyGoldActivity;
import com.lingxi.preciousmetal.ui.adapter.base.CommonAdapter;
import com.lingxi.preciousmetal.ui.adapter.base.ViewHolder;
import com.lingxi.preciousmetal.util.utilCode.AppUtils;
import com.lingxi.preciousmetal.util.utilCode.AppViewUtils;

import java.util.List;


public class PostionAnalyseAdapter extends CommonAdapter<PositionStatisticsBean.CftcDataBean> implements View.OnClickListener{


    public PostionAnalyseAdapter(Context context, List<PositionStatisticsBean.CftcDataBean> datas, int itemLayoutResId) {
        super(context, datas, itemLayoutResId);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.getViewHolder(context, itemLayoutResId, position, convertView, parent);
        convert(viewHolder, getItem(position));
        LinearLayout kMarket_adapter_layout = viewHolder.getView(R.id.adapter_position_analyse);
        if (position%2==1){
            kMarket_adapter_layout.setBackgroundColor(ContextCompat.getColor(context, R.color.whiteTwo));
        } else {
            kMarket_adapter_layout.setBackgroundColor(ContextCompat.getColor(context, R.color.whiteFive));
        }
        return viewHolder.getConvertView();
    }

    @Override
    public void convert(ViewHolder viewHolder, PositionStatisticsBean.CftcDataBean item) {
        TextView analyse_name_textView = viewHolder.getView(R.id.analyse_name_textView);
        TextView analyse_up_textView = viewHolder.getView(R.id.analyse_up_textView);
        TextView analyse_loss_textView = viewHolder.getView(R.id.analyse_loss_textView);
        TextView analyse_time_textView = viewHolder.getView(R.id.analyse_time_textView);
        analyse_time_textView.setText(item.getDate());
        analyse_name_textView.setText(item.getName());
        analyse_up_textView.setText(item.getMore_number());
        analyse_loss_textView.setText(item.getReduce_number());
        AppUtils.setCustomFont(context,analyse_name_textView);
        AppUtils.setCustomFont(context,analyse_up_textView);
        AppUtils.setCustomFont(context,analyse_loss_textView);
        AppUtils.setCustomFont(context,analyse_time_textView);
    }

    @Override
    public void onClick(View v) {

    }
}

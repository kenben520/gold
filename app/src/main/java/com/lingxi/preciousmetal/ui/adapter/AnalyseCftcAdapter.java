package com.lingxi.preciousmetal.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.tifezh.kchartlib.utils.ViewUtil;
import com.lingxi.biz.domain.responseMo.KMarketResultBean;
import com.lingxi.biz.domain.responseMo.PositionStatisticsBean;
import com.lingxi.common.base.BaseApplication;
import com.lingxi.common.util.AppUtil;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.ui.adapter.base.CommonAdapter;
import com.lingxi.preciousmetal.ui.adapter.base.ViewHolder;
import com.lingxi.preciousmetal.ui.widget.CustomSeekBar;
import com.lingxi.preciousmetal.util.utilCode.AppUtils;
import com.lingxi.preciousmetal.util.utilCode.AppViewUtils;

import java.util.List;


public class AnalyseCftcAdapter extends CommonAdapter<PositionStatisticsBean.CftcDataBean> implements View.OnClickListener{


    public AnalyseCftcAdapter(Context context, List<PositionStatisticsBean.CftcDataBean> datas, int itemLayoutResId) {
        super(context, datas, itemLayoutResId);
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder viewHolder = ViewHolder.getViewHolder(context, itemLayoutResId, position, convertView, parent);
//        convert(viewHolder, getItem(position));
//        LinearLayout kMarket_adapter_layout = viewHolder.getView(R.id.adapter_position_analyse);
//        if (position%2==1){
//            kMarket_adapter_layout.setBackgroundColor(ContextCompat.getColor(context, R.color.whiteTwo));
//        } else {
//            kMarket_adapter_layout.setBackgroundColor(ContextCompat.getColor(context, R.color.whiteFive));
//        }
//        return viewHolder.getConvertView();
//    }

    @Override
    public void convert(ViewHolder viewHolder,PositionStatisticsBean.CftcDataBean item) {
        TextView cftc_name_textView = viewHolder.getView(R.id.cftc_name_textView);
        TextView cftc_up_textView = viewHolder.getView(R.id.cftc_up_textView);
        TextView cftc_loss_textView = viewHolder.getView(R.id.cftc_loss_textView);
        CustomSeekBar cftc_seekbarView = viewHolder.getView(R.id.cftc_seekbarView);
        if (BaseApplication.kIndexColor==0){
            cftc_seekbarView.setProgressDrawable(ContextCompat.getDrawable(context,R.drawable.po_green_red_seekbar));
        } else {
            cftc_seekbarView.setProgressDrawable(ContextCompat.getDrawable(context,R.drawable.po_red_green_seekbar));
        }
        cftc_seekbarView.setProgress(item.getMore_rate());
        cftc_name_textView.setText(item.getName());
        cftc_up_textView.setText(item.getMore_rate()+"%");
        cftc_loss_textView.setText(item.getReduce_rate()+"%");
        AppUtils.setCustomFont(context,cftc_loss_textView);
        AppUtils.setCustomFont(context,cftc_up_textView);
        cftc_up_textView.setTextColor(ViewUtil.getKUpColor(context));
        cftc_loss_textView.setTextColor(ViewUtil.getKLossColor(context));
    }

    @Override
    public void onClick(View v) {

    }
}

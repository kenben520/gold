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
import com.lingxi.biz.domain.responseMo.NewNoticeBean;
import com.lingxi.biz.util.ConstantUtil;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.ui.activity.WebViewActivity;
import com.lingxi.preciousmetal.ui.activity.trade.StartBuyGoldActivity;
import com.lingxi.preciousmetal.ui.adapter.base.CommonAdapter;
import com.lingxi.preciousmetal.ui.adapter.base.ViewHolder;
import com.lingxi.preciousmetal.util.utilCode.AppUtils;
import com.lingxi.preciousmetal.util.utilCode.AppViewUtils;

import java.util.List;


public class NoticeAdapter extends CommonAdapter<NewNoticeBean.ItemsBean> implements View.OnClickListener{


    public NoticeAdapter(Context context, List<NewNoticeBean.ItemsBean> datas, int itemLayoutResId) {
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
    public void convert(ViewHolder viewHolder, NewNoticeBean.ItemsBean item) {
        TextView notice_name_textView = viewHolder.getView(R.id.notice_name_textView);
        TextView notice_content_textView = viewHolder.getView(R.id.notice_content_textView);
        TextView notice_time_textView = viewHolder.getView(R.id.notice_time_textView);
        TextView notice_look_content_textView = viewHolder.getView(R.id.notice_look_content_textView);
        notice_name_textView.setText(item.getTitle());
        notice_content_textView.setText(item.getContent());
        notice_time_textView.setText(item.getFormet_add_time());
        notice_look_content_textView.setTag(item);
      //  notice_look_content_textView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        NewNoticeBean.ItemsBean item =  (NewNoticeBean.ItemsBean)v.getTag();
        WebViewActivity.actionStart(context, item.getLink());
    }
}

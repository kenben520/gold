package com.lingxi.preciousmetal.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.domain.TimeTitleBean;
import com.lingxi.preciousmetal.ui.adapter.base.CommonAdapter;
import com.lingxi.preciousmetal.ui.adapter.base.ViewHolder;

import java.util.List;


public class KTimeTitlePortAdapter extends CommonAdapter<TimeTitleBean> {

    public KTimeTitlePortAdapter(Context context, List<TimeTitleBean> datas, int itemLayoutResId) {
        super(context, datas, itemLayoutResId);
    }

    private int mSelectIndex;

    public void setSelectIndex(int selectIndex){
        mSelectIndex = selectIndex;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size()-1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }

    @Override
    public void convert(ViewHolder viewHolder, TimeTitleBean item) {
        TextView time_title_textView =  viewHolder.getView(R.id.time_title_textView);
        time_title_textView.setText(item.getTimeTitle());
        View time_left_line_layout = viewHolder.getView(R.id.time_left_line_layout);
        if (mSelectIndex==item.getQueryType()){
            time_title_textView.setTextColor(ContextCompat.getColor(context,R.color.whiteColor));
            time_left_line_layout.setVisibility(View.VISIBLE);
        } else {
            time_title_textView.setTextColor(ContextCompat.getColor(context,R.color.whiteTwo33));
            time_left_line_layout.setVisibility(View.GONE);
        }
    }
}

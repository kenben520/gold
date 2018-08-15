package com.lingxi.preciousmetal.ui.adapter;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.domain.CalendarItem;
import com.lingxi.preciousmetal.domain.FilterSection;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class IndicatorSectionAdapter extends BaseSectionQuickAdapter<FilterSection, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     * @param sectionHeadResId The section head layout id for each item
     * @param layoutResId      The layout resource id of each item.
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public IndicatorSectionAdapter(int layoutResId, int sectionHeadResId, List data) {
        super(layoutResId, sectionHeadResId, data);
    }

    private boolean flagDisPlay;

    public void setDisplayGone(boolean flag){
        flagDisPlay = flag;
        notifyDataSetChanged();
    }

    private IndicatorOnclickListener indicatorOnclickListener;

    public interface IndicatorOnclickListener {
        void itemClick(boolean flag);
    }

    public void setIndicatorOnclickListener(IndicatorOnclickListener listener){
        this.indicatorOnclickListener=listener;
    }

    @Override
    protected void convertHead(BaseViewHolder helper, final FilterSection item) {
        helper.setText(R.id.header, item.header);
        RadioGroup radioGroup = helper.getView(R.id.radioGroup_indicator_layout);
        RadioButton indicator_gone = helper.getView(R.id.indicator_gone);
        RadioButton indicator_visible = helper.getView(R.id.indicator_visible);
        if (flagDisPlay){
            indicator_visible.setChecked(true);
        } else {
            indicator_gone.setChecked(true);
        }
        if ("主图指标".equals(item.header)){
            radioGroup.setVisibility(View.GONE);
        } else {
            radioGroup.setVisibility(View.VISIBLE);
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
               if (checkedId==R.id.indicator_gone){
                   indicatorOnclickListener.itemClick(false);
               } else if (checkedId==R.id.indicator_visible){
                   indicatorOnclickListener.itemClick(true);
               }
                }
            });
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, FilterSection item) {
        CalendarItem video = (CalendarItem) item.t;
        TextView tv = helper.getView(R.id.tv);;
        helper.setText(R.id.tv, video.getName());
        LinearLayout filter_layout = helper.getView(R.id.filter_layout);
        if (video.isUserCheck()){
            tv.setTextColor(ContextCompat.getColor(mContext, R.color.mind));
            filter_layout.setBackgroundResource(R.color.mindTr);
        } else {
            tv.setTextColor(ContextCompat.getColor(mContext, R.color.warmGreyTwo));
            filter_layout.setBackgroundResource(R.color.whiteTwo);
        }
    }
}

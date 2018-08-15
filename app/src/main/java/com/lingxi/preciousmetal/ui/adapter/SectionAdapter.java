package com.lingxi.preciousmetal.ui.adapter;

import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.domain.FilterSection;
import com.lingxi.preciousmetal.domain.CalendarItem;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class SectionAdapter extends BaseSectionQuickAdapter<FilterSection, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param sectionHeadResId The section head layout id for each item
     * @param layoutResId      The layout resource id of each item.
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public SectionAdapter(int layoutResId, int sectionHeadResId, List data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, final FilterSection item) {
        helper.setText(R.id.header, item.header);
//        helper.setVisible(R.id.more, item.isMore());
//        helper.addOnClickListener(R.id.more);
    }

    @Override
    protected void convert(BaseViewHolder helper, FilterSection item) {
        CalendarItem video = (CalendarItem) item.t;
        helper.setText(R.id.tv, video.getName());
        LinearLayout filter_layout = helper.getView(R.id.filter_layout);
        if (video.isUserCheck()){
            filter_layout.setBackgroundResource(R.drawable.solid_light_mind);
        } else {
            filter_layout.setBackgroundResource(R.drawable.db_radius_white);
        }
    }
}

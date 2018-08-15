package com.lingxi.preciousmetal.ui.adapter;

import android.content.Context;
import android.widget.TextView;

import com.lingxi.biz.domain.responseMo.KnowledgeResultMo;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.ui.adapter.base.CommonAdapter;
import com.lingxi.preciousmetal.ui.adapter.base.ViewHolder;

import java.util.List;
import java.util.Map;


public class KnowledgeCateAdapter extends CommonAdapter<KnowledgeResultMo.ArticlesBean> {

    public KnowledgeCateAdapter(Context context, List<KnowledgeResultMo.ArticlesBean> datas, int itemLayoutResId) {
        super(context, datas, itemLayoutResId);
    }

    @Override
    public void convert(ViewHolder viewHolder, KnowledgeResultMo.ArticlesBean item) {
        TextView titleView = viewHolder.getView(R.id.item_title_textview);
        titleView.setText(item.title);
    }

}

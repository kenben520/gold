package com.lingxi.preciousmetal.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lingxi.biz.util.ConstantUtil;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.ui.adapter.base.CommonAdapter;
import com.lingxi.preciousmetal.ui.adapter.base.ViewHolder;

import java.util.List;
import java.util.Map;


public class KnowledgeTypeAdapter extends CommonAdapter<Map<String, Object>> {
    List<Map<String, Object>> mDatas;

    public KnowledgeTypeAdapter(Context context, List<Map<String, Object>> datas, int itemLayoutResId) {
        super(context, datas, itemLayoutResId);
        mDatas = datas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.getViewHolder(context, itemLayoutResId, position, convertView, parent);
        convert(viewHolder, getItem(position));

        return viewHolder.getConvertView();
    }

    @Override
    public void convert(ViewHolder viewHolder, Map<String, Object> item) {
        TextView knowledge_type_title_view = viewHolder.getView(R.id.knowledge_type_title_view);
        String title = (String) item.get("title");
        knowledge_type_title_view.setText(title);

        TextView knowledge_type_content_view = viewHolder.getView(R.id.knowledge_type_content_view);
        String content = (String) item.get("content");
        knowledge_type_content_view.setText(content);

        LinearLayout knowledge_type_bg_imageView = viewHolder.getView(R.id.knowledge_type_bg_imageView);
        knowledge_type_bg_imageView.setBackground(ContextCompat.getDrawable(context, (int) item.get("image")));

        View knowledge_type_h_view = viewHolder.getView(R.id.knowledge_type_h_view);
        if ( ConstantUtil.TRADEQA.equals(title)) {
            knowledge_type_h_view.setVisibility(View.VISIBLE);
        } else {
            knowledge_type_h_view.setVisibility(View.GONE);
        }
//		ImageView knowledge_type_imageView = viewHolder.getView(R.id.knowledge_type_imageView);
//		knowledge_type_imageView.setBackground(ContextCompat.getDrawable(context,(int)item.get("contentBg")));
    }

}

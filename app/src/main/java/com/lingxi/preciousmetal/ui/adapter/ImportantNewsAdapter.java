package com.lingxi.preciousmetal.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.lingxi.biz.domain.responseMo.MessImportResultBean;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.ui.adapter.base.CommonAdapter;
import com.lingxi.preciousmetal.ui.adapter.base.ViewHolder;
import com.lingxi.preciousmetal.util.utilCode.TimeUtils;

import java.util.List;
import java.util.Map;

public class ImportantNewsAdapter extends CommonAdapter<MessImportResultBean.ItemsBean> {

    public ImportantNewsAdapter(Context context, List<MessImportResultBean.ItemsBean> datas, int itemLayoutResId) {
        super(context, datas, itemLayoutResId);
    }

    @Override
    public void convert(ViewHolder viewHolder, MessImportResultBean.ItemsBean bean) {
        TextView important_title_textVIew = viewHolder.getView(R.id.important_title_textVIew);
        viewHolder.getView(R.id.divider).setVisibility(View.GONE);
        important_title_textVIew.setText(bean.title);
        TextView important_time_textVIew = viewHolder.getView(R.id.important_time_textVIew);
        important_time_textVIew.setText(TimeUtils.millis2String(bean.add_time * 1000));
    }

}

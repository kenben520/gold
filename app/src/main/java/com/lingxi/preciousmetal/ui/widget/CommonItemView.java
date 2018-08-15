package com.lingxi.preciousmetal.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lingxi.preciousmetal.R;
/**
 * Created by zhangwei on 2018/4/17.
 */
public class CommonItemView extends LinearLayout {
    private Context mContext;

    public CommonItemView(Context context) {
        this(context, null);
    }

    public CommonItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        init(context);
    }

    public ImageView new_notice_imgView;

    public void initData(final CommonItemBean data) {
        if (data == null) return;
        View mDriverTopView = this.findViewById(R.id.driver_view);
        View mDriverBottomView = this.findViewById(R.id.set_view_item_divider);
        ImageView img = (ImageView) this.findViewById(R.id.set_imageview_img);
        new_notice_imgView = (ImageView) this.findViewById(R.id.new_notice_imgView);
        TextView tv_title = (TextView) this.findViewById(R.id.set_textview_title);
        TextView tv_right_title = (TextView) this.findViewById(R.id.set_textview_right_title);
        ImageView rightArrow = (ImageView) this.findViewById(R.id.set_imageview_right_arrow);
        View divider = this.findViewById(R.id.set_view_divider);

        if (data.isShowTopDivider) {
            mDriverTopView.setVisibility(VISIBLE);
        } else {
            mDriverTopView.setVisibility(GONE);
        }
        if (data.isShowBottomDivider) {
            mDriverBottomView.setVisibility(VISIBLE);
        } else {
            mDriverBottomView.setVisibility(GONE);
        }
        if (data.isShowRightArrow) {
            rightArrow.setVisibility(VISIBLE);
        } else {
            rightArrow.setVisibility(GONE);
        }
//        if (data.id == 5 || data.id == 2) {
//            divider.setVisibility(INVISIBLE);
//        } else {
//            divider.setVisibility(VISIBLE);
//        }
        if (data.imgResId > 0){
            img.setVisibility(VISIBLE);
            img.setImageResource(data.imgResId);
        }else{
            img.setVisibility(GONE);
        }

        tv_title.setText("" + data.title);
        tv_right_title.setText("" + data.rightTitle);


//        this.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
    }

    public void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.mine_menu_item, this);

    }

}

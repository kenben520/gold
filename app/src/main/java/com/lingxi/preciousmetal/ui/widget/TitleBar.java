package com.lingxi.preciousmetal.ui.widget;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.util.utilCode.StringUtils;

public class TitleBar {

        /**
         * 左侧/右侧图标和中间标题
         */
        private View titleView;

        /**
         * 左侧/右侧图标和中间标题
         */
        private RelativeLayout rl_title_bar;

        /**
         * 跟布局
         */
        private LinearLayout ll_title_bar;

        /**
         * 左侧图标
         */
        private ImageView iv_left_icon;

        /**
         * 右侧图标
         */
        private ImageView iv_rightIco;

        /**
         * 中间标题
         */
        private TextView tv_title_middle;

        /**
         * 右侧标题
         */
        private TextView tv_title_right;

        /**
         * 构造方法：用于获取对象
         */
        public TitleBar(Activity context) {
            titleView = context.findViewById(R.id.rl_title_bar);
            rl_title_bar = (RelativeLayout) titleView.findViewById(R.id.rl_title_bar);
            ll_title_bar = (LinearLayout) context.findViewById(R.id.ll_title_bar);
            tv_title_middle = (TextView) titleView.findViewById(R.id.tv_title_middle);
            tv_title_right = (TextView) titleView.findViewById(R.id.tv_title_right);
            iv_left_icon = (ImageView) titleView.findViewById(R.id.iv_left_icon);
            iv_rightIco = (ImageView) titleView.findViewById(R.id.iv_rightIco);
        }

//        new TitleBar(this).setLeftIco(0).setTitleText("新手入门").setLeftIcoListening(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        }).setRightIcoListening(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });


    /**
         * 用于设置标题栏文字
         *
         * @param titleText 传入要设置的标题
         * @return
         */
        public TitleBar setTitleText(String titleText) {
            if (!StringUtils.isEmpty(titleText)) {
                tv_title_middle.setText(titleText);
            }
            return this;
        }

        /**
         * 设置标题栏文字颜色
         *
         * @return
         */
        public TitleBar setTitleTextColor() {
            tv_title_middle.setTextColor(Color.WHITE);
            return this;
        }

        /**
         * 设置标题栏右边的文字
         *
         * @return
         */
        public TitleBar setTitleRight(String rightTitle) {
            if (!StringUtils.isEmpty(rightTitle)) {
                tv_title_right.setVisibility(View.VISIBLE);
                iv_rightIco.setVisibility(View.GONE);
                tv_title_right.setTextColor(Color.WHITE);
                tv_title_right.setText(rightTitle);
            }
            return this;
        }


        /**
         * 用于设置标题栏左边要显示的图片
         *
         * @param resId 标题栏左边的图标的id，一般为返回图标
         * @return
         */
        public TitleBar setLeftIco(int resId) {
            iv_left_icon.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
            iv_left_icon.setImageResource(resId);
            return this;
        }


        /**
         * 用于设置标题栏右边要显示的图片
         *
         * @param resId 标题栏右边的图标id
         * @return
         */
        public TitleBar setRightIco(int resId) {
            iv_rightIco.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
            iv_rightIco.setImageResource(resId);
            return this;
        }

        /**
         * 用户设置 标题栏右侧的图标的背景drawable
         *
         * @param resId drawable的id
         * @return
         */
        public TitleBar setRightIconBgDr(int resId) {
            iv_rightIco.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
    //        iv_rightIco.setImageResource(R.drawable.ic_back_selector);
            return this;
        }

        /**
         * 用于设置标题栏左边图片的单击事件
         *
         * @param listener 传入的事件对象
         * @return
         */
        public TitleBar setLeftIcoListening(View.OnClickListener listener) {
            if (iv_left_icon.getVisibility() == View.VISIBLE) {
                iv_left_icon.setOnClickListener(listener);
            }
            return this;
        }

        /**
         * 用于设置标题栏右边图片的单击事件
         *
         * @param listener 传入的事件对象
         * @return
         */
        public TitleBar setRightIcoListening(View.OnClickListener listener) {
            if (iv_rightIco.getVisibility() == View.VISIBLE) {
                iv_rightIco.setOnClickListener(listener);
            }
            return this;
        }
    }
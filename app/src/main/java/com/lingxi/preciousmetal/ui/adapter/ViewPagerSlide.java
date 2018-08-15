package com.lingxi.preciousmetal.ui.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by zhangwei on 2018/5/22.
 */

public class ViewPagerSlide extends ViewPager {
    private boolean isPagingEnabled = true;

    public ViewPagerSlide(Context context) {
        super(context);
    }

    public ViewPagerSlide(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onInterceptTouchEvent(event);
    }
    //是否可以滑动
    public void setPagingEnabled(boolean b) {
        this.isPagingEnabled = b;
    }
}

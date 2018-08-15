package com.lingxi.preciousmetal.ui.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AppGuideAdapter extends PagerAdapter {
    private int[] listId;
    private LayoutInflater mInflater;
    private Activity activity;

    public AppGuideAdapter(int[] listId, Activity activity) {
        this.listId = listId;
        this.activity = activity;
        this.mInflater = LayoutInflater.from(this.activity);
    }

    @Override
    public int getCount() {

        return listId.length;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {

        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(View container, int position) {
        View view = this.mInflater.inflate(listId[position], null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        ((ViewPager) container).addView(view);
        return view;
    }

    @Override
    public void destroyItem(final View arg0, final int arg1, final Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);
    }
}


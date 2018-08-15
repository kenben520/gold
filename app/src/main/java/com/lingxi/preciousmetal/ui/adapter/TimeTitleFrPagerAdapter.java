package com.lingxi.preciousmetal.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.lingxi.preciousmetal.domain.TimeTitleBean;

import java.util.ArrayList;
import java.util.List;

public class TimeTitleFrPagerAdapter extends FragmentPagerAdapter {

    private List<TimeTitleBean> titles;

    private List<Fragment> fragments;

    public TimeTitleFrPagerAdapter(FragmentManager fm, List<TimeTitleBean> list, List<Fragment> fragments) {
        super(fm);
        this.titles = list;
        this.fragments=fragments;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position).getTimeTitle();
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
}

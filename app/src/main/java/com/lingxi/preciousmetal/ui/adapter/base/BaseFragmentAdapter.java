package com.lingxi.preciousmetal.ui.adapter.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;

import java.lang.ref.WeakReference;

public abstract class BaseFragmentAdapter extends FragmentPagerAdapter {


    private WeakReference<Activity> mActivity;
    private SparseArray<Fragment> mFragments;


    public BaseFragmentAdapter(Fragment f) {
        super(f.getFragmentManager());

        initGFragmentPagerAdapter(f.getActivity());
    }

    public BaseFragmentAdapter(TranslucentStatusBarActivity activity) {
        super(activity.getSupportFragmentManager());

        initGFragmentPagerAdapter(activity);
    }

    public BaseFragmentAdapter(Fragment childFragment, boolean isChild) {
        super(childFragment.getChildFragmentManager());
        initGFragmentPagerAdapter(childFragment.getActivity());
    }

    private void initGFragmentPagerAdapter(Activity activity) {
        mActivity = new WeakReference<>(activity);

        mFragments = new SparseArray<>(4);
    }

    @Nullable
    public Activity getActivity() {
        if (mActivity != null) {
            return mActivity.get();
        }
        return null;
    }

    public Class<?> getFragmentCls(int position) {
        return getFragments()[position];
    }

    @Override
    public Fragment getItem(int position) {
        return Fragment.instantiate(getActivity(),
                getFragmentCls(position).getName(), getFragmentInitBundle(position));
    }


    @Nullable
    public Fragment getItemAt(int position) {
        return mFragments.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment f = (Fragment) super.instantiateItem(container, position);
        mFragments.put(position, f);
        return f;
    }

    public abstract Class<?>[] getFragments();

    public Bundle getFragmentInitBundle(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return getFragments().length;
    }

    public void release() {
        if (mActivity != null) {
            mActivity.clear();
            mActivity = null;
        }

        if (mFragments != null) {
            mFragments.clear();
            mFragments = null;
        }
    }
}

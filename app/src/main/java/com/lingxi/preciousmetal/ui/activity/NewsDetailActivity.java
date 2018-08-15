package com.lingxi.preciousmetal.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.gxz.PagerSlidingTabStrip;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.BaseActivity;
import com.lingxi.preciousmetal.ui.adapter.NewsFrPagerAdapter;
import com.lingxi.preciousmetal.ui.fragment.FragmentCommon;

import java.util.ArrayList;

public class NewsDetailActivity extends BaseActivity {

    private PagerSlidingTabStrip tabs;
    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_main);


    }
}

package com.lingxi.preciousmetal.ui.activity.study;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.gxz.PagerSlidingTabStrip;
import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.ui.adapter.NewsFrPagerAdapter;
import com.lingxi.preciousmetal.ui.widget.TopBarView;

import java.util.ArrayList;

public class NewStartActivity extends TranslucentStatusBarActivity {
    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private TopBarView mTopBarView;
    private String searchType;

    private void initTopBar() {
        mTopBarView =  findViewById(R.id.topbar_view);
        mTopBarView.search_close_btn.setVisibility(View.VISIBLE);
        mTopBarView.setMode(TopBarView.MODE_BACK);
        mTopBarView.search_close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudySearchActivity.actionStart(mContext,searchType);
            }
        });
    }

    public static void startMyActivity(Context activity, int fType,String searchType){
        Intent intent = new Intent(activity,NewStartActivity.class);
        intent.putExtra("fType",fType);
        intent.putExtra("searchType",searchType);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_main);
        initTopBar();
        pager = findViewById(R.id.news_main_pager);
        tabs = findViewById(R.id.news_main_tabs);
        ArrayList<String> titles = new ArrayList<>();
        int type = getIntent().getIntExtra("fType",0);
        searchType = getIntent().getStringExtra("searchType");
        if (type==1){
            mTopBarView.setTitle("基础知识");
            titles.add("投资入门");
            titles.add("行业知识");
            titles.add("交易术语");
        } else if (type==2){
            titles.add("K线图分析");
            titles.add("心态分析");
            titles.add("时机分析");
            mTopBarView.setTitle("技术分析");
        }
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(KnowledgeFragment.newInstance(type,"1"));
        fragments.add(KnowledgeFragment.newInstance(type,"2"));
        fragments.add(KnowledgeFragment.newInstance(type,"3"));

        pager.setAdapter(new NewsFrPagerAdapter(getSupportFragmentManager(), titles, fragments));
        tabs.setViewPager(pager);
        pager.setCurrentItem(0);

    }
}

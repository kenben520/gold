package com.lingxi.preciousmetal.ui.fragment.news;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.gxz.PagerSlidingTabStrip;
import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.ui.adapter.NewsFrPagerAdapter;
import com.lingxi.preciousmetal.ui.widget.TopBarView;

import java.util.ArrayList;

public class NewsMainActivity extends TranslucentStatusBarActivity {

    private PagerSlidingTabStrip tabs;
    private ViewPager pager;

    public static void startMyActivity(Context activity, int fType){
        Intent intent = new Intent(activity, NewsMainActivity.class);
        intent.putExtra("fType",fType);
        activity.startActivity(intent);
    }

    private void initTopBar() {
        TopBarView mTopBarView =  findViewById(R.id.topbar_view);
        mTopBarView.setMode(TopBarView.MODE_BACK);
        mTopBarView.setTitle("新闻资讯");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_main);
        initTopBar();
        pager =  findViewById(R.id.news_main_pager);
        tabs = findViewById(R.id.news_main_tabs);

        ArrayList<String> titles = new ArrayList<>();
        titles.add("推荐");
        titles.add("快讯");
        titles.add("每日头条");
        titles.add("国际要闻");
        titles.add("行情走势");
        titles.add("机构观点");

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(NewsRecommendFragment.newInstance("1"));
        fragments.add(NewsFlashFragment2.newInstance("1"));
        fragments.add(NewsCommonFragment.newInstance("26"));
        fragments.add(NewsCommonFragment.newInstance("20"));
        fragments.add(NewsCommonFragment.newInstance("27"));
        fragments.add(NewsCommonFragment.newInstance("28"));

        pager.setAdapter(new NewsFrPagerAdapter(getSupportFragmentManager(), titles, fragments));
        tabs.setViewPager(pager);
//        int fType = getIntent().getIntExtra("fType",0);
//        if (fType==0){
//            pager.setCurrentItem(0);
//        } else if (fType==1)
//            pager.setCurrentItem(1);
//        else if (fType==2)
//            pager.setCurrentItem(2);
//        else if (fType==3)
//            pager.setCurrentItem(3);
    }
}

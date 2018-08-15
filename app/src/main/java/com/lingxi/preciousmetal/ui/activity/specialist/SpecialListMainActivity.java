package com.lingxi.preciousmetal.ui.activity.specialist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;
import com.lingxi.preciousmetal.chart.activity.ViewFindUtils;
import com.lingxi.preciousmetal.ui.adapter.NewsFrPagerAdapter;
import com.lingxi.preciousmetal.ui.widget.TabEntity;
import com.lingxi.preciousmetal.ui.widget.TopBarView;

import java.util.ArrayList;

public class SpecialListMainActivity extends TranslucentStatusBarActivity {

    private ViewPager vp;
    private CommonTabLayout tabLayout;

    public static void startMyActivity(Context activity){
        Intent intent = new Intent(activity, SpecialListMainActivity.class);
        activity.startActivity(intent);
    }

    private void initTopBar() {
        TopBarView mTopBarView =  findViewById(R.id.topbar_view);
        mTopBarView.setMode(TopBarView.MODE_BACK);
        mTopBarView.setTitle("独家策略");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_notice);
        initTopBar();
        vp =  findViewById(R.id.vp_2);
        tabLayout = findViewById(R.id.notice_tabLayout);

        ArrayList<String> titles = new ArrayList<>();
        titles.add("点评");
        titles.add("观点");
        titles.add("视频");
        ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
        for(String str : titles){
            mTabEntities.add(new TabEntity(str, 0,0));
        }
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(FragmentViewPoint.newInstance(1));
        fragments.add(FragmentViewPoint.newInstance(2));
        fragments.add(FragmentTeacherVideo.newInstance());

        tabLayout.setTabData(mTabEntities);

        vp.setAdapter(new NewsFrPagerAdapter(getSupportFragmentManager(), titles, fragments));
        vp.setOffscreenPageLimit(3);

        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vp.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}

package com.lingxi.preciousmetal.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.flyco.roundview.RoundTextView;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.flyco.tablayout.utils.UnreadMsgUtils;
import com.github.tifezh.kchartlib.utils.ViewUtil;
import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.NewNoticeLastRequestMo;
import com.lingxi.biz.domain.responseMo.NewNoticeLastBean;
import com.lingxi.biz.service.CommonService;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;
import com.lingxi.preciousmetal.chart.activity.ViewFindUtils;
import com.lingxi.preciousmetal.ui.adapter.NewsFrPagerAdapter;
import com.lingxi.preciousmetal.ui.fragment.NewNoticeFragment;
import com.lingxi.preciousmetal.ui.widget.TabEntity;
import com.lingxi.preciousmetal.ui.widget.TopBarView;
import com.lingxi.preciousmetal.util.utilCode.SharedPreferencesHelper;

import java.util.ArrayList;

public class NewNoticeActivity extends TranslucentStatusBarActivity implements OnTabSelectListener {

    public static void startMyActivity(Context activity) {
        Intent intent = new Intent(activity, NewNoticeActivity.class);
        activity.startActivity(intent);
    }

    private void initTopBar() {
        TopBarView mTopBarView =  findViewById(R.id.topbar_view);
        mTopBarView.setMode(TopBarView.MODE_BACK);
        mTopBarView.setTitle("最新公告");
    }

    private Context mContext = this;
    private ArrayList<Fragment> mFragments = new ArrayList<>();


    private ViewPager vp;
    private CommonTabLayout tabLayout;
    private SharedPreferencesHelper sharedPreferencesHelper;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_notice);
        initTopBar();
        mFragments.add(NewNoticeFragment.newInstance(1));
        mFragments.add(NewNoticeFragment.newInstance(2));
        mFragments.add(NewNoticeFragment.newInstance(3));
        View decorView = getWindow().getDecorView();
        vp = ViewFindUtils.find(decorView, R.id.vp_2);
        ArrayList<String> titles = new ArrayList<>();
        titles.add("重要公告");
        titles.add("休市安排");
        titles.add("系统维护");

//        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(new NewsFrPagerAdapter(getSupportFragmentManager(), titles, mFragments));
        tabLayout = ViewFindUtils.find(decorView, R.id.notice_tabLayout);
        /** 字体加粗,大写 */
//        tabLayout.setViewPager(vp);

        for (int i = 0; i < titles.size(); i++) {
            mTabEntities.add(new TabEntity(titles.get(i),0,0));
        }

        tabLayout.setTabData(mTabEntities);
        tabLayout.setOnTabSelectListener(this);
        vp.setCurrentItem(0);
        sharedPreferencesHelper = new SharedPreferencesHelper(mContext, "newNotice");
        int type1Show =  (int)sharedPreferencesHelper.getSharedPreference("type1Show",0);
        int type2Show =  (int)sharedPreferencesHelper.getSharedPreference("type2Show",0);
        int type3Show =  (int)sharedPreferencesHelper.getSharedPreference("type3Show",0);

        if (type1Show==0){
           // tabLayout.showDot(0);
            RoundTextView redView1 = tabLayout.getMsgView(0);
            UnreadMsgUtils.setSize(redView1, ViewUtil.Dp2Px(mContext,7));
            redView1.setBackgroundColor(ContextCompat.getColor(mContext,R.color.kWhiteRed));
        }
        if (type2Show==0){
            tabLayout.showDot(1);
            RoundTextView redView1 = tabLayout.getMsgView(1);
            UnreadMsgUtils.setSize(redView1, ViewUtil.Dp2Px(mContext,7));
            redView1.setBackgroundColor(ContextCompat.getColor(mContext,R.color.kWhiteRed));
        }
        if (type3Show==0){
            tabLayout.showDot(2);
            RoundTextView redView1 = tabLayout.getMsgView(2);
            UnreadMsgUtils.setSize(redView1, ViewUtil.Dp2Px(mContext,7));
            redView1.setBackgroundColor(ContextCompat.getColor(mContext,R.color.kWhiteRed));
        }
        sharedPreferencesHelper.put("type1Show",1);
        tabLayout.setMsgMargin(0, 0, 0);
        tabLayout.setMsgMargin(1, 5, 0);
        tabLayout.setMsgMargin(2, 3, 0);

//        tabLayout.setMsgMargin(0, -10, -5);
    //   tabLayout.hideMsg(0);
//        MsgView rtv_2_3 = tabLayout.getMsgView(3);
//        if (rtv_2_3 != null) {
//            rtv_2_3.setBackgroundColor(Color.parseColor("#6D8FB0"));
//        }
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vp.setCurrentItem(position);
                tabLayout.hideMsg(0);
                tabLayout.hideMsg(position);
                if (position==0){
                    sharedPreferencesHelper.put("type1Show",1);
                } else if (position==1){
                    sharedPreferencesHelper.put("type2Show",1);
                } else if (position==2){
                    sharedPreferencesHelper.put("type3Show",1);
                }
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
                tabLayout.hideMsg(position);
                if (position==1){
                    sharedPreferencesHelper.put("type2Show",1);
                } else if (position==2){
                    sharedPreferencesHelper.put("type3Show",1);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onTabSelect(int position) {
    }

    @Override
    public void onTabReselect(int position) {
    }


}

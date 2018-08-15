package com.lingxi.preciousmetal.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ViewGroup;

import com.flyco.roundview.RoundTextView;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.flyco.tablayout.utils.UnreadMsgUtils;
import com.github.tifezh.kchartlib.utils.ViewUtil;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.Error;
import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.NewNoticeLastRequestMo;
import com.lingxi.biz.domain.responseMo.NewNoticeLastBean;
import com.lingxi.biz.service.CommonService;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;
import com.lingxi.preciousmetal.login.LoginHelper;
//import com.lingxi.preciousmetal.ui.activity.study.StudySearchActivity;
import com.lingxi.preciousmetal.ui.activity.kefu.KefuLoginActivity;
import com.lingxi.preciousmetal.ui.activity.kefu.OnlineKefuActivity;
import com.lingxi.preciousmetal.ui.fragment.HomeFragment3;
import com.lingxi.preciousmetal.ui.fragment.KMarketFragment;
import com.lingxi.preciousmetal.ui.fragment.MineFragment;
import com.lingxi.preciousmetal.ui.fragment.analyzeplate.AnalyzePlateFragment;
import com.lingxi.preciousmetal.ui.fragment.trade.TradeUserInfoFragment;
import com.lingxi.preciousmetal.ui.widget.TabEntity;
import com.lingxi.preciousmetal.util.utilCode.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;

public class GoldMainActivity extends TranslucentStatusBarActivity {
    //    List<TabViewChild> tabViewChildList;
//    TabView tabView;
    private SharedPreferencesHelper sharedPreferencesHelper;
    private CommonTabLayout mTabLayout_2;
    private String[] mTitles = {"首页", "行情", "交易", "解盘", "我的"};
    private int[] mIconSelectIds = {
            R.drawable.tab_home_sel_icon, R.drawable.tab_market_sel_icon,
            R.drawable.tab_deal_sel_icon, R.drawable.tab_video_sel_icon, R.drawable.tab_my_sel_icon};
    private int[] mIconUnselectIds = {
            R.drawable.tab_home_unsel_icon, R.drawable.tab_market_unsel_icon,
            R.drawable.tab_deal_un_sel_icon, R.drawable.tab_video_un_sel_icon, R.drawable.tab_my_un_sel_icon};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ViewPager vp;
    private MyConnectionListener connectionListener = null;

    public static void actionStart(Context context,int type) {
        Intent intent = new Intent(context, GoldMainActivity.class);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        int type = intent.getIntExtra("type",0);
        if (type == 1) {
            vp.setCurrentItem(2);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StatusBarUtil.setTransparent(this);
//        StatusBarUtil.setTranslucent(this, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
//        StatusBarUtil.setTranslucent(this, 0);
        //    StatusBarUtil.setColor(this, ContextCompat.getColor(this,R.color.black), 30);
        //   StatusBarUtil.setColorForSwipeBack(this, ContextCompat.getColor(this,R.color.black),0);
        setContentView(R.layout.activity_gold_main);
//        ChartActivity.startMyActivity(this, ConstantUtil.XAUUSD,1);
//        SpecialListMainActivity.startMyActivity(mContext);
//        NewsMainActivity.startMyActivity(this,0);
//        ActivityUtils.startActivity(MinuteChartActivity.class);
//        ActivityUtils.startActivity(TestActivity.class);
//        StartBuyGoldActivity.startMyActivity(mContext,1);
//        ActivityUtils.startActivity(StartBuyGoldActivity.class);
//        ActivityUtils.startActivity(SetProfitAndLossActivity.class);
//        ActivityUtils.startActivity(MinuteChartView.class);
        // NewNoticeActivity.startMyActivity(mContext);
//        StudySearchActivity.actionStart(this,"");
//        SetProfitAndLossActivity.startMyActivity(this,1);
        ArrayList<Fragment> fragments = new ArrayList<>();
//        fragments.add(TradeUserInfoFragment.newInstance());
//        fragments.add(HomeFragment2.newInstance("首页"));
        fragments.add(HomeFragment3.newInstance("首页"));
//        fragments.add(EntryOrdersFragment.newInstance());
        fragments.add(KMarketFragment.newInstance("行情"));
        fragments.add(TradeUserInfoFragment.newInstance());
//        fragments.add(KMarketFragment.newInstance("行情"));
        fragments.add(AnalyzePlateFragment.newInstance());
        fragments.add(MineFragment.newInstance());
//        TradeUserInfoActivity.startMyActivity(this);
        vp = findViewById(R.id.vp);
        vp.setOffscreenPageLimit(5);
        VpAdapter adapter = new VpAdapter(getSupportFragmentManager(), fragments);
        vp.setAdapter(adapter);

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        mTabLayout_2 = findViewById(R.id.homeTbaLayout);
        mTabLayout_2.setTabData(mTabEntities);
        mTabLayout_2.setOnTabSelectListener(new OnTabSelectListener() {
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
                mTabLayout_2.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vp.setCurrentItem(0);
        //两位数
//        mTabLayout_2.showMsg(0, 55);
//        mTabLayout_2.setMsgMargin(0, -5, 5);
//        //三位数
//        mTabLayout_2.showMsg(1, 100);

        mTabLayout_2.setTextSelectColor(ContextCompat.getColor(mContext,R.color.mind));
        mTabLayout_2.setTextUnselectColor(ContextCompat.getColor(mContext,R.color.FF686868));
        mTabLayout_2.setUnderlineHeight(0);

        LoginHelper.getInstance().httpGetUserInfo();
        LoginHelper.getInstance().httpGetAccountInfo();

        sharedPreferencesHelper = new SharedPreferencesHelper(mContext, "newNotice");

        NewNoticeLastRequestMo requestMo =  new NewNoticeLastRequestMo();
        CommonService.getNewNoticeLast(requestMo,new BizResultListener<NewNoticeLastBean>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, NewNoticeLastBean resultBean) {

            }

            @Override
            public void onSuccess(NewNoticeLastBean resultBean) {
                boolean isShowNew = false;
                if (resultBean!=null && resultBean.getList()!=null){
                    for (NewNoticeLastBean.ListBean item: resultBean.getList()) {
                        int newId = item.getNotice_id();
                        int type = item.getType();
                        if (type==1){
                            int saveId =  (int)sharedPreferencesHelper.getSharedPreference("type1Id",0);
                            int type1Show =  (int)sharedPreferencesHelper.getSharedPreference("type1Show",0);

                            if (saveId==newId && type1Show==1){
                                sharedPreferencesHelper.put("type1Show",1);
                            } else {
                                isShowNew = true;
                                sharedPreferencesHelper.put("type1Show",0);
                            }
                        } else if (type==2){
                            int saveId =  (int)sharedPreferencesHelper.getSharedPreference("type2Id",0);
                            int type2Show =  (int)sharedPreferencesHelper.getSharedPreference("type2Show",0);
                            if (saveId==newId && type2Show==1){
                                sharedPreferencesHelper.put("type2Show",1);
                            } else {
                                isShowNew = true;
                                sharedPreferencesHelper.put("type2Show",0);
                            }
                        } else if (type==3){
                            int saveId =  (int)sharedPreferencesHelper.getSharedPreference("type3Id",0);
                            int type3Show =  (int)sharedPreferencesHelper.getSharedPreference("type3Show",0);
                            if (saveId==newId && type3Show==1){
                                sharedPreferencesHelper.put("type3Show",1);
                            } else {
                                isShowNew = true;
                                sharedPreferencesHelper.put("type3Show",0);
                            }
                        }
                    }

                    if (isShowNew){
                        mTabLayout_2.showDot(4);
                        mTabLayout_2.setMsgMargin(4, -7, 4);

                        RoundTextView redView1 = mTabLayout_2.getMsgView(4);
                        UnreadMsgUtils.setSize(redView1, ViewUtil.Dp2Px(mContext,7));
                        redView1.setBackgroundColor(ContextCompat.getColor(mContext,R.color.kWhiteRed));
                    }
                }
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
            }
        });

        //注册一个监听连接状态的listener
        connectionListener = new MyConnectionListener();
        ChatClient.getInstance().addConnectionListener(connectionListener);
    }
    public class MyConnectionListener implements ChatClient.ConnectionListener {

        @Override
        public void onConnected() {

        }

        @Override
        public void onDisconnected(final int errorCode) {
            if (errorCode == Error.USER_NOT_FOUND || errorCode == Error.USER_LOGIN_ANOTHER_DEVICE
                    || errorCode == Error.USER_AUTHENTICATION_FAILED
                    || errorCode == Error.USER_REMOVED) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //demo中为了演示当用户被删除或者修改密码后验证失败,跳出会话界面
                        //正常APP应该跳到登录界面或者其他操作
                        if (OnlineKefuActivity.instance != null) {
                            OnlineKefuActivity.instance.finish();
                        }
                        ChatClient.getInstance().logout(false, null);
                    }
                });
            }
        }
    }

    private int mPosition;
    /**
     * view pager adapter
     */
    private class VpAdapter extends FragmentPagerAdapter {
        private List<Fragment> data;

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            mPosition = position;
            if (mPosition==4){
                //获取我的fragment
                int type1Show =  (int)sharedPreferencesHelper.getSharedPreference("type1Show",0);
                int type2Show =  (int)sharedPreferencesHelper.getSharedPreference("type2Show",0);
                int type3Show =  (int)sharedPreferencesHelper.getSharedPreference("type3Show",0);
                if (type1Show==1 && type2Show==1 && type3Show==1){
                    mTabLayout_2.hideMsg(4);
                }
//                else {
//                    mTabLayout_2.showDot(4);
//                    RoundTextView redView1 = mTabLayout_2.getMsgView(4);
//                    UnreadMsgUtils.setSize(redView1, ViewUtil.Dp2Px(mContext,7));
//                    redView1.setBackgroundColor(ContextCompat.getColor(mContext,R.color.kWhiteRed));
//                }
            }
        }

        public VpAdapter(FragmentManager fm, List<Fragment> data) {
            super(fm);
            this.data = data;
        }


        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Fragment getItem(int position) {
            return data.get(position);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (connectionListener != null) {
            ChatClient.getInstance().removeConnectionListener(connectionListener);
        }
    }

}

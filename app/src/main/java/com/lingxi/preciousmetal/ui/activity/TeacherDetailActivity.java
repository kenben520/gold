package com.lingxi.preciousmetal.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.GetTeacherDetailRequestMo;
import com.lingxi.biz.domain.responseMo.EssenceCourseMo;
import com.lingxi.biz.domain.responseMo.TeacherDetailMo;
import com.lingxi.biz.service.AnalyseTradeService;
import com.lingxi.common.base.BaseApplication;
import com.lingxi.common.util.StringUtil;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;
import com.lingxi.preciousmetal.domain.UserInfoBean;
import com.lingxi.preciousmetal.login.LoginHelper;
import com.lingxi.preciousmetal.ui.adapter.NewsFrPagerAdapter;
import com.lingxi.preciousmetal.ui.adapter.ViewPagerSlide;
import com.lingxi.preciousmetal.ui.fragment.analyzeplate.TeacherOpinionFragment;
import com.lingxi.preciousmetal.ui.fragment.analyzeplate.TeacherShowTradeFragment;
import com.lingxi.preciousmetal.ui.fragment.analyzeplate.TeacherVideoFragment;
import com.lingxi.preciousmetal.ui.widget.TopBarView;
import com.lingxi.preciousmetal.ui.widget.dialog.DialogManager;
import com.lingxi.preciousmetal.util.DisplayUtil;
import com.lingxi.preciousmetal.util.glideutils.GlideUtils;
import com.lingxi.preciousmetal.util.utilCode.ObjectUtils;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.noties.scrollable.CanScrollVerticallyDelegate;
import ru.noties.scrollable.ScrollableLayout;

/**
 * Created by zhangwei on 2018/4/19.
 */

public class TeacherDetailActivity extends TranslucentStatusBarActivity {
    public static final int GOLD_TYPE = 1;
    public static final int SILVER_TYPE = 2;
    public static int showType = GOLD_TYPE;
    UserInfoBean mUserInfoBean;
    @BindView(R.id.iv_user_icon)
    ImageView ivUserIcon;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.tag_1)
    TextView tag1;
    @BindView(R.id.tag_2)
    TextView tag2;
    @BindView(R.id.tv_like_num)
    TextView tvLikeNum;
    @BindView(R.id.tv_win_pc)
    TextView tvWinPc;
    @BindView(R.id.tv_intrd)
    TextView tvIntrd;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.pager)
    ViewPagerSlide pager;
    @BindView(R.id.topbar_view)
    TopBarView mTopbarView;
    String teacherId,teacherName;
    @BindView(R.id.scrollable_layout)
    ScrollableLayout scrollableLayout;
    NewsFrPagerAdapter pagerAdapter;
    @BindView(R.id.header)
    LinearLayout header;
    @BindView(R.id.layout_live_status)
    LinearLayout layoutLiveStatus;
    private int currentPosition = 0;
    private boolean isLive;

    public static void actionStart(Context context, String teacherId,String name, boolean isLive) {
        Intent intent = new Intent(context, TeacherDetailActivity.class);
        intent.putExtra("teacherId", teacherId);
        intent.putExtra("teacherName", name);
        intent.putExtra("isLive", isLive);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_detail_header_view);
        ButterKnife.bind(this);
        mUserInfoBean = LoginHelper.getInstance().getLoginUserInfo();
        teacherId = getIntent().getStringExtra("teacherId");
        teacherName = getIntent().getStringExtra("teacherName");
        isLive = getIntent().getBooleanExtra("isLive", false);
        initTopBar();
        initView();
        loadData(false);
    }

    private TeacherShowTradeFragment wednesdayF;
    private TeacherVideoFragment tuesdayF;
    private TeacherOpinionFragment mondayF;

    private void initView() {
        if (showType == GOLD_TYPE) {
        } else if (showType == SILVER_TYPE) {
        }

        ArrayList<String> titles = new ArrayList<>();
        titles.add("观点");
        titles.add("视频");
        titles.add("晒单");
        tabs.addTab(tabs.newTab().setText(titles.get(0)));
        tabs.addTab(tabs.newTab().setText(titles.get(1)));
        tabs.addTab(tabs.newTab().setText(titles.get(2)));
        ArrayList<Fragment> fragments = new ArrayList<>();
        mondayF = TeacherOpinionFragment.newInstance(teacherId);
        tuesdayF = TeacherVideoFragment.newInstance(teacherId,teacherName);
        wednesdayF = TeacherShowTradeFragment.newInstance(teacherId);
        fragments.add(mondayF);
        fragments.add(tuesdayF);
        fragments.add(wednesdayF);
        pager.setOffscreenPageLimit(3);
        pager.setPagingEnabled(true);
        pagerAdapter = new NewsFrPagerAdapter(getSupportFragmentManager(), titles, fragments);
        pager.setAdapter(pagerAdapter);
        tabs.setupWithViewPager(pager);
//        tabs.setTabMode(TabLayout.MODE_FIXED);

        scrollableLayout.setCloseUpAlgorithm(null);
        scrollableLayout.setDraggableView(tabs);
        scrollableLayout.setCanScrollVerticallyDelegate(new CanScrollVerticallyDelegate() {
            @Override
            public boolean canScrollVertically(int direction) {
                if (currentPosition == 0) {
                    final TeacherOpinionFragment fragment = (TeacherOpinionFragment) pagerAdapter.getItem(currentPosition);
                    return fragment != null && fragment.canScrollVertically(direction);
                } else if (currentPosition == 1) {
                    final TeacherVideoFragment fragment = (TeacherVideoFragment) pagerAdapter.getItem(currentPosition);
                    return fragment != null && fragment.canScrollVertically(direction);
                } else if (currentPosition == 2) {
                    final TeacherShowTradeFragment fragment = (TeacherShowTradeFragment) pagerAdapter.getItem(currentPosition);
                    return fragment != null && fragment.canScrollVertically(direction);
                }
                return false;
            }
        });
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        header.post(new Runnable() {
            @Override
            public void run() {
                int height = header.getMeasuredHeight() + DisplayUtil.dip2px(BaseApplication.getInstance(), 40) + DisplayUtil.dip2px(BaseApplication.getInstance(), 70);
                int fragmentVisibleHeight = DisplayUtil.getScreenWidth(BaseApplication.getInstance()) - height;
                mondayF.setVisibleHeight(fragmentVisibleHeight);
                tuesdayF.setVisibleHeight(fragmentVisibleHeight);
                wednesdayF.setVisibleHeight(fragmentVisibleHeight);
            }
        });
        layoutLiveStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (isLive) {
            layoutLiveStatus.setVisibility(View.VISIBLE);
        } else {
            layoutLiveStatus.setVisibility(View.GONE);
        }
    }

    private void initTopBar() {
        mTopbarView.setMode(TopBarView.MODE_BACK);
        mTopbarView.setTitle("老师主页");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void loadData(final boolean hasUI) {
        if (hasUI) {
            DialogManager.getInstance().showLoadingDialog(this, "", false);
        }
        GetTeacherDetailRequestMo liveListRequestMo = new GetTeacherDetailRequestMo(teacherId);
        AnalyseTradeService.getTeacherDetail(liveListRequestMo, new BizResultListener<TeacherDetailMo>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, TeacherDetailMo teacherDetailMo) {

            }

            @Override
            public void onSuccess(TeacherDetailMo teacherDetailMo) {
                updateInfo(teacherDetailMo);
                DialogManager.getInstance().cancellLoadingDialog();
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                if (hasUI) {
                    ToastUtils.showShort(bizMessage);
                    DialogManager.getInstance().cancellLoadingDialog();
                }

            }
        });
    }

    private void updateInfo(TeacherDetailMo teacherDetailMo) {
        if (ObjectUtils.isEmpty(teacherDetailMo.teacher)) return;
        EssenceCourseMo.TeacherBean teacherBean = teacherDetailMo.teacher;
        name.setText(teacherBean.getTea_name());
        List<String> tags = teacherBean.getTea_title();
        if (!ObjectUtils.isEmpty(tags)) {
            if (tags.size() > 0 && !StringUtil.isEmpty(tags.get(0))) {
                tag1.setText(tags.get(0));
                tag1.setVisibility(View.VISIBLE);
            }
            if (tags.size() > 1 && !StringUtil.isEmpty(tags.get(1))) {
                tag2.setText(tags.get(1));
                tag2.setVisibility(View.VISIBLE);
            }
        }
        GlideUtils.loadRoundImage(this, R.drawable.ic_avatar_default1, 52, teacherBean.getTea_img_url(), ivUserIcon);
        tvLikeNum.setText(teacherBean.getTea_total_click() + "");
        tvWinPc.setText(teacherBean.getTea_win_num() + "%");
        tvIntrd.setText(teacherBean.getTea_dis_comment());
    }
}

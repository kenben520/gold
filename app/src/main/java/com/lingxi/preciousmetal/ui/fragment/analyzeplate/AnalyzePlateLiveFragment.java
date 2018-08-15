package com.lingxi.preciousmetal.ui.fragment.analyzeplate;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.LiveListRequestMo;
import com.lingxi.biz.domain.responseMo.LiveProgramListMo;
import com.lingxi.biz.service.AnalyseTradeService;
import com.lingxi.common.base.BaseFragment;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.common.EventBusKeyDefine;
import com.lingxi.preciousmetal.ui.activity.PolyvLivePlayerActivity;
import com.lingxi.preciousmetal.ui.adapter.NewsFrPagerAdapter;
import com.lingxi.preciousmetal.ui.adapter.ViewPagerSlide;
import com.lingxi.preciousmetal.ui.widget.TopBarView;
import com.lingxi.preciousmetal.ui.widget.dialog.DialogManager;
import com.lingxi.preciousmetal.util.glideutils.GlideUtils;
import com.lingxi.preciousmetal.util.utilCode.AppUtils;
import com.lingxi.preciousmetal.util.utilCode.ObjectUtils;
import com.lingxi.preciousmetal.util.utilCode.TimeUtils;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static java.util.Calendar.FRIDAY;
import static java.util.Calendar.MONDAY;
import static java.util.Calendar.SATURDAY;
import static java.util.Calendar.SUNDAY;
import static java.util.Calendar.THURSDAY;
import static java.util.Calendar.TUESDAY;
import static java.util.Calendar.WEDNESDAY;

/**
 * Created by zhangwei on 2018/5/22.
 * 解盘tab 直播
 */

public class AnalyzePlateLiveFragment extends BaseFragment {

    @BindView(R.id.topbar_view)
    TopBarView mTopbarView;
    @BindView(R.id.news_main_tabs1)
    TabLayout toolbar_tab;
    @BindView(R.id.news_main_pager2)
    ViewPagerSlide pager;
    Unbinder unbinder;
    String[] tab_title = {"星期一", "星期二", "星期三", "星期四", "星期五"};
    @BindView(R.id.tv_live_status)
    TextView tvLiveStatus;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.content)
    LinearLayout content;
    @BindView(R.id.ProgressBar)
    android.widget.ProgressBar ProgressBar;
    @BindView(R.id.empty_img)
    ImageView emptyImg;
    @BindView(R.id.empty_tips)
    TextView emptyTips;
    @BindView(R.id.empty_layout)
    LinearLayout emptyLayout;
    @BindView(R.id.iv_live_cover)
    ImageView ivLiveCover;
    @BindView(R.id.layout_cover)
    RelativeLayout layoutCover;
    @BindView(R.id.iv_live_status)
    ImageView ivLiveStatus;
    private LiveProgramListMo accountInfoMo;

    public static AnalyzePlateLiveFragment newInstance() {
        AnalyzePlateLiveFragment tradeFragment = new AnalyzePlateLiveFragment();
        return tradeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_analyze_plate_live, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
        registorEventBus();
        initView();
        loadData(true);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private boolean isLoadData = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && !isLoadData && getActivity() != null) {
//            isLoadData = true;
//            loadData();
        }
    }

    private void loadData(final boolean hasUI) {
        if (hasUI) {
            DialogManager.getInstance().showLoadingDialog(getActivity(), "", false);
        }
        LiveListRequestMo liveListRequestMo = new LiveListRequestMo();
        AnalyseTradeService.getLiveProgramList(liveListRequestMo, new BizResultListener<LiveProgramListMo>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, LiveProgramListMo liveProgramListMo) {

            }

            @Override
            public void onSuccess(LiveProgramListMo liveProgramListMo) {
                updateInfo(liveProgramListMo);
                refreshLayout.finishRefresh();
                DialogManager.getInstance().cancellLoadingDialog();
                content.setVisibility(View.VISIBLE);
                emptyLayout.setVisibility(View.GONE);
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                if (hasUI) {
                    refreshLayout.finishRefresh();
                    ToastUtils.showShort(bizMessage);
                    DialogManager.getInstance().cancellLoadingDialog();
                    if (ObjectUtils.isEmpty(accountInfoMo)) {
                        content.setVisibility(View.GONE);
                        emptyLayout.setVisibility(View.VISIBLE);
                        emptyImg.setImageResource(R.drawable.icon_no_network);
                        emptyTips.setText(getString(R.string.no_network_tips));
                    } else {
                        emptyLayout.setVisibility(View.GONE);
                        content.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    private void refreshDate(int position, String date) {
        final TabLayout.Tab tab = toolbar_tab.getTabAt(position);
        View view = tab.getCustomView();
        if (view != null) {
            TextView textView2 = (TextView) view.findViewById(R.id.tv_title2);
            textView2.setText(date);
        }
    }

    private void updateInfo(LiveProgramListMo accountInfoMo) {
        this.accountInfoMo = accountInfoMo;
        HashMap<String, List<LiveProgramListMo.LiveProgramBean>> friday = new HashMap<>();
        friday.put("friday", accountInfoMo.friday);
        HashMap<String, List<LiveProgramListMo.LiveProgramBean>> thursday = new HashMap<>();
        thursday.put("thursday", accountInfoMo.thursday);
        HashMap<String, List<LiveProgramListMo.LiveProgramBean>> wednesday = new HashMap<>();
        wednesday.put("wednesday", accountInfoMo.wednesday);
        HashMap<String, List<LiveProgramListMo.LiveProgramBean>> tuesday = new HashMap<>();
        tuesday.put("tuesday", accountInfoMo.tuesday);
        HashMap<String, List<LiveProgramListMo.LiveProgramBean>> monday = new HashMap<>();
        monday.put("monday", accountInfoMo.monday);
        mondayF.onDataChange(accountInfoMo.monday);
        tuesdayF.onDataChange(accountInfoMo.tuesday);
        wednesdayF.onDataChange(accountInfoMo.wednesday);
        thursdayF.onDataChange(accountInfoMo.thursday);
        fridayF.onDataChange(accountInfoMo.friday);
        refreshView(accountInfoMo);
        GlideUtils.loadImageViewCrop(getContext(), accountInfoMo.b_image_url, ivLiveCover);
        if (!ObjectUtils.isEmpty(accountInfoMo.date) && accountInfoMo.date.size() == 5) {
            refreshDate(0, accountInfoMo.date.get(0));
            refreshDate(1, accountInfoMo.date.get(1));
            refreshDate(2, accountInfoMo.date.get(2));
            refreshDate(3, accountInfoMo.date.get(3));
            refreshDate(4, accountInfoMo.date.get(4));
        }

//        EventBus.getDefault().post(new EventBusKeyDefine.EventBusMsgData<HashMap<String, List<LiveProgramListMo.LiveProgramBean>>>(EventBusKeyDefine.EVENTBUS_PROGRAM_LIST, friday));
//        EventBus.getDefault().post(new EventBusKeyDefine.EventBusMsgData<HashMap<String, List<LiveProgramListMo.LiveProgramBean>>>(EventBusKeyDefine.EVENTBUS_PROGRAM_LIST, thursday));
//        EventBus.getDefault().post(new EventBusKeyDefine.EventBusMsgData<HashMap<String, List<LiveProgramListMo.LiveProgramBean>>>(EventBusKeyDefine.EVENTBUS_PROGRAM_LIST, wednesday));
//        EventBus.getDefault().post(new EventBusKeyDefine.EventBusMsgData<HashMap<String, List<LiveProgramListMo.LiveProgramBean>>>(EventBusKeyDefine.EVENTBUS_PROGRAM_LIST, tuesday));
//        EventBus.getDefault().post(new EventBusKeyDefine.EventBusMsgData<HashMap<String, List<LiveProgramListMo.LiveProgramBean>>>(EventBusKeyDefine.EVENTBUS_PROGRAM_LIST, monday));

    }

    private void refreshView(LiveProgramListMo liveProgramListMo) {
        boolean hasLive = hasLive(liveProgramListMo);
        if (tvLiveStatus == null)
            return;
        if (hasLive) {
            tvLiveStatus.setText("直播进行中");
            ivLiveStatus.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.lx_list_living));
        } else {
            tvLiveStatus.setText("自由交流中");
            ivLiveStatus.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.lx_list_chat));
        }
    }

    private boolean hasLive(LiveProgramListMo accountInfoMo) {
        if (!ObjectUtils.isEmpty(accountInfoMo.friday)) {
            for (int i = 0; i < accountInfoMo.friday.size(); i++) {
                LiveProgramListMo.LiveProgramBean bean = accountInfoMo.friday.get(i);
                if (bean.isState()) {
                    return true;
                }
            }
        }

        if (!ObjectUtils.isEmpty(accountInfoMo.thursday)) {
            for (int i = 0; i < accountInfoMo.thursday.size(); i++) {
                LiveProgramListMo.LiveProgramBean bean = accountInfoMo.thursday.get(i);
                if (bean.isState()) {
                    return true;
                }
            }
        }

        if (!ObjectUtils.isEmpty(accountInfoMo.wednesday)) {
            for (int i = 0; i < accountInfoMo.wednesday.size(); i++) {
                LiveProgramListMo.LiveProgramBean bean = accountInfoMo.wednesday.get(i);
                if (bean.isState()) {
                    return true;
                }
            }
        }

        if (!ObjectUtils.isEmpty(accountInfoMo.tuesday)) {
            for (int i = 0; i < accountInfoMo.tuesday.size(); i++) {
                LiveProgramListMo.LiveProgramBean bean = accountInfoMo.tuesday.get(i);
                if (bean.isState()) {
                    return true;
                }
            }
        }

        if (!ObjectUtils.isEmpty(accountInfoMo.monday)) {
            for (int i = 0; i < accountInfoMo.monday.size(); i++) {
                LiveProgramListMo.LiveProgramBean bean = accountInfoMo.monday.get(i);
                if (bean.isState()) {
                    return true;
                }
            }
        }

        return false;
    }

    private AnalyzePlateProgramFragment mondayF, tuesdayF, wednesdayF, thursdayF, fridayF;

    private void initView() {
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadData(true);
            }
        });
        mTopbarView.setVisibility(View.GONE);
        refreshLoginStateView();
        ArrayList<String> titles = new ArrayList<>();
        titles.add("星期一");
        titles.add("星期二");
        titles.add("星期三");
        titles.add("星期四");
        titles.add("星期五");
        toolbar_tab.addTab(toolbar_tab.newTab().setText(tab_title[0]));
        toolbar_tab.addTab(toolbar_tab.newTab().setText(tab_title[1]));
        toolbar_tab.addTab(toolbar_tab.newTab().setText(tab_title[2]));
        toolbar_tab.addTab(toolbar_tab.newTab().setText(tab_title[3]));
        toolbar_tab.addTab(toolbar_tab.newTab().setText(tab_title[4]));
        toolbar_tab.post(new Runnable() {
            @Override
            public void run() {
//                ApplicationUtils.setIndicator(toolbar_tab, 10, 10);
            }
        });

        ArrayList<Fragment> fragments = new ArrayList<>();
        mondayF = AnalyzePlateProgramFragment.newInstance("monday");
        tuesdayF = AnalyzePlateProgramFragment.newInstance("tuesday");
        wednesdayF = AnalyzePlateProgramFragment.newInstance("wednesday");
        thursdayF = AnalyzePlateProgramFragment.newInstance("thursday");
        fridayF = AnalyzePlateProgramFragment.newInstance("friday");
        fragments.add(mondayF);
        fragments.add(tuesdayF);
        fragments.add(wednesdayF);
        fragments.add(thursdayF);
        fragments.add(fridayF);
//        fragments.add(AnalyzePlateProgramFragment.newInstance("monday"));
//        fragments.add(AnalyzePlateProgramFragment.newInstance("tuesday"));
//        fragments.add(AnalyzePlateProgramFragment.newInstance("wednesday"));
//        fragments.add(AnalyzePlateProgramFragment.newInstance("thursday"));
//        fragments.add(AnalyzePlateProgramFragment.newInstance("friday"));
//        mFragmentAdapter = new BaseFragmentAdapter(this) {
//            @Override
//            public Class<?>[] getFragments() {
//                return MAIN_ACTIVITY_FRAGMENT_CLS;
//            }
//
//            @Override
//            public Bundle getFragmentInitBundle(int position) {
//                Bundle bundle = new Bundle();
//                switch (position) {
//                    case 0:
//                        bundle.putString("whatDay", "monday");
//                        bundle.putSerializable("list", (Serializable)accountInfoMo.monday);
//                        break;
//                    case 1:
//                        bundle.putString("whatDay", "tuesday");
//                        bundle.putSerializable("list", (Serializable)accountInfoMo.tuesday);
//                        break;
//                    case 2:
//                        bundle.putString("whatDay", "wednesday");
//                        bundle.putSerializable("list", (Serializable)accountInfoMo.wednesday);
//                        break;
//                    case 3:
//                        bundle.putString("whatDay", "thursday");
//                        bundle.putSerializable("list", (Serializable)accountInfoMo.thursday);
//                        break;
//                    case 4:
//                        bundle.putString("whatDay", "friday");
//                        bundle.putSerializable("list", (Serializable)accountInfoMo.friday);
//                        break;
//                }
//                return bundle;
//            }
//        };
        pager.setOffscreenPageLimit(5);
        pager.setPagingEnabled(true);
        pager.setAdapter(new NewsFrPagerAdapter(getChildFragmentManager(), titles, fragments));
//        tabs.setViewPager(pager);

        toolbar_tab.setupWithViewPager(pager);
        toolbar_tab.setTabMode(TabLayout.MODE_FIXED);
        int weekIndex = TimeUtils.getWeekIndex(System.currentTimeMillis());
        switch (weekIndex) {
            case SATURDAY:
            case SUNDAY:
            case MONDAY:
                setCurrentFragment(0);
                break;
            case TUESDAY:
                setCurrentFragment(1);
                break;
            case WEDNESDAY:
                setCurrentFragment(2);
                break;
            case THURSDAY:
                setCurrentFragment(3);
                break;
            case FRIDAY:
                setCurrentFragment(4);
                break;
        }
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setEnableRefresh(false);

        final TabLayout.Tab tab = toolbar_tab.getTabAt(1);
        tab.setCustomView(getTabItemView(1));
        final TabLayout.Tab tab0 = toolbar_tab.getTabAt(0);
        tab0.setCustomView(getTabItemView(0));
        final TabLayout.Tab tab2 = toolbar_tab.getTabAt(2);
        tab2.setCustomView(getTabItemView(2));
        final TabLayout.Tab tab3 = toolbar_tab.getTabAt(3);
        tab3.setCustomView(getTabItemView(3));
        final TabLayout.Tab tab4 = toolbar_tab.getTabAt(4);
        tab4.setCustomView(getTabItemView(4));
    }

    public View getTabItemView(int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.plate_live_tab_layout_item, null);
//        view.setBackground(getResources().getDrawable(R.drawable.bg_plate_live_tab_item));
        TextView textView = (TextView) view.findViewById(R.id.tv_title1);
        TextView textView2 = (TextView) view.findViewById(R.id.tv_title2);
        AppUtils.setCustomFont(getContext(), textView2);
        if (position == 0) {
            textView.setText("一");
        } else if (position == 1) {
            textView.setText("二");
        } else if (position == 2) {
            textView.setText("三");
        } else if (position == 3) {
            textView.setText("四");
        } else if (position == 4) {
            textView.setText("五");
        }
        return view;
    }

    private void setCurrentFragment(int fType) {
        if (fType == 0) {
            pager.setCurrentItem(0);
        } else if (fType == 1)
            pager.setCurrentItem(1);
        else if (fType == 2)
            pager.setCurrentItem(2);
        else if (fType == 3)
            pager.setCurrentItem(3);
        else if (fType == 4)
            pager.setCurrentItem(4);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unregistorEventBus();
        unbinder.unbind();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThrea(EventBusKeyDefine.EventBusMsgData<String> data) {
        int type = data.getType();
        try {
            if (EventBusKeyDefine.EVENTBUS_LOGIN_SUCCESS == type || EventBusKeyDefine.EVENTBUS_LOGOUT == type || EventBusKeyDefine.EVENTBUS_USER_INFO_UPDATE == type || EventBusKeyDefine.EVENTBUS_REGISTER_SUCCESS == type) {
                refreshLoginStateView();
            } else if (EventBusKeyDefine.EVENTBUS_REFRESH_LIVE_TEACHER == type) {
                loadData(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshLoginStateView() {
//        if (LoginHelper.getInstance().isLogin()) {
//            layoutUnlogin.setVisibility(View.GONE);
//            layoutTradeLogin.setVisibility(View.VISIBLE);
//        } else {
//            layoutUnlogin.setVisibility(View.VISIBLE);
//            layoutTradeLogin.setVisibility(View.GONE);
//        }
    }

    @OnClick({R.id.layout_cover, R.id.empty_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_cover:
                PolyvLivePlayerActivity.actionStart(getActivity());
                break;
            case R.id.empty_layout:
                loadData(true);
                break;
        }
    }
}

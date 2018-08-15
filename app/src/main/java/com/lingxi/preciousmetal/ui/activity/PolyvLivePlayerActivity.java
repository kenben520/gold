package com.lingxi.preciousmetal.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.admaster.sdk.api.AdmasterSdk;
import com.easefun.polyvsdk.live.PolyvLiveSDKUtil;
import com.easefun.polyvsdk.live.chat.playback.api.PolyvLive_Status;
import com.easefun.polyvsdk.live.chat.playback.api.listener.PolyvLive_StatusNorListener;
import com.easefun.polyvsdk.live.video.PolyvLiveMediaInfoType;
import com.easefun.polyvsdk.live.video.PolyvLivePlayErrorReason;
import com.easefun.polyvsdk.live.video.PolyvLiveVideoView;
import com.easefun.polyvsdk.live.video.PolyvLiveVideoViewListener;
import com.easefun.polyvsdk.live.video.auxiliary.PolyvLiveAuxiliaryVideoView;
import com.easefun.polyvsdk.live.vo.PolyvLiveChannelVO;
import com.easefun.polyvsdk.live.vo.PolyvLiveMarqueeVo;
import com.easefun.polyvsdk.marquee.PolyvMarqueeItem;
import com.easefun.polyvsdk.marquee.PolyvMarqueeView;
import com.jaeger.library.StatusBarUtil;
import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.LiveStatusRequestMo;
import com.lingxi.biz.domain.responseMo.LiveStatusMo;
import com.lingxi.biz.service.AnalyseTradeService;
import com.lingxi.common.base.GlideApp;
import com.lingxi.common.util.StringUtil;
import com.lingxi.polylib.util.AdmasterSdkUtils;
import com.lingxi.polylib.util.PolyvMarqueeUtils;
import com.lingxi.polylib.util.PolyvScreenUtils;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.BaseActivity;
import com.lingxi.preciousmetal.common.CommonParam;
import com.lingxi.preciousmetal.common.EventBusKeyDefine;
import com.lingxi.preciousmetal.domain.UserInfoBean;
import com.lingxi.preciousmetal.login.LoginHelper;
import com.lingxi.preciousmetal.ui.activity.kefu.KefuHelper;
import com.lingxi.preciousmetal.ui.adapter.NewsFrPagerAdapter;
import com.lingxi.preciousmetal.ui.adapter.ViewPagerSlide;
import com.lingxi.preciousmetal.ui.fragment.FragmentVideoFlash;
import com.lingxi.preciousmetal.ui.fragment.KefuFragment;
import com.lingxi.preciousmetal.ui.fragment.analyzeplate.EssenceCourseFragment;
import com.lingxi.preciousmetal.ui.fragment.analyzeplate.PolyvChatFragment;
import com.lingxi.preciousmetal.ui.jpush.ChatManagerUtil;
import com.lingxi.preciousmetal.ui.jpush.MyPolyvChatManager;
import com.lingxi.preciousmetal.ui.receiver.NetStateReceiver;
import com.lingxi.preciousmetal.ui.widget.dialog.CommonDialog;
import com.lingxi.preciousmetal.ui.widget.player.PolyvPlayerAuxiliaryView;
import com.lingxi.preciousmetal.ui.widget.player.PolyvPlayerLightView;
import com.lingxi.preciousmetal.ui.widget.player.PolyvPlayerMediaController;
import com.lingxi.preciousmetal.ui.widget.player.PolyvPlayerVolumeView;
import com.lingxi.preciousmetal.util.ApplicationUtils;
import com.lingxi.preciousmetal.util.glideutils.GlideUtils;
import com.lingxi.preciousmetal.util.utilCode.NetworkUtils;
import com.lingxi.preciousmetal.util.utilCode.ObjectUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class PolyvLivePlayerActivity extends BaseActivity implements PolyvPlayerMediaController.EventListener {
    private static final String TAG = PolyvLivePlayerActivity.class.getSimpleName();
    private String userId, channelId, chatUserId, nickName;
    private String defaultNickName = "没有昵称";// TODO: 2018/5/29
    // 聊天室管理类
    private MyPolyvChatManager chatManager;
    // 聊天室fragment
    private PolyvChatFragment polyvChatFragment;
    private KefuFragment kefuFragment;
//    private PolyvQuestionFragment polyvQuestionFragment;

    //    private PolyvTabViewPagerFragment tabViewPagerFragment;
//    private PolyvDanmuFragment danmuFragment;
    // 获取直播状态接口
    private PolyvLive_Status live_status;
    /**
     * 播放器的parentView
     */
    private RelativeLayout viewLayout = null;

    private ImageView polyv_live_audio_view = null;//音频背景

    /**
     * 播放器主类
     */
    private PolyvLiveVideoView videoView = null;
    /**
     * 跑马灯控件
     */
    private PolyvMarqueeView marqueeView = null;
    private PolyvMarqueeItem marqueeItem = null;
    private PolyvMarqueeUtils marqueeUtils = null;
    //暂停时显示的20%透明度背景
    private View v_pause_bg;
    /**
     * 播放器控制栏
     */
    private PolyvPlayerMediaController mediaController = null;
    /**
     * 辅助播放器类，用于播放视频片头广告
     */
    private PolyvLiveAuxiliaryVideoView auxiliaryVideoView = null;
//    ProgressBar auxiliaryLoadingProgress;
    /**
     * 辅助显示类，用于显示图片广告
     */
    private PolyvPlayerAuxiliaryView auxiliaryView = null;
    /**
     * 手势亮度指示标志
     */
    private PolyvPlayerLightView lightView = null;
    /**
     * 手势音量指示标志
     */
    private PolyvPlayerVolumeView volumeView = null;
    /**
     * 用于显示广告倒计时
     */
    private TextView advertCountDown = null;
    private TextView tv_no_stream_tips;
    private ImageView iv_loading_progress;
    private boolean isPlay = false;
    // 当再次直播的类型是ppt直播时，是否需要跳转到ppt直播的播放器
    private boolean isToOtherLivePlayer = true;
    TabLayout toolbar_tab;
    ViewPagerSlide pager;
    View back;
    RelativeLayout noStream;
    private CommonDialog commonDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null)
            savedInstanceState.putParcelable("android:support:fragments", null);
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.black));
        setContentView(R.layout.polyv_activity_player_live);
        registorEventBus();
        // 生成播放器父控件的宽高比为16:9的高
        PolyvScreenUtils.generateHeight16_9(this);
        // 初始化广告监测器
        AdmasterSdk.init(getApplicationContext(), "");

        chatUserId = getIntent().getStringExtra("chatUserId");
        nickName = getIntent().getStringExtra("nickName");
        if (StringUtil.isEmpty(nickName)) {
            nickName = defaultNickName;
        }
        userId = getIntent().getStringExtra("userId");
        channelId = getIntent().getStringExtra("channelId");

        isToOtherLivePlayer = getIntent().getBooleanExtra("isToOtherLivePlayer", true);
        loadLiveStatus();
        findIdAndNew();
        addFragment();
        initView();

        boolean isLandscape = getIntent().getBooleanExtra("isLandscape", false);
        if (isLandscape)
            mediaController.changeToLandscape();
        else
            mediaController.changeToPortrait();


//        setLivePlay(userId, channelId);
    }

    public static void actionStart(Context context) {
        String nickName = "游客msd" + Build.SERIAL;
        boolean isLogin4 = LoginHelper.getInstance().isLogin();
        if (!isLogin4) {
            Intent intent = PolyvLivePlayerActivity.newIntent(context, CommonParam.POLY_USER_ID, CommonParam.POLY_CHANNEL_ID, CommonParam.POLY_CHAT_USER_ID, nickName, false, false);
            context.startActivity(intent);
        } else {
            UserInfoBean userInfo = LoginHelper.getInstance().getLoginUserInfo();
            Intent intent = PolyvLivePlayerActivity.newIntent(context, CommonParam.POLY_USER_ID, CommonParam.POLY_CHANNEL_ID, userInfo.user_id, userInfo.nick_name, false, false);
            context.startActivity(intent);
        }
    }

    private void addFragment() {
//        PolyvTabFragment tabFragment = new PolyvTabFragment();
//        tabViewPagerFragment = new PolyvTabViewPagerFragment();
        polyvChatFragment = new PolyvChatFragment();
        kefuFragment = KefuFragment.newInstance();
//        polyvQuestionFragment = new PolyvQuestionFragment();
        // 聊天室实例
        chatManager = ChatManagerUtil.getInstance().getChatManager();
//        tabViewPagerFragment.initConfig(polyvChatFragment);
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.add(R.id.fl_tab, tabFragment, "tabFragment")
//                .add(R.id.fl_tag_viewpager, tabViewPagerFragment, "tabViewPagerFragment");


//        danmuFragment = new PolyvDanmuFragment();
//        fragmentTransaction.add(R.id.fl_danmu, danmuFragment, "danmuFragment").commit();

//        tabViewPagerFragment.setDanmuFragment(danmuFragment);


        ArrayList<String> titles = new ArrayList<>();
        titles.add("课程精华");
        titles.add("课程精华");
        titles.add("在线客服");
        titles.add("行情快讯");
//        titles.add("咨询提问");
        toolbar_tab.addTab(toolbar_tab.newTab().setText(titles.get(0)));
        toolbar_tab.addTab(toolbar_tab.newTab().setText(titles.get(1)));
        toolbar_tab.addTab(toolbar_tab.newTab().setText(titles.get(2)));
        toolbar_tab.addTab(toolbar_tab.newTab().setText(titles.get(3)));
//        toolbar_tab.addTab(toolbar_tab.newTab().setText(titles.get(4)));
        toolbar_tab.post(new Runnable() {
            @Override
            public void run() {
                ApplicationUtils.setIndicator(toolbar_tab, 10, 10);
            }
        });
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new EssenceCourseFragment());
        fragments.add(polyvChatFragment);
        fragments.add(kefuFragment);
        fragments.add(FragmentVideoFlash.newInstance("行情快讯"));
//        fragments.add(polyvQuestionFragment);
        pager.setOffscreenPageLimit(6);
        pager.setPagingEnabled(true);
        NewsFrPagerAdapter pagerAdapter = new NewsFrPagerAdapter(getSupportFragmentManager(), titles, fragments);
        pager.setAdapter(pagerAdapter);

        polyvChatFragment.initChatConfig(chatManager, pagerAdapter, chatUserId, userId, channelId, nickName);
//        polyvQuestionFragment.initChatConfig(chatManager, pagerAdapter, userId, channelId, nickName);

        toolbar_tab.setupWithViewPager(pager);
        toolbar_tab.setTabMode(TabLayout.MODE_FIXED);
        pager.setCurrentItem(0);
        final TabLayout.Tab tab = toolbar_tab.getTabAt(1);
        tab.setCustomView(getTabItemView(1));
        final TabLayout.Tab tab0 = toolbar_tab.getTabAt(0);
        tab0.setCustomView(getTabItemView(0));
        final TabLayout.Tab tab2 = toolbar_tab.getTabAt(2);
        tab2.setCustomView(getTabItemView(2));
        final TabLayout.Tab tab3 = toolbar_tab.getTabAt(3);
        tab3.setCustomView(getTabItemView(3));
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                View view = tab.getCustomView();
                if (position == 1) {
                    ((TextView) view.findViewById(R.id.textview)).setSelected(true);
                    ((TextView) view.findViewById(R.id.textview)).setEnabled(true);
                    updateUnReadCountView(RESET_MSG);
                } else {
                    ((TextView) view.findViewById(R.id.textview)).setSelected(false);
                    ((TextView) view.findViewById(R.id.textview)).setEnabled(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void updateUnReadCountView(int type) {
        final TabLayout.Tab tab = toolbar_tab.getTabAt(1);
        View view = tab.getCustomView();
        TextView textView = ((TextView) view.findViewById(R.id.tv_unread_count));
        if (RECEIVE_MSG == type) {
            String text = textView.getText().toString();
            if (!"99+".equals(text)) {
                int unReadCount = Integer.parseInt(text);
                if (unReadCount >= 99) {
                    textView.setText(++unReadCount + "+");
                } else {
                    textView.setText(++unReadCount + "");
                }
                textView.setVisibility(View.VISIBLE);
            }
        } else if (RESET_MSG == type) {
            textView.setText(0 + "");
            textView.setVisibility(View.GONE);
        }
    }

    LiveStatusMo liveStatusMo;

    public View getTabItemView(int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.chat_room_tab_layout_item, null);
        TextView textView = (TextView) view.findViewById(R.id.textview);
        if (position == 0) {
            textView.setText("课程精华");
        } else if (position == 1) {
            textView.setText("聊天室");
        } else if (position == 2) {
            textView.setText("在线客服");
        } else if (position == 3) {
            textView.setText("行情快讯");
        }
        return view;
    }


    private void loadLiveStatus() {
        LiveStatusRequestMo liveListRequestMo = new LiveStatusRequestMo();
        AnalyseTradeService.getLiveStatusInfo(liveListRequestMo, new BizResultListener<LiveStatusMo>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, LiveStatusMo mo) {
            }

            @Override
            public void onSuccess(LiveStatusMo mo) {
                liveStatusMo = mo;
                updateLiveStatusInfo();
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {

            }
        });
    }

    public void updateLiveStatusInfo() {
        EventBus.getDefault().post(new EventBusKeyDefine.EventBusMsgData<Integer>(EventBusKeyDefine.EVENTBUS_LIVE_STATUS, liveStatusMo.state));
        if (liveStatusMo.state != 1) {//正在直播
            if (!ObjectUtils.isEmpty(liveStatusMo.video)) {//2 有下一直播
                if (liveStatusMo.state == 2) {
//                    int weekIndex = TimeUtils.getWeekIndex(System.currentTimeMillis());
//                    String weekStr = "";
//                    switch (liveStatusMo.video.getWhat_day()) {
//                        case 1:
//                            weekStr = "星期一";
//                            break;
//                        case 2:
//                            weekStr = "星期二";
//                            break;
//                        case 3:
//                            weekStr = "星期三";
//                            break;
//                        case 4:
//                            weekStr = "星期四";
//                            break;
//                        case 5:
//                            weekStr = "星期五";
//                            break;
//                    }
//                    StringBuilder tips = new StringBuilder();
//                    tips.append("当前时段无课程安排，下一直播\n");
//                    if (!StringUtil.isEmpty(weekStr)) tips.append(weekStr + " ");
//                    String starttime = liveStatusMo.video.getStart_time() == null ? "" : liveStatusMo.video.getStart_time();
//                    tips.append(starttime);
//                    tips.append("-");
//                    String endtime = liveStatusMo.video.getEnd_time() == null ? "" : liveStatusMo.video.getEnd_time();
//                    tips.append(endtime);
//                    tips.append(" ");
//                    String teacher = liveStatusMo.video.getTeacher() == null ? "" : liveStatusMo.video.getTeacher();
//                    tips.append(teacher);
//                    tips.append("-");
//                    tips.append("《");
//                    String title = liveStatusMo.video.getTitle() == null ? "" : liveStatusMo.video.getTitle();
//                    tips.append(title);
//                    tips.append("》");
//                    tv_no_stream_tips.setText(tips.toString());
                    tv_no_stream_tips.setText("当前暂无直播");
                }
                if (liveStatusMo.state == 3)//3 没有下一直播
                {
                    tv_no_stream_tips.setText(liveStatusMo.video.getTitle());
                }
            }
            noStream.setVisibility(View.VISIBLE);
        } else {
            checkWifi();
        }
        if (!ObjectUtils.isEmpty(liveStatusMo.video)) {
            mediaController.updateLiveProgramInfo(liveStatusMo.video);
        }
    }

    public void checkWifi() {
        if (NetworkUtils.isWifiConnected()) {
            iv_loading_progress.setVisibility(View.VISIBLE);
            setLivePlay(userId, channelId);
        } else {
            popWifiDialog();
        }
    }

    private boolean isSetLivePlay = false;

    private void popWifiDialog() {
        if (!isFinishing() && !(commonDialog != null && commonDialog.isShowing())) {
            commonDialog = new CommonDialog(PolyvLivePlayerActivity.this, "", "当前为非WiFi环境，是否使用流量观看视频?", commitClickListener, 1, "取消播放", "继续播放", null, 1);
            commonDialog.show();
        }
    }

    private CommonDialog.CommitClickListener commitClickListener = new CommonDialog.CommitClickListener() {
        @Override
        public void click(int whichDialog, Object object) {
            switch (whichDialog) {
                case 1:
                    if (!isSetLivePlay) {
                        iv_loading_progress.setVisibility(View.VISIBLE);
                        setLivePlay(userId, channelId);
                    } else {
                        if (!videoView.isPlaying()) {
                            mediaController.playOrPause();
                        }
                    }
                    break;
            }

        }
    };

    private void findIdAndNew() {
        viewLayout = (RelativeLayout) findViewById(R.id.view_layout);
        videoView = (PolyvLiveVideoView) findViewById(R.id.polyv_live_video_view);
        polyv_live_audio_view = (ImageView) findViewById(R.id.polyv_live_audio_view);
        GlideUtils.loadImageViewFitCenter(this, R.drawable.bg_audio, polyv_live_audio_view);

        marqueeView = (PolyvMarqueeView) findViewById(R.id.polyv_marquee_view);
        v_pause_bg = findViewById(R.id.v_pause_bg);
        mediaController = (PolyvPlayerMediaController) findViewById(R.id.polyv_player_media_controller);
        mediaController.setEventListener(this);
        ProgressBar loadingProgress = (ProgressBar) findViewById(R.id.loading_progress);
        noStream = (RelativeLayout) findViewById(R.id.no_stream);
        tv_no_stream_tips = (TextView) findViewById(R.id.tv_no_stream_tips);
        iv_loading_progress = (ImageView) findViewById(R.id.iv_loading_progress);
        GlideApp.with(mContext).load(R.drawable.loading1).into(iv_loading_progress);

        back = findViewById(R.id.layout_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        auxiliaryVideoView = (PolyvLiveAuxiliaryVideoView) findViewById(R.id.polyv_live_auxiliary_video_view);
//        auxiliaryLoadingProgress = (ProgressBar) findViewById(R.id.auxiliary_loading_progress);
        auxiliaryView = (PolyvPlayerAuxiliaryView) findViewById(R.id.polyv_player_auxiliary_view);
        lightView = (PolyvPlayerLightView) findViewById(R.id.polyv_player_light_view);
        volumeView = (PolyvPlayerVolumeView) findViewById(R.id.polyv_player_volume_view);
        advertCountDown = (TextView) findViewById(R.id.count_down);
        mediaController.initConfig(viewLayout, false);

//        mediaController.setDanmuFragment(danmuFragment);
//        tabViewPagerFragment.setVideoView(videoView);
        videoView.setMediaController(mediaController);
        videoView.setAuxiliaryVideoView(auxiliaryVideoView);
        videoView.setPlayerBufferingIndicator(iv_loading_progress);
        videoView.setNoStreamIndicator(noStream);
        auxiliaryVideoView.setPlayerBufferingIndicator(iv_loading_progress);
        auxiliaryView.setPolyvLiveVideoView(videoView);
        // 设置跑马灯
        videoView.setMarqueeView(marqueeView, marqueeItem = new PolyvMarqueeItem());
        toolbar_tab = (TabLayout) findViewById(R.id.fl_tab);
        pager = (ViewPagerSlide) findViewById(R.id.fl_tag_viewpager);
        viewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PolyvScreenUtils.isPortrait(PolyvLivePlayerActivity.this) && (polyvChatFragment.emoLayoutIsVisible() || ApplicationUtils.isShowKeyboard(PolyvLivePlayerActivity.this, viewLayout))) {
                    polyvChatFragment.resetEmoLayout(true);
                }
            }
        });
    }

    @Override
    public void changeToPortrait() {
        back.setVisibility(View.VISIBLE);
    }

    @Override
    public void changeToLandscape() {
        back.setVisibility(View.GONE);
    }

    private void initView() {
        videoView.setOpenMarquee(true);
        videoView.setOpenNotLivePlayback(isToOtherLivePlayer);
        videoView.setOpenWait(true);
        videoView.setOpenAd(true);
        videoView.setOpenPreload(true, 2);
        videoView.setNeedGestureDetector(true);
        videoView.setOnPreparedListener(new PolyvLiveVideoViewListener.OnPreparedListener() {
            @Override
            public void onPrepared() {
                if (commonDialog != null && commonDialog.isShowing() && videoView.isPlaying()) {
                    mediaController.playOrPause();
                } else {
                    Toast.makeText(PolyvLivePlayerActivity.this, "准备完毕，可以播放", Toast.LENGTH_SHORT).show();
                }
            }
        });

        videoView.setOnVideoPlayListener(new PolyvLiveVideoViewListener.OnVideoPlayListener() {
            @Override
            public void onPlay() {
                if (commonDialog != null && commonDialog.isShowing() && videoView.isPlaying()) {
                    mediaController.playOrPause();
                } else {
                    v_pause_bg.setVisibility(View.GONE);
                }
            }
        });

        videoView.setOnVideoPauseListener(new PolyvLiveVideoViewListener.OnVideoPauseListener() {
            @Override
            public void onPause() {
                v_pause_bg.setVisibility(View.VISIBLE);
            }
        });

        videoView.setOnInfoListener(new PolyvLiveVideoViewListener.OnInfoListener() {
            @Override
            public void onInfo(int what, int extra) {
                switch (what) {
                    case PolyvLiveMediaInfoType.MEDIA_INFO_BUFFERING_START:
                        Toast.makeText(PolyvLivePlayerActivity.this, "开始缓冲", Toast.LENGTH_SHORT).show();
                        break;

                    case PolyvLiveMediaInfoType.MEDIA_INFO_BUFFERING_END:
                        Toast.makeText(PolyvLivePlayerActivity.this, "结束缓冲", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        videoView.setOnVideoPlayErrorListener(new PolyvLiveVideoViewListener.OnVideoPlayErrorListener() {
            @Override
            public void onVideoPlayError(@NonNull PolyvLivePlayErrorReason errorReason) {
                switch (errorReason.getType()) {
                    case NETWORK_DENIED:
                        Toast.makeText(PolyvLivePlayerActivity.this, "无法连接网络，请连接网络后播放", Toast.LENGTH_SHORT).show();
                        break;

                    case START_ERROR:
                        Toast.makeText(PolyvLivePlayerActivity.this, "播放错误，请重新播放(error code " + PolyvLivePlayErrorReason.ErrorType.START_ERROR.getCode() + ")", Toast.LENGTH_SHORT).show();
                        break;

                    case CHANNEL_NULL:
                        Toast.makeText(PolyvLivePlayerActivity.this, "频道信息获取失败，请重新播放(error code " + PolyvLivePlayErrorReason.ErrorType.CHANNEL_NULL.getCode() + ")", Toast.LENGTH_SHORT).show();
                        break;

                    case LIVE_UID_NOT_EQUAL:
                        Toast.makeText(PolyvLivePlayerActivity.this, "用户id错误，请重新设置(error code" + PolyvLivePlayErrorReason.ErrorType.LIVE_UID_NOT_EQUAL.getCode() + ")", Toast.LENGTH_SHORT).show();
                        break;

                    case LIVE_CID_NOT_EQUAL:
                        Toast.makeText(PolyvLivePlayerActivity.this, "频道号错误，请重新设置(error code " + PolyvLivePlayErrorReason.ErrorType.LIVE_CID_NOT_EQUAL.getCode() + ")", Toast.LENGTH_SHORT).show();
                        break;

                    case LIVE_PLAY_ERROR:
                        Toast.makeText(PolyvLivePlayerActivity.this, "播放错误，请稍后重试(error code " + PolyvLivePlayErrorReason.ErrorType.LIVE_PLAY_ERROR.getCode() + ")", Toast.LENGTH_SHORT).show();
                        break;

                    case RESTRICT_NULL:
                        Toast.makeText(PolyvLivePlayerActivity.this, "限制信息错误，请稍后重试(error code " + PolyvLivePlayErrorReason.ErrorType.RESTRICT_NULL.getCode() + ")", Toast.LENGTH_SHORT).show();
                        break;

                    case RESTRICT_ERROR:
                        Toast.makeText(PolyvLivePlayerActivity.this, errorReason.getErrorMsg() + "(error code " + PolyvLivePlayErrorReason.ErrorType.RESTRICT_ERROR.getCode() + ")", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        videoView.setOnErrorListener(new PolyvLiveVideoViewListener.OnErrorListener() {
            @Override
            public void onError() {
                Toast.makeText(PolyvLivePlayerActivity.this, "播放错误，请稍后重试(error code " + PolyvLivePlayErrorReason.ErrorType.WAIT_PLAY_ERROR.getCode() + ")", Toast.LENGTH_SHORT).show();
            }
        });

        videoView.setOnCoverImageOutListener(new PolyvLiveVideoViewListener.OnCoverImageOutListener() {
            @Override
            public void onOut(@NonNull String url, @Nullable String clickPath) {
                auxiliaryView.show(url, clickPath);
            }
        });

        videoView.setOnWillPlayWaittingListener(new PolyvLiveVideoViewListener.OnWillPlayWaittingListener() {
            @Override
            public void onWillPlayWaitting(boolean isCoverImage) {
                Toast.makeText(PolyvLivePlayerActivity.this, "当前暂无直播，将播放暖场" + (isCoverImage ? "图片" : "视频"), Toast.LENGTH_LONG).show();
                // 获取直播状态
                getLive_Status();
            }
        });

        videoView.setOnNoLiveAtPresentListener(new PolyvLiveVideoViewListener.OnNoLiveAtPresentListener() {
            @Override
            public void onNoLiveAtPresent() {
                // 获取直播状态
                if (!ObjectUtils.isEmpty(liveStatusMo) && liveStatusMo.state == 1 && !showNoTeacher) {//正在直播
                    tv_no_stream_tips.setText("老师离开一小会,请耐心等候");
//                    Toast.makeText(PolyvLivePlayerActivity.this, "老师离开一小会！", Toast.LENGTH_SHORT).show();
                    showNoTeacher = true;
                }
                getLive_Status();
            }
        });

        videoView.setOnNoLivePlaybackListener(new PolyvLiveVideoViewListener.OnNoLivePlaybackListener() {
            @Override
            public void onNoLivePlayback(String playbackUrl, String recordFileSessionId, String title, boolean isList) {
                // TODO: 2018/5/25 被删除
//                Toast.makeText(PolyvLivePlayerActivity.this, "当前暂无直播，将播放回放视频", Toast.LENGTH_SHORT).show();
//                Intent playIntent;
//                if (TextUtils.isEmpty(recordFileSessionId)) {
//                    playIntent = PolyvPbPlayerActivity.newUrlIntent(PolyvLivePlayerActivity.this, playbackUrl, title, false);
//                    playIntent = PolyvPbPlayerActivity.addChatExtra(playIntent, userId, channelId, chatUserId, nickName, true);
//                    playIntent = PolyvPbPlayerActivity.addLiveExtra(playIntent, false, isToOtherLivePlayer);
//                    playIntent = PolyvPbPlayerActivity.addPlaybackParam(playIntent, videoView.getPlaybackParam());
//                } else {
//                    playIntent = PolyvPPTPbPlayerActivity.newUrlIntent(PolyvLivePlayerActivity.this, playbackUrl, title, recordFileSessionId, isList, false);
//                    playIntent = PolyvPPTPbPlayerActivity.addChatExtra(playIntent, userId, channelId, chatUserId, nickName, true);
//                    playIntent = PolyvPPTPbPlayerActivity.addLiveExtra(playIntent, false, isToOtherLivePlayer);
//                    playIntent = PolyvPPTPbPlayerActivity.addPlaybackParam(playIntent, videoView.getPlaybackParam());
//                }
//                startActivity(playIntent);
//                finish();
            }
        });

        videoView.setOnGetMarqueeVoListener(new PolyvLiveVideoViewListener.OnGetMarqueeVoListener() {
            @Override
            public void onGetMarqueeVo(PolyvLiveMarqueeVo marqueeVo) {
                if (marqueeUtils == null)
                    marqueeUtils = new PolyvMarqueeUtils();
                // 更新为后台设置的跑马灯类型
                marqueeUtils.updateMarquee(PolyvLivePlayerActivity.this, marqueeVo, marqueeItem, channelId, userId, nickName);
            }
        });

        videoView.setOnAdvertisementOutListener(new PolyvLiveVideoViewListener.OnAdvertisementOutListener() {
            @Override
            public void onOut(@NonNull PolyvLiveChannelVO.ADMatter adMatter) {
                auxiliaryView.show(adMatter);
            }

            @Override
            public void onClick(@NonNull PolyvLiveChannelVO.ADMatter adMatter) {
                // 发送广告点击监测
                AdmasterSdkUtils.sendAdvertMonitor(adMatter, AdmasterSdkUtils.MONITOR_CLICK);
                if (!TextUtils.isEmpty(adMatter.getAddrUrl())) {
                    try {
                        new URL(adMatter.getAddrUrl());
                    } catch (MalformedURLException e1) {
                        Log.e(TAG, PolyvLiveSDKUtil.getExceptionFullMessage(e1, -1));
                        return;
                    }

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(adMatter.getAddrUrl()));
                    startActivity(intent);
                }
            }
        });

        videoView.setOnAdvertisementCountDownListener(new PolyvLiveVideoViewListener.OnAdvertisementCountDownListener() {
            @Override
            public void onCountDown(int num) {
                advertCountDown.setText(String.format("广告也精彩：%d秒", num));
                advertCountDown.setVisibility(View.VISIBLE);
            }

            @Override
            public void onEnd(@NonNull PolyvLiveChannelVO.ADMatter adMatter) {
                advertCountDown.setVisibility(View.GONE);
                auxiliaryView.hide();
                // 发送广告曝光监测
                AdmasterSdkUtils.sendAdvertMonitor(adMatter, AdmasterSdkUtils.MONITOR_SHOW);
            }
        });

        videoView.setOnGestureLeftUpListener(new PolyvLiveVideoViewListener.OnGestureLeftUpListener() {

            @Override
            public void callback(boolean start, boolean end) {
                Log.d(TAG, String.format("LeftUp %b %b brightness %d", start, end, videoView.getBrightness(PolyvLivePlayerActivity.this)));
                int brightness = videoView.getBrightness(PolyvLivePlayerActivity.this) + 5;
                if (brightness > 100) {
                    brightness = 100;
                }

                videoView.setBrightness(PolyvLivePlayerActivity.this, brightness);
                lightView.setViewLightValue(brightness, end);
            }
        });

        videoView.setOnGestureLeftDownListener(new PolyvLiveVideoViewListener.OnGestureLeftDownListener() {

            @Override
            public void callback(boolean start, boolean end) {
                Log.d(TAG, String.format("LeftDown %b %b brightness %d", start, end, videoView.getBrightness(PolyvLivePlayerActivity.this)));
                int brightness = videoView.getBrightness(PolyvLivePlayerActivity.this) - 5;
                if (brightness < 0) {
                    brightness = 0;
                }

                videoView.setBrightness(PolyvLivePlayerActivity.this, brightness);
                lightView.setViewLightValue(brightness, end);
            }
        });

        videoView.setOnGestureRightUpListener(new PolyvLiveVideoViewListener.OnGestureRightUpListener() {

            @Override
            public void callback(boolean start, boolean end) {
                Log.d(TAG, String.format("RightUp %b %b volume %d", start, end, videoView.getVolume()));
                // 加减单位最小为10，否则无效果
                int volume = videoView.getVolume() + 10;
                if (volume > 100) {
                    volume = 100;
                }

                videoView.setVolume(volume);
                volumeView.setViewVolumeValue(volume, end);
            }
        });

        videoView.setOnGestureRightDownListener(new PolyvLiveVideoViewListener.OnGestureRightDownListener() {

            @Override
            public void callback(boolean start, boolean end) {
                Log.d(TAG, String.format("RightDown %b %b volume %d", start, end, videoView.getVolume()));
                // 加减单位最小为10，否则无效果
                int volume = videoView.getVolume() - 10;
                if (volume < 0) {
                    volume = 0;
                }

                videoView.setVolume(volume);
                volumeView.setViewVolumeValue(volume, end);
            }
        });
    }

    private void prepare() {
        advertCountDown.setVisibility(View.GONE);
        auxiliaryView.hide();
        // 取消请求
        if (marqueeUtils != null)
            marqueeUtils.shutdown();
    }

    public static Intent newIntent(Context context, String userId, String channelId, String chatUserId, String nickName, boolean isLandscape) {
        return newIntent(context, userId, channelId, chatUserId, nickName, isLandscape, true);
    }

    public static Intent newIntent(Context context, String userId, String channelId, String chatUserId, String nickName, boolean isLandscape, boolean isToOtherLivePlayer) {
        Intent intent = new Intent(context, PolyvLivePlayerActivity.class);
        intent.putExtra("userId", userId);
        intent.putExtra("channelId", channelId);
        intent.putExtra("chatUserId", chatUserId);
        intent.putExtra("nickName", nickName);
        intent.putExtra("isLandscape", isLandscape);
        intent.putExtra("isToOtherLivePlayer", isToOtherLivePlayer);
        return intent;
    }

    /**
     * 播放普通直播。<br/>
     */
    public void setLivePlay(String userId, String channelId) {
        prepare();
        videoView.setLivePlay(userId, channelId, false);
        isSetLivePlay = true;
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment == polyvChatFragment) {
            UserInfoBean userInfoBean = LoginHelper.getInstance().getLoginUserInfo();
            String avatar = "";
            if (userInfoBean != null) {
                avatar = userInfoBean.avatars;
            }
            if (!StringUtil.isEmpty(avatar)) {
                chatManager.login(chatUserId, channelId, nickName, avatar);
            } else {
                chatManager.login(chatUserId, channelId, nickName, CommonParam.POLY_CHAT_TOURIST_DEFAULT_AVATAR);
            }
        } else if (fragment == kefuFragment) {
            KefuHelper.getInstance().login(new KefuHelper.OnLoginListener() {
                @Override
                public void loginSuccess() {
                    EventBus.getDefault().post(new EventBusKeyDefine.EventBusMsgData<Integer>(EventBusKeyDefine.EVENTBUS_KEFU_VIEW_FRESH, 1));
                }

                @Override
                public void loginFail() {
                }
            });
        }
    }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
////        tabViewPagerFragment.getOnlineListFragment().onActivityResult(requestCode, resultCode, data);
//    }

    @Override
    public void back() {
        if (PolyvScreenUtils.isLandscape(this)) {
            mediaController.changeToPortrait();
            return;
        }
        if (PolyvScreenUtils.isPortrait(this) && (polyvChatFragment.emoLayoutIsVisible() || ApplicationUtils.isShowKeyboard(this, viewLayout))) {
            polyvChatFragment.resetEmoLayout(true);
            return;
        }
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (PolyvScreenUtils.isLandscape(this)) {
                mediaController.changeToPortrait();
                return true;
            }
//            if (PolyvScreenUtils.isPortrait(this) && (polyvChatFragment.emoLayoutIsVisible() || ApplicationUtils.isShowKeyboard(this, viewLayout))) {
            if (PolyvScreenUtils.isPortrait(this) && polyvChatFragment.emoLayoutIsVisible()) {
                polyvChatFragment.resetEmoLayout(true);
                return true;
            }
//            if (PolyvScreenUtils.isPortrait(this) && tabViewPagerFragment.getQuestionFragment().emoLayoutIsVisible()) {
//                tabViewPagerFragment.getQuestionFragment().resetEmoLayout(true);
//                return true;
//            }
//            if (PolyvScreenUtils.isPortrait(this) && tabViewPagerFragment.getOnlineListFragment().isLinkMicConnected()) {
//                tabViewPagerFragment.getOnlineListFragment().showStopCallDialog(true);
//                return true;
//            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean showNoTeacher;

    // 获取直播状态
    private void getLive_Status() {
        if (live_status == null)
            live_status = new PolyvLive_Status();
        live_status.shutdownSchedule();
        live_status.getLive_Status(channelId, 6000, 4000, new PolyvLive_StatusNorListener() {
            @Override
            public void success(boolean isLiving, final boolean isPPTLive) {
                if (isLiving) {
                    live_status.shutdownSchedule();
                    if (!isFinishing()) {
                        Toast.makeText(PolyvLivePlayerActivity.this, "直播开始了！", Toast.LENGTH_SHORT).show();
                        if (!isPPTLive || !isToOtherLivePlayer)
                            setLivePlay(userId, channelId);
                        else {
//                            startActivity(PolyvPPTLivePlayerActivity.newIntent(PolyvLivePlayerActivity.this, userId, channelId, chatUserId, nickName, false));
//                            finish();
                        }
                    }
                } else {

                }
            }

            @Override
            public void fail(String failTips, int code) {
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThrea1(EventBusKeyDefine.EventBusMsgData<Integer> data) {
        int type = data.getType();
        try {
            if (EventBusKeyDefine.EVENTBUS_CHATROOM_UNREAD_COUNT == type) {
                if (!ObjectUtils.isEmpty(data) && !ObjectUtils.isEmpty(data.getData())) {
                    if (1 != pager.getCurrentItem()) {
                        updateUnReadCountView(RECEIVE_MSG);
                    }
                }
            } else if (EventBusKeyDefine.EVENTBUS_LIVE_NETSTATE == type) {
                int netState = data.getData();
                if (NetStateReceiver.isMobileNet(netState)) {
                    if (videoView.isPlaying()) {
                        mediaController.playOrPause();
                    }
                    popWifiDialog();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final int RECEIVE_MSG = 1;
    private final int RESET_MSG = 2;


    private void clearGestureInfo() {
        videoView.clearGestureInfo();
        lightView.hide();
        volumeView.hide();
    }

    @Override
    public void onResume() {
        super.onResume();
        //回来后继续播放
        if (isPlay) {
            videoView.onActivityResume();
            if (auxiliaryView.isPauseAdvert()) {
                auxiliaryView.hide();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        clearGestureInfo();
    }

    @Override
    public void onStop() {
        super.onStop();
        //弹出去暂停
        isPlay = videoView.onActivityStop();
        if (isPlay)//页面跳转时如果当前正在播放
        {

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregistorEventBus();
        // 取消请求
        if (live_status != null)
            live_status.shutdownSchedule();
        if (marqueeUtils != null)
            marqueeUtils.shutdown();
        // 退出聊天室
        ChatManagerUtil.getInstance().disconnect();
        videoView.destroy();
        auxiliaryView.hide();
        mediaController.disable();
        // 关闭广告监测器
        AdmasterSdk.terminateSDK();
    }
}

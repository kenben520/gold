package com.lingxi.preciousmetal.ui.widget.player;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.easefun.polyvsdk.live.video.IPolyvLiveVideoView;
import com.easefun.polyvsdk.live.video.PolyvLiveMediaController;
import com.easefun.polyvsdk.live.video.PolyvLiveVideoView;
import com.lingxi.biz.domain.responseMo.LiveProgramListMo;
import com.lingxi.polylib.util.PolyvScreenUtils;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.ui.fragment.analyzeplate.PolyvDanmuFragment;
import com.lingxi.preciousmetal.util.utilCode.ObjectUtils;

public class PolyvPlayerMediaController extends PolyvLiveMediaController implements View.OnClickListener {
    private static final String TAG = PolyvPlayerMediaController.class.getSimpleName();
    private Context mContext = null;
    private PolyvLiveVideoView videoView = null;
    private PolyvDanmuFragment danmuFragment;
    //播放器所在的activity
    private Activity videoActivity;
    //controllerView,播放器的ParentView
    private View view, parentView, audioView;
    private ImageView iv_play, iv_land, iv_dmswitch;
    private ImageView iv_mode, iv_back, iv_mute;
    private TextView tv_mode, title;
    //显示的状态
    private boolean isShowing;
    //控制栏显示的时间
    private static final int longTime = 5000;
    private static final int HIDE = 12;
    private static final int UPDATE_PLAY_BUTTON = 13;
    private PopupWindow popupWindow;
    // 是否使用popupWindow弹出控制栏，非ppt直播/回放用false即可。
    // (注：如果是ppt直播/回放时，true：可移动的播放器层级<弹幕层级<控制栏层级，false：控制栏<可移动的播放器<弹幕)
    private boolean usePopupWindow;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HIDE:
                    hide();
                    break;
                case UPDATE_PLAY_BUTTON:
                    updatePlayButton();
                    break;
            }
        }
    };

    private void updatePlayButton() {
        if (isShowing && videoView != null) {
            if (videoView.isPlaying()) {
                iv_play.setSelected(false);
            } else {
                iv_play.setSelected(true);
            }
            handler.sendMessageDelayed(handler.obtainMessage(UPDATE_PLAY_BUTTON), 1000);
        }
    }

    private LiveProgramListMo.LiveProgramBean liveProgramBean;

    public void updateLiveProgramInfo(LiveProgramListMo.LiveProgramBean bean) {
        liveProgramBean = bean;
        if (!ObjectUtils.isEmpty(liveProgramBean)) {
            title.setText(liveProgramBean.getTitle());
        }
    }

    public PolyvPlayerMediaController(Context context) {
        this(context, null);
    }

    public PolyvPlayerMediaController(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PolyvPlayerMediaController(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        this.videoActivity = (Activity) context;
    }

    private void findIdAndNew() {
        iv_play = (ImageView) view.findViewById(R.id.iv_play);
        iv_land = (ImageView) view.findViewById(R.id.iv_land);
        iv_dmswitch = (ImageView) view.findViewById(R.id.iv_dmswitch);
        tv_mode = (TextView) view.findViewById(R.id.tv_mode);
        iv_mode = (ImageView) view.findViewById(R.id.iv_mode);
        title = (TextView) view.findViewById(R.id.title);
        iv_back = (ImageView) view.findViewById(R.id.iv_back);
        iv_mute = (ImageView) view.findViewById(R.id.iv_mute);
    }

    private void initView() {
        iv_play.setOnClickListener(this);
        iv_land.setOnClickListener(this);
        iv_dmswitch.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        iv_mode.setOnClickListener(this);
        iv_mute.setOnClickListener(this);
    }

    /**
     * 初始化控制栏的配置
     *
     * @param parentView     播放器的父控件
     * @param usePopupWindow 是否用popupWindow弹出控制栏
     */
    public void initConfig(final ViewGroup parentView, boolean usePopupWindow) {
        this.parentView = parentView;
        this.audioView = parentView.findViewById(R.id.polyv_live_audio_view);
        this.usePopupWindow = usePopupWindow;
        this.view = LayoutInflater.from(getContext()).inflate(R.layout.polyv_controller_media_port, usePopupWindow ? null : this);
        if (usePopupWindow) {
            popupWindow = new PopupWindow(mContext);
            popupWindow.setBackgroundDrawable(null);
            popupWindow.setContentView(view);
            view.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return videoView == null ? false : videoView.onPPTLiveTranTouchEvent(event, parentView.getMeasuredWidth());
                }
            });
        }
        findIdAndNew();
        initView();
    }

    public void setDanmuFragment(PolyvDanmuFragment danmuFragment) {
        this.danmuFragment = danmuFragment;
    }

    /**
     * 切换到横屏
     */
    public void changeToLandscape() {
        PolyvScreenUtils.setLandscape(videoActivity);
        //初始为横屏时，状态栏需要隐藏
        PolyvScreenUtils.hideStatusBar(videoActivity);
        //初始为横屏时，控制栏的宽高需要设置
        title.setVisibility(VISIBLE);
        iv_back.setVisibility(VISIBLE);
        iv_back.setSelected(true);
        if(mEventListener!=null)mEventListener.changeToLandscape();
        initLandScapeWH();
    }

    private void initLandScapeWH() {
        ViewGroup.LayoutParams vlp = parentView.getLayoutParams();
        vlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        vlp.height = ViewGroup.LayoutParams.MATCH_PARENT;
        iv_land.setSelected(true);
    }

    /**
     * 切换到竖屏
     */
    public void changeToPortrait() {
        PolyvScreenUtils.setPortrait(videoActivity);
        title.setVisibility(GONE);
        iv_back.setVisibility(GONE);
        iv_back.setSelected(false);
        if(mEventListener!=null)mEventListener.changeToPortrait();
//        tv_mode.setTextSize(Dimension.DP,12);
        initPortraitWH();
    }

    private void initPortraitWH() {
        ViewGroup.LayoutParams vlp = parentView.getLayoutParams();
        vlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        vlp.height = PolyvScreenUtils.getHeight16_9();
        iv_land.setSelected(false);
    }

    //根据屏幕状态改变控制栏布局
    private void resetControllerLayout() {
        hide();
        PolyvScreenUtils.reSetStatusBar(videoActivity);
        if (PolyvScreenUtils.isLandscape(mContext)) {
            initLandScapeWH();
        } else {
            initPortraitWH();
        }
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        resetControllerLayout();
    }

    @Override
    public void setMediaPlayer(IPolyvLiveVideoView player) {
        videoView = (PolyvLiveVideoView) player;
    }

    @Override
    public void release() {
        //播放器release时主动调用
    }

    @Override
    public void destroy() {
        //播放器destroy时主动调用
    }

    @Override
    public void hide() {
        if (isShowing) {
            handler.removeMessages(HIDE);
            handler.removeMessages(UPDATE_PLAY_BUTTON);
            isShowing = !isShowing;
            if (usePopupWindow) {
                try {
                    popupWindow.dismiss();
                } catch (Exception e) {
                }
                if (popupWindowDismissListener != null)
                    popupWindowDismissListener.dismiss();
            } else {
                setVisibility(View.GONE);
            }
        }
    }

    @Override
    public boolean isShowing() {
        return isShowing;
    }

    @Override
    public void setAnchorView(View view) {
        //...
    }

    /**
     * 退出播放器的Activity时需调用
     */
    public void disable() {
        hide();
    }

    @Override
    public void show(int timeout) {
        if (!isShowing) {
            handler.removeMessages(UPDATE_PLAY_BUTTON);
            handler.sendEmptyMessage(UPDATE_PLAY_BUTTON);
            isShowing = !isShowing;
            if (usePopupWindow) {
                int[] location = new int[2];
                parentView.getLocationInWindow(location);
                popupWindow.setWidth(parentView.getMeasuredWidth());
                popupWindow.setHeight(parentView.getMeasuredHeight());
                try {
                    popupWindow.showAtLocation(parentView, Gravity.NO_GRAVITY, location[0], location[1]);
                } catch (Exception e) {
                }
            } else {
                setVisibility(View.VISIBLE);
            }
        }
        handler.removeMessages(HIDE);
        handler.sendMessageDelayed(handler.obtainMessage(HIDE), timeout);
    }

    @Override
    public void show() {
        show(longTime);
    }

    //根据视频的播放状态去暂停或播放
    public void playOrPause() {
        handler.removeMessages(UPDATE_PLAY_BUTTON);
        if (videoView != null) {
            if (videoView.isPlaying()) {
                videoView.pause();
                iv_play.setSelected(true);
            } else {
                videoView.onStart();
                iv_play.setSelected(false);
            }
        }
    }

    //重置显示/隐藏弹幕的控件
    private void resetDmSwitchView() {
        if (iv_dmswitch.isSelected()) {
            iv_dmswitch.setSelected(false);
            danmuFragment.show();
        } else {
            iv_dmswitch.setSelected(true);
            danmuFragment.hide();
        }
    }

    //重置切换横竖屏的view
    private void resetOrientationView() {
        if (iv_land.isSelected()) {
            changeToPortrait();
        } else {
            changeToLandscape();
        }
    }

    private void changeToAudio() {
        iv_mode.setSelected(true);
//        videoView.setVisibility(INVISIBLE);
        audioView.setVisibility(VISIBLE);
        tv_mode.setText("实时音频直播");
    }

    public void changeToVideo() {
        iv_mode.setSelected(false);
        audioView.setVisibility(INVISIBLE);
//        videoView.setVisibility(VISIBLE);
        tv_mode.setText("实时直播");
    }

    //切换音视频模式
    private void switchAVMode() {
        if (iv_mode.isSelected()) {
//            if (PolyvScreenUtils.isPortrait(mContext)) {
                iv_land.setVisibility(VISIBLE);
//            }
            changeToVideo();
        } else {
//            if (PolyvScreenUtils.isPortrait(mContext)) {
                iv_land.setVisibility(INVISIBLE);
//            }
            changeToAudio();
        }
    }

    //切换静音模式
    private void switchMuteMode() {
        if (iv_mute.isSelected()) {
            iv_mute.setSelected(false);
            videoView.openSound();
        } else {
            iv_mute.setSelected(true);
            videoView.closeSound();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_play:
                playOrPause();
                break;
            case R.id.iv_dmswitch:
                resetDmSwitchView();
                break;
            case R.id.iv_land:
                resetOrientationView();
                break;
            case R.id.iv_back:
                if (mEventListener != null) {
                    mEventListener.back();
                }
                break;
            case R.id.iv_mode:
                switchAVMode();
                break;
            case R.id.iv_mute:
                switchMuteMode();
                break;
        }
    }

    public interface PopupWindowDismissListener {
        void dismiss();
    }

    private EventListener mEventListener;

    public interface EventListener {
        void back();
        void changeToPortrait();
        void changeToLandscape();
    }

    public void setEventListener(EventListener listener) {
        this.mEventListener = listener;
    }

    private PopupWindowDismissListener popupWindowDismissListener;

    public void setPopupWindowDismissListener(PopupWindowDismissListener listener) {
        this.popupWindowDismissListener = listener;
    }
}

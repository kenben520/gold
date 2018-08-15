package com.lingxi.preciousmetal.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easefun.polyvsdk.live.chat.PolyvChatManager;
import com.easefun.polyvsdk.live.chat.PolyvChatMessage;
import com.jaeger.library.StatusBarUtil;
import com.lingxi.biz.domain.responseMo.EssenceCourseMo;
import com.lingxi.common.util.StringUtil;
import com.lingxi.polylib.util.PolyvFaceManager;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.BaseActivity;
import com.lingxi.preciousmetal.common.EventBusKeyDefine;
import com.lingxi.preciousmetal.domain.UserInfoBean;
import com.lingxi.preciousmetal.login.LoginHelper;
import com.lingxi.preciousmetal.ui.adapter.PolyvEmoGridViewAdapter;
import com.lingxi.preciousmetal.ui.adapter.PolyvEmoPagerAdapter;
import com.lingxi.preciousmetal.ui.adapter.PolyvQuestionAdapter;
import com.lingxi.preciousmetal.ui.fragment.analyzeplate.PolyvChatFragment;
import com.lingxi.preciousmetal.ui.jpush.ChatManagerUtil;
import com.lingxi.preciousmetal.ui.jpush.MyPolyvChatManager;
import com.lingxi.preciousmetal.util.utilCode.ObjectUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifEditText;
import pl.droidsonroids.gif.GifImageSpan;
import pl.droidsonroids.gif.RelativeImageSpan;


/**
 * Created by zhangwei on 2018/3/26.
 */
public class QuestionActivity extends BaseActivity implements View.OnClickListener {
    private boolean isInitialized;
    private static final int RECEIVEANSWER = 6;
    public static String userId, channelId, chatUserId, nickName;
    private String defaultNickName = "没有昵称";// TODO: 2018/5/29
    // 聊天listView
    private ListView lv_question;
    // 聊天listView的适配器
    private PolyvQuestionAdapter polyvChatAdapter;
    // 聊天信息列表集合
    public static LinkedList<PolyvChatMessage> messages;

    // 信息编辑控件
    private GifEditText et_talk;
    // 聊天管理类
    private MyPolyvChatManager chatManager;
    //    NewsFrPagerAdapter pager;
    // 表情ViewPager
    private ViewPager vp_emo;
    // 表情的父布局
    private RelativeLayout rl_bot;
    // 表情页的下方圆点...，表情开关，发送按钮
    private ImageView iv_page1, iv_page2, iv_page3, iv_page4, iv_page5, iv_emoswitch, btn_close;
    private TextView iv_send;
    // 表情的文本长度
    private int emoLength;
    // 列
    private int columns = 7;
    // 行
    private int rows = 3;
    // 页
    private int pages = 5;
    //    private PolyvTabViewPagerFragment viewPagerFragment;
    private boolean isAddTips;

    public static void actionStart(Activity context, String userId, String channelId, String chatUserId, String nickName) {
        Intent intent = new Intent(context, QuestionActivity.class);
        intent.putExtra("userId", userId);
        intent.putExtra("channelId", channelId);
        intent.putExtra("chatUserId", chatUserId);
        intent.putExtra("nickName", nickName);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.activity_open, 0);
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (isFinishing())
                return;
            switch (msg.what) {
                case RECEIVEANSWER:
                    PolyvChatMessage chatMessage = (PolyvChatMessage) msg.obj;
                    // 由于显示信息的逻辑与聊天室接收信息一样，所以把chatType改为CHATTYPE_RECEIVE
                    chatMessage.setChatType(PolyvChatMessage.CHATTYPE_RECEIVE);
                    chatMessage.setValues(new String[]{chatMessage.getContent()});
                    polyvChatAdapter.add(chatMessage,false);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.black));
        setContentView(R.layout.polyv_fragment_question);
        registorEventBus();
        if (!isInitialized) {
            isInitialized = true;
            this.chatManager = ChatManagerUtil.getInstance().getChatManager();
            findIdAndNew();
            initView();
        }
    }


    // 接收老师对自己的回复时调用
    public void receiveAnswer(PolyvChatMessage chatMessage) {
        if (!isInitialized)
            return;
        Message message = handler.obtainMessage();
        message.obj = chatMessage;
        message.what = RECEIVEANSWER;
        handler.sendMessage(message);
    }

    // 聊天室登录成功时调用
    public void loginSuccess() {
        if (!isInitialized || isAddTips)
            return;
        isAddTips = true;
//        polyvChatAdapter.add(new PolyvChatMessage("", "", "", 0, null, new PolyvChatMessage.User("", "讲师", "http://livestatic.polyv.net/assets/images/teacher.png", "", "", "", ""), new CharSequence[]{"同学，您好！请问有什么问题吗？"}));
    }


    //根据索引获取表情页的view
    private View getEmoGridView(int position) {
        GridView gv_emo = (GridView) LayoutInflater.from(this).inflate(R.layout.polyv_gridview_emo, null)
                .findViewById(R.id.gv_emo);
        List<String> lists = new ArrayList<String>();
        lists.addAll(PolyvFaceManager.getInstance().getFaceMap().keySet());
        final List<String> elists = lists.subList(position * (columns * rows), (position + 1) * (columns * rows));
        PolyvEmoGridViewAdapter emoGridViewAdapter = new PolyvEmoGridViewAdapter(elists, this);
        gv_emo.setAdapter(emoGridViewAdapter);
        gv_emo.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if (position == elists.size() - 1)
                    deleteEmoText();
                else
                    appendEmo(elists.get(position));
            }
        });
        return gv_emo;
    }

    private void findIdAndNew() {
        lv_question = (ListView) findViewById(R.id.lv_question);
        iv_send = (TextView) findViewById(R.id.iv_send);
        et_talk = (GifEditText) findViewById(R.id.et_talk);
        vp_emo = (ViewPager) findViewById(R.id.vp_emo);
        iv_page1 = (ImageView) findViewById(R.id.iv_page1);
        iv_page2 = (ImageView) findViewById(R.id.iv_page2);
        iv_page3 = (ImageView) findViewById(R.id.iv_page3);
        iv_page4 = (ImageView) findViewById(R.id.iv_page4);
        iv_page5 = (ImageView) findViewById(R.id.iv_page5);
        iv_emoswitch = (ImageView) findViewById(R.id.iv_emoswitch);
        btn_close = (ImageView) findViewById(R.id.btn_close);
        rl_bot = (RelativeLayout) findViewById(R.id.rl_bot);
        chatUserId = getIntent().getStringExtra("chatUserId");
        nickName = getIntent().getStringExtra("nickName");
        if (StringUtil.isEmpty(nickName)) {
            nickName = defaultNickName;
        }
        userId = getIntent().getStringExtra("userId");
        channelId = getIntent().getStringExtra("channelId");
        messages=new LinkedList<>();
        LinkedList message = PolyvChatFragment.questionMessagesHmp.get(nickName);
        if (ObjectUtils.isEmpty(message)) {
            message=new LinkedList<>();
            messages.addAll(message);
            PolyvChatFragment.questionMessagesHmp.put(nickName, messages);
        }else{
            messages.addAll(message);
        }
    }

    // 删除表情
    private void deleteEmoText() {
        int start = et_talk.getSelectionStart();
        int end = et_talk.getSelectionEnd();
        if (end > 0) {
            if (start != end) {
                et_talk.getText().delete(start, end);
            } else if (isEmo(end)) {
                et_talk.getText().delete(end - emoLength, end);
            } else {
                et_talk.getText().delete(end - 1, end);
            }
        }
    }

    //判断是否是表情
    private boolean isEmo(int end) {
        String preMsg = et_talk.getText().subSequence(0, end).toString();
        int regEnd = preMsg.lastIndexOf("]");
        int regStart = preMsg.lastIndexOf("[");
        if (regEnd == end - 1 && regEnd - regStart >= 2) {
            String regex = preMsg.substring(regStart);
            emoLength = regex.length();
            if (PolyvFaceManager.getInstance().getFaceId(regex) != -1)
                return true;
        }
        return false;
    }

    //添加表情
    private void appendEmo(String emoKey) {
        SpannableStringBuilder span = new SpannableStringBuilder(emoKey);
        int textSize = (int) et_talk.getTextSize();
        Drawable drawable = null;
        ImageSpan imageSpan = null;
        try {
            drawable = new GifDrawable(getResources(), PolyvFaceManager.getInstance().getFaceId(emoKey));
            imageSpan = new GifImageSpan(drawable, RelativeImageSpan.ALIGN_CENTER);
        } catch (Resources.NotFoundException | IOException e) {
            drawable = getResources().getDrawable(PolyvFaceManager.getInstance().getFaceId(emoKey));
            imageSpan = new RelativeImageSpan(drawable, RelativeImageSpan.ALIGN_CENTER);
        }
        drawable.setBounds(0, 0, (int) (textSize * 1.5), (int) (textSize * 1.5));
        span.setSpan(imageSpan, 0, span.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        int selectionStart = et_talk.getSelectionStart();
        int selectionEnd = et_talk.getSelectionEnd();
        if (selectionStart != selectionEnd)
            et_talk.getText().replace(selectionStart, selectionEnd, span);
        else
            et_talk.getText().insert(selectionStart, span);
    }

    EssenceCourseMo.TeacherBean teacherBean;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThrea1(EventBusKeyDefine.EventBusMsgData<EssenceCourseMo.TeacherBean> data) {
        int type = data.getType();
        try {
            if (EventBusKeyDefine.EVENTBUS_LIVE_TEACHER == type) {
                if (!ObjectUtils.isEmpty(data) && !ObjectUtils.isEmpty(data.getData())) {
                    teacherBean = data.getData();
                    if (polyvChatAdapter != null) {
                        polyvChatAdapter.setTeacherName(teacherBean.getTea_name());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveAnswer(EventBusKeyDefine.EventBusMsgData<PolyvChatMessage> data) {
        int type = data.getType();
        try {
            if (EventBusKeyDefine.EVENTBUS_RECEIVE_ANSWER == type) {
                if (!ObjectUtils.isEmpty(data) && !ObjectUtils.isEmpty(data.getData())) {
                    PolyvChatMessage chatMessage = data.getData();
                    receiveAnswer(chatMessage);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    private void initView() {
        polyvChatAdapter = new PolyvQuestionAdapter(this, messages, lv_question);
        if (chatManager.getConnectStatus() == MyPolyvChatManager.ConnectStatus.LOGINSUCCESS || chatManager.getConnectStatus() == MyPolyvChatManager.ConnectStatus.RECONNECTSUCCESS)
            loginSuccess();
        if (teacherBean != null) {
            polyvChatAdapter.setTeacherName(teacherBean.getTea_name());
        }
        UserInfoBean userInfoBean = LoginHelper.getInstance().getLoginUserInfo();
        if (userInfoBean != null) {
            polyvChatAdapter.setSelfPic(userInfoBean.avatars);
        }

        polyvChatAdapter.setResendType(false);
        polyvChatAdapter.setChatManager(chatManager);
//        polyvChatAdapter.setViewPagerFragment(viewPagerFragment);
        polyvChatAdapter.setOnItemClickListener(new PolyvQuestionAdapter.OnItemClickListener() {

            @Override
            public void onClick(View view) {
                closeKeybordAndEmo(et_talk, QuestionActivity.this);
            }

            @Override
            public void atUser(PolyvChatMessage.User view) {

            }
        });
        lv_question.setAdapter(polyvChatAdapter);
        lv_question.setOnScrollListener(new AbsListView.OnScrollListener() {
            int height;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
//                        isScrollEnd = false;
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        if (lv_question.getLastVisiblePosition() == (lv_question.getCount() - 1)) {
                            View lastView = lv_question.getChildAt(lv_question.getLastVisiblePosition() - lv_question.getFirstVisiblePosition());
//                            isScrollEnd = lastView.getBottom() <= lv_question.getHeight();
                        }
//                        if (lv_question.getLastVisiblePosition() >= lastPreviewItem) {
//                            tv_read.setVisibility(View.GONE);
//                            lastPreviewItem = -1;
//                        }
                        // 判断滚动到顶部
                        if (lv_question.getFirstVisiblePosition() == 0) {
//                            loadMoreChatMessage(channelId, chatUserId);
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int itemsHeight = 0;
                for (int i = 0; i < visibleItemCount - firstVisibleItem; i++) {
                    itemsHeight += lv_question.getChildAt(i).getMeasuredHeight();
                }
                if (itemsHeight > (height = Math.max(height, lv_question.getMeasuredHeight()))) {
                    if (!lv_question.isStackFromBottom()) {
                        lv_question.setStackFromBottom(true);
                        lv_question.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                polyvChatAdapter.notifyDataSetChanged();
                            }
                        }, 300);
                    }
                } else if (visibleItemCount == totalItemCount && lv_question.isStackFromBottom()) {
                    lv_question.setStackFromBottom(false);
                    lv_question.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            polyvChatAdapter.notifyDataSetChanged();
                        }
                    }, 300);
                }
            }
        });
        lv_question.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    closeKeybordAndEmo(et_talk, QuestionActivity.this);
                }
                return false;
            }
        });
        iv_send.setOnClickListener(this);
        // 表情
        List<View> lists = new ArrayList<>();
        for (int i = 0; i < pages; i++) {
            lists.add(getEmoGridView(i));
        }
        PolyvEmoPagerAdapter emoPagerAdapter = new PolyvEmoPagerAdapter(lists, this);
        vp_emo.setAdapter(emoPagerAdapter);
        vp_emo.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                resetPageImageView();
                switch (arg0) {
                    case 0:
                        iv_page1.setSelected(true);
                        break;
                    case 1:
                        iv_page2.setSelected(true);
                        break;
                    case 2:
                        iv_page3.setSelected(true);
                        break;
                    case 3:
                        iv_page4.setSelected(true);
                        break;
                    case 4:
                        iv_page5.setSelected(true);
                        break;
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        iv_page1.setSelected(true);
        iv_page1.setOnClickListener(this);
        iv_page2.setOnClickListener(this);
        iv_page3.setOnClickListener(this);
        iv_page4.setOnClickListener(this);
        iv_page5.setOnClickListener(this);
        iv_emoswitch.setOnClickListener(this);
        btn_close.setOnClickListener(this);
        et_talk.setOnClickListener(this);
    }

    private void resetPageImageView() {
        iv_page1.setSelected(false);
        iv_page2.setSelected(false);
        iv_page3.setSelected(false);
        iv_page4.setSelected(false);
        iv_page5.setSelected(false);
    }

    /**
     * 重置表情布局的可见性
     *
     * @param isgone 以隐藏方式
     */
    public void resetEmoLayout(boolean isgone) {
        if (rl_bot.getVisibility() == View.VISIBLE || isgone) {
            rl_bot.setVisibility(View.GONE);
        } else {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            rl_bot.setVisibility(View.VISIBLE);
            closeKeybord(et_talk, this);
            et_talk.requestFocus();
        }
    }

    // 发送提问信息
    private void sendMsg() {
        String msg = et_talk.getText().toString();
        List<String> badwords = PolyvChatFragment.getBadwords();
        if (msg.trim().length() == 0) {
            Toast.makeText(this, "发送信息不能为空!", Toast.LENGTH_SHORT).show();
            return;
        }
        for (int i = 0; i < badwords.size(); i++) {
            if (msg.contains(badwords.get(i))) {
                Toast.makeText(this, "抱歉，您的言论包含敏感词，请修改后发送", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        final PolyvChatMessage message = new PolyvChatMessage(msg);
        polyvChatAdapter.add(message,true);
        final int lastPosition = polyvChatAdapter.getCount() - 1;
        if (chatManager.sendQuestionMsg(message)) {
            polyvChatAdapter.updateStatusView(true, false, lastPosition);
        } else {
            polyvChatAdapter.updateStatusView(false, false, lastPosition);
        }
        lv_question.setSelection(lastPosition);
        et_talk.setText("");
        closeKeybordAndEmo(et_talk, this);
    }

//    public boolean onKeyDown(int keycode, KeyEvent event) {
//        if (keycode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//            // 右键处理
//            moveTaskToBack(true);
//        }
//        return true;
//    }

    //关闭键盘和表情布局
    private void closeKeybordAndEmo(EditText mEditText, Context mContext) {
        closeKeybord(mEditText, mContext);
        resetEmoLayout(true);
    }

    //关闭键盘
    private void closeKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    //表情布局是否可见
    public boolean emoLayoutIsVisible() {
        if (!isInitialized)
            return false;
        return rl_bot.getVisibility() == View.VISIBLE;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            closeKeybordAndEmo(et_talk, this);
    }


    @Override
    public void onStop() {
        super.onStop();
        // 隐藏表情布局
        resetEmoLayout(true);
        // 清除焦点
        et_talk.clearFocus();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_send:
                sendMsg();
                break;
            case R.id.iv_page1:
                vp_emo.setCurrentItem(0);
                break;
            case R.id.iv_page2:
                vp_emo.setCurrentItem(1);
                break;
            case R.id.iv_page3:
                vp_emo.setCurrentItem(2);
                break;
            case R.id.iv_page4:
                vp_emo.setCurrentItem(3);
                break;
            case R.id.iv_page5:
                vp_emo.setCurrentItem(4);
                break;
            case R.id.iv_emoswitch:
                resetEmoLayout(false);
                break;
            case R.id.btn_close:
                hideSoftInput();
                finish();
//                moveTaskToBack(fa);
                break;
            case R.id.et_talk:
                resetEmoLayout(true);
                break;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        polyvChatAdapter.clear();
        unregistorEventBus();
    }

//    private boolean isRealFinish=false;
//    @Override
//    public void finish() {
//        if (isRealFinish) {
//            super.finish();
//            this.moveTaskToBack(true);
//        } else {
//            this.moveTaskToBack(false);
//        }
//        isRealFinish = false;
//    }

    @Override
    public void finish() {
        super.finish();
        //关闭窗体动画显示
        this.overridePendingTransition(0, R.anim.activity_close);
    }
}

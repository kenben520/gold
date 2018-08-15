package com.lingxi.preciousmetal.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources.NotFoundException;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.ChatClient;
import com.hyphenate.chat.ChatManager;
import com.hyphenate.chat.Conversation;
import com.hyphenate.chat.Message;
import com.hyphenate.helpdesk.easeui.UIProvider;
import com.hyphenate.helpdesk.easeui.util.Config;
import com.hyphenate.helpdesk.easeui.widget.MessageList;
import com.hyphenate.helpdesk.model.AgentIdentityInfo;
import com.hyphenate.helpdesk.model.QueueIdentityInfo;
import com.hyphenate.helpdesk.model.VisitorInfo;
import com.lingxi.common.base.BaseFragment;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.common.EventBusKeyDefine;
import com.lingxi.preciousmetal.ui.activity.kefu.KefuHelper;
import com.lingxi.preciousmetal.ui.activity.kefu.KefuMessageHelper;
import com.lingxi.preciousmetal.common.KefuMessageConstant;
import com.lingxi.preciousmetal.ui.adapter.KefuEmoGridViewAdapter;
import com.lingxi.preciousmetal.ui.adapter.KefuMessageAdapter;
import com.lingxi.preciousmetal.ui.adapter.PolyvEmoPagerAdapter;
import com.lingxi.preciousmetal.ui.activity.kefu.KefuFaceManager;
import com.lingxi.preciousmetal.ui.listener.KeyBoardListener;
import com.lingxi.preciousmetal.ui.widget.MsgEditText;
import com.lingxi.preciousmetal.util.AndroidUtils;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageSpan;
import pl.droidsonroids.gif.RelativeImageSpan;

/**
 * 聊天Fragment
 */
public class KefuFragment extends BaseFragment implements OnClickListener, ChatManager.MessageListener {
    private boolean isInitialized;

    // fragmentView
    private View view;
    // 聊天listView
    private ListView lv_chat;
    //    // 聊天listView的适配器
//    private PolyvChatAdapter polyvChatAdapter;
//    // 聊天信息列表集合
//    private LinkedList<PolyvChatMessage> messages;
    //listview是否滚到最后
//    private boolean isScrollEnd = true;
//    //已看聊天信息的最后一个item
//    private int lastPreviewItem = -1;
    // 信息编辑控件
    private MsgEditText et_talk;
//    // 聊天管理类
//    private MyPolyvChatManager chatManager;
//    private String nickName;
//    // 登录聊天室的学员id，频道所属的用户id，频道id
//    private String chatUserId, userId, channelId;

    // 表情ViewPager
    private ViewPager vp_emo;
    // 表情的父布局
    private RelativeLayout rl_bot;
    // 表情页的下方圆点...，表情开关，发送按钮
    private ImageView iv_page1, iv_page2, iv_page3, iv_page4, iv_page5, iv_emoswitch;
    private View layout_emoswitch, layout_pic;
    private TextView iv_send;
    // 表情的文本长度
    private int emoLength;
    // 列
    private int columns = 7;
    // 行
    private int rows = 3;
    // 页
    private int pages = 2;
    //    // 获取的历史记录条数
//    private int messageCount = 20;
//    // 获取历史记录的次数
//    private int count = 0;
    protected int pagesize = 20;
    protected SwipeRefreshLayout swipeRefreshLayout;

    /**
     * 传入fragment的参数
     */
    protected Bundle fragmentArgs;
    protected Conversation conversation;
    protected String toChatUsername;
    protected boolean showUserNick;
    private VisitorInfo visitorInfo;
    private AgentIdentityInfo agentIdentityInfo;
    private QueueIdentityInfo queueIdentityInfo;
    KefuMessageAdapter messageAdapter;
    private boolean isMessageListInited;
    protected boolean isloading;
    protected boolean haveMoreData = true;

    public static KefuFragment newInstance() {
        KefuFragment fragmentCommon = new KefuFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Config.EXTRA_VISITOR_INFO, KefuMessageHelper.createVisitorInfo());
        bundle.putParcelable(Config.EXTRA_QUEUE_INFO, KefuMessageHelper.createQueueIdentity(null));//暂无技能组
        bundle.putBoolean(Config.EXTRA_SHOW_NICK, true);
        bundle.putString(Config.EXTRA_SERVICE_IM_NUMBER, KefuMessageConstant.DEFAULT_CUSTOMER_ACCOUNT);
        fragmentCommon.setArguments(bundle);
        return fragmentCommon;
    }


//    // 获取聊天室管理类
//    public MyPolyvChatManager getChatManager() {
//        return this.chatManager;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.kefu_fragment_chat, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
        registorEventBus();
    }

    //根据索引获取表情页的view
    private View getEmoGridView(int position) {
        GridView gv_emo = (GridView) LayoutInflater.from(getContext()).inflate(R.layout.polyv_gridview_emo, null)
                .findViewById(R.id.gv_emo);
        List<String> lists = new ArrayList<String>();
        lists.addAll(KefuFaceManager.getInstance().getFaceMap().keySet());
        int endIndex = (position + 1) * (columns * rows);
        int remainingIndex = lists.size() - (position) * (columns * rows);
        final List<String> elists = lists.subList(position * (columns * rows), remainingIndex > endIndex ? endIndex : lists.size());
        KefuEmoGridViewAdapter emoGridViewAdapter = new KefuEmoGridViewAdapter(elists, getContext());
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
        lv_chat = (ListView) view.findViewById(R.id.lv_chat);
        iv_send = (TextView) view.findViewById(R.id.iv_send);
        layout_pic = (View) view.findViewById(R.id.layout_pic);
        et_talk = (MsgEditText) view.findViewById(R.id.et_talk);
        et_talk.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1500)});
        vp_emo = (ViewPager) view.findViewById(R.id.vp_emo);
        iv_page1 = (ImageView) view.findViewById(R.id.iv_page1);
        iv_page2 = (ImageView) view.findViewById(R.id.iv_page2);
        iv_page3 = (ImageView) view.findViewById(R.id.iv_page3);
        iv_page4 = (ImageView) view.findViewById(R.id.iv_page4);
        iv_page5 = (ImageView) view.findViewById(R.id.iv_page5);
        iv_emoswitch = (ImageView) view.findViewById(R.id.iv_emoswitch);
        layout_emoswitch = (View) view.findViewById(R.id.layout_emoswitch);
        layoutRoot = view.findViewById(R.id.layout_root);
        mInputParent = view.findViewById(R.id.fl_bot);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(com.hyphenate.helpdesk.R.id.chat_swipe_layout);
//        swipeRefreshLayout.setColorSchemeResources(com.hyphenate.helpdesk.R.color.holo_blue_bright, com.hyphenate.helpdesk.R.color.holo_green_light,
//                com.hyphenate.helpdesk.R.color.holo_orange_light, com.hyphenate.helpdesk.R.color.holo_red_light);
        rl_bot = (RelativeLayout) view.findViewById(R.id.rl_bot);
//        messages = new LinkedList<>();
    }


    public com.hyphenate.chat.Message getItem(int position) {
        return messageAdapter.getItem(position);
    }

    /**
     * 刷新页面,并跳至给定position
     *
     * @param position
     */
    public void refreshSeekTo(int position) {
        if (messageAdapter != null) {
            messageAdapter.refreshSeekTo(position);
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
            if (KefuFaceManager.getInstance().getFaceId(regex) != -1)
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
            drawable = new GifDrawable(getResources(), KefuFaceManager.getInstance().getFaceId(emoKey));
            imageSpan = new GifImageSpan(drawable, RelativeImageSpan.ALIGN_CENTER);
        } catch (NotFoundException | IOException e) {
            drawable = getResources().getDrawable(KefuFaceManager.getInstance().getFaceId(emoKey));
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

    private final static String MASK_STR = "@";

    public void addSpan(String atStr, String userId) {
        et_talk.addAtSpan(MASK_STR, atStr, userId);
        Log.d("ddd", et_talk.getUserIdString());
    }

    //listview是否滚到最后
    private boolean isScrollEnd = true;
    //已看聊天信息的最后一个item
    private int lastPreviewItem = -1;

    protected void setRefreshLayoutListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        if (getActivity() == null || getActivity().isFinishing()) {
                            return;
                        }
                        if (lv_chat.getFirstVisiblePosition() == 0 && !isloading && haveMoreData) {
                            List<com.hyphenate.chat.Message> messages = null;
                            try {
                                messages = conversation.loadMessages(getItem(0).messageId(),
                                        pagesize);
                            } catch (Exception e1) {
                                swipeRefreshLayout.setRefreshing(false);
                                return;
                            }
                            if (messages != null && messages.size() > 0) {
                                refreshSeekTo(messages.size() - 1);
                                if (messages.size() != pagesize) {
                                    haveMoreData = false;
                                }
                            } else {
                                haveMoreData = false;
                            }

                            isloading = false;

                        } else {
//                            Toast.makeText(getActivity(), getResources().getString(com.hyphenate.helpdesk.R.string.no_more_messages),
//                                    Toast.LENGTH_SHORT).show();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 600);
            }
        });
    }

    @SuppressWarnings("deprecation")
    private void initView() {
//        // 获取聊天室历史信息
//        loadMoreChatMessage(channelId, chatUserId);

//        polyvChatAdapter = new PolyvChatAdapter(getContext(), messages, lv_chat);
//        polyvChatAdapter.setSelfNickName(nickName);
//        UserInfoBean userInfoBean = LoginHelper.getInstance().getLoginUserInfo();
//        if (userInfoBean != null) {//说明不是游客
//            polyvChatAdapter.setSelfPic(userInfoBean.avatars);
//        }else{//游客
//            polyvChatAdapter.setSelfPic(CommonParam.POLY_CHAT_TOURIST_DEFAULT_AVATAR);
//        }
//        polyvChatAdapter.setResendType(true);
//        polyvChatAdapter.setChatManager(chatManager);
//        polyvChatAdapter.setOnItemClickListener(new OnItemClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                closeKeybordAndEmo(et_talk, getContext());
//            }
//
//            @Override
//            public void atUser(PolyvChatMessage.User user) {
//
//            }
//        });
//        lv_chat.setAdapter(polyvChatAdapter);
//        lv_chat.setOnScrollListener(new AbsListView.OnScrollListener() {
//            int height;
//
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                switch (scrollState) {
//                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
//                        isScrollEnd = false;
//                        break;
//                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
//                        if (lv_chat.getLastVisiblePosition() == (lv_chat.getCount() - 1)) {
//                            View lastView = lv_chat.getChildAt(lv_chat.getLastVisiblePosition() - lv_chat.getFirstVisiblePosition());
//                            isScrollEnd = lastView.getBottom() <= lv_chat.getHeight();
//                        }
//                        if (lv_chat.getLastVisiblePosition() >= lastPreviewItem) {
//                            lastPreviewItem = -1;
//                        }
//                        // 判断滚动到顶部
//                        if (lv_chat.getFirstVisiblePosition() == 0) {
//                        }
//                        break;
//                }
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                int itemsHeight = 0;
//                for (int i = 0; i < visibleItemCount - firstVisibleItem; i++) {
//                    itemsHeight += lv_chat.getChildAt(i).getMeasuredHeight();
//                }
//                if (itemsHeight > (height = Math.max(height, lv_chat.getMeasuredHeight()))) {
//                    if (!lv_chat.isStackFromBottom()) {
////                        lv_chat.setStackFromBottom(true);
////                        lv_chat.postDelayed(new Runnable() {
////                            @Override
////                            public void run() {
////                                polyvChatAdapter.notifyDataSetChanged();
////                            }
////                        }, 300);
//                    }
//                } else if (visibleItemCount == totalItemCount && lv_chat.isStackFromBottom()) {
//                    lv_chat.setStackFromBottom(false);
////                    lv_chat.postDelayed(new Runnable() {
////                        @Override
////                        public void run() {
////                            polyvChatAdapter.notifyDataSetChanged();
////                        }
////                    }, 300);
//                }
//            }
//        });
        lv_chat.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    closeKeybordAndEmo(et_talk, getContext());
                }
                return false;
            }
        });
        iv_send.setOnClickListener(this);
        layout_pic.setOnClickListener(this);
        // 表情
        List<View> lists = new ArrayList<>();
        for (int i = 0; i < pages; i++) {
            lists.add(getEmoGridView(i));
        }
        PolyvEmoPagerAdapter emoPagerAdapter = new PolyvEmoPagerAdapter(lists, getContext());
        vp_emo.setAdapter(emoPagerAdapter);
        vp_emo.setOnPageChangeListener(new OnPageChangeListener() {

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
        layout_emoswitch.setOnClickListener(this);
        et_talk.setOnClickListener(this);

    }

    ChatManager.VisitorWaitListener visitorWaitListener = new ChatManager.VisitorWaitListener() {
        @Override
        public void waitCount(final int num) {
            if (getActivity() == null) {
                return;
            }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                        if (num > 0){
//                            tvTipWaitCount.setVisibility(View.VISIBLE);
//                            tvTipWaitCount.setText(getString(com.hyphenate.helpdesk.R.string.current_wait_count, num));
//                        }else{
//                            tvTipWaitCount.setVisibility(View.GONE);
//                        }
                }
            });
        }
    };


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
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            rl_bot.setVisibility(View.VISIBLE);
            closeKeybord(et_talk, getContext());
            et_talk.requestFocus();
        }
    }


    //关闭键盘和表情布局
    public void closeKeybord() {
        closeKeybord(et_talk, getContext());
    }


    //关闭键盘和表情布局
    public void closeKeybordAndEmo(EditText mEditText, Context mContext) {
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
//        return rl_bot.getVisibility() == View.VISIBLE && viewPagerFragment.getCurrentIndex() == 0;
        return rl_bot.getVisibility() == View.VISIBLE;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!isInitialized) {
            isInitialized = true;
            fragmentArgs = getArguments();
            // IM服务号
            toChatUsername = fragmentArgs.getString(Config.EXTRA_SERVICE_IM_NUMBER);
            // 是否显示用户昵称
            showUserNick = fragmentArgs.getBoolean(Config.EXTRA_SHOW_NICK, false);
            //指定技能组
            queueIdentityInfo = fragmentArgs.getParcelable(Config.EXTRA_QUEUE_INFO);
            //指定客服
            agentIdentityInfo = fragmentArgs.getParcelable(Config.EXTRA_AGENT_INFO);
            visitorInfo = fragmentArgs.getParcelable(Config.EXTRA_VISITOR_INFO);
            ChatClient.getInstance().chatManager().bindChat(toChatUsername);
            findIdAndNew();
            initView();
        }
    }

    private KeyBoardListener mKeyBoardListener;
    View layoutRoot;
    View mInputParent;

    boolean isSetUpView = false;

    protected synchronized void setUpView() {
        if (isSetUpView || !isInitialized) return;
        if (ChatClient.getInstance().isLoggedInBefore()) {
            isSetUpView = true;
            onConversationInit();
            onMessageListInit();
            setRefreshLayoutListener();
        } else {
            KefuHelper.getInstance().login(new KefuHelper.OnLoginListener() {
                @Override
                public void loginSuccess() {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setUpView();
                            }
                        });
                    }
                }

                @Override
                public void loginFail() {
                }
            });
        }
    }

    protected void onMessageListInit() {
        initMessageAdapter();
        setListItemClickListener();
        isMessageListInited = true;
    }

    protected void setListItemClickListener() {
        messageAdapter.setItemClickListener(new MessageList.MessageListItemClickListener() {

            @Override
            public void onUserAvatarClick(String username) {
            }

            @Override
            public void onResendClick(final Message message) {
//                new AlertDialog(getActivity(), com.hyphenate.helpdesk.R.string.resend, com.hyphenate.helpdesk.R.string.confirm_resend, null, new AlertDialog.AlertDialogUser() {
//                    @Override
//                    public void onResult(boolean confirmed, Bundle bundle) {
//                        if (!confirmed) {
//                            return;
//                        }
                ChatClient.getInstance().chatManager().resendMessage(message);
//                    }
//                }, true).show();
            }

            @Override
            public void onBubbleLongClick(Message message) {
            }

            @Override
            public boolean onBubbleClick(Message message) {
                return false;
            }
        });
    }


    public void initMessageAdapter() {
        messageAdapter = new KefuMessageAdapter(getContext(), toChatUsername, lv_chat);
        messageAdapter.setShowAvatar(false);
        messageAdapter.setShowUserNick(showUserNick);
        messageAdapter.setMyBubbleBg(null);
        messageAdapter.setOtherBuddleBg(null);
        messageAdapter.setCustomChatRowProvider(null);
        messageAdapter.setOnItemClickListener(new KefuMessageAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view) {
                closeKeybordAndEmo(et_talk, getContext());
            }
        });

        // 设置adapter显示消息
        lv_chat.setAdapter(messageAdapter);

        refreshSelectLast();
    }


    // 发送消息方法
    //=============================================

    // 发送聊天信息
    protected void sendTextMessage() {
        String msg = et_talk.getText().toString();
        if (msg.trim().length() == 0) {
            Toast.makeText(getContext(), "发送信息不能为空!", Toast.LENGTH_SHORT).show();
            return;
        }
        com.hyphenate.chat.Message message = com.hyphenate.chat.Message.createTxtSendMessage(msg, toChatUsername);
        attachMessageAttrs(message);
        ChatClient.getInstance().chatManager().sendMessage(message);
        refreshSelectLast();
        et_talk.setText("");
        closeKeybordAndEmo(et_talk, getContext());
    }

    public void attachMessageAttrs(com.hyphenate.chat.Message message) {
        if (visitorInfo != null) {
            message.addContent(visitorInfo);
        }
        if (queueIdentityInfo != null) {
            message.addContent(queueIdentityInfo);
        }
        if (agentIdentityInfo != null) {
            message.addContent(agentIdentityInfo);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            closeKeybordAndEmo(et_talk, getContext());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
        // 隐藏表情布局
        resetEmoLayout(true);
        // 清除焦点
        et_talk.clearFocus();
        // background
        ChatClient.getInstance().chatManager().removeMessageListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpView();
        if (isMessageListInited)
            refresh();
        // register the event listener when enter the foreground
        ChatClient.getInstance().chatManager().addMessageListener(this);
    }

    /**
     * 刷新列表
     */
    public void refresh() {
        if (messageAdapter != null) {
            messageAdapter.refresh();
        }
    }

    private static final int REQUEST_CODE_UPLOAD_IMAGE = 18;

    private void selectImage(final int requestCode) {
        AndroidUtils.getInstance().selectImage(this, requestCode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_send:
                if (isSetUpView) {
                    sendTextMessage();
                } else {
                }
                break;
            case R.id.layout_pic:
                if (isSetUpView) {
                    selectImage(REQUEST_CODE_UPLOAD_IMAGE);
//                    selectPicFromLocal();
                }
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
            case R.id.layout_emoswitch:
                resetEmoLayout(false);
                break;
            case R.id.et_talk:
                resetEmoLayout(true);
                break;
        }
    }

    protected static final int REQUEST_CODE_LOCAL = 2;

    /**
     * 从图库获取图片
     */
    protected void selectPicFromLocal() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");

        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, REQUEST_CODE_LOCAL);
    }

    @SuppressLint("NewApi")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_LOCAL) { // 发送本地图片
                if (data != null) {
                    Uri selectedImage = data.getData();
                    if (selectedImage != null) {
                        sendPicByUri(selectedImage);
                    }
                }
            } else if (requestCode == REQUEST_CODE_UPLOAD_IMAGE) {
                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                String cropPicPath = path.get(0);
//                    cropPicPath = data.getStringExtra(REQUEST_CODE_UPLOAD_IMAGE);
                if (cropPicPath != null) {
                    Uri selectedImage = Uri.parse(cropPicPath);
                    if (selectedImage != null) {
                        sendPicByUri(selectedImage);
                    }
                } else {
                    ToastUtils.showShort("请重新选择图片");
                }
            }
        }
    }

        /**
         * 根据图库图片uri发送图片
         *
         * @param selectedImage
         */

    protected void sendPicByUri(Uri selectedImage) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            cursor = null;

            if (picturePath == null || picturePath.equals("null")) {
                Toast toast = Toast.makeText(getActivity(), com.hyphenate.helpdesk.R.string.cant_find_pictures, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }
            sendImageMessage(picturePath);
        } else {
            File file = new File(selectedImage.getPath());
            if (!file.exists()) {
                Toast toast = Toast.makeText(getActivity(), com.hyphenate.helpdesk.R.string.cant_find_pictures, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;

            }
            sendImageMessage(file.getAbsolutePath());
        }

    }


    protected void sendImageMessage(String imagePath) {
        if (TextUtils.isEmpty(imagePath)) {
            return;
        }
        File imageFile = new File(imagePath);
        if (!imageFile.exists()) {
            return;
        }

        com.hyphenate.chat.Message message = com.hyphenate.chat.Message.createImageSendMessage(imagePath, false, toChatUsername);
        attachMessageAttrs(message);
        ChatClient.getInstance().chatManager().sendMessage(message);
        refreshSelectLastDelay(MessageList.defaultDelay);
    }

    public void refreshSelectLastDelay(long delay) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (messageAdapter != null) {
                    messageAdapter.refreshSelectLast();
                }
            }
        }, delay);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unregistorEventBus();
        ChatClient.getInstance().chatManager().unbindChat();
        ChatClient.getInstance().chatManager().removeVisitorWaitListener(visitorWaitListener);
    }


    protected void onConversationInit() {
        // 获取当前conversation对象
        conversation = ChatClient.getInstance().chatManager().getConversation(toChatUsername);
        if (conversation != null) {
            // 把此会话的未读数置为0
            conversation.markAllMessagesAsRead();
            final List<com.hyphenate.chat.Message> msgs = conversation.getAllMessages();
            int msgCount = msgs != null ? msgs.size() : 0;
            if (msgCount < conversation.getAllMsgCount() && msgCount < pagesize) {
                String msgId = null;
                if (msgs != null && msgs.size() > 0) {
                    msgId = msgs.get(0).messageId();
                }
                conversation.loadMessages(msgId, pagesize - msgCount);
            }
        }

    }

    @Override
    public void onMessage(List<com.hyphenate.chat.Message> msgs) {
        for (com.hyphenate.chat.Message message : msgs) {
            String username = null;
            username = message.from();

            // 如果是当前会话的消息，刷新聊天页面
            if (username != null && username.equals(toChatUsername)) {
                refreshSelectLast();
                // 声音和震动提示有新消息
                UIProvider.getInstance().getNotifier().viberateAndPlayTone(message);
            } else {
                // 如果消息不是和当前聊天ID的消息
                UIProvider.getInstance().getNotifier().onNewMsg(message);
            }
        }

    }

    @Override
    public void onCmdMessage(List<com.hyphenate.chat.Message> msgs) {

    }


    @Override
    public void onMessageStatusUpdate() {
        refreshSelectLast();
    }

    @Override
    public void onMessageSent() {
        refreshSelectLast();
    }

    /**
     * 刷新列表，并且跳至最后一个item
     */
    public void refreshSelectLast() {
        if (messageAdapter != null) {
            messageAdapter.refreshSelectLast();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThrea1(EventBusKeyDefine.EventBusMsgData<Integer> data) {
        int type = data.getType();
        try {
            if (EventBusKeyDefine.EVENTBUS_KEFU_VIEW_FRESH == type) {
                setUpView();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

package com.lingxi.preciousmetal.ui.fragment.analyzeplate;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources.NotFoundException;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easefun.polyvsdk.live.chat.PolyvChatManager;
import com.easefun.polyvsdk.live.chat.PolyvChatMessage;
import com.easefun.polyvsdk.live.chat.api.PolyvChatBadword;
import com.easefun.polyvsdk.live.chat.api.PolyvChatHistory;
import com.easefun.polyvsdk.live.chat.api.listener.PolyvChatBadwordListener;
import com.easefun.polyvsdk.live.chat.api.listener.PolyvChatHistoryListener;
import com.lingxi.biz.domain.responseMo.EssenceCourseMo;
import com.lingxi.common.base.BaseFragment;
import com.lingxi.polylib.util.PolyvFaceManager;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.common.CommonParam;
import com.lingxi.preciousmetal.common.EventBusKeyDefine;
import com.lingxi.preciousmetal.domain.UserInfoBean;
import com.lingxi.preciousmetal.login.LoginHelper;
import com.lingxi.preciousmetal.ui.activity.QuestionActivity;
import com.lingxi.preciousmetal.ui.adapter.NewsFrPagerAdapter;
import com.lingxi.preciousmetal.ui.adapter.PolyvChatAdapter;
import com.lingxi.preciousmetal.ui.adapter.PolyvChatAdapter.OnItemClickListener;
import com.lingxi.preciousmetal.ui.adapter.PolyvEmoGridViewAdapter;
import com.lingxi.preciousmetal.ui.adapter.PolyvEmoPagerAdapter;
import com.lingxi.preciousmetal.ui.jpush.MyPolyvChatManager;
import com.lingxi.preciousmetal.ui.listener.KeyBoardListener;
import com.lingxi.preciousmetal.ui.widget.MsgEditText;
import com.lingxi.preciousmetal.util.utilCode.ObjectUtils;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageSpan;
import pl.droidsonroids.gif.RelativeImageSpan;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

//import com.bumptech.glide.load.resource.drawable.Glideawable;

/**
 * 聊天Fragment
 */
public class PolyvChatFragment extends BaseFragment implements OnClickListener {
    private boolean isInitialized;
    private static final int DISCONNECT = 5;
    private static final int RECEIVEMESSAGE = 6;
    private static final int LOGINING = 12;
    private static final int LOGINSUCCESS = 13;
    private static final int RECONNECTING = 19;
    private static final int RECONNECTSUCCESS = 30;
    private static final int GETHISTORYSUCCESS = 31;
    private static final int GETHISTORYFAIL = 32;
    private static final int RECEIVEANSWER = 33;
    private static final int BULLETIN= 3;

    // fragmentView
    private View view;
    // 聊天listView
    private ListView lv_chat;
    // 聊天listView的适配器
    private PolyvChatAdapter polyvChatAdapter;
    // 聊天信息列表集合
    private LinkedList<PolyvChatMessage> messages;
    //    public static LinkedList<PolyvChatMessage> questionMessages=new LinkedList<>();
    public static HashMap<String, LinkedList<PolyvChatMessage>> questionMessagesHmp = new HashMap<>();
    //listview是否滚到最后
    private boolean isScrollEnd = true;
    //已看聊天信息的最后一个item
    private int lastPreviewItem = -1;
    // 聊天室状态
    private TextView tv_status, tv_read;
    // 信息编辑控件
    private MsgEditText et_talk;
    // 聊天管理类
    private MyPolyvChatManager chatManager;
    private String nickName;
    // 登录聊天室的学员id，频道所属的用户id，频道id
    private String chatUserId, userId, channelId;
    private Animation collapseAnimation;

    // 表情ViewPager
    private ViewPager vp_emo;
    // 表情的父布局
    private RelativeLayout rl_bot;
    // 表情页的下方圆点...，表情开关，发送按钮
    private ImageView iv_page1, iv_page2, iv_page3, iv_page4, iv_page5, iv_emoswitch;
    private View layout_emoswitch;
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
    // 加载更多按钮
    private TextView tv_loadmore;
    private View layout_ask_teacher;
    // at老师
    private TextView tv_at_teacher, tv_tips_top;
    private View question_red_dot;
    private View layout_tips_top, btn_close_tips, layout_only_teacher,iv_only_teacher;
    // 获取聊天室历史信息接口
    private PolyvChatHistory chatHistory;
    // 获取严禁词列表接口
    private PolyvChatBadword chatBadword;
    //    private PolyvChatQuiz polyvChatQuiz;
    // 获取的历史记录条数
    private int messageCount = 20;
    // 获取历史记录的次数
    private int count = 0;
    // 严禁词列表
    public static List<String> badwords = new ArrayList<>();
    private String patternExpression;
    NewsFrPagerAdapter pager;

    private boolean isAtMe(String msg) {
        Pattern p = Pattern.compile(patternExpression);
        Matcher m = p.matcher(msg);
        return m.find();
    }

    private boolean isAtTeacher(String msg) {
        if (teacherBean == null) return false;
        Pattern p = Pattern.compile("@" + teacherBean.getTea_name() + " ");
        Matcher m = p.matcher(msg);
        return m.find();
    }




    // 接收老师对自己的回复时调用
    public void receiveAnswer(PolyvChatMessage chatMessage) {
        if (!isInitialized)
            return;
//        Message message = handler.obtainMessage();
//        message.obj = chatMessage;
//        message.what = RECEIVEANSWER;
//        handler.sendMessage(message);
        question_red_dot.setVisibility(VISIBLE);
        chatMessage.setChatType(PolyvChatMessage.CHATTYPE_RECEIVE);
        chatMessage.setValues(new String[]{chatMessage.getContent()});
        questionMessagesHmp.get(nickName).add(chatMessage);
        EventBus.getDefault().post(new EventBusKeyDefine.EventBusMsgData<PolyvChatMessage>(EventBusKeyDefine.EVENTBUS_RECEIVE_ANSWER, chatMessage));
    }
    private KeyBoardListener mKeyBoardListener;

    private int atMePostion = 0;
    private boolean ban = false;
    private boolean kick = false;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (getContext() == null)
                return;
            boolean needUnread=true;
            boolean isQuestion=false;
            switch (msg.what) {
//                case BULLETIN:
//                    final PolyvChatMessage chatMessage2 = (PolyvChatMessage) msg.obj;
//                    chatMessage2.setValues(new String[]{chatMessage2.getContent()});
//                    break;
                case RECEIVEANSWER:
                    PolyvChatMessage chatMessageAs = (PolyvChatMessage) msg.obj;
                    // 由于显示信息的逻辑与聊天室接收信息一样，所以把chatType改为CHATTYPE_RECEIVE
                    chatMessageAs.setChatType(PolyvChatMessage.CHATTYPE_RECEIVE);
                    chatMessageAs.setValues(new String[]{chatMessageAs.getContent()});
                    polyvChatAdapter.add(chatMessageAs);
                    break;
                case RECEIVEMESSAGE:
                    boolean syncAdd = true;
                    final PolyvChatMessage chatMessage = (PolyvChatMessage) msg.obj;
                    if (chatMessage.getChatType() == PolyvChatMessage.CHATTYPE_RECEIVE) { // 发言信息
//                        viewPagerFragment.getDanmuFragment().sendDanmaku(chatMessage.getValues()[0]);
                        //at提示
                        String msgStr = String.valueOf(chatMessage.getValues()[0])+" ";
                        if (isAtMe(msgStr)) {
                            String nick="";
                            if(!ObjectUtils.isEmpty(chatMessage.getUser()))nick=chatMessage.getUser().getNick();
                            String replaceStr=msgStr.replaceAll("@.*?\\s","");
                            atMePostion = lv_chat.getCount();
                            tv_tips_top.setText(nick+"@我:"+replaceStr);
                            layout_tips_top.setVisibility(View.VISIBLE);
                        }
                        if (!isScrollEnd) {
                            tv_read.setText("有更多的信息，点击查看");
                            if (tv_read.getVisibility() == View.GONE) {
                                lastPreviewItem = lv_chat.getCount();
                                tv_read.setVisibility(View.VISIBLE);
                            }
                        }
                    } else if (chatMessage.getChatType() == PolyvChatMessage.CHATTYPE_RECEIVE_QUESTION) { // 提问/回复信息
                        if (PolyvChatMessage.EVENT_T_ANSWER.equals(chatMessage.getEvent())) {
                            if (chatMessage.getS_userId().equals(chatManager.getUserId())) {
                                isQuestion=true;
                                receiveAnswer(chatMessage);
//                                viewPagerFragment.getQuestionFragment().receiveAnswer(chatMessage);
//                                String content = chatMessage.getContent();
//                                if (StringUtil.isEmpty(content)) return;
//                                atMePostion = lv_chat.getCount();
//                                tv_tips_top.setText(content);
//                                layout_tips_top.setVisibility(View.VISIBLE);
//                                if (!isScrollEnd) {
//                                    tv_read.setText("有更多的信息，点击查看");
//                                    if (tv_read.getVisibility() == View.GONE) {
//                                        lastPreviewItem = lv_chat.getCount();
//                                        tv_read.setVisibility(View.VISIBLE);
//                                    }
//                                }
//                                if (syncAdd && !StringUtil.isEmpty(content)) {
//                                    polyvChatAdapter.add(chatMessage);
//                                }
//                                return;
                            }
                        }
                    } else if (chatMessage.getChatType() == PolyvChatMessage.CHATTYPE_RECEIVE_NOTICE) { // 其他的通知信息
                        switch (chatMessage.getEvent()) {
                            // 用户被踢，不能发送信息，再次连接聊天室可恢复
                            case PolyvChatMessage.EVENT_KICK:
                                String nick = chatMessage.getUser().getNick();
                                if (chatMessage.getUser().getUid().equals(chatManager.getUid())) {
                                    nick = nick + "(我)";
                                    kick = true;
                                    chatMessage.setValues(new String[]{"你被管理员踢出房间了"});
                                    //viewPagerFragment.getOnlineListFragment().kickOrShield(true);
                                }
                                // 这里需要自定义显示的信息
                                break;
                            // 用户被禁言，不能发送信息，不能再次连接聊天室，需要在直播后台恢复
                            case PolyvChatMessage.EVENT_BANIP:
                                List<PolyvChatMessage.User> banLists = chatMessage.banLists;
                                StringBuilder stringBuilder = new StringBuilder();
                                for (PolyvChatMessage.User user : banLists) {
                                    String nickName = user.getNick();
                                    if (user.getUid().equals(chatManager.getUid())) {
                                        nickName = nickName + "(我)";
                                        ban = true;
                                        //viewPagerFragment.getOnlineListFragment().kickOrShield(false);
                                    }
                                    stringBuilder.append(nickName).append("、");
                                }
                                // 这里需要自定义显示的信息
                                chatMessage.setValues(new String[]{"你被管理员禁言了"});
                                break;
                            // 取消禁言
                            case PolyvChatMessage.EVENT_UNSHIELD:
                                List<PolyvChatMessage.User> unBanLists = chatMessage.banLists;
                                StringBuilder stringBuilder1 = new StringBuilder();
                                for (PolyvChatMessage.User user : unBanLists) {
                                    String nickName = user.getNick();
                                    if (user.getUid().equals(chatManager.getUid())) {
                                        nickName = nickName + "(我)";
                                        ban = false;
                                        //viewPagerFragment.getOnlineListFragment().unshield();
                                    }
                                    stringBuilder1.append(nickName).append("、");
                                }
                                // 这里需要自定义显示的信息
                                chatMessage.setValues(new String[]{"你被管理员取消禁言"});
                                break;
                            // 聊天室关闭时，不能接收或发送信息
                            case PolyvChatMessage.EVENT_CLOSEROOM:
//                                boolean isClose = chatMessage.getValue().isClosed();
//                                if (isClose)
//                                    chatMessage.setValues(new String[]{"聊天室关闭"});
//                                else
//                                    chatMessage.setValues(new String[]{"聊天室开启"});
                                break;
                            // 公告
                            case PolyvChatMessage.EVENT_GONGGAO:
                                chatMessage.setValues(new String[]{chatMessage.getContent()});
                                break;
                            // 送花事件
                            case PolyvChatMessage.EVENT_FLOWERS:
//                                String sendNick = chatMessage.getUser().getNick();
//                                SpannableStringBuilder span = new SpannableStringBuilder(sendNick + " 赠送了鲜花p");
//                                Drawable drawable = getResources().getDrawable(R.drawable.polyv_gift_flower);
//                                int textSize = (int) getResources().getDimension(R.dimen.tv_textsize);
//                                drawable.setBounds(0, 0, textSize * 2, textSize * 2);
//                                span.setSpan(new RelativeImageSpan(drawable, RelativeImageSpan.ALIGN_CENTER), span.length() - 1, span.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                                chatMessage.setValues(new CharSequence[]{span});
                                break;
                            // 打赏事件
                            case PolyvChatMessage.EVENT_REWARD:
//                                final PolyvChatMessage.Content content = chatMessage.getRewardContent();
//                                final int textSize1 = (int) getResources().getDimension(R.dimen.tv_textsize);
//                                // 现金打赏
//                                if (content.isMoneyReward()) {
//                                    SpannableStringBuilder span1 = new SpannableStringBuilder("p" + content.getUnick() + " 打赏了 " + content.getRewardContent() + "元");
//                                    Drawable drawable1 = getResources().getDrawable(R.drawable.polyv_icon_money);
//                                    drawable1.setBounds(0, 0, textSize1 * 2, textSize1 * 2);
//                                    span1.setSpan(new RelativeImageSpan(drawable1, RelativeImageSpan.ALIGN_CENTER), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                                    chatMessage.setValues(new CharSequence[]{span1});
//                                }// 道具打赏
//                                else {
//                                    syncAdd = false;
//                                    Glide.with(getContext()).load(content.getGimg()).bitmapTransform(new CropCircleTransformation(getContext())).listener(new RequestListener<String, GlideDrawable>() {
//                                        @Override
//                                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                                            return true;
//                                        }
//
//                                        @Override
//                                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                                            SpannableStringBuilder span = new SpannableStringBuilder(content.getUnick() + " 赠送了" + content.getRewardContent() + " p");
//                                            resource.setBounds(0, 0, textSize1 * 2, textSize1 * 2);
//                                            span.setSpan(new RelativeImageSpan(resource, RelativeImageSpan.ALIGN_CENTER), span.length() - 1, span.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                                            chatMessage.setValues(new CharSequence[]{span});
//                                            polyvChatAdapter.add(chatMessage);
//                                            return true;
//                                        }
//                                    }).into(textSize1 * 2, textSize1 * 2);
//                                }
                                break;
                            // 发送红包事件
                            case PolyvChatMessage.EVENT_REDPAPER:
//                                String sendNick2 = chatMessage.getUser().getNick();
//                                SpannableStringBuilder span2 = new SpannableStringBuilder(sendNick2 + " 发送了" + chatMessage.getNumber() + "个红包，赶紧上微信领取吧");
//                                span2.setSpan(new ForegroundColorSpan(Color.rgb(255, 140, 0)), 0, span2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                                chatMessage.setValues(new CharSequence[]{span2});
                                break;
                            // 接收红包事件
                            case PolyvChatMessage.EVENT_GET_REDPAPER:
//                                String receiveNick = chatMessage.getUser().getNick();
//                                chatMessage.setValues(new CharSequence[]{receiveNick + " 收到了" + chatMessage.getSenderNick() + "的1个红包"});
                                break;
                            // 点赞
                            case PolyvChatMessage.EVENT_LIKES:
                                if(!ObjectUtils.isEmpty(chatMessage.getUser()))
                                {
                                    String sendNick3 = chatMessage.getUser().getNick();
//                                SpannableStringBuilder span3 = new SpannableStringBuilder(sendNick3 + "给老师点赞了");
//                                span3.setSpan(new ForegroundColorSpan(Color.rgb(255, 140, 0)), 0, sendNick3.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    chatMessage.setValues(new CharSequence[]{sendNick3 + "给老师点赞了"});
                                }
                                break;
                            // 登录
                            case PolyvChatMessage.EVENT_LOGIN:
                                needUnread=false;
                                EventBus.getDefault().post(new EventBusKeyDefine.EventBusMsgData<Integer>(EventBusKeyDefine.EVENTBUS_LIVE_PEOPLE_COUNT, chatMessage.getOnlineUserNumber()));
                                chatMessage.setValues(new String[]{chatMessage.getUser().getNick() + "进入直播间"});
                                break;
                            case PolyvChatMessage.EVENT_LOGOUT:
                                EventBus.getDefault().post(new EventBusKeyDefine.EventBusMsgData<Integer>(EventBusKeyDefine.EVENTBUS_LIVE_PEOPLE_COUNT, chatMessage.getOnlineUserNumber()));
                                break;
                            // 移除某条聊天信息
                            case PolyvChatMessage.EVENT_REMOVE_CONTENT:
                                polyvChatAdapter.remove(chatMessage.getId());
                                break;
                            // 清空所有聊天信息
                            case PolyvChatMessage.EVENT_REMOVE_HISTORY:
                                chatHistory.shutdown();
                                tv_loadmore.setVisibility(View.GONE);
                                polyvChatAdapter.clear();
                                break;
                        }
                    }
                    if (syncAdd && chatMessage.getValues() != null&&!isQuestion) {
                        if(needUnread)
                        {
                            EventBus.getDefault().post(new EventBusKeyDefine.EventBusMsgData<Integer>(EventBusKeyDefine.EVENTBUS_CHATROOM_UNREAD_COUNT,1));
                        }
                        polyvChatAdapter.add(chatMessage);
                    }
                    break;
                case DISCONNECT:
//                    tv_status.setText("连接失败(" + ((PolyvChatManager.ConnectStatus) msg.obj).getDescribe() + ")");
//                    tv_status.setVisibility(View.VISIBLE);
                    break;
                case LOGINING:
//                    tv_status.setText("正在登录中...");
//                    tv_status.setVisibility(View.VISIBLE);
                    break;
                case LOGINSUCCESS:
                    //viewPagerFragment.getQuestionFragment().loginSuccess();
                    //viewPagerFragment.getOnlineListFragment().loginSuccess();
//                    tv_status.setText("登录成功");
//                    tv_status.clearAnimation();
//                    tv_status.startAnimation(collapseAnimation);
                    break;
                case RECONNECTING:
                    //viewPagerFragment.getOnlineListFragment().reconnecting();
//                    tv_status.setText("正在重连中...");
//                    tv_status.setVisibility(View.VISIBLE);
                    break;
                case RECONNECTSUCCESS:
                    //viewPagerFragment.getOnlineListFragment().reconnectSuccess();
//                    tv_status.setText("重连成功");
//                    tv_status.clearAnimation();
//                    tv_status.startAnimation(collapseAnimation);
                    break;
                case GETHISTORYSUCCESS:
                    final List<PolyvChatMessage> lists = (List<PolyvChatMessage>) msg.obj;
                    if (lists.size() == messageCount) {
                        tv_loadmore.setText("加载更多...");
                        tv_loadmore.setTextColor(getResources().getColor(R.color.center_view_color_blue));
                    } else
                        tv_loadmore.setVisibility(View.GONE);
                    if (count == 1 && lists.size() > 0) {
                        // 添加一条以上是历史消息的信息
                        String emptyMsg = "────────";
                        SpannableStringBuilder span = new SpannableStringBuilder(emptyMsg + " 以上是历史消息 " + emptyMsg);
                        PolyvChatMessage historyMsg = new PolyvChatMessage("", "", "", 0, null, null, new CharSequence[]{span});
                        historyMsg.setChatType(PolyvChatMessage.CHATTYPE_RECEIVE_NOTICE);
                        lists.add(historyMsg);
                    }
                    //
                    polyvChatAdapter.addAll(lists);
                    final List<PolyvChatMessage> messages = polyvChatAdapter.getMessages();
                    if (count == 1 && lists.size() > 0 && messages.size() > 0)
                        lv_chat.smoothScrollToPosition(messages.size() - 1);
                    else
                        lv_chat.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (lists.size() > 0 && messages.size() > 0) {
//                                    lv_chat.setSelection(0);//todo
                                    isScrollEnd = false;
                                }
                            }
                        }, 300);
                    break;
                case GETHISTORYFAIL:
                    count--;
                    Toast.makeText(getContext(), "加载历史信息失败\n" + msg.obj + "-" + msg.arg1, Toast.LENGTH_SHORT).show();
                    tv_loadmore.setText("加载更多...");
                    tv_loadmore.setTextColor(getResources().getColor(R.color.center_view_color_blue));
                    break;
            }
        }
    };

    private List<PolyvChatMessage> filter(List<PolyvChatMessage> list) {
        boolean isOnlyTeacher = false;
        if (!isOnlyTeacher) {

        }
        return null;
    }

    /**
     * 初始化聊天室的配置
     *
     * @param chatManager 聊天室实例
     * @param chatUserId  登录聊天室的学员id(注：相同chatUserId之间不会收到对方的信息)
     * @param userId      频道所属的用户id
     * @param channelId   频道id
     */
    public void initChatConfig(@NonNull MyPolyvChatManager chatManager, NewsFrPagerAdapter pager, String chatUserId, String userId, String channelId, String nick_name) {
        this.chatUserId = chatUserId;
        this.userId = userId;
        this.channelId = channelId;
        this.pager = pager;
        this.nickName = nick_name;
        this.chatManager = chatManager;
        patternExpression = "@" + nickName;
        this.chatManager.setOnChatManagerListener(new MyPolyvChatManager.ChatManagerListener() {

            @Override
            public void connectStatus(MyPolyvChatManager.ConnectStatus connect_status) {
                switch (connect_status) {
                    case DISCONNECT:
                        handler.sendMessage(handler.obtainMessage(DISCONNECT, connect_status.DISCONNECT));
                        break;
                    case LOGINING:
                        handler.sendEmptyMessage(LOGINING);
                        break;
                    case LOGINSUCCESS:
                        handler.sendEmptyMessage(LOGINSUCCESS);
                        break;
                    case RECONNECTING:
                        handler.sendEmptyMessage(RECONNECTING);
                        break;
                    case RECONNECTSUCCESS:
                        handler.sendEmptyMessage(RECONNECTSUCCESS);
                        break;
                }
            }

            @Override
            public void receiveChatMessage(PolyvChatMessage chatMessage) {
                Message message = handler.obtainMessage();
                message.obj = chatMessage;
                message.what = RECEIVEMESSAGE;
                handler.sendMessage(message);
            }
        });
    }

    // 获取聊天室管理类
    public MyPolyvChatManager getChatManager() {
        return this.chatManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.polyv_fragment_chat, null);
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
        lists.addAll(PolyvFaceManager.getInstance().getFaceMap().keySet());
        final List<String> elists = lists.subList(position * (columns * rows), (position + 1) * (columns * rows));
        PolyvEmoGridViewAdapter emoGridViewAdapter = new PolyvEmoGridViewAdapter(elists, getContext());
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
        tv_status = (TextView) view.findViewById(R.id.tv_status);// TODO: 2018/5/29 登录状态view
        tv_read = (TextView) view.findViewById(R.id.tv_read);
        iv_send = (TextView) view.findViewById(R.id.iv_send);
        et_talk = (MsgEditText) view.findViewById(R.id.et_talk);
        et_talk.setFilters(new InputFilter[]{new InputFilter.LengthFilter(40)});
        vp_emo = (ViewPager) view.findViewById(R.id.vp_emo);
        iv_page1 = (ImageView) view.findViewById(R.id.iv_page1);
        iv_page2 = (ImageView) view.findViewById(R.id.iv_page2);
        iv_page3 = (ImageView) view.findViewById(R.id.iv_page3);
        iv_page4 = (ImageView) view.findViewById(R.id.iv_page4);
        iv_page5 = (ImageView) view.findViewById(R.id.iv_page5);
        iv_emoswitch = (ImageView) view.findViewById(R.id.iv_emoswitch);
        layout_emoswitch = (View) view.findViewById(R.id.layout_emoswitch);
        rl_bot = (RelativeLayout) view.findViewById(R.id.rl_bot);
        tv_loadmore = (TextView) view.findViewById(R.id.tv_loadmore);
        tv_at_teacher = (TextView) view.findViewById(R.id.tv_at_teacher);
        layout_ask_teacher = (View) view.findViewById(R.id.layout_ask_teacher);
        question_red_dot = view.findViewById(R.id.question_red_dot);
        tv_tips_top = (TextView) view.findViewById(R.id.tv_tips_top);
        layout_only_teacher = (View) view.findViewById(R.id.layout_only_teacher);
        iv_only_teacher = (View) view.findViewById(R.id.iv_only_teacher);
        btn_close_tips = (LinearLayout) view.findViewById(R.id.btn_close_tips);
        layout_tips_top = (LinearLayout) view.findViewById(R.id.layout_tips_top);
        tv_at_teacher.setOnClickListener(this);
        btn_close_tips.setOnClickListener(this);
        tv_tips_top.setOnClickListener(this);
        layout_only_teacher.setOnClickListener(this);
        chatHistory = new PolyvChatHistory();
        chatBadword = new PolyvChatBadword();
//        polyvChatQuiz = new PolyvChatQuiz();
        //viewPagerFragment = (PolyvTabViewPagerFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fl_tag_viewpager);
        messages = new LinkedList<>();
//        collapseAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.polyv_collapse);
//        collapseAnimation.setAnimationListener(new ViewAnimationListener(tv_status));
        mKeyBoardListener = new KeyBoardListener(layout_only_teacher, new KeyBoardListener.OnSoftKeyboardListener() {
            @Override
            public void onSoftKeyboardShown(int keyBoardHeight) {
                layout_only_teacher.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onSoftKeyboardHidden(int keyBoardHeight) {
                layout_only_teacher.setVisibility(VISIBLE);
            }
        });

    }

    private class ViewAnimationListener implements AnimationListener {
        private View view;

        public ViewAnimationListener(View view) {
            this.view = view;
        }

        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            view.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
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
        } catch (NotFoundException | IOException e) {
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

    // 获取严禁词
    public static List<String> getBadwords() {
        return badwords;
    }

    // 添加严禁词
    private void addBadwords() {
        // 添加默认的严禁词
        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream in = getActivity().getResources().openRawResource(R.raw.default_badword);
                byte[] buf = new byte[1024];
                int len = 0;
                StringBuilder stringBuilder = new StringBuilder();
                try {
                    while ((len = in.read(buf)) != -1) {
                        stringBuilder.append(new String(buf, 0, len));
                    }
                    String[] badwordArr = stringBuilder.toString().substring(1, stringBuilder.length() - 2).replaceAll("\"", "").split(",");
                    for (int i = 0; i < badwordArr.length; i++) {
                        if (!badwordArr[i].trim().equals(""))
                            badwords.add(badwordArr[i]);
                    }
                } catch (IOException e) {
                }
            }
        }).start();
        // 添加后台设置的严禁词
        chatBadword.getBadwordList(userId, channelId, Integer.MAX_VALUE, new PolyvChatBadwordListener() {
            @Override
            public void success(List<String> lists) {
                badwords.addAll(lists);
            }

            @Override
            public void fail(String failTips, int code) {
            }
        });
    }

    private final static String MASK_STR = "@";

    public void addSpan(String atStr, String userId) {
        et_talk.addAtSpan(MASK_STR, atStr, userId);
        Log.d("ddd", et_talk.getUserIdString());
    }

//    private String sign(long timestamp) {
//        String appId = MyApplication.appId;
//        String userId = CommonParam.POLY_USER_ID;
//        String appSecret = MyApplication.appSecret;
//// 创建参数表 （创建接口需要传递的所有参数表）
//        Map<String, String> paramMap = new HashMap<String, String>();
//        paramMap.put("appId", appId);
//        paramMap.put("timestamp", Long.toString(timestamp));
////对参数名进行字典排序
//        String[] keyArray = paramMap.keySet().toArray(new String[0]);
//        Arrays.sort(keyArray);
//
////拼接有序的参数串
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append(appSecret);
//        for (String key : keyArray) {
//            stringBuilder.append(key).append(paramMap.get(key));
//        }
//
//        stringBuilder.append(appSecret);
//        String signSource = stringBuilder.toString();
//
//        String sign = Md5Util.getMd5(signSource).toUpperCase();
//        System.out.println("http://api.live.polyv.net/v1/users/" + userId + "/channels?appId=" + appId + "&timestamp=" + timestamp + "&sign=" + sign);
//        return sign;
//    }

    @SuppressWarnings("deprecation")
    private void initView() {
        // 添加严禁词
        addBadwords();
        // 获取聊天室历史信息
        loadMoreChatMessage(channelId, chatUserId);
//        long var4 = System.currentTimeMillis();
//        String var6 = Md5Util.getMd5("POLYV_LIVE_API_INNOR" + "channelId" + channelId + "timestamp" + var4 + "POLYV_LIVE_API_INNOR").toUpperCase();
//        String var7 = String.format("http://api.polyv.net/live/v2/chat/197776/getQuestion?%s", new Object[]{"appId=" + MyApplication.appId + "&timestamp=" + var4 + "&sign=" + sign(var4)});
//        Log.e("url", var7);
//        polyvChatQuiz.hasQuiz(channelId, new PolyvChatQuizListener() {
//            @Override
//            public void success(boolean b) {
//                int i = 0;
//                LoginHelper.getInstance().isLogin();
//            }
//
//            @Override
//            public void fail(String s, int i) {
//                int n = 0;
//                LoginHelper.getInstance().isLogin();
//            }
//        });
        polyvChatAdapter = new PolyvChatAdapter(getContext(), messages, lv_chat);
        polyvChatAdapter.setSelfNickName(nickName);
        if (teacherBean != null) {
            polyvChatAdapter.setTeacherName(teacherBean.getTea_name());
        }
        UserInfoBean userInfoBean = LoginHelper.getInstance().getLoginUserInfo();
        if (userInfoBean != null) {//说明不是游客
            polyvChatAdapter.setSelfPic(userInfoBean.avatars);
        }else{//游客
            polyvChatAdapter.setSelfPic(CommonParam.POLY_CHAT_TOURIST_DEFAULT_AVATAR);
        }
        polyvChatAdapter.setResendType(true);
        polyvChatAdapter.setChatManager(chatManager);
//        polyvChatAdapter.setViewPagerFragment(viewPagerFragment);
        polyvChatAdapter.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onClick(View view) {
                closeKeybordAndEmo(et_talk, getContext());
            }

            @Override
            public void atUser(PolyvChatMessage.User user) {
                if (!polyvChatAdapter.isTeacher(user)) {
                    addSpan(user.getNick(), user.getUserId());
                } else {
                    showQuestionDialog();
                }
            }
        });
        lv_chat.setAdapter(polyvChatAdapter);
        lv_chat.setOnScrollListener(new AbsListView.OnScrollListener() {
            int height;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        isScrollEnd = false;
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        if (lv_chat.getLastVisiblePosition() == (lv_chat.getCount() - 1)) {
                            View lastView = lv_chat.getChildAt(lv_chat.getLastVisiblePosition() - lv_chat.getFirstVisiblePosition());
                            isScrollEnd = lastView.getBottom() <= lv_chat.getHeight();
                        }
                        if (lv_chat.getLastVisiblePosition() >= lastPreviewItem) {
                            tv_read.setVisibility(View.GONE);
                            lastPreviewItem = -1;
                        }
                        // 判断滚动到顶部
                        if (lv_chat.getFirstVisiblePosition() == 0) {
                            loadMoreChatMessage(channelId, chatUserId);
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int itemsHeight = 0;
                for (int i = 0; i < visibleItemCount - firstVisibleItem; i++) {
                    itemsHeight += lv_chat.getChildAt(i).getMeasuredHeight();
                }
                if (itemsHeight > (height = Math.max(height, lv_chat.getMeasuredHeight()))) {
                    if (!lv_chat.isStackFromBottom()) {
                        lv_chat.setStackFromBottom(true);
                        lv_chat.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                polyvChatAdapter.notifyDataSetChanged();
                            }
                        }, 300);
                    }
                } else if (visibleItemCount == totalItemCount && lv_chat.isStackFromBottom()) {
                    lv_chat.setStackFromBottom(false);
                    lv_chat.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            polyvChatAdapter.notifyDataSetChanged();
                        }
                    }, 300);
                }
            }
        });
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
        tv_loadmore.setOnClickListener(this);
        tv_read.setOnClickListener(this);
    }

    private void showQuestionDialog() {
        question_red_dot.setVisibility(GONE);
        QuestionActivity.actionStart(getActivity(), userId, channelId, chatUserId, nickName);
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
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            rl_bot.setVisibility(View.VISIBLE);
            closeKeybord(et_talk, getContext());
            et_talk.requestFocus();
        }
    }

    // 发送聊天信息
    private void sendMsg() {
        String msg = et_talk.getText().toString();
        if (msg.trim().length() == 0) {
            Toast.makeText(getContext(), "发送信息不能为空!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (kick) {
            Toast.makeText(getContext(), "抱歉，您已经被管理员踢出房间，暂不能发言", Toast.LENGTH_SHORT).show();
            return;
        }
        if (ban) {
            Toast.makeText(getContext(), "抱歉，您已经被管理员禁言，请联系客服", Toast.LENGTH_SHORT).show();
            return;
        }
        for (int i = 0; i < badwords.size(); i++) {
            if (msg.contains(badwords.get(i))) {
                Toast.makeText(getContext(), "抱歉，您的言论包含敏感词，请修改后发送", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        final PolyvChatMessage message = new PolyvChatMessage(msg);
        polyvChatAdapter.add(message);
        final int lastPosition = polyvChatAdapter.getCount() - 1;
        if (chatManager.sendChatMessage(message)) {
//            viewPagerFragment.getDanmuFragment().sendDanmaku(message.getValues()[0]);
            polyvChatAdapter.updateStatusView(true, false, lastPosition);
        } else {
            polyvChatAdapter.updateStatusView(false, false, lastPosition);
        }
        if (isAtTeacher(msg)) {
            chatManager.sendQuestionMsg(message);
        }

        lv_chat.setSelection(lastPosition);
        tv_read.setVisibility(View.GONE);
        lastPreviewItem = -1;
        isScrollEnd = true;
        et_talk.setText("");
        closeKeybordAndEmo(et_talk, getContext());
    }

    private void sendLikeEvent(){
        final PolyvChatMessage message = new PolyvChatMessage("like");
        if (chatManager.sendLikesMsg(message)) {

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

    //加载聊天室的历史信息
    private void loadMoreChatMessage(String channelId, String chatUserId) {
        tv_loadmore.setText("加载中...");
        tv_loadmore.setTextColor(getResources().getColor(R.color.bottom_et_color_gray));
        count++;
        chatHistory.getChatHistory(channelId, chatUserId, count * messageCount - messageCount, count * messageCount - 1, new PolyvChatHistoryListener() {
            @Override
            public void success(List<PolyvChatMessage> lists) {
                Message message = handler.obtainMessage(GETHISTORYSUCCESS);
                message.obj = lists;
                handler.sendMessage(message);
            }

            @Override
            public void fail(String failTips, int code) {
                Message message = handler.obtainMessage(GETHISTORYFAIL);
                message.obj = failTips;
                message.arg1 = code;
                handler.sendMessage(message);
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!isInitialized) {
            isInitialized = true;
            findIdAndNew();
            initView();
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
        // 中断请求
        chatHistory.shutdown();
        chatBadword.shutdown();
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
            case R.id.layout_emoswitch:
                resetEmoLayout(false);
                break;
            case R.id.et_talk:
                resetEmoLayout(true);
                break;
            case R.id.tv_loadmore:
                if ("加载更多...".equals(tv_loadmore.getText()))
                    loadMoreChatMessage(channelId, chatUserId);
                break;
            case R.id.tv_read:
                tv_read.setVisibility(View.GONE);
                lastPreviewItem = -1;
                lv_chat.setSelection(lv_chat.getCount() - 1);
                isScrollEnd = true;
                break;
            case R.id.tv_at_teacher:
                if (teacherBean != null) {
                    showQuestionDialog();
//                    addSpan(teacherBean.getTea_name(), teacherBean.getId());
                }
                break;
            case R.id.btn_close_tips:
                layout_tips_top.setVisibility(View.GONE);
                break;
            case R.id.layout_only_teacher:
                boolean selected = layout_only_teacher.isSelected();
                layout_only_teacher.setSelected(!selected);
                iv_only_teacher.setSelected(!selected);
                polyvChatAdapter.swtichOnlyTeacher(!selected);
                break;
            case R.id.tv_tips_top:
                layout_tips_top.setVisibility(View.GONE);
                if (!((atMePostion >= lv_chat.getFirstVisiblePosition()) && (atMePostion <= lv_chat.getLastVisiblePosition())) && atMePostion >= 0&& atMePostion < lv_chat.getCount()) {
                    lv_chat.setSelection(atMePostion);
                    isScrollEnd = true;
                }
                break;
        }
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
    public void onEventMainThrea2(EventBusKeyDefine.EventBusMsgData<Integer> data) {
        int type = data.getType();
        try {
            if (EventBusKeyDefine.EVENTBUS_LIVE_STATUS == type) {
                if (!ObjectUtils.isEmpty(data) && !ObjectUtils.isEmpty(data.getData())) {
                    int liveStatus = data.getData();
                    if(liveStatus==1)
                    {
                        layout_ask_teacher.setVisibility(VISIBLE);
                    }else{
                        layout_ask_teacher.setVisibility(GONE);
                    }
                }
            }else if (EventBusKeyDefine.EVENTBUS_TEACHER_LIKE == type) {
                sendLikeEvent();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unregistorEventBus();
    }
}

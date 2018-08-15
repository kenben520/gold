package com.lingxi.preciousmetal.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
//import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.easefun.polyvsdk.live.chat.PolyvChatManager;
import com.easefun.polyvsdk.live.chat.PolyvChatMessage;
import com.lingxi.common.base.GlideApp;
import com.lingxi.common.util.StringUtil;
import com.lingxi.polylib.util.PolyvRoundDisplayerUtils;
import com.lingxi.polylib.util.PolyvTextImageLoader;
import com.lingxi.polylib.util.PolyvTimeUtils;
import com.lingxi.polylib.util.PolyvViewHolder;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.ui.jpush.MyPolyvChatManager;
import com.lingxi.preciousmetal.util.utilCode.ObjectUtils;
import com.lingxi.preciousmetal.util.utilCode.TimeUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import pl.droidsonroids.gif.GifSpanTextView;
import pl.droidsonroids.gif.RelativeImageSpan;

public class PolyvChatAdapter extends BaseAdapter implements OnClickListener {
    private List<PolyvChatMessage> messages;
    private List<PolyvChatMessage> teacherMessages;
    private List<PolyvChatMessage> allMessages;
    private LayoutInflater inflater;
    private ListView lv_chat;
    private DisplayImageOptions options;
    private MyPolyvChatManager chatManager;
    private PolyvTextImageLoader textImageLoader;
    private Context context;
    private BitmapDrawable bitmapDrawable;
    private CropCircleTransformation cropCircleTransformation;
    private String selfNickName, selfPic, teacherName;
    // 重发时，是发送聊天室还是发提问信息
    private boolean isResendChatMessage = true;

    public List<PolyvChatMessage> getMessages() {
        return messages;
    }

    public PolyvChatAdapter(Context context, List<PolyvChatMessage> messages, ListView lv_chat) {
        this.context = context;
        List<PolyvChatMessage> messageList = new ArrayList<>();
        messageList.addAll(messages);
        this.messages = messageList;
        List<PolyvChatMessage> allMsg = new ArrayList<>();
        allMsg.addAll(messages);
        this.allMessages = allMsg;
        this.teacherMessages = filter(messages);
        this.inflater = LayoutInflater.from(context);
        this.lv_chat = lv_chat;
        this.options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.chat_default_avatar) // resource
                // or
                // drawable
                .showImageForEmptyUri(R.drawable.chat_default_avatar) // resource
                // or
                // drawable
                .showImageOnFail(R.drawable.chat_default_avatar) // resource or
                // drawable
                .bitmapConfig(Config.RGB_565).cacheInMemory(true).cacheOnDisk(true)
                .displayer(new PolyvRoundDisplayerUtils(0)).build();
        this.textImageLoader = new PolyvTextImageLoader(context);
        this.cropCircleTransformation = new CropCircleTransformation(context);
        this.bitmapDrawable = new BitmapDrawable(context.getResources());
    }

    private List<PolyvChatMessage> filter(List<PolyvChatMessage> list) {
        List<PolyvChatMessage> tempList = new ArrayList<>();
        if (ObjectUtils.isEmpty(list)) return tempList;
        for (int i = 0; i < list.size(); i++) {
            PolyvChatMessage message = list.get(i);
            final PolyvChatMessage.User user = message.getUser();
            if (user != null) {
                if (isTeacher(user)) {
                    tempList.add(message);
                }
            } else {
                int g = 0;
            }
        }
        return tempList;
    }


    public void setChatManager(MyPolyvChatManager chatManager) {
        this.chatManager = chatManager;
    }

    public void setResendType(boolean isResendChatMessage) {
        this.isResendChatMessage = isResendChatMessage;
    }

    public void clear() {
        messages.clear();
        allMessages.clear();
        teacherMessages.clear();
        notifyDataSetChanged();
    }

    public void add(PolyvChatMessage message) {
        if (onlyTeacher) {
            final PolyvChatMessage.User user = message.getUser();
            if (isTeacher(user)) {
                messages.add(message);
            }
        } else {
            messages.add(message);
        }
        allMessages.add(message);
        addTeacherMsg(message);
        notifyDataSetChanged();
    }

    private void addTeacherMsg(PolyvChatMessage message) {
        final PolyvChatMessage.User user = message.getUser();
        if (user != null && isTeacher(user)) {
            teacherMessages.add(message);
        }
    }

    // 添加历史记录
    public void addAll(List<PolyvChatMessage> lists) {
        allMessages.addAll(0, lists);
        teacherMessages.addAll(0, filter(lists));
        if (onlyTeacher) {
            messages.addAll(0, filter(lists));
        } else {
            messages.addAll(0, lists);
        }
        notifyDataSetChanged();
    }

    public PolyvChatMessage remove(int position) {
        PolyvChatMessage chatMessage = messages.remove(position);
        notifyDataSetChanged();
        return chatMessage;
    }

    public PolyvChatMessage removeAllList(int position) {
        PolyvChatMessage chatMessage = allMessages.remove(position);
        notifyDataSetChanged();
        return chatMessage;
    }

    /**
     * 根据聊天id移除聊天信息
     *
     * @param chatId
     */
    public PolyvChatMessage remove(String chatId) {
        PolyvChatMessage chatMessage = null;
        for (int i = 0; i < messages.size(); i++) {
            if (chatId.equals(messages.get(i).getId())) {
                chatMessage = messages.remove(i);
                allMessages.remove(i);
                break;
            }
        }
        notifyDataSetChanged();
        return chatMessage;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        return messages.get(position).getChatType();
    }

    private View getChatView(int position) {
        switch (getItemViewType(position)) {
            case PolyvChatMessage.CHATTYPE_RECEIVE:
            case PolyvChatMessage.CHATTYPE_RECEIVE_QUESTION:
                return inflater.inflate(R.layout.polyv_listivew_chat_receive, null);
            case PolyvChatMessage.CHATTYPE_RECEIVE_NOTICE:
                return inflater.inflate(R.layout.polyv_listview_chat_receive_notice, null);
            case PolyvChatMessage.CHATTYPE_SEND:
                return inflater.inflate(R.layout.polyv_listview_chat_send, null);
        }
        return null;
    }

    public static StringBuffer addChild(String str,List<String> inputs,StringBuffer resStr,boolean isTeacher){
        int index=str.length();//用来做为标识,判断关键字的下标
        String next="";//保存str中最先找到的关键字
        for (int i = inputs.size() -1 ; i>= 0;i--) {
            String theNext=inputs.get(i);
            int theIndex=str.indexOf(theNext);
            if(theIndex==-1){//过滤掉无效关键字
                inputs.remove(i);
            }else if(theIndex<index){
                index=theIndex;//替换下标
                next=theNext;
            }
        }

        //如果条件成立,表示串中已经没有可以被替换的关键字,否则递归处理
        if(index==str.length()){
            resStr.append(str);
        }else{
            resStr.append(str.substring(0,index));
            if(isTeacher){
                resStr.append("<font color='#FFFFFF' font weight=bolder>"+str.substring(index,index+next.length())+"</font>");
            }else{
                resStr.append("<font color='#000000' font weight=bolder>"+str.substring(index,index+next.length())+"</font>");
            }
            String str1=str.substring(index+next.length(),str.length());
            addChild(str1,inputs,resStr,isTeacher);//剩余的字符串继续替换
        }
        return resStr;
    }

    private List<String> getAllAtString(String msg) {
        List<String> atStrList=new ArrayList<>();
        Pattern p = Pattern.compile("@.*?\\s");
        Matcher m = p.matcher(msg);
        while( m.find() )
        {
            atStrList.add(m.group());
        }
        return atStrList;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final PolyvChatMessage message = messages.get(position);
        final PolyvChatMessage.User user = message.getUser();
        if (convertView == null)
            convertView = getChatView(position);
        final LinearLayout ll_parent = PolyvViewHolder.get(convertView, R.id.ll_parent);
//        ll_parent.setVisibility(View.VISIBLE);
//        if (onlyTeacher) {
//            switch (message.getChatType()) {
//                case PolyvChatMessage.CHATTYPE_RECEIVE:
//                    if (!isTeacher(user)) {
//                        ll_parent.setVisibility(View.GONE);
//                        return convertView;
//                    }
//                    break;
//                case PolyvChatMessage.CHATTYPE_RECEIVE_QUESTION:
//                    if (!isTeacher(user)) {
//                        ll_parent.setVisibility(View.GONE);
//                        return convertView;
//                    }
//                    break;
//                case PolyvChatMessage.CHATTYPE_RECEIVE_NOTICE:
//                    break;
//                case PolyvChatMessage.CHATTYPE_SEND:
//                    ll_parent.setVisibility(View.GONE);
//                    return convertView;
//            }
//        }
        final ImageView iv_resend = PolyvViewHolder.get(convertView, R.id.iv_resend);
        final ImageView iv_avatar = PolyvViewHolder.get(convertView, R.id.iv_avatar);
        final GifSpanTextView tv_msg = PolyvViewHolder.get(convertView, R.id.tv_msg);
        final TextView tv_time = PolyvViewHolder.get(convertView, R.id.tv_time);
        final TextView tv_name = PolyvViewHolder.get(convertView, R.id.tv_name);
        final TextView tv_notice = PolyvViewHolder.get(convertView, R.id.tv_notice);
        final TextView tv_send_time_receive = PolyvViewHolder.get(convertView, R.id.tv_send_time_receive);
        final TextView tv_tag_receive = PolyvViewHolder.get(convertView, R.id.tv_tag_receive);
        final TextView tv_send_time = PolyvViewHolder.get(convertView, R.id.tv_send_time);
        final TextView tv_sender_name = PolyvViewHolder.get(convertView, R.id.tv_sender_name);
        final ImageView iv_avatar_sender = PolyvViewHolder.get(convertView, R.id.iv_avatar_sender);
        ll_parent.setOnClickListener(this);
        if (message.getChatType() != PolyvChatMessage.CHATTYPE_RECEIVE_NOTICE) {
            if (message.getChatType() == PolyvChatMessage.CHATTYPE_RECEIVE_QUESTION) {//私聊和公聊返回数据不是在同一个字段 私聊这里是老师的回复内容 没有时间
                StringBuffer str = new StringBuffer("");
                String msgContent=message.getContent()+" ";
                str =  addChild(msgContent, getAllAtString(msgContent), str,isTeacher(user));
                textImageLoader.displayTextImage(Html.fromHtml(str.toString()), tv_msg);
                tv_time.setVisibility(View.GONE);
            } else {
                StringBuffer str = new StringBuffer("");
                String msgContent=message.getValues()[0].toString()+" ";
                str =  addChild(msgContent, getAllAtString(msgContent), str,isTeacher(user));
                textImageLoader.displayTextImage(Html.fromHtml(str.toString()), tv_msg);
                tv_time.setText(PolyvTimeUtils.friendlyTime(message.getTime()));
                if (message.isShowTime()) {
                    tv_time.setVisibility(View.VISIBLE);
                } else {
                    tv_time.setVisibility(View.GONE);
                }
            }
        }

        switch (message.getChatType()) {
            // 其他用户发送的信息
            case PolyvChatMessage.CHATTYPE_RECEIVE:
                tv_name.setText(user.getNick());
                tv_name.setTag(user);
                iv_avatar.setTag(user);
                if (message.getTime() > 0)//私聊时间字段为空
                {
                    tv_send_time_receive.setVisibility(View.VISIBLE);
                    tv_send_time_receive.setText(TimeUtils.getDataStr(message.getTime() / 1000, "HH:mm"));
                } else {
                    tv_send_time_receive.setVisibility(View.GONE);
                }
                ImageLoader.getInstance().displayImage(user.getPic(), iv_avatar, options);
                if (isTeacher(user)) {
                    tv_tag_receive.setText(getTeacherName());
                    tv_tag_receive.setVisibility(View.VISIBLE);
                    tv_msg.setBackground(context.getResources().getDrawable(R.drawable.bg_corner_shape_y4));
                    tv_msg.setTextColor(context.getResources().getColor(R.color.whiteTwo));
                } else {
                    tv_msg.setBackground(context.getResources().getDrawable(R.drawable.bg_corner_shape_w4));
                    tv_msg.setTextColor(context.getResources().getColor(R.color.black1));
                    tv_tag_receive.setTag(user);
                    tv_send_time_receive.setTag(user);
                    iv_avatar.setOnClickListener(this);
                    tv_name.setOnClickListener(this);
                    tv_tag_receive.setOnClickListener(this);
                    tv_send_time_receive.setOnClickListener(this);
                    tv_tag_receive.setVisibility(View.GONE);
                }
                break;
            case PolyvChatMessage.CHATTYPE_RECEIVE_QUESTION:
                tv_name.setText(user.getNick());
                ImageLoader.getInstance().displayImage(user.getPic(), iv_avatar, options);
                break;
            // 聊天室的一些通知信息
            case PolyvChatMessage.CHATTYPE_RECEIVE_NOTICE:
                tv_notice.setTag(tv_notice);
                // 历史记录里的发送红包信息
                if (message.isHistorySendRedPaper()) {
                    String sendNick2 = message.getUser().getNick();
                    SpannableStringBuilder span2 = new SpannableStringBuilder(sendNick2 + " 发送了" + message.getNumber() + "个红包，赶紧上微信领取吧");
                    span2.setSpan(new ForegroundColorSpan(Color.rgb(255, 140, 0)), 0, span2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tv_notice.setText(span2);
                }// 历史记录里的接收红包信息
                else if (message.isHistoryGetRedPaper()) {
                    String receiveNick = message.getUser().getNick();
                    tv_notice.setText(receiveNick + " 收到了" + message.getSenderNick() + "的1个红包");
                }//历史记录里的打赏信息
                else if (message.isHistoryReward()) {
                    final PolyvChatMessage.Content content = message.getRewardContent();
                    final int textSize1 = (int) context.getResources().getDimension(R.dimen.tv_textsize);
                    // 现金打赏
                    if (content.isMoneyReward()) {
                        SpannableStringBuilder span1 = new SpannableStringBuilder("p" + content.getUnick() + " 打赏了 " + content.getRewardContent() + "元");
                        Drawable drawable1 = context.getResources().getDrawable(R.drawable.polyv_icon_money);
                        drawable1.setBounds(0, 0, textSize1 * 2, textSize1 * 2);
                        span1.setSpan(new RelativeImageSpan(drawable1, RelativeImageSpan.ALIGN_CENTER), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tv_notice.setText(span1);
                    }// 道具打赏
                    else {
                        String imgUrl = content.getGimg();
                        String space = "图片";
                        final SpannableStringBuilder span = new SpannableStringBuilder(content.getUnick() + " 赠送了" + content.getRewardContent() + " " + space);
                        bitmapDrawable.setBounds(0, 0, textSize1 * 2, textSize1 * 2);
                        span.setSpan(new RelativeImageSpan(bitmapDrawable, RelativeImageSpan.ALIGN_CENTER), span.length() - space.length(), span.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tv_notice.setTag(imgUrl);
                        tv_notice.setText(span);
                        RequestOptions myOptions = new RequestOptions()
                                .transform(cropCircleTransformation);
                        GlideApp.with(context).load(imgUrl).apply(myOptions).listener(new MyRequestListener(span, tv_notice, textSize1, space.length(), imgUrl)).into(textSize1 * 2, textSize1 * 2);
//                        Glide.with(context).load(imgUrl)..bitmapTransform(cropCircleTransformation)
//                                .listener(new MyRequestListener(span, tv_notice, textSize1, space.length(), imgUrl)).into(textSize1 * 2, textSize1 * 2);
                    }
                } else {
                    tv_notice.setText(message.getValues()[0]);
                }
                break;
            // 自己发送的信息
            case PolyvChatMessage.CHATTYPE_SEND:
                tv_sender_name.setText(getSelfNick());
                ImageLoader.getInstance().displayImage(getSelfPic(), iv_avatar_sender, options);
                long time = message.getTime() <= 0 ? System.currentTimeMillis() / 1000 : message.getTime() / 1000;
                tv_send_time.setText(TimeUtils.getDataStr(time, "HH:mm"));
                if (!message.isSendSuccess()) {
                    iv_resend.setVisibility(View.VISIBLE);
                } else {
                    iv_resend.setVisibility(View.GONE);
                }
                iv_resend.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        iv_resend.setVisibility(View.GONE);
                        if (isResendChatMessage) {
                            if (chatManager.sendChatMessage(message)) {
//                                viewPagerFragment.getDanmuFragment().sendDanmaku(message.getValues()[0]);
                                updateStatusView(true, true, position);
                            } else {
                                updateStatusView(false, true, position);
                            }
                        } else {
                            if (chatManager.sendQuestionMsg(message)) {
                                updateStatusView(true, true, position);
                            } else {
                                updateStatusView(false, true, position);
                            }
                        }
                    }
                });
                break;
        }
        return convertView;
    }

    public boolean isTeacher(PolyvChatMessage.User user) {
        if (user == null) return false;
        return "teacher".equals(user.getUserType());
    }

    private boolean onlyTeacher = false;

    public void swtichOnlyTeacher(boolean onlyTeacher) {
        if (this.onlyTeacher == onlyTeacher) return;
        this.onlyTeacher = onlyTeacher;
        ArrayList<PolyvChatMessage> list = new ArrayList<>();
        if (this.onlyTeacher) {
            list.addAll(teacherMessages);
        } else {
            list.addAll(allMessages);
        }
        messages = list;
        notifyDataSetChanged();
    }


    public String getSelfNick() {
        return selfNickName;
    }

    public void setSelfNickName(String selfNickName) {
        this.selfNickName = selfNickName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherName() {
        return "老师";
//        return StringUtil.isEmpty(teacherName) ? "老师" : teacherName;
    }

    public String getSelfPic() {
        return selfPic;
    }

    public void setSelfPic(String selfPic) {
        this.selfPic = selfPic;
    }

    private static class MyRequestListener implements RequestListener<Drawable> {
        private SpannableStringBuilder span;
        private WeakReference<TextView> wr_textView;
        private int textSize;
        private int perchLength;
        private String tag;

        public MyRequestListener(SpannableStringBuilder span, TextView textView, int textSize, int perchLength, String tag) {
            this.span = span;
            this.wr_textView = new WeakReference<TextView>(textView);
            this.textSize = textSize;
            this.perchLength = perchLength;
            this.tag = tag;
        }

        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
            return true;
        }

        @Override
        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
            TextView textView = wr_textView.get();
            if (textView != null && textView.getTag() != null && textView.getTag().equals(tag)) {
                resource.setBounds(0, 0, textSize * 2, textSize * 2);
                span.setSpan(new RelativeImageSpan(resource, RelativeImageSpan.ALIGN_CENTER), span.length() - perchLength, span.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView.setText(span);
            }
            return true;
        }
    }

//    private static class MyRequestListener implements RequestListener<String, GlideDrawable> {
//        private SpannableStringBuilder span;
//        private WeakReference<TextView> wr_textView;
//        private int textSize;
//        private int perchLength;
//        private String tag;
//
//        public MyRequestListener(SpannableStringBuilder span, TextView textView, int textSize, int perchLength, String tag) {
//            this.span = span;
//            this.wr_textView = new WeakReference<TextView>(textView);
//            this.textSize = textSize;
//            this.perchLength = perchLength;
//            this.tag = tag;
//        }
//
//        @Override
//        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//            return true;
//        }
//
//        @Override
//        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//            TextView textView = wr_textView.get();
//            if (textView != null && textView.getTag() != null && textView.getTag().equals(tag)) {
//                resource.setBounds(0, 0, textSize * 2, textSize * 2);
//                span.setSpan(new RelativeImageSpan(resource, RelativeImageSpan.ALIGN_CENTER), span.length() - perchLength, span.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                textView.setText(span);
//            }
//            return true;
//        }
//    }

    /**
     * 发送信息所需的更新发送状态view
     *
     * @param isSendSuccess 是否发送成功
     * @param isReSend      是否是重发
     * @param position      该信息的索引
     */

    public void updateStatusView(boolean isSendSuccess, boolean isReSend, int position) {
        View view = lv_chat.getChildAt(position - lv_chat.getFirstVisiblePosition());
        if (view != null) {
            if (isSendSuccess) {
                if (isReSend && messages.size() > 1) {
                    // 更新到最新
                    messages.add(remove(position));
                    allMessages.add(removeAllList(position));
                }
            } else {
                ImageView iv_resend = (ImageView) view.findViewById(R.id.iv_resend);
                iv_resend.setVisibility(View.VISIBLE);
            }
        }
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        public void onClick(View view);

        void atUser(PolyvChatMessage.User view);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_parent:
                if (listener != null)
                    listener.onClick(v);
                break;
            case R.id.tv_send_time_receive:
            case R.id.tv_tag_receive:
            case R.id.tv_name:
            case R.id.iv_avatar:
                PolyvChatMessage.User user = (PolyvChatMessage.User) v.getTag();
                if (listener != null && user != null)
                    listener.atUser(user);
                break;
        }
    }
}

package com.lingxi.preciousmetal.ui.jpush;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Patterns;
import android.view.SurfaceView;

import com.easefun.polyvsdk.live.chat.IPolyvChatManager;
import com.easefun.polyvsdk.live.chat.PolyvChatManager;
import com.easefun.polyvsdk.live.chat.PolyvChatMessage;
import com.easefun.polyvsdk.live.chat.linkmic.api.entity.PolyvJoinLeaveEntity;
import com.easefun.polyvsdk.live.chat.linkmic.api.entity.PolyvJoinRequestEntity;
import com.easefun.polyvsdk.live.chat.linkmic.api.entity.PolyvJoinResponseEntity;
import com.easefun.polyvsdk.live.chat.linkmic.api.entity.PolyvJoinSuccessEntity;
import com.easefun.polyvsdk.live.chat.linkmic.api.entity.PolyvMicrophoneEvent;
import com.easefun.polyvsdk.live.chat.linkmic.module.Constant;
import com.easefun.polyvsdk.live.chat.linkmic.module.EngineConfig;
import com.easefun.polyvsdk.live.chat.linkmic.module.MyEngineEventHandler;
import com.easefun.polyvsdk.live.chat.util.Md5Util;
import com.easefun.polyvsdk.live.chat.util.NetUtil;
import com.easefun.polyvsdk.live.util.Hex;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.VideoCanvas;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by zhangwei on 2018/7/17.
 */

public class MyPolyvChatManager  implements IPolyvChatManager {
    @Override
    public void setLinkMicrophoneListener(PolyvChatManager.LinkMicrophoneListener linkMicrophoneListener) {

    }

    @Override
    public void setOnChatManagerListener(PolyvChatManager.ChatManagerListener chatManagerListener) {

    }


    private static final String TAG = MyPolyvChatManager.class.getSimpleName();
    private static final String USERTYPE = "slice";
    static final String default_avatarUrl = "http://www.polyv.net/images/effect/effect-device.png";
    private static final int GET_TOKEN_SUCCESS = 12;
    private static final int GET_TOKEN_FAIL = 13;
    private static final String CHAT_SERVER_URL = "http://chat.polyv.net:8000";
    private static final String GET_TOKEN_URL_NEW = "http://api.live.polyv.net/v2/channels/%s/mic-auth/?%s";
    public static final String DISCONNECT_UNLOGOUT = "未注销";
    public static final String DISCONNECT_UNUNITED = "未连接";
    public static final String DISCONNECT_NET_EXCEPTION = "网络异常";
    /** @deprecated */
    public static final String DISCONNECT_SHIT = "已被禁言";
    public static final String DISCONNECT_SERVER_DECLINE = "连接服务器失败";
    public static final String DISCONNECT_APPID_APPSECRET_IS_EMPTY = "appId or appSecret is empty";
    public static final String DISCONNECT_SOCKET_EXCEPTION = "socket异常";
    public static final String DISCONNECT_JSON_EXCEPTION = "json异常";
    public static final String DISCONNECT_TOKEN_EXCEPTON = "token异常";
    private static final String DISCONNECT_INVALID_PARAMETER = "invalid parameter ";
    public static String appId;
    public static String appSecret;
    private static MyPolyvChatManager chatManager;
    private String token;
    private MyPolyvChatManager.ConnectStatus connect_status;
    private boolean canSendChatMessage;
    private boolean canSendQuestionMsg;
    private boolean canSendRequest;
    private boolean isCloseRoom;
    private boolean isKicked;
    private boolean isBanIp;
    private String loginJson;
    private Socket socket;
    private String roomId;
    private String userId;
    private String nickName;
    private String imageUrl;
    private String linkMicUid;
    private String socketId;
    private String recSocketId;
    private String lmsSocketId;
    private static String body;
    public static String status;
    private ExecutorService executorService;
    private MyPolyvChatHeartBeat chatHeartBeat;
    private NetUtil netUtil;
    private Handler handler;
    private Emitter.Listener onJoinResponse;
    private Emitter.Listener onJoinLeave;
    private Emitter.Listener onJoinSuccess;
    private Emitter.Listener onJoinRequest;
    private Emitter.Listener onConnect;
    private Emitter.Listener onDisconnect;
    private Emitter.Listener onConnectError;
    private Emitter.Listener onNewMessage;
    private MyPolyvChatManager.ChatManagerListener chatManagerListener;
    private MyPolyvChatManager.PPTDataListener pptDataListener;
    private MyPolyvChatManager.LinkMicrophoneListener linkMicrophoneListener;

    public static void initConfig(String var0, String var1) {
        appId = var0;
        appSecret = var1;
    }

    public MyPolyvChatManager() {
        this.connect_status = MyPolyvChatManager.ConnectStatus.DISCONNECT;
        this.handler = new Handler() {
            public void handleMessage(Message var1) {
                if(var1.what == 12) {
                    MyPolyvChatManager.this.login();
                } else {
                    MyPolyvChatManager.this.connectFail("网络异常");
                }

            }
        };
        this.onJoinResponse = new Emitter.Listener() {
            public void call(Object... var1) {
                try {
                    PolyvJoinResponseEntity var2 = PolyvJoinResponseEntity.jsonToObject((String)var1[0]);
                    if(MyPolyvChatManager.this.linkMicrophoneListener != null) {
                        MyPolyvChatManager.this.linkMicrophoneListener.joinResponse(var2);
                    }
                } catch (Exception var3) {
                    ;
                }

            }
        };
        this.onJoinLeave = new Emitter.Listener() {
            public void call(Object... var1) {
                try {
                    PolyvJoinLeaveEntity var2 = PolyvJoinLeaveEntity.jsonToObject((String)var1[0]);
                    if(MyPolyvChatManager.this.linkMicrophoneListener != null) {
                        MyPolyvChatManager.this.linkMicrophoneListener.joinLeave(var2);
                    }
                } catch (Exception var3) {
                    ;
                }

            }
        };
        this.onJoinSuccess = new Emitter.Listener() {
            public void call(Object... var1) {
                try {
                    PolyvJoinSuccessEntity var2 = PolyvJoinSuccessEntity.jsonToObject((String)var1[0]);
                    if(var2.user.uid.equals(MyPolyvChatManager.this.socket.id())) {
                        MyPolyvChatManager.this.lmsSocketId = MyPolyvChatManager.this.socket.id();
                    }

                    if(MyPolyvChatManager.this.linkMicrophoneListener != null) {
                        MyPolyvChatManager.this.linkMicrophoneListener.joinSuccess(var2);
                    }
                } catch (Exception var3) {
                    ;
                }

            }
        };
        this.onJoinRequest = new Emitter.Listener() {
            public void call(Object... var1) {
                try {
                    PolyvJoinRequestEntity var2 = PolyvJoinRequestEntity.jsonToObject((String)var1[0]);
                    if(MyPolyvChatManager.this.linkMicrophoneListener != null) {
                        MyPolyvChatManager.this.linkMicrophoneListener.joinRequest(var2);
                    }
                } catch (Exception var3) {
                    ;
                }

            }
        };
        this.onConnect = new Emitter.Listener() {
            public void call(Object... var1) {
                MyPolyvChatManager.this.socketId = MyPolyvChatManager.this.socket.id();
                MyPolyvChatManager.this.socket.emit("message", new Object[]{MyPolyvChatManager.this.loginJson});
            }
        };
        this.onDisconnect = new Emitter.Listener() {
            public void call(Object... var1) {
                if(((String)var1[0]).equals("io server disconnect")) {
                    MyPolyvChatManager.this.connectFail("连接服务器失败");
                } else {
                    MyPolyvChatManager.this.handler.post(new Runnable() {
                        public void run() {
                            MyPolyvChatManager.this.recSocketId = MyPolyvChatManager.this.socketId;
                            MyPolyvChatManager.this.disconnect(true, false);
                            MyPolyvChatManager.this.connect_status = MyPolyvChatManager.ConnectStatus.RECONNECTING;
                            MyPolyvChatManager.this.loginOrReconnect();
                        }
                    });
                }

            }
        };
        this.onConnectError = new Emitter.Listener() {
            public void call(Object... var1) {
            }
        };
        this.onNewMessage = new Emitter.Listener() {
            public void call(Object... var1) {
                String var2 = (String)var1[0];

                try {
                    JSONObject var3 = new JSONObject(var2);
                    String var4 = var3.optString("EVENT");
                    JSONObject var5 = var3.optJSONObject("user");
                    String var6;
                    if(var4.equals("LOGIN")) {
                        if(var5 != null) {
                            var6 = var5.optString("uid");
                            if(var6.equals(MyPolyvChatManager.this.socket.id())) {
                                MyPolyvChatManager.this.chatHeartBeat.start("https://apichat.polyv.net/front/heartbeat?uid=" + MyPolyvChatManager.this.socket.id());
                                MyPolyvChatManager.this.canSendChatMessage = true;
                                MyPolyvChatManager.this.canSendQuestionMsg = true;
                                MyPolyvChatManager.this.canSendRequest = true;
                                if(MyPolyvChatManager.this.connect_status != MyPolyvChatManager.ConnectStatus.DISCONNECT && MyPolyvChatManager.this.connect_status != MyPolyvChatManager.ConnectStatus.LOGINING) {
                                    if(MyPolyvChatManager.this.connect_status == MyPolyvChatManager.ConnectStatus.RECONNECTING) {
                                        MyPolyvChatManager.this.connect_status = MyPolyvChatManager.ConnectStatus.RECONNECTSUCCESS;
                                        MyPolyvChatManager.this.callConnectStatus(MyPolyvChatManager.this.connect_status);
                                    }
                                } else {
                                    MyPolyvChatManager.this.connect_status = MyPolyvChatManager.ConnectStatus.LOGINSUCCESS;
                                    MyPolyvChatManager.this.callConnectStatus(MyPolyvChatManager.this.connect_status);
                                }
                            }

                            MyPolyvChatManager.this.callReceiveChatMessageNotice(var3);
                        }
                    } else if(!var4.equals("LOGOUT") && !var4.equals("REMOVE_CONTENT") && !var4.equals("REMOVE_HISTORY")) {
                        if(var4.equals("onSliceID")) {
                            MyPolyvChatManager.this.callOnData(var4, var2);
                        } else if(!var4.equals("S_QUESTION") && !var4.equals("T_ANSWER")) {
                            if(var4.equals("SPEAK")) {
                                if(var5 != null) {
                                    var6 = var5.optString("userId");
                                    if(!MyPolyvChatManager.this.userId.equals(var6)) {
                                        MyPolyvChatManager.this.callReceiveChatMessage(PolyvChatMessage.fromJsonObject(var3));
                                    }
                                }
                            } else if(var4.equals("KICK")) {
                                if(var5 != null) {
                                    var6 = var5.optString("uid");
                                    if(var6.equals(MyPolyvChatManager.this.socket.id())) {
                                        MyPolyvChatManager.this.isKicked = true;
                                        MyPolyvChatManager.this.canSendChatMessage = !MyPolyvChatManager.this.isKicked && !MyPolyvChatManager.this.isBanIp && !MyPolyvChatManager.this.isCloseRoom;
                                        MyPolyvChatManager.this.canSendQuestionMsg = !MyPolyvChatManager.this.isKicked && !MyPolyvChatManager.this.isBanIp;
                                    }
                                }

                                MyPolyvChatManager.this.callReceiveChatMessageNotice(var3);
                            } else {
                                PolyvChatMessage var11;
                                if(!var4.equals("BANIP") && !var4.equals("UNSHIELD")) {
                                    if(var4.equals("CLOSEROOM")) {
                                        var11 = PolyvChatMessage.fromJsonObject(var3);
                                        if(var11 == null) {
                                            return;
                                        }

                                        var11.setChatType(2);
                                        MyPolyvChatManager.this.isCloseRoom = var11.getValue().isClosed();
                                        MyPolyvChatManager.this.canSendChatMessage = !MyPolyvChatManager.this.isKicked && !MyPolyvChatManager.this.isBanIp && !MyPolyvChatManager.this.isCloseRoom;
                                        MyPolyvChatManager.this.canSendQuestionMsg = !MyPolyvChatManager.this.isKicked && !MyPolyvChatManager.this.isBanIp;
                                        MyPolyvChatManager.this.callReceiveChatMessageNotice(var11);
                                    } else if(!var4.equals("GONGGAO") && !var4.equals("FLOWERS") && !var4.equals("REWARD") && !var4.equals("REDPAPER") && !var4.equals("GET_REDPAPER") && !var4.equals("LIKES")) {
                                        if(!var4.equals("onSliceStart") && !var4.equals("onSliceDraw") && !var4.equals("onSliceControl") && !var4.equals("onSliceOpen")) {
                                            if(var4.equals("OPEN_MICROPHONE")) {
                                                try {
                                                    PolyvMicrophoneEvent var12 = PolyvMicrophoneEvent.jsonToObject(var2);
                                                    if(MyPolyvChatManager.this.linkMicrophoneListener != null) {
                                                        MyPolyvChatManager.this.linkMicrophoneListener.onMicrophoneEvent(var12);
                                                    }
                                                } catch (Exception var9) {
                                                    ;
                                                }
                                            }
                                        } else {
                                            MyPolyvChatManager.this.callOnData(var4, var2);
                                        }
                                    } else {
                                        MyPolyvChatManager.this.callReceiveChatMessageNotice(var3);
                                    }
                                } else {
                                    var11 = PolyvChatMessage.fromJsonObject(var3);
                                    if(var11 == null || var11.banLists == null) {
                                        return;
                                    }

                                    Iterator var13 = var11.banLists.iterator();

                                    while(var13.hasNext()) {
                                        PolyvChatMessage.User var8 = (PolyvChatMessage.User)var13.next();
                                        if(var8.getUid().equals(MyPolyvChatManager.this.socket.id())) {
                                            MyPolyvChatManager.this.isBanIp = var4.equals("BANIP");
                                            MyPolyvChatManager.this.canSendChatMessage = !MyPolyvChatManager.this.isKicked && !MyPolyvChatManager.this.isBanIp && !MyPolyvChatManager.this.isCloseRoom;
                                            MyPolyvChatManager.this.canSendQuestionMsg = !MyPolyvChatManager.this.isKicked && !MyPolyvChatManager.this.isBanIp;
                                            break;
                                        }
                                    }

                                    var11.setChatType(2);
                                    MyPolyvChatManager.this.callReceiveChatMessageNotice(var11);
                                }
                            }
                        } else if(var5 != null) {
                            var6 = var5.optString("userId");
                            if(!MyPolyvChatManager.this.userId.equals(var6)) {
                                PolyvChatMessage var7 = PolyvChatMessage.fromJsonObject(var3);
                                var7.setChatType(3);
                                MyPolyvChatManager.this.callReceiveChatMessageNotice(var7);
                            }
                        }
                    } else {
                        MyPolyvChatManager.this.callReceiveChatMessageNotice(var3);
                    }
                } catch (JSONException var10) {
                    ;
                }

            }
        };
        this.chatHeartBeat = new MyPolyvChatHeartBeat();
    }

    public MyPolyvChatManager.ConnectStatus getConnectStatus() {
        return this.connect_status;
    }

    public boolean sendJoinLeave(String var1) {
        return this.sendJoinLeave(var1, false);
    }

    public boolean sendJoinLeave(String var1, boolean var2) {
        if(this.canSendRequest) {
            JSONObject var3 = new JSONObject();
            JSONObject var4 = new JSONObject();

            try {
                var3.put("roomId", this.roomId);
                var4.put("nick", this.nickName);
                var4.put("pic", this.imageUrl);
                var4.put("userId", var1);
                var4.put("userType", "slice");
                var3.put("user", var4);
            } catch (JSONException var6) {
                return false;
            }

            this.socket.emit("joinLeave", new Object[]{var3.toString()});
            this.linkMicUid = null;
            this.clearLMSSocketId();
            return true;
        } else {
            if(var2) {
                this.clearLMSSocketId();
            }

            return false;
        }
    }

    public boolean sendJoinRequest(String var1) {
        if(this.canSendRequest) {
            JSONObject var2 = new JSONObject();
            JSONObject var3 = new JSONObject();

            try {
                var2.put("roomId", this.roomId);
                var3.put("nick", this.nickName);
                var3.put("pic", this.imageUrl);
                var3.put("userId", var1);
                var3.put("userType", "slice");
                var2.put("user", var3);
            } catch (JSONException var5) {
                return false;
            }

            this.socket.emit("joinRequest", new Object[]{var2.toString()});
            this.linkMicUid = var1;
            return true;
        } else {
            return false;
        }
    }

    public boolean sendQuestionMsg(PolyvChatMessage var1) {
        if(this.canSendQuestionMsg) {
            JSONObject var2 = new JSONObject();

            try {
                var2.put("EVENT", "S_QUESTION");
                var2.put("content", var1.getValues()[0]);
                JSONObject var3 = new JSONObject();
                var3.put("actor", "学生");
                var3.put("nick", this.nickName);
                var3.put("pic", this.imageUrl);
                var3.put("userId", this.userId);
                var3.put("userType", "student");
                var2.put("user", var3);
                var2.put("roomId", this.roomId);
            } catch (JSONException var5) {
                return false;
            }

            long var6 = System.currentTimeMillis();
            this.socket.emit("message", new Object[]{var2.toString()});
            var1.setTime(var6);
            var1.setSendSuccess(true);
            return true;
        } else {
            return false;
        }
    }

    public boolean sendChatMessage(PolyvChatMessage var1) {
        if(this.canSendChatMessage) {
            JSONObject var2 = new JSONObject();
            JSONArray var3 = new JSONArray();

            try {
                var2.put("EVENT", "SPEAK");
                var3.put(0, var1.getValues()[0]);
                var2.put("values", var3);
                var2.put("roomId", this.roomId);
            } catch (JSONException var6) {
                return false;
            }

            long var4 = System.currentTimeMillis();
            this.socket.emit("message", new Object[]{var2.toString()});
            var1.setTime(var4);
            var1.setSendSuccess(true);
            return true;
        } else {
            return false;
        }
    }

    public boolean sendLikesMsg(PolyvChatMessage var1) {
        if(this.canSendChatMessage) {
        JSONObject var2 = new JSONObject();
        try {
            var2.put("EVENT", "LIKES");
            var2.put("content", var1.getValues()[0]);
            JSONObject var3 = new JSONObject();
            var3.put("nick", this.nickName);
            var3.put("userId", this.userId);
            var2.put("user", var3);
            var2.put("nick", this.nickName);
            var2.put("roomId", this.roomId);
        } catch (JSONException var5) {
            return false;
        }

        String jsonStr=var2.toString();
        long var6 = System.currentTimeMillis();
        this.socket.emit("message", new Object[]{jsonStr});
        var1.setTime(var6);
        var1.setSendSuccess(true);
            return true;
        } else {
            return false;
        }
    }

    public boolean sendChatMsg(PolyvChatMessage var1) {
        if(this.canSendChatMessage) {
            JSONObject var2 = new JSONObject();
            JSONArray var3 = new JSONArray();

            try {
                var2.put("EVENT", "SPEAK");
                var3.put(0, var1.getValues()[0].toString().replaceAll("\n", "\\\\n").replaceAll("\"", "''"));
                var2.put("values", var3);
                var2.put("roomId", this.roomId);
            } catch (JSONException var6) {
                return false;
            }

            long var4 = System.currentTimeMillis();
            this.socket.emit("message", new Object[]{var2.toString()});
            var1.setTime(var4);
            var1.setSendSuccess(true);
            return true;
        } else {
            return false;
        }
    }

    /** @deprecated */
    public static MyPolyvChatManager getInstance() {
        if(chatManager == null) {
            Class var0 = MyPolyvChatManager.class;
            synchronized(MyPolyvChatManager.class) {
                if(chatManager == null) {
                    chatManager = new MyPolyvChatManager();
                }
            }
        }

        return chatManager;
    }

    public void disconnect() {
        this.disconnect(true, true);
    }

    public void reset() {
        this.disconnect(true, false);
    }

    private void disconnect(boolean var1, boolean var2) {
        this.chatHeartBeat.shutdownSchedule();
        this.handler.removeMessages(13);
        this.handler.removeMessages(12);
        if(var1) {
            this.connect_status = MyPolyvChatManager.ConnectStatus.DISCONNECT;
        }

        PolyvChatMessage.resetLastTime();
        if(this.socket != null) {
            if(var2 && this.linkMicUid != null) {
                this.sendJoinLeave(this.linkMicUid);
            }

            this.canSendChatMessage = false;
            this.canSendQuestionMsg = false;
            this.canSendRequest = false;
            this.socket.disconnect();
            this.socket.off("connect", this.onConnect);
            this.socket.off("disconnect", this.onDisconnect);
            this.socket.off("connect_error", this.onConnectError);
            this.socket.off("connect_timeout", this.onConnectError);
            this.socket.off("message", this.onNewMessage);
            this.socket.off("joinRequest", this.onJoinRequest);
            this.socket.off("joinResponse", this.onJoinResponse);
            this.socket.off("joinSuccess", this.onJoinSuccess);
            this.socket.off("joinLeave", this.onJoinLeave);
        }

        if(this.netUtil != null) {
            this.netUtil.shutdown(this.executorService);
        } else if(this.executorService != null) {
            this.executorService.shutdownNow();
        }

        this.netUtil = null;
        this.executorService = null;
        if(var2) {
            this.pptDataListener = null;
            this.chatManagerListener = null;
            this.linkMicrophoneListener = null;
        }

    }

    private void connectFail(String var1) {
        MyPolyvChatManager.ConnectStatus var2 = MyPolyvChatManager.ConnectStatus.DISCONNECT;
        var2.setDescribe(var1);
        this.connect_status = var2;
        this.disconnect(false, false);
        this.callConnectStatus(this.connect_status);
    }

    private void getToken() {
        if(!TextUtils.isEmpty(appId) && !TextUtils.isEmpty(appSecret)) {
            String var1 = System.currentTimeMillis() + "";
            String var2 = Md5Util.getMd5(appSecret + "appId" + appId + "timestamp" + var1 + appSecret).toUpperCase();
            StringBuilder var3 = new StringBuilder();
            var3.append("appId=").append(appId).append("&timestamp=").append(var1).append("&sign=").append(var2);
            String var4 = String.format("http://api.live.polyv.net/v2/channels/%s/mic-auth/?%s", new Object[]{this.roomId, var3.toString()});
            if(this.executorService == null) {
                this.executorService = Executors.newSingleThreadExecutor();
                this.netUtil = NetUtil.with(var4, "GET", 2147483647, false, true);
            }

            this.executorService.submit(new Runnable() {
                public void run() {
                    ExecutorService var1 = MyPolyvChatManager.this.executorService;
                    String var2 = MyPolyvChatManager.this.netUtil.getData();
                    if(var1 == MyPolyvChatManager.this.executorService) {
                        if(var2 == null) {
                            MyPolyvChatManager.this.handler.sendEmptyMessage(13);
                        } else if(var2.equals("")) {
                            MyPolyvChatManager.this.connectFail("token异常");
                        } else if(NetUtil.isFailData(var2)) {
                            MyPolyvChatManager.this.connectFail(var2);
                        } else if(!NetUtil.isRequestInterrupt(var2)) {
                            try {
                                JSONObject var3 = new JSONObject(var2);
                                int var4 = var3.optInt("code");
                                if(var4 != 200) {
                                    MyPolyvChatManager.this.connectFail(var3.optString("message"));
                                } else {
                                    String var5 = var3.optString("data");
                                    SecretKeySpec var6 = new SecretKeySpec("polyvliveSDKAuth".getBytes(), "AES");
                                    IvParameterSpec var7 = new IvParameterSpec("1111000011110000".getBytes());
                                    Cipher var8 = Cipher.getInstance("AES/CBC/PKCS5Padding");
                                    var8.init(2, var6, var7);
                                    byte[] var9 = var8.doFinal(Hex.decodeHex(var5.toCharArray()));
                                    String var10 = new String(Base64.decode(var9, 0), "UTF-8");
                                    JSONObject var11 = new JSONObject(var10);
                                    MyPolyvChatManager.this.token = var11.optString("chat_token");
                                    MyPolyvChatManager.status = var11.optString("connect_channel_key");
                                    MyPolyvChatManager.body = var5;
                                    MyPolyvChatManager.this.handler.sendEmptyMessage(12);
                                }
                            } catch (Exception var12) {
                                MyPolyvChatManager.this.connectFail(var12.getMessage());
                            }
                        }
                    }

                }
            });
        } else {
            this.connectFail("appId or appSecret is empty");
        }
    }

    void delayLogin() {
        this.login(this.userId, this.roomId, this.nickName, this.imageUrl);
    }

    public void pptLogin(String var1, String var2, String var3) {
        this.pptLogin(var1, var2, var3, "http://www.polyv.net/images/effect/effect-device.png");
    }

    public void pptLogin(String var1, String var2, String var3, String var4) {
        this.userId = var1;
        this.roomId = var2;
        this.nickName = var3;
        this.imageUrl = var4;
    }

    public void login(String var1, String var2, String var3) {
        this.login(var1, var2, var3, "http://www.polyv.net/images/effect/effect-device.png");
    }

    private boolean verifyParameters(String var1, String var2, String var3, String var4) {
        MyPolyvChatManager.ConnectStatus var5 = MyPolyvChatManager.ConnectStatus.DISCONNECT;
        if(TextUtils.isEmpty(var1)) {
            var5.setDescribe("invalid parameter userId is empty");
            this.callConnectStatus(var5);
            return false;
        } else if(TextUtils.isEmpty(var2)) {
            var5.setDescribe("invalid parameter roomId is empty");
            this.callConnectStatus(var5);
            return false;
        } else if(TextUtils.isEmpty(var3)) {
            var5.setDescribe("invalid parameter nickName is empty");
            this.callConnectStatus(var5);
            return false;
        } else if(TextUtils.isEmpty(var4)) {
            var5.setDescribe("invalid parameter imageUrl is empty");
            this.callConnectStatus(var5);
            return false;
        } else if(!Patterns.WEB_URL.matcher(var4).matches()) {
            var5.setDescribe("invalid parameter imageUrl is " + var4);
            this.callConnectStatus(var5);
            return false;
        } else {
            return true;
        }
    }

    public void login(String var1, String var2, String var3, String var4) {
        if(this.verifyParameters(var1, var2, var3, var4)) {
            if(this.connect_status != MyPolyvChatManager.ConnectStatus.DISCONNECT) {
                MyPolyvChatManager.ConnectStatus var5 = MyPolyvChatManager.ConnectStatus.DISCONNECT;
                var5.setDescribe("未注销");
                this.callConnectStatus(var5);
            } else {
                this.userId = var1;
                this.roomId = var2;
                this.nickName = var3;
                this.imageUrl = var4;
                this.loginOrReconnect();
            }
        }
    }

    private void loginOrReconnect() {
        if(this.connect_status == MyPolyvChatManager.ConnectStatus.DISCONNECT) {
            this.connect_status = MyPolyvChatManager.ConnectStatus.LOGINING;
            this.callConnectStatus(this.connect_status);
        } else if(this.connect_status == MyPolyvChatManager.ConnectStatus.RECONNECTING) {
            this.callConnectStatus(this.connect_status);
        }

        this.getToken();
    }

    private void login() {
        try {
            IO.Options var1 = new IO.Options();
            var1.query = "token=" + this.token;
            var1.transports = new String[]{"websocket"};
            this.socket = IO.socket("http://chat.polyv.net:8000", var1);
        } catch (URISyntaxException var5) {
            this.connectFail("socket异常");
            return;
        }

        JSONObject var6 = new JSONObject();
        JSONArray var2 = new JSONArray();

        try {
            var6.put("EVENT", "LOGIN");
            var2.put(0, this.nickName);
            var2.put(1, this.imageUrl);
            var2.put(2, this.userId);
            var6.put("values", var2);
            var6.put("roomId", this.roomId);
            var6.put("type", "slice");
            this.loginJson = var6.toString();
        } catch (JSONException var4) {
            this.connectFail("json异常");
            return;
        }

        this.socket.on("connect", this.onConnect);
        this.socket.on("disconnect", this.onDisconnect);
        this.socket.on("connect_error", this.onConnectError);
        this.socket.on("connect_timeout", this.onConnectError);
        this.socket.on("joinRequest", this.onJoinRequest);
        this.socket.on("joinSuccess", this.onJoinSuccess);
        this.socket.on("joinResponse", this.onJoinResponse);
        this.socket.on("joinLeave", this.onJoinLeave);
        this.socket.on("message", this.onNewMessage);
        this.socket.connect();
    }

    public boolean isUsedUid(String var1) {
        return var1 == null?false:var1.equals(this.recSocketId) || var1.equals(this.socketId);
    }

    public boolean isRequestStatus() {
        return this.linkMicUid != null;
    }

    public String getChannelId() {
        return this.roomId;
    }

    public String getUserType() {
        return "slice";
    }

    public String getNickName() {
        return this.nickName;
    }

    public String getPic() {
        return this.imageUrl;
    }

    public String getUid() {
        return this.socketId;
    }

    public String getRecUid() {
        return this.recSocketId;
    }

    public String getLMSSocketId() {
        return this.lmsSocketId;
    }

    public void clearLMSSocketId() {
        this.lmsSocketId = null;
    }

    public String getJRLinkMicUid() {
        return this.linkMicUid;
    }

    public String getUserId() {
        return this.userId;
    }

    private void callReceiveChatMessageNotice(JSONObject var1) {
        PolyvChatMessage var2 = PolyvChatMessage.fromJsonObject(var1);
        if(var2 != null) {
            var2.setChatType(2);
            this.callReceiveChatMessageNotice(var2);
        }
    }

    private void callConnectStatus(MyPolyvChatManager.ConnectStatus var1) {
        if(this.chatManagerListener != null) {
            this.chatManagerListener.connectStatus(var1);
        }

    }

    private void callReceiveChatMessage(PolyvChatMessage var1) {
        if(this.chatManagerListener != null && var1.getValues() != null) {
            this.chatManagerListener.receiveChatMessage(var1);
        }

    }

    private void callReceiveChatMessageNotice(PolyvChatMessage var1) {
        if(this.chatManagerListener != null && var1 != null) {
            this.chatManagerListener.receiveChatMessage(var1);
        }

    }

    private void callOnData(String var1, String var2) {
        if(this.pptDataListener != null) {
            this.pptDataListener.onData(this.roomId, var1, var2);
        }

    }

    public void setLinkMicrophoneListener(MyPolyvChatManager.LinkMicrophoneListener var1) {
        this.linkMicrophoneListener = var1;
    }

    void setPptDataListener(MyPolyvChatManager.PPTDataListener var1) {
        this.pptDataListener = var1;
    }


    public void setOnChatManagerListener(MyPolyvChatManager.ChatManagerListener var1) {
        this.chatManagerListener = var1;
    }

    public static class WorkerThread extends Thread {
        private final Context mContext;
        private static final int ACTION_WORKER_THREAD_QUIT = 4112;
        private static final int ACTION_WORKER_JOIN_CHANNEL = 8208;
        private static final int ACTION_WORKER_LEAVE_CHANNEL = 8209;
        private static final int ACTION_WORKER_CONFIG_ENGINE = 8210;
        private static final int ACTION_WORKER_PREVIEW = 8212;
        private MyPolyvChatManager.WorkerThread.WorkerThreadHandler mWorkerHandler;
        private boolean mReady;
        private RtcEngine mRtcEngine;
        private EngineConfig mEngineConfig;
        private final MyEngineEventHandler mEngineEventHandler;

        public final void waitForReady() {
            while(!this.mReady) {
                try {
                    Thread.sleep(20L);
                } catch (InterruptedException var2) {
                    var2.printStackTrace();
                }
            }

        }

        public void run() {
            Looper.prepare();
            this.mWorkerHandler = new MyPolyvChatManager.WorkerThread.WorkerThreadHandler(this);
            this.mReady = true;
            Looper.loop();
        }

        public final void enablePreProcessor() {
        }

        public final void setPreParameters(float var1, int var2) {
            Constant.PRP_DEFAULT_LIGHTNESS = var1;
            Constant.PRP_DEFAULT_SMOOTHNESS = var2;
        }

        public final void disablePreProcessor() {
        }

        public final void joinChannel(String var1, int var2) {
            if(Thread.currentThread() != this) {
                Message var3 = new Message();
                var3.what = 8208;
                var3.obj = new String[]{var1};
                var3.arg1 = var2;
                this.mWorkerHandler.sendMessage(var3);
            } else {
                this.ensureRtcEngineReadyLock();
                this.mRtcEngine.joinChannel(MyPolyvChatManager.status, var1, "OpenVCall", var2);
                this.mEngineConfig.mChannel = var1;
                this.enablePreProcessor();
            }
        }

        public final void leaveChannel(String var1) {
            if(Thread.currentThread() != this) {
                Message var2 = new Message();
                var2.what = 8209;
                var2.obj = var1;
                this.mWorkerHandler.sendMessage(var2);
            } else {
                if(this.mRtcEngine != null) {
                    this.mRtcEngine.leaveChannel();
                }

                this.disablePreProcessor();
            }
        }

        public final EngineConfig getEngineConfig() {
            return this.mEngineConfig;
        }

        public final void configEngine(int var1, String var2, String var3) {
            this.configEngine(var1, var2, var3, (SurfaceView)null);
        }

        public final void configEngine(int var1, String var2, String var3, SurfaceView var4) {
            if(Thread.currentThread() != this) {
                Message var5 = new Message();
                var5.what = 8210;
                var5.obj = new Object[]{Integer.valueOf(var1), var2, var3, var4};
                this.mWorkerHandler.sendMessage(var5);
            } else {
                this.ensureRtcEngineReadyLock();
                this.mEngineConfig.mVideoProfile = var1;
                if(!TextUtils.isEmpty(var2)) {
                    this.mRtcEngine.setEncryptionMode(var3);
                    this.mRtcEngine.setEncryptionSecret(var2);
                }

                this.mRtcEngine.setVideoProfile(this.mEngineConfig.mVideoProfile, false);
                if(var4 != null) {
                    this.mRtcEngine.setupLocalVideo(new VideoCanvas(var4, 1, this.mEngineConfig.mUid));
                }

            }
        }

        public final void preview(boolean var1, SurfaceView var2, int var3) {
            if(Thread.currentThread() != this) {
                Message var4 = new Message();
                var4.what = 8212;
                var4.obj = new Object[]{Boolean.valueOf(var1), var2, Integer.valueOf(var3)};
                this.mWorkerHandler.sendMessage(var4);
            } else {
                this.ensureRtcEngineReadyLock();
                if(var1) {
                    this.mRtcEngine.setupLocalVideo(new VideoCanvas(var2, 1, var3));
                    this.mRtcEngine.startPreview();
                } else {
                    this.mRtcEngine.stopPreview();
                }

            }
        }

        public static String getDeviceID(Context var0) {
            return Settings.Secure.getString(var0.getContentResolver(), "android_id");
        }

        private RtcEngine ensureRtcEngineReadyLock() {
            if(this.mRtcEngine == null) {
                String var1 = "abcdefghijklmnopqrstuvwxyz123456";

                try {
                    SecretKeySpec var2 = new SecretKeySpec("polyvliveSDKAuth".getBytes(), "AES");
                    IvParameterSpec var3 = new IvParameterSpec("1111000011110000".getBytes());
                    Cipher var4 = Cipher.getInstance("AES/CBC/PKCS5Padding");
                    var4.init(2, var2, var3);
                    byte[] var5 = var4.doFinal(Hex.decodeHex(MyPolyvChatManager.body.toCharArray()));
                    String var6 = new String(Base64.decode(var5, 0), "UTF-8");
                    var1 = (new JSONObject(var6)).optString("connect_appId");
                } catch (Exception var8) {
                    ;
                }

                try {
                    this.mRtcEngine = RtcEngine.create(this.mContext, var1, this.mEngineEventHandler.mRtcEventHandler);
                } catch (Exception var7) {
                    var7.printStackTrace();
                }

                this.mEngineEventHandler.init(this.mRtcEngine);
                this.mRtcEngine.setChannelProfile(0);
                this.mRtcEngine.enableVideo();
            }

            return this.mRtcEngine;
        }

        public MyEngineEventHandler eventHandler() {
            return this.mEngineEventHandler;
        }

        public RtcEngine getRtcEngine() {
            return this.mRtcEngine;
        }

        public final void exit() {
            if(this.mReady) {
                if(Thread.currentThread() != this) {
                    this.mWorkerHandler.sendEmptyMessage(4112);
                    return;
                }

                this.mReady = false;
                Looper.myLooper().quit();
                this.mWorkerHandler.release();
            }

        }

        public WorkerThread(Context var1) {
            this.mContext = var1;
            this.mEngineConfig = new EngineConfig();
            this.mEngineConfig.mUid = Math.abs((int)System.currentTimeMillis());
            this.mEngineEventHandler = new MyEngineEventHandler(this.mContext, this.mEngineConfig);
        }

        private final class WorkerThreadHandler extends Handler {
            private MyPolyvChatManager.WorkerThread mWorkerThread;

            WorkerThreadHandler(MyPolyvChatManager.WorkerThread var2) {
                this.mWorkerThread = var2;
            }

            public void release() {
                this.mWorkerThread = null;
            }

            public void handleMessage(Message var1) {
                if(this.mWorkerThread != null) {
                    switch(var1.what) {
                        case 4112:
                            this.mWorkerThread.exit();
                            break;
                        case 8208:
                            String[] var2 = (String[])((String[])var1.obj);
                            this.mWorkerThread.joinChannel(var2[0], var1.arg1);
                            break;
                        case 8209:
                            String var3 = (String)var1.obj;
                            this.mWorkerThread.leaveChannel(var3);
                            break;
                        case 8210:
                            Object[] var4 = (Object[])((Object[])var1.obj);
                            this.mWorkerThread.configEngine(((Integer)var4[0]).intValue(), (String)var4[1], (String)var4[2], (SurfaceView)var4[3]);
                            break;
                        case 8212:
                            Object[] var5 = (Object[])((Object[])var1.obj);
                            this.mWorkerThread.preview(((Boolean)var5[0]).booleanValue(), (SurfaceView)var5[1], ((Integer)var5[2]).intValue());
                    }

                }
            }
        }
    }

    public interface ChatManagerListener {
        void connectStatus(MyPolyvChatManager.ConnectStatus var1);

        void receiveChatMessage(PolyvChatMessage var1);
    }

    interface PPTDataListener {
        void onData(String var1, String var2, String var3);
    }

    public interface LinkMicrophoneListener {
        void joinLeave(PolyvJoinLeaveEntity var1);

        void joinRequest(PolyvJoinRequestEntity var1);

        void joinResponse(PolyvJoinResponseEntity var1);

        void joinSuccess(PolyvJoinSuccessEntity var1);

        void onMicrophoneEvent(PolyvMicrophoneEvent var1);
    }

    public static enum ConnectStatus {
        DISCONNECT("未连接"),
        LOGINING("登录中"),
        LOGINSUCCESS("登录成功"),
        RECONNECTING("重连中"),
        RECONNECTSUCCESS("重连成功");

        private String describe;

        private ConnectStatus(String var3) {
            this.describe = var3;
        }

        public String getDescribe() {
            return this.describe;
        }

        public void setDescribe(String var1) {
            this.describe = var1;
        }
    }
}

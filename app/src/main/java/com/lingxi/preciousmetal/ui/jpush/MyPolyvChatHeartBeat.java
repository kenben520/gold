package com.lingxi.preciousmetal.ui.jpush;

import com.easefun.polyvsdk.live.chat.util.NetUtilApiH2;

/**
 * Created by zhangwei on 2018/7/20.
 */

public class MyPolyvChatHeartBeat extends NetUtilApiH2 {
    static final String HEARTBEAT_URL = "https://apichat.polyv.net/front/heartbeat?";

    public MyPolyvChatHeartBeat() {
    }

    public void start(String var1) {
        this.start(var1, 1);
    }

    void start(String var1, int var2) {
        this.init(var1, "GET", var2, false, true);
        this.only_getData(0L, 60000L);
    }
}

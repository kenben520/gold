package com.lingxi.preciousmetal.common;

/**
 * Created by zhangwei on 2018/4/23.
 */

public class EventBusKeyDefine {
    public static int EVENTBUS_LOGIN_SUCCESS = 1;
    public static int EVENTBUS_LOGOUT = 2;
    public static int EVENTBUS_USER_INFO_UPDATE = 3;
    public static int EVENTBUS_REGISTER_SUCCESS = 4;
    public static int EVENTBUS_PROGRAM_LIST = 5;
    public static int EVENTBUS_LIVE_TEACHER = 6;
    public static int EVENTBUS_REFRESH_LIVE_TEACHER = 7;
    public static int EVENTBUS_RECEIVE_ANSWER = 8;
    public static int EVENTBUS_LIVE_PEOPLE_COUNT = 9;
    public static int EVENTBUS_CHATROOM_UNREAD_COUNT = 10;
    public static int EVENTBUS_SWITCH_GOODS_NAME = 11;
    public static int EVENTBUS_LIVE_STATUS = 12;
    public static int EVENTBUS_TEACHER_LIKE = 13;
    public static int EVENTBUS_LIVE_NETSTATE = 14;//直播间弱网提示
    public static int EVENTBUS_KEFU_VIEW_FRESH = 15;

    public static class EventBusMsgData<T> {
        public int type;
        public T data;

        public EventBusMsgData(int type, T data) {
            this.type = type;
            this.data = data;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }
    }
}

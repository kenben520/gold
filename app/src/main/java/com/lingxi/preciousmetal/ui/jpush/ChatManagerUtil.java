package com.lingxi.preciousmetal.ui.jpush;

public class ChatManagerUtil {
    private static ChatManagerUtil chatManagerUtil;
    private static MyPolyvChatManager chatManager;

    private ChatManagerUtil() {
    }

    public static ChatManagerUtil getInstance() {
        if (chatManagerUtil == null) {
            chatManagerUtil = new ChatManagerUtil();
        }
        return chatManagerUtil;
    }

    public MyPolyvChatManager getChatManager() {
        if (chatManager == null) {
            chatManager = new MyPolyvChatManager();
        }
        return chatManager;
    }

    public void disconnect() {
        chatManager.disconnect();
        chatManager = null;
    }
}

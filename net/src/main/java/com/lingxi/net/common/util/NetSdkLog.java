package com.lingxi.net.common.util;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangwei on 2018/3/29.
 */
public class NetSdkLog {

    private static final String TAG = "netsdk.NetSdkLog";
    private static boolean printLog = false;
    private static LogEnable logEnable = LogEnable.DebugEnable;
    private static Map<String, LogEnable> logEnabaleMap = new HashMap<String, LogEnable>(5);

    public enum LogEnable {

        VerboseEnable("V"), DebugEnable("D"), InfoEnable("I"), WarnEnable("W"), ErrorEnable("E"), NoneEnable("L");
        private String logEnable;

        public String getLogEnable() {
            return logEnable;
        }

        private LogEnable(String logEnable) {
            this.logEnable = logEnable;
        }
    }

    static {
        for (LogEnable logEnable : LogEnable.values()) {
            logEnabaleMap.put(logEnable.getLogEnable(), logEnable);
        }
    }

    public static void setPrintLog(boolean printLog) {
        NetSdkLog.printLog = printLog;
        Log.d(TAG, "[setPrintLog] printLog=" + printLog);
    }

    public static boolean isPrintLog() {
        return printLog;
    }


    public static void setLogEnable(LogEnable logEnable) {
        if (logEnable != null) {
            NetSdkLog.logEnable = logEnable;
            Log.d(TAG, "[setLogEnable] logEnable=" + logEnable);
        }
    }

    public static void d(String tag, String msg) {
        if (isLogEnable(LogEnable.DebugEnable)) {
            if (printLog) {
                Log.d(tag, append(msg));
            }
        }

    }

    public static void d(String tag, String... msg) {
        if (isLogEnable(LogEnable.DebugEnable)) {
            if (printLog) {
                Log.d(tag, append(msg));
            }
        }
    }


    public static void i(String tag, String msg) {
        if (isLogEnable(LogEnable.InfoEnable)) {
            if (printLog) {
                Log.i(tag, append(msg));
            }
        }
    }

    public static void i(String tag, String... msg) {
        if (isLogEnable(LogEnable.InfoEnable)) {
            if (printLog) {
                Log.i(tag, append(msg));
            }
        }
    }


    public static void w(String tag, String msg) {
        if (isLogEnable(LogEnable.WarnEnable)) {
            if (printLog) {
                Log.w(tag, append(msg));
            }
        }
    }

    public static void w(String tag, String msg, Throwable t) {
        if (isLogEnable(LogEnable.WarnEnable)) {
            if (printLog) {
                Log.w(tag, append(msg), t);
            }
        }
    }

    public static void e(String tag, String msg) {
        if (isLogEnable(LogEnable.ErrorEnable)) {
            if (printLog) {
                Log.e(tag, append(msg));
            }
        }
    }

    public static void e(String tag, String msg, Throwable t) {
        if (isLogEnable(LogEnable.ErrorEnable)) {
            if (printLog) {
                Log.e(tag, append(msg), t);
            }
        }
    }


    private static String append(String... msg) {
        StringBuilder builder = new StringBuilder();
        if (null != msg) {
            for (int i = 0; i < msg.length; i++) {
                builder.append(msg[i]);
                if (i < msg.length - 1) {
                    builder.append(",");
                }
            }
        }
        return builder.toString();
    }


    private String lTag = null;

    private StringBuilder logBuf = null;

    public NetSdkLog(String tag) {
        lTag = tag;
        logBuf = new StringBuilder();
    }


    public void d() {
        if (isLogEnable(LogEnable.DebugEnable)) {
            if (printLog) {
                Log.d(lTag, logBuf.toString());
            }
        }
    }

    public void i() {
        if (isLogEnable(LogEnable.InfoEnable)) {
            if (printLog) {
                Log.i(lTag, logBuf.toString());
            }
        }
    }

    public void w() {
        if (isLogEnable(LogEnable.WarnEnable)) {
            if (printLog) {
                Log.w(lTag, logBuf.toString());
            }
        }
    }

    public void w(Throwable t) {
        if (isLogEnable(LogEnable.WarnEnable)) {
            if (printLog) {
                Log.w(lTag, logBuf.toString(), t);
            }
        }
    }

    public void e() {
        if (isLogEnable(LogEnable.ErrorEnable)) {
            if (printLog) {
                Log.e(lTag, logBuf.toString());
            }
        }
    }

    public void e(Throwable t) {
        if (isLogEnable(LogEnable.ErrorEnable)) {
            if (printLog) {
                Log.e(lTag, logBuf.toString(), t);
            }
        }
    }


    public static boolean isLogEnable(LogEnable logEnable) {
        return logEnable.ordinal() >= NetSdkLog.logEnable.ordinal();
    }

}

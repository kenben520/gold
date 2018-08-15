package com.lingxi.preciousmetal.domain;

import com.lingxi.common.base.BaseApplication;
import com.lingxi.common.util.StringUtil;
import com.lingxi.preciousmetal.util.utilCode.ObjectUtils;
import com.lingxi.preciousmetal.util.utilCode.SPUtils;

import static com.lingxi.preciousmetal.common.CommonParam.SIGNAL_PARAMS_SET;

/**
 * Created by zhangwei on 2018/7/10.
 */

public class SignalParamValues {
    public BBI bbi;
    public BOLL boll;
    public MA ma;
    public MIKE mike;
    public PBX pbx;
    public ARBR arbr;
    public ATR atr;
    public BIAS bias;
    public CCI cci;
    public DKBY dkby;
    public KD kd;
    public KDJ kdj;
    public LWR lwr;
    public MACD macd;
    public QHLSR qhlsr;
    public RSI rsi;
    public WR wr;

    public BBI getBbi() {
        return bbi;
    }

    public static SignalParamValues create() {
        SignalParamValues signalParamValues = null;
        String signalParamValuesStr = SPUtils.getInstance().getString(SIGNAL_PARAMS_SET);
        if (!StringUtil.isEmpty(signalParamValuesStr)) {
            signalParamValues = BaseApplication.getInstance().mCustomJsonConverter.parseJson(signalParamValuesStr, SignalParamValues.class);
        }
        if (ObjectUtils.isEmpty(signalParamValues)) {
            signalParamValues = new SignalParamValues();

            signalParamValues.bbi = new BBI();
            signalParamValues.boll = new BOLL();
            signalParamValues.ma = new MA();
            signalParamValues.mike = new MIKE();
            signalParamValues.pbx = new PBX();
            signalParamValues.arbr = new ARBR();
            signalParamValues.atr = new ATR();
            signalParamValues.bias = new BIAS();
            signalParamValues.cci = new CCI();
            signalParamValues.dkby = new DKBY();
            signalParamValues.kd = new KD();
            signalParamValues.kdj = new KDJ();
            signalParamValues.lwr = new LWR();
            signalParamValues.macd = new MACD();
            signalParamValues.qhlsr = new QHLSR();
            signalParamValues.rsi = new RSI();
            signalParamValues.wr = new WR();
            SPUtils.getInstance().put(SIGNAL_PARAMS_SET, BaseApplication.getInstance().mCustomJsonConverter.toJson(signalParamValues));
        }
        return signalParamValues;
    }

    public static void save(SignalParamValues signalParamValues) {
        SPUtils.getInstance().put(SIGNAL_PARAMS_SET, BaseApplication.getInstance().mCustomJsonConverter.toJson(signalParamValues));
    }

    public static class BBI {
        public int value1 = 3;
        public int value2 = 6;
        public int value3 = 12;
        public int value4 = 24;
    }

    public static class BOLL {
        public int value1 = 26;
        public int value2 = 2;
    }

    public static class MA {
        public int value1 = 5;
        public int value2 = 10;
        public int value3 = 20;
        public int value4 = 30;
    }

    public static class MIKE {
        public int value1 = 12;
    }

    public static class PBX {
    }

    public static class ARBR {
        public int value1 = 26;
    }

    public static class ATR {
        public int value1 = 14;
    }

    public static class BIAS {
        public int value1 = 6;
        public int value2 = 12;
        public int value3 = 24;
    }

    public static class CCI {
        public int value1 = 14;
    }

    public static class DKBY {
    }

    public static class KD {
        public int value1 = 9;
        public int value2 = 3;
        public int value3 = 3;
    }

    public static class KDJ {
        public int value1 = 9;
        public int value2 = 3;
        public int value3 = 3;
    }

    public static class LWR {
        public int value1 = 9;
        public int value2 = 3;
        public int value3 = 3;
    }

    public static class MACD {
        public int value1 = 26;
        public int value2 = 12;
        public int value3 = 9;
    }

    public static class QHLSR {

    }

    public static class RSI {
        public int value1 = 6;
        public int value2 = 12;
        public int value3 = 24;
    }

    public static class WR {
        public int value1 = 14;
    }

}


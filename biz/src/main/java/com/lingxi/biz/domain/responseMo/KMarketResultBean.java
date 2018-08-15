package com.lingxi.biz.domain.responseMo;

import java.util.List;

public class KMarketResultBean extends BaseMo {

    public List<MarketBean> market;

    public static class MarketBean {
        /**
         * prod_name : 黄金
         * last_px : 1327.07
         * px_change : -3.4
         * px_change_rate : -0.26
         * high_px : 1332.14
         * low_px : 1325.68
         * open_px : 1330.47
         * preclose_px : 1330.47
         * update_time : 1524635495
         * business_amount : 0
         * trade_status : TRADE
         * securities_type : commodity
         * price_precision : 2
         * week_52_high : 1366.13
         * week_52_low : 1204.95
         * p_code : XAUUSD
         * p_short_code : LLG
         */
        public int flagKUp;//0等于，2跌，1涨
        public String prod_name;
        public float last_px;
        public float px_change;
        public float px_change_rate;
        public float high_px;
        public float low_px;
        public float open_px;
        public float preclose_px;
        public int update_time;
        public int business_amount;
        public String trade_status;
        public String securities_type;
        public int price_precision;
        public float week_52_high;
        public float week_52_low;
        public String p_code;
        public String p_short_code;

    }
}

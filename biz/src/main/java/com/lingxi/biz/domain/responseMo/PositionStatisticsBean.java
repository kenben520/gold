package com.lingxi.biz.domain.responseMo;

import java.util.List;

/**
 * Created by qian on 2018/4/24.
 */

public class PositionStatisticsBean extends BaseMo {

    private List<CftcDataBean> cftc_data;
    private List<MerPosition24Bean> mer_position_24;
    private List<MerPositionTotalBean> mer_position_total;

    public List<CftcDataBean> getCftc_data() {
        return cftc_data;
    }

    public void setCftc_data(List<CftcDataBean> cftc_data) {
        this.cftc_data = cftc_data;
    }

    public List<MerPosition24Bean> getMer_position_24() {
        return mer_position_24;
    }

    public void setMer_position_24(List<MerPosition24Bean> mer_position_24) {
        this.mer_position_24 = mer_position_24;
    }

    public List<MerPositionTotalBean> getMer_position_total() {
        return mer_position_total;
    }

    public void setMer_position_total(List<MerPositionTotalBean> mer_position_total) {
        this.mer_position_total = mer_position_total;
    }

    public static class CftcDataBean {
        /**
         * name : 纽约期金
         * more_rate : 78
         * reduce_rate : 22
         * more_rate_number : 78
         * reduce_rate_number : 22
         * more_number : 234431
         * reduce_number : 66483
         * date : 2018-03-13
         */

        private String name;
        private int more_rate;
        private int reduce_rate;
        private String more_rate_number;
        private String reduce_rate_number;
        private String more_number;
        private String reduce_number;
        private String date;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getMore_rate() {
            return more_rate;
        }

        public void setMore_rate(int more_rate) {
            this.more_rate = more_rate;
        }

        public int getReduce_rate() {
            return reduce_rate;
        }

        public void setReduce_rate(int reduce_rate) {
            this.reduce_rate = reduce_rate;
        }

        public String getMore_rate_number() {
            return more_rate_number;
        }

        public void setMore_rate_number(String more_rate_number) {
            this.more_rate_number = more_rate_number;
        }

        public String getReduce_rate_number() {
            return reduce_rate_number;
        }

        public void setReduce_rate_number(String reduce_rate_number) {
            this.reduce_rate_number = reduce_rate_number;
        }

        public String getMore_number() {
            return more_number;
        }

        public void setMore_number(String more_number) {
            this.more_number = more_number;
        }

        public String getReduce_number() {
            return reduce_number;
        }

        public void setReduce_number(String reduce_number) {
            this.reduce_number = reduce_number;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

    public static class MerPosition24Bean {
        /**
         * act_empty : 26%
         * act_more : 74%
         * act_empty_number : 26
         * act_more_number : 74
         * name : 伦敦金
         */

        private String act_empty;
        private String act_more;
        private float act_empty_number;
        private float act_more_number;
        private String name;

        public String getAct_empty() {
            return act_empty;
        }

        public void setAct_empty(String act_empty) {
            this.act_empty = act_empty;
        }

        public String getAct_more() {
            return act_more;
        }

        public void setAct_more(String act_more) {
            this.act_more = act_more;
        }

        public float getAct_empty_number() {
            return act_empty_number;
        }

        public void setAct_empty_number(float act_empty_number) {
            this.act_empty_number = act_empty_number;
        }

        public float getAct_more_number() {
            return act_more_number;
        }

        public void setAct_more_number(float act_more_number) {
            this.act_more_number = act_more_number;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class MerPositionTotalBean {
        /**
         * act_empty : 34%
         * act_more : 66%
         * act_empty_number : 34
         * act_more_number : 66
         * name : 伦敦金
         */

        private String act_empty;
        private String act_more;
        private int act_empty_number;
        private int act_more_number;
        private String name;

        public String getAct_empty() {
            return act_empty;
        }

        public void setAct_empty(String act_empty) {
            this.act_empty = act_empty;
        }

        public String getAct_more() {
            return act_more;
        }

        public void setAct_more(String act_more) {
            this.act_more = act_more;
        }

        public int getAct_empty_number() {
            return act_empty_number;
        }

        public void setAct_empty_number(int act_empty_number) {
            this.act_empty_number = act_empty_number;
        }

        public int getAct_more_number() {
            return act_more_number;
        }

        public void setAct_more_number(int act_more_number) {
            this.act_more_number = act_more_number;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

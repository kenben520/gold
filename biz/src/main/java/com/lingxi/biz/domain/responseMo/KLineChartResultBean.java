package com.lingxi.biz.domain.responseMo;


import java.util.List;

public class KLineChartResultBean extends BaseMo {

    private List<ChartBean> chart;

    public List<ChartBean> getChart() {
        return chart;
    }

    public void setChart(List<ChartBean> chart) {
        this.chart = chart;
    }

    public static class ChartBean {
        /**
         * open_px : 1319.730
         * close_px : 1318.550
         * high_px : 1320.300
         * low_px : 1318.230
         * upper : 1319.686
         * mid : 1317.750
         * lower : 1315.814
         * k : 59.711
         * d : 58.547
         * j : 62.039
         * rsi6 : 54.784
         * rsi12 : 49.718
         * rsi24 : 45.268
         * diff : -0.829
         * dea : -1.247
         * macd : 0.835
         * ma5 : 1318.112
         * ma10 : 1317.670
         * ma20 : 1317.591
         * ma60 : 1321.634
         */
        private long time_stamp;
        private float open_px;
        private float close_px;
        private float high_px;
        private float low_px;
        private float upper;
        private float mid;
        private float lower;
        private float k;
        private float d;
        private float j;
        private float rsi6;
        private float rsi12;
        private float rsi24;
        private float diff;
        private float dea;
        private float macd;
        private float ma5;
        private float ma10;
        private float ma20;
        private float ma60;

        public long getTime_stamp() {
            return time_stamp;
        }

        public void setTime_stamp(long time_stamp) {
            this.time_stamp = time_stamp;
        }

        public float getOpen_px() {
            return open_px;
        }

        public void setOpen_px(float open_px) {
            this.open_px = open_px;
        }

        public float getClose_px() {
            return close_px;
        }

        public void setClose_px(float close_px) {
            this.close_px = close_px;
        }

        public float getHigh_px() {
            return high_px;
        }

        public void setHigh_px(float high_px) {
            this.high_px = high_px;
        }

        public float getLow_px() {
            return low_px;
        }

        public void setLow_px(float low_px) {
            this.low_px = low_px;
        }

        public float getUpper() {
            return upper;
        }

        public void setUpper(float upper) {
            this.upper = upper;
        }

        public float getMid() {
            return mid;
        }

        public void setMid(float mid) {
            this.mid = mid;
        }

        public float getLower() {
            return lower;
        }

        public void setLower(float lower) {
            this.lower = lower;
        }

        public float getK() {
            return k;
        }

        public void setK(float k) {
            this.k = k;
        }

        public float getD() {
            return d;
        }

        public void setD(float d) {
            this.d = d;
        }

        public float getJ() {
            return j;
        }

        public void setJ(float j) {
            this.j = j;
        }

        public float getRsi6() {
            return rsi6;
        }

        public void setRsi6(float rsi6) {
            this.rsi6 = rsi6;
        }

        public float getRsi12() {
            return rsi12;
        }

        public void setRsi12(float rsi12) {
            this.rsi12 = rsi12;
        }

        public float getRsi24() {
            return rsi24;
        }

        public void setRsi24(float rsi24) {
            this.rsi24 = rsi24;
        }

        public float getDiff() {
            return diff;
        }

        public void setDiff(float diff) {
            this.diff = diff;
        }

        public float getDea() {
            return dea;
        }

        public void setDea(float dea) {
            this.dea = dea;
        }

        public float getMacd() {
            return macd;
        }

        public void setMacd(float macd) {
            this.macd = macd;
        }

        public float getMa5() {
            return ma5;
        }

        public void setMa5(float ma5) {
            this.ma5 = ma5;
        }

        public float getMa10() {
            return ma10;
        }

        public void setMa10(float ma10) {
            this.ma10 = ma10;
        }

        public float getMa20() {
            return ma20;
        }

        public void setMa20(float ma20) {
            this.ma20 = ma20;
        }

        public float getMa60() {
            return ma60;
        }

        public void setMa60(float ma60) {
            this.ma60 = ma60;
        }
    }
}

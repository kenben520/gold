package com.lingxi.biz.domain.responseMo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangwei on 2018/4/25.
 */

public class SignalParamsMo implements Serializable {

    /**
     * XAUUSD : {"quota":{"5m":{"RSI":["80.0000",1],"MACD":[4102.0026,1],"CCI":["84.0751",3],"BBI":["1267.66540",1],"WAR":["0.1188",2],"DMA20_50":["-361.2828",1],"DMA10_50":["-456.7082",1],"DMA5_10":["-253.4356",1],"KD":["75.9831",2,"75.9831","76.7610"],"ROC":["-0.00043",1]},"60m":{"RSI":["58.4615",1],"MACD":[4080.2946,1],"CCI":["84.4890",3],"BBI":["1273.47208",2],"WAR":["0.8591",2],"DMA20_50":["-359.4254",1],"DMA10_50":["-454.0714",1],"DMA5_10":["-254.4010",1],"KD":["27.7540",2,"27.7540","42.5847"],"ROC":["0.00100",2]},"1day":{"RSI":["72.9730",1],"MACD":[4128.7548,1],"CCI":["88.0985",3],"BBI":["1334.59470",2],"WAR":["0.9407",2],"DMA20_50":["-360.2589",1],"DMA10_50":["-453.3289",1],"DMA5_10":["-258.9512",1],"KD":["38.2839",2,"38.2839","43.7903"],"ROC":["0.02336",1]}},"average":{"5m":{"MA5":["1267.5720",1],"MA10":["1267.5080",1],"MA20":["1267.9050",1],"MA50":["1267.9074",1],"MA100":["633.9537",1],"MA200":["316.9769",1]},"60m":{"MA5":["1274.0500",1],"MA10":["1273.6320",1],"MA20":["1272.6770",1],"MA50":["1269.1222",1],"MA100":["634.5611",1],"MA200":["317.2805",1]},"1day":{"MA5":["1344.7440",1],"MA10":["1334.3000",1],"MA20":["1323.7880",1],"MA50":["1304.8854",1],"MA100":["652.4427",1],"MA200":["326.2214",1]}},"result":{"5m":{"quota":[7,2],"average":[6,0],"weather":["晴朗天气   极端看多"]},"60m":{"quota":[5,4],"average":[6,0],"weather":["晴朗天气   极端看多"]},"1day":{"quota":[6,3],"average":[6,0],"weather":["晴朗天气   极端看多"]}}}
     * XAGUSD : {"quota":{"5m":{"RSI":["80.0000",1],"MACD":[52.8692,1],"CCI":["1.0834",3],"BBI":["16.33547",1],"WAR":["0.0606",2],"DMA20_50":["-4.6519",1],"DMA10_50":["-5.8831",1],"DMA5_10":["-3.2656",1],"KD":["77.9281",1,"77.9281","77.1500"],"ROC":["-0.00065",2]},"60m":{"RSI":["15.0000",2],"MACD":[52.6076,1],"CCI":["1.0822",3],"BBI":["16.31912",1],"WAR":["0.5341",2],"DMA20_50":["-4.6399",1],"DMA10_50":["-5.8636",1],"DMA5_10":["-3.2499",1],"KD":["34.0330",2,"34.0330","44.6777"],"ROC":["0.00077",2]},"1day":{"RSI":["53.9749",1],"MACD":[52.6878,1],"CCI":["1.1064",3],"BBI":["16.78112",1],"WAR":["0.4645",2],"DMA20_50":["-4.7118",1],"DMA10_50":["-5.8621",1],"DMA5_10":["-3.1665",1],"KD":["52.0523",1,"52.0523","46.5443"],"ROC":["0.03218",2]}},"average":{"5m":{"MA5":["16.3340",1],"MA10":["16.3331",1],"MA20":["16.3401",1],"MA50":["16.3363",1],"MA100":["8.1682",1],"MA200":["4.0841",1]},"60m":{"MA5":["16.3286",1],"MA10":["16.3143",1],"MA20":["16.3124",1],"MA50":["16.3018",1],"MA100":["8.1509",1],"MA200":["4.0754",1]},"1day":{"MA5":["17.0050",1],"MA10":["16.7980",1],"MA20":["16.6332",1],"MA50":["16.5792",1],"MA100":["8.2896",1],"MA200":["4.1448",1]}},"result":{"5m":{"quota":[7,2],"average":[6,0],"weather":["晴朗天气   极端看多"]},"60m":{"quota":[5,4],"average":[6,0],"weather":["晴朗天气   极端看多"]},"1day":{"quota":[7,2],"average":[6,0],"weather":["晴朗天气   极端看多"]}}}
     */

    private SignalBean XAUUSD;
    private SignalBean XAGUSD;

    public SignalBean getXAUUSD() {
        return XAUUSD;
    }

    public void setXAUUSD(SignalBean XAUUSD) {
        this.XAUUSD = XAUUSD;
    }

    public SignalBean getXAGUSD() {
        return XAGUSD;
    }

    public void setXAGUSD(SignalBean XAGUSD) {
        this.XAGUSD = XAGUSD;
    }

    public static class SignalBean {
        /**
         * quota : {"5m":{"RSI":["80.0000",1],"MACD":[4102.0026,1],"CCI":["84.0751",3],"BBI":["1267.66540",1],"WAR":["0.1188",2],"DMA20_50":["-361.2828",1],"DMA10_50":["-456.7082",1],"DMA5_10":["-253.4356",1],"KD":["75.9831",2,"75.9831","76.7610"],"ROC":["-0.00043",1]},"60m":{"RSI":["58.4615",1],"MACD":[4080.2946,1],"CCI":["84.4890",3],"BBI":["1273.47208",2],"WAR":["0.8591",2],"DMA20_50":["-359.4254",1],"DMA10_50":["-454.0714",1],"DMA5_10":["-254.4010",1],"KD":["27.7540",2,"27.7540","42.5847"],"ROC":["0.00100",2]},"1day":{"RSI":["72.9730",1],"MACD":[4128.7548,1],"CCI":["88.0985",3],"BBI":["1334.59470",2],"WAR":["0.9407",2],"DMA20_50":["-360.2589",1],"DMA10_50":["-453.3289",1],"DMA5_10":["-258.9512",1],"KD":["38.2839",2,"38.2839","43.7903"],"ROC":["0.02336",1]}}
         * average : {"5m":{"MA5":["1267.5720",1],"MA10":["1267.5080",1],"MA20":["1267.9050",1],"MA50":["1267.9074",1],"MA100":["633.9537",1],"MA200":["316.9769",1]},"60m":{"MA5":["1274.0500",1],"MA10":["1273.6320",1],"MA20":["1272.6770",1],"MA50":["1269.1222",1],"MA100":["634.5611",1],"MA200":["317.2805",1]},"1day":{"MA5":["1344.7440",1],"MA10":["1334.3000",1],"MA20":["1323.7880",1],"MA50":["1304.8854",1],"MA100":["652.4427",1],"MA200":["326.2214",1]}}
         * result : {"5m":{"quota":[7,2],"average":[6,0],"weather":["晴朗天气   极端看多"]},"60m":{"quota":[5,4],"average":[6,0],"weather":["晴朗天气   极端看多"]},"1day":{"quota":[6,3],"average":[6,0],"weather":["晴朗天气   极端看多"]}}
         */

        private QuotaBean quota;
        private AverageBean average;
        private ResultBean result;

        public QuotaBean getQuota() {
            return quota;
        }

        public void setQuota(QuotaBean quota) {
            this.quota = quota;
        }

        public AverageBean getAverage() {
            return average;
        }

        public void setAverage(AverageBean average) {
            this.average = average;
        }

        public ResultBean getResult() {
            return result;
        }

        public void setResult(ResultBean result) {
            this.result = result;
        }

        public static class QuotaBean {
            /**
             * 5m : {"RSI":["80.0000",1],"MACD":[4102.0026,1],"CCI":["84.0751",3],"BBI":["1267.66540",1],"WAR":["0.1188",2],"DMA20_50":["-361.2828",1],"DMA10_50":["-456.7082",1],"DMA5_10":["-253.4356",1],"KD":["75.9831",2,"75.9831","76.7610"],"ROC":["-0.00043",1]}
             * 60m : {"RSI":["58.4615",1],"MACD":[4080.2946,1],"CCI":["84.4890",3],"BBI":["1273.47208",2],"WAR":["0.8591",2],"DMA20_50":["-359.4254",1],"DMA10_50":["-454.0714",1],"DMA5_10":["-254.4010",1],"KD":["27.7540",2,"27.7540","42.5847"],"ROC":["0.00100",2]}
             * 1day : {"RSI":["72.9730",1],"MACD":[4128.7548,1],"CCI":["88.0985",3],"BBI":["1334.59470",2],"WAR":["0.9407",2],"DMA20_50":["-360.2589",1],"DMA10_50":["-453.3289",1],"DMA5_10":["-258.9512",1],"KD":["38.2839",2,"38.2839","43.7903"],"ROC":["0.02336",1]}
             */

            @SerializedName("5m")
            private TimeQuotaBean _$5m;
            @SerializedName("60m")
            private TimeQuotaBean _$60m;
            @SerializedName("1day")
            private TimeQuotaBean _$1day;

            public TimeQuotaBean get_$5m() {
                return _$5m;
            }

            public void set_$5m(TimeQuotaBean _$5m) {
                this._$5m = _$5m;
            }

            public TimeQuotaBean get_$60m() {
                return _$60m;
            }

            public void set_$60m(TimeQuotaBean _$60m) {
                this._$60m = _$60m;
            }

            public TimeQuotaBean get_$1day() {
                return _$1day;
            }

            public void set_$1day(TimeQuotaBean _$1day) {
                this._$1day = _$1day;
            }

            public static class TimeQuotaBean {
                private List<String> RSI;
                private List<String> MACD;
                private List<String> CCI;
                private List<String> BBI;
                private List<String> WAR;
                private List<String> DMA20_50;
                private List<String> DMA10_50;
                private List<String> DMA5_10;
                private List<String> KD;
                private List<String> ROC;

                public List<String> getRSI() {
                    return RSI;
                }

                public void setRSI(List<String> RSI) {
                    this.RSI = RSI;
                }

                public List<String> getMACD() {
                    return MACD;
                }

                public void setMACD(List<String> MACD) {
                    this.MACD = MACD;
                }

                public List<String> getCCI() {
                    return CCI;
                }

                public void setCCI(List<String> CCI) {
                    this.CCI = CCI;
                }

                public List<String> getBBI() {
                    return BBI;
                }

                public void setBBI(List<String> BBI) {
                    this.BBI = BBI;
                }

                public List<String> getWAR() {
                    return WAR;
                }

                public void setWAR(List<String> WAR) {
                    this.WAR = WAR;
                }

                public List<String> getDMA20_50() {
                    return DMA20_50;
                }

                public void setDMA20_50(List<String> DMA20_50) {
                    this.DMA20_50 = DMA20_50;
                }

                public List<String> getDMA10_50() {
                    return DMA10_50;
                }

                public void setDMA10_50(List<String> DMA10_50) {
                    this.DMA10_50 = DMA10_50;
                }

                public List<String> getDMA5_10() {
                    return DMA5_10;
                }

                public void setDMA5_10(List<String> DMA5_10) {
                    this.DMA5_10 = DMA5_10;
                }

                public List<String> getKD() {
                    return KD;
                }

                public void setKD(List<String> KD) {
                    this.KD = KD;
                }

                public List<String> getROC() {
                    return ROC;
                }

                public void setROC(List<String> ROC) {
                    this.ROC = ROC;
                }
            }

            public static class _$60mBean {
                private List<String> RSI;
                private List<Double> MACD;
                private List<String> CCI;
                private List<String> BBI;
                private List<String> WAR;
                private List<String> DMA20_50;
                private List<String> DMA10_50;
                private List<String> DMA5_10;
                private List<String> KD;
                private List<String> ROC;

                public List<String> getRSI() {
                    return RSI;
                }

                public void setRSI(List<String> RSI) {
                    this.RSI = RSI;
                }

                public List<Double> getMACD() {
                    return MACD;
                }

                public void setMACD(List<Double> MACD) {
                    this.MACD = MACD;
                }

                public List<String> getCCI() {
                    return CCI;
                }

                public void setCCI(List<String> CCI) {
                    this.CCI = CCI;
                }

                public List<String> getBBI() {
                    return BBI;
                }

                public void setBBI(List<String> BBI) {
                    this.BBI = BBI;
                }

                public List<String> getWAR() {
                    return WAR;
                }

                public void setWAR(List<String> WAR) {
                    this.WAR = WAR;
                }

                public List<String> getDMA20_50() {
                    return DMA20_50;
                }

                public void setDMA20_50(List<String> DMA20_50) {
                    this.DMA20_50 = DMA20_50;
                }

                public List<String> getDMA10_50() {
                    return DMA10_50;
                }

                public void setDMA10_50(List<String> DMA10_50) {
                    this.DMA10_50 = DMA10_50;
                }

                public List<String> getDMA5_10() {
                    return DMA5_10;
                }

                public void setDMA5_10(List<String> DMA5_10) {
                    this.DMA5_10 = DMA5_10;
                }

                public List<String> getKD() {
                    return KD;
                }

                public void setKD(List<String> KD) {
                    this.KD = KD;
                }

                public List<String> getROC() {
                    return ROC;
                }

                public void setROC(List<String> ROC) {
                    this.ROC = ROC;
                }
            }

            public static class _$1dayBean {
                private List<String> RSI;
                private List<Double> MACD;
                private List<String> CCI;
                private List<String> BBI;
                private List<String> WAR;
                private List<String> DMA20_50;
                private List<String> DMA10_50;
                private List<String> DMA5_10;
                private List<String> KD;
                private List<String> ROC;

                public List<String> getRSI() {
                    return RSI;
                }

                public void setRSI(List<String> RSI) {
                    this.RSI = RSI;
                }

                public List<Double> getMACD() {
                    return MACD;
                }

                public void setMACD(List<Double> MACD) {
                    this.MACD = MACD;
                }

                public List<String> getCCI() {
                    return CCI;
                }

                public void setCCI(List<String> CCI) {
                    this.CCI = CCI;
                }

                public List<String> getBBI() {
                    return BBI;
                }

                public void setBBI(List<String> BBI) {
                    this.BBI = BBI;
                }

                public List<String> getWAR() {
                    return WAR;
                }

                public void setWAR(List<String> WAR) {
                    this.WAR = WAR;
                }

                public List<String> getDMA20_50() {
                    return DMA20_50;
                }

                public void setDMA20_50(List<String> DMA20_50) {
                    this.DMA20_50 = DMA20_50;
                }

                public List<String> getDMA10_50() {
                    return DMA10_50;
                }

                public void setDMA10_50(List<String> DMA10_50) {
                    this.DMA10_50 = DMA10_50;
                }

                public List<String> getDMA5_10() {
                    return DMA5_10;
                }

                public void setDMA5_10(List<String> DMA5_10) {
                    this.DMA5_10 = DMA5_10;
                }

                public List<String> getKD() {
                    return KD;
                }

                public void setKD(List<String> KD) {
                    this.KD = KD;
                }

                public List<String> getROC() {
                    return ROC;
                }

                public void setROC(List<String> ROC) {
                    this.ROC = ROC;
                }
            }
        }

        public static class AverageBean {
            /**
             * 5m : {"MA5":["1267.5720",1],"MA10":["1267.5080",1],"MA20":["1267.9050",1],"MA50":["1267.9074",1],"MA100":["633.9537",1],"MA200":["316.9769",1]}
             * 60m : {"MA5":["1274.0500",1],"MA10":["1273.6320",1],"MA20":["1272.6770",1],"MA50":["1269.1222",1],"MA100":["634.5611",1],"MA200":["317.2805",1]}
             * 1day : {"MA5":["1344.7440",1],"MA10":["1334.3000",1],"MA20":["1323.7880",1],"MA50":["1304.8854",1],"MA100":["652.4427",1],"MA200":["326.2214",1]}
             */

            @SerializedName("5m")
            private TimeAverageBean _$5m;
            @SerializedName("60m")
            private TimeAverageBean _$60m;
            @SerializedName("1day")
            private TimeAverageBean _$1day;

            public TimeAverageBean get_$5m() {
                return _$5m;
            }

            public void set_$5m(TimeAverageBean _$5m) {
                this._$5m = _$5m;
            }

            public TimeAverageBean get_$60m() {
                return _$60m;
            }

            public void set_$60m(TimeAverageBean _$60m) {
                this._$60m = _$60m;
            }

            public TimeAverageBean get_$1day() {
                return _$1day;
            }

            public void set_$1day(TimeAverageBean _$1day) {
                this._$1day = _$1day;
            }

            public static class TimeAverageBean {
                private List<String> MA5;
                private List<String> MA10;
                private List<String> MA20;
                private List<String> MA50;
                private List<String> MA100;
                private List<String> MA200;

                public List<String> getMA5() {
                    return MA5;
                }

                public void setMA5(List<String> MA5) {
                    this.MA5 = MA5;
                }

                public List<String> getMA10() {
                    return MA10;
                }

                public void setMA10(List<String> MA10) {
                    this.MA10 = MA10;
                }

                public List<String> getMA20() {
                    return MA20;
                }

                public void setMA20(List<String> MA20) {
                    this.MA20 = MA20;
                }

                public List<String> getMA50() {
                    return MA50;
                }

                public void setMA50(List<String> MA50) {
                    this.MA50 = MA50;
                }

                public List<String> getMA100() {
                    return MA100;
                }

                public void setMA100(List<String> MA100) {
                    this.MA100 = MA100;
                }

                public List<String> getMA200() {
                    return MA200;
                }

                public void setMA200(List<String> MA200) {
                    this.MA200 = MA200;
                }
            }

            public static class _$60mBeanX {
                private List<String> MA5;
                private List<String> MA10;
                private List<String> MA20;
                private List<String> MA50;
                private List<String> MA100;
                private List<String> MA200;

                public List<String> getMA5() {
                    return MA5;
                }

                public void setMA5(List<String> MA5) {
                    this.MA5 = MA5;
                }

                public List<String> getMA10() {
                    return MA10;
                }

                public void setMA10(List<String> MA10) {
                    this.MA10 = MA10;
                }

                public List<String> getMA20() {
                    return MA20;
                }

                public void setMA20(List<String> MA20) {
                    this.MA20 = MA20;
                }

                public List<String> getMA50() {
                    return MA50;
                }

                public void setMA50(List<String> MA50) {
                    this.MA50 = MA50;
                }

                public List<String> getMA100() {
                    return MA100;
                }

                public void setMA100(List<String> MA100) {
                    this.MA100 = MA100;
                }

                public List<String> getMA200() {
                    return MA200;
                }

                public void setMA200(List<String> MA200) {
                    this.MA200 = MA200;
                }
            }

            public static class _$1dayBeanX {
                private List<String> MA5;
                private List<String> MA10;
                private List<String> MA20;
                private List<String> MA50;
                private List<String> MA100;
                private List<String> MA200;

                public List<String> getMA5() {
                    return MA5;
                }

                public void setMA5(List<String> MA5) {
                    this.MA5 = MA5;
                }

                public List<String> getMA10() {
                    return MA10;
                }

                public void setMA10(List<String> MA10) {
                    this.MA10 = MA10;
                }

                public List<String> getMA20() {
                    return MA20;
                }

                public void setMA20(List<String> MA20) {
                    this.MA20 = MA20;
                }

                public List<String> getMA50() {
                    return MA50;
                }

                public void setMA50(List<String> MA50) {
                    this.MA50 = MA50;
                }

                public List<String> getMA100() {
                    return MA100;
                }

                public void setMA100(List<String> MA100) {
                    this.MA100 = MA100;
                }

                public List<String> getMA200() {
                    return MA200;
                }

                public void setMA200(List<String> MA200) {
                    this.MA200 = MA200;
                }
            }
        }

        public static class ResultBean {
            /**
             * 5m : {"quota":[7,2],"average":[6,0],"weather":["晴朗天气   极端看多"]}
             * 60m : {"quota":[5,4],"average":[6,0],"weather":["晴朗天气   极端看多"]}
             * 1day : {"quota":[6,3],"average":[6,0],"weather":["晴朗天气   极端看多"]}
             */

            @SerializedName("5m")
            private TimeResultBean _$5m;
            @SerializedName("60m")
            private TimeResultBean _$60m;
            @SerializedName("1day")
            private TimeResultBean _$1day;

            public TimeResultBean get_$5m() {
                return _$5m;
            }

            public void set_$5m(TimeResultBean _$5m) {
                this._$5m = _$5m;
            }

            public TimeResultBean get_$60m() {
                return _$60m;
            }

            public void set_$60m(TimeResultBean _$60m) {
                this._$60m = _$60m;
            }

            public TimeResultBean get_$1day() {
                return _$1day;
            }

            public void set_$1day(TimeResultBean _$1day) {
                this._$1day = _$1day;
            }

            public static class TimeResultBean {
                private List<Integer> quota;
                private List<Integer> average;
                private String weather;
                private int weather_status;
                public List<Integer> getQuota() {
                    return quota;
                }

                public void setQuota(List<Integer> quota) {
                    this.quota = quota;
                }

                public List<Integer> getAverage() {
                    return average;
                }

                public void setAverage(List<Integer> average) {
                    this.average = average;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public int getWeather_status() {
                    return weather_status;
                }
            }

            public static class _$60mBeanXX {
                private List<Integer> quota;
                private List<Integer> average;
                private List<String> weather;

                public List<Integer> getQuota() {
                    return quota;
                }

                public void setQuota(List<Integer> quota) {
                    this.quota = quota;
                }

                public List<Integer> getAverage() {
                    return average;
                }

                public void setAverage(List<Integer> average) {
                    this.average = average;
                }

                public List<String> getWeather() {
                    return weather;
                }

                public void setWeather(List<String> weather) {
                    this.weather = weather;
                }
            }

            public static class _$1dayBeanXX {
                private List<Integer> quota;
                private List<Integer> average;
                private List<String> weather;

                public List<Integer> getQuota() {
                    return quota;
                }

                public void setQuota(List<Integer> quota) {
                    this.quota = quota;
                }

                public List<Integer> getAverage() {
                    return average;
                }

                public void setAverage(List<Integer> average) {
                    this.average = average;
                }

                public List<String> getWeather() {
                    return weather;
                }

                public void setWeather(List<String> weather) {
                    this.weather = weather;
                }
            }
        }
    }

    public static class XAGUSDBean {
        /**
         * quota : {"5m":{"RSI":["80.0000",1],"MACD":[52.8692,1],"CCI":["1.0834",3],"BBI":["16.33547",1],"WAR":["0.0606",2],"DMA20_50":["-4.6519",1],"DMA10_50":["-5.8831",1],"DMA5_10":["-3.2656",1],"KD":["77.9281",1,"77.9281","77.1500"],"ROC":["-0.00065",2]},"60m":{"RSI":["15.0000",2],"MACD":[52.6076,1],"CCI":["1.0822",3],"BBI":["16.31912",1],"WAR":["0.5341",2],"DMA20_50":["-4.6399",1],"DMA10_50":["-5.8636",1],"DMA5_10":["-3.2499",1],"KD":["34.0330",2,"34.0330","44.6777"],"ROC":["0.00077",2]},"1day":{"RSI":["53.9749",1],"MACD":[52.6878,1],"CCI":["1.1064",3],"BBI":["16.78112",1],"WAR":["0.4645",2],"DMA20_50":["-4.7118",1],"DMA10_50":["-5.8621",1],"DMA5_10":["-3.1665",1],"KD":["52.0523",1,"52.0523","46.5443"],"ROC":["0.03218",2]}}
         * average : {"5m":{"MA5":["16.3340",1],"MA10":["16.3331",1],"MA20":["16.3401",1],"MA50":["16.3363",1],"MA100":["8.1682",1],"MA200":["4.0841",1]},"60m":{"MA5":["16.3286",1],"MA10":["16.3143",1],"MA20":["16.3124",1],"MA50":["16.3018",1],"MA100":["8.1509",1],"MA200":["4.0754",1]},"1day":{"MA5":["17.0050",1],"MA10":["16.7980",1],"MA20":["16.6332",1],"MA50":["16.5792",1],"MA100":["8.2896",1],"MA200":["4.1448",1]}}
         * result : {"5m":{"quota":[7,2],"average":[6,0],"weather":["晴朗天气   极端看多"]},"60m":{"quota":[5,4],"average":[6,0],"weather":["晴朗天气   极端看多"]},"1day":{"quota":[7,2],"average":[6,0],"weather":["晴朗天气   极端看多"]}}
         */

        private QuotaBeanX quota;
        private AverageBeanX average;
        private ResultBeanX result;

        public QuotaBeanX getQuota() {
            return quota;
        }

        public void setQuota(QuotaBeanX quota) {
            this.quota = quota;
        }

        public AverageBeanX getAverage() {
            return average;
        }

        public void setAverage(AverageBeanX average) {
            this.average = average;
        }

        public ResultBeanX getResult() {
            return result;
        }

        public void setResult(ResultBeanX result) {
            this.result = result;
        }

        public static class QuotaBeanX {
            /**
             * 5m : {"RSI":["80.0000",1],"MACD":[52.8692,1],"CCI":["1.0834",3],"BBI":["16.33547",1],"WAR":["0.0606",2],"DMA20_50":["-4.6519",1],"DMA10_50":["-5.8831",1],"DMA5_10":["-3.2656",1],"KD":["77.9281",1,"77.9281","77.1500"],"ROC":["-0.00065",2]}
             * 60m : {"RSI":["15.0000",2],"MACD":[52.6076,1],"CCI":["1.0822",3],"BBI":["16.31912",1],"WAR":["0.5341",2],"DMA20_50":["-4.6399",1],"DMA10_50":["-5.8636",1],"DMA5_10":["-3.2499",1],"KD":["34.0330",2,"34.0330","44.6777"],"ROC":["0.00077",2]}
             * 1day : {"RSI":["53.9749",1],"MACD":[52.6878,1],"CCI":["1.1064",3],"BBI":["16.78112",1],"WAR":["0.4645",2],"DMA20_50":["-4.7118",1],"DMA10_50":["-5.8621",1],"DMA5_10":["-3.1665",1],"KD":["52.0523",1,"52.0523","46.5443"],"ROC":["0.03218",2]}
             */

            @SerializedName("5m")
            private _$5mBeanXXX _$5m;
            @SerializedName("60m")
            private _$60mBeanXXX _$60m;
            @SerializedName("1day")
            private _$1dayBeanXXX _$1day;

            public _$5mBeanXXX get_$5m() {
                return _$5m;
            }

            public void set_$5m(_$5mBeanXXX _$5m) {
                this._$5m = _$5m;
            }

            public _$60mBeanXXX get_$60m() {
                return _$60m;
            }

            public void set_$60m(_$60mBeanXXX _$60m) {
                this._$60m = _$60m;
            }

            public _$1dayBeanXXX get_$1day() {
                return _$1day;
            }

            public void set_$1day(_$1dayBeanXXX _$1day) {
                this._$1day = _$1day;
            }

            public static class _$5mBeanXXX {
                private List<String> RSI;
                private List<Double> MACD;
                private List<String> CCI;
                private List<String> BBI;
                private List<String> WAR;
                private List<String> DMA20_50;
                private List<String> DMA10_50;
                private List<String> DMA5_10;
                private List<String> KD;
                private List<String> ROC;

                public List<String> getRSI() {
                    return RSI;
                }

                public void setRSI(List<String> RSI) {
                    this.RSI = RSI;
                }

                public List<Double> getMACD() {
                    return MACD;
                }

                public void setMACD(List<Double> MACD) {
                    this.MACD = MACD;
                }

                public List<String> getCCI() {
                    return CCI;
                }

                public void setCCI(List<String> CCI) {
                    this.CCI = CCI;
                }

                public List<String> getBBI() {
                    return BBI;
                }

                public void setBBI(List<String> BBI) {
                    this.BBI = BBI;
                }

                public List<String> getWAR() {
                    return WAR;
                }

                public void setWAR(List<String> WAR) {
                    this.WAR = WAR;
                }

                public List<String> getDMA20_50() {
                    return DMA20_50;
                }

                public void setDMA20_50(List<String> DMA20_50) {
                    this.DMA20_50 = DMA20_50;
                }

                public List<String> getDMA10_50() {
                    return DMA10_50;
                }

                public void setDMA10_50(List<String> DMA10_50) {
                    this.DMA10_50 = DMA10_50;
                }

                public List<String> getDMA5_10() {
                    return DMA5_10;
                }

                public void setDMA5_10(List<String> DMA5_10) {
                    this.DMA5_10 = DMA5_10;
                }

                public List<String> getKD() {
                    return KD;
                }

                public void setKD(List<String> KD) {
                    this.KD = KD;
                }

                public List<String> getROC() {
                    return ROC;
                }

                public void setROC(List<String> ROC) {
                    this.ROC = ROC;
                }
            }

            public static class _$60mBeanXXX {
                private List<String> RSI;
                private List<Double> MACD;
                private List<String> CCI;
                private List<String> BBI;
                private List<String> WAR;
                private List<String> DMA20_50;
                private List<String> DMA10_50;
                private List<String> DMA5_10;
                private List<String> KD;
                private List<String> ROC;

                public List<String> getRSI() {
                    return RSI;
                }

                public void setRSI(List<String> RSI) {
                    this.RSI = RSI;
                }

                public List<Double> getMACD() {
                    return MACD;
                }

                public void setMACD(List<Double> MACD) {
                    this.MACD = MACD;
                }

                public List<String> getCCI() {
                    return CCI;
                }

                public void setCCI(List<String> CCI) {
                    this.CCI = CCI;
                }

                public List<String> getBBI() {
                    return BBI;
                }

                public void setBBI(List<String> BBI) {
                    this.BBI = BBI;
                }

                public List<String> getWAR() {
                    return WAR;
                }

                public void setWAR(List<String> WAR) {
                    this.WAR = WAR;
                }

                public List<String> getDMA20_50() {
                    return DMA20_50;
                }

                public void setDMA20_50(List<String> DMA20_50) {
                    this.DMA20_50 = DMA20_50;
                }

                public List<String> getDMA10_50() {
                    return DMA10_50;
                }

                public void setDMA10_50(List<String> DMA10_50) {
                    this.DMA10_50 = DMA10_50;
                }

                public List<String> getDMA5_10() {
                    return DMA5_10;
                }

                public void setDMA5_10(List<String> DMA5_10) {
                    this.DMA5_10 = DMA5_10;
                }

                public List<String> getKD() {
                    return KD;
                }

                public void setKD(List<String> KD) {
                    this.KD = KD;
                }

                public List<String> getROC() {
                    return ROC;
                }

                public void setROC(List<String> ROC) {
                    this.ROC = ROC;
                }
            }

            public static class _$1dayBeanXXX {
                private List<String> RSI;
                private List<Double> MACD;
                private List<String> CCI;
                private List<String> BBI;
                private List<String> WAR;
                private List<String> DMA20_50;
                private List<String> DMA10_50;
                private List<String> DMA5_10;
                private List<String> KD;
                private List<String> ROC;

                public List<String> getRSI() {
                    return RSI;
                }

                public void setRSI(List<String> RSI) {
                    this.RSI = RSI;
                }

                public List<Double> getMACD() {
                    return MACD;
                }

                public void setMACD(List<Double> MACD) {
                    this.MACD = MACD;
                }

                public List<String> getCCI() {
                    return CCI;
                }

                public void setCCI(List<String> CCI) {
                    this.CCI = CCI;
                }

                public List<String> getBBI() {
                    return BBI;
                }

                public void setBBI(List<String> BBI) {
                    this.BBI = BBI;
                }

                public List<String> getWAR() {
                    return WAR;
                }

                public void setWAR(List<String> WAR) {
                    this.WAR = WAR;
                }

                public List<String> getDMA20_50() {
                    return DMA20_50;
                }

                public void setDMA20_50(List<String> DMA20_50) {
                    this.DMA20_50 = DMA20_50;
                }

                public List<String> getDMA10_50() {
                    return DMA10_50;
                }

                public void setDMA10_50(List<String> DMA10_50) {
                    this.DMA10_50 = DMA10_50;
                }

                public List<String> getDMA5_10() {
                    return DMA5_10;
                }

                public void setDMA5_10(List<String> DMA5_10) {
                    this.DMA5_10 = DMA5_10;
                }

                public List<String> getKD() {
                    return KD;
                }

                public void setKD(List<String> KD) {
                    this.KD = KD;
                }

                public List<String> getROC() {
                    return ROC;
                }

                public void setROC(List<String> ROC) {
                    this.ROC = ROC;
                }
            }
        }

        public static class AverageBeanX {
            /**
             * 5m : {"MA5":["16.3340",1],"MA10":["16.3331",1],"MA20":["16.3401",1],"MA50":["16.3363",1],"MA100":["8.1682",1],"MA200":["4.0841",1]}
             * 60m : {"MA5":["16.3286",1],"MA10":["16.3143",1],"MA20":["16.3124",1],"MA50":["16.3018",1],"MA100":["8.1509",1],"MA200":["4.0754",1]}
             * 1day : {"MA5":["17.0050",1],"MA10":["16.7980",1],"MA20":["16.6332",1],"MA50":["16.5792",1],"MA100":["8.2896",1],"MA200":["4.1448",1]}
             */

            @SerializedName("5m")
            private _$5mBeanXXXX _$5m;
            @SerializedName("60m")
            private _$60mBeanXXXX _$60m;
            @SerializedName("1day")
            private _$1dayBeanXXXX _$1day;

            public _$5mBeanXXXX get_$5m() {
                return _$5m;
            }

            public void set_$5m(_$5mBeanXXXX _$5m) {
                this._$5m = _$5m;
            }

            public _$60mBeanXXXX get_$60m() {
                return _$60m;
            }

            public void set_$60m(_$60mBeanXXXX _$60m) {
                this._$60m = _$60m;
            }

            public _$1dayBeanXXXX get_$1day() {
                return _$1day;
            }

            public void set_$1day(_$1dayBeanXXXX _$1day) {
                this._$1day = _$1day;
            }

            public static class _$5mBeanXXXX {
                private List<String> MA5;
                private List<String> MA10;
                private List<String> MA20;
                private List<String> MA50;
                private List<String> MA100;
                private List<String> MA200;

                public List<String> getMA5() {
                    return MA5;
                }

                public void setMA5(List<String> MA5) {
                    this.MA5 = MA5;
                }

                public List<String> getMA10() {
                    return MA10;
                }

                public void setMA10(List<String> MA10) {
                    this.MA10 = MA10;
                }

                public List<String> getMA20() {
                    return MA20;
                }

                public void setMA20(List<String> MA20) {
                    this.MA20 = MA20;
                }

                public List<String> getMA50() {
                    return MA50;
                }

                public void setMA50(List<String> MA50) {
                    this.MA50 = MA50;
                }

                public List<String> getMA100() {
                    return MA100;
                }

                public void setMA100(List<String> MA100) {
                    this.MA100 = MA100;
                }

                public List<String> getMA200() {
                    return MA200;
                }

                public void setMA200(List<String> MA200) {
                    this.MA200 = MA200;
                }
            }

            public static class _$60mBeanXXXX {
                private List<String> MA5;
                private List<String> MA10;
                private List<String> MA20;
                private List<String> MA50;
                private List<String> MA100;
                private List<String> MA200;

                public List<String> getMA5() {
                    return MA5;
                }

                public void setMA5(List<String> MA5) {
                    this.MA5 = MA5;
                }

                public List<String> getMA10() {
                    return MA10;
                }

                public void setMA10(List<String> MA10) {
                    this.MA10 = MA10;
                }

                public List<String> getMA20() {
                    return MA20;
                }

                public void setMA20(List<String> MA20) {
                    this.MA20 = MA20;
                }

                public List<String> getMA50() {
                    return MA50;
                }

                public void setMA50(List<String> MA50) {
                    this.MA50 = MA50;
                }

                public List<String> getMA100() {
                    return MA100;
                }

                public void setMA100(List<String> MA100) {
                    this.MA100 = MA100;
                }

                public List<String> getMA200() {
                    return MA200;
                }

                public void setMA200(List<String> MA200) {
                    this.MA200 = MA200;
                }
            }

            public static class _$1dayBeanXXXX {
                private List<String> MA5;
                private List<String> MA10;
                private List<String> MA20;
                private List<String> MA50;
                private List<String> MA100;
                private List<String> MA200;

                public List<String> getMA5() {
                    return MA5;
                }

                public void setMA5(List<String> MA5) {
                    this.MA5 = MA5;
                }

                public List<String> getMA10() {
                    return MA10;
                }

                public void setMA10(List<String> MA10) {
                    this.MA10 = MA10;
                }

                public List<String> getMA20() {
                    return MA20;
                }

                public void setMA20(List<String> MA20) {
                    this.MA20 = MA20;
                }

                public List<String> getMA50() {
                    return MA50;
                }

                public void setMA50(List<String> MA50) {
                    this.MA50 = MA50;
                }

                public List<String> getMA100() {
                    return MA100;
                }

                public void setMA100(List<String> MA100) {
                    this.MA100 = MA100;
                }

                public List<String> getMA200() {
                    return MA200;
                }

                public void setMA200(List<String> MA200) {
                    this.MA200 = MA200;
                }
            }
        }

        public static class ResultBeanX {
            /**
             * 5m : {"quota":[7,2],"average":[6,0],"weather":["晴朗天气   极端看多"]}
             * 60m : {"quota":[5,4],"average":[6,0],"weather":["晴朗天气   极端看多"]}
             * 1day : {"quota":[7,2],"average":[6,0],"weather":["晴朗天气   极端看多"]}
             */

            @SerializedName("5m")
            private _$5mBeanXXXXX _$5m;
            @SerializedName("60m")
            private _$60mBeanXXXXX _$60m;
            @SerializedName("1day")
            private _$1dayBeanXXXXX _$1day;

            public _$5mBeanXXXXX get_$5m() {
                return _$5m;
            }

            public void set_$5m(_$5mBeanXXXXX _$5m) {
                this._$5m = _$5m;
            }

            public _$60mBeanXXXXX get_$60m() {
                return _$60m;
            }

            public void set_$60m(_$60mBeanXXXXX _$60m) {
                this._$60m = _$60m;
            }

            public _$1dayBeanXXXXX get_$1day() {
                return _$1day;
            }

            public void set_$1day(_$1dayBeanXXXXX _$1day) {
                this._$1day = _$1day;
            }

            public static class _$5mBeanXXXXX {
                private List<Integer> quota;
                private List<Integer> average;
                private List<String> weather;

                public List<Integer> getQuota() {
                    return quota;
                }

                public void setQuota(List<Integer> quota) {
                    this.quota = quota;
                }

                public List<Integer> getAverage() {
                    return average;
                }

                public void setAverage(List<Integer> average) {
                    this.average = average;
                }

                public List<String> getWeather() {
                    return weather;
                }

                public void setWeather(List<String> weather) {
                    this.weather = weather;
                }
            }

            public static class _$60mBeanXXXXX {
                private List<Integer> quota;
                private List<Integer> average;
                private List<String> weather;

                public List<Integer> getQuota() {
                    return quota;
                }

                public void setQuota(List<Integer> quota) {
                    this.quota = quota;
                }

                public List<Integer> getAverage() {
                    return average;
                }

                public void setAverage(List<Integer> average) {
                    this.average = average;
                }

                public List<String> getWeather() {
                    return weather;
                }

                public void setWeather(List<String> weather) {
                    this.weather = weather;
                }
            }

            public static class _$1dayBeanXXXXX {
                private List<Integer> quota;
                private List<Integer> average;
                private List<String> weather;

                public List<Integer> getQuota() {
                    return quota;
                }

                public void setQuota(List<Integer> quota) {
                    this.quota = quota;
                }

                public List<Integer> getAverage() {
                    return average;
                }

                public void setAverage(List<Integer> average) {
                    this.average = average;
                }

                public List<String> getWeather() {
                    return weather;
                }

                public void setWeather(List<String> weather) {
                    this.weather = weather;
                }
            }
        }
    }
}

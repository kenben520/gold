package com.lingxi.biz.domain.responseMo;

import java.io.Serializable;
import java.util.List;

public class HomeAllResultBean extends BaseMo {

    private OnlineBean online;
    private NoticeBean notice;
    private List<BannelBean> bannel;
    private List<MarketBean> market;
    private ExpertBean expert;
    private XagusdBean xagusd;
    private XauusdBean xauusd;
    private H5Bean h5;
    private AccumulativeBean accumulative;

    public H5Bean getH5() {
        return h5;
    }

    public void setH5(H5Bean h5) {
        this.h5 = h5;
    }

    public AccumulativeBean getAccumulative() {
        return accumulative;
    }

    public void setAccumulative(AccumulativeBean accumulative) {
        this.accumulative = accumulative;
    }

    public OnlineBean getOnline() {
        return online;
    }

    public void setOnline(OnlineBean online) {
        this.online = online;
    }

    public NoticeBean getNotice() {
        return notice;
    }

    public void setNotice(NoticeBean notice) {
        this.notice = notice;
    }

    public List<BannelBean> getBannel() {
        return bannel;
    }

    public void setBannel(List<BannelBean> bannel) {
        this.bannel = bannel;
    }

    public List<MarketBean> getMarket() {
        return market;
    }

    public void setMarket(List<MarketBean> market) {
        this.market = market;
    }

    public ExpertBean getExpert() {
        return expert;
    }

    //
    public void setExpert(ExpertBean expert) {
        this.expert = expert;
    }

    public XagusdBean getXagusd() {
        return xagusd;
    }

    public void setXagusd(XagusdBean xagusd) {
        this.xagusd = xagusd;
    }

    public XauusdBean getXauusd() {
        return xauusd;
    }

    public void setXauusd(XauusdBean xauusd) {
        this.xauusd = xauusd;
    }

    public static class OnlineBean {
        /**
         * is_online : 4
         * tea_title : 暂无直播老师
         * tea_img_url :
         * tea_two_code :
         * teacher_bannel : https://fenghe161.oss-cn-shenzhen.aliyuncs.com//uploads/image_pc/20180601/beb97c3b2faed4d687d4d718df9d066d.png
         */

        private int is_online;
        private String tea_img_url;
        private long start_time;
        private long end_time;
        private String tea_name;
        private String tea_profit;
        private String title;
        private String tea_total_click;
        private String tea_two_code;
        private String tea_win_num;
        private String teacher_bannel;

        public int getIs_online() {
            return is_online;
        }

        public void setIs_online(int is_online) {
            this.is_online = is_online;
        }

        public String getTea_img_url() {
            return tea_img_url;
        }

        public void setTea_img_url(String tea_img_url) {
            this.tea_img_url = tea_img_url;
        }

        public long getStart_time() {
            return start_time;
        }

        public void setStart_time(long start_time) {
            this.start_time = start_time;
        }

        public long getEnd_time() {
            return end_time;
        }

        public void setEnd_time(long end_time) {
            this.end_time = end_time;
        }

        public String getTea_name() {
            return tea_name;
        }

        public void setTea_name(String tea_name) {
            this.tea_name = tea_name;
        }

        public String getTea_profit() {
            return tea_profit;
        }

        public void setTea_profit(String tea_profit) {
            this.tea_profit = tea_profit;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTea_total_click() {
            return tea_total_click;
        }

        public void setTea_total_click(String tea_total_click) {
            this.tea_total_click = tea_total_click;
        }

        public String getTea_two_code() {
            return tea_two_code;
        }

        public void setTea_two_code(String tea_two_code) {
            this.tea_two_code = tea_two_code;
        }

        public String getTea_win_num() {
            return tea_win_num;
        }

        public void setTea_win_num(String tea_win_num) {
            this.tea_win_num = tea_win_num;
        }

        public String getTeacher_bannel() {
            return teacher_bannel;
        }

        public void setTeacher_bannel(String teacher_bannel) {
            this.teacher_bannel = teacher_bannel;
        }
    }

    public static class NoticeBean implements Serializable {
        /**
         * add_time : 1525750892
         * title : 【系统维护】后台系统升级公告
         * content : 欢迎光临美盛达贵金属，为了提升后台管理系统的稳定性，本公司平台将于2018年5月9日(周三)下午13:00至16:00 进行系统维护及更新。
         * <p>
         * 如有任何疑问，欢迎随时联系24小时客服咨询。
         * <p>
         * QQ在线客服：888888888。
         * <p>
         * 24小时客服热线：+852-8888-8888
         * notice_id : 11
         */

        private long add_time;
        private String title;
        private String content;
        private String notice_id;

        public long getAdd_time() {
            return add_time;
        }

        public void setAdd_time(long add_time) {
            this.add_time = add_time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getNotice_id() {
            return notice_id;
        }

        public void setNotice_id(String notice_id) {
            this.notice_id = notice_id;
        }
    }

    public static class BannelBean {
        /**
         * b_image_url : https://fenghe161.oss-cn-shenzhen.aliyuncs.com//uploads/image_pc/20180601/8611942b352b6d7ac669498bea2c1cc7.png
         * b_jump_url : http://www.lingxi.net
         * b_type : 5
         * b_is_del : 0
         * b_sort_num : 1
         * id : 6
         */

        private String b_image_url;
        private String b_jump_url;
        private String b_type;
        private String b_is_del;
        private String b_sort_num;
        private String id;
        private String b_title;

        public String getB_title() {
            return b_title;
        }

        public void setB_title(String b_title) {
            this.b_title = b_title;
        }

        public String getB_image_url() {
            return b_image_url;
        }

        public void setB_image_url(String b_image_url) {
            this.b_image_url = b_image_url;
        }

        public String getB_jump_url() {
            return b_jump_url;
        }

        public void setB_jump_url(String b_jump_url) {
            this.b_jump_url = b_jump_url;
        }

        public String getB_type() {
            return b_type;
        }

        public void setB_type(String b_type) {
            this.b_type = b_type;
        }

        public String getB_is_del() {
            return b_is_del;
        }

        public void setB_is_del(String b_is_del) {
            this.b_is_del = b_is_del;
        }

        public String getB_sort_num() {
            return b_sort_num;
        }

        public void setB_sort_num(String b_sort_num) {
            this.b_sort_num = b_sort_num;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class MarketBean {
        /**
         * prod_name : 伦敦金
         * last_px : 1297.88
         * px_change : 1.28
         * px_change_rate : 0.1
         * high_px : 1299.2
         * low_px : 1295.62
         * open_px : 1296.6
         * preclose_px : 1296.6
         * update_time : 1528359372
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
        private String prod_name;
        private float last_px;
        private float px_change;
        private float px_change_rate;
        private float high_px;
        private float low_px;
        private float open_px;
        private float preclose_px;
        private int update_time;
        private int business_amount;
        private String trade_status;
        private String securities_type;
        private int price_precision;
        private float week_52_high;
        private float week_52_low;
        private String p_code;
        private String p_short_code;

        public String getProd_name() {
            return prod_name;
        }

        public void setProd_name(String prod_name) {
            this.prod_name = prod_name;
        }

        public float getLast_px() {
            return last_px;
        }

        public void setLast_px(float last_px) {
            this.last_px = last_px;
        }

        public float getPx_change() {
            return px_change;
        }

        public void setPx_change(float px_change) {
            this.px_change = px_change;
        }

        public float getPx_change_rate() {
            return px_change_rate;
        }

        public void setPx_change_rate(float px_change_rate) {
            this.px_change_rate = px_change_rate;
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

        public float getOpen_px() {
            return open_px;
        }

        public void setOpen_px(float open_px) {
            this.open_px = open_px;
        }

        public float getPreclose_px() {
            return preclose_px;
        }

        public void setPreclose_px(float preclose_px) {
            this.preclose_px = preclose_px;
        }

        public int getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(int update_time) {
            this.update_time = update_time;
        }

        public int getBusiness_amount() {
            return business_amount;
        }

        public void setBusiness_amount(int business_amount) {
            this.business_amount = business_amount;
        }

        public String getTrade_status() {
            return trade_status;
        }

        public void setTrade_status(String trade_status) {
            this.trade_status = trade_status;
        }

        public String getSecurities_type() {
            return securities_type;
        }

        public void setSecurities_type(String securities_type) {
            this.securities_type = securities_type;
        }

        public int getPrice_precision() {
            return price_precision;
        }

        public void setPrice_precision(int price_precision) {
            this.price_precision = price_precision;
        }

        public float getWeek_52_high() {
            return week_52_high;
        }

        public void setWeek_52_high(float week_52_high) {
            this.week_52_high = week_52_high;
        }

        public float getWeek_52_low() {
            return week_52_low;
        }

        public void setWeek_52_low(float week_52_low) {
            this.week_52_low = week_52_low;
        }

        public String getP_code() {
            return p_code;
        }

        public void setP_code(String p_code) {
            this.p_code = p_code;
        }

        public String getP_short_code() {
            return p_short_code;
        }

        public void setP_short_code(String p_short_code) {
            this.p_short_code = p_short_code;
        }
    }

    public static class H5Bean {
        private String platform;
        private String advantage;
        private String history;

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public String getAdvantage() {
            return advantage;
        }

        public void setAdvantage(String advantage) {
            this.advantage = advantage;
        }

        public String getHistory() {
            return history;
        }

        public void setHistory(String history) {
            this.history = history;
        }
    }


    public static class AccumulativeBean {

        /**
         * customer : 541,382
         * order : 7,541,372
         * realtime : 20
         * characteristic : 38
         */

        private String customer;
        private String order;
        private String realtime;
        private String characteristic;

        public String getCustomer() {
            return customer;
        }

        public void setCustomer(String customer) {
            this.customer = customer;
        }

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public String getRealtime() {
            return realtime;
        }

        public void setRealtime(String realtime) {
            this.realtime = realtime;
        }

        public String getCharacteristic() {
            return characteristic;
        }

        public void setCharacteristic(String characteristic) {
            this.characteristic = characteristic;
        }
    }


    public static class XauusdBean {
        private int pos_xauusd_res;
        private String weather;
        private String xauusd;

        public int getPos_xauusd_res() {
            return pos_xauusd_res;
        }

        public void setPos_xauusd_res(int pos_xauusd_res) {
            this.pos_xauusd_res = pos_xauusd_res;
        }

        public String getWeather() {
            return weather;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public String getXauusd() {
            return xauusd;
        }

        public void setXauusd(String xauusd) {
            this.xauusd = xauusd;
        }
    }

    public static class XagusdBean {

        private int pos_xagusd_res;
        private String weather;
        private String xagusd;

        public int getPos_xagusd_res() {
            return pos_xagusd_res;
        }

        public void setPos_xagusd_res(int pos_xagusd_res) {
            this.pos_xagusd_res = pos_xagusd_res;
        }

        public String getWeather() {
            return weather;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public String getXagusd() {
            return xagusd;
        }

        public void setXagusd(String xagusd) {
            this.xagusd = xagusd;
        }
    }

    public static class ExpertBean {
        /**
         * article_id : 1027
         * code :
         * title : 非农强劲美元便能独善其身？
         * cate_id : 25
         * link : http://www.lingxi.net/index.php?app=article&act=expert_view_app&article_id=1027
         * content :
         * 黄金/美元
         * 策略建议：收复12
         * sort_order : 255
         * if_show : 1
         * add_time : 1528070400
         * morning : 0
         * recommend : 0
         * art_keywords :  黄金投资,投资黄金,如何投资黄金，黄金投资平台，黄金投资公司
         * art_description : 连续两个月不及预期之后，上周五公布的5月非农数据大幅好于预期，5月新增非农就业22.3万人。与此同时，美国5月失业率降至3.8%，创18年新低；时薪数据增速超预期。
         * major_id : 0
         */

        private String article_id;
        private String codeX;
        private String title;
        private String cate_id;
        private String link;
        private String content;
        private String sort_order;
        private String if_show;
        private long add_time;
        private int morning;
        private String recommend;
        private String art_keywords;
        private String art_description;
        private String major_id;

        public String getArticle_id() {
            return article_id;
        }

        public void setArticle_id(String article_id) {
            this.article_id = article_id;
        }

        public String getCodeX() {
            return codeX;
        }

        public void setCodeX(String codeX) {
            this.codeX = codeX;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCate_id() {
            return cate_id;
        }

        public void setCate_id(String cate_id) {
            this.cate_id = cate_id;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getSort_order() {
            return sort_order;
        }

        public void setSort_order(String sort_order) {
            this.sort_order = sort_order;
        }

        public String getIf_show() {
            return if_show;
        }

        public void setIf_show(String if_show) {
            this.if_show = if_show;
        }

        public long getAdd_time() {
            return add_time;
        }

        public void setAdd_time(long add_time) {
            this.add_time = add_time;
        }

        public int getMorning() {
            return morning;
        }

        public void setMorning(int morning) {
            this.morning = morning;
        }

        public String getRecommend() {
            return recommend;
        }

        public void setRecommend(String recommend) {
            this.recommend = recommend;
        }

        public String getArt_keywords() {
            return art_keywords;
        }

        public void setArt_keywords(String art_keywords) {
            this.art_keywords = art_keywords;
        }

        public String getArt_description() {
            return art_description;
        }

        public void setArt_description(String art_description) {
            this.art_description = art_description;
        }

        public String getMajor_id() {
            return major_id;
        }

        public void setMajor_id(String major_id) {
            this.major_id = major_id;
        }
    }


}

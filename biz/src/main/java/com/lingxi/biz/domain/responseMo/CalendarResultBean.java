package com.lingxi.biz.domain.responseMo;

import android.text.TextUtils;

import com.lingxi.biz.util.ConstantUtil;

import java.util.List;

public class CalendarResultBean extends BaseMo {

    public List<CalendarBean> finance;

    public static class CalendarBean {

        /**
         * id : 949497
         * importance : 3
         * influence : 利多金银
         * level : 0
         * mark : BB
         * previous : 53.10
         * actual : 53.30
         * forecast :
         * revised :
         * push_status : 1
         * related_assets :
         * remark :
         * stars : 3
         * timestamp : 1524443400
         * title : 4月制造业PMI初值
         * accurate_flag : 1
         * calendar_type : FD
         * category_id : 0
         * country : 日本
         * currency : JPY
         * description :
         * flagURL : https://www.api51.cn/84/39/2c/japan-2x.png1
         * ticker : MPMIJPMA Index
         * subscribe_status : 0
         * is_has_history_data : true
         * uri : https://www.api51.cn/calendar/784ffa8bbeffd57b4337c5e23deb6cb9/overview
         * calendar_key : 784ffa8bbeffd57b4337c5e23deb6cb9
         */

        private int id;
        private int importance;
        private String influence;
        private int level;
        private String mark;
        private String previous;
        private String actual;
        private String forecast;
        private String revised;
        private int push_status;
        private String related_assets;
        private String remark;
        private int stars;
        private int timestamp;
        private String title;
        private int accurate_flag;
        private String calendar_type;
        private int category_id;
        private String country;
        private String currency;
        private String description;
        private String flagURL;
        private String ticker;
        private int subscribe_status;
        private boolean is_has_history_data;
        private String uri;
        private String calendar_key;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getImportance() {
            return importance;
        }

        public void setImportance(int importance) {
            this.importance = importance;
        }

        public String getInfluence() {
            return influence;
        }

        public void setInfluence(String influence) {
            this.influence = influence;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getMark() {
            return mark;
        }

        public void setMark(String mark) {
            this.mark = mark;
        }

        public String getPrevious() {
            if (TextUtils.isEmpty(previous)){
                previous = ConstantUtil.SHOWDEFVALUE;
            }
            return previous;
        }

        public void setPrevious(String previous) {
            this.previous = previous;
        }

        public String getActual() {
            if (TextUtils.isEmpty(actual)){
                actual = ConstantUtil.SHOWDEFVALUE;
            }
            return actual;
        }

        public void setActual(String actual) {
            this.actual = actual;
        }

        public String getForecast() {
            if (TextUtils.isEmpty(forecast)){
                forecast = ConstantUtil.SHOWDEFVALUE;
            }
            return forecast;
        }

        public void setForecast(String forecast) {
            this.forecast = forecast;
        }

        public String getRevised() {
            return revised;
        }

        public void setRevised(String revised) {
            this.revised = revised;
        }

        public int getPush_status() {
            return push_status;
        }

        public void setPush_status(int push_status) {
            this.push_status = push_status;
        }

        public String getRelated_assets() {
            return related_assets;
        }

        public void setRelated_assets(String related_assets) {
            this.related_assets = related_assets;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getStars() {
            return stars;
        }

        public void setStars(int stars) {
            this.stars = stars;
        }

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getAccurate_flag() {
            return accurate_flag;
        }

        public void setAccurate_flag(int accurate_flag) {
            this.accurate_flag = accurate_flag;
        }

        public String getCalendar_type() {
            return calendar_type;
        }

        public void setCalendar_type(String calendar_type) {
            this.calendar_type = calendar_type;
        }

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getFlagURL() {
            return flagURL;
        }

        public void setFlagURL(String flagURL) {
            this.flagURL = flagURL;
        }

        public String getTicker() {
            return ticker;
        }

        public void setTicker(String ticker) {
            this.ticker = ticker;
        }

        public int getSubscribe_status() {
            return subscribe_status;
        }

        public void setSubscribe_status(int subscribe_status) {
            this.subscribe_status = subscribe_status;
        }

        public boolean isIs_has_history_data() {
            return is_has_history_data;
        }

        public void setIs_has_history_data(boolean is_has_history_data) {
            this.is_has_history_data = is_has_history_data;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public String getCalendar_key() {
            return calendar_key;
        }

        public void setCalendar_key(String calendar_key) {
            this.calendar_key = calendar_key;
        }
    }
}

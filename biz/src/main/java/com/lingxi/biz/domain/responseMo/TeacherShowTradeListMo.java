package com.lingxi.biz.domain.responseMo;

import java.util.List;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class TeacherShowTradeListMo extends BaseMo {

    /**
     * sheets : [{"tea_name":"张明老师","profit":"100.00","profit_time":"2018-01-01 08:33:00","thumb":"https://fenghe3b85acb.png","tea_img_url":"https://fenghe161.oss-cn-shenzhdd614385a.png"},{"tea_name":"张明老师","profit":"50.00","profit_time":"2018-01-01 08:33:00","thumb":"https://fengh0c9c553e82f1.png","tea_img_url":"https://fenghdd614385a.png"}]
     * page : 1
     * total : 2
     * countpage : 1
     */

    private int page;
    private int total;
    private int countpage;
    private List<SheetsBean> sheets;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCountpage() {
        return countpage;
    }

    public void setCountpage(int countpage) {
        this.countpage = countpage;
    }

    public List<SheetsBean> getSheets() {
        return sheets;
    }

    public void setSheets(List<SheetsBean> sheets) {
        this.sheets = sheets;
    }

    public static class SheetsBean {
        /**
         * tea_name : 张明老师
         * profit : 100.00
         * profit_time : 2018-01-01 08:33:00
         * thumb : https://fenghe3b85acb.png
         * tea_img_url : https://fenghe161.oss-cn-shenzhdd614385a.png
         */

        private String tea_name;
        private String profit;
        private String profit_time;
        private String thumb;
        private String tea_img_url;
        private boolean blankData;

        public String getTea_name() {
            return tea_name;
        }

        public void setTea_name(String tea_name) {
            this.tea_name = tea_name;
        }

        public String getProfit() {
            return profit;
        }

        public void setProfit(String profit) {
            this.profit = profit;
        }

        public String getProfit_time() {
            return profit_time;
        }

        public void setProfit_time(String profit_time) {
            this.profit_time = profit_time;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getTea_img_url() {
            return tea_img_url;
        }

        public void setTea_img_url(String tea_img_url) {
            this.tea_img_url = tea_img_url;
        }

        public boolean isBlankData() {
            return blankData;
        }

        public void setBlankData(boolean blankData) {
            this.blankData = blankData;
        }
    }
}

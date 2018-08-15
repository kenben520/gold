package com.lingxi.biz.domain.responseMo;

import java.util.List;

/**
 * Created by qian on 2018/4/24.
 */

public class MessQuickResultBean extends BaseMo{


    /**
     * items : [{"display_time":1529683201,"red":0,"content_text":"欧元区 6月 Markit综合PMI初值","front":"54.10","expect":"53.90","actual":"54.80","red_get":"欧元,金银","green_get":"美元","orange_get":0,"special":1},{"display_time":1529683200,"red":1,"content_text":"欧元区 6月 Markit制造业PMI初值","front":"55.50","expect":"55","actual":"55","red_get":"","green_get":"","orange_get":1,"special":1},{"display_time":1529683049,"red":0,"content_text":"中国台湾 5月 失业率","front":"3.69%","expect":"3.70%","actual":"3.69%","red_get":"","green_get":"","orange_get":0,"special":1},{"display_time":1529683031,"red":0,"content_text":"俄罗斯外交部消息人士：俄罗斯不排除在今年夏季与美国就战略稳定对话举行峰会","special":0},{"display_time":1529682824,"red":0,"content_text":"美元兑瑞郎自日低短线拉升近20点，至0.9899","special":0},{"display_time":1529682727,"red":0,"content_text":"前外汇局国际收支司司长管涛：外汇市场不必对中美贸易争端反应过度，也不必由此过于看空人民币。中长期来看，人民币没有大幅贬值的概率，也没有大幅贬值的空间。（中国外汇）","special":0}]
     * page : 1
     * total : 90
     * countpage : 9
     */

    private int page;
    private int total;
    private int countpage;
    private List<ItemsBean> items;

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

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * display_time : 1529683201
         * red : 0
         * content_text : 欧元区 6月 Markit综合PMI初值
         * front : 54.10
         * expect : 53.90
         * actual : 54.80
         * red_get : 欧元,金银
         * green_get : 美元
         * orange_get : 0
         * special : 1
         */

        private long display_time;
        private int red;
        private String content_text;
        private String front;
        private String expect;
        private String actual;
        private String red_get;
        private String green_get;
        private int orange_get;
        private int special;

        public long getDisplay_time() {
            return display_time;
        }

        public void setDisplay_time(long display_time) {
            this.display_time = display_time;
        }

        public int getRed() {
            return red;
        }

        public void setRed(int red) {
            this.red = red;
        }

        public String getContent_text() {
            return content_text;
        }

        public void setContent_text(String content_text) {
            this.content_text = content_text;
        }

        public String getFront() {
            return front;
        }

        public void setFront(String front) {
            this.front = front;
        }

        public String getExpect() {
            return expect;
        }

        public void setExpect(String expect) {
            this.expect = expect;
        }

        public String getActual() {
            return actual;
        }

        public void setActual(String actual) {
            this.actual = actual;
        }

        public String getRed_get() {
            return red_get;
        }

        public void setRed_get(String red_get) {
            this.red_get = red_get;
        }

        public String getGreen_get() {
            return green_get;
        }

        public void setGreen_get(String green_get) {
            this.green_get = green_get;
        }

        public int getOrange_get() {
            return orange_get;
        }

        public void setOrange_get(int orange_get) {
            this.orange_get = orange_get;
        }

        public int getSpecial() {
            return special;
        }

        public void setSpecial(int special) {
            this.special = special;
        }
    }
}

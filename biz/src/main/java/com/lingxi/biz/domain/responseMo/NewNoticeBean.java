package com.lingxi.biz.domain.responseMo;

import java.util.List;

/**
 * Created by qian on 2018/4/24.
 */

public class NewNoticeBean extends BaseMo {

    private String total;
    private int page;
    private int countpage;
    private List<ItemsBean> items;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
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
         * is_show : 1
         * notice_id : 10
         * add_time : 1525750644
         * title : 非农就业数据公布温馨提示
         * type : 1
         * content : 2018年5月4日(五)晚上20时30分(北京时间) 美国将公布2018年4月份非农就业数据。
         * <p>
         * 美盛达贵金属为客户提供一个「公平、透明」的电子交易环境，然而受交易市场的流动性及波动性影响，因此在行情波动大或者市场流动性不足时，公司可能会根据实际市况调整挂单距离及点差费用。
         * formet_start_time : 2018-05-01 16:00:00
         * formet_end_time : 2018-07-31 16:00:00
         * formet_add_time : 2018-05-08 03:37:24
         * link : http://lx.com/index.php?app=notice&act=app_notice_detail&notice_id=10&version=1.5
         */

        private String is_show;
        private int notice_id;
        private String add_time;
        private String title;
        private String type;
        private String content;
        private String formet_start_time;
        private String formet_end_time;
        private String formet_add_time;
        private String link;

        public String getIs_show() {
            return is_show;
        }

        public void setIs_show(String is_show) {
            this.is_show = is_show;
        }

        public int getNotice_id() {
            return notice_id;
        }

        public void setNotice_id(int notice_id) {
            this.notice_id = notice_id;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getFormet_start_time() {
            return formet_start_time;
        }

        public void setFormet_start_time(String formet_start_time) {
            this.formet_start_time = formet_start_time;
        }

        public String getFormet_end_time() {
            return formet_end_time;
        }

        public void setFormet_end_time(String formet_end_time) {
            this.formet_end_time = formet_end_time;
        }

        public String getFormet_add_time() {
            return formet_add_time;
        }

        public void setFormet_add_time(String formet_add_time) {
            this.formet_add_time = formet_add_time;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }
}

package com.lingxi.biz.domain.responseMo;

import java.util.List;

/**
 * Created by qian on 2018/4/24.
 */

public class SpecialRemarkBean extends BaseMo {

    private List<ArticlesBean> articles;

    public List<ArticlesBean> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticlesBean> articles) {
        this.articles = articles;
    }

    public static class ArticlesBean {
        /**
         * article_id : 817
         * title : 意大利政局动荡加重 黄金终结两连跌
         * sort_order : 255
         * add_time : 1527616432
         * morning : 1
         * content : 欧洲政坛动乱加剧，意大利政局动荡加重了市场的避险情绪，西班牙大选也即将来临，
         * link : http://www.lingxi.net/index.php?app=article&act=expert_view_app&article_id=817
         */

        private String article_id;
        private String title;
        private int sort_order;
        private long add_time;
        private int morning;
        private String content;
        private String link;

        public String getArticle_id() {
            return article_id;
        }

        public void setArticle_id(String article_id) {
            this.article_id = article_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getSort_order() {
            return sort_order;
        }

        public void setSort_order(int sort_order) {
            this.sort_order = sort_order;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }
}

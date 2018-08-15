package com.lingxi.biz.domain.responseMo;

import java.util.List;

public class StudySearchResultBean extends BaseMo {


    private List<ArticlesBean> articles;

    public List<ArticlesBean> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticlesBean> articles) {
        this.articles = articles;
    }

    public static class ArticlesBean {
        /**
         * article_id : 3965
         * title : 现货黄金交易杠杆
         * add_time : 1530478800
         * link : http://lx.com/index.php?app=article&act=view_app&article_id=3965
         */

        private String article_id;
        private String title;
        private long add_time;
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

        public long getAdd_time() {
            return add_time;
        }

        public void setAdd_time(long add_time) {
            this.add_time = add_time;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }
}

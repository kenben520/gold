package com.lingxi.biz.domain.responseMo;

import java.util.List;

/**
 * Created by qian on 2018/4/24.
 */

public class SpecialViewPointBean extends BaseMo{


    private List<ArticlesBean> articles;

    public List<ArticlesBean> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticlesBean> articles) {
        this.articles = articles;
    }

    public static class ArticlesBean {
        /**
         * article_id : 1014
         * title : 北美自贸谈判仍面临诸多不确定性
         * sort_order : 255
         * add_time : 1528183129
         * teacher_id : 1
         * image : https://fenghe161.oss-cn-shenzhen.aliyuncs.com/uploads/image_pc/20180622/1529639206.9542819.jpg?x-oss-process=image/resize,m_lfit,w_600,limit_0/auto-orient,1/quality,q_90
         * tea_name : 小米老师
         * content : ① 墨西哥将于7月1日举行总统大选，美国将于11月中旬举行中期选举，关于北美自贸协
         * link : http://www.lingxi.net/index.php?app=article&act=viewpoint_view_app&article_id=1014
         */

        private String article_id;
        private String title;
        private int sort_order;
        private long add_time;
        private String teacher_id;
        private String image;
        private String tea_name;
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

        public String getTeacher_id() {
            return teacher_id;
        }

        public void setTeacher_id(String teacher_id) {
            this.teacher_id = teacher_id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getTea_name() {
            return tea_name;
        }

        public void setTea_name(String tea_name) {
            this.tea_name = tea_name;
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

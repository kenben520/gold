package com.lingxi.biz.domain.responseMo;

import java.util.List;

/**
 * Created by zhangwei on 2018/4/25.
 */

public class TeacherOpinionslMo extends BaseMo {

    /**
     * articles : [{"title":"测试的视频标题","image":"https://fenghe161.oss-cn-shenzhen.aliyuncs.com/","add_time":"2018-01-01 08:00:00","link":"http://www.msd.cn/index.php?app=article&act=expert_view_app&article_id=1030"}]
     * page : 1
     * total : 1
     * countpage : 1
     */

    private int page;
    private int total;
    private int countpage;
    private List<ArticlesBean> articles;

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

    public List<ArticlesBean> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticlesBean> articles) {
        this.articles = articles;
    }

    public static class ArticlesBean {
        /**
         * title : 测试的视频标题
         * image : https://fenghe161.oss-cn-shenzhen.aliyuncs.com/
         * add_time : 2018-01-01 08:00:00
         * link : http://www.msd.cn/index.php?app=article&act=expert_view_app&article_id=1030
         */

        private String title;
        private String image;
        private String add_time;
        private String link;
        private boolean blankData;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public boolean isBlankData() {
            return blankData;
        }

        public void setBlankData(boolean blankData) {
            this.blankData = blankData;
        }
    }
}

package com.lingxi.biz.domain.responseMo;

import java.util.List;

/**
 * Created by qian on 2018/4/24.
 */

public class MessExpertResultMo extends  BaseMo {

    public List<ArticlesMBean> articles;

    public static class ArticlesMBean {
        public String article_id;
        public String title;
        public String sort_order;
        public long add_time;
        public int morning;//0晚上 1早上
        public String content;
        public String link;
    }
}

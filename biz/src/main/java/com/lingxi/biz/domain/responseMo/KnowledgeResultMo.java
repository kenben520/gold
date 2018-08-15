package com.lingxi.biz.domain.responseMo;

import java.io.Serializable;
import java.util.List;

public class KnowledgeResultMo extends BaseMo {

    public List<ArticlesBean> articles;


    public static class ArticlesBean implements Serializable{
        /**
         * article_id : 11
         * title : 投资入门资讯1
         * sort_order : 255
         * add_time : 1240122848
         */

        public String article_id;
        public String title;
        public String sort_order;
        public long add_time;
        public String link;
        public String content;

    }
}

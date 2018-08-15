package com.lingxi.biz.domain.responseMo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qian on 2018/4/24.
 */

public class MessImportResultBean extends BaseMo{
    public List<ItemsBean> items;

    public static class ItemsBean implements Serializable {
        public String major_id;
        public String title;
        public String short_content;
        public String content;
        public long add_time;
    }
}

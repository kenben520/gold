package com.lingxi.biz.domain.responseMo;

import java.util.List;

/**
 * Created by zhangwei on 2018/4/25.
 */

public class AnnouncementListMo<T> extends BaseMo {
    public int total;
    public int page;
    public int countpage;
    public List<T> items;
}

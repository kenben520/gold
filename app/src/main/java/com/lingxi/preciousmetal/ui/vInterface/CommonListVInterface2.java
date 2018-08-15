package com.lingxi.preciousmetal.ui.vInterface;

import com.lingxi.biz.domain.responseMo.KnowledgeResultMo;

import java.util.List;

/**
 * Created by zhangwei on 2018/4/25.
 */

public interface CommonListVInterface2 {
    public void showList(List<KnowledgeResultMo.ArticlesBean> videoList);

    public void showFail(String msg);
}

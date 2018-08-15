package com.lingxi.preciousmetal.ui.listener;

import com.lingxi.biz.domain.responseMo.LiveProgramListMo;

import java.util.List;

/**
 * Created by zhangwei on 2018/5/28.
 */

public interface DataChangeListener<T> {
    void onDataChange(T liveProgramBeans);
}

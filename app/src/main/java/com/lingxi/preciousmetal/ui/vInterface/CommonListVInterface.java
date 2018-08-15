package com.lingxi.preciousmetal.ui.vInterface;

import java.util.List;

/**
 * Created by zhangwei on 2018/4/25.
 */

public interface CommonListVInterface<T> {
    public void showList(List<T> videoList);

    public void showFail(String msg);
}

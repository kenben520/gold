package com.lingxi.preciousmetal.domain;

import java.io.Serializable;

/**
 * Created by zhangwei on 2018/4/20.
 */

public class BaseBean<M> implements Serializable {
    protected M mo;

    public BaseBean(M mo) {
        this.mo = mo;
    }
}

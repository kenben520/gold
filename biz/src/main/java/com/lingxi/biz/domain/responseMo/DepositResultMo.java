package com.lingxi.biz.domain.responseMo;

import java.io.Serializable;

/**
 * Created by zhangwei on 2018/5/20.
 */

public class DepositResultMo implements Serializable {
    /**
     * comment : deposit 21001.12
     * depositedTime : 1497852054348
     */

    private String comment;
    private long depositedTime;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getDepositedTime() {
        return depositedTime;
    }

    public void setDepositedTime(long depositedTime) {
        this.depositedTime = depositedTime;
    }
}

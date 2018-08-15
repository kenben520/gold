package com.lingxi.biz.domain.responseMo;

import java.io.Serializable;

/**
 * Created by zhangwei on 2018/4/18.
 */
public class BasePushImMo implements Serializable{
    private String title;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

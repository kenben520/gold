package com.lingxi.biz.domain.responseMo;

/**
 * Created by zhangwei on 2018/4/20.
 */

public class AgreementMo extends BaseMo {
    /**
     * title : 客户协议书
     * content : 代理经纪人披露美盛达贵金属有限公司(以下简称「本公司」)和代理经纪人完全独立，本公司没有监管其代理经纪人的活动，并不对代理经纪人作出的声明负责。本公司和代理经纪人直接的协议并不建立合伙或合资机构关系，因此代理经纪人不是本公司的代表或职员....。
     */

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

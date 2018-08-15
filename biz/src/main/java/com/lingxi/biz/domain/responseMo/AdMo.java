package com.lingxi.biz.domain.responseMo;

/**
 * Created by zhangwei on 2018/4/20.
 */

public class AdMo extends BaseMo {

    /**
     * title : app广告启动图1
     * image : https://fenghe161.oss-cn-shenzhen.aliyuncs.com/uploads/image_pc/20180724/1532421655.87221337.jpg
     * link : http://www.lingxi.net/
     */

    private String title;
    private String id;
    private String image;
    private String link;
    private long showTime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public long getShowTime() {
        return showTime;
    }

    public void setShowTime(long showTime) {
        this.showTime = showTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

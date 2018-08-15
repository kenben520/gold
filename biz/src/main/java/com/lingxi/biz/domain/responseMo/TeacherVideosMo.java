package com.lingxi.biz.domain.responseMo;

import java.util.List;

/**
 * Created by zhangwei on 2018/4/25.
 */

public class TeacherVideosMo extends BaseMo {

    /**
     * videos : [{"video_id":"0a12e5bb9d4b84a87f5e26c099b25370_0","image":"https://fenghe161.oss-635373.8829459.png","title":"123"},{"video_id":"0a12e5bb9d4b84a87f5e26c099b25370_0","image":"https://fenghe635353.17241153.jpg","title":"abac"}]
     * page : 1
     * total : 2
     * countpage : 1
     */

    private int page;
    private int total;
    private int countpage;
    private List<StudyVideoBean.VideoListBean> videos;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCountpage() {
        return countpage;
    }

    public void setCountpage(int countpage) {
        this.countpage = countpage;
    }

    public List<StudyVideoBean.VideoListBean> getVideos() {
        return videos;
    }

    public void setVideos(List<StudyVideoBean.VideoListBean> videos) {
        this.videos = videos;
    }
}

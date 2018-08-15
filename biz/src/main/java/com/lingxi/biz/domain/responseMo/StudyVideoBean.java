package com.lingxi.biz.domain.responseMo;

import android.text.TextUtils;

import java.util.List;

public class StudyVideoBean extends BaseMo {

    private List<VideoListBean> video_list;

    public List<VideoListBean> getVideo_list() {
        return video_list;
    }

    public void setVideo_list(List<VideoListBean> video_list) {
        this.video_list = video_list;
    }

    public static class VideoListBean extends BaseMo {
        /**
         * id : 4
         * title : 测试
         * video_id : 0a12e5bb9dcd362a381493916d6a529a_0
         * link : http://nbachina.qq.com/a/20180619/030357.htm
         * type : 2
         * image : https://fenghe161.oss-cn-shenzhen.aliyuncs.com//0
         * add_time : 1970-01-01 08:00
         * tea_name : 小米老师
         */

        private String id;
        private String title;
        private String video_id;
        private String link;
        private String type;
        private String image;
        private long add_time_stamp;
        private String add_time;
        private String tea_name;
        private boolean blankData;
        private String sub_title;
        private String video_time;

        public String getSub_title() {
            return sub_title;
        }

        public void setSub_title(String sub_title) {
            this.sub_title = sub_title;
        }

        public String getVideo_time() {
            return video_time;
        }

        public void setVideo_time(String video_time) {
            this.video_time = video_time;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public long getAdd_time_stamp() {
            return add_time_stamp;
        }

        public void setAdd_time_stamp(long add_time_stamp) {
            this.add_time_stamp = add_time_stamp;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getTea_name() {
            if (!TextUtils.isEmpty(tea_name) && tea_name.length()>5){
                this.tea_name = tea_name.substring(0,5)+"...";
            }
            return tea_name;
        }

        public void setTea_name(String tea_name) {
            this.tea_name = tea_name;
        }

        public boolean isBlankData() {
            return blankData;
        }

        public void setBlankData(boolean blankData) {
            this.blankData = blankData;
        }
    }
}

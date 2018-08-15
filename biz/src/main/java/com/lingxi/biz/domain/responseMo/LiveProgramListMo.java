package com.lingxi.biz.domain.responseMo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangwei on 2018/5/25.
 */

public class LiveProgramListMo extends BaseMo {

    public List<LiveProgramBean> friday;
    public List<LiveProgramBean> thursday;
    public List<LiveProgramBean> wednesday;
    public List<LiveProgramBean> tuesday;
    public List<LiveProgramBean> monday;
    public List<String> date;
    public String b_image_url;
    public class LiveProgramBean implements Serializable {
        /**
         * title : 欧市直击
         * teacher : 肖政老司机
         * start_time : 00:00
         * end_time : 2:00
         * what_day : 5
         * video_id : 22
         */

        public String title;
        public String teacher;
        public String start_time;
        public String end_time;
        public int what_day;
        public String video_id;
        public boolean state;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTeacher() {
            return teacher;
        }

        public void setTeacher(String teacher) {
            this.teacher = teacher;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public int getWhat_day() {
            return what_day;
        }

        public void setWhat_day(int what_day) {
            this.what_day = what_day;
        }

        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }

        public boolean isState() {
            return state;
        }

        public void setState(boolean state) {
            this.state = state;
        }


    }
}

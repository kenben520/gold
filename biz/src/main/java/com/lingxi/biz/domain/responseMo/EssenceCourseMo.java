package com.lingxi.biz.domain.responseMo;

import java.util.List;

/**
 * Created by zhangwei on 2018/5/25.
 */

public class EssenceCourseMo extends BaseMo {

    /**
     * teacher : {"tea_name":"小米","tea_title":"大神,快准狠","tea_total_click":"173","tea_win_num":"89","tea_profit":"888.00","start_time":"1526968303","end_time":"1527985703","id":"1"}
     * course : [{"cou_tea_title":"测试","cou_tea_comment":"美元大幅升值，美国利率走高以及地缘政治局势缓和都是推动黄金下跌的动力。若是晚间GDP继续高于预期，美元将有可能继续创新。","cou_tea_image":"http://www.lxlumen.com/uploads/image_pc/20180521/d7613fac2bddd4ce3ff38b8cc528c74a.png,http://www.lumen-api.com/uploads/image_pc/20180521/62c53aebba10d483e3eb8f47bb810390.png","cou_release_time":"1526963635","id":"1"},{"cou_tea_title":"测试","cou_tea_comment":"在前日黄金二度向上攻击1326不成，行情急转向下，最低来到1315，w底型态失败，以当前的盘面来看，既然w底失败，盘面连接1355-1333-1326下降轨形成，接下来就是确认突破信号，就操作而言，盘面持续偏向弱势，多单不宜过度乐观，空单也毋需追单，追单需严设风控。","cou_tea_image":"http://www.lxlumen.com/uploads/image_pc/20180521/d7613fac2bddd4ce3ff38b8cc528c74a.png,http://www.lumen-api.com/uploads/image_pc/20180521/62c53aebba10d483e3eb8f47bb810390.png","cou_release_time":"1526963635","id":"2"}]
     */

    private TeacherBean teacher;
    private List<CourseBean> course;

    public TeacherBean getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherBean teacher) {
        this.teacher = teacher;
    }

    public List<CourseBean> getCourse() {
        return course;
    }

    public void setCourse(List<CourseBean> course) {
        this.course = course;
    }

    public class TeacherBean {
        /**
         * tea_name : 小米
         * tea_title : 大神,快准狠
         * tea_total_click : 173
         * tea_win_num : 89
         * tea_profit : 888.00
         * start_time : 1526968303
         * end_time : 1527985703
         * id : 1
         */

        private String tea_name;
        private String tea_dis_comment;
        private String tea_img_url;
        private List<String> tea_title;
        private List<String> teacher_click_hash;
        private int tea_total_click;
        private String tea_win_num;
        private float tea_profit;
        private long start_time;
        private long end_time;
        private String id;
        private String title;
        private boolean isPriase;
        private int is_online;//1 直播中 //2 已结束 //3 未开始 //4暂无直播
        private int online_cur_num;

        public String getTea_name() {
            return tea_name;
        }

        public void setTea_name(String tea_name) {
            this.tea_name = tea_name;
        }

        public String getTea_dis_comment() {
            return tea_dis_comment;
        }

        public void setTea_dis_comment(String tea_dis_comment) {
            this.tea_dis_comment = tea_dis_comment;
        }

        public String getTea_img_url() {
            return tea_img_url;
        }

        public void setTea_img_url(String tea_img_url) {
            this.tea_img_url = tea_img_url;
        }

        public List<String> getTea_title() {
            return tea_title;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setTea_title(List<String> tea_title) {
            this.tea_title = tea_title;
        }

        public int getTea_total_click() {
            return tea_total_click;
        }

        public void setTea_total_click(int tea_total_click) {
            this.tea_total_click = tea_total_click;
        }

        public String getTea_win_num() {
            return tea_win_num;
        }

        public void setTea_win_num(String tea_win_num) {
            this.tea_win_num = tea_win_num;
        }

        public float getTea_profit() {
            return tea_profit;
        }

        public void setTea_profit(float tea_profit) {
            this.tea_profit = tea_profit;
        }

        public long getStart_time() {
            return start_time;
        }

        public void setStart_time(long start_time) {
            this.start_time = start_time;
        }

        public long getEnd_time() {
            return end_time;
        }

        public void setEnd_time(long end_time) {
            this.end_time = end_time;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isPriase() {
            return isPriase;
        }

        public void setPriase(boolean priase) {
            isPriase = priase;
        }

        public int getIs_online() {
            return is_online;
        }

        public void setIs_online(int is_online) {
            this.is_online = is_online;
        }

        public int getOnline_cur_num() {
            return online_cur_num;
        }

        public void setOnline_cur_num(int online_cur_num) {
            this.online_cur_num = online_cur_num;
        }

        public List<String> getTeacher_click_hash() {
            return teacher_click_hash;
        }

        public void setTeacher_click_hash(List<String> teacher_click_hash) {
            this.teacher_click_hash = teacher_click_hash;
        }
    }

    public class CourseBean {
        /**
         * cou_tea_title : 测试
         * cou_tea_comment : 美元大幅升值，美国利率走高以及地缘政治局势缓和都是推动黄金下跌的动力。若是晚间GDP继续高于预期，美元将有可能继续创新。
         * cou_tea_image : http://www.lxlumen.com/uploads/image_pc/20180521/d7613fac2bddd4ce3ff38b8cc528c74a.png,http://www.lumen-api.com/uploads/image_pc/20180521/62c53aebba10d483e3eb8f47bb810390.png
         * cou_release_time : 1526963635
         * id : 1
         */

        private String cou_tea_title;
        private String cou_tea_comment;
        private String cou_tea_image;
        private String tea_name;
        private long cou_release_time;
        private String id;

        public String getTea_name() {
            return tea_name;
        }

        public void setTea_name(String tea_name) {
            this.tea_name = tea_name;
        }

        public String getCou_tea_title() {
            return cou_tea_title;
        }

        public void setCou_tea_title(String cou_tea_title) {
            this.cou_tea_title = cou_tea_title;
        }

        public String getCou_tea_comment() {
            return cou_tea_comment;
        }

        public void setCou_tea_comment(String cou_tea_comment) {
            this.cou_tea_comment = cou_tea_comment;
        }

        public String getCou_tea_image() {
            return cou_tea_image;
        }

        public void setCou_tea_image(String cou_tea_image) {
            this.cou_tea_image = cou_tea_image;
        }

        public long getCou_release_time() {
            return cou_release_time;
        }

        public void setCou_release_time(long cou_release_time) {
            this.cou_release_time = cou_release_time;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}

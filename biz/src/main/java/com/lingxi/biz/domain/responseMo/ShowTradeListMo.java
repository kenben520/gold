package com.lingxi.biz.domain.responseMo;

import java.util.List;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class ShowTradeListMo extends BaseMo {

    private List<SheetsListBean> sheets_list;

    public List<SheetsListBean> getSheets_list() {
        return sheets_list;
    }

    public void setSheets_list(List<SheetsListBean> sheets_list) {
        this.sheets_list = sheets_list;
    }

    public static class SheetsListBean {
        /**
         * she_user_id : 1
         * she_add_time : 1526959635
         * she_click_num : 2
         * she_image : http://www.lxlumen.com/uploads/image_pc/20180521/d7613fac2bddd4ce3ff38b8cc528c74a.png,http://www.lumen-api.com/uploads/image_pc/20180521/62c53aebba10d483e3eb8f47bb810390.png
         * she_heart : 哈哈哈哈哈哈哈，快来玩贪玩蓝月，我又赚了1000美金。
         * user_name : admin
         * avatars :
         * id : 1
         */

        private String she_user_id;
        private long she_add_time;
        private int she_click_num;
        private String she_image;
        private String she_heart;
        private String user_name;
        private String avatars;
        private String id;
        private boolean yourself_into;
        private List<String> thumbs_up_people;

        public String getShe_user_id() {
            return she_user_id;
        }

        public void setShe_user_id(String she_user_id) {
            this.she_user_id = she_user_id;
        }

        public long getShe_add_time() {
            return she_add_time;
        }

        public void setShe_add_time(long she_add_time) {
            this.she_add_time = she_add_time;
        }

        public int getShe_click_num() {
            return she_click_num;
        }

        public void setShe_click_num(int she_click_num) {
            this.she_click_num = she_click_num;
        }

        public String getShe_image() {
            return she_image;
        }

        public void setShe_image(String she_image) {
            this.she_image = she_image;
        }

        public String getShe_heart() {
            return she_heart;
        }

        public void setShe_heart(String she_heart) {
            this.she_heart = she_heart;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getAvatars() {
            return avatars;
        }

        public void setAvatars(String avatars) {
            this.avatars = avatars;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isYourself_into() {
            return yourself_into;
        }

        public List<String> getThumbs_up_people() {
            return thumbs_up_people;
        }

        public void setThumbs_up_people(List<String> thumbs_up_people) {
            this.thumbs_up_people = thumbs_up_people;
        }

        public void setYourself_into(boolean yourself_into) {
            this.yourself_into = yourself_into;
        }
    }
}

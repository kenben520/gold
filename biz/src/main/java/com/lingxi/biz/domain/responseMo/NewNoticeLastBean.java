package com.lingxi.biz.domain.responseMo;

import java.util.List;

/**
 * Created by qian on 2018/4/24.
 */

public class NewNoticeLastBean extends BaseMo {


    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * notice_id : 10
         * type : 1
         */

        private int notice_id;
        private int type;

        public int getNotice_id() {
            return notice_id;
        }

        public void setNotice_id(int notice_id) {
            this.notice_id = notice_id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}

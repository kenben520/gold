package com.lingxi.preciousmetal.domain;

import java.io.Serializable;

public class TimeTitleBean implements Serializable {

    private String timeTitle;
    private int queryType;
    private int selectedIndex;

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    public String getTimeTitle() {
        return timeTitle;
    }

    public TimeTitleBean(String timeTitle, int queryType) {
        this.timeTitle = timeTitle;
        this.queryType = queryType;
    }

    public void setTimeTitle(String timeTitle) {
        this.timeTitle = timeTitle;
    }

    public int getQueryType() {
        return queryType;
    }

    public void setQueryType(int queryType) {
        this.queryType = queryType;
    }
}

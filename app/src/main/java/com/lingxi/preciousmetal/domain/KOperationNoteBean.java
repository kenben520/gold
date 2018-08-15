package com.lingxi.preciousmetal.domain;

import java.util.List;

public class KOperationNoteBean {

    private int mainIndex;
    private String mainName;
    private int childIndex;
    private String childName;
    private List<FilterSection> indicatorList;
    private int kCurrentType;
    private int childDisplayOrGone;

    public int getMainIndex() {
        return mainIndex;
    }

    public void setMainIndex(int mainIndex) {
        this.mainIndex = mainIndex;
    }

    public String getMainName() {
        return mainName;
    }

    public void setMainName(String mainName) {
        this.mainName = mainName;
    }

    public int getChildIndex() {
        return childIndex;
    }

    public void setChildIndex(int childIndex) {
        this.childIndex = childIndex;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public List<FilterSection> getIndicatorList() {
        return indicatorList;
    }

    public void setIndicatorList(List<FilterSection> indicatorList) {
        this.indicatorList = indicatorList;
    }

    public int getkCurrentType() {
        return kCurrentType;
    }

    public void setkCurrentType(int kCurrentType) {
        this.kCurrentType = kCurrentType;
    }

    public int getChildDisplayOrGone() {
        return childDisplayOrGone;
    }

    public void setChildDisplayOrGone(int childDisplayOrGone) {
        this.childDisplayOrGone = childDisplayOrGone;
    }
}

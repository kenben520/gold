package com.lingxi.preciousmetal.domain;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class CalendarItem {

    private boolean isCheck;
    private String name;
    private boolean userCheck;
    private int type;
    private String typeId;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public boolean isUserCheck() {
        return userCheck;
    }

    public void setUserCheck(boolean userCheck) {
        this.userCheck = userCheck;
    }

    public CalendarItem(boolean isCheck, String name,int type,String typeId) {
        this.isCheck = isCheck;
        this.name = name;
        this.type = type;
        this.typeId = typeId;
        this.userCheck = isCheck;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

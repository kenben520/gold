package com.lingxi.preciousmetal.ui.widget.verticalTabView;

import android.support.v4.app.Fragment;

public class TabViewChild {
    private String textViewText;
    private Fragment mFragment;


    private TabViewChild(){

    }
    public TabViewChild(String textViewText, Fragment mFragment) {
        this.textViewText = textViewText;
        this.mFragment=mFragment;
    }

    public String getTextViewText() {
        return textViewText;
    }


    public void setTextViewText(String textViewText) {
        this.textViewText = textViewText;
    }


    public Fragment getmFragment() {
        return mFragment;
    }

    public void setmFragment(Fragment mFragment) {
        this.mFragment = mFragment;
    }
}

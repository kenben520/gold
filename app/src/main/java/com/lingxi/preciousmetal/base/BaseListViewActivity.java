package com.lingxi.preciousmetal.base;

import android.os.Bundle;

import com.jaeger.library.StatusBarUtil;

public class BaseListViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, null);


    }
}

package com.lingxi.preciousmetal.base;

import android.os.Bundle;

import com.jaeger.library.StatusBarUtil;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class TranslucentStatusBarActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, null);
    }
}

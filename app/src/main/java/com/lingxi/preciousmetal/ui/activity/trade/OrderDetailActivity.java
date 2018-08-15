package com.lingxi.preciousmetal.ui.activity.trade;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;

public class OrderDetailActivity extends TranslucentStatusBarActivity {


    public static void startMyActivity(Context context, int type) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_order_detail);

    }
}

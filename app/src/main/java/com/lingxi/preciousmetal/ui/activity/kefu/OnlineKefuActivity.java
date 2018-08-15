package com.lingxi.preciousmetal.ui.activity.kefu;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.hyphenate.helpdesk.easeui.util.Config;
import com.jaeger.library.StatusBarUtil;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.BaseActivity;
import com.lingxi.preciousmetal.ui.fragment.KefuFragment;
import com.lingxi.preciousmetal.ui.widget.TopBarView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangwei on 2018/8/6.
 */

public class OnlineKefuActivity extends BaseActivity {

    @BindView(R.id.topbar_view)
    TopBarView mTopbarView;
    @BindView(R.id.container)
    FrameLayout container;
    public static OnlineKefuActivity instance = null;
    String toChatUsername;
    private KefuFragment chatFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_kefu);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.black));
        instance = this;
        ButterKnife.bind(this);
        initTopBar();
        initView();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
            //IM服务号
            toChatUsername = bundle.getString(Config.EXTRA_SERVICE_IM_NUMBER);
        //可以直接new ChatFragment使用
        String chatFragmentTAG = "chatFragment";
        chatFragment = (KefuFragment) getSupportFragmentManager().findFragmentByTag(chatFragmentTAG);
        if (chatFragment == null){
            chatFragment = KefuFragment.newInstance();
//            //传入参数
//            chatFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment, chatFragmentTAG).commit();
        }
    }

    private void initView() {
    }

    private void initTopBar() {
        mTopbarView.setMode(TopBarView.MODE_BACK);
        mTopbarView.setTitle("在线客服");
    }

}

package com.lingxi.preciousmetal.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;
import com.lingxi.preciousmetal.domain.UserInfoBean;
import com.lingxi.preciousmetal.login.LoginHelper;
import com.lingxi.preciousmetal.ui.activity.kefu.KefuLoginActivity;
import com.lingxi.preciousmetal.ui.widget.TopBarView;
import com.lingxi.preciousmetal.util.utilCode.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhangwei on 2018/4/24.
 */

public class DepositWithdrawMoneyActivity extends TranslucentStatusBarActivity {
    @BindView(R.id.topbar_view)
    TopBarView topbarView;
    @BindView(R.id.btn_withdraw)
    TextView btnWithdraw;
    @BindView(R.id.btn_deposit)
    TextView btnDeposit;
    UserInfoBean userInfoBean;
    @BindView(R.id.tv_tips1)
    TextView tvTips1;
    @BindView(R.id.tv_bank_name)
    TextView tvBankName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_bank_cd_num)
    TextView tvBankCdNum;
    @BindView(R.id.tv_bank_type)
    TextView tvBankType;
    @BindView(R.id.tv_money_type)
    TextView tvMoneyType;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, DepositWithdrawMoneyActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_withdraw_money_entrance);
        ButterKnife.bind(this);
        userInfoBean = LoginHelper.getInstance().getLoginUserInfo();
        initTopBar();
        initView();
    }

    private void initView() {
        if (userInfoBean != null) {
            tvBankName.setText(userInfoBean.bank_name);
            tvBankCdNum.setText(userInfoBean.bank_account_hide);
        }
        AppUtils.setCustomFont(this, tvBankCdNum);
    }

    private void initTopBar() {
        topbarView.setMode(TopBarView.MODE_BACK);
        topbarView.setTitle("资金存取");
        topbarView.setActionButton(R.drawable.ic_custom_service_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KefuLoginActivity.actionStart(DepositWithdrawMoneyActivity.this);
            }
        });
    }

    @OnClick({R.id.btn_withdraw, R.id.btn_deposit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_withdraw:
                WithdrawMoneyActivity.actionStart(DepositWithdrawMoneyActivity.this);
                break;
            case R.id.btn_deposit:
                DepositMoneyActivity.actionStart(DepositWithdrawMoneyActivity.this);
                break;
        }
    }
}

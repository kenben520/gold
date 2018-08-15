package com.lingxi.preciousmetal.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.DepositRequestMo;
import com.lingxi.biz.domain.responseMo.AccountInfoMo;
import com.lingxi.biz.domain.responseMo.DepositResultMo;
import com.lingxi.biz.service.TradeService;
import com.lingxi.common.util.StringUtil;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;
import com.lingxi.preciousmetal.domain.UserInfoBean;
import com.lingxi.preciousmetal.login.LoginHelper;
import com.lingxi.preciousmetal.ui.activity.kefu.KefuLoginActivity;
import com.lingxi.preciousmetal.ui.widget.TopBarView;
import com.lingxi.preciousmetal.ui.widget.dialog.CommonDialog;
import com.lingxi.preciousmetal.ui.widget.dialog.DialogManager;
import com.lingxi.preciousmetal.util.CashierInputFilter;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.lingxi.preciousmetal.ui.activity.PropertyDetailActivity.ACCOUNT_ID;

/**
 * Created by zhangwei on 2018/4/24.
 */

public class DepositMoneyActivity extends TranslucentStatusBarActivity {
    @BindView(R.id.topbar_view)
    TopBarView topbarView;
    UserInfoBean userInfoBean;
    @BindView(R.id.tv_user_account)
    TextView tvUserAccount;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_bank_cd_num)
    TextView tvBankCdNum;
    @BindView(R.id.edit_deposit_money_usd)
    EditText editDepositMoneyUsd;
    @BindView(R.id.tv_tips1)
    TextView tvTips1;
    @BindView(R.id.tv_deposit_money_rmb)
    TextView tvDepositMoneyRmb;
    @BindView(R.id.btn_submit)
    TextView btnSubmit;
    private AccountInfoMo accountInfoMo;
    private double exchangeRate = 6.4;
    private CommonDialog commonDialog;
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, DepositMoneyActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_money);
        userInfoBean = LoginHelper.getInstance().getLoginUserInfo();
        accountInfoMo = LoginHelper.getInstance().getAccountInfo();
        ButterKnife.bind(this);
        initTopBar();
        initView();
    }

    private void initView() {
        if (userInfoBean != null) {
            tvUserAccount.setText(userInfoBean.id_card_hide);
            tvUsername.setText(userInfoBean.user_name);
            String cardEnd4 = "";
            if (!StringUtil.isEmpty(userInfoBean.bank_account) && userInfoBean.bank_account.length() > 4) {
                cardEnd4 = userInfoBean.bank_account.substring(userInfoBean.bank_account.length() - 4);
            }
            String cardStr = userInfoBean.bank_name + "(尾号" + cardEnd4 + ")";
            tvBankCdNum.setText(cardStr);
        }

//        tvUserAccount.setText(ACCOUNT_ID);
//        tvUserAccount.setText("");
        tvTips1.setText("汇率" + exchangeRate);
        editDepositMoneyUsd.setFilters(new InputFilter[]{new CashierInputFilter()});
        editDepositMoneyUsd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String moneyUsdStr = s.toString();
                if (!TextUtils.isEmpty(moneyUsdStr)) {
                    Double moneyUsd = Double.parseDouble(moneyUsdStr);
                    changePayRmbMoney(moneyUsd);
                } else {
                    changePayRmbMoney(0.00);
                }
            }
        });
    }

    private void changePayRmbMoney(Double moneyUsd) {
        DecimalFormat nf1 = new DecimalFormat("##0.00");
        String str1 = nf1.format(moneyUsd * exchangeRate);
        tvDepositMoneyRmb.setText(str1);
    }

    private void initTopBar() {
        topbarView.setMode(TopBarView.MODE_BACK);
        topbarView.setTitle("资金存入");
        topbarView.setActionButton(R.drawable.ic_custom_service_white , new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KefuLoginActivity.actionStart(DepositMoneyActivity.this);
            }
        });
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        String moneyUsd = editDepositMoneyUsd.getText().toString().trim();
        if (TextUtils.isEmpty(moneyUsd)) {
            ToastUtils.showShort("请输入入金金额");
            return;
        }
        DialogManager.getInstance().showLoadingDialog(DepositMoneyActivity.this, "", false);
        DepositRequestMo loginRequestMo = new DepositRequestMo(ACCOUNT_ID, Double.parseDouble(moneyUsd), "");
        TradeService.deposit(loginRequestMo, new BizResultListener<DepositResultMo>() {
            @Override
            public void onPreExecute() {
            }

            @Override
            public void hitCache(boolean hit, DepositResultMo accountInfoMo) {

            }

            @Override
            public void onSuccess(DepositResultMo accountInfoMo) {
                DialogManager.getInstance().cancellLoadingDialog();
                if (!isFinishing() && !(commonDialog != null && !commonDialog.isShowing())) {
                    commonDialog = new CommonDialog(DepositMoneyActivity.this, "提交成功！", "银行反馈可能有延时，入金成功我们将第一时间通知您！", commitClickListener, 1, "", "确定");
                    commonDialog.show();
                }
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                ToastUtils.showShort(bizMessage);
                DialogManager.getInstance().cancellLoadingDialog();
            }
        });
    }

    private CommonDialog.CommitClickListener commitClickListener = new CommonDialog.CommitClickListener() {
        @Override
        public void click(int whichDialog,Object object) {
            switch (whichDialog) {
                case 1:
                    Intent intent = new Intent(DepositMoneyActivity.this, GoldMainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }

        }
    };

}

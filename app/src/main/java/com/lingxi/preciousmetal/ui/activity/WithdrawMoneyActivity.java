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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.GetAccountInfoRequestMo;
import com.lingxi.biz.domain.requestMo.WithdrawRequestMo;
import com.lingxi.biz.domain.responseMo.AccountInfoMo;
import com.lingxi.biz.domain.responseMo.DepositResultMo;
import com.lingxi.biz.service.TradeService;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;
import com.lingxi.preciousmetal.common.EventBusKeyDefine;
import com.lingxi.preciousmetal.domain.UserInfoBean;
import com.lingxi.preciousmetal.login.LoginHelper;
import com.lingxi.preciousmetal.ui.activity.kefu.KefuLoginActivity;
import com.lingxi.preciousmetal.ui.listener.KeyBoardListener;
import com.lingxi.preciousmetal.ui.widget.TopBarView;
import com.lingxi.preciousmetal.ui.widget.dialog.CommonDialog;
import com.lingxi.preciousmetal.ui.widget.dialog.DialogManager;
import com.lingxi.preciousmetal.util.CashierInputFilter;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.lingxi.preciousmetal.ui.activity.PropertyDetailActivity.ACCOUNT_ID;

/**
 * Created by zhangwei on 2018/4/24.
 */

public class WithdrawMoneyActivity extends TranslucentStatusBarActivity {
    @BindView(R.id.topbar_view)
    TopBarView topbarView;
    @BindView(R.id.layout_content)
    LinearLayout mInputParent;
    @BindView(R.id.layout_root)
    LinearLayout layoutRoot;
    @BindView(R.id.tv_user_account)
    TextView tvUserAccount;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_withdraw_money_usd)
    TextView tvWithdrawMoneyUsd;
    @BindView(R.id.tv_tips1)
    TextView tvTips1;
    @BindView(R.id.tv_bank_name)
    TextView tvBankName;
    @BindView(R.id.tv_bank_cd_num)
    TextView tvBankCdNum;
    @BindView(R.id.edit_withdraw_money_usd)
    EditText editWithdrawMoneyUsd;
    @BindView(R.id.tv_tips2)
    TextView tvTips2;
    @BindView(R.id.tv_deposit_money_rmb)
    TextView tvWithdrawMoneyRmb;
    @BindView(R.id.btn_submit)
    TextView btnSubmit;
    @BindView(R.id.tv_bank_branch_name)
    TextView tvBankBranchName;
    private KeyBoardListener mKeyBoardListener;
    UserInfoBean userInfoBean;
    private double exchangeRate = 6.4;
    private CommonDialog commonDialog;
    private AccountInfoMo accountInfoMo;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, WithdrawMoneyActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_money);
        userInfoBean = LoginHelper.getInstance().getLoginUserInfo();
        accountInfoMo = LoginHelper.getInstance().getAccountInfo();
        ButterKnife.bind(this);
        initTopBar();
        initListener();
        initView();
    }


    private void loadData() {
        DialogManager.getInstance().showLoadingDialog(WithdrawMoneyActivity.this, "", false);
        GetAccountInfoRequestMo loginRequestMo = new GetAccountInfoRequestMo(ACCOUNT_ID);
        TradeService.getAccountInfo(loginRequestMo, new BizResultListener<AccountInfoMo>() {
            @Override
            public void onPreExecute() {
            }

            @Override
            public void hitCache(boolean hit, AccountInfoMo accountInfoMo) {

            }

            @Override
            public void onSuccess(AccountInfoMo accountInfoMo) {
                updateAccountInfo(accountInfoMo);
                DialogManager.getInstance().cancellLoadingDialog();
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                ToastUtils.showShort(bizMessage);
                DialogManager.getInstance().cancellLoadingDialog();
            }
        });
    }

    private void updateAccountInfo(AccountInfoMo accountInfoMo) {
        LoginHelper.getInstance().updateAccountInfoMo(accountInfoMo);
        this.accountInfoMo = accountInfoMo;
        DecimalFormat nf = new DecimalFormat("#,###,##0.00");
        String str3 = nf.format(accountInfoMo.getMarginAvailable());
        tvWithdrawMoneyUsd.setText(str3);
//        DecimalFormat nf = new DecimalFormat("#,###,##0.00");
//        String str = nf.format(accountInfoMo.getEquity());
//        tvTotalProperty.setText(str);
//        String str1 = nf.format(accountInfoMo.getBalance());
//        tvBalance.setText(str1);
//        String str2 = nf.format(accountInfoMo.getMarginUsed());
//        tvDepositAlreadyUsed.setText(str2);
//        String str3 = nf.format(accountInfoMo.getMarginAvailable());
//        tvDepositAvailable.setText(str3);
//        String str4 = nf.format(accountInfoMo.getCredit());
//        tvDepositCredit.setText("（含信用额" + str4 + "）");
//        String str5 = nf.format(accountInfoMo.getFloatingProfit());
//        tvFloatingPl.setText(str5);
//        DecimalFormat nf1 = new DecimalFormat("##0.00");
//        String str6 = nf1.format(accountInfoMo.getMarginUtilisation());
//        tvDepositRate.setText(str6 + "%");
//        tvDate.setText(TimeUtils.getYMDToLong(System.currentTimeMillis() / 1000));
        EventBus.getDefault().post(new EventBusKeyDefine.EventBusMsgData<String>(EventBusKeyDefine.EVENTBUS_USER_INFO_UPDATE, ""));
    }


    private void initView() {
        if (userInfoBean != null) {
            tvUserAccount.setText(userInfoBean.id_card_hide);
            tvUsername.setText(userInfoBean.user_name);
            tvBankBranchName.setText(userInfoBean.belong_to_branch);
//            DecimalFormat nf = new DecimalFormat("#,###,##0.00");
//            String str = nf.format(userInfoBean.balance);
//            tvWithdrawMoneyUsd.setText(str+"（美元）");
            tvBankName.setText(userInfoBean.bank_name);
            tvBankCdNum.setText(userInfoBean.bank_account_hide);
//            editWithdrawMoneyUsd
        }
        editWithdrawMoneyUsd.setFilters(new InputFilter[]{new CashierInputFilter()});
//        tvUserAccount.setText(ACCOUNT_ID);
//        tvUserAccount.setText("");
        tvTips2.setText("汇率" + exchangeRate);
        if (accountInfoMo != null) {
            updateAccountInfo(accountInfoMo);
        } else {
            loadData();
        }
    }

    private void changePayRmbMoney(Double moneyUsd) {
        DecimalFormat nf1 = new DecimalFormat("##0.00");
        String str1 = nf1.format(moneyUsd * exchangeRate);
        tvWithdrawMoneyRmb.setText(str1);
    }

    private void initListener() {
        mKeyBoardListener = new KeyBoardListener(layoutRoot, new KeyBoardListener.OnSoftKeyboardListener() {
            @Override
            public void onSoftKeyboardShown(int keyBoardHeight) {
                mInputParent.setTranslationY(-keyBoardHeight);
            }

            @Override
            public void onSoftKeyboardHidden(int keyBoardHeight) {
                mInputParent.setTranslationY(0);
            }
        });

        editWithdrawMoneyUsd.addTextChangedListener(new TextWatcher() {
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

    private void initTopBar() {
        topbarView.setMode(TopBarView.MODE_BACK);
        topbarView.setTitle("取款申请");
        topbarView.setActionButton(R.drawable.ic_custom_service_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KefuLoginActivity.actionStart(WithdrawMoneyActivity.this);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mKeyBoardListener != null) {
            mKeyBoardListener.onDestroy();
        }
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        if (accountInfoMo == null) return;
        String moneyUsd = editWithdrawMoneyUsd.getText().toString().trim();
        if (TextUtils.isEmpty(moneyUsd)) {
            ToastUtils.showShort("请输入出金金额");
            return;
        }
        double moneyUsd1 = Double.parseDouble(moneyUsd);
        if (moneyUsd1 > accountInfoMo.getMarginAvailable()) {
            ToastUtils.showShort("出金金额超出可取金额");
            return;
        }
        DialogManager.getInstance().showLoadingDialog(WithdrawMoneyActivity.this, "", false);
        WithdrawRequestMo loginRequestMo = new WithdrawRequestMo(ACCOUNT_ID, Double.parseDouble(moneyUsd), "");
        TradeService.withdraw(loginRequestMo, new BizResultListener<DepositResultMo>() {
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
                    commonDialog = new CommonDialog(WithdrawMoneyActivity.this, "提交成功！", "取款申请成功，请等待系统出金", commitClickListener, 1, "", "确定");
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
                    Intent intent = new Intent(WithdrawMoneyActivity.this, GoldMainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }

        }
    };
}

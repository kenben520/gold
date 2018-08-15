package com.lingxi.preciousmetal.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.GetAccountInfoRequestMo;
import com.lingxi.biz.domain.responseMo.AccountInfoMo;
import com.lingxi.biz.service.TradeService;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;
import com.lingxi.preciousmetal.common.EventBusKeyDefine;
import com.lingxi.preciousmetal.login.LoginHelper;
import com.lingxi.preciousmetal.ui.activity.kefu.KefuLoginActivity;
import com.lingxi.preciousmetal.ui.activity.trade.PropertySheetsActivity;
import com.lingxi.preciousmetal.ui.activity.trade.TradeUserInfoActivity;
import com.lingxi.preciousmetal.ui.widget.TopBarView;
import com.lingxi.preciousmetal.ui.widget.dialog.DialogManager;
import com.lingxi.preciousmetal.util.utilCode.ObjectUtils;
import com.lingxi.preciousmetal.util.utilCode.TimeUtils;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.lingxi.preciousmetal.ui.activity.trade.PropertySheetsActivity.TYPE_1;

/**
 * Created by zhangwei on 2018/4/24.
 */

public class PropertyDetailActivity extends TranslucentStatusBarActivity {
    @BindView(R.id.topbar_view)
    TopBarView topbarView;
    @BindView(R.id.btn_withdraw)
    TextView btnWithdraw;
    @BindView(R.id.btn_deposit)
    TextView btnDeposit;
    @BindView(R.id.ProgressBar)
    android.widget.ProgressBar ProgressBar;
    @BindView(R.id.empty_img)
    ImageView emptyImg;
    @BindView(R.id.empty_tips)
    TextView emptyTips;
    @BindView(R.id.empty_layout)
    LinearLayout mEmptyLayout;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.content)
    LinearLayout content;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_total_property)
    TextView tvTotalProperty;
    @BindView(R.id.tv_deposit_already_used)
    TextView tvDepositAlreadyUsed;
    @BindView(R.id.tv_deposit_available)
    TextView tvDepositAvailable;
    @BindView(R.id.tv_deposit_credit)
    TextView tvDepositCredit;
    @BindView(R.id.tv_floating_pl)
    TextView tvFloatingPl;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.tv_deposit_rate)
    TextView tvDepositRate;
    @BindView(R.id.btn_watch_propery_sheet_list)
    TextView btnWatchProperySheetList;
    private AccountInfoMo accountInfoMo;
    public static final String ACCOUNT_ID = "2109732023";// TODO: 2018/6/8 目前测试 id固定

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, PropertyDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_detail);
        ButterKnife.bind(this);
        initTopBar();
        initView();
        loadData();
    }

    private void loadData() {
        DialogManager.getInstance().showLoadingDialog(PropertyDetailActivity.this, "", false);
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
                updateInfo(accountInfoMo);
                refreshLayout.finishRefresh();
                DialogManager.getInstance().cancellLoadingDialog();
                content.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                refreshLayout.finishRefresh();
                ToastUtils.showShort(bizMessage);
                DialogManager.getInstance().cancellLoadingDialog();
                if (ObjectUtils.isEmpty(accountInfoMo)) {
                    content.setVisibility(View.GONE);
                    mEmptyLayout.setVisibility(View.VISIBLE);
                    emptyImg.setImageResource(R.drawable.icon_no_network);
                    emptyTips.setText(getString(R.string.no_network_tips));
                } else {
                    mEmptyLayout.setVisibility(View.GONE);
                    content.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void updateInfo(AccountInfoMo accountInfoMo) {
        LoginHelper.getInstance().updateAccountInfoMo(accountInfoMo);
        EventBus.getDefault().post(new EventBusKeyDefine.EventBusMsgData<String>(EventBusKeyDefine.EVENTBUS_USER_INFO_UPDATE, ""));
        this.accountInfoMo = accountInfoMo;
        DecimalFormat nf = new DecimalFormat("#,###,##0.00");
        String str = nf.format(accountInfoMo.getEquity());
        tvTotalProperty.setText(str);
        String str1 = nf.format(accountInfoMo.getBalance());
        tvBalance.setText(str1);
        String str2 = nf.format(accountInfoMo.getMarginUsed());
        tvDepositAlreadyUsed.setText(str2);
        String str3 = nf.format(accountInfoMo.getMarginAvailable());
        tvDepositAvailable.setText(str3);
        String str4 = nf.format(accountInfoMo.getCredit());
        tvDepositCredit.setText("(含信用额" + str4 + ")");
        String str5 = nf.format(accountInfoMo.getFloatingProfit());
        tvFloatingPl.setText(str5);
        DecimalFormat nf1 = new DecimalFormat("##0.00");
        String str6 = nf1.format(accountInfoMo.getMarginUtilisation());
        tvDepositRate.setText(str6 + "%");
        tvDate.setText(TimeUtils.getYMDToLong(System.currentTimeMillis() / 1000));
    }

    private void initView() {
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadData();
            }
        });
    }

    private void initTopBar() {
        topbarView.setMode(TopBarView.MODE_BACK);
        topbarView.setTitle("资产详情");
        topbarView.setActionButton(R.drawable.ic_custom_service_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KefuLoginActivity.actionStart(PropertyDetailActivity.this);
            }
        });
    }

    @OnClick({R.id.btn_withdraw, R.id.btn_deposit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_withdraw:
                WithdrawMoneyActivity.actionStart(PropertyDetailActivity.this);
                break;
            case R.id.btn_deposit:
                DepositMoneyActivity.actionStart(PropertyDetailActivity.this);
                break;
        }
    }

    @OnClick(R.id.btn_watch_propery_sheet_list)
    public void onViewClicked() {
        PropertySheetsActivity.actionStart(this,TYPE_1);
    }
}

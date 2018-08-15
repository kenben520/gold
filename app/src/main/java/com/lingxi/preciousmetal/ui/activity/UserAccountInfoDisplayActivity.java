package com.lingxi.preciousmetal.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.common.IntentParam;
import com.lingxi.preciousmetal.domain.UserInfoBean;
import com.lingxi.preciousmetal.login.LoginHelper;
import com.lingxi.preciousmetal.presenter.pIml.UserInfoPresenter;
import com.lingxi.preciousmetal.ui.activity.kefu.KefuLoginActivity;
import com.lingxi.preciousmetal.ui.vInterface.UserInfoVInterface;
import com.lingxi.preciousmetal.ui.widget.TopBarView;
import com.lingxi.preciousmetal.ui.widget.dialog.DialogManager;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhangwei on 2018/4/19.
 */

public class UserAccountInfoDisplayActivity extends TranslucentStatusBarActivity implements UserInfoVInterface {
    @BindView(R.id.topbar_view)
    TopBarView mTopbarView;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_idcard_num)
    TextView tvIdcardNum;
    @BindView(R.id.tv_bank_name)
    TextView tvBankName;
    @BindView(R.id.tv_bank_cd_num)
    TextView tvBankCdNum;
    @BindView(R.id.tv_bank_branch_name)
    TextView tvBankBranchName;
    UserInfoBean mUserInfoBean;
    String mUserId;
    @BindView(R.id.layout_audit_success)
    LinearLayout layoutAuditSuccess;
    @BindView(R.id.tv_fail_content)
    TextView tvFailContent;
    @BindView(R.id.layout_audit_fail)
    LinearLayout layoutAuditFail;
    @BindView(R.id.layout_auditing)
    LinearLayout layoutAuditing;
    @BindView(R.id.btn_resubmit)
    TextView btnResubmit;
    private UserInfoPresenter userInfoPresenter;

    public static void actionStart(Context context, UserInfoBean userInfoBean) {
        Intent intent = new Intent(context, UserAccountInfoDisplayActivity.class);
        intent.putExtra(IntentParam.USER_INFO, userInfoBean);
        context.startActivity(intent);
    }

    public static void actionStart(Context context, String userId) {
        Intent intent = new Intent(context, UserAccountInfoDisplayActivity.class);
        intent.putExtra(IntentParam.USER_ID, userId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);
        ButterKnife.bind(this);
        initTopBar();
        userInfoPresenter = new UserInfoPresenter(this);
        mUserInfoBean = (UserInfoBean) getIntent().getSerializableExtra(IntentParam.USER_INFO);
        mUserId = getIntent().getStringExtra(IntentParam.USER_ID);
        initView();
    }

    private void initView() {
        if (mUserInfoBean != null) {
            refreshAuditSuccessView(mUserInfoBean);
        } else if (!TextUtils.isEmpty(mUserId)) {
            userInfoPresenter.getUserInfo(mUserId);
        }
    }

    public void refreshAuditSuccessView(UserInfoBean mUserInfoBean) {
        if (mUserInfoBean != null) {
            tvName.setText(mUserInfoBean.user_name);
            tvIdcardNum.setText(mUserInfoBean.id_card_hide);
            tvBankName.setText(mUserInfoBean.bank_name);
            tvBankCdNum.setText(mUserInfoBean.bank_account_hide);
            tvBankBranchName.setText(mUserInfoBean.belong_to_branch);
        }
    }

    private void initTopBar() {
        mTopbarView.setMode(TopBarView.MODE_BACK);
        mTopbarView.setTitle("个人资料");
        mTopbarView.setActionButton(R.drawable.ic_custom_service_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KefuLoginActivity.actionStart(UserAccountInfoDisplayActivity.this);
            }
        });
    }

    @Override
    public void auditSuccess(UserInfoBean userInfoBean) {
        layoutAuditSuccess.setVisibility(View.VISIBLE);
        layoutAuditFail.setVisibility(View.GONE);
        layoutAuditing.setVisibility(View.GONE);
        refreshAuditSuccessView(userInfoBean);
    }

    @Override
    public void auditFail(UserInfoBean userInfoBean) {
        layoutAuditSuccess.setVisibility(View.GONE);
        layoutAuditFail.setVisibility(View.VISIBLE);
        layoutAuditing.setVisibility(View.GONE);
        tvFailContent.setText(userInfoBean.fail_reason);
    }

    @Override
    public void auditing(UserInfoBean baseMo) {
        layoutAuditSuccess.setVisibility(View.GONE);
        layoutAuditFail.setVisibility(View.GONE);
        layoutAuditing.setVisibility(View.VISIBLE);
    }

    @Override
    public void getUserInfoFail(String errorMsg) {
        ToastUtils.showShort(errorMsg);
    }

    @Override
    public void showLoadingDialog(String message) {
        DialogManager.getInstance().showLoadingDialog(this, message, false);
    }

    @Override
    public void cancelLoadingDialog() {
        DialogManager.getInstance().cancellLoadingDialog();
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UserAccountInfoDisplayActivity.this, GoldMainActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_resubmit)
    public void onViewClicked() {
        UserInfoBean userInfo = LoginHelper.getInstance().getLoginUserInfo();
        if(userInfo!=null)
        {
            UploadUserInfoActivity.actionStart(UserAccountInfoDisplayActivity.this, userInfo);
        }
    }
}

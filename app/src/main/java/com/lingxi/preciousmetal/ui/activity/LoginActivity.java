package com.lingxi.preciousmetal.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lingxi.biz.domain.responseMo.LoginResultMo;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.BaseActivity;
import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;
import com.lingxi.preciousmetal.common.IntentParam;
import com.lingxi.preciousmetal.presenter.pIml.LoginPresenter;
import com.lingxi.preciousmetal.ui.activity.kefu.KefuLoginActivity;
import com.lingxi.preciousmetal.ui.vInterface.LoginVInterface;
import com.lingxi.preciousmetal.ui.widget.TopBarView;
import com.lingxi.preciousmetal.ui.widget.dialog.DialogManager;
import com.lingxi.preciousmetal.util.VerifyUtil;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

import static com.lingxi.preciousmetal.common.IntentParam.SKIP_FROM_GUIDE_PAGE;

/**
 * 登录页
 * Created by zhangwei on 2018/4/18.
 */

public class LoginActivity extends TranslucentStatusBarActivity implements LoginVInterface {
    @BindView(R.id.topbar_view)
    TopBarView mTopbarView;
    @BindView(R.id.tv_phone_num)
    TextView mTvPhoneNum;
    @BindView(R.id.edit_phone_num)
    EditText mEditPhoneNum;
    @BindView(R.id.btn_login)
    TextView mBtnLogin;
    @BindView(R.id.btn_register)
    TextView mBtnRegister;
    @BindView(R.id.btn_password_forget)
    TextView mBtnPasswordForget;
    @BindView(R.id.tv_password)
    TextView mTvPassword;
    @BindView(R.id.edit_password)
    EditText mEditPassword;
    private LoginPresenter mLoginPresenter;
    private String skipFrom;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void actionStart(Context context, String from) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(IntentParam.SKIP_FROM, from);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        skipFrom = getIntent().getStringExtra(IntentParam.SKIP_FROM);
        mLoginPresenter = new LoginPresenter(this);
        initTopBar();
        initView();
    }

    private void initView() {

    }

    private void initTopBar() {
        mTopbarView.setMode(TopBarView.MODE_BACK);
        mTopbarView.setTitle("帐号登录");
        mTopbarView.setActionButton(R.drawable.ic_custom_service_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KefuLoginActivity.actionStart(LoginActivity.this);
            }
        });
        mTopbarView.setBackButton(R.drawable.btn_back, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SKIP_FROM_GUIDE_PAGE.equals(skipFrom)) {
                    Intent intent = new Intent(LoginActivity.this, GoldMainActivity.class);
                    startActivity(intent);
                } else {
                }
                hideSoftInput();
                ((BaseActivity) view.getContext()).finish();
            }
        });

    }

    @OnClick({R.id.btn_login, R.id.btn_register, R.id.btn_password_forget})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                doLogin();
                break;
            case R.id.btn_register:
                RegisterActivity.actionStart(LoginActivity.this);
                break;
            case R.id.btn_password_forget:
                ForgetPasswordActivity.actionStart(LoginActivity.this);
                break;
        }
    }

    private void doLogin() {
        String phone = mEditPhoneNum.getText().toString().trim();
        String password = mEditPassword.getText().toString().trim();
        if (!VerifyUtil.isValidPhoneNumber(phone) || TextUtils.isEmpty(password)) {
            ToastUtils.showShort("请输入完整的账号密码");
            return;
        }
        mLoginPresenter.login(phone, password);
    }

    @Override
    public void loginSuccess(LoginResultMo accountVo) {
        if (SKIP_FROM_GUIDE_PAGE.equals(skipFrom)) {
            Intent intent = new Intent(LoginActivity.this, GoldMainActivity.class);
            startActivity(intent);
        } else {
        }
        JPushInterface.setAlias(this,1,accountVo.user_id);
        finish();
    }

    @Override
    public void loginFail(String errorMsg) {
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


}

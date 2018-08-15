package com.lingxi.preciousmetal.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.MessageCodeRequestMo;
import com.lingxi.biz.domain.responseMo.LoginResultMo;
import com.lingxi.biz.domain.responseMo.MessageCodeBean;
import com.lingxi.biz.service.CommonService;
import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.presenter.pIml.ForgetPasswordPresenter;
import com.lingxi.preciousmetal.ui.activity.kefu.KefuLoginActivity;
import com.lingxi.preciousmetal.ui.vInterface.ForgetPasswordVInterface;
import com.lingxi.preciousmetal.ui.widget.TopBarView;
import com.lingxi.preciousmetal.ui.widget.dialog.DialogManager;
import com.lingxi.preciousmetal.util.VerifyUtil;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhangwei on 2018/4/19.
 */

public class ForgetPasswordActivity extends TranslucentStatusBarActivity implements ForgetPasswordVInterface {
    @BindView(R.id.topbar_view)
    TopBarView topbarView;
    @BindView(R.id.tv_phone_num)
    TextView tvPhoneNum;
    @BindView(R.id.edit_phone_num)
    EditText mEditPhoneNum;
    @BindView(R.id.edit_verification_code)
    EditText mEditVerificationCode;
    @BindView(R.id.btn_obtain_verfication_code)
    TextView mBtnObtainVerficationCode;
    @BindView(R.id.tv_idcard_num)
    TextView tvIdcardNum;
    @BindView(R.id.edit_idcard_num)
    EditText mEditIdcardNum;
    @BindView(R.id.btn_ensure)
    TextView btnEnsure;
    @BindView(R.id.tv_new_password)
    TextView mTvNewPassword;
    @BindView(R.id.edit_new_password)
    EditText mEditNewPassword;
    private ForgetPasswordPresenter mForgetPasswordPresenter;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ForgetPasswordActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_forget);
        ButterKnife.bind(this);
        mForgetPasswordPresenter = new ForgetPasswordPresenter(this);
        initTopBar();
        initView();
    }

    private void initView() {

    }

    private void initTopBar() {
        topbarView.setMode(TopBarView.MODE_BACK);
        topbarView.setTitle("重置密码");
        topbarView.setActionButton(R.drawable.ic_custom_service_white , new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KefuLoginActivity.actionStart(ForgetPasswordActivity.this);
            }
        });
    }

    private boolean flag = true;

    @OnClick(R.id.btn_obtain_verfication_code)
    public void onObtainVerificationCodeClick() {
        if (flag == true) {
            String phone = mEditPhoneNum.getText().toString().trim();
            if (TextUtils.isEmpty(phone)) {
                ToastUtils.showShort("请输入您的手机号码");
                return;
            }
            if (!VerifyUtil.isValidPhoneNumber(phone)) {
                ToastUtils.showShort("请输入正确的手机号");
                return;
            }
            flag = false;
            new CountDownTimer(60000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mBtnObtainVerficationCode.setText(millisUntilFinished / 1000 + "");
                }

                @Override
                public void onFinish() {
                    mBtnObtainVerficationCode.setText("点击获取验证码");
                    flag = true;
                }
            }.start();
            MessageCodeRequestMo requestMo = new MessageCodeRequestMo(phone,"forgetPassword");
            CommonService.getMessageCode(requestMo, new BizResultListener<MessageCodeBean>() {
                @Override
                public void onPreExecute() {

                }

                @Override
                public void hitCache(boolean hit, MessageCodeBean knowledgeResultMo) {
                }

                @Override
                public void onSuccess(MessageCodeBean loginResultMo) {
                    ToastUtils.showShort("验证码发送成功");
                    flag = true;
                }

                @Override
                public void onFail(int resultCode, int bizCode, String bizMessage) {
                    ToastUtils.showShort(bizMessage);
                }
            });
        }
    }

    private void doForget() {
        String phone = mEditPhoneNum.getText().toString().trim();
        String verifyCode = mEditVerificationCode.getText().toString().trim();
        String idCardNum = mEditIdcardNum.getText().toString().trim();
        String newPassword = mEditNewPassword.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showShort("请输入您的手机号码");
            return;
        }
        if (!VerifyUtil.isValidPhoneNumber(phone)) {
            ToastUtils.showShort("请输入正确的手机号");
            return;
        }

        if (!VerifyUtil.isValidVerifyCode(verifyCode)) {
            ToastUtils.showShort("请输入您的验证码");
            return;
        }

        if (TextUtils.isEmpty(idCardNum)) {
            ToastUtils.showShort("请输入您的身份证号");
            return;
        }
        if (!VerifyUtil.isValidIdCard(idCardNum)) {
            ToastUtils.showShort("请输入正确的身份证号");
            return;
        }

        if (!VerifyUtil.isValidNamePassword(newPassword)) {
            ToastUtils.showShort("请输入正确的密码格式");
            return;
        }
        mForgetPasswordPresenter.forgetPassword(phone, verifyCode, idCardNum, newPassword);
    }

    @OnClick(R.id.btn_ensure)
    public void onViewClicked() {
        doForget();
    }

    @Override
    public void forgetSuccess(LoginResultMo accountVo) {
        Intent intent = new Intent(ForgetPasswordActivity.this, GoldMainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void forgetFail(String errorMsg) {
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

package com.lingxi.preciousmetal.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.GetAgreementRequestMo;
import com.lingxi.biz.domain.requestMo.MessageCodeRequestMo;
import com.lingxi.biz.domain.responseMo.AgreementMo;
import com.lingxi.biz.domain.responseMo.LoginResultMo;
import com.lingxi.biz.domain.responseMo.MessageCodeBean;
import com.lingxi.biz.service.CommonService;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.BaseActivity;
import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;
import com.lingxi.preciousmetal.common.IntentParam;
import com.lingxi.preciousmetal.presenter.pIml.RegisterPresenter;
import com.lingxi.preciousmetal.ui.activity.kefu.KefuLoginActivity;
import com.lingxi.preciousmetal.ui.vInterface.RegisterVInterface;
import com.lingxi.preciousmetal.ui.widget.TopBarView;
import com.lingxi.preciousmetal.ui.widget.dialog.CustomerAgreementDialog;
import com.lingxi.preciousmetal.ui.widget.dialog.DialogManager;
import com.lingxi.preciousmetal.util.VerifyUtil;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.lingxi.preciousmetal.common.IntentParam.SKIP_FROM_GUIDE_PAGE;

/**
 * Created by zhangwei on 2018/4/19.
 */

public class RegisterActivity extends TranslucentStatusBarActivity implements RegisterVInterface {
    @BindView(R.id.topbar_view)
    TopBarView mTopbarView;
    @BindView(R.id.tv_phone_num)
    TextView mTvPhoneNum;
    @BindView(R.id.edit_phone_num)
    EditText mEditPhoneNum;
    @BindView(R.id.edit_verification_code)
    EditText mEditVerificationCode;
    @BindView(R.id.btn_obtain_verfication_code)
    TextView mBtnObtainVerficationCode;
    @BindView(R.id.tv_idcard_num)
    TextView mTvIdcardNum;
    @BindView(R.id.edit_idcard_num)
    EditText mEditIdcardNum;
    @BindView(R.id.tv_username)
    TextView mTvUsername;
    @BindView(R.id.edit_username)
    EditText mEditUsername;
    @BindView(R.id.cb_agree)
    ImageView mCbAgree;
    @BindView(R.id.tv_agree)
    LinearLayout mTvAgree;
    @BindView(R.id.btn_register)
    TextView mBtnRegister;
    @BindView(R.id.tv_tips1)
    TextView tvTips1;
    @BindView(R.id.tv_tips2)
    TextView tvTips2;
    @BindView(R.id.tv_tips3)
    TextView tvTips3;
    private RegisterPresenter mRegisterPresenter;
    private String skipFrom;
    private CustomerAgreementDialog agreementDialog;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
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
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mRegisterPresenter = new RegisterPresenter(this);
        skipFrom = getIntent().getStringExtra(IntentParam.SKIP_FROM);
        initTopBar();
        initView();
    }

    private void initView() {
//        mEditUsername.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                String editable = mEditUsername.getText().toString();
//                String str = VerifyUtil.stringFilter1(editable.toString());
//                if (!editable.equals(str)) {
//                    mEditUsername.setText(str);
//                    mEditUsername.setSelection(str.length());
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        tvTips1.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
//        tvTips1.getPaint().setAntiAlias(true);
//        tvTips2.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
//        tvTips2.getPaint().setAntiAlias(true);
//        tvTips3.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
//        tvTips3.getPaint().setAntiAlias(true);
    }

    private void initTopBar() {
        mTopbarView.setMode(TopBarView.MODE_BACK);
        mTopbarView.setTitle("开立真实账户");
        mTopbarView.setActionButton(R.drawable.ic_custom_service_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KefuLoginActivity.actionStart(RegisterActivity.this);
            }
        });
        mTopbarView.setBackButton(R.drawable.btn_back, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SKIP_FROM_GUIDE_PAGE.equals(skipFrom)) {
                    Intent intent = new Intent(RegisterActivity.this, GoldMainActivity.class);
                    startActivity(intent);
                } else {
                }
                hideSoftInput();
                ((BaseActivity) view.getContext()).finish();
            }
        });
    }

    @Override
    public void registerSuccess(LoginResultMo accountVo) {
        Intent intent = new Intent(RegisterActivity.this, GoldMainActivity.class);
        startActivity(intent);
    }

    @Override
    public void registerFail(String errorMsg) {
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


    @OnClick({R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                doRegister();
                break;
        }
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
            flag = true;
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
            MessageCodeRequestMo requestMo = new MessageCodeRequestMo(phone, "userRegister");
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

    private void doRegister() {
        String phone = mEditPhoneNum.getText().toString().trim();
        String verifyCode = mEditVerificationCode.getText().toString().trim();
        String idCardNum = mEditIdcardNum.getText().toString().trim();
        String userName = mEditUsername.getText().toString().trim();
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

        if (!VerifyUtil.isValidName(userName)) {
            ToastUtils.showShort("请输入您的姓名");
            return;
        }
        if (!mCbAgree.isEnabled()) {
            ToastUtils.showShort("请勾选协议书");
            return;
        }
        mRegisterPresenter.register(phone, verifyCode, idCardNum, userName);
    }

    @OnClick(R.id.tv_agree)
    public void onViewClicked() {
        boolean cbAgree = mCbAgree.isEnabled();
        mCbAgree.setEnabled(!cbAgree);
        mBtnRegister.setEnabled(!cbAgree);
    }

    private void getAgreement(String article_id) {
        GetAgreementRequestMo getAgreementRequestMo = new GetAgreementRequestMo(article_id);
        CommonService.getAgreement(getAgreementRequestMo, new BizResultListener<AgreementMo>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, AgreementMo agreementMo) {

            }

            @Override
            public void onSuccess(AgreementMo agreementMo) {
                if(!isFinishing())
                {
                    agreementDialog = new CustomerAgreementDialog(RegisterActivity.this, agreementMo.getTitle(),agreementMo.getContent());
                    agreementDialog.show();
                }
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {

            }
        });
    }
    @OnClick({R.id.tv_tips1, R.id.tv_tips2, R.id.tv_tips3})
    public void onTipsViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_tips1:
                getAgreement("64");
                break;
            case R.id.tv_tips2:
                getAgreement("4366");
                break;
            case R.id.tv_tips3:
                getAgreement("66");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (agreementDialog != null) {
            if (agreementDialog.isShowing()) agreementDialog.dismiss();
        }
        super.onDestroy();
    }

}

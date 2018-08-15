package com.lingxi.preciousmetal.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lingxi.biz.domain.responseMo.BaseMo;
import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;
import com.lingxi.common.util.MPermissionUtils;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.common.IntentParam;
import com.lingxi.preciousmetal.domain.BankBean;
import com.lingxi.preciousmetal.domain.UserInfoBean;
import com.lingxi.preciousmetal.presenter.pIml.UpUserInfoPresenter;
import com.lingxi.preciousmetal.ui.activity.kefu.KefuLoginActivity;
import com.lingxi.preciousmetal.ui.vInterface.UpUserInfoVInterface;
import com.lingxi.preciousmetal.ui.widget.TopBarView;
import com.lingxi.preciousmetal.ui.widget.dialog.CommonDialog;
import com.lingxi.preciousmetal.ui.widget.dialog.DialogManager;
import com.lingxi.preciousmetal.util.AndroidUtils;
import com.lingxi.preciousmetal.util.BankInfoUtil;
import com.lingxi.preciousmetal.util.VerifyUtil;
import com.lingxi.preciousmetal.util.glideutils.GlideUtils;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by zhangwei on 2018/4/19.
 */

public class UploadBankCardInfoActivity extends TranslucentStatusBarActivity implements UpUserInfoVInterface {
    @BindView(R.id.topbar_view)
    TopBarView mTopbarView;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.edit_bank_name)
    TextView editBankName;
    @BindView(R.id.iv_arrow1)
    ImageView ivArrow1;
    @BindView(R.id.tv_bankcard_num)
    TextView tvBankcardNum;
    @BindView(R.id.edit_bankcard_num)
    EditText editBankcardNum;
    @BindView(R.id.tv_bank_branch_name)
    TextView tvBankBranchName;
    @BindView(R.id.edit_bank_branch_name)
    EditText editBankBranchName;
    @BindView(R.id.tv_agree)
    TextView tvAgree;
    @BindView(R.id.iv_card_front_side)
    ImageView ivCardFrontSide;
    @BindView(R.id.ic_add)
    ImageView icAdd;
    @BindView(R.id.tv_add_tips)
    TextView tvAddTips;
    @BindView(R.id.btn_next)
    TextView btnNext;
    @BindView(R.id.layout_add_tips)
    LinearLayout layoutAddTips;
    @BindView(R.id.layout_select_bank)
    RelativeLayout layoutSelectBank;

    private UpUserInfoPresenter upUserInfoPresenter;
    UserInfoBean mUserInfoBean;
    private String mBankCardPicLocalPath;
    private String mBankCardPicUrl;
    private CommonDialog commonDialog;

    public static void actionStart(Context context, UserInfoBean userInfoBean) {
        Intent intent = new Intent(context, UploadBankCardInfoActivity.class);
        intent.putExtra(IntentParam.USER_INFO, userInfoBean);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bankcard_info);
        ButterKnife.bind(this);
        upUserInfoPresenter = new UpUserInfoPresenter(this);
        mUserInfoBean = (UserInfoBean) getIntent().getSerializableExtra(IntentParam.USER_INFO);
        initTopBar();
        initView();
    }


    private void initView() {
        if (mUserInfoBean != null) {
            tvUsername.setText(mUserInfoBean.user_name);
        }
        editBankcardNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                onCheckCard();
            }
        });
    }

    private BankInfoUtil mInfoUtil;

    private void onCheckCard() {
        String cardNum = editBankcardNum.getText().toString();
        if (!TextUtils.isEmpty(cardNum) && VerifyUtil.checkBankCard(cardNum)) {
            mInfoUtil = new BankInfoUtil(cardNum);
            editBankName.setText(mInfoUtil.getBankName());
        } else {
        }
    }

    private void initTopBar() {
        mTopbarView.setMode(TopBarView.MODE_BACK);
        mTopbarView.setTitle("银行信息");
        mTopbarView.setActionButton(R.drawable.ic_custom_service_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KefuLoginActivity.actionStart(UploadBankCardInfoActivity.this);
            }
        });
    }

    @Override
    public void uploadFrontIDPicSuccess(String url) {

    }

    @Override
    public void uploadFrontIDPicFail(String errorMsg) {

    }

    @Override
    public void uploadBackIDPicSuccess(String url) {

    }

    @Override
    public void uploadBackIDPicFail(String errorMsg) {

    }

    @Override
    public void uploadBankPicSuccess(String url) {
        GlideUtils.loadImageViewCrop(UploadBankCardInfoActivity.this, mBankCardPicLocalPath, ivCardFrontSide);
        layoutAddTips.setVisibility(View.GONE);
        mBankCardPicUrl = url;
    }

    @Override
    public void uploadBankPicFail(String errorMsg) {
        ToastUtils.showShort(errorMsg);
    }

    @Override
    public void submitUserInfoSuccess(BaseMo baseMo) {
        if (!isFinishing() && !(commonDialog != null && !commonDialog.isShowing())) {
            upUserInfoPresenter.getUserInfo(mUserInfoBean.user_id);
            commonDialog = new CommonDialog(UploadBankCardInfoActivity.this, "提交成功！", "请等待工作人员审核，审核结果我们将第一时间使用短信及邮件通知您！", commitClickListener, 1, "", "确定");
            commonDialog.show();
        }
    }

    private CommonDialog.CommitClickListener commitClickListener = new CommonDialog.CommitClickListener() {
        @Override
        public void click(int whichDialog,Object object) {
            switch (whichDialog) {
                case 1:
                    Intent intent = new Intent(UploadBankCardInfoActivity.this, GoldMainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }

        }
    };

    @Override
    public void onBackPressed() {
        upUserInfoPresenter.back();
    }

    @Override
    public void submitUserInfoFail(String errorMsg) {
        ToastUtils.showShort(errorMsg);
    }

    @Override
    public void goBackToMain() {
        Intent intent = new Intent(UploadBankCardInfoActivity.this, GoldMainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void justBack() {
        super.onBackPressed();
    }

    private static final int REQUEST_CODE_UPLOAD_BANK_CARD_FRONT_SIDE = 1;
    protected static final int REQUEST_CODE_SELECT_BANK = 2;

    @OnClick({R.id.iv_card_front_side, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_card_front_side:
                selectImage(REQUEST_CODE_UPLOAD_BANK_CARD_FRONT_SIDE);
                break;
            case R.id.btn_next:
                doSubmitUserInfo();
                break;
        }
    }

    // TODO: 2018/4/19 冗余代码
    private void selectImage(final int requestCode) {
        MPermissionUtils.requestPermissionsResult(this, 0, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                new MPermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        Intent intent = new Intent(UploadBankCardInfoActivity.this, MultiImageSelectorActivity.class);
                        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
                        // max select image amount
                        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
                        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity
                                .MODE_SINGLE);
                        startActivityForResult(intent, requestCode);
                    }

                    @Override
                    public void onPermissionDenied() {
                        MPermissionUtils.showTipsDialog(UploadBankCardInfoActivity.this);
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_UPLOAD_BANK_CARD_FRONT_SIDE:
                if (resultCode == Activity.RESULT_OK) {
                    //裁剪后返回的数据
                    List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    String cropPicPath = path.get(0);
                    if (cropPicPath != null) {
                        updateCardViewInfo(cropPicPath);
                    } else {
                        AndroidUtils.getInstance().showToast(UploadBankCardInfoActivity.this, "请重新选择图片");
                    }
                }
                break;
            case REQUEST_CODE_SELECT_BANK:
                if (REQUEST_CODE_SELECT_BANK == requestCode && RESULT_OK == resultCode) {
                    BankBean bankBean = (BankBean) data.getSerializableExtra(IntentParam.BANK);
                    editBankName.setText(bankBean.bank);
                }
                break;
        }
    }

    private void updateCardViewInfo(String path) {
        mBankCardPicLocalPath = path;
        upUserInfoPresenter.uploadBankPic(mBankCardPicLocalPath);
    }


    private void doSubmitUserInfo() {
        String bankName = editBankName.getText().toString().trim();
        String bankCardNum = editBankcardNum.getText().toString().trim();
        String bankBranchName = editBankBranchName.getText().toString().trim();

        if (TextUtils.isEmpty(bankName)) {
            ToastUtils.showShort("请您选择开户银行");
            return;
        }

        if (!VerifyUtil.checkBankCard(bankCardNum)) {
            ToastUtils.showShort("请输入正确的银行卡号");
            return;
        }

        if (TextUtils.isEmpty(bankBranchName)) {
            ToastUtils.showShort("请输入支行名称");
            return;
        }

        if (TextUtils.isEmpty(mBankCardPicUrl)) {
            ToastUtils.showShort("请上传银行卡正面");
            return;
        }
        mUserInfoBean.bank_name = bankName;
        mUserInfoBean.bank_account = bankCardNum;
        mUserInfoBean.belong_to_branch = bankBranchName;
        mUserInfoBean.bank_card_front = mBankCardPicUrl;
        upUserInfoPresenter.submitUserInfo(mUserInfoBean);
    }

    @OnClick(R.id.layout_select_bank)
    public void onViewClicked() {
        Intent intent = new Intent(UploadBankCardInfoActivity.this, SelectBankActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SELECT_BANK);
    }

    @Override
    protected void onDestroy() {
        if (commonDialog != null) {
            if (commonDialog.isShowing()) commonDialog.dismiss();
        }
        super.onDestroy();
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

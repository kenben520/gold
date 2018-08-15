package com.lingxi.preciousmetal.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lingxi.biz.domain.responseMo.BaseMo;
import com.lingxi.common.util.StringUtil;
import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;
import com.lingxi.common.util.MPermissionUtils;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.common.IntentParam;
import com.lingxi.preciousmetal.domain.UserInfoBean;
import com.lingxi.preciousmetal.presenter.pIml.UpUserInfoPresenter;
import com.lingxi.preciousmetal.ui.activity.kefu.KefuLoginActivity;
import com.lingxi.preciousmetal.ui.vInterface.UpUserInfoVInterface;
import com.lingxi.preciousmetal.ui.widget.TopBarView;
import com.lingxi.preciousmetal.ui.widget.dialog.DialogManager;
import com.lingxi.preciousmetal.util.AndroidUtils;
import com.lingxi.preciousmetal.util.CropUtils;
import com.lingxi.preciousmetal.util.VerifyUtil;
import com.lingxi.preciousmetal.util.glideutils.GlideUtils;
import com.lingxi.preciousmetal.util.utilCode.StringUtils;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;


/**
 * Created by zhangwei on 2018/4/12.
 */
public class UploadUserInfoActivity extends TranslucentStatusBarActivity implements UpUserInfoVInterface {
    @BindView(R.id.topbar_view)
    TopBarView mTopbarView;
    @BindView(R.id.layout_id_card_front_side)
    LinearLayout layoutIdCardFrontSide;
    @BindView(R.id.layout_id_card_back_side)
    LinearLayout layoutIdCardBackSide;
    @BindView(R.id.iv_id_card_front_side)
    ImageView mIvIdCardFrontSide;
    @BindView(R.id.iv_id_card_back_side)
    ImageView mIvIdCardBackSide;
    @BindView(R.id.btn_next)
    TextView mBtnNext;
    @BindView(R.id.edit_username)
    EditText mEditUsername;
    @BindView(R.id.edit_idcard_num)
    EditText mEditIdcardNum;
    private UpUserInfoPresenter upUserInfoPresenter;
    private String mCardFrontLocalPath;
    private String mCardBackLocalPath;
    private String mCardFrontUrl, mCardBackUrl;
    UserInfoBean mUserInfoBean;

    public static void actionStart(Context context, UserInfoBean userInfoBean) {
        Intent intent = new Intent(context, UploadUserInfoActivity.class);
        intent.putExtra(IntentParam.USER_INFO, userInfoBean);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_id_card_info);
        ButterKnife.bind(this);
        upUserInfoPresenter = new UpUserInfoPresenter(this);
        mUserInfoBean = (UserInfoBean) getIntent().getSerializableExtra(IntentParam.USER_INFO);
        initTopBar();
        initView();
    }

    private void initView() {
        if (mUserInfoBean != null) {
            mEditUsername.setText(mUserInfoBean.user_name);
            mEditIdcardNum.setText(mUserInfoBean.id_card);
            if (!StringUtil.isEmpty(mUserInfoBean.user_name)) {
                mEditUsername.setSelection(mUserInfoBean.user_name.length());
            }

        }
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
    }


    @Override
    public void uploadFrontIDPicSuccess(String url) {
        GlideUtils.loadImageViewCrop(UploadUserInfoActivity.this, mCardFrontLocalPath, mIvIdCardFrontSide);
        layoutIdCardFrontSide.setVisibility(View.GONE);
        mCardFrontUrl = url;
    }

    @Override
    public void uploadFrontIDPicFail(String errorMsg) {
        ToastUtils.showShort(errorMsg);
    }

    @Override
    public void uploadBackIDPicSuccess(String url) {
        GlideUtils.loadImageViewCrop(UploadUserInfoActivity.this, mCardBackLocalPath, mIvIdCardBackSide);
        layoutIdCardBackSide.setVisibility(View.GONE);
        mCardBackUrl = url;
    }

    @Override
    public void uploadBackIDPicFail(String errorMsg) {
        ToastUtils.showShort(errorMsg);
    }

    @Override
    public void uploadBankPicSuccess(String url) {

    }

    @Override
    public void uploadBankPicFail(String errorMsg) {

    }

    @Override
    public void submitUserInfoSuccess(BaseMo baseMo) {

    }


    @Override
    public void submitUserInfoFail(String errorMsg) {

    }

    @Override
    public void goBackToMain() {
    }

    @Override
    public void justBack() {

    }

    @Override
    public void showLoadingDialog(String message) {
        DialogManager.getInstance().showLoadingDialog(this, message, false);
    }

    @Override
    public void cancelLoadingDialog() {
        DialogManager.getInstance().cancellLoadingDialog();
    }


    private static final int REQUEST_CODE_UPLOAD_FRONT_SIDE = 1;
    private static final int REQUEST_CODE_UPLOAD_BACK_SIDE = 2;

    @OnClick(R.id.iv_id_card_front_side)
    public void onClickCardFrontSide(View v) {
        selectImage(REQUEST_CODE_UPLOAD_FRONT_SIDE);
    }

    @OnClick(R.id.iv_id_card_back_side)
    public void onClickCardBackSide(View v) {
        selectImage(REQUEST_CODE_UPLOAD_BACK_SIDE);
    }

    private void selectImage(final int requestCode) {
        MPermissionUtils.requestPermissionsResult(this, 0, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                new MPermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        Intent intent = new Intent(UploadUserInfoActivity.this, MultiImageSelectorActivity.class);
                        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
                        // max select image amount
                        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
                        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity
                                .MODE_SINGLE);
                        startActivityForResult(intent, requestCode);
                    }

                    @Override
                    public void onPermissionDenied() {
                        MPermissionUtils.showTipsDialog(UploadUserInfoActivity.this);
                    }
                });
    }
    private boolean needCrop = true;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_UPLOAD_FRONT_SIDE:
            case REQUEST_CODE_UPLOAD_BACK_SIDE:
                if (resultCode == Activity.RESULT_OK) {
                    //裁剪后返回的数据
                    List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    String cropPicPath = path.get(0);
                    if (cropPicPath != null) {
                        if(needCrop)
                        {
                            String  cropPath= CropUtils.compress(cropPicPath);
                            if(!StringUtils.isEmpty(cropPath))
                            {
                                updateCardViewInfo(requestCode, cropPath);
                            }
                        }else{
                            updateCardViewInfo(requestCode, cropPicPath);
                        }
                    } else {
                        AndroidUtils.getInstance().showToast(UploadUserInfoActivity.this, "请重新选择图片");
                    }
                }
                break;
        }
    }

    private void updateCardViewInfo(int requestCode, String path) {
        switch (requestCode) {
            case REQUEST_CODE_UPLOAD_FRONT_SIDE:
                mCardFrontLocalPath = path;
//                GlideUtils.loadImageViewCrop(UploadUserInfoActivity.this, mCardFrontLocalPath, mIvIdCardFrontSide);
//                layoutIdCardFrontSide.setVisibility(View.GONE);
                upUserInfoPresenter.uploadFrontIdPic(mCardFrontLocalPath);
                break;
            case REQUEST_CODE_UPLOAD_BACK_SIDE:
                mCardBackLocalPath = path;
//                GlideUtils.loadImageViewCrop(UploadUserInfoActivity.this, mCardBackLocalPath, mIvIdCardBackSide);
//                layoutIdCardBackSide.setVisibility(View.GONE);
                upUserInfoPresenter.uploadBackIdPic(mCardBackLocalPath);
                break;
        }
    }

    private void initTopBar() {
        mTopbarView.setMode(TopBarView.MODE_BACK);
        mTopbarView.setTitle("身份资料");
        mTopbarView.setActionButton(R.drawable.ic_custom_service_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KefuLoginActivity.actionStart(UploadUserInfoActivity.this);
            }
        });
    }

    @OnClick(R.id.btn_next)
    public void onViewClicked() {
        String idCardNum = mEditIdcardNum.getText().toString().trim();
        String userName = mEditUsername.getText().toString().trim();

//        userName="张伟";
//        idCardNum="652800197805071235";

        if (!VerifyUtil.isValidName(userName)) {
            ToastUtils.showShort("请输入您的姓名");
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

        if (TextUtils.isEmpty(mCardFrontUrl)) {
            ToastUtils.showShort("上传身份证人像页");
            return;
        }
        if (TextUtils.isEmpty(mCardBackUrl)) {
            ToastUtils.showShort("上传身份证国徽页");
            return;
        }
        mUserInfoBean.user_name = userName;
        mUserInfoBean.id_card = idCardNum;
        mUserInfoBean.id_card_front = mCardFrontUrl;
        mUserInfoBean.id_card_behind = mCardBackUrl;
        UploadBankCardInfoActivity.actionStart(UploadUserInfoActivity.this, mUserInfoBean);
    }


}

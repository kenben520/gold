package com.lingxi.preciousmetal.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.FeedBackRequestMo;
import com.lingxi.biz.domain.requestMo.GetAdRequestMo;
import com.lingxi.biz.domain.responseMo.AdMo;
import com.lingxi.biz.domain.responseMo.BaseMo;
import com.lingxi.biz.domain.responseMo.FeedBackMo;
import com.lingxi.biz.service.CommonService;
import com.lingxi.common.base.BaseApplication;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;
import com.lingxi.preciousmetal.domain.UserInfoBean;
import com.lingxi.preciousmetal.login.LoginHelper;
import com.lingxi.preciousmetal.presenter.pIml.ShowTradePresenter;
import com.lingxi.preciousmetal.ui.vInterface.ShowTradeVInterface;
import com.lingxi.preciousmetal.ui.widget.TopBarView;
import com.lingxi.preciousmetal.ui.widget.dialog.CommonDialog;
import com.lingxi.preciousmetal.ui.widget.dialog.DialogManager;
import com.lingxi.preciousmetal.util.AndroidUtils;
import com.lingxi.preciousmetal.util.CropUtils;
import com.lingxi.preciousmetal.util.VerifyUtil;
import com.lingxi.preciousmetal.util.glideutils.GlideUtils;
import com.lingxi.preciousmetal.util.utilCode.ObjectUtils;
import com.lingxi.preciousmetal.util.utilCode.SPUtils;
import com.lingxi.preciousmetal.util.utilCode.StringUtils;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

import static com.lingxi.preciousmetal.common.CommonParam.ADVERTISEMENT_SPLASH;

/**
 * Created by zhangwei on 2018/4/17.
 */

public class FeedBackActivity extends TranslucentStatusBarActivity implements ShowTradeVInterface,TextWatcher {
    @BindView(R.id.topbar_view)
    TopBarView mTopbarView;
    @BindView(R.id.tv_len_count)
    TextView mTvLenCount;
    @BindView(R.id.et_inf)
    EditText etInf;
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.dotted_line)
    View dottedLine;
    @BindView(R.id.tv_phone_num)
    TextView tvPhoneNum;
    @BindView(R.id.edit_phone_num)
    EditText editPhoneNum;
    @BindView(R.id.btn_next)
    TextView btnSubmit;
    private int mMaxLen = 200;
    private String mPicLocalPath;
    private String mPicUrl;
    private CommonDialog commonDialog;
    private ShowTradePresenter showTradePresenter;
    private boolean needCrop = true;
    UserInfoBean userInfoBean;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, FeedBackActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        userInfoBean = LoginHelper.getInstance().getLoginUserInfo();
        ButterKnife.bind(this);
        showTradePresenter = new ShowTradePresenter(this);
        initView();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (null != s.toString()) {
            mTvLenCount.setText(s.toString().length() + "/" + mMaxLen);
            if (s.toString().length() >= mMaxLen) {
                mTvLenCount.setTextColor(getResources().getColor(R.color.orangeyRedTwo));
            } else {
                mTvLenCount.setTextColor(getResources().getColor(R.color.gray13));
            }
            if (s.toString().length() <= 0) {
                btnSubmit.setEnabled(false);
            } else {
                btnSubmit.setEnabled(true);
            }
        }
    }

    private void doSubmit() {
        String text = etInf.getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            ToastUtils.showShort("请填写问题描述");
            return;
        }
        String phone = editPhoneNum.getText().toString().trim();
        if (!TextUtils.isEmpty(phone)&&!VerifyUtil.isValidPhoneNumber(phone)) {
            ToastUtils.showShort("请填写正确的手机号码");
            return;
        }
//        showTradePresenter.submit(userInfoBean.user_id,text, mPicUrl);
        feedback(phone,text,mPicUrl);
    }


    private void feedback(String user_mobile, String content, String pic) {
        FeedBackRequestMo liveListRequestMo = new FeedBackRequestMo(user_mobile,content,pic);
        CommonService.feedback(liveListRequestMo, new BizResultListener<FeedBackMo>() {
            @Override
            public void onPreExecute() {
                showLoadingDialog(null);
            }

            @Override
            public void hitCache(boolean hit, FeedBackMo feedBackMo) {

            }

            @Override
            public void onSuccess(FeedBackMo feedBackMo) {
                submitSuccess(null);
                cancelLoadingDialog();
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                submitFail(bizMessage);
                cancelLoadingDialog();
            }
        });
    }

    private static final int REQUEST_CODE_UPLOAD_PIC = 16;

    private void selectImage(final int requestCode) {
        AndroidUtils.getInstance().selectImage(this, requestCode);
    }

    private void cropImage(String cropPicPath) {
        CropUtils.startCrop(cropPicPath, this);
    }

    private void updateCardViewInfo(String path) {
        mPicLocalPath = path;
        showTradePresenter.uploadPic(mPicLocalPath);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_UPLOAD_PIC:
                if (resultCode == Activity.RESULT_OK) {
                    //裁剪后返回的数据
                    List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    String cropPicPath = path.get(0);
                    if (cropPicPath != null) {
                        if (needCrop) {
                            String  cropPath=CropUtils.compress(cropPicPath);
                            if(!StringUtils.isEmpty(cropPath))
                            {
                                updateCardViewInfo(cropPath);
                            }
                        } else {
                            updateCardViewInfo(cropPicPath);
                        }
                    } else {
                        ToastUtils.showShort("请重新选择图片");
                    }
                }
                break;
            case UCrop.REQUEST_CROP:
                if (resultCode == Activity.RESULT_OK) {
                    final Uri resultUri = UCrop.getOutput(data);
                    String cropPicPath = resultUri.getPath();
                    //裁剪后返回的数据
                    if (cropPicPath != null) {
                        updateCardViewInfo(cropPicPath);
                    } else {
                        ToastUtils.showShort("请重新选择图片");
                    }
                }
                break;
            case UCrop.RESULT_ERROR:
                CropUtils.handleCropError(data);
                break;
        }

    }


    private void initView() {
        initTopBar();
        etInf.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mMaxLen)});
        etInf.addTextChangedListener(this);
        if(LoginHelper.getInstance().isLogin())
        {
            UserInfoBean userInfoBean=LoginHelper.getInstance().getLoginUserInfo();
            editPhoneNum.setText(userInfoBean.phone_mob);
        }
    }

    private void initTopBar() {
        mTopbarView.setMode(TopBarView.MODE_BACK);
        mTopbarView.setTitle("意见反馈");
    }


    @Override
    public void uploadUserHeadPicSuccess(String url) {
        GlideUtils.loadImageViewCrop(FeedBackActivity.this, mPicLocalPath, ivPic);
        mPicUrl = url;
    }

    @Override
    public void uploadUserHeadPicFail(String errorMsg) {
        ToastUtils.showShort(errorMsg);
    }

    @Override
    public void submitSuccess(BaseMo baseMo) {
        ToastUtils.showShort("提交成功");
        finish();
//        if (!isFinishing() && !(commonDialog != null && !commonDialog.isShowing())) {
//            commonDialog = new CommonDialog(FeedBackActivity.this, "提交成功！", "请等待工作人员审核", commitClickListener, 1, "", "确定");
//            commonDialog.show();
//        }
    }

    private CommonDialog.CommitClickListener commitClickListener = new CommonDialog.CommitClickListener() {
        @Override
        public void click(int whichDialog,Object object) {
            switch (whichDialog) {
                case 1:
                    Intent intent = new Intent(FeedBackActivity.this, GoldMainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }

        }
    };

    @Override
    public void submitFail(String errorMsg) {
        ToastUtils.showShort(errorMsg);
    }

    @OnClick({R.id.iv_pic, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_pic:
                selectImage(REQUEST_CODE_UPLOAD_PIC);
                break;
            case R.id.btn_next:
                doSubmit();
                break;
        }
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

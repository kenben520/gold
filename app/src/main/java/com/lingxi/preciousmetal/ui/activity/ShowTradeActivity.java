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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lingxi.biz.domain.responseMo.BaseMo;
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
import com.lingxi.preciousmetal.util.glideutils.GlideUtils;
import com.lingxi.preciousmetal.util.utilCode.StringUtils;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;
import com.yalantis.ucrop.UCrop;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by zhangwei on 2018/4/19.
 */

public class ShowTradeActivity extends TranslucentStatusBarActivity implements ShowTradeVInterface, TextWatcher {
    @BindView(R.id.topbar_view)
    TopBarView mTopbarView;
    @BindView(R.id.et_inf)
    EditText et_inf;
    @BindView(R.id.tv_len_count)
    TextView mTvLenCount;
    @BindView(R.id.rl_modifyInf)
    RelativeLayout rlModifyInf;
    @BindView(R.id.btn_next)
    TextView btnNext;
    public static final int MAX_LEN_LIFESTATION = 30;
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    private int mMaxLen = MAX_LEN_LIFESTATION;
    private String mPicLocalPath;
    private String mPicUrl;
    private CommonDialog commonDialog;
    private ShowTradePresenter showTradePresenter;
    private boolean needCrop = true;
    UserInfoBean userInfoBean;
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ShowTradeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_trade_form);
        userInfoBean = LoginHelper.getInstance().getLoginUserInfo();
        ButterKnife.bind(this);
        showTradePresenter = new ShowTradePresenter(this);
        initTopBar();
        initView();
    }

    private void initView() {
        mMaxLen = MAX_LEN_LIFESTATION;
        et_inf.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mMaxLen)});
        et_inf.addTextChangedListener(this);
    }

    private void initTopBar() {
        mTopbarView.setMode(TopBarView.MODE_BACK);
        mTopbarView.setTitle("我要晒单");
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
                mTvLenCount.setTextColor(getResources().getColor(R.color.warmGrey));
            }
        }
    }

    private void doSubmit() {
        String text = et_inf.getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            ToastUtils.showShort("请输入您的交易心得");
            return;
        }
        if (TextUtils.isEmpty(mPicUrl)) {
            ToastUtils.showShort("请上传图片");
            return;
        }
        showTradePresenter.submit(userInfoBean.user_id,text, mPicUrl);
    }


    private static final int REQUEST_CODE_UPLOAD_PIC = 15;

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
    public void uploadUserHeadPicSuccess(String url) {
        GlideUtils.loadImageViewCrop(ShowTradeActivity.this, mPicLocalPath, ivPic);
        mPicUrl = url;
    }

    @Override
    public void uploadUserHeadPicFail(String errorMsg) {
        ToastUtils.showShort(errorMsg);
    }

    @Override
    public void submitSuccess(BaseMo baseMo) {
        if (!isFinishing() && !(commonDialog != null && !commonDialog.isShowing())) {
            commonDialog = new CommonDialog(ShowTradeActivity.this, "提交成功！", "请等待工作人员审核", commitClickListener, 1, "", "确定");
            commonDialog.show();
        }
    }

    private CommonDialog.CommitClickListener commitClickListener = new CommonDialog.CommitClickListener() {
        @Override
        public void click(int whichDialog,Object object) {
            switch (whichDialog) {
                case 1:
                    Intent intent = new Intent(ShowTradeActivity.this, GoldMainActivity.class);
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
}

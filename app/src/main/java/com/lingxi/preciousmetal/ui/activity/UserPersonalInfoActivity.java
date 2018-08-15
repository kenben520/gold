package com.lingxi.preciousmetal.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lingxi.common.util.StringUtil;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;
import com.lingxi.preciousmetal.common.EventBusKeyDefine;
import com.lingxi.preciousmetal.domain.UserInfoBean;
import com.lingxi.preciousmetal.login.LoginHelper;
import com.lingxi.preciousmetal.presenter.pIml.UserPersonalInfoPresenter;
import com.lingxi.preciousmetal.ui.activity.kefu.KefuLoginActivity;
import com.lingxi.preciousmetal.ui.vInterface.UserPernalInfoVInterface;
import com.lingxi.preciousmetal.ui.widget.TopBarView;
import com.lingxi.preciousmetal.ui.widget.dialog.CommonDialog;
import com.lingxi.preciousmetal.util.AndroidUtils;
import com.lingxi.preciousmetal.util.CropUtils;
import com.lingxi.preciousmetal.util.VerifyUtil;
import com.lingxi.preciousmetal.util.glideutils.GlideUtils;
import com.lingxi.preciousmetal.util.utilCode.PhoneUtils;
import com.lingxi.preciousmetal.util.utilCode.StringUtils;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;
import com.yalantis.ucrop.UCrop;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by zhangwei on 2018/5/23.
 */

public class UserPersonalInfoActivity extends TranslucentStatusBarActivity implements UserPernalInfoVInterface {
    @BindView(R.id.topbar_view)
    TopBarView topbarView;
    @BindView(R.id.iv_user_icon)
    ImageView ivUserIcon;
    @BindView(R.id.edit_nickname)
    EditText editNickname;
    @BindView(R.id.btn_delete)
    ImageView btnDelete;
    @BindView(R.id.tv_agree)
    LinearLayout tvAgree;
    @BindView(R.id.btn_ensure)
    TextView btnEnsure;
    @BindView(R.id.edit_email)
    EditText editEmail;
    private UserPersonalInfoPresenter mineFragmentPresenter;
    private String mUserHeadPicLocalPath;
    private boolean needCrop = true;
    private String mPicUrl;
    UserInfoBean userInfoBean;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, UserPersonalInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        ButterKnife.bind(this);
        userInfoBean = LoginHelper.getInstance().getLoginUserInfo();
        mineFragmentPresenter = new UserPersonalInfoPresenter(this);
        initTopBar();
        initView();
    }

    private void initView() {
        UserInfoBean userInfoBean = LoginHelper.getInstance().getLoginUserInfo();
        if (userInfoBean != null) {
            editNickname.setText(userInfoBean.nick_name);
            if (!StringUtil.isEmpty(userInfoBean.nick_name)) {
                editNickname.setSelection(userInfoBean.nick_name.length());
            }
            editEmail.setText(userInfoBean.email);
//            mPicUrl=userInfoBean.avatars;
//            GlideUtils.loadCirclePic(this, R.drawable.ic_avatar_default1, userInfoBean.avatars, ivUserIcon);
            GlideUtils.loadRoundImage(this, R.drawable.ic_avatar_default1, 58, userInfoBean.avatars, ivUserIcon);

        }
        editNickname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    btnDelete.setVisibility(View.VISIBLE);
                } else {
                    btnDelete.setVisibility(View.GONE);
                }
            }
        });
        editNickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String editable = editNickname.getText().toString();
                String str = VerifyUtil.stringFilter(editable.toString());
                if (!editable.equals(str)) {
                    editNickname.setText(str);
                    editNickname.setSelection(str.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void initTopBar() {
        topbarView.setMode(TopBarView.MODE_BACK);
        topbarView.setTitle("个人信息");
        topbarView.setActionButton(R.drawable.ic_custom_service_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KefuLoginActivity.actionStart(UserPersonalInfoActivity.this);
            }
        });
    }

    @OnClick({R.id.iv_user_icon, R.id.btn_delete, R.id.btn_ensure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_user_icon:
                selectImage(REQUEST_CODE_MODIFY_USER_HEAD_PIC);
                break;
            case R.id.btn_delete:
                editNickname.setText("");
                break;
            case R.id.btn_ensure:
                doSubmit();
                break;
        }
    }

    private void doSubmit() {
        String text = editNickname.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            ToastUtils.showShort("请输入昵称");
            return;
        }
        if (TextUtils.isEmpty(mPicUrl) && TextUtils.isEmpty(userInfoBean.avatars)) {
            ToastUtils.showShort("请上传图片");
            return;
        }

        if (TextUtils.isEmpty(userInfoBean.email)){
            if(!TextUtils.isEmpty(email)&&!VerifyUtil.isEmail(email)){
                ToastUtils.showShort("请输入正确的邮箱格式");
                return;
            }
        }else{
            if(TextUtils.isEmpty(email)){
                ToastUtils.showShort("请输入邮箱");
                return;
            }
            if(!VerifyUtil.isEmail(email)){
                ToastUtils.showShort("请输入正确的邮箱格式");
                return;
            }
        }
        String url = TextUtils.isEmpty(mPicUrl) ? "" : mPicUrl;
        mineFragmentPresenter.modifyUserInfo(userInfoBean.user_id, url, text,email);
    }

    private static final int REQUEST_CODE_MODIFY_USER_HEAD_PIC = 10;

    private void selectImage(final int requestCode) {
        AndroidUtils.getInstance().selectImage(this, requestCode);
    }

    private void cropImage(String cropPicPath) {
        CropUtils.startCrop(cropPicPath, this);
    }

    private void updateCardViewInfo(String path) {
//        GlideUtils.loadImageViewCrop(this, R.drawable.main_default_header, path, ivUserIcon);
        mUserHeadPicLocalPath = path;
        mineFragmentPresenter.uploadUserHeadPic(mUserHeadPicLocalPath);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_MODIFY_USER_HEAD_PIC:
                if (resultCode == Activity.RESULT_OK) {
                    //裁剪后返回的数据
                    List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    String cropPicPath = path.get(0);
//                    cropPicPath = data.getStringExtra(REQUEST_CODE_MODIFY_USER_HEAD_PIC);
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
    private CommonDialog commonDialog;
    private CommonDialog.CommitClickListener commitClickListener = new CommonDialog.CommitClickListener() {
        @Override
        public void click(int whichDialog,Object object) {
            switch (whichDialog) {
                case 1:
                    finish();
                    break;
            }

        }
    };

    @Override
    protected void onDestroy() {
        if (commonDialog != null) {
            if (commonDialog.isShowing()) commonDialog.dismiss();
        }
        super.onDestroy();
    }


    @Override
    public void modifyUserInfoSuccess(String avatar,String email) {
        UserInfoBean userInfoBean = LoginHelper.getInstance().getLoginUserInfo();
        if (userInfoBean != null) {
            String text = editNickname.getText().toString().trim();
            String emailText = editEmail.getText().toString().trim();
            boolean modifyEmail=!(emailText.equals(userInfoBean.email)&&!StringUtils.isEmpty(userInfoBean.email));
            userInfoBean.nick_name = text;
            userInfoBean.email = emailText;
            String url = TextUtils.isEmpty(mPicUrl) ? userInfoBean.avatars : avatar;
            userInfoBean.avatars = url;
            LoginHelper.getInstance().updateLoginUserInfo(userInfoBean);
            EventBus.getDefault().post(new EventBusKeyDefine.EventBusMsgData<String>(EventBusKeyDefine.EVENTBUS_USER_INFO_UPDATE, ""));
            if(modifyEmail)
            {
                commonDialog = new CommonDialog(UserPersonalInfoActivity.this, "提示", "已发送认证邮件，请登录邮箱进行认证", commitClickListener, 1, "", "确认");
                commonDialog.show();
                return;
            }
        }
        finish();
    }

    @Override
    public void modifyUserInfoFail(String errorMsg) {
        ToastUtils.showShort(errorMsg);
    }

    @Override
    public void uploadUserHeadPicSuccess(String url) {
        GlideUtils.loadRoundImage(this, R.drawable.ic_avatar_default1, 58, mUserHeadPicLocalPath, ivUserIcon);
        mPicUrl = url;
    }

    @Override
    public void uploadUserHeadPicFail(String errorMsg) {
        ToastUtils.showShort(errorMsg);
    }

}

package com.lingxi.preciousmetal.presenter.pIml;

import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.ModifyUserPersonalInfoRequestMo;
import com.lingxi.biz.domain.requestMo.UploadPicRequestMo;
import com.lingxi.biz.domain.responseMo.BaseMo;
import com.lingxi.biz.domain.responseMo.UploadPicResultMo;
import com.lingxi.biz.domain.responseMo.UserInfoResultMo;
import com.lingxi.biz.service.UploadService;
import com.lingxi.biz.service.UserService;
import com.lingxi.common.util.StringUtil;
import com.lingxi.preciousmetal.presenter.BasePresenter;
import com.lingxi.preciousmetal.ui.vInterface.UserPernalInfoVInterface;


/**
 * Created by zhangwei on 2018/4/19.
 */

public class UserPersonalInfoPresenter extends BasePresenter<UserPernalInfoVInterface> {
    private BizResultListener modifyUserHeadListener;
    private BizResultListener uploadUserHeadListener;
    public UserPersonalInfoPresenter(UserPernalInfoVInterface view) {
        super(view);
        initListener();
    }

    private void initListener() {
        uploadUserHeadListener = new BizResultListener<UploadPicResultMo>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, UploadPicResultMo loginResultMo) {

            }

            @Override
            public void onSuccess(UploadPicResultMo uploadPicResultMo) {
                if (isViewAttached() && uploadPicResultMo != null && !StringUtil.isEmpty(uploadPicResultMo.pictures)) {
                    getView().uploadUserHeadPicSuccess(uploadPicResultMo.pictures);
                } else {
                    onFail(-1, -1, "上传失败，请重新上传");
                }
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                if (isViewAttached()) {
                    getView().uploadUserHeadPicFail(bizMessage);
                }
            }
        };
        modifyUserHeadListener = new BizResultListener<UserInfoResultMo>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, UserInfoResultMo loginResultMo) {

            }

            @Override
            public void onSuccess(UserInfoResultMo uploadPicResultMo) {
                if (isViewAttached()) {
                    getView().modifyUserInfoSuccess(uploadPicResultMo.avatars,uploadPicResultMo.email);
                } else {
                    onFail(-1, -1, "上传失败，请重新上传");
                }
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                if (isViewAttached()) {
                    getView().modifyUserInfoFail(bizMessage);
                }
            }
        };
    }

    public void uploadUserHeadPic(String localPicPath) {
        UploadPicRequestMo uploadPicRequestMo = new UploadPicRequestMo(localPicPath);
        UploadService.uploadPic(uploadPicRequestMo, uploadUserHeadListener);
    }

    public void modifyUserInfo(String userId, String avatars, String nickName, String email) {
        ModifyUserPersonalInfoRequestMo uploadPicRequestMo = new ModifyUserPersonalInfoRequestMo(userId, avatars, nickName,email);
        UserService.modifyAvatar(uploadPicRequestMo, modifyUserHeadListener);
    }
}

/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lingxi.preciousmetal.ui.activity.kefu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.hyphenate.chat.ChatClient;
import com.hyphenate.chat.Conversation;
import com.hyphenate.helpdesk.Error;
import com.hyphenate.helpdesk.callback.Callback;
import com.hyphenate.helpdesk.easeui.util.IntentBuilder;
import com.jaeger.library.StatusBarUtil;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.BaseActivity;
import com.lingxi.preciousmetal.common.KefuMessageConstant;


public class KefuLoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";

    private boolean progressShow;
    private ProgressDialog progressDialog;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, KefuLoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.black));
        //ChatClient.getInstance().isLoggedInBefore() 可以检测是否已经登录过环信，如果登录过则环信SDK会自动登录，不需要再次调用登录操作
        if (ChatClient.getInstance().isLoggedInBefore()) {
            progressDialog = getProgressDialog();
            progressDialog.setMessage(getResources().getString(R.string.is_contact_customer));
            progressDialog.show();
            toChatActivity();
        } else {
            //随机创建一个用户并登录环信服务器
            createRandomAccountThenLoginChatServer();
        }
    }


    private void createRandomAccountThenLoginChatServer() {
        // 自动生成账号,此处每次都随机生成一个账号,为了演示.正式应从自己服务器获取账号
        final String account = KefuMessageHelper.getRandomAccount();
        final String userPwd = KefuMessageConstant.DEFAULT_ACCOUNT_PWD;
        progressDialog = getProgressDialog();
        progressDialog.setMessage(getString(R.string.system_is_regist));
        progressDialog.show();
        // createAccount to huanxin server
        // if you have a account, this step will ignore
        // TODO: 2018/7/31 注册
        ChatClient.getInstance().register(account, userPwd, new Callback() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "demo register success");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //登录环信服务器
                        login(account, userPwd);
                    }
                });
            }

            @Override
            public void onError(final int errorCode, String error) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        if (errorCode == Error.NETWORK_ERROR) {
                            Toast.makeText(getApplicationContext(), R.string.network_unavailable, Toast.LENGTH_SHORT).show();
                        } else if (errorCode == Error.USER_ALREADY_EXIST) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //登录环信服务器
                                    login(account, userPwd);
                                }
                            });
                            return;
//                            Toast.makeText(getApplicationContext(), R.string.user_already_exists, Toast.LENGTH_SHORT).show();
                        } else if (errorCode == Error.USER_AUTHENTICATION_FAILED) {
                            Toast.makeText(getApplicationContext(), R.string.no_register_authority, Toast.LENGTH_SHORT).show();
                        } else if (errorCode == Error.USER_ILLEGAL_ARGUMENT) {
                            Toast.makeText(getApplicationContext(), R.string.illegal_user_name, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.register_user_fail, Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }
        });
    }

    private ProgressDialog getProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(KefuLoginActivity.this);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setOnCancelListener(new OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    progressShow = false;
                }
            });
        }
        return progressDialog;
    }

    private void login(final String uname, final String upwd) {
        progressShow = true;
        progressDialog = getProgressDialog();
        progressDialog.setMessage(getResources().getString(R.string.is_contact_customer));
        if (!progressDialog.isShowing()) {
            if (isFinishing()) {
                return;
            }
            progressDialog.show();
        }
        // login huanxin server
        ChatClient.getInstance().login(uname, upwd, new Callback() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "demo login success!");
                if (!progressShow) {
                    return;
                }
                toChatActivity();
            }

            @Override
            public void onError(int code, String error) {
                Log.e(TAG, "login fail,code:" + code + ",error:" + error);
                if (!progressShow) {
                    return;
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                        Toast.makeText(KefuLoginActivity.this,
                                getResources().getString(R.string.is_contact_customer_failure_seconed),
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }
        });
    }

    private void toChatActivity() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!KefuLoginActivity.this.isFinishing())
                    progressDialog.dismiss();

                // 获取华为 HMS 推送 token
//				HMSPushHelper.getInstance().getHMSPushToken();

                //此处演示设置技能组,如果后台设置的技能组名称为[shouqian|shouhou],这样指定即分配到技能组中.
                String queueName = null;
                //设置点击通知栏跳转事件
                Conversation conversation = ChatClient.getInstance().chatManager().getConversation(KefuMessageConstant.DEFAULT_CUSTOMER_ACCOUNT);
                String titleName = null;
                if (conversation.officialAccount() != null) {
                    titleName = conversation.officialAccount().getName();
                }
                // 进入主页面
                Intent intent = new IntentBuilder(KefuLoginActivity.this)
                        .setTargetClass(OnlineKefuActivity.class)
                        .setVisitorInfo(KefuMessageHelper.createVisitorInfo())
                        .setServiceIMNumber(KefuMessageConstant.DEFAULT_CUSTOMER_ACCOUNT)
                        .setScheduleQueue(KefuMessageHelper.createQueueIdentity(queueName))
                        .setTitleName(titleName)
                        .setShowUserNick(true)
                        .build();
                startActivity(intent);
                finish();

            }
        });
    }

}

package com.lingxi.preciousmetal.ui.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.lingxi.common.base.GlideApp;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.ui.activity.WebViewActivity;
import com.lingxi.preciousmetal.util.DisplayUtil;
import com.lingxi.preciousmetal.util.glideutils.GlideUtils;
import com.lingxi.preciousmetal.util.utilCode.StringUtils;
//广告弹窗
public class HomeAdDialog extends Dialog implements View.OnClickListener {

    public HomeAdDialog(@NonNull Context context) {
        super(context);
    }

    public HomeAdDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected HomeAdDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public HomeAdDialog(Context context, String url, String link) {
        super(context);
        init(context, url, link);
    }

    private void init(Context context, String url, final String link) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.dialog_ads_1);

//        Window window = getWindow();
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.width = (DisplayUtil.getScreenWidth(context)) * 4 / 5;
//        window.setGravity(Gravity.CENTER);

        ImageView imageView = (ImageView) findViewById(R.id.iv_ads);
//        GlideUtils.loadImageViewCenterInside(context, R.drawable.background_gray4, url, imageView);
        GlideApp.with(context).load(url).centerInside().into(imageView);
        View dialog_commit = findViewById(R.id.btn_close);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StringUtils.isEmpty(link)) {
                    WebViewActivity.actionStart(getContext(), link);
                    dismiss();
                }
            }
        });
        dialog_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setCanceledOnTouchOutside(true);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_commit:
                dismiss();
                break;
        }
        dismiss();
    }
}


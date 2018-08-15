package com.lingxi.preciousmetal.ui.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.util.glideutils.GlideUtils;

/**
 * Created by zhangwei on 2018/4/20.
 */
public class DialogManager {

    private DialogManager() {

    }

    private static class DialogManagerHolder {
        private static DialogManager Instance = new DialogManager();
    }

    public static DialogManager getInstance() {
        return DialogManagerHolder.Instance;
    }

    private Dialog loadDialog = null;

    public synchronized void showLoadingDialog(Activity a,
                                               String message, Boolean _isClickableOutside) {
        showLoadingDialog(a, message, _isClickableOutside, true);
    }

    public synchronized void showLoadingDialog(Activity a,
                                               String message, Boolean _isClickableOutside, Boolean cancelable)// modify
    {
        if (loadDialog != null) {
            if (loadDialog.isShowing())
                return;
        }
        View view = LayoutInflater.from(a).inflate(
                R.layout.loading_view_dialog_default, null);
        loadDialog = new Dialog(a, R.style.NoTitleDialogAndTransparent);
        loadDialog.setContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
//        TextView _tips = (TextView) view.findViewById(R.id.lvd_tips);
//        if (!TextUtils.isEmpty(message)) {
//            _tips.setText(message);
//            _tips.setVisibility(View.VISIBLE);
//        } else {
//            _tips.setText("");
//            _tips.setVisibility(View.GONE);
//        }
        loadDialog.setCanceledOnTouchOutside(_isClickableOutside);
        if (loadDialog != null) {
            try {
                if (!loadDialog.isShowing() && !a.isFinishing())
                    loadDialog.show();
            } catch (Exception e) {
            }
        }
        if (!cancelable) {
            loadDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface arg0, int arg1,
                                     KeyEvent arg2) {
                    if (arg2.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                        return true;
                    }
                    return false;
                }
            });
        }
    }

    public synchronized void cancellLoadingDialog() {
        if (loadDialog != null && loadDialog.isShowing()) {
            loadDialog.dismiss();
        }
    }
}

package com.lingxi.preciousmetal.ui.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.util.DisplayUtil;

public class CustomerAgreementDialog extends Dialog implements View.OnClickListener {

    private TextView tv_dialog_title, tv_dialog_commit;
    private View lay_dialog_title, vertical_line_view;

    public CustomerAgreementDialog(@NonNull Context context) {
        super(context);
    }

    public CustomerAgreementDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CustomerAgreementDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public CustomerAgreementDialog(Context context, String dialog_title, String dialog_msg) {
        super(context);
        init(context,dialog_title, dialog_msg);
    }

    private void init(Context context, String dialog_title, String dialog_msg) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.layout_customer_agreement_dialog);

        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = (DisplayUtil.getScreenWidth(context)) * 4 / 5;
        window.setGravity(Gravity.CENTER);

        lay_dialog_title = findViewById(R.id.lay_dialog_title);
        tv_dialog_title = (TextView) findViewById(R.id.dialog_title);
        vertical_line_view = findViewById(R.id.vertical_line_view);

        ((TextView) findViewById(R.id.dialog_msg)).setText(dialog_msg + "");
        tv_dialog_title.setText(dialog_title + "");

        tv_dialog_commit = (TextView) findViewById(R.id.dialog_commit);

        setCanceledOnTouchOutside(false);
        tv_dialog_commit.setOnClickListener(this);
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


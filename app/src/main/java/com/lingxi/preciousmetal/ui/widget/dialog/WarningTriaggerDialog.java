package com.lingxi.preciousmetal.ui.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.lingxi.biz.domain.responseMo.WarningsItemMo;
import com.lingxi.biz.util.ConstantUtil;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.ui.activity.trade.StartBuyGoldActivity;
import com.lingxi.preciousmetal.util.DisplayUtil;
import com.lingxi.preciousmetal.util.utilCode.TimeUtils;

import static com.lingxi.preciousmetal.util.utilCode.TimeUtils.FMDHMS;

public class WarningTriaggerDialog extends Dialog implements View.OnClickListener {

    private TextView tv_dialog_title, tv_dialog_commit, tv_dialog_cancel,tv_1,tv_2,tv_price,tv_time;
//    private View lay_dialog_title, vertical_line_view;
    private CommitClickListener commitClickListener;
    private int whichDialog;
    //基础模式
    public WarningTriaggerDialog(Context context, String dialog_title, String dialog_msg, CommitClickListener commitClickListener, int whichDialog, String tv_cancel, String tv_commit,WarningsItemMo item) {
        super(context);
        this.commitClickListener = commitClickListener;
        this.whichDialog = whichDialog;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.dialog_warning_trigger);

        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = (DisplayUtil.getScreenWidth(context)) * 4 / 5;
        window.setGravity(Gravity.CENTER);

//        lay_dialog_title = findViewById(R.id.lay_dialog_title);
        tv_dialog_title = (TextView) findViewById(R.id.tv_dialog_title);
        tv_price = (TextView) findViewById(R.id.tv_price);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_1 = (TextView) findViewById(R.id.tv_1);
        tv_2 = (TextView) findViewById(R.id.tv_2);
        String title1 = "";
        if (ConstantUtil.XAUUSD.equals(item.prod_code)) {
            title1 = "伦敦金";
        } else if (ConstantUtil.XAGUSD.equals(item.prod_code)) {
            title1 = "伦敦银";
        }
        tv_dialog_title.setText(title1+"预警提醒");
//        if (item.pro_type == 1) {
//            tv_1.append("买:"+item.pro_warn_price);
//            tv_1.setTextColor(getContext().getColor(R.color.orangeyRedTwo));
//            tv_2.append("卖:"+item.pro_warn_price);
//            tv_2.setTextColor(getContext().getColor(R.color.orangeyRedTwo));
//        } else if (item.pro_type == 2) {
//            title2.append("买出价格:");
//        }

        StringBuilder title2 = new StringBuilder();
        title2.append("提醒:");
        if (item.pro_type == 1) {
            title2.append("买入价格");
        } else if (item.pro_type == 2) {
            title2.append("买出价格");
        }
        if (item.pro_direction == 1) {
            title2.append("≥");
        } else if (item.pro_direction == 2) {
            title2.append("≤");
        }
        title2.append(item.pro_warn_price);
        tv_price.setText(title2.toString());
        tv_time.setText("时间:"+TimeUtils.getDataStr(item.pro_trigger_time,FMDHMS));
//        vertical_line_view = findViewById(R.id.vertical_line_view);
//
//        ((TextView) findViewById(R.id.dialog_msg)).setText(dialog_msg + "");
//
        tv_dialog_cancel = (TextView) findViewById(R.id.dialog_cancel);
        tv_dialog_commit = (TextView) findViewById(R.id.dialog_commit);
//
//
//        if (!TextUtils.isEmpty(dialog_title)) {
//            lay_dialog_title.setVisibility(View.VISIBLE);
//            tv_dialog_title.setText(dialog_title + "");
//        }
//        if (!TextUtils.isEmpty(tv_cancel)) {
//            tv_dialog_cancel.setVisibility(View.VISIBLE);
//            tv_dialog_cancel.setText(String.valueOf(tv_cancel));
//        }
//        if (!TextUtils.isEmpty(tv_commit)) {
//            tv_dialog_commit.setVisibility(View.VISIBLE);
//            tv_dialog_commit.setText(String.valueOf(tv_commit));
//        }
//        if (!TextUtils.isEmpty(tv_commit) && !TextUtils.isEmpty(dialog_title)) {
//            vertical_line_view.setVisibility(View.VISIBLE);
//        }
        setCanceledOnTouchOutside(false);
        tv_dialog_cancel.setOnClickListener(this);
        tv_dialog_commit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_cancel:
                dismiss();
                break;
            case R.id.dialog_commit:
                StartBuyGoldActivity.startMyActivity(getContext(),0);

//                commitClickListener.Click(whichDialog);
//                dismiss();
                break;
        }
        dismiss();
    }

    public interface CommitClickListener {
        void Click(int whichDialog);
    }


}


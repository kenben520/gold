package com.lingxi.preciousmetal.ui.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.tifezh.kchartlib.utils.ViewUtil;
import com.lingxi.biz.domain.responseMo.OrderSucceedBean;
import com.lingxi.biz.domain.responseMo.TradeAllPositionBean;
import com.lingxi.biz.util.ConstantUtil;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.ui.activity.trade.SetProfitAndLossActivity;
import com.lingxi.preciousmetal.util.DisplayUtil;
import com.lingxi.preciousmetal.util.utilCode.AppUtils;

public class CommonDialog extends Dialog implements View.OnClickListener {

    private TextView tv_dialog_title, tv_dialog_commit, tv_dialog_cancel;
    private View lay_dialog_title, vertical_line_view;
    private CommitClickListener commitClickListener;
    private int whichDialog;
    private Object object;

    public CommonDialog(@NonNull Context context) {
        super(context);
    }

    public CommonDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CommonDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    //基础模式
    public CommonDialog(Context context, String dialog_title, String dialog_msg, CommitClickListener commitClickListener, int whichDialog, String tv_cancel, String tv_commit) {
        super(context);
        init(context, dialog_title, dialog_msg, commitClickListener, whichDialog, tv_cancel, tv_commit, null);
    }

    public CommonDialog(Context context, String dialog_title, String dialog_msg, CommitClickListener commitClickListener, int whichDialog, String tv_cancel, String tv_commit, Object object) {
        super(context);
        init(context, dialog_title, dialog_msg, commitClickListener, whichDialog, tv_cancel, tv_commit, object);
    }


    public CommonDialog(Context context, String dialog_title, String dialog_msg, CommitClickListener commitClickListener, int whichDialog, String tv_cancel, String tv_commit, Object object,int style) {
        super(context);
        if(style==2)
        {
            initS2(context, dialog_title, dialog_msg, commitClickListener, whichDialog, tv_cancel, tv_commit, object);
        }else if(style==3)
        {
            initClearPosition(context, dialog_title, dialog_msg, commitClickListener, whichDialog, tv_cancel, tv_commit, object);
        }else{
            init(context, dialog_title, dialog_msg, commitClickListener, whichDialog, tv_cancel, tv_commit, object,style);
        }
    }

    public CommonDialog(Context context, TradeSucceedListener commitClickListener, Object object,double handNum,double depositEvaluate,String goodsName) {
        super(context);
        initTradeSuccess(context,commitClickListener,object,handNum,depositEvaluate,goodsName);
    }


    private void init(Context context, String dialog_title, String dialog_msg, CommitClickListener commitClickListener, int whichDialog, String tv_cancel, String tv_commit, Object object) {
        this.commitClickListener = commitClickListener;
        this.whichDialog = whichDialog;
        this.object = object;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.layout_common_dialog);

        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = (DisplayUtil.getScreenWidth(context)) * 4 / 5;
        window.setGravity(Gravity.CENTER);

        lay_dialog_title = findViewById(R.id.lay_dialog_title);
        tv_dialog_title = (TextView) findViewById(R.id.dialog_title);
        vertical_line_view = findViewById(R.id.vertical_line_view);

        ((TextView) findViewById(R.id.dialog_msg)).setText(dialog_msg + "");

        tv_dialog_cancel = (TextView) findViewById(R.id.dialog_cancel);
        tv_dialog_commit = (TextView) findViewById(R.id.dialog_commit);


        if (!TextUtils.isEmpty(dialog_title)) {
            lay_dialog_title.setVisibility(View.VISIBLE);
            tv_dialog_title.setText(dialog_title + "");
        }
        if (!TextUtils.isEmpty(tv_cancel)) {
            tv_dialog_cancel.setVisibility(View.VISIBLE);
            tv_dialog_cancel.setText(String.valueOf(tv_cancel));
        }
        if (!TextUtils.isEmpty(tv_commit)) {
            tv_dialog_commit.setVisibility(View.VISIBLE);
            tv_dialog_commit.setText(String.valueOf(tv_commit));
        }
        if (!TextUtils.isEmpty(tv_commit) && !TextUtils.isEmpty(dialog_title)) {
            vertical_line_view.setVisibility(View.VISIBLE);
        }
        setCanceledOnTouchOutside(false);
        tv_dialog_cancel.setOnClickListener(this);
        tv_dialog_commit.setOnClickListener(this);
    }

    private void init(Context context, String dialog_title, String dialog_msg, CommitClickListener commitClickListener, int whichDialog, String tv_cancel, String tv_commit, Object object,int style) {
        this.commitClickListener = commitClickListener;
        this.whichDialog = whichDialog;
        this.object = object;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.layout_common_dialog_style1);

        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = (DisplayUtil.getScreenWidth(context)) * 4 / 5;
        window.setGravity(Gravity.CENTER);


        ((TextView) findViewById(R.id.dialog_msg)).setText(dialog_msg + "");

        tv_dialog_cancel = (TextView) findViewById(R.id.dialog_cancel);
        tv_dialog_commit = (TextView) findViewById(R.id.dialog_commit);

        if (!TextUtils.isEmpty(tv_cancel)) {
            tv_dialog_cancel.setVisibility(View.VISIBLE);
            tv_dialog_cancel.setText(String.valueOf(tv_cancel));
        }
        if (!TextUtils.isEmpty(tv_commit)) {
            tv_dialog_commit.setVisibility(View.VISIBLE);
            tv_dialog_commit.setText(String.valueOf(tv_commit));
        }
        setCanceledOnTouchOutside(false);
        tv_dialog_cancel.setOnClickListener(this);
        tv_dialog_commit.setOnClickListener(this);
    }


    private void initS2(Context context, String dialog_title, String dialog_msg, CommitClickListener commitClickListener, int whichDialog, String tv_cancel, String tv_commit, Object object) {
        this.commitClickListener = commitClickListener;
        this.whichDialog = whichDialog;
        this.object = object;
        TradeAllPositionBean bean=(TradeAllPositionBean)object;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.layout_common_dialog_style2);

        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = (DisplayUtil.getScreenWidth(context)) * 4 / 5;
        window.setGravity(Gravity.CENTER);

        TextView position_pnl_view=findViewById(R.id.tv_winlost);
        View bg_1=findViewById(R.id.bg_1);
        TextView position_price_view=findViewById(R.id.tv_price1);
        TextView position_now_price_view=findViewById(R.id.tv_price2);
        TextView position_volume_view=findViewById(R.id.tv_handnum);


        int type = 1;
        String format;
        if (type==1){
            format = "%.2f";
        } else {
            format = "%.3f";
        }
        position_price_view.setText(String.format(format,bean.getOpenPrice()));
        position_now_price_view.setText(String.format(format,bean.getClosePrice()));
        position_volume_view.setText(bean.getVolume()+"手");

        double pnl = bean.getPnl();
        if (pnl>0){
            bg_1.setBackground(getContext().getResources().getDrawable(R.drawable.lx_green_bg));
            position_pnl_view.setTextColor(ViewUtil.getKUpColor(context));
            position_pnl_view.setText("+$"+pnl);
        } else {
            bg_1.setBackground(getContext().getResources().getDrawable(R.drawable.lx_red_bg));
            position_pnl_view.setTextColor(ViewUtil.getKLossColor(context));
            position_pnl_view.setText("-$"+pnl);
        }
        AppUtils.setCustomFont(context,position_pnl_view);

//        lay_dialog_title = findViewById(R.id.lay_dialog_title);
//        tv_dialog_title = (TextView) findViewById(R.id.dialog_title);
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
        if (!TextUtils.isEmpty(tv_cancel)) {
            tv_dialog_cancel.setVisibility(View.VISIBLE);
            tv_dialog_cancel.setText(String.valueOf(tv_cancel));
        }
        if (!TextUtils.isEmpty(tv_commit)) {
            tv_dialog_commit.setVisibility(View.VISIBLE);
            tv_dialog_commit.setText(String.valueOf(tv_commit));
        }
//        if (!TextUtils.isEmpty(tv_commit) && !TextUtils.isEmpty(dialog_title)) {
//            vertical_line_view.setVisibility(View.VISIBLE);
//        }
        setCanceledOnTouchOutside(false);
        tv_dialog_cancel.setOnClickListener(this);
        tv_dialog_commit.setOnClickListener(this);
    }

    private void initClearPosition(Context context, String dialog_title, String dialog_msg, CommitClickListener commitClickListener, int whichDialog, String tv_cancel, String tv_commit, Object object) {
        this.commitClickListener = commitClickListener;
        this.whichDialog = whichDialog;
        this.object = object;
        TradeAllPositionBean bean=(TradeAllPositionBean)object;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.dialog_close_position);

        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = (DisplayUtil.getScreenWidth(context)) * 4 / 5;
        window.setGravity(Gravity.CENTER);

        TextView w_order_price_View = findViewById(R.id.w_order_price_View);
        TextView w_order_now_price_View = findViewById(R.id.w_order_now_price_View);
        TextView w_order_profit_View = findViewById(R.id.w_order_profit_View);
        AppUtils.setCustomFont(context, w_order_price_View);
        AppUtils.setCustomFont(context, w_order_now_price_View);
        AppUtils.setCustomFont(context, w_order_profit_View);

        w_order_price_View.setText(String.valueOf(bean.getOpenPrice()));
        w_order_now_price_View.setText(ConstantUtil.SHOWDEFVALUE);
        double pnl = bean.getPnl();
        if (pnl > 0) {
            w_order_profit_View.setTextColor(ViewUtil.getKUpColor(getContext()));
            w_order_profit_View.setText("+" + pnl);
        } else {
            w_order_profit_View.setTextColor(ViewUtil.getKLossColor(getContext()));
            w_order_profit_View.setText(String.valueOf(pnl));
        }
        tv_dialog_cancel = (TextView) findViewById(R.id.w_sure_btn);
        setCanceledOnTouchOutside(false);
        tv_dialog_cancel.setOnClickListener(this);
    }

    private void initTradeSuccess(Context context, final TradeSucceedListener commitClickListener, Object object,double handNumber,double depositEvaluate,String goodsName) {
        this.whichDialog = whichDialog;
        this.object = object;
        OrderSucceedBean bean=(OrderSucceedBean)object;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.dialog_trade_succeed);

        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = (DisplayUtil.getScreenWidth(context)) * 4 / 5;
        window.setGravity(Gravity.CENTER);

        TextView order_id_View = findViewById(R.id.order_id_View);
        TextView order_price_View = findViewById(R.id.order_price_View);
        TextView order_vol_View = findViewById(R.id.order_vol_View);
        TextView order_margin_View = findViewById(R.id.order_margin_View);
        TextView order_profit_price_View = findViewById(R.id.order_profit_price_View);
        TextView order_loss_price_View = findViewById(R.id.order_loss_price_View);

        String formatStr = "%.2f";
        if (ConstantUtil.XAUUSD.equals(goodsName)) {
        } else if (ConstantUtil.XAGUSD.equals(goodsName)) {
            formatStr = "%.3f";
        }
        order_id_View.setText("#" + bean.getOrderId());
        order_price_View.setText(String.format(formatStr,bean.getOrderPrice()));
        order_vol_View.setText(String.valueOf(handNumber));
        order_margin_View.setText(String.format("%.2f", handNumber*1000));
        order_profit_price_View.setText("未设置");
        order_loss_price_View.setText("未设置");

        findViewById(R.id.set_profit_loss_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if(commitClickListener!=null)
                {
                    commitClickListener.setProfitAndLoss();
                }
            }
        });
        findViewById(R.id.my_trade_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if(commitClickListener!=null)
                {
                    commitClickListener.watchPosition();
                }
            }
        });
        findViewById(R.id.w_close_succeed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setCanceledOnTouchOutside(false);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_cancel:
                dismiss();
                break;
            case R.id.dialog_commit:
                commitClickListener.click(whichDialog, object);
                dismiss();
                break;
        }
        dismiss();
    }

    public interface CommitClickListener {
        void click(int whichDialog, Object object);
    }

    public interface TradeSucceedListener {
        void watchPosition();
        void setProfitAndLoss();
    }


}


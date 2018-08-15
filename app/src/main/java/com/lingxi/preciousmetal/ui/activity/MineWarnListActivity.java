package com.lingxi.preciousmetal.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.WarnDeleteRequestMo;
import com.lingxi.biz.domain.requestMo.WarnListRequestMo;
import com.lingxi.biz.domain.responseMo.BaseMo;
import com.lingxi.biz.domain.responseMo.WarningsItemMo;
import com.lingxi.biz.service.TradeService;
import com.lingxi.biz.util.ConstantUtil;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.domain.UserInfoBean;
import com.lingxi.preciousmetal.login.LoginHelper;
import com.lingxi.preciousmetal.ui.widget.TopBarView;
import com.lingxi.preciousmetal.ui.widget.dialog.CommonDialog;
import com.lingxi.preciousmetal.ui.widget.dialog.DialogManager;
import com.lingxi.preciousmetal.util.utilCode.TimeUtils;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

/**
 * Created by zhangwei on 2018/4/25.
 */

public class MineWarnListActivity extends BaseListActivity<WarningsItemMo> {
    private QuickAdapter mAdapter;
    UserInfoBean mUserInfoBean;
    private CommonDialog commonDialog;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MineWarnListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserInfoBean = LoginHelper.getInstance().getLoginUserInfo();
        initView();
        bindData();
    }

    public void loadData() {
        WarnListRequestMo liveListRequestMo = new WarnListRequestMo(mUserInfoBean.user_id);
        TradeService.getWarnList(liveListRequestMo, new BizResultListener<List<WarningsItemMo>>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, List<WarningsItemMo> warningsItemMos) {

            }

            @Override
            public void onSuccess(List<WarningsItemMo> warningsItemMos) {
                showList(warningsItemMos);
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                showFail(bizMessage);
            }
        });
    }

    private void initView() {
        initTopBar();
        noDataTips="暂无设置预警，请添加吧";
        mAdapter = new QuickAdapter();
        initView(mAdapter);
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = 0;
                loadData();
            }
        });
    }

    String currentIncid;

    public class QuickAdapter extends BaseQuickAdapter<WarningsItemMo, BaseViewHolder> {
        public QuickAdapter() {
            super(R.layout.item_warns);
        }

        @Override
        protected void convert(final BaseViewHolder viewHolder, final WarningsItemMo item) {
            String title1 = "";
            if (ConstantUtil.XAUUSD.equals(item.prod_code)) {
                title1 = "伦敦金";
            } else if (ConstantUtil.XAGUSD.equals(item.prod_code)) {
                title1 = "伦敦银";
            }
            StringBuilder title2 = new StringBuilder();
            if (item.pro_type == 1) {
                title2.append("买入价格:");
            } else if (item.pro_type == 2) {
                title2.append("买出价格:");
            }
            if (item.pro_direction == 1) {
                title2.append("≥");
            } else if (item.pro_direction == 2) {
                title2.append("≤");
            }
            title2.append(item.pro_warn_price);
            String timeTag = "24h";
            int day = (int) (item.pro_h_exp - item.add_time) / 3600 / 24;
            if (1 == day) {
                timeTag = "24h";
            } else if (7 == day) {
                timeTag = "7d";
            } else if (15 == day) {
                timeTag = "15d";
            }
            viewHolder.setText(R.id.title1, title1).setText(R.id.title2, title2.toString()).setText(R.id.title3, "到期时间：" + TimeUtils.millis2String(item.pro_h_exp * 1000));
            viewHolder.setText(R.id.tv_tag, timeTag);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            if((viewHolder.getLayoutPosition()+1)%2==0)
            {
                viewHolder.itemView.setBackgroundColor(getResources().getColor(R.color.whiteTwo));
            }else{
                viewHolder.itemView.setBackgroundColor(getResources().getColor(R.color.grayF5));
            }
            viewHolder.getView(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentIncid = item.inc_id;
                    commonDialog = new CommonDialog(MineWarnListActivity.this, "温馨提示", "是否确认删除?", commitClickListener, 1, "取消", "确定");
                    commonDialog.show();
                }
            });
        }
    }


    private CommonDialog.CommitClickListener commitClickListener = new CommonDialog.CommitClickListener() {
        @Override
        public void click(int whichDialog,Object object) {
            switch (whichDialog) {
                case 1:
                    delete();
                    break;
            }

        }
    };

    private void delete() {
//        DialogManager.getInstance().showLoadingDialog(MineWarnListActivity.this, "", false);
        WarnDeleteRequestMo liveListRequestMo = new WarnDeleteRequestMo(mUserInfoBean.user_id, currentIncid);
        TradeService.deleteWarning(liveListRequestMo, new BizResultListener<BaseMo>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, BaseMo baseMo) {

            }

            @Override
            public void onSuccess(BaseMo baseMo) {
//                DialogManager.getInstance().showLoadingDialog(MineWarnListActivity.this, "", false);
                pageNum = 0;
                bindData();
                ToastUtils.showShort("删除成功");
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                showFail(bizMessage);
//                DialogManager.getInstance().showLoadingDialog(MineWarnListActivity.this, "", false);
            }
        });
    }

    public void initTopBar() {
        topbarView.setMode(TopBarView.MODE_BACK);
        topbarView.setTitle("我的预警");
        topbarView.setActionButton(R.drawable.warning_add, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WarnAddActivity.actionStart(MineWarnListActivity.this);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            pageNum = 0;
            bindData();
        }
    }
}

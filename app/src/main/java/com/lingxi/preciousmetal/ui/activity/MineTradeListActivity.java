package com.lingxi.preciousmetal.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.ShowTradeDeleteRequestMo;
import com.lingxi.biz.domain.requestMo.ShowTradeListRequestMo;
import com.lingxi.biz.domain.responseMo.BaseMo;
import com.lingxi.biz.domain.responseMo.ShowTradeListMo;
import com.lingxi.biz.service.AnalyseTradeService;
import com.lingxi.common.base.BaseApplication;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.domain.UserInfoBean;
import com.lingxi.preciousmetal.login.LoginHelper;
import com.lingxi.preciousmetal.ui.widget.TopBarView;
import com.lingxi.preciousmetal.ui.widget.dialog.CommonDialog;
import com.lingxi.preciousmetal.util.DisplayUtil;
import com.lingxi.preciousmetal.util.glideutils.GlideUtils;
import com.lingxi.preciousmetal.util.utilCode.StringUtils;
import com.lingxi.preciousmetal.util.utilCode.TimeUtils;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangwei on 2018/4/25.
 */

public class MineTradeListActivity extends BaseListActivity<ShowTradeListMo.SheetsListBean> {
    private QuickAdapter mAdapter;
    UserInfoBean mUserInfoBean;
    private CommonDialog commonDialog;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MineTradeListActivity.class);
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
        ShowTradeListRequestMo liveListRequestMo = new ShowTradeListRequestMo(pageNum + 1, LIMIT, mUserInfoBean.user_id, 2);
        AnalyseTradeService.getShowTradeList(liveListRequestMo, new BizResultListener<ShowTradeListMo>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, ShowTradeListMo showTradeListMo) {

            }

            @Override
            public void onSuccess(ShowTradeListMo showTradeListMo) {
                if (showTradeListMo != null && showTradeListMo.getSheets_list() != null) {
                    showList(showTradeListMo.getSheets_list());
                }
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                showFail(bizMessage);
            }
        });
    }

    private void initView() {
        noDataTips = "空空如也，从这里开始我的晒单之旅吧";
        menu.setVisibility(View.VISIBLE);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowTradeActivity.actionStart(MineTradeListActivity.this);
            }
        });
        initTopBar();
        mAdapter = new QuickAdapter();
        initView(mAdapter);
    }

    int height = DisplayUtil.dip2px(BaseApplication.getInstance(), 290);
    int width = height * 9 / 16;
    String currentIncid;

    public class QuickAdapter extends BaseQuickAdapter<ShowTradeListMo.SheetsListBean, BaseViewHolder> {
        public QuickAdapter() {
            super(R.layout.item_show_trade_mine);
        }

        @Override
        protected void convert(final BaseViewHolder viewHolder, final ShowTradeListMo.SheetsListBean item) {
//            ViewGroup.LayoutParams lp = viewHolder.getView(R.id.iv_image).getLayoutParams();
//            lp.width = width;
//            lp.height = height;
            viewHolder.setText(R.id.tv_like_num, item.getShe_click_num() + "").setText(R.id.title3, item.getShe_heart()).setText(R.id.title2, TimeUtils.millis2String(item.getShe_add_time() * 1000));
//            GlideUtils.loadImageViewLoding(MineTradeListActivity.this, item.getShe_image(), (ImageView) viewHolder.getView(R.id.iv_image),R.drawable.background_gray4,R.drawable.background_gray4);
            GlideUtils.loadImageViewCrop(MineTradeListActivity.this, R.drawable.background_gray4, item.getShe_image(), (ImageView) viewHolder.getView(R.id.iv_image));


            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            viewHolder.getView(R.id.iv_image).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<ShowTradeListMo.SheetsListBean> datas = getData();
                    ArrayList<String> list = new ArrayList<>();
                    for (int i = 0; i < datas.size(); i++) {
                        list.add(datas.get(i).getShe_image());
                    }
                    ViewPagerActivity.actionStart(MineTradeListActivity.this, list, viewHolder.getAdapterPosition());
                }
            });

            viewHolder.getView(R.id.iv_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isFinishing() && !StringUtils.isEmpty(item.getId()) && (commonDialog==null||(commonDialog != null && !commonDialog.isShowing()))) {
                        currentIncid = item.getId();
                        commonDialog = new CommonDialog(MineTradeListActivity.this, "", "确定要删除这个晒单吗？", commitClickListener, 1, "取消", "删除");
                        commonDialog.show();
                    }
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
        ShowTradeDeleteRequestMo liveListRequestMo = new ShowTradeDeleteRequestMo(mUserInfoBean.user_id, currentIncid);
        AnalyseTradeService.deleteShowTrade(liveListRequestMo, new BizResultListener<BaseMo>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, BaseMo baseMo) {

            }

            @Override
            public void onSuccess(BaseMo baseMo) {
                pageNum = 0;
                bindData();
                ToastUtils.showShort("删除成功");
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                showFail(bizMessage);
            }
        });
    }


    public void initTopBar() {
        topbarView.setMode(TopBarView.MODE_BACK);
        topbarView.setTitle("我的晒单");
    }
}

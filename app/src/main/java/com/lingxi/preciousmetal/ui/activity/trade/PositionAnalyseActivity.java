package com.lingxi.preciousmetal.ui.activity.trade;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.tifezh.kchartlib.utils.ViewUtil;
import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.PositionStatisticsRequestMo;
import com.lingxi.biz.domain.responseMo.KMarketResultBean;
import com.lingxi.biz.domain.responseMo.PositionStatisticsBean;
import com.lingxi.biz.service.CommonService;
import com.lingxi.common.util.AppUtil;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;
import com.lingxi.preciousmetal.ui.adapter.AnalyseCftcAdapter;
import com.lingxi.preciousmetal.ui.adapter.PostionAnalyseAdapter;
import com.lingxi.preciousmetal.ui.widget.CustomCircleProgressBar;
import com.lingxi.preciousmetal.ui.widget.MyListView;
import com.lingxi.preciousmetal.ui.widget.TopBarView;
import com.lingxi.preciousmetal.ui.widget.dialog.DialogManager;
import com.lingxi.preciousmetal.util.utilCode.AppUtils;
import com.lingxi.preciousmetal.util.utilCode.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PositionAnalyseActivity extends TranslucentStatusBarActivity {

    @BindView(R.id.analyse_gd_up_color_view)
    CardView analyseGdUpColorView;
    @BindView(R.id.analyse_gd_up_percent_view)
    TextView analyseGdUpPercentView;
    @BindView(R.id.analyse_gd_loss_color_view)
    CardView analyseGdLossColorView;
    @BindView(R.id.analyse_gd_loss_percent_view)
    TextView analyseGdLossPercentView;
    @BindView(R.id.cftc_up_color_view)
    CardView cftcUpColorView;
    @BindView(R.id.cftc_loss_color_view)
    CardView cftcLossColorView;
    @BindView(R.id.goods_buy_listView)
    MyListView goodsBuyListView;
    @BindView(R.id.analyse_sd_up_color_view)
    CardView analyseSdUpColorView;
    @BindView(R.id.analyse_sd_up_percent_view)
    TextView analyseSdUpPercentView;
    @BindView(R.id.analyse_sd_loss_color_view)
    CardView analyseSdLossColorView;
    @BindView(R.id.analyse_sd_loss_percent_view)
    TextView analyseSdLossPercentView;
    @BindView(R.id.analyse_gd_bar)
    CustomCircleProgressBar analyseGdBar;
    @BindView(R.id.cftc_listView)
    MyListView cftcListView;
    @BindView(R.id.analyse_sd_bar)
    CustomCircleProgressBar analyseSdBar;
    @BindView(R.id.radioGroup_indicator_layout)
    RadioGroup radioGroupIndicatorLayout;
    private List<PositionStatisticsBean.CftcDataBean> cftcList;
    private List<PositionStatisticsBean.MerPositionTotalBean> positionTotalBeans;
    private List<PositionStatisticsBean.MerPosition24Bean> position24Beans;
    private AnalyseCftcAdapter mcftcAdapter;
    private PostionAnalyseAdapter nameAdapter;

    public static void startMyActivity(Context activity) {
        Intent intent = new Intent(activity, PositionAnalyseActivity.class);
        activity.startActivity(intent);
    }

    private void initTopBar() {
        TopBarView mTopBarView = findViewById(R.id.topbar_view);
        mTopBarView.setMode(TopBarView.MODE_BACK);
        mTopBarView.setTitle("持仓数据");
    }


    private void loadData() {
        DialogManager.getInstance().showLoadingDialog(this, "", false);

        PositionStatisticsRequestMo requestMo = new PositionStatisticsRequestMo();
        CommonService.getPositionStatistics(requestMo, new BizResultListener<PositionStatisticsBean>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, PositionStatisticsBean resultBean) {

            }

            @Override
            public void onSuccess(PositionStatisticsBean resultBean) {
                if (resultBean!=null){
                    cftcList.addAll(resultBean.getCftc_data());
                    mcftcAdapter.notifyDataSetChanged();
                    nameAdapter.notifyDataSetChanged();

                    positionTotalBeans = resultBean.getMer_position_total();
                    position24Beans = resultBean.getMer_position_24();
                    getDefaultView();
                }
                DialogManager.getInstance().cancellLoadingDialog();
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                LogUtils.i("持仓数据请求失败==");
                DialogManager.getInstance().cancellLoadingDialog();
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_analyse);
        ButterKnife.bind(this);
        initTopBar();
        loadData();
        int upColor = ViewUtil.getKUpColor(mContext);
        int lossColor = ViewUtil.getKLossColor(mContext);

        analyseGdBar.setOutsideColor(lossColor);
        analyseGdBar.setInsideColor(upColor);

        analyseSdBar.setOutsideColor(lossColor);
        analyseSdBar.setInsideColor(upColor);

        analyseGdUpColorView.setCardBackgroundColor(upColor);
        analyseGdUpPercentView.setTextColor(upColor);
        analyseGdLossColorView.setCardBackgroundColor(lossColor);
        analyseGdLossPercentView.setTextColor(lossColor);
        analyseGdUpColorView.setCardElevation(0);
        analyseGdLossColorView.setCardElevation(0);

        AppUtils.setCustomFont(mContext,analyseGdUpPercentView);
        AppUtils.setCustomFont(mContext,analyseGdLossPercentView);
        AppUtils.setCustomFont(mContext,analyseSdUpPercentView);
        AppUtils.setCustomFont(mContext,analyseSdLossPercentView);

        analyseSdUpColorView.setCardBackgroundColor(upColor);
        analyseSdUpPercentView.setTextColor(upColor);
        analyseSdLossColorView.setCardBackgroundColor(lossColor);
        analyseSdLossPercentView.setTextColor(lossColor);
        analyseSdUpColorView.setCardElevation(0);
        analyseSdLossColorView.setCardElevation(0);

        cftcUpColorView.setCardBackgroundColor(upColor);
        cftcLossColorView.setCardBackgroundColor(lossColor);
        cftcUpColorView.setCardElevation(0);
        cftcLossColorView.setCardElevation(0);

        cftcList = new ArrayList();

        nameAdapter = new PostionAnalyseAdapter(mContext, cftcList, R.layout.adapter_postion_analyse);
        goodsBuyListView.setAdapter(nameAdapter);

        mcftcAdapter = new AnalyseCftcAdapter(mContext, cftcList, R.layout.adapter_postion_cftc);
        cftcListView.setAdapter(mcftcAdapter);

        radioGroupIndicatorLayout.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.indicator_oneDay){
                    getDefaultView();
                } else  if (checkedId==R.id.indicator_all){
                    if (positionTotalBeans==null || positionTotalBeans.size()<2){
                        return;
                    }
                    int gdUp = positionTotalBeans.get(0).getAct_more_number();
                    int gdLoss = positionTotalBeans.get(0).getAct_empty_number();
                    analyseGdBar.setProgress(gdLoss);
                    analyseGdUpPercentView.setText(gdUp + "%");
                    analyseGdLossPercentView.setText(gdLoss + "%");

                    int sdUp = positionTotalBeans.get(1).getAct_more_number();
                    int sdLoss = positionTotalBeans.get(1).getAct_empty_number();
                    analyseSdBar.setProgress(sdLoss);
                    analyseSdUpPercentView.setText(sdUp + "%");
                    analyseSdLossPercentView.setText(sdLoss + "%");
                }
            }
        });
    }

    private void getDefaultView(){
        if (position24Beans==null || position24Beans.size()<2){
            return;
        }
        int gdUp = (int)position24Beans.get(0).getAct_more_number();
        int gdLoss =  (int)position24Beans.get(0).getAct_empty_number();
        analyseGdBar.setProgress(gdLoss);
        analyseGdUpPercentView.setText(gdUp + "%");
        analyseGdLossPercentView.setText(gdLoss + "%");

        int sdUp = (int)position24Beans.get(1).getAct_more_number();
        int sdLoss =  (int)position24Beans.get(1).getAct_empty_number();
        analyseSdBar.setProgress(sdLoss);
        analyseSdUpPercentView.setText(sdUp + "%");
        analyseSdLossPercentView.setText(sdLoss + "%");
    }

}

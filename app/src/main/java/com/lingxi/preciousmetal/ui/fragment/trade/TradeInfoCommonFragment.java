package com.lingxi.preciousmetal.ui.fragment.trade;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zhouwei.library.CustomPopWindow;
import com.github.tifezh.kchartlib.utils.ViewUtil;
import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.TAllEntrustRequestMo;
import com.lingxi.biz.domain.requestMo.TMoneyHistoryRequestMo;
import com.lingxi.biz.domain.requestMo.TradeAllPositionRequestMo;
import com.lingxi.biz.domain.responseMo.ClearOrderHistoryBean;
import com.lingxi.biz.domain.responseMo.OrderSucceedBean;
import com.lingxi.biz.domain.responseMo.TradeAllEntrustBean;
import com.lingxi.biz.domain.responseMo.TradeAllPositionBean;
import com.lingxi.biz.domain.responseMo.TradeMoneyHistoryBean;
import com.lingxi.biz.service.TradeService;
import com.lingxi.biz.util.ConstantUtil;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.MyApplication;
import com.lingxi.preciousmetal.ui.activity.trade.SetProfitAndLossActivity;
import com.lingxi.preciousmetal.ui.activity.trade.StartBuyGoldActivity;
import com.lingxi.preciousmetal.ui.adapter.TClearOrderHistoryAdapter;
import com.lingxi.preciousmetal.ui.adapter.TEntrustAdapter;
import com.lingxi.preciousmetal.ui.adapter.TMoneyHistoryAdapter;
import com.lingxi.preciousmetal.ui.adapter.TPositionHistoryAdapter;
import com.lingxi.preciousmetal.ui.adapter.base.CommonAdapter;
import com.lingxi.preciousmetal.ui.widget.dialog.CommonDialog;
import com.lingxi.preciousmetal.ui.widget.dialog.DialogManager;
import com.lingxi.preciousmetal.util.net.FastJsonTools;
import com.lingxi.preciousmetal.util.net.HttpResultListener;
import com.lingxi.preciousmetal.util.net.RequestGet;
import com.lingxi.preciousmetal.util.utilCode.AppUtils;
import com.lingxi.preciousmetal.util.utilCode.ScreenUtils;
import com.lingxi.preciousmetal.util.utilCode.TimeUtils;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/*专家点评
 */
public class TradeInfoCommonFragment extends Fragment implements TPositionHistoryAdapter.InnerItemOnclickListener {

    public static TradeInfoCommonFragment newInstance(String text) {
        TradeInfoCommonFragment fragmentCommon = new TradeInfoCommonFragment();
        Bundle bundle = new Bundle();
        bundle.putString("text", text);
        fragmentCommon.setArguments(bundle);
        return fragmentCommon;
    }

    private CommonAdapter mAdapter;
    private List<TradeMoneyHistoryBean> moneyHistoryList;
    private List<TradeAllPositionBean> positionList;
    private List<TradeAllEntrustBean> entrustList;
    private List<ClearOrderHistoryBean> clearOrderList;
    private ListView trade_info_listview;
    private String type;
    private boolean isFirstLoad;
    RefreshLayout refreshLayout;
    private String moneyStartTime, moneyEndTime, orderStartIme, orderEndTime;
    private CommonDialog commonDialog;
    private View mContentView;

    public void upMoneyDataView(String startTime, String endTime) {
        moneyStartTime = startTime;
        moneyEndTime = endTime;
        initLoadData();
    }

    public void upOrderHistoryDataView(String startTime, String endTime) {
        orderStartIme = startTime;
        orderEndTime = endTime;
        initLoadData();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        trade_info_listview = view.findViewById(R.id.trade_info_listview);
        trade_info_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if ("1".equals(type)) {
                    TradeAllPositionBean bean = positionList.get(position);
                    StartBuyGoldActivity.startMyActivity(getActivity(), 2, bean);
                } else if ("2".equals(type)) {
                    TradeAllEntrustBean bean = entrustList.get(position);
                    StartBuyGoldActivity.startMyActivity(getActivity(), 3, bean);
                }
            }
        });
        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setEnableAutoLoadMore(true);//开启自动加载功能（非必须）
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().post(new Runnable() {
                    @Override
                    public void run() {
                        initLoadData();
                        refreshLayout.finishRefresh();
                        refreshLayout.setNoMoreData(false);
                    }
                });
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().post(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishLoadMore();
                    }
                });
            }
        });
        String endDate = TimeUtils.date2String(new Date(), TimeUtils.DEFAULT_FORMATGMC);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        String startTime = TimeUtils.date2String(calendar.getTime(), TimeUtils.DEFAULT_FORMATGMC);

        moneyStartTime = startTime;
        moneyEndTime = endDate;
        orderStartIme = startTime;
        orderEndTime = endDate;
        isFirstLoad = false;
        type = getArguments().getString("text");
        initLoadData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstLoad) {
            initLoadData();
        }
        isFirstLoad = true;
    }

    private void initLoadData() {
        if ("1".equals(type)) {
            positionList = new ArrayList<>();
            loadPositionsData();
        } else if ("2".equals(type)) {
            entrustList = new ArrayList<>();
            loadEntrustData();
        } else if ("3".equals(type)) {
            clearOrderList = new ArrayList<>();
            requestOrderHistory();
        } else if ("4".equals(type)) {
            moneyHistoryList = new ArrayList<>();
            loadMoneyHistoryData();
        }
    }

    private void requestOrderHistory() {
        //  DialogManager.getInstance().showLoadingDialog(getActivity(), "", false);
        String url = String.format(RequestGet.orderHistory, orderStartIme, orderEndTime);
        RequestGet.requestHttpData(getActivity(), ConstantUtil.GET, url, null, new HttpResultListener() {
            @Override
            public void onSuccess(String data) {
                clearOrderList = FastJsonTools.getPersons(data, ClearOrderHistoryBean.class);
                if (clearOrderList != null && clearOrderList.size() > 0) {
                    Collections.reverse(clearOrderList);
                }
                TClearOrderHistoryAdapter mAdapter = new TClearOrderHistoryAdapter(getActivity(), clearOrderList, R.layout.adapter_clear_order_history);
                trade_info_listview.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                DialogManager.getInstance().cancellLoadingDialog();
            }

            @Override
            public void onFailure(String data) {
                DialogManager.getInstance().cancellLoadingDialog();
            }
        });
    }

    private void loadEntrustData() {
        //DialogManager.getInstance().showLoadingDialog(getActivity(), "", false);
        TAllEntrustRequestMo requestMo = new TAllEntrustRequestMo(ConstantUtil.ACCOUNT_ID);
        TradeService.getAllEntrust(requestMo, new BizResultListener<List<TradeAllEntrustBean>>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, List<TradeAllEntrustBean> bean) {

            }

            @Override
            public void onSuccess(List<TradeAllEntrustBean> listBean) {
                entrustList = listBean;
                mAdapter = new TEntrustAdapter(getActivity(), entrustList, R.layout.adapter_trade_entryorders_new);
                trade_info_listview.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                DialogManager.getInstance().cancellLoadingDialog();
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                ToastUtils.showShort(bizMessage);
                DialogManager.getInstance().cancellLoadingDialog();
            }
        });
    }

    private void loadPositionsData() {
        //   DialogManager.getInstance().showLoadingDialog(getActivity(), "", false);
        TradeAllPositionRequestMo requestMo = new TradeAllPositionRequestMo(ConstantUtil.ACCOUNT_ID);
        TradeService.getAllPosition(requestMo, new BizResultListener<List<TradeAllPositionBean>>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, List<TradeAllPositionBean> bean) {

            }

            @Override
            public void onSuccess(List<TradeAllPositionBean> listBean) {
                positionList = listBean;
                TPositionHistoryAdapter mAdapter = new TPositionHistoryAdapter(getActivity(), positionList, R.layout.adapter_trade_positions_new);
                trade_info_listview.setAdapter(mAdapter);
                mAdapter.setOnInnerItemOnClickListener(TradeInfoCommonFragment.this);
                mAdapter.notifyDataSetChanged();
                //      DialogManager.getInstance().cancellLoadingDialog();
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                ToastUtils.showShort(bizMessage);
                //   DialogManager.getInstance().cancellLoadingDialog();
            }
        });
    }

    private void loadMoneyHistoryData() {
        //   DialogManager.getInstance().showLoadingDialog(getActivity(), "", false);
        TMoneyHistoryRequestMo requestMo = new TMoneyHistoryRequestMo(ConstantUtil.ACCOUNT_ID, moneyStartTime, moneyEndTime);
        TradeService.getMoneyHistory(requestMo, new BizResultListener<List<TradeMoneyHistoryBean>>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, List<TradeMoneyHistoryBean> bean) {

            }

            @Override
            public void onSuccess(List<TradeMoneyHistoryBean> listBean) {
                moneyHistoryList = listBean;
                if (listBean != null && listBean.size() > 0) {
                    Collections.reverse(moneyHistoryList);
//                    Collections.sort(moneyHistoryList);
                    mAdapter = new TMoneyHistoryAdapter(getActivity(), moneyHistoryList, R.layout.adapter_trade_money_history);
                    trade_info_listview.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
                DialogManager.getInstance().cancellLoadingDialog();
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                ToastUtils.showShort(bizMessage);
                DialogManager.getInstance().cancellLoadingDialog();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mContentView == null) {
            mContentView = inflater.inflate(R.layout.fragment_info_trade_com, container, false);
        }
        return mContentView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void itemClick(View v) {
        TradeAllPositionBean bean = (TradeAllPositionBean) v.getTag();
        if (isAdded() && !isHidden() && (commonDialog == null || (commonDialog != null && !commonDialog.isShowing()))) {
            commonDialog = new CommonDialog(getContext(), "", "确定平仓？", commitClickListener, 1, "取消", "确定", bean,2);
            commonDialog.show();
        }
    }

    private CommonDialog.CommitClickListener commitClickListener = new CommonDialog.CommitClickListener() {
        @Override
        public void click(int whichDialog, Object object) {
            switch (whichDialog) {
                case 1:
                    TradeAllPositionBean bean = (TradeAllPositionBean) object;
                    requestClosePosition(bean);
                    break;
            }
        }
    };

    private void requestClosePosition(final TradeAllPositionBean bean) {
        // DialogManager.getInstance().showLoadingDialog(getActivity(), "", false);
        String url = String.format(RequestGet.closePosition, bean.getPositionId(), bean.getVolume());
        RequestGet.requestHttpData(getActivity(), ConstantUtil.DELETE, url, null, new HttpResultListener() {
            @Override
            public void onSuccess(String data) {
                DialogManager.getInstance().cancellLoadingDialog();
                windowClosePosition(bean);
                loadPositionsData();
            }

            @Override
            public void onFailure(String data) {
                DialogManager.getInstance().cancellLoadingDialog();
            }
        });
    }

    public void initTradeWindow(final OrderSucceedBean bean1) {
//        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.window_trade_succeed,trade_info_listview );
        filterPopWindow = new CustomPopWindow.PopupWindowBuilder(getActivity())
                .setView(R.layout.window_trade_succeed).size(ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight())
                .create().showAsDropDown(trade_info_listview);
//        TextView order_id_View = contentView.findViewById(R.id.order_id_View);
//        TextView order_price_View = contentView.findViewById(R.id.order_price_View);
//        TextView order_vol_View = contentView.findViewById(R.id.order_vol_View);
//        TextView order_margin_View = contentView.findViewById(R.id.order_margin_View);
//        TextView order_profit_price_View = contentView.findViewById(R.id.order_profit_price_View);
//        TextView order_loss_price_View = contentView.findViewById(R.id.order_loss_price_View);
//
//        order_id_View.setText("#"  );
//        order_price_View.setText(String.valueOf(1));
//        order_vol_View.setText(String.valueOf(1));
//        order_profit_price_View.setText(ConstantUtil.SHOWDEFVALUE);
//        order_loss_price_View.setText(ConstantUtil.SHOWDEFVALUE);
//        order_margin_View.setText(ConstantUtil.SHOWDEFVALUE);
//
//        contentView.findViewById(R.id.set_profit_loss_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                filterPopWindow.dissmiss();
////                SetProfitAndLossActivity.startMyActivity(getActivity(), 1, bean, getNowPrice(), orderDirection,goodsName,handNumber);
//            }
//        });
//        contentView.findViewById(R.id.my_trade_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                filterPopWindow.dissmiss();
//                getActivity().finish();
//            }
//        });
//        contentView.findViewById(R.id.w_close_succeed).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                filterPopWindow.dissmiss();
//            }
//        });
    }


    private CustomPopWindow filterPopWindow;

    public void windowClosePosition(TradeAllPositionBean bean) {
        if (isAdded() && !isHidden() && (commonDialog == null || (commonDialog != null && !commonDialog.isShowing()))) {
            commonDialog = new CommonDialog(getContext(), "", "平仓成功", commitClickListener, 3, "取消", "确定", bean,3);
            commonDialog.show();
        }
    }

}

package com.lingxi.preciousmetal.ui.fragment.trade;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhouwei.library.CustomPopWindow;
import com.github.tifezh.kchartlib.utils.ViewUtil;
import com.lingxi.biz.domain.responseMo.TradeAllEntrustBean;
import com.lingxi.biz.domain.responseMo.TradeAllPositionBean;
import com.lingxi.biz.util.ConstantUtil;
import com.lingxi.common.base.BaseFragment;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.ui.activity.trade.SetProfitAndLossActivity;
import com.lingxi.preciousmetal.ui.widget.dialog.DialogManager;
import com.lingxi.preciousmetal.util.net.HttpResultListener;
import com.lingxi.preciousmetal.util.net.RequestGet;
import com.lingxi.preciousmetal.util.utilCode.AppUtils;
import com.lingxi.preciousmetal.util.utilCode.Arith;
import com.lingxi.preciousmetal.util.utilCode.ScreenUtils;
import com.lingxi.preciousmetal.util.utilCode.StringUtils;
import com.lingxi.preciousmetal.util.utilCode.TimeUtils;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by zhangwei on 2018/4/26.
 */

public class OrderDetailFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.order_pnl_View)
    TextView orderPnlView;
    @BindView(R.id.order_swap_View)
    TextView orderSwapView;
    @BindView(R.id.order_profit_loss_layout)
    LinearLayout orderProfitLossLayout;
    @BindView(R.id.order_id_View)
    TextView orderIdView;
    @BindView(R.id.order_time_View)
    TextView orderTimeView;
    @BindView(R.id.order_price_View)
    TextView orderPriceView;
    @BindView(R.id.order_now_price_View)
    TextView orderNowPriceView;
    @BindView(R.id.order_volume_View)
    TextView orderVolumeView;
    @BindView(R.id.order_marginRate_View)
    TextView orderMarginRateView;
    @BindView(R.id.order_takeProfit_View)
    TextView orderTakeProfitView;
    @BindView(R.id.order_stopLoss_View)
    TextView orderStopLossView;
    @BindView(R.id.set_profit_loss_btn)
    TextView setProfitLossBtn;
    @BindView(R.id.order_btn)
    TextView orderBtn;
    @BindView(R.id.trade_detail_text1)
    TextView tradeDetailText1;
    @BindView(R.id.order_price_View1)
    TextView orderPriceView1;

    private int type;
    private ImageView price_minus_btn, price_plus_btn;
    private TextView entrust_volume_textView;
    private View entrust_volume_view;
    private EditText price_input_edtext;
    private TradeAllPositionBean positionBean = null;
    private TradeAllEntrustBean closePositionBean;
    private double inputVol;

    public static OrderDetailFragment newInstance(int type) {
        OrderDetailFragment tradeFragment = new OrderDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        tradeFragment.setArguments(bundle);
        return tradeFragment;
    }

    public static OrderDetailFragment newInstance(int type, TradeAllEntrustBean bean) {
        OrderDetailFragment tradeFragment = new OrderDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putSerializable("positionBean", bean);
        tradeFragment.setArguments(bundle);
        return tradeFragment;
    }

    public static OrderDetailFragment newInstance(int type, TradeAllPositionBean bean) {
        OrderDetailFragment tradeFragment = new OrderDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putSerializable("positionBean", bean);
        tradeFragment.setArguments(bundle);
        return tradeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        entrust_volume_textView = view.findViewById(R.id.entrust_volume_textView);
        entrust_volume_view = view.findViewById(R.id.entrust_volume_view);
        price_minus_btn = entrust_volume_view.findViewById(R.id.hand_minus_btn);
        price_plus_btn = entrust_volume_view.findViewById(R.id.hand_plus_btn);
        price_input_edtext = entrust_volume_view.findViewById(R.id.hand_input_edText);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        type = getArguments().getInt("type", 1);
        double stopLoss = 0, takeProfit = 0, openPrice = 0, marginRate = 0;
        String orderTime = "";

        if (type == 1) {
            orderProfitLossLayout.setVisibility(View.VISIBLE);
            positionBean = (TradeAllPositionBean) getArguments().getSerializable("positionBean");
            orderId = positionBean.getPositionId();
            volume = positionBean.getVolume();
            takeProfit = positionBean.getTakeProfit();
            stopLoss = positionBean.getStopLoss();
            openPrice = positionBean.getOpenPrice();
            orderTime = TimeUtils.stringToMDTZ(positionBean.getOpenTime(), TimeUtils.FMDHMS);
            marginRate = positionBean.getMaintenanceMargin();
            orderMarginRateView.setText(String.valueOf(marginRate));

            closePositionBean = new TradeAllEntrustBean();//用于设置止盈 赋值
            closePositionBean.setStopLoss(stopLoss);
            closePositionBean.setTakeProfit(takeProfit);
            closePositionBean.setOrderPrice(openPrice);
            closePositionBean.setOrderId(orderId);

            double pnl = positionBean.getPnl();
            if (pnl > 0) {
                orderPnlView.setTextColor(ViewUtil.getKUpColor(mContext));
                orderPnlView.setText("+" + pnl);
            } else {
                orderPnlView.setTextColor(ViewUtil.getKLossColor(mContext));
                orderPnlView.setText(String.valueOf(pnl));
            }
            orderSwapView.setText("包含利息" + positionBean.getSwap());
            price_input_edtext.setText(String.valueOf(volume));

            orderBtn.setText("平仓");
            price_minus_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String vol = price_input_edtext.getText().toString();
                    if (StringUtils.isEmpty(vol)) {
                        ToastUtils.showShort("请输入正确交易的手数");
                        return;
                    }
                    inputVol = Double.parseDouble(vol);
                    inputVol = Arith.sub(inputVol, 0.01);
                    if (inputVol <= 0) {
                        ToastUtils.showShort("请输入正确的交易手数");
                        inputVol = 0.01;
                    }
                    price_input_edtext.setText(String.valueOf(inputVol));
                }
            });
            price_plus_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String vol = price_input_edtext.getText().toString();
                    if (StringUtils.isEmpty(vol)) {
                        ToastUtils.showShort("请输入正确的交易手数");
                        return;
                    }
                    inputVol = Double.parseDouble(vol);
                    inputVol = Arith.add(inputVol, 0.01);
                    if (inputVol > volume) {
                        ToastUtils.showShort("请输入正确的交易手数");
                        inputVol = volume;
                    }
                    price_input_edtext.setText(String.valueOf(inputVol));
                }
            });
            tradeDetailText1.setText("建仓时间");
            orderPriceView1.setText("建仓价格");
        } else if (type == 2) {
            closePositionBean = (TradeAllEntrustBean) getArguments().getSerializable("positionBean");
            orderProfitLossLayout.setVisibility(View.GONE);
            entrust_volume_view.setVisibility(View.GONE);
            entrust_volume_textView.setVisibility(View.GONE);
            orderBtn.setText("撤单");

            orderId = closePositionBean.getOrderId();
            volume = closePositionBean.getVolume();
            takeProfit = closePositionBean.getTakeProfit();
            stopLoss = closePositionBean.getStopLoss();
            openPrice = closePositionBean.getOrderPrice();
            orderTime = TimeUtils.stringToMDTZ(closePositionBean.getDateTime(), TimeUtils.FMDHMS);
            orderMarginRateView.setText(ConstantUtil.SHOWDEFVALUE);
        }
        orderTimeView.setText(orderTime);
        String openPriceStr = String.format("%.2f", openPrice);
        orderPriceView.setText(openPriceStr);
        if (takeProfit > 0) {
            orderTakeProfitView.setText(String.valueOf(takeProfit));
        } else {
            orderTakeProfitView.setText(ConstantUtil.SHOWDEFVALUE);
        }
        if (stopLoss > 0) {
            orderStopLossView.setText(String.valueOf(stopLoss));
        } else {
            orderTakeProfitView.setText(ConstantUtil.SHOWDEFVALUE);
        }
        orderNowPriceView.setText(ConstantUtil.SHOWDEFVALUE);
        orderVolumeView.setText(volume + "手");
        orderIdView.setText("#" + orderId);
    }

    private void initView() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.set_profit_loss_btn, R.id.order_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.set_profit_loss_btn:
                if (type == 1) {
                    if (0 == positionBean.getCmd()) {
                        SetProfitAndLossActivity.startMyActivity(getContext(), 2, closePositionBean, positionBean.getClosePrice(), "Buy",positionBean.getSymbol(),positionBean.getVolume());
                    } else if (1 == positionBean.getCmd()) {
                        SetProfitAndLossActivity.startMyActivity(getContext(), 2, closePositionBean, positionBean.getClosePrice(), "Sell",positionBean.getSymbol(),positionBean.getVolume());
                    }
                } else if (type == 2) {
                    if (2 == positionBean.getCmd()) {
                        SetProfitAndLossActivity.startMyActivity(getContext(), 2, closePositionBean, positionBean.getClosePrice(), "Buy",positionBean.getSymbol(),positionBean.getVolume());
                    } else if (3 == positionBean.getCmd()) {
                        SetProfitAndLossActivity.startMyActivity(getContext(), 2, closePositionBean, positionBean.getClosePrice(), "Sell",positionBean.getSymbol(),positionBean.getVolume());
                    }
                }
                break;
            case R.id.order_btn:
                if (type == 1) {
                    String vol = price_input_edtext.getText().toString();
                    if (StringUtils.isEmpty(vol)) {
                        ToastUtils.showShort("请输入需要交易的手数");
                        return;
                    }
                    inputVol = Double.parseDouble(vol);
                    if (inputVol <= 0) {
                        ToastUtils.showShort("请输入正确的交易手数");
                        return;
                    }
                    if (inputVol > volume) {
                        ToastUtils.showShort("您输入的手数大于订单的手数");
                        return;
                    }
                    requestClosePositionData();
                } else {
                    requestCloseEntrustData();
                }
                break;
        }
    }

    private int orderId;
    private double volume;

    private void requestClosePositionData() {
        DialogManager.getInstance().showLoadingDialog(getActivity(), "", false);
        String url = String.format(RequestGet.closePosition, orderId, inputVol);
        RequestGet.requestHttpData(getActivity(), ConstantUtil.DELETE, url, null, new HttpResultListener() {
            @Override
            public void onSuccess(String data) {
                DialogManager.getInstance().cancellLoadingDialog();
                initTradeWindow();
            }

            @Override
            public void onFailure(String data) {
                DialogManager.getInstance().cancellLoadingDialog();
            }
        });
    }

    private void requestCloseEntrustData() {
        // 取消委托单
        DialogManager.getInstance().showLoadingDialog(getActivity(), "", false);
        String url = String.format(RequestGet.closeEntrustOrder, orderId);
        RequestGet.requestHttpData(getActivity(), ConstantUtil.DELETE, url, null, new HttpResultListener() {
            @Override
            public void onSuccess(String data) {
                ToastUtils.showShort("订单撤销成功");
                mContext.finish();
            }

            @Override
            public void onFailure(String data) {
                DialogManager.getInstance().cancellLoadingDialog();
            }
        });
    }

    private CustomPopWindow filterPopWindow;

    public void initTradeWindow() {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.window_close_position, null);
        filterPopWindow = new CustomPopWindow.PopupWindowBuilder(getActivity())
                .setView(contentView).size(ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight())
                .create().showAsDropDown(orderBtn);
        contentView.findViewById(R.id.w_sure_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterPopWindow.dissmiss();
            }
        });
        TextView w_order_price_View = contentView.findViewById(R.id.w_order_price_View);
        TextView w_order_now_price_View = contentView.findViewById(R.id.w_order_now_price_View);
        TextView w_order_profit_View = contentView.findViewById(R.id.w_order_profit_View);
        AppUtils.setCustomFont(getActivity(), w_order_price_View);
        AppUtils.setCustomFont(getActivity(), w_order_now_price_View);
        AppUtils.setCustomFont(getActivity(), w_order_profit_View);

        w_order_price_View.setText(String.valueOf(positionBean.getOpenPrice()));
        w_order_now_price_View.setText(ConstantUtil.SHOWDEFVALUE);
        double pnl = positionBean.getPnl();
        if (pnl > 0) {
            w_order_profit_View.setTextColor(ViewUtil.getKUpColor(mContext));
            w_order_profit_View.setText("+" + pnl);
        } else {
            w_order_profit_View.setTextColor(ViewUtil.getKLossColor(mContext));
            w_order_profit_View.setText(String.valueOf(pnl));
        }
        contentView.findViewById(R.id.w_sure_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                filterPopWindow.dissmiss();
            }
        });

    }
}

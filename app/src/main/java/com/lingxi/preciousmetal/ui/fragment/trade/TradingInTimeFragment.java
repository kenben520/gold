package com.lingxi.preciousmetal.ui.fragment.trade;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.zhouwei.library.CustomPopWindow;
import com.github.tifezh.kchartlib.utils.ViewUtil;
import com.gxz.PagerSlidingTabStrip;
import com.lingxi.biz.domain.responseMo.AccountInfoMo;
import com.lingxi.biz.domain.responseMo.KMarketResultBean;
import com.lingxi.biz.domain.responseMo.OrderSucceedBean;
import com.lingxi.biz.domain.responseMo.TradeAllPositionBean;
import com.lingxi.biz.util.ConstantUtil;
import com.lingxi.common.base.BaseFragment;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.MyApplication;
import com.lingxi.preciousmetal.common.EventBusKeyDefine;
import com.lingxi.preciousmetal.login.LoginHelper;
import com.lingxi.preciousmetal.ui.activity.GoldMainActivity;
import com.lingxi.preciousmetal.ui.activity.trade.SetProfitAndLossActivity;
import com.lingxi.preciousmetal.ui.widget.dialog.CommonDialog;
import com.lingxi.preciousmetal.ui.widget.dialog.DialogManager;
import com.lingxi.preciousmetal.util.VerifyUtil;
import com.lingxi.preciousmetal.util.net.FastJsonTools;
import com.lingxi.preciousmetal.util.net.HttpResultListener;
import com.lingxi.preciousmetal.util.net.RequestGet;
import com.lingxi.preciousmetal.util.utilCode.ActivityUtils;
import com.lingxi.preciousmetal.util.utilCode.AppUtils;
import com.lingxi.preciousmetal.util.utilCode.AppViewUtils;
import com.lingxi.preciousmetal.util.utilCode.Arith;
import com.lingxi.preciousmetal.util.utilCode.ObjectUtils;
import com.lingxi.preciousmetal.util.utilCode.ScreenUtils;
import com.lingxi.preciousmetal.util.utilCode.StringUtils;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 市价交易
 */
public class TradingInTimeFragment extends BaseFragment {
    @BindView(R.id.hand_minus_btn)
    ImageView handMinusBtn;
    @BindView(R.id.hand_input_edText)
    EditText handInputEdText;
    @BindView(R.id.hand_plus_btn)
    ImageView handPlusBtn;
    @BindView(R.id.hand_RadioGroup)
    RadioGroup handRadioGroup;
    Unbinder unbinder;
    @BindView(R.id.trade_buy_btn)
    TextView tradeBuyBtn;
    @BindView(R.id.trade_sell_btn)
    TextView tradeSellBtn;
    @BindView(R.id.tv_deposit_evaluate)
    TextView tvDepositEvaluate;
    @BindView(R.id.tv_deposit_available)
    TextView tvDepositAvailable;
    private PagerSlidingTabStrip tabs;
    private ViewPager pager;

    private double handNumber;
    private double handType;
    private String orderDirection;
    private AccountInfoMo accountInfoMo;
    String goodsName = ConstantUtil.XAUUSD;

    public static TradingInTimeFragment newInstance(String goodsCode) {
        TradingInTimeFragment tradeFragment = new TradingInTimeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("p_code", goodsCode);
        tradeFragment.setArguments(bundle);
        return tradeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trade_in_time, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
        registorEventBus();
        Bundle bundle = getArguments();
        goodsName = bundle.getString("p_code");
        initView(root);
        initData();
    }

    public void setNowPrice(float price, String goods) {
        if (tradeBuyBtn != null) {
            String format = "";
            float dian = 0.f;
            if ((ConstantUtil.XAUUSD.equals(goods))) {
                dian = 0.5f;
                format = "%.2f";
            } else {
                dian = 0.04f;
                format = "%.3f";
            }
            tradeBuyBtn.setText(getString(R.string.kBuy) + String.format(format, price + dian));
            tradeSellBtn.setText(getString(R.string.kSell) + String.format(format, price));
        }
    }

    private void initData() {
//        goodsName=
        accountInfoMo = LoginHelper.getInstance().getAccountInfo();
        handNumber = 0.01;
        handType = 0.01;
        orderDirection = "Buy";
        handInputEdText.setText(String.valueOf(handNumber));
        refreshDepositEvaluateView(handNumber);
        if (!ObjectUtils.isEmpty(accountInfoMo)) {
            tvDepositAvailable.setText("可用保证金：$" + String.format("%.2f", accountInfoMo.getMarginAvailable()));
        }
    }

    private void initView(View View) {
        tradeBuyBtn.setBackgroundColor(ViewUtil.getKUpColor(mContext));
        tradeSellBtn.setBackgroundColor(ViewUtil.getKLossColor(mContext));
        handRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.hand_one_radio) {
                    handType = 0.01;
                } else if (checkedId == R.id.hand_two_radio) {
                    handType = 0.1;
                } else if (checkedId == R.id.hand_three_radio) {
                    handType = 0.5;
                } else if (checkedId == R.id.hand_four_radio) {
                    handType = 1;
                }
                handNumber = handType;
                handInputEdText.setText(String.valueOf(handType));
            }
        });
        handInputEdText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String moneyUsdStr = s.toString();
                if (!TextUtils.isEmpty(moneyUsdStr)) {
                    if (VerifyUtil.isNumeric(moneyUsdStr)) {
                        Double moneyUsd = Double.parseDouble(moneyUsdStr);
                        refreshDepositEvaluateView(moneyUsd);
                    }
                } else {
                    refreshDepositEvaluateView(0.00);
                }
            }
        });
    }

    private double getPerHand() {
        return 100000 / 100;
    }
    private double depositEvaluate;
    private void refreshDepositEvaluateView(Double moneyUsd) {
        depositEvaluate=moneyUsd * getPerHand();
        tvDepositEvaluate.setText("参考保证金：$" + String.format("%.2f", moneyUsd * getPerHand()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unregistorEventBus();
        unbinder.unbind();
    }

    @OnClick({R.id.hand_minus_btn, R.id.hand_plus_btn, R.id.trade_buy_btn, R.id.trade_sell_btn})
    public void onViewClicked(View view) {
        String inputHand = handInputEdText.getText().toString();
        if (StringUtils.isEmpty(inputHand) || !VerifyUtil.isNumeric(inputHand)) {
            ToastUtils.showShort("请输入正确的交易手数");
            return;
        } else {
            handNumber = Double.parseDouble(handInputEdText.getText().toString());
        }

        switch (view.getId()) {
            case R.id.hand_minus_btn:
                handNumber = Arith.sub(handNumber, handType);
                showHandView();
                break;
            case R.id.hand_plus_btn:
                handNumber = Arith.add(handNumber, handType);
                showHandView();
                break;
            case R.id.trade_buy_btn:
                orderDirection = "Buy";
                requestAddTrade();
                break;
            case R.id.trade_sell_btn:
                orderDirection = "Sell";
                requestAddTrade();
                break;
        }
    }

    private void requestAddTrade() {
        Map<String, Object> map = new HashMap<>();
        map.put("takeProfit", 0);
        map.put("stopLoss", 0);
        map.put("orderPrice", 0);
        map.put("orderType", "Market");
//        map.put("orderDirection","Buy");
        map.put("orderDirection", orderDirection);
        map.put("volume", handNumber);
        map.put("symbol", goodsName);
        Map<String, Object> mapBean = new HashMap<>();
        mapBean.put("order", map);
        DialogManager.getInstance().showLoadingDialog(getActivity(), "", false);
        RequestGet.requestHttpData(getActivity(), ConstantUtil.POST, RequestGet.addTrade, mapBean, new HttpResultListener() {
            @Override
            public void onSuccess(String data) {
                DialogManager.getInstance().cancellLoadingDialog();
                OrderSucceedBean bean = FastJsonTools.getPerson(data, OrderSucceedBean.class);
                showTradeSucceedDialog(bean);
            }

            @Override
            public void onFailure(String data) {
                DialogManager.getInstance().cancellLoadingDialog();
            }
        });
    }

    private CommonDialog commonDialog;
    public void showTradeSucceedDialog(final OrderSucceedBean bean) {
        if (isAdded() && !isHidden() && (commonDialog == null || (commonDialog != null && !commonDialog.isShowing()))) {
            commonDialog = new CommonDialog(getContext(), new CommonDialog.TradeSucceedListener() {
                @Override
                public void watchPosition() {
                    getActivity().finish();
                    GoldMainActivity.actionStart(mContext,1);
//                    ActivityUtils.finishActivity(GoldMainActivity.class);
//                    ActivityUtils.startActivity(GoldMainActivity.class);
//                    getActivity().finish();
                }

                @Override
                public void setProfitAndLoss() {
                    SetProfitAndLossActivity.startMyActivity(getActivity(), 1, bean, getNowPrice(), orderDirection,goodsName,handNumber);
                }
            }, bean, handNumber,depositEvaluate,goodsName);
            commonDialog.show();
        }
    }


    private CustomPopWindow filterPopWindow;

    public void initTradeWindow(final OrderSucceedBean bean) {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.window_trade_succeed, null);
        filterPopWindow = new CustomPopWindow.PopupWindowBuilder(getActivity())
                .setView(contentView).size(ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight())
                .create().showAsDropDown(handInputEdText);
        TextView order_id_View = contentView.findViewById(R.id.order_id_View);
        TextView order_price_View = contentView.findViewById(R.id.order_price_View);
        TextView order_vol_View = contentView.findViewById(R.id.order_vol_View);
        TextView order_margin_View = contentView.findViewById(R.id.order_margin_View);
        TextView order_profit_price_View = contentView.findViewById(R.id.order_profit_price_View);
        TextView order_loss_price_View = contentView.findViewById(R.id.order_loss_price_View);

        order_id_View.setText("#" + bean.getOrderId());
        order_price_View.setText(String.valueOf(bean.getOrderPrice()));
        order_vol_View.setText(String.valueOf(handNumber));
        order_profit_price_View.setText(ConstantUtil.SHOWDEFVALUE);
        order_loss_price_View.setText(ConstantUtil.SHOWDEFVALUE);
        order_margin_View.setText(ConstantUtil.SHOWDEFVALUE);

        contentView.findViewById(R.id.set_profit_loss_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterPopWindow.dissmiss();
                SetProfitAndLossActivity.startMyActivity(getActivity(), 1, bean, getNowPrice(), orderDirection,goodsName,handNumber);
            }
        });
        contentView.findViewById(R.id.my_trade_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterPopWindow.dissmiss();
                getActivity().finish();
            }
        });
        contentView.findViewById(R.id.w_close_succeed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterPopWindow.dissmiss();
            }
        });
    }

    private double getNowPrice() {
        List<KMarketResultBean.MarketBean> marketList = MyApplication.marketBeanList;
        KMarketResultBean.MarketBean marketBean = null;
        if (ConstantUtil.XAUUSD.equals(goodsName)) {
            if (marketList != null && marketList.size() >= 3) {
                marketBean = marketList.get(0);
            }
        } else if (ConstantUtil.XAGUSD.equals(goodsName)) {
            if (marketList != null && marketList.size() >= 3) {
                marketBean = marketList.get(1);
            }
        }
        if (marketBean != null) {
            return marketBean.last_px;
        } else {
            return 0;
        }
    }

    private void showHandView() {
        if (handNumber <= 0) {
            ToastUtils.showShort("请输入正确的交易手数");
            handNumber = 0.01;
        } else if (handNumber >= 30) {
            ToastUtils.showShort("请输入正确的交易手数");
            handNumber = 30;
        }
        handInputEdText.setText(String.valueOf(handNumber));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThrea(EventBusKeyDefine.EventBusMsgData<String> data) {
        int type = data.getType();
        try {
            if (EventBusKeyDefine.EVENTBUS_SWITCH_GOODS_NAME == type) {
                if (!ObjectUtils.isEmpty(data) && !ObjectUtils.isEmpty(data.getData())) {
                    String goodName = data.getData();
                    goodsName = goodName;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

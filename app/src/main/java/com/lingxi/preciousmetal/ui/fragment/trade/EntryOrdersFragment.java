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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.zhouwei.library.CustomPopWindow;
import com.gxz.PagerSlidingTabStrip;
import com.lingxi.biz.domain.responseMo.AccountInfoMo;
import com.lingxi.biz.domain.responseMo.KMarketResultBean;
import com.lingxi.biz.domain.responseMo.OrderSucceedBean;
import com.lingxi.biz.util.ConstantUtil;
import com.lingxi.common.base.BaseApplication;
import com.lingxi.common.base.BaseFragment;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.MyApplication;
import com.lingxi.preciousmetal.common.EventBusKeyDefine;
import com.lingxi.preciousmetal.login.LoginHelper;
import com.lingxi.preciousmetal.ui.activity.trade.SetProfitAndLossActivity;
import com.lingxi.preciousmetal.ui.widget.dialog.CommonDialog;
import com.lingxi.preciousmetal.ui.widget.dialog.DialogManager;
import com.lingxi.preciousmetal.util.VerifyUtil;
import com.lingxi.preciousmetal.util.net.FastJsonTools;
import com.lingxi.preciousmetal.util.net.HttpResultListener;
import com.lingxi.preciousmetal.util.net.RequestGet;
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
import butterknife.Unbinder;
/**
 * 挂单交易
 */
public class EntryOrdersFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.tv_deposit_tips)
    TextView tvDepositTips;
    Unbinder unbinder;
    @BindView(R.id.tv_rang_price_tips)
    TextView tvRangPriceTips;
    @BindView(R.id.buy_radio)
    RadioButton buyRadio;
    @BindView(R.id.sell_radio)
    RadioButton sellRadio;
    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private View entrust_volume_view, entrust_price_view;
    private ImageView hand_minus_btn, hand_plus_btn;
    private ImageView price_minus_btn, price_plus_btn;
    private EditText hand_input_edText, pric_input_edtext;
    private TextView entry_order_sure_btn;
    private double orderPrice, orderVol;
    private String orderType, orderDirection;
    private RadioGroup entry_order_group, direction_RadioGroup;
    private double handType = 0.1;
    private AccountInfoMo accountInfoMo;
    String goodsName = ConstantUtil.XAUUSD;

    public static EntryOrdersFragment newInstance(String goodsCode) {
        EntryOrdersFragment tradeFragment = new EntryOrdersFragment();
        Bundle bundle = new Bundle();
        bundle.putString("p_code", goodsCode);
        tradeFragment.setArguments(bundle);
        return tradeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trade_entry_orders, container, false);
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

    private void initData() {
        accountInfoMo = LoginHelper.getInstance().getAccountInfo();
        orderType = "DayOrder";
        orderDirection = "Sell";
        orderVol = 0.1;
        hand_input_edText.setText(String.valueOf(orderVol));
        entry_order_sure_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String volStr = hand_input_edText.getText().toString();
                if (StringUtils.isEmpty(volStr) || !VerifyUtil.isNumeric(volStr)) {
                    ToastUtils.showShort("请输入挂单手数");
                    return;
                }
                String price = pric_input_edtext.getText().toString();
                if (StringUtils.isEmpty(price) || !VerifyUtil.isNumeric(price)) {
                    ToastUtils.showShort("请输入挂单价格");
                    return;
                }
                orderPrice = Double.parseDouble(price);
                orderVol = Double.parseDouble(volStr);
                requestAddTrade();
            }
        });

        entry_order_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (R.id.today_radio_btn == checkedId) {
                    orderType = "DayOrder";
                } else if (R.id.week_radio_btn == checkedId) {
                    orderType = "GoodTillCancel";
                }
            }
        });

        direction_RadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (R.id.buy_radio == checkedId) {
                    orderDirection = "Buy";
                    showRangPriceTipsView();
                } else if (R.id.sell_radio == checkedId) {
                    orderDirection = "Sell";
                    showRangPriceTipsView();
                }
            }
        });
        refreshPriceInputView();
        showRangPriceTipsView();
        refreshDepositTipsView(orderVol);
    }

    private double getPerHand() {
        return 100000 / 100;
    }

    /**
     * orderVol手数
     *
     * @param orderVol
     */
    private void refreshDepositTipsView(Double orderVol) {
        StringBuilder sb = new StringBuilder();
        sb.append("占用保证金($):");
        sb.append(String.format("%.2f", orderVol * getPerHand()));
        sb.append("  ");
        sb.append("可用保证金($):");
        if (!ObjectUtils.isEmpty(accountInfoMo)) {
            sb.append(String.format("%.2f", accountInfoMo.getMarginAvailable()));
        } else {
            sb.append("0.00");
        }
        tvDepositTips.setText(sb.toString());
    }


    private void requestAddTrade() {
        Map<String, Object> map = new HashMap<>();
        map.put("takeProfit", 0);
        map.put("stopLoss", 0);
        map.put("orderPrice", orderPrice);
        map.put("orderType", "Limit");
        map.put("orderDuration", orderType);
        map.put("orderDirection", orderDirection);
        map.put("volume", orderVol);
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
                }

                @Override
                public void setProfitAndLoss() {
                    SetProfitAndLossActivity.startMyActivity(getActivity(), 1, bean, getNowPrice(), orderDirection, goodsName, orderVol);
                }
            }, bean, orderVol,0,goodsName);
            commonDialog.show();
        }
    }

    private CustomPopWindow filterPopWindow;

    public void initTradeWindow(final OrderSucceedBean bean) {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.window_trade_succeed, null);
        filterPopWindow = new CustomPopWindow.PopupWindowBuilder(getActivity())
                .setView(contentView).size(ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight())
                .create().showAsDropDown(entry_order_group);
        TextView order_id_View = contentView.findViewById(R.id.order_id_View);
        TextView order_price_View = contentView.findViewById(R.id.order_price_View);
        TextView order_vol_View = contentView.findViewById(R.id.order_vol_View);
        TextView order_margin_View = contentView.findViewById(R.id.order_margin_View);
        TextView order_profit_price_View = contentView.findViewById(R.id.order_profit_price_View);
        TextView order_loss_price_View = contentView.findViewById(R.id.order_loss_price_View);

        order_id_View.setText("#" + bean.getOrderId());
        order_price_View.setText(String.format("0.2%", bean.getOrderPrice()));
        order_vol_View.setText(String.valueOf(orderVol));
        order_profit_price_View.setText(ConstantUtil.SHOWDEFVALUE);
        order_loss_price_View.setText(ConstantUtil.SHOWDEFVALUE);
        order_margin_View.setText(ConstantUtil.SHOWDEFVALUE);

        contentView.findViewById(R.id.set_profit_loss_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterPopWindow.dissmiss();
                SetProfitAndLossActivity.startMyActivity(getActivity(), 1, bean, getNowPrice(), orderDirection, goodsName, orderVol);
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

    private void initView(View view) {
        entry_order_group = view.findViewById(R.id.entry_order_group);
        direction_RadioGroup = view.findViewById(R.id.direction_RadioGroup);
        entry_order_sure_btn = view.findViewById(R.id.entry_order_sure_btn);
        entrust_volume_view = view.findViewById(R.id.entrust_volume_view);
        entrust_price_view = view.findViewById(R.id.entrust_price_view);

        hand_minus_btn = entrust_volume_view.findViewById(R.id.hand_minus_btn);
        hand_plus_btn = entrust_volume_view.findViewById(R.id.hand_plus_btn);
        hand_input_edText = entrust_volume_view.findViewById(R.id.hand_input_edText);

        price_minus_btn = entrust_price_view.findViewById(R.id.hand_minus_btn);
        price_plus_btn = entrust_price_view.findViewById(R.id.hand_plus_btn);
        pric_input_edtext = entrust_price_view.findViewById(R.id.hand_input_edText);
        pric_input_edtext.setHint("请输入挂单价格");

        hand_minus_btn.setBackgroundResource(R.drawable.lx_exchange_market_grey_minus);
        price_minus_btn.setBackgroundResource(R.drawable.lx_exchange_market_grey_minus);

        hand_plus_btn.setBackgroundResource(R.drawable.lx_exchange_market_grey_plus);
        price_plus_btn.setBackgroundResource(R.drawable.lx_exchange_market_grey_plus);

        if (BaseApplication.kIndexColor==0){
            buyRadio.setBackground(ContextCompat.getDrawable(mContext,R.drawable.db_sel_red_un_white_bg));
            sellRadio.setBackground(ContextCompat.getDrawable(mContext,R.drawable.db_sel_green_un_white_bg));
        } else {
            buyRadio.setBackground(ContextCompat.getDrawable(mContext,R.drawable.db_sel_green_un_white_bg));
            sellRadio.setBackground(ContextCompat.getDrawable(mContext,R.drawable.db_sel_red_un_white_bg));
        }
        hand_plus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderVol = Arith.add(orderVol, handType);
                if (orderVol >= 30) {
                    ToastUtils.showShort("请输入正确的交易手数");
                    orderVol = 30;
                }
                hand_input_edText.setText(String.valueOf(orderVol));
            }
        });
        hand_minus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderVol = Arith.sub(orderVol, handType);
                if (orderVol <= 0) {
                    ToastUtils.showShort("请输入正确的交易手数");
                    orderVol = 0.01;
                }
                hand_input_edText.setText(String.valueOf(orderVol));
            }
        });

        price_plus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderPrice = Arith.add(orderPrice, handType);
                pric_input_edtext.setText(String.valueOf(orderPrice));
            }
        });
        price_minus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderPrice = Arith.sub(orderPrice, handType);
                if (orderPrice <= 0) {
                    ToastUtils.showShort("请输入正确的交易价格");
                    orderPrice = 0;
                }
                pric_input_edtext.setText(String.valueOf(orderPrice));
            }
        });
        hand_input_edText.addTextChangedListener(new TextWatcher() {
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
                        refreshDepositTipsView(moneyUsd);
                    }
                } else {
                    refreshDepositTipsView(0.00);
                }
            }
        });
    }

    private void showRangPriceTipsView() {
        List<KMarketResultBean.MarketBean> marketList = MyApplication.marketBeanList;
        KMarketResultBean.MarketBean marketBean = null;
        String formatStr = "%.2f";
        if (ConstantUtil.XAUUSD.equals(goodsName)) {
            if (marketList != null && marketList.size() >= 3) {
                marketBean = marketList.get(0);
            }
        } else if (ConstantUtil.XAGUSD.equals(goodsName)) {
            if (marketList != null && marketList.size() >= 3) {
                marketBean = marketList.get(1);
            }
            formatStr = "%.3f";
        }
        if (marketBean != null) {
            StringBuilder sb = new StringBuilder();
            if ("Buy".equals(orderDirection)) {
                sb.append("范围($):≥ ");
                sb.append(String.format(formatStr, marketBean.last_px));
            } else if ("Sell".equals(orderDirection)) {
                sb.append("范围($):≤ ");
                sb.append(String.format(formatStr, marketBean.last_px));
            }

//            //"范围($):≥ 1315.73 或 ≤ 1311.73"
//            StringBuilder sb = new StringBuilder();
//            sb.append("范围($):≥ ");
//            sb.append(String.format("%.2f", marketBean.last_px + 2));
//            sb.append(" 或 ≤ ");
//            sb.append(String.format("%.2f", marketBean.last_px - 2));
            tvRangPriceTips.setText(sb.toString());
        } else {
            tvRangPriceTips.setText("");
        }
    }


    private void refreshPriceInputView() {
        List<KMarketResultBean.MarketBean> marketList = MyApplication.marketBeanList;
        KMarketResultBean.MarketBean marketBean = null;
        String formatStr = "%.2f";
        if (ConstantUtil.XAUUSD.equals(goodsName)) {
            if (marketList != null && marketList.size() >= 3) {
                marketBean = marketList.get(0);
            }
//            pric_input_edtext.setFilters(new InputFilter[]{new MoneyInputFilterGold()});
        } else if (ConstantUtil.XAGUSD.equals(goodsName)) {
            if (marketList != null && marketList.size() >= 3) {
                marketBean = marketList.get(1);
            }
            formatStr = "%.3f";
//            MoneyInputFilterSilver moneyInputFilterSilver=new MoneyInputFilterSilver();
//            moneyInputFilterSilver.setMaxValue();
//            pric_input_edtext.setFilters(new InputFilter[]{});
        }
        if (marketBean != null) {
            pric_input_edtext.setText(String.format(formatStr, marketBean.last_px));
        } else {
            pric_input_edtext.setText("0.0");
        }
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unregistorEventBus();
        unbinder.unbind();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThrea(EventBusKeyDefine.EventBusMsgData<String> data) {
        int type = data.getType();
        try {
            if (EventBusKeyDefine.EVENTBUS_SWITCH_GOODS_NAME == type) {
                if (!ObjectUtils.isEmpty(data) && !ObjectUtils.isEmpty(data.getData())) {
                    String goodName = data.getData();
                    goodsName = goodName;
                    refreshPriceInputView();
                    showRangPriceTipsView();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {

    }
}

package com.lingxi.preciousmetal.ui.activity.trade;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lingxi.biz.domain.responseMo.OrderSucceedBean;
import com.lingxi.biz.domain.responseMo.TradeAllEntrustBean;
import com.lingxi.biz.util.ConstantUtil;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;
import com.lingxi.preciousmetal.ui.widget.TopBarView;
import com.lingxi.preciousmetal.ui.widget.dialog.DialogManager;
import com.lingxi.preciousmetal.util.VerifyUtil;
import com.lingxi.preciousmetal.util.net.HttpResultListener;
import com.lingxi.preciousmetal.util.net.RequestGet;
import com.lingxi.preciousmetal.util.utilCode.AppUtils;
import com.lingxi.preciousmetal.util.utilCode.Arith;
import com.lingxi.preciousmetal.util.utilCode.StringUtils;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetProfitAndLossActivity extends TranslucentStatusBarActivity implements View.OnClickListener {

    @BindView(R.id.tv_win_tips)
    TextView tvWinTips;
    @BindView(R.id.tv_loss_tips)
    TextView tvLossTips;
    private View loss_layout, profit_layout;
    private ImageView loss_minus_btn, loss_plus_btn;
    private ImageView profit_minus_btn, profit_plus_btn;
    private EditText loss_input_edText, profit_input_edtext;
    OrderSucceedBean orderSucceedBean;
    private TextView order_price_View, order_now_price_View, profit_loss_sure_btn;
    private int orderId;
    private double priceStopLoss, priceTakeProfit;
    private double handType = 0.1;
    private double nowPrice;
    private String goodsName;
    private String orderDirection;
    double orderPrice;
    double handNum;

    //nowPrice 现价
    //buyOrBuy买还是卖
    //goodsName 品种
    public static void startMyActivity(Context context, int type, TradeAllEntrustBean bean, double nowPrice, String orderDirection, String goodsName, double handNum) {
        // 1持仓 进来 2委托进来
        Intent intent = new Intent(context, SetProfitAndLossActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("orderBean", bean);
        intent.putExtra("goodsName", goodsName);
        intent.putExtra("nowPrice", nowPrice);
        intent.putExtra("orderDirection", orderDirection);
        intent.putExtra("handNum", handNum);
        context.startActivity(intent);
    }

    public static void startMyActivity(Context context, int type, OrderSucceedBean bean, double nowPrice, String orderDirection, String goodsName, double handNum) {
        // 1持仓 进来 2委托进来
        Intent intent = new Intent(context, SetProfitAndLossActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("orderBean", bean);
        intent.putExtra("goodsName", goodsName);
        intent.putExtra("nowPrice", nowPrice);
        intent.putExtra("orderDirection", orderDirection);
        intent.putExtra("handNum", handNum);
        context.startActivity(intent);
    }

    private void initTopBar() {
        TopBarView mTopBarView = findViewById(R.id.topbar_view);
        mTopBarView.setMode(TopBarView.MODE_BACK);
        mTopBarView.setTitle("设置止盈止损");
    }

    private double getDifPrice() {
        double difPrice = 0;
        if (ConstantUtil.XAUUSD.equals(goodsName)) {
            difPrice = 2;
        } else if (ConstantUtil.XAGUSD.equals(goodsName)) {
            difPrice = 0.02;
        } else if (ConstantUtil.EURUSD.equals(goodsName)) {
            difPrice = 0.02;
        }
        return difPrice;
    }

    private String getFormatStr() {
        String formatStr = "%.2f";
        if (ConstantUtil.XAUUSD.equals(goodsName)) {
        } else if (ConstantUtil.XAGUSD.equals(goodsName)) {
            formatStr = "%.3f";
        } else if (ConstantUtil.EURUSD.equals(goodsName)) {
            formatStr = "%.4f";
        }
        return formatStr;
    }

    private void initPriceView() {
        String formatStr = getFormatStr();
        double difPrice = getDifPrice();
        order_price_View.setText(String.format(formatStr, orderPrice));
        order_now_price_View.setText(String.format(formatStr, nowPrice));
        if ("Buy".equals(orderDirection)) {
            tvWinTips.setText("范围($): ≥ " + String.format(formatStr, nowPrice + difPrice) + "预计盈亏($): --");
            tvLossTips.setText("范围($): ≤ " + String.format(formatStr, nowPrice - difPrice) + "预计盈亏($): --");
        } else if ("Sell".equals(orderDirection)) {
            tvWinTips.setText("范围($): ≤ " + String.format(formatStr, nowPrice - difPrice) + "预计盈亏($): --");
            tvLossTips.setText("范围($): ≥ " + String.format(formatStr, nowPrice + difPrice) + "预计盈亏($): --");
        }
    }

    private void updateWinTips(double setPrice) {
        String formatStr = getFormatStr();
        double difPrice = getDifPrice();
        if ("Buy".equals(orderDirection)) {
            tvWinTips.setText("范围($): ≥ " + String.format(formatStr, nowPrice + difPrice) + "预计盈亏($): " + String.format(formatStr, getEvaluateWinorLossPrice(setPrice)));
        } else if ("Sell".equals(orderDirection)) {
            tvWinTips.setText("范围($): ≤ " + String.format(formatStr, nowPrice - difPrice) + "预计盈亏($): " + String.format(formatStr, getEvaluateWinorLossPrice(setPrice)));
        }
    }

    private void updateLossTips(double setPrice) {
        String formatStr = getFormatStr();
        double difPrice = getDifPrice();
        if ("Buy".equals(orderDirection)) {
            tvLossTips.setText("范围($): ≤ " + String.format(formatStr, nowPrice - difPrice) + "预计盈亏($): " + String.format(formatStr, getEvaluateWinorLossPrice(setPrice)));
        } else if ("Sell".equals(orderDirection)) {
            tvLossTips.setText("范围($): ≥ " + String.format(formatStr, nowPrice + difPrice) + "预计盈亏($): " + String.format(formatStr, getEvaluateWinorLossPrice(setPrice)));
        }
    }


    private double getEvaluateWinorLossPrice(double setPrice) {
        double value = 0;
        if (ConstantUtil.XAUUSD.equals(goodsName)) {
            value = orderPrice * handNum * 100;
        } else if (ConstantUtil.XAGUSD.equals(goodsName)) {
            value = orderPrice * handNum * 5000;
        } else if (ConstantUtil.EURUSD.equals(goodsName)) {
            value = orderPrice * handNum * 5000;
        }
        if ("Buy".equals(orderDirection)) {
            value = value * 1;

        } else if ("Sell".equals(orderDirection)) {
            value = value * -1;
        }
        return setPrice - value;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_profit_and_loss);
        ButterKnife.bind(this);
        initTopBar();
        initView();
        initData();
    }

    private void requestLossProfit() {
        Map<String, Object> map = new HashMap<>();
        map.put("stopLoss", priceStopLoss);
        map.put("takeProfit", priceTakeProfit);
        DialogManager.getInstance().showLoadingDialog(this, "", false);
        String url = String.format(RequestGet.profitLoss, orderId);
        RequestGet.requestHttpData(this, ConstantUtil.PUT, url, map, new HttpResultListener() {
            @Override
            public void onSuccess(String data) {
                DialogManager.getInstance().cancellLoadingDialog();
                finish();
            }

            @Override
            public void onFailure(String data) {
                DialogManager.getInstance().cancellLoadingDialog();
            }
        });
    }

    private void initData() {
        int fromType = getIntent().getIntExtra("type", 0);
        nowPrice = getIntent().getDoubleExtra("nowPrice", 0);
        handNum = getIntent().getDoubleExtra("handNum", 0);
        goodsName = getIntent().getStringExtra("goodsName");
        orderDirection = getIntent().getStringExtra("orderDirection");
        orderPrice = 0;
        if (fromType == 1) {
            orderSucceedBean = (OrderSucceedBean) getIntent().getSerializableExtra("orderBean");
            if (orderSucceedBean != null) {
                orderId = orderSucceedBean.getOrderId();
                orderPrice = orderSucceedBean.getOrderPrice();
                priceStopLoss = orderSucceedBean.getStopLoss();
                priceTakeProfit = orderSucceedBean.getTakeProfit();
            }
        } else if (fromType == 2) {
            TradeAllEntrustBean bean = (TradeAllEntrustBean) getIntent().getSerializableExtra("orderBean");
            priceStopLoss = bean.getStopLoss();
            priceTakeProfit = bean.getTakeProfit();
            orderPrice = bean.getOrderPrice();
            orderId = bean.getOrderId();
        }
        order_now_price_View.setText(ConstantUtil.SHOWDEFVALUE);
        AppUtils.setCustomFont(this, order_price_View);
        AppUtils.setCustomFont(this, order_now_price_View);
        if (priceStopLoss > 0) {
            loss_input_edText.setText(String.valueOf(priceStopLoss));
        }
        if (priceTakeProfit > 0) {
            profit_input_edtext.setText(String.valueOf(priceTakeProfit));
        }
        profit_loss_sure_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loss = loss_input_edText.getText().toString();

                if (StringUtils.isEmpty(loss)) {
                    ToastUtils.showShort("请输入止损价");
                    return;
                }
                if (!VerifyUtil.isNumeric(loss)) {
                    ToastUtils.showShort("止损价格式错误");
                    return;
                }

                String takeProfit = profit_input_edtext.getText().toString();
                if (StringUtils.isEmpty(takeProfit)) {
                    ToastUtils.showShort("请输入止盈价");
                    return;
                }

                if (!VerifyUtil.isNumeric(takeProfit)) {
                    ToastUtils.showShort("止盈价格式错误");
                    return;
                }

                priceStopLoss = Double.parseDouble(loss);
                priceTakeProfit = Double.parseDouble(takeProfit);
                if (priceStopLoss <= 0) {
                    ToastUtils.showShort("请设置止损价");
                    return;
                } else if (priceTakeProfit <= 0) {
                    ToastUtils.showShort("请设置止盈价");
                    return;
                }
                requestLossProfit();
            }
        });
        initPriceView();
    }

    private void initView() {
        order_price_View = findViewById(R.id.order_price_View);
        order_now_price_View = findViewById(R.id.order_now_price_View);
        profit_loss_sure_btn = findViewById(R.id.profit_loss_sure_btn);
        loss_layout = findViewById(R.id.loss_layout);
        profit_layout = findViewById(R.id.profit_layout);

        loss_minus_btn = loss_layout.findViewById(R.id.hand_minus_btn);
        loss_plus_btn = loss_layout.findViewById(R.id.hand_plus_btn);
        loss_input_edText = loss_layout.findViewById(R.id.hand_input_edText);
        loss_input_edText.setHint("请输入止盈价格");

        profit_minus_btn = profit_layout.findViewById(R.id.hand_minus_btn);
        profit_plus_btn = profit_layout.findViewById(R.id.hand_plus_btn);
        profit_input_edtext = profit_layout.findViewById(R.id.hand_input_edText);
        profit_input_edtext.setHint("请输入止损价格");

        loss_minus_btn.setBackgroundResource(R.drawable.lx_exchange_market_grey_minus);
        profit_minus_btn.setBackgroundResource(R.drawable.lx_exchange_market_grey_minus);

        loss_plus_btn.setBackgroundResource(R.drawable.lx_exchange_market_grey_plus);
        profit_plus_btn.setBackgroundResource(R.drawable.lx_exchange_market_grey_plus);

        loss_minus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priceStopLoss = Arith.sub(priceStopLoss, handType);
                if (priceStopLoss <= 0) {
                    ToastUtils.showShort("请输入正确的交易价格");
                    priceStopLoss = 0;
                }
                loss_input_edText.setText(String.valueOf(priceStopLoss));
            }
        });
        loss_plus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priceStopLoss = Arith.add(priceStopLoss, handType);
                loss_input_edText.setText(String.valueOf(priceStopLoss));
            }
        });

        profit_minus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priceTakeProfit = Arith.sub(priceTakeProfit, handType);
                if (priceTakeProfit <= 0) {
                    ToastUtils.showShort("请输入正确的交易价格");
                    priceTakeProfit = 0;
                }
                profit_input_edtext.setText(String.valueOf(priceTakeProfit));
            }
        });
        profit_plus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priceTakeProfit = Arith.add(priceTakeProfit, handType);
                profit_input_edtext.setText(String.valueOf(priceTakeProfit));
            }
        });

        loss_input_edText.addTextChangedListener(new TextWatcher() {
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
                        updateLossTips(moneyUsd);
                    }
                } else {
                    updateLossTips(0.00);
                }
            }
        });

        profit_input_edtext.addTextChangedListener(new TextWatcher() {
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
                        updateWinTips(moneyUsd);
                    }
                } else {
                    updateWinTips(0.00);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
    }
}

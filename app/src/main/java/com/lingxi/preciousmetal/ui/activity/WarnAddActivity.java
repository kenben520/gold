package com.lingxi.preciousmetal.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.WarnAddRequestMo;
import com.lingxi.biz.domain.responseMo.BaseMo;
import com.lingxi.biz.domain.responseMo.LoginResultMo;
import com.lingxi.biz.service.TradeService;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;
import com.lingxi.preciousmetal.domain.UserInfoBean;
import com.lingxi.preciousmetal.login.LoginHelper;
import com.lingxi.preciousmetal.ui.listener.KeyBoardListener;
import com.lingxi.preciousmetal.ui.vInterface.RegisterVInterface;
import com.lingxi.preciousmetal.ui.widget.TopBarView;
import com.lingxi.preciousmetal.ui.widget.dialog.DialogManager;
import com.lingxi.preciousmetal.util.MoneyInputFilterGold;
import com.lingxi.preciousmetal.util.MoneyInputFilterSilver;
import com.lingxi.preciousmetal.util.VerifyUtil;
import com.lingxi.preciousmetal.util.utilCode.TimeUtils;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.lingxi.preciousmetal.util.utilCode.TimeUtils.FMDHMS;

/**
 * Created by zhangwei on 2018/4/19.
 */

public class WarnAddActivity extends TranslucentStatusBarActivity implements RegisterVInterface {
    @BindView(R.id.topbar_view)
    TopBarView mTopbarView;
    @BindView(R.id.btn_gold)
    TextView btnGold;
    @BindView(R.id.btn_silver)
    TextView btnSilver;
    @BindView(R.id.tv_buy_recommond)
    TextView tvBuyRecommond;
    @BindView(R.id.tv_sell_recommond)
    TextView tvSellRecommond;
    @BindView(R.id.rdb_1)
    RadioButton rdb1;
    @BindView(R.id.tv_rdb_1)
    TextView tvRdb1;
    @BindView(R.id.rdb_2)
    RadioButton rdb2;
    @BindView(R.id.tv_rdb_2)
    TextView tvRdb2;
    @BindView(R.id.btn_submit)
    TextView btnSubmit;
    //    private RegisterPresenter mRegisterPresenter;
    public   int GOLD_TYPE = 1;
    public   int SILVER_TYPE = 2;
    public  int showType = GOLD_TYPE;
    @BindView(R.id.rdb_3)
    RadioButton rdb3;
    @BindView(R.id.tv_rdb_3)
    TextView tvRdb3;
    @BindView(R.id.rdb_4)
    RadioButton rdb4;
    @BindView(R.id.tv_rdb_4)
    TextView tvRdb4;
    @BindView(R.id.edit_input_money)
    EditText editInputMoney;
    @BindView(R.id.rdb_5)
    RadioButton rdb5;
    @BindView(R.id.tv_rdb_5)
    TextView tvRdb5;
    @BindView(R.id.rdb_6)
    RadioButton rdb6;
    @BindView(R.id.tv_rdb_6)
    TextView tvRdb6;
    @BindView(R.id.rdb_7)
    RadioButton rdb7;
    @BindView(R.id.tv_rdb_7)
    TextView tvRdb7;
    @BindView(R.id.radio_group1)
    RadioGroup radioGroup;
    @BindView(R.id.radio_group2)
    RadioGroup radioGroup2;
    @BindView(R.id.radio_group3)
    RadioGroup radioGroup3;
    @BindView(R.id.layout_root)
    LinearLayout layoutRoot;
    @BindView(R.id.layout_content)
    LinearLayout mInputParent;
    private double adviseBuyGoldPrice = 1299.41, adviseSellGoldPrice = 1299.71;
    private double adviseBuySilverPrice = 14.212, adviseSellSilverPrice = 14.813;
    UserInfoBean mUserInfoBean;
    private KeyBoardListener mKeyBoardListener;
    DecimalFormat nf1 = new DecimalFormat("##0.00");
    DecimalFormat nf2 = new DecimalFormat("##0.000");
    private int jzGold = 1;
    private int directionGold = 1;
    private int timeGold = 1;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, WarnAddActivity.class);
        context.startActivity(intent);
    }

    public static void actionStart(Context context,int showType) {
        Intent intent = new Intent(context, WarnAddActivity.class);
        intent.putExtra("showType", showType);
        context.startActivity(intent);
    }

    public static void actionStart(Activity context) {
        Intent intent = new Intent(context, WarnAddActivity.class);
        context.startActivityForResult(intent, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_warn);
        showType = getIntent().getIntExtra("showType", GOLD_TYPE);
        ButterKnife.bind(this);
        if (!LoginHelper.getInstance().isLogin()) {
            ToastUtils.showShort("请先登录");
            finish();
        }
        mUserInfoBean = LoginHelper.getInstance().getLoginUserInfo();
//        mRegisterPresenter = new RegisterPresenter(this);
        initTopBar();
        initView();
    }

//    public void setMarketValue() {
//        if (showType == GOLD_TYPE) {
//            name.setText("伦敦金");
//        } else if (showType == SILVER_TYPE) {
//            name.setText("伦敦银");
//        }
//        List<HomeAllResultBean.MarketBean> marketList = MyApplication.marketList;
//        if (marketList != null && marketList.size() >= 3) {
//            String value = "%.2f";
//            // 黄金小数点2位，白银小数点3位，美元小数点4位
//            HomeAllResultBean.MarketBean marketBean = null;
//            if (showType == GOLD_TYPE) {
//                marketBean = marketList.get(0);
//                value = "%.2f";
//            } else if (showType == SILVER_TYPE) {
//                marketBean = marketList.get(1);
//                value = "%.3f";
//            }
//            money.setText(String.valueOf(marketBean.getLast_px()));
//            double changeDb = marketBean.getPx_change();
//            if (changeDb > 0) {
//                money.setTextColor(ContextCompat.getColor(getContext(), R.color.trueGreen));
//                riseFallRate.setTextColor(ContextCompat.getColor(getContext(), R.color.trueGreen));
//                riseFallPrice.setTextColor(ContextCompat.getColor(getContext(), R.color.trueGreen));
//                ivRiseFall.setBackgroundResource(R.drawable.lx_signal_up);
//            } else {
//                money.setTextColor(ContextCompat.getColor(getContext(), R.color.orangeyRedTwo));
//                riseFallRate.setTextColor(ContextCompat.getColor(getContext(), R.color.orangeyRedTwo));
//                riseFallPrice.setTextColor(ContextCompat.getColor(getContext(), R.color.orangeyRedTwo));
//                ivRiseFall.setBackgroundResource(R.drawable.lx_signal_down);
//            }
//            String change = String.format(value, changeDb);
//            riseFallPrice.setText(change);
//            String rate = String.format("%.2f", marketBean.getPx_change_rate());
//            riseFallRate.setText(rate);
//            AppUtils.setCustomFont(getContext(), money);
//        }
//    }
//

    private void initView() {
        if (showType == GOLD_TYPE) {
            switchToGoldTab();
        } else if (showType == SILVER_TYPE) {
            switchToSilverTab();
        }
        mKeyBoardListener = new KeyBoardListener(layoutRoot, new KeyBoardListener.OnSoftKeyboardListener() {
            @Override
            public void onSoftKeyboardShown(int keyBoardHeight) {
                mInputParent.setTranslationY(-keyBoardHeight);
            }

            @Override
            public void onSoftKeyboardHidden(int keyBoardHeight) {
                mInputParent.setTranslationY(0);
            }
        });
        rdb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setBuyPrice();
                }
            }
        });
        rdb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setSellPrice();
                }
            }
        });
    }



    private void initTopBar() {
        mTopbarView.setMode(TopBarView.MODE_BACK);
        mTopbarView.setTitle("新增预警");
    }

    @Override
    public void registerSuccess(LoginResultMo accountVo) {
        Intent intent = new Intent(WarnAddActivity.this, GoldMainActivity.class);
        startActivity(intent);
    }

    @Override
    public void registerFail(String errorMsg) {
        ToastUtils.showShort(errorMsg);
    }

    @Override
    public void showLoadingDialog(String message) {
        DialogManager.getInstance().showLoadingDialog(this, message, false);
    }

    @Override
    public void cancelLoadingDialog() {
        DialogManager.getInstance().cancellLoadingDialog();
    }

    @OnClick({R.id.tv_rdb_1, R.id.rdb_1, R.id.tv_rdb_2, R.id.rdb_2, R.id.btn_gold, R.id.btn_silver, R.id.tv_rdb_3, R.id.rdb_3, R.id.tv_rdb_4, R.id.rdb_4, R.id.tv_rdb_5, R.id.rdb_5, R.id.tv_rdb_6, R.id.rdb_6, R.id.tv_rdb_7, R.id.rdb_7, R.id.layout_content})
    public void onViewClicked(View view) {
        hideSoftInput();
        switch (view.getId()) {
            case R.id.rdb_1:
            case R.id.tv_rdb_1:
                jzGold = 1;
                rdb1.toggle();
                break;
            case R.id.rdb_2:
            case R.id.tv_rdb_2:
                jzGold = 2;
                rdb2.toggle();
                break;
            case R.id.tv_rdb_3:
            case R.id.rdb_3:
                directionGold = 1;
                rdb3.toggle();
                break;
            case R.id.rdb_4:
            case R.id.tv_rdb_4:
                directionGold = 2;
                rdb4.toggle();
                break;
            case R.id.rdb_5:
            case R.id.tv_rdb_5:
                timeGold = 1;
                rdb5.toggle();
                break;
            case R.id.rdb_6:
            case R.id.tv_rdb_6:
                timeGold = 2;
                rdb6.toggle();
                break;
            case R.id.rdb_7:
            case R.id.tv_rdb_7:
                timeGold = 3;
                rdb7.toggle();
                break;
            case R.id.btn_gold:
                if (showType == SILVER_TYPE) {
                    showType = GOLD_TYPE;
                    switchToGoldTab();
                }
                break;
            case R.id.btn_silver:
                if (showType == GOLD_TYPE) {
                    showType = SILVER_TYPE;
                    switchToSilverTab();
                }
                break;
        }

    }

    public void switchToGoldTab() {
        btnSilver.setSelected(false);
        btnGold.setSelected(true);
        editInputMoney.setFilters(new InputFilter[]{new MoneyInputFilterGold()});
        String str1 = nf1.format(adviseBuyGoldPrice);
        String str2 = nf1.format(adviseSellGoldPrice);
        tvBuyRecommond.setText(str1);
        tvSellRecommond.setText(str2);
        if (jzGold == 1) {
            rdb1.toggle();
            setBuyPrice();
        } else if (jzGold == 2) {
            rdb2.toggle();
            setSellPrice();
        }
        if (directionGold == 1) {
            rdb3.toggle();
        } else if (directionGold == 2) {
            rdb4.toggle();
        }
        if (timeGold == 1) {
            rdb5.toggle();
        } else if (timeGold == 2) {
            rdb6.toggle();
        } else if (timeGold == 3) {
            rdb7.toggle();
        }

    }

    private  void setBuyPrice(){
        String str1 = "";
        if (showType == GOLD_TYPE) {
            str1 = nf1.format(adviseBuyGoldPrice);
        } else if (showType == SILVER_TYPE) {
            str1 = nf2.format(adviseBuySilverPrice);
        }
        editInputMoney.setText(str1);
    }

    private  void setSellPrice(){
        String str1 = "";
        if (showType == GOLD_TYPE) {
            str1 = nf1.format(adviseSellGoldPrice);
        } else if (showType == SILVER_TYPE) {
            str1 = nf2.format(adviseSellSilverPrice);
        }
        editInputMoney.setText(str1);
    }

    public void switchToSilverTab() {
        btnSilver.setSelected(true);
        btnGold.setSelected(false);
        editInputMoney.setFilters(new InputFilter[]{new MoneyInputFilterSilver()});
        String str1 = nf2.format(adviseBuySilverPrice);
        String str2 = nf2.format(adviseSellSilverPrice);
        tvBuyRecommond.setText(str1);
        tvSellRecommond.setText(str2);
        if (jzGold == 1) {
            rdb1.toggle();
            setBuyPrice();
        } else if (jzGold == 2) {
            rdb2.toggle();
            setSellPrice();
        }
        if (directionGold == 1) {
            rdb3.toggle();
        } else if (directionGold == 2) {
            rdb4.toggle();
        }
        if (timeGold == 1) {
            rdb5.toggle();
        } else if (timeGold == 2) {
            rdb6.toggle();
        } else if (timeGold == 3) {
            rdb7.toggle();
        }
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked1() {
        addWarn();
    }

    private void addWarn() {
        String pro_warn_price = editInputMoney.getText().toString().trim();
        if(!VerifyUtil.isNumeric(pro_warn_price))
        {
            ToastUtils.showShort("请输入正确的价格格式");
            return;
        }
        if (TextUtils.isEmpty(pro_warn_price)) {
            ToastUtils.showShort("请输入预警价格");
            return;
        }
        String prod_code;
        if (showType == 1) {
            prod_code = "XAUUSD";
        } else {
            prod_code = "XAGUSD";
        }
        int pro_type = 0;
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.rdb_1:
                pro_type = 1;
                break;
            case R.id.rdb_2:
                pro_type = 2;
                break;
        }
        int pro_direction = 0;
        switch (radioGroup2.getCheckedRadioButtonId()) {
            case R.id.rdb_3:
                pro_direction = 1;
                break;
            case R.id.rdb_4:
                pro_direction = 2;
                break;
        }
        double moneyUsd1 = Double.parseDouble(pro_warn_price);
        if (pro_direction == 1) {
            if (pro_type == 1) {
                if (moneyUsd1 <= adviseBuyGoldPrice) {
                    ToastUtils.showShort("您设置的价格不能小于等于买入价格");
                    return;
                }

            } else if (pro_type == 2) {
                if (moneyUsd1 <= adviseSellGoldPrice) {
                    ToastUtils.showShort("您设置的价格不能小于等于买卖出价格");
                    return;
                }
            }
        }
        if (pro_direction == 2) {
            if (pro_type == 1) {
                if (moneyUsd1 >= adviseBuyGoldPrice) {
                    ToastUtils.showShort("您设置的价格不能大于等于买入价格");
                    return;
                }
            } else if (pro_type == 2) {
                if (moneyUsd1 >= adviseSellGoldPrice) {
                    ToastUtils.showShort("您设置的价格不能大于等于买卖出价格");
                    return;
                }
            }
        }
        int pro_h_exp = 0;
        switch (radioGroup3.getCheckedRadioButtonId()) {
            case R.id.rdb_5:
                pro_h_exp = 1;
                break;
            case R.id.rdb_6:
                pro_h_exp = 7;
                break;
            case R.id.rdb_7:
                pro_h_exp = 15;
                break;
        }
        WarnAddRequestMo liveListRequestMo = new WarnAddRequestMo(mUserInfoBean.user_id, prod_code, pro_type, pro_direction, String.valueOf(moneyUsd1), pro_h_exp);
        TradeService.addWarning(liveListRequestMo, new BizResultListener<BaseMo>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, BaseMo baseMo) {

            }

            @Override
            public void onSuccess(BaseMo baseMo) {
                ToastUtils.showShort("成功");
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                ToastUtils.showShort(bizMessage);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mKeyBoardListener != null) {
            mKeyBoardListener.onDestroy();
        }
    }

}

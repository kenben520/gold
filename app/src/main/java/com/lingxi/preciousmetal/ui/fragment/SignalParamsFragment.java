package com.lingxi.preciousmetal.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.tifezh.kchartlib.utils.ViewUtil;
import com.lingxi.biz.domain.responseMo.HomeAllResultBean;
import com.lingxi.biz.domain.responseMo.LiveProgramListMo;
import com.lingxi.biz.domain.responseMo.SignalParamsMo;
import com.lingxi.biz.util.ConstantUtil;
import com.lingxi.common.base.BaseApplication;
import com.lingxi.common.base.BaseFragment;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.MyApplication;
import com.lingxi.preciousmetal.common.EventBusKeyDefine;
import com.lingxi.preciousmetal.ui.listener.DataChangeListener;
import com.lingxi.preciousmetal.ui.widget.CustomSeekBar;
import com.lingxi.preciousmetal.util.utilCode.AppUtils;
import com.lingxi.preciousmetal.util.utilCode.ObjectUtils;
import com.lingxi.preciousmetal.util.utilCode.TimeUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.lingxi.preciousmetal.util.utilCode.TimeUtils.FYMDHM;


public class SignalParamsFragment extends BaseFragment implements DataChangeListener<SignalParamsMo> {
    @BindView(R.id.btn_5_min)
    TextView btn5Min;
    @BindView(R.id.btn_one_hour)
    TextView btnOneHour;
    @BindView(R.id.btn_day)
    TextView btnDay;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.tag_1)
    TextView tag1;
    @BindView(R.id.rise_fall_price)
    TextView riseFallPrice;
    @BindView(R.id.rise_fall_rate)
    TextView riseFallRate;
    @BindView(R.id.tv_tech_index)
    TextView tvTechIndex;
    @BindView(R.id.tv_tech_index_positive)
    TextView tvTechIndexPositive;
    @BindView(R.id.tech_index_rate_bar)
    CustomSeekBar techIndexRateBar;
    @BindView(R.id.tv_tech_index_negtive)
    TextView tvTechIndexNegtive;
    @BindView(R.id.avg_index)
    TextView avgIndex;
    @BindView(R.id.tv_avg_index_positive)
    TextView tvAvgIndexPositive;
    @BindView(R.id.avg_index_rate_bar)
    CustomSeekBar avgIndexRateBar;
    @BindView(R.id.tv_avg_index_negtive)
    TextView tvAvgIndexNegtive;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.tv_rsi_value)
    TextView tvRsiValue;
    @BindView(R.id.tv_rsi_direction)
    TextView tvRsiDirection;
    @BindView(R.id.tv_macd1226_value)
    TextView tvMacd1226Value;
    @BindView(R.id.tv_macd1226_direction)
    TextView tvMacd1226Direction;
    @BindView(R.id.tv_cci14_value)
    TextView tvCci14Value;
    @BindView(R.id.tv_cci14_direction)
    TextView tvCci14Direction;
    @BindView(R.id.tv_bbi_value)
    TextView tvBbiValue;
    @BindView(R.id.tv_bbi_direction)
    TextView tvBbiDirection;
    @BindView(R.id.tv_wr_value)
    TextView tvWrValue;
    @BindView(R.id.tv_wr_direction)
    TextView tvWrDirection;
    @BindView(R.id.tv_dma1050_value)
    TextView tvDma1050Value;
    @BindView(R.id.tv_dma1050_direction)
    TextView tvDma1050Direction;
    @BindView(R.id.tv_dma510_value)
    TextView tvDma510Value;
    @BindView(R.id.tv_dma510_direction)
    TextView tvDma510Direction;
    @BindView(R.id.tv_kd_value)
    TextView tvKdValue;
    @BindView(R.id.tv_kd_direction)
    TextView tvKdDirection;
    @BindView(R.id.tv_roc_value)
    TextView tvRocValue;
    @BindView(R.id.tv_roc_direction)
    TextView tvRocDirection;
    @BindView(R.id.tv_ma5_value)
    TextView tvMa5Value;
    @BindView(R.id.tv_ma5_direction)
    TextView tvMa5Direction;
    @BindView(R.id.tv_ma10_value)
    TextView tvMa10Value;
    @BindView(R.id.tv_ma10_direction)
    TextView tvMa10Direction;
    @BindView(R.id.tv_ma20_value)
    TextView tvMa20Value;
    @BindView(R.id.tv_ma20_direction)
    TextView tvMa20Direction;
    @BindView(R.id.tv_ma50_value)
    TextView tvMa50Value;
    @BindView(R.id.tv_ma50_direction)
    TextView tvMa50Direction;
    @BindView(R.id.tv_ma100_value)
    TextView tvMa100Value;
    @BindView(R.id.tv_ma100_direction)
    TextView tvMa100Direction;
    @BindView(R.id.tv_ma200_value)
    TextView tvMa200Value;
    @BindView(R.id.tv_ma200_direction)
    TextView tvMa200Direction;
    @BindView(R.id.tv_dma2050_value)
    TextView tvDma2050Value;
    @BindView(R.id.tv_dma2050_direction)
    TextView tvDma2050Direction;
    @BindView(R.id.iv_rise_fall)
    ImageView ivRiseFall;
    @BindView(R.id.tag_2)
    TextView tag2;
    @BindView(R.id.iv_weather_sunny)
    ImageView ivWeatherSunny;
    @BindView(R.id.iv_weather_cloudy)
    ImageView ivWeatherCloudy;
    @BindView(R.id.iv_weather_suncloudy)
    ImageView ivWeatherSuncloudy;
    @BindView(R.id.layout_money)
    LinearLayout layoutMoney;
    @BindView(R.id.line_5_min)
    View line5Min;
    @BindView(R.id.line_one_hour)
    View lineOneHour;
    @BindView(R.id.line_day)
    View lineDay;
    //    @BindView(R.id.iv_weather)
//    ImageView ivWeather;
    private View mContentView;
    Unbinder unbinder;
    public static final int FIVE_MIN_TYPE = 1;
    public static final int ONE_HOUR_TYPE = 2;
    public static final int ONE_DAY_TYPE = 3;
    public static int showType = ONE_HOUR_TYPE;
    public String p_code;

    public static SignalParamsFragment newInstance(String p_code) {
        SignalParamsFragment fragmentCommon = new SignalParamsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("p_code", p_code);
        fragmentCommon.setArguments(bundle);
        return fragmentCommon;
    }

    public void switchTo5min() {
        btn5Min.setSelected(true);
        btnDay.setSelected(false);
        btnOneHour.setSelected(false);
        line5Min.setSelected(true);
        lineDay.setSelected(false);
        lineOneHour.setSelected(false);
        refreshData();
    }

    public void switchToOneHour() {
        btn5Min.setSelected(false);
        btnDay.setSelected(false);
        btnOneHour.setSelected(true);
        line5Min.setSelected(false);
        lineDay.setSelected(false);
        lineOneHour.setSelected(true);
        refreshData();
    }

    public void switchToOneDay() {
        btn5Min.setSelected(false);
        btnDay.setSelected(true);
        btnOneHour.setSelected(false);
        line5Min.setSelected(false);
        lineDay.setSelected(true);
        lineOneHour.setSelected(false);
        refreshData();
    }

    @Override
    public void onViewCreated(@NonNull final View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
        registorEventBus();
        Bundle bundle = getArguments();
        p_code = bundle.getString("p_code");
//        list = (List<HomeAllResultBean.MarketBean>) bundle.getSerializable("marketlist");
        initView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mContentView == null) {
            mContentView = inflater.inflate(R.layout.fragment_signal_params, container, false);
        }
        unbinder = ButterKnife.bind(this, mContentView);
        if (BaseApplication.kIndexColor == 1) {
            techIndexRateBar.setProgressDrawable(ContextCompat.getDrawable(mContext, R.drawable.po_red_green_seekbar));
            avgIndexRateBar.setProgressDrawable(ContextCompat.getDrawable(mContext, R.drawable.po_red_green_seekbar));
        }
        return mContentView;
    }

    SignalParamsMo signalParamsMo = null;

    private void refreshData() {
        if (ObjectUtils.isEmpty(signalParamsMo)) return;
        SignalParamsMo.SignalBean signalBean = null;
        if (ConstantUtil.XAUUSD.equals(p_code)) {
            signalBean = signalParamsMo.getXAUUSD();
        } else if (ConstantUtil.XAGUSD.equals(p_code)) {
            signalBean = signalParamsMo.getXAGUSD();
        }
        if (ObjectUtils.isEmpty(signalBean)) return;
        SignalParamsMo.SignalBean.ResultBean resultBean = signalBean.getResult();//结论部分
        SignalParamsMo.SignalBean.QuotaBean quotaBean = signalBean.getQuota();//技术指标部分
        SignalParamsMo.SignalBean.AverageBean averageBean = signalBean.getAverage();//均线指标部分
        SignalParamsMo.SignalBean.ResultBean.TimeResultBean timeResultBean = null;
        SignalParamsMo.SignalBean.QuotaBean.TimeQuotaBean timeQuotaBean = null;
        SignalParamsMo.SignalBean.AverageBean.TimeAverageBean timeAverageBean = null;
        if (showType == FIVE_MIN_TYPE) {
            timeResultBean = resultBean.get_$5m();
            timeQuotaBean = quotaBean.get_$5m();
            timeAverageBean = averageBean.get_$5m();
        } else if (showType == ONE_HOUR_TYPE) {
            timeResultBean = resultBean.get_$60m();
            timeQuotaBean = quotaBean.get_$60m();
            timeAverageBean = averageBean.get_$5m();
        } else if (showType == ONE_DAY_TYPE) {
            timeResultBean = resultBean.get_$1day();
            timeQuotaBean = quotaBean.get_$1day();
            timeAverageBean = averageBean.get_$5m();
        }
        if (!ObjectUtils.isEmpty(timeResultBean)) {
            List<Integer> resultQuataInteger = timeResultBean.getQuota();
            List<Integer> averageQuataInteger = timeResultBean.getAverage();
            if (resultQuataInteger != null && resultQuataInteger.size() >= 2) {
                tvTechIndexPositive.setText("看多(" + resultQuataInteger.get(0) + ")");
                tvTechIndexNegtive.setText("看空(" + resultQuataInteger.get(1) + ")");
            }
            if (averageQuataInteger != null && averageQuataInteger.size() >= 2) {
                tvAvgIndexPositive.setText("看多(" + averageQuataInteger.get(0) + ")");
                tvAvgIndexNegtive.setText("看空(" + averageQuataInteger.get(1) + ")");
            }
            tvTechIndexPositive.setTextColor(ViewUtil.getKUpColor(mContext));
            tvTechIndexNegtive.setTextColor(ViewUtil.getKLossColor(mContext));
            tvAvgIndexPositive.setTextColor(ViewUtil.getKUpColor(mContext));
            tvAvgIndexNegtive.setTextColor(ViewUtil.getKLossColor(mContext));

            String resultStr = timeResultBean.getWeather();
            tvResult.setText("结论: " + resultStr);
            int value = timeResultBean.getWeather_status();
            if (value == 1) {
                ivWeatherSunny.setVisibility(View.GONE);
                ivWeatherSuncloudy.setVisibility(View.GONE);
                ivWeatherCloudy.setVisibility(View.VISIBLE);
            } else if (value == 2) {
                ivWeatherSunny.setVisibility(View.GONE);
                ivWeatherSuncloudy.setVisibility(View.VISIBLE);
                ivWeatherCloudy.setVisibility(View.GONE);
            } else if (value == 3) {
                ivWeatherSunny.setVisibility(View.VISIBLE);
                ivWeatherSuncloudy.setVisibility(View.GONE);
                ivWeatherCloudy.setVisibility(View.GONE);
            }
            int total1 = resultQuataInteger.get(0) + resultQuataInteger.get(1);
            int total2 = averageQuataInteger.get(0) + averageQuataInteger.get(1);
            techIndexRateBar.setProgress(resultQuataInteger.get(0) * 100 / total1);
            avgIndexRateBar.setProgress(averageQuataInteger.get(0) * 100 / total2);
        }
        if (!ObjectUtils.isEmpty(timeQuotaBean)) {
            List<String> rsilist = timeQuotaBean.getRSI();
            if (rsilist != null && rsilist.size() >= 2) {
                tvRsiValue.setText(rsilist.get(0));
                setDirectionTextView(tvRsiDirection, rsilist.get(1));
            }
            List<String> macdlist = timeQuotaBean.getMACD();
            if (macdlist != null && macdlist.size() >= 2) {
                tvMacd1226Value.setText(macdlist.get(0));
                setDirectionTextView(tvMacd1226Direction, macdlist.get(1));
            }
            List<String> ccilist = timeQuotaBean.getCCI();
            if (ccilist != null && ccilist.size() >= 2) {
                tvCci14Value.setText(ccilist.get(0));
                setDirectionTextView(tvCci14Direction, ccilist.get(1));
            }
            List<String> bbilist = timeQuotaBean.getBBI();
            if (bbilist != null && bbilist.size() >= 2) {
                tvBbiValue.setText(bbilist.get(0));
                setDirectionTextView(tvBbiDirection, bbilist.get(1));
            }
            List<String> warlist = timeQuotaBean.getWAR();
            if (warlist != null && warlist.size() >= 2) {
                tvWrValue.setText(warlist.get(0));
                setDirectionTextView(tvWrDirection, warlist.get(1));
            }
            List<String> dma2050list = timeQuotaBean.getDMA20_50();
            if (dma2050list != null && dma2050list.size() >= 2) {
                tvDma2050Value.setText(dma2050list.get(0));
                setDirectionTextView(tvDma2050Direction, dma2050list.get(1));
            }
            List<String> dma1050list = timeQuotaBean.getDMA10_50();
            if (dma1050list != null && dma1050list.size() >= 2) {
                tvDma1050Value.setText(dma1050list.get(0));
                setDirectionTextView(tvDma1050Direction, dma1050list.get(1));
            }
            List<String> dma0510list = timeQuotaBean.getDMA5_10();
            if (dma0510list != null && dma0510list.size() >= 2) {
                tvDma510Value.setText(dma0510list.get(0));
                setDirectionTextView(tvDma510Direction, dma0510list.get(1));
            }
            List<String> kdlist = timeQuotaBean.getKD();
            if (kdlist != null && kdlist.size() >= 2) {
                tvKdValue.setText(kdlist.get(0));
                setDirectionTextView(tvKdDirection, kdlist.get(1));
            }
            List<String> roclist = timeQuotaBean.getROC();
            if (roclist != null && roclist.size() >= 2) {
                tvRocValue.setText(roclist.get(0));
                setDirectionTextView(tvRocDirection, roclist.get(1));
            }
        }

        if (!ObjectUtils.isEmpty(timeAverageBean)) {
            List<String> ma5list = timeAverageBean.getMA5();
            if (ma5list != null && ma5list.size() >= 2) {
                tvMa5Value.setText(ma5list.get(0));
                setDirectionTextView(tvMa5Direction, ma5list.get(1));
            }
            List<String> ma10list = timeAverageBean.getMA10();
            if (ma10list != null && ma10list.size() >= 2) {
                tvMa10Value.setText(ma10list.get(0));
                setDirectionTextView(tvMa10Direction, ma10list.get(1));
            }
            List<String> ma20list = timeAverageBean.getMA20();
            if (ma20list != null && ma20list.size() >= 2) {
                tvMa20Value.setText(ma20list.get(0));
                setDirectionTextView(tvMa20Direction, ma20list.get(1));
            }
            List<String> ma50list = timeAverageBean.getMA50();
            if (ma50list != null && ma50list.size() >= 2) {
                tvMa50Value.setText(ma50list.get(0));
                setDirectionTextView(tvMa50Direction, ma50list.get(1));
            }
            List<String> ma100list = timeAverageBean.getMA100();
            if (ma100list != null && ma100list.size() >= 2) {
                tvMa100Value.setText(ma100list.get(0));
                setDirectionTextView(tvMa100Direction, ma100list.get(1));
            }
            List<String> ma200list = timeAverageBean.getMA200();
            if (ma200list != null && ma200list.size() >= 2) {
                tvMa200Value.setText(ma200list.get(0));
                setDirectionTextView(tvMa200Direction, ma200list.get(1));
            }
        }
    }

    private void setDirectionTextView(TextView textView, String direction) {
        if ("1".equals(direction)) {
            textView.setText("看多");
            textView.setTextColor(ViewUtil.getKUpColor(mContext));
        } else if ("2".equals(direction)) {
            textView.setText("看空");
            textView.setTextColor(ViewUtil.getKLossColor(mContext));
        } else if ("3".equals(direction)) {
            textView.setText("中性");
            textView.setTextColor(getResources().getColor(R.color.black1));
        } else {
            textView.setText("");
        }
    }

    private void initView() {
        if (showType == FIVE_MIN_TYPE) {
            switchTo5min();
        } else if (showType == ONE_HOUR_TYPE) {
            switchToOneHour();
        } else if (showType == ONE_DAY_TYPE) {
            switchToOneDay();
        }
        setMarketValue();
//        avgIndexRateBar.setClickable(false);
//        avgIndexRateBar.setEnabled(false);
//        avgIndexRateBar.setSelected(false);
//        avgIndexRateBar.setFocusable(false);
//        techIndexRateBar.setClickable(false);
//        techIndexRateBar.setEnabled(false);
//        techIndexRateBar.setSelected(false);
//        techIndexRateBar.setFocusable(false);
    }

    public void setMarketValue() {
        if (ConstantUtil.XAUUSD.equals(p_code)) {
            name.setText("伦敦金");
        } else if (ConstantUtil.XAGUSD.equals(p_code)) {
            name.setText("伦敦银");
        }
        List<HomeAllResultBean.MarketBean> marketList = MyApplication.marketList;
        if (marketList != null && marketList.size() >= 3) {
            String value = "%.2f";
            // 黄金小数点2位，白银小数点3位，美元小数点4位
            HomeAllResultBean.MarketBean marketBean = null;
            if (ConstantUtil.XAUUSD.equals(p_code)) {
                marketBean = marketList.get(0);
                value = "%.2f";
            } else if (ConstantUtil.XAGUSD.equals(p_code)) {
                marketBean = marketList.get(1);
                value = "%.3f";
            }
            money.setText(String.format(value, marketBean.getLast_px()));
            time.setText(TimeUtils.getDataStr(marketBean.getUpdate_time(), FYMDHM) + " GMT +0800");
            double changeDb = marketBean.getPx_change();
            String change = String.format(value, changeDb);
            String rate = String.format("%.2f", marketBean.getPx_change_rate());
            if (changeDb > 0) {
                money.setTextColor(ViewUtil.getKUpColor(mContext));
                riseFallRate.setTextColor(ViewUtil.getKUpColor(mContext));
                riseFallPrice.setTextColor(ViewUtil.getKUpColor(mContext));
                ivRiseFall.setBackground(ViewUtil.getKUpImage(mContext, 2));
                riseFallPrice.setText("+" + change);
                riseFallRate.setText("+" + rate + "%");
            } else {
                money.setTextColor(ViewUtil.getKLossColor(mContext));
                riseFallRate.setTextColor(ViewUtil.getKLossColor(mContext));
                riseFallPrice.setTextColor(ViewUtil.getKLossColor(mContext));
                ivRiseFall.setBackground(ViewUtil.getKLossImage(mContext, 2));
                riseFallPrice.setText(change);
                riseFallRate.setText(rate + "%");
            }
            AppUtils.setCustomFont(getContext(), money);
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && getActivity() != null) {
            scrollToTop();
        }
    }


    public void scrollToTop() {
        if (null != scrollView) {
            scrollView.scrollTo(0, 0);
            scrollView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    boolean isScrollToUp = scrollView.canScrollVertically(-1);
                    if (!isScrollToUp)//是否已经滚动到顶部，防止IndexOutOfBoundsException发生
                    {
                    } else {
                        scrollToTop();
                    }
                }
            }, 100);
        }
    }


    @Override
    public void onDataChange(SignalParamsMo signalParamsMo) {
        this.signalParamsMo = signalParamsMo;
        refreshData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThrea1(EventBusKeyDefine.EventBusMsgData<HashMap<String, List<LiveProgramListMo.LiveProgramBean>>> data) {
        int type = data.getType();
        try {
//            if (EventBusKeyDefine.EVENTBUS_PROGRAM_LIST == type) {
//                if (!ObjectUtils.isEmpty(data) && !ObjectUtils.isEmpty(data.getData())) {
//                    for (String key : data.getData().keySet()) {
//                        String weekDay = key;
//                        if (p_code.equals(weekDay)) {
////                            List<LiveProgramListMo.LiveProgramBean> value = data.getData().get(key);
//                        }
//                    }
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unregistorEventBus();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_5_min, R.id.btn_one_hour, R.id.btn_day})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_5_min:
                if (showType != FIVE_MIN_TYPE) {
                    showType = FIVE_MIN_TYPE;
                    switchTo5min();
                }

                break;
            case R.id.btn_one_hour:
                if (showType != ONE_HOUR_TYPE) {
                    showType = ONE_HOUR_TYPE;
                    switchToOneHour();
                }
                break;
            case R.id.btn_day:
                if (showType != ONE_DAY_TYPE) {
                    showType = ONE_DAY_TYPE;
                    switchToOneDay();
                }
                break;
        }
    }
}

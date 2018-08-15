package com.lingxi.preciousmetal.ui.fragment.trade;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSeekBar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.lingxi.common.base.BaseFragment;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.MyApplication;
import com.lingxi.preciousmetal.common.EventBusKeyDefine;
import com.lingxi.preciousmetal.domain.SignalParamValues;
import com.lingxi.preciousmetal.util.MoneyInputFilterGold;
import com.lingxi.preciousmetal.util.VerifyUtil;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by zhangwei on 2018/4/25.
 */

public class SignalSettingFragment extends BaseFragment {
    @BindView(R.id.tag_name_1)
    TextView tagName1;
    @BindView(R.id.edit_set_1)
    EditText editSet1;
    @BindView(R.id.tv_start_value_1)
    TextView tvStartValue1;
    @BindView(R.id.seek_bar_1)
    AppCompatSeekBar seekBar1;
    @BindView(R.id.tv_end_value_1)
    TextView tvEndValue1;
    @BindView(R.id.layout_signal_1)
    LinearLayout layoutSignal1;
    @BindView(R.id.tag_name_2)
    TextView tagName2;
    @BindView(R.id.edit_set_2)
    EditText editSet2;
    @BindView(R.id.tv_start_value_2)
    TextView tvStartValue2;
    @BindView(R.id.seek_bar_2)
    AppCompatSeekBar seekBar2;
    @BindView(R.id.tv_end_value_2)
    TextView tvEndValue2;
    @BindView(R.id.layout_signal_2)
    LinearLayout layoutSignal2;
    @BindView(R.id.tag_name_3)
    TextView tagName3;
    @BindView(R.id.edit_set_3)
    EditText editSet3;
    @BindView(R.id.tv_start_value_3)
    TextView tvStartValue3;
    @BindView(R.id.seek_bar_3)
    AppCompatSeekBar seekBar3;
    @BindView(R.id.tv_end_value_3)
    TextView tvEndValue3;
    @BindView(R.id.layout_signal_3)
    LinearLayout layoutSignal3;
    @BindView(R.id.tag_name_4)
    TextView tagName4;
    @BindView(R.id.edit_set_4)
    EditText editSet4;
    @BindView(R.id.tv_start_value_4)
    TextView tvStartValue4;
    @BindView(R.id.seek_bar_4)
    AppCompatSeekBar seekBar4;
    @BindView(R.id.tv_end_value_4)
    TextView tvEndValue4;
    @BindView(R.id.layout_signal_4)
    LinearLayout layoutSignal4;
    @BindView(R.id.btn_save)
    TextView btnSave;
    @BindView(R.id.btn_reset)
    TextView btnReset;
    @BindView(R.id.layout_content)
    LinearLayout layoutContent;
    @BindView(R.id.empty_layout)
    LinearLayout emptyLayout;
    @BindView(R.id.tag_name_5)
    TextView tagName5;
    @BindView(R.id.edit_set_5)
    EditText editSet5;
    @BindView(R.id.tv_start_value_5)
    TextView tvStartValue5;
    @BindView(R.id.seek_bar_5)
    AppCompatSeekBar seekBar5;
    @BindView(R.id.tv_end_value_5)
    TextView tvEndValue5;
    @BindView(R.id.layout_signal_5)
    LinearLayout layoutSignal5;
    private View mContentView;
    Unbinder unbinder;
    private String type;
    private static SignalParamValues mSignalParamValues;//指标参数
    private int mSeekBarFactor = 10;
    private int mSeekBar1Min = 1;
    private int mSeekBar1Max = 100;
    private int mSeekBar2Min = 1;
    private int mSeekBar2Max = 100;
    private int mSeekBar3Min = 1;
    private int mSeekBar3Max = 100;
    private int mSeekBar4Min = 1;
    private int mSeekBar4Max = 100;

    public static SignalSettingFragment newInstance(String type) {
        SignalSettingFragment fragmentCommon = new SignalSettingFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        fragmentCommon.setArguments(bundle);
        return fragmentCommon;
    }


    @Override
    public void onViewCreated(@NonNull final View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
        registorEventBus();
        initView();
        bindData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mContentView == null) {
            mContentView = inflater.inflate(R.layout.fragment_signal_setting, container, false);
        }
        unbinder = ButterKnife.bind(this, mContentView);
        return mContentView;
    }

    private void bindData() {
    }

    private void initView() {
        type = getArguments().getString("type");
        mSignalParamValues = MyApplication.getSignalParamValues();
        TextWatcher textWatcher1 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String valueStr = s.toString();
                int value = 0;
                if (!TextUtils.isEmpty(valueStr)) {
                    value = Integer.parseInt(valueStr);
                } else {
                    editSet1.setText("0");
                    value = 0;
                }
                int realvalue = value - mSeekBar1Min;
                if (realvalue < 0) {
                    realvalue = 0;
                }
                seekBar1.setProgress(realvalue * mSeekBarFactor);
            }
        };
        TextWatcher textWatcher2 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String valueStr = s.toString();
                int value = 0;
                if (!TextUtils.isEmpty(valueStr)) {
                    value = Integer.parseInt(valueStr);
                } else {
                    editSet2.setText("0");
                    value = 0;
                }
                int realvalue = value - mSeekBar2Min;
                if (realvalue < 0) {
                    realvalue = 0;
                }
                seekBar2.setProgress(realvalue * mSeekBarFactor);
            }
        };
        TextWatcher textWatcher3 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String valueStr = s.toString();
                int value = 0;
                if (!TextUtils.isEmpty(valueStr)) {
                    value = Integer.parseInt(valueStr);
                } else {
                    editSet3.setText("0");
                    value = 0;
                }
                int realvalue = value - mSeekBar3Min;
                if (realvalue < 0) {
                    realvalue = 0;
                }
                seekBar3.setProgress(realvalue * mSeekBarFactor);
            }
        };
        TextWatcher textWatcher4 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String valueStr = s.toString();
                int value = 0;
                if (!TextUtils.isEmpty(valueStr)) {
                    value = Integer.parseInt(valueStr);
                } else {
                    editSet4.setText("0");
                    value = 0;
                }
                int realvalue = value - mSeekBar4Min;
                if (realvalue < 0) {
                    realvalue = 0;
                }
                seekBar4.setProgress(realvalue * mSeekBarFactor);
            }
        };
        TextWatcher textWatcher5 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String valueStr = s.toString();
                float value = 0f;
                if (!TextUtils.isEmpty(valueStr)) {
                    if (!VerifyUtil.isNumeric(valueStr)) {
                        return;
                    }
                    value = Float.parseFloat(valueStr);
                } else {
                    editSet5.setText("0.1");
                    value = 0.1f;
                }
                float realvalue = value - mSeekBar2Min;
                if (realvalue <= 0f) {
                    realvalue = 0;
//                    editSet5.setText("0.1");
                }
                if (realvalue <= 0.1f) {
                    seekBar5.setProgress(0);
                } else {
                    seekBar5.setProgress(((int) (realvalue * mSeekBarFactor)));
                }
            }
        };
        SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!fromUser)
                    return;
                switch (seekBar.getId()) {
                    case R.id.seek_bar_1:
                        int realProgress = progress / mSeekBarFactor;
                        if (realProgress < mSeekBar1Min) {
                            seekBar.setProgress(progress);
                            editSet1.setText(mSeekBar1Min + "");
                        } else {
                            editSet1.setText((progress / mSeekBarFactor) + "");
                        }
                        break;
                    case R.id.seek_bar_2:
                        int realProgress2 = progress / mSeekBarFactor;
                        if (realProgress2 < mSeekBar2Min) {
                            seekBar.setProgress(progress);
                            editSet2.setText(mSeekBar2Min + "");
                        } else {
                            editSet2.setText((progress / mSeekBarFactor) + "");
                        }
                        break;
                    case R.id.seek_bar_3:
                        int realProgress3 = progress / mSeekBarFactor;
                        if (realProgress3 < mSeekBar3Min) {
                            seekBar.setProgress(progress);
                            editSet3.setText(mSeekBar3Min + "");
                        } else {
                            editSet3.setText((progress / mSeekBarFactor) + "");
                        }
                        break;
                    case R.id.seek_bar_4:
                        int realProgress4 = progress / mSeekBarFactor;
                        if (realProgress4 < mSeekBar4Min) {
                            seekBar.setProgress(progress);
                            editSet4.setText(mSeekBar4Min + "");
                        } else {
                            editSet4.setText((progress / mSeekBarFactor) + "");
                        }
                        break;
                    case R.id.seek_bar_5:
                        int realProgress5 = progress / mSeekBarFactor;
                        if (realProgress5 <= mSeekBar2Min) {
                            editSet5.setText("0.1");
                        } else {
                            editSet5.setText((progress / mSeekBarFactor) + "");
                        }
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
        editSet1.addTextChangedListener(textWatcher1);
        editSet2.addTextChangedListener(textWatcher2);
        editSet3.addTextChangedListener(textWatcher3);
        editSet4.addTextChangedListener(textWatcher4);
        editSet5.addTextChangedListener(textWatcher5);
        editSet5.setFilters(new InputFilter[]{new MoneyInputFilterGold()});
        seekBar1.setOnSeekBarChangeListener(seekBarChangeListener);
        seekBar2.setOnSeekBarChangeListener(seekBarChangeListener);
        seekBar3.setOnSeekBarChangeListener(seekBarChangeListener);
        seekBar4.setOnSeekBarChangeListener(seekBarChangeListener);
        seekBar5.setOnSeekBarChangeListener(seekBarChangeListener);
        initData(false);

    }

    /**
     * @param revertToSystemDefault 恢复系统默认
     */
    private void initData(boolean revertToSystemDefault) {
        switch (type) {
            case "BBI":
                mSeekBar1Min = 1;
                mSeekBar1Max = 100;
                mSeekBar2Min = 1;
                mSeekBar2Max = 100;
                mSeekBar3Min = 1;
                mSeekBar3Max = 100;
                mSeekBar4Min = 1;
                mSeekBar4Max = 100;
                layoutSignal1.setVisibility(View.VISIBLE);
                layoutSignal2.setVisibility(View.VISIBLE);
                layoutSignal3.setVisibility(View.VISIBLE);
                layoutSignal4.setVisibility(View.VISIBLE);
                tagName1.setText("n1");
                tagName2.setText("n2");
                tagName3.setText("n3");
                tagName4.setText("n4");
                editSet1.setText((revertToSystemDefault?3:mSignalParamValues.bbi.value1) + "");
                editSet2.setText((revertToSystemDefault?6:mSignalParamValues.bbi.value2) + "");
                editSet3.setText((revertToSystemDefault?12:mSignalParamValues.bbi.value3) + "");
                editSet4.setText((revertToSystemDefault?24:mSignalParamValues.bbi.value4) + "");
                tvStartValue1.setText("1");
                tvStartValue2.setText("1");
                tvStartValue3.setText("1");
                tvStartValue4.setText("1");
                tvEndValue1.setText("100");
                tvEndValue2.setText("100");
                tvEndValue3.setText("100");
                tvEndValue4.setText("100");
                seekBar1.setMax(mSeekBar1Max * mSeekBarFactor);
                seekBar2.setMax(mSeekBar2Max * mSeekBarFactor);
                seekBar3.setMax(mSeekBar3Max * mSeekBarFactor);
                seekBar4.setMax(mSeekBar4Max * mSeekBarFactor);
                seekBar1.setProgress((revertToSystemDefault?3:mSignalParamValues.bbi.value1) * mSeekBarFactor);
                seekBar2.setProgress((revertToSystemDefault?6:mSignalParamValues.bbi.value2) * mSeekBarFactor);
                seekBar3.setProgress((revertToSystemDefault?12:mSignalParamValues.bbi.value3) * mSeekBarFactor);
                seekBar4.setProgress((revertToSystemDefault?24:mSignalParamValues.bbi.value4) * mSeekBarFactor);
                break;
            case "BOLL":
                mSeekBarFactor = 10;
                mSeekBar1Min = 5;
                mSeekBar1Max = 300;
                mSeekBar2Min = 1;
                mSeekBar2Max = 10;
                layoutSignal1.setVisibility(View.VISIBLE);
                layoutSignal2.setVisibility(View.VISIBLE);
                tagName1.setText("n");
                tagName2.setText("p");
                editSet1.setText((revertToSystemDefault?26:mSignalParamValues.boll.value1) + "");
                editSet2.setText((revertToSystemDefault?2:mSignalParamValues.boll.value2) + "");
                tvStartValue1.setText("5");
                tvStartValue2.setText("1");
                tvEndValue1.setText("300");
                tvEndValue2.setText("10");
                seekBar1.setMax(mSeekBar1Max * mSeekBarFactor);
                seekBar2.setMax(mSeekBar2Max * mSeekBarFactor);
                seekBar1.setProgress((revertToSystemDefault?26:mSignalParamValues.boll.value1) * mSeekBarFactor);
                seekBar2.setProgress((revertToSystemDefault?2:mSignalParamValues.boll.value2) * mSeekBarFactor);
                //float use
//                if (mSignalParamValues.boll.value2 <= 0.1f) {
//                    seekBar5.setProgress(0);
//                } else {
//                    seekBar5.setProgress(((int) (mSignalParamValues.boll.value2 * mSeekBarFactor)));
//                }
                break;
            case "MA":
                mSeekBar1Min = 1;
                mSeekBar1Max = 300;
                mSeekBar2Min = 1;
                mSeekBar2Max = 300;
                mSeekBar3Min = 1;
                mSeekBar3Max = 300;
                mSeekBar4Min = 1;
                mSeekBar4Max = 300;
                layoutSignal1.setVisibility(View.VISIBLE);
                layoutSignal2.setVisibility(View.VISIBLE);
                layoutSignal3.setVisibility(View.VISIBLE);
                layoutSignal4.setVisibility(View.VISIBLE);
                tagName1.setText("l1");
                tagName2.setText("l2");
                tagName3.setText("l3");
                tagName4.setText("l4");
                editSet1.setText((revertToSystemDefault?5:mSignalParamValues.ma.value1) + "");
                editSet2.setText((revertToSystemDefault?10:mSignalParamValues.ma.value2) + "");
                editSet3.setText((revertToSystemDefault?20:mSignalParamValues.ma.value3) + "");
                editSet4.setText((revertToSystemDefault?30:mSignalParamValues.ma.value4) + "");
                tvStartValue1.setText("1");
                tvStartValue2.setText("1");
                tvStartValue3.setText("1");
                tvStartValue4.setText("1");
                tvEndValue1.setText("300");
                tvEndValue2.setText("300");
                tvEndValue3.setText("300");
                tvEndValue4.setText("300");
                seekBar1.setMax(mSeekBar1Max * mSeekBarFactor);
                seekBar2.setMax(mSeekBar2Max * mSeekBarFactor);
                seekBar3.setMax(mSeekBar3Max * mSeekBarFactor);
                seekBar4.setMax(mSeekBar4Max * mSeekBarFactor);
                seekBar1.setProgress((revertToSystemDefault?5:mSignalParamValues.ma.value1) * mSeekBarFactor);
                seekBar2.setProgress((revertToSystemDefault?10:mSignalParamValues.ma.value2) * mSeekBarFactor);
                seekBar3.setProgress((revertToSystemDefault?20:mSignalParamValues.ma.value3) * mSeekBarFactor);
                seekBar4.setProgress((revertToSystemDefault?30:mSignalParamValues.ma.value4) * mSeekBarFactor);
                break;
            case "MIKE":
                mSeekBar1Min = 1;
                mSeekBar1Max = 200;
                layoutSignal1.setVisibility(View.VISIBLE);
                tagName1.setText("n");
                editSet1.setText((revertToSystemDefault?12:mSignalParamValues.mike.value1) + "");
                tvStartValue1.setText("1");
                tvEndValue1.setText("200");
                seekBar1.setMax(mSeekBar1Max * mSeekBarFactor);
                seekBar1.setProgress((revertToSystemDefault?12:mSignalParamValues.mike.value1) * mSeekBarFactor);
                break;
            case "PBX":
            case "DKBY":
            case "QHLSR":
                layoutContent.setVisibility(View.GONE);
                emptyLayout.setVisibility(View.VISIBLE);
                break;
            case "ARBR":
                mSeekBar1Min = 2;
                mSeekBar1Max = 300;
                layoutSignal1.setVisibility(View.VISIBLE);
                tagName1.setText("n");
                editSet1.setText((revertToSystemDefault?26:mSignalParamValues.arbr.value1) + "");
                tvStartValue1.setText("2");
                tvEndValue1.setText("300");
                seekBar1.setMax(mSeekBar1Max * mSeekBarFactor);
                seekBar1.setProgress((revertToSystemDefault?26:mSignalParamValues.arbr.value1) * mSeekBarFactor);
                break;
            case "ATR":
                mSeekBar1Min = 1;
                mSeekBar1Max = 300;
                layoutSignal1.setVisibility(View.VISIBLE);
                tagName1.setText("n");
                editSet1.setText((revertToSystemDefault?14:mSignalParamValues.atr.value1) + "");
                tvStartValue1.setText("1");
                tvEndValue1.setText("300");
                seekBar1.setMax(mSeekBar1Max * mSeekBarFactor);
                seekBar1.setProgress((revertToSystemDefault?14:mSignalParamValues.atr.value1) * mSeekBarFactor);
                break;
            case "BIAS":
                mSeekBar1Min = 1;
                mSeekBar1Max = 300;
                mSeekBar2Min = 1;
                mSeekBar2Max = 300;
                mSeekBar3Min = 1;
                mSeekBar3Max = 300;
                layoutSignal1.setVisibility(View.VISIBLE);
                layoutSignal2.setVisibility(View.VISIBLE);
                layoutSignal3.setVisibility(View.VISIBLE);
                tagName1.setText("l1");
                tagName2.setText("l2");
                tagName3.setText("l3");
                editSet1.setText((revertToSystemDefault?6:mSignalParamValues.bias.value1) + "");
                editSet2.setText((revertToSystemDefault?12:mSignalParamValues.bias.value2) + "");
                editSet3.setText((revertToSystemDefault?24:mSignalParamValues.bias.value3) + "");
                tvStartValue1.setText("1");
                tvStartValue2.setText("1");
                tvStartValue3.setText("1");
                tvEndValue1.setText("300");
                tvEndValue2.setText("300");
                tvEndValue3.setText("300");
                seekBar1.setMax(mSeekBar1Max * mSeekBarFactor);
                seekBar2.setMax(mSeekBar2Max * mSeekBarFactor);
                seekBar3.setMax(mSeekBar3Max * mSeekBarFactor);
                seekBar1.setProgress((revertToSystemDefault?6:mSignalParamValues.bias.value1) * mSeekBarFactor);
                seekBar2.setProgress((revertToSystemDefault?12:mSignalParamValues.bias.value2) * mSeekBarFactor);
                seekBar3.setProgress((revertToSystemDefault?24:mSignalParamValues.bias.value3) * mSeekBarFactor);
                break;
            case "CCI":
                mSeekBar1Min = 2;
                mSeekBar1Max = 100;
                layoutSignal1.setVisibility(View.VISIBLE);
                tagName1.setText("n");
                editSet1.setText((revertToSystemDefault?14:mSignalParamValues.cci.value1) + "");
                tvStartValue1.setText("2");
                tvEndValue1.setText("100");
                seekBar1.setMax(mSeekBar1Max * mSeekBarFactor);
                seekBar1.setProgress((revertToSystemDefault?14:mSignalParamValues.cci.value1) * mSeekBarFactor);
                break;
            case "KD":
                mSeekBar1Min = 1;
                mSeekBar1Max = 100;
                mSeekBar2Min = 2;
                mSeekBar2Max = 40;
                mSeekBar3Min = 2;
                mSeekBar3Max = 40;
                layoutSignal1.setVisibility(View.VISIBLE);
                layoutSignal2.setVisibility(View.VISIBLE);
                layoutSignal3.setVisibility(View.VISIBLE);
                tagName1.setText("n");
                tagName2.setText("m1");
                tagName3.setText("m2");
                editSet1.setText((revertToSystemDefault?9:mSignalParamValues.kd.value1) + "");
                editSet2.setText((revertToSystemDefault?3:mSignalParamValues.kd.value2) + "");
                editSet3.setText((revertToSystemDefault?3:mSignalParamValues.kd.value3) + "");
                tvStartValue1.setText("1");
                tvStartValue2.setText("2");
                tvStartValue3.setText("2");
                tvEndValue1.setText("100");
                tvEndValue2.setText("40");
                tvEndValue3.setText("40");
                seekBar1.setMax(mSeekBar1Max * mSeekBarFactor);
                seekBar2.setMax(mSeekBar2Max * mSeekBarFactor);
                seekBar3.setMax(mSeekBar3Max * mSeekBarFactor);
                seekBar1.setProgress((revertToSystemDefault?9:mSignalParamValues.kd.value1) * mSeekBarFactor);
                seekBar2.setProgress((revertToSystemDefault?3:mSignalParamValues.kd.value2) * mSeekBarFactor);
                seekBar3.setProgress((revertToSystemDefault?3:mSignalParamValues.kd.value3) * mSeekBarFactor);
                break;
            case "KDJ":
                mSeekBar1Min = 1;
                mSeekBar1Max = 100;
                mSeekBar2Min = 2;
                mSeekBar2Max = 40;
                mSeekBar3Min = 2;
                mSeekBar3Max = 40;
                layoutSignal1.setVisibility(View.VISIBLE);
                layoutSignal2.setVisibility(View.VISIBLE);
                layoutSignal3.setVisibility(View.VISIBLE);
                tagName1.setText("n");
                tagName2.setText("m1");
                tagName3.setText("m2");
                editSet1.setText((revertToSystemDefault?9:mSignalParamValues.kdj.value1) + "");
                editSet2.setText((revertToSystemDefault?3:mSignalParamValues.kdj.value2) + "");
                editSet3.setText((revertToSystemDefault?3:mSignalParamValues.kdj.value3) + "");
                tvStartValue1.setText("1");
                tvStartValue2.setText("2");
                tvStartValue3.setText("2");
                tvEndValue1.setText("100");
                tvEndValue2.setText("40");
                tvEndValue3.setText("40");
                seekBar1.setMax(mSeekBar1Max * mSeekBarFactor);
                seekBar2.setMax(mSeekBar2Max * mSeekBarFactor);
                seekBar3.setMax(mSeekBar3Max * mSeekBarFactor);
                seekBar1.setProgress((revertToSystemDefault?9:mSignalParamValues.kdj.value1) * mSeekBarFactor);
                seekBar2.setProgress((revertToSystemDefault?3:mSignalParamValues.kdj.value2) * mSeekBarFactor);
                seekBar3.setProgress((revertToSystemDefault?3:mSignalParamValues.kdj.value3) * mSeekBarFactor);
                break;
            case "LW&R":
                mSeekBar1Min = 1;
                mSeekBar1Max = 100;
                mSeekBar2Min = 2;
                mSeekBar2Max = 40;
                mSeekBar3Min = 2;
                mSeekBar3Max = 40;
                layoutSignal1.setVisibility(View.VISIBLE);
                layoutSignal2.setVisibility(View.VISIBLE);
                layoutSignal3.setVisibility(View.VISIBLE);
                tagName1.setText("n");
                tagName2.setText("m1");
                tagName3.setText("m2");
                editSet1.setText((revertToSystemDefault?9:mSignalParamValues.lwr.value1) + "");
                editSet2.setText((revertToSystemDefault?3:mSignalParamValues.lwr.value2) + "");
                editSet3.setText((revertToSystemDefault?3:mSignalParamValues.lwr.value3) + "");
                tvStartValue1.setText("1");
                tvStartValue2.setText("2");
                tvStartValue3.setText("2");
                tvEndValue1.setText("100");
                tvEndValue2.setText("40");
                tvEndValue3.setText("40");
                seekBar1.setMax(mSeekBar1Max * mSeekBarFactor);
                seekBar2.setMax(mSeekBar2Max * mSeekBarFactor);
                seekBar3.setMax(mSeekBar3Max * mSeekBarFactor);
                seekBar1.setProgress((revertToSystemDefault?9:mSignalParamValues.lwr.value1) * mSeekBarFactor);
                seekBar2.setProgress((revertToSystemDefault?3:mSignalParamValues.lwr.value2) * mSeekBarFactor);
                seekBar3.setProgress((revertToSystemDefault?3:mSignalParamValues.lwr.value3) * mSeekBarFactor);
                break;
            case "MACD":
                mSeekBar1Min = 20;
                mSeekBar1Max = 100;
                mSeekBar2Min = 5;
                mSeekBar2Max = 40;
                mSeekBar3Min = 2;
                mSeekBar3Max = 60;
                layoutSignal1.setVisibility(View.VISIBLE);
                layoutSignal2.setVisibility(View.VISIBLE);
                layoutSignal3.setVisibility(View.VISIBLE);
                tagName1.setText("long");
                tagName2.setText("short");
                tagName3.setText("m");
                editSet1.setText((revertToSystemDefault?26:mSignalParamValues.macd.value1) + "");
                editSet2.setText((revertToSystemDefault?12:mSignalParamValues.macd.value2) + "");
                editSet3.setText((revertToSystemDefault?9:mSignalParamValues.macd.value3) + "");
                tvStartValue1.setText("20");
                tvStartValue2.setText("5");
                tvStartValue3.setText("2");
                tvEndValue1.setText("100");
                tvEndValue2.setText("40");
                tvEndValue3.setText("60");
                seekBar1.setMax(mSeekBar1Max * mSeekBarFactor);
                seekBar2.setMax(mSeekBar2Max * mSeekBarFactor);
                seekBar3.setMax(mSeekBar3Max * mSeekBarFactor);
                seekBar1.setProgress((revertToSystemDefault?26:mSignalParamValues.macd.value1) * mSeekBarFactor);
                seekBar2.setProgress((revertToSystemDefault?12:mSignalParamValues.macd.value2)* mSeekBarFactor);
                seekBar3.setProgress((revertToSystemDefault?9:mSignalParamValues.macd.value3) * mSeekBarFactor);
                break;
            case "RSI":
                mSeekBar1Min = 2;
                mSeekBar1Max = 200;
                mSeekBar2Min = 2;
                mSeekBar2Max = 200;
                mSeekBar3Min = 2;
                mSeekBar3Max = 200;
                layoutSignal1.setVisibility(View.VISIBLE);
                layoutSignal2.setVisibility(View.VISIBLE);
                layoutSignal3.setVisibility(View.VISIBLE);
                tagName1.setText("n1");
                tagName2.setText("n2");
                tagName3.setText("n3");
                editSet1.setText((revertToSystemDefault?6:mSignalParamValues.rsi.value1) + "");
                editSet2.setText((revertToSystemDefault?12:mSignalParamValues.rsi.value2) + "");
                editSet3.setText((revertToSystemDefault?24:mSignalParamValues.rsi.value3) + "");
                tvStartValue1.setText("2");
                tvStartValue2.setText("2");
                tvStartValue3.setText("2");
                tvEndValue1.setText("200");
                tvEndValue2.setText("200");
                tvEndValue3.setText("200");
                seekBar1.setMax(mSeekBar1Max * mSeekBarFactor);
                seekBar2.setMax(mSeekBar2Max * mSeekBarFactor);
                seekBar3.setMax(mSeekBar3Max * mSeekBarFactor);
                seekBar1.setProgress((revertToSystemDefault?6:mSignalParamValues.rsi.value1) * mSeekBarFactor);
                seekBar2.setProgress((revertToSystemDefault?12:mSignalParamValues.rsi.value2) * mSeekBarFactor);
                seekBar3.setProgress((revertToSystemDefault?24:mSignalParamValues.rsi.value3) * mSeekBarFactor);
                break;
            case "WR":
                mSeekBar1Min = 2;
                mSeekBar1Max = 100;
                layoutSignal1.setVisibility(View.VISIBLE);
                tagName1.setText("n");
                editSet1.setText((revertToSystemDefault?14:mSignalParamValues.wr.value1) + "");
                tvStartValue1.setText("2");
                tvEndValue1.setText("100");
                seekBar1.setMax(mSeekBar1Max * mSeekBarFactor);
                seekBar1.setProgress((revertToSystemDefault?14:mSignalParamValues.wr.value1) * mSeekBarFactor);
                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThrea(EventBusKeyDefine.EventBusMsgData<String> data) {
        int type = data.getType();
        try {
            if (EventBusKeyDefine.EVENTBUS_LOGIN_SUCCESS == type || EventBusKeyDefine.EVENTBUS_LOGOUT == type || EventBusKeyDefine.EVENTBUS_USER_INFO_UPDATE == type || EventBusKeyDefine.EVENTBUS_REGISTER_SUCCESS == type) {

            }
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

    @OnClick({R.id.btn_save, R.id.btn_reset})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                String text1 = editSet1.getText().toString();
                String text2 = editSet2.getText().toString();
                String text3 = editSet3.getText().toString();
                String text4 = editSet4.getText().toString();
                String text5 = editSet5.getText().toString();
                int value1 = Integer.parseInt(text1);
                int value2 = Integer.parseInt(text2);
                int value3 = Integer.parseInt(text3);
                int value4 = Integer.parseInt(text4);
                float value5=0;
                if (layoutSignal1.getVisibility() == View.VISIBLE && (value1 < mSeekBar1Min || value1 > mSeekBar1Max)) {
                    ToastUtils.showLong("请输入有效的参数范围,有效的参数范围为(" + mSeekBar1Min + "," + mSeekBar1Max + ")");
                    return;
                }
                if (layoutSignal2.getVisibility() == View.VISIBLE && (value2 < mSeekBar2Min || value2 > mSeekBar2Max)) {
                    ToastUtils.showLong("请输入有效的参数范围,有效的参数范围为(" + mSeekBar2Min + "," + mSeekBar2Max + ")");
                    return;
                }
                if (layoutSignal3.getVisibility() == View.VISIBLE && (value3 < mSeekBar3Min || value3 > mSeekBar3Max)) {
                    ToastUtils.showLong("请输入有效的参数范围,有效的参数范围为(" + mSeekBar3Min + "," + mSeekBar3Max + ")");
                    return;
                }
                if (layoutSignal4.getVisibility() == View.VISIBLE && (value4 < mSeekBar4Min || value4 > mSeekBar4Max)) {
                    ToastUtils.showLong("请输入有效的参数范围,有效的参数范围为(" + mSeekBar4Min + "," + mSeekBar4Max + ")");
                    return;
                }
                if (layoutSignal5.getVisibility() == View.VISIBLE) {
                    if (!VerifyUtil.isNumeric(text5)) {
                        ToastUtils.showLong("数字格式错误，请重新输入");
                        return;
                    }
                    value5 = Float.parseFloat(text5);
                    if ((value5 < 0.1f || value5 > 10f)) {
                        ToastUtils.showLong("请输入有效的参数范围,有效的参数范围为(" + 0.1 + "," + 10 + ")");
                        return;
                    }
                }
                switch (type) {
                    case "BBI":
                        mSignalParamValues.bbi.value1 = value1;
                        mSignalParamValues.bbi.value2 = value2;
                        mSignalParamValues.bbi.value3 = value3;
                        mSignalParamValues.bbi.value4 = value4;
                        break;
                    case "BOLL":
                        mSignalParamValues.boll.value1 = value1;
                        mSignalParamValues.boll.value2 = value2;
                        break;
                    case "MA":
                        mSignalParamValues.ma.value1 = value1;
                        mSignalParamValues.ma.value2 = value2;
                        mSignalParamValues.ma.value3 = value3;
                        mSignalParamValues.ma.value4 = value4;
                        break;
                    case "ARBR":
                        mSignalParamValues.arbr.value1 = value1;
                        break;
                    case "ATR":
                        mSignalParamValues.atr.value1 = value1;
                        break;
                    case "BIAS":
                        mSignalParamValues.bias.value1 = value1;
                        mSignalParamValues.bias.value2 = value2;
                        mSignalParamValues.bias.value3 = value3;
                        break;
                    case "CCI":
                        mSignalParamValues.cci.value1 = value1;
                        break;
                    case "KD":
                        mSignalParamValues.kd.value1 = value1;
                        mSignalParamValues.kd.value2 = value2;
                        mSignalParamValues.kd.value3 = value3;
                        break;
                    case "KDJ":
                        mSignalParamValues.kdj.value1 = value1;
                        mSignalParamValues.kdj.value2 = value2;
                        mSignalParamValues.kdj.value3 = value3;
                        break;
                    case "LW&R":
                        mSignalParamValues.lwr.value1 = value1;
                        mSignalParamValues.lwr.value2 = value2;
                        mSignalParamValues.lwr.value3 = value3;
                        break;
                    case "MACD":
                        mSignalParamValues.macd.value1 = value1;
                        mSignalParamValues.macd.value2 = value2;
                        mSignalParamValues.macd.value3 = value3;
                        break;
                    case "RSI":
                        mSignalParamValues.rsi.value1 = value1;
                        mSignalParamValues.rsi.value2 = value2;
                        mSignalParamValues.rsi.value3 = value3;
                        break;
                    case "WR":
                        mSignalParamValues.wr.value1 = value1;
                        break;
                }
                SignalParamValues.save(mSignalParamValues);
                MyApplication.setSignalParamValues(mSignalParamValues);
                ToastUtils.showLong("设置成功");
                break;
            case R.id.btn_reset:
                initData(true);
                break;
        }
    }
}

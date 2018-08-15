package com.github.tifezh.kchartlib.chart;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;

import com.github.tifezh.kchartlib.R;
import com.github.tifezh.kchartlib.chart.draw.ARBRDraw;
import com.github.tifezh.kchartlib.chart.draw.ATRDraw;
import com.github.tifezh.kchartlib.chart.draw.BBIDraw;
import com.github.tifezh.kchartlib.chart.draw.BIASDraw;
import com.github.tifezh.kchartlib.chart.draw.BOLLDraw;
import com.github.tifezh.kchartlib.chart.draw.CCIDraw;
import com.github.tifezh.kchartlib.chart.draw.DKBYDraw;
import com.github.tifezh.kchartlib.chart.draw.IndexDrawUtil;
import com.github.tifezh.kchartlib.chart.draw.KDDraw;
import com.github.tifezh.kchartlib.chart.draw.KDJDraw;
import com.github.tifezh.kchartlib.chart.draw.LWRDraw;
import com.github.tifezh.kchartlib.chart.draw.MACDDraw;
import com.github.tifezh.kchartlib.chart.draw.MADraw;
import com.github.tifezh.kchartlib.chart.draw.MIKEDraw;
import com.github.tifezh.kchartlib.chart.draw.MainDraw;
import com.github.tifezh.kchartlib.chart.draw.PBXDraw;
import com.github.tifezh.kchartlib.chart.draw.RSIDraw;
import com.github.tifezh.kchartlib.chart.draw.WRDraw;
import com.github.tifezh.kchartlib.utils.ViewUtil;

import java.util.Map;

/**
 * k线图
 * Created by tian on 2016/5/20.
 */

public class KChartView extends BaseKChartView {
    ProgressBar mProgressBar;
    private boolean isRefreshing=false;
    private boolean isLoadMoreEnd=false;
    private boolean mLastScrollEnable;
    private boolean mLastScaleEnable;

    private KChartRefreshListener mRefreshListener;

    private MainDraw mMainDraw;//滑动view
    private MADraw mMADraw;
    private BBIDraw mBBIDraw;
    private MIKEDraw mMIKEDraw;
    private PBXDraw mPBXDraw;
    private MACDDraw mMACDDraw;
    private BOLLDraw mBOLLDraw;
    private RSIDraw mRSIDraw;
    private KDJDraw mKDJDraw;
//    private VolumeDraw mVolumeDraw;
    private KDDraw mKDDraw;
    private ARBRDraw mARBRDraw;
    private ATRDraw mATRDraw;
    private BIASDraw mBIASDraw;
    private WRDraw mWRDraw;
    private CCIDraw mCCIDraw;
    private LWRDraw mLWRDraw;
    private DKBYDraw mDKBYDraw;

    public KChartView(Context context) {
        this(context, null);
    }

    public KChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        initAttrs(attrs);
    }

    public void setIndexParams(Map<String, int[]> map) {
        mARBRDraw.params = map.get("ARBR");
        mATRDraw.params = map.get("ATR");
        mBBIDraw.params = map.get("BBI");
        mBIASDraw.params = map.get("BIAS");
        mKDDraw.params = map.get("KD");
        mKDJDraw.params = map.get("KDJ");
        mLWRDraw.params = map.get("LWR");
        mMACDDraw.params = map.get("MACD");
        mMADraw.params = map.get("MA");
        mMIKEDraw.params = map.get("MIKE");
        mRSIDraw.params = map.get("RSI");
        mWRDraw.params = map.get("WR");
        mBOLLDraw.params = map.get("BOLL");
        mCCIDraw.params = map.get("CCI");
    }

    private void initView() {
        mProgressBar=new ProgressBar(getContext());
        LayoutParams layoutParams = new LayoutParams(dp2px(50), dp2px(50));
        layoutParams.addRule(CENTER_IN_PARENT);
        addView(mProgressBar,layoutParams);
        mProgressBar.setVisibility(GONE);

        mMainDraw=new MainDraw(this);
        setMainDraw(mMainDraw);

        mMADraw = new MADraw(this);
        mBBIDraw = new BBIDraw(this);
        mBOLLDraw=new BOLLDraw(this);
        mPBXDraw = new PBXDraw(this);
        mMIKEDraw = new MIKEDraw(this);

        addMainChildDraw("MA", mMADraw);
        addMainChildDraw("BBI", mBBIDraw);
        addMainChildDraw("BOLL",mBOLLDraw);
        addMainChildDraw("MIKE", mMIKEDraw);
        addMainChildDraw("PBX", mPBXDraw);

//        mVolumeDraw=new VolumeDraw(this);
        mMACDDraw=new MACDDraw(this);
        mKDJDraw=new KDJDraw(this);
        mRSIDraw=new RSIDraw(this);
        mKDDraw = new KDDraw(this);
        mARBRDraw = new ARBRDraw(this);
        mATRDraw = new ATRDraw(this);
        mBIASDraw = new BIASDraw(this);
        mWRDraw = new WRDraw(this);
        mCCIDraw = new CCIDraw(this);
        mLWRDraw = new LWRDraw(this);
        mDKBYDraw = new DKBYDraw(this);

        addChildDraw("ARBR", mARBRDraw);
        addChildDraw("ATR", mATRDraw);
        addChildDraw("BIAS", mBIASDraw);
        addChildDraw("CCI", mCCIDraw);
        addChildDraw("DKBY", mDKBYDraw);
        addChildDraw("KD", mKDDraw);
        addChildDraw("KDJ", mKDJDraw);
        addChildDraw("LW&R", mLWRDraw);
        addChildDraw("MACD",mMACDDraw);
        addChildDraw("RSI", mRSIDraw);
        addChildDraw("W&R", mWRDraw);
//        addChildDraw("VOL",mVolumeDraw);
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.KChartView);
        if(array!=null)
        {
            try {
                //public
               // setPointWidth(array.getDimension(R.styleable.KChartView_kc_point_width,getDimension(R.dimen.chart_point_width)));
                setTextSize(array.getDimension(R.styleable.KChartView_kc_text_size,getDimension(R.dimen.chart_text_size)));
                setTextColor(array.getColor(R.styleable.KChartView_kc_text_color,getColor(R.color.chart_text)));
                setLineWidth(array.getDimension(R.styleable.KChartView_kc_line_width,getDimension(R.dimen.chart_line_width)));
                setBackgroundColor(array.getColor(R.styleable.KChartView_kc_background_color,getColor(R.color.chart_background)));
                setSelectedLineColor(array.getColor(R.styleable.KChartView_kc_selected_line_color,getColor(R.color.chart_text)));
                setSelectedLineWidth(array.getDimension(R.styleable.KChartView_kc_selected_line_width,getDimension(R.dimen.chart_line_width)));
                setGridLineWidth(array.getDimension(R.styleable.KChartView_kc_grid_line_width,getDimension(R.dimen.chart_grid_line_width)));
                setGridLineColor(array.getColor(R.styleable.KChartView_kc_grid_line_color,getColor(R.color.chart_grid_line)));

                int color1 = ActivityCompat.getColor(getContext(),R.color.chart_line_1);
                int color2 = ActivityCompat.getColor(getContext(),R.color.chart_line_2);
                int color3 = ActivityCompat.getColor(getContext(),R.color.chart_line_3);
                int color4 = ActivityCompat.getColor(getContext(),R.color.chart_line_4);
                int color5 = ActivityCompat.getColor(getContext(),R.color.chart_line_5);
                int color6 = ActivityCompat.getColor(getContext(),R.color.chart_line_6);

                //macd
                setMACDWidth(array.getDimension(R.styleable.KChartView_kc_macd_width,getDimension(R.dimen.chart_candle_width)));
                setDIFColor(color1);
                setDEAColor(color2);
                setMACDColor(color3);

                //kdj
                setKColor(color1);
                setDColor(color2);
                setJColor(color3);
                //rsi
                setRSI1Color(color1);
                setRSI2Color(color2);
                setRSI3Color(color3);
                //boll
                setMbColor(color1);
                setUpColor(color2);
                setDnColor(color3);

                // mike
                mMIKEDraw.setMRColor(color1);
                mMIKEDraw.setWRColor(color2);
                mMIKEDraw.setSRColor(color3);
                mMIKEDraw.setMSColor(color4);
                mMIKEDraw.setWSColor(color5);
                mMIKEDraw.setSSColor(color6);

                // pbx
                mPBXDraw.setPBX1Color(color1);
                mPBXDraw.setPBX2Color(color2);
                mPBXDraw.setPBX3Color(color3);
                mPBXDraw.setPBX4Color(color4);
                // ma
                setMa1Color(color1);
                setMa2Color(color2);
                setMa3Color(color3);
                setMa4Color(color4);
                //bbi
                setBBIColor(color1);
                //arbr
                mARBRDraw.setARColor(color1);
                mARBRDraw.setBRColor(color2);
                //atr
                mATRDraw.setTRColor(color1);
                mATRDraw.setATRColor(color2);

                //wr
                mWRDraw.setWR1Color(color1);
                //cci
                mCCIDraw.setCCIColor(color1);

                //lwr
                mLWRDraw.setLWR1Color(color1);
                mLWRDraw.setLWR2Color(color2);

                //birs
                mBIASDraw.setBIAS1Color(color1);
                mBIASDraw.setBIAS2Color(color2);
                mBIASDraw.setBIAS3Color(color3);

                //dkby
                mDKBYDraw.setDKBY1Color(color1);
                mDKBYDraw.setDKBY2Color(color2);
                mDKBYDraw.setDKBY3Color(color3);
                mDKBYDraw.setDKBY4Color(color4);

                //main
                setCandleWidth(array.getDimension(R.styleable.KChartView_kc_candle_width,getDimension(R.dimen.chart_candle_width)));
                setCandleLineWidth(array.getDimension(R.styleable.KChartView_kc_candle_line_width,getDimension(R.dimen.chart_candle_line_width)));
                setSelectorBackgroundColor(array.getColor(R.styleable.KChartView_kc_selector_background_color,getColor(R.color.chart_selector)));
                setSelectorTextSize(array.getDimension(R.styleable.KChartView_kc_selector_text_size,getDimension(R.dimen.chart_selector_text_size)));
                setCandleSolid(array.getBoolean(R.styleable.KChartView_kc_candle_solid,true));
                //tab
                mKChartTabView.setIndicatorColor(array.getColor(R.styleable.KChartView_kc_tab_indicator_color,getColor(R.color.chart_tab_indicator)));
                mKChartTabView.setBackgroundColor(array.getColor(R.styleable.KChartView_kc_tab_background_color,getColor(R.color.chart_tab_background)));
                mKChartMainTabView.setIndicatorColor(array.getColor(R.styleable.KChartView_kc_tab_indicator_color,getColor(R.color.chart_tab_indicator)));
                mKChartMainTabView.setBackgroundColor(array.getColor(R.styleable.KChartView_kc_tab_background_color,getColor(R.color.chart_tab_background)));
                ColorStateList colorStateList = array.getColorStateList(R.styleable.KChartView_kc_tab_text_color);
                if(colorStateList==null)
                {
                    mKChartTabView.setTextColor(ContextCompat.getColorStateList(getContext(),R.color.tab_text_color_selector));
                    mKChartMainTabView.setTextColor(ContextCompat.getColorStateList(getContext(),R.color.tab_text_color_selector));
                }
                else {
                    mKChartTabView.setTextColor(colorStateList);
                    mKChartMainTabView.setTextColor(colorStateList);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally {
                array.recycle();
            }
        }
    }

    private float getDimension(@DimenRes int resId)
    {
        return getResources().getDimension(resId);
    }

    private int getColor(@ColorRes int resId)
    {
        return ContextCompat.getColor(getContext(),resId);
    }

    @Override
    public void onLeftSide() {
        showLoading();
    }

    @Override
    public void onRightSide() {
    }

    public void showLoading()
    {
        if(!isLoadMoreEnd &&!isRefreshing)
        {
            isRefreshing=true;
            if(mProgressBar!=null)
            {
                mProgressBar.setVisibility(View.VISIBLE);
            }
            if(mRefreshListener!=null)
            {
                mRefreshListener.onLoadMoreBegin(this);
            }
            mLastScaleEnable =isScaleEnable();
            mLastScrollEnable=isScrollEnable();
            super.setScrollEnable(false);
            super.setScaleEnable(false);
        }
    }

    public void hideLoading(){
        if(mProgressBar!=null)
        {
            mProgressBar.setVisibility(View.GONE);
        }
        super.setScrollEnable(mLastScrollEnable);
        super.setScaleEnable(mLastScaleEnable);
    }

    public void setCurrentType(int currentType){
        mMADraw.kShowType = currentType;
        mMainDraw.cuurentkType = currentType;
    }


    /**
     * 刷新完成
     */
    public void refreshComplete()
    {
        isRefreshing=false;
        hideLoading();
    }

    /**
     * 刷新完成，没有数据
     */
    public void refreshEnd()
    {
        isLoadMoreEnd =true;
        isRefreshing=false;
        hideLoading();
    }

    /**
     * 重置加载更多
     */
    public void resetLoadMoreEnd() {
        isLoadMoreEnd=false;
    }

    public interface KChartRefreshListener{
        /**
         * 加载更多
         * @param chart
         */
        void onLoadMoreBegin(KChartView chart);
    }

    @Override
    public void setScaleEnable(boolean scaleEnable) {
        if(isRefreshing)
        {
            throw new IllegalStateException("请勿在刷新状态设置属性");
        }
        super.setScaleEnable(scaleEnable);

    }

    @Override
    public void setScrollEnable(boolean scrollEnable) {
        if(isRefreshing)
        {
            throw new IllegalStateException("请勿在刷新状态设置属性");
        }
        super.setScrollEnable(scrollEnable);
    }

    /**
     * 设置DIF颜色
     */
    public void setDIFColor(int color) {
        mMACDDraw.setDIFColor(color);
    }

    /**
     * 设置DEA颜色
     */
    public void setDEAColor(int color) {
        mMACDDraw.setDEAColor(color);
    }

    /**
     * 设置MACD颜色
     */
    public void setMACDColor(int color) {
        mMACDDraw.setMACDColor(color);
    }

    /**
     * 设置MACD的宽度
     * @param MACDWidth
     */
    public void setMACDWidth(float MACDWidth) {
        mMACDDraw.setMACDWidth(MACDWidth);
    }

    /**
     * 设置BBI的颜色
     * @param color
     */
    public void setBBIColor(int color) {
        mBBIDraw.setColor(color);
    }

    /**
     * 设置up颜色
     */
    public void setUpColor(int color) {
        mBOLLDraw.setUpColor(color);
    }

    /**
     * 设置mb颜色
     * @param color
     */
    public void setMbColor(int color) {
        mBOLLDraw.setMbColor(color);
    }

    /**
     * 设置dn颜色
     */
    public void setDnColor(int color) {
        mBOLLDraw.setDnColor(color);
    }

    /**
     * 设置K颜色
     */
    public void setKColor(int color) {
        mKDJDraw.setKColor(color);
        mKDDraw.setKColor(color);
    }

    /**
     * 设置D颜色
     */
    public void setDColor(int color) {
       mKDJDraw.setDColor(color);
       mKDDraw.setDColor(color);
    }

    /**
     * 设置J颜色
     */
    public void setJColor(int color) {
        mKDJDraw.setJColor(color);
    }

    /**
     * 设置ma5颜色
     * @param color
     */
    public void setMa1Color(int color) {
        mMADraw.setMa1Color(color);
//        mVolumeDraw.setMa5Color(color);
    }

    /**
     * 设置ma10颜色
     * @param color
     */
    public void setMa2Color(int color) {
        mMADraw.setMa2Color(color);
//        mVolumeDraw.setMa10Color(color);
    }

    /**
     * 设置ma20颜色
     * @param color
     */
    public void setMa3Color(int color) {
        mMADraw.setMa3Color(color);
    }

    public void setMa4Color(int color) {
        mMADraw.setMa4Color(color);
    }

    /**
     * 设置选择器文字大小
     * @param textSize
     */
    public void setSelectorTextSize(float textSize){
        mMainDraw.setSelectorTextSize(textSize);
    }

    public void setSelectorTextColor(int color){
        mMainDraw.setSelectorTextColor(color);
    }

    public void setOnChangedIndicatorsListener(MainDraw.OnChangedIndicatorsListener listener){
        mMainDraw.setOnChangedIndicatorsListener(listener);
    }

    public void resultKPaintColor() {
        mMainDraw.resultKPaintColor();
    }

    /**
     * 设置选择器背景
     * @param color
     */
    public void setSelectorBackgroundColor(int color) {
        mMainDraw.setSelectorBackgroundColor(color);
    }

    /**
     * 设置蜡烛宽度
     * @param candleWidth
     */
    public void setCandleWidth(float candleWidth) {
        mMainDraw.setCandleWidth(candleWidth);
    }

    /**
     * 设置蜡烛线宽度
     * @param candleLineWidth
     */
    public void setCandleLineWidth(float candleLineWidth) {
        mMainDraw.setCandleLineWidth(candleLineWidth);
    }

    /**
     * 蜡烛是否空心
     */
    public void setCandleSolid(boolean candleSolid) {
        mMainDraw.setCandleSolid(candleSolid);
    }

    /**
     * k线类型
     */
    public void setShowType(int type) {
        mMainDraw.setShowType(type);
    }

    public void setRSI1Color(int color) {
        mRSIDraw.setRSI1Color(color);
    }

    public void setRSI2Color(int color) {
        mRSIDraw.setRSI2Color(color);
    }

    public void setRSI3Color(int color) {
        mRSIDraw.setRSI3Color(color);
    }

    @Override
    public void setTextSize(float textSize) {
        super.setTextSize(textSize);
        mMADraw.setTextSize(textSize);
        mBOLLDraw.setTextSize(textSize);
        mBBIDraw.setTextSize(textSize);
        mPBXDraw.setTextSize(textSize);
        mMIKEDraw.setTextSize(textSize);

        mRSIDraw.setTextSize(textSize);
        mMACDDraw.setTextSize(textSize);
        mKDJDraw.setTextSize(textSize);
//        mVolumeDraw.setTextSize(textSize);
        mKDDraw.setTextSize(textSize);
        mARBRDraw.setTextSize(textSize);
        mATRDraw.setTextSize(textSize);
        mBIASDraw.setTextSize(textSize);
        mWRDraw.setTextSize(textSize);
        mCCIDraw.setTextSize(textSize);
        mLWRDraw.setTextSize(textSize);
        mDKBYDraw.setTextSize(textSize);

        IndexDrawUtil.getInstance().setTextSize(textSize);
    }

    @Override
    public void setLineWidth(float lineWidth) {
        super.setLineWidth(lineWidth);
        mMADraw.setLineWidth(lineWidth);
        mBOLLDraw.setLineWidth(lineWidth);
        mRSIDraw.setLineWidth(lineWidth);
        mMACDDraw.setLineWidth(lineWidth);
        mKDJDraw.setLineWidth(lineWidth);
//        mVolumeDraw.setLineWidth(lineWidth);
        mBBIDraw.setLineWidth(lineWidth);
        mPBXDraw.setLineWidth(lineWidth);
        mMIKEDraw.setLineWidth(lineWidth);
        mKDDraw.setLineWidth(lineWidth);
        mARBRDraw.setLineWidth(lineWidth);
        mATRDraw.setLineWidth(lineWidth);
        mBIASDraw.setLineWidth(lineWidth);
        mWRDraw.setLineWidth(lineWidth);
        mCCIDraw.setLineWidth(lineWidth);
        mLWRDraw.setLineWidth(lineWidth);
        mDKBYDraw.setLineWidth(lineWidth);

        IndexDrawUtil.getInstance().setLineWidth(lineWidth);
    }

    @Override
    public void setTextColor(int color) {
        super.setTextColor(color);
        mMainDraw.setSelectorTextColor(color);
    }

    /**
     * 设置刷新监听
     */
    public void setRefreshListener(KChartRefreshListener refreshListener) {
        mRefreshListener = refreshListener;
    }
}

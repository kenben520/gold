package com.github.tifezh.kchartlib.chart.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.github.tifezh.kchartlib.R;
import com.github.tifezh.kchartlib.chart.BaseKChartView;
import com.github.tifezh.kchartlib.chart.base.IMainChartDraw;
import com.github.tifezh.kchartlib.chart.entity.ICandle;
import com.github.tifezh.kchartlib.chart.entity.IKLine;
import com.github.tifezh.kchartlib.chart.base.IChartDraw;
import com.github.tifezh.kchartlib.chart.base.IValueFormatter;
import com.github.tifezh.kchartlib.chart.formatter.ValueFormatter;
import com.github.tifezh.kchartlib.utils.ViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 主图的实现类
 * Created by tifezh on 2016/6/14.
 */

public class MainDraw implements IMainChartDraw<ICandle> {

    private float mCandleWidth = 0;
    private float mCandleLineWidth = 0;
    private Paint mRedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mGreenPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    // open==close时显示的价格
    private Paint mGrayPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mkLinePaint = new Paint();

    private IChartDraw mChildDraw;
    private Paint mSelectorTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mSelectorBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Context mContext;
    public int kType = 0;
    public int cuurentkType = 0;
    private boolean mCandleSolid = true;
    private BaseKChartView mChartView;

    public MainDraw(BaseKChartView view) {
        Context context=view.getContext();
        this.mChartView = view;
        mContext = context;
//        mRedPaint.setColor(ContextCompat.getColor(context,R.color.chart_red));
//        mGreenPaint.setColor(ContextCompat.getColor(context,R.color.chart_green));
        mRedPaint.setColor(ViewUtil.getKUpColor(mContext));
        mGreenPaint.setColor(ViewUtil.getKLossColor(mContext));
        mGrayPaint.setColor(ContextCompat.getColor(context,R.color.chart_gray));

        mkLinePaint.setAntiAlias(true);
        mkLinePaint.setStyle(Paint.Style.FILL);
        mkLinePaint.setStrokeWidth(2);
        mkLinePaint.setColor(Color.BLACK);
        mkLinePaint.setStrokeWidth(2);
    }

    @Override
    public void setChildDraw(IChartDraw chartDraw) {
        this.mChildDraw = chartDraw;
        mChartView.invalidate();
    }

    @Override
    public void drawTranslated(@Nullable ICandle lastPoint, @NonNull ICandle curPoint, float lastX, float curX, @NonNull Canvas canvas, @NonNull BaseKChartView view, int position) {
        drawCandle(view, canvas, curX, curPoint.getHighPrice(), curPoint.getLowPrice(), curPoint.getOpenPrice(), curPoint.getClosePrice());

        if(this.mChildDraw != null){
            this.mChildDraw.drawTranslated(lastPoint, curPoint, lastX, curX, canvas, view, position);
        }
        if (kType == 2) {
            view.drawMainLine(canvas, mkLinePaint, lastX, lastPoint.getClosePrice(), curX, curPoint.getClosePrice());
        }
    }

    @Override
    public void drawText(@NonNull Canvas canvas, @NonNull BaseKChartView view, int position, float x, float y) {
        if(this.mChildDraw != null){
            this.mChildDraw.drawText(canvas, view, position, x, y);
        }

        if (view.isLongPress()) {
            int index = view.getSelectedIndex();
            ICandle point = (ICandle) view.getItem(index);
            indicatorsListener.onIndicatorsChanged(point.getOpenPrice(),point.getHighPrice(),point.getLowPrice(),point.getClosePrice());
            drawSelector(view, canvas);
        }
    }


    @Override
    public float getMaxValue(ICandle point) {
        if(this.mChildDraw!=null){
            return Math.max(point.getHighPrice(), this.mChildDraw.getMaxValue(point));
        } else {
            return point.getHighPrice();
        }
    }

    @Override
    public float getMinValue(ICandle point) {
        if(this.mChildDraw!=null){
            return Math.min(point.getLowPrice(), this.mChildDraw.getMinValue(point));
        } else {
            return point.getLowPrice();
        }
    }

    @Override
    public IValueFormatter getValueFormatter() {
        return new ValueFormatter();
    }

    /**
     * 画Candle
     * @param canvas
     * @param x      x轴坐标
     * @param high   最高价
     * @param low    最低价
     * @param open   开盘价
     * @param close  收盘价
     */
    private void drawCandle(BaseKChartView view, Canvas canvas, float x, float high, float low, float open, float close) {
        high = view.getMainY(high);
        low = view.getMainY(low);
        open = view.getMainY(open);
        close = view.getMainY(close);
        float r = mCandleWidth / 2 ;
        float scaleX = view.getScaleX();
        float lineR = mCandleLineWidth / scaleX / 2;

        if (kType==0){
            if (open > close) {
                mRedPaint.setStrokeWidth(mCandleLineWidth / scaleX);
                //实心
                if(!mCandleSolid) {
                    canvas.drawRect(x - r, close, x + r, open, mRedPaint);
                    canvas.drawRect(x - lineR, high, x + lineR, low, mRedPaint);
                }   else {
                    canvas.drawLine(x, high, x, close, mRedPaint);
                    canvas.drawLine(x, open, x, low, mRedPaint);
                    canvas.drawLine(x - r , open, x - r , close, mRedPaint);
                    canvas.drawLine(x + r , open, x + r , close, mRedPaint);
                    mRedPaint.setStrokeWidth(mCandleLineWidth);
                    canvas.drawLine(x - r, open, x + r, open, mRedPaint);
                    canvas.drawLine(x - r, close, x + r, close, mRedPaint);
                }
            } else if (open < close) {
                mGreenPaint.setStrokeWidth(mCandleLineWidth / scaleX);
                canvas.drawRect(x - r, open, x + r, close, mGreenPaint);
                canvas.drawLine(x, high, x, low, mGreenPaint);
            } else {
                mGrayPaint.setStrokeWidth(mCandleLineWidth / scaleX);
                canvas.drawRect(x - r, open, x + r, close + 1, mGrayPaint);
                canvas.drawLine(x, high, x, low, mGrayPaint);
            }
        } else if (kType==1) {
            Paint paint = null;
            if (open > close) {
                paint = mRedPaint;
            } else if(open < close) {
                paint = mGreenPaint;
            } else {
                paint = mGrayPaint;
            }
            paint.setStrokeWidth(mCandleLineWidth / scaleX);
            canvas.drawLine(x, high, x, low, paint);
            paint.setStrokeWidth(mCandleLineWidth);
            canvas.drawLine(x - r, open, x, open, paint);
            canvas.drawLine(x, close, x + r, close, paint);
        }
    }


    private float maxW;
    /**
     * draw选择器
     * @param view
     * @param canvas
     */
    private void drawSelector(BaseKChartView view, Canvas canvas) {
        Paint.FontMetrics metrics = mSelectorTextPaint.getFontMetrics();
        float textHeight = metrics.descent - metrics.ascent;

        int index = view.getSelectedIndex();
        float padding = ViewUtil.Dp2Px(mContext, 5);
        float margin = ViewUtil.Dp2Px(mContext, 5);
        float width = 0;
        float left;
        float top = view.getTopPadding();
        float height = padding * 10 + textHeight * 7;

        ICandle point = (ICandle) view.getItem(index);

        List<String> strings = new ArrayList<>();
        strings.add(view.formatDateTime(view.getAdapter().getDate(index)));
        strings.add("高:" + point.getHighPrice());
        strings.add("低:" + point.getLowPrice());
        strings.add("开:" + point.getOpenPrice());
        strings.add("收:" + point.getClosePrice());
        if (index>1){
            ICandle topPoint = (ICandle) view.getItem(index-1);
            float px_change = point.getClosePrice() - topPoint.getClosePrice();
            float px_changeRate = px_change/topPoint.getClosePrice()*100;
            strings.add("涨跌幅:" + String.format("%.2f",px_changeRate)+"%");
            strings.add("涨跌:" + String.format("%.2f",px_change));
        }

        for (String s : strings) {
            width = Math.max(width, mSelectorTextPaint.measureText(s));
        }
        width += padding * 2;
        if (width>maxW){
            maxW = width;
        }
        float x = view.translateXtoX(view.getX(index));
        if (x > view.getChartWidth() / 2) {
            left = margin;
        } else {
            left = view.getChartWidth() - maxW - margin;
        }

        if (cuurentkType==1){
            top = 5;
        }

        RectF r = new RectF(left, top, left + maxW, top + height);
        canvas.drawRoundRect(r, padding, padding, mSelectorBackgroundPaint);
        float y = top + padding * 2 + (textHeight - metrics.bottom - metrics.top) / 2;

        for (String s : strings) {
            canvas.drawText(s, left + padding, y, mSelectorTextPaint);
            y += textHeight + padding;
        }
    }

    /**
     * 设置蜡烛宽度
     * @param candleWidth
     */
    public void setCandleWidth(float candleWidth) {
        mCandleWidth = candleWidth;
    }

    /**
     * 设置蜡烛线宽度
     * @param candleLineWidth
     */
    public void setCandleLineWidth(float candleLineWidth) {
        mCandleLineWidth = candleLineWidth;
    }

    /**
     * 设置蜡烛线宽度 重新设置蜡烛图颜色
     */
    public void resultKPaintColor() {
        mRedPaint.setColor(ViewUtil.getKUpColor(mContext));
        mGreenPaint.setColor(ViewUtil.getKLossColor(mContext));
        mGrayPaint.setColor(ContextCompat.getColor(mContext,R.color.chart_gray));
    }

    /**
     * 设置选择器文字颜色
     * @param color
     */
    public void setSelectorTextColor(int color) {
        mSelectorTextPaint.setColor(color);
    }

    /**
     * 设置选择器文字大小
     * @param textSize
     */
    public void setSelectorTextSize(float textSize){
        mSelectorTextPaint.setTextSize(textSize);
    }

    /**
     * 设置选择器背景
     * @param color
     */
    public void setSelectorBackgroundColor(int color) {
        mSelectorBackgroundPaint.setColor(color);
    }


    /**
     * 蜡烛是否实心
     */
    public void setCandleSolid(boolean candleSolid) {
        mCandleSolid = candleSolid;
    }

    public void setShowType(int type){
        this.kType = type;
    }

    /**
     * 选中点变化时的监听
     */
    public interface OnChangedIndicatorsListener {
        void onIndicatorsChanged(float Open, float max,float low,float close);
    }

    private OnChangedIndicatorsListener indicatorsListener;

    public void setOnChangedIndicatorsListener(OnChangedIndicatorsListener listener){
        indicatorsListener = listener;
    }

}

package com.github.tifezh.kchartlib.chart.draw;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.tifezh.kchartlib.chart.BaseKChartView;
import com.github.tifezh.kchartlib.chart.base.IChartDraw;
import com.github.tifezh.kchartlib.chart.base.IValueFormatter;
import com.github.tifezh.kchartlib.chart.entity.IDKBY;
import com.github.tifezh.kchartlib.chart.formatter.ValueFormatter;

/**
 * Created by zhangwei on 2018/7/12.
 */

public class DKBYDraw implements IChartDraw<IDKBY> {
    private Paint mDKBY1TextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mDKBY2TextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mDKBY1Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mDKBY2Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mDKBY3Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mDKBY4Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float mTextSize = 0;

    public DKBYDraw(BaseKChartView view) {
        mDKBY1Paint.setStyle(Paint.Style.STROKE);
        mDKBY2Paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public void drawTranslated(@Nullable IDKBY lastPoint, @NonNull IDKBY curPoint, float lastX, float curX, @NonNull Canvas canvas, @NonNull BaseKChartView view, int position) {
        view.drawChildCircle(canvas, mDKBY1Paint, lastX, lastPoint.getSell(), curX, curPoint.getSell());
        view.drawChildCircle(canvas, mDKBY2Paint, lastX, lastPoint.getBuy(), curX, curPoint.getBuy());
        view.drawChildLine(canvas, mDKBY3Paint, lastX, lastPoint.getEne1(), curX, curPoint.getEne1());
        view.drawChildLine(canvas, mDKBY4Paint, lastX, lastPoint.getEne2(), curX, curPoint.getEne2());
    }

    @Override
    public void drawText(@NonNull Canvas canvas, @NonNull BaseKChartView view, int position, float x, float y) {
        String text = "";
        float left = x;
        IDKBY point = (IDKBY) view.getItem(position);
        text = "SELL:" + view.formatValue(point.getSell()) + " ";
        canvas.drawText(text, x, y, mDKBY1TextPaint);
        x += mDKBY1TextPaint.measureText(text);
        text = "BUY:" + view.formatValue(point.getBuy()) + " ";
        canvas.drawText(text, x, y, mDKBY2TextPaint);
        x += mDKBY2TextPaint.measureText(text);
        text = "ENE1:" + view.formatValue(point.getEne1()) + " ";
        canvas.drawText(text, x, y, mDKBY3Paint);
       // x += mDKBY3Paint.measureText(text);

        y+=mTextSize;
        text = "ENE2:" + view.formatValue(point.getEne1()) + " ";
        canvas.drawText(text, left, y, mDKBY4Paint);
    }


    @Override
    public float getMaxValue(IDKBY point) {
        return Math.max(Math.max(point.getSell(), point.getBuy()), Math.max(point.getEne1(), point.getEne2()));
    }

    @Override
    public float getMinValue(IDKBY point) {
        return Math.min(Math.min(point.getSell(), point.getBuy()), Math.min(point.getEne1(), point.getEne2()));
    }

    @Override
    public IValueFormatter getValueFormatter() {
        return new ValueFormatter();
    }

    public void setDKBY1Color(int color) {
        mDKBY1Paint.setColor(color);
        mDKBY1TextPaint.setColor(color);
    }

    public void setDKBY2Color(int color) {
        mDKBY2Paint.setColor(color);
        mDKBY2TextPaint.setColor(color);
    }

    public void setDKBY3Color(int color) {
        mDKBY3Paint.setColor(color);
    }

    public void setDKBY4Color(int color) {
        mDKBY4Paint.setColor(color);
    }

    /**
     * 设置曲线宽度
     */
    public void setLineWidth(float width) {
        mDKBY1TextPaint.setStrokeWidth(width);
        mDKBY2TextPaint.setStrokeWidth(width);
        mDKBY1Paint.setStrokeWidth(width);
        mDKBY2Paint.setStrokeWidth(width);
        mDKBY3Paint.setStrokeWidth(width);
        mDKBY4Paint.setStrokeWidth(width);
    }

    /**
     * 设置文字大小
     */
    public void setTextSize(float textSize) {
        mDKBY1TextPaint.setTextSize(textSize);
        mDKBY2TextPaint.setTextSize(textSize);
        mDKBY2Paint.setTextSize(textSize);
        mDKBY3Paint.setTextSize(textSize);
        mDKBY1Paint.setTextSize(textSize);
        mDKBY4Paint.setTextSize(textSize);
        mTextSize = textSize;
    }
}


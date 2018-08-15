package com.github.tifezh.kchartlib.chart.draw;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.tifezh.kchartlib.chart.BaseKChartView;
import com.github.tifezh.kchartlib.chart.base.IChartDraw;
import com.github.tifezh.kchartlib.chart.base.IValueFormatter;
import com.github.tifezh.kchartlib.chart.entity.IBIAS;
import com.github.tifezh.kchartlib.chart.formatter.ValueFormatter;

/**
 * Created by zhangwei on 2018/7/12.
 */

public class BIASDraw implements IChartDraw<IBIAS> {
    public int[] params;

    private Paint mBIAS1Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mBIAS2Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mBIAS3Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float mTextSize = 0;
    public BIASDraw(BaseKChartView view) {

    }

    @Override
    public void drawTranslated(@Nullable IBIAS lastPoint, @NonNull IBIAS curPoint, float lastX, float curX, @NonNull Canvas canvas, @NonNull BaseKChartView view, int position) {
        view.drawChildLine(canvas, mBIAS1Paint, lastX, lastPoint.getBias1(), curX, curPoint.getBias1());
        view.drawChildLine(canvas, mBIAS2Paint, lastX, lastPoint.getBias2(), curX, curPoint.getBias2());
        view.drawChildLine(canvas, mBIAS3Paint, lastX, lastPoint.getBias3(), curX, curPoint.getBias3());
    }

    @Override
    public void drawText(@NonNull Canvas canvas, @NonNull BaseKChartView view, int position, float x, float y) {
        float left = x;
        x = IndexDrawUtil.getInstance().drawParam(canvas, params,x, y);
        String text = "";
        IBIAS point = (IBIAS) view.getItem(position);
        text = "BIAS1:" + view.formatValue(point.getBias1()) + " ";
        canvas.drawText(text, x, y, mBIAS1Paint);
        x += mBIAS1Paint.measureText(text);
        text = "BIAS2:" + view.formatValue(point.getBias2()) + " ";
        canvas.drawText(text, x, y, mBIAS2Paint);
     //   x += mBIAS2Paint.measureText(text);

        y+=mTextSize;
        text = "BIAS3:" + view.formatValue(point.getBias3()) + " ";
        canvas.drawText(text, left, y, mBIAS3Paint);

    }


    @Override
    public float getMaxValue(IBIAS point) {
        return Math.max(point.getBias1(), Math.max(point.getBias2(), point.getBias3()));
    }

    @Override
    public float getMinValue(IBIAS point) {
        return Math.min(point.getBias1(), Math.min(point.getBias2(), point.getBias3()));
    }

    @Override
    public IValueFormatter getValueFormatter() {
        return new ValueFormatter();
    }

    public void setBIAS1Color(int color) {
        mBIAS1Paint.setColor(color);
    }

    public void setBIAS2Color(int color) {
        mBIAS2Paint.setColor(color);
    }

    public void setBIAS3Color(int color) {
        mBIAS3Paint.setColor(color);
    }

    /**
     * 设置曲线宽度
     */
    public void setLineWidth(float width) {
        mBIAS1Paint.setStrokeWidth(width);
        mBIAS2Paint.setStrokeWidth(width);
        mBIAS3Paint.setStrokeWidth(width);
    }

    /**
     * 设置文字大小
     */
    public void setTextSize(float textSize) {
        mBIAS2Paint.setTextSize(textSize);
        mBIAS3Paint.setTextSize(textSize);
        mBIAS1Paint.setTextSize(textSize);
        mTextSize = textSize;
    }
}


package com.github.tifezh.kchartlib.chart.draw;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.tifezh.kchartlib.chart.BaseKChartView;
import com.github.tifezh.kchartlib.chart.base.IChartDraw;
import com.github.tifezh.kchartlib.chart.base.IValueFormatter;
import com.github.tifezh.kchartlib.chart.entity.IBBI;
import com.github.tifezh.kchartlib.chart.entity.IBOLL;
import com.github.tifezh.kchartlib.chart.entity.IMA;
import com.github.tifezh.kchartlib.chart.formatter.ValueFormatter;

/**
 * Created by jilucheng on 2018/4/18.
 */

public class BBIDraw implements IChartDraw<IBBI> {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public int[] params;

    public BBIDraw(BaseKChartView view) {
    }

    @Override
    public void drawTranslated(@Nullable IBBI lastPoint, @NonNull IBBI curPoint, float lastX, float curX, @NonNull Canvas canvas, @NonNull BaseKChartView view, int position) {
        view.drawMainLine(canvas, mPaint, lastX, lastPoint.getBBI(), curX, curPoint.getBBI());
    }

    @Override
    public void drawText(@NonNull Canvas canvas, @NonNull BaseKChartView view, int position, float x, float y) {
        x = IndexDrawUtil.getInstance().drawParam(canvas, params,x, y);
        IBBI point = (IBBI) view.getItem(position);
        canvas.drawText("BBI:"+point.getBBI(), x, y, mPaint);
    }

    @Override
    public float getMaxValue(IBBI point) {
        return point.getBBI();
    }

    @Override
    public float getMinValue(IBBI point) {
        return point.getBBI();
    }

    @Override
    public IValueFormatter getValueFormatter() {
        return new ValueFormatter();
    }

    /**
     * 设置dn颜色
     */
    public void setColor(int color) {
        mPaint.setColor(color);
    }

    /**
     * 设置曲线宽度
     */
    public void setLineWidth(float width)
    {
        mPaint.setStrokeWidth(width);
    }

    /**
     * 设置文字大小
     */
    public void setTextSize(float textSize)
    {
        mPaint.setTextSize(textSize);
    }
}

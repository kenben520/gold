package com.github.tifezh.kchartlib.chart.draw;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.tifezh.kchartlib.chart.BaseKChartView;
import com.github.tifezh.kchartlib.chart.base.IChartDraw;
import com.github.tifezh.kchartlib.chart.base.IValueFormatter;
import com.github.tifezh.kchartlib.chart.entity.ILWR;
import com.github.tifezh.kchartlib.chart.formatter.ValueFormatter;

/**
 * Created by zhangwei on 2018/7/12.
 */

public class LWRDraw implements IChartDraw<ILWR> {
    public int[] params;
    private Paint mLWR1Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mLWR2Paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public LWRDraw(BaseKChartView view) {

    }

    @Override
    public void drawTranslated(@Nullable ILWR lastPoint, @NonNull ILWR curPoint, float lastX, float curX, @NonNull Canvas canvas, @NonNull BaseKChartView view, int position) {
        view.drawChildLine(canvas, mLWR1Paint, lastX, lastPoint.getLwr1(), curX, curPoint.getLwr1());
        view.drawChildLine(canvas, mLWR2Paint, lastX, lastPoint.getLwr2(), curX, curPoint.getLwr2());
    }

    @Override
    public void drawText(@NonNull Canvas canvas, @NonNull BaseKChartView view, int position, float x, float y) {
        x = IndexDrawUtil.getInstance().drawParam(canvas, params,x, y);
        String text = "";
        ILWR point = (ILWR) view.getItem(position);
        text = "LWR1:" + view.formatValue(point.getLwr1()) + " ";
        canvas.drawText(text, x, y, mLWR1Paint);
        x += mLWR1Paint.measureText(text);
        text = "LWR2:" + view.formatValue(point.getLwr2()) + " ";
        canvas.drawText(text, x, y, mLWR2Paint);
    }


    @Override
    public float getMaxValue(ILWR point) {
        return Math.max(point.getLwr1(), point.getLwr2());
    }

    @Override
    public float getMinValue(ILWR point) {
        return Math.min(point.getLwr1(), point.getLwr2());
    }

    @Override
    public IValueFormatter getValueFormatter() {
        return new ValueFormatter();
    }

    public void setLWR1Color(int color) {
        mLWR1Paint.setColor(color);
    }

    public void setLWR2Color(int color) {
        mLWR2Paint.setColor(color);
    }


    /**
     * 设置曲线宽度
     */
    public void setLineWidth(float width) {
        mLWR1Paint.setStrokeWidth(width);
        mLWR2Paint.setStrokeWidth(width);
    }

    /**
     * 设置文字大小
     */
    public void setTextSize(float textSize) {
        mLWR2Paint.setTextSize(textSize);
        mLWR1Paint.setTextSize(textSize);
    }
}


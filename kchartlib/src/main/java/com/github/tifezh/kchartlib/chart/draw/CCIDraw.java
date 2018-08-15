package com.github.tifezh.kchartlib.chart.draw;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.tifezh.kchartlib.chart.BaseKChartView;
import com.github.tifezh.kchartlib.chart.base.IChartDraw;
import com.github.tifezh.kchartlib.chart.base.IValueFormatter;
import com.github.tifezh.kchartlib.chart.entity.ICCI;
import com.github.tifezh.kchartlib.chart.formatter.ValueFormatter;

/**
 * Created by zhangwei on 2018/7/12.
 */

public class CCIDraw implements IChartDraw<ICCI> {
    public int[] params;
    private Paint mCciPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CCIDraw(BaseKChartView view) {

    }

    @Override
    public void drawTranslated(@Nullable ICCI lastPoint, @NonNull ICCI curPoint, float lastX, float curX, @NonNull Canvas canvas, @NonNull BaseKChartView view, int position) {
        view.drawChildLine(canvas, mCciPaint, lastX, lastPoint.getCci(), curX, curPoint.getCci());
    }

    @Override
    public void drawText(@NonNull Canvas canvas, @NonNull BaseKChartView view, int position, float x, float y) {
        x = IndexDrawUtil.getInstance().drawParam(canvas, params,x, y);
        String text = "";
        ICCI point = (ICCI) view.getItem(position);
        text = "CCI:" + view.formatValue(point.getCci()) + " ";
        canvas.drawText(text, x, y, mCciPaint);
    }


    @Override
    public float getMaxValue(ICCI point) {
        return point.getCci();
    }

    @Override
    public float getMinValue(ICCI point) {
        return point.getCci();
    }

    @Override
    public IValueFormatter getValueFormatter() {
        return new ValueFormatter();
    }

    public void setCCIColor(int color) {
        mCciPaint.setColor(color);
    }

    /**
     * 设置曲线宽度
     */
    public void setLineWidth(float width) {
        mCciPaint.setStrokeWidth(width);
    }

    /**
     * 设置文字大小
     */
    public void setTextSize(float textSize) {
        mCciPaint.setTextSize(textSize);
    }
}


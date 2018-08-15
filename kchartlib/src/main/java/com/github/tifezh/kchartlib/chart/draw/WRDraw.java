package com.github.tifezh.kchartlib.chart.draw;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.tifezh.kchartlib.chart.BaseKChartView;
import com.github.tifezh.kchartlib.chart.base.IChartDraw;
import com.github.tifezh.kchartlib.chart.base.IValueFormatter;
import com.github.tifezh.kchartlib.chart.entity.IWR;
import com.github.tifezh.kchartlib.chart.formatter.ValueFormatter;

/**
 * Created by zhangwei on 2018/7/12.
 */

public class WRDraw implements IChartDraw<IWR> {
    public int[] params;
    private Paint mWR1Paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public WRDraw(BaseKChartView view) {

    }

    @Override
    public void drawTranslated(@Nullable IWR lastPoint, @NonNull IWR curPoint, float lastX, float curX, @NonNull Canvas canvas, @NonNull BaseKChartView view, int position) {
        view.drawChildLine(canvas, mWR1Paint, lastX, lastPoint.getWr(), curX, curPoint.getWr());
    }

    @Override
    public void drawText(@NonNull Canvas canvas, @NonNull BaseKChartView view, int position, float x, float y) {
        x = IndexDrawUtil.getInstance().drawParam(canvas, params,x, y);
        String text = "";
        IWR point = (IWR) view.getItem(position);
        text = "WR:" + view.formatValue(point.getWr()) + " ";
        canvas.drawText(text, x, y, mWR1Paint);
    }


    @Override
    public float getMaxValue(IWR point) {
        return point.getWr();
    }

    @Override
    public float getMinValue(IWR point) {
        return point.getWr();
    }

    @Override
    public IValueFormatter getValueFormatter() {
        return new ValueFormatter();
    }

    public void setWR1Color(int color) {
        mWR1Paint.setColor(color);
    }

    /**
     * 设置曲线宽度
     */
    public void setLineWidth(float width) {
        mWR1Paint.setStrokeWidth(width);
    }

    /**
     * 设置文字大小
     */
    public void setTextSize(float textSize) {
        mWR1Paint.setTextSize(textSize);
    }
}


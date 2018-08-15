package com.github.tifezh.kchartlib.chart.draw;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.tifezh.kchartlib.chart.BaseKChartView;
import com.github.tifezh.kchartlib.chart.base.IChartDraw;
import com.github.tifezh.kchartlib.chart.base.IValueFormatter;
import com.github.tifezh.kchartlib.chart.entity.IARBR;
import com.github.tifezh.kchartlib.chart.entity.IATR;
import com.github.tifezh.kchartlib.chart.formatter.ValueFormatter;

/**
 * Created by jilucheng on 2018/5/3.
 */

public class ATRDraw implements IChartDraw<IATR>{
    private Paint mTRPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mATRPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public int[] params;

    public ATRDraw(BaseKChartView view) {

    }

    @Override
    public void drawTranslated(@Nullable IATR lastPoint, @NonNull IATR curPoint, float lastX, float curX, @NonNull Canvas canvas, @NonNull BaseKChartView view, int position) {
        view.drawChildLine(canvas, mTRPaint, lastX, lastPoint.getTR(), curX, curPoint.getTR());
        view.drawChildLine(canvas, mATRPaint, lastX, lastPoint.getATR(), curX, curPoint.getATR());
    }

    @Override
    public void drawText(@NonNull Canvas canvas, @NonNull BaseKChartView view, int position, float x, float y) {
        x = IndexDrawUtil.getInstance().drawParam(canvas, params,x, y);
        String text = "";
        IATR point = (IATR) view.getItem(position);
        text = "TR:" + view.formatValue(point.getTR()) + "  ";
        canvas.drawText(text, x, y, mTRPaint);
        x += mTRPaint.measureText(text);
        text = "ATR:" + view.formatValue(point.getATR()) + "  ";
        canvas.drawText(text, x, y, mATRPaint);
    }

    @Override
    public float getMaxValue(IATR point) {
        return Math.max(point.getTR(), point.getATR());
    }

    @Override
    public float getMinValue(IATR point) {
        return Math.min(point.getTR(), point.getATR());
    }

    @Override
    public IValueFormatter getValueFormatter() {
        return new ValueFormatter();
    }

    public void setTRColor(int color) {
        mTRPaint.setColor(color);
    }

    public void setATRColor(int color) {
        mATRPaint.setColor(color);
    }
    /**
     * 设置曲线宽度
     */
    public void setLineWidth(float width)
    {
        mTRPaint.setStrokeWidth(width);
        mATRPaint.setStrokeWidth(width);
    }

    /**
     * 设置文字大小
     */
    public void setTextSize(float textSize)
    {
        mTRPaint.setTextSize(textSize);
        mATRPaint.setTextSize(textSize);
    }
}

package com.github.tifezh.kchartlib.chart.draw;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.tifezh.kchartlib.chart.BaseKChartView;
import com.github.tifezh.kchartlib.chart.base.IChartDraw;
import com.github.tifezh.kchartlib.chart.base.IValueFormatter;
import com.github.tifezh.kchartlib.chart.entity.IARBR;
import com.github.tifezh.kchartlib.chart.entity.IKDJ;
import com.github.tifezh.kchartlib.chart.formatter.ValueFormatter;

/**
 * Created by jilucheng on 2018/5/3.
 */

public class ARBRDraw implements IChartDraw<IARBR>{
    private Paint mARPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mBRPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public int[] params;

    public ARBRDraw(BaseKChartView view) {
    }

    @Override
    public void drawTranslated(@Nullable IARBR lastPoint, @NonNull IARBR curPoint, float lastX, float curX, @NonNull Canvas canvas, @NonNull BaseKChartView view, int position) {
        view.drawChildLine(canvas, mARPaint, lastX, lastPoint.getAR(), curX, curPoint.getAR());
        view.drawChildLine(canvas, mBRPaint, lastX, lastPoint.getBR(), curX, curPoint.getBR());
    }

    @Override
    public void drawText(@NonNull Canvas canvas, @NonNull BaseKChartView view, int position, float x, float y) {
        x = IndexDrawUtil.getInstance().drawParam(canvas, params,x, y);

        String text = "";
        IARBR point = (IARBR) view.getItem(position);
        text = "AR:" + view.formatValue(point.getAR()) + "  ";
        canvas.drawText(text, x, y, mARPaint);
        x += mARPaint.measureText(text);
        text = "BR:" + view.formatValue(point.getBR()) + "  ";
        canvas.drawText(text, x, y, mBRPaint);
    }

    @Override
    public float getMaxValue(IARBR point) {
        return Math.max(point.getAR(), point.getBR());
    }

    @Override
    public float getMinValue(IARBR point) {
        return Math.min(point.getAR(), point.getBR());
    }

    @Override
    public IValueFormatter getValueFormatter() {
        return new ValueFormatter();
    }

    public void setARColor(int color) {
        mARPaint.setColor(color);
    }

    public void setBRColor(int color) {
        mBRPaint.setColor(color);
    }

    /**
     * 设置曲线宽度
     */
    public void setLineWidth(float width)
    {
        mARPaint.setStrokeWidth(width);
        mBRPaint.setStrokeWidth(width);
    }

    /**
     * 设置文字大小
     */
    public void setTextSize(float textSize)
    {
        mARPaint.setTextSize(textSize);
        mBRPaint.setTextSize(textSize);
    }
}

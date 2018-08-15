package com.github.tifezh.kchartlib.chart.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.github.tifezh.kchartlib.R;
import com.github.tifezh.kchartlib.chart.BaseKChartView;
import com.github.tifezh.kchartlib.chart.base.IChartDraw;
import com.github.tifezh.kchartlib.chart.base.IValueFormatter;
import com.github.tifezh.kchartlib.chart.entity.IKLine;
import com.github.tifezh.kchartlib.chart.entity.IMA;
import com.github.tifezh.kchartlib.chart.formatter.ValueFormatter;
import com.github.tifezh.kchartlib.utils.ViewUtil;

/**
 * Created by jilucheng on 2018/4/18.
 */

public class MADraw implements IChartDraw<IMA> {
    public int[] params;
    private Paint ma1Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint ma2Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint ma3Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint ma4Paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public MADraw(BaseKChartView view) {
    }

    public int kShowType;

    @Override
    public void drawTranslated(@Nullable IMA lastPoint, @NonNull IMA curPoint, float lastX, float curX, @NonNull Canvas canvas, @NonNull BaseKChartView view, int position) {
        //画ma5
        if (lastPoint.getMA1Price() != 0) {
            view.drawMainLine(canvas, ma1Paint, lastX, lastPoint.getMA1Price(), curX, curPoint.getMA1Price());
        }
        //画ma10
        if (lastPoint.getMA2Price() != 0) {
            view.drawMainLine(canvas, ma2Paint, lastX, lastPoint.getMA2Price(), curX, curPoint.getMA2Price());
        }
        //画ma20
        if (lastPoint.getMA3Price() != 0) {
            view.drawMainLine(canvas, ma3Paint, lastX, lastPoint.getMA3Price(), curX, curPoint.getMA3Price());
        }

        if (lastPoint.getMA4Price() != 0) {
            view.drawMainLine(canvas, ma4Paint, lastX, lastPoint.getMA4Price(), curX, curPoint.getMA4Price());
        }
    }

    @Override
    public void drawText(@NonNull Canvas canvas, @NonNull BaseKChartView view, int position, float x, float y) {
         if (kShowType==1){
             return;
         }
        float left = x;
        IMA point = (IKLine) view.getItem(position);

        x = IndexDrawUtil.getInstance().drawParam(canvas, params,x, y);

        String text = "MA1:" + view.formatValue(point.getMA1Price()) + " ";
        canvas.drawText(text, x, y, ma1Paint);

        x += ma2Paint.measureText(text);
        text = "MA2:" + view.formatValue(point.getMA2Price());
        canvas.drawText(text, x, y, ma2Paint);
//        x += ma2Paint.measureText(text);

        Rect rect = new Rect();
        ma3Paint.getTextBounds(text, 0, text.length(), rect);

        int h = rect.height()+view.textPad();
        text = "MA3:" + view.formatValue(point.getMA3Price()) + " ";
        canvas.drawText(text, left, y+h, ma3Paint);
        x = left + ma3Paint.measureText(text);

        text = "MA4:" + view.formatValue(point.getMA4Price()) + " ";
        canvas.drawText(text, x, y+h, ma4Paint);
    }

    @Override
    public float getMaxValue(IMA point) {
        return Math.max(Math.max(point.getMA1Price(), point.getMA2Price()),
                Math.max(point.getMA3Price(), point.getMA4Price()));
    }

    @Override
    public float getMinValue(IMA point) {
        return Math.min(Math.min(point.getMA1Price(), point.getMA2Price()),
                Math.min(point.getMA3Price(),point.getMA4Price()));
    }

    @Override
    public IValueFormatter getValueFormatter() {
        return new ValueFormatter();
    }

    /**
     * 设置ma5颜色
     * @param color
     */
    public void setMa1Color(int color) {
        this.ma1Paint.setColor(color);
    }

    /**
     * 设置ma10颜色
     * @param color
     */
    public void setMa2Color(int color) {
        this.ma2Paint.setColor(color);
    }

    /**
     * 设置ma20颜色
     * @param color
     */
    public void setMa3Color(int color) {
        this.ma3Paint.setColor(color);
    }

    /**
     * 设置ma20颜色
     * @param color
     */
    public void setMa4Color(int color) {
        this.ma4Paint.setColor(color);
    }
    /**
     * 设置曲线宽度
     */
    public void setLineWidth(float width)
    {
        ma1Paint.setStrokeWidth(width);
        ma2Paint.setStrokeWidth(width);
        ma3Paint.setStrokeWidth(width);
        ma4Paint.setStrokeWidth(width);
    }

    /**
     * 设置文字大小
     */
    public void setTextSize(float textSize)
    {
        ma1Paint.setTextSize(textSize);
        ma2Paint.setTextSize(textSize);
        ma3Paint.setTextSize(textSize);
        ma4Paint.setTextSize(textSize);
    }
}

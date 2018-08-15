package com.github.tifezh.kchartlib.chart.draw;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.tifezh.kchartlib.chart.BaseKChartView;
import com.github.tifezh.kchartlib.chart.base.IChartDraw;
import com.github.tifezh.kchartlib.chart.base.IValueFormatter;
import com.github.tifezh.kchartlib.chart.entity.IPBX;
import com.github.tifezh.kchartlib.chart.formatter.ValueFormatter;

/**
 * Created by jilucheng on 2018/5/2.
 */

public class PBXDraw implements IChartDraw<IPBX> {
    private Paint mPBX1Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPBX2Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPBX3Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPBX4Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float mTextSize = 0;
    public PBXDraw(BaseKChartView view) {

    }

    @Override
    public void drawTranslated(@Nullable IPBX lastPoint, @NonNull IPBX curPoint, float lastX, float curX, @NonNull Canvas canvas, @NonNull BaseKChartView view, int position) {
        float[] currPBX = curPoint.getPBX();
        float[] lastPBX = lastPoint.getPBX();
        view.drawMainLine(canvas, mPBX1Paint, lastX, lastPBX[0], curX, currPBX[0]);
        view.drawMainLine(canvas, mPBX2Paint, lastX, lastPBX[1], curX, currPBX[1]);
        view.drawMainLine(canvas, mPBX3Paint, lastX, lastPBX[2], curX, currPBX[2]);
        view.drawMainLine(canvas, mPBX4Paint, lastX, lastPBX[3], curX, currPBX[3]);
    }

    @Override
    public void drawText(@NonNull Canvas canvas, @NonNull BaseKChartView view, int position, float x, float y) {
        float left = x;
        IPBX point = (IPBX) view.getItem(position);
        float[] pbx = point.getPBX();
        String text = "PBX1:" + view.formatValue(pbx[0]) + " ";
        canvas.drawText(text, x, y, mPBX1Paint);
        x += mPBX1Paint.measureText(text);
        text = "PBX2:" + view.formatValue(pbx[1]) + " ";
        canvas.drawText(text, x, y, mPBX2Paint);
       // x += mPBX2Paint.measureText(text);

        y+=mTextSize;
        x = left;
        text = "PBX3:" + view.formatValue(pbx[2]) + " ";
        canvas.drawText(text, x, y, mPBX3Paint);
        x += mPBX3Paint.measureText(text);
        text = "PBX4:" + view.formatValue(pbx[3]) + " ";
        canvas.drawText(text, x, y, mPBX3Paint);
    }

    public void setPBX1Color(int color) {
        mPBX1Paint.setColor(color);
    }

    public void setPBX2Color(int color) {
        mPBX2Paint.setColor(color);
    }

    public void setPBX3Color(int color) {
        mPBX3Paint.setColor(color);
    }

    public void setPBX4Color(int color) {
        mPBX4Paint.setColor(color);
    }
    @Override
    public float getMaxValue(IPBX point) {
        float[] pbx = point.getPBX();
        return Math.max(Math.max(pbx[0],pbx[1]),Math.max(pbx[2],pbx[3]));
    }

    @Override
    public float getMinValue(IPBX point) {
        float[] pbx = point.getPBX();
        return Math.min(Math.min(pbx[0],pbx[1]),Math.min(pbx[2],pbx[3]));
    }

    @Override
    public IValueFormatter getValueFormatter() {
        return new ValueFormatter();
    }

    /**
     * 设置曲线宽度
     */
    public void setLineWidth(float width)
    {
        mPBX1Paint.setStrokeWidth(width);
        mPBX2Paint.setStrokeWidth(width);
        mPBX3Paint.setStrokeWidth(width);
        mPBX4Paint.setStrokeWidth(width);
    }

    /**
     * 设置文字大小
     */
    public void setTextSize(float textSize)
    {
        mPBX1Paint.setTextSize(textSize);
        mPBX2Paint.setTextSize(textSize);
        mPBX3Paint.setTextSize(textSize);
        mPBX4Paint.setTextSize(textSize);
        mTextSize = textSize;
    }
}

package com.github.tifezh.kchartlib.chart.draw;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.github.tifezh.kchartlib.chart.BaseKChartView;
import com.github.tifezh.kchartlib.chart.base.IChartDraw;
import com.github.tifezh.kchartlib.chart.base.IValueFormatter;
import com.github.tifezh.kchartlib.chart.entity.IKLine;
import com.github.tifezh.kchartlib.chart.entity.IMA;
import com.github.tifezh.kchartlib.chart.entity.IMIKE;
import com.github.tifezh.kchartlib.chart.formatter.ValueFormatter;

/**
 * Created by jilucheng on 2018/5/2.
 */

public class MIKEDraw implements IChartDraw<IMIKE> {
    public int[] params;
    private Paint mWRPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mMRPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mSRPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mWSPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mMSPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mSSPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float mTextSize = 0;
    public MIKEDraw(BaseKChartView view) {

    }

    @Override
    public void drawTranslated(@Nullable IMIKE lastPoint, @NonNull IMIKE curPoint, float lastX, float curX, @NonNull Canvas canvas, @NonNull BaseKChartView view, int position) {
        view.drawMainLine(canvas, mWRPaint, lastX, lastPoint.getMikeWR(), curX, curPoint.getMikeWR());
        view.drawMainLine(canvas, mMRPaint, lastX, lastPoint.getMikeMR(), curX, curPoint.getMikeMR());
        view.drawMainLine(canvas, mSRPaint, lastX, lastPoint.getMikeSR(), curX, curPoint.getMikeSR());
        view.drawMainLine(canvas, mWSPaint, lastX, lastPoint.getMikeWS(), curX, curPoint.getMikeWS());
        view.drawMainLine(canvas, mMSPaint, lastX, lastPoint.getMikeMS(), curX, curPoint.getMikeMS());
        view.drawMainLine(canvas, mSSPaint, lastX, lastPoint.getMikeSS(), curX, curPoint.getMikeSS());
    }

    @Override
    public void drawText(@NonNull Canvas canvas, @NonNull BaseKChartView view, int position, float x, float y) {
        float left = x;
        x = IndexDrawUtil.getInstance().drawParam(canvas, params,x, y);
        IMIKE point = (IMIKE) view.getItem(position);
        String text = "WR:" + view.formatValue(point.getMikeWR()) + "  ";
        canvas.drawText(text, x, y, mWRPaint);
        x += mWRPaint.measureText(text);
        text = "MR:" + view.formatValue(point.getMikeMR()) + "  ";
        canvas.drawText(text, x, y, mMRPaint);
        x += mMRPaint.measureText(text);
        text = "SR:" + view.formatValue(point.getMikeSR()) + "  ";
        canvas.drawText(text, x, y, mSRPaint);

        x = left;
        y += mTextSize;

        text = "WS:" + view.formatValue(point.getMikeWS()) + "  ";
        canvas.drawText(text, x, y, mWSPaint);
        x += mWSPaint.measureText(text);
        text = "MS:" + view.formatValue(point.getMikeMS()) + "  ";
        canvas.drawText(text, x, y, mMSPaint);
        x += mMSPaint.measureText(text);
        text = "SS:" + view.formatValue(point.getMikeSS()) + "  ";
        canvas.drawText(text, x, y, mSSPaint);
    }

    @Override
    public float getMaxValue(IMIKE point) {
        return point.getMikeSR();
    }

    @Override
    public float getMinValue(IMIKE point) {
        return point.getMikeSS();
    }

    @Override
    public IValueFormatter getValueFormatter() {
        return new ValueFormatter();
    }

    public void setWRColor(int color) {
        mWRPaint.setColor(color);
    }

    public void setMRColor(int color) {
        mMRPaint.setColor(color);
    }

    public void setSRColor(int color) {
        mSRPaint.setColor(color);
    }

    public void setWSColor(int color) {
        mWSPaint.setColor(color);
    }

    public void setMSColor(int color) {
        mMSPaint.setColor(color);
    }

    public void setSSColor(int color) {
        mSSPaint.setColor(color);
    }
    /**
     * 设置曲线宽度
     */
    public void setLineWidth(float width)
    {
        mWRPaint.setStrokeWidth(width);
        mMRPaint.setStrokeWidth(width);
        mSRPaint.setStrokeWidth(width);
        mWSPaint.setStrokeWidth(width);
        mMSPaint.setStrokeWidth(width);
        mSSPaint.setStrokeWidth(width);
    }

    /**
     * 设置文字大小
     */
    public void setTextSize(float textSize)
    {
        mWRPaint.setTextSize(textSize);
        mMRPaint.setTextSize(textSize);
        mSRPaint.setTextSize(textSize);
        mWSPaint.setTextSize(textSize);
        mMSPaint.setTextSize(textSize);
        mSSPaint.setTextSize(textSize);
        mTextSize = textSize;
    }
}

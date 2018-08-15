package com.github.tifezh.kchartlib.chart.draw;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.lang.reflect.Array;

/**
 * Created by jilucheng on 2018/7/12.
 */

public class IndexDrawUtil {
    private static final IndexDrawUtil instance = new IndexDrawUtil();
    private Paint paintText = new Paint(Paint.ANTI_ALIAS_FLAG);

    public IndexDrawUtil() {

    }

    public static IndexDrawUtil getInstance(){
        return instance;
    }

    /**
     * 设置线条宽带
     * @param width
     */
    public void setLineWidth(float width){
        paintText.setStrokeWidth(width);
    }

    /**
     * 设置字体大小
     * @param size
     */
    public void setTextSize(float size) {
        paintText.setTextSize(size);
    }

    /**
     * 设置字体颜色
     * @param color
     */
    public void setTextColor(int color) {
        paintText.setColor(color);
    }

    /**
     * 绘制参数文本
     * @param params
     * @return 绘制完文本后字符最右边的X坐标
     */
    public float drawParam(Canvas canvas, int[] params, float x, float y) {
        String text = "(";
        if (params==null){
            return 0;
        }
        for( int i=0; i<params.length; i++) {
            text += params[i];
            if(i<params.length-1){
                text += ",";
            }
        }
        text += ")  ";
        canvas.drawText(text, x, y, paintText);
        return x + paintText.measureText(text);
    }
}

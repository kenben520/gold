package com.lingxi.preciousmetal.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.lingxi.preciousmetal.R;

public class RoundView extends View {

    private static final int BACKGROUND_COLOR = 0x00000000;//默认背景颜色
    private static final int BORDER_COLOR = 0xFFca731f;
    private static final String TEXT = "跳过";
    private static final float TEXT_SIZE = 50f;
    private static final int TEXT_COLOR = 0xFFFFFFFF;
    private static final float BORDER_WIDTH = 15f;

    private int backgroundColor;//背景颜色
    private int borderColor;
    private int textColor;
    private float textSize;
    private String text;

    private float progress = 360;

    private Paint circlePaint;
    private TextPaint textPaint;
    private Paint borderPaint;
    private float borderWidth;

    private StaticLayout staticLayout;

    private CountDownTimerListener listener;

    public RoundView(Context context) {
        super(context);
    }

    public RoundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundView);//自定义属性
        backgroundColor = ta.getColor(R.styleable.RoundView_background_color, BACKGROUND_COLOR);
        borderWidth = ta.getDimension(R.styleable.RoundView_border_width,
                BORDER_WIDTH);
        borderColor = ta.getColor(R.styleable.RoundView_border_color,
                BORDER_COLOR);
        text = ta.getString(R.styleable.RoundView_text);
        if (text == null) {//默认文字
            text = TEXT;
        }
        textSize = ta.getDimension(R.styleable.RoundView_text_size,
                TEXT_SIZE);//默认字体大小
        textColor = ta.getColor(R.styleable.RoundView_text_color,
                TEXT_COLOR);//默认字体颜色
        ta.recycle();//回收TypedArray
        init();
    }

    private void init() {
        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);//设置画笔的锯齿效果。
        circlePaint.setDither(true);//是否使用图像抖动处理,会使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
        circlePaint.setColor(backgroundColor);//设置绘制的颜色
        circlePaint.setStyle(Paint.Style.FILL);//

        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setDither(true);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);

        borderPaint = new Paint();
        borderPaint.setAntiAlias(true);
        borderPaint.setDither(true);
        borderPaint.setColor(borderColor);
        borderPaint.setStrokeWidth(borderWidth);
        borderPaint.setStyle(Paint.Style.STROKE);

        int textWidth = (int) textPaint.measureText(text.substring(0,
                (text.length() + 2) / 2));//换行的位置
        staticLayout = new StaticLayout(text, textPaint, textWidth,
                Layout.Alignment.ALIGN_NORMAL, 1F, 0, false);//换行

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);//尺寸
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        /*模式mode共有三种情况，取值分别为MeasureSpec.UNSPECIFIED, MeasureSpec.EXACTLY, MeasureSpec.AT_MOST。
        MeasureSpec.EXACTLY是精确尺寸，当我们将控件的layout_width或layout_height指定为具体数值时如andorid:layout_width="50dip"，或者为FILL_PARENT是，都是控件大小已经确定的情况，都是精确尺寸。

        MeasureSpec.AT_MOST是最大尺寸，当控件的layout_width或layout_height指定为WRAP_CONTENT时，控件大小一般随着控件的子空间或内容进行变化，此时控件尺寸只要不超过父控件允许的最大尺寸即可。因此，此时的mode是AT_MOST，size给出了父控件允许的最大尺寸。

        MeasureSpec.UNSPECIFIED是未指定尺寸，这种情况不多，一般都是父控件是AdapterView，通过measure方法传入的模式。*/
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode != MeasureSpec.EXACTLY) {
            width = staticLayout.getWidth();
        }
        if (heightMode != MeasureSpec.EXACTLY) {
            height = staticLayout.getHeight();
        }
        setMeasuredDimension(width, height);
    }

    int width;
    int height;
    int min;
    // 画边框
    RectF rectF;

    @Override
    protected void onDraw(Canvas canvas) {
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        min = Math.min(width, height);//求两数中最小
        // 画底盘(圆)
        canvas.drawCircle(width / 2, height / 2, min / 2, circlePaint);

        if (width > height) {
            rectF = new RectF(width / 2 - min / 2 + borderWidth / 2,
                    0 + borderWidth / 2, width / 2 + min / 2 - borderWidth / 2,
                    height - borderWidth / 2);
        } else {
            rectF = new RectF(borderWidth / 2, height / 2 - min / 2
                    + borderWidth / 2, width - borderWidth / 2, height / 2
                    - borderWidth / 2 + min / 2);
        }
        //弧线
        canvas.drawArc(rectF, -90, progress, false, borderPaint);
        // 画居中的文字
        canvas.translate(width / 2, height / 2 - staticLayout.getHeight() / 2);
        staticLayout.draw(canvas);
    }

    CountDownTimer countDownTimer;

    public void closeTimer() {
        if(countDownTimer!=null)
        {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    private static final String TAG = RoundView.class.getSimpleName();

    public void start() {
        if (listener != null) {
            listener.onStartCount();
        }
        countDownTimer = new CountDownTimer(3600, 36) {
            @Override
            public void onTick(long millisUntilFinished) {
                progress = ((3600 - millisUntilFinished) / 3600f) * 360 - 360;//百分比*360°
                //Log.w(TAG, "progress:" + progress+"   millisUntilFinished    "+ millisUntilFinished);
                invalidate();
            }

            @Override
            public void onFinish() {
                progress = 0;
                invalidate();
                if (listener != null) {
                    listener.onFinishCount();
                }
            }
        }.start();
    }

    public void setCountDownTimerListener(CountDownTimerListener listener) {
        this.listener = listener;
    }

    public interface CountDownTimerListener {

        void onStartCount();

        void onFinishCount();
    }
}

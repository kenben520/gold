package com.github.tifezh.kchartlib.chart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.github.tifezh.kchartlib.R;
import com.github.tifezh.kchartlib.chart.entity.KMinuteLineEntity;
import com.github.tifezh.kchartlib.utils.ViewUtil;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KMinuteChartView extends View {
    private GestureDetector detector;
//    private Paint mPaint;
    private Paint linePaint,upPaint,lossPaint,openPricePaint;
    private float baseData;
    private float minPrice = Float.MAX_VALUE,maxPrice,maxPriceY,mixPriceY;
    private ArrayList<String> times;
//    private List<Float> prices;
//    private List<Float> avgPricesList;
    private List<KMinuteLineEntity> minutePricesList;
    private boolean longPressFlag = false;
    private int touchIndex;
    private TouchMoveListener touchMoveListener;
    private float priceTextWidth;
    private LinearGradient linearGradient = null;

    private Rect mMainRect;

    public KMinuteChartView(Context context) {
        super(context);
        init();
    }

    public KMinuteChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public KMinuteChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public KMinuteChartView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private float timeTextWidth;
    private Paint timePaint,touchPricePaint,touchRectPaint,touchLinePaint,avgPaint,pricePaint,priceBgPaint;
    private Paint nowPriceLinePaint,nowPriceRectPaint,animationPaint;

    private void  initPaint(){
        animationPaint =  new Paint();
        animationPaint.setAntiAlias(true);
        animationPaint.setStyle(Paint.Style.FILL);
        animationPaint.setColor(Color.parseColor("#1478F0"));

        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.FILL);
        linePaint.setColor(Color.parseColor("#FFEDEDED"));
        linePaint.setStrokeWidth(2);

        timePaint = new Paint();
        timePaint.setAntiAlias(true);
        timePaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 9f, getResources().getDisplayMetrics()));
        timePaint.setColor(Color.parseColor("#FFC4C6C9"));
        timeTextWidth = timePaint.measureText("06:00");

        upPaint = new Paint();
        upPaint.setAntiAlias(true);
        upPaint.setColor(ViewUtil.getKUpColor(getContext()));
        upPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 9f, getResources().getDisplayMetrics()));

        lossPaint = new Paint();
        lossPaint.setAntiAlias(true);
        lossPaint.setColor(ViewUtil.getKLossColor(getContext()));
        lossPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 9f, getResources().getDisplayMetrics()));

        openPricePaint = new Paint();
        openPricePaint.setAntiAlias(true);
        openPricePaint.setColor(Color.parseColor("#FF9D9D9D"));
        openPricePaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 9f, getResources().getDisplayMetrics()));

        touchPricePaint = new Paint();
        touchPricePaint.setAntiAlias(true);
        touchPricePaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 9f, getResources().getDisplayMetrics()));
        touchPricePaint.setColor(Color.parseColor("#FFFFFFFF"));

        touchRectPaint = new Paint();
        touchRectPaint.setAntiAlias(true);
        touchRectPaint.setColor(Color.parseColor("#FF545454"));
        touchRectPaint.setStyle(Paint.Style.FILL);

        nowPriceRectPaint = new Paint();
        nowPriceRectPaint.setAntiAlias(true);
        nowPriceRectPaint.setColor(ContextCompat.getColor(getContext(),R.color.nowPxBgColor));
        nowPriceRectPaint.setStyle(Paint.Style.FILL);

        touchLinePaint = new Paint();
        touchLinePaint.setAntiAlias(true);
        touchLinePaint.setColor(Color.parseColor("#FF545454"));
        touchLinePaint.setStyle(Paint.Style.FILL);
        touchLinePaint.setStrokeWidth(ViewUtil.Dp2Px(getContext(),2));

        avgPaint = new Paint();
        avgPaint.setColor(Color.RED);
        avgPaint.setAntiAlias(true);
        avgPaint.setStyle(Paint.Style.STROKE);
        avgPaint.setStrokeWidth(ViewUtil.Dp2Px(getContext(),1));

        pricePaint = new Paint();
        pricePaint.setColor(Color.parseColor("#1f7bec"));
        pricePaint.setAntiAlias(true);
        pricePaint.setStyle(Paint.Style.STROKE);
        pricePaint.setStrokeWidth(ViewUtil.Dp2Px(getContext(),1));

        priceBgPaint = new Paint();
        priceBgPaint.setColor(Color.parseColor("#d4e9ff"));
        priceBgPaint.setAntiAlias(true);
        priceBgPaint.setStyle(Paint.Style.FILL);
        priceBgPaint.setStrokeWidth(ViewUtil.Dp2Px(getContext(),1));

        nowPriceLinePaint = new Paint();
        nowPriceLinePaint.setColor(ContextCompat.getColor(getContext(), R.color.nowPxLineColor));
        nowPriceLinePaint.setAntiAlias(true);
        nowPriceLinePaint.setStrokeWidth(ViewUtil.Dp2Px(getContext(),1));
        nowPriceLinePaint.setAntiAlias(true);
        nowPriceLinePaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return detector.onTouchEvent(event);
    }

    /**
     * create the test data
     */
    int xPointSize = 24 * 60;
    float xPointWidth = 0;
    int priceSize;
    float mScaleY;//y轴百分比
    private String decimalPoint;

    public void setPriceDecimalPoint(String point){
        decimalPoint = point;
    }

    public void resultKPaintColor() {
        upPaint.setColor(ViewUtil.getKUpColor(getContext()));
        lossPaint.setColor(ViewUtil.getKLossColor(getContext()));
    }

    public void setBasicsPrice(float closePrice,float maxPrice1,float minPrice1){
        baseData =  closePrice;
        maxPrice = maxPrice1;
        minPrice = minPrice1;
    }

    private float lastPrice;

    public void addMinutePrice(KMinuteLineEntity  bean) {
        float tmp = bean.getPrice();
        priceSize = minutePricesList.size();
        if(priceSize>0){
            KMinuteLineEntity lastBean =  minutePricesList.get(priceSize-1);
            if (bean.kTime!=lastBean.kTime){
                lastPrice = lastBean.price;
                maxPrice = Math.max(maxPrice,tmp);
                minPrice = Math.min(minPrice,tmp);
                minutePricesList.add(bean);
                postInvalidate();
            } else if(lastPrice!=tmp){
                lastBean.setTime(bean.getTime());
                lastBean.price = bean.price;
                postInvalidate();
            }
        }
    }

    public void setMinutePrice(List<KMinuteLineEntity> price1){
        minutePricesList.clear();
        minutePricesList.addAll(price1);
        priceSize = price1.size();
        postInvalidate();
    }

    private void initTime(){
        upAnimation();
        baseData = 0.0f;
        minutePricesList = new ArrayList<>();
//        avgPricesList = new ArrayList<>();
        times = new ArrayList<>();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = dateFormat.parse("2017-01-01 05:59:00");
            for (int i = 0; i <= xPointSize; i++) {
                date.setTime(date.getTime() + 60 * 1000);
                times.add(formatTime(dateFormat.format(date)));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    boolean flagAdd;
    float radius = 5;

    /**
     * format time
     *
     * @param time timeStr
     * @return format time
     */
    private String formatTime(String time) {
        return time.substring(11, 16);
    }

    /**
     * get action name
     *
     * @param event event
     * @return event name
     */
    private String getActionName(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return "ACTION_DOWN";
            case MotionEvent.ACTION_MOVE:
                return "ACTION_MOVE";
            case MotionEvent.ACTION_UP:
                return "ACTION_UP";
            default:
                return "other action";
        }
    }

    private int viewWidth;
    private float viewHeightAll;
    private float timeViewHeight = 0;
    private float margin,padLeft;
    private float KShowHeight,contentHeight;
    private float pxLastY=0;
    private int mTopPadding;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        longPressFlag = false;
        viewWidth = getWidth();
        viewHeightAll = getHeight();
        mTopPadding = 16;
        KShowHeight = viewHeightAll - ViewUtil.Dp2Px(getContext(),mTopPadding);
        mMainRect = new Rect(0,mTopPadding,viewWidth,(int)KShowHeight);
        timeViewHeight = viewHeightAll-10;
        margin = ViewUtil.Dp2Px(getContext(),10);
        padLeft = 5;
        contentHeight = viewHeightAll-10-ViewUtil.Dp2Px(getContext(),8);

        xPointWidth =  (viewWidth-padLeft)/xPointSize;
        centerTime = viewWidth/2;
        centerY = contentHeight/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float item = contentHeight / 410f;
        if (baseData>0){
            drawTimes(canvas, viewWidth);
        }
        drawAvgAndPriceLine(canvas);
        drawPriceAndPercent(canvas, viewWidth, item);
        drawTouchLines(canvas, viewWidth, item);
        drawCurrentPx(canvas);
    }

    private void drawAvgAndPriceLine(Canvas canvas) {
        mScaleY  = KShowHeight / (maxPriceY - mixPriceY);
//        Path avgPath = new Path();
        Path pricesPath = new Path();
        Path pricesBgPath = new Path();
        if (priceSize<=0){
            return;
        }
        for (int i = 0; i < priceSize; i++) {
            lastX = padLeft+xPointWidth * i;
            //画价格
            float temPrice =  minutePricesList.get(i).price;
            float point = maxPriceY-temPrice;
            pxLastY =  mTopPadding+ point*mScaleY;
            if (pxLastY>KShowHeight){
                pxLastY = KShowHeight-5;
            } else if (pxLastY<mTopPadding){
                  pxLastY = mTopPadding+5;
            }
            if (i==0){
                pricesPath.moveTo(lastX,pxLastY);
                pricesBgPath.moveTo(lastX,pxLastY);
            } else {
                pricesPath.lineTo(lastX,pxLastY);
                pricesBgPath.lineTo(lastX,pxLastY);
            }
        }
        canvas.drawPath(pricesPath,pricePaint);
//        canvas.drawPath(avgPath,avgPaint);
        linearGradient = new LinearGradient(padLeft,0,padLeft,timeViewHeight,Color.parseColor("#d4e9ff"),Color.WHITE, Shader.TileMode.MIRROR);
        priceBgPaint.setShader(linearGradient);
        pricesBgPath.lineTo(lastX,contentHeight);
        pricesBgPath.lineTo(padLeft,contentHeight);
        pricesBgPath.lineTo(padLeft,contentHeight);
        pricesBgPath.close();
        canvas.drawPath(pricesBgPath,priceBgPaint);
    }

    private void drawCurrentPx(Canvas canvas){
        if (priceSize>0){
            canvas.drawLine(0, pxLastY, viewWidth-priceTextWidth, pxLastY, nowPriceLinePaint);// 横线
            float rectPriceX = viewWidth-priceTextWidth - ViewUtil.Dp2Px(getContext(),2);
            RectF rec = new RectF(rectPriceX, pxLastY-ViewUtil.Dp2Px(getContext(),6),viewWidth, pxLastY+ViewUtil.Dp2Px(getContext(),7));
            canvas.drawRoundRect(rec, 6, 6, nowPriceRectPaint);
            canvas.drawText(String.format(decimalPoint,minutePricesList.get(priceSize-1).price), rectPriceX+ViewUtil.Dp2Px(getContext(),2),fixTextY(pxLastY,touchPricePaint),touchPricePaint);
            //画圆角动画
            RadialGradient mRadialGradient = new RadialGradient(lastX, pxLastY, (int)(radius*1), new int[] {
                    Color.parseColor("#d4e9ff") , Color.parseColor("#d4e9ff")}, null,
                    Shader.TileMode.REPEAT);
            animationPaint.setShader(mRadialGradient);
            Paint circlePaint = new Paint();
            circlePaint.setColor(Color.parseColor("#1478F0"));
            canvas.drawCircle(lastX, pxLastY, radius+(int)(radius*1), animationPaint);
            canvas.drawCircle(lastX, pxLastY, radius, circlePaint);
        }
    }

    boolean isLastX = false;
    /**
     * draw touch lines and point
     * @param canvas    canvas
     * @param viewWidth view's width
     * @param item      the view's height divided into 410
     */
    private void drawTouchLines(Canvas canvas, int viewWidth, float item) {
        if (longPressFlag) {
            if (priceSize<=0||touchIndex>=priceSize){
                return;
            }
            float selectIndex = currentX;
            float currentPrice;
            if (currentX>lastX){
                isLastX = true;
                selectIndex = lastX;
                currentPrice = maxPriceY-(maxPriceY-mixPriceY)/contentHeight*(currentY-mMainRect.top);
            } else {
                isLastX = false;
                currentPrice = minutePricesList.get(touchIndex).price;
            }
            drawDashEffect(canvas,selectIndex,mMainRect.top ,selectIndex,contentHeight);
            float rectTimeW = timeTextWidth + ViewUtil.Dp2Px(getContext(),5);
            RectF timeRect;
            int rectTimeH = ViewUtil.Dp2Px(getContext(),10);
            if (centerY<=currentY){
                //上半部分
                timeRect = new RectF(selectIndex-rectTimeW/2, mMainRect.top,selectIndex+rectTimeW/2,mMainRect.top+rectTimeH);
                //canvas.drawRoundRect(timeRect, 6, 6, touchRectPaint);
            } else {
                timeRect = new RectF(selectIndex-rectTimeW/2,contentHeight,selectIndex+rectTimeW/2, contentHeight+rectTimeH);
                //canvas.drawText(times.get(touchIndex)+"", selectIndex-timeTextWidth/2, fixTextY(contentHeight,touchPricePaint) , touchPricePaint);
            }
            canvas.drawRoundRect(timeRect, 6, 6, touchRectPaint);
            canvas.drawText(times.get(touchIndex)+"", selectIndex-timeTextWidth/2, timeRect.top+rectTimeH-5, touchPricePaint);

            float rectPriceX;
            if (centerTime>=mTouchX){
                rectPriceX = viewWidth-priceTextWidth - ViewUtil.Dp2Px(getContext(),10);
                drawDashEffect(canvas,0,currentY,viewWidth-priceTextWidth,currentY);
            } else {
                rectPriceX = ViewUtil.Dp2Px(getContext(), 2);
                drawDashEffect(canvas,priceTextWidth,currentY,viewWidth,currentY);
            }
            RectF rec = new RectF(rectPriceX, currentY-ViewUtil.Dp2Px(getContext(),8),rectPriceX+priceTextWidth + ViewUtil.Dp2Px(getContext(),5), currentY+ViewUtil.Dp2Px(getContext(),8));
            canvas.drawRoundRect(rec, 6, 6, touchRectPaint);
            canvas.drawText(String.format(decimalPoint,currentPrice), rectPriceX+ViewUtil.Dp2Px(getContext(),2), fixTextY(currentY,touchPricePaint) ,touchPricePaint);
        }
    }

    /**
     * 解决text居中的问题
     */
    public float fixTextY(float y,Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return (y + (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent);
    }

    /**
     * show touch 数据
     */
    private void showTouchLine(float touchX) {
        currentX = touchX;
        mTouchX = touchX;
        longPressFlag = true;

        float x2 = touchX*xPointSize/getWidth();
        BigDecimal b  =  new BigDecimal(x2);
        touchIndex = b.setScale(0,BigDecimal.ROUND_HALF_UP).intValue();
        if (touchIndex>=priceSize){
            touchIndex = priceSize-1;
            if (touchIndex>=xPointSize){
                touchIndex = xPointSize-1;
            }
        }
        postInvalidate();
//        if (touchMoveListener != null && touchIndex >= 0) {
//            touchMoveListener.change(times.get(touchIndex)+"", prices.get(touchIndex)+"",
//                    formatPrice((prices.get(touchIndex) - baseData) / baseData * 100) + "%",
//                    "4613.93万");
//        }
    }

    /**
     * draw a doted line
     *
     * @param canvas canvas
     * @param x      startX
     * @param y      startY
     * @param endX   endX
     * @param endY   endY
     */
    private void drawDashEffect(Canvas canvas, float x, float y, float endX, float endY) {
        PathEffect effects = new DashPathEffect(new float[]{18, 18, 18,18}, 1);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.parseColor("#FF545454"));
        p.setPathEffect(effects);
        p.setStrokeWidth(3f);
        p.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        path.moveTo(x, y);
        path.lineTo(endX, endY);
        canvas.drawPath(path, p);
    }

    /**
     * @param canvas    canvas
     * @param viewWidth view's width
     */
    private void drawTimes(Canvas canvas, int viewWidth) {
//        float xItem = viewWidth/4;
        float pad = timeTextWidth/2;
        int padLeft = ViewUtil.Dp2Px(getContext(),5);

        canvas.drawText("06:00", padLeft, timeViewHeight, timePaint);
        canvas.drawText("12:00", (int)(viewWidth*0.25-pad), timeViewHeight, timePaint);
        canvas.drawText("18:00", viewWidth/2-pad, timeViewHeight, timePaint);
        canvas.drawText("00:00", viewWidth/2 + (int)(viewWidth*0.25-pad), timeViewHeight, timePaint);
        canvas.drawText("06:00", viewWidth-timeTextWidth-padLeft, timeViewHeight, timePaint);

        //画竖线
        canvas.drawLine(viewWidth-3, margin, viewWidth-3, contentHeight, linePaint);
        canvas.drawLine(0, margin, 0, contentHeight, linePaint);
    }

    /**
     * 画价格和涨幅
     * @param canvas    canvas
     * @param viewWidth view's width
     * @param item      the view's height divided into 410
     */
    private void drawPriceAndPercent(Canvas canvas, int viewWidth, float item) {
        if (baseData<=0 || priceSize<=0){
            return;
        }
        String ysClosePx = String.format(decimalPoint,baseData);
        priceTextWidth = touchPricePaint.measureText(ysClosePx+" ");

        float maxScale = 0.1f;// 默认的比例
        float maxDifference = Math.abs(maxPrice-baseData);
        float minDifference = Math.abs(minPrice-baseData);
        float difference;

        if (maxDifference>minDifference){
            difference = maxDifference;
            maxScale = difference/3;
            maxPriceY = maxPrice;
            mixPriceY = baseData-maxScale*3;
        } else {
            difference = minDifference;
            maxScale = difference/3;
            mixPriceY = minPrice;
            maxPriceY = baseData+maxScale*3;
        }

        BigDecimal bDecimal  =  new BigDecimal(maxScale);
        maxScale = bDecimal.setScale(3,BigDecimal.ROUND_HALF_UP).floatValue();

        float yItem = KShowHeight*0.17f;
        float yItem1 = yItem;
        float yItem2 = yItem*2;
        float yItem3 = yItem*3;
        float yItem4 = yItem*4;
        float yItem5 = yItem*5;
//        float yItem6 = yItem*6;
        float linePad = ViewUtil.Dp2Px(getContext(),10);
        float startX = ViewUtil.Dp2Px(getContext(),3);

        String upPrice1 = String.format(decimalPoint,baseData+maxScale*2);
        String upPrice2 = String.format(decimalPoint,baseData+maxScale);

        String lossPrice1 = String.format(decimalPoint,baseData-maxScale);
        String lossPrice2 = String.format(decimalPoint,baseData-maxScale*2);
        //draw max price
        canvas.drawText(String.format(decimalPoint,maxPriceY)+"", startX, mMainRect.top+linePad, upPaint);
        canvas.drawText(upPrice1, startX, yItem1+linePad, upPaint);
        canvas.drawText(upPrice2, startX, yItem2+linePad, upPaint);
        canvas.drawText(String.format(decimalPoint,baseData), startX, yItem3+linePad, openPricePaint);
        canvas.drawText(lossPrice1, startX, yItem4+linePad, lossPaint);
        canvas.drawText(lossPrice2, startX, yItem5+linePad, lossPaint);
        canvas.drawText(String.format(decimalPoint,mixPriceY), startX,mMainRect.bottom-10, lossPaint);

        String percent = String.format("%.2f",maxScale*3/baseData*100)+"%";
        String percent1 = String.format("%.2f",maxScale*2/baseData*100)+"%";
        String percent2 = String.format("%.2f",maxScale/baseData*100)+"%";

        //百分比
        float percentX = viewWidth - openPricePaint.measureText(percent2)-ViewUtil.Dp2Px(getContext(),4);
        canvas.drawText(percent, percentX, mMainRect.top+linePad, upPaint);
        canvas.drawText(percent1, percentX, yItem1+linePad, upPaint);
        canvas.drawText(percent2, percentX, yItem2+linePad, upPaint);
        canvas.drawText(String.format("%.2f",0.0000f)+"%", percentX,yItem3+linePad, openPricePaint);
        canvas.drawText("-"+percent2, percentX-7,yItem4+linePad, lossPaint);
        canvas.drawText("-"+percent1 , percentX-7,yItem5+linePad, lossPaint);
        canvas.drawText("-"+percent, percentX-7,mMainRect.bottom-10, lossPaint);

        // 横线
        canvas.drawLine(0, mMainRect.top, viewWidth,mMainRect.top, linePaint);
        canvas.drawLine(0, yItem1, viewWidth,yItem1, linePaint);
        canvas.drawLine(0, yItem2, viewWidth,yItem2, linePaint);
        canvas.drawLine(0, yItem3, viewWidth,yItem3, linePaint);
        canvas.drawLine(0, yItem4, viewWidth,yItem4, linePaint);
        canvas.drawLine(0, yItem5, viewWidth,yItem5, linePaint);
        canvas.drawLine(0, mMainRect.bottom, viewWidth,mMainRect.bottom, linePaint);
        canvas.drawLine(0, contentHeight, viewWidth,contentHeight, linePaint);

//        drawDashEffect(canvas,0,yItem1+linePad,viewWidth,yItem1+linePad);
//        drawDashEffect(canvas,0,yItem2+linePad,viewWidth,yItem2+linePad);
//        drawDashEffect(canvas,0,yItem3+linePad,viewWidth,yItem3+linePad);
//        drawDashEffect(canvas,0,yItem4+linePad,viewWidth,yItem4+linePad);
//        drawDashEffect(canvas,0,yItem5+linePad,viewWidth,yItem5+linePad);
//        canvas.drawLine(0, contentHeight, viewWidth, contentHeight, linePaint);
    }

    /**
     * hide touch line
     */
    private void hideTouchLine() {
        touchIndex = -1;
        if (touchMoveListener != null) {
            touchMoveListener.change("", "", "", "");
        }
        postInvalidate();
    }

    float lastX = 0f;
    private float currentX,mTouchX = 0f;
    private float currentY = 0f;
    private int centerTime = 0;
    float centerY = 0;
    public interface TouchMoveListener {
        void change(String time, String price, String percent, String count);
    }

    private void init() {
        initPaint();
        detector = new GestureDetector(getContext(), new GestureDetector.OnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                currentY = e.getY();
                showTouchLine(e.getRawX());
                Log.e("onLongPress", getActionName(e));
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float
                    distanceY) {
                Log.e("onScroll", getActionName(e2) + "  Y: " + distanceY + "  e2: " + e2.getRawY
                        ());
                if (e2.getAction() == MotionEvent.ACTION_MOVE && longPressFlag) {
                    showTouchLine(e2.getRawX());
                    currentY = e2.getY();
                }
                return true;
            }


            @Override
            public boolean onDown2(MotionEvent e) {

                return false;
            }

            @Override
            public boolean onUp2(MotionEvent e) {
                return false;
            }


            @Override
            public boolean onUp(MotionEvent e) {
                Log.e("onUp", getActionName(e));
                if (!longPressFlag){
                    hideTouchLine();
                }
                return true;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                longPressFlag = false;
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float
                    velocityY) {
//                Log.e("onFling", getActionName(e2));
                return true;
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.e("onSingleTapUp", getActionName(e));
                return true;
            }

            @Override
            public void onShowPress(MotionEvent e) {
//                Log.e("onShowPress", getActionName(e));
            }
        });
        initTime();
    }

    public void upAnimation() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (radius >= 12) {
                        flagAdd = true;
                    }
                    if (radius <= 5) {
                        flagAdd = false;
                    }
                    if (flagAdd) {
                        radius -= radius*0.4;
                    } else {
                        radius += radius*0.2;
                        if (radius>15){
                            radius = 15;
                        }
                    }
                    postInvalidate();
                }
            }
//        }).start();
        }).start();
    }
}

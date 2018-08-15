package com.github.tifezh.kchartlib.chart;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.tifezh.kchartlib.R;
import com.github.tifezh.kchartlib.chart.base.IMainChartDraw;
import com.github.tifezh.kchartlib.chart.entity.IKLine;
import com.github.tifezh.kchartlib.chart.formatter.TimeFormatter;
import com.github.tifezh.kchartlib.chart.formatter.ValueFormatter;
import com.github.tifezh.kchartlib.chart.base.IAdapter;
import com.github.tifezh.kchartlib.chart.base.IChartDraw;
import com.github.tifezh.kchartlib.chart.base.IDateTimeFormatter;
import com.github.tifezh.kchartlib.chart.base.IValueFormatter;
import com.github.tifezh.kchartlib.utils.ViewUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * k线图
 * Created by tian on 2016/5/3.
 */
public abstract class BaseKChartView extends ScrollAndScaleView {
    public int currentType = 0;
    public float currentY;
    private float mTranslateX = Float.MIN_VALUE;

    private int mWidth,kmWidth,mHeight = 0;
    private boolean leftIshow;
    private int mTopPadding;

    private int mBottomPadding;

    private float mMainScaleY = 1;

    private float mChildScaleY = 1;

    private float mDataLen = 0;

    private float mMainMaxValue = Float.MAX_VALUE;

    private float mMainMinValue = Float.MIN_VALUE;

    private float mChildMaxValue = Float.MAX_VALUE;

    private float mChildMinValue = Float.MIN_VALUE;

    private int mStartIndex = 0;

    private int mStopIndex = 0;
    private int padLeft = 0;
    private int mPointWidth = 6;

    private int mGridRows = 4;

    private int mGridColumns = 4;

    private Paint mGridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint nowPxPaint = new Paint();

    private Paint mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Paint mSelectedLinePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint touchRectPaint=new Paint(Paint.ANTI_ALIAS_FLAG);

    private int mSelectedIndex;

    private IMainChartDraw mMainDraw;

    private IAdapter mAdapter;

    private DataSetObserver mDataSetObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            mItemCount = getAdapter().getCount();
            notifyChanged();
        }

        @Override
        public void onInvalidated() {
            mItemCount = getAdapter().getCount();
            notifyChanged();
        }
    };

    //当前点的个数
    private int mItemCount;
    private IChartDraw mMainChildDraw;
    private IChartDraw mChildDraw;
    private List<IChartDraw> mChildDraws = new ArrayList<>();
    private List<IChartDraw> mMainChildDraws = new ArrayList<>();

    private IValueFormatter mValueFormatter;
    private IDateTimeFormatter mDateTimeFormatter;

    protected KChartTabView mKChartMainTabView;//上面指标
    protected KChartTabView mKChartTabView;//下面指标
    public Button kIndicatorsMainBtn,kIndicatorsChildBtn;
    public ImageView k_main_set_btn,kChild_set_btn;
    private ValueAnimator mAnimator;

    private long mAnimationDuration = 500;

    private float mOverScrollRange = 0;

    private OnSelectedChangedListener mOnSelectedChangedListener = null;
    private OnSelectedIndicatorsListener  mOnSelectedIndicatorsListener = null;
    private OnUpInDicatorsNameListener upIndicatorsNameListener = null;
    private int leftImageY,leftImageX,leftImageH;
    private Rect mMainRect;
    private Rect mTabRect;
    private Rect mChildRect;
    float maxRadius;
    private float mLineWidth;

    public BaseKChartView(Context context) {
        super(context);
        init();
    }

    public BaseKChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseKChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        rollIndex = 0;
        maxRadius = ViewUtil.Dp2Px(getContext(),1);
        padLeft = dp2px(5);
//        mGridPaint.setPathEffect(new DashPathEffect(new float[] {15, 35}, 0));
        mGridPaint.setStyle(Paint.Style.STROKE);
        mGridPaint.setColor(Color.parseColor("#FFEDEDED"));
        mGridPaint.setStrokeWidth(ViewUtil.Dp2Px(getContext(),2));

        touchRectPaint = new Paint();
        touchRectPaint.setColor(Color.parseColor("#FF545454"));
        touchRectPaint.setStyle(Paint.Style.FILL);

        nowPxPaint.setColor(Color.WHITE);
        nowPxPaint.setAntiAlias(true);
        nowPxPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 9f, getResources().getDisplayMetrics()));

        setWillNotDraw(false);
        mDetector = new GestureDetectorCompat(getContext(), this);
        mScaleDetector = new ScaleGestureDetector(getContext(), this);
        mTopPadding = (int) getResources().getDimension(R.dimen.chart_top_padding);
        mBottomPadding = (int)getResources().getDimension(R.dimen.chart_bottom_padding);

        mKChartMainTabView = new KChartTabView(getContext());
        addView(mKChartMainTabView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        kIndicatorsMainBtn = mKChartMainTabView.kIndicatorsBtn;
        k_main_set_btn = mKChartMainTabView.k_indicators_set_btn;
        mKChartMainTabView.setOnTabSelectListener(new KChartTabView.TabSelectListener() {
            @Override
            public void onTabSelected(int type) {
                mOnSelectedIndicatorsListener.onSelectedChanged(mKChartMainTabView,type);
//                setMainChildDraw(type);
            }
        });
        mKChartMainTabView.mTvFullScreen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity) getContext();
                boolean isVertical = (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
                if (isVertical) {
                    activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } else {
                    activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

                }
            }
        });

        mKChartTabView = new KChartTabView(getContext());
        addView(mKChartTabView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        kIndicatorsChildBtn = mKChartTabView.kIndicatorsBtn;
        kChild_set_btn = mKChartTabView.k_indicators_set_btn;
        mKChartTabView.setOnTabSelectListener(new KChartTabView.TabSelectListener() {
            @Override
            public void onTabSelected(int type) {
//                setChildDraw(type);
                mOnSelectedIndicatorsListener.onSelectedChanged(mKChartTabView,type);
            }
        });

        mAnimator = ValueAnimator.ofFloat(0f, 1f);
        mAnimator.setDuration(mAnimationDuration);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                invalidate();
            }
        });
    }

    float downX,upX,currentX;
    static int rollIndex;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                currentX = event.getX();
                break;
            case MotionEvent.ACTION_POINTER_UP:

                break;
            case MotionEvent.ACTION_UP:
                upX = event.getX();
                rollIndex = mScrollX/mPointWidth;
                int y = (int)event.getY();
                int x = (int)event.getX();
                if (event.getY() > leftImageY && event.getY()<= leftImageY+leftImageH){
                    if(event.getX()>leftImageX && event.getX() <= leftImageX+leftImageH){
//                        setScrollX(0);
                        Log.e("tag", "onTouchEvent: "+rollIndex);
                        setScrollX(0);
                        invalidate();
                    }
                }
                if (mChildRect.contains(x,y)){
                    float X2 = downX-upX;
                    X2 =  Math.abs(X2);
                    if (X2<=10){
                        childIndex++;
                        if (childIndex>=mChildDraws.size()){
                            childIndex = 0;
                        }
                        upIndicatorsNameListener.OnUpInDicatorsNameListener("",childIndex);
                        // kIndicatorsMainBtn.setText(DataServer);
                        upChildIndicatorDraw(childIndex);
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        this.mHeight = h;
        kmWidth = w;
        initRect(w,h);
        mPointWidth= mWidth/49;
        mTranslateX = 0;
        //设置下指标位置
        mKChartTabView.setTranslationY(mMainRect.bottom);
//        setTranslateXFromScrollX(mPointWidth*rollIndex);
        isLongPress = false;
        setScrollX(mPointWidth*(rollIndex));
        invalidate();
    }

    public void upShowTypeView(int type){
        isLongPress = false;
        currentType = type;
        initRect(mWidth,mHeight);
        mKChartTabView.setTranslationY(mMainRect.bottom);
//        mKChartMainTabView.setTranslationY(mMainRect.top);
        setTranslateXFromScrollX(mScrollX);
        if (currentType==0){
            mKChartMainTabView.setVisibility(View.VISIBLE);
            mKChartTabView.setVisibility(View.VISIBLE);
        } else if (currentType==2){
            mKChartMainTabView.setVisibility(View.VISIBLE);
            mKChartTabView.setVisibility(View.GONE);
        } else {
            mKChartMainTabView.setVisibility(View.GONE);
            mKChartTabView.setVisibility(View.GONE);
        }
        invalidate();
        //设置下指标位置
    }

    int mMainChildSpace;

    private void initRect(int w,int h)  {
        mWidth = kmWidth - (int) mTextPaint.measureText("000.000")-dp2px(10);
        mMainChildSpace = mKChartTabView.getMeasuredHeight();
        int displayHeight = h - mTopPadding - mBottomPadding - mMainChildSpace;
        int mMainHeight = (int) (displayHeight * 0.7f);
        int mChildHeight = (int) (displayHeight * 0.3f);
        if (currentType==1){
            mTopPadding = 0;
            mBottomPadding = 0;
            mMainChildSpace = 0;
        }
        if (currentType!=0){
            mMainRect=new Rect(0,mTopPadding,mWidth,getHeight()-dp2px(20));
        }  else {
            mMainRect=new Rect(0,mTopPadding,mWidth,mTopPadding+mMainHeight);
        }

        mTabRect = new Rect(0,mMainRect.bottom,mWidth,mMainRect.bottom+mMainChildSpace);
        mChildRect=new Rect(0,mTabRect.bottom+dp2px(5),mWidth,mTabRect.bottom+mChildHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(mBackgroundPaint.getColor());
        if (mWidth == 0 || mMainRect.height() == 0 || mItemCount == 0) {
            mMainMaxValue = 00.000f;
            return;
        }
        mWidth = kmWidth - (int) mTextPaint.measureText(formatValue(mMainMaxValue))-dp2px(10);
//        mPointWidth= mWidth/49;
        calculateValue();
        canvas.save();
        canvas.scale(1, 1);
        drawGird(canvas);
        drawK(canvas);
        drawText(canvas);
        drawValue(canvas, isLongPress ? mSelectedIndex : mStopIndex);
        canvas.restore();
    }

    private float lineH;
    /**
     * 画表格
     * @param canvas
     */
    private void drawGird(Canvas canvas) {
//        setLayerType(LAYER_TYPE_SOFTWARE, null);
        //-----------------------上方k线图------------------------
        //横向的grid
        canvas.drawLine(0, 0, mWidth, 0, mGridPaint);
        float rowSpace = mMainRect.height()/ mGridRows;
        for (int i = 0; i <= mGridRows; i++) {
            if (i==0){
                drawDashEffect(canvas,0,mMainChildSpace,mWidth,mMainChildSpace);
            } else {
                canvas.drawLine(0, rowSpace * i+mMainRect.top, mWidth, rowSpace * i+mMainRect.top, mGridPaint);
            }
        }
        if (currentType==0){
            lineH = mChildRect.bottom-padLeft;
        } else {
            lineH = mMainRect.bottom-padLeft;
        }
        //-----------------------下方子图------------------------
        if (currentType==0){
            //副指标线
            canvas.drawLine(0, mChildRect.top, mWidth, mChildRect.top, mGridPaint);
            canvas.drawLine(0, mChildRect.bottom, mWidth, mChildRect.bottom, mGridPaint);
            //两边线
            canvas.drawLine(0, 0, 0,mChildRect.bottom, mGridPaint);
            canvas.drawLine(mWidth, 0, mWidth,mChildRect.bottom, mGridPaint);
            drawDashEffect(canvas,0,lineH,mWidth,lineH);
        } else {
            //两边线
            canvas.drawLine(0, 0, 0,mMainRect.bottom+20, mGridPaint);
            canvas.drawLine(mWidth, 0, mWidth,mMainRect.bottom+20, mGridPaint);
            drawDashEffect(canvas,0,mMainRect.bottom,mWidth,mMainRect.bottom);
            canvas.drawLine(0,mMainRect.bottom+20, mWidth, mMainRect.bottom+20, mGridPaint);
        }
    }

    /**
     * 画k线图
     * @param canvas
     */
    private void drawK(Canvas canvas) {
        //保存之前的平移，缩放
        canvas.save();
        canvas.translate(mTranslateX * mScaleX-20, 0);
        canvas.scale(mScaleX, 1);
        for (int i = mStartIndex; i <= mStopIndex; i++) {
            Object currentPoint = getItem(i);
            float currentPointX = getX(i);
//            if (currentPointX<mWidth){
            Object lastPoint = i == 0 ? currentPoint : getItem(i - 1);
            float lastX = i == 0 ? currentPointX : getX(i - 1);
            if (mMainDraw != null) {
                mMainDraw.drawTranslated(lastPoint, currentPoint, lastX, currentPointX, canvas, this, i);
            }
            if (mChildDraw != null && currentType==0) {
                mChildDraw.drawTranslated(lastPoint, currentPoint, lastX, currentPointX, canvas, this, i);
            }
//            }
        }
        //还原 平移缩放
        canvas.restore();
    }

    /**
     * 画文字
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        Paint.FontMetrics fm = mTextPaint.getFontMetrics();
        float textHeight = fm.descent - fm.ascent;
        float baseLine = (textHeight - fm.bottom - fm.top) / 2;
        if (currentType!=0){
            baseLine = 0;
        }
        //--------------画上方k线图的值-------------
        if (mMainDraw != null) {
            float maxPriceY = 0;
            if (currentType==1){
                maxPriceY = 30;//上面的距离
            } else {
                maxPriceY = mMainChildSpace;
            }
            canvas.drawText(formatValue(mMainMaxValue), mWidth+padLeft,fixTextY(maxPriceY), mTextPaint);
            canvas.drawText(formatValue(mMainMinValue), mWidth+padLeft, mMainRect.bottom, mTextPaint);
            float rowValue = (mMainMaxValue - mMainMinValue) / mGridRows;
            float rowSpace = mMainRect.height() / mGridRows;
            for (int i = 1; i < mGridRows; i++) {
                String text = formatValue(rowValue * (mGridRows - i) + mMainMinValue);
                canvas.drawText(text, mWidth+padLeft, fixTextY(rowSpace * i+mMainRect.top), mTextPaint);
            }
        }
        //--------------画下方子图的值-------------
        if (currentType==0){
            if (mChildDraw != null) {
                canvas.drawText(mChildDraw.getValueFormatter().format(mChildMaxValue), mWidth+padLeft, mChildRect.top+ baseLine, mTextPaint);
                canvas.drawText(mChildDraw.getValueFormatter().format((mChildMaxValue-mChildMinValue)/2), mWidth+padLeft,fixTextY(mChildRect.top+mChildRect.height()/2), mTextPaint);
                canvas.drawText(mChildDraw.getValueFormatter().format(mChildMinValue), mWidth+padLeft, mChildRect.bottom, mTextPaint);
            }
        }

        if (mItemCount>0 && mScrollX<10){
            leftIshow = false;
            //画现价
            IKLine point = (IKLine) getItem(mItemCount-1);
            float closePrice = point.getClosePrice();
            float y = getMainY(closePrice);
            canvas.drawLine(0,y,mWidth,y, mSelectedLinePaint);
            Paint circlePaint = new Paint();
            circlePaint.setColor(ContextCompat.getColor(getContext(),R.color.nowPxBgColor));
            int rectH = 30;
            RectF rec = new RectF(mWidth,y-rectH,kmWidth-padLeft,y+rectH);
            canvas.drawRoundRect(rec, 6, 6, circlePaint);

            canvas.drawText(formatValue(closePrice),mWidth+padLeft,fixTextY(y),nowPxPaint);
        } else {
            leftImageX = mWidth-dp2px(50);
            leftImageY = mMainRect.bottom-dp2px(50);
            leftIshow = true;
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.k_load_left_icon);
            leftImageH = bitmap.getHeight();
            canvas.drawBitmap(bitmap,leftImageX,leftImageY,mTextPaint);
        }

        //--------------画时间---------------------
        //  int timeColums = 1;//显示的个数
//        float columnSpace = mWidth / timeColums;

        float startX = getX(mStartIndex) - mPointWidth / 2;
        float stopX = getX(mStopIndex) + mPointWidth / 2;
        //画时间
        float timeY = getHeight()-8;
        float translateX = xToTranslateX(0);
        if (translateX >= startX && translateX <= stopX) {
            canvas.drawText(formatDateTime(getAdapter().getDate(mStartIndex)), 0, timeY, mTextPaint);
        }
        //  translateX = xToTranslateX(mWidth);
        // if (translateX >= startX && translateX <= stopX) {
        String text = formatDateTime(getAdapter().getDate(mStopIndex));
        canvas.drawText(text, mWidth - mTextPaint.measureText(text), timeY, mTextPaint);
        // }
        if (isLongPress) {
            float showY;
            if (currentY<mMainRect.top){
                showY = mMainRect.top;
            } else if (currentY>mMainRect.bottom){
                showY = mMainRect.bottom;
                if (mChildRect.contains((int)currentX,(int)currentY)){
                    //正常显示
                    showY = currentY;
                }
            } else {
                showY = currentY;
            }
            String priceY = "";
            if (currentY>mChildRect.top){
                priceY = mChildDraw.getValueFormatter().format(getChildPrice());
            } else {
                priceY = formatValue(getMainPrice());
            }
            float r = textHeight / 2+5;
            float w = kmWidth - mWidth;
            float x;
            RectF rec;
            if (currentX > mWidth / 2) {
                x = padLeft;
                rec = new RectF(0, showY - r, w, showY + r);
            } else {
                x = mWidth+padLeft;
                rec = new RectF(mWidth, showY - r, kmWidth, showY + r);
            }
            if (currentX>mWidth){
                currentX = mWidth;
            }
            drawDashEffect2(canvas,0,showY,mWidth,showY);
            canvas.drawRoundRect(rec, 6, 6, touchRectPaint);
            canvas.drawText(priceY, x, fixTextY(showY), nowPxPaint);
            drawVLine(canvas);
        }
    }

    private void drawVLine(Canvas canvas){
        String time = formatDateTime(getAdapter().getDate(mSelectedIndex));
        float priceTextWidth = nowPxPaint.measureText(time)+20;
        Paint.FontMetrics fm = nowPxPaint.getFontMetrics();
        float textHeight = fm.descent - fm.ascent;
//            float textW = 40;
        float r = priceTextWidth / 2;
        float y = mMainChildSpace;//getMainY(point.getCloseP

        float x = currentX;
        RectF rec = new RectF(x-r, y, x+r, y + textHeight+20);
        drawDashEffect2(canvas, x,mMainChildSpace+textHeight, x,lineH);
        canvas.drawRoundRect(rec, 6, 6, touchRectPaint);
        canvas.drawText(time,rec.left+10,mMainChildSpace+textHeight+3, nowPxPaint);
    }
    /**
     * 画值
     * @param canvas
     * @param position 显示某个点的值
     */
    private void drawValue(Canvas canvas, int position) {
//        Paint.FontMetrics fm = mTextPaint.getFontMetrics();
//        float textHeight = fm.descent - fm.ascent;
//        float baseLine = (textHeight - fm.bottom - fm.top) / 2;
        if (position >= 0 && position < mItemCount) {
            if (mMainDraw != null) {
                //  if (currentType==2 || currentType==0){
                float x  = mKChartMainTabView.getWidth();
                mMainDraw.drawText(canvas, this, position,x,dp2px(15));
                //}
            }
            if (mChildDraw != null && currentType==0) {
//                float y = mChildRect.top + baseLine;
                float x  = mKChartTabView.getWidth();
                //  float x = mTextPaint.measureText(mChildDraw.getValueFormatter().format(mChildMaxValue) + " ");
                mChildDraw.drawText(canvas, this, position, x, mMainRect.bottom+dp2px(13));
            }
        }
    }

    public int dp2px(float dp) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public int sp2px(float spValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 格式化值
     */
    public String formatValue(float value) {
        if (getValueFormatter() == null) {
            setValueFormatter(new ValueFormatter());
        }
        return getValueFormatter().format(value);
    }

    /**
     * 重新计算并刷新线条
     */
    public void notifyChanged() {
        if (mItemCount != 0) {
            mDataLen = (mItemCount - 1) * mPointWidth;
            checkAndFixScrollX();
            setTranslateXFromScrollX(mScrollX);
        } else {
            setScrollX(0);
        }
        invalidate();
    }

    private void calculateSelectedX(float x) {
        mSelectedIndex = indexOfTranslateX(xToTranslateX(x));
        if (mSelectedIndex < mStartIndex) {
            mSelectedIndex = mStartIndex;
        }
        if (mSelectedIndex > mStopIndex) {
            mSelectedIndex = mStopIndex;
        }
    }

    @Override
    public void onLongPress(MotionEvent e) {
        super.onLongPress(e);
        int lastIndex = mSelectedIndex;
        currentY = e.getY();
        calculateSelectedX(e.getX());
        if (lastIndex != mSelectedIndex) {
            onSelectedChanged(this, getItem(mSelectedIndex), mSelectedIndex);
        }
        invalidate();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        setTranslateXFromScrollX(mScrollX);
    }

    @Override
    protected void onScaleChanged(float scale, float oldScale) {
        checkAndFixScrollX();
        setTranslateXFromScrollX(mScrollX);
        super.onScaleChanged(scale, oldScale);
    }

    /**
     * 计算当前的显示区域
     */
    private void calculateValue() {
        if (!isLongPress()) {
            mSelectedIndex = -1;
        }
        mMainMaxValue = Float.MIN_VALUE;
        mMainMinValue = Float.MAX_VALUE;
        mChildMaxValue = Float.MIN_VALUE;
        mChildMinValue = Float.MAX_VALUE;
        mStartIndex = indexOfTranslateX(xToTranslateX(0));
        mStopIndex = indexOfTranslateX(xToTranslateX(mWidth-mPointWidth));
        IKLine point;
        for (int i = mStartIndex; i <= mStopIndex; i++) {
            point = (IKLine) getItem(i);
            if (mMainDraw != null) {
                mMainMaxValue = Math.max(mMainMaxValue, mMainDraw.getMaxValue(point));
                mMainMinValue = Math.min(mMainMinValue, mMainDraw.getMinValue(point));
            }
            if (mChildDraw != null) {
                mChildMaxValue = Math.max(mChildMaxValue, mChildDraw.getMaxValue(point));
                mChildMinValue = Math.min(mChildMinValue, mChildDraw.getMinValue(point));
            }
        }
        if(mMainMaxValue!=mMainMinValue) {
            float padding = (mMainMaxValue - mMainMinValue) * 0.05f;
            mMainMaxValue += padding;
            mMainMinValue -= padding;
        } else {
            //当最大值和最小值都相等的时候 分别增大最大值和 减小最小值
            mMainMaxValue += Math.abs(mMainMaxValue*0.05f);
            mMainMinValue -= Math.abs(mMainMinValue*0.05f);
            if (mMainMaxValue == 0) {
                mMainMaxValue = 1;
            }
        }

        if (mChildMaxValue == mChildMinValue) {
            //当最大值和最小值都相等的时候 分别增大最大值和 减小最小值
            mChildMaxValue += Math.abs(mChildMaxValue*0.05f);
            mChildMinValue -= Math.abs(mChildMinValue*0.05f);
            if (mChildMaxValue == 0) {
                mChildMaxValue = 1;
            }
        }

        mMainScaleY = mMainRect.height() * 1f / (mMainMaxValue - mMainMinValue);
        //虚线的值
        mChildScaleY = (mChildRect.height()-dp2px(5)) * 1f / (mChildMaxValue - mChildMinValue);
//        if (mAnimator.isRunning()) {
//            float value = (float) mAnimator.getAnimatedValue();
//            mStopIndex = mStartIndex + Math.round(value * (mStopIndex - mStartIndex));
//        }
    }

    /**
     * 获取平移的最小值
     * @return
     */
    private float getMinTranslateX() {
        return -mDataLen + mWidth / mScaleX - mPointWidth / 2;
    }

    /**
     * 获取平移的最大值
     * @return
     */
    private float getMaxTranslateX() {
        if (!isFullScreen()) {
            return getMinTranslateX();
        }
        return mPointWidth / 2;
    }

    @Override
    public int getMinScrollX() {
        return (int) -(mOverScrollRange / mScaleX);
    }

    public int getMaxScrollX() {
        return Math.round(getMaxTranslateX() - getMinTranslateX());
    }

    public int indexOfTranslateX(float translateX) {
        return indexOfTranslateX(translateX, 0, mItemCount - 1);
    }

    /**
     * 在主区域画线
     * @param startX    开始点的横坐标
     * @param stopX     开始点的值
     * @param stopX     结束点的横坐标
     * @param stopValue 结束点的值
     */
    public void drawMainLine(Canvas canvas, Paint paint, float startX, float startValue, float stopX, float stopValue) {
        canvas.drawLine(startX, getMainY(startValue), stopX, getMainY(stopValue), paint);
    }

    /**
     * 在子区域画线
     * @param startX     开始点的横坐标
     * @param startValue 开始点的值
     * @param stopX      结束点的横坐标
     * @param stopValue  结束点的值
     */
    public void drawChildLine(Canvas canvas, Paint paint, float startX, float startValue, float stopX, float stopValue) {
        canvas.drawLine(startX, getChildY(startValue), stopX, getChildY(stopValue), paint);
    }

    /**
     * 在子区域画圆圈
     * @param
     */
    public void drawChildCircle(Canvas canvas, Paint paint, float startX, float startValue, float stopX, float stopValue) {
        canvas.drawCircle(stopX, getChildY(stopValue), getRadius(startX,stopX), paint);
    }


    /**
     * 根据索引获取实体
     * @param position 索引值
     * @return
     */
    public Object getItem(int position) {
        if (mAdapter != null) {
            return mAdapter.getItem(position);
        } else {
            return null;
        }
    }

    /**
     * 根据索引索取x坐标
     * @param position 索引值
     * @return
     */
    public float getX(int position) {
        return position * mPointWidth;
    }

    /**
     * 获取适配器
     * @return
     */
    public IAdapter getAdapter() {
        return mAdapter;
    }
    /**
     * 设置子图的绘制方法
     * @param position
     */
    private void setChildDraw(int position) {
        if (position>=mChildDraws.size()){
            position = 0;
            childIndex=0;
        }
        this.mChildDraw = mChildDraws.get(position);
        invalidate();
    }

    private int childIndex;
    public void upMainIndicatorDraw(int position){
        setMainChildDraw(position);
    }

    public void upChildIndicatorDraw(int position){
        childIndex = position;
        setChildDraw(position);
    }

    public void setUpIndicatorsNameListener(OnUpInDicatorsNameListener l) {
        this.upIndicatorsNameListener = l;
    }

    public void setOnSelectedIndicatorsListener(OnSelectedIndicatorsListener l) {
        this.mOnSelectedIndicatorsListener = l;
    }

    private void setMainChildDraw(int position){
        this.mMainChildDraw = mMainChildDraws.get(position);
        this.mMainDraw.setChildDraw(this.mMainChildDraw);
        invalidate();
    }

    /**
     * 给子区域添加画图方法
     * @param name 显示的文字标签
     * @param childDraw IChartDraw
     */
    public void addChildDraw(String name, IChartDraw childDraw) {
        if (currentType!=1){
            mChildDraws.add(childDraw);
            setChildDraw(0);
        }
        mKChartTabView.kIndicatorsBtn.setText("MACD");
    }

    /**
     * 给主区域添加画图方法
     * @param name 显示的文字标签
     * @param childDraw IChartDraw
     */
    public void addMainChildDraw(String name, IChartDraw childDraw) {
        mMainChildDraws.add(childDraw);
        setMainChildDraw(0);
        //   mKChartMainTabView.addTab(name);
    }

    /**
     * scrollX 转换为 TranslateX
     * @param scrollX
     */
    private void setTranslateXFromScrollX(int scrollX) {
        mTranslateX = scrollX + getMinTranslateX();
    }

    /**
     * 获取ValueFormatter
     * @return
     */
    public IValueFormatter getValueFormatter() {
        return mValueFormatter;
    }

    /**
     * 设置ValueFormatter
     * @param valueFormatter value格式化器
     */
    public void setValueFormatter(IValueFormatter valueFormatter) {
        this.mValueFormatter = valueFormatter;
    }

    /**
     * 获取DatetimeFormatter
     * @return 时间格式化器
     */
    public IDateTimeFormatter getDateTimeFormatter() {
        return mDateTimeFormatter;
    }

    /**
     * 设置dateTimeFormatter
     * @param dateTimeFormatter 时间格式化器
     */
    public void setDateTimeFormatter(IDateTimeFormatter dateTimeFormatter) {
        mDateTimeFormatter = dateTimeFormatter;
    }

    /**
     * 格式化时间
     * @param date
     */
    public String formatDateTime(Date date) {
        if (getDateTimeFormatter() == null) {
            setDateTimeFormatter(new TimeFormatter());
        }
        return getDateTimeFormatter().format(date);
    }

    /**
     * 获取主区域的 IChartDraw
     * @return IChartDraw
     */
    public IChartDraw getMainDraw() {
        return mMainDraw;
    }

    /**
     * 设置主区域的 IChartDraw
     * @param mainDraw IChartDraw
     */
    public void setMainDraw(IMainChartDraw mainDraw) {
        mMainDraw = mainDraw;
    }

    /**
     * 二分查找当前值的index
     * @return
     */
    public int indexOfTranslateX(float translateX, int start, int end) {
        if (end == start) {
            return start;
        }
        if (end - start == 1) {
            float startValue = getX(start);
            float endValue = getX(end);
            return Math.abs(translateX - startValue) < Math.abs(translateX - endValue) ? start : end;
        }
        int mid = start + (end - start) / 2;
        float midValue = getX(mid);
        if (translateX < midValue) {
            return indexOfTranslateX(translateX, start, mid);
        } else if (translateX > midValue) {
            return indexOfTranslateX(translateX, mid, end);
        } else {
            return mid;
        }
    }

    /**
     * 设置数据适配器
     */
    public void setAdapter(IAdapter adapter) {
        if (mAdapter != null && mDataSetObserver != null) {
            mAdapter.unregisterDataSetObserver(mDataSetObserver);
        }
        mAdapter = adapter;
        if (mAdapter != null) {
            mAdapter.registerDataSetObserver(mDataSetObserver);
            mItemCount = mAdapter.getCount();
        } else {
            mItemCount = 0;
        }
        //交易模块
        if (currentType==0){
            mKChartMainTabView.setVisibility(View.VISIBLE);
            mKChartTabView.setVisibility(View.VISIBLE);

        } else if (currentType==2){
            mKChartMainTabView.setVisibility(View.VISIBLE);
            mKChartTabView.setVisibility(View.GONE);
        } else {
            mKChartMainTabView.setVisibility(View.GONE);
            mKChartTabView.setVisibility(View.GONE);
        }
        notifyChanged();
    }

    /**
     * 开始动画
     */
    public void startAnimation() {
        if (mAnimator != null) {
            mAnimator.start();
        }
    }

    /**
     * 设置动画时间
     */
    public void setAnimationDuration(long duration) {
        if (mAnimator != null) {
            mAnimator.setDuration(duration);
        }
    }

    /**
     * 设置表格行数
     */
    public void setGridRows(int gridRows) {
        if (gridRows < 1) {
            gridRows = 1;
        }
        mGridRows = gridRows;
    }

    /**
     * 设置表格列数
     */
    public void setGridColumns(int gridColumns) {
        if (gridColumns < 1) {
            gridColumns = 1;
        }
        mGridColumns = gridColumns;
    }

    /**
     * view中的x转化为TranslateX
     * @param x
     * @return
     */
    public float xToTranslateX(float x) {
        return -mTranslateX + x / mScaleX ;
    }

    /**
     * translateX转化为view中的x
     * @param translateX
     * @return
     */
    public float translateXtoX(float translateX) {
        return (translateX + mTranslateX) * mScaleX ;
    }

    /**
     * 获取上方padding
     */
    public float getTopPadding() {
        return mTopPadding;
    }

    /**
     * 获取图的宽度
     * @return
     */
    public int getChartWidth() {
        return mWidth;
    }

    /**
     * 是否长按
     */
    public boolean isLongPress() {
        return isLongPress;
    }

    /**
     * 获取选择索引
     */
    public int getSelectedIndex() {
        return mSelectedIndex;
    }

    public Rect getChildRect() {
        return mChildRect;
    }

    /**
     * 设置选择监听
     */
    public void setOnSelectedChangedListener(OnSelectedChangedListener l) {
        this.mOnSelectedChangedListener = l;
    }

    public void onSelectedChanged(BaseKChartView view, Object point, int index) {
        if (this.mOnSelectedChangedListener != null) {
            mOnSelectedChangedListener.onSelectedChanged(view, point, index);
        }
    }

    /**
     * 数据是否充满屏幕
     *
     * @return
     */
    public boolean isFullScreen() {
        return mDataLen >= mWidth / mScaleX;
    }

    /**
     * 设置超出右方后可滑动的范围
     */
    public void setOverScrollRange(float overScrollRange) {
        if (overScrollRange < 0) {
            overScrollRange = 0;
        }
        mOverScrollRange = overScrollRange;
    }

    /**
     * 设置上方padding
     * @param topPadding
     */
    public void setTopPadding(int topPadding) {
        mTopPadding = topPadding;
    }

    /**
     * 设置下方padding
     * @param bottomPadding
     */
    public void setBottomPadding(int bottomPadding) {
        mBottomPadding = bottomPadding;
    }

    /**
     * 设置表格线宽度
     */
    public void setGridLineWidth(float width) {
        mGridPaint.setStrokeWidth(width);
    }

    /**
     * 设置表格线颜色
     */
    public void setGridLineColor(int color) {
        mGridPaint.setColor(color);
    }

    /**
     * 设置选择线宽度
     */
    public void setSelectedLineWidth(float width) {
        mSelectedLinePaint.setStrokeWidth(width);
    }

    /**
     * 设置表格线颜色
     */
    public void setSelectedLineColor(int color) {
        mSelectedLinePaint.setColor(color);
        mSelectedLinePaint.setColor(ContextCompat.getColor(getContext(), R.color.nowPxLineColor));
    }
    /**
     *设置文字颜色
     */
    public void setTextColor(int color) {
        mTextPaint.setColor(color);
    }

    public int textPad(){
        return dp2px(5);
    }

    /**
     * 设置文字大小
     */
    public void setTextSize(float textSize)
    {
        mTextPaint.setTextSize(textSize);
    }

    /**
     * 设置背景颜色
     */
    public void setBackgroundColor(int color) {
        mBackgroundPaint.setColor(color);
    }


    /**
     * 选中点变化时的监听
     */
    public interface OnSelectedChangedListener {
        /**
         * 当选点中变化时
         * @param view  当前view
         * @param point 选中的点
         * @param index 选中点的索引
         */
        void onSelectedChanged(BaseKChartView view, Object point, int index);
    }

    public interface OnUpInDicatorsNameListener {
        void OnUpInDicatorsNameListener(String name,int index);
    }

    /**
     * 选中点变化时的监听
     */
    public interface OnSelectedIndicatorsListener {
        /**
         * 当选点中变化时
         * @param view  当前view
         * @param index 选中点的索引
         */
        void onSelectedChanged(OnClickListener view, int index);
    }
    /**
     * 获取文字大小
     */
    public float getTextSize()
    {
        return mTextPaint.getTextSize();
    }

    /**
     * 获取曲线宽度
     */
    public float getLineWidth() {
        return mLineWidth;
    }

    /**
     * 设置曲线的宽度
     */
    public void setLineWidth(float lineWidth) {
        mLineWidth = lineWidth;
    }

    /**
     * 设置每个点的宽度
     */
    public void setPointWidth(int pointWidth) {
        mPointWidth = pointWidth;
    }

    public Paint getGridPaint() {
        return mGridPaint;
    }

    public Paint getTextPaint() {
        return mTextPaint;
    }

    public Paint getBackgroundPaint() {
        return mBackgroundPaint;
    }

    public Paint getSelectedLinePaint() {
        return mSelectedLinePaint;
    }

    public float getMainY(float value) {
        return (mMainMaxValue - value) * mMainScaleY+mMainRect.top;
    }

    private float getMainPrice(){
        return  mMainMaxValue-(mMainMaxValue-mMainMinValue)/mMainRect.height()*(currentY-mMainRect.top);
    }

    private float getChildPrice(){
        //  String decima = ViewUtil.getNumberDecimalDigits(mChildMaxValue)+"";
        float price = mChildMaxValue-(mChildMaxValue-mChildMinValue)/mChildRect.height()*(currentY-mChildRect.top);
        return price;
    }

    public float getChildY(float value) {
        return (mChildMaxValue - value) * mChildScaleY + mChildRect.top;
    }

    public float getRadius(float lastx, float cx) {
        return Math.min(Math.abs(lastx-cx)/2, maxRadius);
    }
    /**
     * 解决text居中的问题
     */
    public float fixTextY(float y) {
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        return (y + (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent);
    }

    private void drawDashEffect(Canvas canvas, float x, float y, float endX, float endY) {
        PathEffect effects = new DashPathEffect(new float[]{10, 10, 10,10}, 1);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.parseColor("#FFEDEDED"));
        p.setPathEffect(effects);
        p.setStrokeWidth(dp2px(1));
        p.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        path.moveTo(x, y);
        path.lineTo(endX, endY);
        canvas.drawPath(path, p);
    }

    private void drawDashEffect2(Canvas canvas, float x, float y, float endX, float endY) {
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
}

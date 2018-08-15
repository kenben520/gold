package com.lingxi.preciousmetal.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lingxi.preciousmetal.base.BaseActivity;
import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;
import com.lingxi.preciousmetal.R;


/**
 * Created by zhangwei on 2018/4/17.
 */
public class TopBarView extends LinearLayout implements View.OnClickListener {

    private final String TAG = TopBarView.class.getSimpleName();

    public void setOnTopBarItemClickListener(OnTopBarItemClickListener listener) {
        mListener = listener;
    }

    public interface OnTopBarItemClickListener {
        public void onTopBarItemClick(View v);
    }

    private OnTopBarItemClickListener mListener;

    /**
     * 默认只有标题
     */
    public static final int MODE_DEFAULT = 1;
    /**
     * 带返回和标题
     */
    public static final int MODE_BACK = 3;
    /**
     * 自定义模式
     */
    public static final int MODE_CUSTOM = 5;
    /*
     * view
     */
    private ImageButton mActionButton;
    private ImageView mBackButton;
    private View mBackView;

    private TextView mTitleText;
    private TextView mActionTitleText;
    private TextView mLeftTitleText;
    public ImageView search_close_btn;
    private View mDivideView;

    public TopBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public TopBarView(Context context) {
        super(context);
        initialize();
    }

    public TopBarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    /**
     * @param mode
     */
    public void setMode(int mode) {
        switch (mode) {
            case MODE_DEFAULT:
                setVisibility(VISIBLE);
                break;
            case MODE_BACK:
                initBackMode();
                setVisibility(VISIBLE);
                break;
            case MODE_CUSTOM:
                setVisibility(VISIBLE);
                break;
        }
    }

    private void initBackMode() {
        setBackButton(R.drawable.btn_back, new OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftInput();
                ((BaseActivity) view.getContext()).finish();
            }
        });
    }

    /**
     * 输入框隐藏
     */
    public void hideSoftInput() {
        if (getContext() != null) {
            InputMethodManager imm = (InputMethodManager) ((Activity) getContext())
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            View view = ((Activity) getContext()).getCurrentFocus();
            if (view != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public void setDivideViewGone() {
        mDivideView.setVisibility(GONE);
    }

    public void resetTopBarState() {
        setDivideViewGone();
        setTitle(null);
        setActionButton(0, null);
        setBackButton(0, null);
        setActionTitle("", null);
    }

    public void setBackButtonOnClickListener(OnClickListener listener) {
        mBackView.setOnClickListener(listener);
    }

    public void setBackButton(int res, OnClickListener listener) {
        if (res != 0) {
            mBackView.setVisibility(VISIBLE);
            mBackButton.setImageResource(res);
            mBackView.setOnClickListener(listener);
        } else {
            mBackView.setVisibility(GONE);
        }
    }

    public void setActionButton(int res, OnClickListener listener) {
        if (res != 0) {
            mActionButton.setVisibility(VISIBLE);
            mActionButton.setImageResource(res);
            mActionButton.setOnClickListener(listener);
        } else {
            mActionButton.setVisibility(GONE);
        }
    }

    /**
     * @return
     */
    public TextView getTitleView() {
        return mTitleText;
    }

    public void setActionButtonVisibility(boolean isDispaly) {
        if (isDispaly) {
            mActionButton.setVisibility(View.VISIBLE);
        } else {
            mActionButton.setVisibility(View.GONE);
        }

    }

    public void setActionButtonEnabled(boolean isEnabled) {
        if (!isEnabled) {
            mActionButton.setImageDrawable(null);
        }
        mActionButton.setEnabled(isEnabled);
    }

    public void setTitle(String text) {
        mTitleText.setText(text);
        mTitleText.setOnClickListener(null);
    }

    public void setTitle(String text, OnClickListener listener) {
        mTitleText.setText(text);
        mTitleText.setOnClickListener(listener);
    }

    public void setActionTitle(String text, OnClickListener listener) {
        if (!TextUtils.isEmpty(text)) {
            mActionTitleText.setVisibility(VISIBLE);
        } else {
            mActionTitleText.setVisibility(GONE);
        }
        mActionTitleText.setText(text);
        mActionTitleText.setOnClickListener(listener);
    }

    public void setLeftTitle(String text, OnClickListener listener) {
        if (!TextUtils.isEmpty(text)) {
            mLeftTitleText.setVisibility(VISIBLE);
            mBackButton.setVisibility(GONE);
        } else {
            mLeftTitleText.setVisibility(GONE);
            mBackButton.setVisibility(VISIBLE);
        }
        mLeftTitleText.setText(text);
        mBackView.setOnClickListener(listener);
    }

    public void setLeftTitle(String text) {
        if (!TextUtils.isEmpty(text)) {
            mLeftTitleText.setVisibility(VISIBLE);
            mBackButton.setVisibility(GONE);
        } else {
            mLeftTitleText.setVisibility(GONE);
            mBackButton.setVisibility(VISIBLE);
        }
        mLeftTitleText.setText(text);
    }

    private void initialize() {
        LayoutInflater.from(getContext()).inflate(R.layout.top_bar_default_layout, this);
        mDivideView = findViewById(R.id.top_bar_divide);
        initCommonView();
        resetTopBarState();
    }

    protected void initCommonView() {
        mBackButton = (ImageView) findViewById(R.id.left_btn);
        mBackView = findViewById(R.id.layout_left_btn);
        mActionButton = (ImageButton) findViewById(R.id.right_btn);
        mTitleText = (TextView) findViewById(R.id.title);
        mActionTitleText = (TextView) findViewById(R.id.action_title);
        mLeftTitleText = (TextView) findViewById(R.id.left_title);

        search_close_btn = (ImageView) findViewById(R.id.search_close_btn);
    }

    public void startAnimation(int a, int b) {
        Activity p = ((Activity) getContext()).getParent();
        if (p != null) {
            p.overridePendingTransition(a, b);
        }

        ((Activity) getContext()).overridePendingTransition(a, b);

    }

    @Override
    public void onClick(View view) {
        if (mListener != null) {
            mListener.onTopBarItemClick(view);
        }
    }
}

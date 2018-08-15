package com.lingxi.preciousmetal.ui.listener;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
/**
 * Created by zhangwei on 2018/5/4.
 */
public class KeyBoardListener {

    private static final String TAG = KeyBoardListener.class.getSimpleName();


    public interface OnSoftKeyboardListener {

        public void onSoftKeyboardShown(int keyBoardHeight);

        public void onSoftKeyboardHidden(int keyBoardHeight);

    }

    private int mBaseHeight = -1;

    private View mView;
    private OnSoftKeyboardListener mListener;

    private int mLastKeyBoardHeight = 0;

    private int mKeyBoardHeight = 0;

    public KeyBoardListener(View contentView, OnSoftKeyboardListener listener) {
        mView = contentView;
        mView.getViewTreeObserver().addOnGlobalLayoutListener(mOnGlobalLayoutListener);
        mListener = listener;
    }

    public void onDestroy() {
        if (mView != null) {
            mView.getViewTreeObserver().removeGlobalOnLayoutListener(mOnGlobalLayoutListener);
        }
    }

    private ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener = new ViewTreeObserver
            .OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {

            int h = getKeyboardHeight();

            if (mBaseHeight == -1) {
                mBaseHeight = h;
            }

            if (mLastKeyBoardHeight != h) {

                boolean isShow = false;
                if (h > mBaseHeight) {
                    mKeyBoardHeight = h - mBaseHeight;
                    isShow = true;
                }

                mLastKeyBoardHeight = h;

                if (mListener != null) {
                    if (isShow) {
                        mListener.onSoftKeyboardShown(mKeyBoardHeight);
                    } else {
                        mListener.onSoftKeyboardHidden(mKeyBoardHeight);
                    }
                }
            }

        }
    };

    private int getKeyboardHeight() {

        Rect r = new Rect();
        mView.getWindowVisibleDisplayFrame(r);

        int screenHeight = mView.getRootView().getHeight();
        int heightDifference = screenHeight - (r.bottom - r.top);

        return heightDifference;
    }

    public void setViewOnKeyBoardTop(View view) {
        view.setTranslationY(-getKeyBoardHeight());
    }

    public int getKeyBoardHeight() {
        return mKeyBoardHeight;
    }

}

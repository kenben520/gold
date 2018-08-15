package com.lingxi.preciousmetal.presenter;

/**
 * Created by zhangwei on 2018/4/18.
 */

public abstract class BasePresenter<V> {
    public static String TAG = BasePresenter.class.getSimpleName();
    public V mView;

    public BasePresenter() {
        TAG = this.getClass().getSimpleName();
    }

    //创建View
    public BasePresenter(V view) {
        this.mView = view;
        TAG = this.getClass().getSimpleName();
    }

    public void setView(V view) {
        this.mView = view;
    }

    //摧毁View
    public void destroyView() {
        this.mView = null;
    }

    public boolean isViewAttached() {
        return mView != null;
    }

    public V getView() {
        return mView;
    }
}

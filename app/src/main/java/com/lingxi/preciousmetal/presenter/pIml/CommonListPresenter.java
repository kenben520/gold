package com.lingxi.preciousmetal.presenter.pIml;

import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.ListDataRequestMo;
import com.lingxi.biz.domain.responseMo.AnnouncementListMo;
import com.lingxi.biz.service.CommonService;
import com.lingxi.preciousmetal.presenter.BasePresenter;
import com.lingxi.preciousmetal.ui.vInterface.CommonListVInterface;

/**
 * Created by zhangwei on 2018/4/25.
 */

public class CommonListPresenter<T> extends BasePresenter<CommonListVInterface<T>> {
    private BizResultListener getListDataListener;

    public CommonListPresenter(CommonListVInterface view) {
        super(view);
        initListener();
    }

    private void initListener() {
        getListDataListener = new BizResultListener<AnnouncementListMo<T>>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, AnnouncementListMo<T> mo) {

            }

            @Override
            public void onSuccess(AnnouncementListMo<T> mo) {
                if (isViewAttached() && mo != null && mo.items != null) {
                    getView().showList(mo.items);
                }
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                if (isViewAttached()) {
                    getView().showFail(bizMessage);
                }
            }
        };
    }


    public void getListData(int page, int count) {
        ListDataRequestMo listDataRequestMo = new ListDataRequestMo(page, count);
        CommonService.getListData(listDataRequestMo, getListDataListener);
    }
}

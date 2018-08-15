package com.lingxi.preciousmetal.presenter.pIml;

import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.ListDataRequestMo;
import com.lingxi.biz.domain.requestMo.ListDataRequestMo2;
import com.lingxi.biz.domain.responseMo.AnnouncementListMo;
import com.lingxi.biz.domain.responseMo.KnowledgeResultMo;
import com.lingxi.biz.service.CommonService;
import com.lingxi.preciousmetal.presenter.BasePresenter;
import com.lingxi.preciousmetal.ui.vInterface.CommonListVInterface;
import com.lingxi.preciousmetal.ui.vInterface.CommonListVInterface2;

/**
 * Created by zhangwei on 2018/4/25.
 */

public class CommonListPresenter2 extends BasePresenter<CommonListVInterface2> {
    private BizResultListener getListDataListener2;

    public CommonListPresenter2(CommonListVInterface2 view) {
        super(view);
        initListener();
    }

    private void initListener() {
        getListDataListener2 = new BizResultListener<KnowledgeResultMo>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, KnowledgeResultMo mo) {

            }

            @Override
            public void onSuccess(KnowledgeResultMo mo) {
                if (isViewAttached() && mo != null && mo.articles != null) {
                    getView().showList(mo.articles);
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



    public void getListData2(int startId) {
        ListDataRequestMo2 listDataRequestMo = new ListDataRequestMo2(startId);
        CommonService.getListData2(listDataRequestMo, getListDataListener2);
    }
}

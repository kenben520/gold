package com.lingxi.biz.service;

import com.lingxi.biz.base.BizNetDefaultListener;
import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.MessCommonListRequestMo;
import com.lingxi.biz.domain.requestMo.MessQuickRequestMo;
import com.lingxi.biz.domain.requestMo.NewFundAnalysisRequestMo;
import com.lingxi.biz.domain.requestMo.NewQaRequestMo;
import com.lingxi.biz.domain.requestMo.NewsRecomRequestMo;
import com.lingxi.biz.domain.requestMo.SpecialRemarkRequestMo;
import com.lingxi.biz.domain.requestMo.SpecialViewPointRequestMo;
import com.lingxi.biz.domain.requestMo.StudyVideoRequestMo;
import com.lingxi.biz.domain.requestMo.StudyWordRequestMo;
import com.lingxi.biz.domain.response.KnowledgeResponse;
import com.lingxi.biz.domain.response.MessCommonListResponse;
import com.lingxi.biz.domain.response.MessQuickResponse;
import com.lingxi.biz.domain.response.NewRecomResponse;
import com.lingxi.biz.domain.response.SpecialRemarkResponse;
import com.lingxi.biz.domain.response.SpecialViewPointResponse;
import com.lingxi.biz.domain.response.StudyVideoResponse;
import com.lingxi.biz.domain.responseMo.KnowledgeResultMo;
import com.lingxi.biz.domain.responseMo.MessQuickResultBean;
import com.lingxi.biz.domain.responseMo.NewRecomBean;
import com.lingxi.biz.domain.responseMo.SpecialRemarkBean;
import com.lingxi.biz.domain.responseMo.SpecialViewPointBean;
import com.lingxi.biz.domain.responseMo.StudyVideoBean;
import com.lingxi.net.NetWorker;
import com.lingxi.net.basenet.MethodEnum;
import com.lingxi.net.biznet.BizNetRequest;

import java.util.List;

public class NewsService {

    public static void newsRecommend(NewsRecomRequestMo requestMo, BizResultListener<NewRecomBean> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(requestMo.getRequest(), NewRecomResponse.class, 1, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void newsCommontList(MessCommonListRequestMo requestMo, BizResultListener<NewRecomBean> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(requestMo.getRequest(), MessCommonListResponse.class, 1, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void newsFlashList(MessQuickRequestMo requestMo, BizResultListener<MessQuickResultBean> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(requestMo.getRequest(), MessQuickResponse.class, 1, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void specialViewPoint(SpecialViewPointRequestMo requestMo, BizResultListener<SpecialViewPointBean> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(requestMo.getRequest(), SpecialViewPointResponse.class, 1, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.GET;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void specialRemarkData(SpecialRemarkRequestMo requestMo, BizResultListener<SpecialRemarkBean> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(requestMo.getRequest(), SpecialRemarkResponse.class, 1, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.GET;
        netWorker.asyncRequest(bizNetRequest);
    }

}

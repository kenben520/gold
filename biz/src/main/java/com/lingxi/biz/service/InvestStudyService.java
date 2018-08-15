package com.lingxi.biz.service;

import com.lingxi.biz.base.BizNetDefaultListener;
import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.KnowledgeRequestMo;
import com.lingxi.biz.domain.requestMo.NewFundAnalysisRequestMo;
import com.lingxi.biz.domain.requestMo.NewQaRequestMo;
import com.lingxi.biz.domain.requestMo.StudyVideoRequestMo;
import com.lingxi.biz.domain.requestMo.StudyWordRequestMo;
import com.lingxi.biz.domain.response.KnowledgeResponse;
import com.lingxi.biz.domain.response.StudyVideoResponse;
import com.lingxi.biz.domain.responseMo.KnowledgeResultMo;
import com.lingxi.biz.domain.responseMo.StudyVideoBean;
import com.lingxi.net.NetWorker;
import com.lingxi.net.basenet.MethodEnum;
import com.lingxi.net.biznet.BizNetRequest;

public class InvestStudyService {

    public static void newQaApp(NewQaRequestMo requestMo, BizResultListener<KnowledgeResultMo> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(requestMo.getRequest(), KnowledgeResponse.class, 1, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.GET;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void newFundAnalysis(NewFundAnalysisRequestMo requestMo, BizResultListener<KnowledgeResultMo> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(requestMo.getRequest(), KnowledgeResponse.class, 1, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.GET;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void studyWord(StudyWordRequestMo requestMo, BizResultListener<KnowledgeResultMo> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(requestMo.getRequest(), KnowledgeResponse.class, 1, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.GET;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void studyVideo(StudyVideoRequestMo requestMo, BizResultListener<StudyVideoBean> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(requestMo.getRequest(), StudyVideoResponse.class, 1, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.GET;
        netWorker.asyncRequest(bizNetRequest);
    }

}

package com.lingxi.biz.service;

import com.lingxi.biz.base.BizNetDefaultListener;
import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.FeedBackRequestMo;
import com.lingxi.biz.domain.requestMo.GetAdRequestMo;
import com.lingxi.biz.domain.requestMo.GetAgreementRequestMo;
import com.lingxi.biz.domain.requestMo.ListDataRequestMo;
import com.lingxi.biz.domain.requestMo.ListDataRequestMo2;
import com.lingxi.biz.domain.requestMo.MessFinanceRequestMo;
import com.lingxi.biz.domain.requestMo.MessageCodeRequestMo;
import com.lingxi.biz.domain.requestMo.ModifyUserPersonalInfoRequestMo;
import com.lingxi.biz.domain.requestMo.NewNoticeLastRequestMo;
import com.lingxi.biz.domain.requestMo.NewNoticeRequestMo;
import com.lingxi.biz.domain.requestMo.PositionStatisticsRequestMo;
import com.lingxi.biz.domain.requestMo.StudySearchRequestMo;
import com.lingxi.biz.domain.response.AnnouncementsResponse;
import com.lingxi.biz.domain.response.FeedBackResponse;
import com.lingxi.biz.domain.response.GetAdResponse;
import com.lingxi.biz.domain.response.GetAgreementResponse;
import com.lingxi.biz.domain.response.GetUserInfoResponse;
import com.lingxi.biz.domain.response.KnowledgeResponse;
import com.lingxi.biz.domain.response.MessFinanceResponse;
import com.lingxi.biz.domain.response.MessageCodeResponse;
import com.lingxi.biz.domain.response.NewNoticeLastResponse;
import com.lingxi.biz.domain.response.NewNoticeResponse;
import com.lingxi.biz.domain.response.PositionStatisticsResponse;
import com.lingxi.biz.domain.response.StudySearchResponse;
import com.lingxi.biz.domain.responseMo.AdMo;
import com.lingxi.biz.domain.responseMo.AgreementMo;
import com.lingxi.biz.domain.responseMo.AnnouncementListMo;
import com.lingxi.biz.domain.responseMo.AnnouncementMo;
import com.lingxi.biz.domain.responseMo.CalendarResultBean;
import com.lingxi.biz.domain.responseMo.FeedBackMo;
import com.lingxi.biz.domain.responseMo.KnowledgeResultMo;
import com.lingxi.biz.domain.responseMo.MessageCodeBean;
import com.lingxi.biz.domain.responseMo.NewNoticeBean;
import com.lingxi.biz.domain.responseMo.NewNoticeLastBean;
import com.lingxi.biz.domain.responseMo.PositionStatisticsBean;
import com.lingxi.biz.domain.responseMo.StudySearchResultBean;
import com.lingxi.biz.domain.responseMo.UserInfoResultMo;
import com.lingxi.net.NetWorker;
import com.lingxi.net.basenet.MethodEnum;
import com.lingxi.net.biznet.BizNetRequest;
import com.lingxi.net.common.util.StringUtils;

import java.util.List;

/**
 * Created by zhangwei on 2018/4/25.
 */

public class CommonService {
    public static void getListData(ListDataRequestMo listDataRequestMo, BizResultListener<AnnouncementListMo<AnnouncementMo>> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(listDataRequestMo.getRequest(), AnnouncementsResponse.class, 1, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        netWorker.asyncRequest(bizNetRequest);
    }


    public static void getListData2(ListDataRequestMo2 listDataRequestMo, BizResultListener<KnowledgeResultMo> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(listDataRequestMo.getRequest(), KnowledgeResponse.class, 1, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void test(ListDataRequestMo listDataRequestMo, BizResultListener<AnnouncementListMo<AnnouncementMo>> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(listDataRequestMo.getRequest(), AnnouncementsResponse.class, 1, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        bizNetRequest.needCache = true;
        String key = null;
        if (listDataRequestMo != null && listDataRequestMo.getRequest() != null) {
            key = StringUtils.concatStr(listDataRequestMo.getRequest().app, listDataRequestMo.getRequest().act);
        }
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("key is null");
        }
        bizNetRequest.cacheKey = key;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void getMessageCode(MessageCodeRequestMo requestMo, BizResultListener<MessageCodeBean> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(requestMo.getRequest(), MessageCodeResponse.class, 1, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        netWorker.asyncRequest(bizNetRequest);
    }


    public static void getAd(GetAdRequestMo logoutRequestMo, BizResultListener<AdMo> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(logoutRequestMo.getRequest(), GetAdResponse.class, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void getAgreement(GetAgreementRequestMo logoutRequestMo, BizResultListener<AgreementMo> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(logoutRequestMo.getRequest(), GetAgreementResponse.class, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void feedback(FeedBackRequestMo logoutRequestMo, BizResultListener<FeedBackMo> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(logoutRequestMo.getRequest(), FeedBackResponse.class, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        netWorker.asyncRequest(bizNetRequest);
    }


    public static void getPositionStatistics(PositionStatisticsRequestMo requestMo, BizResultListener<PositionStatisticsBean> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(requestMo.getRequest(), PositionStatisticsResponse.class, 1, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void getNewNoticeList(NewNoticeRequestMo requestMo, BizResultListener<NewNoticeBean> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(requestMo.getRequest(), NewNoticeResponse.class, 1, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void getNewNoticeLast(NewNoticeLastRequestMo requestMo, BizResultListener<NewNoticeLastBean> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(requestMo.getRequest(), NewNoticeLastResponse.class, 1, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void getStudySearchList(StudySearchRequestMo requestMo, BizResultListener<StudySearchResultBean> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(requestMo.getRequest(), StudySearchResponse.class, 1, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        netWorker.asyncRequest(bizNetRequest);
    }
}

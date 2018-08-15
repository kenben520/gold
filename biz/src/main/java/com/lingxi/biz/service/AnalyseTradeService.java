package com.lingxi.biz.service;

import com.lingxi.biz.base.BizNetDefaultListener;
import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.EssenceCourseRequestMo;
import com.lingxi.biz.domain.requestMo.GetLivePeopleCountRequestMo;
import com.lingxi.biz.domain.requestMo.GetTeacherDetailRequestMo;
import com.lingxi.biz.domain.requestMo.LikeRequestMo;
import com.lingxi.biz.domain.requestMo.LiveListRequestMo;
import com.lingxi.biz.domain.requestMo.LiveStatusRequestMo;
import com.lingxi.biz.domain.requestMo.ShowTradeDeleteRequestMo;
import com.lingxi.biz.domain.requestMo.ShowTradeListRequestMo;
import com.lingxi.biz.domain.requestMo.ShowTradeRequestMo;
import com.lingxi.biz.domain.requestMo.TeacherOpinionsRequestMo;
import com.lingxi.biz.domain.requestMo.TeacherShowTradeListRequestMo;
import com.lingxi.biz.domain.requestMo.TeacherVideosRequestMo;
import com.lingxi.biz.domain.requestMo.WarnDeleteRequestMo;
import com.lingxi.biz.domain.response.DefaultBaseResponse;
import com.lingxi.biz.domain.response.EssenceCourseResponse;
import com.lingxi.biz.domain.response.GetLivePeopleCountResponse;
import com.lingxi.biz.domain.response.LikeResponse;
import com.lingxi.biz.domain.response.LiveProgramResponse;
import com.lingxi.biz.domain.response.LiveStatusResponse;
import com.lingxi.biz.domain.response.ShowTradeListResponse;
import com.lingxi.biz.domain.response.ShowTradeResponse;
import com.lingxi.biz.domain.response.TeacherDetailResponse;
import com.lingxi.biz.domain.response.TeacherOpinionsResponse;
import com.lingxi.biz.domain.response.TeacherShowTradeListResponse;
import com.lingxi.biz.domain.response.TeacherVideosResponse;
import com.lingxi.biz.domain.responseMo.BaseMo;
import com.lingxi.biz.domain.responseMo.EssenceCourseMo;
import com.lingxi.biz.domain.responseMo.GetLivePeopleCountMo;
import com.lingxi.biz.domain.responseMo.LikeResultMo;
import com.lingxi.biz.domain.responseMo.LiveProgramListMo;
import com.lingxi.biz.domain.responseMo.LiveStatusMo;
import com.lingxi.biz.domain.responseMo.ShowTradeListMo;
import com.lingxi.biz.domain.responseMo.TeacherDetailMo;
import com.lingxi.biz.domain.responseMo.TeacherOpinionslMo;
import com.lingxi.biz.domain.responseMo.TeacherShowTradeListMo;
import com.lingxi.biz.domain.responseMo.TeacherVideosMo;
import com.lingxi.net.NetWorker;
import com.lingxi.net.basenet.MethodEnum;
import com.lingxi.net.biznet.BizNetRequest;

/**
 * Created by zhangwei on 2018/5/25.
 */

public class AnalyseTradeService {
    public static void getLiveProgramList(LiveListRequestMo forgetPasswordRequestMo, BizResultListener<LiveProgramListMo> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(forgetPasswordRequestMo.getRequest(), LiveProgramResponse.class, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void getEssenceCourse(EssenceCourseRequestMo forgetPasswordRequestMo, BizResultListener<EssenceCourseMo> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(forgetPasswordRequestMo.getRequest(), EssenceCourseResponse.class, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void likeCourse(LikeRequestMo forgetPasswordRequestMo, BizResultListener<LikeResultMo> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(forgetPasswordRequestMo.getRequest(), LikeResponse.class, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void getShowTradeList(ShowTradeListRequestMo forgetPasswordRequestMo, BizResultListener<ShowTradeListMo> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(forgetPasswordRequestMo.getRequest(), ShowTradeListResponse.class, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void showTrade(ShowTradeRequestMo forgetPasswordRequestMo, BizResultListener<BaseMo> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(forgetPasswordRequestMo.getRequest(), ShowTradeResponse.class, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void getLiveStatusInfo(LiveStatusRequestMo forgetPasswordRequestMo, BizResultListener<LiveStatusMo> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(forgetPasswordRequestMo.getRequest(), LiveStatusResponse.class, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.GET;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void getTeacherDetail(GetTeacherDetailRequestMo forgetPasswordRequestMo, BizResultListener<TeacherDetailMo> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(forgetPasswordRequestMo.getRequest(), TeacherDetailResponse.class, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void getTeacherOpinionList(TeacherOpinionsRequestMo forgetPasswordRequestMo, BizResultListener<TeacherOpinionslMo> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(forgetPasswordRequestMo.getRequest(), TeacherOpinionsResponse.class, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void getTeacherVideoList(TeacherVideosRequestMo forgetPasswordRequestMo, BizResultListener<TeacherVideosMo> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(forgetPasswordRequestMo.getRequest(), TeacherVideosResponse.class, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void getTeacherShowTradeList(TeacherShowTradeListRequestMo forgetPasswordRequestMo, BizResultListener<TeacherShowTradeListMo> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(forgetPasswordRequestMo.getRequest(), TeacherShowTradeListResponse.class, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void deleteShowTrade(ShowTradeDeleteRequestMo forgetPasswordRequestMo, BizResultListener<BaseMo> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(forgetPasswordRequestMo.getRequest(), DefaultBaseResponse.class, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void getLivePeopleCount(GetLivePeopleCountRequestMo forgetPasswordRequestMo, BizResultListener<GetLivePeopleCountMo> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(forgetPasswordRequestMo.getRequest(), GetLivePeopleCountResponse.class, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.GET;
        netWorker.asyncRequest(bizNetRequest);
    }

}

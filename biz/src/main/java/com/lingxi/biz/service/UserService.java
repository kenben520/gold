package com.lingxi.biz.service;

import com.lingxi.biz.base.BizNetDefaultListener;
import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.ForgetPasswordRequestMo;
import com.lingxi.biz.domain.requestMo.GetUserInfoRequestMo;
import com.lingxi.biz.domain.requestMo.HomeAllRequestMo;
import com.lingxi.biz.domain.requestMo.IndustryRequestMo;
import com.lingxi.biz.domain.requestMo.InvestmentRequestMo;
import com.lingxi.biz.domain.requestMo.LoginRequestMo;
import com.lingxi.biz.domain.requestMo.LogoutRequestMo;
import com.lingxi.biz.domain.requestMo.MessExpertRequestMo;
import com.lingxi.biz.domain.requestMo.MessFinanceRequestMo;
import com.lingxi.biz.domain.requestMo.MessImportantRequestMo;
import com.lingxi.biz.domain.requestMo.MessQuickRequestMo;
import com.lingxi.biz.domain.requestMo.NewKlineRequestMo;
import com.lingxi.biz.domain.requestMo.NewMentalRequestMo;
import com.lingxi.biz.domain.requestMo.NewOpportunRequestMo;
import com.lingxi.biz.domain.requestMo.NewQaRequestMo;
import com.lingxi.biz.domain.requestMo.NewTradeRequestMo;
import com.lingxi.biz.domain.requestMo.ModifyUserPersonalInfoRequestMo;
import com.lingxi.biz.domain.requestMo.RegisterRequestMo;
import com.lingxi.biz.domain.requestMo.SubmitUserInfoRequestMo;
import com.lingxi.biz.domain.response.DefaultBaseResponse;
import com.lingxi.biz.domain.response.GetUserInfoResponse;
import com.lingxi.biz.domain.response.HomeAllResponse;
import com.lingxi.biz.domain.response.KnowledgeResponse;
import com.lingxi.biz.domain.response.LoginResponse;
import com.lingxi.biz.domain.response.MessExpertResponse;
import com.lingxi.biz.domain.response.MessFinanceResponse;
import com.lingxi.biz.domain.response.MessImportResponse;
import com.lingxi.biz.domain.response.MessQuickResponse;
import com.lingxi.biz.domain.response.RegisterResponse;
import com.lingxi.biz.domain.responseMo.BaseMo;
import com.lingxi.biz.domain.responseMo.CalendarResultBean;
import com.lingxi.biz.domain.responseMo.HomeAllResultBean;
import com.lingxi.biz.domain.responseMo.KnowledgeResultMo;
import com.lingxi.biz.domain.responseMo.LoginResultMo;
import com.lingxi.biz.domain.responseMo.MessExpertResultMo;
import com.lingxi.biz.domain.responseMo.MessImportResultBean;
import com.lingxi.biz.domain.responseMo.MessQuickResultBean;
import com.lingxi.biz.domain.responseMo.UserInfoResultMo;
import com.lingxi.net.NetWorker;
import com.lingxi.net.basenet.MethodEnum;
import com.lingxi.net.biznet.BizNetRequest;
import com.lingxi.net.common.util.StringUtils;

/**
 * Created by zhangwei on 2018/4/2.
 */

public class UserService {
    public static void forgetPassword(ForgetPasswordRequestMo forgetPasswordRequestMo, BizResultListener<LoginResultMo> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(forgetPasswordRequestMo.getRequest(), RegisterResponse.class, 1, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void register(RegisterRequestMo registerRequestMo, BizResultListener<LoginResultMo> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(registerRequestMo.getRequest(), RegisterResponse.class, 1, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void homeAllApp(HomeAllRequestMo KnowledgeRequestMo, BizResultListener<HomeAllResultBean> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(KnowledgeRequestMo.getRequest(), HomeAllResponse.class, 1, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        //
        bizNetRequest.needCache = true;
        String key = null;
        if (KnowledgeRequestMo != null && KnowledgeRequestMo.getRequest() != null) {
            key = StringUtils.concatStr(KnowledgeRequestMo.getRequest().app, KnowledgeRequestMo.getRequest().act);
        }
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("key is null");
        }
        bizNetRequest.cacheKey = key;
        //
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void login(LoginRequestMo loginRequestMo, BizResultListener<LoginResultMo> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(loginRequestMo.getRequest(), LoginResponse.class, 1, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void investment_app(InvestmentRequestMo KnowledgeRequestMo, BizResultListener<KnowledgeResultMo> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(KnowledgeRequestMo.getRequest(), KnowledgeResponse.class, 1, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.GET;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void industryApp(IndustryRequestMo KnowledgeRequestMo, BizResultListener<KnowledgeResultMo> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(KnowledgeRequestMo.getRequest(), KnowledgeResponse.class, 1, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.GET;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void messExpertApp(MessExpertRequestMo KnowledgeRequestMo, BizResultListener<MessExpertResultMo> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(KnowledgeRequestMo.getRequest(), MessExpertResponse.class, 1, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.GET;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void newKlineApp(NewKlineRequestMo KnowledgeRequestMo, BizResultListener<KnowledgeResultMo> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(KnowledgeRequestMo.getRequest(), KnowledgeResponse.class, 1, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.GET;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void newTradeApp(NewTradeRequestMo KnowledgeRequestMo, BizResultListener<KnowledgeResultMo> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(KnowledgeRequestMo.getRequest(), KnowledgeResponse.class, 1, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.GET;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void newMentalApp(NewMentalRequestMo KnowledgeRequestMo, BizResultListener<KnowledgeResultMo> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(KnowledgeRequestMo.getRequest(), KnowledgeResponse.class, 1, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.GET;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void messQuickApp(MessQuickRequestMo KnowledgeRequestMo, BizResultListener<MessQuickResultBean> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(KnowledgeRequestMo.getRequest(), MessQuickResponse.class, 1, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void messImportApp(MessImportantRequestMo KnowledgeRequestMo, BizResultListener<MessImportResultBean> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(KnowledgeRequestMo.getRequest(), MessImportResponse.class, 1, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void messFinceApp(MessFinanceRequestMo KnowledgeRequestMo, BizResultListener<CalendarResultBean> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(KnowledgeRequestMo.getRequest(), MessFinanceResponse.class, 1, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void newOpportunitylApp(NewOpportunRequestMo KnowledgeRequestMo, BizResultListener<KnowledgeResultMo> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(KnowledgeRequestMo.getRequest(), KnowledgeResponse.class, 1, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.GET;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void submitUserInfo(SubmitUserInfoRequestMo submitUserInfoRequestMo, BizResultListener<BaseMo> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(submitUserInfoRequestMo.getRequest(), DefaultBaseResponse.class, 1, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void getUserInfo(GetUserInfoRequestMo getUserInfoRequestMo, BizResultListener<UserInfoResultMo> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(getUserInfoRequestMo.getRequest(), GetUserInfoResponse.class, 1, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void logout(LogoutRequestMo logoutRequestMo, BizResultListener<BaseMo> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(logoutRequestMo.getRequest(), DefaultBaseResponse.class, 1, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void modifyAvatar(ModifyUserPersonalInfoRequestMo logoutRequestMo, BizResultListener<UserInfoResultMo> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(logoutRequestMo.getRequest(), GetUserInfoResponse.class, 1, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        netWorker.asyncRequest(bizNetRequest);
    }
}

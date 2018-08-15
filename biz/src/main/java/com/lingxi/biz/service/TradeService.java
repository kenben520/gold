package com.lingxi.biz.service;

import com.lingxi.biz.base.BizNetDefaultListener;
import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.DepositRequestMo;
import com.lingxi.biz.domain.requestMo.GetAccountInfoRequestMo;
import com.lingxi.biz.domain.requestMo.GetSignalParamsRequestMo;
import com.lingxi.biz.domain.requestMo.TAllEntrustRequestMo;
import com.lingxi.biz.domain.requestMo.TMoneyHistoryRequestMo;
import com.lingxi.biz.domain.requestMo.TTransactionHistoryRequestMo;
import com.lingxi.biz.domain.requestMo.TradeAllPositionRequestMo;
import com.lingxi.biz.domain.requestMo.TradeSubOrderRequestMo;
import com.lingxi.biz.domain.requestMo.WarnAddRequestMo;
import com.lingxi.biz.domain.requestMo.WarnDeleteRequestMo;
import com.lingxi.biz.domain.requestMo.WarnListRequestMo;
import com.lingxi.biz.domain.requestMo.WithdrawRequestMo;
import com.lingxi.biz.domain.response.DefaultBaseResponse;
import com.lingxi.biz.domain.response.DepositResponse;
import com.lingxi.biz.domain.response.GetAccountInfoResponse;
import com.lingxi.biz.domain.response.GetSignalParamsResponse;
import com.lingxi.biz.domain.response.TAllEntrustResponse;
import com.lingxi.biz.domain.response.TMoneyHistoryResponse;
import com.lingxi.biz.domain.response.TTransactionHistoryResponse;
import com.lingxi.biz.domain.response.TradeAllPositionResponse;
import com.lingxi.biz.domain.response.TradeSubOrderResponse;
import com.lingxi.biz.domain.response.WarnListResponse;
import com.lingxi.biz.domain.responseMo.AccountInfoMo;
import com.lingxi.biz.domain.responseMo.BaseMo;
import com.lingxi.biz.domain.responseMo.DepositResultMo;
import com.lingxi.biz.domain.responseMo.SignalParamsMo;
import com.lingxi.biz.domain.responseMo.TTransactionHistoryBean;
import com.lingxi.biz.domain.responseMo.TradeAllEntrustBean;
import com.lingxi.biz.domain.responseMo.TradeAllPositionBean;
import com.lingxi.biz.domain.responseMo.TradeMoneyHistoryBean;
import com.lingxi.biz.domain.responseMo.TradeSubOrderBean;
import com.lingxi.biz.domain.responseMo.WarningsItemMo;
import com.lingxi.net.NetWorker;
import com.lingxi.net.basenet.MethodEnum;
import com.lingxi.net.basenet.PlatformEnum;
import com.lingxi.net.biznet.BizNetRequest;

import java.util.List;

/**
 * Created by zhangwei on 2018/5/18.
 */

public class TradeService {
    public static void getAccountInfo(GetAccountInfoRequestMo getAccountInfoRequestMo, BizResultListener<AccountInfoMo> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(getAccountInfoRequestMo.getRequest(), GetAccountInfoResponse.class, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.GET;
        bizNetRequest.needHttps = true;
        bizNetRequest.platform = PlatformEnum.LEAN_WORK_SERVER;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void deposit(DepositRequestMo getAccountInfoRequestMo, BizResultListener<DepositResultMo> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(getAccountInfoRequestMo.getRequest(), DepositResponse.class, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        bizNetRequest.needHttps = true;
        bizNetRequest.platform = PlatformEnum.LEAN_WORK_SERVER;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void withdraw(WithdrawRequestMo getAccountInfoRequestMo, BizResultListener<DepositResultMo> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(getAccountInfoRequestMo.getRequest(), DepositResponse.class, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        bizNetRequest.needHttps = true;
        bizNetRequest.platform = PlatformEnum.LEAN_WORK_SERVER;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void getAllPosition(TradeAllPositionRequestMo getAccountInfoRequestMo, BizResultListener<List<TradeAllPositionBean>> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(getAccountInfoRequestMo.getRequest(), TradeAllPositionResponse.class, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.GET;
        bizNetRequest.needHttps = true;
        bizNetRequest.platform = PlatformEnum.LEAN_WORK_SERVER;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void subOrder(TradeSubOrderRequestMo requestMo, BizResultListener<TradeSubOrderBean> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(requestMo.getRequest(), TradeSubOrderResponse.class, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        bizNetRequest.needHttps = true;
        bizNetRequest.platform = PlatformEnum.LEAN_WORK_SERVER;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void getMoneyHistory(TMoneyHistoryRequestMo requestMo, BizResultListener<List<TradeMoneyHistoryBean>> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(requestMo.getRequest(), TMoneyHistoryResponse.class, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.GET;
        bizNetRequest.needHttps = true;
        bizNetRequest.platform = PlatformEnum.LEAN_WORK_SERVER;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void getAllEntrust(TAllEntrustRequestMo requestMo, BizResultListener<List<TradeAllEntrustBean>> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(requestMo.getRequest(), TAllEntrustResponse.class, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.GET;
        bizNetRequest.needHttps = true;
        bizNetRequest.platform = PlatformEnum.LEAN_WORK_SERVER;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void getTransactionHistory(TTransactionHistoryRequestMo requestMo, BizResultListener<List<TTransactionHistoryBean>> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(requestMo.getRequest(), TTransactionHistoryResponse.class, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.GET;
        bizNetRequest.needHttps = true;
        bizNetRequest.platform = PlatformEnum.LEAN_WORK_SERVER;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void getWarnList(WarnListRequestMo forgetPasswordRequestMo, BizResultListener<List<WarningsItemMo>> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(forgetPasswordRequestMo.getRequest(), WarnListResponse.class, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        netWorker.asyncRequest(bizNetRequest);
    }
    public static void addWarning(WarnAddRequestMo forgetPasswordRequestMo, BizResultListener<BaseMo> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(forgetPasswordRequestMo.getRequest(), DefaultBaseResponse.class, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        netWorker.asyncRequest(bizNetRequest);
    }
    public static void deleteWarning(WarnDeleteRequestMo forgetPasswordRequestMo, BizResultListener<BaseMo> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(forgetPasswordRequestMo.getRequest(), DefaultBaseResponse.class, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        netWorker.asyncRequest(bizNetRequest);
    }
    public static void getSignParams(GetSignalParamsRequestMo forgetPasswordRequestMo, BizResultListener<SignalParamsMo> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(forgetPasswordRequestMo.getRequest(), GetSignalParamsResponse.class, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        netWorker.asyncRequest(bizNetRequest);
    }
}

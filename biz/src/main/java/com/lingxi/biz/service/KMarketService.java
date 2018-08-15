package com.lingxi.biz.service;

import com.lingxi.biz.base.BizNetDefaultListener;
import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.request.KlineChartRequest;
import com.lingxi.biz.domain.requestMo.KMarketRequestMo;
import com.lingxi.biz.domain.requestMo.KlineChartRequestMo;
import com.lingxi.biz.domain.requestMo.KnowledgeRequestMo;
import com.lingxi.biz.domain.requestMo.ListDataRequestMo;
import com.lingxi.biz.domain.requestMo.ListDataRequestMo2;
import com.lingxi.biz.domain.response.AnnouncementsResponse;
import com.lingxi.biz.domain.response.KMarketResponse;
import com.lingxi.biz.domain.response.KlineChartResponse;
import com.lingxi.biz.domain.response.KnowledgeResponse;
import com.lingxi.biz.domain.responseMo.AnnouncementListMo;
import com.lingxi.biz.domain.responseMo.AnnouncementMo;
import com.lingxi.biz.domain.responseMo.KLineChartResultBean;
import com.lingxi.biz.domain.responseMo.KMarketResultBean;
import com.lingxi.biz.domain.responseMo.KnowledgeResultMo;
import com.lingxi.net.NetWorker;
import com.lingxi.net.basenet.MethodEnum;
import com.lingxi.net.biznet.BizNetRequest;
import com.lingxi.net.common.util.StringUtils;

/**
 * Created by qian on 2018/4/25.
 */

public class KMarketService {

    public static void kMarketApp(KMarketRequestMo requestMo, BizResultListener<KMarketResultBean> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(requestMo.getRequest(), KMarketResponse.class, 1, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        bizNetRequest.needCache = true;
        String key = null;
        if (requestMo != null && requestMo.getRequest() != null) {
            key = StringUtils.concatStr(requestMo.getRequest().app, requestMo.getRequest().act);
        }
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("key is null");
        }
        bizNetRequest.cacheKey = key;
        netWorker.asyncRequest(bizNetRequest);
    }

    public static void marketChart(KlineChartRequestMo requestMo, BizResultListener<KLineChartResultBean> listener) throws IllegalArgumentException {
        NetWorker netWorker = new NetWorker();
        BizNetRequest bizNetRequest = new BizNetRequest(requestMo.getRequest(), KlineChartResponse.class, 1, new BizNetDefaultListener(listener));
        bizNetRequest.method = MethodEnum.POST;
        netWorker.asyncRequest(bizNetRequest);
    }
}

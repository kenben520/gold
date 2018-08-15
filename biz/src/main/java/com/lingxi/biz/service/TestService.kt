package com.lingxi.biz.service

import com.lingxi.biz.base.BizNetDefaultListener
import com.lingxi.biz.base.BizResultListener
import com.lingxi.biz.domain.requestMo.ListDataRequestMo
import com.lingxi.biz.domain.response.AnnouncementsResponse
import com.lingxi.biz.domain.responseMo.AnnouncementListMo
import com.lingxi.biz.domain.responseMo.AnnouncementMo
import com.lingxi.net.NetWorker
import com.lingxi.net.basenet.MethodEnum
import com.lingxi.net.biznet.BizNetRequest
import com.lingxi.net.common.util.StringUtils

/**
 * Created by zhangwei on 2018/5/16.
 */
class TestService {
    companion object {
//        fun  testService1(listDataRequestMo:ListDataRequestMo) {
//            val netWorker =  NetWorker()
//            val a: ListDataRequest =listDataRequestMo.request
//            val b: ListDataRequest =listDataRequestMo!!.request
//        }
            fun  testService(listDataRequestMo:ListDataRequestMo,listener: BizResultListener<AnnouncementListMo<AnnouncementMo>>)
        {
            val netWorker =  NetWorker()
            val bizNetRequest= BizNetRequest<AnnouncementsResponse>(listDataRequestMo.request, AnnouncementsResponse::class.java,1,BizNetDefaultListener(listener))
            bizNetRequest.method = MethodEnum.POST
            bizNetRequest.needCache = true
            var key:String?=null
            if (listDataRequestMo != null && listDataRequestMo.request != null) {
                key = StringUtils.concatStr(listDataRequestMo.request.app, listDataRequestMo.request.act)
            }
            if (StringUtils.isEmpty(key)) {
                throw IllegalArgumentException("key is null")
            }
            bizNetRequest.cacheKey = key
            netWorker.asyncRequest(bizNetRequest)
        }
    }


}


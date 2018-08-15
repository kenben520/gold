package com.lingxi.biz.domain.requestMo;

import com.lingxi.biz.domain.request.KlineChartRequest;

public class KlineChartRequestMo {

    private KlineChartRequest request;

    public KlineChartRequestMo(String prod_code,int candle_period,int data_count) {
        request = new KlineChartRequest();
        request.prod_code = prod_code;
        request.candle_period = candle_period;
        request.data_count = data_count;
    }

    public KlineChartRequest getRequest() {
        return request;
    }

}

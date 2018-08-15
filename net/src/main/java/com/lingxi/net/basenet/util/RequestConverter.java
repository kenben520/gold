package com.lingxi.net.basenet.util;


import com.lingxi.net.basenet.INetDataObject;
import com.lingxi.net.basenet.NetRequest;


/**
 * Created by zhangwei on 2018/3/29.
 */
public class RequestConverter {
	
	private static final String TAG = "netsdk.RequestConverter";
	

	/**
	 * 将BizRequest转换成NetRequest
	 * @param inputDO
	 * @return
	 */
	public static NetRequest convertBizRequestToNetRequest(INetDataObject inputDO){
		if (inputDO==null) {
			return null;
		}
		NetRequest request= ReflectUtil.convertToNetRequest(inputDO);
		return request;
	}
}

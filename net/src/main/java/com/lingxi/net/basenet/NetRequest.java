package com.lingxi.net.basenet;


import com.lingxi.net.basenet.INetDataObject;
import com.lingxi.net.common.util.StringUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by zhangwei on 2018/3/29.
 */
public class NetRequest implements Serializable,INetDataObject {

	/**
	 *
	 */
	private static final long serialVersionUID = -439476282014493612L;

	/**
	 * 调用的api名称
	 */
	private String apiName;

	private String baseUrl;

	private String url;

	/**
	 * 接口唯一标识名
	 */
	private String apiUniqueKey;

	/**
	 * 调用的api版本
	 */
	private String version;

	/**
	 * 调用api的业务参数拼接成的data串,没有业务参数传"{}"即可
	 */
	private String data="{}";

	/**
	 * API 入参map，调用API时不用设置
	 */
	public Map<String,String> dataParams;


	/**
	 * @return the apiName
	 */
	public String getApiName() {
		return apiName;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @param apiName the apiName to set
	 */
	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public String getApiUniqueKey() {
		return apiUniqueKey;
	}

	public void setApiUniqueKey(String apiUniqueKey) {
		this.apiUniqueKey = apiUniqueKey;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		StringBuilder builder=new StringBuilder("NetRequest [");
		builder.append(" apiName=").append(apiName);
		builder.append(", version=").append(version);
		builder.append(", data=").append(data);
		builder.append("]");
		return builder.toString();
	}


	public String getKey() {
		if (StringUtils.isBlank(apiName) || StringUtils.isBlank(version)) {
			return null;
		}

		return	StringUtils.concatStr2LowerCase(apiName, version);

	}

}

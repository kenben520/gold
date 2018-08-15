package com.lingxi.net.basenet;
/**
 * Created by zhangwei on 2018/3/29.
 */
public enum MethodEnum {

	GET("GET"),POST("POST")/*,HEAD("HEAD"),PATCH("PATCH")*/;

	private String method;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	private MethodEnum(String method) {
		this.method = method;
	}



}

package com.lingxi.net.basenet;
/**
 * Created by zhangwei on 2018/3/29.
 */
public enum ProtocolEnum {
 
	HTTP("http://"),HTTPSECURE("https://");
	
	private String protocol;

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	private ProtocolEnum(String protocol) {
		this.protocol = protocol;
	}
}

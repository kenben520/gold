package com.lingxi.net.basenet;

import com.lingxi.net.basenet.INetDataObject;
import com.lingxi.net.basenet.MethodEnum;
import com.lingxi.net.basenet.ProtocolEnum;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangwei on 2018/3/29.
 */

public class NetworkExtraProp implements Serializable,INetDataObject {
	
	private static final long serialVersionUID = -3528337805304245196L;


	/**	 
	 * 请求协议类型  (http、https)
	 */
	protected ProtocolEnum protocol=ProtocolEnum.HTTPSECURE;

	/**
	 * 请求的服务器平台
	 */
	protected PlatformEnum platform=PlatformEnum.SELF_SERVER;


	/**
	 * Method类型(GET、POST)
	 */
	protected MethodEnum method=MethodEnum.GET;

	/**	 
	 * connHeaders 加入到Connection的Header 
	 */
	protected Map<String, String> requestHeaders;
    
    /**
     * 不参与生成cacheKey的参数列表
     */
    public List<String> cacheKeyBlackList=null;

    /**
     * 设置http url查询串参数
     */
    public Map<String, String> queryParameterMap;
    /**
     * 连接超时时间,单位ms
     */
    public int  connTimeout    = 0;
    
    /**
     * 读超时时间,单位ms
     */
    public int socketTimeout    = 0;


    /**
	 * @return the protocol
	 */
	public ProtocolEnum getProtocol() {
		return protocol;
	}
	/**
	 * @param protocol the protocol to set
	 */
	public void setProtocol(ProtocolEnum protocol) {
		if (null==protocol) {
			return;
		}
		this.protocol = protocol;
	}



	/**
	 * @return the method
	 */
	public MethodEnum getMethod() {
		return method;
	}

	public PlatformEnum getPlatform() {
		return platform;
	}

	public void setPlatform(PlatformEnum platform) {
		this.platform = platform;
	}

	/**
	 * @param method the method to set
	 */
	public void setMethod(MethodEnum method) {
		if (null==method) {
			return;
		}
		this.method = method;
	}

	public Map<String, String> getRequestHeaders() {
		return requestHeaders;
	}
	
	public void setRequestHeaders(Map<String, String> requestHeaders) {
		this.requestHeaders = requestHeaders;
	}
	

	@Override
	public String toString() {
		StringBuilder builder=new StringBuilder("NetworkExtraProp [");
		builder.append("protocol=").append(protocol);
		builder.append(", method=").append(method);
		builder.append(", requestHeaders=").append(requestHeaders);
		builder.append(", cacheKeyBlackList=").append(cacheKeyBlackList);
		builder.append(", queryParameterMap=").append(queryParameterMap);
		builder.append(", connTimeout=").append(connTimeout);
		builder.append(", socketTimeout=").append(socketTimeout);
		builder.append("]");
		return builder.toString();
	}

}

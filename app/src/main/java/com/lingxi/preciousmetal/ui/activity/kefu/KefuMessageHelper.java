package com.lingxi.preciousmetal.ui.activity.kefu;

import android.content.Context;
import android.text.TextUtils;

import com.hyphenate.helpdesk.model.AgentIdentityInfo;
import com.hyphenate.helpdesk.model.ContentFactory;
import com.hyphenate.helpdesk.model.OrderInfo;
import com.hyphenate.helpdesk.model.QueueIdentityInfo;
import com.hyphenate.helpdesk.model.VisitorInfo;
import com.hyphenate.helpdesk.model.VisitorTrack;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.domain.UserInfoBean;
import com.lingxi.preciousmetal.login.LoginHelper;

import java.util.Locale;
import java.util.Random;

public class KefuMessageHelper {

	public static VisitorInfo createVisitorInfo() {
		VisitorInfo info = ContentFactory.createVisitorInfo(null);
		if(LoginHelper.getInstance().isLogin())
		{
			UserInfoBean userInfoBean=LoginHelper.getInstance().getLoginUserInfo();
			info.nickName(userInfoBean.nick_name)
					.name(userInfoBean.user_name)
					.phone(userInfoBean.phone_mob)
					.description("")
					.email(userInfoBean.email);
		}else{
			info.nickName("")
					.name("")
					.phone("")
					.description("")
					.email("");
		}
		return info;
	}

	public static AgentIdentityInfo createAgentIdentity(String agentName) {
		if (TextUtils.isEmpty(agentName)){
			return null;
		}
		AgentIdentityInfo info = ContentFactory.createAgentIdentityInfo(null);
		info.agentName(agentName);
		return info;
	}
	
	public static QueueIdentityInfo createQueueIdentity(String queueName) {
		if (TextUtils.isEmpty(queueName)){
			return null;
		}
		QueueIdentityInfo info = ContentFactory.createQueueIdentityInfo(null);
		info.queueName(queueName);
		return info;
	}


	/**
	 * @return
	 */
	public static String getRandomAccount(){
		String val = "";
		Random random = new Random();
		for(int i = 0; i < 15; i++){
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; //输出字母还是数字
			if("char".equalsIgnoreCase(charOrNum)){// 字符串
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; //取得大写字母还是小写字母
				val += (char) (choice + random.nextInt(26));
			}else if("num".equalsIgnoreCase(charOrNum)){// 数字
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val.toLowerCase(Locale.getDefault());
	}
}

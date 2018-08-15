/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lingxi.preciousmetal.common;


import android.os.Build;

import java.util.Locale;

public class KefuMessageConstant {
	//--------------客服常量
	public static final String DEFAULT_CUSTOMER_APPKEY = "1460180731061271#kefuchannelapp55718";
	public static final String DEFAULT_CUSTOMER_ACCOUNT = "kefuchannelimid_803452";
	public static final String DEFAULT_ACCOUNT_PWD = "password";
	public static final String DEFAULT_USER_NAME = ("msd0" + Build.SERIAL).toLowerCase(Locale.getDefault());
	public static final String DEFAULT_TENANT_ID = "82717";
	public static final String DEFAULT_PROJECT_ID = "306713";

	public static final int MESSAGE_TO_DEFAULT = 0;
	public static final int MESSAGE_TO_PRE_SALES = 1;
	public static final int MESSAGE_TO_AFTER_SALES = 2;
	public static final String MESSAGE_TO_INTENT_EXTRA = "message_to";

	public static final String INTENT_CODE_IMG_SELECTED_KEY = "img_selected";
	public static final int INTENT_CODE_IMG_SELECTED_DEFAULT = 0;
}

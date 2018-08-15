package com.lingxi.preciousmetal.ui.activity.kefu;

import com.hyphenate.helpdesk.easeui.util.SmileUtils;
import com.lingxi.preciousmetal.R;

import java.util.LinkedHashMap;
import java.util.Map;

/** 表情集合类 */
public class KefuFaceManager {
	public static final String DELETE_KEY1 = "em_delete_delete_expression1";
	public static final String DELETE_KEY2 = "em_delete_delete_expression2";

	private KefuFaceManager() {
		initFaceMap();
	}

	private static KefuFaceManager instance;

	public static KefuFaceManager getInstance() {
		if (null == instance)
			instance = new KefuFaceManager();
		return instance;
	}
	
	public Map<String, Integer> getFaceMap(){
		return mFaceMap;
	}

	private Map<String, Integer> mFaceMap;

	private void initFaceMap() {
		mFaceMap = new LinkedHashMap<String, Integer>();
		mFaceMap.put(SmileUtils.ee_1, R.drawable.e_e_1);
		mFaceMap.put(SmileUtils.ee_2, R.drawable.e_e_2);
		mFaceMap.put(SmileUtils.ee_3, R.drawable.e_e_3);
		mFaceMap.put(SmileUtils.ee_4, R.drawable.e_e_4);
		mFaceMap.put(SmileUtils.ee_5, R.drawable.e_e_5);
		mFaceMap.put(SmileUtils.ee_6, R.drawable.e_e_6);
		mFaceMap.put(SmileUtils.ee_7, R.drawable.e_e_7);
		mFaceMap.put(SmileUtils.ee_8, R.drawable.e_e_8);
		mFaceMap.put(SmileUtils.ee_9, R.drawable.e_e_9);
		mFaceMap.put(SmileUtils.ee_10, R.drawable.e_e_10);
		mFaceMap.put(SmileUtils.ee_11, R.drawable.e_e_11);
		mFaceMap.put(SmileUtils.ee_12, R.drawable.e_e_12);
		mFaceMap.put(SmileUtils.ee_13, R.drawable.e_e_13);
		mFaceMap.put(SmileUtils.ee_14, R.drawable.e_e_14);
		mFaceMap.put(SmileUtils.ee_15, R.drawable.e_e_15);
		mFaceMap.put(SmileUtils.ee_16, R.drawable.e_e_16);
		mFaceMap.put(SmileUtils.ee_17, R.drawable.e_e_17);
		mFaceMap.put(SmileUtils.ee_18, R.drawable.e_e_18);
		mFaceMap.put(SmileUtils.ee_19, R.drawable.e_e_19);
		mFaceMap.put(SmileUtils.ee_20, R.drawable.e_e_20);
		mFaceMap.put(DELETE_KEY1, R.drawable.hd_delete_expression);

		mFaceMap.put(SmileUtils.ee_21, R.drawable.e_e_21);
		mFaceMap.put(SmileUtils.ee_22, R.drawable.e_e_22);
		mFaceMap.put(SmileUtils.ee_23, R.drawable.e_e_23);
		mFaceMap.put(SmileUtils.ee_24, R.drawable.e_e_24);
		mFaceMap.put(SmileUtils.ee_25, R.drawable.e_e_25);
		mFaceMap.put(SmileUtils.ee_26, R.drawable.e_e_26);
		mFaceMap.put(SmileUtils.ee_27, R.drawable.e_e_27);
		mFaceMap.put(SmileUtils.ee_28, R.drawable.e_e_28);
		mFaceMap.put(SmileUtils.ee_29, R.drawable.e_e_29);
		mFaceMap.put(SmileUtils.ee_30, R.drawable.e_e_30);
		mFaceMap.put(SmileUtils.ee_31, R.drawable.e_e_31);
		mFaceMap.put(SmileUtils.ee_32, R.drawable.e_e_32);
		mFaceMap.put(SmileUtils.ee_33, R.drawable.e_e_33);
		mFaceMap.put(SmileUtils.ee_34, R.drawable.e_e_34);
		mFaceMap.put(SmileUtils.ee_35, R.drawable.e_e_35);
		mFaceMap.put(DELETE_KEY2, R.drawable.hd_delete_expression);
	}

	public int getFaceId(String faceStr) {
		if (mFaceMap.containsKey(faceStr)) {
			return mFaceMap.get(faceStr);
		}
		return -1;
	}

}

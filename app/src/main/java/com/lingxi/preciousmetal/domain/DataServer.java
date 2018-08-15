package com.lingxi.preciousmetal.domain;


import com.lingxi.preciousmetal.base.MyApplication;
import com.lingxi.preciousmetal.util.ApplicationUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class DataServer {

//    private static final String HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK = "https://avatars1.githubusercontent.com/u/7698209?v=3&s=460";
//    private static final String CYM_CHAD = "CymChad";
//    private static final String CHAY_CHAN = "ChayChan";

    private DataServer() {

    }

    public static List<TimeTitleBean> getTimeTitleData() {
        ArrayList<TimeTitleBean> kTitles = new ArrayList<>();
        List<String> list = MyApplication.getSortSignalList();
        kTitles.add(new TimeTitleBean("分时",0));
        for (String tag: list){
            kTitles.add(new TimeTitleBean(ApplicationUtils.getTagName(tag),Integer.parseInt(tag)));
        }
//        kTitles.add(new TimeTitleBean("1分",1));
//        kTitles.add(new TimeTitleBean("5分",2));
//        kTitles.add(new TimeTitleBean("15分",3));
//        kTitles.add(new TimeTitleBean("30分",4));
//        kTitles.add(new TimeTitleBean("1小时",5));
//        kTitles.add(new TimeTitleBean("4小时",7));
//        kTitles.add(new TimeTitleBean("日k",8));
//        kTitles.add(new TimeTitleBean("1周",10));
//        kTitles.add(new TimeTitleBean("1月",11));
        kTitles.add(new TimeTitleBean("自定义",-1));
        return kTitles;
    }

    public static String getIndicatorsName(int childIndex) {
        List<FilterSection> list =  MyApplication.operationNoteBean.getIndicatorList();
        String name = null;
        for (FilterSection bean:list){
            if (bean.isHeader) {
                continue;
            }
            CalendarItem cItem = bean.t;
            if (cItem.getType() == 2) {
                cItem.setUserCheck(false);
                if (childIndex==Integer.parseInt(cItem.getTypeId())){
                     name = cItem.getName();
                    cItem.setUserCheck(true);
                } else {
                    cItem.setUserCheck(false);
                }
            }
        }
        return name;
    }

    public static List<FilterSection> getIndicatorsData() {
        List<FilterSection> list = new ArrayList<>();
        list.add(new FilterSection(true, "主图指标", true));
        list.add(new FilterSection(new CalendarItem(false, "BBI",1,"1")));
        list.add(new FilterSection(new CalendarItem(false, "BOLL",1,"2")));
        list.add(new FilterSection(new CalendarItem(true, "MA",1,"0")));
        list.add(new FilterSection(new CalendarItem(false, "MIKE",1,"3")));
        list.add(new FilterSection(new CalendarItem(false, "PBX",1,"4")));
        list.add(new FilterSection(true, "幅图指标", true));
        list.add(new FilterSection(new CalendarItem(false, "ARBR",2,"0")));
        list.add(new FilterSection(new CalendarItem(false, "ATR",2,"1")));
        list.add(new FilterSection(new CalendarItem(false, "BIAS",2,"2")));
        list.add(new FilterSection(new CalendarItem(false, "CCI",2,"3")));
        list.add(new FilterSection(new CalendarItem(false, "DKBY",2,"4")));
        list.add(new FilterSection(new CalendarItem(false, "KD",2,"5")));
        list.add(new FilterSection(new CalendarItem(false, "KDJ",2,"6")));
        list.add(new FilterSection(new CalendarItem(false, "LW&R",2,"7")));
        list.add(new FilterSection(new CalendarItem(true, "MACD",2,"8")));
//        list.add(new FilterSection(new CalendarItem(false, "QHLSR",2,"9")));
        list.add(new FilterSection(new CalendarItem(false, "RSI",2,"9")));
        list.add(new FilterSection(new CalendarItem(false, "W&R",2,"10")));
        return list;
    }


    public static List<FilterSection> getSampleData() {
        List<FilterSection> list = new ArrayList<>();
        list.add(new FilterSection(true, "地区", true));
        list.add(new FilterSection(new CalendarItem(false, "中国",1,"中国")));
        list.add(new FilterSection(new CalendarItem(false, "美国",1,"美国")));
        list.add(new FilterSection(new CalendarItem(false, "欧元区",1,"欧元区")));
        list.add(new FilterSection(new CalendarItem(false, "英国",1,"英国")));
        list.add(new FilterSection(new CalendarItem(false, "法国",1,"法国")));
        list.add(new FilterSection(new CalendarItem(false, "德国",1,"德国")));
        list.add(new FilterSection(new CalendarItem(false, "加拿大",1,"加拿大")));
        list.add(new FilterSection(new CalendarItem(false, "日本",1,"日本")));
//        list.add(new FilterSection(new CalendarItem(false, "台湾")));
//        list.add(new FilterSection(new CalendarItem(false, "欧盟")));
//        list.add(new FilterSection(new CalendarItem(false, "韩国")));

//        list.add(new FilterSection(new CalendarItem(false, "俄罗斯")));
//        list.add(new FilterSection(new CalendarItem(false, "英国")));
//        list.add(new FilterSection(new CalendarItem(false, "新加坡")));
//        list.add(new FilterSection(new CalendarItem(false, "美国")));
        list.add(new FilterSection(true, "状态", true));
        list.add(new FilterSection(new CalendarItem(false, "已公布",2,"0")));
        list.add(new FilterSection(new CalendarItem(false, "未公布",2,"1")));
        list.add(new FilterSection(true, "重要程度", false));
        list.add(new FilterSection(new CalendarItem(false, "一星",3,"1")));
        list.add(new FilterSection(new CalendarItem(false, "二星",3,"2")));
        list.add(new FilterSection(new CalendarItem(false, "三星",3,"3")));
        list.add(new FilterSection(new CalendarItem(false, "四星",3,"4")));
        list.add(new FilterSection(new CalendarItem(false, "五星",3,"5")));
        list.add(new FilterSection(true, "类型", false));
        list.add(new FilterSection(new CalendarItem(false, "数据",4,"FD")));
        list.add(new FilterSection(new CalendarItem(false, "事件",4,"FE")));
        list.add(new FilterSection(new CalendarItem(false, "假期",4,"VN")));
        return list;
    }

}

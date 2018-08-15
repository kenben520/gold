package com.lingxi.net.basenet.util;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.lingxi.net.basenet.INetDataObject;
import com.lingxi.net.basenet.NetConstant;
import com.lingxi.net.basenet.NetRequest;
import com.lingxi.net.common.util.NetSdkLog;
import com.lingxi.net.common.util.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ReflectUtil {

    private static final String TAG = "netsdk.ReflectUtil";
    //|version |是 |string | 端口版本 |
//|app |是 |string |控制器名称 |
//|act |是 |string |动作名称 |
    private static final String API_NAME = "API_NAME";
    private static final String APP = "app";
    private static final String ACT = "act";
    private static final String VERSION = "version";
    private static final String BASE_URL = "baseUrl";
    private static final String URL = "url";
    private static final String NEED_LOGIN = "NEED_LOGIN";
    private static final String NEED_SESSION = "NEED_SESSION";
    private static final String SERIAL_VERSION_UID = "serialVersionUID";
    private static final String ORIGINALJSON = "ORIGINALJSON";

    private static final HashMap<Class<?>, Field[]> classFields = new HashMap<>();

    /**
     * 将Object对象转成NetRequest
     *
     * @param input
     * @return
     */
    public static NetRequest convertToNetRequest(Object input) {
        NetRequest netRequest = new NetRequest();
        if (input != null) {
            // 解析协议参数
//            parseUrlParams(netRequest, input);
            // 处理业务参数
            Map<String, String> dataParams = parseDataParams(input);
            // 拼接data内业务参数
//            String dataStr = converMapToDataStr(dataParams);
//            netRequest.setData(dataStr);
            netRequest.dataParams = dataParams;
        }
        return netRequest;
    }

    /**
     * @param inputDO
     * @return
     */
    public static NetRequest convertToNetRequest(INetDataObject inputDO) {
        NetRequest netRequest = new NetRequest();
        if (inputDO != null) {
            // 解析协议参数
            parseUrlParams(netRequest, inputDO);
            // 处理业务参数
            Map<String, String> dataParams = parseDataParams(inputDO);
            netRequest.dataParams = dataParams;
        }
        return netRequest;
    }

    /**
     * 将dataParams中业务参数拼接成dataStr
     *
     * @param map
     * @return
     */
    public static String converMapToDataStr(final Map<String, String> map) {
        StringBuilder dataStr = new StringBuilder("{");
        if (map != null && map.size() > 0) {
            StringBuilder totalBuilder = new StringBuilder();
            for (Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (key != null && value != null) {
                    StringBuilder keyBuilder = new StringBuilder();
                    try {
                        keyBuilder.append(JSON.toJSONString(key));
                        keyBuilder.append(":");
                        keyBuilder.append(JSON.toJSONString(value));
                        keyBuilder.append(",");
                        totalBuilder.append(keyBuilder);
                    } catch (Throwable e) {
                        NetSdkLog.e(TAG, "[converMapToDataStr] convert key=" + key + ",value=" + value
                                + " to dataStr error ---" + e.toString());
                    }
                }
            }
            int length = totalBuilder.toString().length();
            if (length > 1) {
                dataStr.append(totalBuilder.substring(0, length - 1));
            }
        }
        dataStr.append("}");
        return dataStr.toString();

    }

    /**
     * 处理业务参数，忽略协议参数大小写
     *
     * @param input INetDataObject实例对象
     */
    public static Map<String, String> parseDataParams(INetDataObject input) {
        if (input == null) {
            return new HashMap<String, String>();
        }
        Class<?> clz = input.getClass();
        return parseFields(input, clz);
    }

    /**
     * 处理业务参数，忽略协议参数大小写
     *
     * @param input JavaBean实例对象
     */
    private static Map<String, String> parseDataParams(Object input) {
        if (input == null) {
            return new HashMap<String, String>();
        }
        Class<?> clz = input.getClass();
        return parseFields(input, clz);
    }

    /**
     * 处理业务参数，忽略协议参数大小写
     *
     * @param input JavaBean实例对象
     */
    private static Map<String, String> parseFields(Object input, Class<?> clz) {
        HashMap<String, String> dataParams = new HashMap<String, String>();
        // 获取当前类的所有属性，包括public、protected、private的，但是不包括父类的属性
        Field[] subFields = getClassFields(clz);
        parseFieldsToMap(input, subFields, dataParams, false);

        Class<?> superClass = clz.getSuperclass();
        while (superClass != null && superClass != Object.class) {
            Field[] superFields = getClassFields(superClass);
            parseFieldsToMap(input, superFields, dataParams, false);
            superClass = superClass.getSuperclass();
        }

        // 获取所有public的属性，包括父类的；
//        Field[] publicFields = clz.getFields();
//        parseFieldsToMap(input, publicFields, dataParams, true);
        return dataParams;
    }

    private static Field[] getClassFields(Class<?> clz) {
        if (classFields != null && classFields.size() > 0 && classFields.containsKey(clz)) {
            return classFields.get(clz);
        }
        return clz.getDeclaredFields();
    }

    /**
     * 解析属性值到Map中，忽略协议参数大小写
     */
    private static void parseFieldsToMap(Object input, Field[] fields, HashMap<String, String> dataParams,
                                         boolean checkFieldInMap) {
        if (fields == null || fields.length == 0) {
            return;
        }
        String fieldName = null;
        Object value = null;
        for (int i = 0; i < fields.length; i++) {
            value = null;
            try {
                fieldName = fields[i].getName();
                if (excludeField(fieldName, dataParams, checkFieldInMap)) {
                    /*
                     * 1、如果是内部类，去掉当前相关类引用；
                     * 2、排除api,v,ecode,sid,serialVersionUID等参数；
                     * 3、排除ORIGINALJSON参数；
                     */
                    continue;
                } else {
                    fields[i].setAccessible(true);
                    value = fields[i].get(input);
                }
            } catch (Throwable e) {
                NetSdkLog.e(TAG, "[parseFieldsToMap]get biz param error through reflection.---" + e.toString());
            }
            if (value != null) {
                try {
                    if (value instanceof String) {
                        dataParams.put(fieldName, value.toString());
                    } else {
                        dataParams.put(fieldName, JSON.toJSONString(value));
                    }
                } catch (Throwable e) {
                    NetSdkLog.e(TAG, "[parseFieldsToMap]transform biz param to json string error.---" + e.toString());
                }
            }

        }
    }

    /**
     * 排除属性值
     *
     * @param fieldName       属性名
     * @param dataParams      接受的属性集合
     * @param checkFieldInMap 是否判断属性集合中包含当前属性值
     * @return boolean true：排除；false：不排除。
     */
    private static boolean excludeField(String fieldName, HashMap<String, String> dataParams, boolean checkFieldInMap) {
        /*
         * 1、如果是内部类，去掉当前相关类引用；
         * 2、排除api,v,ecode,sid,serialVersionUID等参数；
         * 3、排除ORIGINALJSON参数；
         */
        if ((fieldName.indexOf("$") != -1)) {
            /*
             * 1、如果是内部类，去掉当前相关类引用；
             */
            return true;
        } else if (API_NAME.equals(fieldName) || VERSION.equals(fieldName) || NEED_LOGIN.equals(fieldName)
                || NEED_SESSION.equals(fieldName) || SERIAL_VERSION_UID.equalsIgnoreCase(fieldName)) {
            /*
             * 2、排除api,v,ecode,sid,serialVersionUID等参数；
             */
            return true;
        } else if (ORIGINALJSON.equalsIgnoreCase(fieldName)) {
            /*
             * 3、排除ORIGINALJSON参数；
             */
            return true;
        } else if (checkFieldInMap) {
            /*
             * 判断数据Map重复field；
             * 子类存在重复public属性时，不需要再获取该属性值，
             * 因为class.getFields()会获取所有public的属性，包括父类的；
             * 当子类存在与父类同名的属性时，会返回子类的属性值，
             * 因此，在class.getDeclaredFields()时，就不需要再获取同名属性值了。
             */
            if (dataParams.containsKey(fieldName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 处理协议参数
     *
     * @param request
     * @param input
     */
    public static void parseUrlParams(NetRequest request, Object input) {
        if (input != null) {
            Gson gson = new Gson();
            String jsonData = gson.toJson(input);//通过Gson将Bean转化为Json字符串形式
            request.setData(jsonData);

            Object api1Object = ReflectUtil.getFieldValueByName(API_NAME, input);
            Object api2Object = ReflectUtil.getFieldValueByName(APP, input);
            Object api3Object = ReflectUtil.getFieldValueByName(ACT, input);
            Object api4Object = ReflectUtil.getFieldValueByName(BASE_URL, input);
            Object api5Object = ReflectUtil.getFieldValueByName(URL, input);
//            if (api1Object != null) {
//                String api = api1Object.toString();
//                request.setApiName(api);
//            }

            if (api2Object != null && api3Object != null) {
                String app = api2Object.toString();
                String act = api3Object.toString();
                request.setApiUniqueKey(app + "&" + act);
            }

            String baseUrl = NetConstant.Companion.getBASE_URL();
            if (api4Object != null) {
                String url = api4Object.toString();
                baseUrl = StringUtils.isEmpty(url) ? baseUrl : url;
            }
            request.setBaseUrl(baseUrl);

            if (api5Object != null) {
                String url = api5Object.toString();
                request.setUrl(url);
            }

            Object versionObject = ReflectUtil.getFieldValueByName(VERSION, input);
            if (versionObject != null) {
                String version = versionObject.toString();
                request.setVersion(version);
            }
        }

    }


    /**
     * 通过反射获取对象的属性值
     *
     * @param fieldName
     * @param o
     * @return
     */
    public static Object getFieldValueByName(String fieldName, Object o) {
        if (o == null || fieldName == null) {
            return null;
        }
        Class<?> c = (Class<?>) o.getClass();
        Field[] fs = c.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            f.setAccessible(true);
            if (f.getName().equals(fieldName)) {
                try {
                    return f.get(o);
                } catch (IllegalArgumentException e) {
                    NetSdkLog.e(TAG, e.toString());
                } catch (IllegalAccessException e) {
                    NetSdkLog.e(TAG, e.toString());
                }
            }
        }
        return null;
    }

}

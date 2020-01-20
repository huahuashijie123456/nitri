package com.fuhuitong.nitri.sys.conf;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @Author Wang
 * @Date 2019/5/25 0025 11:49
 **/
public class JsonUtils {
    /**
     * json string 转换为 map 对象
     *
     * @param jsonStr
     * @return
     */
    public static Map<Object, Object> jsonToMap(String jsonStr) {
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        Map<Object, Object> map = (Map) jsonObject;
        return map;
    }

    /**
     * json string 转换为 对象
     *
     * @param jsonObj
     * @param type
     * @return
     */
    public static <T> T jsonToBean(String jsonObj, Class<T> type) {
        T obj = JSONObject.parseObject(jsonObj, type);
        return obj;
    }
}

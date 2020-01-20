package com.fuhuitong.nitri.common.utils;


import com.fuhuitong.nitri.sys.entity.Dict;
import com.fuhuitong.nitri.sys.service.DictServiceImpl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * @Author Wang
 * @Date 2019/3/21 0021 9:59
 **/
@Component
public class DictUtils {
    public static final String CACHE_DICT_MAP = "dictMap";
    public static DictUtils Tool;

    @PostConstruct
    public void init() {
        Tool = this;
    }

    @Autowired
    private DictServiceImpl dictService;
    @Autowired
    private RedisUtil redisUtil;


    public static String getDictLabel(String value, String type, String defaultValue) {
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)) {
            List<Dict> dictlist = getDictList(type);


            JSONArray array = JSONArray.parseArray(JSON.toJSONString(dictlist));

            List<Dict> list = JSONObject.parseArray(array.toJSONString(), Dict.class);

            for (Dict dict : list) {

                String t = dict.getType();
                if (type.equals(t) && value.equals(dict.getValue())) {
                    return dict.getLabel();
                }
            }
        }
        return defaultValue;
    }

    public static String getDictLabels(String values, String type, String defaultValue) {
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(values)) {
            List<String> valueList = Lists.newArrayList();
            for (String value : StringUtils.split(values, ",")) {
                valueList.add(getDictLabel(value, type, defaultValue));
            }
            return StringUtils.join(valueList, ",");
        }
        return defaultValue;
    }

    public static String getDictValue(String label, String type, String defaultLabel) {
        List<Dict> dictlist = getDictList(type);
        JSONArray array = JSONArray.parseArray(JSON.toJSONString(dictlist));
        List<Dict> list = JSONObject.parseArray(array.toJSONString(), Dict.class);

        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(label)) {
            for (Dict dict : list) {
                if (type.equals(dict.getType()) && label.equals(dict.getLabel())) {
                    return dict.getValue();
                }
            }
        }
        return defaultLabel;
    }


    public static List<Dict> getDictList(String type) {
        Object obj = Tool.redisUtil.get(CACHE_DICT_MAP);
        String dictMapstr = "";
        if (obj != null) {
            dictMapstr = obj.toString();
        }
        JSONObject jasonObject = JSONObject.parseObject(dictMapstr);
        Map<String, List<Dict>> dictMap = (Map) jasonObject;
        if (dictMapstr == null || dictMap == null) {
            dictMap = Maps.newHashMap();
            for (Dict dict : Tool.dictService.getDictList()) {
                List<Dict> dictList = dictMap.get(dict.getType());
                if (dictList != null) {
                    dictList.add(dict);
                } else {
                    dictMap.put(dict.getType(), Lists.newArrayList(dict));
                }
            }
            Tool.redisUtil.set(CACHE_DICT_MAP, JSON.toJSONString(dictMap));
        }
        List<Dict> dictList = dictMap.get(type);
        if (dictList == null) {
            dictList = Lists.newArrayList();
        }


        return dictList;
    }


    public static void delDictRedis() {
        Tool.redisUtil.del(CACHE_DICT_MAP);
    }


}

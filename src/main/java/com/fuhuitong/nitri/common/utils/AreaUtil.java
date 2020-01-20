package com.fuhuitong.nitri.common.utils;

import com.fuhuitong.nitri.sys.entity.SysArea;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author Wang
 * @Date 2019/4/19 0019 15:35
 **/
public class AreaUtil {

    public static List<SysArea> getChild(String id, List<SysArea> rootArea) {
        List<SysArea> childList = rootArea.stream().filter(menu -> menu.getParentCode().equals(id)).collect(Collectors.toList());
        childList.forEach(menu -> menu.setSubs(getChild(menu.getAreaCode(), rootArea)));
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }

    public static Map<String, Object> getAreaTree(List<SysArea> rootArea) {
        List<SysArea> menuList = rootArea.stream().filter(treeDatum -> treeDatum.getParentCode().equals("0")).collect(Collectors.toList());
        for (SysArea menu : menuList) menu.setSubs(getChild(menu.getAreaCode(), rootArea));
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("area", menuList);
        return jsonMap;
    }


}

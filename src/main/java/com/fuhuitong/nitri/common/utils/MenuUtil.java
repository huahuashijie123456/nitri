package com.fuhuitong.nitri.common.utils;

import com.fuhuitong.nitri.sys.entity.Menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author Wang
 * @Date 2019/4/19 0019 15:35
 **/
public class MenuUtil {

    public static List<Menu> getChild(String id, List<Menu> rootMenu) {
        List<Menu> childList = rootMenu.stream().filter(menu -> menu.getParentId().equals(id)).collect(Collectors.toList());
        childList.forEach(menu -> menu.setSubs(getChild(menu.getId(), rootMenu)));
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }

    public static Map<String, Object> getMenu(List<Menu> treeData) {
        List<Menu> menuList = new ArrayList<>();
        for (Menu treeDatum : treeData) {

            if (treeDatum.getParentId().equals("0")) {
                menuList.add(treeDatum);
            }
        }
        for (Menu menu : menuList) {
            menu.setSubs(getChild(menu.getId(), treeData));
        }
        Map<String, Object> jsonMap = new HashMap();
        jsonMap.put("menu", menuList);
        return jsonMap;
    }


}

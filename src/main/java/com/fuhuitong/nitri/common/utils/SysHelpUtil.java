package com.fuhuitong.nitri.common.utils;

import com.fuhuitong.nitri.sys.entity.Department;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author Wang
 * @Date 2019/5/21 0019 13:26
 **/
public class SysHelpUtil {

    public static List<Department> getChild(String id, List<Department> rootDepartment) {
        List<Department> childList = rootDepartment.stream().filter(Department -> Department.getParentId().equals(id)).collect(Collectors.toList());
        childList.forEach(Department -> Department.setSubs(getChild(Department.getId(), rootDepartment)));
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }

    public static Map<String, Object> getDepartment(List<Department> treeData) {
        List<Department> DepartmentList = treeData.stream().filter(treeDatum -> treeDatum.getParentId().equals("0")).collect(Collectors.toList());
        for (Department Department : DepartmentList) Department.setSubs(getChild(Department.getId(), treeData));
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("Department", DepartmentList);
        return jsonMap;
    }


}

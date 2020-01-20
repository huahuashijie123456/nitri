package com.fuhuitong.nitri.common.utils.Excel;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Author Wang
 * @Date 2019/5/14 0014 9:55
 **/
public class MapSortDemo {
    public static void main(String[] args) {

        Map<Integer, ExcelEntity> map = new TreeMap();

        ExcelEntity excelEntity = new ExcelEntity();
        excelEntity.setSort(4);
        map.put(4, excelEntity);

        ExcelEntity excelEntity2 = new ExcelEntity();
        excelEntity2.setSort(7);
        map.put(7, excelEntity2);


        ExcelEntity excelEntity1 = new ExcelEntity();
        excelEntity1.setSort(1);
        map.put(1, excelEntity1);

        Map<Integer, ExcelEntity> resultMap = sortMapByKey(map);    //按Key进行排序

        for (Map.Entry<Integer, ExcelEntity> entry : resultMap.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    /**
     * 使用 Map按key进行排序
     *
     * @param map
     * @return
     */
    public static Map<Integer, ExcelEntity> sortMapByKey(Map<Integer, ExcelEntity> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<Integer, ExcelEntity> sortMap = new TreeMap<Integer, ExcelEntity>(new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }
}


class MapKeyComparator implements Comparator<Integer> {
    @Override
    public int compare(Integer o1, Integer o2) {
        return o1.compareTo(o2);
    }
}




/*
class MapKeyComparator implements Comparator<String> {

    @Override
    public int compare(String str1, String str2) {

        return str1.compareTo(str2);
    }


}
*/

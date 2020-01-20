package com.fuhuitong.nitri.common.utils.Excel;

import com.fuhuitong.nitri.common.utils.DictUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * obj：需要导出的数据集合
 * className：需要导出集合的对象Class
 * fileName: 导出Excel文件名
 * excelHeaders：导出Excel内容头信息
 * fields：需要导出的对应字段
 *
 * @Author Wang
 * @Date 2019/5/11 0011 14:49
 **/
public class ExcelExportUtils {
    public static void publicExport(List objList, Class className, String fileName, HttpServletResponse response) throws Exception {
        Map<Integer, ExcelEntity> map = new TreeMap();
        Field[] fs = className.getDeclaredFields();
        for (Field f : fs) {
            if (f.isAnnotationPresent(ExcelField.class)) {
                ExcelField fieldAnnotation = f.getDeclaredAnnotation(ExcelField.class);
                if (fieldAnnotation.type() == 0 || fieldAnnotation.type() == 3) {  // 0导出 1导入
                    ExcelEntity excelEntity = new ExcelEntity(fieldAnnotation.sort(), f.getName(), fieldAnnotation.name(), fieldAnnotation.dict(), fieldAnnotation.dataformat(), fieldAnnotation.permission());
                    map.put(fieldAnnotation.sort(), excelEntity);
                }
            }
        }

        XSSFWorkbook book = null;
        //管理员导出数据
        book = new XSSFWorkbook();
        //创建一个Name的新表
        XSSFSheet sheet = book.createSheet(fileName);
        sheet.setDefaultColumnWidth(20);
        sheet.setDefaultRowHeightInPoints(20);
        // sheet.autoSizeColumn((short)0);
        // 获取表的第一行
        XSSFRow firstRow = sheet.createRow(0);
        //创建表的第一行的每列的说明
        XSSFCell[] firstCells = new XSSFCell[map.size()];
        //给表的第一行的每一列赋值

        Map<Integer, ExcelEntity> resultMap = MapSortDemo.sortMapByKey(map);    //按Key进行排序
        int j = 0;
        for (Map.Entry<Integer, ExcelEntity> entry : resultMap.entrySet()) {
            firstCells[j] = firstRow.createCell(j);
            firstCells[j].setCellValue(new XSSFRichTextString(entry.getValue().getName()));
            j++;
        }
        List queryList = (List) objList;
        for (int i = 0; i < queryList.size(); i++) {
            XSSFRow row = sheet.createRow(i + 1);
            int lie = 0;
            for (Map.Entry<Integer, ExcelEntity> entry : resultMap.entrySet()) {
                Object object = queryList.get(i);
                String value = (className.getMethod("get" + firstUpper(entry.getValue().getFieldName())).invoke(object) == null) ? "" : className.getMethod("get" + firstUpper(entry.getValue().getFieldName())).invoke(object) + "";
                if (StringUtils.isNotBlank(entry.getValue().getDict())) {
                    if (value.indexOf(",") > 0) {
                        value = DictUtils.getDictLabels(value, entry.getValue().getDict(), "");
                    } else {
                        value = DictUtils.getDictLabel(value, entry.getValue().getDict(), "");
                    }
                } else if (StringUtils.isNotBlank(entry.getValue().getDataformat())) {
                    if (value.length() > 1) {
                        if (value.indexOf("T") > 0) {
                            value = value.replace("T", " ");
                            if (value.length() == 16) {
                                value = value + ":00";
                            }
                        }
                        DateTimeFormatter df = DateTimeFormatter.ofPattern(entry.getValue().getDataformat());
                        LocalDateTime ldt = LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                        value = df.format(ldt);
                    }
                }
                XSSFCell cell = row.createCell(lie);
                cell.setCellValue(value);
                System.out.print(value);
                lie++;
            }
        }
        OutputStream os = null;
        try {
            //防止导出的Excel文件名中文乱码
            String filename = new String(fileName.getBytes("utf-8"), "ISO_8859_1");
            response.setHeader("Content-Disposition", "attachment;filename=" + filename + ".xlsx");
            response.setContentType("application");
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            os = response.getOutputStream();
            book.write(os);
        } catch (IOException e) {
            System.out.println("IO流异常");
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                System.out.println("关闭IO流异常");
            }
        }
    }

    /**
     * 将字符串的第一个字母转换成大写
     */
    public static String firstUpper(String string) {
        char[] charArray = string.toCharArray();
        charArray[0] -= 32;
        return String.valueOf(charArray);
    }
}

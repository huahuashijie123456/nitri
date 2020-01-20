package com.fuhuitong.nitri.common.utils.Excel;

/**
 * @Author Wang
 * @Date 2019/4/1 0001 18:52
 **/


import com.fuhuitong.nitri.common.utils.DictUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.apache.http.client.utils.DateUtils.parseDate;

public class ExcelImportUtils {

    private final static String excel2003L = ".xls"; // 2003- 版本的excel
    private final static String excel2007U = ".xlsx"; // 2007+ 版本的excel


    /**
     * 将流中的Excel数据转成List<Map>(读取Excel)
     *
     * @param in       输入流
     * @param fileName 文件名（判断Excel版本）
     * @return
     * @throws Exception
     */
    public static List<Object> readExcel(Class className, InputStream in, String fileName, int stratrow) throws Exception {

        Map<String, String> treemap = new TreeMap<>();
        Field[] fs = className.getDeclaredFields();
        for (Field f : fs) {
            if (f.isAnnotationPresent(ExcelField.class)) {
                ExcelField fieldAnnotation = f.getDeclaredAnnotation(ExcelField.class);
                if (fieldAnnotation.type() == 1 || fieldAnnotation.type() == 3) {  // 0导出 1导入
                    treemap.put(fieldAnnotation.name(), f.getName());
                }
            }
        }

        // 根据文件名来创建Excel工作薄
        Workbook work = getWorkbook(in, fileName);
        if (null == work) {
            throw new Exception("创建Excel工作薄为空！");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;
        // 返回数据
        List<Map<String, Object>> resultList = new ArrayList<>();
        //循环多个工作表
        //   for (int i = 0; i < work.getNumberOfSheets(); i++) {
        Map<String, Object> result = new HashMap<>();
        sheet = work.getSheetAt(0);

        //获取有合并单元格的区域
        List<CellRangeAddress> combineCellList = getCombineCellList(sheet);
        // 测试有几行数据是有表头数据的
        Boolean flag = true;
        int h = 0;
        for (int j = sheet.getFirstRowNum(); j < sheet.getLastRowNum() + 1; j++) {
            row = sheet.getRow(j);
            // 遍历所有的列
            if (flag) {
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                    cell = row.getCell(y);
                    Object v = getCellValue(cell);
                    flag = false;
                    if (v != null && !v.toString().equals("")) {
                        flag = true;
                        break;
                    }
                }
                h++;
            }
        }
        //列数
        int colNum = 0;
        //表头行数
        // int h1=h-0;   //h1 3,  h 4
        //表头数据
        List<List> list1 = new ArrayList<>();
        //循环行，不要从0开始，防止前几行为空的情况
        List<Map> list2 = new ArrayList<>();

        //起始行 必须包含表头
        //  int stratrow=0;
        List<String> headList = new LinkedList(); //表头
        //sheet.getFirstRowNum()
        for (int k = stratrow; k < sheet.getFirstRowNum() + h; k++) {
            row = sheet.getRow(k);
            if (k == stratrow) {
                for (int x = row.getFirstCellNum(); x < row.getLastCellNum(); x++) { //每列 表头
                    String value = getCellValue(row.getCell(x)).toString();
                    headList.add(treemap.get(value));
                    System.out.println(value);
                }
                continue;
            }

            Map<String, Object> map = new HashMap<>();
            List dataList = new LinkedList();
            System.out.println("==================");
            for (int x = row.getFirstCellNum(); x < row.getLastCellNum(); x++) {
                cell = row.getCell(x);
                //表格cell的数据，合并的只有左上角一个有数据，其余全为空
                String v = getCellValue(cell).toString();
                dataList.add(v);
                // System.out.println(x+"v-->"+v);
                map.put(headList.get(x), v);
            }
            list2.add(map);
        }


        List<Object> listObj = new ArrayList();
        for (int i = 0; i < list2.size(); i++) {
            listObj.add(setFieldValue(className.newInstance(), list2.get(i)));

        }


        // System.out.print("list2==>"+list2.get(0).get(0));
        //  resultList.add(result);
        //    }

        //work.close();
        return listObj;
    }

    //获取合并单元格集合
    public static List<CellRangeAddress> getCombineCellList(Sheet sheet) {
        List<CellRangeAddress> list = new ArrayList<>();
        //获得一个 sheet 中合并单元格的数量
        int sheetmergerCount = sheet.getNumMergedRegions();
        //遍历所有的合并单元格
        for (int i = 0; i < sheetmergerCount; i++) {
            //获得合并单元格保存进list中
            CellRangeAddress ca = sheet.getMergedRegion(i);
            list.add(ca);
        }
        return list;
    }


    /**
     * 描述：根据文件后缀，自适应上传文件的版本
     *
     * @param inStr ,fileName
     * @return
     * @throws Exception
     */
    public static Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
        Workbook wb = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (excel2003L.equals(fileType)) {
            wb = new HSSFWorkbook(inStr); // 2003-
        } else if (excel2007U.equals(fileType)) {
            wb = new XSSFWorkbook(inStr); // 2007+
        } else {
            throw new Exception("解析的文件格式有误！");
        }
        return wb;
    }

    /**
     * 描述：对表格中数值进行格式化
     *
     * @param cell
     * @return
     */
    public static Object getCellValue(Cell cell) {
        Object value = null;
        DecimalFormat df = new DecimalFormat("0"); // 格式化number String字符
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd"); // 日期格式化
        DecimalFormat df2 = new DecimalFormat("0"); // 格式化数字
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    value = cell.getRichStringCellValue().getString();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                        value = df.format(cell.getNumericCellValue());
                    } else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
                        value = sdf.format(cell.getDateCellValue());
                    } else {
                        value = df2.format(cell.getNumericCellValue());
                    }
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    value = cell.getBooleanCellValue();
                    break;
                case Cell.CELL_TYPE_BLANK:
                    value = "";
                    break;
                case Cell.CELL_TYPE_ERROR:
                    value = "";
                    break;
                default:
                    break;
            }
        } else {
            value = "";
        }
        return value;
    }

    public static void main(String[] args) throws Exception {
       /* Customer customer=new Customer();
        Map m=new TreeMap();
        m.put("name","wanghongwei");
        m.put("phone","18700092834");
        Customer customer1=(Customer)setFieldValue(customer,m);
        System.out.println(customer1);*/

        //   List<Map> l=getNetWorkExcel("http://images.sptc.wang/1558682761416c5db844ee55145ad8145c9415ad30e95.xlsx");

        //    System.out.println(l);

    }


    /**
     * set属性的值到Bean
     *
     * @param bean
     * @param valMap
     */
    public static Object setFieldValue(Object bean, Map<String, String> valMap) {
        Class<?> cls = bean.getClass();
        // 取出bean里的所有方法
        Method[] methods = cls.getDeclaredMethods();
        Field[] fields = cls.getDeclaredFields();

        for (Field field : fields) {
            try {
                String fieldSetName = parSetName(field.getName());
                if (!checkSetMet(methods, fieldSetName)) {
                    continue;
                }
                ExcelField fieldAnnotation = field.getDeclaredAnnotation(ExcelField.class);
                if (field.isAnnotationPresent(ExcelField.class) && fieldAnnotation.type() == 1) {

                    //  String dict=fieldAnnotation.dict();

                    Method fieldSetMet = cls.getMethod(fieldSetName, field.getType());
                    String value = valMap.get(field.getName());
                    if (null != value && !"".equals(value)) {
                        String fieldType = field.getType().getSimpleName();
                        if ("String".equals(fieldType)) {
                            fieldSetMet.invoke(bean, getValue(fieldAnnotation, value));
                        }
                        if ("LocalDateTime".equals(fieldType)) {
                            fieldSetMet.invoke(bean, parseLocalDateTime(value + " 00:00:00"));
                        } else if ("Date".equals(fieldType)) {
                            Date temp = parseDate(getValue(fieldAnnotation, value));
                            fieldSetMet.invoke(bean, temp);
                        } else if ("Integer".equals(fieldType) || "int".equals(fieldType)) {
                            Integer intval = Integer.parseInt(getValue(fieldAnnotation, value));
                            fieldSetMet.invoke(bean, intval);
                        } else if ("Long".equalsIgnoreCase(fieldType)) {
                            Long temp = Long.parseLong(getValue(fieldAnnotation, value));
                            fieldSetMet.invoke(bean, temp);
                        } else if ("Double".equalsIgnoreCase(fieldType)) {
                            Double temp = Double.parseDouble(getValue(fieldAnnotation, value));
                            fieldSetMet.invoke(bean, temp);
                        } else if ("Boolean".equalsIgnoreCase(fieldType)) {
                            Boolean temp = Boolean.parseBoolean(getValue(fieldAnnotation, value));
                            fieldSetMet.invoke(bean, temp);
                        } else {
                            System.out.println("not supper type" + fieldType);
                        }
                    }
                }
            } catch (Exception e) {
                continue;
            }
        }
        return bean;
    }

    private static String getValue(ExcelField fieldAnnotation, String value) {
        if (!"".equals(fieldAnnotation.dict())) {
            return DictUtils.getDictValue(value, fieldAnnotation.dict(), "");
        }
        return value;
    }

    private static String parSetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        return "set" + fieldName.substring(0, 1).toUpperCase()
                + fieldName.substring(1);
    }

    public static LocalDateTime parseLocalDateTime(String dateStr) {
        DateTimeFormatter df1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateStr, df1);
    }


    /**
     * 判断是否存在某属性的 set方法
     *
     * @param methods
     * @param fieldSetMet
     * @return boolean
     */
    private static boolean checkSetMet(Method[] methods, String fieldSetMet) {
        for (Method met : methods) {
            if (fieldSetMet.equals(met.getName())) {
                return true;
            }
        }
        return false;
    }


    public static List<Object> getNetWorkExcel(String uri, Class className, int startRow) throws Exception {
        String filename = uri.substring(uri.lastIndexOf("/") + 1);
        URL url = null;
        url = new URL(uri);
        URLConnection conn = url.openConnection();
        conn.setConnectTimeout(3000);
        conn.setReadTimeout(3 * 60 * 1000);
        InputStream is = conn.getInputStream();
        List<Object> ls = readExcel(className, is, filename, startRow);
        return ls;
    }


}
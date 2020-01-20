package com.fuhuitong.nitri.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Wang
 * @Date 2019/5/8 0008 14:58
 **/
public class RedeemCodeUtils {

    public static void main(String[] args) {
        String st1 = createBigSmallLetterStrOrNumberRadom(16);
        String st2 = createSmallStrOrNumberRadom(8);
        String st3 = createBigStrOrNumberRadom(8);
        System.out.println(st1);
        System.out.println(st2);
        System.out.println(st3);
    }


    /**
     * 生成随机字符串List
     *
     * @param length
     * @param size
     * @return
     */
    public static List<String> createBigSmallLetterStrOrNumberRadomList(int length, int size) {
        List<String> list = new ArrayList();
        for (int i = 0; i < size; i++) {
            //RC4.
            list.add(createBigSmallLetterStrOrNumberRadom(length));
        }
        return list;
    }

    /**
     * @param num
     * @return
     * @author wangqing@9fbank.com 2015-6-26 下午2:51:44
     * @function 生成num位的随机字符串(数字 、 大写字母随机混排)
     */
    public static String createBigSmallLetterStrOrNumberRadom(int num) {

        String str = "";
        for (int i = 0; i < num; i++) {
            int intVal = (int) (Math.random() * 58 + 65);
            if (intVal >= 91 && intVal <= 96) {
                i--;
            }
            if (intVal < 91 || intVal > 96) {
                if (intVal % 2 == 0) {
                    str += (char) intVal;
                } else {
                    str += (int) (Math.random() * 10);
                }
            }
        }
        return str;
    }

    /**
     * @param num
     * @return
     * @author wangqing@9fbank.com 2015-6-26 下午2:51:44
     * @function 生成num位的随机字符串(数字 、 小写字母随机混排)
     */
    public static String createSmallStrOrNumberRadom(int num) {

        String str = "";
        for (int i = 0; i < num; i++) {
            int intVal = (int) (Math.random() * 26 + 97);
            if (intVal % 2 == 0) {
                str += (char) intVal;
            } else {
                str += (int) (Math.random() * 10);
            }
        }
        return str;
    }

    /**
     * @param num
     * @return
     * @author wangqing@9fbank.com 2015-6-26 下午2:51:44
     * @function 生成num位的随机字符串(小写字母与数字混排)
     */
    public static String createBigStrOrNumberRadom(int num) {

        String str = "";
        for (int i = 0; i < num; i++) {
            int intVal = (int) (Math.random() * 26 + 65);
            if (intVal % 2 == 0) {
                str += (char) intVal;
            } else {
                str += (int) (Math.random() * 10);
            }
        }
        return str;
    }

}

package com.fuhuitong.nitri.common.utils;

import java.util.Random;

/**
 * @Author Wang
 * @Date 2019/6/3 0003 11:27
 **/
public class RandonNumberUtils {
    private static Random random = new Random();

    public static String getRandonNumber(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(7));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("4位随机验证码：  " + getRandonNumber(4));
        System.out.println("5位随机验证码：  " + getRandonNumber(5));
        System.out.println("6位随机验证码：  " + getRandonNumber(6));
    }

}

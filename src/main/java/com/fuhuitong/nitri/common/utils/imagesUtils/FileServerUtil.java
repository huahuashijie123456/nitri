package com.fuhuitong.nitri.common.utils.imagesUtils;

public class FileServerUtil {
    public static boolean checkFileSizeIsLimit(Long fileLen, int fileSize, String fileUnit) {
//        long len = file.length();
        double fileSizeCom = 0;
        if ("B".equals(fileUnit.toUpperCase())) {
            fileSizeCom = (double) fileLen;
        } else if ("K".equals(fileUnit.toUpperCase())) {
            fileSizeCom = (double) fileLen / 1024;
        } else if ("M".equals(fileUnit.toUpperCase())) {
            fileSizeCom = (double) fileLen / (1024*1024);
        } else if ("G".equals(fileUnit.toUpperCase())) {
            fileSizeCom = (double) fileLen / (1024*1024*1024);
        }
        if (fileSizeCom > fileSize) {
            return false;
        }
        return true;
    }
}

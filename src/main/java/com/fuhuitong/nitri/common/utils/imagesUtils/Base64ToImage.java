package com.fuhuitong.nitri.common.utils.imagesUtils;

import com.fuhuitong.nitri.common.utils.SnowflakeIdWorker;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class Base64ToImage {
    /**
     *
     * @param BASE64str bas64字符串
     * @param path 存储地址
     * @param ext 图片后缀
     * @return 存储地址
     */
    private static SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(0, 0);

    public static String BASE64CodeToBeImage(String BASE64str,String path,String ext){
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        //文件名称
        String uploadFileName =snowflakeIdWorker.nextId() + "."+ext;
        File targetFile = new File(path, uploadFileName);
        BASE64Decoder decoder = new BASE64Decoder();
        try(OutputStream out = new FileOutputStream(targetFile)){
            byte[] b = decoder.decodeBuffer(BASE64str);
            for (int i = 0; i <b.length ; i++) {
                if (b[i] <0) {
                    b[i]+=256;
                }
            }
            out.write(b);
            out.flush();
            out.close();
            return  path+"/"+uploadFileName+"."+ext;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


}

package com.fuhuitong.nitri.common.utils.imagesUtils;

/**
 * @Author Wang
 * @Date 2019/4/24 0024 19:16
 **/

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;


public class UploadFile {
    String ACCESS_KEY = "ApJJ0fYosOYkewefheLYIvQsFxF2P7Vsj8H0kial";
    String SECRET_KEY = "FLkeQBSCgOAogpD0-yntum4AUBvbaaSIVnZSo64w";
    String bucketname = "blog";
    String uuid = UUID.randomUUID().toString().replaceAll("-","");
    String key = System.currentTimeMillis()+uuid;
    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    Zone z = Zone.zone0();
    Configuration c = new Configuration(z);
    UploadManager uploadManager = new UploadManager(c);
    public String getUpToken() {
        return auth.uploadToken(bucketname);
    }

    public String upload(MultipartFile file) throws IOException {
        try {
            String filename=file.getOriginalFilename();
            Response res = uploadManager.put(file.getBytes(), key+filename.substring(filename.lastIndexOf(".")),getUpToken());
            return res.bodyString();
        } catch (QiniuException e) {
            Response r = e.response;
            System.out.println(r.toString());
            try {
                System.out.println(r.bodyString());
            } catch (QiniuException e1) {
            }
        }
        return "";
    }

}

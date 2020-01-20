package com.fuhuitong.nitri.sys.controller;

import com.alibaba.fastjson.JSON;
import com.fuhuitong.nitri.common.entity.ResultJson;
import com.fuhuitong.nitri.common.utils.imagesUtils.FileServerUtil;
import com.fuhuitong.nitri.common.utils.imagesUtils.UploadFile;
import com.fuhuitong.nitri.sys.enums.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author Wang
 * @Date 2019/4/24 0024 19:13
 **/
@Api(description = "图片上传")
@RestController
public class QiniuController {
    @Value("${oss.qiniu.url}")
    private String qiniuurl;
    @ApiOperation(value = "上传文件",notes = "")
    @PostMapping(value = "/qiNiuContent")
    public ResultJson upload(@RequestParam MultipartFile file){
       if(!FileServerUtil.checkFileSizeIsLimit(file.getSize(),100,"M")){
           return ResultJson.failure(ResultCode.FILE_SIZE);
       }
        try {
           String obj= new UploadFile().upload(file);
           if(StringUtils.isNotEmpty(obj)){
               return ResultJson.ok(qiniuurl+"/"+JSON.parseObject(obj).get("key"));
           }
        } catch (IOException e) {
           return ResultJson.failure(ResultCode.SERVER_ERROR);
        }
        return ResultJson.ok();
    }
}

package com.fuhuitong.nitri.sys.controller;

import com.fuhuitong.nitri.common.entity.ResultJson;
import com.fuhuitong.nitri.sys.entity.*;
import com.fuhuitong.nitri.sys.enums.ResultCode;
import com.fuhuitong.nitri.sys.service.DictServiceImpl;
import com.fuhuitong.nitri.common.utils.DictUtils;
import com.fuhuitong.nitri.common.utils.Log;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Wang
 * @Date 2019/4/28 0028 15:09
 **/

@Api(description = "字典相关控制器")
@RestController
@RequestMapping("dict")
public class DictController {

    @Autowired
    private DictServiceImpl dictService;


    @ApiOperation(value = "获取数据字典", notes = "获取数据字典")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "type", paramType = "query", dataType = "String", required = true),
    })
    @PostMapping("getDict")
    public ResultJson getDict(String type) {
        String str[] = type.split(",");
        Map<String, Object> map = new HashMap();
        for (String str1 : str) {
            List<Dict> list = DictUtils.getDictList(str1);
            map.put(str1, list);
        }
        return ResultJson.ok(map);
    }


    @ApiOperation(value = "根据values获取多个label", notes = "根据values获取多个label")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "type", paramType = "query", dataType = "String", required = true),
    })
    @PostMapping("getDictLabels")
    public ResultJson getDictLabels(String type, String values) {
        return ResultJson.ok(DictUtils.getDictLabels(values, type, ""));
    }


    @ApiOperation(value = "根据values获取label", notes = "根据values获取label")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "type", paramType = "query", dataType = "String", required = true),
    })
    @PostMapping("getDictLabel")
    public ResultJson getDictLabel(String type, String value) {
        return ResultJson.ok(DictUtils.getDictLabel(value, type, ""));
    }


    @Log("查询字典列表")
    @ApiOperation("查询数据字典列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Integer", required = true),
            @ApiImplicitParam(name = "size", value = "分页大小", paramType = "query", dataType = "Integer", required = true)
    })
    @PostMapping("findDictPage")
    public ResultJson findDictPage(Integer page, Integer size, Dict dict) {
        Page ipage = new Page(page, size);
        return ResultJson.ok(dictService.getDictAllPage(ipage, dict));
    }


    @Log("添加字典")
    @ApiOperation(value = "添加数据字典", notes = "")
    @PostMapping("addDict")
    public ResultJson addDict(Dict dict) {
        if (dictService.addDict(dict) > 0) {
            return ResultJson.ok();
        }
        return ResultJson.failure(ResultCode.BAD_REQUEST);
    }


    @Log("删除字典")
    @ApiOperation(value = "删除字典", notes = "")
    @PostMapping("delDict")
    public ResultJson delDict(String id) {
        if (dictService.delDict(id) > 0) {
            return ResultJson.ok();
        }
        return ResultJson.failure(ResultCode.BAD_REQUEST);
    }

    @Log("修改字典")
    @ApiOperation(value = "修改字典", notes = "")
    @PostMapping("updateDict")
    public ResultJson updateDict(Dict dict) {
        if (dictService.updateDict(dict) > 0) {
            return ResultJson.ok();
        }
        return ResultJson.failure(ResultCode.BAD_REQUEST);
    }


    @Log("根据label获取Value")
    @ApiOperation(value = "根据label获取Value", notes = "")
    @PostMapping("findDictLabelByValue")
    public ResultJson findDictLabelByValue(String label, String type) {
        return ResultJson.ok(DictUtils.getDictValue(label, type, ""));
    }

    @ApiOperation(value = "数据字典类型下拉框", notes = "")
    @PostMapping("dicType")
    public ResultJson dicType(){
        return ResultJson.ok(dictService.dicType());
    }
}

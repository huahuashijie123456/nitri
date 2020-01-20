package com.fuhuitong.nitri.sys.controller;

import com.fuhuitong.nitri.common.entity.ResultJson;
import com.fuhuitong.nitri.sys.entity.SysArea;
import com.fuhuitong.nitri.sys.service.SysAreaServiceImpl;
import com.fuhuitong.nitri.common.utils.AreaUtil;
import com.fuhuitong.nitri.common.utils.PingYinUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author Wang
 * @Date 2019/5/10 0010 16:13
 **/
@Api(description = "行政区划")
@RestController
@RequestMapping("SysArea")
public class SysAreaController {
    @Autowired
    private SysAreaServiceImpl sysAreaService;

    @ApiOperation(value = "行政区划tree树")
    @PostMapping("tree")
    public ResultJson findSysAreaList() {
        List<SysArea> list = sysAreaService.findSysAreaList();
        return ResultJson.ok(AreaUtil.getAreaTree(list));
    }


    @ApiOperation(value = "行政区划加拼音 ABC ")
    @PostMapping("addABC")
    public ResultJson addABCSysArea() {
        List<SysArea> list = sysAreaService.findSysAreaList();
        for (SysArea sysArea : list) {
            sysArea.setPinyin(PingYinUtil.getadc(sysArea.getAreaName()));
            sysAreaService.updateSysAreaList(sysArea);
        }
        return ResultJson.ok(list);
    }

    @ApiOperation(value = "行政区划 清楚缓存")
    @PostMapping("removeCache")
    @CacheEvict(allEntries = true, cacheNames = "findSysArea")
    public ResultJson RemoveCache() {
        return ResultJson.ok();
    }


}

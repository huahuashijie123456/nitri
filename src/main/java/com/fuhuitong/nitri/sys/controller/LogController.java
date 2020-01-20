package com.fuhuitong.nitri.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fuhuitong.nitri.common.entity.ResultJson;
import com.fuhuitong.nitri.sys.entity.SysLog;
import com.fuhuitong.nitri.sys.mapper.SysLogMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "系统日志")
@RestController
@RequestMapping("log")
public class LogController {

    @Autowired
    private SysLogMapper sysLogMapper;

    @ApiOperation(value = "日志列表",notes = "")
    @PostMapping("list")
    public ResultJson list(int page,int size,SysLog sysLog){
        return ResultJson.ok(sysLogMapper.selectPage(new Page<>(page,size),new LambdaQueryWrapper<SysLog>().orderByDesc(SysLog::getCreateTime)));
    }

}

package com.fuhuitong.nitri.xgd.controller;



import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fuhuitong.nitri.common.entity.ResultJson;
import com.fuhuitong.nitri.sys.enums.ResultCode;
import com.fuhuitong.nitri.xgd.entity.NitriCompany;
import com.fuhuitong.nitri.xgd.mapper.NitriCompanyMapper;
import com.fuhuitong.nitri.xgd.service.impl.NitriCompanyServiceImpl;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 企业 前端控制器
 * </p>
 *
 * @author jason
 * @since 2019-10-09
 */
@Slf4j
@Api(description = "西工大企业相关")
@RestController
@RequestMapping("/company")
public class NitriCompanyController {

    @Autowired
    private NitriCompanyServiceImpl nitriCompanyService;

    /**
     * 分页条件
     * @param page 当前页
     * @param size 条数
     * @param nitriCompany 检索条件
     * @return
     */
    @ApiOperation(value = "孵化企业列表")
    @PostMapping(value = "/queryNitriCompanyPageByCompanyType")
    public ResultJson queryNitriCompanyPageByCompanyType(int page,int size,NitriCompany nitriCompany) {
        Page<NitriCompany> pageInfo = new Page<>(page, size);
        Page<NitriCompany> pages = nitriCompanyService.queryNitriCompanyPage(pageInfo,nitriCompany);
        log.info("企业列表-----------",pages);
        return ResultJson.ok(pages);
    }

    /**
     * 院属企业列表
     * @param page 页码
     * @param size 每页显示条数
     * @param nitriCompany 检索条件 企业名称 创建时间
     * @return
     */
    @ApiOperation(value = "院属企业列表")
    @PostMapping(value = "/queryNitriCompanyPage")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Integer", required = true),
            @ApiImplicitParam(name = "size", value = "大小", paramType = "query", dataType = "Integer", required = true)
    })
    public ResultJson queryNitriCompanyPage(int page,int size,NitriCompany nitriCompany) {
        Page<NitriCompany> pageInfo = new Page<>(page, size);
        Page<NitriCompany> pages = nitriCompanyService.queryNitriCompanyPage(pageInfo,nitriCompany);
        log.info("企业列表-----------",pages);
        return ResultJson.ok(pages);
    }


    /**
     * 企业详情
     * @param id
     * @return
     */
    @ApiOperation(value = "企业详情")
    @PostMapping(value = "/queryNitriCompany")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "企业id", paramType = "query", dataType = "String", required = true)
    })
    public ResultJson<NitriCompany> queryNitriCompany(String id){
        NitriCompany nitriCompany =nitriCompanyService.queryNitriCompany(id);
        log.info("企业详情--------------",nitriCompany);
        return ResultJson.ok(nitriCompany);
    }

    /**
     * 添加企业
     * @param nitriCompany
     * @return
     */
    @ApiOperation(value = "添加企业")
    @PostMapping(value = "/addNitriCompany")
    public ResultJson<NitriCompany> addNitriCompany(NitriCompany nitriCompany){
        if(nitriCompanyService.addNitriCompany(nitriCompany)>0){
            return  ResultJson.ok("新增成功");
        }
        return ResultJson.failure(ResultCode.BAD_REQUEST.getCode(),"新增失败，请联系管理员");
    }

    /**
     * 修改企业
     * @param nitriCompany
     * @return
     */
    @ApiOperation(value = "修改企业")
    @PostMapping(value = "/updateNitriCompany")
    public ResultJson updateNitriCompany(NitriCompany nitriCompany){
        if(nitriCompanyService.updateNitriCompany(nitriCompany)>0){
            return  ResultJson.ok("修改成功");
        }
        return ResultJson.failure(ResultCode.BAD_REQUEST.getCode(),"修改失败，请联系管理员");

    }

    /**
     * 删除企业
     * @param nitriCompany
     * @return
     */
    @ApiOperation(value = "删除企业")
    @PostMapping(value = "/delNitriCompany")
    public ResultJson  delNitriCompany(NitriCompany nitriCompany){
        if(nitriCompanyService.delNitriCompany(nitriCompany)>0){
            ResultJson.ok("删除成功");
        }
        return ResultJson.failure(ResultCode.BAD_REQUEST.getCode(),"删除失败，请联系管理员");
    }

    /**
     * 修改院属企业下为孵化企业
     * @param nitriCompany
     * @return
     */
    @ApiOperation(value = "修改院属")
    @PostMapping(value = "/updateNitriCompanyByCompanyType")
    public ResultJson updateNitriCompanyByCompanyType(NitriCompany nitriCompany){
        if(nitriCompanyService.updateNitriCompanyByCompanyType(nitriCompany)>0){
            return ResultJson.ok("更新成功");
        }
        return ResultJson.failure(ResultCode.BAD_REQUEST.getCode(),"修改失败，请联系管理员");
    }

    /**
     * 修改企业上下架
     * @param nitriCompany 上下架
     * @return
     */
    @ApiOperation(value = "修改企业上下架")
    @PostMapping(value = "/updateNitriCompanyByStatus")
    public ResultJson updateNitriCompanyByStatus(NitriCompany nitriCompany){
        if(nitriCompany.getStatus()==0){

            if(nitriCompanyService.updateNitriCompanyByStatusUp(nitriCompany)>0){
                  return ResultJson.ok("下架成功");
            }
        }else{
            if(nitriCompanyService.updateNitriCompanyByStatusDown(nitriCompany)>0){
                return ResultJson.ok("上架成功");
            }
        }
        return ResultJson.failure(ResultCode.BAD_REQUEST.getCode(),"修改失败，请联系管理员");
    }

    /**
     * 企业入驻申请
     * @param nitriCompany 企业信息
     * @return
     */
//    @ApiOperation(value = "企业入驻申请")
//    @PostMapping(value = "/addNitriCompany")
//    public ResultJson addNitriCompany1(NitriCompany nitriCompany){
//        return null;
//    }
}


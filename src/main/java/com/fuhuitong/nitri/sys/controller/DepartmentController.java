package com.fuhuitong.nitri.sys.controller;

import com.fuhuitong.nitri.common.entity.ResultJson;
import com.fuhuitong.nitri.sys.entity.Department;
import com.fuhuitong.nitri.sys.entity.UserDetail;
import com.fuhuitong.nitri.sys.entity.UserRole;
import com.fuhuitong.nitri.sys.enums.ResultCode;
import com.fuhuitong.nitri.sys.mapper.AuthMapper;
import com.fuhuitong.nitri.sys.mapper.UserRoleMapper;
import com.fuhuitong.nitri.sys.service.DepartmentServiceImpl;
import com.fuhuitong.nitri.sys.service.UserDetailServiceImpl;
import com.fuhuitong.nitri.sys.service.UserRoleServiceImpl;
import com.fuhuitong.nitri.common.utils.DepartmentUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 部门相关
 *
 * @Author Wang
 * @Date 2019/5/21 0021 11:52
 **/
@RestController
@Api(description = "部门 相关")
@RequestMapping("sys/department")
public class DepartmentController {
    @Autowired
    private DepartmentServiceImpl departmentService;
    @Autowired
    private UserDetailServiceImpl userDetailService;
    @Autowired
    private UserRoleServiceImpl userRoleService;
    @Autowired
    private AuthMapper authMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @ApiOperation(value = "添加部门")
    @PostMapping("addDepartment")
    public ResultJson addDepartment(Department department) {
     //   department.setMerchantsId(ContextHolder.getCurrentUser().getMaritalId());
        if (departmentService.addDepartment(department) > 0) {
            return ResultJson.ok();
        }
        return ResultJson.failure(ResultCode.BAD_REQUEST);
    }

    @ApiOperation(value = "删除部门")
    @PostMapping("delDepartment")
    public ResultJson delDepartment(String id) {
        if (departmentService.delDepartmentById(id) > 0) {
            return ResultJson.ok();
        }
        return ResultJson.failure(ResultCode.BAD_REQUEST);
    }


    @ApiOperation(value = "部门树")
    @PostMapping("departmentTree")
    public ResultJson departmentTree() {
        List<Department> list = departmentService.getDepartmentList();
        return ResultJson.ok(DepartmentUtil.getDepartment(list));
    }

    @ApiOperation(value = "部门人员树")
    @PostMapping("departmentUserTree")
    public ResultJson departmentUserTree() {
        List<Department> list = departmentService.findDepartmentTree();
        return ResultJson.ok(DepartmentUtil.getDepartment(list));
    }


    @ApiOperation(value = "员工列表")
    @PostMapping("employeeListPage")
    public ResultJson findEmployeeListPage(int page, int size, UserDetail userDetail) {
        //userDetail.setMaritalId(ContextHolder.getCurrentUser().getMaritalId());
        return ResultJson.ok(userDetailService.findUserDetailListPage(new Page(page, size), userDetail));
    }


    @PostMapping("delUser")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResultJson delUser(UserDetail userDetail) {
        if (StringUtil.isNullOrEmpty(userDetail.getId()+"")) {
            return ResultJson.failure(ResultCode.BAD_REQUEST);
        }
        userRoleMapper.delete(new QueryWrapper<UserRole>().eq("user_id", userDetail.getId()));
        if (authMapper.deleteById(userDetail) > 0) {
            return ResultJson.ok();
        } else {
            return ResultJson.failure(ResultCode.BAD_REQUEST);
        }

    }



}

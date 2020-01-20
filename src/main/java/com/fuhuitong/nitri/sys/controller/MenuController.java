package com.fuhuitong.nitri.sys.controller;

import com.fuhuitong.nitri.common.entity.ResultJson;
import com.fuhuitong.nitri.sys.entity.Menu;
import com.fuhuitong.nitri.sys.entity.Role;
import com.fuhuitong.nitri.sys.entity.RoleMenu;
import com.fuhuitong.nitri.sys.enums.ResultCode;
import com.fuhuitong.nitri.sys.service.DepartmentServiceImpl;
import com.fuhuitong.nitri.sys.service.MenuServiceImpl;
import com.fuhuitong.nitri.sys.service.UserRoleServiceImpl;
import com.fuhuitong.nitri.common.utils.ContextHolder;
import com.fuhuitong.nitri.common.utils.Log;
import com.fuhuitong.nitri.common.utils.MenuUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Wang
 * @Date 2019/4/19 0019 15:31
 **/
@Api(description = "菜单系统相关")
@RestController
@RequestMapping("menu")
public class MenuController {
    @Autowired
    private MenuServiceImpl menuService;
    @Autowired
    private DepartmentServiceImpl departmentService;
    @Autowired
    private UserRoleServiceImpl userRoleService;


    @Log("添加修改菜单")
    @ApiOperation(value = "添加修改菜单", notes = "添加修改菜单")
    @PostMapping("addMenu")
    public ResultJson addMenu(Menu menu){
        if(menuService.addMenu(menu)>0){
            return ResultJson.ok();
        }
        else{
            return ResultJson.failure(ResultCode.BAD_REQUEST);
        }
    }

    @Log("删除菜单")
    @ApiOperation(value = "删除菜单", notes = "删除菜单")
    @PostMapping("delMenu")
    public ResultJson delMenu(String id){
        if(menuService.delMenu(id)>0){
            return ResultJson.ok();
        }
        else{
            return ResultJson.failure(ResultCode.BAD_REQUEST);
        }
    }




    @Log("当前人菜单")
    @ApiOperation(value = "当前人菜单列表", notes = "当前登录人能看到的菜单以及权限")
    @PostMapping("build")
    public ResultJson getMenuList(HttpServletRequest request) {
        String  roleId = ContextHolder.getCurrentUser().getRole().getId();
        List<Menu> treeData = menuService.getCurrentMenuList(roleId);
        return ResultJson.ok(MenuUtil.getMenu(treeData));
    }


    @Log("角色菜单和权限列表")
    @ApiOperation(value = "当前或指定角色人菜单和权限列表", notes = "角色id不传时，默认是当前操作人菜单权限，传角色id的话就是该角色的")
    @PostMapping("getMenuPermission")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", paramType = "query", dataType = "String", required = true),
    })
    public ResultJson getMenuPermissionList(String roleId) {
        Map map = new HashMap();
        List<Menu> treeData = menuService.getCurrentMenuAndPermission(roleId);
        map.put("current", treeData);
        roleId = ContextHolder.getCurrentUser().getRole().getId();
        List<Menu> treeData2 = menuService.getCurrentMenuAndPermission(roleId);
        map.put("all", MenuUtil.getMenu(treeData2));
        return ResultJson.ok(map);
    }


    @Log("菜单树")
    @ApiOperation(value = "菜单设置-菜单树", notes = "系统设置菜单设置里面的菜单树")
    @PostMapping("tree")
    public ResultJson getMenuTree() {
        List<Menu> treeData = menuService.getMenuList();
        return ResultJson.ok(MenuUtil.getMenu(treeData));
    }

    @Log("添加/修改角色")
    @ApiOperation(value = "添加修改角色", notes = "只输入角色名，描述即可，添加完成，需要给新的角色分配菜单权限,有id就是修改")
    @PostMapping("addRole")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色id", paramType = "query", dataType = "String", required =false),
            @ApiImplicitParam(name = "name", value = "角色标识", paramType = "query", dataType = "String", required = false),
            @ApiImplicitParam(name = "roleZh", value = "角色名称", paramType = "query", dataType = "String", required = false),
            @ApiImplicitParam(name = "roleDescribe", value = "角色描述", paramType = "query", dataType = "String", required = false)
    })
    public ResultJson addRole(String id,String name, String roleZh, String roleDescribe) {
        Role role = new Role();
        role.setId(id);
        role.setName(name);
        role.setNameZh(roleZh);
        role.setRoleDescribe(roleDescribe);
   //     role.setMerchantsId(ContextHolder.getCurrentUser().getMaritalId());
        if (userRoleService.addRole(role) > 0) {
            return ResultJson.ok();
        }
        return ResultJson.failure(ResultCode.OPERATE_ERROR);
    }


    @Log("查询所有角色有分页")
    @ApiOperation(value = "查询商户所有角色", notes = "商户merchantsId默认不传，默认当前商户")
    @PostMapping("getRoleList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "size", value = "大小", paramType = "query", dataType = "String", required = true)
    })
    public ResultJson getRoleList(HttpServletRequest request, Integer page, Integer size) {
        IPage<Role> pages = userRoleService.getRoleList(new Page<>(page,size));
        return ResultJson.ok(pages);
    }


    @Log("角色根据分配菜单")
    @ApiOperation(value = "角色分配菜单", notes = "")
    @PostMapping("addRoleMenus")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuIds", value = "一个或多个菜单id(菜单id逗号隔开传如menuIds=123,456,789)", paramType = "query", dataType = "String", required = true),
    })
    public ResultJson addRoleMenus(String roleId, @RequestParam(value = "menuIds") List<String> menuIds) {
        List<RoleMenu> roleMenus = new ArrayList<>();
        for (String menuid : menuIds) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setMenuId(menuid);
            roleMenu.setRoleId(roleId);
            roleMenus.add(roleMenu);
        }
        if (menuService.addRoleMenu(roleMenus) > 0) {
            return ResultJson.ok();
        }
        return ResultJson.failure(ResultCode.BAD_REQUEST);
    }


    @Log("删除角色")
    @ApiOperation(value = "删除角色", notes = "删除角色表和角色菜单关联表")
    @PostMapping("delRole")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", paramType = "query", dataType = "String", required = true),
    })
    public ResultJson delRole(String roleId) {
        if (menuService.delRole(roleId) > 0) {
            return ResultJson.ok();
        }
        return ResultJson.failure(ResultCode.OPERATE_ERROR);
    }


}

package com.fuhuitong.nitri.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuhuitong.nitri.common.utils.ContextHolder;
import com.fuhuitong.nitri.sys.entity.Menu;
import com.fuhuitong.nitri.sys.entity.RoleMenu;
import com.fuhuitong.nitri.sys.entity.UserDetail;
import com.fuhuitong.nitri.sys.mapper.MenuMapper;
import com.fuhuitong.nitri.sys.mapper.RoleMapper;
import com.fuhuitong.nitri.sys.mapper.RoleMenuMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author Wang
 * @Date 2019/4/19 0019 15:14
 **/
@Service
public class MenuServiceImpl {
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Autowired
    private RoleMapper roleMapper;


    /**
     *添加修改菜单
     */
    @CacheEvict(allEntries = true, cacheNames = {"getCurrentMenu", "getCurrentPermission", "getCurrentMenuAndPermission"})
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int addMenu(Menu menu){
        if(StringUtil.isNullOrEmpty(menu.getId())){
            if(StringUtil.isNullOrEmpty(menu.getParentId())){
                menu.setParentId("0");
            }
            UserDetail userDetail=ContextHolder.getCurrentUser();
            if("ROLE_ADMIN".equals(userDetail.getRole().getName())){
                 if(menuMapper.insert(menu)>0){
                     RoleMenu roleMenu=new RoleMenu();
                     roleMenu.setMenuId(menu.getId());
                     roleMenu.setRoleId(userDetail.getRole().getId());
                     return roleMenuMapper.insert(roleMenu);
                 }
                return 0;
            }
            return menuMapper.insert(menu);

        }
        return menuMapper.updateById(menu);
    }



    @CacheEvict(allEntries = true, cacheNames = {"getCurrentMenu", "getCurrentPermission", "getCurrentMenuAndPermission"})
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int delMenu(String id){
        roleMenuMapper.delete(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getMenuId,id));
        return menuMapper.delete(new LambdaQueryWrapper<Menu>().eq(Menu::getId,id).or().eq(Menu::getParentId,id));
    }





    /**
     * 获取所有菜单
     *
     * @return
     */
    public List<Menu> getMenuList() {
        return menuMapper.selectList(new LambdaQueryWrapper<Menu>().orderByDesc(Menu::getParentId).orderByAsc(Menu::getSort));
    }


    /**
     * 根据角色获取菜单
     *
     * @param roleId
     * @return
     */
    public List<Menu> getCurrentMenuList(String roleId) {
        return menuMapper.getCurrentMenu(roleId);
    }


    /**
     * @param roleId
     * @return
     */
    public List<String> getCurrentPermission(String roleId) {
        return menuMapper.getCurrentPermission(roleId);
    }

    /**
     * 根据角色获取菜单和权限
     *
     * @param roleId
     * @return
     */
    public List<Menu> getCurrentMenuAndPermission(String roleId) {
        return menuMapper.getCurrentMenuAndPermission(roleId);
    }


    /**
     * 角色分配菜单
     *
     * @param roleMenus
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int addRoleMenu(List<RoleMenu> roleMenus) {
        roleMenuMapper.delete(new QueryWrapper<RoleMenu>().eq("role_id", roleMenus.get(0).getRoleId()));
        return menuMapper.addRoleMenus(roleMenus);
    }


    /**删除角色
     * @param roleId
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int delRole(String roleId) {
        roleMenuMapper.delete(new QueryWrapper<RoleMenu>().eq("role_id", roleId));
        return roleMapper.deleteById(roleId);
    }


}

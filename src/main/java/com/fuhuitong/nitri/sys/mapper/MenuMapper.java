package com.fuhuitong.nitri.sys.mapper;

import com.fuhuitong.nitri.sys.entity.Menu;
import com.fuhuitong.nitri.sys.entity.RoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * @Author Wang
 * @Date 2019/4/19 0019 15:13
 **/
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 角色分配菜单
     *
     * @param roleMenu
     * @return
     */
    @CacheEvict(allEntries = true, cacheNames = {"getCurrentMenu", "getCurrentPermission", "getCurrentMenuAndPermission"})
    int addRoleMenus(List<RoleMenu> roleMenu);


    /**
     * 根据角色获取菜单
     *
     * @param roleId
     * @return
     */
    @Cacheable(key = "#p0", cacheNames = "getCurrentMenu")
    List<Menu> getCurrentMenu(@Param("roleId") String roleId);

    /**
     * 根据角色获取权限
     *
     * @param roleId
     * @return
     */

    @Cacheable(key = "#p0", cacheNames = "getCurrentPermission")
    List<String> getCurrentPermission(@Param("roleId")String roleId);


    /**
     * 根据角色获取菜单和权限
     *
     * @param roleId
     * @return
     */

    @Cacheable(key = "#p0", cacheNames = "getCurrentMenuAndPermission")
    List<Menu> getCurrentMenuAndPermission(@Param("roleId") String roleId);


}

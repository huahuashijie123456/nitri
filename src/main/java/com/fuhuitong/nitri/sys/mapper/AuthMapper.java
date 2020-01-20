package com.fuhuitong.nitri.sys.mapper;

import com.fuhuitong.nitri.sys.entity.Role;
import com.fuhuitong.nitri.sys.entity.UserDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Wang
 * @Date 2019/4/19 0019 10:38
 **/
@Repository
public interface AuthMapper extends BaseMapper<UserDetail> {
    /**
     * 根据用户名查找用户
     *
     * @param username
     * @return
     */
    UserDetail findByUsername(@Param("username") String username);

    /**
     * 创建新用户
     *
     * @param userDetail
     */
    int insertUser(UserDetail userDetail);

    /**
     * 创建用户角色
     *
     * @param userId
     * @param roleId
     * @return
     */
    int insertRoles(@Param("userId") String userId, @Param("roleId") String roleId);

    /**
     * 根据角色id查找角色
     *
     * @param roleId
     * @return
     */
    Role findRoleById(@Param("roleId")String roleId);

    /**
     * 根据用户id查找该用户角色
     *
     * @param userId
     * @return
     */
    Role findRoleByUserId(@Param("userId")String userId);


    /**
     * 查询员工
     *
     * @param page
     * @param userDetail
     * @return
     */
    Page<UserDetail> findUserListPage(Page page, UserDetail userDetail);


    List<UserDetail> findUserListPage(UserDetail userDetail);

    /**
     *
     */
    UserDetail queryUserByUserId(@Param("userId")String userId);
}


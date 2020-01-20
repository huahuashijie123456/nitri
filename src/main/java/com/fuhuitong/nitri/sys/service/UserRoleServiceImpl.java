package com.fuhuitong.nitri.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuhuitong.nitri.sys.entity.Role;
import com.fuhuitong.nitri.sys.entity.UserDetail;
import com.fuhuitong.nitri.sys.entity.UserRole;
import com.fuhuitong.nitri.sys.mapper.RoleMapper;
import com.fuhuitong.nitri.sys.mapper.UserDetailMapper;
import com.fuhuitong.nitri.sys.mapper.UserRoleMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author Wang
 * @Date 2019/4/22 0022 9:49
 **/
@Service
public class UserRoleServiceImpl {
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserDetailMapper userDetailMapper;


    /**
     * 用户添加角色
     *
     * @param userRole
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int addUserRole(UserRole userRole) {
        return userRoleMapper.insert(userRole);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateUserRole(UserRole userRole) {
        return userRoleMapper.update(userRole, new QueryWrapper<UserRole>().eq("user_id", userRole.getUserId()));
    }


    /**
     * 添加员工
     * @param id
     * @param username
     * @param password
     * @param name
     * @param roleId
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int addStaff(String id,String username,String password,String name,String roleId){
        UserDetail userDetail=new UserDetail();
        userDetail.setName(name);
        userDetail.setUsername(username);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(StringUtil.isNullOrEmpty(password)){
            password="123456";
        }
        userDetail.setPassword(encoder.encode(password));
        userDetail.setUserType(1); //1后台用户 0app用户

        if(StringUtil.isNullOrEmpty(id)){  //
            userDetailMapper.insert(userDetail);
            UserRole userRole=new UserRole(userDetail.getId(),roleId);
            return addUserRole(userRole);
        }
        else {
            UserRole userRole=new UserRole(id,roleId);
            updateUserRole(userRole);
            userDetail.setId(id);
            userDetail.setUsername(null);
            return userDetailMapper.updateById(userDetail);
        }
    }

    /**
     * 添加渠道用户
     * @param username 登录名
     * @param password 密码
     * @param name 姓名
     * @return 成功与否
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int addChannel(String username,String password,String name){
        UserDetail userDetail=new UserDetail();
        userDetail.setUsername(username);
        userDetail.setName(name);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userDetail.setPassword(encoder.encode(password));
        userDetail.setUserType(2); //1后台用户 0app用户 2 渠道用户

        int i=userDetailMapper.insert(userDetail);
        return i;
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int delStaff(String id){
        userRoleMapper.delete(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId,id));
        return  userDetailMapper.deleteById(id);
    }





    /**
     * 添加修改系统角色
     *
     * @param role
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int addRole(Role role) {
        if ((role.getId()!=null)) {
            return roleMapper.update(role, new QueryWrapper<Role>().eq("id", role.getId()));
        }
        return roleMapper.insert(role);
    }


    /**
     * 查询当前商户的一个角色列表
     *
     * @return
     */
    public IPage<Role> getRoleList(Page<Role> page) {
        return roleMapper.selectPage(page, new QueryWrapper<Role>());
    }


    /**
     * 查询所有角色
     *
     * @return
     */
    public List<Role> getRoleListAll() {
        return roleMapper.selectList(new QueryWrapper<Role>());
    }


}

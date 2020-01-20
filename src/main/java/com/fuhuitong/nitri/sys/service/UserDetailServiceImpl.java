package com.fuhuitong.nitri.sys.service;

import com.fuhuitong.nitri.sys.entity.UserDetail;
import com.fuhuitong.nitri.sys.mapper.AuthMapper;
import com.fuhuitong.nitri.sys.mapper.UserDetailMapper;
import com.fuhuitong.nitri.sys.mapper.UserRoleMapper;
import com.fuhuitong.nitri.common.utils.RedisUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author Wang
 * @Date 2019/4/22 0022 17:33
 **/
@Service
public class UserDetailServiceImpl {
    @Autowired
    private UserDetailMapper userMapper;


    @Autowired
    private AuthMapper authMapper;
    /**
     * 用户角色Mapper
     */
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 添加用户主表
     *
     * @param userDetail
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int addUser(UserDetail userDetail) {
        return userMapper.insert(userDetail);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateUser(UserDetail userDetail) {
        return userMapper.updateById(userDetail);
    }


    /**
     * 查询商户底下的员工
     *
     * @param userDetail
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public Page<UserDetail> findUserDetailListPage(Page page, UserDetail userDetail) {
        return authMapper.findUserListPage(page, userDetail);
    }


    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public List<UserDetail> findUserDetailListPage(UserDetail userDetail) {
        return authMapper.findUserListPage(userDetail);
    }


    /**
     * 删除用户
     *
     * @param id 用户id
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int deleteUser(String id) {
        //删除用户角色关联表数据
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", id);
        userRoleMapper.delete(queryWrapper);
        return userMapper.deleteById(id);
    }


    /**
     * 验证密码
     *
     * @return
     */
    public boolean checkPassword(String password, String enpassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(password, enpassword);
    }


    /**
     * 查询当前用户的模板、渠道、规则
     * @param userId 用户id
     * @return
     */
    public UserDetail queryUserByUserId(String userId) {
        UserDetail userDetail=authMapper.queryUserByUserId(userId);
        return userDetail;
    }
}

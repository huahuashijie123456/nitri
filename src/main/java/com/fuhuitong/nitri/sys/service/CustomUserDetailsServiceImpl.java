package com.fuhuitong.nitri.sys.service;

import com.fuhuitong.nitri.sys.entity.Role;
import com.fuhuitong.nitri.sys.entity.UserDetail;
import com.fuhuitong.nitri.sys.mapper.AuthMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @Author Wang
 * @Date 2019/4/16 0019 21:36
 **/
@Component(value = "CustomUserDetailsService")
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    private final AuthMapper authMapper;

    public CustomUserDetailsServiceImpl(AuthMapper authMapper) {
        this.authMapper = authMapper;
    }

    @Override
    public UserDetail loadUserByUsername(String name) throws UsernameNotFoundException {
        UserDetail userDetail = authMapper.findByUsername(name);
        System.out.println(userDetail);
        if (userDetail == null) {
            throw new UsernameNotFoundException(String.format("No userDetail found with username '%s'.", name));
        }
        Role role = authMapper.findRoleByUserId(userDetail.getId());
        userDetail.setRole(role);
        return userDetail;
    }
}

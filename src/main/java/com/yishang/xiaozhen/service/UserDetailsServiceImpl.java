package com.yishang.xiaozhen.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yishang.xiaozhen.config.jwt.JwtUser;
import com.yishang.xiaozhen.entity.AdminUser;
import com.yishang.xiaozhen.mapper.AdminUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AdminUserMapper adminUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<AdminUser> query = new QueryWrapper<>();
        query.eq("username", username);
        AdminUser user = adminUserMapper.selectOne(query);
        if(user == null){
            throw new UsernameNotFoundException("用户不存在。");
        }
        //获取的权限集合
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_ONE");
        roles.add("ROLE_TWO");
        user.setRoles(roles);
        return new JwtUser(user);
    }
}

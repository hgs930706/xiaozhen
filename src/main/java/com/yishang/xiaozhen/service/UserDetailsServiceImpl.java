package com.yishang.xiaozhen.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yishang.xiaozhen.config.jwt.JwtUser;
import com.yishang.xiaozhen.entity.AdminUser;
import com.yishang.xiaozhen.entity.Authority;
import com.yishang.xiaozhen.entity.RoleAuthority;
import com.yishang.xiaozhen.entity.UserRole;
import com.yishang.xiaozhen.mapper.AdminUserMapper;
import com.yishang.xiaozhen.mapper.AuthorityMapper;
import com.yishang.xiaozhen.mapper.RoleAuthorityMapper;
import com.yishang.xiaozhen.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AdminUserMapper adminUserMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private AuthorityMapper authorityMapper;

    @Autowired
    private RoleAuthorityMapper roleAuthorityMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<AdminUser> query = new QueryWrapper<>();
        query.eq("username", username);
        query.eq("is_status", 1);//有效用户
        AdminUser user = adminUserMapper.selectOne(query);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在。");
        }
        //获取角色集合
        QueryWrapper<UserRole> queryRole = new QueryWrapper<>();
        queryRole.eq("user_id", user.getId());
        List<UserRole> userRoles = userRoleMapper.selectList(queryRole);
        List<String> roleIds = userRoles.stream().map(UserRole::getId).distinct().collect(Collectors.toList());
        //角色获取权限
        QueryWrapper<RoleAuthority> queryRoleAuthority = new QueryWrapper<>();
        queryRoleAuthority.in("role_id", roleIds);
        List<RoleAuthority> roleAuthorities = roleAuthorityMapper.selectList(queryRoleAuthority);
        List<String> authoritieIds = roleAuthorities.stream().map(RoleAuthority::getAuthorityId).distinct().collect(Collectors.toList());
        //获取权限name
        List<Authority> authorities = authorityMapper.selectBatchIds(authoritieIds);
        List<String> roles = authorities.stream().map(Authority::getName).collect(Collectors.toList());
        //保存用户权限
        user.setRoles(roles);
        return new JwtUser(user);
    }
}

package com.yishang.xiaozhen.service;

import com.yishang.xiaozhen.entity.UserRole;
import com.yishang.xiaozhen.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 用户角色关系表 服务实现类
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@Service
public class UserRoleServiceImpl{

    @Autowired
    private UserRoleMapper userRoleMapper;

    public Integer insert(String userId,String roleId) {
        UserRole object = new UserRole();
        object.setUserId(userId);
        object.setRoleId(roleId);
        return null;
    }

    public Integer insert(Object object) {
        return null;
    }


    public Map<String,Object> list(Object object) {
        return null;
    }


    public Object detail(String id) {
        return null;
    }


    public Integer update(Object object) {
        return null;
    }


    public Integer delete(String id) {
        return null;
    }
}

package com.yishang.xiaozhen.service;

import com.yishang.xiaozhen.entity.UserRole;
import com.yishang.xiaozhen.mapper.UserRoleMapper;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public ResultUtil insert(String userId, List<String> roleIds) {
        for (String roleId : roleIds) {
            UserRole object = new UserRole();
            object.setUserId(userId);
            object.setRoleId(roleId);
            userRoleMapper.insert(object);
        }
        return ResultUtil.success();
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

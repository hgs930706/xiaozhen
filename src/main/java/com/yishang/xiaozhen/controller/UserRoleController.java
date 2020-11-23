package com.yishang.xiaozhen.controller;


import com.yishang.xiaozhen.entity.UserRole;
import com.yishang.xiaozhen.mapper.UserRoleMapper;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户角色关系表 前端控制器
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@RestController
@RequestMapping("/userRole")
public class UserRoleController {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @PostMapping("/insert")
    public ResultUtil insert(UserRole object) {


        return null;
    }
}

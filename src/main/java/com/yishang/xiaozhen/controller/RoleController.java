package com.yishang.xiaozhen.controller;


import com.yishang.xiaozhen.entity.Role;
import com.yishang.xiaozhen.mapper.RoleMapper;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleMapper roleMapper;

    @PostMapping("/insert")
    public ResultUtil insert(Role object){
        Role r1= new Role();
        r1.setName("超级管理员");
        roleMapper.insert(r1);

        Role r2= new Role();
        r2.setName("场地管理员");
        roleMapper.insert(r2);

        Role r3= new Role();
        r3.setName("活动管理员");
        roleMapper.insert(r3);

        Role r4= new Role();
        r4.setName("接待管理员");
        roleMapper.insert(r4);


        return null;
    }
}

package com.yishang.xiaozhen.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yishang.xiaozhen.entity.AdminUser;
import com.yishang.xiaozhen.mapper.AdminUserMapper;
import com.yishang.xiaozhen.util.ImageUploadUtil;
import com.yishang.xiaozhen.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 管理用户信息 服务实现类
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@Service
@Slf4j
public class AdminUserServiceImpl {


    @Autowired
    private AdminUserMapper adminUserMapper;

    @Autowired
    private UserRoleServiceImpl userRoleServiceImpl;

    public ResultUtil insert(AdminUser object, MultipartFile file, HttpServletRequest request) {
        QueryWrapper<AdminUser> query = new QueryWrapper<>();
        query.eq("username", object.getUsername()).eq("is_status", 1);
        Integer count = adminUserMapper.selectCount(query);
        if (count > 0) {return ResultUtil.error("用户名已存在。");}
        object.setUserImage(ImageUploadUtil.uploadImage(file, request));
        adminUserMapper.insert(object);
        // todo 维护用户和角色关系
        // 多个角色，勾选几个新增几个
        userRoleServiceImpl.insert(object.getId(),"");

        return ResultUtil.success();
    }

    public Map<String,Object> list(Object object) {
        return null;
    }


    public Object detail(String id) {
        return null;
    }


    public ResultUtil update(AdminUser object, MultipartFile file) {
//        String s = ImageUploadUtil.uploadImage(file);
//        object.setUserImage(s);
        adminUserMapper.updateById(object);

        // todo 维护用户和角色关系，删除原有角色，在新增角色
        return ResultUtil.success();
    }

    public Integer delete(String id) {
        return null;
    }
}

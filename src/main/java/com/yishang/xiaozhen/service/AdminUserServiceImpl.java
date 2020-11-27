package com.yishang.xiaozhen.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yishang.xiaozhen.entity.AdminUser;
import com.yishang.xiaozhen.mapper.AdminUserMapper;
import com.yishang.xiaozhen.util.ImageUploadUtil;
import com.yishang.xiaozhen.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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

    @Transactional
    public ResultUtil insert(AdminUser object, MultipartFile file, HttpServletRequest request) {
        QueryWrapper<AdminUser> query = new QueryWrapper<>();
        query.eq("username", object.getUsername()).eq("is_status", 1);
        Integer count = adminUserMapper.selectCount(query);
        if (count > 0) {return ResultUtil.error("用户名已存在。");}

        object.setUserImage(ImageUploadUtil.uploadImage(file, request));
        adminUserMapper.insert(object);
        //用户角色
        userRoleServiceImpl.insert(object.getId(),object.getRoles());

        return ResultUtil.success();
    }

    public ResultUtil list(Integer page,Integer size) {
        IPage<AdminUser> ipage = new Page<>(page, size);

        QueryWrapper<AdminUser> query = new QueryWrapper<>();
//        query.eq("activity_name", activityName);
        query.eq("is_status", 1);
        ipage = adminUserMapper.selectPage(ipage, query);

        Map<String,Object> map = new HashMap();
        map.put("list",ipage.getRecords());
        map.put("total",ipage.getTotal());
        return ResultUtil.success(map);

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

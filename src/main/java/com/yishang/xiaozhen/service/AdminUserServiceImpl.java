package com.yishang.xiaozhen.service;

import com.yishang.xiaozhen.entity.AdminUser;
import com.yishang.xiaozhen.mapper.AdminUserMapper;
import com.yishang.xiaozhen.util.ImageUploadUtil;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
public class AdminUserServiceImpl {

    @Autowired
    private AdminUserMapper adminUserMapper;

    public ResultUtil insert(AdminUser object, MultipartFile file) {
        String s = ImageUploadUtil.uploadImage(file);
        object.setUserImage(s);
        adminUserMapper.insert(object);
        // todo 维护角色关系
        return ResultUtil.success();
    }

    public Map<String,Object> list(Object object) {
        return null;
    }


    public Object detail(String id) {
        return null;
    }


    public ResultUtil update(AdminUser object, MultipartFile file) {
        String s = ImageUploadUtil.uploadImage(file);
        object.setUserImage(s);
        adminUserMapper.updateById(object);
        // todo 维护角色关系，删除原有角色，在新增角色
        return ResultUtil.success();
    }

    public Integer delete(String id) {
        return null;
    }
}

package com.yishang.xiaozhen.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yishang.xiaozhen.entity.AdminUser;
import com.yishang.xiaozhen.entity.UserRole;
import com.yishang.xiaozhen.entity.dto.AdminUserDTO;
import com.yishang.xiaozhen.mapper.AdminUserMapper;
import com.yishang.xiaozhen.mapper.UserRoleMapper;
import com.yishang.xiaozhen.util.ImageUploadUtil;
import com.yishang.xiaozhen.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Transactional
    public ResultUtil insert(AdminUser object, MultipartFile file, HttpServletRequest request) {
        QueryWrapper<AdminUser> query = new QueryWrapper<>();
        query.eq("username", object.getUsername());
        query.eq("is_status", 1);
        Integer count = adminUserMapper.selectCount(query);
        if (count > 0) {return ResultUtil.error("用户名已存在。");}

        object.setUserImage(ImageUploadUtil.uploadImage(file, request));
        adminUserMapper.insert(object);
        //用户角色
        userRoleServiceImpl.insert(object.getId(),object.getRoles());

        return ResultUtil.success();
    }

    public ResultUtil list(Integer page,Integer size,String username,String role,Integer isStatus) {

        List<AdminUserDTO> adminUserDTOS = adminUserMapper.selectPage((page-1)*size, size, username, role, isStatus);

        Map<String,Object> map = new HashMap();
        map.put("list",adminUserDTOS);
        map.put("total",adminUserMapper.selectCount2(username, role, isStatus));
        return ResultUtil.success(map);

    }


    public Object detail(String id) {
        return null;
    }


    @Transactional
    public ResultUtil update(AdminUser object, MultipartFile file, HttpServletRequest request) {
        AdminUser adminUser = adminUserMapper.selectById(object.getId());
        //重置密码
        if("000000".equals(object.getPassword())){//保持旧密码
            object.setPassword(adminUser.getPassword());
        }else{
            object.setPassword(bCryptPasswordEncoder.encode(object.getPassword()));
        }
        String userImage = "";
        if(file == null){
            userImage = adminUser.getUserImage();
        }else{
            userImage = ImageUploadUtil.uploadImage(file,request);
        }
        object.setUserImage(userImage);
        adminUserMapper.updateById(object);
        // 删除原有角色，在新增角色
        QueryWrapper<UserRole> query = new QueryWrapper<>();
        query.eq("user_id",object.getId());
        userRoleMapper.delete(query);
        // 新增用户角色
        userRoleServiceImpl.insert(object.getId(),object.getRoles());
        return ResultUtil.success();
    }

    public Integer delete(String id) {
        return null;
    }
}

package com.yishang.xiaozhen.controller;


import com.yishang.xiaozhen.entity.AdminUser;
import com.yishang.xiaozhen.service.AdminUserServiceImpl;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 管理用户信息 前端控制器
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@RestController
@RequestMapping("/adminUser")
public class AdminUserController {

    @Autowired
    private AdminUserServiceImpl adminUserServiceImpl;


    @GetMapping("/list")
    public String list(){

        return null;
    }

    @GetMapping("/detail")
    public String detail(@RequestParam(name = "id") String id){

        return null;
    }


    @PostMapping("/insert")
    public ResultUtil insert(AdminUser object, MultipartFile file){
        ResultUtil result = adminUserServiceImpl.insert(object,file);
        return result;
    }

    @PostMapping("/update")
    public ResultUtil update(AdminUser object, MultipartFile file){
        if(StringUtils.isEmpty(object.getId())){
            return ResultUtil.error("id不能为空!");
        }
        ResultUtil result = adminUserServiceImpl.update(object, file);
        return result;
    }
}

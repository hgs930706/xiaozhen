package com.yishang.xiaozhen.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yishang.xiaozhen.config.jwt.JwtTokenUtil;
import com.yishang.xiaozhen.entity.AdminUser;
import com.yishang.xiaozhen.mapper.AdminUserMapper;
import com.yishang.xiaozhen.service.AdminUserServiceImpl;
import com.yishang.xiaozhen.util.DateUtil;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Autowired
    private AdminUserMapper adminUserMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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
        if(StringUtils.isEmpty(object.getPassword()) || StringUtils.isEmpty(object.getUsername())){
            return ResultUtil.error("用户名或密码不能为空");
        }
        if(!DateUtil.validateMobilePhone(object.getUsername())){
            return ResultUtil.error("用户名只支持手机号码");
        }
        if(7 >= object.getPassword().length() ||  object.getPassword().length() >= 13){
            return ResultUtil.error("密码长度不符合要求，请重新输入！");
        }
        object.setPassword(bCryptPasswordEncoder.encode(object.getPassword()));
        ResultUtil result = adminUserServiceImpl.insert(object,file);
        return result;
    }

    /**
     * 重置密码
     * @param oldPassword
     * @param newPassword
     * @param confirmPassword 确认密码
     * @return
     */
    @PostMapping("/reset")
    public ResultUtil reset(@RequestParam(name = "oldPassword") String oldPassword
            ,@RequestParam(name = "newPassword")String newPassword
            ,@RequestParam(name = "confirmPassword") String confirmPassword){

        String username = JwtTokenUtil.currentUserName();
        QueryWrapper<AdminUser> query = new QueryWrapper<>();
        query.eq("username", username);
        AdminUser user = adminUserMapper.selectOne(query);
        if(user == null){
            return ResultUtil.error("用户名不存在");
        }
        boolean matches = bCryptPasswordEncoder.matches(oldPassword, user.getPassword());
        if(matches){
            // 密码由8-12位数字，字母任意组合而成
            if(7 < newPassword.length() &&  newPassword.length() < 13){
                if(newPassword.equals(confirmPassword)){
                    user.setPassword(bCryptPasswordEncoder.encode(confirmPassword));
                    //更新密码
                    adminUserMapper.updateById(user);
                    return ResultUtil.success("密码重置成功，请重新登录！");
                }else{
                    return ResultUtil.error("新密码不一致！");
                }
            }else{
                return ResultUtil.error("密码长度不符合要求，请重新输入！");
            }
        }else{
            return ResultUtil.error("旧密码错误！");
        }
    }


    /**
     * 管理员，修改用户
     * @param object
     * @param file
     * @return
     */
    @PostMapping("/update")
    public ResultUtil update(AdminUser object, MultipartFile file){
        if(StringUtils.isEmpty(object.getId())){
            return ResultUtil.error("id不能为空!");
        }
        //重置密码
        object.setPassword(bCryptPasswordEncoder.encode(object.getPassword()));
        ResultUtil result = adminUserServiceImpl.update(object, file);
        return result;
    }
}

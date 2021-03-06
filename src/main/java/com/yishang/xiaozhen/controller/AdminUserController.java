package com.yishang.xiaozhen.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yishang.xiaozhen.config.jwt.JwtTokenUtil;
import com.yishang.xiaozhen.entity.AdminUser;
import com.yishang.xiaozhen.mapper.AdminUserMapper;
import com.yishang.xiaozhen.service.AdminUserServiceImpl;
import com.yishang.xiaozhen.util.DateUtil;
import com.yishang.xiaozhen.util.ResultUtil;
import com.yishang.xiaozhen.util.VerifyCode;
import com.yishang.xiaozhen.util.VerifyCodeGen;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * 管理用户信息 前端控制器
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@Slf4j
@RestController
@RequestMapping("/adminUser")
public class AdminUserController {

    @Autowired
    private AdminUserServiceImpl adminUserServiceImpl;

    @Autowired
    private AdminUserMapper adminUserMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/info")
    public ResultUtil info(){
        String username = JwtTokenUtil.currentUserName();
        QueryWrapper<AdminUser> query = new QueryWrapper<>();
        query.eq("username", username);
        query.eq("is_status", 1);//有效用户
        AdminUser user = adminUserMapper.selectOne(query);
        if (user == null) {
            return ResultUtil.error("用户不存在。");
        }
        return ResultUtil.success(user);
    }

    @GetMapping("/list")
    public ResultUtil list(Integer page,Integer size,String username,String role,Integer isStatus){
        if (null == page || page <= 0) {
            page = 1;
        }
        if (null == size || size <= 0) {
            size = 10;
        }
        return adminUserServiceImpl.list(page,size,username,role,isStatus);
    }

    @GetMapping("/detail")
    public ResultUtil detail(String id){
        if(StringUtils.isEmpty(id)){
            return ResultUtil.error("id不能为空!");
        }
        return ResultUtil.success(adminUserMapper.selectById(id));
    }

    @PostMapping("/insert")
    public ResultUtil insert(AdminUser object, MultipartFile file, HttpServletRequest request){
        if(StringUtils.isEmpty(object.getId())){
            if(StringUtils.isEmpty(object.getPassword()) || StringUtils.isEmpty(object.getUsername())){
                return ResultUtil.error("用户名或密码不能为空");
            }
            if(!DateUtil.validateMobilePhone(object.getUsername())){
                return ResultUtil.error("用户名只支持手机号码");
            }
//            if(7 >= object.getPassword().length() ||  object.getPassword().length() >= 13){
//                return ResultUtil.error("密码长度不符合要求，请重新输入！");
//            }
            object.setPassword(bCryptPasswordEncoder.encode(object.getPassword()));
           return adminUserServiceImpl.insert(object,file,request);
        }else{
           //更新
           return adminUserServiceImpl.update(object, file,request);
        }

    }

    /**
     * 管理员，修改用户
     * @param object
     * @param file
     * @return
     */
    @PostMapping("/update")
    public ResultUtil update(AdminUser object, MultipartFile file, HttpServletRequest request){
        if(StringUtils.isEmpty(object.getId())){
            return ResultUtil.error("id不能为空!");
        }
        //重置密码
        object.setPassword(bCryptPasswordEncoder.encode(object.getPassword()));
        ResultUtil result = adminUserServiceImpl.update(object, file,request);
        return result;
    }

    /**
     * 重置密码
     * @return
     */
    @PostMapping("/reset")
    public ResultUtil reset(@RequestBody JSONObject object){

        String oldPassword = object.getString("oldPassword");
        String newPassword = object.getString("newPassword");
        String confirmPassword = object.getString("confirmPassword");
        if(StringUtils.isEmpty(oldPassword) || StringUtils.isEmpty(newPassword) || StringUtils.isEmpty(confirmPassword)){
            return ResultUtil.error("缺少必填项");
        }
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



    @GetMapping("/verifyCode")
    public void verifyCode(HttpServletRequest request, HttpServletResponse response) {
        VerifyCodeGen iVerifyCodeGen = new VerifyCodeGen();
        try {
            //设置长宽
            VerifyCode verifyCode = iVerifyCodeGen.generate(80, 28);
            String code = verifyCode.getCode();
            log.info(code);
            //将VerifyCode绑定session
            request.getSession().setAttribute("VerifyCode", code);
            //设置响应头
            response.setHeader("Pragma", "no-cache");
            //设置响应头
            response.setHeader("Cache-Control", "no-cache");
            //在代理服务器端防止缓冲
            response.setDateHeader("Expires", 0);
            //设置响应内容类型
            response.setContentType("image/jpeg");
            response.getOutputStream().write(verifyCode.getImgBytes());
            response.getOutputStream().flush();
        } catch (IOException e) {
            log.info("", e);
        }
    }

    @GetMapping("/getverifyCode")
    public ResultUtil getverifyCode(String code,HttpServletRequest request) {
        String verifyCode = (String)request.getSession().getAttribute("VerifyCode");
        if(!StringUtils.isEmpty(code)){
            if(code.equalsIgnoreCase(verifyCode)){
                return ResultUtil.success();
            }
        }
        log.info(code);
        return ResultUtil.error("验证码不一致");
    }


    @GetMapping("/reg")
    public String reg(String username, String password) {
        AdminUser user = new AdminUser();
        user.setUsername(username);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        adminUserMapper.insert(user);
        return "注册成功。";
    }

}

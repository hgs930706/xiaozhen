package com.yishang.xiaozhen.controller.test;

import com.yishang.xiaozhen.entity.AdminUser;
import com.yishang.xiaozhen.mapper.AdminUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AdminUserMapper adminUserMapper;

    @GetMapping("/reg")
    public String reg(String username, String password) {
        AdminUser user = new AdminUser();
        user.setUsername(username);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        adminUserMapper.insert(user);
        return "注册成功。";
    }

    /**
     * 用户退出登录，调转的页面
     */
    @GetMapping("/login2")
    public void get() {
//        List<WxUserEntity> userList = wxUserRepository.selectList(null);
//        for(WxUserEntity user: userList) {
//            System.out.println(user);
//        }
        System.out.println("get");
    }

}

package com.yishang.xiaozhen.controller.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信回调
 */
@Slf4j
@RestController
@RequestMapping("/api/back")
public class WxBackApi {

//    @Autowired
//    private WxUserRepository wxUserRepository;

    @GetMapping("/test")
    public String test(){
//        List<WxUserEntity> userList = wxUserRepository.selectList(null);
//        for(WxUserEntity user: userList) {
//            System.out.println(user);
//        }
//        System.out.println("get");
        return "ok";
    }
}

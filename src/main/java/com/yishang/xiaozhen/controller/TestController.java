package com.yishang.xiaozhen.controller;

import com.yishang.xiaozhen.util.VerifyCode;
import com.yishang.xiaozhen.util.VerifyCodeGen;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @GetMapping("/verifyCode")
    public String verifyCode(HttpServletRequest request, HttpServletResponse response) {
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
            return code;
        } catch (IOException e) {
            log.info("", e);
        }
        return "111";
    }

    @GetMapping("/getverifyCode")
    public void getverifyCode(HttpServletRequest request, HttpServletResponse response) {
        String verifyCode = (String)request.getSession().getAttribute("VerifyCode");
        System.out.println(verifyCode);
    }

}

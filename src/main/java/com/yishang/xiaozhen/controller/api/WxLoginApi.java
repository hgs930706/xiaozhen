package com.yishang.xiaozhen.controller.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yishang.xiaozhen.config.jwt.JwtTokenUtil;
import com.yishang.xiaozhen.entity.WxUser;
import com.yishang.xiaozhen.mapper.WxUserMapper;
import com.yishang.xiaozhen.util.HttpClientUtil;
import com.yishang.xiaozhen.config.WxBaseConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 微信登录
 */
@Slf4j
@RestController
@RequestMapping("/api/wx")
public class WxLoginApi {

    @Autowired
    private WxUserMapper wxUserMapper;

    /**
     * 第一步：C端直接调用此接口，引导用户同意授权，获取code
     *
     * @param response
     * @throws IOException
     */
    @GetMapping("/login")
    public void login(HttpServletResponse response) throws IOException {

        response.sendRedirect(WxBaseConfig.getWebUrl());
    }

    /**
     * 第二步：通过code换取网页授权access_token
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/callBack")
    public void callBack(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        String code = request.getParameter("code");
        String json = HttpClientUtil.get(WxBaseConfig.getCode(code));

        JSONObject obj = JSON.parseObject(json);
        String accessToken = obj.getString("access_token");
        String openId = obj.getString("openid");

        String infoJson = HttpClientUtil.get(WxBaseConfig.getBaseUserInfo(accessToken,openId));
        log.info("从微信获取的用户信息：{}", infoJson);

        //保存微信端授权用户
        this.saveWxUser(infoJson);

        //默认微信用户权限
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_WX");
        String token = JwtTokenUtil.createToken(openId, roles, true);
//         这里就可以配置，跳转路径，例如成功获取用户信息之后，我们跳转到我们自己的首页
        response.sendRedirect("http://localhost:8080/#/view/home?token="+ JwtTokenUtil.TOKEN_PREFIX + token);
        log.info("微信客户端token：{}", token);
    }

    private void saveWxUser(String infoJson) {
        JSONObject jsonObject = JSON.parseObject(infoJson);
        String openId = jsonObject.getString("openid");
        QueryWrapper<WxUser> query = new QueryWrapper<>();
        query.eq("open_id", openId).eq("is_status", 1);
        Integer count = wxUserMapper.selectCount(query);
        if (count > 0) {
            log.info("此用户已维护。");// 用户信息已维护，那就执行一次更新，防止用户更新了基本信息
        }else {
            //保存用户信息
            WxUser user = new WxUser();
            user.setNickname(jsonObject.getString("nickname"));
            user.setSex(Integer.parseInt(jsonObject.getString("sex")));
            user.setHeadImgurl(jsonObject.getString("headimgurl"));
            user.setOpenId(openId);
            wxUserMapper.insert(user);
            log.info("新增用户成功！");
        }
    }



    @GetMapping("/test")
    public void test(HttpServletRequest request,HttpServletResponse response){
        System.out.println(request.getAttribute("token"));
        System.out.println(request.getParameter("token"));
    }


    /**
     * 对接微信
     *
     * @param req
     * @param resp
     */
    @GetMapping
    public void get(HttpServletRequest req, HttpServletResponse resp) {
        // 测试，获取所有参数
        Enumeration enu = req.getParameterNames();
        while (enu.hasMoreElements()) {
            String paraName = (String) enu.nextElement();
            System.out.println(paraName + ": " + req.getParameter(paraName));
        }

        String signature = req.getParameter("signature");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        String echostr = req.getParameter("echostr");
        System.out.println("微信回发的消息：" + signature);
        PrintWriter out = null;
        try {
            out = resp.getWriter();
            if (WxBaseConfig.checkSignature(signature, timestamp, nonce)) {
                out.print(echostr);
                System.out.println("微信接入成功！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    /**
     * 处理微信，响应的消息
     *
     * @param req
     * @param resp
     */
    @PostMapping
    public void post(HttpServletRequest req, HttpServletResponse resp) {


    }

}

package com.yishang.xiaozhen.controller.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yishang.xiaozhen.config.jwt.JwtTokenUtil;
import com.yishang.xiaozhen.entity.WxUser;
import com.yishang.xiaozhen.mapper.WxUserMapper;
import com.yishang.xiaozhen.util.HttpClientUtil;
import com.yishang.xiaozhen.util.WxBaseUtil;
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
import java.net.URLEncoder;
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

    //测试公众号
    private static final String AppID = "wx3d9b64b22ab377cf";
    private static final String AppSecret = "819f315a68bb826b8337cf6faeb9d80b";

    //如果用户同意授权，页面将跳转至此地址
    private static final String BACK_URL = "http://27816r3s27.wicp.vip/api/wx/callBack";

    //第一步：用户同意授权，获取code
    private static final String INDEX = "https://open.weixin.qq.com/connect/oauth2/authorize" +
            "?appid=APPID" +
            "&redirect_uri=BACK_URL" +
            "&response_type=code" +
            "&scope=snsapi_userinfo" +
            "&state=STATE#wechat_redirect";

    //第二步：通过code换取网页授权access_token
    private static final String INDEX2 = "https://api.weixin.qq.com/sns/oauth2/access_token" +
            "?appid=APPID" +
            "&secret=SECRET" +
            "&code=CODE" +
            "&grant_type=authorization_code";

    //第四步：拉取用户信息(需scope为 snsapi_userinfo)
    private static final String INDEX3 = "https://api.weixin.qq.com/sns/userinfo" +
            "?access_token=ACCESS_TOKEN" +
            "&openid=OPENID&lang=zh_CN";

    @Autowired
    private WxUserMapper wxUserMapper;

    /**
     * 第一步：C端直接调用此接口，引导用户同意授权，获取code
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/login")
    public void login(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        String url = INDEX.replace("APPID", AppID).replace("BACK_URL", URLEncoder.encode(BACK_URL));
        response.sendRedirect(url);
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
        String url = INDEX2.replace("APPID", AppID).replace("SECRET", AppSecret).replace("CODE", code);
        String json = HttpClientUtil.get(url);

        JSONObject obj = JSON.parseObject(json);
        String openId = obj.getString("openid");
        String accessToken = obj.getString("access_token");

        String infoUrl = INDEX3.replace("ACCESS_TOKEN",accessToken).replace("OPENID",openId);
        String infoJson = HttpClientUtil.get(infoUrl);
        log.info("从微信获取的用户信息：{}", infoJson);

        //保存微信端授权用户
        this.saveWxUser(infoJson);

        //默认微信用户权限
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_WX");
        String token = JwtTokenUtil.createToken(openId, roles, false);
        // 这里就可以配置，跳转路径，例如成功获取用户信息之后，我们跳转到我们自己的首页
        response.setHeader("token", JwtTokenUtil.TOKEN_PREFIX + token);
        response.sendRedirect("http://news.baidu.com/");

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
            if (WxBaseUtil.checkSignature(signature, timestamp, nonce)) {
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

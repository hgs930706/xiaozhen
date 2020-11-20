package com.yishang.xiaozhen.controller.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yishang.xiaozhen.config.jwt.JwtTokenUtil;
import com.yishang.xiaozhen.entity.WxUser;
import com.yishang.xiaozhen.util.HttpClientUtil;
import com.yishang.xiaozhen.util.WxBaseUtil;
import lombok.extern.slf4j.Slf4j;
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

/**
 * 微信登录
 */
@Slf4j
@RestController
@RequestMapping("/api/wx")
public class WxApi {

//    @Autowired
//    private WxUserRepository wxUserRepository;

    //正式公众号
//    private static final String AppID = "wx5f8846d229ab65ea";
//    private static final String AppSecret = "e53f76a97f11fed5d6d7b9bc30e18ed7";

    //测试公众号
    private static final String AppID = "wx3d9b64b22ab377cf";
    private static final String AppSecret = "819f315a68bb826b8337cf6faeb9d80b";



    /**
     * 第一步：C端直接调用此接口，引导用户同意授权，获取code
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/login")
    public void login(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        String backUrl = "http://27816r3s27.wicp.vip/wx/callBack";
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
                "appid=" + AppID +
                "&redirect_uri=" + URLEncoder.encode(backUrl) +
                "&response_type=code" +
                "&scope=snsapi_userinfo" +
                "&state=STATE#wechat_redirect";
        response.sendRedirect(url);
    }

    /**
     * 第二步：通过code换取网页授权access_token
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/callBack")
    public void callBack(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        Enumeration enu=request.getParameterNames();
        while(enu.hasMoreElements()){
            String paraName=(String)enu.nextElement();
            System.out.println(paraName+": "+request.getParameter(paraName));
        }
        String code = request.getParameter("code");
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=" + AppID +
                "&secret="+ AppSecret +
                "&code=" + code +
                "&grant_type=authorization_code";
        String json = HttpClientUtil.get(url);
        JSONObject obj = JSON.parseObject(json);
        String openId = obj.getString("openid");
        String accessToken = obj.getString("access_token");

        String infoUrl = "https://api.weixin.qq.com/sns/userinfo?" +
                "access_token=" + accessToken +
                "&openid=" + openId +
                "&lang=zh_CN";
        String infoJson = HttpClientUtil.get(infoUrl);
        log.info("从微信获取的用户信息：{}",infoJson);
        //保存微信端授权用户
        this.saveWxUser(infoJson);

        // 这里就可以配置，跳转路径，例如成功获取用户信息之后，我们跳转到我们自己的首页
//        response.setHeader("token", JwtTokenUtil.TOKEN_PREFIX + token);
//        response.sendRedirect("http://news.baidu.com/");
        PrintWriter out = response.getWriter();
        String token = JwtTokenUtil.createToken(openId, new ArrayList<>(),false);
        log.info("微信客户端token：{}",token);
        try{
            out.print(token);
        }catch (Exception e){

        }finally {
            out.close();
        }

    }
    private void saveWxUser(String infoJson){
        JSONObject jsonObject = JSON.parseObject(infoJson);
        String openId = jsonObject.getString("openid");
        QueryWrapper<WxUser> query = new QueryWrapper<>();
        query.eq("open_id", openId).eq("status", 1);
//        Integer count = wxUserRepository.selectCount(query);
        if(1 > 0){
            log.info("此用户已维护。");
            // todo 用户信息已维护，那就执行一次更新，防止用户更新了基本信息
            return;
        }
        //保存用户信息
        WxUser user = new WxUser();
        user.setNickname(jsonObject.getString("nickname"));
        user.setSex(Integer.parseInt(jsonObject.getString("sex")));
        user.setHeadImgurl(jsonObject.getString("headimgurl"));
        user.setOpenId(openId);
//        wxUserRepository.insert(user);
        log.info("新增用户成功！");
    }



    /**
     * 对接微信
     * @param req
     * @param resp
     */
    @GetMapping
    public void get(HttpServletRequest req, HttpServletResponse resp) {
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
     * @param req
     * @param resp
     */
    @PostMapping
    public void post(HttpServletRequest req, HttpServletResponse resp) {


    }

}

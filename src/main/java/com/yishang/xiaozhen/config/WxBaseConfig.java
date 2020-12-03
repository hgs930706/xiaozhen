package com.yishang.xiaozhen.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yishang.xiaozhen.entity.wx.AccessToken;
import com.yishang.xiaozhen.util.HttpClientUtil;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * 对接微信的基本配置
 */

public class WxBaseConfig {

    //测试公众号
//    private static final String APPID = "wx3d9b64b22ab377cf";
//    private static final String APPSECRET = "819f315a68bb826b8337cf6faeb9d80b";
    //正式公众号
    private static final String APPID = "wx5f8846d229ab65ea";
    private static final String APPSECRET = "e53f76a97f11fed5d6d7b9bc30e18ed7";


    //接入微信的签名
    private static final String TOKEN = "fanren";

    //https请求方式: GET
    private static final String ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    //http请求方式: POST
    private static final String TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";



    //第一步：用户同意授权，获取code
    private static final String INDEX = "https://open.weixin.qq.com/connect/oauth2/authorize" +
            "?appid=APPID" +
            "&redirect_uri=BACK_URL" +
            "&response_type=code" +
            "&scope=snsapi_userinfo" +
            "&state=STATE#wechat_redirect";
    //如果用户同意授权，页面将跳转至此地址
    private static final String BACK_URL = "http://27816r3s27.wicp.vip/api/wx/callBack";
    // 也可以直接回调到前端,然后前端拿着code，在请求后台
    // private static final String BACK_URL = "http://27816r3s27.wicp.vip:32056/login";

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

    //存储accessToken,避免频繁获取
    private static AccessToken at;

    //网页授权
    public static String getWebUrl(){
        String url = INDEX.replace("APPID", APPID).replace("BACK_URL", URLEncoder.encode(BACK_URL));
        return url;
    }

    //根据code获取网页授权的accessToken
    public static String getCode(String  code){
        String url = INDEX2.replace("APPID", APPID).replace("SECRET", APPSECRET).replace("CODE", code);
        return url;
    }

    public static String getBaseUserInfo(String accessToken,String openId){
        String infoJson = INDEX3.replace("ACCESS_TOKEN",accessToken).replace("OPENID",openId);
        return infoJson;
    }

    public static String getTemplateURL(){
        // 执行消息模板发送
        String templateURL = TEMPLATE_URL.replace("ACCESS_TOKEN",getAccessToken());
        return templateURL;
    }

    // 向外暴露
    public static synchronized String getAccessToken(){
        if(at == null || at.isExpired()){
            getToken();
        }
        return at.getAccessToken();
    }
    /**
     * 获取accessToken
     * @return
     */
    private static void getToken(){
        String accessTokenURL = ACCESS_TOKEN.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
        String json = HttpClientUtil.get(accessTokenURL);

        JSONObject obj = JSON.parseObject(json);
        String accessToken = obj.getString("access_token");
        String expiresIn = obj.getString("expires_in");
        at = new AccessToken(accessToken,expiresIn);
    }




    /**
     * 校验是不是来自微信服务器的请求
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        if (signature == null) {
            return false;
        }
        String[] paramArr = new String[] { TOKEN, timestamp, nonce };
        Arrays.sort(paramArr);

        String content = paramArr[0].concat(paramArr[1]).concat(paramArr[2]);

        String ciphertext = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(content.toString().getBytes());
            ciphertext = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return signature.equalsIgnoreCase(ciphertext);
    }
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (byte element : byteArray) {
            strDigest += byteToHexStr(element);
        }
        return strDigest;
    }
    private static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        String s = new String(tempArr);
        return s;
    }





}

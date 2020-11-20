package com.yishang.xiaozhen.util;

import com.alibaba.fastjson.JSON;
import com.yishang.xiaozhen.entity.wx.AccessToken;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * 对接微信的基本配置
 */
public class WxBaseUtil {

    //接入微信的签名
    private static final String TOKEN = "fanren";

    private static final String APPID = "wx5f8846d229ab65ea";

    private static final String APPSECRET = "e53f76a97f11fed5d6d7b9bc30e18ed7";

    //https请求方式: GET
    private static final String ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    //http请求方式: POST
    private static final String TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

    /**
     * 获取accessToken
     * @return
     */
    public static AccessToken getAccessToken(){
        String accessTokenURL = ACCESS_TOKEN.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
        String json = HttpClientUtil.get(accessTokenURL);
        AccessToken accessToken = JSON.parseObject(json, AccessToken.class);
        return accessToken;
    }

    public static String getTemplateURL(){
        // 执行消息模板发送
        String templateURL = TEMPLATE_URL.replace("ACCESS_TOKEN",getAccessToken().getAccessToken());
        return templateURL;
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

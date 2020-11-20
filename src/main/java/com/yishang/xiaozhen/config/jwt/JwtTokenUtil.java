package com.yishang.xiaozhen.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class JwtTokenUtil {

    // 添加角色的key
    private static final String ROLE_CLAIMS = "rol";

    //jwt 服务器秘钥
    private static final String SECRET = "jwtsecretdemo";

    //发行人，持票人
    private static final String ISS = "echisan";

    // 过期时间是3600秒，既是1个小时
    private static final long EXPIRATION = 300L;

    // 选择了记住我之后的过期时间为7天
    private static final long EXPIRATION_REMEMBER = 604800L;

    public static final String TOKEN_HEADER = "Authorization";

    public static final String TOKEN_PREFIX = "Bearer ";

    // 修改一下创建token的方法
    public static String createToken(String username, List<String> roles, boolean isRememberMe) {
        long expiration = isRememberMe ? EXPIRATION_REMEMBER : EXPIRATION;
        HashMap<String, Object> map = new HashMap<>();
        map.put(ROLE_CLAIMS, roles);
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET)
                // 这里要早set一点，放到后面会覆盖别的字段
                .setClaims(map)
                .setIssuer(ISS)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
    }


    // 从token中获取用户名
    public static String getUsername(String token){
        return getTokenBody(token).getSubject();
    }

    /**
     * 获取用户角色
     * @param token
     * @return
     */
    public static List<String> getUserRole(String token){
        return (List<String>)getTokenBody(token).get(ROLE_CLAIMS);
    }

    // 是否已过期
    public static boolean isExpiration(String token){
        return getTokenBody(token).getExpiration().before(new Date());
//        ExpiredJwtException
    }

    private static Claims getTokenBody(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
        log.info("jwt解析的内容：{}",claims);
        return claims;
    }

    public static void main(String[] args) {

        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJodWdhb3N1Iiwia" +
                "XNzIjoiZWNoaXNhbiIsImV4cCI6MTYwNTI1ODY0MSwiaWF0IjoxNjA" +
                "1MjU4MzQxLCJyb2wiOlsiUk9MRV9PTkUiLCJST0xFX1R" +
                "XTyJdfQ.huXs2VK-FH3q-qTxt65uKn2rw43mpdK24xwnoL8gGKjx" +
                "GNt5_mmXLTj6l5Vr1dYz-ARwJcwe2kBxPXWQ_bGYcA";

//        System.out.println(getUsername(token));
        System.out.println(isExpiration(token));
//        System.out.println((getTokenBody(token).getExpiration().getTime() - System.currentTimeMillis()) > 5 * 1000);

    }

    public static String getUserName(){
        if(SecurityContextHolder.getContext() == null) {
            return "暂时没登录，所以没有用户！";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "测试用户";
    }
}

package com.yishang.xiaozhen.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
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
    private static final String SECRET = "yishangxiaozhen";

    //发行人，持票人
    private static final String ISS = "fanren";

    // 过期时间是3600秒，既是1个小时
    private static final long EXPIRATION = 3600L;

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
        try {
            return getTokenBody(token).getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    private static Claims getTokenBody(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

    public static String currentUserName(){
        if(SecurityContextHolder.getContext() == null) {
            return "";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (String) authentication.getPrincipal();
    }
}

package com.yishang.xiaozhen.config.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String tokenHeader = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
        // 如果请求头中没有Authorization信息则直接放行了

        if(tokenHeader == null || !tokenHeader.startsWith(JwtTokenUtil.TOKEN_PREFIX)){
            chain.doFilter(request,response);
            return;
        }
        // 如果请求头中有token，则进行解析，并且设置认证信息
        String token = tokenHeader.replace(JwtTokenUtil.TOKEN_PREFIX, "");
        try{
            String username = JwtTokenUtil.getUsername(token);
            List<String> list = JwtTokenUtil.getUserRole(token);
            List<GrantedAuthority> roles = new ArrayList<>();
            for (String role : list) {
                roles.add(new SimpleGrantedAuthority(role));
            }
            if (username != null){
                // 这里从token中获取用户信息并放入上下文
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(username,null,roles)
                );
            }
        }catch (ExpiredJwtException e){
            log.error("jwt已过期。");
        }catch (Exception e){
            log.error("jwt解析异常。");
        }
        super.doFilterInternal(request, response, chain);
    }


    private Authentication getAuthentication(String tokenHeader) {

        return null;
    }
}

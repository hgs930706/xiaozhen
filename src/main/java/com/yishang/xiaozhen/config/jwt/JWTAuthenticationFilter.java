package com.yishang.xiaozhen.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yishang.xiaozhen.entity.wx.LoginUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
        //自定义登录路径
//        super.setFilterProcessesUrl("/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 从输入流中获取到登录的信息
        try {
            LoginUser user = new ObjectMapper().readValue(request.getInputStream(), LoginUser.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), new ArrayList<>());
            //这一步才会调用loadUserByUsername
            return authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        // g查看源代码会发现调用etPrincipal()方法会返回一个实现了`UserDetails`接口的对象
        JwtUser jwtUser = (JwtUser) authResult.getPrincipal();
        List<String> roles = new ArrayList();
        // 因为在JwtUser中存了权限信息，可以直接获取，由于只有一个角色就这么干了
        Collection<? extends GrantedAuthority> authorities = jwtUser.getAuthorities();
        for (GrantedAuthority authority : authorities){
            roles.add(authority.getAuthority());
        }
        // 根据用户名，角色创建token
        String token = JwtTokenUtil.createToken(jwtUser.getUsername(), roles,false);

        // 按照jwt的规定，最后请求的格式应该是 `Bearer token`
        response.setHeader("token", JwtTokenUtil.TOKEN_PREFIX + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        response.getWriter().write("authentication failed, reason: " + failed.getMessage());
    }


}

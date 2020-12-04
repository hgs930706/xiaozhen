package com.yishang.xiaozhen.config.security;


import com.yishang.xiaozhen.config.jwt.JWTAuthenticationFilter;
import com.yishang.xiaozhen.config.jwt.JWTAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @Autowired
    private MyAuthenticationEntryPoint myAuthenticationEntryPoint;

    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;

    // 加密密码的
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //SpringSecurity会自动寻找name=corsConfigurationSource的Bean
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //  你需要跨域的地址  注意这里的 127.0.0.1 != localhost
        // * 表示对所有的地址都可以访问
        corsConfiguration.addAllowedOrigin("*");
        //  跨域的请求头
        corsConfiguration.addAllowedHeader("*"); // 2
        //  跨域的请求方法
        corsConfiguration.addAllowedMethod("*"); // 3
        //加上了这一句，大致意思是可以携带 cookie
        //最终的结果是可以 在跨域请求的时候获取同一个 session
        corsConfiguration.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    /**
     * 添加 UserDetailsService， 实现自定义登录校验
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                //用户认证处理
                .userDetailsService(userDetailsService)
                //密码处理
                .passwordEncoder(bCryptPasswordEncoder());
    }
    @Override
    public void configure(WebSecurity web){
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers("/images/**");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 允许跨域访问
        http.cors()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 不需要session
                .and()
                .authorizeRequests()
//                .antMatchers("/images/**").permitAll()//这里也可以配置静态资源
                .antMatchers("/api/activityBooking/insert").hasAuthority("ROLE_WX")
                .antMatchers("/api/meetingAreaBooking/insert").hasAuthority("ROLE_WX")
                .antMatchers("/api/receiveBooking/insert").hasAuthority("ROLE_WX")
                .antMatchers("/api/feedback/insert").hasAuthority("ROLE_WX")
                .antMatchers("/api/wxUser/detail").hasAuthority("ROLE_WX")
                .antMatchers("/api/msgAction/msgs").hasAuthority("ROLE_WX")
                .antMatchers("/api/feedback/bookings").hasAuthority("ROLE_WX")
                .antMatchers("/api/**").permitAll()
                .antMatchers("/adminUser/verifyCode").permitAll()
                .antMatchers("/adminUser/getverifyCode").permitAll()
                .antMatchers("/adminUser/insert").hasAuthority("ROLE_USER")
                .antMatchers("/activityBooking/approval").hasAuthority("ROLE_ACTIVITY_APPROVAL")
                .antMatchers("/meetingAreaBooking/approval").hasAuthority("ROLE_MEETING_AREA_APPROVAL")
                .antMatchers("/receiveBooking/approval").hasAuthority("ROLE_RECEIVE_APPROVAL")
                .antMatchers("/meetingArea/insert").hasAuthority("ROLE_MEETING_AREA_MANAGER")
                .antMatchers("/activity/insert").hasAuthority("ROLE_ACTIVITY_MANAGER")
                .anyRequest()
                .authenticated()//其它接口只需要验证，不需要权限
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(myAuthenticationEntryPoint)
                .accessDeniedHandler(myAccessDeniedHandler)
//                .logout().logoutSuccessUrl("/admin/login2")
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()));
    }
}

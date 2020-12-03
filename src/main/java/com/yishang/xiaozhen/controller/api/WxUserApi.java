package com.yishang.xiaozhen.controller.api;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yishang.xiaozhen.config.jwt.JwtTokenUtil;
import com.yishang.xiaozhen.entity.WxUser;
import com.yishang.xiaozhen.mapper.WxUserMapper;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 微信用户信息 微信端
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@RestController
@RequestMapping("/api/wxUser")
public class WxUserApi {

    @Autowired
    private WxUserMapper wxUserMapper;

    @GetMapping("/detail")
    public ResultUtil detail(){
        String openId = JwtTokenUtil.currentUserName();
        QueryWrapper query = new QueryWrapper();
        query.eq("open_id",openId);
        WxUser wxUser = wxUserMapper.selectOne(query);
        if(wxUser == null){
            ResultUtil.error("用户未登录");
        }
        return ResultUtil.success(wxUser);
    }
}

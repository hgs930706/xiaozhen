package com.yishang.xiaozhen.controller;


import com.yishang.xiaozhen.entity.Authority;
import com.yishang.xiaozhen.mapper.AuthorityMapper;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@RestController
@RequestMapping("/authority")
public class AuthorityController {

    @Autowired
    private AuthorityMapper authorityMapper;

    @PostMapping("/insert")
    public ResultUtil insert(Authority object) {

        Authority a1 = new  Authority();
        a1.setName("ROLE_RECEIVE_APPROVAL");
        a1.setDisplayName("接待预约审批");
        authorityMapper.insert(a1);

        Authority a2 = new  Authority();
        a2.setName("ROLE_ACTIVITY_APPROVAL");
        a2.setDisplayName("活动审批");
        authorityMapper.insert(a2);

        Authority a3 = new  Authority();
        a3.setName("ROLE_MEETING_AREA_APPROVAL");
        a3.setDisplayName("会议场地审批");
        authorityMapper.insert(a3);

        Authority a4 = new  Authority();
        a4.setName("ROLE_MEETING_AREA_MANAGER");
        a4.setDisplayName("会议场地管理");
        authorityMapper.insert(a4);

        Authority a5 = new  Authority();
        a5.setName("ROLE_ACTIVITY_MANAGER");
        a5.setDisplayName("活动管理");
        authorityMapper.insert(a5);

        Authority a6 = new  Authority();
        a6.setName("ROLE_MSG_ACTION");
        a6.setDisplayName("消息日志");
        authorityMapper.insert(a6);

        Authority a7 = new  Authority();
        a7.setName("ROLE_MSG_TEMPLATE");
        a7.setDisplayName("消息模板");
        authorityMapper.insert(a7);

        Authority a8 = new  Authority();
        a8.setName("ROLE_FEEDBACK");
        a8.setDisplayName("意见反馈");
        authorityMapper.insert(a8);


        Authority a9 = new  Authority();
        a9.setName("ROLE_ENTERPRISE");
        a9.setDisplayName("企业管理");
        authorityMapper.insert(a9);

        Authority a10 = new  Authority();
        a10.setName("ROLE_MAP");
        a10.setDisplayName("地图管理");
        authorityMapper.insert(a10);

        Authority a11 = new  Authority();
        a11.setName("ROLE_USER");
        a11.setDisplayName("用户管理");
        authorityMapper.insert(a11);



        return null;
    }
}

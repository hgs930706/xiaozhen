package com.yishang.xiaozhen.controller;


import com.yishang.xiaozhen.entity.RoleAuthority;
import com.yishang.xiaozhen.mapper.RoleAuthorityMapper;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 角色与权限关系表 前端控制器
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@RestController
@RequestMapping("/roleAuthority")
public class RoleAuthorityController {

    @Autowired
    private RoleAuthorityMapper roleAuthorityMapper;

    @PostMapping("/insert")
    public ResultUtil insert(RoleAuthority object) {
        // 场地管理员、0087f1a0d01df131be61ef3651f3518f
        RoleAuthority ra1 = new RoleAuthority();
        ra1.setRoleId("0087f1a0d01df131be61ef3651f3518f");
        ra1.setAuthorityId("66ac92e51b2bfeeaaf50bbd74c5ca599");
        roleAuthorityMapper.insert(ra1);

        RoleAuthority ra2 = new RoleAuthority();
        ra2.setRoleId("0087f1a0d01df131be61ef3651f3518f");
        ra2.setAuthorityId("aaacb785a976ef24d99768ce9f891659");
        roleAuthorityMapper.insert(ra2);

        // 活动管理员、ffcd942f461b847c1db039f3963f8125
        RoleAuthority ra3 = new RoleAuthority();
        ra3.setRoleId("ffcd942f461b847c1db039f3963f8125");
        ra3.setAuthorityId("b4d81180fd885d2792ec05ecb739f5ec");
        roleAuthorityMapper.insert(ra3);

        RoleAuthority ra4 = new RoleAuthority();
        ra4.setRoleId("ffcd942f461b847c1db039f3963f8125");
        ra4.setAuthorityId("edb34f70713edc5e7c3c0806f2f0ab30");
        roleAuthorityMapper.insert(ra4);

        // 接待管理员、a2881f1383a9c67c627dd03b6481f29d
        RoleAuthority ra5 = new RoleAuthority();
        ra5.setRoleId("a2881f1383a9c67c627dd03b6481f29d");
        ra5.setAuthorityId("2685bff20e0788f844abaed471913ad6");
        roleAuthorityMapper.insert(ra5);

        // 超级管理员、7391efee113209475175c379da439db5
        RoleAuthority ra6 = new RoleAuthority();
        ra6.setRoleId("7391efee113209475175c379da439db5");
        ra6.setAuthorityId("0a315c84babc166dfc8b5d309393582d");
        roleAuthorityMapper.insert(ra6);
        RoleAuthority ra61 = new RoleAuthority();
        ra61.setRoleId("7391efee113209475175c379da439db5");
        ra61.setAuthorityId("2685bff20e0788f844abaed471913ad6");
        roleAuthorityMapper.insert(ra61);
        RoleAuthority ra62 = new RoleAuthority();
        ra62.setRoleId("7391efee113209475175c379da439db5");
        ra62.setAuthorityId("343a7f898e7bbfdfae5b871a1d0d21b6");
        roleAuthorityMapper.insert(ra62);
        RoleAuthority ra63 = new RoleAuthority();
        ra63.setRoleId("7391efee113209475175c379da439db5");
        ra63.setAuthorityId("66ac92e51b2bfeeaaf50bbd74c5ca599");
        roleAuthorityMapper.insert(ra63);
        RoleAuthority ra64 = new RoleAuthority();
        ra64.setRoleId("7391efee113209475175c379da439db5");
        ra64.setAuthorityId("aaacb785a976ef24d99768ce9f891659");
        roleAuthorityMapper.insert(ra64);
        RoleAuthority ra65 = new RoleAuthority();
        ra65.setRoleId("7391efee113209475175c379da439db5");
        ra65.setAuthorityId("ab7bf5bcc52e65ed20fd794a6408a6ee");
        roleAuthorityMapper.insert(ra65);
        RoleAuthority ra66 = new RoleAuthority();
        ra66.setRoleId("7391efee113209475175c379da439db5");
        ra66.setAuthorityId("edb34f70713edc5e7c3c0806f2f0ab30");
        roleAuthorityMapper.insert(ra66);
        RoleAuthority ra67 = new RoleAuthority();
        ra67.setRoleId("7391efee113209475175c379da439db5");
        ra67.setAuthorityId("b4d81180fd885d2792ec05ecb739f5ec");
        roleAuthorityMapper.insert(ra67);
        RoleAuthority ra68 = new RoleAuthority();
        ra68.setRoleId("7391efee113209475175c379da439db5");
        ra68.setAuthorityId("ccad846082cbe9b06ae75929090ed2f0");
        roleAuthorityMapper.insert(ra68);
        RoleAuthority ra69 = new RoleAuthority();
        ra69.setRoleId("7391efee113209475175c379da439db5");
        ra69.setAuthorityId("dcc86a0d58e60a3fbfcf29b8ac1e95a8");
        roleAuthorityMapper.insert(ra69);
        RoleAuthority ra610 = new RoleAuthority();
        ra610.setRoleId("7391efee113209475175c379da439db5");
        ra610.setAuthorityId("dfd586e4ce7569bc07cbd27bd3272d25");
        roleAuthorityMapper.insert(ra610);


        return null;
    }
}

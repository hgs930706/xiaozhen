package com.yishang.xiaozhen.controller.api;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 企业入驻信息表 微信端
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@RestController
@RequestMapping("/api/enterpriseInfo")
public class EnterpriseInfoApi {

    /**
     * 企业列表，
     * @param streetType 街区类型
     * @return 返回当前街区类型，所有企业
     */
    @GetMapping("/list")
    public String list(@RequestParam(name = "streetType") Integer streetType){



        return null;
    }


    /**
     * 企业详情
     * @param id
     * @return
     */
    @GetMapping("/detail")
    public String detail(@RequestParam(name = "id") String id){



        return null;
    }



    /**
     * 默认返回一个企业，用于首页的展示
     * @return
     */
    @GetMapping("/default")
    public String default_(){



        return null;
    }

}

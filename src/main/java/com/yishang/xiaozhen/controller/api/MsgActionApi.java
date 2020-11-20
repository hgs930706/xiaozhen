package com.yishang.xiaozhen.controller.api;


import com.yishang.xiaozhen.service.MsgActionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  消息日志表 微信端
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@RestController
@RequestMapping("/api/msgAction")
public class MsgActionApi {

    @Autowired
    private MsgActionServiceImpl msgActionServiceImpl;


    /**
     * 我的消息列表，（包括：接待预约，会议场地预约，报名消息）
     * @return
     */
    @GetMapping("/msgs")
    public String msgs(){



        return null;
    }

    @GetMapping("/detail")
    public String detail(@RequestParam(name = "id") String id){

        return null;
    }



}
